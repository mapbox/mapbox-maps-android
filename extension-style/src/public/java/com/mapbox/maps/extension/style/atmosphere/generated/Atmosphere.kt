// This file is generated.
package com.mapbox.maps.extension.style.atmosphere.generated

import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.UiThread
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxStyleException
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.types.AtmosphereDsl
import com.mapbox.maps.extension.style.types.StyleTransition
import com.mapbox.maps.extension.style.utils.ColorUtils.colorIntToRgbaExpression
import com.mapbox.maps.extension.style.utils.ColorUtils.rgbaExpressionToColorInt
import com.mapbox.maps.extension.style.utils.ColorUtils.rgbaExpressionToColorString
import com.mapbox.maps.extension.style.utils.unwrap

/**
 * A global effect that fades layers and markers based on their distance to the camera. The fog can be used to approximate the effect of atmosphere on distant objects and enhance the depth perception of the map when used with terrain or 3D features. Note: fog is renamed to atmosphere in the Android and iOS SDKs and planned to be changed in GL-JS v.3.0.0.
 *
 * @see [The online documentation](https://docs.mapbox.com/mapbox-gl-js/style-spec/fog/)
 */
@UiThread
class Atmosphere : AtmosphereDslReceiver, StyleContract.StyleAtmosphereExtension {
  internal var delegate: StyleInterface? = null
  private val properties = HashMap<String, PropertyValue<*>>()

