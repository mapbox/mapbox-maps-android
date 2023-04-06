// This file is generated.

package com.mapbox.maps.plugin.locationcomponent.generated

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.PuckBearingSource
import com.mapbox.maps.plugin.locationcomponent.R

/**
 * Utility class for parsing [AttributeSet] to [LocationComponentSettings].
 */
internal object LocationComponentAttributeParser {
  /**
   * Parse [AttributeSet] to [LocationComponentSettings].
   *
   * @param context Context
   * @param attrs AttributionSet
   */
  fun parseLocationComponentSettings(context: Context, attrs: AttributeSet?, pixelRatio: Float = 1.0f): LocationComponentSettings {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.mapbox_MapView, 0, 0)
    try {
      return LocationComponentSettings {
        enabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_locationComponentEnabled, false)
        pulsingEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_locationComponentPulsingEnabled, false)
        pulsingColor = typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentPulsingColor, Color.parseColor("#4A90E2"))
        pulsingMaxRadius = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_locationComponentPulsingMaxRadius, 10f * pixelRatio)
        showAccuracyRing = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_locationComponentShowAccuracyRing, false)
        accuracyRingColor = typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentAccuracyRingColor, Color.parseColor("#4d89cff0"))
        accuracyRingBorderColor = typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentAccuracyRingBorderColor, Color.parseColor("#4d89cff0"))
        layerAbove = typedArray.getString(R.styleable.mapbox_MapView_mapbox_locationComponentLayerAbove)
        layerBelow = typedArray.getString(R.styleable.mapbox_MapView_mapbox_locationComponentLayerBelow)
        puckBearingEnabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_locationComponentPuckBearingEnabled, true)
        puckBearingSource = PuckBearingSource.values()[typedArray.getInt(R.styleable.mapbox_MapView_mapbox_locationComponentPuckBearingSource, 0)]
        locationPuck = when (typedArray.getInt(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuck, -1)) {
          0 -> LocationPuck2D(
            topImage = ImageHolder.from(typedArray.getResourceId(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DTopImage, -1)),
            bearingImage = ImageHolder.from(typedArray.getResourceId(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DBearingImage, -1)),
            shadowImage = ImageHolder.from(typedArray.getResourceId(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DShadowImage, -1)),
            scaleExpression = typedArray.getString(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DScaleExpression),
            opacity = typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck2DOpacity, 1f),
          )
          1 -> LocationPuck3D(
            modelUri = typedArray.getString(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck3DModelUri)
              ?: throw IllegalArgumentException("model-uri must be specified in order to use 3d location puck."),
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
            modelTranslation = listOf(
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck3DModelTranslation_lon, 0.0f),
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck3DModelTranslation_lat, 0.0f),
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck3DModelTranslation_z, 0.0f),
            ),
            modelRotation = listOf(
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck3DModelRotation_x, 0.0f),
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck3DModelRotation_y, 0.0f),
              typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_locationComponentLocationPuckLocationPuck3DModelRotation_z, 90.0f),
            ),
          )
          else -> LocationPuck2D(
            topImage = ImageHolder.from(R.drawable.mapbox_user_icon),
            bearingImage = ImageHolder.from(R.drawable.mapbox_user_stroke_icon),
            shadowImage = ImageHolder.from(R.drawable.mapbox_user_icon_shadow),
          )
        }
      }
    } finally {
      typedArray.recycle()
    }
  }
}

// End of generated file.