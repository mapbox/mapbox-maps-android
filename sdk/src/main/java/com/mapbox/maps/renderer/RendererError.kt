package com.mapbox.maps.renderer

import com.mapbox.maps.MapView
import javax.microedition.khronos.egl.EGL10

/**
 * Typed enum wrapping up all the EGL error codes represented as ints.
 */
sealed class RendererError(val eglErrorCode: Int) {
  /**
   * Error code returned when no matching EGL config was found meaning that map rendering is not supported on given device.
   * This could be also checked with static method [MapView.isRenderingSupported] prior to creating the [MapView].
   */
  object NoValidEglConfigFound : RendererError(0)
  /**
   * Represents out of memory caused by [EGL10.EGL_BAD_ALLOC].
   *
   * Could be thrown when creating context, surface or display and system is running low on resources.
   * Best action will be try to release Android resources and re-create the [MapView].
   */
  object OutOfMemory : RendererError(EGL10.EGL_BAD_ALLOC)
  /**
   * Represents all other EGL errors.
   *
   * Most likely this error is unrecoverable meaning that renderer setup did not succeed.
   */
  class EglError(eglErrorCode: Int) : RendererError(eglErrorCode)
}