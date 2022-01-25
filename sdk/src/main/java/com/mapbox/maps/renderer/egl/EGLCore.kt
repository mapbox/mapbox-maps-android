package com.mapbox.maps.renderer.egl

import android.view.Surface
import com.mapbox.common.Logger
import javax.microedition.khronos.egl.*

/**
 * Core EGL state (display, context, config).
 *
 * The EGLContext must only be attached to one thread at a time.  This class is not thread-safe.
 *
 * Inspired by [Grafika](https://github.com/google/grafika/blob/master/app/src/main/java/com/android/grafika/gles/EglCore.java)
 */
internal class EGLCore(
  private val translucentSurface: Boolean,
  private val antialiasingSampleCount: Int,
) {
  private lateinit var egl: EGL10
  private lateinit var eglConfig: EGLConfig
  private var eglDisplay: EGLDisplay = EGL10.EGL_NO_DISPLAY
  private var eglContext: EGLContext = EGL10.EGL_NO_CONTEXT

  internal val eglNoSurface: EGLSurface = EGL10.EGL_NO_SURFACE

  fun prepareEgl(): Boolean {
    egl = EGLContext.getEGL() as EGL10
    eglDisplay = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY)
    if (eglDisplay == EGL10.EGL_NO_DISPLAY) {
      Logger.e(TAG, "Unable to get EGL14 display")
      return false
    }

    val versions = IntArray(2)
    if (!egl.eglInitialize(eglDisplay, versions)) {
      Logger.e(TAG, "eglInitialize failed")
      return false
    }

    EGLConfigChooser(translucentSurface, antialiasingSampleCount).chooseConfig(egl, eglDisplay)?.let {
      eglConfig = it
    } ?: run {
      return false
    }

    val context = egl.eglCreateContext(
      eglDisplay,
      eglConfig,
      EGL10.EGL_NO_CONTEXT,
      intArrayOf(EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE)
    )
    val contextCreated = checkEglErrorNoException("eglCreateContext")
    if (!contextCreated) {
      return false
    }
    eglContext = context
    // Confirm with query.
    val values = IntArray(1)
    egl.eglQueryContext(
      eglDisplay,
      eglContext,
      EGL_CONTEXT_CLIENT_VERSION,
      values
    )
    Logger.i(TAG, "EGLContext created, client version ${values[0]}")
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
   * Destroys the specified surface.  Note the EGLSurface won't actually be destroyed if it's
   * still current in a context.
   */
  fun releaseSurface(eglSurface: EGLSurface) {
    if (eglSurface != EGL10.EGL_NO_SURFACE && eglDisplay != EGL10.EGL_NO_DISPLAY) {
      egl.eglDestroySurface(eglDisplay, eglSurface)
    }
  }

  /**
   * Creates an EGL surface associated with a Surface.
   */
  fun createWindowSurface(surface: Surface): EGLSurface {
    // Create a window surface, and attach it to the Surface we received.
    val surfaceAttribs = intArrayOf(EGL10.EGL_NONE)
    val eglSurface = egl.eglCreateWindowSurface(
      eglDisplay,
      eglConfig,
      surface,
      surfaceAttribs
    )
    val eglWindowCreated = checkEglErrorNoException("eglCreateWindowSurface")
    if (!eglWindowCreated || eglSurface == null) {
      Logger.e(TAG, "Surface was null")
      return eglNoSurface
    }
    return eglSurface
  }

  /**
   * Makes no context current.
   */
  fun makeNothingCurrent(): Boolean {
    if (!egl.eglMakeCurrent(eglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT)) {
      val eglMakeNothingCurrentSuccess = checkEglErrorNoException("eglMakeNothingCurrent")
      if (!eglMakeNothingCurrentSuccess) {
        return false
      }
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
      Logger.i(TAG, "NOTE: makeCurrent w/o display")
    }
    if (!egl.eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext)) {
      Logger.e(TAG, "eglMakeCurrent failed")
      return false
    }
    return true
  }

  /**
   * Calls eglSwapBuffers.  Use this to "publish" the current frame.
   */
  fun swapBuffers(eglSurface: EGLSurface): Int {
    val swapStatus = egl.eglSwapBuffers(eglDisplay, eglSurface)
    if (!swapStatus) {
      return egl.eglGetError()
    }
    return EGL10.EGL_SUCCESS
  }

  /**
   * Checks for EGL errors.
   */
  private fun checkEglErrorNoException(msg: String): Boolean {
    val error = egl.eglGetError()
    if (error != EGL10.EGL_SUCCESS) {
      Logger.e(TAG, msg + ": EGL error: 0x${Integer.toHexString(error)}")
      return false
    }
    return true
  }

  companion object {
    private const val TAG = "Mbgl-EglCore"
    private const val EGL_CONTEXT_CLIENT_VERSION = 0x3098
  }
}