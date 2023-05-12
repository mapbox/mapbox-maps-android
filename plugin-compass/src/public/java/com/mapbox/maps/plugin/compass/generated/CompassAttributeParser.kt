// This file is generated.

package com.mapbox.maps.plugin.compass.generated

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.plugin.compass.R

/**
 * Utility class for parsing [AttributeSet] to [CompassSettings].
 */
internal object CompassAttributeParser {
  /**
   * Parse [AttributeSet] to [CompassSettings].
   *
   * @param context Context
   * @param attrs AttributionSet
   */

  fun parseCompassSettings(context: Context, attrs: AttributeSet?, pixelRatio: Float = 1.0f): CompassSettings {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.mapbox_MapView, 0, 0)
    try {
      return CompassSettings {
        enabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_compassEnabled, true)
        position = typedArray.getInt(R.styleable.mapbox_MapView_mapbox_compassGravity, Gravity.TOP or Gravity.END)
        marginLeft = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_compassMarginLeft, 4f * pixelRatio)
        marginTop = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_compassMarginTop, 4f * pixelRatio)
        marginRight = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_compassMarginRight, 4f * pixelRatio)
        marginBottom = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_compassMarginBottom, 4f * pixelRatio)
        opacity = typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_compassOpacity, 1f)
        rotation = typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_compassRotation, 0f)
        visibility = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_compassVisibility, true)
        fadeWhenFacingNorth = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_compassFadeWhenFacingNorth, true)
        clickable = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_compassClickable, true)
        image = ImageHolder.from(typedArray.getResourceId(R.styleable.mapbox_MapView_mapbox_compassImage, -1))
      }
    } finally {
      typedArray.recycle()
    }
  }
}

// End of generated file.