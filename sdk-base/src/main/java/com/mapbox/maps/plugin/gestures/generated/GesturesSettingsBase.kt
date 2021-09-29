// This file is generated.

package com.mapbox.maps.plugin.gestures.generated

import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.ScrollMode

/**
 * Abstract settings class for GesturesPlugin.
 *
 * This abstract class exposes all the required public APIs to configure the GesturesPlugin.
 */
abstract class GesturesSettingsBase : GesturesSettingsInterface {
  /**
   * Gesture configuration allows to control the user touch interaction.
   */
  protected abstract var internalSettings: GesturesSettings

  /**
   * Apply the changes to the GesturesSettings to the GesturesPlugin.
   */
  protected abstract fun applySettings()

  /**
   * Get current gestures configuration.
   *
   * @return gestures settings
   */
  override fun getSettings(): GesturesSettings {
    return internalSettings.copy()
  }

  /**
   * Update gestures configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of GesturesSettings
   */
  override fun updateSettings(block: GesturesSettings.() -> Unit) {
    this.internalSettings.apply(block)
    applySettings()
  }

  /**
   * Whether the rotate gesture is enabled.
   */
  override var rotateEnabled: Boolean
    get() {
      return this.internalSettings.rotateEnabled
    }
    set(value) {
      this.internalSettings.rotateEnabled = value
      applySettings()
    }

  /**
   * Whether the pinch to zoom gesture is enabled.
   */
  override var pinchToZoomEnabled: Boolean
    get() {
      return this.internalSettings.pinchToZoomEnabled
    }
    set(value) {
      this.internalSettings.pinchToZoomEnabled = value
      applySettings()
    }

  /**
   * Whether the single-touch scroll gesture is enabled.
   */
  override var scrollEnabled: Boolean
    get() {
      return this.internalSettings.scrollEnabled
    }
    set(value) {
      this.internalSettings.scrollEnabled = value
      applySettings()
    }

  /**
   * Whether the pitch gesture is enabled.
   */
  override var pitchEnabled: Boolean
    get() {
      return this.internalSettings.pitchEnabled
    }
    set(value) {
      this.internalSettings.pitchEnabled = value
      applySettings()
    }

  /**
   * Configures the directions in which the map is allowed to move during a scroll gesture.
   */
  override var scrollMode: ScrollMode
    get() {
      return this.internalSettings.scrollMode
    }
    set(value) {
      this.internalSettings.scrollMode = value
      applySettings()
    }

  /**
   * Whether double tapping the map with one touch results in a zoom-in animation.
   */
  override var doubleTapToZoomInEnabled: Boolean
    get() {
      return this.internalSettings.doubleTapToZoomInEnabled
    }
    set(value) {
      this.internalSettings.doubleTapToZoomInEnabled = value
      applySettings()
    }

  /**
   * Whether single tapping the map with two touches results in a zoom-out animation.
   */
  override var doubleTouchToZoomOutEnabled: Boolean
    get() {
      return this.internalSettings.doubleTouchToZoomOutEnabled
    }
    set(value) {
      this.internalSettings.doubleTouchToZoomOutEnabled = value
      applySettings()
    }

  /**
   * Whether the quick zoom gesture is enabled.
   */
  override var quickZoomEnabled: Boolean
    get() {
      return this.internalSettings.quickZoomEnabled
    }
    set(value) {
      this.internalSettings.quickZoomEnabled = value
      applySettings()
    }

  /**
   * By default, gestures rotate and zoom around the center of the gesture. Set this property to rotate and zoom around a fixed point instead.
   */
  override var focalPoint: ScreenCoordinate?
    get() {
      return this.internalSettings.focalPoint
    }
    set(value) {
      this.internalSettings.focalPoint = value
      applySettings()
    }

  /**
   * Whether a deceleration animation following a pinch-to-zoom gesture is enabled. True by default.
   */
  override var pinchToZoomDecelerationEnabled: Boolean
    get() {
      return this.internalSettings.pinchToZoomDecelerationEnabled
    }
    set(value) {
      this.internalSettings.pinchToZoomDecelerationEnabled = value
      applySettings()
    }

  /**
   * Whether a deceleration animation following a rotate gesture is enabled. True by default.
   */
  override var rotateDecelerationEnabled: Boolean
    get() {
      return this.internalSettings.rotateDecelerationEnabled
    }
    set(value) {
      this.internalSettings.rotateDecelerationEnabled = value
      applySettings()
    }

  /**
   * Whether a deceleration animation following a scroll gesture is enabled. True by default.
   */
  override var scrollDecelerationEnabled: Boolean
    get() {
      return this.internalSettings.scrollDecelerationEnabled
    }
    set(value) {
      this.internalSettings.scrollDecelerationEnabled = value
      applySettings()
    }

  /**
   * Whether rotate threshold increases when pinching to zoom. true by default.
   */
  override var increaseRotateThresholdWhenPinchingToZoom: Boolean
    get() {
      return this.internalSettings.increaseRotateThresholdWhenPinchingToZoom
    }
    set(value) {
      this.internalSettings.increaseRotateThresholdWhenPinchingToZoom = value
      applySettings()
    }

  /**
   * Whether pinch to zoom threshold increases when rotating. true by default.
   */
  override var increasePinchToZoomThresholdWhenRotating: Boolean
    get() {
      return this.internalSettings.increasePinchToZoomThresholdWhenRotating
    }
    set(value) {
      this.internalSettings.increasePinchToZoomThresholdWhenRotating = value
      applySettings()
    }

  /**
   * The amount by which the zoom level increases or decreases during a double-tap-to-zoom-in or double-touch-to-zoom-out gesture. 1.0 by default. Must be positive.
   */
  override var zoomAnimationAmount: Float
    get() {
      return this.internalSettings.zoomAnimationAmount
    }
    set(value) {
      this.internalSettings.zoomAnimationAmount = value
      applySettings()
    }
}

// End of generated file.