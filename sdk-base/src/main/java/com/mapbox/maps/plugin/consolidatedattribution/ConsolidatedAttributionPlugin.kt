package com.mapbox.maps.plugin.consolidatedattribution

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.LifecyclePlugin
import com.mapbox.maps.plugin.ViewPlugin
import com.mapbox.maps.plugin.attribution.AttributionDialogManager
import com.mapbox.maps.plugin.consolidatedattribution.generated.ConsolidatedAttributionSettingsInterface
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate

/**
 * Plugin interface for the consolidated attribution control.
 *
 * The control combines the Mapbox logo, the data attribution text and a disclosure chevron into a
 * single pill. It expands once on first launch, then collapses on a timer and on any map
 * interaction. While it is enabled it hides the separate logo and attribution ornaments.
 *
 * The plugin is not part of [com.mapbox.maps.MapInitOptions.defaultPluginList]; add it explicitly:
 * ```
 * MapInitOptions(
 *   context = context,
 *   plugins = MapInitOptions.defaultPluginList +
 *     Plugin.Mapbox(Plugin.MAPBOX_CONSOLIDATED_ATTRIBUTION_PLUGIN_ID),
 * )
 * ```
 * then opt in:
 * ```
 * mapView.consolidatedAttribution.updateSettings { enabled = true }
 * ```
 */
@MapboxExperimental
interface ConsolidatedAttributionPlugin : ViewPlugin, LifecyclePlugin, ConsolidatedAttributionSettingsInterface {

  /**
   * Set a custom [AttributionDialogManager] invoked when the pill is tapped.
   */
  @MapboxExperimental
  fun setCustomAttributionDialogManager(dialogManager: AttributionDialogManager)

  /**
   * Get the instance of [MapAttributionDelegate] backing the control's attribution text.
   */
  @MapboxExperimental
  fun getMapAttributionDelegate(): MapAttributionDelegate
}