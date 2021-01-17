package com.mapbox.maps.extension.style.types

import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.style.expressions.dsl.generated.rgba
import com.mapbox.maps.extension.style.utils.ColorUtils
import java.lang.StringBuilder

/**
 * Represents a string broken into sections annotated with separate formatting options.
 *
 * @see <a href="https://www.mapbox.com/mapbox-gl-js/style-spec/#types-formatted">Style specification</a>
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
     * Construct a [Formatted] object from a HashMap (Returned from the Core SDK).
     */
    fun fromProperty(map: HashMap<*, *>): Formatted {
      val formatted = Formatted()
      val property = map["sections"] as List<*>

      property.forEach { section ->
        val sectionMap = section as HashMap<*, *>
        val text = sectionMap["text"] as String
        val formattedSection = FormattedSection(text)
        sectionMap.keys.forEach { key ->
          when (key) {
            "textColor" -> {
              val colorMap = sectionMap[key] as HashMap<String, Double>
              formattedSection.textColor = ColorUtils.rgbaExpressionToColorString(
                rgba {
                  literal(colorMap["r"]!! * 255)
                  literal(colorMap["g"]!! * 255)
                  literal(colorMap["b"]!! * 255)
                  literal(colorMap["a"]!!)
                }
              )
            }
            "scale" -> formattedSection.fontScale = sectionMap[key] as Double
            "fontStack" -> formattedSection.fontStack = (sectionMap[key] as String).split(',')
          }
        }
        formatted.add(formattedSection)
      }
      return formatted
    }
  }
}

/**
 * DSL function for [Formatted].
 */
fun formatted(block: Formatted.() -> Unit): Formatted = Formatted().apply(block)