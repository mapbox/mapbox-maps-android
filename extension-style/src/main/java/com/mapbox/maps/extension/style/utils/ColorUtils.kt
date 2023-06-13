package com.mapbox.maps.extension.style.utils

import android.graphics.Color
import android.util.Log
import androidx.annotation.ColorInt
import com.mapbox.maps.extension.style.expressions.dsl.generated.rgba
import com.mapbox.maps.extension.style.expressions.generated.Expression
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Color utility class.
 */
object ColorUtils {
  /**
   * Convert an rgba string to a Color int.
   *
   *
   * R, G, B color components have to be in the [0-255] range, while alpha has to be in the [0.0-1.0] range.
   * For example: "rgba(255, 128, 0, 0.7)".
   *
   * @param value the String representation of rgba
   * @return the int representation of rgba
   */
  @ColorInt
  fun rgbaToColor(value: String): Int? { // we need to accept and floor float values as well, as those can come from core
    val c = Pattern.compile(
      "rgba?\\s*\\(\\s*(\\d+\\.?\\d*)\\s*,\\s*(\\d+\\.?\\d*)\\s*,\\s*(\\d+\\.?\\d*)\\s*," +
        "?\\s*(\\d+\\.?\\d*)?\\s*\\)"
    )
    val m = c.matcher(value)
    return if (m.matches() && m.groupCount() == 3) {
      Color.rgb(
        m.group(1)!!.toFloat().toInt(), m.group(2)!!.toFloat().toInt(),
        m.group(3)!!.toFloat().toInt()
      )
    } else if (m.matches() && m.groupCount() == 4) {
      Color.argb(
        (m.group(4)!!.toFloat() * 255).toInt(), m.group(1)!!.toFloat().toInt(),
        m.group(2)!!.toFloat().toInt(), m.group(3)!!.toFloat().toInt()
      )
    } else {
      Log.e(TAG, "Not a valid rgb/rgba value")
      null
    }
  }

  /**
   * Converts Android color int to "rbga(r, g, b, a)" String equivalent.
   *
   *
   * Alpha value will be converted from 0-255 range to 0-1.
   *
   *
   * @param color Android color int
   * @return String rgba color
   */
  fun colorToRgbaString(@ColorInt color: Int): String {
    val numberFormat =
      NumberFormat.getNumberInstance(Locale.US)
    val decimalFormat = numberFormat as DecimalFormat
    decimalFormat.applyPattern("#.###")
    val alpha =
      decimalFormat.format((color shr 24 and 0xFF).toFloat() / 255.0f.toDouble())
    return String.format(
      Locale.US, "rgba(%d, %d, %d, %s)",
      color shr 16 and 0xFF, color shr 8 and 0xFF, color and 0xFF, alpha
    )
  }

  /**
   * Convert an rgba Expression to a Color int.
   *
   *
   * R, G, B color components have to be in the [0-255] range, while alpha has to be in the [0.0-1.0] range.
   * For example: "rgba(255, 128, 0, 0.7)".
   *
   * @param value the Expression representation of rgba
   * @return the int representation of rgba
   */
  @ColorInt
  fun rgbaExpressionToColorInt(value: Expression): Int? { // we need to accept and floor float values as well, as those can come from core
    val c = Pattern.compile(
      "\\[?\\s*rgba?\\s*,?\\s*(\\d+\\.?\\d*)\\s*,\\s*(\\d+\\.?\\d*)\\s*,\\s*(\\d+\\.?\\d*)\\s*,?\\s*(\\d+\\.?\\d*)?\\s*\\]"
    )
    val m = c.matcher(value.toString())
    return if (m.matches() && m.groupCount() == 3) {
      Color.rgb(
        m.group(1)!!.toFloat().toInt(), m.group(2)!!.toFloat().toInt(),
        m.group(3)!!.toFloat().toInt()
      )
    } else if (m.matches() && m.groupCount() == 4) {
      Color.argb(
        (m.group(4)!!.toFloat() * 255).toInt(), m.group(1)!!.toFloat().toInt(),
        m.group(2)!!.toFloat().toInt(), m.group(3)!!.toFloat().toInt()
      )
    } else {
      Log.e(TAG, "Not a valid rgb/rgba value")
      null
    }
  }

