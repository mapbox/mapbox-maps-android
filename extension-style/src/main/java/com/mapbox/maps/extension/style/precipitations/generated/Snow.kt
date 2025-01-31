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
import com.mapbox.maps.extension.style.types.SnowDsl
import com.mapbox.maps.extension.style.types.StyleTransition
import com.mapbox.maps.extension.style.utils.ColorUtils.colorIntToRgbaExpression
import com.mapbox.maps.extension.style.utils.ColorUtils.rgbaExpressionToColorInt
import com.mapbox.maps.extension.style.utils.ColorUtils.rgbaExpressionToColorString
import com.mapbox.maps.extension.style.utils.unwrap

/**
 * The experimental Snow API to render snow effect on the map.
 */
@UiThread
@MapboxExperimental
class Snow : SnowDslReceiver, StyleContract.StyleSnowExtension {
  internal var delegate: MapboxStyleManager? = null
  private val properties = HashMap<String, PropertyValue<*>>()

  /**
   * Thinning factor of snow particles from center. 0 - no thinning. 1 - maximal central area thinning. Default value: 0.4. Value range: [0, 1]
   */
  @MapboxExperimental
  val centerThinning: Double?
    /**
     * Thinning factor of snow particles from center. 0 - no thinning. 1 - maximal central area thinning. Default value: 0.4. Value range: [0, 1]
     *
     * @return center-thinning as Double
     */
    get() {
      return getPropertyValue("center-thinning")
    }

