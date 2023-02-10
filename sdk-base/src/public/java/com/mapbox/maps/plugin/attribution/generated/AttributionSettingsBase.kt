// This file is generated.

package com.mapbox.maps.plugin.attribution.generated

/**
 * Abstract settings class for AttributionPlugin.
 *
 * This abstract class exposes all the required public APIs to configure the AttributionPlugin.
 */
abstract class AttributionSettingsBase : AttributionSettingsInterface {
  /**
   * Shows the attribution icon on the map.
   */
  protected abstract var internalSettings: AttributionSettings

  /**
   * Apply the changes to the AttributionSettings to the AttributionPlugin.
   */
  protected abstract fun applySettings()

  /**
   * Get current attribution configuration.
   *
   * @return attribution settings
   */
  override fun getSettings(): AttributionSettings {
    return internalSettings.copy()
  }

  /**
   * Update attribution configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of AttributionSettings
   */
  override fun updateSettings(block: AttributionSettings.() -> Unit) {
    this.internalSettings.apply(block)
    applySettings()
  }

  /**
   * Whether the attribution icon is visible on the map.
   */
  override var enabled: Boolean
    get() {
      return this.internalSettings.enabled
    }
    set(value) {
      if (this.internalSettings.enabled != value) {
        this.internalSettings.enabled = value
        applySettings()
      }
    }

  /**
   * Defines text color of the attribution icon.
   */
  override var iconColor: Int
    get() {
      return this.internalSettings.iconColor
    }
    set(value) {
      if (this.internalSettings.iconColor != value) {
        this.internalSettings.iconColor = value
        applySettings()
      }
    }

  /**
   * Defines where the attribution icon is positioned on the map
   */
  override var position: Int
    get() {
      return this.internalSettings.position
    }
    set(value) {
      if (this.internalSettings.position != value) {
        this.internalSettings.position = value
        applySettings()
      }
    }

  /**
   * Defines the margin to the left that the attribution icon honors.
   */
  override var marginLeft: Float
    get() {
      return this.internalSettings.marginLeft
    }
    set(value) {
      if (this.internalSettings.marginLeft != value) {
        this.internalSettings.marginLeft = value
        applySettings()
      }
    }

  /**
   * Defines the margin to the top that the attribution icon honors.
   */
  override var marginTop: Float
    get() {
      return this.internalSettings.marginTop
    }
    set(value) {
      if (this.internalSettings.marginTop != value) {
        this.internalSettings.marginTop = value
        applySettings()
      }
    }

  /**
   * Defines the margin to the right that the attribution icon honors.
   */
  override var marginRight: Float
    get() {
      return this.internalSettings.marginRight
    }
    set(value) {
      if (this.internalSettings.marginRight != value) {
        this.internalSettings.marginRight = value
        applySettings()
      }
    }

  /**
   * Defines the margin to the bottom that the attribution icon honors.
   */
  override var marginBottom: Float
    get() {
      return this.internalSettings.marginBottom
    }
    set(value) {
      if (this.internalSettings.marginBottom != value) {
        this.internalSettings.marginBottom = value
        applySettings()
      }
    }

  /**
   * Whether the attribution can be clicked and click events can be registered.
   */
  override var clickable: Boolean
    get() {
      return this.internalSettings.clickable
    }
    set(value) {
      if (this.internalSettings.clickable != value) {
        this.internalSettings.clickable = value
        applySettings()
      }
    }
}

// End of generated file.