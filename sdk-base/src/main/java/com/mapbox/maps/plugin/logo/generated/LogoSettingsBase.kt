// This file is generated.

package com.mapbox.maps.plugin.logo.generated

/**
 * Abstract settings class for LogoPlugin.
 *
 * This abstract class exposes all the required public APIs to configure the LogoPlugin.
 */
abstract class LogoSettingsBase : LogoSettingsInterface {
  /**
   * Shows the Mapbox logo on the map.
   */
  protected abstract var internalSettings: LogoSettings

  /**
   * Apply the changes to the LogoSettings to the LogoPlugin.
   */
  protected abstract fun applySettings()

  /**
   * Get current logo configuration.
   *
   * @return logo settings
   */
  override fun getSettings(): LogoSettings {
    return internalSettings.copy()
  }

  /**
   * Update logo configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of LogoSettings
   */
  override fun updateSettings(block: LogoSettings.() -> Unit) {
    this.internalSettings.apply(block)
    applySettings()
  }

  /**
   * Whether the logo is visible on the map.
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
   * Defines where the logo is positioned on the map
   */
  override var position: Int
    get() {
      return this.internalSettings.position
    }
    set(value) {
      this.internalSettings.position = value
      applySettings()
    }

  /**
   * Defines the margin to the left that the attribution icon honors.
   */
  override var marginLeft: Float
    get() {
      return this.internalSettings.marginLeft
    }
    set(value) {
      this.internalSettings.marginLeft = value
      applySettings()
    }

  /**
   * Defines the margin to the top that the attribution icon honors.
   */
  override var marginTop: Float
    get() {
      return this.internalSettings.marginTop
    }
    set(value) {
      this.internalSettings.marginTop = value
      applySettings()
    }

  /**
   * Defines the margin to the right that the attribution icon honors.
   */
  override var marginRight: Float
    get() {
      return this.internalSettings.marginRight
    }
    set(value) {
      this.internalSettings.marginRight = value
      applySettings()
    }

  /**
   * Defines the margin to the bottom that the attribution icon honors.
   */
  override var marginBottom: Float
    get() {
      return this.internalSettings.marginBottom
    }
    set(value) {
      this.internalSettings.marginBottom = value
      applySettings()
    }
}

// End of generated file.