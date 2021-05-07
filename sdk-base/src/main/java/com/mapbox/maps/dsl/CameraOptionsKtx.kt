package com.mapbox.maps.dsl

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraState

/**
 * DSL builder function to create [CameraOptions] object.
 */
inline fun cameraOptions(block: CameraOptions.Builder.() -> Unit): CameraOptions =
  CameraOptions.Builder().apply(block).build()

/**
 * DSL builder function to create [CameraOptions] object.
 *
 * @param cameraState existing camera state that is used for a mutation base
 */
inline fun cameraOptions(
  cameraState: CameraState,
  block: CameraOptions.Builder.() -> Unit
): CameraOptions = CameraOptions.Builder()
  .padding(cameraState.padding)
  .center(cameraState.center)
  .bearing(cameraState.bearing)
  .zoom(cameraState.zoom)
  .pitch(cameraState.pitch)
  .apply(block)
  .build()