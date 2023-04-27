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
    return internalSettings.toBuilder().build()
  }

  /**
   * Update gestures configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of GesturesSettings
   */
  override fun updateSettings(block: GesturesSettings.Builder.() -> Unit) {
    this.internalSettings = this.internalSettings.toBuilder().apply(block).build()
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
      if (this.internalSettings.rotateEnabled != value) {
        this.internalSettings = this.internalSettings.toBuilder().setRotateEnabled(value).build()
        applySettings()
      }
    }

  /**
   * Whether the pinch to zoom gesture is enabled.
   */
  override var pinchToZoomEnabled: Boolean
    get() {
      return this.internalSettings.pinchToZoomEnabled
    }
    set(value) {
      if (this.internalSettings.pinchToZoomEnabled != value) {
        this.internalSettings = this.internalSettings.toBuilder().setPinchToZoomEnabled(value).build()
        applySettings()
      }
    }

  /**
   * Whether the single-touch scroll gesture is enabled.
   */
  override var scrollEnabled: Boolean
    get() {
      return this.internalSettings.scrollEnabled
    }
    set(value) {
      if (this.internalSettings.scrollEnabled != value) {
        this.internalSettings = this.internalSettings.toBuilder().setScrollEnabled(value).build()
        applySettings()
      }
    }

  /**
   * Whether rotation is enabled for the pinch to zoom gesture.
   */
  override var simultaneousRotateAndPinchToZoomEnabled: Boolean
    get() {
      return this.internalSettings.simultaneousRotateAndPinchToZoomEnabled
    }
    set(value) {
      if (this.internalSettings.simultaneousRotateAndPinchToZoomEnabled != value) {
        this.internalSettings = this.internalSettings.toBuilder().setSimultaneousRotateAndPinchToZoomEnabled(value).build()
        applySettings()
      }
    }

  /**
   * Whether the pitch gesture is enabled.
   */
  override var pitchEnabled: Boolean
    get() {
      return this.internalSettings.pitchEnabled
    }
    set(value) {
      if (this.internalSettings.pitchEnabled != value) {
        this.internalSettings = this.internalSettings.toBuilder().setPitchEnabled(value).build()
        applySettings()
      }
    }

  /**
   * Configures the directions in which the map is allowed to move during a scroll gesture.
   */
  override var scrollMode: ScrollMode
    get() {
      return this.internalSettings.scrollMode
    }
    set(value) {
      if (this.internalSettings.scrollMode != value) {
        this.internalSettings = this.internalSettings.toBuilder().setScrollMode(value).build()
        applySettings()
      }
    }

  /**
   * Whether double tapping the map with one touch results in a zoom-in animation.
   */
  override var doubleTapToZoomInEnabled: Boolean
    get() {
      return this.internalSettings.doubleTapToZoomInEnabled
    }
    set(value) {
      if (this.internalSettings.doubleTapToZoomInEnabled != value) {
        this.internalSettings = this.internalSettings.toBuilder().setDoubleTapToZoomInEnabled(value).build()
        applySettings()
      }
    }

  /**
   * Whether single tapping the map with two touches results in a zoom-out animation.
   */
  override var doubleTouchToZoomOutEnabled: Boolean
    get() {
      return this.internalSettings.doubleTouchToZoomOutEnabled
    }
    set(value) {
      if (this.internalSettings.doubleTouchToZoomOutEnabled != value) {
        this.internalSettings = this.internalSettings.toBuilder().setDoubleTouchToZoomOutEnabled(value).build()
        applySettings()
      }
    }

  /**
   * Whether the quick zoom gesture is enabled.
   */
  override var quickZoomEnabled: Boolean
    get() {
      return this.internalSettings.quickZoomEnabled
    }
    set(value) {
      if (this.internalSettings.quickZoomEnabled != value) {
        this.internalSettings = this.internalSettings.toBuilder().setQuickZoomEnabled(value).build()
        applySettings()
      }
    }

  /**
   * By default, gestures rotate and zoom around the center of the gesture. Set this property to rotate and zoom around a fixed point instead.
   */
  override var focalPoint: ScreenCoordinate?
    get() {
      return this.internalSettings.focalPoint
    }
    set(value) {
      if (this.internalSettings.focalPoint != value) {
        this.internalSettings = this.internalSettings.toBuilder().setFocalPoint(value).build()
        applySettings()
      }
    }

  /**
   * Whether a deceleration animation following a pinch-to-zoom gesture is enabled. True by default.
   */
  override var pinchToZoomDecelerationEnabled: Boolean
    get() {
      return this.internalSettings.pinchToZoomDecelerationEnabled
    }
    set(value) {
      if (this.internalSettings.pinchToZoomDecelerationEnabled != value) {
        this.internalSettings = this.internalSettings.toBuilder().setPinchToZoomDecelerationEnabled(value).build()
        applySettings()
      }
    }

  /**
   * Whether a deceleration animation following a rotate gesture is enabled. True by default.
   */
  override var rotateDecelerationEnabled: Boolean
    get() {
      return this.internalSettings.rotateDecelerationEnabled
    }
    set(value) {
      if (this.internalSettings.rotateDecelerationEnabled != value) {
        this.internalSettings = this.internalSettings.toBuilder().setRotateDecelerationEnabled(value).build()
        applySettings()
      }
    }

  /**
   * Whether a deceleration animation following a scroll gesture is enabled. True by default.
   */
  override var scrollDecelerationEnabled: Boolean
    get() {
      return this.internalSettings.scrollDecelerationEnabled
    }
    set(value) {
      if (this.internalSettings.scrollDecelerationEnabled != value) {
        this.internalSettings = this.internalSettings.toBuilder().setScrollDecelerationEnabled(value).build()
        applySettings()
      }
    }

  /**
   * Whether rotate threshold increases when pinching to zoom. true by default.
   */
  override var increaseRotateThresholdWhenPinchingToZoom: Boolean
    get() {
      return this.internalSettings.increaseRotateThresholdWhenPinchingToZoom
    }
    set(value) {
      if (this.internalSettings.increaseRotateThresholdWhenPinchingToZoom != value) {
        this.internalSettings = this.internalSettings.toBuilder().setIncreaseRotateThresholdWhenPinchingToZoom(value).build()
        applySettings()
      }
    }

  /**
   * Whether pinch to zoom threshold increases when rotating. true by default.
   */
  override var increasePinchToZoomThresholdWhenRotating: Boolean
    get() {
      return this.internalSettings.increasePinchToZoomThresholdWhenRotating
    }
    set(value) {
      if (this.internalSettings.increasePinchToZoomThresholdWhenRotating != value) {
        this.internalSettings = this.internalSettings.toBuilder().setIncreasePinchToZoomThresholdWhenRotating(value).build()
        applySettings()
      }
    }

  /**
   * The amount by which the zoom level increases or decreases during a double-tap-to-zoom-in or double-touch-to-zoom-out gesture. 1.0 by default. Must be positive.
   */
  override var zoomAnimationAmount: Float
    get() {
      return this.internalSettings.zoomAnimationAmount
    }
    set(value) {
      if (this.internalSettings.zoomAnimationAmount != value) {
        this.internalSettings = this.internalSettings.toBuilder().setZoomAnimationAmount(value).build()
        applySettings()
      }
    }

  /**
   * Whether pan is enabled for the pinch gesture.
   */
  override var pinchScrollEnabled: Boolean
    get() {
      return this.internalSettings.pinchScrollEnabled
    }
    set(value) {
      if (this.internalSettings.pinchScrollEnabled != value) {
        this.internalSettings = this.internalSettings.toBuilder().setPinchScrollEnabled(value).build()
        applySettings()
      }
    }
}

// End of generated file.