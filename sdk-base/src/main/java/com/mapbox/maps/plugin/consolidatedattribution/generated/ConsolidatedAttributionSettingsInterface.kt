// This file is generated.

package com.mapbox.maps.plugin.consolidatedattribution.generated

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.ConsolidatedAttributionCollapsedStyle
import com.mapbox.maps.plugin.ConsolidatedAttributionIntroExpansion
import com.mapbox.maps.plugin.ConsolidatedAttributionMaterial

/**
 * Interface that defines the public settings interface for ConsolidatedAttributionPlugin.
 */
@MapboxExperimental
interface ConsolidatedAttributionSettingsInterface {
  /**
   * Get current consolidatedattribution configuration.
   *
   * @return consolidatedattribution settings
   */
  fun getSettings(): ConsolidatedAttributionSettings

  /**
   * Update consolidatedattribution configuration, the update will be applied to the plugin automatically.
   *
   * @param block the receiver function of ConsolidatedAttributionSettings
   */
  fun updateSettings(block: ConsolidatedAttributionSettings.Builder.() -> Unit)

  /**
   * Whether the consolidated attribution control is visible on the map. When enabled, it replaces the separate Mapbox logo and attribution icon. Default value: false.
   */
  var enabled: Boolean

  /**
   * Defines where the consolidated attribution control is positioned on the map. Default value: "bottom-left".
   */
  var position: Int

  /**
   * Defines the margin to the left that the consolidated attribution control honors. Default value: 8.
   */
  var marginLeft: Float

  /**
   * Defines the margin to the top that the consolidated attribution control honors. Default value: 8.
   */
  var marginTop: Float

  /**
   * Defines the margin to the right that the consolidated attribution control honors. Default value: 8.
   */
  var marginRight: Float

  /**
   * Defines the margin to the bottom that the consolidated attribution control honors. Default value: 8.
   */
  var marginBottom: Float

  /**
   * Defines how much of the consolidated attribution control remains visible once it collapses. Default value: "icon-and-wordmark".
   */
  var collapsedStyle: ConsolidatedAttributionCollapsedStyle

  /**
   * Defines the material filling the background of the consolidated attribution control. Light and dark rendering follows the night mode of the host configuration rather than this property. Default value: "solid".
   */
  var material: ConsolidatedAttributionMaterial

  /**
   * Defines how often the consolidated attribution control plays its intro expansion. Default value: "once".
   */
  var introExpansion: ConsolidatedAttributionIntroExpansion

  /**
   * Nudges the background of the consolidated attribution control toward the hue of this color, bringing the control closer to the host application palette. Defaults to fully transparent, which applies no tint. Only the hue is taken, so the background keeps the brightness and the translucency selected by the material. The tint applies to the light appearance only, and the Mapbox logo, the attribution text and the chevron are never tinted. Default value: "#00000000".
   */
  var tintColor: Int
}

// End of generated file.