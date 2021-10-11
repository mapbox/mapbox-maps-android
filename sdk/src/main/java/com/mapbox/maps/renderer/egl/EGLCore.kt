package com.mapbox.maps.renderer.egl

import android.opengl.EGL14
import android.opengl.EGL14.EGL_CONTEXT_CLIENT_VERSION
import android.opengl.EGL14.EGL_DEFAULT_DISPLAY
import android.opengl.EGL14.EGL_EXTENSIONS
import android.opengl.EGL14.EGL_NONE
import android.opengl.EGL14.EGL_NO_CONTEXT
import android.opengl.EGL14.EGL_NO_DISPLAY
import android.opengl.EGL14.EGL_NO_SURFACE
import android.opengl.EGL14.EGL_SUCCESS
import android.opengl.EGL14.eglChooseConfig
import android.opengl.EGL14.eglCreateContext
import android.opengl.EGL14.eglCreateWindowSurface
import android.opengl.EGL14.eglDestroyContext
import android.opengl.EGL14.eglDestroySurface
import android.opengl.EGL14.eglGetDisplay
import android.opengl.EGL14.eglGetError
import android.opengl.EGL14.eglInitialize
import android.opengl.EGL14.eglMakeCurrent
import android.opengl.EGL14.eglQueryContext
import android.opengl.EGL14.eglQueryString
import android.opengl.EGL14.eglSwapBuffers
import android.opengl.EGL14.eglTerminate
import android.opengl.EGLDisplay
import android.opengl.EGLConfig
import android.opengl.EGLContext
import android.opengl.EGLSurface
import android.opengl.GLUtils
import android.text.TextUtils
import android.view.Surface
import com.mapbox.common.Logger
import kotlin.collections.HashSet

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
  private lateinit var eglConfig: EGLConfig
  private var eglDisplay: EGLDisplay = EGL_NO_DISPLAY
  private var eglContext: EGLContext = EGL_NO_CONTEXT
  private val eglExts: HashSet<String> = hashSetOf()

  var eglStatusSuccess: Boolean = true

  fun prepareEgl() {
    //egl = EGLContext.getEGL() as EGL14
    if (!hasEglDisplay() && !connectDisplay()) {
      Logger.e(TAG, "Can not connect display, abort!")
      return
    }

    val versions = IntArray(2)
    if (!eglInitialize(eglDisplay, versions, 0 , versions, 1)) {
      Logger.e(TAG, "eglInitialize failed: " + GLUtils.getEGLErrorString(eglGetError()))
      eglStatusSuccess = false
      return
    }

     chooseEglConfig(eglDisplay)?.let {
       eglConfig = it
       Logger.e(TAG, "eglConfig not initialized!")
    }
    /*EGLConfigChooser(translucentSurface).chooseConfig(egl, eglDisplay)?.let {
      eglConfig = it
    }*/ ?: run {
      eglStatusSuccess = false
      return
    }

    val context = eglCreateContext(
      eglDisplay,
      eglConfig,
      EGL_NO_CONTEXT,
      intArrayOf(EGL_CONTEXT_CLIENT_VERSION, OPENGLES_VERSION, EGL_NONE),
      0
    )
    checkEglErrorNoException("eglCreateContext")
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
    if (eglDisplay != EGL_NO_DISPLAY) {
      // Android is unusual in that it uses a reference-counted EGLDisplay.  So for
      // every eglInitialize() we need an eglTerminate().
      makeNothingCurrent()
      eglDestroyContext(eglDisplay, eglContext)
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
    var attrs: IntArray? = null
    val wcgCapability = getWcgCapability()
    if (checkExtensionCapability(KHR_GL_COLOR_SPACE) && wcgCapability > 0) {
      attrs = intArrayOf(
        EGL_GL_COLORSPACE_KHR,
        wcgCapability,
        EGL_NONE
      )
    }

    val eglSurface = eglCreateWindowSurface(
      eglDisplay,
      eglConfig,
      surface,
      attrs,
      0
    )
    checkEglErrorNoException("eglCreateWindowSurface")
    if (eglSurface == null) {
      Logger.e(TAG, "surface was null")
      eglStatusSuccess = false
    }
    return eglSurface
  }

  /**
   * Makes no context current.
   */
  fun makeNothingCurrent() {
    if (!eglMakeCurrent(eglDisplay, EGL_NO_SURFACE, EGL_NO_SURFACE, EGL_NO_CONTEXT)) {
      checkEglErrorNoException("eglMakeNothingCurrent")
    }
  }

  /**
   * Makes our EGL context current, using the supplied surface for both "draw" and "read".
   */
  fun makeCurrent(eglSurface: EGLSurface): Boolean {
    if (!eglStatusSuccess) {
      return false
    }
    if (eglDisplay == EGL14.EGL_NO_DISPLAY) {
      Logger.d(TAG, "NOTE: makeCurrent w/o display")
    }
    if (!eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext)) {
      Logger.e(TAG, "eglMakeCurrent failed")
      return false
    }
    return true
  }

  /**
   * Calls eglSwapBuffers.  Use this to "publish" the current frame.
   */
  fun swapBuffers(eglSurface: EGLSurface): Int {
    val swapStatus = eglSwapBuffers(eglDisplay, eglSurface)
    if (!swapStatus) {
      return eglGetError()
    }
    return EGL_SUCCESS
  }

  /**
   * Checks for EGL errors.
   */
  private fun checkEglErrorNoException(msg: String) {
    val error = eglGetError()
    if (error != EGL_SUCCESS) {
      Logger.e(TAG, msg + ": EGL error: 0x${Integer.toHexString(error)}")
      eglStatusSuccess = false
    }
  }

  private fun connectDisplay(): Boolean {
    eglExts.clear()
    eglDisplay = eglGetDisplay(EGL_DEFAULT_DISPLAY)
    if (!hasEglDisplay()) {
      Logger.e(TAG, "Unable to get EGL14 display: " + GLUtils.getEGLErrorString(eglGetError()))
      eglStatusSuccess = false
      return false
    }

    val queryString = eglQueryString(eglDisplay, EGL_EXTENSIONS)
    if (!TextUtils.isEmpty(queryString)) {
      eglExts.addAll(queryString.split(" ").toTypedArray())
    }
    return true
  }

  private fun hasEglDisplay(): Boolean {
    return eglDisplay != EGL_NO_DISPLAY
  }

  private fun chooseEglConfig(display: EGLDisplay): EGLConfig? {
    val configsCount = IntArray(1)
    val configs = arrayOfNulls<EGLConfig>(1)
    val configSpec: IntArray = getConfig()
    return if (!eglChooseConfig(display, configSpec, 0, configs, 0, 1, configsCount, 0)) {
      Logger.e(TAG, "eglChooseConfig failed: " + GLUtils.getEGLErrorString(eglGetError()))
      null
    } else {
      if (configsCount[0] <= 0) {
        Logger.w(TAG, "eglChooseConfig failed, invalid configs count: " + configsCount[0])
        null
      } else {
        configs[0]
      }
    }
  }

  private fun getConfig(): IntArray {
    return intArrayOf(
      EGL14.EGL_RED_SIZE, 8,
      EGL14.EGL_GREEN_SIZE, 8,
      EGL14.EGL_BLUE_SIZE, 8,
      EGL14.EGL_ALPHA_SIZE, 0,
      EGL14.EGL_DEPTH_SIZE, 0,
      EGL14.EGL_STENCIL_SIZE, 0,
      EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
      EGL14.EGL_CONFIG_CAVEAT, EGL_NONE,
      EGL_NONE
    )
  }

  private fun checkExtensionCapability(extName: String): Boolean = eglExts.contains(extName)

  private fun getWcgCapability(): Int {
    return if (checkExtensionCapability(EXT_GL_COLORSPACE_DISPLAY_P3_PASSTHROUGH)) {
      EGL_GL_COLORSPACE_DISPLAY_P3_PASSTHROUGH_EXT
    } else 0
  }

  companion object {
    private const val TAG = "Mbgl-EglCore"
    //private const val EGL_CONTEXT_CLIENT_VERSION = 0x3098
    private const val OPENGLES_VERSION = 2
    private const val EGL_GL_COLORSPACE_KHR = 0x309D
    private const val EGL_GL_COLORSPACE_DISPLAY_P3_PASSTHROUGH_EXT = 0x3490

    /**
     * https://www.khronos.org/registry/EGL/extensions/EXT/EGL_EXT_gl_colorspace_display_p3_passthrough.txt
     */
    private const val EXT_GL_COLORSPACE_DISPLAY_P3_PASSTHROUGH =
      "EGL_EXT_gl_colorspace_display_p3_passthrough"

    /**
     * https://www.khronos.org/registry/EGL/extensions/KHR/EGL_KHR_gl_colorspace.txt
     */
    private const val KHR_GL_COLOR_SPACE = "EGL_KHR_gl_colorspace"
  }
}