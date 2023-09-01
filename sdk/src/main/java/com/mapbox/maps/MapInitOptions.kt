package com.mapbox.maps

import android.content.Context
import android.util.AttributeSet
import com.mapbox.maps.MapView.Companion.DEFAULT_ANTIALIASING_SAMPLE_COUNT
import com.mapbox.maps.plugin.*

/**
 * Defines configuration [MapInitOptions] for a [MapboxMap]. These options can be used when adding a
 * map to your application programmatically (as opposed to via XML). If you are using a MapFragment,
 * you can pass these options in using the static factory method newInstance(MapboxMapOptions).
 * If you are using a [MapView], you can pass these options in using the constructor
 * MapView(Context, MapboxMapOptions). If you add a map using XML, then you can apply these options
 * using custom XML tags.
 *
 * @property context The context of the MapView.
 * @property mapOptions Describes the map options value when using a MapView.
 * @property plugins The plugins, a list of strings representing class names, that will be loaded as part of MapView initialisation,
 * @property cameraOptions The Initial Camera options when creating a MapView.
 * @property textureView Flag indicating to use a TextureView as render surface for the MapView. Default is false.
 * @property styleUri The styleUri will applied for the MapView in the onStart lifecycle event if no style is set. Default is [Style.STANDARD]. If set to null, then there is no default style will be loaded.
 * @property attrs The [AttributeSet] object that init the MapView.
 * @property antialiasingSampleCount sample count to control multisample anti-aliasing (MSAA) option for rendering. E.g. passing 4 enables MSAA x4 if it is supported. Default is 1 (MSAA turned off).
 */
data class MapInitOptions @JvmOverloads constructor(
  val context: Context,
  var mapOptions: MapOptions = getDefaultMapOptions(context),
  var plugins: List<Plugin> = defaultPluginList,
  var cameraOptions: CameraOptions? = null,
  var textureView: Boolean = false,
  val styleUri: String? = Style.STANDARD,
  var attrs: AttributeSet? = null,
  var antialiasingSampleCount: Int = DEFAULT_ANTIALIASING_SAMPLE_COUNT,
) {

  /**
   * Static methods
   */
  companion object {
    /**
     * Get a default [MapOptions] with reasterization mode [GlyphsRasterizationMode#ALL_GLYPHS_RASTERIZED_LOCALLY]
     * @property context the context of the application.
     */
    fun getDefaultMapOptions(context: Context): MapOptions =
      MapOptions.Builder().applyDefaultParams(context).build()

    /**
     * Default map view plugin registry. All Mapbox plugins are present in this list.
     *
     * If specific plugins are required to be added to [MapView] on startup
     * consider creating [MapView] programmatically specifying [plugins].
     *
     * If creating [MapView] from xml - all plugins listed here will be applied to given [MapView] on startup.
     */
    val defaultPluginList: List<Plugin> = listOf(
      Plugin.Mapbox(Plugin.MAPBOX_CAMERA_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_GESTURES_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_COMPASS_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_LOGO_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_ATTRIBUTION_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_LOCATION_COMPONENT_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_SCALEBAR_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_ANNOTATION_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_LIFECYCLE_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_MAP_OVERLAY_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_VIEWPORT_PLUGIN_ID),
    )
  }
}

/**
 * Get a default [MapOptions.Builder] with reasterization mode [GlyphsRasterizationMode#ALL_GLYPHS_RASTERIZED_LOCALLY]
 * @property context the context of the application.
 * @return [MapOptions.Builder]
 */
fun MapOptions.Builder.applyDefaultParams(context: Context): MapOptions.Builder = also {
  glyphsRasterizationOptions(
    GlyphsRasterizationOptions.Builder()
      .rasterizationMode(GlyphsRasterizationMode.IDEOGRAPHS_RASTERIZED_LOCALLY)
      .fontFamily(FontUtils.extractValidFont(null))
      .build()
  )
  pixelRatio(context.resources.displayMetrics.density)
  constrainMode(ConstrainMode.HEIGHT_ONLY)
  contextMode(ContextMode.UNIQUE)
  orientation(NorthOrientation.UPWARDS)
  viewportMode(ViewportMode.DEFAULT)
  crossSourceCollisions(true)
}