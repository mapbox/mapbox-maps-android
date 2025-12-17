// This file is generated.

package com.mapbox.maps.plugin.indoorselector.generated

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import com.mapbox.maps.plugin.indoorselector.R

/**
 * Utility class for parsing [AttributeSet] to [IndoorSelectorSettings].
 */
internal object IndoorSelectorAttributeParser {
  /**
   * Parse [AttributeSet] to [IndoorSelectorSettings].
   *
   * @param context Context
   * @param attrs AttributionSet
   */
  fun parseIndoorSelectorSettings(context: Context, attrs: AttributeSet?, pixelRatio: Float = 1.0f): IndoorSelectorSettings {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.mapbox_MapView, 0, 0)
    try {
      return IndoorSelectorSettings {
        enabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_indoorSelectorEnabled, true)
        position = typedArray.getInt(R.styleable.mapbox_MapView_mapbox_indoorSelectorGravity, Gravity.TOP or Gravity.END)
        marginLeft = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_indoorSelectorMarginLeft, 8f * pixelRatio)
        marginTop = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_indoorSelectorMarginTop, 60f * pixelRatio)
        marginRight = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_indoorSelectorMarginRight, 8f * pixelRatio)
        marginBottom = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_indoorSelectorMarginBottom, 8f * pixelRatio)
      }
    } finally {
      typedArray.recycle()
    }
  }
}

// End of generated file.