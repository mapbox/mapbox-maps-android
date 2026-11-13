// This file is generated.

package com.mapbox.maps.plugin.consolidatedattribution.generated

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.ConsolidatedAttributionCollapsedStyle
import com.mapbox.maps.plugin.ConsolidatedAttributionIntroExpansion
import com.mapbox.maps.plugin.ConsolidatedAttributionMaterial

/**
 * Abstract settings class for ConsolidatedAttributionPlugin.
 *
 * This abstract class exposes all the required public APIs to configure the ConsolidatedAttributionPlugin.
 */
@MapboxExperimental
abstract class ConsolidatedAttributionSettingsBase : ConsolidatedAttributionSettingsInterface {
  /**
   * Shows the consolidated attribution control, a single pill combining the Mapbox logo, the data attribution text, and a disclosure chevron.
   */
  protected abstract var internalSettings: ConsolidatedAttributionSettings

  /**
   * Apply the changes to the ConsolidatedAttributionSettings to the ConsolidatedAttributionPlugin.
   */
  protected abstract fun applySettings()

  /**
   * Get current consolidatedattribution configuration.
   *
   * @return consolidatedattribution settings
   */
  override fun getSettings(): ConsolidatedAttributionSettings {
    return internalSettings.toBuilder().build()
  }

  /**
   * Update consolidatedattribution configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of ConsolidatedAttributionSettings
   */
  override fun updateSettings(block: ConsolidatedAttributionSettings.Builder.() -> Unit) {
    val newSettings = this.internalSettings.toBuilder().apply(block).build()
    // Trigger the enabled setter to ensure custom logic (e.g., listener management) is executed
    this.enabled = newSettings.enabled
    this.internalSettings = newSettings
    applySettings()
  }

  /**
   * Whether the consolidated attribution control is visible on the map. When enabled, it replaces the separate Mapbox logo and attribution icon. Default value: false.
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
   * Defines where the consolidated attribution control is positioned on the map. Default value: "bottom-left".
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
   * Defines the margin to the left that the consolidated attribution control honors. Default value: 8.
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
   * Defines the margin to the top that the consolidated attribution control honors. Default value: 8.
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
   * Defines the margin to the right that the consolidated attribution control honors. Default value: 8.
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
   * Defines the margin to the bottom that the consolidated attribution control honors. Default value: 8.
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

  /**
   * Defines how much of the consolidated attribution control remains visible once it collapses. Default value: "icon-and-wordmark".
   */
  override var collapsedStyle: ConsolidatedAttributionCollapsedStyle
    get() {
      return this.internalSettings.collapsedStyle
    }
    set(value) {
      if (this.internalSettings.collapsedStyle != value) {
        this.internalSettings = this.internalSettings.toBuilder().setCollapsedStyle(value).build()
        applySettings()
      }
    }

  /**
   * Defines the material filling the background of the consolidated attribution control. Light and dark rendering follows the night mode of the host configuration rather than this property. Default value: "solid".
   */
  override var material: ConsolidatedAttributionMaterial
    get() {
      return this.internalSettings.material
    }
    set(value) {
      if (this.internalSettings.material != value) {
        this.internalSettings = this.internalSettings.toBuilder().setMaterial(value).build()
        applySettings()
      }
    }

  /**
   * Defines how often the consolidated attribution control plays its intro expansion. Default value: "once".
   */
  override var introExpansion: ConsolidatedAttributionIntroExpansion
    get() {
      return this.internalSettings.introExpansion
    }
    set(value) {
      if (this.internalSettings.introExpansion != value) {
        this.internalSettings = this.internalSettings.toBuilder().setIntroExpansion(value).build()
        applySettings()
      }
    }

  /**
   * Nudges the background of the consolidated attribution control toward the hue of this color, bringing the control closer to the host application palette. Defaults to fully transparent, which applies no tint. Only the hue is taken, so the background keeps the brightness and the translucency selected by the material. The tint applies to the light appearance only, and the Mapbox logo, the attribution text and the chevron are never tinted. Default value: "#00000000".
   */
  override var tintColor: Int
    get() {
      return this.internalSettings.tintColor
    }
    set(value) {
      if (this.internalSettings.tintColor != value) {
        this.internalSettings = this.internalSettings.toBuilder().setTintColor(value).build()
        applySettings()
      }
    }
}

// End of generated file.