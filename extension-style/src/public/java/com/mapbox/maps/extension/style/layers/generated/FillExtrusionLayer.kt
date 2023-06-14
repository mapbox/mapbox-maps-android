// This file is generated.

package com.mapbox.maps.extension.style.layers.generated

import androidx.annotation.ColorInt
import androidx.annotation.UiThread
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.Layer
import com.mapbox.maps.extension.style.layers.properties.*
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.types.*
import com.mapbox.maps.extension.style.utils.ColorUtils.colorIntToRgbaExpression
import com.mapbox.maps.extension.style.utils.ColorUtils.rgbaExpressionToColorInt
import com.mapbox.maps.extension.style.utils.ColorUtils.rgbaExpressionToColorString
import com.mapbox.maps.extension.style.utils.silentUnwrap
import java.util.*

/**
 * An extruded (3D) polygon.
 *
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-fill-extrusion)
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
 */
@UiThread
class FillExtrusionLayer(override val layerId: String, val sourceId: String) : FillExtrusionLayerDsl, Layer() {
  init {
    internalSourceId = sourceId
  }

  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  override fun sourceLayer(sourceLayer: String): FillExtrusionLayer = apply {
    val param = PropertyValue("source-layer", sourceLayer)
    setProperty(param)
  }

  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   */
  val sourceLayer: String?
    /**
     * Get the sourceLayer property
     *
     * @return sourceLayer
     */
    get() {
      return getPropertyValue("source-layer")
    }

  /**
   * A filter is a property at the layer level that determines which features should be rendered in a style layer.
   *
   * Filters are written as expressions, which give you fine-grained control over which features to include: the
   * style layer only displays the features that match the filter condition that you define.
   *
   * Note: Zoom expressions in filters are only evaluated at integer zoom levels. The `feature-state` expression
   * is not supported in filter expressions.
   *
   * @param filter the expression filter to set
   */
  override fun filter(filter: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("filter", filter)
    setProperty(propertyValue)
  }

  /**
   * A filter is a property at the layer level that determines which features should be rendered in a style layer.
   *
   * Filters are written as expressions, which give you fine-grained control over which features to include: the
   * style layer only displays the features that match the filter condition that you define.
   *
   * Note: Zoom expressions in filters are only evaluated at integer zoom levels. The `feature-state` expression
   * is not supported in filter expressions.
   */
  val filter: Expression?
    /**
     * Get the filter property
     *
     * @return filter
     */
    get() = getPropertyValue<Expression>("filter")

  /**
   * Whether this layer is displayed.
   */
  override val visibility: Visibility?
    /**
     * Whether this layer is displayed.
     *
     * Use static method [FillExtrusionLayer.defaultVisibility] to get the default property value.
     *
     * @return VISIBILITY
     */
    get() {
      val property: String? = getPropertyValue("visibility")
      property?.let {
        return Visibility.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Whether this layer is displayed.
   *
   * Use static method [FillExtrusionLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Visibility): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("visibility", visibility)
    setProperty(propertyValue)
  }

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   */
  override val minZoom: Double?
    /**
     * Get the minzoom property
     *
     * Use static method [FillExtrusionLayer.defaultMinZoom] to get the default property value.
     *
     * @return minzoom
     */
    get() {
      return getPropertyValue("minzoom")
    }

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * Use static method [FillExtrusionLayer.defaultMinZoom] to get the default property value.
   *
   * @param minZoom value of minzoom
   */
  override fun minZoom(minZoom: Double): FillExtrusionLayer = apply {
    val param = PropertyValue("minzoom", minZoom)
    setProperty(param)
  }

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   */
  override val maxZoom: Double?
    /**
     * Get the maxzoom property
     *
     * Use static method [FillExtrusionLayer.defaultMaxZoom] to get the default property value.
     *
     * @return maxzoom
     */
    get() {
      return getPropertyValue("maxzoom")
    }

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * Use static method [FillExtrusionLayer.defaultMaxZoom] to get the default property value.
   *
   * @param maxZoom value of maxzoom
   */
  override fun maxZoom(maxZoom: Double): FillExtrusionLayer = apply {
    val param = PropertyValue("maxzoom", maxZoom)
    setProperty(param)
  }

  // Property getters and setters

  /**
   * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance.
   */
  @MapboxExperimental
  val fillExtrusionEdgeRadius: Double?
    /**
     * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionEdgeRadius] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-edge-radius")
    }

  /**
   * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionEdgeRadius] to set the default property.
   *
   * @param fillExtrusionEdgeRadius value of fillExtrusionEdgeRadius
   */
  @MapboxExperimental
  override fun fillExtrusionEdgeRadius(fillExtrusionEdgeRadius: Double): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-edge-radius", fillExtrusionEdgeRadius)
    setProperty(propertyValue)
  }

  /**
   * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance.
   *
   * This is an Expression representation of "fill-extrusion-edge-radius".
   *
   */
  @MapboxExperimental
  val fillExtrusionEdgeRadiusAsExpression: Expression?
    /**
     * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance.
     *
     * Get the FillExtrusionEdgeRadius property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionEdgeRadiusAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-edge-radius")?.let {
        return it
      }
      fillExtrusionEdgeRadius?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionEdgeRadiusAsExpression] to set the default property.
   *
   * @param fillExtrusionEdgeRadius value of fillExtrusionEdgeRadius as Expression
   */
  @MapboxExperimental
  override fun fillExtrusionEdgeRadius(fillExtrusionEdgeRadius: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-edge-radius", fillExtrusionEdgeRadius)
    setProperty(propertyValue)
  }

