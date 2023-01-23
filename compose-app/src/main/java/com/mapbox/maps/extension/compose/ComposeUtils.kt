package com.mapbox.maps.extension.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionContext
import com.mapbox.maps.MapSurface
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.gestures.generated.GesturesSettingsInterface
import kotlinx.coroutines.awaitCancellation


internal suspend inline fun disposingComposition(factory: () -> Composition) {
  val composition = factory()
  try {
    awaitCancellation()
  } finally {
    composition.dispose()
  }
}

internal fun MapView.newComposition(
  parent: CompositionContext,
  content: @Composable () -> Unit
): Composition {
  return Composition(
    MapApplier(this, this), parent
  ).apply {
    setContent(content)
  }
}

internal fun MapSurface.newComposition(
  parent: CompositionContext,
  content: @Composable () -> Unit
): Composition {
  return Composition(
    MapApplier(this, this), parent
  ).apply {
    setContent(content)
  }
}


public fun GesturesSettingsInterface.applySettings(gesturesSettings: GesturesSettings) {
  this.updateSettings {
    rotateEnabled = gesturesSettings.rotateEnabled
    pinchToZoomEnabled = gesturesSettings.pinchToZoomEnabled
    scrollEnabled = gesturesSettings.scrollEnabled
    simultaneousRotateAndPinchToZoomEnabled =
      gesturesSettings.simultaneousRotateAndPinchToZoomEnabled
    pitchEnabled = gesturesSettings.pitchEnabled
    scrollMode = gesturesSettings.scrollMode
    doubleTapToZoomInEnabled = gesturesSettings.doubleTapToZoomInEnabled
    doubleTouchToZoomOutEnabled = gesturesSettings.doubleTouchToZoomOutEnabled
    quickZoomEnabled = gesturesSettings.quickZoomEnabled
    focalPoint = gesturesSettings.focalPoint
    pinchToZoomDecelerationEnabled = gesturesSettings.pinchToZoomDecelerationEnabled
    rotateDecelerationEnabled = gesturesSettings.rotateDecelerationEnabled
    scrollDecelerationEnabled = gesturesSettings.scrollDecelerationEnabled
    increaseRotateThresholdWhenPinchingToZoom =
      gesturesSettings.increaseRotateThresholdWhenPinchingToZoom
    increasePinchToZoomThresholdWhenRotating =
      gesturesSettings.increasePinchToZoomThresholdWhenRotating
    zoomAnimationAmount = gesturesSettings.zoomAnimationAmount
    pinchScrollEnabled = gesturesSettings.pinchScrollEnabled
  }
}