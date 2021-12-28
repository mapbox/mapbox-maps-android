// This file is generated.

package com.mapbox.maps.plugin.locationcomponent.generated

/**
 * Abstract settings class for LocationComponentPlugin.
 *
 * This abstract class exposes all the required public APIs to configure the AccuracyRing of the LocationComponentPlugin.
 */
abstract class LocationComponentSettingsBase2 : LocationComponentSettingsInterface2, LocationComponentSettingsBase() {
  /**
   * The internalSettings that hold all the settings
   */
  protected abstract var internalAccuracyRingSettings: LocationComponentAccuracyRingSettings

  /**
   * Apply the changes to the LocationComponentSettings to the LocationComponentPlugin.
   */
  protected abstract fun applyAccuracyRingSettings()

  /**
   * Get current locationcomponent configuration.
   *
   * @return locationcomponent settings
   */
  override fun getAccuracyRingSettings(): LocationComponentAccuracyRingSettings {
    return internalAccuracyRingSettings.copy()
  }

  /**
   * Update locationcomponent configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of LocationComponentSettings
   */
  override fun updateAccuracyRingSettings(block: LocationComponentAccuracyRingSettings.() -> Unit) {
    this.internalAccuracyRingSettings.apply(block)
    applyAccuracyRingSettings()
  }

  /**
   * Whether show accuracy ring with location puck. Works for 2D location puck only.
   */
  override var showAccuracyRing: Boolean
    get() {
      return this.internalAccuracyRingSettings.showAccuracyRing
    }
    set(value) {
      this.internalAccuracyRingSettings.showAccuracyRing = value
      applyAccuracyRingSettings()
    }

  /**
   * The color of the accuracy ring. Works for 2D location puck only.
   */
  override var accuracyRingColor: Int
    get() {
      return this.internalAccuracyRingSettings.accuracyRingColor
    }
    set(value) {
      this.internalAccuracyRingSettings.accuracyRingColor = value
      applyAccuracyRingSettings()
    }

  /**
   * The color of the accuracy ring border. Works for 2D location puck only.
   */
  override var accuracyRingBorderColor: Int
    get() {
      return this.internalAccuracyRingSettings.accuracyRingBorderColor
    }
    set(value) {
      this.internalAccuracyRingSettings.accuracyRingBorderColor = value
      applyAccuracyRingSettings()
    }
}

// End of generated file.