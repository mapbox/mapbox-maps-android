package com.mapbox.maps.plugin.animation

import com.mapbox.maps.CameraOptions

internal val CameraOptions.isEmpty: Boolean
  get() {
    if (center != null) return false
    if (padding != null) return false
    if (anchor != null) return false
    if (zoom != null) return false
    if (bearing != null) return false
    if (pitch != null) return false

    return true
  }

internal val CameraOptions.hasChanges: Boolean
  get() = !isEmpty