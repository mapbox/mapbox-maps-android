package com.mapbox.maps.plugin.animation

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxExperimental

/**
 * Enum class representing all possible camera animator types.
 */
enum class CameraAnimatorType {
  /**
   * Animator responsible for [CameraOptions.center]
   */
  CENTER,
  /**
   * Animator responsible for [CameraOptions.zoom]
   */
  ZOOM,
  /**
   * Animator responsible for [CameraOptions.bearing]
   */
  BEARING,
  /**
   * Animator responsible for [CameraOptions.pitch]
   */
  PITCH,
  /**
   * Animator responsible for [CameraOptions.anchor]
   */
  ANCHOR,
  /**
   * Animator responsible for [CameraOptions.padding]
   */
  PADDING,
  /**
   * Animator responsible for [CameraOptions.verticalFov]
   */
  @MapboxExperimental
  VERTICAL_FOV
}