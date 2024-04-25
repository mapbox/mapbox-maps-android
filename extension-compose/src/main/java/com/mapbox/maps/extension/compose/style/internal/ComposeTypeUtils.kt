package com.mapbox.maps.extension.compose.style.internal

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.mapbox.bindgen.Value
import com.mapbox.maps.TileCacheBudgetInMegabytes
import com.mapbox.maps.TileCacheBudgetInTiles
import com.mapbox.maps.extension.style.types.PromoteId
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.extension.style.utils.TypeUtils

internal object ComposeTypeUtils {
  fun wrapToValue(value: Any): Value {
    return when (value) {
      is Color -> Value(ColorUtils.colorToRgbaString(value.toArgb()))
      is TileCacheBudgetInMegabytes -> Value(
        hashMapOf(
          "megabytes" to Value(
            value.size
          )
        )
      )
      is TileCacheBudgetInTiles -> Value(
        hashMapOf(
          "tiles" to Value(
            value.size
          )
        )
      )
      is PromoteId -> with(value) {
        val propertyNameValue = Value(propertyName)
        sourceId?.let {
          Value(hashMapOf(sourceId to propertyNameValue))
        } ?: propertyNameValue
      }
      else -> TypeUtils.wrapToValue(value)
    }
  }
}