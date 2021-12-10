package com.mapbox.maps.plugin.viewport.data

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.viewport.ViewportPlugin
import com.mapbox.maps.plugin.viewport.state.ViewportState.Following
import com.mapbox.maps.plugin.viewport.state.ViewportState.Overview

/**
 * Data object that carries the camera frames that [ViewportPlugin] uses for transitions
 * and continuous updates.
 */
data class ViewportData(
  /**
   * Target camera frame to use when transitioning to [Following] or for continuous updates when
   * already in [Following] state.
   */
  val cameraForFollowing: CameraOptions,

  /**
   * Target camera frame to use when transitioning to [Overview] or for continuous updates when
   * already in [Overview] state.
   */
  val cameraForOverview: CameraOptions
)