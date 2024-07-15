package com.mapbox.maps.extension.compose.style

import android.util.Range
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
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
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: ColorValue = ColorValue(Value.valueOf("ColorValue.INITIAL"))

    /**
     * Default value for [ColorValue], setting [DEFAULT] will result in setting the property value
     * defined by the rendering engine.
     */
    @JvmField
    public val DEFAULT: ColorValue = ColorValue(Value.nullValue())
  }
}

/**
 * Defines a Number primitive that can accommodate a [Double].
 *
 * @param value a value representing a number. See [Number](https://docs.mapbox.com/style-spec/reference/types/#number)
 */
@Immutable
public data class DoubleValue(public val value: Value) {
  /**
   * Create a [DoubleValue] that contains finite double [value].
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
    get() = (value.contents as? Number)?.toDouble()

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
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: DoubleValue = DoubleValue(Value("DoubleValue.INITIAL"))

    /**
     * Default value for [DoubleValue], setting [DEFAULT] will result in setting the property value
     * defined by the rendering engine.
     */
    @JvmField
    public val DEFAULT: DoubleValue = DoubleValue(Value.nullValue())
  }
}

/**
 * Defines a primitive that can accommodate a range of two [Double]. Usually defined by a lower and upper limit.
 *
 * @param value a value representing an array of two numbers. See [Number](https://docs.mapbox.com/style-spec/reference/types/#number)
 */
public data class DoubleRangeValue(public val value: Value) {

  /**
   * Create a [DoubleRangeValue] that contains a list of [Double] that represent a range.
   *
   * @param lower the lower limit [Double] to store
   * @param upper the upper limit [Double] to store
   */
  public constructor(lower: Double, upper: Double) : this(
    ComposeTypeUtils.wrapToValue(listOf(lower, upper))
  )

  /**
   * Construct the primitive with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(range: Range<Double>) : this(range.lower, range.upper)

  /**
   * Construct the primitive with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
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
   * [DoubleRangeValue]'s companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: DoubleRangeValue = DoubleRangeValue(Value.valueOf("DoubleRangeValue.INITIAL"))

    /**
     * Default value for [DoubleRangeValue], setting [DEFAULT] will result in setting the property
     * value defined by the rendering engine.
     */
    @JvmField
    public val DEFAULT: DoubleRangeValue = DoubleRangeValue(Value.nullValue())
  }
}

/**
 * Defines a Number primitive that can accommodate a [Long].
 *
 * @param value a value representing a number. See [Number](https://docs.mapbox.com/style-spec/reference/types/#number)
 */
@Immutable
public data class LongValue(public val value: Value) {
  /**
   * Create a [LongValue] that contains long [value].
   *
   * @param value the [Long] to store
   */
  public constructor(value: Long) : this(ComposeTypeUtils.wrapToValue(value))

  /**
   * Construct the primitive with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * The [Long] represented by [value] or `null` if the stored [Value] is not a [Long].
   */
  public val longOrNull: Long?
    get() = (value.contents as? Number)?.toLong()

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * [LongValue]'s companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: LongValue = LongValue(Value("LongValue.INITIAL"))

    /**
     * Default value for [LongValue], setting [DEFAULT] will result in setting the property value
     * defined by the rendering engine.
     */
    @JvmField
    public val DEFAULT: LongValue = LongValue(Value.nullValue())
  }
}

/**
 * Defines a Boolean primitive that can accommodate a [Boolean]. See [Boolean](https://docs.mapbox.com/style-spec/reference/types/#boolean).
 *
 * @param value a value representing a boolean.
 */
@Immutable
public data class BooleanValue(public val value: Value) {
  /**
   * Create a [BooleanValue] that contains boolean [value].
   *
   * @param value the [Boolean] to store
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))

  /**
   * Construct the primitive with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * The [Boolean] represented by [value] or `null` if the stored [Value] is not a [Boolean].
   */
  public val booleanOrNull: Boolean?
    get() = value.contents as? Boolean

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * [BooleanValue]'s companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: BooleanValue = BooleanValue(Value("BooleanValue.INITIAL"))

