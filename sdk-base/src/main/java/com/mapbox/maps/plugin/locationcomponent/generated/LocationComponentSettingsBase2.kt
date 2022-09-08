// This file is generated.

package com.mapbox.maps.plugin.locationcomponent.generated
import com.mapbox.maps.plugin.PuckBearingSource

/**
 * Abstract settings class for LocationComponentPlugin.
 *
 * This abstract class exposes all the required public APIs to configure the LocationComponentPlugin.
 */
abstract class LocationComponentSettingsBase2 : LocationComponentSettingsInterface2, LocationComponentSettingsBase() {
  /**
   * Shows a location puck on the map.
   */
  protected abstract var internalSettings2: LocationComponentSettings2

  /**
   * Apply the changes to the LocationComponentSettings to the LocationComponentPlugin.
   */
  protected abstract fun applySettings2()

  /**
   * Get current locationcomponent configuration.
   *
   * @return locationcomponent settings
   */
  override fun getSettings2(): LocationComponentSettings2 {
    return internalSettings2.copy()
  }

  /**
   * Update locationcomponent configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of LocationComponentSettings
   */
  override fun updateSettings2(block: LocationComponentSettings2.() -> Unit) {
    this.internalSettings2.apply(block)
    applySettings2()
  }

  /**
   * Whether show accuracy ring with location puck. Works for 2D location puck only.
   */
  override var showAccuracyRing: Boolean
    get() {
      return this.internalSettings2.showAccuracyRing
    }
    set(value) {
      if (this.internalSettings2.showAccuracyRing != value) {
        this.internalSettings2.showAccuracyRing = value
        applySettings2()
      }
    }

  /**
   * The color of the accuracy ring. Works for 2D location puck only.
   */
  override var accuracyRingColor: Int
    get() {
      return this.internalSettings2.accuracyRingColor
    }
    set(value) {
      if (this.internalSettings2.accuracyRingColor != value) {
        this.internalSettings2.accuracyRingColor = value
        applySettings2()
      }
    }

  /**
   * The color of the accuracy ring border. Works for 2D location puck only.
   */
  override var accuracyRingBorderColor: Int
    get() {
      return this.internalSettings2.accuracyRingBorderColor
    }
    set(value) {
      if (this.internalSettings2.accuracyRingBorderColor != value) {
        this.internalSettings2.accuracyRingBorderColor = value
        applySettings2()
      }
    }

  /**
   * Whether the puck rotates to track the bearing source.
   */
  override var puckBearingEnabled: Boolean
    get() {
      return this.internalSettings2.puckBearingEnabled
    }
    set(value) {
      if (this.internalSettings2.puckBearingEnabled != value) {
        this.internalSettings2.puckBearingEnabled = value
        applySettings2()
      }
    }

  /**
   * The enum controls how the puck is oriented
   */
  override var puckBearingSource: PuckBearingSource
    get() {
      return this.internalSettings2.puckBearingSource
    }
    set(value) {
      if (this.internalSettings2.puckBearingSource != value) {
        this.internalSettings2.puckBearingSource = value
        applySettings2()
      }
    }
}

// End of generated file.