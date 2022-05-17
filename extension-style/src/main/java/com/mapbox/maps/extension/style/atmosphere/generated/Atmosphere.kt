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
  private var delegate: StyleInterface? = null
  private val properties = HashMap<String, PropertyValue<*>>()

  /**
   * The color of the atmosphere region immediately below the horizon and within the `range` and above the horizon and within `horizon-blend`. Using opacity is recommended only for smoothly transitioning fog on/off as anything less than 100% opacity results in more tiles loaded and drawn.
   */
  val colorAsColorInt: Int?
    /**
     * Get the color property.
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
   * Set the color property.
   *
   * @param color as int
   */
  override fun color(@ColorInt color: Int) = apply {
    val propertyValue = PropertyValue("color", colorIntToRgbaExpression(color))
    setProperty(propertyValue)
  }
  /**
   * The color of the atmosphere region immediately below the horizon and within the `range` and above the horizon and within `horizon-blend`. Using opacity is recommended only for smoothly transitioning fog on/off as anything less than 100% opacity results in more tiles loaded and drawn.
   */
  val color: String?
    /**
     * Get the Color property.
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
   * Set the color property.
   *
   * The color of the atmosphere region immediately below the horizon and within the `range` and above the horizon and within `horizon-blend`. Using opacity is recommended only for smoothly transitioning fog on/off as anything less than 100% opacity results in more tiles loaded and drawn.
   *
   * @param color as String
   */
  override fun color(color: String) = apply {
    setProperty(PropertyValue("color", color))
  }

  /**
   * This is an Expression representation of "color".
   *
   * The color of the atmosphere region immediately below the horizon and within the `range` and above the horizon and within `horizon-blend`. Using opacity is recommended only for smoothly transitioning fog on/off as anything less than 100% opacity results in more tiles loaded and drawn.
   */
  val colorAsExpression: Expression?
    /**
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
   * Set the color property
   *
   * @param color value of color as Expression
   */
  override fun color(color: Expression) = apply {
    val propertyValue = PropertyValue("color", color)
    setProperty(propertyValue)
  }
  /**
   * The color of the atmosphere region above the horizon, `high-color` extends further above the horizon than the `color` property and its spread can be controlled with `horizon-blend`. The opacity can be set to `0` to remove the high atmosphere color contribution.
   */
  val highColorAsColorInt: Int?
    /**
     * Get the highColor property.
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
   * Set the highColor property.
   *
   * @param highColor as int
   */
  override fun highColor(@ColorInt highColor: Int) = apply {
    val propertyValue = PropertyValue("high-color", colorIntToRgbaExpression(highColor))
    setProperty(propertyValue)
  }
  /**
   * The color of the atmosphere region above the horizon, `high-color` extends further above the horizon than the `color` property and its spread can be controlled with `horizon-blend`. The opacity can be set to `0` to remove the high atmosphere color contribution.
   */
  val highColor: String?
    /**
     * Get the HighColor property.
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
   * Set the highColor property.
   *
   * The color of the atmosphere region above the horizon, `high-color` extends further above the horizon than the `color` property and its spread can be controlled with `horizon-blend`. The opacity can be set to `0` to remove the high atmosphere color contribution.
   *
   * @param highColor as String
   */
  override fun highColor(highColor: String) = apply {
    setProperty(PropertyValue("high-color", highColor))
  }

  /**
   * This is an Expression representation of "high-color".
   *
   * The color of the atmosphere region above the horizon, `high-color` extends further above the horizon than the `color` property and its spread can be controlled with `horizon-blend`. The opacity can be set to `0` to remove the high atmosphere color contribution.
   */
  val highColorAsExpression: Expression?
    /**
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
   * Set the highColor property
   *
   * @param highColor value of highColor as Expression
   */
  override fun highColor(highColor: Expression) = apply {
    val propertyValue = PropertyValue("high-color", highColor)
    setProperty(propertyValue)
  }
  /**
   * Horizon blend applies a smooth fade from the color of the atmosphere to the color of space. A value of zero leaves a sharp transition from atmosphere to space. Increasing the value blends the color of atmosphere into increasingly high angles of the sky.
   */
  val horizonBlend: Double?
    /**
     * Get the HorizonBlend property.
     *
     * @return horizon-blend as Double
     */
    get() {
      return getPropertyValue("horizon-blend")
    }

  /**
   * Set the horizonBlend property.
   *
   * Horizon blend applies a smooth fade from the color of the atmosphere to the color of space. A value of zero leaves a sharp transition from atmosphere to space. Increasing the value blends the color of atmosphere into increasingly high angles of the sky.
   *
   * @param horizonBlend as Double
   */
  override fun horizonBlend(horizonBlend: Double) = apply {
    setProperty(PropertyValue("horizon-blend", horizonBlend))
  }

  /**
   * This is an Expression representation of "horizon-blend".
   *
   * Horizon blend applies a smooth fade from the color of the atmosphere to the color of space. A value of zero leaves a sharp transition from atmosphere to space. Increasing the value blends the color of atmosphere into increasingly high angles of the sky.
   */
  val horizonBlendAsExpression: Expression?
    /**
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
   * Set the horizonBlend property
   *
   * @param horizonBlend value of horizonBlend as Expression
   */
  override fun horizonBlend(horizonBlend: Expression) = apply {
    val propertyValue = PropertyValue("horizon-blend", horizonBlend)
    setProperty(propertyValue)
  }
  /**
   * The start and end distance range in which fog fades from fully transparent to fully opaque. The distance to the point at the center of the map is defined as zero, so that negative range values are closer to the camera, and positive values are farther away.
   */
  val range: List<Double>?
    /**
     * Get the Range property.
     *
     * @return range as List<Double>
     */
    get() {
      return getPropertyValue("range")
    }

  /**
   * Set the range property.
   *
   * The start and end distance range in which fog fades from fully transparent to fully opaque. The distance to the point at the center of the map is defined as zero, so that negative range values are closer to the camera, and positive values are farther away.
   *
   * @param range as List<Double>
   */
  override fun range(range: List<Double>) = apply {
    setProperty(PropertyValue("range", range))
  }

  /**
   * This is an Expression representation of "range".
   *
   * The start and end distance range in which fog fades from fully transparent to fully opaque. The distance to the point at the center of the map is defined as zero, so that negative range values are closer to the camera, and positive values are farther away.
   */
  val rangeAsExpression: Expression?
    /**
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
   * Set the range property
   *
   * @param range value of range as Expression
   */
  override fun range(range: Expression) = apply {
    val propertyValue = PropertyValue("range", range)
    setProperty(propertyValue)
  }
  /**
   * The color of the region above the horizon and after the end of the `horizon-blend` contribution. The opacity can be set to `0` to have a transparent background.
   */
  val spaceColorAsColorInt: Int?
    /**
     * Get the spaceColor property.
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
   * Set the spaceColor property.
   *
   * @param spaceColor as int
   */
  override fun spaceColor(@ColorInt spaceColor: Int) = apply {
    val propertyValue = PropertyValue("space-color", colorIntToRgbaExpression(spaceColor))
    setProperty(propertyValue)
  }
  /**
   * The color of the region above the horizon and after the end of the `horizon-blend` contribution. The opacity can be set to `0` to have a transparent background.
   */
  val spaceColor: String?
    /**
     * Get the SpaceColor property.
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
   * Set the spaceColor property.
   *
   * The color of the region above the horizon and after the end of the `horizon-blend` contribution. The opacity can be set to `0` to have a transparent background.
   *
   * @param spaceColor as String
   */
  override fun spaceColor(spaceColor: String) = apply {
    setProperty(PropertyValue("space-color", spaceColor))
  }

  /**
   * This is an Expression representation of "space-color".
   *
   * The color of the region above the horizon and after the end of the `horizon-blend` contribution. The opacity can be set to `0` to have a transparent background.
   */
  val spaceColorAsExpression: Expression?
    /**
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
   * Set the spaceColor property
   *
   * @param spaceColor value of spaceColor as Expression
   */
  override fun spaceColor(spaceColor: Expression) = apply {
    val propertyValue = PropertyValue("space-color", spaceColor)
    setProperty(propertyValue)
  }
  /**
   * A value controlling the star intensity where `0` will show no stars and `1` will show stars at their maximum intensity.
   */
  val starIntensity: Double?
    /**
     * Get the StarIntensity property.
     *
     * @return star-intensity as Double
     */
    get() {
      return getPropertyValue("star-intensity")
    }

  /**
   * Set the starIntensity property.
   *
   * A value controlling the star intensity where `0` will show no stars and `1` will show stars at their maximum intensity.
   *
   * @param starIntensity as Double
   */
  override fun starIntensity(starIntensity: Double) = apply {
    setProperty(PropertyValue("star-intensity", starIntensity))
  }

  /**
   * This is an Expression representation of "star-intensity".
   *
   * A value controlling the star intensity where `0` will show no stars and `1` will show stars at their maximum intensity.
   */
  val starIntensityAsExpression: Expression?
    /**
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
   * Set the starIntensity property
   *
   * @param starIntensity value of starIntensity as Expression
   */
  override fun starIntensity(starIntensity: Expression) = apply {
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
   * Set the color property.
   *
   * @param color as int
   */
  fun color(@ColorInt color: Int): Atmosphere
  /**
   * Set the color property.
   *
   * The color of the atmosphere region immediately below the horizon and within the `range` and above the horizon and within `horizon-blend`. Using opacity is recommended only for smoothly transitioning fog on/off as anything less than 100% opacity results in more tiles loaded and drawn.
   *
   * @param color as String
   */
  fun color(color: String = "#ffffff"): Atmosphere

  /**
   * Set the color property
   *
   * @param color value of color as Expression
   */
  fun color(color: Expression): Atmosphere
  /**
   * Set the highColor property.
   *
   * @param highColor as int
   */
  fun highColor(@ColorInt highColor: Int): Atmosphere
  /**
   * Set the highColor property.
   *
   * The color of the atmosphere region above the horizon, `high-color` extends further above the horizon than the `color` property and its spread can be controlled with `horizon-blend`. The opacity can be set to `0` to remove the high atmosphere color contribution.
   *
   * @param highColor as String
   */
  fun highColor(highColor: String = "#245cdf"): Atmosphere

  /**
   * Set the highColor property
   *
   * @param highColor value of highColor as Expression
   */
  fun highColor(highColor: Expression): Atmosphere
  /**
   * Set the horizonBlend property.
   *
   * Horizon blend applies a smooth fade from the color of the atmosphere to the color of space. A value of zero leaves a sharp transition from atmosphere to space. Increasing the value blends the color of atmosphere into increasingly high angles of the sky.
   *
   * @param horizonBlend as Double
   */
  fun horizonBlend(horizonBlend: Double): Atmosphere

  /**
   * Set the horizonBlend property
   *
   * @param horizonBlend value of horizonBlend as Expression
   */
  fun horizonBlend(horizonBlend: Expression = Expression.fromRaw("""["interpolate",["linear"],["zoom"],4,0.2,7,0.1]""".trimIndent())): Atmosphere
  /**
   * Set the range property.
   *
   * The start and end distance range in which fog fades from fully transparent to fully opaque. The distance to the point at the center of the map is defined as zero, so that negative range values are closer to the camera, and positive values are farther away.
   *
   * @param range as List<Double>
   */
  fun range(range: List<Double> = listOf(0.5, 10.0)): Atmosphere

  /**
   * Set the range property
   *
   * @param range value of range as Expression
   */
  fun range(range: Expression): Atmosphere
  /**
   * Set the spaceColor property.
   *
   * @param spaceColor as int
   */
  fun spaceColor(@ColorInt spaceColor: Int): Atmosphere
  /**
   * Set the spaceColor property.
   *
   * The color of the region above the horizon and after the end of the `horizon-blend` contribution. The opacity can be set to `0` to have a transparent background.
   *
   * @param spaceColor as String
   */
  fun spaceColor(spaceColor: String): Atmosphere

  /**
   * Set the spaceColor property
   *
   * @param spaceColor value of spaceColor as Expression
   */
  fun spaceColor(spaceColor: Expression = Expression.fromRaw("""["interpolate",["linear"],["zoom"],4,"#010b19",7,"#367ab9"]""".trimIndent())): Atmosphere
  /**
   * Set the starIntensity property.
   *
   * A value controlling the star intensity where `0` will show no stars and `1` will show stars at their maximum intensity.
   *
   * @param starIntensity as Double
   */
  fun starIntensity(starIntensity: Double): Atmosphere

  /**
   * Set the starIntensity property
   *
   * @param starIntensity value of starIntensity as Expression
   */
  fun starIntensity(starIntensity: Expression = Expression.fromRaw("""["interpolate",["linear"],["zoom"],5,0.35,6,0]""".trimIndent())): Atmosphere
}

/**
 * DSL function for [Atmosphere].
 */
fun atmosphere(block: AtmosphereDslReceiver.() -> Unit): Atmosphere {
  return Atmosphere().apply(block)
}

// End of generated file.