    /**
     * Default value for [BooleanValue], setting [DEFAULT] will result in setting the property value
     * defined by the rendering engine.
     */
    @JvmField
    public val DEFAULT: BooleanValue = BooleanValue(Value.nullValue())
  }
}

/**
 * Defines a String primitive that can accommodate a [String]. See [String](https://docs.mapbox.com/style-spec/reference/types/#string).
 *
 * @param value a value representing a string.
 */
@Immutable
public data class StringValue(public val value: Value) {
  /**
   * Create a [StringValue] that contains string [value].
   *
   * @param value the [StringValue] to store
   */
  public constructor(value: String) : this(ComposeTypeUtils.wrapToValue(value))

  /**
   * Construct the primitive with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * The [String] represented by [value] or `null` if the stored [Value] is not a [String].
   */
  public val stringOrNull: String?
    get() = value.contents as? String

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * [StringValue]'s companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: StringValue = StringValue(Value("StringValue.INITIAL"))

    /**
     * Default value for [StringValue], setting [DEFAULT] will result in setting the property value
     * defined by the rendering engine.
     */
    @JvmField
    public val DEFAULT: StringValue = StringValue(Value.nullValue())
  }
}

/**
 * Defines a list of String primitive that can accommodate a list of [String].
 *
 * @param value a value representing a string.
 */
@Immutable
public data class StringListValue(public val value: Value) {
  /**
   * Create a [StringListValue] that contains string [value].
   *
   * @param value the [StringListValue] to store
   */
  public constructor(vararg value: String) : this(ComposeTypeUtils.wrapToValue(value))

  /**
   * Create a [StringListValue] that contains string [value].
   *
   * @param value the [StringListValue] to store
   */
  public constructor(value: List<String>) : this(ComposeTypeUtils.wrapToValue(value))

