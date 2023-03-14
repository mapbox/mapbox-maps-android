package com.mapbox.maps.extension.style.types

import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.style.expressions.dsl.generated.rgba
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.extension.style.utils.unwrapToTyped
import java.lang.StringBuilder
import java.util.ArrayList

/**
 * Represents a string broken into sections annotated with separate formatting options.
 *
 * @see [Style specification](https://www.mapbox.com/mapbox-gl-js/style-spec/#types-formatted)
 *
 */
@LayersDsl
class Formatted : ArrayList<FormattedSection>() {
  /**
   * Convert Formatted to a Value.
   *
   * @return a value class
   */
  fun toValue(): Value {
    return Value(this.map { it.toValue() })
  }

  /**
   * Get the formatted field's text as a plain String.
   */
  fun getTextAsString(): String {
    val stringBuilder = StringBuilder()
    this.forEach { section ->
      stringBuilder.append(section.text)
    }
    return stringBuilder.toString()
  }

  /**
   * DSL function to add a formatted section to the Formatted.
   */
  fun formattedSection(text: String, block: FormattedSection.() -> Unit) {
    this.add(FormattedSection(text).apply(block))
  }

  /**
   * Add a formatted section to the Formatted.
   */
  fun formattedSection(section: FormattedSection) {
    this.add(section)
  }

  /**
   * Static variables and methods.
   */
  companion object {
    /**
     * Construct a [Formatted] object from a format expression.
     */
    fun fromExpression(expression: Expression): Formatted {
      return fromProperty(expression.unwrapToTyped<ArrayList<Any>>())
    }

    /**
     * Construct a [Formatted] object from a Formatted List (Returned from the Core).
     */
    fun fromProperty(list: ArrayList<*>): Formatted {
      val formatted = Formatted()

      if (list.removeFirst() == "format") {
        list.forEachIndexed { index, element ->
          when (element) {
            is String -> {
              val formattedSection = FormattedSection(element)
              val optionsMap = list[index + 1] as HashMap<*, *>
              optionsMap.keys.forEach { key ->
                when (key) {
                  "text-color" -> {
                    val colorExpressionList = optionsMap[key] as ArrayList<*>
                    if (colorExpressionList.removeFirst() == "rgba") {
                      formattedSection.textColor = ColorUtils.rgbaExpressionToColorString(
                        rgba {
                          literal(colorExpressionList[0] as Double)
                          literal(colorExpressionList[1] as Double)
                          literal(colorExpressionList[2] as Double)
                          literal(colorExpressionList[3] as Double)
                        }
                      )
                    }
                  }
                  "font-scale" -> formattedSection.fontScale = optionsMap[key] as Double
                  "text-font" -> {
                    val textFontExpressionList = optionsMap[key] as ArrayList<*>
                    if (textFontExpressionList.removeFirst() == "literal") {
                      formattedSection.fontStack =
                        textFontExpressionList.last() as ArrayList<String>
                    }
                  }
                }
              }
              formatted.add(formattedSection)
            }
            else -> Unit
          }
        }
      }
      return formatted
    }
  }
}

/**
 * DSL function for [Formatted].
 */
fun formatted(block: Formatted.() -> Unit): Formatted = Formatted().apply(block)