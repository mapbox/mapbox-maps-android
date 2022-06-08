package com.mapbox.maps.extension.style.types

import androidx.annotation.ColorInt
import androidx.annotation.Keep
import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.style.utils.ColorUtils

/**
 * A component of the [Formatted].
 *
 * @param text displayed string
 * @param fontScale scale of the font, setting to null will fall back to style's default settings
 * @param fontStack main and fallback fonts that are a part of the style,
 *                  setting null will fall back to style's default settings.
 *                  The requested font stack has to be a part of the used style.
 *                  For more information see
 *                  [The online documentation]https://www.mapbox.com/help/define-font-stack/).
 * @param textColor text color, setting to null will fall back to style's default settings.
 *                  Value of red, green, blue components must range between 0 and 255,
 *                  an alpha component must range between 0 and 1.
 *
 * For more information see [The online documentation]https://docs.mapbox.com/mapbox-gl-js/style-spec/#types-color).
 */
@Keep
@LayersDsl
data class FormattedSection @JvmOverloads constructor(
  val text: String,
  var fontScale: Double? = null,
  var fontStack: List<String>? = null,
  var textColor: String? = null
) {
  /**
   * [ColorInt] representation for textColor.
   */
  var textColorAsInt: Int
    @ColorInt
    get() {
      textColor?.let {
        ColorUtils.rgbaToColor(it)?.let { colorInt ->
          return colorInt
        }
      }
      throw IllegalStateException("Property textColor is not set.")
    }
    set(@ColorInt value) {
      textColor = ColorUtils.colorToRgbaString(value)
    }

  /**
   * Convert [FormattedSection] to a [Value].
   *
   * @return a value class
   */
  internal fun toValue(): Value {
    val params = HashMap<String, Value>()
    fontScale?.let {
      params["font-scale"] = Value(it)
    }
    fontStack?.let { strings ->
      params["text-font"] = Value(strings.map(::Value))
    }
    textColor?.let {
      params["text-color"] = Value(it)
    }
    return Value(listOf(Value(text), Value(params)))
  }
}