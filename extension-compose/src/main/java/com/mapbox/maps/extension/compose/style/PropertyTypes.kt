package com.mapbox.maps.extension.compose.style

import android.util.Range
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.style.internal.ComposeTypeUtils
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.utils.ColorUtils

/**
 * Defines the color used by the Maps render engine.
 * It can be either [Color] or an [Expression].
 *
 * @param value a value representing the color. See [Color](https://docs.mapbox.com/style-spec/reference/types/#color).
 */
@Immutable
@MapboxExperimental
public data class ColorValue(public val value: Value) {
  /**
   * Construct the Color with [Color].
   */
  public constructor(value: Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the Color with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * The [Color] represented by [value] or `null` if the stored [value] represents null is not a color.
   */
  public val colorOrNull: Color?
    get() {
      return if (value.contents is String) {
        ColorUtils.rgbaToColor(value.contents as String)?.let { colorInt -> Color(colorInt) }
      } else if (value is Expression) {
        ColorUtils.rgbaExpressionToColorInt(value)?.let { colorInt -> Color(colorInt) }
      } else {
        null
      }
    }

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * [ColorValue]'s companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [default], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    internal val INITIAL: ColorValue = ColorValue(Value.valueOf("ColorValue.INITIAL"))

    /**
     * Default value for [ColorValue], setting [default] will result in setting the property value
     * defined by the rendering engine.
     */
    public val default: ColorValue = ColorValue(Value.nullValue())
  }
}

/**
 * Defines a Number primitive that can accommodate a [Double].
 *
 * @param value a value representing a number. See [Number](https://docs.mapbox.com/style-spec/reference/types/#number)
 */
@Immutable
@MapboxExperimental
public data class DoubleValue(public val value: Value) {
  /**
   * Create a [DoubleValue] that contains double [value].
   *
   * @param value the [Double] to store
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the primitive with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * The [Double] represented by [value] or `null` if the stored [Value] is not a [Double].
   */
  public val doubleOrNull: Double?
    get() = value.contents as? Double

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * [DoubleValue]'s companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [default], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    internal val INITIAL: DoubleValue = DoubleValue(Value("DoubleValue.INITIAL"))

    /**
     * Default value for [DoubleValue], setting [default] will result in setting the property value
     * defined by the rendering engine.
     */
    public val default: DoubleValue = DoubleValue(Value.nullValue())
  }
}

/**
 * Defines a primitive that can accommodate a range of two [Double]. Usually defined by a lower and upper limit.
 *
 * @param value a value representing an array of two numbers. See [Number](https://docs.mapbox.com/style-spec/reference/types/#number)
 */
public data class RangeDoubleValue(public val value: Value) {

  /**
   * Create a [RangeDoubleValue] that contains a list of [Double] that represent a range.
   *
   * @param lower the lower limit [Double] to store
   * @param upper the upper limit [Double] to store
   */
  public constructor(lower: Double, upper: Double) : this(ComposeTypeUtils.wrapToValue(listOf(lower, upper)))

  /**
   * Construct the primitive with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(range: Range<Double>) : this(range.lower, range.upper)
  /**
   * Construct the Color with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * The [Range] represented by [value] or `null` if the stored [Value] is not a [Range].
   */
  public val rangeOrNull: Range<Double>?
    get() {
      if (value.contents is ArrayList<*>) {
        val contents = value.contents as ArrayList<*>
        // Handle value constructed with `Expression.array(...)`
        val idx = if (contents.firstOrNull() == "array") 1 else 0
        if (contents.size == idx + 2) {
          val lower = (contents[idx] as? Value)?.contents as? Double
          val upper = (contents[idx + 1] as? Value)?.contents as? Double
          if (upper != null && lower != null) {
            try {
              return Range(lower, upper)
            } catch (_: IllegalArgumentException) {
            }
          }
        }
        return null
      } else {
        return null
      }
    }

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * [RangeDoubleValue]'s companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [default], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    internal val INITIAL: RangeDoubleValue = RangeDoubleValue(Value.valueOf("RangeDoubleValue.INITIAL"))

    /**
     * Default value for [RangeDoubleValue], setting [default] will result in setting the property
     * value defined by the rendering engine.
     */
    public val default: RangeDoubleValue = RangeDoubleValue(Value.nullValue())
  }
}