package com.mapbox.maps.extension.compose.internal

import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.plugin.DisabledPlugin
import com.mapbox.maps.plugin.Plugin
import kotlin.collections.plus

/**
 * The Compose extension replaces plugins with Compose-native APIs. This is the single
 * source of truth for that set: each disabled plugin is registered on the map as a [Plugin.Custom]
 * carrying a [DisabledPlugin] placeholder, so accessing its View-based getter (e.g. `mapView.compass`)
 * throws a descriptive [com.mapbox.maps.plugin.PluginDisabledException] pointing at the replacement.
 */
internal object ComposePlugins {

  private val replacements: Map<String, String> = mapOf(
    Plugin.MAPBOX_COMPASS_PLUGIN_ID to "the `MapCompass` composable and `compassSettings`",
    Plugin.MAPBOX_LOGO_PLUGIN_ID to "the `MapLogo` composable and `logoSettings`",
    Plugin.MAPBOX_ATTRIBUTION_PLUGIN_ID to "the `MapAttribution` composable and `attributionSettings`",
    Plugin.MAPBOX_SCALEBAR_PLUGIN_ID to "the `MapScaleBar` composable and `scaleBarSettings`",
    Plugin.MAPBOX_LIFECYCLE_PLUGIN_ID to "the lifecycle handling built into the `MapboxMap` composable",
    Plugin.MAPBOX_INDOOR_SELECTOR_PLUGIN_ID to "the `IndoorSelector` composable and `IndoorSelectorState`",
  )

  /** The full plugin list for a Compose map: defaults with the disabled plugins replaced by placeholders. */
  internal val plugins: List<Plugin> = MapInitOptions.defaultPluginList
    .filterNot { replacements.containsKey(it.id) } +
    replacements.map { (id, replacement) ->
      Plugin.Custom(id, DisabledPlugin(messageFor(id, replacement)))
    }

  private fun messageFor(id: String, replacement: String): String =
    "Plugin '$id' is not available when using the Mapbox Maps Compose extension: it is disabled and " +
      "replaced by $replacement."
}