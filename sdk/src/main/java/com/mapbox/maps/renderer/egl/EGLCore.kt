package com.mapbox.maps.renderer.egl

import android.opengl.*
import android.opengl.EGL14.*
import android.view.Surface
import com.mapbox.maps.logE
import com.mapbox.maps.logI

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
  private val sharedContext: EGLContext = EGL_NO_CONTEXT,
) {
  private lateinit var eglConfig: EGLConfig
  private var eglDisplay: EGLDisplay = EGL_NO_DISPLAY
  internal var eglContext: EGLContext = EGL_NO_CONTEXT

  internal val eglNoSurface: EGLSurface = EGL_NO_SURFACE

  fun prepareEgl(): Boolean {
    eglDisplay = eglGetDisplay(EGL_DEFAULT_DISPLAY)
    if (eglDisplay == EGL_NO_DISPLAY) {
      logE(TAG, "Unable to get EGL14 display")
      return false
    }

    val versions = IntArray(2)
    if (!eglInitialize(eglDisplay, versions, 0, versions, 1)) {
      logE(TAG, "eglInitialize failed")
      return false
    }

    EGLConfigChooser(translucentSurface, antialiasingSampleCount).chooseConfig(eglDisplay)?.let {
      eglConfig = it
    } ?: run {
      return false
    }

    val context = eglCreateContext(
      eglDisplay,
      eglConfig,
      sharedContext,
      intArrayOf(EGL_CONTEXT_CLIENT_VERSION, 2, EGL_NONE),
      0
    )
    val contextCreated = checkEglErrorNoException("eglCreateContext")
    if (!contextCreated) {
      return false
    }
    eglContext = context
    // Confirm with query.
    val values = IntArray(1)
    eglQueryContext(
      eglDisplay,
      eglContext,
      EGL_CONTEXT_CLIENT_VERSION,
      values,
      0
    )
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
    if (eglDisplay != EGL_NO_DISPLAY) {
      // Android is unusual in that it uses a reference-counted EGLDisplay.  So for
      // every eglInitialize() we need an eglTerminate().
      makeNothingCurrent()
      eglDestroyContext(eglDisplay, eglContext)
      eglReleaseThread()
      eglTerminate(eglDisplay)
    }
    eglDisplay = EGL_NO_DISPLAY
    eglContext = EGL_NO_CONTEXT
  }

  /**
   * Destroys the specified surface.  Note the EGLSurface won't actually be destroyed if it's
   * still current in a context.
   */
  fun releaseSurface(eglSurface: EGLSurface) {
    if (eglSurface != EGL_NO_SURFACE && eglDisplay != EGL_NO_DISPLAY) {
      eglDestroySurface(eglDisplay, eglSurface)
    }
  }

  /**
   * Creates an EGL surface associated with a Surface.
   */
  fun createWindowSurface(surface: Surface): EGLSurface {
    // Create a window surface, and attach it to the Surface we received.
    val surfaceAttribs = intArrayOf(EGL_NONE)
    val eglSurface = eglCreateWindowSurface(
      eglDisplay,
      eglConfig,
      surface,
      surfaceAttribs,
      0
    )
    val eglWindowCreated = checkEglErrorNoException("eglCreateWindowSurface")
    if (!eglWindowCreated || eglSurface == null) {
      logE(TAG, "Surface was null")
      return eglNoSurface
    }
    // not connected but was worth trying
//    val success = eglSurfaceAttrib(
//      eglDisplay,
//      eglSurface,
//      EGL_SWAP_BEHAVIOR,
//      EGL_BUFFER_PRESERVED,
//    )
//    if (!success) {
//      logE("KIRYLDD", "Set EGL_BUFFER_PRESERVED swap behaviour failed")
//    }
    return eglSurface
  }

  /**
   * Creates an EGL surface associated with an offscreen buffer.
   */
  fun createOffscreenSurface(width: Int, height: Int): EGLSurface {
    val surfaceAttribs =
      intArrayOf(EGL_WIDTH, width, EGL_HEIGHT, height, EGL_NONE)
    val eglSurface = eglCreatePbufferSurface(
      eglDisplay,
      eglConfig,
      surfaceAttribs,
      0
    )
    checkEglErrorNoException("eglCreatePbufferSurface")
    if (eglSurface == null) {
      logE(TAG, "Offscreen surface was null")
      return eglNoSurface
    }
    return eglSurface
  }

  /**
   * Makes no context current.
   */
  fun makeNothingCurrent(): Boolean {
    if (!eglMakeCurrent(eglDisplay, EGL_NO_SURFACE, EGL_NO_SURFACE, EGL_NO_CONTEXT)) {
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
    if (eglGetCurrentContext() == eglContext) {
      return true
    }
    if (eglDisplay == EGL_NO_DISPLAY) {
      logI(TAG, "NOTE: makeCurrent w/o display")
    }
    if (!eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext)) {
      logE(TAG, "eglMakeCurrent failed")
      return false
    }
    return true
  }

  /**
   * Calls eglSwapBuffers. Use this to "publish" the current frame.
   */
  fun swapBuffers(eglSurface: EGLSurface): Int {
    val swapStatus = eglSwapBuffers(eglDisplay, eglSurface)
    if (!swapStatus) {
      return eglGetError()
    }
    return EGL_SUCCESS
  }

  /**
   * Sends the presentation time stamp to   Time is expressed in nanoseconds.
   */
  fun setPresentationTime(eglSurface: EGLSurface, nsecs: Long) {
    EGLExt.eglPresentationTimeANDROID(eglDisplay, eglSurface, nsecs)
  }

  /**
   * Checks for EGL errors.
   */
  private fun checkEglErrorNoException(msg: String): Boolean {
    val error = eglGetError()
    if (error != EGL_SUCCESS) {
      logE(TAG, msg + ": EGL error: 0x${Integer.toHexString(error)}")
      return false
    }
    return true
  }

  companion object {
    private const val TAG = "Mbgl-EglCore"
    private const val EGL_CONTEXT_CLIENT_VERSION = 0x3098
  }
}