  /**
   * Convert an rgba Expression to a Color String.
   *
   *
   * R, G, B color components have to be in the [0-255] range, while alpha has to be in the [0.0-1.0] range.
   * For example: "rgba(255, 128, 0, 0.7)".
   *
   * @param value the Expression representation of rgba
   * @return the String representation of rgba
   */
  fun rgbaExpressionToColorString(value: Expression): String? {
    // we need to accept and floor float values as well, as those can come from core
    val c = Pattern.compile(
      "\\[?\\s*rgba?\\s*,?\\s*(\\d+\\.?\\d*)\\s*,\\s*(\\d+\\.?\\d*)\\s*,\\s*(\\d+\\.?\\d*)\\s*,?\\s*(\\d+\\.?\\d*)?\\s*\\]"
    )
    val m = c.matcher(value.toString())
    return if (m.matches() && m.groupCount() == 3) {
      String.format(
        Locale.US, "rgba(%d, %d, %d, 1)",
        m.group(1)!!.toFloat().toInt(),
        m.group(2)!!.toFloat().toInt(),
        m.group(3)!!.toFloat().toInt()
      )
    } else if (m.matches() && m.groupCount() == 4) {
      val numberFormat =
        NumberFormat.getNumberInstance(Locale.US)
      val decimalFormat = numberFormat as DecimalFormat
      decimalFormat.applyPattern("#.###")
      val alpha =
        decimalFormat.format(m.group(4)!!.toFloat())
      String.format(
        Locale.US, "rgba(%d, %d, %d, %s)",
        m.group(1)!!.toFloat().toInt(),
        m.group(2)!!.toFloat().toInt(),
        m.group(3)!!.toFloat().toInt(),
        alpha
      )
    } else {
      Log.e(TAG, "Not a valid rgb/rgba value")
      null
    }
  }

  /**
   * Converts Android color int to "rbga(r, g, b, a)" Expression equivalent.
   *
   *
   * Alpha value will be converted from 0-255 range to 0-1.
   *
   *
   * @param color Android color int
   * @return Expression of rgba color
   */
  fun colorIntToRgbaExpression(@ColorInt color: Int): Expression {
    val numberFormat =
      NumberFormat.getNumberInstance(Locale.US)
    val decimalFormat = numberFormat as DecimalFormat
    decimalFormat.applyPattern("#.###")
    val alpha =
      decimalFormat.format((color shr 24 and 0xFF).toFloat() / 255.0f.toDouble())
    return rgba {
      literal((color shr 16 and 0xFF).toLong())
      literal((color shr 8 and 0xFF).toLong())
      literal((color and 0xFF).toLong())
      literal(alpha.toDouble())
    }
  }

  /**
   * Converts Android color int to rgba float array.
   *
   *
   * Returned RGB values range from 0 to 255.
   * Alpha value ranges from 0-1.
   *
   *
   * @param color Android color int
   * @return float rgba array, rgb values range from 0-255, alpha from 0-1
   */
  fun colorToRgbaArray(@ColorInt color: Int): FloatArray {
    return floatArrayOf(
      (color shr 16 and 0xFF.toFloat().toInt()).toFloat(), // r (0-255)
      (color shr 8 and 0xFF.toFloat().toInt()).toFloat(), // g (0-255)
      (color and 0xFF.toFloat().toInt()).toFloat(), // b (0-255)
      (color shr 24 and 0xFF) / 255.0f // a (0-1)
    )
  }

  /**
   * Converts Android color int to GL rgba float array.
   *
   *
   * Returned values range from 0-1.
   *
   *
   * @param color Android color int
   * @return float rgba array, values range from 0 to 1
   */
  fun colorToGlRgbaArray(@ColorInt color: Int): FloatArray {
    return floatArrayOf(
      (color shr 16 and 0xFF) / 255.0f, // r (0-1)
      (color shr 8 and 0xFF) / 255.0f, // g (0-1)
      (color and 0xFF) / 255.0f, // b (0-1)
      (color shr 24 and 0xFF) / 255.0f // a (0-1)
    )
  }

  private const val TAG = "ColorUtils"
}