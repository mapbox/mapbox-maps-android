package com.mapbox.maps.extension.compose.internal

import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.gestures.generated.GesturesSettingsInterface

/**
 * Apply [GesturesSettings] to the [GesturesSettingsInterface] in a single call.
 */
internal fun GesturesSettingsInterface.applySettings(gesturesSettings: GesturesSettings) {
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