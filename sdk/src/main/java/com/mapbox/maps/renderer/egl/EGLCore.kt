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
  private val translucentSurface: Boolean
) {
  private lateinit var egl: EGL10
  private lateinit var eglConfig: EGLConfig
  private var eglDisplay: EGLDisplay = EGL10.EGL_NO_DISPLAY
  private var eglContext: EGLContext = EGL10.EGL_NO_CONTEXT

  var eglStatusSuccess: Boolean = true

  fun prepareEgl() {
    egl = EGLContext.getEGL() as EGL10
    eglDisplay = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY)
    if (eglDisplay == EGL10.EGL_NO_DISPLAY) {
      Logger.e(TAG, "Unable to get EGL14 display")
      eglStatusSuccess = false
      return
    }

    val versions = IntArray(2)
    if (!egl.eglInitialize(eglDisplay, versions)) {
      Logger.e(TAG, "eglInitialize failed")
      eglStatusSuccess = false
      return
    }

    EGLConfigChooser(translucentSurface).chooseConfig(egl, eglDisplay)?.let {
      eglConfig = it
    } ?: run {
      eglStatusSuccess = false
      return
    }

    val context = egl.eglCreateContext(
      eglDisplay,
      eglConfig,
      EGL10.EGL_NO_CONTEXT,
      intArrayOf(EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE)
    )
    checkEglErrorNoException("eglCreateContext")
    eglContext = context
    // Confirm with query.
    val values = IntArray(1)
    egl.eglQueryContext(
      eglDisplay,
      eglContext,
      EGL_CONTEXT_CLIENT_VERSION,
      values
    )
    Logger.d(TAG, "EGLContext created, client version ${values[0]}")
  }

  /**
   * Discards all resources held by this class, notably the EGL context.  This must be
   * called from the thread where the context was created.
   *
   *
   * On completion, no context will be current.
   */
  fun release() {
    if (eglDisplay != EGL10.EGL_NO_DISPLAY) {
      // Android is unusual in that it uses a reference-counted EGLDisplay.  So for
      // every eglInitialize() we need an eglTerminate().
      egl.eglMakeCurrent(
        eglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE,
        EGL10.EGL_NO_CONTEXT
      )
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
      if (egl.eglGetCurrentSurface(EGL10.EGL_DRAW) == eglSurface) {
        egl.eglMakeCurrent(
          eglDisplay,
          EGL10.EGL_NO_SURFACE,
          EGL10.EGL_NO_SURFACE,
          EGL10.EGL_NO_CONTEXT
        )
      }
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
    checkEglErrorNoException("eglCreateWindowSurface")
    if (eglSurface == null) {
      Logger.e(TAG, "surface was null")
      eglStatusSuccess = false
    }
    return eglSurface
  }

  /**
   * Creates an EGL surface associated with an offscreen buffer.
   */
  fun createOffscreenSurface(width: Int, height: Int): EGLSurface? {
    val surfaceAttribs =
      intArrayOf(EGL10.EGL_WIDTH, width, EGL10.EGL_HEIGHT, height, EGL10.EGL_NONE)
    val eglSurface = egl.eglCreatePbufferSurface(
      eglDisplay,
      eglConfig,
      surfaceAttribs
    )
    checkEglErrorNoException("eglCreatePbufferSurface")
    if (eglSurface == null) {
      Logger.e(TAG, "surface was null")
      eglStatusSuccess = false
    }
    return eglSurface
  }

  /**
   * Makes our EGL context current, using the supplied surface for both "draw" and "read".
   */
  fun makeCurrent(eglSurface: EGLSurface): Boolean {
    if (!eglStatusSuccess) {
      return false
    }
    if (eglDisplay == EGL10.EGL_NO_DISPLAY) {
      Logger.d(TAG, "NOTE: makeCurrent w/o display")
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
  private fun checkEglErrorNoException(msg: String) {
    val error = egl.eglGetError()
    if (error != EGL10.EGL_SUCCESS) {
      Logger.e(TAG, msg + ": EGL error: 0x${Integer.toHexString(error)}")
      eglStatusSuccess = false
    }
  }

  companion object {
    private const val TAG = "Mbgl-EglCore"
    private const val EGL_CONTEXT_CLIENT_VERSION = 0x3098
  }
}