  /**
   * The color of the atmosphere region immediately below the horizon and within the `range` and above the horizon and within `horizon-blend`. Using opacity is recommended only for smoothly transitioning fog on/off as anything less than 100% opacity results in more tiles loaded and drawn.
   */
  val colorAsColorInt: Int?
    /**
     * The color of the atmosphere region immediately below the horizon and within the `range` and above the horizon and within `horizon-blend`. Using opacity is recommended only for smoothly transitioning fog on/off as anything less than 100% opacity results in more tiles loaded and drawn.
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
   * The color of the atmosphere region immediately below the horizon and within the `range` and above the horizon and within `horizon-blend`. Using opacity is recommended only for smoothly transitioning fog on/off as anything less than 100% opacity results in more tiles loaded and drawn.
   *
   * @param color as int
   */
  override fun color(@ColorInt color: Int): Atmosphere = apply {
    val propertyValue = PropertyValue("color", colorIntToRgbaExpression(color))
    setProperty(propertyValue)
  }
  /**
   * The color of the atmosphere region immediately below the horizon and within the `range` and above the horizon and within `horizon-blend`. Using opacity is recommended only for smoothly transitioning fog on/off as anything less than 100% opacity results in more tiles loaded and drawn.
   */
  val color: String?
    /**
     * The color of the atmosphere region immediately below the horizon and within the `range` and above the horizon and within `horizon-blend`. Using opacity is recommended only for smoothly transitioning fog on/off as anything less than 100% opacity results in more tiles loaded and drawn.
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
   * The color of the atmosphere region immediately below the horizon and within the `range` and above the horizon and within `horizon-blend`. Using opacity is recommended only for smoothly transitioning fog on/off as anything less than 100% opacity results in more tiles loaded and drawn.
   *
   * @param color as String
   */
  override fun color(color: String): Atmosphere = apply {
    setProperty(PropertyValue("color", color))
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
  override fun colorTransition(options: StyleTransition): Atmosphere = apply {
    val propertyValue = PropertyValue("color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [colorTransition].
   */
  override fun colorTransition(block: StyleTransition.Builder.() -> Unit): Atmosphere = apply {
    colorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The color of the atmosphere region immediately below the horizon and within the `range` and above the horizon and within `horizon-blend`. Using opacity is recommended only for smoothly transitioning fog on/off as anything less than 100% opacity results in more tiles loaded and drawn.
   *
   * This is an Expression representation of "color".
   */
  val colorAsExpression: Expression?
    /**
     * The color of the atmosphere region immediately below the horizon and within the `range` and above the horizon and within `horizon-blend`. Using opacity is recommended only for smoothly transitioning fog on/off as anything less than 100% opacity results in more tiles loaded and drawn.
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
   * The color of the atmosphere region immediately below the horizon and within the `range` and above the horizon and within `horizon-blend`. Using opacity is recommended only for smoothly transitioning fog on/off as anything less than 100% opacity results in more tiles loaded and drawn.
   *
   * @param color value of color as Expression
   */
  override fun color(color: Expression): Atmosphere = apply {
    val propertyValue = PropertyValue("color", color)
    setProperty(propertyValue)
  }
  /**
   * The color of the atmosphere region above the horizon, `high-color` extends further above the horizon than the `color` property and its spread can be controlled with `horizon-blend`. The opacity can be set to `0` to remove the high atmosphere color contribution.
   */
  val highColorAsColorInt: Int?
    /**
     * The color of the atmosphere region above the horizon, `high-color` extends further above the horizon than the `color` property and its spread can be controlled with `horizon-blend`. The opacity can be set to `0` to remove the high atmosphere color contribution.
     *
     * @return highColor as int
     */
    @ColorInt
    get() {
      highColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }
  /**
   * The color of the atmosphere region above the horizon, `high-color` extends further above the horizon than the `color` property and its spread can be controlled with `horizon-blend`. The opacity can be set to `0` to remove the high atmosphere color contribution.
   *
   * @param highColor as int
   */
  override fun highColor(@ColorInt highColor: Int): Atmosphere = apply {
    val propertyValue = PropertyValue("high-color", colorIntToRgbaExpression(highColor))
    setProperty(propertyValue)
  }
  /**
   * The color of the atmosphere region above the horizon, `high-color` extends further above the horizon than the `color` property and its spread can be controlled with `horizon-blend`. The opacity can be set to `0` to remove the high atmosphere color contribution.
   */
  val highColor: String?
    /**
     * The color of the atmosphere region above the horizon, `high-color` extends further above the horizon than the `color` property and its spread can be controlled with `horizon-blend`. The opacity can be set to `0` to remove the high atmosphere color contribution.
     *
     * @return high-color as String
     */
    get() {
      highColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The color of the atmosphere region above the horizon, `high-color` extends further above the horizon than the `color` property and its spread can be controlled with `horizon-blend`. The opacity can be set to `0` to remove the high atmosphere color contribution.
   *
   * @param highColor as String
   */
  override fun highColor(highColor: String): Atmosphere = apply {
    setProperty(PropertyValue("high-color", highColor))
  }

  /**
   * HighColor property transition options.
   */
  val highColorTransition: StyleTransition?
    /**
     * Get the HighColor property transition options.
     *
     * @return transition options for high-color
     */
    get() {
      return getTransitionProperty("high-color-transition")
    }

  /**
   * Set the HighColor property transition options.
   *
   * @param options transition options for high-color
   */
  override fun highColorTransition(options: StyleTransition): Atmosphere = apply {
    val propertyValue = PropertyValue("high-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [highColorTransition].
   */
  override fun highColorTransition(block: StyleTransition.Builder.() -> Unit): Atmosphere = apply {
    highColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The color of the atmosphere region above the horizon, `high-color` extends further above the horizon than the `color` property and its spread can be controlled with `horizon-blend`. The opacity can be set to `0` to remove the high atmosphere color contribution.
   *
   * This is an Expression representation of "high-color".
   */
  val highColorAsExpression: Expression?
    /**
     * The color of the atmosphere region above the horizon, `high-color` extends further above the horizon than the `color` property and its spread can be controlled with `horizon-blend`. The opacity can be set to `0` to remove the high atmosphere color contribution.
     *
     * Get the highColor property as an Expression
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("high-color")?.let {
        return it
      }
      return null
    }
  /**
   * The color of the atmosphere region above the horizon, `high-color` extends further above the horizon than the `color` property and its spread can be controlled with `horizon-blend`. The opacity can be set to `0` to remove the high atmosphere color contribution.
   *
   * @param highColor value of highColor as Expression
   */
  override fun highColor(highColor: Expression): Atmosphere = apply {
    val propertyValue = PropertyValue("high-color", highColor)
    setProperty(propertyValue)
  }
  /**
   * Horizon blend applies a smooth fade from the color of the atmosphere to the color of space. A value of zero leaves a sharp transition from atmosphere to space. Increasing the value blends the color of atmosphere into increasingly high angles of the sky.
   */
  val horizonBlend: Double?
    /**
     * Horizon blend applies a smooth fade from the color of the atmosphere to the color of space. A value of zero leaves a sharp transition from atmosphere to space. Increasing the value blends the color of atmosphere into increasingly high angles of the sky.
     *
     * @return horizon-blend as Double
     */
    get() {
      return getPropertyValue("horizon-blend")
    }

  /**
   * Horizon blend applies a smooth fade from the color of the atmosphere to the color of space. A value of zero leaves a sharp transition from atmosphere to space. Increasing the value blends the color of atmosphere into increasingly high angles of the sky.
   *
   * @param horizonBlend as Double
   */
  override fun horizonBlend(horizonBlend: Double): Atmosphere = apply {
    setProperty(PropertyValue("horizon-blend", horizonBlend))
  }

  /**
   * HorizonBlend property transition options.
   */
  val horizonBlendTransition: StyleTransition?
    /**
     * Get the HorizonBlend property transition options.
     *
     * @return transition options for horizon-blend
     */
    get() {
      return getTransitionProperty("horizon-blend-transition")
    }

  /**
   * Set the HorizonBlend property transition options.
   *
   * @param options transition options for horizon-blend
   */
  override fun horizonBlendTransition(options: StyleTransition): Atmosphere = apply {
    val propertyValue = PropertyValue("horizon-blend-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [horizonBlendTransition].
   */
  override fun horizonBlendTransition(block: StyleTransition.Builder.() -> Unit): Atmosphere = apply {
    horizonBlendTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Horizon blend applies a smooth fade from the color of the atmosphere to the color of space. A value of zero leaves a sharp transition from atmosphere to space. Increasing the value blends the color of atmosphere into increasingly high angles of the sky.
   *
   * This is an Expression representation of "horizon-blend".
   */
  val horizonBlendAsExpression: Expression?
    /**
     * Horizon blend applies a smooth fade from the color of the atmosphere to the color of space. A value of zero leaves a sharp transition from atmosphere to space. Increasing the value blends the color of atmosphere into increasingly high angles of the sky.
     *
     * Get the horizonBlend property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("horizon-blend")?.let {
        return it
      }
      horizonBlend?.let {
        return Expression.literal(it)
      }
      return null
    }
  /**
   * Horizon blend applies a smooth fade from the color of the atmosphere to the color of space. A value of zero leaves a sharp transition from atmosphere to space. Increasing the value blends the color of atmosphere into increasingly high angles of the sky.
   *
   * @param horizonBlend value of horizonBlend as Expression
   */
  override fun horizonBlend(horizonBlend: Expression): Atmosphere = apply {
    val propertyValue = PropertyValue("horizon-blend", horizonBlend)
    setProperty(propertyValue)
  }
  /**
   * The start and end distance range in which fog fades from fully transparent to fully opaque. The distance to the point at the center of the map is defined as zero, so that negative range values are closer to the camera, and positive values are farther away.
   */
  val range: List<Double>?
    /**
     * The start and end distance range in which fog fades from fully transparent to fully opaque. The distance to the point at the center of the map is defined as zero, so that negative range values are closer to the camera, and positive values are farther away.
     *
     * @return range as List<Double>
     */
    get() {
      return getPropertyValue("range")
    }

  /**
   * The start and end distance range in which fog fades from fully transparent to fully opaque. The distance to the point at the center of the map is defined as zero, so that negative range values are closer to the camera, and positive values are farther away.
   *
   * @param range as List<Double>
   */
  override fun range(range: List<Double>): Atmosphere = apply {
    setProperty(PropertyValue("range", range))
  }

  /**
   * Range property transition options.
   */
  val rangeTransition: StyleTransition?
    /**
     * Get the Range property transition options.
     *
     * @return transition options for range
     */
    get() {
      return getTransitionProperty("range-transition")
    }

  /**
   * Set the Range property transition options.
   *
   * @param options transition options for range
   */
  override fun rangeTransition(options: StyleTransition): Atmosphere = apply {
    val propertyValue = PropertyValue("range-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [rangeTransition].
   */
  override fun rangeTransition(block: StyleTransition.Builder.() -> Unit): Atmosphere = apply {
    rangeTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The start and end distance range in which fog fades from fully transparent to fully opaque. The distance to the point at the center of the map is defined as zero, so that negative range values are closer to the camera, and positive values are farther away.
   *
   * This is an Expression representation of "range".
   */
  val rangeAsExpression: Expression?
    /**
     * The start and end distance range in which fog fades from fully transparent to fully opaque. The distance to the point at the center of the map is defined as zero, so that negative range values are closer to the camera, and positive values are farther away.
     *
     * Get the range property as an Expression
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("range")?.let {
        return it
      }
      range?.let {
        return Expression.literal(it)
      }
      return null
    }
  /**
   * The start and end distance range in which fog fades from fully transparent to fully opaque. The distance to the point at the center of the map is defined as zero, so that negative range values are closer to the camera, and positive values are farther away.
   *
   * @param range value of range as Expression
   */
  override fun range(range: Expression): Atmosphere = apply {
    val propertyValue = PropertyValue("range", range)
    setProperty(propertyValue)
  }
  /**
   * The color of the region above the horizon and after the end of the `horizon-blend` contribution. The opacity can be set to `0` to have a transparent background.
   */
  val spaceColorAsColorInt: Int?
    /**
     * The color of the region above the horizon and after the end of the `horizon-blend` contribution. The opacity can be set to `0` to have a transparent background.
     *
     * @return spaceColor as int
     */
    @ColorInt
    get() {
      spaceColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }
  /**
   * The color of the region above the horizon and after the end of the `horizon-blend` contribution. The opacity can be set to `0` to have a transparent background.
   *
   * @param spaceColor as int
   */
  override fun spaceColor(@ColorInt spaceColor: Int): Atmosphere = apply {
    val propertyValue = PropertyValue("space-color", colorIntToRgbaExpression(spaceColor))
    setProperty(propertyValue)
  }
  /**
   * The color of the region above the horizon and after the end of the `horizon-blend` contribution. The opacity can be set to `0` to have a transparent background.
   */
  val spaceColor: String?
    /**
     * The color of the region above the horizon and after the end of the `horizon-blend` contribution. The opacity can be set to `0` to have a transparent background.
     *
     * @return space-color as String
     */
    get() {
      spaceColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The color of the region above the horizon and after the end of the `horizon-blend` contribution. The opacity can be set to `0` to have a transparent background.
   *
   * @param spaceColor as String
   */
  override fun spaceColor(spaceColor: String): Atmosphere = apply {
    setProperty(PropertyValue("space-color", spaceColor))
  }

  /**
   * SpaceColor property transition options.
   */
  val spaceColorTransition: StyleTransition?
    /**
     * Get the SpaceColor property transition options.
     *
     * @return transition options for space-color
     */
    get() {
      return getTransitionProperty("space-color-transition")
    }

  /**
   * Set the SpaceColor property transition options.
   *
   * @param options transition options for space-color
   */
  override fun spaceColorTransition(options: StyleTransition): Atmosphere = apply {
    val propertyValue = PropertyValue("space-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [spaceColorTransition].
   */
  override fun spaceColorTransition(block: StyleTransition.Builder.() -> Unit): Atmosphere = apply {
    spaceColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The color of the region above the horizon and after the end of the `horizon-blend` contribution. The opacity can be set to `0` to have a transparent background.
   *
   * This is an Expression representation of "space-color".
   */
  val spaceColorAsExpression: Expression?
    /**
     * The color of the region above the horizon and after the end of the `horizon-blend` contribution. The opacity can be set to `0` to have a transparent background.
     *
     * Get the spaceColor property as an Expression
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("space-color")?.let {
        return it
      }
      return null
    }
  /**
   * The color of the region above the horizon and after the end of the `horizon-blend` contribution. The opacity can be set to `0` to have a transparent background.
   *
   * @param spaceColor value of spaceColor as Expression
   */
  override fun spaceColor(spaceColor: Expression): Atmosphere = apply {
    val propertyValue = PropertyValue("space-color", spaceColor)
    setProperty(propertyValue)
  }
  /**
   * A value controlling the star intensity where `0` will show no stars and `1` will show stars at their maximum intensity.
   */
  val starIntensity: Double?
    /**
     * A value controlling the star intensity where `0` will show no stars and `1` will show stars at their maximum intensity.
     *
     * @return star-intensity as Double
     */
    get() {
      return getPropertyValue("star-intensity")
    }

  /**
   * A value controlling the star intensity where `0` will show no stars and `1` will show stars at their maximum intensity.
   *
   * @param starIntensity as Double
   */
  override fun starIntensity(starIntensity: Double): Atmosphere = apply {
    setProperty(PropertyValue("star-intensity", starIntensity))
  }

  /**
   * StarIntensity property transition options.
   */
  val starIntensityTransition: StyleTransition?
    /**
     * Get the StarIntensity property transition options.
     *
     * @return transition options for star-intensity
     */
    get() {
      return getTransitionProperty("star-intensity-transition")
    }

  /**
   * Set the StarIntensity property transition options.
   *
   * @param options transition options for star-intensity
   */
  override fun starIntensityTransition(options: StyleTransition): Atmosphere = apply {
    val propertyValue = PropertyValue("star-intensity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [starIntensityTransition].
   */
  override fun starIntensityTransition(block: StyleTransition.Builder.() -> Unit): Atmosphere = apply {
    starIntensityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * A value controlling the star intensity where `0` will show no stars and `1` will show stars at their maximum intensity.
   *
   * This is an Expression representation of "star-intensity".
   */
  val starIntensityAsExpression: Expression?
    /**
     * A value controlling the star intensity where `0` will show no stars and `1` will show stars at their maximum intensity.
     *
     * Get the starIntensity property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("star-intensity")?.let {
        return it
      }
      starIntensity?.let {
        return Expression.literal(it)
      }
      return null
    }
  /**
   * A value controlling the star intensity where `0` will show no stars and `1` will show stars at their maximum intensity.
   *
   * @param starIntensity value of starIntensity as Expression
   */
  override fun starIntensity(starIntensity: Expression): Atmosphere = apply {
    val propertyValue = PropertyValue("star-intensity", starIntensity)
    setProperty(propertyValue)
  }

  /**
   * Bind atmosphere to the map controller.
   *
   * @param delegate The map controller
   */
  override fun bindTo(delegate: StyleInterface) {
    this.delegate = delegate
    val atmosphereParams = HashMap<String, Value>()

    properties.forEach {
      atmosphereParams[it.key] = it.value.value
    }
    val expected = delegate.setStyleAtmosphere(Value(atmosphereParams))
    expected.error?.let {
      throw MapboxStyleException("Set atmosphere failed: $it")
    }
  }

  private fun setProperty(property: PropertyValue<*>) {
    properties[property.propertyName] = property
    updateProperty(property)
  }

  private fun updateProperty(propertyValue: PropertyValue<*>) {
    val expected = delegate?.setStyleAtmosphereProperty(
      propertyValue.propertyName,
      propertyValue.value
    )
    expected?.error?.let {
      throw MapboxStyleException("Set atmosphere property failed: $it")
    }
  }

  private inline fun <reified T> getPropertyValue(propertyName: String): T? {
    delegate?.let {
      return try {
        it.getStyleAtmosphereProperty(propertyName).unwrap()
      } catch (e: RuntimeException) {
        Log.e(TAG, "Get atmosphere property failed: ${e.message}")
        Log.e(TAG, it.getStyleAtmosphereProperty(propertyName).toString())
        null
      }
    }
    throw MapboxStyleException("Get property $propertyName failed: atmosphere is not added to style yet.")
  }

  private fun getTransitionProperty(transitionName: String): StyleTransition? {
    delegate?.let {
      return try {
        @Suppress("UNCHECKED_CAST")
        val styleLayerProperty =
          it.getStyleAtmosphereProperty(transitionName).value.contents as HashMap<String, Value>
        val duration = styleLayerProperty["duration"]?.contents as Long
        val delay = styleLayerProperty["delay"]?.contents as Long
        StyleTransition.Builder().delay(delay).duration(duration).build()
      } catch (e: RuntimeException) {
        Log.e(TAG, "Get atmosphere property failed: ${e.message}")
        Log.e(TAG, it.getStyleAtmosphereProperty(transitionName).toString())
        null
      }
    }
    throw MapboxStyleException("Get property $transitionName failed: atmosphere is not added to style yet.")
  }

  /**
   * Static variables and methods.
   */
  private companion object {
    private const val TAG = "Mbgl-Atmosphere"
  }
}

/**
 * This Interface contains all the functions that will be exposed to Koltin DSL.
 *
 * Separated the DSL receiver class to this interface to avoid IDE code suggestion for
 * property getters.
 */
@AtmosphereDsl
interface AtmosphereDslReceiver {
  /**
   * The color of the atmosphere region immediately below the horizon and within the `range` and above the horizon and within `horizon-blend`. Using opacity is recommended only for smoothly transitioning fog on/off as anything less than 100% opacity results in more tiles loaded and drawn.
   *
   * @param color as int
   */
  fun color(@ColorInt color: Int): Atmosphere
  /**
   * The color of the atmosphere region immediately below the horizon and within the `range` and above the horizon and within `horizon-blend`. Using opacity is recommended only for smoothly transitioning fog on/off as anything less than 100% opacity results in more tiles loaded and drawn.
   *
   * @param color as String
   */
  fun color(color: String = "#ffffff"): Atmosphere

  /**
   * The color of the atmosphere region immediately below the horizon and within the `range` and above the horizon and within `horizon-blend`. Using opacity is recommended only for smoothly transitioning fog on/off as anything less than 100% opacity results in more tiles loaded and drawn.
   *
   * @param color value of color as Expression
   */
  fun color(color: Expression): Atmosphere

  /**
   * Set the Color property transition options.
   *
   * @param options transition options for color
   */
  fun colorTransition(options: StyleTransition): Atmosphere

  /**
   * DSL for [colorTransition].
   */
  fun colorTransition(block: StyleTransition.Builder.() -> Unit): Atmosphere
  /**
   * The color of the atmosphere region above the horizon, `high-color` extends further above the horizon than the `color` property and its spread can be controlled with `horizon-blend`. The opacity can be set to `0` to remove the high atmosphere color contribution.
   *
   * @param highColor as int
   */
  fun highColor(@ColorInt highColor: Int): Atmosphere
  /**
   * The color of the atmosphere region above the horizon, `high-color` extends further above the horizon than the `color` property and its spread can be controlled with `horizon-blend`. The opacity can be set to `0` to remove the high atmosphere color contribution.
   *
   * @param highColor as String
   */
  fun highColor(highColor: String = "#245cdf"): Atmosphere

  /**
   * The color of the atmosphere region above the horizon, `high-color` extends further above the horizon than the `color` property and its spread can be controlled with `horizon-blend`. The opacity can be set to `0` to remove the high atmosphere color contribution.
   *
   * @param highColor value of highColor as Expression
   */
  fun highColor(highColor: Expression): Atmosphere

  /**
   * Set the HighColor property transition options.
   *
   * @param options transition options for high-color
   */
  fun highColorTransition(options: StyleTransition): Atmosphere

  /**
   * DSL for [highColorTransition].
   */
  fun highColorTransition(block: StyleTransition.Builder.() -> Unit): Atmosphere
  /**
   * Horizon blend applies a smooth fade from the color of the atmosphere to the color of space. A value of zero leaves a sharp transition from atmosphere to space. Increasing the value blends the color of atmosphere into increasingly high angles of the sky.
   *
   * @param horizonBlend as Double
   */
  fun horizonBlend(horizonBlend: Double): Atmosphere

  /**
   * Horizon blend applies a smooth fade from the color of the atmosphere to the color of space. A value of zero leaves a sharp transition from atmosphere to space. Increasing the value blends the color of atmosphere into increasingly high angles of the sky.
   *
   * @param horizonBlend value of horizonBlend as Expression
   */
  fun horizonBlend(horizonBlend: Expression = Expression.fromRaw("""["interpolate",["linear"],["zoom"],4,0.2,7,0.1]""".trimIndent())): Atmosphere

  /**
   * Set the HorizonBlend property transition options.
   *
   * @param options transition options for horizon-blend
   */
  fun horizonBlendTransition(options: StyleTransition): Atmosphere

  /**
   * DSL for [horizonBlendTransition].
   */
  fun horizonBlendTransition(block: StyleTransition.Builder.() -> Unit): Atmosphere
  /**
   * The start and end distance range in which fog fades from fully transparent to fully opaque. The distance to the point at the center of the map is defined as zero, so that negative range values are closer to the camera, and positive values are farther away.
   *
   * @param range as List<Double>
   */
  fun range(range: List<Double> = listOf(0.5, 10.0)): Atmosphere

  /**
   * The start and end distance range in which fog fades from fully transparent to fully opaque. The distance to the point at the center of the map is defined as zero, so that negative range values are closer to the camera, and positive values are farther away.
   *
   * @param range value of range as Expression
   */
  fun range(range: Expression): Atmosphere

  /**
   * Set the Range property transition options.
   *
   * @param options transition options for range
   */
  fun rangeTransition(options: StyleTransition): Atmosphere

  /**
   * DSL for [rangeTransition].
   */
  fun rangeTransition(block: StyleTransition.Builder.() -> Unit): Atmosphere
  /**
   * The color of the region above the horizon and after the end of the `horizon-blend` contribution. The opacity can be set to `0` to have a transparent background.
   *
   * @param spaceColor as int
   */
  fun spaceColor(@ColorInt spaceColor: Int): Atmosphere
  /**
   * The color of the region above the horizon and after the end of the `horizon-blend` contribution. The opacity can be set to `0` to have a transparent background.
   *
   * @param spaceColor as String
   */
  fun spaceColor(spaceColor: String): Atmosphere

  /**
   * The color of the region above the horizon and after the end of the `horizon-blend` contribution. The opacity can be set to `0` to have a transparent background.
   *
   * @param spaceColor value of spaceColor as Expression
   */
  fun spaceColor(spaceColor: Expression = Expression.fromRaw("""["interpolate",["linear"],["zoom"],4,"#010b19",7,"#367ab9"]""".trimIndent())): Atmosphere

  /**
   * Set the SpaceColor property transition options.
   *
   * @param options transition options for space-color
   */
  fun spaceColorTransition(options: StyleTransition): Atmosphere

  /**
   * DSL for [spaceColorTransition].
   */
  fun spaceColorTransition(block: StyleTransition.Builder.() -> Unit): Atmosphere
  /**
   * A value controlling the star intensity where `0` will show no stars and `1` will show stars at their maximum intensity.
   *
   * @param starIntensity as Double
   */
  fun starIntensity(starIntensity: Double): Atmosphere

  /**
   * A value controlling the star intensity where `0` will show no stars and `1` will show stars at their maximum intensity.
   *
   * @param starIntensity value of starIntensity as Expression
   */
  fun starIntensity(starIntensity: Expression = Expression.fromRaw("""["interpolate",["linear"],["zoom"],5,0.35,6,0]""".trimIndent())): Atmosphere

  /**
   * Set the StarIntensity property transition options.
   *
   * @param options transition options for star-intensity
   */
  fun starIntensityTransition(options: StyleTransition): Atmosphere

  /**
   * DSL for [starIntensityTransition].
   */
  fun starIntensityTransition(block: StyleTransition.Builder.() -> Unit): Atmosphere
}

/**
 * DSL function for creating [Atmosphere] instance.
 */
fun atmosphere(block: AtmosphereDslReceiver.() -> Unit): Atmosphere {
  return Atmosphere().apply(block)
}

// End of generated file.