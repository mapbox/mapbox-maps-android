// This file is generated.

package com.mapbox.maps.extension.style.light.generated

import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.UiThread
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleException
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.layers.properties.generated.Anchor
import com.mapbox.maps.extension.style.light.LightPosition
import com.mapbox.maps.extension.style.types.LightDsl
import com.mapbox.maps.extension.style.types.StyleTransition
import com.mapbox.maps.extension.style.utils.ColorUtils.colorIntToRgbaExpression
import com.mapbox.maps.extension.style.utils.ColorUtils.rgbaExpressionToColorInt
import com.mapbox.maps.extension.style.utils.ColorUtils.rgbaExpressionToColorString
import com.mapbox.maps.extension.style.utils.unwrap
import java.util.*
import kotlin.collections.HashMap

/**
 * The global light source.
 *
 * Check the [online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#light).
 */
@UiThread
class Light : LightDslReceiver, StyleContract.StyleLightExtension {
  private var delegate: StyleInterface? = null
  private val properties = HashMap<String, PropertyValue<*>>()

  /**
   * Whether extruded geometries are lit relative to the map or viewport.
   */
  val anchor: Anchor?
    /**
     * Whether extruded geometries are lit relative to the map or viewport.
     *
     * @return anchor as Anchor
     */
    get() {
      getPropertyValue<String>("anchor")?.let {
        return Anchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }
  /**
   * Whether extruded geometries are lit relative to the map or viewport.
   *
   * @param anchor as Anchor
   */
  override fun anchor(anchor: Anchor) = apply {
    setProperty(PropertyValue("anchor", anchor))
  }

  /**
   * Whether extruded geometries are lit relative to the map or viewport.
   *
   * This is an Expression representation of "anchor".
   */
  val anchorAsExpression: Expression?
    /**
     * Whether extruded geometries are lit relative to the map or viewport.
     *
     * Get the Anchor property as an Expression
     *
     * @return Anchor
     */
    get() {
      getPropertyValue<Expression>("anchor")?.let {
        return it
      }
      anchor?.let {
        return Expression.literal(it.value)
      }
      return null
    }
  /**
   * Whether extruded geometries are lit relative to the map or viewport.
   *
   * @param anchor value of anchor as Expression
   */
  override fun anchor(anchor: Expression) = apply {
    val propertyValue = PropertyValue("anchor", anchor)
    setProperty(propertyValue)
  }
  /**
   * Enable/Disable shadow casting for this light
   */
  @MapboxExperimental
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
  @MapboxExperimental
  override fun castShadows(castShadows: Boolean) = apply {
    setProperty(PropertyValue("cast-shadows", castShadows))
  }

  /**
   * Enable/Disable shadow casting for this light
   *
   * This is an Expression representation of "cast-shadows".
   */
  @MapboxExperimental
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
  @MapboxExperimental
  override fun castShadows(castShadows: Expression) = apply {
    val propertyValue = PropertyValue("cast-shadows", castShadows)
    setProperty(propertyValue)
  }
  /**
   * Color tint for lighting extruded geometries.
   */
  val colorAsColorInt: Int?
    /**
     * Color tint for lighting extruded geometries.
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
   * Color tint for lighting extruded geometries.
   *
   * @param color as int
   */
  override fun color(@ColorInt color: Int) = apply {
    val propertyValue = PropertyValue("color", colorIntToRgbaExpression(color))
    setProperty(propertyValue)
  }
  /**
   * Color tint for lighting extruded geometries.
   */
  val color: String?
    /**
     * Color tint for lighting extruded geometries.
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
   * Color tint for lighting extruded geometries.
   *
   * @param color as String
   */
  override fun color(color: String) = apply {
    setProperty(PropertyValue("color", color))
  }

  /**
   * Color tint for lighting extruded geometries.
   *
   * This is an Expression representation of "color".
   */
  val colorAsExpression: Expression?
    /**
     * Color tint for lighting extruded geometries.
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
   * Color tint for lighting extruded geometries.
   *
   * @param color value of color as Expression
   */
  override fun color(color: Expression) = apply {
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
  override fun colorTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [colorTransition].
   */
  override fun colorTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    colorTransition(StyleTransition.Builder().apply(block).build())
  }
  /**
   * Intensity of lighting (on a scale from 0 to 1). Higher numbers will present as more extreme contrast.
   */
  val intensity: Double?
    /**
     * Intensity of lighting (on a scale from 0 to 1). Higher numbers will present as more extreme contrast.
     *
     * @return intensity as Double
     */
    get() {
      return getPropertyValue("intensity")
    }
  /**
   * Intensity of lighting (on a scale from 0 to 1). Higher numbers will present as more extreme contrast.
   *
   * @param intensity as Double
   */
  override fun intensity(intensity: Double) = apply {
    setProperty(PropertyValue("intensity", intensity))
  }

  /**
   * Intensity of lighting (on a scale from 0 to 1). Higher numbers will present as more extreme contrast.
   *
   * This is an Expression representation of "intensity".
   */
  val intensityAsExpression: Expression?
    /**
     * Intensity of lighting (on a scale from 0 to 1). Higher numbers will present as more extreme contrast.
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
   * Intensity of lighting (on a scale from 0 to 1). Higher numbers will present as more extreme contrast.
   *
   * @param intensity value of intensity as Expression
   */
  override fun intensity(intensity: Expression) = apply {
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
  override fun intensityTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("intensity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [intensityTransition].
   */
  override fun intensityTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    intensityTransition(StyleTransition.Builder().apply(block).build())
  }
  /**
   * Position of the light source relative to lit (extruded) geometries, in [r radial coordinate, a azimuthal angle, p polar angle] where r indicates the distance from the center of the base of an object to its light, a indicates the position of the light relative to 0 degree (0 degree when `light.anchor` is set to `viewport` corresponds to the top of the viewport, or 0 degree when `light.anchor` is set to `map` corresponds to due north, and degrees proceed clockwise), and p indicates the height of the light (from 0 degree, directly above, to 180 degree, directly below).
   */
  val position: LightPosition?
    /**
     * Position of the light source relative to lit (extruded) geometries, in [r radial coordinate, a azimuthal angle, p polar angle] where r indicates the distance from the center of the base of an object to its light, a indicates the position of the light relative to 0 degree (0 degree when `light.anchor` is set to `viewport` corresponds to the top of the viewport, or 0 degree when `light.anchor` is set to `map` corresponds to due north, and degrees proceed clockwise), and p indicates the height of the light (from 0 degree, directly above, to 180 degree, directly below).
     *
     * @return position as LightPosition
     */
    get() {
      getPropertyValue<List<Double>>("position")?.let {
        return LightPosition.fromList(it)
      }
      return null
    }

  /**
   * Position of the light source relative to lit (extruded) geometries, in [r radial coordinate, a azimuthal angle, p polar angle] where r indicates the distance from the center of the base of an object to its light, a indicates the position of the light relative to 0 degree (0 degree when `light.anchor` is set to `viewport` corresponds to the top of the viewport, or 0 degree when `light.anchor` is set to `map` corresponds to due north, and degrees proceed clockwise), and p indicates the height of the light (from 0 degree, directly above, to 180 degree, directly below).
   *
   * DSL for setting [LightPosition].
   */
  override fun position(radialCoordinate: Double, azimuthalAngle: Double, polarAngle: Double) = apply {
    position(LightPosition(radialCoordinate, azimuthalAngle, polarAngle))
  }
  /**
   * Position of the light source relative to lit (extruded) geometries, in [r radial coordinate, a azimuthal angle, p polar angle] where r indicates the distance from the center of the base of an object to its light, a indicates the position of the light relative to 0 degree (0 degree when `light.anchor` is set to `viewport` corresponds to the top of the viewport, or 0 degree when `light.anchor` is set to `map` corresponds to due north, and degrees proceed clockwise), and p indicates the height of the light (from 0 degree, directly above, to 180 degree, directly below).
   *
   * @param position as LightPosition
   */
  override fun position(position: LightPosition) = apply {
    setProperty(PropertyValue("position", position))
  }

  /**
   * Position of the light source relative to lit (extruded) geometries, in [r radial coordinate, a azimuthal angle, p polar angle] where r indicates the distance from the center of the base of an object to its light, a indicates the position of the light relative to 0 degree (0 degree when `light.anchor` is set to `viewport` corresponds to the top of the viewport, or 0 degree when `light.anchor` is set to `map` corresponds to due north, and degrees proceed clockwise), and p indicates the height of the light (from 0 degree, directly above, to 180 degree, directly below).
   *
   * This is an Expression representation of "position".
   */
  val positionAsExpression: Expression?
    /**
     * Position of the light source relative to lit (extruded) geometries, in [r radial coordinate, a azimuthal angle, p polar angle] where r indicates the distance from the center of the base of an object to its light, a indicates the position of the light relative to 0 degree (0 degree when `light.anchor` is set to `viewport` corresponds to the top of the viewport, or 0 degree when `light.anchor` is set to `map` corresponds to due north, and degrees proceed clockwise), and p indicates the height of the light (from 0 degree, directly above, to 180 degree, directly below).
     *
     * Get the Position property as an Expression
     *
     * @return LightPosition
     */
    get() {
      getPropertyValue<Expression>("position")?.let {
        return it
      }
      position?.let {
        return Expression.literal(it.toList())
      }
      return null
    }
  /**
   * Position of the light source relative to lit (extruded) geometries, in [r radial coordinate, a azimuthal angle, p polar angle] where r indicates the distance from the center of the base of an object to its light, a indicates the position of the light relative to 0 degree (0 degree when `light.anchor` is set to `viewport` corresponds to the top of the viewport, or 0 degree when `light.anchor` is set to `map` corresponds to due north, and degrees proceed clockwise), and p indicates the height of the light (from 0 degree, directly above, to 180 degree, directly below).
   *
   * @param position value of position as Expression
   */
  override fun position(position: Expression) = apply {
    val propertyValue = PropertyValue("position", position)
    setProperty(propertyValue)
  }
  /**
   * Position property transition options.
   */
  val positionTransition: StyleTransition?
    /**
     * Get the Position property transition options.
     *
     * @return transition options for position
     */
    get() {
      return getTransitionProperty("position-transition")
    }
  /**
   * Set the Position property transition options.
   *
   * @param options transition options for position
   */
  override fun positionTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("position-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [positionTransition].
   */
  override fun positionTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    positionTransition(StyleTransition.Builder().apply(block).build())
  }
  /**
   * Determines the shadow strength, affecting the shadow receiver surfaces final color. Values near 0.0 reduce the shadow contribution to the final color. Values near to 1.0 make occluded surfaces almost black.
   */
  @MapboxExperimental
  val shadowIntensity: Double?
    /**
     * Determines the shadow strength, affecting the shadow receiver surfaces final color. Values near 0.0 reduce the shadow contribution to the final color. Values near to 1.0 make occluded surfaces almost black.
     *
     * @return shadowIntensity as Double
     */
    get() {
      return getPropertyValue("shadow-intensity")
    }
  /**
   * Determines the shadow strength, affecting the shadow receiver surfaces final color. Values near 0.0 reduce the shadow contribution to the final color. Values near to 1.0 make occluded surfaces almost black.
   *
   * @param shadowIntensity as Double
   */
  @MapboxExperimental
  override fun shadowIntensity(shadowIntensity: Double) = apply {
    setProperty(PropertyValue("shadow-intensity", shadowIntensity))
  }

  /**
   * Determines the shadow strength, affecting the shadow receiver surfaces final color. Values near 0.0 reduce the shadow contribution to the final color. Values near to 1.0 make occluded surfaces almost black.
   *
   * This is an Expression representation of "shadow-intensity".
   */
  @MapboxExperimental
  val shadowIntensityAsExpression: Expression?
    /**
     * Determines the shadow strength, affecting the shadow receiver surfaces final color. Values near 0.0 reduce the shadow contribution to the final color. Values near to 1.0 make occluded surfaces almost black.
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
   * Determines the shadow strength, affecting the shadow receiver surfaces final color. Values near 0.0 reduce the shadow contribution to the final color. Values near to 1.0 make occluded surfaces almost black.
   *
   * @param shadowIntensity value of shadowIntensity as Expression
   */
  @MapboxExperimental
  override fun shadowIntensity(shadowIntensity: Expression) = apply {
    val propertyValue = PropertyValue("shadow-intensity", shadowIntensity)
    setProperty(propertyValue)
  }
  /**
   * ShadowIntensity property transition options.
   */
  @MapboxExperimental
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
  @MapboxExperimental
  override fun shadowIntensityTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("shadow-intensity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [shadowIntensityTransition].
   */
  @MapboxExperimental
  override fun shadowIntensityTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    shadowIntensityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Bind the light to the map controller.
   *
   * @param delegate The map controller
   */
  override fun bindTo(delegate: StyleInterface) {
    this.delegate = delegate
    val lightParams = HashMap<String, Value>()
    properties.forEach {
      lightParams[it.key] = it.value.value
    }
    val expected = delegate.setStyleLight(Value(lightParams))
    expected.error?.let {
      throw MapboxStyleException("Set light failed: $it")
    }
  }

  private fun setProperty(property: PropertyValue<*>) {
    properties[property.propertyName] = property
    updateProperty(property)
  }

  private fun updateProperty(propertyValue: PropertyValue<*>) {
    val expected = delegate?.setStyleLightProperty(
      propertyValue.propertyName,
      propertyValue.value
    )
    expected?.error?.let {
      throw MapboxStyleException("Set light property failed: $it")
    }
  }

  private inline fun <reified T> getPropertyValue(propertyName: String): T? {
    delegate?.let {
      return try {
        it.getStyleLightProperty(propertyName).unwrap()
      } catch (e: RuntimeException) {
        Log.e(TAG, "Get light property failed: ${e.message}")
        Log.e(TAG, it.getStyleLightProperty(propertyName).toString())
        null
      }
    }
    throw MapboxStyleException("Get property $propertyName failed: light is not added to style yet.")
  }

  private fun getTransitionProperty(transitionName: String): StyleTransition? {
    delegate?.let {
      return try {
        @Suppress("UNCHECKED_CAST")
        val styleLayerProperty =
          it.getStyleLightProperty(transitionName).value.contents as HashMap<String, Value>
        val duration = styleLayerProperty["duration"]?.contents as Long
        val delay = styleLayerProperty["delay"]?.contents as Long
        StyleTransition.Builder().delay(delay).duration(duration).build()
      } catch (e: RuntimeException) {
        Log.e(TAG, "Get light property failed: ${e.message}")
        Log.e(TAG, it.getStyleLightProperty(transitionName).toString())
        null
      }
    }
    throw MapboxStyleException("Get property $transitionName failed: light is not added to style yet.")
  }
  /**
   * Static variables and methods.
   */
  companion object {
    private const val TAG = "Mbgl-Light"
  }
}

/**
 * This Interface contains all the functions that will be exposed to Koltin DSL.
 *
 * Separated the DSL receiver class to this interface to avoid IDE code suggestion for
 * property getters.
 */
@LightDsl
interface LightDslReceiver {

  /**
   * Whether extruded geometries are lit relative to the map or viewport.
   *
   * @param anchor as Anchor
   */
  fun anchor(anchor: Anchor = Anchor.VIEWPORT): Light

  /**
   * Whether extruded geometries are lit relative to the map or viewport.
   *
   * @param anchor value of anchor as Expression
   */
  fun anchor(anchor: Expression): Light

  /**
   * Enable/Disable shadow casting for this light
   *
   * @param castShadows as Boolean
   */
  @MapboxExperimental
  fun castShadows(castShadows: Boolean = false): Light

  /**
   * Enable/Disable shadow casting for this light
   *
   * @param castShadows value of castShadows as Expression
   */
  @MapboxExperimental
  fun castShadows(castShadows: Expression): Light

  /**
   * Color tint for lighting extruded geometries.
   *
   * @param color as int
   */
  fun color(@ColorInt color: Int): Light

  /**
   * Color tint for lighting extruded geometries.
   *
   * @param color as String
   */
  fun color(color: String = "#ffffff"): Light

  /**
   * Color tint for lighting extruded geometries.
   *
   * @param color value of color as Expression
   */
  fun color(color: Expression): Light

  /**
   * Set the Color property transition options.
   *
   * @param options transition options for color
   */
  fun colorTransition(options: StyleTransition): Light

  /**
   * DSL for [colorTransition].
   */
  fun colorTransition(block: StyleTransition.Builder.() -> Unit): Light

  /**
   * Intensity of lighting (on a scale from 0 to 1). Higher numbers will present as more extreme contrast.
   *
   * @param intensity as Double
   */
  fun intensity(intensity: Double = 0.5): Light

  /**
   * Intensity of lighting (on a scale from 0 to 1). Higher numbers will present as more extreme contrast.
   *
   * @param intensity value of intensity as Expression
   */
  fun intensity(intensity: Expression): Light

  /**
   * Set the Intensity property transition options.
   *
   * @param options transition options for intensity
   */
  fun intensityTransition(options: StyleTransition): Light

  /**
   * DSL for [intensityTransition].
   */
  fun intensityTransition(block: StyleTransition.Builder.() -> Unit): Light

  /**
   * Position of the light source relative to lit (extruded) geometries, in [r radial coordinate, a azimuthal angle, p polar angle] where r indicates the distance from the center of the base of an object to its light, a indicates the position of the light relative to 0 degree (0 degree when `light.anchor` is set to `viewport` corresponds to the top of the viewport, or 0 degree when `light.anchor` is set to `map` corresponds to due north, and degrees proceed clockwise), and p indicates the height of the light (from 0 degree, directly above, to 180 degree, directly below).
   *
   * DSL for setting [LightPosition].
   */
  fun position(radialCoordinate: Double, azimuthalAngle: Double, polarAngle: Double): Light

  /**
   * Position of the light source relative to lit (extruded) geometries, in [r radial coordinate, a azimuthal angle, p polar angle] where r indicates the distance from the center of the base of an object to its light, a indicates the position of the light relative to 0 degree (0 degree when `light.anchor` is set to `viewport` corresponds to the top of the viewport, or 0 degree when `light.anchor` is set to `map` corresponds to due north, and degrees proceed clockwise), and p indicates the height of the light (from 0 degree, directly above, to 180 degree, directly below).
   *
   * @param position as LightPosition
   */
  fun position(position: LightPosition = LightPosition(1.15, 210.0, 30.0)): Light

  /**
   * Position of the light source relative to lit (extruded) geometries, in [r radial coordinate, a azimuthal angle, p polar angle] where r indicates the distance from the center of the base of an object to its light, a indicates the position of the light relative to 0 degree (0 degree when `light.anchor` is set to `viewport` corresponds to the top of the viewport, or 0 degree when `light.anchor` is set to `map` corresponds to due north, and degrees proceed clockwise), and p indicates the height of the light (from 0 degree, directly above, to 180 degree, directly below).
   *
   * @param position value of position as Expression
   */
  fun position(position: Expression): Light

  /**
   * Set the Position property transition options.
   *
   * @param options transition options for position
   */
  fun positionTransition(options: StyleTransition): Light

  /**
   * DSL for [positionTransition].
   */
  fun positionTransition(block: StyleTransition.Builder.() -> Unit): Light

  /**
   * Determines the shadow strength, affecting the shadow receiver surfaces final color. Values near 0.0 reduce the shadow contribution to the final color. Values near to 1.0 make occluded surfaces almost black.
   *
   * @param shadowIntensity as Double
   */
  @MapboxExperimental
  fun shadowIntensity(shadowIntensity: Double = 0.2): Light

  /**
   * Determines the shadow strength, affecting the shadow receiver surfaces final color. Values near 0.0 reduce the shadow contribution to the final color. Values near to 1.0 make occluded surfaces almost black.
   *
   * @param shadowIntensity value of shadowIntensity as Expression
   */
  @MapboxExperimental
  fun shadowIntensity(shadowIntensity: Expression): Light

  /**
   * Set the ShadowIntensity property transition options.
   *
   * @param options transition options for shadow-intensity
   */
  @MapboxExperimental
  fun shadowIntensityTransition(options: StyleTransition): Light

  /**
   * DSL for [shadowIntensityTransition].
   */
  @MapboxExperimental
  fun shadowIntensityTransition(block: StyleTransition.Builder.() -> Unit): Light
}

/**
 * DSL function for creating [Light] instance.
 */
fun light(block: LightDslReceiver.() -> Unit): Light = Light().apply(block)

// End of generated file.