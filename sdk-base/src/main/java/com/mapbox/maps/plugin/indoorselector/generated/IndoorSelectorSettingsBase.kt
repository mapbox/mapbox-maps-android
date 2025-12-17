// This file is generated.

package com.mapbox.maps.plugin.indoorselector.generated

import com.mapbox.maps.MapboxExperimental

/**
 * Abstract settings class for IndoorSelectorPlugin.
 *
 * This abstract class exposes all the required public APIs to configure the IndoorSelectorPlugin.
 */
@MapboxExperimental
abstract class IndoorSelectorSettingsBase : IndoorSelectorSettingsInterface {
  /**
   * Settings for the indoor floor selector.
   */
  protected abstract var internalSettings: IndoorSelectorSettings

  /**
   * Apply the changes to the IndoorSelectorSettings to the IndoorSelectorPlugin.
   */
  protected abstract fun applySettings()

  /**
   * Get current indoorselector configuration.
   *
   * @return indoorselector settings
   */
  override fun getSettings(): IndoorSelectorSettings {
    return internalSettings.toBuilder().build()
  }

  /**
   * Update indoorselector configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of IndoorSelectorSettings
   */
  override fun updateSettings(block: IndoorSelectorSettings.Builder.() -> Unit) {
    val newSettings = this.internalSettings.toBuilder().apply(block).build()
    // Trigger the enabled setter to ensure custom logic (e.g., listener management) is executed
    this.enabled = newSettings.enabled
    this.internalSettings = newSettings
    applySettings()
  }

  /**
   * Whether the indoor selector is visible on the map. Default value: true.
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
   * Defines where the indoor selector is positioned on the map. Default value: "top-right".
   */
  override var position: Int
    get() {
      return this.internalSettings.position
    }
    set(value) {
      if (this.internalSettings.position != value) {
        this.internalSettings = this.internalSettings.toBuilder().setPosition(value).build()
        applySettings()
      }
    }

  /**
   * Defines the margin to the left that the indoor selector honors. Default value: 8.
   */
  override var marginLeft: Float
    get() {
      return this.internalSettings.marginLeft
    }
    set(value) {
      if (this.internalSettings.marginLeft != value) {
        this.internalSettings = this.internalSettings.toBuilder().setMarginLeft(value).build()
        applySettings()
      }
    }

  /**
   * Defines the margin to the top that the indoor selector honors. Default value: 60.
   */
  override var marginTop: Float
    get() {
      return this.internalSettings.marginTop
    }
    set(value) {
      if (this.internalSettings.marginTop != value) {
        this.internalSettings = this.internalSettings.toBuilder().setMarginTop(value).build()
        applySettings()
      }
    }

  /**
   * Defines the margin to the right that the indoor selector honors. Default value: 8.
   */
  override var marginRight: Float
    get() {
      return this.internalSettings.marginRight
    }
    set(value) {
      if (this.internalSettings.marginRight != value) {
        this.internalSettings = this.internalSettings.toBuilder().setMarginRight(value).build()
        applySettings()
      }
    }

  /**
   * Defines the margin to the bottom that the indoor selector honors. Default value: 8.
   */
  override var marginBottom: Float
    get() {
      return this.internalSettings.marginBottom
    }
    set(value) {
      if (this.internalSettings.marginBottom != value) {
        this.internalSettings = this.internalSettings.toBuilder().setMarginBottom(value).build()
        applySettings()
      }
    }
}

// End of generated file.