package com.mapbox.maps.renderer

import com.mapbox.maps.MapView
import javax.microedition.khronos.egl.EGL10

/**
 * Typed enum wrapping up all the EGL error codes represented as ints.
 * We do have SDK specific error types: [NO_VALID_EGL_CONFIG_FOUND] and [OUT_OF_MEMORY].
 */
class RendererError(val eglErrorCode: Int) {
  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?): Boolean = other is RendererError &&
    eglErrorCode == other.eglErrorCode

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode(): Int = eglErrorCode.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString(): String = if (eglErrorCode == 0)
    "RendererError(NO_VALID_EGL_CONFIG_FOUND)"
  else
    "RendererError(EGL_ERROR_CODE=$eglErrorCode)"

  /**
   * Companion object.
   */
  companion object {
    /**
     * Error code returned when no matching EGL config was found meaning that map rendering is not supported on given device.
     * This could be also checked with static method [MapView.isRenderingSupported] prior to creating the [MapView].
     */
    @JvmField
    val NO_VALID_EGL_CONFIG_FOUND = RendererError(0)
    /**
     * Represents out of memory caused by [EGL10.EGL_BAD_ALLOC].
     *
     * Could be thrown when creating context, surface or display and system is running low on resources.
     * Best action will be try to release Android resources and re-create the [MapView].
     */
    @JvmField
    val OUT_OF_MEMORY = RendererError(EGL10.EGL_BAD_ALLOC)
  }
}