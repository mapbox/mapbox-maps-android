// This file is generated.
package com.mapbox.maps.extension.style.precipitations.generated

import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.UiThread
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleException
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.types.RainDsl
import com.mapbox.maps.extension.style.types.StyleTransition
import com.mapbox.maps.extension.style.utils.ColorUtils.colorIntToRgbaExpression
import com.mapbox.maps.extension.style.utils.ColorUtils.rgbaExpressionToColorInt
import com.mapbox.maps.extension.style.utils.ColorUtils.rgbaExpressionToColorString
import com.mapbox.maps.extension.style.utils.unwrap

/**
 * The experimental Rain API to render rain effect on the map.
 */
@UiThread
@MapboxExperimental
class Rain : RainDslReceiver, StyleContract.StyleRainExtension {
  internal var delegate: MapboxStyleManager? = null
  private val properties = HashMap<String, PropertyValue<*>>()

  /**
   * Thinning factor of rain particles from center. 0 - no thinning. 1 - maximal central area thinning. Default value: 1. Value range: [0, 1]
   */
  @MapboxExperimental
  val centerThinning: Double?
    /**
     * Thinning factor of rain particles from center. 0 - no thinning. 1 - maximal central area thinning. Default value: 1. Value range: [0, 1]
     *
     * @return center-thinning as Double
     */
    get() {
      return getPropertyValue("center-thinning")
    }

  /**
   * Thinning factor of rain particles from center. 0 - no thinning. 1 - maximal central area thinning. Default value: 1. Value range: [0, 1]
   *
   * @param centerThinning as Double
   */
  @MapboxExperimental
  override fun centerThinning(centerThinning: Double): Rain = apply {
    setProperty(PropertyValue("center-thinning", centerThinning))
  }

  /**
   * CenterThinning property transition options.
   */
  @MapboxExperimental
  val centerThinningTransition: StyleTransition?
    /**
     * Get the CenterThinning property transition options.
     *
     * @return transition options for center-thinning
     */
    get() {
      return getTransitionProperty("center-thinning-transition")
    }

