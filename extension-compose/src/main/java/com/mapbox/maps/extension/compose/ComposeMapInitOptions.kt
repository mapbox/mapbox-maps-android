package com.mapbox.maps.extension.compose

import android.content.Context
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.applyDefaultParams
import com.mapbox.maps.plugin.Plugin

/**
 * Defines configuration [ComposeMapInitOptions] for a [MapboxMap] composable function.
 *
 * @property mapOptions Describes the map options value when using a MapView.
 * @property textureView Flag indicating to use a TextureView as render surface for the MapView. Default is false.
 * @property antialiasingSampleCount Sample count to control multisample anti-aliasing (MSAA) option for rendering. E.g. passing 4 enables MSAA x4 if it is supported. Default is 1 (MSAA turned off).
 * @property mapName Custom name which will be appended to map render related logs. May be useful when using several [MapView]s. Defaults to an empty string.
 */
public data class ComposeMapInitOptions(
  val mapOptions: MapOptions,
  val textureView: Boolean = false,
  val antialiasingSampleCount: Int = DEFAULT_ANTIALIASING_SAMPLE_COUNT,
  val mapName: String = "",
) {
  public constructor(
    pixelRatio: Float,
    textureView: Boolean = false,
    antialiasingSampleCount: Int = DEFAULT_ANTIALIASING_SAMPLE_COUNT,
    mapName: String = "",
  ) : this(
    mapOptions = MapOptions.Builder().applyDefaultParams(pixelRatio).build(),
    textureView = textureView,
    antialiasingSampleCount = antialiasingSampleCount,
    mapName = mapName
  )

  internal fun getMapInitOptions(context: Context): MapInitOptions {
    return MapInitOptions(
      context = context,
      mapOptions = mapOptions,
      // specifically disable following plugins because these are handled within compose extension.
      plugins = MapInitOptions.defaultPluginList - setOf<Plugin>(
        Plugin.Mapbox(Plugin.MAPBOX_LIFECYCLE_PLUGIN_ID),
        Plugin.Mapbox(Plugin.MAPBOX_LOGO_PLUGIN_ID),
        Plugin.Mapbox(Plugin.MAPBOX_ATTRIBUTION_PLUGIN_ID),
        Plugin.Mapbox(Plugin.MAPBOX_SCALEBAR_PLUGIN_ID),
        Plugin.Mapbox(Plugin.MAPBOX_COMPASS_PLUGIN_ID),
      ),
      // specifically set cameraOptions to null, since the camera is controlled through MapViewportState.
      cameraOptions = null,
      textureView = textureView,
      // specifically set styleUri to null, since the default style is provided through style composable function.
      styleUri = null,
      attrs = null,
      antialiasingSampleCount = antialiasingSampleCount,
      mapName = mapName
    )
  }

  /**
   * Companion object.
   */
  public companion object {
    /**
     * Defines the default antialiasing sample count.
     */
    public const val DEFAULT_ANTIALIASING_SAMPLE_COUNT: Int = 1
  }
}