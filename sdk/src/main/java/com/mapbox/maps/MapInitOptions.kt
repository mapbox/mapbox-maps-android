package com.mapbox.maps

import android.content.Context
import android.util.AttributeSet
import com.mapbox.maps.plugin.*
import java.io.File

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
  var plugins: List<String> = getDefaultPlugins(),
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
     * Get a default selection of Mapbox created plugins.
     */
    fun getDefaultPlugins(): List<String> {
      return listOf(
        PLUGIN_CAMERA_ANIMATIONS_CLASS_NAME,
        PLUGIN_COMPASS_CLASS_NAME,
        PLUGIN_LOGO_CLASS_NAME,
        PLUGIN_GESTURE_CLASS_NAME,
        PLUGIN_ATTRIBUTION_CLASS_NAME,
        PLUGIN_LOCATION_COMPONENT_CLASS_NAME,
        PLUGIN_SCALE_BAR_CLASS_NAME,
        PLUGIN_MAPOVERLAY_CLASS_NAME,
        PLUGIN_ANNOTATION_CLASS_NAME
      )
    }
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
 * Get a default [ResourceOptions.Builder] with token from the token parameter or from the
 * Android resources. If no token found, it will throw [MapboxConfigurationException].
 *
 * @param context the context of the application.
 * @param token the default token for the builder, if not specified, the token will be fetched from Android resources. Will throw [MapboxConfigurationException] if there's no token found.
 */
fun ResourceOptions.Builder.applyDefaultParams(
  context: Context,
  token: String? = null
): ResourceOptions.Builder = also {
  // make sure that directory `/mapbox/maps` exists
  val databaseDirectoryPath = "${context.filesDir.absolutePath}/$DATABASE_PATH"
  val databaseDirectory = File(databaseDirectoryPath)
  if (!databaseDirectory.exists()) {
    if (!databaseDirectory.mkdirs()) {
      throw IllegalStateException("Unable to create database folder")
    }
  }

  if (token != null) {
    // Apply the user setting token as the default token
    accessToken(token)
  } else {
    // Otherwise check in the resources
    val tokenResId = context.resources.getIdentifier(
      MAPBOX_ACCESS_TOKEN_RESOURCE_NAME,
      "string",
      context.packageName
    )
    // Throw exception as no token could be found as default token.
    if (tokenResId == 0) throw MapboxConfigurationException()
    accessToken(context.getString(tokenResId))
  }

  cachePath("$databaseDirectoryPath/$DATABASE_NAME")
  cacheSize(DEFAULT_CACHE_SIZE) // 50 mb
}