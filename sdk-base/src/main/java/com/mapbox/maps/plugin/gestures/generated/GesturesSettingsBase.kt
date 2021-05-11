// This file is generated.

package com.mapbox.maps.plugin.gestures.generated

import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.PanScrollMode

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
   * Whether rotation gestures are enabled.
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
   * Whether zoom gestures are enabled.
   */
  override var zoomEnabled: Boolean
    get() {
      return this.internalSettings.zoomEnabled
    }
    set(value) {
      this.internalSettings.zoomEnabled = value
      applySettings()
    }

  /**
   * Whether scroll gestures are enabled.
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
   * Whether pitch gestures are enabled.
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
   * Whether the user is restricted in which direction the map is scrolled.
   */
  override var panScrollMode: PanScrollMode
    get() {
      return this.internalSettings.panScrollMode
    }
    set(value) {
      this.internalSettings.panScrollMode = value
      applySettings()
    }

  /**
   * Whether double tapping the map results in a zoom gesture.
   */
  override var doubleTapToZoomEnabled: Boolean
    get() {
      return this.internalSettings.doubleTapToZoomEnabled
    }
    set(value) {
      this.internalSettings.doubleTapToZoomEnabled = value
      applySettings()
    }

  /**
   * Whether quick zoom gesture is enabled.
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
   * Whether a gesture executes around a fixed focal point or the center of the gesture.
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
   * Whether scale velocity animations are enabled, true by default.
   */
  override var scaleVelocityAnimationEnabled: Boolean
    get() {
      return this.internalSettings.scaleVelocityAnimationEnabled
    }
    set(value) {
      this.internalSettings.scaleVelocityAnimationEnabled = value
      applySettings()
    }

  /**
   * Whether rotate velocity animations are enabled, true by default.
   */
  override var rotateVelocityAnimationEnabled: Boolean
    get() {
      return this.internalSettings.rotateVelocityAnimationEnabled
    }
    set(value) {
      this.internalSettings.rotateVelocityAnimationEnabled = value
      applySettings()
    }

  /**
   * Whether fling velocity animations are enabled, true by default.
   */
  override var flingVelocityAnimationEnabled: Boolean
    get() {
      return this.internalSettings.flingVelocityAnimationEnabled
    }
    set(value) {
      this.internalSettings.flingVelocityAnimationEnabled = value
      applySettings()
    }

  /**
   * Whether rotate threshold increases when scaling. true by default.
   */
  override var increaseRotateThresholdWhenScaling: Boolean
    get() {
      return this.internalSettings.increaseRotateThresholdWhenScaling
    }
    set(value) {
      this.internalSettings.increaseRotateThresholdWhenScaling = value
      applySettings()
    }

  /**
   * Whether rotate is disabled when scaling. true by default.
   */
  override var disableRotateWhenScaling: Boolean
    get() {
      return this.internalSettings.disableRotateWhenScaling
    }
    set(value) {
      this.internalSettings.disableRotateWhenScaling = value
      applySettings()
    }

  /**
   * Whether scale threshold increases when rotating. true by default.
   */
  override var increaseScaleThresholdWhenRotating: Boolean
    get() {
      return this.internalSettings.increaseScaleThresholdWhenRotating
    }
    set(value) {
      this.internalSettings.increaseScaleThresholdWhenRotating = value
      applySettings()
    }

  /**
   * The rate at which the zoom level increases. 1.0 by default
   */
  override var zoomRate: Float
    get() {
      return this.internalSettings.zoomRate
    }
    set(value) {
      this.internalSettings.zoomRate = value
      applySettings()
    }

  /**
   * The pixel ratio of the device that the gestures will take in account.
   */
  override var pixelRatio: Float
    get() {
      return this.internalSettings.pixelRatio
    }
    set(value) {
      this.internalSettings.pixelRatio = value
      applySettings()
    }
}

// End of generated file.