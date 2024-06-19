// This file is generated.

package com.mapbox.maps.extension.style.light.generated

import androidx.annotation.ColorInt
import androidx.annotation.UiThread
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.light.Light
import com.mapbox.maps.extension.style.types.LightDsl
import com.mapbox.maps.extension.style.types.StyleTransition
import com.mapbox.maps.extension.style.utils.ColorUtils.colorIntToRgbaExpression
import com.mapbox.maps.extension.style.utils.ColorUtils.rgbaExpressionToColorInt
import com.mapbox.maps.extension.style.utils.ColorUtils.rgbaExpressionToColorString

/**
 * An indirect light affecting all objects in the map adding a constant amount of light on them. It has no explicit direction and cannot cast shadows.
 *
 * Check the [online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#light).
 */
@UiThread
class AmbientLight internal constructor(override val lightId: String) : AmbientLightDslReceiver, Light() {

  /**
   * Color of the ambient light. Default value: "#ffffff".
   */
  val colorAsColorInt: Int?
    /**
     * Color of the ambient light. Default value: "#ffffff".
     *
     * @return color as int
     */
    @ColorInt
    get() {
      colorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }
  /**
   * Color of the ambient light. Default value: "#ffffff".
   *
   * @param color as int
   */
  override fun color(@ColorInt color: Int): AmbientLight = apply {
    val propertyValue = PropertyValue("color", colorIntToRgbaExpression(color))
    setProperty(propertyValue)
  }
  /**
   * Color of the ambient light. Default value: "#ffffff".
   */
  val color: String?
    /**
     * Color of the ambient light. Default value: "#ffffff".
     *
     * @return color as String
     */
    get() {
      colorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }
  /**
   * Color of the ambient light. Default value: "#ffffff".
   *
   * @param color as String
   */
  override fun color(color: String): AmbientLight = apply {
    setProperty(PropertyValue("color", color))
  }

  /**
   * Color of the ambient light. Default value: "#ffffff".
   *
   * This is an Expression representation of "color".
   */
  val colorAsExpression: Expression?
    /**
     * Color of the ambient light. Default value: "#ffffff".
     *
     * Get the Color property as an Expression
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("color")?.let {
        return it
      }
      return null
    }
  /**
   * Color of the ambient light. Default value: "#ffffff".
   *
   * @param color value of color as Expression
   */
  override fun color(color: Expression): AmbientLight = apply {
    val propertyValue = PropertyValue("color", color)
    setProperty(propertyValue)
  }
  /**
   * Color property transition options.
   */
  val colorTransition: StyleTransition?
    /**
     * Get the Color property transition options.
     *
     * @return transition options for color
     */
    get() {
      return getTransitionProperty("color-transition")
    }
  /**
   * Set the Color property transition options.
   *
   * @param options transition options for color
   */
  override fun colorTransition(options: StyleTransition): AmbientLight = apply {
    val propertyValue = PropertyValue("color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [colorTransition].
   */
  override fun colorTransition(block: StyleTransition.Builder.() -> Unit): AmbientLight = apply {
    colorTransition(StyleTransition.Builder().apply(block).build())
  }
  /**
   * A multiplier for the color of the ambient light. Default value: 0.5. Value range: [0, 1]
   */
  val intensity: Double?
    /**
     * A multiplier for the color of the ambient light. Default value: 0.5. Value range: [0, 1]
     *
     * @return intensity as Double
     */
    get() {
      return getPropertyValue("intensity")
    }
  /**
   * A multiplier for the color of the ambient light. Default value: 0.5. Value range: [0, 1]
   *
   * @param intensity as Double
   */
  override fun intensity(intensity: Double): AmbientLight = apply {
    setProperty(PropertyValue("intensity", intensity))
  }

  /**
   * A multiplier for the color of the ambient light. Default value: 0.5. Value range: [0, 1]
   *
   * This is an Expression representation of "intensity".
   */
  val intensityAsExpression: Expression?
    /**
     * A multiplier for the color of the ambient light. Default value: 0.5. Value range: [0, 1]
     *
     * Get the Intensity property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("intensity")?.let {
        return it
      }
      intensity?.let {
        return Expression.literal(it)
      }
      return null
    }
  /**
   * A multiplier for the color of the ambient light. Default value: 0.5. Value range: [0, 1]
   *
   * @param intensity value of intensity as Expression
   */
  override fun intensity(intensity: Expression): AmbientLight = apply {
    val propertyValue = PropertyValue("intensity", intensity)
    setProperty(propertyValue)
  }
  /**
   * Intensity property transition options.
   */
  val intensityTransition: StyleTransition?
    /**
     * Get the Intensity property transition options.
     *
     * @return transition options for intensity
     */
    get() {
      return getTransitionProperty("intensity-transition")
    }
  /**
   * Set the Intensity property transition options.
   *
   * @param options transition options for intensity
   */
  override fun intensityTransition(options: StyleTransition): AmbientLight = apply {
    val propertyValue = PropertyValue("intensity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [intensityTransition].
   */
  override fun intensityTransition(block: StyleTransition.Builder.() -> Unit): AmbientLight = apply {
    intensityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Get the type of this light
   *
   * @return Type of the light as [String]
   */
  override fun getType(): String {
    return "ambient"
  }
}

/**
 * This Interface contains all the functions that will be exposed to Koltin DSL.
 *
 * Separated the DSL receiver class to this interface to avoid IDE code suggestion for
 * property getters.
 */
@LightDsl
interface AmbientLightDslReceiver {

  /**
   * Color of the ambient light. Default value: "#ffffff".
   *
   * @param color as int
   */
  fun color(@ColorInt color: Int): AmbientLight

  /**
   * Color of the ambient light. Default value: "#ffffff".
   *
   * @param color as String
   */
  fun color(color: String = "#ffffff"): AmbientLight

  /**
   * Color of the ambient light. Default value: "#ffffff".
   *
   * @param color value of color as Expression
   */
  fun color(color: Expression): AmbientLight

  /**
   * Set the Color property transition options.
   *
   * @param options transition options for color
   */
  fun colorTransition(options: StyleTransition): AmbientLight

  /**
   * DSL for [colorTransition].
   */
  fun colorTransition(block: StyleTransition.Builder.() -> Unit): AmbientLight

  /**
   * A multiplier for the color of the ambient light. Default value: 0.5. Value range: [0, 1]
   *
   * @param intensity as Double
   */
  fun intensity(intensity: Double = 0.5): AmbientLight

  /**
   * A multiplier for the color of the ambient light. Default value: 0.5. Value range: [0, 1]
   *
   * @param intensity value of intensity as Expression
   */
  fun intensity(intensity: Expression): AmbientLight

  /**
   * Set the Intensity property transition options.
   *
   * @param options transition options for intensity
   */
  fun intensityTransition(options: StyleTransition): AmbientLight

  /**
   * DSL for [intensityTransition].
   */
  fun intensityTransition(block: StyleTransition.Builder.() -> Unit): AmbientLight
}

/**
 * DSL function for creating [ambientLight] instance.
 */
fun ambientLight(id: String = "ambient", block: AmbientLightDslReceiver.() -> Unit): AmbientLight = AmbientLight(id).apply(block)

// End of generated file.