package com.mapbox.maps

import android.content.res.TypedArray
import android.util.AttributeSet
import java.util.*

/**
 * Utility class for parsing [AttributeSet] to [MapSettings].
 */
internal object MapAttributeParser {

  private const val CONSTRAIN_MODE_HEIGHT_ONLY = 1
  private const val NO_GLYPHS_RASTERIZED_LOCALLY = 0
  private const val IDEOGRAPHS_RASTERIZED_LOCALLY = 1

  /**
   * Parse [AttributeSet] to [MapSettings].
   *
   * @param context Context
   * @param attrs AttributionSet
   */
  fun parseMapOptions(
    attrs: TypedArray,
    pixelRatio: Float = 1.0f
  ): MapOptions {
    val contextMode = attrs.getInt(R.styleable.mapbox_MapView_mapbox_mapContextMode, 0)
    val constrain = attrs.getInt(R.styleable.mapbox_MapView_mapbox_mapConstrainMode, CONSTRAIN_MODE_HEIGHT_ONLY)
    val orientation = attrs.getInt(R.styleable.mapbox_MapView_mapbox_mapOrientation, 0)
    val glyphsMode = attrs.getInt(R.styleable.mapbox_MapView_mapbox_mapGlyphRasterizationMode, IDEOGRAPHS_RASTERIZED_LOCALLY)
    var fontFamily: String? = null
    if (glyphsMode != NO_GLYPHS_RASTERIZED_LOCALLY) {
      fontFamily = FontUtils.extractValidFont(attrs.getString(R.styleable.mapbox_MapView_mapbox_mapFontFamily))
    }
    val viewportMode = attrs.getInt(R.styleable.mapbox_MapView_mapbox_mapViewportMode, 0)
    return MapOptions.Builder()
      .contextMode(ContextMode.values()[contextMode])
      .constrainMode(ConstrainMode.values()[constrain])
      .viewportMode(ViewportMode.values()[viewportMode])
      .orientation(NorthOrientation.values()[orientation])
      .crossSourceCollisions(
        attrs.getBoolean(
          R.styleable.mapbox_MapView_mapbox_mapCrossSourceCollisionsEnabled,
          true
        )
      )
      .optimizeForTerrain(
        attrs.getBoolean(
          R.styleable.mapbox_MapView_mapbox_optimizeForTerrainEnabled,
          true
        )
      )
      .pixelRatio(attrs.getFloat(R.styleable.mapbox_MapView_mapbox_mapPixelRatio, pixelRatio))
      .glyphsRasterizationOptions(
        GlyphsRasterizationOptions.Builder()
          .rasterizationMode(
            GlyphsRasterizationMode.values()[glyphsMode]
          )
          .fontFamily(fontFamily)
          .build()
      )
      .build()
  }
}