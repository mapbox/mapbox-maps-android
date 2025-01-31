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
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers/#fill-extrusion)
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
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   *
   * @param slot value of slot. Setting it to empty string removes the slot.
   */
  override fun slot(slot: String): FillExtrusionLayer = apply {
    val param = PropertyValue("slot", slot)
    setProperty(param)
  }

  /**
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   */
  override val slot: String?
    /**
     * Get the slot property
     *
     * @return slot
     */
    get() {
      return getPropertyValue("slot")
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
   */
  override val visibilityAsExpression: Expression?
    /**
     * Whether this layer is displayed.
     *
     * Use static method [FillExtrusionLayer.defaultVisibility] to get the default property value.
     *
     * @return VISIBILITY as expression
     */
    get() {
      getPropertyValue<Expression>("visibility")?.let {
        return it
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
   * Whether this layer is displayed.
   *
   * Use static method [[FillExtrusionLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Expression): FillExtrusionLayer = apply {
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
   * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance. Default value: 0. Value range: [0, 1]
   */
  @MapboxExperimental
  val fillExtrusionEdgeRadius: Double?
    /**
     * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance. Default value: 0. Value range: [0, 1]
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionEdgeRadius] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-edge-radius")
    }

  /**
   * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance. Default value: 0. Value range: [0, 1]
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
   * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance. Default value: 0. Value range: [0, 1]
   *
   * This is an Expression representation of "fill-extrusion-edge-radius".
   *
   */
  @MapboxExperimental
  val fillExtrusionEdgeRadiusAsExpression: Expression?
    /**
     * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance. Default value: 0. Value range: [0, 1]
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
   * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance. Default value: 0. Value range: [0, 1]
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
   * Provides a control to futher fine-tune the look of the ambient occlusion on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   */
  @MapboxExperimental
  val fillExtrusionAmbientOcclusionGroundAttenuation: Double?
    /**
     * Provides a control to futher fine-tune the look of the ambient occlusion on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionGroundAttenuation] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-ambient-occlusion-ground-attenuation")
    }

  /**
   * Provides a control to futher fine-tune the look of the ambient occlusion on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionGroundAttenuation] to set the default property.
   *
   * @param fillExtrusionAmbientOcclusionGroundAttenuation value of fillExtrusionAmbientOcclusionGroundAttenuation
   */
  @MapboxExperimental
  override fun fillExtrusionAmbientOcclusionGroundAttenuation(fillExtrusionAmbientOcclusionGroundAttenuation: Double): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-ambient-occlusion-ground-attenuation", fillExtrusionAmbientOcclusionGroundAttenuation)
    setProperty(propertyValue)
  }

  /**
   * Provides a control to futher fine-tune the look of the ambient occlusion on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   *
   * This is an Expression representation of "fill-extrusion-ambient-occlusion-ground-attenuation".
   *
   */
  @MapboxExperimental
  val fillExtrusionAmbientOcclusionGroundAttenuationAsExpression: Expression?
    /**
     * Provides a control to futher fine-tune the look of the ambient occlusion on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
     *
     * Get the FillExtrusionAmbientOcclusionGroundAttenuation property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionGroundAttenuationAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-ambient-occlusion-ground-attenuation")?.let {
        return it
      }
      fillExtrusionAmbientOcclusionGroundAttenuation?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Provides a control to futher fine-tune the look of the ambient occlusion on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionGroundAttenuationAsExpression] to set the default property.
   *
   * @param fillExtrusionAmbientOcclusionGroundAttenuation value of fillExtrusionAmbientOcclusionGroundAttenuation as Expression
   */
  @MapboxExperimental
  override fun fillExtrusionAmbientOcclusionGroundAttenuation(fillExtrusionAmbientOcclusionGroundAttenuation: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-ambient-occlusion-ground-attenuation", fillExtrusionAmbientOcclusionGroundAttenuation)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionAmbientOcclusionGroundAttenuation.
   */
  @MapboxExperimental
  val fillExtrusionAmbientOcclusionGroundAttenuationTransition: StyleTransition?
    /**
     * Get the FillExtrusionAmbientOcclusionGroundAttenuation property transition options
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionGroundAttenuationTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-extrusion-ambient-occlusion-ground-attenuation-transition")
    }

  /**
   * Set the FillExtrusionAmbientOcclusionGroundAttenuation property transition options
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionGroundAttenuationTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun fillExtrusionAmbientOcclusionGroundAttenuationTransition(options: StyleTransition): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-ambient-occlusion-ground-attenuation-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionAmbientOcclusionGroundAttenuationTransition].
   */
  @MapboxExperimental
  override fun fillExtrusionAmbientOcclusionGroundAttenuationTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer = apply {
    fillExtrusionAmbientOcclusionGroundAttenuationTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The extent of the ambient occlusion effect on the ground beneath the extruded buildings in meters. Default value: 3. Minimum value: 0.
   */
  @MapboxExperimental
  val fillExtrusionAmbientOcclusionGroundRadius: Double?
    /**
     * The extent of the ambient occlusion effect on the ground beneath the extruded buildings in meters. Default value: 3. Minimum value: 0.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionGroundRadius] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-ambient-occlusion-ground-radius")
    }

  /**
   * The extent of the ambient occlusion effect on the ground beneath the extruded buildings in meters. Default value: 3. Minimum value: 0.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionGroundRadius] to set the default property.
   *
   * @param fillExtrusionAmbientOcclusionGroundRadius value of fillExtrusionAmbientOcclusionGroundRadius
   */
  @MapboxExperimental
  override fun fillExtrusionAmbientOcclusionGroundRadius(fillExtrusionAmbientOcclusionGroundRadius: Double): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-ambient-occlusion-ground-radius", fillExtrusionAmbientOcclusionGroundRadius)
    setProperty(propertyValue)
  }

  /**
   * The extent of the ambient occlusion effect on the ground beneath the extruded buildings in meters. Default value: 3. Minimum value: 0.
   *
   * This is an Expression representation of "fill-extrusion-ambient-occlusion-ground-radius".
   *
   */
  @MapboxExperimental
  val fillExtrusionAmbientOcclusionGroundRadiusAsExpression: Expression?
    /**
     * The extent of the ambient occlusion effect on the ground beneath the extruded buildings in meters. Default value: 3. Minimum value: 0.
     *
     * Get the FillExtrusionAmbientOcclusionGroundRadius property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionGroundRadiusAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-ambient-occlusion-ground-radius")?.let {
        return it
      }
      fillExtrusionAmbientOcclusionGroundRadius?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The extent of the ambient occlusion effect on the ground beneath the extruded buildings in meters. Default value: 3. Minimum value: 0.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionGroundRadiusAsExpression] to set the default property.
   *
   * @param fillExtrusionAmbientOcclusionGroundRadius value of fillExtrusionAmbientOcclusionGroundRadius as Expression
   */
  @MapboxExperimental
  override fun fillExtrusionAmbientOcclusionGroundRadius(fillExtrusionAmbientOcclusionGroundRadius: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-ambient-occlusion-ground-radius", fillExtrusionAmbientOcclusionGroundRadius)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionAmbientOcclusionGroundRadius.
   */
  @MapboxExperimental
  val fillExtrusionAmbientOcclusionGroundRadiusTransition: StyleTransition?
    /**
     * Get the FillExtrusionAmbientOcclusionGroundRadius property transition options
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionGroundRadiusTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-extrusion-ambient-occlusion-ground-radius-transition")
    }

  /**
   * Set the FillExtrusionAmbientOcclusionGroundRadius property transition options
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionGroundRadiusTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun fillExtrusionAmbientOcclusionGroundRadiusTransition(options: StyleTransition): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-ambient-occlusion-ground-radius-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionAmbientOcclusionGroundRadiusTransition].
   */
  @MapboxExperimental
  override fun fillExtrusionAmbientOcclusionGroundRadiusTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer = apply {
    fillExtrusionAmbientOcclusionGroundRadiusTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings. Default value: 0. Value range: [0, 1]
   */
  val fillExtrusionAmbientOcclusionIntensity: Double?
    /**
     * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings. Default value: 0. Value range: [0, 1]
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionIntensity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-ambient-occlusion-intensity")
    }

  /**
   * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings. Default value: 0. Value range: [0, 1]
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
   * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings. Default value: 0. Value range: [0, 1]
   *
   * This is an Expression representation of "fill-extrusion-ambient-occlusion-intensity".
   *
   */
  val fillExtrusionAmbientOcclusionIntensityAsExpression: Expression?
    /**
     * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings. Default value: 0. Value range: [0, 1]
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
   * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings. Default value: 0. Value range: [0, 1]
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
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. This property works only with legacy light. When 3D lights are enabled `fill-extrusion-ambient-occlusion-wall-radius` and `fill-extrusion-ambient-occlusion-ground-radius` are used instead. Default value: 3. Minimum value: 0.
   */
  val fillExtrusionAmbientOcclusionRadius: Double?
    /**
     * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. This property works only with legacy light. When 3D lights are enabled `fill-extrusion-ambient-occlusion-wall-radius` and `fill-extrusion-ambient-occlusion-ground-radius` are used instead. Default value: 3. Minimum value: 0.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionRadius] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-ambient-occlusion-radius")
    }

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. This property works only with legacy light. When 3D lights are enabled `fill-extrusion-ambient-occlusion-wall-radius` and `fill-extrusion-ambient-occlusion-ground-radius` are used instead. Default value: 3. Minimum value: 0.
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
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. This property works only with legacy light. When 3D lights are enabled `fill-extrusion-ambient-occlusion-wall-radius` and `fill-extrusion-ambient-occlusion-ground-radius` are used instead. Default value: 3. Minimum value: 0.
   *
   * This is an Expression representation of "fill-extrusion-ambient-occlusion-radius".
   *
   */
  val fillExtrusionAmbientOcclusionRadiusAsExpression: Expression?
    /**
     * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. This property works only with legacy light. When 3D lights are enabled `fill-extrusion-ambient-occlusion-wall-radius` and `fill-extrusion-ambient-occlusion-ground-radius` are used instead. Default value: 3. Minimum value: 0.
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
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. This property works only with legacy light. When 3D lights are enabled `fill-extrusion-ambient-occlusion-wall-radius` and `fill-extrusion-ambient-occlusion-ground-radius` are used instead. Default value: 3. Minimum value: 0.
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
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. Default value: 3. Minimum value: 0.
   */
  @MapboxExperimental
  val fillExtrusionAmbientOcclusionWallRadius: Double?
    /**
     * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. Default value: 3. Minimum value: 0.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionWallRadius] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-ambient-occlusion-wall-radius")
    }

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. Default value: 3. Minimum value: 0.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionWallRadius] to set the default property.
   *
   * @param fillExtrusionAmbientOcclusionWallRadius value of fillExtrusionAmbientOcclusionWallRadius
   */
  @MapboxExperimental
  override fun fillExtrusionAmbientOcclusionWallRadius(fillExtrusionAmbientOcclusionWallRadius: Double): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-ambient-occlusion-wall-radius", fillExtrusionAmbientOcclusionWallRadius)
    setProperty(propertyValue)
  }

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. Default value: 3. Minimum value: 0.
   *
   * This is an Expression representation of "fill-extrusion-ambient-occlusion-wall-radius".
   *
   */
  @MapboxExperimental
  val fillExtrusionAmbientOcclusionWallRadiusAsExpression: Expression?
    /**
     * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. Default value: 3. Minimum value: 0.
     *
     * Get the FillExtrusionAmbientOcclusionWallRadius property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionWallRadiusAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-ambient-occlusion-wall-radius")?.let {
        return it
      }
      fillExtrusionAmbientOcclusionWallRadius?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. Default value: 3. Minimum value: 0.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionWallRadiusAsExpression] to set the default property.
   *
   * @param fillExtrusionAmbientOcclusionWallRadius value of fillExtrusionAmbientOcclusionWallRadius as Expression
   */
  @MapboxExperimental
  override fun fillExtrusionAmbientOcclusionWallRadius(fillExtrusionAmbientOcclusionWallRadius: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-ambient-occlusion-wall-radius", fillExtrusionAmbientOcclusionWallRadius)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionAmbientOcclusionWallRadius.
   */
  @MapboxExperimental
  val fillExtrusionAmbientOcclusionWallRadiusTransition: StyleTransition?
    /**
     * Get the FillExtrusionAmbientOcclusionWallRadius property transition options
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionWallRadiusTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-extrusion-ambient-occlusion-wall-radius-transition")
    }

  /**
   * Set the FillExtrusionAmbientOcclusionWallRadius property transition options
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionWallRadiusTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun fillExtrusionAmbientOcclusionWallRadiusTransition(options: StyleTransition): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-ambient-occlusion-wall-radius-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionAmbientOcclusionWallRadiusTransition].
   */
  @MapboxExperimental
  override fun fillExtrusionAmbientOcclusionWallRadiusTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer = apply {
    fillExtrusionAmbientOcclusionWallRadiusTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`. Default value: 0. Minimum value: 0. The unit of fillExtrusionBase is in meters.
   */
  val fillExtrusionBase: Double?
    /**
     * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`. Default value: 0. Minimum value: 0. The unit of fillExtrusionBase is in meters.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionBase] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-base")
    }

  /**
   * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`. Default value: 0. Minimum value: 0. The unit of fillExtrusionBase is in meters.
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
   * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`. Default value: 0. Minimum value: 0. The unit of fillExtrusionBase is in meters.
   *
   * This is an Expression representation of "fill-extrusion-base".
   *
   */
  val fillExtrusionBaseAsExpression: Expression?
    /**
     * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`. Default value: 0. Minimum value: 0. The unit of fillExtrusionBase is in meters.
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
   * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`. Default value: 0. Minimum value: 0. The unit of fillExtrusionBase is in meters.
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
   * Controls the behavior of fill extrusion base over terrain Default value: "terrain".
   */
  @MapboxExperimental
  val fillExtrusionBaseAlignment: FillExtrusionBaseAlignment?
    /**
     * Controls the behavior of fill extrusion base over terrain Default value: "terrain".
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionBaseAlignment] to get the default property.
     *
     * @return FillExtrusionBaseAlignment
     */
    get() {
      getPropertyValue<String?>("fill-extrusion-base-alignment")?.let {
        return FillExtrusionBaseAlignment.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Controls the behavior of fill extrusion base over terrain Default value: "terrain".
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionBaseAlignment] to set the default property.
   *
   * @param fillExtrusionBaseAlignment value of fillExtrusionBaseAlignment
   */
  @MapboxExperimental
  override fun fillExtrusionBaseAlignment(fillExtrusionBaseAlignment: FillExtrusionBaseAlignment): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-base-alignment", fillExtrusionBaseAlignment)
    setProperty(propertyValue)
  }

  /**
   * Controls the behavior of fill extrusion base over terrain Default value: "terrain".
   *
   * This is an Expression representation of "fill-extrusion-base-alignment".
   *
   */
  @MapboxExperimental
  val fillExtrusionBaseAlignmentAsExpression: Expression?
    /**
     * Controls the behavior of fill extrusion base over terrain Default value: "terrain".
     *
     * Get the FillExtrusionBaseAlignment property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionBaseAlignmentAsExpression] to get the default property.
     *
     * @return FillExtrusionBaseAlignment
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-base-alignment")?.let {
        return it
      }
      fillExtrusionBaseAlignment?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Controls the behavior of fill extrusion base over terrain Default value: "terrain".
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionBaseAlignmentAsExpression] to set the default property.
   *
   * @param fillExtrusionBaseAlignment value of fillExtrusionBaseAlignment as Expression
   */
  @MapboxExperimental
  override fun fillExtrusionBaseAlignment(fillExtrusionBaseAlignment: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-base-alignment", fillExtrusionBaseAlignment)
    setProperty(propertyValue)
  }

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
   */
  val fillExtrusionColor: String?
    /**
     * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
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
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
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
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
   *
   * This is an Expression representation of "fill-extrusion-color".
   *
   */
  val fillExtrusionColorAsExpression: Expression?
    /**
     * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
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
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
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
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
   */
  val fillExtrusionColorAsColorInt: Int?
    /**
     * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
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
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
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
   * Сolor theme override for [fillExtrusionColor].
   */
  @MapboxExperimental
  val fillExtrusionColorUseTheme: String?
    /**
     * Get the FillExtrusionColorUseTheme property
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionColorUseTheme] to get the default property.
     *
     * @return current FillExtrusionColorUseTheme property as String
     */
    get() {
      return getPropertyValue("fill-extrusion-color-use-theme")
    }

  /**
   * Set the FillExtrusionColorUseTheme as String
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionColorUseTheme] to get the default property.
   *
   * @param fillExtrusionColorUseTheme theme value for color. Overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  override fun fillExtrusionColorUseTheme(fillExtrusionColorUseTheme: String): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-color-use-theme", fillExtrusionColorUseTheme)
    setProperty(propertyValue)
  }

  /**
   * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. Fade out is implemented by scaling down and removing buildings in the fade range in a staggered fashion. Opacity is not changed. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
   */
  val fillExtrusionCutoffFadeRange: Double?
    /**
     * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. Fade out is implemented by scaling down and removing buildings in the fade range in a staggered fashion. Opacity is not changed. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionCutoffFadeRange] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-cutoff-fade-range")
    }

  /**
   * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. Fade out is implemented by scaling down and removing buildings in the fade range in a staggered fashion. Opacity is not changed. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionCutoffFadeRange] to set the default property.
   *
   * @param fillExtrusionCutoffFadeRange value of fillExtrusionCutoffFadeRange
   */
  override fun fillExtrusionCutoffFadeRange(fillExtrusionCutoffFadeRange: Double): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-cutoff-fade-range", fillExtrusionCutoffFadeRange)
    setProperty(propertyValue)
  }

  /**
   * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. Fade out is implemented by scaling down and removing buildings in the fade range in a staggered fashion. Opacity is not changed. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
   *
   * This is an Expression representation of "fill-extrusion-cutoff-fade-range".
   *
   */
  val fillExtrusionCutoffFadeRangeAsExpression: Expression?
    /**
     * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. Fade out is implemented by scaling down and removing buildings in the fade range in a staggered fashion. Opacity is not changed. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
     *
     * Get the FillExtrusionCutoffFadeRange property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionCutoffFadeRangeAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-cutoff-fade-range")?.let {
        return it
      }
      fillExtrusionCutoffFadeRange?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. Fade out is implemented by scaling down and removing buildings in the fade range in a staggered fashion. Opacity is not changed. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionCutoffFadeRangeAsExpression] to set the default property.
   *
   * @param fillExtrusionCutoffFadeRange value of fillExtrusionCutoffFadeRange as Expression
   */
  override fun fillExtrusionCutoffFadeRange(fillExtrusionCutoffFadeRange: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-cutoff-fade-range", fillExtrusionCutoffFadeRange)
    setProperty(propertyValue)
  }

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillExtrusionEmissiveStrength is in intensity.
   */
  val fillExtrusionEmissiveStrength: Double?
    /**
     * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillExtrusionEmissiveStrength is in intensity.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionEmissiveStrength] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-emissive-strength")
    }

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillExtrusionEmissiveStrength is in intensity.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionEmissiveStrength] to set the default property.
   *
   * @param fillExtrusionEmissiveStrength value of fillExtrusionEmissiveStrength
   */
  override fun fillExtrusionEmissiveStrength(fillExtrusionEmissiveStrength: Double): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-emissive-strength", fillExtrusionEmissiveStrength)
    setProperty(propertyValue)
  }

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillExtrusionEmissiveStrength is in intensity.
   *
   * This is an Expression representation of "fill-extrusion-emissive-strength".
   *
   */
  val fillExtrusionEmissiveStrengthAsExpression: Expression?
    /**
     * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillExtrusionEmissiveStrength is in intensity.
     *
     * Get the FillExtrusionEmissiveStrength property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionEmissiveStrengthAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-emissive-strength")?.let {
        return it
      }
      fillExtrusionEmissiveStrength?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillExtrusionEmissiveStrength is in intensity.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionEmissiveStrengthAsExpression] to set the default property.
   *
   * @param fillExtrusionEmissiveStrength value of fillExtrusionEmissiveStrength as Expression
   */
  override fun fillExtrusionEmissiveStrength(fillExtrusionEmissiveStrength: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-emissive-strength", fillExtrusionEmissiveStrength)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionEmissiveStrength.
   */
  val fillExtrusionEmissiveStrengthTransition: StyleTransition?
    /**
     * Get the FillExtrusionEmissiveStrength property transition options
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionEmissiveStrengthTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-extrusion-emissive-strength-transition")
    }

  /**
   * Set the FillExtrusionEmissiveStrength property transition options
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionEmissiveStrengthTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun fillExtrusionEmissiveStrengthTransition(options: StyleTransition): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-emissive-strength-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionEmissiveStrengthTransition].
   */
  override fun fillExtrusionEmissiveStrengthTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer = apply {
    fillExtrusionEmissiveStrengthTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
   */
  @MapboxExperimental
  val fillExtrusionFloodLightColor: String?
    /**
     * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightColor] to get the default property.
     *
     * @return String
     */
    get() {
      fillExtrusionFloodLightColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightColor] to set the default property.
   *
   * @param fillExtrusionFloodLightColor value of fillExtrusionFloodLightColor
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightColor(fillExtrusionFloodLightColor: String): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-flood-light-color", fillExtrusionFloodLightColor)
    setProperty(propertyValue)
  }

  /**
   * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
   *
   * This is an Expression representation of "fill-extrusion-flood-light-color".
   *
   */
  @MapboxExperimental
  val fillExtrusionFloodLightColorAsExpression: Expression?
    /**
     * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
     *
     * Get the FillExtrusionFloodLightColor property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightColorAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-flood-light-color")?.let {
        return it
      }
      return null
    }

  /**
   * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightColorAsExpression] to set the default property.
   *
   * @param fillExtrusionFloodLightColor value of fillExtrusionFloodLightColor as Expression
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightColor(fillExtrusionFloodLightColor: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-flood-light-color", fillExtrusionFloodLightColor)
    setProperty(propertyValue)
  }

  /**
   * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
   */
  @MapboxExperimental
  val fillExtrusionFloodLightColorAsColorInt: Int?
    /**
     * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightColorAsColorInt] to get the default property.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      fillExtrusionFloodLightColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightColorAsColorInt] to set the default property.
   *
   * @param fillExtrusionFloodLightColor value of fillExtrusionFloodLightColor
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightColor(@ColorInt fillExtrusionFloodLightColor: Int): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-flood-light-color", colorIntToRgbaExpression(fillExtrusionFloodLightColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionFloodLightColor.
   */
  @MapboxExperimental
  val fillExtrusionFloodLightColorTransition: StyleTransition?
    /**
     * Get the FillExtrusionFloodLightColor property transition options
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightColorTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("fill-extrusion-flood-light-color-transition")
    }

  /**
   * Set the FillExtrusionFloodLightColor property transition options
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightColorTransition] to set the default property.
   *
   * @param options transition options for String
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightColorTransition(options: StyleTransition): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-flood-light-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionFloodLightColorTransition].
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightColorTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer = apply {
    fillExtrusionFloodLightColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Сolor theme override for [fillExtrusionFloodLightColor].
   */
  @MapboxExperimental
  val fillExtrusionFloodLightColorUseTheme: String?
    /**
     * Get the FillExtrusionFloodLightColorUseTheme property
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightColorUseTheme] to get the default property.
     *
     * @return current FillExtrusionFloodLightColorUseTheme property as String
     */
    get() {
      return getPropertyValue("fill-extrusion-flood-light-color-use-theme")
    }

  /**
   * Set the FillExtrusionFloodLightColorUseTheme as String
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightColorUseTheme] to get the default property.
   *
   * @param fillExtrusionFloodLightColorUseTheme theme value for color. Overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightColorUseTheme(fillExtrusionFloodLightColorUseTheme: String): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-flood-light-color-use-theme", fillExtrusionFloodLightColorUseTheme)
    setProperty(propertyValue)
  }

  /**
   * Provides a control to futher fine-tune the look of the flood light on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   */
  @MapboxExperimental
  val fillExtrusionFloodLightGroundAttenuation: Double?
    /**
     * Provides a control to futher fine-tune the look of the flood light on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightGroundAttenuation] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-flood-light-ground-attenuation")
    }

  /**
   * Provides a control to futher fine-tune the look of the flood light on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightGroundAttenuation] to set the default property.
   *
   * @param fillExtrusionFloodLightGroundAttenuation value of fillExtrusionFloodLightGroundAttenuation
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightGroundAttenuation(fillExtrusionFloodLightGroundAttenuation: Double): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-flood-light-ground-attenuation", fillExtrusionFloodLightGroundAttenuation)
    setProperty(propertyValue)
  }

  /**
   * Provides a control to futher fine-tune the look of the flood light on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   *
   * This is an Expression representation of "fill-extrusion-flood-light-ground-attenuation".
   *
   */
  @MapboxExperimental
  val fillExtrusionFloodLightGroundAttenuationAsExpression: Expression?
    /**
     * Provides a control to futher fine-tune the look of the flood light on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
     *
     * Get the FillExtrusionFloodLightGroundAttenuation property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightGroundAttenuationAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-flood-light-ground-attenuation")?.let {
        return it
      }
      fillExtrusionFloodLightGroundAttenuation?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Provides a control to futher fine-tune the look of the flood light on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightGroundAttenuationAsExpression] to set the default property.
   *
   * @param fillExtrusionFloodLightGroundAttenuation value of fillExtrusionFloodLightGroundAttenuation as Expression
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightGroundAttenuation(fillExtrusionFloodLightGroundAttenuation: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-flood-light-ground-attenuation", fillExtrusionFloodLightGroundAttenuation)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionFloodLightGroundAttenuation.
   */
  @MapboxExperimental
  val fillExtrusionFloodLightGroundAttenuationTransition: StyleTransition?
    /**
     * Get the FillExtrusionFloodLightGroundAttenuation property transition options
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightGroundAttenuationTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-extrusion-flood-light-ground-attenuation-transition")
    }

  /**
   * Set the FillExtrusionFloodLightGroundAttenuation property transition options
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightGroundAttenuationTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightGroundAttenuationTransition(options: StyleTransition): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-flood-light-ground-attenuation-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionFloodLightGroundAttenuationTransition].
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightGroundAttenuationTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer = apply {
    fillExtrusionFloodLightGroundAttenuationTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The extent of the flood light effect on the ground beneath the extruded buildings in meters. Note: this experimental property is evaluated once per tile, during tile initialization. Changing the property value could trigger tile reload. The `feature-state` styling is deprecated and will get removed soon. Default value: 0. The unit of fillExtrusionFloodLightGroundRadius is in meters.
   */
  @MapboxExperimental
  val fillExtrusionFloodLightGroundRadius: Double?
    /**
     * The extent of the flood light effect on the ground beneath the extruded buildings in meters. Note: this experimental property is evaluated once per tile, during tile initialization. Changing the property value could trigger tile reload. The `feature-state` styling is deprecated and will get removed soon. Default value: 0. The unit of fillExtrusionFloodLightGroundRadius is in meters.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightGroundRadius] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-flood-light-ground-radius")
    }

  /**
   * The extent of the flood light effect on the ground beneath the extruded buildings in meters. Note: this experimental property is evaluated once per tile, during tile initialization. Changing the property value could trigger tile reload. The `feature-state` styling is deprecated and will get removed soon. Default value: 0. The unit of fillExtrusionFloodLightGroundRadius is in meters.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightGroundRadius] to set the default property.
   *
   * @param fillExtrusionFloodLightGroundRadius value of fillExtrusionFloodLightGroundRadius
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightGroundRadius(fillExtrusionFloodLightGroundRadius: Double): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-flood-light-ground-radius", fillExtrusionFloodLightGroundRadius)
    setProperty(propertyValue)
  }

  /**
   * The extent of the flood light effect on the ground beneath the extruded buildings in meters. Note: this experimental property is evaluated once per tile, during tile initialization. Changing the property value could trigger tile reload. The `feature-state` styling is deprecated and will get removed soon. Default value: 0. The unit of fillExtrusionFloodLightGroundRadius is in meters.
   *
   * This is an Expression representation of "fill-extrusion-flood-light-ground-radius".
   *
   */
  @MapboxExperimental
  val fillExtrusionFloodLightGroundRadiusAsExpression: Expression?
    /**
     * The extent of the flood light effect on the ground beneath the extruded buildings in meters. Note: this experimental property is evaluated once per tile, during tile initialization. Changing the property value could trigger tile reload. The `feature-state` styling is deprecated and will get removed soon. Default value: 0. The unit of fillExtrusionFloodLightGroundRadius is in meters.
     *
     * Get the FillExtrusionFloodLightGroundRadius property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightGroundRadiusAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-flood-light-ground-radius")?.let {
        return it
      }
      fillExtrusionFloodLightGroundRadius?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The extent of the flood light effect on the ground beneath the extruded buildings in meters. Note: this experimental property is evaluated once per tile, during tile initialization. Changing the property value could trigger tile reload. The `feature-state` styling is deprecated and will get removed soon. Default value: 0. The unit of fillExtrusionFloodLightGroundRadius is in meters.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightGroundRadiusAsExpression] to set the default property.
   *
   * @param fillExtrusionFloodLightGroundRadius value of fillExtrusionFloodLightGroundRadius as Expression
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightGroundRadius(fillExtrusionFloodLightGroundRadius: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-flood-light-ground-radius", fillExtrusionFloodLightGroundRadius)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionFloodLightGroundRadius.
   */
  @MapboxExperimental
  val fillExtrusionFloodLightGroundRadiusTransition: StyleTransition?
    /**
     * Get the FillExtrusionFloodLightGroundRadius property transition options
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightGroundRadiusTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-extrusion-flood-light-ground-radius-transition")
    }

  /**
   * Set the FillExtrusionFloodLightGroundRadius property transition options
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightGroundRadiusTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightGroundRadiusTransition(options: StyleTransition): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-flood-light-ground-radius-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionFloodLightGroundRadiusTransition].
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightGroundRadiusTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer = apply {
    fillExtrusionFloodLightGroundRadiusTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The intensity of the flood light color. Default value: 0. Value range: [0, 1]
   */
  @MapboxExperimental
  val fillExtrusionFloodLightIntensity: Double?
    /**
     * The intensity of the flood light color. Default value: 0. Value range: [0, 1]
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightIntensity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-flood-light-intensity")
    }

  /**
   * The intensity of the flood light color. Default value: 0. Value range: [0, 1]
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightIntensity] to set the default property.
   *
   * @param fillExtrusionFloodLightIntensity value of fillExtrusionFloodLightIntensity
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightIntensity(fillExtrusionFloodLightIntensity: Double): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-flood-light-intensity", fillExtrusionFloodLightIntensity)
    setProperty(propertyValue)
  }

  /**
   * The intensity of the flood light color. Default value: 0. Value range: [0, 1]
   *
   * This is an Expression representation of "fill-extrusion-flood-light-intensity".
   *
   */
  @MapboxExperimental
  val fillExtrusionFloodLightIntensityAsExpression: Expression?
    /**
     * The intensity of the flood light color. Default value: 0. Value range: [0, 1]
     *
     * Get the FillExtrusionFloodLightIntensity property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightIntensityAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-flood-light-intensity")?.let {
        return it
      }
      fillExtrusionFloodLightIntensity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The intensity of the flood light color. Default value: 0. Value range: [0, 1]
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightIntensityAsExpression] to set the default property.
   *
   * @param fillExtrusionFloodLightIntensity value of fillExtrusionFloodLightIntensity as Expression
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightIntensity(fillExtrusionFloodLightIntensity: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-flood-light-intensity", fillExtrusionFloodLightIntensity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionFloodLightIntensity.
   */
  @MapboxExperimental
  val fillExtrusionFloodLightIntensityTransition: StyleTransition?
    /**
     * Get the FillExtrusionFloodLightIntensity property transition options
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightIntensityTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-extrusion-flood-light-intensity-transition")
    }

  /**
   * Set the FillExtrusionFloodLightIntensity property transition options
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightIntensityTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightIntensityTransition(options: StyleTransition): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-flood-light-intensity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionFloodLightIntensityTransition].
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightIntensityTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer = apply {
    fillExtrusionFloodLightIntensityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The extent of the flood light effect on the walls of the extruded buildings in meters. Default value: 0. Minimum value: 0. The unit of fillExtrusionFloodLightWallRadius is in meters.
   */
  @MapboxExperimental
  val fillExtrusionFloodLightWallRadius: Double?
    /**
     * The extent of the flood light effect on the walls of the extruded buildings in meters. Default value: 0. Minimum value: 0. The unit of fillExtrusionFloodLightWallRadius is in meters.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightWallRadius] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-flood-light-wall-radius")
    }

  /**
   * The extent of the flood light effect on the walls of the extruded buildings in meters. Default value: 0. Minimum value: 0. The unit of fillExtrusionFloodLightWallRadius is in meters.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightWallRadius] to set the default property.
   *
   * @param fillExtrusionFloodLightWallRadius value of fillExtrusionFloodLightWallRadius
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightWallRadius(fillExtrusionFloodLightWallRadius: Double): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-flood-light-wall-radius", fillExtrusionFloodLightWallRadius)
    setProperty(propertyValue)
  }

  /**
   * The extent of the flood light effect on the walls of the extruded buildings in meters. Default value: 0. Minimum value: 0. The unit of fillExtrusionFloodLightWallRadius is in meters.
   *
   * This is an Expression representation of "fill-extrusion-flood-light-wall-radius".
   *
   */
  @MapboxExperimental
  val fillExtrusionFloodLightWallRadiusAsExpression: Expression?
    /**
     * The extent of the flood light effect on the walls of the extruded buildings in meters. Default value: 0. Minimum value: 0. The unit of fillExtrusionFloodLightWallRadius is in meters.
     *
     * Get the FillExtrusionFloodLightWallRadius property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightWallRadiusAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-flood-light-wall-radius")?.let {
        return it
      }
      fillExtrusionFloodLightWallRadius?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The extent of the flood light effect on the walls of the extruded buildings in meters. Default value: 0. Minimum value: 0. The unit of fillExtrusionFloodLightWallRadius is in meters.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightWallRadiusAsExpression] to set the default property.
   *
   * @param fillExtrusionFloodLightWallRadius value of fillExtrusionFloodLightWallRadius as Expression
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightWallRadius(fillExtrusionFloodLightWallRadius: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-flood-light-wall-radius", fillExtrusionFloodLightWallRadius)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionFloodLightWallRadius.
   */
  @MapboxExperimental
  val fillExtrusionFloodLightWallRadiusTransition: StyleTransition?
    /**
     * Get the FillExtrusionFloodLightWallRadius property transition options
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightWallRadiusTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-extrusion-flood-light-wall-radius-transition")
    }

  /**
   * Set the FillExtrusionFloodLightWallRadius property transition options
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionFloodLightWallRadiusTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightWallRadiusTransition(options: StyleTransition): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-flood-light-wall-radius-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionFloodLightWallRadiusTransition].
   */
  @MapboxExperimental
  override fun fillExtrusionFloodLightWallRadiusTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer = apply {
    fillExtrusionFloodLightWallRadiusTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The height with which to extrude this layer. Default value: 0. Minimum value: 0. The unit of fillExtrusionHeight is in meters.
   */
  val fillExtrusionHeight: Double?
    /**
     * The height with which to extrude this layer. Default value: 0. Minimum value: 0. The unit of fillExtrusionHeight is in meters.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionHeight] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-height")
    }

  /**
   * The height with which to extrude this layer. Default value: 0. Minimum value: 0. The unit of fillExtrusionHeight is in meters.
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
   * The height with which to extrude this layer. Default value: 0. Minimum value: 0. The unit of fillExtrusionHeight is in meters.
   *
   * This is an Expression representation of "fill-extrusion-height".
   *
   */
  val fillExtrusionHeightAsExpression: Expression?
    /**
     * The height with which to extrude this layer. Default value: 0. Minimum value: 0. The unit of fillExtrusionHeight is in meters.
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
   * The height with which to extrude this layer. Default value: 0. Minimum value: 0. The unit of fillExtrusionHeight is in meters.
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
   * Controls the behavior of fill extrusion height over terrain Default value: "flat".
   */
  @MapboxExperimental
  val fillExtrusionHeightAlignment: FillExtrusionHeightAlignment?
    /**
     * Controls the behavior of fill extrusion height over terrain Default value: "flat".
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionHeightAlignment] to get the default property.
     *
     * @return FillExtrusionHeightAlignment
     */
    get() {
      getPropertyValue<String?>("fill-extrusion-height-alignment")?.let {
        return FillExtrusionHeightAlignment.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Controls the behavior of fill extrusion height over terrain Default value: "flat".
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionHeightAlignment] to set the default property.
   *
   * @param fillExtrusionHeightAlignment value of fillExtrusionHeightAlignment
   */
  @MapboxExperimental
  override fun fillExtrusionHeightAlignment(fillExtrusionHeightAlignment: FillExtrusionHeightAlignment): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-height-alignment", fillExtrusionHeightAlignment)
    setProperty(propertyValue)
  }

  /**
   * Controls the behavior of fill extrusion height over terrain Default value: "flat".
   *
   * This is an Expression representation of "fill-extrusion-height-alignment".
   *
   */
  @MapboxExperimental
  val fillExtrusionHeightAlignmentAsExpression: Expression?
    /**
     * Controls the behavior of fill extrusion height over terrain Default value: "flat".
     *
     * Get the FillExtrusionHeightAlignment property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionHeightAlignmentAsExpression] to get the default property.
     *
     * @return FillExtrusionHeightAlignment
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-height-alignment")?.let {
        return it
      }
      fillExtrusionHeightAlignment?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Controls the behavior of fill extrusion height over terrain Default value: "flat".
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionHeightAlignmentAsExpression] to set the default property.
   *
   * @param fillExtrusionHeightAlignment value of fillExtrusionHeightAlignment as Expression
   */
  @MapboxExperimental
  override fun fillExtrusionHeightAlignment(fillExtrusionHeightAlignment: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-height-alignment", fillExtrusionHeightAlignment)
    setProperty(propertyValue)
  }

  /**
   * If a non-zero value is provided, it sets the fill-extrusion layer into wall rendering mode. The value is used to render the feature with the given width over the outlines of the geometry. Note: This property is experimental and some other fill-extrusion properties might not be supported with non-zero line width. Default value: 0. Minimum value: 0. The unit of fillExtrusionLineWidth is in meters.
   */
  @MapboxExperimental
  val fillExtrusionLineWidth: Double?
    /**
     * If a non-zero value is provided, it sets the fill-extrusion layer into wall rendering mode. The value is used to render the feature with the given width over the outlines of the geometry. Note: This property is experimental and some other fill-extrusion properties might not be supported with non-zero line width. Default value: 0. Minimum value: 0. The unit of fillExtrusionLineWidth is in meters.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionLineWidth] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-line-width")
    }

  /**
   * If a non-zero value is provided, it sets the fill-extrusion layer into wall rendering mode. The value is used to render the feature with the given width over the outlines of the geometry. Note: This property is experimental and some other fill-extrusion properties might not be supported with non-zero line width. Default value: 0. Minimum value: 0. The unit of fillExtrusionLineWidth is in meters.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionLineWidth] to set the default property.
   *
   * @param fillExtrusionLineWidth value of fillExtrusionLineWidth
   */
  @MapboxExperimental
  override fun fillExtrusionLineWidth(fillExtrusionLineWidth: Double): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-line-width", fillExtrusionLineWidth)
    setProperty(propertyValue)
  }

  /**
   * If a non-zero value is provided, it sets the fill-extrusion layer into wall rendering mode. The value is used to render the feature with the given width over the outlines of the geometry. Note: This property is experimental and some other fill-extrusion properties might not be supported with non-zero line width. Default value: 0. Minimum value: 0. The unit of fillExtrusionLineWidth is in meters.
   *
   * This is an Expression representation of "fill-extrusion-line-width".
   *
   */
  @MapboxExperimental
  val fillExtrusionLineWidthAsExpression: Expression?
    /**
     * If a non-zero value is provided, it sets the fill-extrusion layer into wall rendering mode. The value is used to render the feature with the given width over the outlines of the geometry. Note: This property is experimental and some other fill-extrusion properties might not be supported with non-zero line width. Default value: 0. Minimum value: 0. The unit of fillExtrusionLineWidth is in meters.
     *
     * Get the FillExtrusionLineWidth property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionLineWidthAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-line-width")?.let {
        return it
      }
      fillExtrusionLineWidth?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * If a non-zero value is provided, it sets the fill-extrusion layer into wall rendering mode. The value is used to render the feature with the given width over the outlines of the geometry. Note: This property is experimental and some other fill-extrusion properties might not be supported with non-zero line width. Default value: 0. Minimum value: 0. The unit of fillExtrusionLineWidth is in meters.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionLineWidthAsExpression] to set the default property.
   *
   * @param fillExtrusionLineWidth value of fillExtrusionLineWidth as Expression
   */
  @MapboxExperimental
  override fun fillExtrusionLineWidth(fillExtrusionLineWidth: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-line-width", fillExtrusionLineWidth)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionLineWidth.
   */
  @MapboxExperimental
  val fillExtrusionLineWidthTransition: StyleTransition?
    /**
     * Get the FillExtrusionLineWidth property transition options
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionLineWidthTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-extrusion-line-width-transition")
    }

  /**
   * Set the FillExtrusionLineWidth property transition options
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionLineWidthTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun fillExtrusionLineWidthTransition(options: StyleTransition): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-line-width-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionLineWidthTransition].
   */
  @MapboxExperimental
  override fun fillExtrusionLineWidthTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer = apply {
    fillExtrusionLineWidthTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available. Default value: 1. Value range: [0, 1]
   */
  val fillExtrusionOpacity: Double?
    /**
     * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available. Default value: 1. Value range: [0, 1]
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionOpacity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-opacity")
    }

  /**
   * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available. Default value: 1. Value range: [0, 1]
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
   * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available. Default value: 1. Value range: [0, 1]
   *
   * This is an Expression representation of "fill-extrusion-opacity".
   *
   */
  val fillExtrusionOpacityAsExpression: Expression?
    /**
     * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available. Default value: 1. Value range: [0, 1]
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
   * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available. Default value: 1. Value range: [0, 1]
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
   * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true. Default value: true.
   */
  @MapboxExperimental
  val fillExtrusionRoundedRoof: Boolean?
    /**
     * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true. Default value: true.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionRoundedRoof] to get the default property.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("fill-extrusion-rounded-roof")
    }

  /**
   * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true. Default value: true.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionRoundedRoof] to set the default property.
   *
   * @param fillExtrusionRoundedRoof value of fillExtrusionRoundedRoof
   */
  @MapboxExperimental
  override fun fillExtrusionRoundedRoof(fillExtrusionRoundedRoof: Boolean): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-rounded-roof", fillExtrusionRoundedRoof)
    setProperty(propertyValue)
  }

  /**
   * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true. Default value: true.
   *
   * This is an Expression representation of "fill-extrusion-rounded-roof".
   *
   */
  @MapboxExperimental
  val fillExtrusionRoundedRoofAsExpression: Expression?
    /**
     * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true. Default value: true.
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
   * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true. Default value: true.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionRoundedRoofAsExpression] to set the default property.
   *
   * @param fillExtrusionRoundedRoof value of fillExtrusionRoundedRoof as Expression
   */
  @MapboxExperimental
  override fun fillExtrusionRoundedRoof(fillExtrusionRoundedRoof: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-rounded-roof", fillExtrusionRoundedRoof)
    setProperty(propertyValue)
  }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively. Default value: [0,0]. The unit of fillExtrusionTranslate is in pixels.
   */
  val fillExtrusionTranslate: List<Double>?
    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively. Default value: [0,0]. The unit of fillExtrusionTranslate is in pixels.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionTranslate] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("fill-extrusion-translate")
    }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively. Default value: [0,0]. The unit of fillExtrusionTranslate is in pixels.
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
   * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively. Default value: [0,0]. The unit of fillExtrusionTranslate is in pixels.
   *
   * This is an Expression representation of "fill-extrusion-translate".
   *
   */
  val fillExtrusionTranslateAsExpression: Expression?
    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively. Default value: [0,0]. The unit of fillExtrusionTranslate is in pixels.
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
   * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively. Default value: [0,0]. The unit of fillExtrusionTranslate is in pixels.
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
   * Controls the frame of reference for `fill-extrusion-translate`. Default value: "map".
   */
  val fillExtrusionTranslateAnchor: FillExtrusionTranslateAnchor?
    /**
     * Controls the frame of reference for `fill-extrusion-translate`. Default value: "map".
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
   * Controls the frame of reference for `fill-extrusion-translate`. Default value: "map".
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
   * Controls the frame of reference for `fill-extrusion-translate`. Default value: "map".
   *
   * This is an Expression representation of "fill-extrusion-translate-anchor".
   *
   */
  val fillExtrusionTranslateAnchorAsExpression: Expression?
    /**
     * Controls the frame of reference for `fill-extrusion-translate`. Default value: "map".
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
   * Controls the frame of reference for `fill-extrusion-translate`. Default value: "map".
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
   * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down. Default value: true.
   */
  val fillExtrusionVerticalGradient: Boolean?
    /**
     * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down. Default value: true.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionVerticalGradient] to get the default property.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("fill-extrusion-vertical-gradient")
    }

  /**
   * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down. Default value: true.
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
   * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down. Default value: true.
   *
   * This is an Expression representation of "fill-extrusion-vertical-gradient".
   *
   */
  val fillExtrusionVerticalGradientAsExpression: Expression?
    /**
     * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down. Default value: true.
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
   * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down. Default value: true.
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
   * A global multiplier that can be used to scale base, height, AO, and flood light of the fill extrusions. Default value: 1. Minimum value: 0.
   */
  @MapboxExperimental
  val fillExtrusionVerticalScale: Double?
    /**
     * A global multiplier that can be used to scale base, height, AO, and flood light of the fill extrusions. Default value: 1. Minimum value: 0.
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionVerticalScale] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-vertical-scale")
    }

  /**
   * A global multiplier that can be used to scale base, height, AO, and flood light of the fill extrusions. Default value: 1. Minimum value: 0.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionVerticalScale] to set the default property.
   *
   * @param fillExtrusionVerticalScale value of fillExtrusionVerticalScale
   */
  @MapboxExperimental
  override fun fillExtrusionVerticalScale(fillExtrusionVerticalScale: Double): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-vertical-scale", fillExtrusionVerticalScale)
    setProperty(propertyValue)
  }

  /**
   * A global multiplier that can be used to scale base, height, AO, and flood light of the fill extrusions. Default value: 1. Minimum value: 0.
   *
   * This is an Expression representation of "fill-extrusion-vertical-scale".
   *
   */
  @MapboxExperimental
  val fillExtrusionVerticalScaleAsExpression: Expression?
    /**
     * A global multiplier that can be used to scale base, height, AO, and flood light of the fill extrusions. Default value: 1. Minimum value: 0.
     *
     * Get the FillExtrusionVerticalScale property as an Expression
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionVerticalScaleAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-vertical-scale")?.let {
        return it
      }
      fillExtrusionVerticalScale?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * A global multiplier that can be used to scale base, height, AO, and flood light of the fill extrusions. Default value: 1. Minimum value: 0.
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionVerticalScaleAsExpression] to set the default property.
   *
   * @param fillExtrusionVerticalScale value of fillExtrusionVerticalScale as Expression
   */
  @MapboxExperimental
  override fun fillExtrusionVerticalScale(fillExtrusionVerticalScale: Expression): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-vertical-scale", fillExtrusionVerticalScale)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionVerticalScale.
   */
  @MapboxExperimental
  val fillExtrusionVerticalScaleTransition: StyleTransition?
    /**
     * Get the FillExtrusionVerticalScale property transition options
     *
     * Use static method [FillExtrusionLayer.defaultFillExtrusionVerticalScaleTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-extrusion-vertical-scale-transition")
    }

  /**
   * Set the FillExtrusionVerticalScale property transition options
   *
   * Use static method [FillExtrusionLayer.defaultFillExtrusionVerticalScaleTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun fillExtrusionVerticalScaleTransition(options: StyleTransition): FillExtrusionLayer = apply {
    val propertyValue = PropertyValue("fill-extrusion-vertical-scale-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionVerticalScaleTransition].
   */
  @MapboxExperimental
  override fun fillExtrusionVerticalScaleTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer = apply {
    fillExtrusionVerticalScaleTransition(StyleTransition.Builder().apply(block).build())
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
     * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance. Default value: 0. Value range: [0, 1]
     */
    @MapboxExperimental
    val defaultFillExtrusionEdgeRadius: Double?
      /**
       * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance. Default value: 0. Value range: [0, 1]
       *
       * Get the default value of FillExtrusionEdgeRadius property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-edge-radius").silentUnwrap()
      }

    /**
     * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance. Default value: 0. Value range: [0, 1]
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
     * Provides a control to futher fine-tune the look of the ambient occlusion on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
     */
    @MapboxExperimental
    val defaultFillExtrusionAmbientOcclusionGroundAttenuation: Double?
      /**
       * Provides a control to futher fine-tune the look of the ambient occlusion on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
       *
       * Get the default value of FillExtrusionAmbientOcclusionGroundAttenuation property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-ground-attenuation").silentUnwrap()
      }

    /**
     * Provides a control to futher fine-tune the look of the ambient occlusion on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
     *
     * This is an Expression representation of "fill-extrusion-ambient-occlusion-ground-attenuation".
     *
     */
    @MapboxExperimental
    val defaultFillExtrusionAmbientOcclusionGroundAttenuationAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionAmbientOcclusionGroundAttenuation property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-ground-attenuation").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionAmbientOcclusionGroundAttenuation?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionAmbientOcclusionGroundAttenuation.
     */
    @MapboxExperimental
    val defaultFillExtrusionAmbientOcclusionGroundAttenuationTransition: StyleTransition?
      /**
       * Get the FillExtrusionAmbientOcclusionGroundAttenuation property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-ground-attenuation-transition").silentUnwrap()

    /**
     * The extent of the ambient occlusion effect on the ground beneath the extruded buildings in meters. Default value: 3. Minimum value: 0.
     */
    @MapboxExperimental
    val defaultFillExtrusionAmbientOcclusionGroundRadius: Double?
      /**
       * The extent of the ambient occlusion effect on the ground beneath the extruded buildings in meters. Default value: 3. Minimum value: 0.
       *
       * Get the default value of FillExtrusionAmbientOcclusionGroundRadius property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-ground-radius").silentUnwrap()
      }

    /**
     * The extent of the ambient occlusion effect on the ground beneath the extruded buildings in meters. Default value: 3. Minimum value: 0.
     *
     * This is an Expression representation of "fill-extrusion-ambient-occlusion-ground-radius".
     *
     */
    @MapboxExperimental
    val defaultFillExtrusionAmbientOcclusionGroundRadiusAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionAmbientOcclusionGroundRadius property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-ground-radius").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionAmbientOcclusionGroundRadius?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionAmbientOcclusionGroundRadius.
     */
    @MapboxExperimental
    val defaultFillExtrusionAmbientOcclusionGroundRadiusTransition: StyleTransition?
      /**
       * Get the FillExtrusionAmbientOcclusionGroundRadius property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-ground-radius-transition").silentUnwrap()

    /**
     * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings. Default value: 0. Value range: [0, 1]
     */
    val defaultFillExtrusionAmbientOcclusionIntensity: Double?
      /**
       * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings. Default value: 0. Value range: [0, 1]
       *
       * Get the default value of FillExtrusionAmbientOcclusionIntensity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-intensity").silentUnwrap()
      }

    /**
     * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings. Default value: 0. Value range: [0, 1]
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
     * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. This property works only with legacy light. When 3D lights are enabled `fill-extrusion-ambient-occlusion-wall-radius` and `fill-extrusion-ambient-occlusion-ground-radius` are used instead. Default value: 3. Minimum value: 0.
     */
    val defaultFillExtrusionAmbientOcclusionRadius: Double?
      /**
       * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. This property works only with legacy light. When 3D lights are enabled `fill-extrusion-ambient-occlusion-wall-radius` and `fill-extrusion-ambient-occlusion-ground-radius` are used instead. Default value: 3. Minimum value: 0.
       *
       * Get the default value of FillExtrusionAmbientOcclusionRadius property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-radius").silentUnwrap()
      }

    /**
     * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. This property works only with legacy light. When 3D lights are enabled `fill-extrusion-ambient-occlusion-wall-radius` and `fill-extrusion-ambient-occlusion-ground-radius` are used instead. Default value: 3. Minimum value: 0.
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
     * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. Default value: 3. Minimum value: 0.
     */
    @MapboxExperimental
    val defaultFillExtrusionAmbientOcclusionWallRadius: Double?
      /**
       * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. Default value: 3. Minimum value: 0.
       *
       * Get the default value of FillExtrusionAmbientOcclusionWallRadius property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-wall-radius").silentUnwrap()
      }

    /**
     * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. Default value: 3. Minimum value: 0.
     *
     * This is an Expression representation of "fill-extrusion-ambient-occlusion-wall-radius".
     *
     */
    @MapboxExperimental
    val defaultFillExtrusionAmbientOcclusionWallRadiusAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionAmbientOcclusionWallRadius property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-wall-radius").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionAmbientOcclusionWallRadius?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionAmbientOcclusionWallRadius.
     */
    @MapboxExperimental
    val defaultFillExtrusionAmbientOcclusionWallRadiusTransition: StyleTransition?
      /**
       * Get the FillExtrusionAmbientOcclusionWallRadius property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-ambient-occlusion-wall-radius-transition").silentUnwrap()

    /**
     * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`. Default value: 0. Minimum value: 0. The unit of fillExtrusionBase is in meters.
     */
    val defaultFillExtrusionBase: Double?
      /**
       * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`. Default value: 0. Minimum value: 0. The unit of fillExtrusionBase is in meters.
       *
       * Get the default value of FillExtrusionBase property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-base").silentUnwrap()
      }

    /**
     * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`. Default value: 0. Minimum value: 0. The unit of fillExtrusionBase is in meters.
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
     * Controls the behavior of fill extrusion base over terrain Default value: "terrain".
     */
    @MapboxExperimental
    val defaultFillExtrusionBaseAlignment: FillExtrusionBaseAlignment?
      /**
       * Controls the behavior of fill extrusion base over terrain Default value: "terrain".
       *
       * Get the default value of FillExtrusionBaseAlignment property
       *
       * @return FillExtrusionBaseAlignment
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-base-alignment").silentUnwrap<String>()?.let {
          return FillExtrusionBaseAlignment.valueOf(it.uppercase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Controls the behavior of fill extrusion base over terrain Default value: "terrain".
     *
     * This is an Expression representation of "fill-extrusion-base-alignment".
     *
     */
    @MapboxExperimental
    val defaultFillExtrusionBaseAlignmentAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionBaseAlignment property as an Expression
       *
       * @return FillExtrusionBaseAlignment
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-base-alignment").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionBaseAlignment?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
     */
    val defaultFillExtrusionColor: String?
      /**
       * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
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
     * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
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
     * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
     */
    val defaultFillExtrusionColorAsColorInt: Int?
      /**
       * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
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
     * Default color theme for [fillExtrusionColor].
     */
    @MapboxExperimental
    val defaultFillExtrusionColorUseTheme: String?
      /**
       * Get default value of the FillExtrusionColor property as String
       *
       * @return String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-color-use-theme").silentUnwrap()

    /**
     * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. Fade out is implemented by scaling down and removing buildings in the fade range in a staggered fashion. Opacity is not changed. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
     */
    val defaultFillExtrusionCutoffFadeRange: Double?
      /**
       * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. Fade out is implemented by scaling down and removing buildings in the fade range in a staggered fashion. Opacity is not changed. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
       *
       * Get the default value of FillExtrusionCutoffFadeRange property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-cutoff-fade-range").silentUnwrap()
      }

    /**
     * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. Fade out is implemented by scaling down and removing buildings in the fade range in a staggered fashion. Opacity is not changed. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
     *
     * This is an Expression representation of "fill-extrusion-cutoff-fade-range".
     *
     */
    val defaultFillExtrusionCutoffFadeRangeAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionCutoffFadeRange property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-cutoff-fade-range").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionCutoffFadeRange?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillExtrusionEmissiveStrength is in intensity.
     */
    val defaultFillExtrusionEmissiveStrength: Double?
      /**
       * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillExtrusionEmissiveStrength is in intensity.
       *
       * Get the default value of FillExtrusionEmissiveStrength property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-emissive-strength").silentUnwrap()
      }

    /**
     * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillExtrusionEmissiveStrength is in intensity.
     *
     * This is an Expression representation of "fill-extrusion-emissive-strength".
     *
     */
    val defaultFillExtrusionEmissiveStrengthAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionEmissiveStrength property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-emissive-strength").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionEmissiveStrength?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionEmissiveStrength.
     */
    val defaultFillExtrusionEmissiveStrengthTransition: StyleTransition?
      /**
       * Get the FillExtrusionEmissiveStrength property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-emissive-strength-transition").silentUnwrap()

    /**
     * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
     */
    @MapboxExperimental
    val defaultFillExtrusionFloodLightColor: String?
      /**
       * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
       *
       * Get the default value of FillExtrusionFloodLightColor property
       *
       * @return String
       */
      get() {
        defaultFillExtrusionFloodLightColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
     *
     * This is an Expression representation of "fill-extrusion-flood-light-color".
     *
     */
    @MapboxExperimental
    val defaultFillExtrusionFloodLightColorAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionFloodLightColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-flood-light-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
     */
    @MapboxExperimental
    val defaultFillExtrusionFloodLightColorAsColorInt: Int?
      /**
       * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
       *
       * Get the default value of FillExtrusionFloodLightColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultFillExtrusionFloodLightColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionFloodLightColor.
     */
    @MapboxExperimental
    val defaultFillExtrusionFloodLightColorTransition: StyleTransition?
      /**
       * Get the FillExtrusionFloodLightColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-flood-light-color-transition").silentUnwrap()

    /**
     * Default color theme for [fillExtrusionFloodLightColor].
     */
    @MapboxExperimental
    val defaultFillExtrusionFloodLightColorUseTheme: String?
      /**
       * Get default value of the FillExtrusionFloodLightColor property as String
       *
       * @return String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-flood-light-color-use-theme").silentUnwrap()

    /**
     * Provides a control to futher fine-tune the look of the flood light on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
     */
    @MapboxExperimental
    val defaultFillExtrusionFloodLightGroundAttenuation: Double?
      /**
       * Provides a control to futher fine-tune the look of the flood light on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
       *
       * Get the default value of FillExtrusionFloodLightGroundAttenuation property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-flood-light-ground-attenuation").silentUnwrap()
      }

    /**
     * Provides a control to futher fine-tune the look of the flood light on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
     *
     * This is an Expression representation of "fill-extrusion-flood-light-ground-attenuation".
     *
     */
    @MapboxExperimental
    val defaultFillExtrusionFloodLightGroundAttenuationAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionFloodLightGroundAttenuation property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-flood-light-ground-attenuation").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionFloodLightGroundAttenuation?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionFloodLightGroundAttenuation.
     */
    @MapboxExperimental
    val defaultFillExtrusionFloodLightGroundAttenuationTransition: StyleTransition?
      /**
       * Get the FillExtrusionFloodLightGroundAttenuation property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-flood-light-ground-attenuation-transition").silentUnwrap()

    /**
     * The extent of the flood light effect on the ground beneath the extruded buildings in meters. Note: this experimental property is evaluated once per tile, during tile initialization. Changing the property value could trigger tile reload. The `feature-state` styling is deprecated and will get removed soon. Default value: 0. The unit of fillExtrusionFloodLightGroundRadius is in meters.
     */
    @MapboxExperimental
    val defaultFillExtrusionFloodLightGroundRadius: Double?
      /**
       * The extent of the flood light effect on the ground beneath the extruded buildings in meters. Note: this experimental property is evaluated once per tile, during tile initialization. Changing the property value could trigger tile reload. The `feature-state` styling is deprecated and will get removed soon. Default value: 0. The unit of fillExtrusionFloodLightGroundRadius is in meters.
       *
       * Get the default value of FillExtrusionFloodLightGroundRadius property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-flood-light-ground-radius").silentUnwrap()
      }

    /**
     * The extent of the flood light effect on the ground beneath the extruded buildings in meters. Note: this experimental property is evaluated once per tile, during tile initialization. Changing the property value could trigger tile reload. The `feature-state` styling is deprecated and will get removed soon. Default value: 0. The unit of fillExtrusionFloodLightGroundRadius is in meters.
     *
     * This is an Expression representation of "fill-extrusion-flood-light-ground-radius".
     *
     */
    @MapboxExperimental
    val defaultFillExtrusionFloodLightGroundRadiusAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionFloodLightGroundRadius property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-flood-light-ground-radius").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionFloodLightGroundRadius?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionFloodLightGroundRadius.
     */
    @MapboxExperimental
    val defaultFillExtrusionFloodLightGroundRadiusTransition: StyleTransition?
      /**
       * Get the FillExtrusionFloodLightGroundRadius property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-flood-light-ground-radius-transition").silentUnwrap()

    /**
     * The intensity of the flood light color. Default value: 0. Value range: [0, 1]
     */
    @MapboxExperimental
    val defaultFillExtrusionFloodLightIntensity: Double?
      /**
       * The intensity of the flood light color. Default value: 0. Value range: [0, 1]
       *
       * Get the default value of FillExtrusionFloodLightIntensity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-flood-light-intensity").silentUnwrap()
      }

    /**
     * The intensity of the flood light color. Default value: 0. Value range: [0, 1]
     *
     * This is an Expression representation of "fill-extrusion-flood-light-intensity".
     *
     */
    @MapboxExperimental
    val defaultFillExtrusionFloodLightIntensityAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionFloodLightIntensity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-flood-light-intensity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionFloodLightIntensity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionFloodLightIntensity.
     */
    @MapboxExperimental
    val defaultFillExtrusionFloodLightIntensityTransition: StyleTransition?
      /**
       * Get the FillExtrusionFloodLightIntensity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-flood-light-intensity-transition").silentUnwrap()

    /**
     * The extent of the flood light effect on the walls of the extruded buildings in meters. Default value: 0. Minimum value: 0. The unit of fillExtrusionFloodLightWallRadius is in meters.
     */
    @MapboxExperimental
    val defaultFillExtrusionFloodLightWallRadius: Double?
      /**
       * The extent of the flood light effect on the walls of the extruded buildings in meters. Default value: 0. Minimum value: 0. The unit of fillExtrusionFloodLightWallRadius is in meters.
       *
       * Get the default value of FillExtrusionFloodLightWallRadius property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-flood-light-wall-radius").silentUnwrap()
      }

    /**
     * The extent of the flood light effect on the walls of the extruded buildings in meters. Default value: 0. Minimum value: 0. The unit of fillExtrusionFloodLightWallRadius is in meters.
     *
     * This is an Expression representation of "fill-extrusion-flood-light-wall-radius".
     *
     */
    @MapboxExperimental
    val defaultFillExtrusionFloodLightWallRadiusAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionFloodLightWallRadius property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-flood-light-wall-radius").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionFloodLightWallRadius?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionFloodLightWallRadius.
     */
    @MapboxExperimental
    val defaultFillExtrusionFloodLightWallRadiusTransition: StyleTransition?
      /**
       * Get the FillExtrusionFloodLightWallRadius property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-flood-light-wall-radius-transition").silentUnwrap()

    /**
     * The height with which to extrude this layer. Default value: 0. Minimum value: 0. The unit of fillExtrusionHeight is in meters.
     */
    val defaultFillExtrusionHeight: Double?
      /**
       * The height with which to extrude this layer. Default value: 0. Minimum value: 0. The unit of fillExtrusionHeight is in meters.
       *
       * Get the default value of FillExtrusionHeight property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-height").silentUnwrap()
      }

    /**
     * The height with which to extrude this layer. Default value: 0. Minimum value: 0. The unit of fillExtrusionHeight is in meters.
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
     * Controls the behavior of fill extrusion height over terrain Default value: "flat".
     */
    @MapboxExperimental
    val defaultFillExtrusionHeightAlignment: FillExtrusionHeightAlignment?
      /**
       * Controls the behavior of fill extrusion height over terrain Default value: "flat".
       *
       * Get the default value of FillExtrusionHeightAlignment property
       *
       * @return FillExtrusionHeightAlignment
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-height-alignment").silentUnwrap<String>()?.let {
          return FillExtrusionHeightAlignment.valueOf(it.uppercase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Controls the behavior of fill extrusion height over terrain Default value: "flat".
     *
     * This is an Expression representation of "fill-extrusion-height-alignment".
     *
     */
    @MapboxExperimental
    val defaultFillExtrusionHeightAlignmentAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionHeightAlignment property as an Expression
       *
       * @return FillExtrusionHeightAlignment
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-height-alignment").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionHeightAlignment?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * If a non-zero value is provided, it sets the fill-extrusion layer into wall rendering mode. The value is used to render the feature with the given width over the outlines of the geometry. Note: This property is experimental and some other fill-extrusion properties might not be supported with non-zero line width. Default value: 0. Minimum value: 0. The unit of fillExtrusionLineWidth is in meters.
     */
    @MapboxExperimental
    val defaultFillExtrusionLineWidth: Double?
      /**
       * If a non-zero value is provided, it sets the fill-extrusion layer into wall rendering mode. The value is used to render the feature with the given width over the outlines of the geometry. Note: This property is experimental and some other fill-extrusion properties might not be supported with non-zero line width. Default value: 0. Minimum value: 0. The unit of fillExtrusionLineWidth is in meters.
       *
       * Get the default value of FillExtrusionLineWidth property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-line-width").silentUnwrap()
      }

    /**
     * If a non-zero value is provided, it sets the fill-extrusion layer into wall rendering mode. The value is used to render the feature with the given width over the outlines of the geometry. Note: This property is experimental and some other fill-extrusion properties might not be supported with non-zero line width. Default value: 0. Minimum value: 0. The unit of fillExtrusionLineWidth is in meters.
     *
     * This is an Expression representation of "fill-extrusion-line-width".
     *
     */
    @MapboxExperimental
    val defaultFillExtrusionLineWidthAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionLineWidth property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-line-width").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionLineWidth?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionLineWidth.
     */
    @MapboxExperimental
    val defaultFillExtrusionLineWidthTransition: StyleTransition?
      /**
       * Get the FillExtrusionLineWidth property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-line-width-transition").silentUnwrap()

    /**
     * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available. Default value: 1. Value range: [0, 1]
     */
    val defaultFillExtrusionOpacity: Double?
      /**
       * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available. Default value: 1. Value range: [0, 1]
       *
       * Get the default value of FillExtrusionOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-opacity").silentUnwrap()
      }

    /**
     * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available. Default value: 1. Value range: [0, 1]
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
     * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true. Default value: true.
     */
    @MapboxExperimental
    val defaultFillExtrusionRoundedRoof: Boolean?
      /**
       * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true. Default value: true.
       *
       * Get the default value of FillExtrusionRoundedRoof property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-rounded-roof").silentUnwrap()
      }

    /**
     * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true. Default value: true.
     *
     * This is an Expression representation of "fill-extrusion-rounded-roof".
     *
     */
    @MapboxExperimental
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
     * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively. Default value: [0,0]. The unit of fillExtrusionTranslate is in pixels.
     */
    val defaultFillExtrusionTranslate: List<Double>?
      /**
       * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively. Default value: [0,0]. The unit of fillExtrusionTranslate is in pixels.
       *
       * Get the default value of FillExtrusionTranslate property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-translate").silentUnwrap()
      }

    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively. Default value: [0,0]. The unit of fillExtrusionTranslate is in pixels.
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
     * Controls the frame of reference for `fill-extrusion-translate`. Default value: "map".
     */
    val defaultFillExtrusionTranslateAnchor: FillExtrusionTranslateAnchor?
      /**
       * Controls the frame of reference for `fill-extrusion-translate`. Default value: "map".
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
     * Controls the frame of reference for `fill-extrusion-translate`. Default value: "map".
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
     * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down. Default value: true.
     */
    val defaultFillExtrusionVerticalGradient: Boolean?
      /**
       * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down. Default value: true.
       *
       * Get the default value of FillExtrusionVerticalGradient property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-vertical-gradient").silentUnwrap()
      }

    /**
     * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down. Default value: true.
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

    /**
     * A global multiplier that can be used to scale base, height, AO, and flood light of the fill extrusions. Default value: 1. Minimum value: 0.
     */
    @MapboxExperimental
    val defaultFillExtrusionVerticalScale: Double?
      /**
       * A global multiplier that can be used to scale base, height, AO, and flood light of the fill extrusions. Default value: 1. Minimum value: 0.
       *
       * Get the default value of FillExtrusionVerticalScale property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-vertical-scale").silentUnwrap()
      }

    /**
     * A global multiplier that can be used to scale base, height, AO, and flood light of the fill extrusions. Default value: 1. Minimum value: 0.
     *
     * This is an Expression representation of "fill-extrusion-vertical-scale".
     *
     */
    @MapboxExperimental
    val defaultFillExtrusionVerticalScaleAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionVerticalScale property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-vertical-scale").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionVerticalScale?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionVerticalScale.
     */
    @MapboxExperimental
    val defaultFillExtrusionVerticalScaleTransition: StyleTransition?
      /**
       * Get the FillExtrusionVerticalScale property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-vertical-scale-transition").silentUnwrap()
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
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   *
   * @param slot value of slot. Setting it to empty string removes the slot.
   */
  fun slot(slot: String): FillExtrusionLayer

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
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility as Expression
   */
  fun visibility(visibility: Expression): FillExtrusionLayer

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
   * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance. Default value: 0. Value range: [0, 1]
   *
   * @param fillExtrusionEdgeRadius value of fillExtrusionEdgeRadius
   */
  @MapboxExperimental
  fun fillExtrusionEdgeRadius(fillExtrusionEdgeRadius: Double = 0.0): FillExtrusionLayer

  /**
   * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance. Default value: 0. Value range: [0, 1]
   *
   * @param fillExtrusionEdgeRadius value of fillExtrusionEdgeRadius as Expression
   */
  @MapboxExperimental
  fun fillExtrusionEdgeRadius(fillExtrusionEdgeRadius: Expression): FillExtrusionLayer

  /**
   * Provides a control to futher fine-tune the look of the ambient occlusion on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   *
   * @param fillExtrusionAmbientOcclusionGroundAttenuation value of fillExtrusionAmbientOcclusionGroundAttenuation
   */
  @MapboxExperimental
  fun fillExtrusionAmbientOcclusionGroundAttenuation(fillExtrusionAmbientOcclusionGroundAttenuation: Double = 0.69): FillExtrusionLayer

  /**
   * Provides a control to futher fine-tune the look of the ambient occlusion on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   *
   * @param fillExtrusionAmbientOcclusionGroundAttenuation value of fillExtrusionAmbientOcclusionGroundAttenuation as Expression
   */
  @MapboxExperimental
  fun fillExtrusionAmbientOcclusionGroundAttenuation(fillExtrusionAmbientOcclusionGroundAttenuation: Expression): FillExtrusionLayer

  /**
   * Provides a control to futher fine-tune the look of the ambient occlusion on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   *
   * Set the FillExtrusionAmbientOcclusionGroundAttenuation property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun fillExtrusionAmbientOcclusionGroundAttenuationTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * Provides a control to futher fine-tune the look of the ambient occlusion on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   *
   * DSL for [fillExtrusionAmbientOcclusionGroundAttenuationTransition].
   */
  @MapboxExperimental
  fun fillExtrusionAmbientOcclusionGroundAttenuationTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * The extent of the ambient occlusion effect on the ground beneath the extruded buildings in meters. Default value: 3. Minimum value: 0.
   *
   * @param fillExtrusionAmbientOcclusionGroundRadius value of fillExtrusionAmbientOcclusionGroundRadius
   */
  @MapboxExperimental
  fun fillExtrusionAmbientOcclusionGroundRadius(fillExtrusionAmbientOcclusionGroundRadius: Double = 3.0): FillExtrusionLayer

  /**
   * The extent of the ambient occlusion effect on the ground beneath the extruded buildings in meters. Default value: 3. Minimum value: 0.
   *
   * @param fillExtrusionAmbientOcclusionGroundRadius value of fillExtrusionAmbientOcclusionGroundRadius as Expression
   */
  @MapboxExperimental
  fun fillExtrusionAmbientOcclusionGroundRadius(fillExtrusionAmbientOcclusionGroundRadius: Expression): FillExtrusionLayer

  /**
   * The extent of the ambient occlusion effect on the ground beneath the extruded buildings in meters. Default value: 3. Minimum value: 0.
   *
   * Set the FillExtrusionAmbientOcclusionGroundRadius property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun fillExtrusionAmbientOcclusionGroundRadiusTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * The extent of the ambient occlusion effect on the ground beneath the extruded buildings in meters. Default value: 3. Minimum value: 0.
   *
   * DSL for [fillExtrusionAmbientOcclusionGroundRadiusTransition].
   */
  @MapboxExperimental
  fun fillExtrusionAmbientOcclusionGroundRadiusTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings. Default value: 0. Value range: [0, 1]
   *
   * @param fillExtrusionAmbientOcclusionIntensity value of fillExtrusionAmbientOcclusionIntensity
   */
  fun fillExtrusionAmbientOcclusionIntensity(fillExtrusionAmbientOcclusionIntensity: Double = 0.0): FillExtrusionLayer

  /**
   * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings. Default value: 0. Value range: [0, 1]
   *
   * @param fillExtrusionAmbientOcclusionIntensity value of fillExtrusionAmbientOcclusionIntensity as Expression
   */
  fun fillExtrusionAmbientOcclusionIntensity(fillExtrusionAmbientOcclusionIntensity: Expression): FillExtrusionLayer

  /**
   * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings. Default value: 0. Value range: [0, 1]
   *
   * Set the FillExtrusionAmbientOcclusionIntensity property transition options
   *
   * @param options transition options for Double
   */
  fun fillExtrusionAmbientOcclusionIntensityTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings. Default value: 0. Value range: [0, 1]
   *
   * DSL for [fillExtrusionAmbientOcclusionIntensityTransition].
   */
  fun fillExtrusionAmbientOcclusionIntensityTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. This property works only with legacy light. When 3D lights are enabled `fill-extrusion-ambient-occlusion-wall-radius` and `fill-extrusion-ambient-occlusion-ground-radius` are used instead. Default value: 3. Minimum value: 0.
   *
   * @param fillExtrusionAmbientOcclusionRadius value of fillExtrusionAmbientOcclusionRadius
   */
  fun fillExtrusionAmbientOcclusionRadius(fillExtrusionAmbientOcclusionRadius: Double = 3.0): FillExtrusionLayer

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. This property works only with legacy light. When 3D lights are enabled `fill-extrusion-ambient-occlusion-wall-radius` and `fill-extrusion-ambient-occlusion-ground-radius` are used instead. Default value: 3. Minimum value: 0.
   *
   * @param fillExtrusionAmbientOcclusionRadius value of fillExtrusionAmbientOcclusionRadius as Expression
   */
  fun fillExtrusionAmbientOcclusionRadius(fillExtrusionAmbientOcclusionRadius: Expression): FillExtrusionLayer

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. This property works only with legacy light. When 3D lights are enabled `fill-extrusion-ambient-occlusion-wall-radius` and `fill-extrusion-ambient-occlusion-ground-radius` are used instead. Default value: 3. Minimum value: 0.
   *
   * Set the FillExtrusionAmbientOcclusionRadius property transition options
   *
   * @param options transition options for Double
   */
  fun fillExtrusionAmbientOcclusionRadiusTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. This property works only with legacy light. When 3D lights are enabled `fill-extrusion-ambient-occlusion-wall-radius` and `fill-extrusion-ambient-occlusion-ground-radius` are used instead. Default value: 3. Minimum value: 0.
   *
   * DSL for [fillExtrusionAmbientOcclusionRadiusTransition].
   */
  fun fillExtrusionAmbientOcclusionRadiusTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. Default value: 3. Minimum value: 0.
   *
   * @param fillExtrusionAmbientOcclusionWallRadius value of fillExtrusionAmbientOcclusionWallRadius
   */
  @MapboxExperimental
  fun fillExtrusionAmbientOcclusionWallRadius(fillExtrusionAmbientOcclusionWallRadius: Double = 3.0): FillExtrusionLayer

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. Default value: 3. Minimum value: 0.
   *
   * @param fillExtrusionAmbientOcclusionWallRadius value of fillExtrusionAmbientOcclusionWallRadius as Expression
   */
  @MapboxExperimental
  fun fillExtrusionAmbientOcclusionWallRadius(fillExtrusionAmbientOcclusionWallRadius: Expression): FillExtrusionLayer

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. Default value: 3. Minimum value: 0.
   *
   * Set the FillExtrusionAmbientOcclusionWallRadius property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun fillExtrusionAmbientOcclusionWallRadiusTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. Default value: 3. Minimum value: 0.
   *
   * DSL for [fillExtrusionAmbientOcclusionWallRadiusTransition].
   */
  @MapboxExperimental
  fun fillExtrusionAmbientOcclusionWallRadiusTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`. Default value: 0. Minimum value: 0. The unit of fillExtrusionBase is in meters.
   *
   * @param fillExtrusionBase value of fillExtrusionBase
   */
  fun fillExtrusionBase(fillExtrusionBase: Double = 0.0): FillExtrusionLayer

  /**
   * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`. Default value: 0. Minimum value: 0. The unit of fillExtrusionBase is in meters.
   *
   * @param fillExtrusionBase value of fillExtrusionBase as Expression
   */
  fun fillExtrusionBase(fillExtrusionBase: Expression): FillExtrusionLayer

  /**
   * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`. Default value: 0. Minimum value: 0. The unit of fillExtrusionBase is in meters.
   *
   * Set the FillExtrusionBase property transition options
   *
   * @param options transition options for Double
   */
  fun fillExtrusionBaseTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`. Default value: 0. Minimum value: 0. The unit of fillExtrusionBase is in meters.
   *
   * DSL for [fillExtrusionBaseTransition].
   */
  fun fillExtrusionBaseTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * Controls the behavior of fill extrusion base over terrain Default value: "terrain".
   *
   * @param fillExtrusionBaseAlignment value of fillExtrusionBaseAlignment
   */
  @MapboxExperimental
  fun fillExtrusionBaseAlignment(fillExtrusionBaseAlignment: FillExtrusionBaseAlignment = FillExtrusionBaseAlignment.TERRAIN): FillExtrusionLayer

  /**
   * Controls the behavior of fill extrusion base over terrain Default value: "terrain".
   *
   * @param fillExtrusionBaseAlignment value of fillExtrusionBaseAlignment as Expression
   */
  @MapboxExperimental
  fun fillExtrusionBaseAlignment(fillExtrusionBaseAlignment: Expression): FillExtrusionLayer

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
   *
   * @param fillExtrusionColor value of fillExtrusionColor
   */
  fun fillExtrusionColor(fillExtrusionColor: String = "#000000"): FillExtrusionLayer

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
   *
   * @param fillExtrusionColor value of fillExtrusionColor as Expression
   */
  fun fillExtrusionColor(fillExtrusionColor: Expression): FillExtrusionLayer

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
   *
   * @param fillExtrusionColor value of fillExtrusionColor
   */
  fun fillExtrusionColor(@ColorInt fillExtrusionColor: Int): FillExtrusionLayer

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
   *
   * Set the FillExtrusionColor property transition options
   *
   * @param options transition options for String
   */
  fun fillExtrusionColorTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity. Default value: "#000000".
   *
   * DSL for [fillExtrusionColorTransition].
   */
  fun fillExtrusionColorTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * Set the fillExtrusionColorUseTheme as String for [fillExtrusionColor].
   *
   * @param fillExtrusionColorUseTheme overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  fun fillExtrusionColorUseTheme(fillExtrusionColorUseTheme: String): FillExtrusionLayer

  /**
   * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. Fade out is implemented by scaling down and removing buildings in the fade range in a staggered fashion. Opacity is not changed. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
   *
   * @param fillExtrusionCutoffFadeRange value of fillExtrusionCutoffFadeRange
   */
  fun fillExtrusionCutoffFadeRange(fillExtrusionCutoffFadeRange: Double = 0.0): FillExtrusionLayer

  /**
   * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. Fade out is implemented by scaling down and removing buildings in the fade range in a staggered fashion. Opacity is not changed. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
   *
   * @param fillExtrusionCutoffFadeRange value of fillExtrusionCutoffFadeRange as Expression
   */
  fun fillExtrusionCutoffFadeRange(fillExtrusionCutoffFadeRange: Expression): FillExtrusionLayer

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillExtrusionEmissiveStrength is in intensity.
   *
   * @param fillExtrusionEmissiveStrength value of fillExtrusionEmissiveStrength
   */
  fun fillExtrusionEmissiveStrength(fillExtrusionEmissiveStrength: Double = 0.0): FillExtrusionLayer

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillExtrusionEmissiveStrength is in intensity.
   *
   * @param fillExtrusionEmissiveStrength value of fillExtrusionEmissiveStrength as Expression
   */
  fun fillExtrusionEmissiveStrength(fillExtrusionEmissiveStrength: Expression): FillExtrusionLayer

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillExtrusionEmissiveStrength is in intensity.
   *
   * Set the FillExtrusionEmissiveStrength property transition options
   *
   * @param options transition options for Double
   */
  fun fillExtrusionEmissiveStrengthTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillExtrusionEmissiveStrength is in intensity.
   *
   * DSL for [fillExtrusionEmissiveStrengthTransition].
   */
  fun fillExtrusionEmissiveStrengthTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
   *
   * @param fillExtrusionFloodLightColor value of fillExtrusionFloodLightColor
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightColor(fillExtrusionFloodLightColor: String = "#ffffff"): FillExtrusionLayer

  /**
   * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
   *
   * @param fillExtrusionFloodLightColor value of fillExtrusionFloodLightColor as Expression
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightColor(fillExtrusionFloodLightColor: Expression): FillExtrusionLayer

  /**
   * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
   *
   * @param fillExtrusionFloodLightColor value of fillExtrusionFloodLightColor
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightColor(@ColorInt fillExtrusionFloodLightColor: Int): FillExtrusionLayer

  /**
   * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
   *
   * Set the FillExtrusionFloodLightColor property transition options
   *
   * @param options transition options for String
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightColorTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * The color of the flood light effect on the walls of the extruded buildings. Default value: "#ffffff".
   *
   * DSL for [fillExtrusionFloodLightColorTransition].
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightColorTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * Set the fillExtrusionFloodLightColorUseTheme as String for [fillExtrusionFloodLightColor].
   *
   * @param fillExtrusionFloodLightColorUseTheme overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightColorUseTheme(fillExtrusionFloodLightColorUseTheme: String): FillExtrusionLayer

  /**
   * Provides a control to futher fine-tune the look of the flood light on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   *
   * @param fillExtrusionFloodLightGroundAttenuation value of fillExtrusionFloodLightGroundAttenuation
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightGroundAttenuation(fillExtrusionFloodLightGroundAttenuation: Double = 0.69): FillExtrusionLayer

  /**
   * Provides a control to futher fine-tune the look of the flood light on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   *
   * @param fillExtrusionFloodLightGroundAttenuation value of fillExtrusionFloodLightGroundAttenuation as Expression
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightGroundAttenuation(fillExtrusionFloodLightGroundAttenuation: Expression): FillExtrusionLayer

  /**
   * Provides a control to futher fine-tune the look of the flood light on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   *
   * Set the FillExtrusionFloodLightGroundAttenuation property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightGroundAttenuationTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * Provides a control to futher fine-tune the look of the flood light on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother. Default value: 0.69. Value range: [0, 1]
   *
   * DSL for [fillExtrusionFloodLightGroundAttenuationTransition].
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightGroundAttenuationTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * The extent of the flood light effect on the ground beneath the extruded buildings in meters. Note: this experimental property is evaluated once per tile, during tile initialization. Changing the property value could trigger tile reload. The `feature-state` styling is deprecated and will get removed soon. Default value: 0. The unit of fillExtrusionFloodLightGroundRadius is in meters.
   *
   * @param fillExtrusionFloodLightGroundRadius value of fillExtrusionFloodLightGroundRadius
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightGroundRadius(fillExtrusionFloodLightGroundRadius: Double = 0.0): FillExtrusionLayer

  /**
   * The extent of the flood light effect on the ground beneath the extruded buildings in meters. Note: this experimental property is evaluated once per tile, during tile initialization. Changing the property value could trigger tile reload. The `feature-state` styling is deprecated and will get removed soon. Default value: 0. The unit of fillExtrusionFloodLightGroundRadius is in meters.
   *
   * @param fillExtrusionFloodLightGroundRadius value of fillExtrusionFloodLightGroundRadius as Expression
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightGroundRadius(fillExtrusionFloodLightGroundRadius: Expression): FillExtrusionLayer

  /**
   * The extent of the flood light effect on the ground beneath the extruded buildings in meters. Note: this experimental property is evaluated once per tile, during tile initialization. Changing the property value could trigger tile reload. The `feature-state` styling is deprecated and will get removed soon. Default value: 0. The unit of fillExtrusionFloodLightGroundRadius is in meters.
   *
   * Set the FillExtrusionFloodLightGroundRadius property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightGroundRadiusTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * The extent of the flood light effect on the ground beneath the extruded buildings in meters. Note: this experimental property is evaluated once per tile, during tile initialization. Changing the property value could trigger tile reload. The `feature-state` styling is deprecated and will get removed soon. Default value: 0. The unit of fillExtrusionFloodLightGroundRadius is in meters.
   *
   * DSL for [fillExtrusionFloodLightGroundRadiusTransition].
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightGroundRadiusTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * The intensity of the flood light color. Default value: 0. Value range: [0, 1]
   *
   * @param fillExtrusionFloodLightIntensity value of fillExtrusionFloodLightIntensity
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightIntensity(fillExtrusionFloodLightIntensity: Double = 0.0): FillExtrusionLayer

  /**
   * The intensity of the flood light color. Default value: 0. Value range: [0, 1]
   *
   * @param fillExtrusionFloodLightIntensity value of fillExtrusionFloodLightIntensity as Expression
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightIntensity(fillExtrusionFloodLightIntensity: Expression): FillExtrusionLayer

  /**
   * The intensity of the flood light color. Default value: 0. Value range: [0, 1]
   *
   * Set the FillExtrusionFloodLightIntensity property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightIntensityTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * The intensity of the flood light color. Default value: 0. Value range: [0, 1]
   *
   * DSL for [fillExtrusionFloodLightIntensityTransition].
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightIntensityTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * The extent of the flood light effect on the walls of the extruded buildings in meters. Default value: 0. Minimum value: 0. The unit of fillExtrusionFloodLightWallRadius is in meters.
   *
   * @param fillExtrusionFloodLightWallRadius value of fillExtrusionFloodLightWallRadius
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightWallRadius(fillExtrusionFloodLightWallRadius: Double = 0.0): FillExtrusionLayer

  /**
   * The extent of the flood light effect on the walls of the extruded buildings in meters. Default value: 0. Minimum value: 0. The unit of fillExtrusionFloodLightWallRadius is in meters.
   *
   * @param fillExtrusionFloodLightWallRadius value of fillExtrusionFloodLightWallRadius as Expression
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightWallRadius(fillExtrusionFloodLightWallRadius: Expression): FillExtrusionLayer

  /**
   * The extent of the flood light effect on the walls of the extruded buildings in meters. Default value: 0. Minimum value: 0. The unit of fillExtrusionFloodLightWallRadius is in meters.
   *
   * Set the FillExtrusionFloodLightWallRadius property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightWallRadiusTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * The extent of the flood light effect on the walls of the extruded buildings in meters. Default value: 0. Minimum value: 0. The unit of fillExtrusionFloodLightWallRadius is in meters.
   *
   * DSL for [fillExtrusionFloodLightWallRadiusTransition].
   */
  @MapboxExperimental
  fun fillExtrusionFloodLightWallRadiusTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * The height with which to extrude this layer. Default value: 0. Minimum value: 0. The unit of fillExtrusionHeight is in meters.
   *
   * @param fillExtrusionHeight value of fillExtrusionHeight
   */
  fun fillExtrusionHeight(fillExtrusionHeight: Double = 0.0): FillExtrusionLayer

  /**
   * The height with which to extrude this layer. Default value: 0. Minimum value: 0. The unit of fillExtrusionHeight is in meters.
   *
   * @param fillExtrusionHeight value of fillExtrusionHeight as Expression
   */
  fun fillExtrusionHeight(fillExtrusionHeight: Expression): FillExtrusionLayer

  /**
   * The height with which to extrude this layer. Default value: 0. Minimum value: 0. The unit of fillExtrusionHeight is in meters.
   *
   * Set the FillExtrusionHeight property transition options
   *
   * @param options transition options for Double
   */
  fun fillExtrusionHeightTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * The height with which to extrude this layer. Default value: 0. Minimum value: 0. The unit of fillExtrusionHeight is in meters.
   *
   * DSL for [fillExtrusionHeightTransition].
   */
  fun fillExtrusionHeightTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * Controls the behavior of fill extrusion height over terrain Default value: "flat".
   *
   * @param fillExtrusionHeightAlignment value of fillExtrusionHeightAlignment
   */
  @MapboxExperimental
  fun fillExtrusionHeightAlignment(fillExtrusionHeightAlignment: FillExtrusionHeightAlignment = FillExtrusionHeightAlignment.FLAT): FillExtrusionLayer

  /**
   * Controls the behavior of fill extrusion height over terrain Default value: "flat".
   *
   * @param fillExtrusionHeightAlignment value of fillExtrusionHeightAlignment as Expression
   */
  @MapboxExperimental
  fun fillExtrusionHeightAlignment(fillExtrusionHeightAlignment: Expression): FillExtrusionLayer

  /**
   * If a non-zero value is provided, it sets the fill-extrusion layer into wall rendering mode. The value is used to render the feature with the given width over the outlines of the geometry. Note: This property is experimental and some other fill-extrusion properties might not be supported with non-zero line width. Default value: 0. Minimum value: 0. The unit of fillExtrusionLineWidth is in meters.
   *
   * @param fillExtrusionLineWidth value of fillExtrusionLineWidth
   */
  @MapboxExperimental
  fun fillExtrusionLineWidth(fillExtrusionLineWidth: Double = 0.0): FillExtrusionLayer

  /**
   * If a non-zero value is provided, it sets the fill-extrusion layer into wall rendering mode. The value is used to render the feature with the given width over the outlines of the geometry. Note: This property is experimental and some other fill-extrusion properties might not be supported with non-zero line width. Default value: 0. Minimum value: 0. The unit of fillExtrusionLineWidth is in meters.
   *
   * @param fillExtrusionLineWidth value of fillExtrusionLineWidth as Expression
   */
  @MapboxExperimental
  fun fillExtrusionLineWidth(fillExtrusionLineWidth: Expression): FillExtrusionLayer

  /**
   * If a non-zero value is provided, it sets the fill-extrusion layer into wall rendering mode. The value is used to render the feature with the given width over the outlines of the geometry. Note: This property is experimental and some other fill-extrusion properties might not be supported with non-zero line width. Default value: 0. Minimum value: 0. The unit of fillExtrusionLineWidth is in meters.
   *
   * Set the FillExtrusionLineWidth property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun fillExtrusionLineWidthTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * If a non-zero value is provided, it sets the fill-extrusion layer into wall rendering mode. The value is used to render the feature with the given width over the outlines of the geometry. Note: This property is experimental and some other fill-extrusion properties might not be supported with non-zero line width. Default value: 0. Minimum value: 0. The unit of fillExtrusionLineWidth is in meters.
   *
   * DSL for [fillExtrusionLineWidthTransition].
   */
  @MapboxExperimental
  fun fillExtrusionLineWidthTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available. Default value: 1. Value range: [0, 1]
   *
   * @param fillExtrusionOpacity value of fillExtrusionOpacity
   */
  fun fillExtrusionOpacity(fillExtrusionOpacity: Double = 1.0): FillExtrusionLayer

  /**
   * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available. Default value: 1. Value range: [0, 1]
   *
   * @param fillExtrusionOpacity value of fillExtrusionOpacity as Expression
   */
  fun fillExtrusionOpacity(fillExtrusionOpacity: Expression): FillExtrusionLayer

  /**
   * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available. Default value: 1. Value range: [0, 1]
   *
   * Set the FillExtrusionOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun fillExtrusionOpacityTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available. Default value: 1. Value range: [0, 1]
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
   * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true. Default value: true.
   *
   * @param fillExtrusionRoundedRoof value of fillExtrusionRoundedRoof
   */
  @MapboxExperimental
  fun fillExtrusionRoundedRoof(fillExtrusionRoundedRoof: Boolean = true): FillExtrusionLayer

  /**
   * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true. Default value: true.
   *
   * @param fillExtrusionRoundedRoof value of fillExtrusionRoundedRoof as Expression
   */
  @MapboxExperimental
  fun fillExtrusionRoundedRoof(fillExtrusionRoundedRoof: Expression): FillExtrusionLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively. Default value: [0,0]. The unit of fillExtrusionTranslate is in pixels.
   *
   * @param fillExtrusionTranslate value of fillExtrusionTranslate
   */
  fun fillExtrusionTranslate(fillExtrusionTranslate: List<Double> = listOf(0.0, 0.0)): FillExtrusionLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively. Default value: [0,0]. The unit of fillExtrusionTranslate is in pixels.
   *
   * @param fillExtrusionTranslate value of fillExtrusionTranslate as Expression
   */
  fun fillExtrusionTranslate(fillExtrusionTranslate: Expression): FillExtrusionLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively. Default value: [0,0]. The unit of fillExtrusionTranslate is in pixels.
   *
   * Set the FillExtrusionTranslate property transition options
   *
   * @param options transition options for List<Double>
   */
  fun fillExtrusionTranslateTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively. Default value: [0,0]. The unit of fillExtrusionTranslate is in pixels.
   *
   * DSL for [fillExtrusionTranslateTransition].
   */
  fun fillExtrusionTranslateTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * Controls the frame of reference for `fill-extrusion-translate`. Default value: "map".
   *
   * @param fillExtrusionTranslateAnchor value of fillExtrusionTranslateAnchor
   */
  fun fillExtrusionTranslateAnchor(fillExtrusionTranslateAnchor: FillExtrusionTranslateAnchor = FillExtrusionTranslateAnchor.MAP): FillExtrusionLayer

  /**
   * Controls the frame of reference for `fill-extrusion-translate`. Default value: "map".
   *
   * @param fillExtrusionTranslateAnchor value of fillExtrusionTranslateAnchor as Expression
   */
  fun fillExtrusionTranslateAnchor(fillExtrusionTranslateAnchor: Expression): FillExtrusionLayer

  /**
   * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down. Default value: true.
   *
   * @param fillExtrusionVerticalGradient value of fillExtrusionVerticalGradient
   */
  fun fillExtrusionVerticalGradient(fillExtrusionVerticalGradient: Boolean = true): FillExtrusionLayer

  /**
   * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down. Default value: true.
   *
   * @param fillExtrusionVerticalGradient value of fillExtrusionVerticalGradient as Expression
   */
  fun fillExtrusionVerticalGradient(fillExtrusionVerticalGradient: Expression): FillExtrusionLayer

  /**
   * A global multiplier that can be used to scale base, height, AO, and flood light of the fill extrusions. Default value: 1. Minimum value: 0.
   *
   * @param fillExtrusionVerticalScale value of fillExtrusionVerticalScale
   */
  @MapboxExperimental
  fun fillExtrusionVerticalScale(fillExtrusionVerticalScale: Double = 1.0): FillExtrusionLayer

  /**
   * A global multiplier that can be used to scale base, height, AO, and flood light of the fill extrusions. Default value: 1. Minimum value: 0.
   *
   * @param fillExtrusionVerticalScale value of fillExtrusionVerticalScale as Expression
   */
  @MapboxExperimental
  fun fillExtrusionVerticalScale(fillExtrusionVerticalScale: Expression): FillExtrusionLayer

  /**
   * A global multiplier that can be used to scale base, height, AO, and flood light of the fill extrusions. Default value: 1. Minimum value: 0.
   *
   * Set the FillExtrusionVerticalScale property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun fillExtrusionVerticalScaleTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * A global multiplier that can be used to scale base, height, AO, and flood light of the fill extrusions. Default value: 1. Minimum value: 0.
   *
   * DSL for [fillExtrusionVerticalScaleTransition].
   */
  @MapboxExperimental
  fun fillExtrusionVerticalScaleTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer
}

/**
 * DSL function for creating a [FillExtrusionLayer].
 */
fun fillExtrusionLayer(layerId: String, sourceId: String, block: FillExtrusionLayerDsl.() -> Unit): FillExtrusionLayer = FillExtrusionLayer(layerId, sourceId).apply(block)

// End of generated file.