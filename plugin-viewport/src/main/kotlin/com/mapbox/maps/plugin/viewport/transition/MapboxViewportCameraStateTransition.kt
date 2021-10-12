package com.mapbox.maps.plugin.viewport.transition

import android.animation.AnimatorSet
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate

/**
 * Helper class that provides default implementation of [ViewportCameraStateTransition]
 * generators.
 */
class MapboxViewportCameraStateTransition(
  private val cameraManager: MapCameraManagerDelegate,
  private val cameraPlugin: CameraAnimationsPlugin,
  private val viewportCameraTransition: ViewportCameraTransition =
    MapboxViewportCameraTransition(cameraManager, cameraPlugin)
) : ViewportCameraStateTransition {
  override fun transitionToFollowing(
    cameraOptions: CameraOptions,
    transitionOptions: ViewportCameraTransitionOptions
  ): AnimatorSet = with(viewportCameraTransition) {
    transitionFromLowZoomToHighZoom(cameraOptions, transitionOptions)
  }

  override fun transitionToOverview(
    cameraOptions: CameraOptions,
    transitionOptions: ViewportCameraTransitionOptions
  ): AnimatorSet = with(viewportCameraTransition) {
    val currentZoom = cameraManager.cameraState.zoom
    if (currentZoom < cameraOptions.zoom ?: currentZoom) {
      transitionFromLowZoomToHighZoom(cameraOptions, transitionOptions)
    } else {
      transitionFromHighZoomToLowZoom(cameraOptions, transitionOptions)
    }
  }

  override fun updateFrameForFollowing(
    cameraOptions: CameraOptions,
    transitionOptions: ViewportCameraTransitionOptions
  ): AnimatorSet = with(viewportCameraTransition) {
    transitionLinear(cameraOptions, transitionOptions)
  }

  override fun updateFrameForOverview(
    cameraOptions: CameraOptions,
    transitionOptions: ViewportCameraTransitionOptions
  ): AnimatorSet = with(viewportCameraTransition) {
    transitionLinear(cameraOptions, transitionOptions)
  }
}