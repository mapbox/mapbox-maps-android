package com.mapbox.maps.extension.style.utils

import android.graphics.Color
import android.util.Log
import androidx.annotation.ColorInt
import com.mapbox.maps.extension.style.expressions.dsl.generated.rgba
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.utils.ColorUtils.formatAlpha
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Color utility class.
 */
object ColorUtils {
  /**
   * Convert an `rgba(...)` or `rgb(...)` string to a [Color] int.
   *
   *
   * R, G, B color components have to be in the `[0-255]` range, while alpha has to be in the `[0.0-1.0]` range.
   * For example: "rgba(255, 128, 0, 0.7)".
   *
   * @param value the [String] representation of rgba or rgb.
   * @return the int representation
   */
  @ColorInt
  fun rgbaToColor(value: String): Int? {
    // we need to accept R, G and B as float values as well and floor them, as those can come from core
    val c = Pattern.compile(
      "rgba?\\s*\\(\\s*(\\d+\\.?\\d*)\\s*,\\s*(\\d+\\.?\\d*)\\s*,\\s*(\\d+\\.?\\d*)\\s*," +
        "?\\s*(\\d+\\.?\\d*)?\\s*\\)"
    )
    val m = c.matcher(value)
    return matchToColorOrNull(m)
  }

  /**
   * Converts Android color int to "rbga(r, g, b, a)" String equivalent.
   *
   * Alpha value will be converted from 0-255 range to 0-1.
   *
   * @param color Android [Color] int
   * @return [String] `rgba(...)` color
   */
  fun colorToRgbaString(@ColorInt color: Int): String = formatARGB(
    alpha = (color shr 24 and 0xFF) / 255.0,
    red = color shr 16 and 0xFF,
    green = color shr 8 and 0xFF,
    blue = color and 0xFF
  )

  /**
   * Convert an rgba or rgb [Expression] to a [Color] int.
   *
   *
   * R, G, B color components have to be in the `[0-255]` range, while alpha has to be in the `[0.0-1.0]` range.
   * For example: "[rgba, 1, 1, 1, 0.7]".
   *
   * @param value the Expression representation of rgba
   * @return the int representation of rgba
   */
  @ColorInt
  fun rgbaExpressionToColorInt(value: Expression): Int? {
    // we need to accept R, G and B as float values as well and floor them, as those can come from core
    val c = Pattern.compile(
      "\\[?\\s*rgba?\\s*,?\\s*(\\d+\\.?\\d*)\\s*,\\s*(\\d+\\.?\\d*)\\s*,\\s*(\\d+\\.?\\d*)\\s*,?\\s*(\\d+\\.?\\d*)?\\s*\\]"
    )
    val m = c.matcher(value.toString())
    return matchToColorOrNull(m)
  }

  /**
   * Convert an rgba or rgb [Expression] to a `rgba(...)` [String].
   *
   *
   * R, G, B color components have to be in the `[0-255]` range, while alpha has to be in the `[0.0-1.0]` range.
   * For example: "[rgba, 255, 128, 0, 0.7]".
   *
   * @param value the [Expression] representation of rgba/rgb
   * @return the [String] representation of rgba
   */
  fun rgbaExpressionToColorString(value: Expression): String? {
    // we need to accept R, G and B as float values as well and floor them, as those can come from core
    val c = Pattern.compile(
      "\\[?\\s*rgba?\\s*,?\\s*(\\d+\\.?\\d*)\\s*,\\s*(\\d+\\.?\\d*)\\s*,\\s*(\\d+\\.?\\d*)\\s*,?\\s*(\\d+\\.?\\d*)?\\s*\\]"
    )
    val m = c.matcher(value.toString())
    return if (m.matches() && m.groupCount() == 4) {
      // Alpha is represented as a value from 0.0F to 1.0F.
      // If alpha was not present (i.e. string was "rgb[red, green, blue]") then set it to 1.0F
      val alpha = m.group(4)?.toDouble() ?: 1.0
      formatARGB(
        alpha = alpha,
        red = m.group(1)!!.toFloat().toInt(),
        green = m.group(2)!!.toFloat().toInt(),
        blue = m.group(3)!!.toFloat().toInt()
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
    val alpha = (color shr 24 and 0xFF).toDouble() / 255.0
    return rgba {
      literal((color shr 16 and 0xFF).toLong())
      literal((color shr 8 and 0xFF).toLong())
      literal((color and 0xFF).toLong())
      literal(formatAlpha(alpha).toDouble())
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

  private fun matchToColorOrNull(m: Matcher): Int? {
    // Regardless of rgba or rgb we are always getting 4 groups, just that one will be empty
    return if (m.matches() && m.groupCount() == 4) {
      // Alpha is represented as a value from 0.0F to 1.0F.
      // If alpha was not present (i.e. string was "rgb(red, green, blue)") then set it to 1.0F
      val alphaFloat = m.group(4)?.toFloat() ?: 1.0F
      // Apply rounding, same than in
      // https://cs.android.com/android/platform/superproject/main/+/main:frameworks/base/graphics/java/android/graphics/Color.java;l=1318;drc=956a76ae36cfe0b5808abab81e3ccbc5c1fe3656
      val alphaInt: Int = (alphaFloat * 255F + 0.5F).toInt()
      // Red, green and blue are represented as values from 0 to 255 and they might be float.
      // We don't round them but rather discard the fraction (e.g. 128.789 is converted as 128)
      val red = m.group(1)!!.toFloat().toInt()
      val green = m.group(2)!!.toFloat().toInt()
      val blue = m.group(3)!!.toFloat().toInt()
      Color.argb(alphaInt, red, green, blue)
    } else {
      Log.e(TAG, "Not a valid rgb/rgba value")
      null
    }
  }

  private fun formatARGB(
    alpha: Double,
    red: Int,
    green: Int,
    blue: Int
  ): String {
    val alphaString = formatAlpha(alpha)
    return String.format(
      Locale.US,
      "rgba(%d, %d, %d, %s)",
      red, green, blue, alphaString
    )
  }

  private fun formatAlpha(alpha: Double): String {
    val decimalFormat = NumberFormat.getNumberInstance(Locale.US) as DecimalFormat
    decimalFormat.applyPattern("#.########")
    return decimalFormat.format(alpha)
  }

  private const val TAG = "ColorUtils"
}