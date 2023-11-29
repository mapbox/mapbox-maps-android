// This file is generated.

package com.mapbox.maps.extension.style.light.generated

import androidx.annotation.ColorInt
import androidx.annotation.UiThread
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.layers.properties.generated.Anchor
import com.mapbox.maps.extension.style.light.Light
import com.mapbox.maps.extension.style.light.LightPosition
import com.mapbox.maps.extension.style.light.setLight
import com.mapbox.maps.extension.style.types.LightDsl
import com.mapbox.maps.extension.style.types.StyleTransition
import com.mapbox.maps.extension.style.utils.ColorUtils.colorIntToRgbaExpression
import com.mapbox.maps.extension.style.utils.ColorUtils.rgbaExpressionToColorInt
import com.mapbox.maps.extension.style.utils.ColorUtils.rgbaExpressionToColorString
import java.util.Locale

/**
 * A global directional light source which is only applied on 3D layers and hillshade layers. Using this type disables other light sources.
 *
 * Check the [online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#light).
 */
@UiThread
class FlatLight internal constructor(override val lightId: String) : FlatLightDslReceiver, StyleContract.StyleLightExtension, Light() {

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
        return Anchor.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }
  /**
   * Whether extruded geometries are lit relative to the map or viewport.
   *
   * @param anchor as Anchor
   */
  override fun anchor(anchor: Anchor): FlatLight = apply {
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
  override fun anchor(anchor: Expression): FlatLight = apply {
    val propertyValue = PropertyValue("anchor", anchor)
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
  override fun color(@ColorInt color: Int): FlatLight = apply {
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
  override fun color(color: String): FlatLight = apply {
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
  override fun color(color: Expression): FlatLight = apply {
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
  override fun colorTransition(options: StyleTransition): FlatLight = apply {
    val propertyValue = PropertyValue("color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [colorTransition].
   */
  override fun colorTransition(block: StyleTransition.Builder.() -> Unit): FlatLight = apply {
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
  override fun intensity(intensity: Double): FlatLight = apply {
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
  override fun intensity(intensity: Expression): FlatLight = apply {
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
  override fun intensityTransition(options: StyleTransition): FlatLight = apply {
    val propertyValue = PropertyValue("intensity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [intensityTransition].
   */
  override fun intensityTransition(block: StyleTransition.Builder.() -> Unit): FlatLight = apply {
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
  override fun position(radialCoordinate: Double, azimuthalAngle: Double, polarAngle: Double): FlatLight = apply {
    position(LightPosition(radialCoordinate, azimuthalAngle, polarAngle))
  }
  /**
   * Position of the light source relative to lit (extruded) geometries, in [r radial coordinate, a azimuthal angle, p polar angle] where r indicates the distance from the center of the base of an object to its light, a indicates the position of the light relative to 0 degree (0 degree when `light.anchor` is set to `viewport` corresponds to the top of the viewport, or 0 degree when `light.anchor` is set to `map` corresponds to due north, and degrees proceed clockwise), and p indicates the height of the light (from 0 degree, directly above, to 180 degree, directly below).
   *
   * @param position as LightPosition
   */
  override fun position(position: LightPosition): FlatLight = apply {
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
  override fun position(position: Expression): FlatLight = apply {
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
  override fun positionTransition(options: StyleTransition): FlatLight = apply {
    val propertyValue = PropertyValue("position-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [positionTransition].
   */
  override fun positionTransition(block: StyleTransition.Builder.() -> Unit): FlatLight = apply {
    positionTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Get the type of this light
   *
   * @return Type of the light as [String]
   */
  override fun getType(): String {
    return "flat"
  }

  /**
   * Bind the light to the Style.
   *
   * @param delegate The style delegate
   */
  override fun bindTo(delegate: MapboxStyleManager) {
    delegate.setLight(this)
  }
}

/**
 * This Interface contains all the functions that will be exposed to Koltin DSL.
 *
 * Separated the DSL receiver class to this interface to avoid IDE code suggestion for
 * property getters.
 */
@LightDsl
interface FlatLightDslReceiver {

  /**
   * Whether extruded geometries are lit relative to the map or viewport.
   *
   * @param anchor as Anchor
   */
  fun anchor(anchor: Anchor = Anchor.VIEWPORT): FlatLight

  /**
   * Whether extruded geometries are lit relative to the map or viewport.
   *
   * @param anchor value of anchor as Expression
   */
  fun anchor(anchor: Expression): FlatLight

  /**
   * Color tint for lighting extruded geometries.
   *
   * @param color as int
   */
  fun color(@ColorInt color: Int): FlatLight

  /**
   * Color tint for lighting extruded geometries.
   *
   * @param color as String
   */
  fun color(color: String = "#ffffff"): FlatLight

  /**
   * Color tint for lighting extruded geometries.
   *
   * @param color value of color as Expression
   */
  fun color(color: Expression): FlatLight

  /**
   * Set the Color property transition options.
   *
   * @param options transition options for color
   */
  fun colorTransition(options: StyleTransition): FlatLight

  /**
   * DSL for [colorTransition].
   */
  fun colorTransition(block: StyleTransition.Builder.() -> Unit): FlatLight

  /**
   * Intensity of lighting (on a scale from 0 to 1). Higher numbers will present as more extreme contrast.
   *
   * @param intensity as Double
   */
  fun intensity(intensity: Double = 0.5): FlatLight

  /**
   * Intensity of lighting (on a scale from 0 to 1). Higher numbers will present as more extreme contrast.
   *
   * @param intensity value of intensity as Expression
   */
  fun intensity(intensity: Expression): FlatLight

  /**
   * Set the Intensity property transition options.
   *
   * @param options transition options for intensity
   */
  fun intensityTransition(options: StyleTransition): FlatLight

  /**
   * DSL for [intensityTransition].
   */
  fun intensityTransition(block: StyleTransition.Builder.() -> Unit): FlatLight

  /**
   * Position of the light source relative to lit (extruded) geometries, in [r radial coordinate, a azimuthal angle, p polar angle] where r indicates the distance from the center of the base of an object to its light, a indicates the position of the light relative to 0 degree (0 degree when `light.anchor` is set to `viewport` corresponds to the top of the viewport, or 0 degree when `light.anchor` is set to `map` corresponds to due north, and degrees proceed clockwise), and p indicates the height of the light (from 0 degree, directly above, to 180 degree, directly below).
   *
   * DSL for setting [LightPosition].
   */
  fun position(radialCoordinate: Double, azimuthalAngle: Double, polarAngle: Double): FlatLight

  /**
   * Position of the light source relative to lit (extruded) geometries, in [r radial coordinate, a azimuthal angle, p polar angle] where r indicates the distance from the center of the base of an object to its light, a indicates the position of the light relative to 0 degree (0 degree when `light.anchor` is set to `viewport` corresponds to the top of the viewport, or 0 degree when `light.anchor` is set to `map` corresponds to due north, and degrees proceed clockwise), and p indicates the height of the light (from 0 degree, directly above, to 180 degree, directly below).
   *
   * @param position as LightPosition
   */
  fun position(position: LightPosition = LightPosition(1.15, 210.0, 30.0)): FlatLight

  /**
   * Position of the light source relative to lit (extruded) geometries, in [r radial coordinate, a azimuthal angle, p polar angle] where r indicates the distance from the center of the base of an object to its light, a indicates the position of the light relative to 0 degree (0 degree when `light.anchor` is set to `viewport` corresponds to the top of the viewport, or 0 degree when `light.anchor` is set to `map` corresponds to due north, and degrees proceed clockwise), and p indicates the height of the light (from 0 degree, directly above, to 180 degree, directly below).
   *
   * @param position value of position as Expression
   */
  fun position(position: Expression): FlatLight

  /**
   * Set the Position property transition options.
   *
   * @param options transition options for position
   */
  fun positionTransition(options: StyleTransition): FlatLight

  /**
   * DSL for [positionTransition].
   */
  fun positionTransition(block: StyleTransition.Builder.() -> Unit): FlatLight
}

/**
 * DSL function for creating [flatLight] instance.
 */
fun flatLight(id: String = "flat", block: FlatLightDslReceiver.() -> Unit): FlatLight = FlatLight(id).apply(block)

// End of generated file.