  /**
   * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings.
   */
  val fillExtrusionAmbientOcclusionIntensity: Double?
    /**
     * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionIntensity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-ambient-occlusion-intensity")
    }

  /**
   * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionIntensity] to set the default property.
   *
   * @param fillExtrusionAmbientOcclusionIntensity value of fillExtrusionAmbientOcclusionIntensity
   */
  override fun fillExtrusionAmbientOcclusionIntensity(fillExtrusionAmbientOcclusionIntensity: Double): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-ambient-occlusion-intensity", fillExtrusionAmbientOcclusionIntensity)
    setProperty(propertyValue)
  }

  /**
   * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings.
   *
   * This is an Expression representation of "fill-extrusion-ambient-occlusion-intensity".
   *
   */
  val fillExtrusionAmbientOcclusionIntensityAsExpression: Expression?
    /**
     * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings.
     *
     * Get the FillExtrusionAmbientOcclusionIntensity property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionIntensityAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-ambient-occlusion-intensity")?.let {
        return it
      }
      fillExtrusionAmbientOcclusionIntensity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionIntensityAsExpression] to set the default property.
   *
   * @param fillExtrusionAmbientOcclusionIntensity value of fillExtrusionAmbientOcclusionIntensity as Expression
   */
  override fun fillExtrusionAmbientOcclusionIntensity(fillExtrusionAmbientOcclusionIntensity: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-ambient-occlusion-intensity", fillExtrusionAmbientOcclusionIntensity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionAmbientOcclusionIntensity.
   */
  val fillExtrusionAmbientOcclusionIntensityTransition: StyleTransition?
    /**
     * Get the FillExtrusionAmbientOcclusionIntensity property transition options
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionIntensityTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-extrusion-ambient-occlusion-intensity-transition")
    }

  /**
   * Set the FillExtrusionAmbientOcclusionIntensity property transition options
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionIntensityTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun fillExtrusionAmbientOcclusionIntensityTransition(options: StyleTransition): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-ambient-occlusion-intensity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionAmbientOcclusionIntensityTransition].
   */
  override fun fillExtrusionAmbientOcclusionIntensityTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer = apply {
    fillExtrusionAmbientOcclusionIntensityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings.
   */
  val fillExtrusionAmbientOcclusionRadius: Double?
    /**
     * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionRadius] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-ambient-occlusion-radius")
    }

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionRadius] to set the default property.
   *
   * @param fillExtrusionAmbientOcclusionRadius value of fillExtrusionAmbientOcclusionRadius
   */
  override fun fillExtrusionAmbientOcclusionRadius(fillExtrusionAmbientOcclusionRadius: Double): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-ambient-occlusion-radius", fillExtrusionAmbientOcclusionRadius)
    setProperty(propertyValue)
  }

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings.
   *
   * This is an Expression representation of "fill-extrusion-ambient-occlusion-radius".
   *
   */
  val fillExtrusionAmbientOcclusionRadiusAsExpression: Expression?
    /**
     * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings.
     *
     * Get the FillExtrusionAmbientOcclusionRadius property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionRadiusAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-ambient-occlusion-radius")?.let {
        return it
      }
      fillExtrusionAmbientOcclusionRadius?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionRadiusAsExpression] to set the default property.
   *
   * @param fillExtrusionAmbientOcclusionRadius value of fillExtrusionAmbientOcclusionRadius as Expression
   */
  override fun fillExtrusionAmbientOcclusionRadius(fillExtrusionAmbientOcclusionRadius: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-ambient-occlusion-radius", fillExtrusionAmbientOcclusionRadius)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionAmbientOcclusionRadius.
   */
  val fillExtrusionAmbientOcclusionRadiusTransition: StyleTransition?
    /**
     * Get the FillExtrusionAmbientOcclusionRadius property transition options
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionRadiusTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-extrusion-ambient-occlusion-radius-transition")
    }

  /**
   * Set the FillExtrusionAmbientOcclusionRadius property transition options
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionRadiusTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun fillExtrusionAmbientOcclusionRadiusTransition(options: StyleTransition): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-ambient-occlusion-radius-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionAmbientOcclusionRadiusTransition].
   */
  override fun fillExtrusionAmbientOcclusionRadiusTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer = apply {
    fillExtrusionAmbientOcclusionRadiusTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
   */
  val fillExtrusionBase: Double?
    /**
     * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionBase] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-base")
    }

  /**
   * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionBase] to set the default property.
   *
   * @param fillExtrusionBase value of fillExtrusionBase
   */
  override fun fillExtrusionBase(fillExtrusionBase: Double): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-base", fillExtrusionBase)
    setProperty(propertyValue)
  }

  /**
   * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
   *
   * This is an Expression representation of "fill-extrusion-base".
   *
   */
  val fillExtrusionBaseAsExpression: Expression?
    /**
     * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
     *
     * Get the FillExtrusionBase property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionBaseAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-base")?.let {
        return it
      }
      fillExtrusionBase?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionBaseAsExpression] to set the default property.
   *
   * @param fillExtrusionBase value of fillExtrusionBase as Expression
   */
  override fun fillExtrusionBase(fillExtrusionBase: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-base", fillExtrusionBase)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionBase.
   */
  val fillExtrusionBaseTransition: StyleTransition?
    /**
     * Get the FillExtrusionBase property transition options
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionBaseTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-extrusion-base-transition")
    }

  /**
   * Set the FillExtrusionBase property transition options
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionBaseTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun fillExtrusionBaseTransition(options: StyleTransition): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-base-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionBaseTransition].
   */
  override fun fillExtrusionBaseTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer = apply {
    fillExtrusionBaseTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
   */
  val fillExtrusionColor: String?
    /**
     * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionColor] to get the default property.
     *
     * @return String
     */
    get() {
      fillExtrusionColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionColor] to set the default property.
   *
   * @param fillExtrusionColor value of fillExtrusionColor
   */
  override fun fillExtrusionColor(fillExtrusionColor: String): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-color", fillExtrusionColor)
    setProperty(propertyValue)
  }

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
   *
   * This is an Expression representation of "fill-extrusion-color".
   *
   */
  val fillExtrusionColorAsExpression: Expression?
    /**
     * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
     *
     * Get the FillExtrusionColor property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionColorAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-color")?.let {
        return it
      }
      return null
    }

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionColorAsExpression] to set the default property.
   *
   * @param fillExtrusionColor value of fillExtrusionColor as Expression
   */
  override fun fillExtrusionColor(fillExtrusionColor: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-color", fillExtrusionColor)
    setProperty(propertyValue)
  }

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
   */
  val fillExtrusionColorAsColorInt: Int?
    /**
     * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionColorAsColorInt] to get the default property.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      fillExtrusionColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionColorAsColorInt] to set the default property.
   *
   * @param fillExtrusionColor value of fillExtrusionColor
   */
  override fun fillExtrusionColor(@ColorInt fillExtrusionColor: Int): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-color", colorIntToRgbaExpression(fillExtrusionColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionColor.
   */
  val fillExtrusionColorTransition: StyleTransition?
    /**
     * Get the FillExtrusionColor property transition options
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionColorTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("fill-extrusion-color-transition")
    }

  /**
   * Set the FillExtrusionColor property transition options
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionColorTransition] to set the default property.
   *
   * @param options transition options for String
   */
  override fun fillExtrusionColorTransition(options: StyleTransition): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionColorTransition].
   */
  override fun fillExtrusionColorTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer = apply {
    fillExtrusionColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The height with which to extrude this layer.
   */
  val fillExtrusionHeight: Double?
    /**
     * The height with which to extrude this layer.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionHeight] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-height")
    }

  /**
   * The height with which to extrude this layer.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionHeight] to set the default property.
   *
   * @param fillExtrusionHeight value of fillExtrusionHeight
   */
  override fun fillExtrusionHeight(fillExtrusionHeight: Double): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-height", fillExtrusionHeight)
    setProperty(propertyValue)
  }

  /**
   * The height with which to extrude this layer.
   *
   * This is an Expression representation of "fill-extrusion-height".
   *
   */
  val fillExtrusionHeightAsExpression: Expression?
    /**
     * The height with which to extrude this layer.
     *
     * Get the FillExtrusionHeight property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionHeightAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-height")?.let {
        return it
      }
      fillExtrusionHeight?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The height with which to extrude this layer.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionHeightAsExpression] to set the default property.
   *
   * @param fillExtrusionHeight value of fillExtrusionHeight as Expression
   */
  override fun fillExtrusionHeight(fillExtrusionHeight: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-height", fillExtrusionHeight)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionHeight.
   */
  val fillExtrusionHeightTransition: StyleTransition?
    /**
     * Get the FillExtrusionHeight property transition options
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionHeightTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-extrusion-height-transition")
    }

  /**
   * Set the FillExtrusionHeight property transition options
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionHeightTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun fillExtrusionHeightTransition(options: StyleTransition): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-height-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionHeightTransition].
   */
  override fun fillExtrusionHeightTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer = apply {
    fillExtrusionHeightTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
   */
  val fillExtrusionOpacity: Double?
    /**
     * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionOpacity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-opacity")
    }

  /**
   * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionOpacity] to set the default property.
   *
   * @param fillExtrusionOpacity value of fillExtrusionOpacity
   */
  override fun fillExtrusionOpacity(fillExtrusionOpacity: Double): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-opacity", fillExtrusionOpacity)
    setProperty(propertyValue)
  }

  /**
   * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
   *
   * This is an Expression representation of "fill-extrusion-opacity".
   *
   */
  val fillExtrusionOpacityAsExpression: Expression?
    /**
     * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
     *
     * Get the FillExtrusionOpacity property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionOpacityAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-opacity")?.let {
        return it
      }
      fillExtrusionOpacity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionOpacityAsExpression] to set the default property.
   *
   * @param fillExtrusionOpacity value of fillExtrusionOpacity as Expression
   */
  override fun fillExtrusionOpacity(fillExtrusionOpacity: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-opacity", fillExtrusionOpacity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionOpacity.
   */
  val fillExtrusionOpacityTransition: StyleTransition?
    /**
     * Get the FillExtrusionOpacity property transition options
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionOpacityTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-extrusion-opacity-transition")
    }

  /**
   * Set the FillExtrusionOpacity property transition options
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionOpacityTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun fillExtrusionOpacityTransition(options: StyleTransition): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionOpacityTransition].
   */
  override fun fillExtrusionOpacityTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer = apply {
    fillExtrusionOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  val fillExtrusionPattern: String?
    /**
     * Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionPattern] to get the default property.
     *
     * @return String
     */
    get() {
      return getPropertyValue<String>("fill-extrusion-pattern")
    }

  /**
   * Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionPattern] to set the default property.
   *
   * @param fillExtrusionPattern value of fillExtrusionPattern
   */
  override fun fillExtrusionPattern(fillExtrusionPattern: String): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-pattern", fillExtrusionPattern)
    setProperty(propertyValue)
  }

  /**
   * Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * This is an Expression representation of "fill-extrusion-pattern".
   *
   */
  val fillExtrusionPatternAsExpression: Expression?
    /**
     * Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
     * Get the FillExtrusionPattern property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionPatternAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-pattern")?.let {
        return it
      }
      fillExtrusionPattern?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionPatternAsExpression] to set the default property.
   *
   * @param fillExtrusionPattern value of fillExtrusionPattern as Expression
   */
  override fun fillExtrusionPattern(fillExtrusionPattern: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-pattern", fillExtrusionPattern)
    setProperty(propertyValue)
  }

  /**
   * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true.
   */
  val fillExtrusionRoundedRoof: Boolean?
    /**
     * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionRoundedRoof] to get the default property.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("fill-extrusion-rounded-roof")
    }

  /**
   * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionRoundedRoof] to set the default property.
   *
   * @param fillExtrusionRoundedRoof value of fillExtrusionRoundedRoof
   */
  override fun fillExtrusionRoundedRoof(fillExtrusionRoundedRoof: Boolean): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-rounded-roof", fillExtrusionRoundedRoof)
    setProperty(propertyValue)
  }

  /**
   * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true.
   *
   * This is an Expression representation of "fill-extrusion-rounded-roof".
   *
   */
  val fillExtrusionRoundedRoofAsExpression: Expression?
    /**
     * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true.
     *
     * Get the FillExtrusionRoundedRoof property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionRoundedRoofAsExpression] to get the default property.
     *
     * @return Boolean
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-rounded-roof")?.let {
        return it
      }
      fillExtrusionRoundedRoof?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionRoundedRoofAsExpression] to set the default property.
   *
   * @param fillExtrusionRoundedRoof value of fillExtrusionRoundedRoof as Expression
   */
  override fun fillExtrusionRoundedRoof(fillExtrusionRoundedRoof: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-rounded-roof", fillExtrusionRoundedRoof)
    setProperty(propertyValue)
  }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
   */
  val fillExtrusionTranslate: List<Double>?
    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionTranslate] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("fill-extrusion-translate")
    }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionTranslate] to set the default property.
   *
   * @param fillExtrusionTranslate value of fillExtrusionTranslate
   */
  override fun fillExtrusionTranslate(fillExtrusionTranslate: List<Double>): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-translate", fillExtrusionTranslate)
    setProperty(propertyValue)
  }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
   *
   * This is an Expression representation of "fill-extrusion-translate".
   *
   */
  val fillExtrusionTranslateAsExpression: Expression?
    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
     *
     * Get the FillExtrusionTranslate property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionTranslateAsExpression] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-translate")?.let {
        return it
      }
      fillExtrusionTranslate?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionTranslateAsExpression] to set the default property.
   *
   * @param fillExtrusionTranslate value of fillExtrusionTranslate as Expression
   */
  override fun fillExtrusionTranslate(fillExtrusionTranslate: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-translate", fillExtrusionTranslate)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionTranslate.
   */
  val fillExtrusionTranslateTransition: StyleTransition?
    /**
     * Get the FillExtrusionTranslate property transition options
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionTranslateTransition] to get the default property.
     *
     * @return transition options for List<Double>
     */
    get() {
      return getPropertyValue("fill-extrusion-translate-transition")
    }

  /**
   * Set the FillExtrusionTranslate property transition options
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionTranslateTransition] to set the default property.
   *
   * @param options transition options for List<Double>
   */
  override fun fillExtrusionTranslateTransition(options: StyleTransition): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-translate-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionTranslateTransition].
   */
  override fun fillExtrusionTranslateTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer = apply {
    fillExtrusionTranslateTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Controls the frame of reference for `fill-extrusion-translate`.
   */
  val fillExtrusionTranslateAnchor: FillExtrusionTranslateAnchor?
    /**
     * Controls the frame of reference for `fill-extrusion-translate`.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionTranslateAnchor] to get the default property.
     *
     * @return FillExtrusionTranslateAnchor
     */
    get() {
      getPropertyValue<String?>("fill-extrusion-translate-anchor")?.let {
        return FillExtrusionTranslateAnchor.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Controls the frame of reference for `fill-extrusion-translate`.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionTranslateAnchor] to set the default property.
   *
   * @param fillExtrusionTranslateAnchor value of fillExtrusionTranslateAnchor
   */
  override fun fillExtrusionTranslateAnchor(fillExtrusionTranslateAnchor: FillExtrusionTranslateAnchor): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-translate-anchor", fillExtrusionTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * Controls the frame of reference for `fill-extrusion-translate`.
   *
   * This is an Expression representation of "fill-extrusion-translate-anchor".
   *
   */
  val fillExtrusionTranslateAnchorAsExpression: Expression?
    /**
     * Controls the frame of reference for `fill-extrusion-translate`.
     *
     * Get the FillExtrusionTranslateAnchor property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionTranslateAnchorAsExpression] to get the default property.
     *
     * @return FillExtrusionTranslateAnchor
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-translate-anchor")?.let {
        return it
      }
      fillExtrusionTranslateAnchor?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Controls the frame of reference for `fill-extrusion-translate`.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionTranslateAnchorAsExpression] to set the default property.
   *
   * @param fillExtrusionTranslateAnchor value of fillExtrusionTranslateAnchor as Expression
   */
  override fun fillExtrusionTranslateAnchor(fillExtrusionTranslateAnchor: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-translate-anchor", fillExtrusionTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down.
   */
  val fillExtrusionVerticalGradient: Boolean?
    /**
     * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionVerticalGradient] to get the default property.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("fill-extrusion-vertical-gradient")
    }

  /**
   * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionVerticalGradient] to set the default property.
   *
   * @param fillExtrusionVerticalGradient value of fillExtrusionVerticalGradient
   */
  override fun fillExtrusionVerticalGradient(fillExtrusionVerticalGradient: Boolean): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-vertical-gradient", fillExtrusionVerticalGradient)
    setProperty(propertyValue)
  }

  /**
   * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down.
   *
   * This is an Expression representation of "fill-extrusion-vertical-gradient".
   *
   */
  val fillExtrusionVerticalGradientAsExpression: Expression?
    /**
     * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down.
     *
     * Get the FillExtrusionVerticalGradient property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionVerticalGradientAsExpression] to get the default property.
     *
     * @return Boolean
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-vertical-gradient")?.let {
        return it
      }
      fillExtrusionVerticalGradient?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionVerticalGradientAsExpression] to set the default property.
   *
   * @param fillExtrusionVerticalGradient value of fillExtrusionVerticalGradient as Expression
   */
  override fun fillExtrusionVerticalGradient(fillExtrusionVerticalGradient: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-vertical-gradient", fillExtrusionVerticalGradient)
    setProperty(propertyValue)
  }

  /**
   * Get the type of this layer
   *
   * @return Type of the layer as [String]
   */
  override fun getType(): String {
    return "fill-extrusion"
  }
  /**
   * Static variables and methods.
   */
  companion object {
    // Default values for layer properties
    /**
     * Visibility of the layer.
     */
    val defaultVisibility: Visibility?
      /**
       * Get the default Visibility property
       *
       * @return VISIBILITY
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "visibility").silentUnwrap<String>()?.let {
          return Visibility.valueOf(it.uppercase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
     *
     * Range:
     *       minimum: 0
     *       maximum: 24
     */
    val defaultMinZoom: Double?
      /**
       * Get the minzoom property
       *
       * @return minzoom
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "minzoom").silentUnwrap()

    /**
     * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
     *
     * Range:
     *       minimum: 0
     *       maximum: 24
     */
    val defaultMaxZoom: Double?
      /**
       * Get the maxzoom property
       *
       * @return maxzoom
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "maxzoom").silentUnwrap()

    /**
     * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance.
     */
    @MapboxExperimental
    val defaultFillExtrusionEdgeRadius: Double?
      /**
       * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance.
       *
       * Get the default value of FillExtrusionEdgeRadius property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-edge-radius").silentUnwrap()
      }

    /**
     * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance.
     *
     * This is an Expression representation of "fill-extrusion-edge-radius".
     *
     */
    @MapboxExperimental
    val defaultFillExtrusionEdgeRadiusAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionEdgeRadius property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-edge-radius").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionEdgeRadius?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings.
     */
    val defaultFillExtrusionAmbientOcclusionIntensity: Double?
      /**
       * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings.
       *
       * Get the default value of FillExtrusionAmbientOcclusionIntensity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-intensity").silentUnwrap()
      }

    /**
     * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings.
     *
     * This is an Expression representation of "fill-extrusion-ambient-occlusion-intensity".
     *
     */
    val defaultFillExtrusionAmbientOcclusionIntensityAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionAmbientOcclusionIntensity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-intensity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionAmbientOcclusionIntensity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionAmbientOcclusionIntensity.
     */
    val defaultFillExtrusionAmbientOcclusionIntensityTransition: StyleTransition?
      /**
       * Get the FillExtrusionAmbientOcclusionIntensity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-intensity-transition").silentUnwrap()

    /**
     * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings.
     */
    val defaultFillExtrusionAmbientOcclusionRadius: Double?
      /**
       * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings.
       *
       * Get the default value of FillExtrusionAmbientOcclusionRadius property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-radius").silentUnwrap()
      }

    /**
     * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings.
     *
     * This is an Expression representation of "fill-extrusion-ambient-occlusion-radius".
     *
     */
    val defaultFillExtrusionAmbientOcclusionRadiusAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionAmbientOcclusionRadius property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-radius").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionAmbientOcclusionRadius?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionAmbientOcclusionRadius.
     */
    val defaultFillExtrusionAmbientOcclusionRadiusTransition: StyleTransition?
      /**
       * Get the FillExtrusionAmbientOcclusionRadius property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-radius-transition").silentUnwrap()

    /**
     * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
     */
    val defaultFillExtrusionBase: Double?
      /**
       * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
       *
       * Get the default value of FillExtrusionBase property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-base").silentUnwrap()
      }

    /**
     * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
     *
     * This is an Expression representation of "fill-extrusion-base".
     *
     */
    val defaultFillExtrusionBaseAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionBase property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-base").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionBase?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionBase.
     */
    val defaultFillExtrusionBaseTransition: StyleTransition?
      /**
       * Get the FillExtrusionBase property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-base-transition").silentUnwrap()

    /**
     * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
     */
    val defaultFillExtrusionColor: String?
      /**
       * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
       *
       * Get the default value of FillExtrusionColor property
       *
       * @return String
       */
      get() {
        defaultFillExtrusionColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
     *
     * This is an Expression representation of "fill-extrusion-color".
     *
     */
    val defaultFillExtrusionColorAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
     */
    val defaultFillExtrusionColorAsColorInt: Int?
      /**
       * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
       *
       * Get the default value of FillExtrusionColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultFillExtrusionColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionColor.
     */
    val defaultFillExtrusionColorTransition: StyleTransition?
      /**
       * Get the FillExtrusionColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-color-transition").silentUnwrap()

    /**
     * The height with which to extrude this layer.
     */
    val defaultFillExtrusionHeight: Double?
      /**
       * The height with which to extrude this layer.
       *
       * Get the default value of FillExtrusionHeight property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-height").silentUnwrap()
      }

    /**
     * The height with which to extrude this layer.
     *
     * This is an Expression representation of "fill-extrusion-height".
     *
     */
    val defaultFillExtrusionHeightAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionHeight property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-height").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionHeight?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionHeight.
     */
    val defaultFillExtrusionHeightTransition: StyleTransition?
      /**
       * Get the FillExtrusionHeight property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-height-transition").silentUnwrap()

    /**
     * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
     */
    val defaultFillExtrusionOpacity: Double?
      /**
       * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
       *
       * Get the default value of FillExtrusionOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-opacity").silentUnwrap()
      }

    /**
     * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
     *
     * This is an Expression representation of "fill-extrusion-opacity".
     *
     */
    val defaultFillExtrusionOpacityAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionOpacity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-opacity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionOpacity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionOpacity.
     */
    val defaultFillExtrusionOpacityTransition: StyleTransition?
      /**
       * Get the FillExtrusionOpacity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-opacity-transition").silentUnwrap()

    /**
     * Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     */
    val defaultFillExtrusionPattern: String?
      /**
       * Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
       *
       * Get the default value of FillExtrusionPattern property
       *
       * @return String
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-pattern").silentUnwrap()
      }

    /**
     * Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
     * This is an Expression representation of "fill-extrusion-pattern".
     *
     */
    val defaultFillExtrusionPatternAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionPattern property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-pattern").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionPattern?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true.
     */
    val defaultFillExtrusionRoundedRoof: Boolean?
      /**
       * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true.
       *
       * Get the default value of FillExtrusionRoundedRoof property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-rounded-roof").silentUnwrap()
      }

    /**
     * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true.
     *
     * This is an Expression representation of "fill-extrusion-rounded-roof".
     *
     */
    val defaultFillExtrusionRoundedRoofAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionRoundedRoof property as an Expression
       *
       * @return Boolean
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-rounded-roof").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionRoundedRoof?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
     */
    val defaultFillExtrusionTranslate: List<Double>?
      /**
       * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
       *
       * Get the default value of FillExtrusionTranslate property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-translate").silentUnwrap()
      }

    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
     *
     * This is an Expression representation of "fill-extrusion-translate".
     *
     */
    val defaultFillExtrusionTranslateAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionTranslate property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-translate").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionTranslate?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionTranslate.
     */
    val defaultFillExtrusionTranslateTransition: StyleTransition?
      /**
       * Get the FillExtrusionTranslate property transition options
       *
       * @return transition options for List<Double>
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-translate-transition").silentUnwrap()

    /**
     * Controls the frame of reference for `fill-extrusion-translate`.
     */
    val defaultFillExtrusionTranslateAnchor: FillExtrusionTranslateAnchor?
      /**
       * Controls the frame of reference for `fill-extrusion-translate`.
       *
       * Get the default value of FillExtrusionTranslateAnchor property
       *
       * @return FillExtrusionTranslateAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-translate-anchor").silentUnwrap<String>()?.let {
          return FillExtrusionTranslateAnchor.valueOf(it.uppercase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Controls the frame of reference for `fill-extrusion-translate`.
     *
     * This is an Expression representation of "fill-extrusion-translate-anchor".
     *
     */
    val defaultFillExtrusionTranslateAnchorAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionTranslateAnchor property as an Expression
       *
       * @return FillExtrusionTranslateAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-translate-anchor").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionTranslateAnchor?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down.
     */
    val defaultFillExtrusionVerticalGradient: Boolean?
      /**
       * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down.
       *
       * Get the default value of FillExtrusionVerticalGradient property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-vertical-gradient").silentUnwrap()
      }

    /**
     * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down.
     *
     * This is an Expression representation of "fill-extrusion-vertical-gradient".
     *
     */
    val defaultFillExtrusionVerticalGradientAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionVerticalGradient property as an Expression
       *
       * @return Boolean
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-vertical-gradient").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionVerticalGradient?.let {
          return Expression.literal(it)
        }
        return null
      }
  }
}

