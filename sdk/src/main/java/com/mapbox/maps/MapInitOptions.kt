package com.mapbox.maps

import android.content.Context
import android.util.AttributeSet
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
 * @property resourceOptions Resource options when using a MapView. Access token required when using a Mapbox service. Please see [https://www.mapbox.com/help/create-api-access-token/](https://www.mapbox.com/help/create-api-access-token/) to learn how to create one.More information in this guide [https://www.mapbox.com/help/first-steps-android-sdk/#access-tokens](https://www.mapbox.com/help/first-steps-android-sdk/#access-tokens).
 * @property mapOptions Describes the map options value when using a MapView.
 * @property plugins The plugins, a list of strings representing class names, that will be loaded as part of MapView initialisation,
 * @property cameraOptions The Initial Camera options when creating a MapView.
 * @property textureView Flag indicating to use a TextureView as render surface for the MapView. Default is false.
 * @property styleUri The styleUri will applied for the MapView in the onStart lifecycle event if no style is set. Default is [Style.MAPBOX_STREETS]. If set to null, then there is no default style will be loaded.
 * @property attrs The [AttributeSet] object that init the MapView.
 */
data class MapInitOptions constructor(
  val context: Context,
  var resourceOptions: ResourceOptions = getDefaultResourceOptions(context),
  var mapOptions: MapOptions = getDefaultMapOptions(context),
  var plugins: List<Plugin> = defaultPluginList,
  var cameraOptions: CameraOptions? = null,
  var textureView: Boolean = false,
  val styleUri: String? = Style.MAPBOX_STREETS,
  var attrs: AttributeSet? = null
) {

  /**
   * Static methods
   */
  companion object {
    /**
     * Get a default [ResourceOptions] instance.
     * @property context the context of the application.
     */
    fun getDefaultResourceOptions(context: Context): ResourceOptions =
      ResourceOptionsManager.getDefault(context).resourceOptions

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
     * consider creating [MapView] programmatically specifying [MapInitOptions.plugins].
     *
     * If creating [MapView] from xml - all plugins listed here will be applied to given [MapView] on startup.
     */
    val defaultPluginList = listOf(
      Plugin.Mapbox(Plugin.MAPBOX_CAMERA_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_GESTURES_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_COMPASS_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_LOGO_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_ATTRIBUTION_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_LOCATION_COMPONENT_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_SCALEBAR_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_ANNOTATION_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_LIFECYCLE_PLUGIN_ID),
      Plugin.Mapbox(Plugin.MAPBOX_MAP_OVERLAY_PLUGIN_ID)
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

/**
 * Get a default [ResourceOptions.Builder] with Mapbox pre-defined options, and
 * with the access token taken from the Android resources(if available).
 *
 * @param context the context of the application.
 */
fun ResourceOptions.Builder.applyDefaultParams(
  context: Context
): ResourceOptions.Builder = also {
  // check in the resources
  val tokenResId = ResourceOptionsManager.getTokenResId(context)
  // Apply the token from resources.
  if (tokenResId != 0) {
    accessToken(context.getString(tokenResId))
  }
}