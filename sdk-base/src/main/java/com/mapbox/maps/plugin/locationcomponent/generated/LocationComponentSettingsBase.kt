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
    return internalSettings.copy()
  }

  /**
   * Update locationcomponent configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of LocationComponentSettings
   */
  override fun updateSettings(block: LocationComponentSettings.() -> Unit) {
    this.internalSettings.apply(block)
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
      this.internalSettings.enabled = value
      applySettings()
    }

  /**
   * Whether the location puck is pulsing on the map. Only work for 2D location puck.
   */
  override var pulsingEnabled: Boolean
    get() {
      return this.internalSettings.pulsingEnabled
    }
    set(value) {
      this.internalSettings.pulsingEnabled = value
      applySettings()
    }

  /**
   * The color of the pulsing circle. Only work for 2D location puck.
   */
  override var pulsingColor: Int
    get() {
      return this.internalSettings.pulsingColor
    }
    set(value) {
      this.internalSettings.pulsingColor = value
      applySettings()
    }

  /**
   * The maximum radius of the pulsing circle. Only work for 2D location puck.
   */
  override var pulsingMaxRadius: Float
    get() {
      return this.internalSettings.pulsingMaxRadius
    }
    set(value) {
      this.internalSettings.pulsingMaxRadius = value
      applySettings()
    }

  /**
   * Sets the id of the layer that's added above to when placing the component on the map.
   */
  override var layerAbove: String?
    get() {
      return this.internalSettings.layerAbove
    }
    set(value) {
      this.internalSettings.layerAbove = value
      applySettings()
    }

  /**
   * Sets the id of the layer that's added below to when placing the component on the map.
   */
  override var layerBelow: String?
    get() {
      return this.internalSettings.layerBelow
    }
    set(value) {
      this.internalSettings.layerBelow = value
      applySettings()
    }

  /**
   * The enum controls how the puck is oriented
   */
  override var puckBearingSource: PuckBearingSource
    get() {
      return this.internalSettings.puckBearingSource
    }
    set(value) {
      this.internalSettings.puckBearingSource = value
      applySettings()
    }

  /**
   * Defines what the customised look of the location puck.
   */
  override var locationPuck: LocationPuck
    get() {
      return this.internalSettings.locationPuck
    }
    set(value) {
      this.internalSettings.locationPuck = value
      applySettings()
    }
}

// End of generated file.