  /**
   * Set the CenterThinning property transition options.
   *
   * @param options transition options for center-thinning
   */
  @MapboxExperimental
  override fun centerThinningTransition(options: StyleTransition): Rain = apply {
    val propertyValue = PropertyValue("center-thinning-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [centerThinningTransition].
   */
  @MapboxExperimental
  override fun centerThinningTransition(block: StyleTransition.Builder.() -> Unit): Rain = apply {
    centerThinningTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Thinning factor of rain particles from center. 0 - no thinning. 1 - maximal central area thinning. Default value: 1. Value range: [0, 1]
   *
   * This is an Expression representation of "center-thinning".
   */
  @MapboxExperimental
  val centerThinningAsExpression: Expression?
    /**
     * Thinning factor of rain particles from center. 0 - no thinning. 1 - maximal central area thinning. Default value: 1. Value range: [0, 1]
     *
     * Get the centerThinning property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("center-thinning")?.let {
        return it
      }
      centerThinning?.let {
        return Expression.literal(it)
      }
      return null
    }
  /**
   * Thinning factor of rain particles from center. 0 - no thinning. 1 - maximal central area thinning. Default value: 1. Value range: [0, 1]
   *
   * @param centerThinning value of centerThinning as Expression
   */
  @MapboxExperimental
  override fun centerThinning(centerThinning: Expression): Rain = apply {
    val propertyValue = PropertyValue("center-thinning", centerThinning)
    setProperty(propertyValue)
  }
  /**
   * Default value: "#ffffff".
   */
  @MapboxExperimental
  val colorAsColorInt: Int?
    /**
     * Default value: "#ffffff".
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
   * Default value: "#ffffff".
   *
   * @param color as int
   */
  @MapboxExperimental
  override fun color(@ColorInt color: Int): Rain = apply {
    val propertyValue = PropertyValue("color", colorIntToRgbaExpression(color))
    setProperty(propertyValue)
  }
  /**
   * Default value: "#ffffff".
   */
  @MapboxExperimental
  val color: String?
    /**
     * Default value: "#ffffff".
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
   * Default value: "#ffffff".
   *
   * @param color as String
   */
  @MapboxExperimental
  override fun color(color: String): Rain = apply {
    setProperty(PropertyValue("color", color))
  }

  /**
   * Color property transition options.
   */
  @MapboxExperimental
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
  @MapboxExperimental
  override fun colorTransition(options: StyleTransition): Rain = apply {
    val propertyValue = PropertyValue("color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [colorTransition].
   */
  @MapboxExperimental
  override fun colorTransition(block: StyleTransition.Builder.() -> Unit): Rain = apply {
    colorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Default value: "#ffffff".
   *
   * This is an Expression representation of "color".
   */
  @MapboxExperimental
  val colorAsExpression: Expression?
    /**
     * Default value: "#ffffff".
     *
     * Get the color property as an Expression
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
   * Default value: "#ffffff".
   *
   * @param color value of color as Expression
   */
  @MapboxExperimental
  override fun color(color: Expression): Rain = apply {
    val propertyValue = PropertyValue("color", color)
    setProperty(propertyValue)
  }
  /**
   * Rain particles density. Default value: 1. Value range: [0, 1]
   */
  @MapboxExperimental
  val density: Double?
    /**
     * Rain particles density. Default value: 1. Value range: [0, 1]
     *
     * @return density as Double
     */
    get() {
      return getPropertyValue("density")
    }

  /**
   * Rain particles density. Default value: 1. Value range: [0, 1]
   *
   * @param density as Double
   */
  @MapboxExperimental
  override fun density(density: Double): Rain = apply {
    setProperty(PropertyValue("density", density))
  }

  /**
   * Density property transition options.
   */
  @MapboxExperimental
  val densityTransition: StyleTransition?
    /**
     * Get the Density property transition options.
     *
     * @return transition options for density
     */
    get() {
      return getTransitionProperty("density-transition")
    }

  /**
   * Set the Density property transition options.
   *
   * @param options transition options for density
   */
  @MapboxExperimental
  override fun densityTransition(options: StyleTransition): Rain = apply {
    val propertyValue = PropertyValue("density-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [densityTransition].
   */
  @MapboxExperimental
  override fun densityTransition(block: StyleTransition.Builder.() -> Unit): Rain = apply {
    densityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Rain particles density. Default value: 1. Value range: [0, 1]
   *
   * This is an Expression representation of "density".
   */
  @MapboxExperimental
  val densityAsExpression: Expression?
    /**
     * Rain particles density. Default value: 1. Value range: [0, 1]
     *
     * Get the density property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("density")?.let {
        return it
      }
      density?.let {
        return Expression.literal(it)
      }
      return null
    }
  /**
   * Rain particles density. Default value: 1. Value range: [0, 1]
   *
   * @param density value of density as Expression
   */
  @MapboxExperimental
  override fun density(density: Expression): Rain = apply {
    val propertyValue = PropertyValue("density", density)
    setProperty(propertyValue)
  }
  /**
   * Main rain particles direction. Heading & pitch Default value: [0,80]. Value range: [0, 360]
   */
  @MapboxExperimental
  val direction: List<Double>?
    /**
     * Main rain particles direction. Heading & pitch Default value: [0,80]. Value range: [0, 360]
     *
     * @return direction as List<Double>
     */
    get() {
      return getPropertyValue("direction")
    }

  /**
   * Main rain particles direction. Heading & pitch Default value: [0,80]. Value range: [0, 360]
   *
   * @param direction as List<Double>
   */
  @MapboxExperimental
  override fun direction(direction: List<Double>): Rain = apply {
    setProperty(PropertyValue("direction", direction))
  }

  /**
   * Direction property transition options.
   */
  @MapboxExperimental
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
  @MapboxExperimental
  override fun directionTransition(options: StyleTransition): Rain = apply {
    val propertyValue = PropertyValue("direction-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [directionTransition].
   */
  @MapboxExperimental
  override fun directionTransition(block: StyleTransition.Builder.() -> Unit): Rain = apply {
    directionTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Main rain particles direction. Heading & pitch Default value: [0,80]. Value range: [0, 360]
   *
   * This is an Expression representation of "direction".
   */
  @MapboxExperimental
  val directionAsExpression: Expression?
    /**
     * Main rain particles direction. Heading & pitch Default value: [0,80]. Value range: [0, 360]
     *
     * Get the direction property as an Expression
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
   * Main rain particles direction. Heading & pitch Default value: [0,80]. Value range: [0, 360]
   *
   * @param direction value of direction as Expression
   */
  @MapboxExperimental
  override fun direction(direction: Expression): Rain = apply {
    val propertyValue = PropertyValue("direction", direction)
    setProperty(propertyValue)
  }
  /**
   * Rain particles movement factor. Default value: 1. Value range: [0, 1]
   */
  @MapboxExperimental
  val intensity: Double?
    /**
     * Rain particles movement factor. Default value: 1. Value range: [0, 1]
     *
     * @return intensity as Double
     */
    get() {
      return getPropertyValue("intensity")
    }

  /**
   * Rain particles movement factor. Default value: 1. Value range: [0, 1]
   *
   * @param intensity as Double
   */
  @MapboxExperimental
  override fun intensity(intensity: Double): Rain = apply {
    setProperty(PropertyValue("intensity", intensity))
  }

  /**
   * Intensity property transition options.
   */
  @MapboxExperimental
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
  @MapboxExperimental
  override fun intensityTransition(options: StyleTransition): Rain = apply {
    val propertyValue = PropertyValue("intensity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [intensityTransition].
   */
  @MapboxExperimental
  override fun intensityTransition(block: StyleTransition.Builder.() -> Unit): Rain = apply {
    intensityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Rain particles movement factor. Default value: 1. Value range: [0, 1]
   *
   * This is an Expression representation of "intensity".
   */
  @MapboxExperimental
  val intensityAsExpression: Expression?
    /**
     * Rain particles movement factor. Default value: 1. Value range: [0, 1]
     *
     * Get the intensity property as an Expression
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
   * Rain particles movement factor. Default value: 1. Value range: [0, 1]
   *
   * @param intensity value of intensity as Expression
   */
  @MapboxExperimental
  override fun intensity(intensity: Expression): Rain = apply {
    val propertyValue = PropertyValue("intensity", intensity)
    setProperty(propertyValue)
  }
  /**
   * Rain particles opacity. Default value: 1. Value range: [0, 1]
   */
  @MapboxExperimental
  val opacity: Double?
    /**
     * Rain particles opacity. Default value: 1. Value range: [0, 1]
     *
     * @return opacity as Double
     */
    get() {
      return getPropertyValue("opacity")
    }

  /**
   * Rain particles opacity. Default value: 1. Value range: [0, 1]
   *
   * @param opacity as Double
   */
  @MapboxExperimental
  override fun opacity(opacity: Double): Rain = apply {
    setProperty(PropertyValue("opacity", opacity))
  }

  /**
   * Opacity property transition options.
   */
  @MapboxExperimental
  val opacityTransition: StyleTransition?
    /**
     * Get the Opacity property transition options.
     *
     * @return transition options for opacity
     */
    get() {
      return getTransitionProperty("opacity-transition")
    }

  /**
   * Set the Opacity property transition options.
   *
   * @param options transition options for opacity
   */
  @MapboxExperimental
  override fun opacityTransition(options: StyleTransition): Rain = apply {
    val propertyValue = PropertyValue("opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [opacityTransition].
   */
  @MapboxExperimental
  override fun opacityTransition(block: StyleTransition.Builder.() -> Unit): Rain = apply {
    opacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Rain particles opacity. Default value: 1. Value range: [0, 1]
   *
   * This is an Expression representation of "opacity".
   */
  @MapboxExperimental
  val opacityAsExpression: Expression?
    /**
     * Rain particles opacity. Default value: 1. Value range: [0, 1]
     *
     * Get the opacity property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("opacity")?.let {
        return it
      }
      opacity?.let {
        return Expression.literal(it)
      }
      return null
    }
  /**
   * Rain particles opacity. Default value: 1. Value range: [0, 1]
   *
   * @param opacity value of opacity as Expression
   */
  @MapboxExperimental
  override fun opacity(opacity: Expression): Rain = apply {
    val propertyValue = PropertyValue("opacity", opacity)
    setProperty(propertyValue)
  }
  /**
   * Rain vignette screen-space effect. Default value: 0. Value range: [0, 1]
   */
  @MapboxExperimental
  val vignette: Double?
    /**
     * Rain vignette screen-space effect. Default value: 0. Value range: [0, 1]
     *
     * @return vignette as Double
     */
    get() {
      return getPropertyValue("vignette")
    }

  /**
   * Rain vignette screen-space effect. Default value: 0. Value range: [0, 1]
   *
   * @param vignette as Double
   */
  @MapboxExperimental
  override fun vignette(vignette: Double): Rain = apply {
    setProperty(PropertyValue("vignette", vignette))
  }

  /**
   * Vignette property transition options.
   */
  @MapboxExperimental
  val vignetteTransition: StyleTransition?
    /**
     * Get the Vignette property transition options.
     *
     * @return transition options for vignette
     */
    get() {
      return getTransitionProperty("vignette-transition")
    }

  /**
   * Set the Vignette property transition options.
   *
   * @param options transition options for vignette
   */
  @MapboxExperimental
  override fun vignetteTransition(options: StyleTransition): Rain = apply {
    val propertyValue = PropertyValue("vignette-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [vignetteTransition].
   */
  @MapboxExperimental
  override fun vignetteTransition(block: StyleTransition.Builder.() -> Unit): Rain = apply {
    vignetteTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Rain vignette screen-space effect. Default value: 0. Value range: [0, 1]
   *
   * This is an Expression representation of "vignette".
   */
  @MapboxExperimental
  val vignetteAsExpression: Expression?
    /**
     * Rain vignette screen-space effect. Default value: 0. Value range: [0, 1]
     *
     * Get the vignette property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("vignette")?.let {
        return it
      }
      vignette?.let {
        return Expression.literal(it)
      }
      return null
    }
  /**
   * Rain vignette screen-space effect. Default value: 0. Value range: [0, 1]
   *
   * @param vignette value of vignette as Expression
   */
  @MapboxExperimental
  override fun vignette(vignette: Expression): Rain = apply {
    val propertyValue = PropertyValue("vignette", vignette)
    setProperty(propertyValue)
  }

  /**
   * Bind rain to the map controller.
   *
   * @param delegate The map controller
   */
  override fun bindTo(delegate: MapboxStyleManager) {
    this.delegate = delegate
    val rainParams = HashMap<String, Value>()

    properties.forEach {
      rainParams[it.key] = it.value.value
    }
    val expected = delegate.setStyleRain(Value(rainParams))
    expected.error?.let {
      throw MapboxStyleException("Set rain failed: $it")
    }
  }

  private fun setProperty(property: PropertyValue<*>) {
    properties[property.propertyName] = property
    updateProperty(property)
  }

  private fun updateProperty(propertyValue: PropertyValue<*>) {
    val expected = delegate?.setStyleRainProperty(
      propertyValue.propertyName,
      propertyValue.value
    )
    expected?.error?.let {
      throw MapboxStyleException("Set rain property failed: $it")
    }
  }

  private inline fun <reified T> getPropertyValue(propertyName: String): T? {
    delegate?.let {
      return try {
        it.getStyleRainProperty(propertyName).unwrap()
      } catch (e: RuntimeException) {
        Log.e(TAG, "Get rain property failed: ${e.message}")
        Log.e(TAG, it.getStyleRainProperty(propertyName).toString())
        null
      }
    }
    throw MapboxStyleException("Get property $propertyName failed: rain is not added to style yet.")
  }

  private fun getTransitionProperty(transitionName: String): StyleTransition? {
    delegate?.let {
      return try {
        @Suppress("UNCHECKED_CAST")
        val styleLayerProperty =
          it.getStyleRainProperty(transitionName).value.contents as HashMap<String, Value>
        val duration = styleLayerProperty["duration"]?.contents as Long
        val delay = styleLayerProperty["delay"]?.contents as Long
        StyleTransition.Builder().delay(delay).duration(duration).build()
      } catch (e: RuntimeException) {
        Log.e(TAG, "Get rain property failed: ${e.message}")
        Log.e(TAG, it.getStyleRainProperty(transitionName).toString())
        null
      }
    }
    throw MapboxStyleException("Get property $transitionName failed: rain is not added to style yet.")
  }

  /**
   * Static variables and methods.
   */
  private companion object {
    private const val TAG = "Mbgl-Rain"
  }
}

/**
 * This Interface contains all the functions that will be exposed to Koltin DSL.
 *
 * Separated the DSL receiver class to this interface to avoid IDE code suggestion for
 * property getters.
 */
@RainDsl
@MapboxExperimental
interface RainDslReceiver {
  /**
   * Thinning factor of rain particles from center. 0 - no thinning. 1 - maximal central area thinning. Default value: 1. Value range: [0, 1]
   *
   * @param centerThinning as Double
   */
  @MapboxExperimental
  fun centerThinning(centerThinning: Double = 1.0): Rain

  /**
   * Thinning factor of rain particles from center. 0 - no thinning. 1 - maximal central area thinning. Default value: 1. Value range: [0, 1]
   *
   * @param centerThinning value of centerThinning as Expression
   */
  @MapboxExperimental
  fun centerThinning(centerThinning: Expression): Rain

  /**
   * Set the CenterThinning property transition options.
   *
   * @param options transition options for center-thinning
   */
  @MapboxExperimental
  fun centerThinningTransition(options: StyleTransition): Rain

  /**
   * DSL for [centerThinningTransition].
   */
  @MapboxExperimental
  fun centerThinningTransition(block: StyleTransition.Builder.() -> Unit): Rain
  /**
   * Default value: "#ffffff".
   *
   * @param color as int
   */
  @MapboxExperimental
  fun color(@ColorInt color: Int): Rain
  /**
   * Default value: "#ffffff".
   *
   * @param color as String
   */
  @MapboxExperimental
  fun color(color: String = "#ffffff"): Rain

  /**
   * Default value: "#ffffff".
   *
   * @param color value of color as Expression
   */
  @MapboxExperimental
  fun color(color: Expression): Rain

  /**
   * Set the Color property transition options.
   *
   * @param options transition options for color
   */
  @MapboxExperimental
  fun colorTransition(options: StyleTransition): Rain

  /**
   * DSL for [colorTransition].
   */
  @MapboxExperimental
  fun colorTransition(block: StyleTransition.Builder.() -> Unit): Rain
  /**
   * Rain particles density. Default value: 1. Value range: [0, 1]
   *
   * @param density as Double
   */
  @MapboxExperimental
  fun density(density: Double = 1.0): Rain

  /**
   * Rain particles density. Default value: 1. Value range: [0, 1]
   *
   * @param density value of density as Expression
   */
  @MapboxExperimental
  fun density(density: Expression): Rain

  /**
   * Set the Density property transition options.
   *
   * @param options transition options for density
   */
  @MapboxExperimental
  fun densityTransition(options: StyleTransition): Rain

  /**
   * DSL for [densityTransition].
   */
  @MapboxExperimental
  fun densityTransition(block: StyleTransition.Builder.() -> Unit): Rain
  /**
   * Main rain particles direction. Heading & pitch Default value: [0,80]. Value range: [0, 360]
   *
   * @param direction as List<Double>
   */
  @MapboxExperimental
  fun direction(direction: List<Double> = listOf(0.0, 80.0)): Rain

  /**
   * Main rain particles direction. Heading & pitch Default value: [0,80]. Value range: [0, 360]
   *
   * @param direction value of direction as Expression
   */
  @MapboxExperimental
  fun direction(direction: Expression): Rain

  /**
   * Set the Direction property transition options.
   *
   * @param options transition options for direction
   */
  @MapboxExperimental
  fun directionTransition(options: StyleTransition): Rain

  /**
   * DSL for [directionTransition].
   */
  @MapboxExperimental
  fun directionTransition(block: StyleTransition.Builder.() -> Unit): Rain
  /**
   * Rain particles movement factor. Default value: 1. Value range: [0, 1]
   *
   * @param intensity as Double
   */
  @MapboxExperimental
  fun intensity(intensity: Double = 1.0): Rain

  /**
   * Rain particles movement factor. Default value: 1. Value range: [0, 1]
   *
   * @param intensity value of intensity as Expression
   */
  @MapboxExperimental
  fun intensity(intensity: Expression): Rain

  /**
   * Set the Intensity property transition options.
   *
   * @param options transition options for intensity
   */
  @MapboxExperimental
  fun intensityTransition(options: StyleTransition): Rain

  /**
   * DSL for [intensityTransition].
   */
  @MapboxExperimental
  fun intensityTransition(block: StyleTransition.Builder.() -> Unit): Rain
  /**
   * Rain particles opacity. Default value: 1. Value range: [0, 1]
   *
   * @param opacity as Double
   */
  @MapboxExperimental
  fun opacity(opacity: Double = 1.0): Rain

  /**
   * Rain particles opacity. Default value: 1. Value range: [0, 1]
   *
   * @param opacity value of opacity as Expression
   */
  @MapboxExperimental
  fun opacity(opacity: Expression): Rain

  /**
   * Set the Opacity property transition options.
   *
   * @param options transition options for opacity
   */
  @MapboxExperimental
  fun opacityTransition(options: StyleTransition): Rain

  /**
   * DSL for [opacityTransition].
   */
  @MapboxExperimental
  fun opacityTransition(block: StyleTransition.Builder.() -> Unit): Rain
  /**
   * Rain vignette screen-space effect. Default value: 0. Value range: [0, 1]
   *
   * @param vignette as Double
   */
  @MapboxExperimental
  fun vignette(vignette: Double = 0.0): Rain

  /**
   * Rain vignette screen-space effect. Default value: 0. Value range: [0, 1]
   *
   * @param vignette value of vignette as Expression
   */
  @MapboxExperimental
  fun vignette(vignette: Expression): Rain

  /**
   * Set the Vignette property transition options.
   *
   * @param options transition options for vignette
   */
  @MapboxExperimental
  fun vignetteTransition(options: StyleTransition): Rain

  /**
   * DSL for [vignetteTransition].
   */
  @MapboxExperimental
  fun vignetteTransition(block: StyleTransition.Builder.() -> Unit): Rain
}

/**
 * DSL function for creating [Rain] instance.
 */
@MapboxExperimental
fun rain(block: RainDslReceiver.() -> Unit): Rain {
  return Rain().apply(block)
}

// End of generated file.