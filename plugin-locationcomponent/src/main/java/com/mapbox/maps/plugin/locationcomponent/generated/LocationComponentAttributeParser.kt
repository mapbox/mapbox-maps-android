// This file is generated.

package com.mapbox.maps.plugin.locationcomponent.generated

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.mapbox.maps.plugin.PresetPuckStyle
import com.mapbox.maps.plugin.ThreeDLocationPuck
import com.mapbox.maps.plugin.TwoDLocationPuck
import com.mapbox.maps.plugin.locationcomponent.R

/**
 * Utility class for parsing [AttributeSet] to [LocationComponentSettings].
 */
object LocationComponentAttributeParser {
  /**
   * Parse [AttributeSet] to [LocationComponentSettings].
   *
   * @param context Context
   * @param attrs AttributionSet
   */
  fun parseLocationComponentSettings(context: Context, attrs: AttributeSet?, pixelRatio: Float = 1.0f): LocationComponentSettings {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.mapbox_MapView, 0, 0)
    try {
      return LocationComponentSettings(
        enabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_locationComponentEnabled, false),
        staleStateEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_locationComponentStaleStateEnabled, true),
        staleStateTimeout = typedArray.getInt(R.styleable.mapbox_MapView_mapbox_locationComponentStaleStateTimeout, 30000).toLong(),
        minZoomIconScale = typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentMinZoomIconScale, 0.6f),
        maxZoomIconScale = typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentMaxZoomIconScale, 1f),
        layerAbove = typedArray.getString(R.styleable.mapbox_MapView_mapbox_locationComponentLayerAbove),
        layerBelow = typedArray.getString(R.styleable.mapbox_MapView_mapbox_locationComponentLayerBelow),
        locationPuck = when (typedArray.getInt(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuck, -1)) {
          0 -> TwoDLocationPuck(
            topImage = typedArray.getDrawable(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckTwoDLocationPuckTopImage),
            bearingImage = typedArray.getDrawable(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckTwoDLocationPuckBearingImage),
            shadowImage = typedArray.getDrawable(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckTwoDLocationPuckShadowImage),
            bearingTintColor = if (typedArray.hasValue(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckTwoDLocationPuckBearingTintColor)) {
              typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckTwoDLocationPuckBearingTintColor, -1)
            } else null,
            bearingStaleTintColor = if (typedArray.hasValue(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckTwoDLocationPuckBearingStaleTintColor)) {
              typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckTwoDLocationPuckBearingStaleTintColor, -1)
            } else null,
            topTintColor = if (typedArray.hasValue(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckTwoDLocationPuckTopTintColor)) {
              typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckTwoDLocationPuckTopTintColor, -1)
            } else null,
            topStaleTintColor = if (typedArray.hasValue(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckTwoDLocationPuckTopStaleTintColor)) {
              typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckTwoDLocationPuckTopStaleTintColor, -1)
            } else null,
            shadowTintColor = if (typedArray.hasValue(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckTwoDLocationPuckShadowTintColor)) {
              typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckTwoDLocationPuckShadowTintColor, -1)
            } else null,
            shadowStaleTintColor = if (typedArray.hasValue(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckTwoDLocationPuckShadowStaleTintColor)) {
              typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckTwoDLocationPuckShadowStaleTintColor, -1)
            } else null,
            pulsingEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckTwoDLocationPuckPulsingEnabled, false),
            pulsingColor = typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckTwoDLocationPuckPulsingColor, Color.BLUE),
            pulsingMaxRadius = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckTwoDLocationPuckPulsingMaxRadius, 10f),
          )
          1 -> ThreeDLocationPuck(
            modelUri = typedArray.getString(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckThreeDLocationPuckModelUri)
              ?: throw RuntimeException("model-uri must be specified in order to use 3d location puck."),
            position = listOf(
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckThreeDLocationPuckPosition_lon, 0.0f),
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckThreeDLocationPuckPosition_lat, 0.0f),
            ),
            modelOpacity = typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckThreeDLocationPuckModelOpacity, 1f),
            modelScale = listOf(
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckThreeDLocationPuckModelScale_x, 1.0f),
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckThreeDLocationPuckModelScale_y, 1.0f),
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckThreeDLocationPuckModelScale_z, 1.0f),
            ),
            modelRotation = listOf(
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckThreeDLocationPuckModelRotation_x, 0.0f),
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckThreeDLocationPuckModelRotation_y, 0.0f),
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckThreeDLocationPuckModelRotation_z, 90.0f),
            ),
          )
          else -> null
        },
        presetPuckStyle = PresetPuckStyle.values()[typedArray.getInt(R.styleable.mapbox_MapView_mapbox_locationComponentPresetPuckStyle, 0)],
      )
    } finally {
      typedArray.recycle()
    }
  }
}

// End of generated file.