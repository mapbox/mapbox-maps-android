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
 * A light that has a direction and is located at infinite, so its rays are parallel. Simulates the sun light and it can cast shadows
 *
 * Check the [online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#light).
 */
@UiThread
class DirectionalLight internal constructor(override val lightId: String) : DirectionalLightDslReceiver, Light() {

  /**
   * Enable/Disable shadow casting for this light
   */
  val castShadows: Boolean?
    /**
     * Enable/Disable shadow casting for this light
     *
     * @return castShadows as Boolean
     */
    get() {
      return getPropertyValue("cast-shadows")
    }
  /**
   * Enable/Disable shadow casting for this light
   *
   * @param castShadows as Boolean
   */
  override fun castShadows(castShadows: Boolean): DirectionalLight = apply {
    setProperty(PropertyValue("cast-shadows", castShadows))
  }

  /**
   * Enable/Disable shadow casting for this light
   *
   * This is an Expression representation of "cast-shadows".
   */
  val castShadowsAsExpression: Expression?
    /**
     * Enable/Disable shadow casting for this light
     *
     * Get the CastShadows property as an Expression
     *
     * @return Boolean
     */
    get() {
      getPropertyValue<Expression>("cast-shadows")?.let {
        return it
      }
      castShadows?.let {
        return Expression.literal(it)
      }
      return null
    }
  /**
   * Enable/Disable shadow casting for this light
   *
   * @param castShadows value of castShadows as Expression
   */
  override fun castShadows(castShadows: Expression): DirectionalLight = apply {
    val propertyValue = PropertyValue("cast-shadows", castShadows)
    setProperty(propertyValue)
  }
  /**
   * Color of the directional light.
   */
  val colorAsColorInt: Int?
    /**
     * Color of the directional light.
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
   * Color of the directional light.
   *
   * @param color as int
   */
  override fun color(@ColorInt color: Int): DirectionalLight = apply {
    val propertyValue = PropertyValue("color", colorIntToRgbaExpression(color))
    setProperty(propertyValue)
  }
  /**
   * Color of the directional light.
   */
  val color: String?
    /**
     * Color of the directional light.
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
   * Color of the directional light.
   *
   * @param color as String
   */
  override fun color(color: String): DirectionalLight = apply {
    setProperty(PropertyValue("color", color))
  }

  /**
   * Color of the directional light.
   *
   * This is an Expression representation of "color".
   */
  val colorAsExpression: Expression?
    /**
     * Color of the directional light.
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
   * Color of the directional light.
   *
   * @param color value of color as Expression
   */
  override fun color(color: Expression): DirectionalLight = apply {
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
  override fun colorTransition(options: StyleTransition): DirectionalLight = apply {
    val propertyValue = PropertyValue("color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [colorTransition].
   */
  override fun colorTransition(block: StyleTransition.Builder.() -> Unit): DirectionalLight = apply {
    colorTransition(StyleTransition.Builder().apply(block).build())
  }
  /**
   * Direction of the light source specified as [a azimuthal angle, p polar angle] where a indicates the azimuthal angle of the light relative to north (in degrees and proceeding clockwise), and p indicates polar angle of the light (from 0 degree, directly above, to 180 degree, directly below).
   */
  val direction: List<Double>?
    /**
     * Direction of the light source specified as [a azimuthal angle, p polar angle] where a indicates the azimuthal angle of the light relative to north (in degrees and proceeding clockwise), and p indicates polar angle of the light (from 0 degree, directly above, to 180 degree, directly below).
     *
     * @return direction as List<Double>
     */
    get() {
      return getPropertyValue("direction")
    }
  /**
   * Direction of the light source specified as [a azimuthal angle, p polar angle] where a indicates the azimuthal angle of the light relative to north (in degrees and proceeding clockwise), and p indicates polar angle of the light (from 0 degree, directly above, to 180 degree, directly below).
   *
   * @param direction as List<Double>
   */
  override fun direction(direction: List<Double>): DirectionalLight = apply {
    setProperty(PropertyValue("direction", direction))
  }

  /**
   * Direction of the light source specified as [a azimuthal angle, p polar angle] where a indicates the azimuthal angle of the light relative to north (in degrees and proceeding clockwise), and p indicates polar angle of the light (from 0 degree, directly above, to 180 degree, directly below).
   *
   * This is an Expression representation of "direction".
   */
  val directionAsExpression: Expression?
    /**
     * Direction of the light source specified as [a azimuthal angle, p polar angle] where a indicates the azimuthal angle of the light relative to north (in degrees and proceeding clockwise), and p indicates polar angle of the light (from 0 degree, directly above, to 180 degree, directly below).
     *
     * Get the Direction property as an Expression
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("direction")?.let {
        return it
      }
      direction?.let {
        return Expression.literal(it)
      }
      return null
    }
  /**
   * Direction of the light source specified as [a azimuthal angle, p polar angle] where a indicates the azimuthal angle of the light relative to north (in degrees and proceeding clockwise), and p indicates polar angle of the light (from 0 degree, directly above, to 180 degree, directly below).
   *
   * @param direction value of direction as Expression
   */
  override fun direction(direction: Expression): DirectionalLight = apply {
    val propertyValue = PropertyValue("direction", direction)
    setProperty(propertyValue)
  }
  /**
   * Direction property transition options.
   */
  val directionTransition: StyleTransition?
    /**
     * Get the Direction property transition options.
     *
     * @return transition options for direction
     */
    get() {
      return getTransitionProperty("direction-transition")
    }
  /**
   * Set the Direction property transition options.
   *
   * @param options transition options for direction
   */
  override fun directionTransition(options: StyleTransition): DirectionalLight = apply {
    val propertyValue = PropertyValue("direction-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [directionTransition].
   */
  override fun directionTransition(block: StyleTransition.Builder.() -> Unit): DirectionalLight = apply {
    directionTransition(StyleTransition.Builder().apply(block).build())
  }
  /**
   * A multiplier for the color of the directional light.
   */
  val intensity: Double?
    /**
     * A multiplier for the color of the directional light.
     *
     * @return intensity as Double
     */
    get() {
      return getPropertyValue("intensity")
    }
  /**
   * A multiplier for the color of the directional light.
   *
   * @param intensity as Double
   */
  override fun intensity(intensity: Double): DirectionalLight = apply {
    setProperty(PropertyValue("intensity", intensity))
  }

  /**
   * A multiplier for the color of the directional light.
   *
   * This is an Expression representation of "intensity".
   */
  val intensityAsExpression: Expression?
    /**
     * A multiplier for the color of the directional light.
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
   * A multiplier for the color of the directional light.
   *
   * @param intensity value of intensity as Expression
   */
  override fun intensity(intensity: Expression): DirectionalLight = apply {
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
  override fun intensityTransition(options: StyleTransition): DirectionalLight = apply {
    val propertyValue = PropertyValue("intensity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [intensityTransition].
   */
  override fun intensityTransition(block: StyleTransition.Builder.() -> Unit): DirectionalLight = apply {
    intensityTransition(StyleTransition.Builder().apply(block).build())
  }
  /**
   * Determines the shadow strength, affecting the shadow receiver surfaces final color. Values near 0.0 reduce the shadow contribution to the final color. Values near to 1.0 make occluded surfaces receive almost no directional light. Designed to be used mostly for transitioning between values 0 and 1.
   */
  val shadowIntensity: Double?
    /**
     * Determines the shadow strength, affecting the shadow receiver surfaces final color. Values near 0.0 reduce the shadow contribution to the final color. Values near to 1.0 make occluded surfaces receive almost no directional light. Designed to be used mostly for transitioning between values 0 and 1.
     *
     * @return shadowIntensity as Double
     */
    get() {
      return getPropertyValue("shadow-intensity")
    }
  /**
   * Determines the shadow strength, affecting the shadow receiver surfaces final color. Values near 0.0 reduce the shadow contribution to the final color. Values near to 1.0 make occluded surfaces receive almost no directional light. Designed to be used mostly for transitioning between values 0 and 1.
   *
   * @param shadowIntensity as Double
   */
  override fun shadowIntensity(shadowIntensity: Double): DirectionalLight = apply {
    setProperty(PropertyValue("shadow-intensity", shadowIntensity))
  }

  /**
   * Determines the shadow strength, affecting the shadow receiver surfaces final color. Values near 0.0 reduce the shadow contribution to the final color. Values near to 1.0 make occluded surfaces receive almost no directional light. Designed to be used mostly for transitioning between values 0 and 1.
   *
   * This is an Expression representation of "shadow-intensity".
   */
  val shadowIntensityAsExpression: Expression?
    /**
     * Determines the shadow strength, affecting the shadow receiver surfaces final color. Values near 0.0 reduce the shadow contribution to the final color. Values near to 1.0 make occluded surfaces receive almost no directional light. Designed to be used mostly for transitioning between values 0 and 1.
     *
     * Get the ShadowIntensity property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("shadow-intensity")?.let {
        return it
      }
      shadowIntensity?.let {
        return Expression.literal(it)
      }
      return null
    }
  /**
   * Determines the shadow strength, affecting the shadow receiver surfaces final color. Values near 0.0 reduce the shadow contribution to the final color. Values near to 1.0 make occluded surfaces receive almost no directional light. Designed to be used mostly for transitioning between values 0 and 1.
   *
   * @param shadowIntensity value of shadowIntensity as Expression
   */
  override fun shadowIntensity(shadowIntensity: Expression): DirectionalLight = apply {
    val propertyValue = PropertyValue("shadow-intensity", shadowIntensity)
    setProperty(propertyValue)
  }
  /**
   * ShadowIntensity property transition options.
   */
  val shadowIntensityTransition: StyleTransition?
    /**
     * Get the ShadowIntensity property transition options.
     *
     * @return transition options for shadow-intensity
     */
    get() {
      return getTransitionProperty("shadow-intensity-transition")
    }
  /**
   * Set the ShadowIntensity property transition options.
   *
   * @param options transition options for shadow-intensity
   */
  override fun shadowIntensityTransition(options: StyleTransition): DirectionalLight = apply {
    val propertyValue = PropertyValue("shadow-intensity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [shadowIntensityTransition].
   */
  override fun shadowIntensityTransition(block: StyleTransition.Builder.() -> Unit): DirectionalLight = apply {
    shadowIntensityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Get the type of this light
   *
   * @return Type of the light as [String]
   */
  override fun getType(): String {
    return "directional"
  }
}

/**
 * This Interface contains all the functions that will be exposed to Koltin DSL.
 *
 * Separated the DSL receiver class to this interface to avoid IDE code suggestion for
 * property getters.
 */
@LightDsl
interface DirectionalLightDslReceiver {

  /**
   * Enable/Disable shadow casting for this light
   *
   * @param castShadows as Boolean
   */
  fun castShadows(castShadows: Boolean = false): DirectionalLight

  /**
   * Enable/Disable shadow casting for this light
   *
   * @param castShadows value of castShadows as Expression
   */
  fun castShadows(castShadows: Expression): DirectionalLight

  /**
   * Color of the directional light.
   *
   * @param color as int
   */
  fun color(@ColorInt color: Int): DirectionalLight

  /**
   * Color of the directional light.
   *
   * @param color as String
   */
  fun color(color: String = "#ffffff"): DirectionalLight

  /**
   * Color of the directional light.
   *
   * @param color value of color as Expression
   */
  fun color(color: Expression): DirectionalLight

  /**
   * Set the Color property transition options.
   *
   * @param options transition options for color
   */
  fun colorTransition(options: StyleTransition): DirectionalLight

  /**
   * DSL for [colorTransition].
   */
  fun colorTransition(block: StyleTransition.Builder.() -> Unit): DirectionalLight

  /**
   * Direction of the light source specified as [a azimuthal angle, p polar angle] where a indicates the azimuthal angle of the light relative to north (in degrees and proceeding clockwise), and p indicates polar angle of the light (from 0 degree, directly above, to 180 degree, directly below).
   *
   * @param direction as List<Double>
   */
  fun direction(direction: List<Double> = listOf(210.0, 30.0)): DirectionalLight

  /**
   * Direction of the light source specified as [a azimuthal angle, p polar angle] where a indicates the azimuthal angle of the light relative to north (in degrees and proceeding clockwise), and p indicates polar angle of the light (from 0 degree, directly above, to 180 degree, directly below).
   *
   * @param direction value of direction as Expression
   */
  fun direction(direction: Expression): DirectionalLight

  /**
   * Set the Direction property transition options.
   *
   * @param options transition options for direction
   */
  fun directionTransition(options: StyleTransition): DirectionalLight

  /**
   * DSL for [directionTransition].
   */
  fun directionTransition(block: StyleTransition.Builder.() -> Unit): DirectionalLight

  /**
   * A multiplier for the color of the directional light.
   *
   * @param intensity as Double
   */
  fun intensity(intensity: Double = 0.5): DirectionalLight

  /**
   * A multiplier for the color of the directional light.
   *
   * @param intensity value of intensity as Expression
   */
  fun intensity(intensity: Expression): DirectionalLight

  /**
   * Set the Intensity property transition options.
   *
   * @param options transition options for intensity
   */
  fun intensityTransition(options: StyleTransition): DirectionalLight

  /**
   * DSL for [intensityTransition].
   */
  fun intensityTransition(block: StyleTransition.Builder.() -> Unit): DirectionalLight

  /**
   * Determines the shadow strength, affecting the shadow receiver surfaces final color. Values near 0.0 reduce the shadow contribution to the final color. Values near to 1.0 make occluded surfaces receive almost no directional light. Designed to be used mostly for transitioning between values 0 and 1.
   *
   * @param shadowIntensity as Double
   */
  fun shadowIntensity(shadowIntensity: Double = 1.0): DirectionalLight

  /**
   * Determines the shadow strength, affecting the shadow receiver surfaces final color. Values near 0.0 reduce the shadow contribution to the final color. Values near to 1.0 make occluded surfaces receive almost no directional light. Designed to be used mostly for transitioning between values 0 and 1.
   *
   * @param shadowIntensity value of shadowIntensity as Expression
   */
  fun shadowIntensity(shadowIntensity: Expression): DirectionalLight

  /**
   * Set the ShadowIntensity property transition options.
   *
   * @param options transition options for shadow-intensity
   */
  fun shadowIntensityTransition(options: StyleTransition): DirectionalLight

  /**
   * DSL for [shadowIntensityTransition].
   */
  fun shadowIntensityTransition(block: StyleTransition.Builder.() -> Unit): DirectionalLight
}

/**
 * DSL function for creating [directionalLight] instance.
 */
fun directionalLight(id: String = "directional", block: DirectionalLightDslReceiver.() -> Unit): DirectionalLight = DirectionalLight(id).apply(block)

// End of generated file.