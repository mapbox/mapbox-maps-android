// This file is generated.

package com.mapbox.maps.plugin.consolidatedattribution.generated

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import com.mapbox.maps.plugin.ConsolidatedAttributionCollapsedStyle
import com.mapbox.maps.plugin.ConsolidatedAttributionIntroExpansion
import com.mapbox.maps.plugin.ConsolidatedAttributionMaterial
import com.mapbox.maps.plugin.consolidatedattribution.R

/**
 * Utility class for parsing [AttributeSet] to [ConsolidatedAttributionSettings].
 */
internal object ConsolidatedAttributionAttributeParser {
  /**
   * Parse [AttributeSet] to [ConsolidatedAttributionSettings].
   *
   * @param context Context
   * @param attrs AttributionSet
   */
  @OptIn(com.mapbox.maps.MapboxExperimental::class)
  fun parseConsolidatedAttributionSettings(context: Context, attrs: AttributeSet?, pixelRatio: Float = 1.0f): ConsolidatedAttributionSettings {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.mapbox_MapView, 0, 0)
    try {
      return ConsolidatedAttributionSettings {
        enabled = typedArray.getBoolean(R.styleable.mapbox_MapView_mapbox_consolidatedAttributionEnabled, false)
        position = typedArray.getInt(R.styleable.mapbox_MapView_mapbox_consolidatedAttributionGravity, Gravity.BOTTOM or Gravity.START)
        marginLeft = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_consolidatedAttributionMarginLeft, 8f * pixelRatio)
        marginTop = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_consolidatedAttributionMarginTop, 8f * pixelRatio)
        marginRight = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_consolidatedAttributionMarginRight, 8f * pixelRatio)
        marginBottom = typedArray.getDimension(R.styleable.mapbox_MapView_mapbox_consolidatedAttributionMarginBottom, 8f * pixelRatio)
        collapsedStyle = ConsolidatedAttributionCollapsedStyle.values()[typedArray.getInt(R.styleable.mapbox_MapView_mapbox_consolidatedAttributionCollapsedStyle, ConsolidatedAttributionCollapsedStyle.ICON_AND_WORDMARK.ordinal)]
        material = ConsolidatedAttributionMaterial.values()[typedArray.getInt(R.styleable.mapbox_MapView_mapbox_consolidatedAttributionMaterial, ConsolidatedAttributionMaterial.SOLID.ordinal)]
        introExpansion = ConsolidatedAttributionIntroExpansion.values()[typedArray.getInt(R.styleable.mapbox_MapView_mapbox_consolidatedAttributionIntroExpansion, ConsolidatedAttributionIntroExpansion.ONCE.ordinal)]
        tintColor = typedArray.getColor(R.styleable.mapbox_MapView_mapbox_consolidatedAttributionTintColor, Color.parseColor("#00000000"))
      }
    } finally {
      typedArray.recycle()
    }
  }
}

// End of generated file.