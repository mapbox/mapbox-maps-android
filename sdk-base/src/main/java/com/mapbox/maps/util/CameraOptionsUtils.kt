@file:JvmName("CameraOptionsUtils")

package com.mapbox.maps.util

import com.mapbox.maps.CameraOptions

/**
 * Check whether given [CameraOptions] object is empty.
 */
val CameraOptions.isEmpty: Boolean
  get() {
    if (center != null) return false
    if (padding != null) return false
    if (anchor != null) return false
    if (zoom != null) return false
    if (bearing != null) return false
    if (pitch != null) return false

    return true
  }