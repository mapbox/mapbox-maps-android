// This file is generated.

package com.mapbox.maps.plugin.locationcomponent.generated

import com.mapbox.maps.plugin.LocationPuck
import com.mapbox.maps.plugin.PuckBearingSource

/**
 * Abstract settings class for LocationComponentPlugin.
 *
 * This abstract class exposes all the required public APIs to configure the LocationComponentPlugin.
 */
abstract class LocationComponentSettingsBase : LocationComponentSettingsInterface {
  /**
   * Shows a location puck on the map.
   */
  protected abstract var internalSettings: LocationComponentSettings

  /**
   * Apply the changes to the LocationComponentSettings to the LocationComponentPlugin.
   */
  protected abstract fun applySettings()

  /**
   * Get current locationcomponent configuration.
   *
   * @return locationcomponent settings
   */
  override fun getSettings(): LocationComponentSettings {
    return internalSettings.toBuilder().build()
  }

  /**
   * Update locationcomponent configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of LocationComponentSettings
   */
  override fun updateSettings(block: LocationComponentSettings.Builder.() -> Unit) {
    this.internalSettings = this.internalSettings.toBuilder().apply(block).build()
    applySettings()
  }

  /**
   * Whether the user location is visible on the map.
   */
  override var enabled: Boolean
    get() {
      return this.internalSettings.enabled
    }
    set(value) {
      if (this.internalSettings.enabled != value) {
        this.internalSettings = this.internalSettings.toBuilder().setEnabled(value).build()
        applySettings()
      }
    }

  /**
   * Whether the location puck is pulsing on the map. Works for 2D location puck only.
   */
  override var pulsingEnabled: Boolean
    get() {
      return this.internalSettings.pulsingEnabled
    }
    set(value) {
      if (this.internalSettings.pulsingEnabled != value) {
        this.internalSettings = this.internalSettings.toBuilder().setPulsingEnabled(value).build()
        applySettings()
      }
    }

  /**
   * The color of the pulsing circle. Works for 2D location puck only.
   */
  override var pulsingColor: Int
    get() {
      return this.internalSettings.pulsingColor
    }
    set(value) {
      if (this.internalSettings.pulsingColor != value) {
        this.internalSettings = this.internalSettings.toBuilder().setPulsingColor(value).build()
        applySettings()
      }
    }

  /**
   * The maximum radius of the pulsing circle. Works for 2D location puck only. Note: Setting [pulsingMaxRadius] to LocationComponentConstants.PULSING_MAX_RADIUS_FOLLOW_ACCURACY will set the pulsing circle's maximum radius to follow location accuracy circle.
   */
  override var pulsingMaxRadius: Float
    get() {
      return this.internalSettings.pulsingMaxRadius
    }
    set(value) {
      if (this.internalSettings.pulsingMaxRadius != value) {
        this.internalSettings = this.internalSettings.toBuilder().setPulsingMaxRadius(value).build()
        applySettings()
      }
    }

  /**
   * Whether show accuracy ring with location puck. Works for 2D location puck only.
   */
  override var showAccuracyRing: Boolean
    get() {
      return this.internalSettings.showAccuracyRing
    }
    set(value) {
      if (this.internalSettings.showAccuracyRing != value) {
        this.internalSettings = this.internalSettings.toBuilder().setShowAccuracyRing(value).build()
        applySettings()
      }
    }

  /**
   * The color of the accuracy ring. Works for 2D location puck only.
   */
  override var accuracyRingColor: Int
    get() {
      return this.internalSettings.accuracyRingColor
    }
    set(value) {
      if (this.internalSettings.accuracyRingColor != value) {
        this.internalSettings = this.internalSettings.toBuilder().setAccuracyRingColor(value).build()
        applySettings()
      }
    }

  /**
   * The color of the accuracy ring border. Works for 2D location puck only.
   */
  override var accuracyRingBorderColor: Int
    get() {
      return this.internalSettings.accuracyRingBorderColor
    }
    set(value) {
      if (this.internalSettings.accuracyRingBorderColor != value) {
        this.internalSettings = this.internalSettings.toBuilder().setAccuracyRingBorderColor(value).build()
        applySettings()
      }
    }

  /**
   * Sets the id of the layer that's added above to when placing the component on the map.
   */
  override var layerAbove: String?
    get() {
      return this.internalSettings.layerAbove
    }
    set(value) {
      if (this.internalSettings.layerAbove != value) {
        this.internalSettings = this.internalSettings.toBuilder().setLayerAbove(value).build()
        applySettings()
      }
    }

  /**
   * Sets the id of the layer that's added below to when placing the component on the map.
   */
  override var layerBelow: String?
    get() {
      return this.internalSettings.layerBelow
    }
    set(value) {
      if (this.internalSettings.layerBelow != value) {
        this.internalSettings = this.internalSettings.toBuilder().setLayerBelow(value).build()
        applySettings()
      }
    }

  /**
   * Whether the puck rotates to track the bearing source.
   */
  override var puckBearingEnabled: Boolean
    get() {
      return this.internalSettings.puckBearingEnabled
    }
    set(value) {
      if (this.internalSettings.puckBearingEnabled != value) {
        this.internalSettings = this.internalSettings.toBuilder().setPuckBearingEnabled(value).build()
        applySettings()
      }
    }

  /**
   * The enum controls how the puck is oriented
   */
  override var puckBearingSource: PuckBearingSource
    get() {
      return this.internalSettings.puckBearingSource
    }
    set(value) {
      if (this.internalSettings.puckBearingSource != value) {
        this.internalSettings = this.internalSettings.toBuilder().setPuckBearingSource(value).build()
        applySettings()
      }
    }

  /**
   * Defines what the customised look of the location puck. Note that direct changes to the puck variables won't have any effect, a new puck needs to be set every time.
   */
  override var locationPuck: LocationPuck
    get() {
      return this.internalSettings.locationPuck
    }
    set(value) {
      if (this.internalSettings.locationPuck != value) {
        this.internalSettings = this.internalSettings.toBuilder().setLocationPuck(value).build()
        applySettings()
      }
    }
}

// End of generated file.