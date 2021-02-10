package com.mapbox.maps.dsl

import com.mapbox.maps.CameraOptions

/**
 * DSL builder function to create [CameraOptions] object.
 */
inline fun cameraOptions(block: CameraOptions.Builder.() -> Unit) =
  CameraOptions.Builder().apply(block).build()