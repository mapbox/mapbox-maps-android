// This file is generated.

package com.mapbox.maps.plugin.attribution.generated

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import com.mapbox.maps.plugin.attribution.R

/**
 * Utility class for parsing [AttributeSet] to [AttributionSettings].
 */
internal object AttributionAttributeParser {
  /**
   * Parse [AttributeSet] to [AttributionSettings].
   *
   * @param context Context
   * @param attrs AttributionSet
   */

  fun parseAttributionSettings(context: Context, attrs: AttributeSet?, pixelRatio: Float = 1.0f): AttributionSettings {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.mapbox_MapView, 0, 0)
    try {
      return AttributionSettings {
        enabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_attributionEnabled, true)
        iconColor = typedArray.getColor(R.styleable.mapbox_MapView_mapbox_attributionIconColor, Color.parseColor("#FF1E8CAB"))
        position = typedArray.getInt(R.styleable.mapbox_MapView_mapbox_attributionGravity, Gravity.BOTTOM or Gravity.START)
        marginLeft = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_attributionMarginLeft, 92f * pixelRatio)
        marginTop = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_attributionMarginTop, 4f * pixelRatio)
        marginRight = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_attributionMarginRight, 4f * pixelRatio)
        marginBottom = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_attributionMarginBottom, 4f * pixelRatio)
        clickable = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_attributionClickable, true)
      }
    } finally {
      typedArray.recycle()
    }
  }
}

// End of generated file.