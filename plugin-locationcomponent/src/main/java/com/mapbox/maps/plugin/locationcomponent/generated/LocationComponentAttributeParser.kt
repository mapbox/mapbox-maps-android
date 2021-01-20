// This file is generated.

package com.mapbox.maps.plugin.locationcomponent.generated

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.PresetPuckStyle
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
        pulsingEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_locationComponentPulsingEnabled, false),
        pulsingColor = typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentPulsingColor, Color.BLUE),
        pulsingMaxRadius = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_locationComponentPulsingMaxRadius, 10f * pixelRatio),
        layerAbove = typedArray.getString(R.styleable.mapbox_MapView_mapbox_locationComponentLayerAbove),
        layerBelow = typedArray.getString(R.styleable.mapbox_MapView_mapbox_locationComponentLayerBelow),
        locationPuck = when (typedArray.getInt(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuck, -1)) {
          0 -> LocationPuck2D(
            topImage = typedArray.getDrawable(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DTopImage),
            bearingImage = typedArray.getDrawable(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DBearingImage),
            shadowImage = typedArray.getDrawable(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DShadowImage),
            bearingTintColor = if (typedArray.hasValue(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DBearingTintColor)) {
              typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DBearingTintColor, -1)
            } else null,
            bearingStaleTintColor = if (typedArray.hasValue(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DBearingStaleTintColor)) {
              typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DBearingStaleTintColor, -1)
            } else null,
            topTintColor = if (typedArray.hasValue(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DTopTintColor)) {
              typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DTopTintColor, -1)
            } else null,
            topStaleTintColor = if (typedArray.hasValue(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DTopStaleTintColor)) {
              typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DTopStaleTintColor, -1)
            } else null,
            shadowTintColor = if (typedArray.hasValue(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DShadowTintColor)) {
              typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DShadowTintColor, -1)
            } else null,
            shadowStaleTintColor = if (typedArray.hasValue(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DShadowStaleTintColor)) {
              typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DShadowStaleTintColor, -1)
            } else null,
          )
          1 -> LocationPuck3D(
            modelUri = typedArray.getString(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck3DModelUri)
              ?: throw RuntimeException("model-uri must be specified in order to use 3d location puck."),
            position = listOf(
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck3DPosition_lon, 0.0f),
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck3DPosition_lat, 0.0f),
            ),
            modelOpacity = typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck3DModelOpacity, 1f),
            modelScale = listOf(
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck3DModelScale_x, 1.0f),
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck3DModelScale_y, 1.0f),
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck3DModelScale_z, 1.0f),
            ),
            modelScaleExpression = typedArray.getString(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck3DModelScaleExpression),
            modelRotation = listOf(
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck3DModelRotation_x, 0.0f),
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck3DModelRotation_y, 0.0f),
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck3DModelRotation_z, 90.0f),
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