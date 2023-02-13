package com.mapbox.maps.extension.compose.internal

import com.mapbox.maps.plugin.attribution.generated.AttributionSettings
import com.mapbox.maps.plugin.attribution.generated.AttributionSettingsInterface
import com.mapbox.maps.plugin.compass.generated.CompassSettings
import com.mapbox.maps.plugin.compass.generated.CompassSettingsInterface
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.gestures.generated.GesturesSettingsInterface
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings2
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettingsInterface
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettingsInterface2
import com.mapbox.maps.plugin.logo.generated.LogoSettings
import com.mapbox.maps.plugin.logo.generated.LogoSettingsInterface
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettings
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettingsInterface

/**
 * Apply [AttributionSettings] to the [AttributionSettingsInterface] in a single call.
 */
internal fun AttributionSettingsInterface.applySettings(attributionSettings: AttributionSettings) {
  this.updateSettings {
    enabled = attributionSettings.enabled
    iconColor = attributionSettings.iconColor
    position = attributionSettings.position
    marginLeft = attributionSettings.marginLeft
    marginTop = attributionSettings.marginTop
    marginRight = attributionSettings.marginRight
    marginBottom = attributionSettings.marginBottom
    clickable = attributionSettings.clickable
  }
}

/**
 * Apply [CompassSettings] to the [CompassSettingsInterface] in a single call.
 */
internal fun CompassSettingsInterface.applySettings(compassSettings: CompassSettings) {
  this.updateSettings {
    enabled = compassSettings.enabled
    position = compassSettings.position
    marginLeft = compassSettings.marginLeft
    marginTop = compassSettings.marginTop
    marginRight = compassSettings.marginRight
    marginBottom = compassSettings.marginBottom
    opacity = compassSettings.opacity
    rotation = compassSettings.rotation
    visibility = compassSettings.visibility
    fadeWhenFacingNorth = compassSettings.fadeWhenFacingNorth
    clickable = compassSettings.clickable
    image = compassSettings.image
  }
}

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

/**
 * Apply [LocationComponentSettings] to the [LocationComponentSettingsInterface] in a single call.
 */
internal fun LocationComponentSettingsInterface.applySettings(locationComponentSettings: LocationComponentSettings) {
  this.updateSettings {
    enabled = locationComponentSettings.enabled
    pulsingEnabled = locationComponentSettings.pulsingEnabled
    pulsingColor = locationComponentSettings.pulsingColor
    pulsingMaxRadius = locationComponentSettings.pulsingMaxRadius
    layerAbove = locationComponentSettings.layerAbove
    layerBelow = locationComponentSettings.layerBelow
    locationPuck = locationComponentSettings.locationPuck
  }
}

/**
 * Apply [LocationComponentSettings2] to the [LocationComponentSettingsInterface2] in a single call.
 */
internal fun LocationComponentSettingsInterface2.applySettings(locationComponentSettings2: LocationComponentSettings2) {
  this.updateSettings {
    showAccuracyRing = locationComponentSettings2.showAccuracyRing
    accuracyRingColor = locationComponentSettings2.accuracyRingColor
    accuracyRingBorderColor = locationComponentSettings2.accuracyRingBorderColor
    puckBearingEnabled = locationComponentSettings2.puckBearingEnabled
    puckBearingSource = locationComponentSettings2.puckBearingSource
  }
}

/**
 * Apply [LocationComponentSettings2] to the [LocationComponentSettingsInterface2] in a single call.
 */
internal fun LogoSettingsInterface.applySettings(logoSettings: LogoSettings) {
  this.updateSettings {
    enabled = logoSettings.enabled
    position = logoSettings.position
    marginLeft = logoSettings.marginLeft
    marginTop = logoSettings.marginTop
    marginRight = logoSettings.marginRight
    marginBottom = logoSettings.marginBottom
  }
}

/**
 * Apply [ScaleBarSettings] to the [ScaleBarSettingsInterface] in a single call.
 */
internal fun ScaleBarSettingsInterface.applySettings(scaleBarSettings: ScaleBarSettings) {
  this.updateSettings {
    enabled = scaleBarSettings.enabled
    position = scaleBarSettings.position
    marginLeft = scaleBarSettings.marginLeft
    marginTop = scaleBarSettings.marginTop
    marginRight = scaleBarSettings.marginRight
    marginBottom = scaleBarSettings.marginBottom
    textColor = scaleBarSettings.textColor
    primaryColor = scaleBarSettings.primaryColor
    secondaryColor = scaleBarSettings.secondaryColor
    borderWidth = scaleBarSettings.borderWidth
    height = scaleBarSettings.height
    textBarMargin = scaleBarSettings.textBarMargin
    textBorderWidth = scaleBarSettings.textBorderWidth
    textSize = scaleBarSettings.textSize
    isMetricUnits = scaleBarSettings.isMetricUnits
    refreshInterval = scaleBarSettings.refreshInterval
    showTextBorder = scaleBarSettings.showTextBorder
    ratio = scaleBarSettings.ratio
    useContinuousRendering = scaleBarSettings.useContinuousRendering
  }
}