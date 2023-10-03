package com.mapbox.maps.renderer.egl

import android.opengl.*
import android.os.Handler
import android.os.Looper
import android.view.Surface
import androidx.annotation.RestrictTo
import androidx.annotation.WorkerThread
import com.mapbox.maps.MapView
import com.mapbox.maps.logE
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import com.mapbox.maps.renderer.RendererError
import com.mapbox.maps.renderer.RendererSetupErrorListener
import java.util.*

/**
 * Core EGL state (display, context, config).
 *
 * The EGLContext must only be attached to one thread at a time. This class is not thread-safe.
 *
 * Inspired by [Grafika](https://github.com/google/grafika/blob/master/app/src/main/java/com/android/grafika/gles/EglCore.java)
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class EGLCore(
  private val translucentSurface: Boolean,
  private val antialiasingSampleCount: Int,
  private val sharedContext: EGLContext = EGL14.EGL_NO_CONTEXT,
) {
  private lateinit var eglConfig: EGLConfig
  private var eglDisplay: EGLDisplay = EGL14.EGL_NO_DISPLAY
  internal var eglContext: EGLContext = EGL14.EGL_NO_CONTEXT

  internal val eglNoSurface: EGLSurface = EGL14.EGL_NO_SURFACE

  /**
   * For user convenience we will deliver [RendererSetupErrorListener] on main thread.
   */
  private val mainHandler = Handler(Looper.getMainLooper())
  /**
   * We require error accumulation as renderer is created when [MapView] is created and
   * some errors could already be triggered on render thread
   * before user will call [MapView.addRendererSetupErrorListener] -
   * in that case accumulated errors will still be delivered to the user.
   */
  private val accumulatedRendererErrorList = LinkedList<RendererError>()
  private val rendererSetupErrorListenerSet = HashSet<RendererSetupErrorListener>()

  fun prepareEgl(): Boolean {
    eglDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY)
    if (eglDisplay == EGL14.EGL_NO_DISPLAY) {
      // no need to report error upstream here due to https://www.khronos.org/registry/EGL/sdk/docs/man/html/eglGetDisplay.xhtml
      logW(TAG, "Unable to get default display, eglInitialize will most likely fail shortly.")
    }
    val versions = IntArray(2)
    if (!EGL14.eglInitialize(
        /* dpy = */ eglDisplay,
        /* major = */ versions,
        /* majorOffset = */ 0,
        /* minor = */ versions,
        /* minorOffset = */ 1
      )
    ) {
      checkEglErrorAndNotify("eglInitialize")
      return false
    }

    EGLConfigChooser(translucentSurface, antialiasingSampleCount).chooseConfig(eglDisplay)?.let {
      eglConfig = it
    } ?: run {
      notifyListeners(RendererError.NO_VALID_EGL_CONFIG_FOUND)
      return false
    }
    val contextEgl3 = EGL14.eglCreateContext(
      /* dpy = */ eglDisplay,
      /* config = */ eglConfig,
      /* share_context = */ sharedContext,
      /* attrib_list = */ attribsEgl3,
      /* offset = */ 0,
    )

    val notSupportingEglContext3 = checkEglError("eglCreateContext") != null
    eglContext = if (notSupportingEglContext3) {
      val contextEgl2 = EGL14.eglCreateContext(
        /* dpy = */ eglDisplay,
        /* config = */ eglConfig,
        /* share_context = */ sharedContext,
        /* attrib_list = */ attribsEgl2,
        /* offset = */ 0,
      )
      checkEglErrorAndNotify("eglCreateContext")
      contextEgl2
    } else {
      contextEgl3
    }

    val eglVersion = queryContextVersion()
    logI(TAG, "EGLContext created, client version $eglVersion")
    return true
  }

  fun queryContextVersion(): Int {
    val values = IntArray(1)
    val eglQueryContextSuccess = EGL14.eglQueryContext(
      /* dpy = */ eglDisplay,
      /* ctx = */ eglContext,
      /* attribute = */ EGL14.EGL_CONTEXT_CLIENT_VERSION,
      /* value = */ values,
      /* offset = */ 0,
    )
    if (!eglQueryContextSuccess) {
      checkEglErrorAndNotify("eglQueryContext")
    }
    return values[0]
  }

  /**
   * Discards all resources held by this class, notably the EGL context.  This must be
   * called from the thread where the context was created.
   *
   * On completion, no context will be current.
   */
  fun release() {
    if (eglDisplay != EGL14.EGL_NO_DISPLAY) {
      // Android is unusual in that it uses a reference-counted EGLDisplay.  So for
      // every eglInitialize() we need an eglTerminate().
      makeNothingCurrent()
      EGL14.eglDestroyContext(eglDisplay, eglContext)
      EGL14.eglTerminate(eglDisplay)
    }
    eglDisplay = EGL14.EGL_NO_DISPLAY
    eglContext = EGL14.EGL_NO_CONTEXT
  }

  /**
   * Destroys the specified surface. Note the EGLSurface won't actually be destroyed if it's
   * still current in a context.
   */
  fun releaseSurface(eglSurface: EGLSurface) {
    if (eglSurface != EGL14.EGL_NO_SURFACE && eglDisplay != EGL14.EGL_NO_DISPLAY) {
      EGL14.eglDestroySurface(eglDisplay, eglSurface)
      logI(TAG, "EGL surface was destroyed.")
    } else {
      logI(TAG, "EGL surface was already destroyed before.")
    }
  }

  /**
   * Creates an EGL surface associated with a Surface.
   */
  fun createWindowSurface(surface: Surface): EGLSurface {
    try {
      // Create a window surface, and attach it to the Surface we received.
      val surfaceAttribs = intArrayOf(EGL14.EGL_NONE)
      // may throw java.lang.IllegalArgumentException even when surface is valid
      val eglSurface = EGL14.eglCreateWindowSurface(
        /* dpy = */ eglDisplay,
        /* config = */ eglConfig,
        /* win = */ surface,
        /* attrib_list = */ surfaceAttribs,
        /* offset = */ 0,
      )
      val eglWindowCreatedError = checkEglErrorAndNotify("eglCreateWindowSurface")
      if (eglWindowCreatedError != null || eglSurface == null) {
        return eglNoSurface
      }
      return eglSurface
    } catch (e: Exception) {
      logE(TAG, "eglCreateWindowSurface has thrown an exception:\n${e.localizedMessage}")
      return eglNoSurface
    }
  }

  /**
   * Creates an EGL surface associated with an offscreen buffer.
   */
  fun createOffscreenSurface(width: Int, height: Int): EGLSurface {
    val surfaceAttribs =
      intArrayOf(EGL14.EGL_WIDTH, width, EGL14.EGL_HEIGHT, height, EGL14.EGL_NONE)
    val eglSurface = EGL14.eglCreatePbufferSurface(
      /* dpy = */ eglDisplay,
      /* config = */ eglConfig,
      /* attrib_list = */ surfaceAttribs,
      /* offset = */ 0,
    )
    val eglCreatePbufferSurfaceError = checkEglErrorAndNotify("eglCreatePbufferSurface")
    if (eglCreatePbufferSurfaceError != null || eglSurface == null) {
      return eglNoSurface
    }
    return eglSurface
  }

  /**
   * Makes no context current.
   */
  fun makeNothingCurrent(): Boolean {
    if (!EGL14.eglMakeCurrent(
        /* dpy = */ eglDisplay,
        /* draw = */ EGL14.EGL_NO_SURFACE,
        /* read = */ EGL14.EGL_NO_SURFACE,
        /* ctx = */ EGL14.EGL_NO_CONTEXT
      )
    ) {
      checkEglErrorAndNotify("makeNothingCurrent")
      return false
    }
    return true
  }

  /**
   * Makes our EGL context current, using the supplied surface for both "draw" and "read".
   */
  fun makeCurrent(eglSurface: EGLSurface): Boolean {
    // do nothing if current context is applied before
    if (EGL14.eglGetCurrentContext() == eglContext) {
      return true
    }
    if (eglDisplay == EGL14.EGL_NO_DISPLAY) {
      logI(TAG, "NOTE: makeCurrent but eglDisplay is EGL_NO_DISPLAY")
    }
    if (!EGL14.eglMakeCurrent(
        /* dpy = */ eglDisplay,
        /* draw = */ eglSurface,
        /* read = */ eglSurface,
        /* ctx = */ eglContext
      )
    ) {
      checkEglErrorAndNotify("eglMakeCurrent")
      return false
    }
    return true
  }

  /**
   * Calls eglSwapBuffers. Use this to "publish" the current frame.
   */
  fun swapBuffers(eglSurface: EGLSurface): Int {
    val swapStatus = EGL14.eglSwapBuffers(eglDisplay, eglSurface)
    if (!swapStatus) {
      return EGL14.eglGetError()
    }
    return EGL14.EGL_SUCCESS
  }

  @WorkerThread
  internal fun addRendererStateListener(listener: RendererSetupErrorListener) {
    rendererSetupErrorListenerSet.add(listener)
    if (accumulatedRendererErrorList.isNotEmpty()) {
      val immutableCopy = LinkedList(accumulatedRendererErrorList)
      mainHandler.post {
        immutableCopy.forEach { error ->
          listener.onError(error)
        }
      }
    }
  }

  @WorkerThread
  internal fun removeRendererStateListener(listener: RendererSetupErrorListener) {
    rendererSetupErrorListenerSet.remove(listener)
  }

  @WorkerThread
  internal fun clearRendererStateListeners() {
    accumulatedRendererErrorList.clear()
    rendererSetupErrorListenerSet.clear()
  }

  private fun checkEglErrorAndNotify(functionName: String): Int? {
    val eglError = checkEglError(functionName)
    if (eglError != null) {
      val mappedError = if (eglError == EGL14.EGL_BAD_ALLOC)
        RendererError.OUT_OF_MEMORY
      else
        RendererError(eglError)
      logE(TAG, "EGL error ${mappedError.eglErrorCode} occurred for $functionName!")
      notifyListeners(mappedError)
    }
    return eglError
  }

  private fun checkEglError(msg: String): Int? {
    val error = EGL14.eglGetError()
    if (error != EGL14.EGL_SUCCESS) {
      logE(TAG, msg + ": EGL error: 0x${Integer.toHexString(error)}")
      return error
    }
    return null
  }

  private fun notifyListeners(error: RendererError) {
    accumulatedRendererErrorList.add(error)
    if (rendererSetupErrorListenerSet.isNotEmpty()) {
      val immutableCopy = HashSet(rendererSetupErrorListenerSet)
      mainHandler.post {
        immutableCopy.forEach {
          it.onError(error)
        }
      }
    }
  }

  companion object {
    private const val TAG = "Mbgl-EglCore"
    private val attribsEgl3 = intArrayOf(EGL14.EGL_CONTEXT_CLIENT_VERSION, 3, EGL14.EGL_NONE)
    private val attribsEgl2 = intArrayOf(EGL14.EGL_CONTEXT_CLIENT_VERSION, 2, EGL14.EGL_NONE)
  }
}