/**
 * This Interface contains all the functions that will be exposed to Koltin DSL.
 *
 * Separated the DSL receiver class to this interface to avoid IDE code suggestion for
 * property getters.
 */
@LayersDsl
interface FillExtrusionLayerDsl {
  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  fun sourceLayer(sourceLayer: String): FillExtrusionLayer

  /**
   * A filter is a property at the layer level that determines which features should be rendered in a style layer.
   *
   * Filters are written as expressions, which give you fine-grained control over which features to include: the
   * style layer only displays the features that match the filter condition that you define.
   *
   * Note: Zoom expressions in filters are only evaluated at integer zoom levels. The `feature-state` expression
   * is not supported in filter expressions.
   *
   * @param filter the expression filter to set
   */
  fun filter(filter: Expression): FillExtrusionLayer

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): FillExtrusionLayer

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param minZoom value of minzoom
   */
  fun minZoom(minZoom: Double): FillExtrusionLayer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param maxZoom value of maxzoom
   */
  fun maxZoom(maxZoom: Double): FillExtrusionLayer

  // Property getters and setters

  /**
   * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance.
   *
   * @param fillExtrusionEdgeRadius value of fillExtrusionEdgeRadius
   */
  @MapboxExperimental
  fun fillExtrusionEdgeRadius(fillExtrusionEdgeRadius: Double = 0.0): FillExtrusionLayer

  /**
   * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance.
   *
   * @param fillExtrusionEdgeRadius value of fillExtrusionEdgeRadius as Expression
   */
  @MapboxExperimental
  fun fillExtrusionEdgeRadius(fillExtrusionEdgeRadius: Expression): FillExtrusionLayer

  /**
   * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings.
   *
   * @param fillExtrusionAmbientOcclusionIntensity value of fillExtrusionAmbientOcclusionIntensity
   */
  fun fillExtrusionAmbientOcclusionIntensity(fillExtrusionAmbientOcclusionIntensity: Double = 0.0): FillExtrusionLayer

  /**
   * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings.
   *
   * @param fillExtrusionAmbientOcclusionIntensity value of fillExtrusionAmbientOcclusionIntensity as Expression
   */
  fun fillExtrusionAmbientOcclusionIntensity(fillExtrusionAmbientOcclusionIntensity: Expression): FillExtrusionLayer

  /**
   * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings.
   *
   * Set the FillExtrusionAmbientOcclusionIntensity property transition options
   *
   * @param options transition options for Double
   */
  fun fillExtrusionAmbientOcclusionIntensityTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings.
   *
   * DSL for [fillExtrusionAmbientOcclusionIntensityTransition].
   */
  fun fillExtrusionAmbientOcclusionIntensityTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings.
   *
   * @param fillExtrusionAmbientOcclusionRadius value of fillExtrusionAmbientOcclusionRadius
   */
  fun fillExtrusionAmbientOcclusionRadius(fillExtrusionAmbientOcclusionRadius: Double = 3.0): FillExtrusionLayer

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings.
   *
   * @param fillExtrusionAmbientOcclusionRadius value of fillExtrusionAmbientOcclusionRadius as Expression
   */
  fun fillExtrusionAmbientOcclusionRadius(fillExtrusionAmbientOcclusionRadius: Expression): FillExtrusionLayer

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings.
   *
   * Set the FillExtrusionAmbientOcclusionRadius property transition options
   *
   * @param options transition options for Double
   */
  fun fillExtrusionAmbientOcclusionRadiusTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings.
   *
   * DSL for [fillExtrusionAmbientOcclusionRadiusTransition].
   */
  fun fillExtrusionAmbientOcclusionRadiusTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
   *
   * @param fillExtrusionBase value of fillExtrusionBase
   */
  fun fillExtrusionBase(fillExtrusionBase: Double = 0.0): FillExtrusionLayer

  /**
   * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
   *
   * @param fillExtrusionBase value of fillExtrusionBase as Expression
   */
  fun fillExtrusionBase(fillExtrusionBase: Expression): FillExtrusionLayer

  /**
   * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
   *
   * Set the FillExtrusionBase property transition options
   *
   * @param options transition options for Double
   */
  fun fillExtrusionBaseTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
   *
   * DSL for [fillExtrusionBaseTransition].
   */
  fun fillExtrusionBaseTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
   *
   * @param fillExtrusionColor value of fillExtrusionColor
   */
  fun fillExtrusionColor(fillExtrusionColor: String = "#000000"): FillExtrusionLayer

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
   *
   * @param fillExtrusionColor value of fillExtrusionColor as Expression
   */
  fun fillExtrusionColor(fillExtrusionColor: Expression): FillExtrusionLayer

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
   *
   * @param fillExtrusionColor value of fillExtrusionColor
   */
  fun fillExtrusionColor(@ColorInt fillExtrusionColor: Int): FillExtrusionLayer

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
   *
   * Set the FillExtrusionColor property transition options
   *
   * @param options transition options for String
   */
  fun fillExtrusionColorTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
   *
   * DSL for [fillExtrusionColorTransition].
   */
  fun fillExtrusionColorTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * The height with which to extrude this layer.
   *
   * @param fillExtrusionHeight value of fillExtrusionHeight
   */
  fun fillExtrusionHeight(fillExtrusionHeight: Double = 0.0): FillExtrusionLayer

  /**
   * The height with which to extrude this layer.
   *
   * @param fillExtrusionHeight value of fillExtrusionHeight as Expression
   */
  fun fillExtrusionHeight(fillExtrusionHeight: Expression): FillExtrusionLayer

  /**
   * The height with which to extrude this layer.
   *
   * Set the FillExtrusionHeight property transition options
   *
   * @param options transition options for Double
   */
  fun fillExtrusionHeightTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * The height with which to extrude this layer.
   *
   * DSL for [fillExtrusionHeightTransition].
   */
  fun fillExtrusionHeightTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
   *
   * @param fillExtrusionOpacity value of fillExtrusionOpacity
   */
  fun fillExtrusionOpacity(fillExtrusionOpacity: Double = 1.0): FillExtrusionLayer

  /**
   * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
   *
   * @param fillExtrusionOpacity value of fillExtrusionOpacity as Expression
   */
  fun fillExtrusionOpacity(fillExtrusionOpacity: Expression): FillExtrusionLayer

  /**
   * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
   *
   * Set the FillExtrusionOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun fillExtrusionOpacityTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
   *
   * DSL for [fillExtrusionOpacityTransition].
   */
  fun fillExtrusionOpacityTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * @param fillExtrusionPattern value of fillExtrusionPattern
   */
  fun fillExtrusionPattern(fillExtrusionPattern: String): FillExtrusionLayer

  /**
   * Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * @param fillExtrusionPattern value of fillExtrusionPattern as Expression
   */
  fun fillExtrusionPattern(fillExtrusionPattern: Expression): FillExtrusionLayer

  /**
   * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true.
   *
   * @param fillExtrusionRoundedRoof value of fillExtrusionRoundedRoof
   */
  fun fillExtrusionRoundedRoof(fillExtrusionRoundedRoof: Boolean = true): FillExtrusionLayer

  /**
   * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true.
   *
   * @param fillExtrusionRoundedRoof value of fillExtrusionRoundedRoof as Expression
   */
  fun fillExtrusionRoundedRoof(fillExtrusionRoundedRoof: Expression): FillExtrusionLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
   *
   * @param fillExtrusionTranslate value of fillExtrusionTranslate
   */
  fun fillExtrusionTranslate(fillExtrusionTranslate: List<Double> = listOf(0.0, 0.0)): FillExtrusionLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
   *
   * @param fillExtrusionTranslate value of fillExtrusionTranslate as Expression
   */
  fun fillExtrusionTranslate(fillExtrusionTranslate: Expression): FillExtrusionLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
   *
   * Set the FillExtrusionTranslate property transition options
   *
   * @param options transition options for List<Double>
   */
  fun fillExtrusionTranslateTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
   *
   * DSL for [fillExtrusionTranslateTransition].
   */
  fun fillExtrusionTranslateTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * Controls the frame of reference for `fill-extrusion-translate`.
   *
   * @param fillExtrusionTranslateAnchor value of fillExtrusionTranslateAnchor
   */
  fun fillExtrusionTranslateAnchor(fillExtrusionTranslateAnchor: FillExtrusionTranslateAnchor = FillExtrusionTranslateAnchor.MAP): FillExtrusionLayer

  /**
   * Controls the frame of reference for `fill-extrusion-translate`.
   *
   * @param fillExtrusionTranslateAnchor value of fillExtrusionTranslateAnchor as Expression
   */
  fun fillExtrusionTranslateAnchor(fillExtrusionTranslateAnchor: Expression): FillExtrusionLayer

  /**
   * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down.
   *
   * @param fillExtrusionVerticalGradient value of fillExtrusionVerticalGradient
   */
  fun fillExtrusionVerticalGradient(fillExtrusionVerticalGradient: Boolean = true): FillExtrusionLayer

  /**
   * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down.
   *
   * @param fillExtrusionVerticalGradient value of fillExtrusionVerticalGradient as Expression
   */
  fun fillExtrusionVerticalGradient(fillExtrusionVerticalGradient: Expression): FillExtrusionLayer
}

/**
 * DSL function for creating a [FillExtrusionLayer].
 */
fun fillExtrusionLayer(layerId: String, sourceId: String, block: FillExtrusionLayerDsl.() -> Unit): FillExtrusionLayer = FillExtrusionLayer(layerId, sourceId).apply(block)

// End of generated file.