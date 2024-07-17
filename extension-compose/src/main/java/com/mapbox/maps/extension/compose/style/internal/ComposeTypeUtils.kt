package com.mapbox.maps.extension.compose.style.internal

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.TileCacheBudgetInMegabytes
import com.mapbox.maps.TileCacheBudgetInTiles
import com.mapbox.maps.extension.compose.style.layers.generated.ClipLayerTypes
import com.mapbox.maps.extension.compose.style.layers.generated.TextVariableAnchor
import com.mapbox.maps.extension.compose.style.layers.generated.TextWritingMode
import com.mapbox.maps.extension.style.types.PromoteId
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.extension.style.utils.TypeUtils

internal object ComposeTypeUtils {
  @OptIn(MapboxExperimental::class)
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
      is TextWritingMode -> {
        value.value
      }
      is TextVariableAnchor -> {
        value.value
      }
      is ClipLayerTypes -> {
        value.value
      }
      is List<*> -> {
        val valueArray = value.map { it?.let { wrapToValue(it) } }
        Value(valueArray)
      }
      else -> TypeUtils.wrapToValue(value)
    }
  }
}