  /**
   * Thinning factor of snow particles from center. 0 - no thinning. 1 - maximal central area thinning. Default value: 0.4. Value range: [0, 1]
   *
   * @param centerThinning as Double
   */
  @MapboxExperimental
  override fun centerThinning(centerThinning: Double): Snow = apply {
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
  override fun centerThinningTransition(options: StyleTransition): Snow = apply {
    val propertyValue = PropertyValue("center-thinning-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [centerThinningTransition].
   */
  @MapboxExperimental
  override fun centerThinningTransition(block: StyleTransition.Builder.() -> Unit): Snow = apply {
    centerThinningTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Thinning factor of snow particles from center. 0 - no thinning. 1 - maximal central area thinning. Default value: 0.4. Value range: [0, 1]
   *
   * This is an Expression representation of "center-thinning".
   */
  @MapboxExperimental
  val centerThinningAsExpression: Expression?
    /**
     * Thinning factor of snow particles from center. 0 - no thinning. 1 - maximal central area thinning. Default value: 0.4. Value range: [0, 1]
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
   * Thinning factor of snow particles from center. 0 - no thinning. 1 - maximal central area thinning. Default value: 0.4. Value range: [0, 1]
   *
   * @param centerThinning value of centerThinning as Expression
   */
  @MapboxExperimental
  override fun centerThinning(centerThinning: Expression): Snow = apply {
    val propertyValue = PropertyValue("center-thinning", centerThinning)
    setProperty(propertyValue)
  }
  /**
   * Snow particles color. Default value: "#ffffff".
   */
  @MapboxExperimental
  val colorAsColorInt: Int?
    /**
     * Snow particles color. Default value: "#ffffff".
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
   * Snow particles color. Default value: "#ffffff".
   *
   * @param color as int
   */
  @MapboxExperimental
  override fun color(@ColorInt color: Int): Snow = apply {
    val propertyValue = PropertyValue("color", colorIntToRgbaExpression(color))
    setProperty(propertyValue)
  }
  /**
   * Snow particles color. Default value: "#ffffff".
   */
  @MapboxExperimental
  val color: String?
    /**
     * Snow particles color. Default value: "#ffffff".
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
   * Snow particles color. Default value: "#ffffff".
   *
   * @param color as String
   */
  @MapboxExperimental
  override fun color(color: String): Snow = apply {
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
  override fun colorTransition(options: StyleTransition): Snow = apply {
    val propertyValue = PropertyValue("color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [colorTransition].
   */
  @MapboxExperimental
  override fun colorTransition(block: StyleTransition.Builder.() -> Unit): Snow = apply {
    colorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Snow particles color. Default value: "#ffffff".
   *
   * This is an Expression representation of "color".
   */
  @MapboxExperimental
  val colorAsExpression: Expression?
    /**
     * Snow particles color. Default value: "#ffffff".
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
   * Snow particles color. Default value: "#ffffff".
   *
   * @param color value of color as Expression
   */
  @MapboxExperimental
  override fun color(color: Expression): Snow = apply {
    val propertyValue = PropertyValue("color", color)
    setProperty(propertyValue)
  }
  /**
   * Snow particles density. Controls the overall particles number. Default value: "["interpolate",["linear"],["zoom"],11,0,13,0.85]". Value range: [0, 1]
   */
  @MapboxExperimental
  val density: Double?
    /**
     * Snow particles density. Controls the overall particles number. Default value: "["interpolate",["linear"],["zoom"],11,0,13,0.85]". Value range: [0, 1]
     *
     * @return density as Double
     */
    get() {
      return getPropertyValue("density")
    }

  /**
   * Snow particles density. Controls the overall particles number. Default value: "["interpolate",["linear"],["zoom"],11,0,13,0.85]". Value range: [0, 1]
   *
   * @param density as Double
   */
  @MapboxExperimental
  override fun density(density: Double): Snow = apply {
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
  override fun densityTransition(options: StyleTransition): Snow = apply {
    val propertyValue = PropertyValue("density-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [densityTransition].
   */
  @MapboxExperimental
  override fun densityTransition(block: StyleTransition.Builder.() -> Unit): Snow = apply {
    densityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Snow particles density. Controls the overall particles number. Default value: "["interpolate",["linear"],["zoom"],11,0,13,0.85]". Value range: [0, 1]
   *
   * This is an Expression representation of "density".
   */
  @MapboxExperimental
  val densityAsExpression: Expression?
    /**
     * Snow particles density. Controls the overall particles number. Default value: "["interpolate",["linear"],["zoom"],11,0,13,0.85]". Value range: [0, 1]
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
   * Snow particles density. Controls the overall particles number. Default value: "["interpolate",["linear"],["zoom"],11,0,13,0.85]". Value range: [0, 1]
   *
   * @param density value of density as Expression
   */
  @MapboxExperimental
  override fun density(density: Expression): Snow = apply {
    val propertyValue = PropertyValue("density", density)
    setProperty(propertyValue)
  }
  /**
   * Main snow particles direction. Azimuth and polar angles Default value: [0,50]. Value range: [0, 360]
   */
  @MapboxExperimental
  val direction: List<Double>?
    /**
     * Main snow particles direction. Azimuth and polar angles Default value: [0,50]. Value range: [0, 360]
     *
     * @return direction as List<Double>
     */
    get() {
      return getPropertyValue("direction")
    }

  /**
   * Main snow particles direction. Azimuth and polar angles Default value: [0,50]. Value range: [0, 360]
   *
   * @param direction as List<Double>
   */
  @MapboxExperimental
  override fun direction(direction: List<Double>): Snow = apply {
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
  override fun directionTransition(options: StyleTransition): Snow = apply {
    val propertyValue = PropertyValue("direction-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [directionTransition].
   */
  @MapboxExperimental
  override fun directionTransition(block: StyleTransition.Builder.() -> Unit): Snow = apply {
    directionTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Main snow particles direction. Azimuth and polar angles Default value: [0,50]. Value range: [0, 360]
   *
   * This is an Expression representation of "direction".
   */
  @MapboxExperimental
  val directionAsExpression: Expression?
    /**
     * Main snow particles direction. Azimuth and polar angles Default value: [0,50]. Value range: [0, 360]
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
   * Main snow particles direction. Azimuth and polar angles Default value: [0,50]. Value range: [0, 360]
   *
   * @param direction value of direction as Expression
   */
  @MapboxExperimental
  override fun direction(direction: Expression): Snow = apply {
    val propertyValue = PropertyValue("direction", direction)
    setProperty(propertyValue)
  }
  /**
   * Snow flake particle size. Correlates with individual particle screen size Default value: 0.71. Value range: [0, 5]
   */
  @MapboxExperimental
  val flakeSize: Double?
    /**
     * Snow flake particle size. Correlates with individual particle screen size Default value: 0.71. Value range: [0, 5]
     *
     * @return flake-size as Double
     */
    get() {
      return getPropertyValue("flake-size")
    }

  /**
   * Snow flake particle size. Correlates with individual particle screen size Default value: 0.71. Value range: [0, 5]
   *
   * @param flakeSize as Double
   */
  @MapboxExperimental
  override fun flakeSize(flakeSize: Double): Snow = apply {
    setProperty(PropertyValue("flake-size", flakeSize))
  }

  /**
   * FlakeSize property transition options.
   */
  @MapboxExperimental
  val flakeSizeTransition: StyleTransition?
    /**
     * Get the FlakeSize property transition options.
     *
     * @return transition options for flake-size
     */
    get() {
      return getTransitionProperty("flake-size-transition")
    }

  /**
   * Set the FlakeSize property transition options.
   *
   * @param options transition options for flake-size
   */
  @MapboxExperimental
  override fun flakeSizeTransition(options: StyleTransition): Snow = apply {
    val propertyValue = PropertyValue("flake-size-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [flakeSizeTransition].
   */
  @MapboxExperimental
  override fun flakeSizeTransition(block: StyleTransition.Builder.() -> Unit): Snow = apply {
    flakeSizeTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Snow flake particle size. Correlates with individual particle screen size Default value: 0.71. Value range: [0, 5]
   *
   * This is an Expression representation of "flake-size".
   */
  @MapboxExperimental
  val flakeSizeAsExpression: Expression?
    /**
     * Snow flake particle size. Correlates with individual particle screen size Default value: 0.71. Value range: [0, 5]
     *
     * Get the flakeSize property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("flake-size")?.let {
        return it
      }
      flakeSize?.let {
        return Expression.literal(it)
      }
      return null
    }
  /**
   * Snow flake particle size. Correlates with individual particle screen size Default value: 0.71. Value range: [0, 5]
   *
   * @param flakeSize value of flakeSize as Expression
   */
  @MapboxExperimental
  override fun flakeSize(flakeSize: Expression): Snow = apply {
    val propertyValue = PropertyValue("flake-size", flakeSize)
    setProperty(propertyValue)
  }
  /**
   * Snow particles movement factor. Controls the overall particles movement speed. Default value: 1. Value range: [0, 1]
   */
  @MapboxExperimental
  val intensity: Double?
    /**
     * Snow particles movement factor. Controls the overall particles movement speed. Default value: 1. Value range: [0, 1]
     *
     * @return intensity as Double
     */
    get() {
      return getPropertyValue("intensity")
    }

  /**
   * Snow particles movement factor. Controls the overall particles movement speed. Default value: 1. Value range: [0, 1]
   *
   * @param intensity as Double
   */
  @MapboxExperimental
  override fun intensity(intensity: Double): Snow = apply {
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
  override fun intensityTransition(options: StyleTransition): Snow = apply {
    val propertyValue = PropertyValue("intensity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [intensityTransition].
   */
  @MapboxExperimental
  override fun intensityTransition(block: StyleTransition.Builder.() -> Unit): Snow = apply {
    intensityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Snow particles movement factor. Controls the overall particles movement speed. Default value: 1. Value range: [0, 1]
   *
   * This is an Expression representation of "intensity".
   */
  @MapboxExperimental
  val intensityAsExpression: Expression?
    /**
     * Snow particles movement factor. Controls the overall particles movement speed. Default value: 1. Value range: [0, 1]
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
   * Snow particles movement factor. Controls the overall particles movement speed. Default value: 1. Value range: [0, 1]
   *
   * @param intensity value of intensity as Expression
   */
  @MapboxExperimental
  override fun intensity(intensity: Expression): Snow = apply {
    val propertyValue = PropertyValue("intensity", intensity)
    setProperty(propertyValue)
  }
  /**
   * Snow particles opacity. Default value: 1. Value range: [0, 1]
   */
  @MapboxExperimental
  val opacity: Double?
    /**
     * Snow particles opacity. Default value: 1. Value range: [0, 1]
     *
     * @return opacity as Double
     */
    get() {
      return getPropertyValue("opacity")
    }

  /**
   * Snow particles opacity. Default value: 1. Value range: [0, 1]
   *
   * @param opacity as Double
   */
  @MapboxExperimental
  override fun opacity(opacity: Double): Snow = apply {
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
  override fun opacityTransition(options: StyleTransition): Snow = apply {
    val propertyValue = PropertyValue("opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [opacityTransition].
   */
  @MapboxExperimental
  override fun opacityTransition(block: StyleTransition.Builder.() -> Unit): Snow = apply {
    opacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Snow particles opacity. Default value: 1. Value range: [0, 1]
   *
   * This is an Expression representation of "opacity".
   */
  @MapboxExperimental
  val opacityAsExpression: Expression?
    /**
     * Snow particles opacity. Default value: 1. Value range: [0, 1]
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
   * Snow particles opacity. Default value: 1. Value range: [0, 1]
   *
   * @param opacity value of opacity as Expression
   */
  @MapboxExperimental
  override fun opacity(opacity: Expression): Snow = apply {
    val propertyValue = PropertyValue("opacity", opacity)
    setProperty(propertyValue)
  }
  /**
   * Snow vignette screen-space effect. Adds snow tint to screen corners Default value: "["interpolate",["linear"],["zoom"],11,0,13,0.3]". Value range: [0, 1]
   */
  @MapboxExperimental
  val vignette: Double?
    /**
     * Snow vignette screen-space effect. Adds snow tint to screen corners Default value: "["interpolate",["linear"],["zoom"],11,0,13,0.3]". Value range: [0, 1]
     *
     * @return vignette as Double
     */
    get() {
      return getPropertyValue("vignette")
    }

  /**
   * Snow vignette screen-space effect. Adds snow tint to screen corners Default value: "["interpolate",["linear"],["zoom"],11,0,13,0.3]". Value range: [0, 1]
   *
   * @param vignette as Double
   */
  @MapboxExperimental
  override fun vignette(vignette: Double): Snow = apply {
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
  override fun vignetteTransition(options: StyleTransition): Snow = apply {
    val propertyValue = PropertyValue("vignette-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [vignetteTransition].
   */
  @MapboxExperimental
  override fun vignetteTransition(block: StyleTransition.Builder.() -> Unit): Snow = apply {
    vignetteTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Snow vignette screen-space effect. Adds snow tint to screen corners Default value: "["interpolate",["linear"],["zoom"],11,0,13,0.3]". Value range: [0, 1]
   *
   * This is an Expression representation of "vignette".
   */
  @MapboxExperimental
  val vignetteAsExpression: Expression?
    /**
     * Snow vignette screen-space effect. Adds snow tint to screen corners Default value: "["interpolate",["linear"],["zoom"],11,0,13,0.3]". Value range: [0, 1]
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
   * Snow vignette screen-space effect. Adds snow tint to screen corners Default value: "["interpolate",["linear"],["zoom"],11,0,13,0.3]". Value range: [0, 1]
   *
   * @param vignette value of vignette as Expression
   */
  @MapboxExperimental
  override fun vignette(vignette: Expression): Snow = apply {
    val propertyValue = PropertyValue("vignette", vignette)
    setProperty(propertyValue)
  }
  /**
   * Snow vignette screen-space corners tint color. Default value: "#ffffff".
   */
  @MapboxExperimental
  val vignetteColorAsColorInt: Int?
    /**
     * Snow vignette screen-space corners tint color. Default value: "#ffffff".
     *
     * @return vignetteColor as int
     */
    @ColorInt
    get() {
      vignetteColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }
  /**
   * Snow vignette screen-space corners tint color. Default value: "#ffffff".
   *
   * @param vignetteColor as int
   */
  @MapboxExperimental
  override fun vignetteColor(@ColorInt vignetteColor: Int): Snow = apply {
    val propertyValue = PropertyValue("vignette-color", colorIntToRgbaExpression(vignetteColor))
    setProperty(propertyValue)
  }
  /**
   * Snow vignette screen-space corners tint color. Default value: "#ffffff".
   */
  @MapboxExperimental
  val vignetteColor: String?
    /**
     * Snow vignette screen-space corners tint color. Default value: "#ffffff".
     *
     * @return vignette-color as String
     */
    get() {
      vignetteColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * Snow vignette screen-space corners tint color. Default value: "#ffffff".
   *
   * @param vignetteColor as String
   */
  @MapboxExperimental
  override fun vignetteColor(vignetteColor: String): Snow = apply {
    setProperty(PropertyValue("vignette-color", vignetteColor))
  }

  /**
   * VignetteColor property transition options.
   */
  @MapboxExperimental
  val vignetteColorTransition: StyleTransition?
    /**
     * Get the VignetteColor property transition options.
     *
     * @return transition options for vignette-color
     */
    get() {
      return getTransitionProperty("vignette-color-transition")
    }

  /**
   * Set the VignetteColor property transition options.
   *
   * @param options transition options for vignette-color
   */
  @MapboxExperimental
  override fun vignetteColorTransition(options: StyleTransition): Snow = apply {
    val propertyValue = PropertyValue("vignette-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [vignetteColorTransition].
   */
  @MapboxExperimental
  override fun vignetteColorTransition(block: StyleTransition.Builder.() -> Unit): Snow = apply {
    vignetteColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Snow vignette screen-space corners tint color. Default value: "#ffffff".
   *
   * This is an Expression representation of "vignette-color".
   */
  @MapboxExperimental
  val vignetteColorAsExpression: Expression?
    /**
     * Snow vignette screen-space corners tint color. Default value: "#ffffff".
     *
     * Get the vignetteColor property as an Expression
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("vignette-color")?.let {
        return it
      }
      return null
    }
  /**
   * Snow vignette screen-space corners tint color. Default value: "#ffffff".
   *
   * @param vignetteColor value of vignetteColor as Expression
   */
  @MapboxExperimental
  override fun vignetteColor(vignetteColor: Expression): Snow = apply {
    val propertyValue = PropertyValue("vignette-color", vignetteColor)
    setProperty(propertyValue)
  }

  /**
   * Bind snow to the map controller.
   *
   * @param delegate The map controller
   */
  override fun bindTo(delegate: MapboxStyleManager) {
    this.delegate = delegate
    val snowParams = HashMap<String, Value>()

    properties.forEach {
      snowParams[it.key] = it.value.value
    }
    val expected = delegate.setStyleSnow(Value(snowParams))
    expected.error?.let {
      throw MapboxStyleException("Set snow failed: $it")
    }
  }

  private fun setProperty(property: PropertyValue<*>) {
    properties[property.propertyName] = property
    updateProperty(property)
  }

  private fun updateProperty(propertyValue: PropertyValue<*>) {
    val expected = delegate?.setStyleSnowProperty(
      propertyValue.propertyName,
      propertyValue.value
    )
    expected?.error?.let {
      throw MapboxStyleException("Set snow property failed: $it")
    }
  }

  private inline fun <reified T> getPropertyValue(propertyName: String): T? {
    delegate?.let {
      return try {
        it.getStyleSnowProperty(propertyName).unwrap()
      } catch (e: RuntimeException) {
        Log.e(TAG, "Get snow property failed: ${e.message}")
        Log.e(TAG, it.getStyleSnowProperty(propertyName).toString())
        null
      }
    }
    throw MapboxStyleException("Get property $propertyName failed: snow is not added to style yet.")
  }

  private fun getTransitionProperty(transitionName: String): StyleTransition? {
    delegate?.let {
      return try {
        @Suppress("UNCHECKED_CAST")
        val styleLayerProperty =
          it.getStyleSnowProperty(transitionName).value.contents as HashMap<String, Value>
        val duration = styleLayerProperty["duration"]?.contents as Long
        val delay = styleLayerProperty["delay"]?.contents as Long
        StyleTransition.Builder().delay(delay).duration(duration).build()
      } catch (e: RuntimeException) {
        Log.e(TAG, "Get snow property failed: ${e.message}")
        Log.e(TAG, it.getStyleSnowProperty(transitionName).toString())
        null
      }
    }
    throw MapboxStyleException("Get property $transitionName failed: snow is not added to style yet.")
  }

  /**
   * Static variables and methods.
   */
  private companion object {
    private const val TAG = "Mbgl-Snow"
  }
}

/**
 * This Interface contains all the functions that will be exposed to Koltin DSL.
 *
 * Separated the DSL receiver class to this interface to avoid IDE code suggestion for
 * property getters.
 */
@SnowDsl
@MapboxExperimental
interface SnowDslReceiver {
  /**
   * Thinning factor of snow particles from center. 0 - no thinning. 1 - maximal central area thinning. Default value: 0.4. Value range: [0, 1]
   *
   * @param centerThinning as Double
   */
  @MapboxExperimental
  fun centerThinning(centerThinning: Double = 0.4): Snow

  /**
   * Thinning factor of snow particles from center. 0 - no thinning. 1 - maximal central area thinning. Default value: 0.4. Value range: [0, 1]
   *
   * @param centerThinning value of centerThinning as Expression
   */
  @MapboxExperimental
  fun centerThinning(centerThinning: Expression): Snow
  /**
   * Set the CenterThinning property transition options.
   *
   * @param options transition options for center-thinning
   */
  @MapboxExperimental
  fun centerThinningTransition(options: StyleTransition): Snow

  /**
   * DSL for [centerThinningTransition].
   */
  @MapboxExperimental
  fun centerThinningTransition(block: StyleTransition.Builder.() -> Unit): Snow
  /**
   * Snow particles color. Default value: "#ffffff".
   *
   * @param color as int
   */
  @MapboxExperimental
  fun color(@ColorInt color: Int): Snow
  /**
   * Snow particles color. Default value: "#ffffff".
   *
   * @param color as String
   */
  @MapboxExperimental
  fun color(color: String = "#ffffff"): Snow

  /**
   * Snow particles color. Default value: "#ffffff".
   *
   * @param color value of color as Expression
   */
  @MapboxExperimental
  fun color(color: Expression): Snow

  /**
   * Set the Color property transition options.
   *
   * @param options transition options for color
   */
  @MapboxExperimental
  fun colorTransition(options: StyleTransition): Snow

  /**
   * DSL for [colorTransition].
   */
  @MapboxExperimental
  fun colorTransition(block: StyleTransition.Builder.() -> Unit): Snow
  /**
   * Snow particles density. Controls the overall particles number. Default value: "["interpolate",["linear"],["zoom"],11,0,13,0.85]". Value range: [0, 1]
   *
   * @param density as Double
   */
  @MapboxExperimental
  fun density(density: Double): Snow

  /**
   * Snow particles density. Controls the overall particles number. Default value: "["interpolate",["linear"],["zoom"],11,0,13,0.85]". Value range: [0, 1]
   *
   * @param density value of density as Expression
   */
  @MapboxExperimental
  fun density(density: Expression = Expression.fromRaw("""["interpolate",["linear"],["zoom"],11,0,13,0.85]""".trimIndent())): Snow
  /**
   * Set the Density property transition options.
   *
   * @param options transition options for density
   */
  @MapboxExperimental
  fun densityTransition(options: StyleTransition): Snow

  /**
   * DSL for [densityTransition].
   */
  @MapboxExperimental
  fun densityTransition(block: StyleTransition.Builder.() -> Unit): Snow
  /**
   * Main snow particles direction. Azimuth and polar angles Default value: [0,50]. Value range: [0, 360]
   *
   * @param direction as List<Double>
   */
  @MapboxExperimental
  fun direction(direction: List<Double> = listOf(0.0, 50.0)): Snow

  /**
   * Main snow particles direction. Azimuth and polar angles Default value: [0,50]. Value range: [0, 360]
   *
   * @param direction value of direction as Expression
   */
  @MapboxExperimental
  fun direction(direction: Expression): Snow
  /**
   * Set the Direction property transition options.
   *
   * @param options transition options for direction
   */
  @MapboxExperimental
  fun directionTransition(options: StyleTransition): Snow

  /**
   * DSL for [directionTransition].
   */
  @MapboxExperimental
  fun directionTransition(block: StyleTransition.Builder.() -> Unit): Snow
  /**
   * Snow flake particle size. Correlates with individual particle screen size Default value: 0.71. Value range: [0, 5]
   *
   * @param flakeSize as Double
   */
  @MapboxExperimental
  fun flakeSize(flakeSize: Double = 0.71): Snow

  /**
   * Snow flake particle size. Correlates with individual particle screen size Default value: 0.71. Value range: [0, 5]
   *
   * @param flakeSize value of flakeSize as Expression
   */
  @MapboxExperimental
  fun flakeSize(flakeSize: Expression): Snow
  /**
   * Set the FlakeSize property transition options.
   *
   * @param options transition options for flake-size
   */
  @MapboxExperimental
  fun flakeSizeTransition(options: StyleTransition): Snow

  /**
   * DSL for [flakeSizeTransition].
   */
  @MapboxExperimental
  fun flakeSizeTransition(block: StyleTransition.Builder.() -> Unit): Snow
  /**
   * Snow particles movement factor. Controls the overall particles movement speed. Default value: 1. Value range: [0, 1]
   *
   * @param intensity as Double
   */
  @MapboxExperimental
  fun intensity(intensity: Double = 1.0): Snow

  /**
   * Snow particles movement factor. Controls the overall particles movement speed. Default value: 1. Value range: [0, 1]
   *
   * @param intensity value of intensity as Expression
   */
  @MapboxExperimental
  fun intensity(intensity: Expression): Snow
  /**
   * Set the Intensity property transition options.
   *
   * @param options transition options for intensity
   */
  @MapboxExperimental
  fun intensityTransition(options: StyleTransition): Snow

  /**
   * DSL for [intensityTransition].
   */
  @MapboxExperimental
  fun intensityTransition(block: StyleTransition.Builder.() -> Unit): Snow
  /**
   * Snow particles opacity. Default value: 1. Value range: [0, 1]
   *
   * @param opacity as Double
   */
  @MapboxExperimental
  fun opacity(opacity: Double = 1.0): Snow

  /**
   * Snow particles opacity. Default value: 1. Value range: [0, 1]
   *
   * @param opacity value of opacity as Expression
   */
  @MapboxExperimental
  fun opacity(opacity: Expression): Snow
  /**
   * Set the Opacity property transition options.
   *
   * @param options transition options for opacity
   */
  @MapboxExperimental
  fun opacityTransition(options: StyleTransition): Snow

  /**
   * DSL for [opacityTransition].
   */
  @MapboxExperimental
  fun opacityTransition(block: StyleTransition.Builder.() -> Unit): Snow
  /**
   * Snow vignette screen-space effect. Adds snow tint to screen corners Default value: "["interpolate",["linear"],["zoom"],11,0,13,0.3]". Value range: [0, 1]
   *
   * @param vignette as Double
   */
  @MapboxExperimental
  fun vignette(vignette: Double): Snow

  /**
   * Snow vignette screen-space effect. Adds snow tint to screen corners Default value: "["interpolate",["linear"],["zoom"],11,0,13,0.3]". Value range: [0, 1]
   *
   * @param vignette value of vignette as Expression
   */
  @MapboxExperimental
  fun vignette(vignette: Expression = Expression.fromRaw("""["interpolate",["linear"],["zoom"],11,0,13,0.3]""".trimIndent())): Snow
  /**
   * Set the Vignette property transition options.
   *
   * @param options transition options for vignette
   */
  @MapboxExperimental
  fun vignetteTransition(options: StyleTransition): Snow

  /**
   * DSL for [vignetteTransition].
   */
  @MapboxExperimental
  fun vignetteTransition(block: StyleTransition.Builder.() -> Unit): Snow
  /**
   * Snow vignette screen-space corners tint color. Default value: "#ffffff".
   *
   * @param vignetteColor as int
   */
  @MapboxExperimental
  fun vignetteColor(@ColorInt vignetteColor: Int): Snow
  /**
   * Snow vignette screen-space corners tint color. Default value: "#ffffff".
   *
   * @param vignetteColor as String
   */
  @MapboxExperimental
  fun vignetteColor(vignetteColor: String = "#ffffff"): Snow

  /**
   * Snow vignette screen-space corners tint color. Default value: "#ffffff".
   *
   * @param vignetteColor value of vignetteColor as Expression
   */
  @MapboxExperimental
  fun vignetteColor(vignetteColor: Expression): Snow

  /**
   * Set the VignetteColor property transition options.
   *
   * @param options transition options for vignette-color
   */
  @MapboxExperimental
  fun vignetteColorTransition(options: StyleTransition): Snow

  /**
   * DSL for [vignetteColorTransition].
   */
  @MapboxExperimental
  fun vignetteColorTransition(block: StyleTransition.Builder.() -> Unit): Snow
}

/**
 * DSL function for creating [Snow] instance.
 */
@MapboxExperimental
fun snow(block: SnowDslReceiver.() -> Unit): Snow {
  return Snow().apply(block)
}

// End of generated file.