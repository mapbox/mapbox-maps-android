package com.mapbox.maps.extension.compose.style.internal

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.extension.style.utils.TypeUtils

internal object ComposeTypeUtils {
  fun wrapToValue(value: Any): Value {
    return when (value) {
      is Color -> Value(ColorUtils.colorToRgbaString(value.toArgb()))
      else -> TypeUtils.wrapToValue(value)
    }
  }
}