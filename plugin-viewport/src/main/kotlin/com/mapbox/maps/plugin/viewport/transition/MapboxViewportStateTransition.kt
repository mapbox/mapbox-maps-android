package com.mapbox.maps.plugin.viewport.transition

import android.animation.AnimatorSet
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate

/**
 * Helper class that provides default implementation of [ViewportStateTransition]
 * generators.
 */
internal class MapboxViewportStateTransition(
  private val cameraManager: MapCameraManagerDelegate,
  private val cameraPlugin: CameraAnimationsPlugin,
  private val viewportTransition: ViewportTransition =
    MapboxViewportTransition(cameraManager, cameraPlugin)
) : ViewportStateTransition {
  override fun transitionToFollowing(
    cameraOptions: CameraOptions,
    transitionOptions: ViewportTransitionOptions
  ): AnimatorSet = with(viewportTransition) {
    transitionFromLowZoomToHighZoom(cameraOptions, transitionOptions)
  }

  override fun transitionToOverview(
    cameraOptions: CameraOptions,
    transitionOptions: ViewportTransitionOptions
  ): AnimatorSet = with(viewportTransition) {
    val currentZoom = cameraManager.cameraState.zoom
    if (currentZoom < cameraOptions.zoom ?: currentZoom) {
      transitionFromLowZoomToHighZoom(cameraOptions, transitionOptions)
    } else {
      transitionFromHighZoomToLowZoom(cameraOptions, transitionOptions)
    }
  }

  override fun updateFrameForFollowing(
    cameraOptions: CameraOptions,
    transitionOptions: ViewportTransitionOptions
  ): AnimatorSet = with(viewportTransition) {
    transitionLinear(cameraOptions, transitionOptions)
  }

  override fun updateFrameForOverview(
    cameraOptions: CameraOptions,
    transitionOptions: ViewportTransitionOptions
  ): AnimatorSet = with(viewportTransition) {
    transitionLinear(cameraOptions, transitionOptions)
  }
}