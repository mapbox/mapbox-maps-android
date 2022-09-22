package com.mapbox.maps.extension.style.layers.properties

import android.util.Log
import androidx.annotation.ColorInt
import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.types.StyleTransition
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.extension.style.utils.TypeUtils

/**
 * Properties for Style
 *
 * @param propertyName the property name
 * @param propertyValue the property value
 */
open class PropertyValue<T> internal constructor(val propertyName: String, val propertyValue: T) {

  /**
   * The [Value] representation of the property.
   */
  val value: Value = try {
    TypeUtils.wrapToValue(propertyValue as Any)
  } catch (e: IllegalArgumentException) {
    throw IllegalArgumentException("Incorrect property value for $propertyName: ${e.message}", e.cause)
  }

  /**
   * Returns if this is a expression.
   *
   * @return true if this is a expression, false if not
   */
  val isExpression: Boolean
    get() = propertyValue is Expression

  /**
   * Returns if this is a transition.
   *
   * @return true if this is a transition, false if not
   */
  val isTransition: Boolean
    get() = propertyValue is StyleTransition

  /**
   * Returns if this is a constant value.
   *
   * @return true if this is a constant value, false if not
   */
  val isValue: Boolean
    get() = !isExpression && !isTransition

  /**
   * Get the value of the property.
   *
   * @return the property value
   */
  fun getValue(): T? {
    return if (isValue) { // noinspection unchecked
      propertyValue
    } else {
      null
    }
  }

  /**
   * Get the color int value of the property if the value is a color.
   *
   * @return the color int value of the property, null if not a color value
   */
  @get:ColorInt
  val colorInt: Int?
    get() {
      if (!isValue || propertyValue !is String) {
        Log.e(
          TAG,
          String.format(
            "%s is not a String value and can not be converted to a color it",
            propertyName
          )
        )
        return null
      }
      return try {
        ColorUtils.rgbaToColor(propertyValue as String)
      } catch (ex: Exception) {
        Log.e(
          TAG,
          java.lang.String.format(
            "%s could not be converted to a Color int: %s",
            propertyName,
            ex.message
          )
        )
        null
      }
    }

  /**
   * Get the string representation of a property value.
   *
   * @return the string representation
   */
  override fun toString(): String {
    return String.format("%s: %s", propertyName, propertyValue)
  }

  /**
   * Static variables and methods.
   */
  companion object {
    private const val TAG = "Mbgl-PropertyValue"
  }
}