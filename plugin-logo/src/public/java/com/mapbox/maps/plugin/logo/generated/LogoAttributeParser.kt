// This file is generated.

package com.mapbox.maps.plugin.logo.generated

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import com.mapbox.maps.plugin.logo.R

/**
 * Utility class for parsing [AttributeSet] to [LogoSettings].
 */
internal object LogoAttributeParser {
  /**
   * Parse [AttributeSet] to [LogoSettings].
   *
   * @param context Context
   * @param attrs AttributionSet
   */

  fun parseLogoSettings(context: Context, attrs: AttributeSet?, pixelRatio: Float = 1.0f): LogoSettings {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.mapbox_MapView, 0, 0)
    try {
      return LogoSettings {
        enabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_logoEnabled, true)
        position = typedArray.getInt(R.styleable.mapbox_MapView_mapbox_logoGravity, Gravity.BOTTOM or Gravity.START)
        marginLeft = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_logoMarginLeft, 4f * pixelRatio)
        marginTop = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_logoMarginTop, 4f * pixelRatio)
        marginRight = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_logoMarginRight, 4f * pixelRatio)
        marginBottom = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_logoMarginBottom, 4f * pixelRatio)
      }
    } finally {
      typedArray.recycle()
    }
  }
}

// End of generated file.