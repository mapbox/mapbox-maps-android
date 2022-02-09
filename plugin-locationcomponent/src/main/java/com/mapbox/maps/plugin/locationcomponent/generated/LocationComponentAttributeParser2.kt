// This file is generated.

package com.mapbox.maps.plugin.locationcomponent.generated

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.mapbox.maps.plugin.locationcomponent.R

/**
 * Utility class for parsing [AttributeSet] to [LocationComponentSettings].
 */
internal object LocationComponentAttributeParser2 {
  /**
   * Parse [AttributeSet] to [LocationComponentSettings2].
   *
   * @param context Context
   * @param attrs AttributionSet
   */
  fun parseLocationComponentSettings2(context: Context, attrs: AttributeSet?, pixelRatio: Float = 1.0f): LocationComponentSettings2 {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.mapbox_MapView, 0, 0)
    try {
      return LocationComponentSettings2(
        showAccuracyRing = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_locationComponentShowAccuracyRing, false),
        accuracyRingColor = typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentAccuracyRingColor, Color.parseColor("#4d89cff0")),
        accuracyRingBorderColor = typedArray.getColor(R.styleable.mapbox_MapView_mapbox_locationComponentAccuracyRingBorderColor, Color.parseColor("#4d89cff0")),
      )
    } finally {
      typedArray.recycle()
    }
  }
}

// End of generated file.