  /**
   * Construct the primitive with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * The list of [String] represented by [value] or `null` if the stored [Value] is not a list of [String].
   */
  public val stringListOrNull: List<String>?
    get() {
      if (value.contents is ArrayList<*>) {
        val contents = value.contents as ArrayList<*>
        // Handle value constructed with `Expression.array(...)`
        val idx = if (contents.firstOrNull() == "array") 1 else 0
        if (contents.size > idx) {
          val mutableList = mutableListOf<String>()
          contents.subList(idx, contents.size).forEach { content ->
            ((content as? Value)?.contents as? String)?.let {
              mutableList.add(it)
            } ?: return null
          }
          return mutableList.toList()
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
   * [StringValue]'s companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: StringListValue = StringListValue(Value("StringListValue.INITIAL"))

    /**
     * Default value for [StringListValue], setting [DEFAULT] will result in setting the property value
     * defined by the rendering engine.
     */
    @JvmField
    public val DEFAULT: StringListValue = StringListValue(Value.nullValue())
  }
}

/**
 * Defines a primitive that can accommodate a list of [Double].
 *
 * @param value a value representing an array of numbers. See [Number](https://docs.mapbox.com/style-spec/reference/types/#number)
 */
public data class DoubleListValue(public val value: Value) {
  /**
   * Create a [DoubleListValue] from a list of [Double].
   *
   * @param value the list of [Double] to store
   */
  public constructor(vararg value: Double) : this(ComposeTypeUtils.wrapToValue(value))

  /**
   * Create a [DoubleListValue] from a list of [Double].
   *
   * @param value the list of [Double] to store
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))

  /**
   * Construct the Color with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * The list of [Double] represented by [value] or `null` if the stored [Value] is not a list of [Double].
   */
  public val doubleListOrNull: List<Double>?
    get() {
      if (value.contents is ArrayList<*>) {
        val contents = value.contents as ArrayList<*>
        // Handle value constructed with `Expression.array(...)`
        val idx = if (contents.firstOrNull() == "array") 1 else 0
        if (contents.size > idx) {
          val mutableList = mutableListOf<Double>()
          contents.subList(idx, contents.size).forEach { content ->
            ((content as? Value)?.contents as? Double)?.let {
              mutableList.add(it)
            } ?: return null
          }
          return mutableList.toList()
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
   * [DoubleListValue]'s companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: DoubleListValue = DoubleListValue(Value.valueOf("DoubleListValue.INITIAL"))

    /**
     * Default value for [DoubleListValue], setting [DEFAULT] will result in setting the property
     * value defined by the rendering engine.
     */
    @JvmField
    public val DEFAULT: DoubleListValue = DoubleListValue(Value.nullValue())
  }
}

/**
 * Defines the timing for the interpolation between a transitionable style layer property's previous value and new value.
 *
 * @param value the transition wrapped in [Value] to be used with native renderer.
 */
public data class Transition internal constructor(public val value: Value) {

  /**
   * Construct the [Transition] with duration and delay.
   */
  public constructor(durationMillis: Long = 0L, delayMillis: Long = 0L) : this(
    Value(
      hashMapOf(
        "delay" to Value(delayMillis),
        "duration" to Value(durationMillis)
      )
    )
  )

  /**
   * Get the delay of the [Transition] in milliseconds.
   */
  @Suppress("UNCHECKED_CAST")
  public val delayMillis: Long
    get() = (value.contents as HashMap<String, Value>)["delay"]!!.contents as Long

  /**
   * Get the duration of the [Transition] in milliseconds.
   */
  @Suppress("UNCHECKED_CAST")
  public val durationMillis: Long
    get() = (value.contents as HashMap<String, Value>)["duration"]!!.contents as Long

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: Transition = Transition(Value.valueOf("Transition.INITIAL"))

    /**
     * Default value for [Transition], setting default will result in restoring the default transition defined in the rendering engine.
     */
    @JvmField
    public val DEFAULT: Transition = Transition(Value.nullValue())
  }
}

/**
 * Defines a primitive that can accommodate a list of [com.mapbox.geojson.Point]s.
 *
 * @param value a value representing an array of coordinates. See [Array](https://docs.mapbox.com/style-spec/reference/types/#array)
 */
public data class PointListValue(public val value: Value) {

  /**
   * Create a [PointListValue] that contains a list of [Point]s.
   *
   * @param point the [Point] to store
   */
  public constructor(vararg point: Point) : this(
    point.map { listOf(it.longitude(), it.latitude()) }
  )

  /**
   * Create a [PointListValue] that contains a list of longitude, latitude pairs .
   *
   * @param point the longitude, latitude pair to store
   */
  public constructor(vararg point: Pair<Double, Double>) : this(point.map { listOf(it.first, it.second) })

  /**
   * Create a [PointListValue] that contains a list of pairs of longitude, latitude as list.
   *
   * @param points the list of points to store
   */
  public constructor(points: List<List<Double>>) : this(ComposeTypeUtils.wrapToValue(points))

  /**
   * Construct the primitive with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * The list of [Point] represented by [value] or `null` if the stored [Value] is not a a list of [Point].
   */
  public val pointsOrNull: List<Point>?
    get() {
      @Suppress("UNCHECKED_CAST")
      return (value.contents as? List<Value>)?.map { innerList ->
        val innerListContents = innerList.contents
        if (innerListContents is List<*> && innerListContents.size == 2) {
          val longitudeValue = (innerListContents[0] as? Value)?.contents
          val latitudeValue = (innerListContents[1] as? Value)?.contents
          if (longitudeValue is Double && latitudeValue is Double) {
            Point.fromLngLat(longitudeValue, latitudeValue)
          } else {
            return null
          }
        } else {
          return null
        }
      }
    }

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * [PointListValue]'s companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom Point to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: PointListValue = PointListValue(Value.valueOf("PointListValue.INITIAL"))

    /**
     * Default value for [PointListValue], setting [DEFAULT] will result in setting the property
     * value defined by the rendering engine.
     */
    @JvmField
    public val DEFAULT: PointListValue = PointListValue(Value.nullValue())
  }
}