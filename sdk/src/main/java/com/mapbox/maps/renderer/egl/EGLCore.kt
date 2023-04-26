package com.mapbox.maps.renderer.egl

import android.os.Handler
import android.os.Looper
import android.view.Surface
import androidx.annotation.WorkerThread
import com.mapbox.maps.MapView
import com.mapbox.maps.logE
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import com.mapbox.maps.renderer.RendererError
import com.mapbox.maps.renderer.RendererSetupErrorListener
import java.util.*
import javax.microedition.khronos.egl.*
import kotlin.collections.HashSet

/**
 * Core EGL state (display, context, config).
 *
 * The EGLContext must only be attached to one thread at a time. This class is not thread-safe.
 *
 * Inspired by [Grafika](https://github.com/google/grafika/blob/master/app/src/main/java/com/android/grafika/gles/EglCore.java)
 */
internal class EGLCore(
  private val translucentSurface: Boolean,
  private val antialiasingSampleCount: Int,
  private val sharedContext: EGLContext = EGL10.EGL_NO_CONTEXT,
) {
  private lateinit var egl: EGL10
  private lateinit var eglConfig: EGLConfig
  private var eglDisplay: EGLDisplay = EGL10.EGL_NO_DISPLAY
  internal var eglContext: EGLContext = EGL10.EGL_NO_CONTEXT

  internal val eglNoSurface: EGLSurface = EGL10.EGL_NO_SURFACE

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
    egl = EGLContext.getEGL() as EGL10
    eglDisplay = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY)
    if (eglDisplay == EGL10.EGL_NO_DISPLAY) {
      // no need to report error upstream here due to https://www.khronos.org/registry/EGL/sdk/docs/man/html/eglGetDisplay.xhtml
      logW(TAG, "Unable to get default display, eglInitialize will most likely fail shortly.")
    }
    val versions = IntArray(2)
    if (!egl.eglInitialize(eglDisplay, versions)) {
      checkEglErrorAndNotify("eglInitialize")
      return false
    }
    EGLConfigChooser(translucentSurface, antialiasingSampleCount).chooseConfig(egl, eglDisplay)?.let {
      eglConfig = it
    } ?: run {
      notifyListeners(RendererError.NO_VALID_EGL_CONFIG_FOUND)
      return false
    }
    val context = egl.eglCreateContext(
      eglDisplay,
      eglConfig,
      sharedContext,
      intArrayOf(EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE)
    )
    checkEglErrorAndNotify("eglCreateContext")
    eglContext = context
    // Confirm with query.
    val values = IntArray(1)
    val eglQueryContextSuccess = egl.eglQueryContext(
      eglDisplay,
      eglContext,
      EGL_CONTEXT_CLIENT_VERSION,
      values
    )
    if (!eglQueryContextSuccess) {
      checkEglErrorAndNotify("eglQueryContext")
    }
    logI(TAG, "EGLContext created, client version ${values[0]}")
    return true
  }

  /**
   * Discards all resources held by this class, notably the EGL context.  This must be
   * called from the thread where the context was created.
   *
   * On completion, no context will be current.
   */
  fun release() {
    if (eglDisplay != EGL10.EGL_NO_DISPLAY) {
      // Android is unusual in that it uses a reference-counted EGLDisplay.  So for
      // every eglInitialize() we need an eglTerminate().
      makeNothingCurrent()
      egl.eglDestroyContext(eglDisplay, eglContext)
      egl.eglTerminate(eglDisplay)
    }
    eglDisplay = EGL10.EGL_NO_DISPLAY
    eglContext = EGL10.EGL_NO_CONTEXT
  }

  /**
   * Destroys the specified surface. Note the EGLSurface won't actually be destroyed if it's
   * still current in a context.
   */
  fun releaseSurface(eglSurface: EGLSurface) {
    if (eglSurface != EGL10.EGL_NO_SURFACE && eglDisplay != EGL10.EGL_NO_DISPLAY) {
      egl.eglDestroySurface(eglDisplay, eglSurface)
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
      val surfaceAttribs = intArrayOf(EGL10.EGL_NONE)
      // may throw java.lang.IllegalArgumentException even when surface is valid
      val eglSurface = egl.eglCreateWindowSurface(
        eglDisplay,
        eglConfig,
        surface,
        surfaceAttribs
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
      intArrayOf(EGL10.EGL_WIDTH, width, EGL10.EGL_HEIGHT, height, EGL10.EGL_NONE)
    val eglSurface = egl.eglCreatePbufferSurface(
      eglDisplay,
      eglConfig,
      surfaceAttribs
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
    if (!egl.eglMakeCurrent(eglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT)) {
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
    if (egl.eglGetCurrentContext() == eglContext) {
      return true
    }
    if (eglDisplay == EGL10.EGL_NO_DISPLAY) {
      logI(TAG, "NOTE: makeCurrent w/o display")
    }
    if (!egl.eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext)) {
      checkEglErrorAndNotify("eglMakeCurrent")
      return false
    }
    return true
  }

  /**
   * Calls eglSwapBuffers. Use this to "publish" the current frame.
   */
  fun swapBuffers(eglSurface: EGLSurface): Int {
    val swapStatus = egl.eglSwapBuffers(eglDisplay, eglSurface)
    if (!swapStatus) {
      return egl.eglGetError()
    }
    return EGL10.EGL_SUCCESS
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
      val mappedError = if (eglError == EGL10.EGL_BAD_ALLOC)
        RendererError.OUT_OF_MEMORY
      else
        RendererError(eglError)
      logE(TAG, "EGL error ${mappedError.eglErrorCode} occurred for $functionName!")
      notifyListeners(mappedError)
    }
    return eglError
  }

  private fun checkEglError(msg: String): Int? {
    val error = egl.eglGetError()
    if (error != EGL10.EGL_SUCCESS) {
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
    private const val EGL_CONTEXT_CLIENT_VERSION = 0x3098
  }
}