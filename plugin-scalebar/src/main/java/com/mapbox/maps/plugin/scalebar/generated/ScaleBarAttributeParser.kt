// This file is generated.

package com.mapbox.maps.plugin.scalebar.generated

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import com.mapbox.maps.plugin.scalebar.LocaleUnitResolver
import com.mapbox.maps.plugin.scalebar.R

/**
 * Utility class for parsing [AttributeSet] to [ScaleBarSettings].
 */
object ScaleBarAttributeParser {
  /**
   * Parse [AttributeSet] to [ScaleBarSettings].
   *
   * @param context Context
   * @param attrs AttributionSet
   */
  fun parseScaleBarSettings(context: Context, attrs: AttributeSet?, pixelRatio: Float = 1.0f): ScaleBarSettings {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.mapbox_MapView, 0, 0)
    try {
      return ScaleBarSettings(
        enabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_scaleBarEnabled, true),
        position = typedArray.getInt(R.styleable.mapbox_MapView_mapbox_scaleBarGravity, Gravity.TOP or Gravity.START),
        marginLeft = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_scaleBarMarginLeft, 4f * pixelRatio),
        marginTop = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_scaleBarMarginTop, 4f * pixelRatio),
        marginRight = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_scaleBarMarginRight, 4f * pixelRatio),
        marginBottom = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_scaleBarMarginBottom, 4f * pixelRatio),
        textColor = typedArray.getColor(R.styleable.mapbox_MapView_mapbox_scaleBarTextColor, Color.BLACK),
        primaryColor = typedArray.getColor(R.styleable.mapbox_MapView_mapbox_scaleBarPrimaryColor, Color.BLACK),
        secondaryColor = typedArray.getColor(R.styleable.mapbox_MapView_mapbox_scaleBarSecondaryColor, Color.WHITE),
        borderWidth = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_scaleBarBorderWidth, 2f * pixelRatio),
        height = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_scaleBarHeight, 2f * pixelRatio),
        textBarMargin = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_scaleBarTextBarMargin, 8f * pixelRatio),
        textBorderWidth = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_scaleBarTextBorderWidth, 2f * pixelRatio),
        textSize = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_scaleBarTextSize, 8f * pixelRatio),
        isMetricUnits = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_scaleBarIsMetricUnits, LocaleUnitResolver.isMetricSystem),
        refreshInterval = typedArray.getInt(R.styleable.mapbox_MapView_mapbox_scaleBarRefreshInterval, 15).toLong(),
        showTextBorder = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_scaleBarShowTextBorder, true),
        ratio = typedArray.getFloat(R.styleable.mapbox_MapView_mapbox_scaleBarRatio, 0.5f),
      )
    } finally {
      typedArray.recycle()
    }
  }
}

// End of generated file.