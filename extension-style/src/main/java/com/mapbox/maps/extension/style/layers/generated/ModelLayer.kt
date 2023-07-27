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
 * A layer to render 3D Models.
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
 */
@UiThread
@MapboxExperimental
class ModelLayer(override val layerId: String, val sourceId: String) : ModelLayerDsl, Layer() {
  init {
    internalSourceId = sourceId
  }

  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  override fun sourceLayer(sourceLayer: String): ModelLayer = apply {
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
   * @param slot value of slot
   */
  @MapboxExperimental
  override fun slot(slot: String): ModelLayer = apply {
    val param = PropertyValue("slot", slot)
    setProperty(param)
  }

  /**
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   */
  @MapboxExperimental
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
  override fun filter(filter: Expression): ModelLayer = apply {
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
     * Use static method [ModelLayer.defaultVisibility] to get the default property value.
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
     * Use static method [ModelLayer.defaultVisibility] to get the default property value.
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
   * Use static method [ModelLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Visibility): ModelLayer = apply {
    val propertyValue = PropertyValue("visibility", visibility)
    setProperty(propertyValue)
  }

  /**
   * Whether this layer is displayed.
   *
   * Use static method [[ModelLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Expression): ModelLayer = apply {
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
     * Use static method [ModelLayer.defaultMinZoom] to get the default property value.
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
   * Use static method [ModelLayer.defaultMinZoom] to get the default property value.
   *
   * @param minZoom value of minzoom
   */
  override fun minZoom(minZoom: Double): ModelLayer = apply {
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
     * Use static method [ModelLayer.defaultMaxZoom] to get the default property value.
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
   * Use static method [ModelLayer.defaultMaxZoom] to get the default property value.
   *
   * @param maxZoom value of maxzoom
   */
  override fun maxZoom(maxZoom: Double): ModelLayer = apply {
    val param = PropertyValue("maxzoom", maxZoom)
    setProperty(param)
  }

  // Property getters and setters

  /**
   * Model to render.
   */
  @MapboxExperimental
  val modelId: String?
    /**
     * Model to render.
     *
     * Use static method [ModelLayer.defaultModelId] to get the default property.
     *
     * @return String
     */
    get() {
      return getPropertyValue("model-id")
    }

  /**
   * Model to render.
   *
   * Use static method [ModelLayer.defaultModelId] to set the default property.
   *
   * @param modelId value of modelId
   */
  @MapboxExperimental
  override fun modelId(modelId: String): ModelLayer = apply {
    val propertyValue = PropertyValue("model-id", modelId)
    setProperty(propertyValue)
  }

  /**
   * Model to render.
   *
   * This is an Expression representation of "model-id".
   *
   */
  @MapboxExperimental
  val modelIdAsExpression: Expression?
    /**
     * Model to render.
     *
     * Get the ModelId property as an Expression
     *
     * Use static method [ModelLayer.defaultModelIdAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("model-id")?.let {
        return it
      }
      modelId?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Model to render.
   *
   * Use static method [ModelLayer.defaultModelIdAsExpression] to set the default property.
   *
   * @param modelId value of modelId as Expression
   */
  @MapboxExperimental
  override fun modelId(modelId: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-id", modelId)
    setProperty(propertyValue)
  }

  /**
   * Intensity of the ambient occlusion if present in the 3D model.
   */
  @MapboxExperimental
  val modelAmbientOcclusionIntensity: Double?
    /**
     * Intensity of the ambient occlusion if present in the 3D model.
     *
     * Use static method [ModelLayer.defaultModelAmbientOcclusionIntensity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("model-ambient-occlusion-intensity")
    }

  /**
   * Intensity of the ambient occlusion if present in the 3D model.
   *
   * Use static method [ModelLayer.defaultModelAmbientOcclusionIntensity] to set the default property.
   *
   * @param modelAmbientOcclusionIntensity value of modelAmbientOcclusionIntensity
   */
  @MapboxExperimental
  override fun modelAmbientOcclusionIntensity(modelAmbientOcclusionIntensity: Double): ModelLayer = apply {
    val propertyValue = PropertyValue("model-ambient-occlusion-intensity", modelAmbientOcclusionIntensity)
    setProperty(propertyValue)
  }

  /**
   * Intensity of the ambient occlusion if present in the 3D model.
   *
   * This is an Expression representation of "model-ambient-occlusion-intensity".
   *
   */
  @MapboxExperimental
  val modelAmbientOcclusionIntensityAsExpression: Expression?
    /**
     * Intensity of the ambient occlusion if present in the 3D model.
     *
     * Get the ModelAmbientOcclusionIntensity property as an Expression
     *
     * Use static method [ModelLayer.defaultModelAmbientOcclusionIntensityAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("model-ambient-occlusion-intensity")?.let {
        return it
      }
      modelAmbientOcclusionIntensity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Intensity of the ambient occlusion if present in the 3D model.
   *
   * Use static method [ModelLayer.defaultModelAmbientOcclusionIntensityAsExpression] to set the default property.
   *
   * @param modelAmbientOcclusionIntensity value of modelAmbientOcclusionIntensity as Expression
   */
  @MapboxExperimental
  override fun modelAmbientOcclusionIntensity(modelAmbientOcclusionIntensity: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-ambient-occlusion-intensity", modelAmbientOcclusionIntensity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelAmbientOcclusionIntensity.
   */
  @MapboxExperimental
  val modelAmbientOcclusionIntensityTransition: StyleTransition?
    /**
     * Get the ModelAmbientOcclusionIntensity property transition options
     *
     * Use static method [ModelLayer.defaultModelAmbientOcclusionIntensityTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("model-ambient-occlusion-intensity-transition")
    }

  /**
   * Set the ModelAmbientOcclusionIntensity property transition options
   *
   * Use static method [ModelLayer.defaultModelAmbientOcclusionIntensityTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun modelAmbientOcclusionIntensityTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-ambient-occlusion-intensity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelAmbientOcclusionIntensityTransition].
   */
  @MapboxExperimental
  override fun modelAmbientOcclusionIntensityTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelAmbientOcclusionIntensityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Enable/Disable shadow casting for this layer
   */
  @MapboxExperimental
  val modelCastShadows: Boolean?
    /**
     * Enable/Disable shadow casting for this layer
     *
     * Use static method [ModelLayer.defaultModelCastShadows] to get the default property.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("model-cast-shadows")
    }

  /**
   * Enable/Disable shadow casting for this layer
   *
   * Use static method [ModelLayer.defaultModelCastShadows] to set the default property.
   *
   * @param modelCastShadows value of modelCastShadows
   */
  @MapboxExperimental
  override fun modelCastShadows(modelCastShadows: Boolean): ModelLayer = apply {
    val propertyValue = PropertyValue("model-cast-shadows", modelCastShadows)
    setProperty(propertyValue)
  }

  /**
   * Enable/Disable shadow casting for this layer
   *
   * This is an Expression representation of "model-cast-shadows".
   *
   */
  @MapboxExperimental
  val modelCastShadowsAsExpression: Expression?
    /**
     * Enable/Disable shadow casting for this layer
     *
     * Get the ModelCastShadows property as an Expression
     *
     * Use static method [ModelLayer.defaultModelCastShadowsAsExpression] to get the default property.
     *
     * @return Boolean
     */
    get() {
      getPropertyValue<Expression>("model-cast-shadows")?.let {
        return it
      }
      modelCastShadows?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Enable/Disable shadow casting for this layer
   *
   * Use static method [ModelLayer.defaultModelCastShadowsAsExpression] to set the default property.
   *
   * @param modelCastShadows value of modelCastShadows as Expression
   */
  @MapboxExperimental
  override fun modelCastShadows(modelCastShadows: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-cast-shadows", modelCastShadows)
    setProperty(propertyValue)
  }

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
   */
  @MapboxExperimental
  val modelColor: String?
    /**
     * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
     *
     * Use static method [ModelLayer.defaultModelColor] to get the default property.
     *
     * @return String
     */
    get() {
      modelColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
   *
   * Use static method [ModelLayer.defaultModelColor] to set the default property.
   *
   * @param modelColor value of modelColor
   */
  @MapboxExperimental
  override fun modelColor(modelColor: String): ModelLayer = apply {
    val propertyValue = PropertyValue("model-color", modelColor)
    setProperty(propertyValue)
  }

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
   *
   * This is an Expression representation of "model-color".
   *
   */
  @MapboxExperimental
  val modelColorAsExpression: Expression?
    /**
     * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
     *
     * Get the ModelColor property as an Expression
     *
     * Use static method [ModelLayer.defaultModelColorAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("model-color")?.let {
        return it
      }
      return null
    }

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
   *
   * Use static method [ModelLayer.defaultModelColorAsExpression] to set the default property.
   *
   * @param modelColor value of modelColor as Expression
   */
  @MapboxExperimental
  override fun modelColor(modelColor: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-color", modelColor)
    setProperty(propertyValue)
  }

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
   */
  @MapboxExperimental
  val modelColorAsColorInt: Int?
    /**
     * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
     *
     * Use static method [ModelLayer.defaultModelColorAsColorInt] to get the default property.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      modelColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
   *
   * Use static method [ModelLayer.defaultModelColorAsColorInt] to set the default property.
   *
   * @param modelColor value of modelColor
   */
  @MapboxExperimental
  override fun modelColor(@ColorInt modelColor: Int): ModelLayer = apply {
    val propertyValue = PropertyValue("model-color", colorIntToRgbaExpression(modelColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelColor.
   */
  @MapboxExperimental
  val modelColorTransition: StyleTransition?
    /**
     * Get the ModelColor property transition options
     *
     * Use static method [ModelLayer.defaultModelColorTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("model-color-transition")
    }

  /**
   * Set the ModelColor property transition options
   *
   * Use static method [ModelLayer.defaultModelColorTransition] to set the default property.
   *
   * @param options transition options for String
   */
  @MapboxExperimental
  override fun modelColorTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelColorTransition].
   */
  @MapboxExperimental
  override fun modelColorTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix.
   */
  @MapboxExperimental
  val modelColorMixIntensity: Double?
    /**
     * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix.
     *
     * Use static method [ModelLayer.defaultModelColorMixIntensity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("model-color-mix-intensity")
    }

  /**
   * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix.
   *
   * Use static method [ModelLayer.defaultModelColorMixIntensity] to set the default property.
   *
   * @param modelColorMixIntensity value of modelColorMixIntensity
   */
  @MapboxExperimental
  override fun modelColorMixIntensity(modelColorMixIntensity: Double): ModelLayer = apply {
    val propertyValue = PropertyValue("model-color-mix-intensity", modelColorMixIntensity)
    setProperty(propertyValue)
  }

  /**
   * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix.
   *
   * This is an Expression representation of "model-color-mix-intensity".
   *
   */
  @MapboxExperimental
  val modelColorMixIntensityAsExpression: Expression?
    /**
     * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix.
     *
     * Get the ModelColorMixIntensity property as an Expression
     *
     * Use static method [ModelLayer.defaultModelColorMixIntensityAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("model-color-mix-intensity")?.let {
        return it
      }
      modelColorMixIntensity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix.
   *
   * Use static method [ModelLayer.defaultModelColorMixIntensityAsExpression] to set the default property.
   *
   * @param modelColorMixIntensity value of modelColorMixIntensity as Expression
   */
  @MapboxExperimental
  override fun modelColorMixIntensity(modelColorMixIntensity: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-color-mix-intensity", modelColorMixIntensity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelColorMixIntensity.
   */
  @MapboxExperimental
  val modelColorMixIntensityTransition: StyleTransition?
    /**
     * Get the ModelColorMixIntensity property transition options
     *
     * Use static method [ModelLayer.defaultModelColorMixIntensityTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("model-color-mix-intensity-transition")
    }

  /**
   * Set the ModelColorMixIntensity property transition options
   *
   * Use static method [ModelLayer.defaultModelColorMixIntensityTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun modelColorMixIntensityTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-color-mix-intensity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelColorMixIntensityTransition].
   */
  @MapboxExperimental
  override fun modelColorMixIntensityTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelColorMixIntensityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source.
   */
  @MapboxExperimental
  val modelEmissiveStrength: Double?
    /**
     * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source.
     *
     * Use static method [ModelLayer.defaultModelEmissiveStrength] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("model-emissive-strength")
    }

  /**
   * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source.
   *
   * Use static method [ModelLayer.defaultModelEmissiveStrength] to set the default property.
   *
   * @param modelEmissiveStrength value of modelEmissiveStrength
   */
  @MapboxExperimental
  override fun modelEmissiveStrength(modelEmissiveStrength: Double): ModelLayer = apply {
    val propertyValue = PropertyValue("model-emissive-strength", modelEmissiveStrength)
    setProperty(propertyValue)
  }

  /**
   * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source.
   *
   * This is an Expression representation of "model-emissive-strength".
   *
   */
  @MapboxExperimental
  val modelEmissiveStrengthAsExpression: Expression?
    /**
     * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source.
     *
     * Get the ModelEmissiveStrength property as an Expression
     *
     * Use static method [ModelLayer.defaultModelEmissiveStrengthAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("model-emissive-strength")?.let {
        return it
      }
      modelEmissiveStrength?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source.
   *
   * Use static method [ModelLayer.defaultModelEmissiveStrengthAsExpression] to set the default property.
   *
   * @param modelEmissiveStrength value of modelEmissiveStrength as Expression
   */
  @MapboxExperimental
  override fun modelEmissiveStrength(modelEmissiveStrength: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-emissive-strength", modelEmissiveStrength)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelEmissiveStrength.
   */
  @MapboxExperimental
  val modelEmissiveStrengthTransition: StyleTransition?
    /**
     * Get the ModelEmissiveStrength property transition options
     *
     * Use static method [ModelLayer.defaultModelEmissiveStrengthTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("model-emissive-strength-transition")
    }

  /**
   * Set the ModelEmissiveStrength property transition options
   *
   * Use static method [ModelLayer.defaultModelEmissiveStrengthTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun modelEmissiveStrengthTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-emissive-strength-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelEmissiveStrengthTransition].
   */
  @MapboxExperimental
  override fun modelEmissiveStrengthTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelEmissiveStrengthTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)).
   */
  @MapboxExperimental
  val modelHeightBasedEmissiveStrengthMultiplier: List<Double>?
    /**
     * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)).
     *
     * Use static method [ModelLayer.defaultModelHeightBasedEmissiveStrengthMultiplier] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("model-height-based-emissive-strength-multiplier")
    }

  /**
   * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)).
   *
   * Use static method [ModelLayer.defaultModelHeightBasedEmissiveStrengthMultiplier] to set the default property.
   *
   * @param modelHeightBasedEmissiveStrengthMultiplier value of modelHeightBasedEmissiveStrengthMultiplier
   */
  @MapboxExperimental
  override fun modelHeightBasedEmissiveStrengthMultiplier(modelHeightBasedEmissiveStrengthMultiplier: List<Double>): ModelLayer = apply {
    val propertyValue = PropertyValue("model-height-based-emissive-strength-multiplier", modelHeightBasedEmissiveStrengthMultiplier)
    setProperty(propertyValue)
  }

  /**
   * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)).
   *
   * This is an Expression representation of "model-height-based-emissive-strength-multiplier".
   *
   */
  @MapboxExperimental
  val modelHeightBasedEmissiveStrengthMultiplierAsExpression: Expression?
    /**
     * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)).
     *
     * Get the ModelHeightBasedEmissiveStrengthMultiplier property as an Expression
     *
     * Use static method [ModelLayer.defaultModelHeightBasedEmissiveStrengthMultiplierAsExpression] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("model-height-based-emissive-strength-multiplier")?.let {
        return it
      }
      modelHeightBasedEmissiveStrengthMultiplier?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)).
   *
   * Use static method [ModelLayer.defaultModelHeightBasedEmissiveStrengthMultiplierAsExpression] to set the default property.
   *
   * @param modelHeightBasedEmissiveStrengthMultiplier value of modelHeightBasedEmissiveStrengthMultiplier as Expression
   */
  @MapboxExperimental
  override fun modelHeightBasedEmissiveStrengthMultiplier(modelHeightBasedEmissiveStrengthMultiplier: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-height-based-emissive-strength-multiplier", modelHeightBasedEmissiveStrengthMultiplier)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelHeightBasedEmissiveStrengthMultiplier.
   */
  @MapboxExperimental
  val modelHeightBasedEmissiveStrengthMultiplierTransition: StyleTransition?
    /**
     * Get the ModelHeightBasedEmissiveStrengthMultiplier property transition options
     *
     * Use static method [ModelLayer.defaultModelHeightBasedEmissiveStrengthMultiplierTransition] to get the default property.
     *
     * @return transition options for List<Double>
     */
    get() {
      return getPropertyValue("model-height-based-emissive-strength-multiplier-transition")
    }

  /**
   * Set the ModelHeightBasedEmissiveStrengthMultiplier property transition options
   *
   * Use static method [ModelLayer.defaultModelHeightBasedEmissiveStrengthMultiplierTransition] to set the default property.
   *
   * @param options transition options for List<Double>
   */
  @MapboxExperimental
  override fun modelHeightBasedEmissiveStrengthMultiplierTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-height-based-emissive-strength-multiplier-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelHeightBasedEmissiveStrengthMultiplierTransition].
   */
  @MapboxExperimental
  override fun modelHeightBasedEmissiveStrengthMultiplierTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelHeightBasedEmissiveStrengthMultiplierTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity of the model layer.
   */
  @MapboxExperimental
  val modelOpacity: Double?
    /**
     * The opacity of the model layer.
     *
     * Use static method [ModelLayer.defaultModelOpacity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("model-opacity")
    }

  /**
   * The opacity of the model layer.
   *
   * Use static method [ModelLayer.defaultModelOpacity] to set the default property.
   *
   * @param modelOpacity value of modelOpacity
   */
  @MapboxExperimental
  override fun modelOpacity(modelOpacity: Double): ModelLayer = apply {
    val propertyValue = PropertyValue("model-opacity", modelOpacity)
    setProperty(propertyValue)
  }

  /**
   * The opacity of the model layer.
   *
   * This is an Expression representation of "model-opacity".
   *
   */
  @MapboxExperimental
  val modelOpacityAsExpression: Expression?
    /**
     * The opacity of the model layer.
     *
     * Get the ModelOpacity property as an Expression
     *
     * Use static method [ModelLayer.defaultModelOpacityAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("model-opacity")?.let {
        return it
      }
      modelOpacity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The opacity of the model layer.
   *
   * Use static method [ModelLayer.defaultModelOpacityAsExpression] to set the default property.
   *
   * @param modelOpacity value of modelOpacity as Expression
   */
  @MapboxExperimental
  override fun modelOpacity(modelOpacity: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-opacity", modelOpacity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelOpacity.
   */
  @MapboxExperimental
  val modelOpacityTransition: StyleTransition?
    /**
     * Get the ModelOpacity property transition options
     *
     * Use static method [ModelLayer.defaultModelOpacityTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("model-opacity-transition")
    }

  /**
   * Set the ModelOpacity property transition options
   *
   * Use static method [ModelLayer.defaultModelOpacityTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun modelOpacityTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelOpacityTransition].
   */
  @MapboxExperimental
  override fun modelOpacityTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Enable/Disable shadow receiving for this layer
   */
  @MapboxExperimental
  val modelReceiveShadows: Boolean?
    /**
     * Enable/Disable shadow receiving for this layer
     *
     * Use static method [ModelLayer.defaultModelReceiveShadows] to get the default property.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("model-receive-shadows")
    }

  /**
   * Enable/Disable shadow receiving for this layer
   *
   * Use static method [ModelLayer.defaultModelReceiveShadows] to set the default property.
   *
   * @param modelReceiveShadows value of modelReceiveShadows
   */
  @MapboxExperimental
  override fun modelReceiveShadows(modelReceiveShadows: Boolean): ModelLayer = apply {
    val propertyValue = PropertyValue("model-receive-shadows", modelReceiveShadows)
    setProperty(propertyValue)
  }

  /**
   * Enable/Disable shadow receiving for this layer
   *
   * This is an Expression representation of "model-receive-shadows".
   *
   */
  @MapboxExperimental
  val modelReceiveShadowsAsExpression: Expression?
    /**
     * Enable/Disable shadow receiving for this layer
     *
     * Get the ModelReceiveShadows property as an Expression
     *
     * Use static method [ModelLayer.defaultModelReceiveShadowsAsExpression] to get the default property.
     *
     * @return Boolean
     */
    get() {
      getPropertyValue<Expression>("model-receive-shadows")?.let {
        return it
      }
      modelReceiveShadows?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Enable/Disable shadow receiving for this layer
   *
   * Use static method [ModelLayer.defaultModelReceiveShadowsAsExpression] to set the default property.
   *
   * @param modelReceiveShadows value of modelReceiveShadows as Expression
   */
  @MapboxExperimental
  override fun modelReceiveShadows(modelReceiveShadows: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-receive-shadows", modelReceiveShadows)
    setProperty(propertyValue)
  }

  /**
   * The rotation of the model in euler angles [lon, lat, z].
   */
  @MapboxExperimental
  val modelRotation: List<Double>?
    /**
     * The rotation of the model in euler angles [lon, lat, z].
     *
     * Use static method [ModelLayer.defaultModelRotation] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("model-rotation")
    }

  /**
   * The rotation of the model in euler angles [lon, lat, z].
   *
   * Use static method [ModelLayer.defaultModelRotation] to set the default property.
   *
   * @param modelRotation value of modelRotation
   */
  @MapboxExperimental
  override fun modelRotation(modelRotation: List<Double>): ModelLayer = apply {
    val propertyValue = PropertyValue("model-rotation", modelRotation)
    setProperty(propertyValue)
  }

  /**
   * The rotation of the model in euler angles [lon, lat, z].
   *
   * This is an Expression representation of "model-rotation".
   *
   */
  @MapboxExperimental
  val modelRotationAsExpression: Expression?
    /**
     * The rotation of the model in euler angles [lon, lat, z].
     *
     * Get the ModelRotation property as an Expression
     *
     * Use static method [ModelLayer.defaultModelRotationAsExpression] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("model-rotation")?.let {
        return it
      }
      modelRotation?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The rotation of the model in euler angles [lon, lat, z].
   *
   * Use static method [ModelLayer.defaultModelRotationAsExpression] to set the default property.
   *
   * @param modelRotation value of modelRotation as Expression
   */
  @MapboxExperimental
  override fun modelRotation(modelRotation: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-rotation", modelRotation)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelRotation.
   */
  @MapboxExperimental
  val modelRotationTransition: StyleTransition?
    /**
     * Get the ModelRotation property transition options
     *
     * Use static method [ModelLayer.defaultModelRotationTransition] to get the default property.
     *
     * @return transition options for List<Double>
     */
    get() {
      return getPropertyValue("model-rotation-transition")
    }

  /**
   * Set the ModelRotation property transition options
   *
   * Use static method [ModelLayer.defaultModelRotationTransition] to set the default property.
   *
   * @param options transition options for List<Double>
   */
  @MapboxExperimental
  override fun modelRotationTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-rotation-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelRotationTransition].
   */
  @MapboxExperimental
  override fun modelRotationTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelRotationTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source.
   */
  @MapboxExperimental
  val modelRoughness: Double?
    /**
     * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source.
     *
     * Use static method [ModelLayer.defaultModelRoughness] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("model-roughness")
    }

  /**
   * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source.
   *
   * Use static method [ModelLayer.defaultModelRoughness] to set the default property.
   *
   * @param modelRoughness value of modelRoughness
   */
  @MapboxExperimental
  override fun modelRoughness(modelRoughness: Double): ModelLayer = apply {
    val propertyValue = PropertyValue("model-roughness", modelRoughness)
    setProperty(propertyValue)
  }

  /**
   * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source.
   *
   * This is an Expression representation of "model-roughness".
   *
   */
  @MapboxExperimental
  val modelRoughnessAsExpression: Expression?
    /**
     * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source.
     *
     * Get the ModelRoughness property as an Expression
     *
     * Use static method [ModelLayer.defaultModelRoughnessAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("model-roughness")?.let {
        return it
      }
      modelRoughness?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source.
   *
   * Use static method [ModelLayer.defaultModelRoughnessAsExpression] to set the default property.
   *
   * @param modelRoughness value of modelRoughness as Expression
   */
  @MapboxExperimental
  override fun modelRoughness(modelRoughness: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-roughness", modelRoughness)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelRoughness.
   */
  @MapboxExperimental
  val modelRoughnessTransition: StyleTransition?
    /**
     * Get the ModelRoughness property transition options
     *
     * Use static method [ModelLayer.defaultModelRoughnessTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("model-roughness-transition")
    }

  /**
   * Set the ModelRoughness property transition options
   *
   * Use static method [ModelLayer.defaultModelRoughnessTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun modelRoughnessTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-roughness-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelRoughnessTransition].
   */
  @MapboxExperimental
  override fun modelRoughnessTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelRoughnessTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The scale of the model.
   */
  @MapboxExperimental
  val modelScale: List<Double>?
    /**
     * The scale of the model.
     *
     * Use static method [ModelLayer.defaultModelScale] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("model-scale")
    }

  /**
   * The scale of the model.
   *
   * Use static method [ModelLayer.defaultModelScale] to set the default property.
   *
   * @param modelScale value of modelScale
   */
  @MapboxExperimental
  override fun modelScale(modelScale: List<Double>): ModelLayer = apply {
    val propertyValue = PropertyValue("model-scale", modelScale)
    setProperty(propertyValue)
  }

  /**
   * The scale of the model.
   *
   * This is an Expression representation of "model-scale".
   *
   */
  @MapboxExperimental
  val modelScaleAsExpression: Expression?
    /**
     * The scale of the model.
     *
     * Get the ModelScale property as an Expression
     *
     * Use static method [ModelLayer.defaultModelScaleAsExpression] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("model-scale")?.let {
        return it
      }
      modelScale?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The scale of the model.
   *
   * Use static method [ModelLayer.defaultModelScaleAsExpression] to set the default property.
   *
   * @param modelScale value of modelScale as Expression
   */
  @MapboxExperimental
  override fun modelScale(modelScale: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-scale", modelScale)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelScale.
   */
  @MapboxExperimental
  val modelScaleTransition: StyleTransition?
    /**
     * Get the ModelScale property transition options
     *
     * Use static method [ModelLayer.defaultModelScaleTransition] to get the default property.
     *
     * @return transition options for List<Double>
     */
    get() {
      return getPropertyValue("model-scale-transition")
    }

  /**
   * Set the ModelScale property transition options
   *
   * Use static method [ModelLayer.defaultModelScaleTransition] to set the default property.
   *
   * @param options transition options for List<Double>
   */
  @MapboxExperimental
  override fun modelScaleTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-scale-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelScaleTransition].
   */
  @MapboxExperimental
  override fun modelScaleTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelScaleTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Defines scaling mode. Only applies to location-indicator type layers.
   */
  @MapboxExperimental
  val modelScaleMode: ModelScaleMode?
    /**
     * Defines scaling mode. Only applies to location-indicator type layers.
     *
     * Use static method [ModelLayer.defaultModelScaleMode] to get the default property.
     *
     * @return ModelScaleMode
     */
    get() {
      getPropertyValue<String?>("model-scale-mode")?.let {
        return ModelScaleMode.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Defines scaling mode. Only applies to location-indicator type layers.
   *
   * Use static method [ModelLayer.defaultModelScaleMode] to set the default property.
   *
   * @param modelScaleMode value of modelScaleMode
   */
  @MapboxExperimental
  override fun modelScaleMode(modelScaleMode: ModelScaleMode): ModelLayer = apply {
    val propertyValue = PropertyValue("model-scale-mode", modelScaleMode)
    setProperty(propertyValue)
  }

  /**
   * Defines scaling mode. Only applies to location-indicator type layers.
   *
   * This is an Expression representation of "model-scale-mode".
   *
   */
  @MapboxExperimental
  val modelScaleModeAsExpression: Expression?
    /**
     * Defines scaling mode. Only applies to location-indicator type layers.
     *
     * Get the ModelScaleMode property as an Expression
     *
     * Use static method [ModelLayer.defaultModelScaleModeAsExpression] to get the default property.
     *
     * @return ModelScaleMode
     */
    get() {
      getPropertyValue<Expression>("model-scale-mode")?.let {
        return it
      }
      modelScaleMode?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Defines scaling mode. Only applies to location-indicator type layers.
   *
   * Use static method [ModelLayer.defaultModelScaleModeAsExpression] to set the default property.
   *
   * @param modelScaleMode value of modelScaleMode as Expression
   */
  @MapboxExperimental
  override fun modelScaleMode(modelScaleMode: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-scale-mode", modelScaleMode)
    setProperty(propertyValue)
  }

  /**
   * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets.
   */
  @MapboxExperimental
  val modelTranslation: List<Double>?
    /**
     * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets.
     *
     * Use static method [ModelLayer.defaultModelTranslation] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("model-translation")
    }

  /**
   * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets.
   *
   * Use static method [ModelLayer.defaultModelTranslation] to set the default property.
   *
   * @param modelTranslation value of modelTranslation
   */
  @MapboxExperimental
  override fun modelTranslation(modelTranslation: List<Double>): ModelLayer = apply {
    val propertyValue = PropertyValue("model-translation", modelTranslation)
    setProperty(propertyValue)
  }

  /**
   * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets.
   *
   * This is an Expression representation of "model-translation".
   *
   */
  @MapboxExperimental
  val modelTranslationAsExpression: Expression?
    /**
     * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets.
     *
     * Get the ModelTranslation property as an Expression
     *
     * Use static method [ModelLayer.defaultModelTranslationAsExpression] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("model-translation")?.let {
        return it
      }
      modelTranslation?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets.
   *
   * Use static method [ModelLayer.defaultModelTranslationAsExpression] to set the default property.
   *
   * @param modelTranslation value of modelTranslation as Expression
   */
  @MapboxExperimental
  override fun modelTranslation(modelTranslation: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-translation", modelTranslation)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelTranslation.
   */
  @MapboxExperimental
  val modelTranslationTransition: StyleTransition?
    /**
     * Get the ModelTranslation property transition options
     *
     * Use static method [ModelLayer.defaultModelTranslationTransition] to get the default property.
     *
     * @return transition options for List<Double>
     */
    get() {
      return getPropertyValue("model-translation-transition")
    }

  /**
   * Set the ModelTranslation property transition options
   *
   * Use static method [ModelLayer.defaultModelTranslationTransition] to set the default property.
   *
   * @param options transition options for List<Double>
   */
  @MapboxExperimental
  override fun modelTranslationTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-translation-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelTranslationTransition].
   */
  @MapboxExperimental
  override fun modelTranslationTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelTranslationTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Defines rendering behavior of model in respect to other 3D scene objects.
   */
  @MapboxExperimental
  val modelType: ModelType?
    /**
     * Defines rendering behavior of model in respect to other 3D scene objects.
     *
     * Use static method [ModelLayer.defaultModelType] to get the default property.
     *
     * @return ModelType
     */
    get() {
      getPropertyValue<String?>("model-type")?.let {
        return ModelType.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Defines rendering behavior of model in respect to other 3D scene objects.
   *
   * Use static method [ModelLayer.defaultModelType] to set the default property.
   *
   * @param modelType value of modelType
   */
  @MapboxExperimental
  override fun modelType(modelType: ModelType): ModelLayer = apply {
    val propertyValue = PropertyValue("model-type", modelType)
    setProperty(propertyValue)
  }

  /**
   * Defines rendering behavior of model in respect to other 3D scene objects.
   *
   * This is an Expression representation of "model-type".
   *
   */
  @MapboxExperimental
  val modelTypeAsExpression: Expression?
    /**
     * Defines rendering behavior of model in respect to other 3D scene objects.
     *
     * Get the ModelType property as an Expression
     *
     * Use static method [ModelLayer.defaultModelTypeAsExpression] to get the default property.
     *
     * @return ModelType
     */
    get() {
      getPropertyValue<Expression>("model-type")?.let {
        return it
      }
      modelType?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Defines rendering behavior of model in respect to other 3D scene objects.
   *
   * Use static method [ModelLayer.defaultModelTypeAsExpression] to set the default property.
   *
   * @param modelType value of modelType as Expression
   */
  @MapboxExperimental
  override fun modelType(modelType: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-type", modelType)
    setProperty(propertyValue)
  }

  /**
   * Get the type of this layer
   *
   * @return Type of the layer as [String]
   */
  override fun getType(): String {
    return "model"
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
        StyleManager.getStyleLayerPropertyDefaultValue("model", "visibility").silentUnwrap<String>()?.let {
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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "minzoom").silentUnwrap()

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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "maxzoom").silentUnwrap()

    /**
     * Model to render.
     */
    @MapboxExperimental
    val defaultModelId: String?
      /**
       * Model to render.
       *
       * Get the default value of ModelId property
       *
       * @return String
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-id").silentUnwrap()
      }

    /**
     * Model to render.
     *
     * This is an Expression representation of "model-id".
     *
     */
    @MapboxExperimental
    val defaultModelIdAsExpression: Expression?
      /**
       * Get default value of the ModelId property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-id").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultModelId?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Intensity of the ambient occlusion if present in the 3D model.
     */
    @MapboxExperimental
    val defaultModelAmbientOcclusionIntensity: Double?
      /**
       * Intensity of the ambient occlusion if present in the 3D model.
       *
       * Get the default value of ModelAmbientOcclusionIntensity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-ambient-occlusion-intensity").silentUnwrap()
      }

    /**
     * Intensity of the ambient occlusion if present in the 3D model.
     *
     * This is an Expression representation of "model-ambient-occlusion-intensity".
     *
     */
    @MapboxExperimental
    val defaultModelAmbientOcclusionIntensityAsExpression: Expression?
      /**
       * Get default value of the ModelAmbientOcclusionIntensity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-ambient-occlusion-intensity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultModelAmbientOcclusionIntensity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for ModelAmbientOcclusionIntensity.
     */
    @MapboxExperimental
    val defaultModelAmbientOcclusionIntensityTransition: StyleTransition?
      /**
       * Get the ModelAmbientOcclusionIntensity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-ambient-occlusion-intensity-transition").silentUnwrap()

    /**
     * Enable/Disable shadow casting for this layer
     */
    @MapboxExperimental
    val defaultModelCastShadows: Boolean?
      /**
       * Enable/Disable shadow casting for this layer
       *
       * Get the default value of ModelCastShadows property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-cast-shadows").silentUnwrap()
      }

    /**
     * Enable/Disable shadow casting for this layer
     *
     * This is an Expression representation of "model-cast-shadows".
     *
     */
    @MapboxExperimental
    val defaultModelCastShadowsAsExpression: Expression?
      /**
       * Get default value of the ModelCastShadows property as an Expression
       *
       * @return Boolean
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-cast-shadows").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultModelCastShadows?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
     */
    @MapboxExperimental
    val defaultModelColor: String?
      /**
       * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
       *
       * Get the default value of ModelColor property
       *
       * @return String
       */
      get() {
        defaultModelColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
     *
     * This is an Expression representation of "model-color".
     *
     */
    @MapboxExperimental
    val defaultModelColorAsExpression: Expression?
      /**
       * Get default value of the ModelColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
     */
    @MapboxExperimental
    val defaultModelColorAsColorInt: Int?
      /**
       * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
       *
       * Get the default value of ModelColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultModelColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for ModelColor.
     */
    @MapboxExperimental
    val defaultModelColorTransition: StyleTransition?
      /**
       * Get the ModelColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-color-transition").silentUnwrap()

    /**
     * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix.
     */
    @MapboxExperimental
    val defaultModelColorMixIntensity: Double?
      /**
       * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix.
       *
       * Get the default value of ModelColorMixIntensity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-color-mix-intensity").silentUnwrap()
      }

    /**
     * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix.
     *
     * This is an Expression representation of "model-color-mix-intensity".
     *
     */
    @MapboxExperimental
    val defaultModelColorMixIntensityAsExpression: Expression?
      /**
       * Get default value of the ModelColorMixIntensity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-color-mix-intensity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultModelColorMixIntensity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for ModelColorMixIntensity.
     */
    @MapboxExperimental
    val defaultModelColorMixIntensityTransition: StyleTransition?
      /**
       * Get the ModelColorMixIntensity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-color-mix-intensity-transition").silentUnwrap()

    /**
     * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source.
     */
    @MapboxExperimental
    val defaultModelEmissiveStrength: Double?
      /**
       * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source.
       *
       * Get the default value of ModelEmissiveStrength property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-emissive-strength").silentUnwrap()
      }

    /**
     * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source.
     *
     * This is an Expression representation of "model-emissive-strength".
     *
     */
    @MapboxExperimental
    val defaultModelEmissiveStrengthAsExpression: Expression?
      /**
       * Get default value of the ModelEmissiveStrength property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-emissive-strength").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultModelEmissiveStrength?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for ModelEmissiveStrength.
     */
    @MapboxExperimental
    val defaultModelEmissiveStrengthTransition: StyleTransition?
      /**
       * Get the ModelEmissiveStrength property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-emissive-strength-transition").silentUnwrap()

    /**
     * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)).
     */
    @MapboxExperimental
    val defaultModelHeightBasedEmissiveStrengthMultiplier: List<Double>?
      /**
       * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)).
       *
       * Get the default value of ModelHeightBasedEmissiveStrengthMultiplier property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-height-based-emissive-strength-multiplier").silentUnwrap()
      }

    /**
     * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)).
     *
     * This is an Expression representation of "model-height-based-emissive-strength-multiplier".
     *
     */
    @MapboxExperimental
    val defaultModelHeightBasedEmissiveStrengthMultiplierAsExpression: Expression?
      /**
       * Get default value of the ModelHeightBasedEmissiveStrengthMultiplier property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-height-based-emissive-strength-multiplier").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultModelHeightBasedEmissiveStrengthMultiplier?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for ModelHeightBasedEmissiveStrengthMultiplier.
     */
    @MapboxExperimental
    val defaultModelHeightBasedEmissiveStrengthMultiplierTransition: StyleTransition?
      /**
       * Get the ModelHeightBasedEmissiveStrengthMultiplier property transition options
       *
       * @return transition options for List<Double>
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-height-based-emissive-strength-multiplier-transition").silentUnwrap()

    /**
     * The opacity of the model layer.
     */
    @MapboxExperimental
    val defaultModelOpacity: Double?
      /**
       * The opacity of the model layer.
       *
       * Get the default value of ModelOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-opacity").silentUnwrap()
      }

    /**
     * The opacity of the model layer.
     *
     * This is an Expression representation of "model-opacity".
     *
     */
    @MapboxExperimental
    val defaultModelOpacityAsExpression: Expression?
      /**
       * Get default value of the ModelOpacity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-opacity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultModelOpacity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for ModelOpacity.
     */
    @MapboxExperimental
    val defaultModelOpacityTransition: StyleTransition?
      /**
       * Get the ModelOpacity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-opacity-transition").silentUnwrap()

    /**
     * Enable/Disable shadow receiving for this layer
     */
    @MapboxExperimental
    val defaultModelReceiveShadows: Boolean?
      /**
       * Enable/Disable shadow receiving for this layer
       *
       * Get the default value of ModelReceiveShadows property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-receive-shadows").silentUnwrap()
      }

    /**
     * Enable/Disable shadow receiving for this layer
     *
     * This is an Expression representation of "model-receive-shadows".
     *
     */
    @MapboxExperimental
    val defaultModelReceiveShadowsAsExpression: Expression?
      /**
       * Get default value of the ModelReceiveShadows property as an Expression
       *
       * @return Boolean
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-receive-shadows").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultModelReceiveShadows?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * The rotation of the model in euler angles [lon, lat, z].
     */
    @MapboxExperimental
    val defaultModelRotation: List<Double>?
      /**
       * The rotation of the model in euler angles [lon, lat, z].
       *
       * Get the default value of ModelRotation property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-rotation").silentUnwrap()
      }

    /**
     * The rotation of the model in euler angles [lon, lat, z].
     *
     * This is an Expression representation of "model-rotation".
     *
     */
    @MapboxExperimental
    val defaultModelRotationAsExpression: Expression?
      /**
       * Get default value of the ModelRotation property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-rotation").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultModelRotation?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for ModelRotation.
     */
    @MapboxExperimental
    val defaultModelRotationTransition: StyleTransition?
      /**
       * Get the ModelRotation property transition options
       *
       * @return transition options for List<Double>
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-rotation-transition").silentUnwrap()

    /**
     * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source.
     */
    @MapboxExperimental
    val defaultModelRoughness: Double?
      /**
       * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source.
       *
       * Get the default value of ModelRoughness property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-roughness").silentUnwrap()
      }

    /**
     * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source.
     *
     * This is an Expression representation of "model-roughness".
     *
     */
    @MapboxExperimental
    val defaultModelRoughnessAsExpression: Expression?
      /**
       * Get default value of the ModelRoughness property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-roughness").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultModelRoughness?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for ModelRoughness.
     */
    @MapboxExperimental
    val defaultModelRoughnessTransition: StyleTransition?
      /**
       * Get the ModelRoughness property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-roughness-transition").silentUnwrap()

    /**
     * The scale of the model.
     */
    @MapboxExperimental
    val defaultModelScale: List<Double>?
      /**
       * The scale of the model.
       *
       * Get the default value of ModelScale property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-scale").silentUnwrap()
      }

    /**
     * The scale of the model.
     *
     * This is an Expression representation of "model-scale".
     *
     */
    @MapboxExperimental
    val defaultModelScaleAsExpression: Expression?
      /**
       * Get default value of the ModelScale property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-scale").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultModelScale?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for ModelScale.
     */
    @MapboxExperimental
    val defaultModelScaleTransition: StyleTransition?
      /**
       * Get the ModelScale property transition options
       *
       * @return transition options for List<Double>
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-scale-transition").silentUnwrap()

    /**
     * Defines scaling mode. Only applies to location-indicator type layers.
     */
    @MapboxExperimental
    val defaultModelScaleMode: ModelScaleMode?
      /**
       * Defines scaling mode. Only applies to location-indicator type layers.
       *
       * Get the default value of ModelScaleMode property
       *
       * @return ModelScaleMode
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-scale-mode").silentUnwrap<String>()?.let {
          return ModelScaleMode.valueOf(it.uppercase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Defines scaling mode. Only applies to location-indicator type layers.
     *
     * This is an Expression representation of "model-scale-mode".
     *
     */
    @MapboxExperimental
    val defaultModelScaleModeAsExpression: Expression?
      /**
       * Get default value of the ModelScaleMode property as an Expression
       *
       * @return ModelScaleMode
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-scale-mode").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultModelScaleMode?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets.
     */
    @MapboxExperimental
    val defaultModelTranslation: List<Double>?
      /**
       * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets.
       *
       * Get the default value of ModelTranslation property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-translation").silentUnwrap()
      }

    /**
     * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets.
     *
     * This is an Expression representation of "model-translation".
     *
     */
    @MapboxExperimental
    val defaultModelTranslationAsExpression: Expression?
      /**
       * Get default value of the ModelTranslation property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-translation").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultModelTranslation?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for ModelTranslation.
     */
    @MapboxExperimental
    val defaultModelTranslationTransition: StyleTransition?
      /**
       * Get the ModelTranslation property transition options
       *
       * @return transition options for List<Double>
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-translation-transition").silentUnwrap()

    /**
     * Defines rendering behavior of model in respect to other 3D scene objects.
     */
    @MapboxExperimental
    val defaultModelType: ModelType?
      /**
       * Defines rendering behavior of model in respect to other 3D scene objects.
       *
       * Get the default value of ModelType property
       *
       * @return ModelType
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-type").silentUnwrap<String>()?.let {
          return ModelType.valueOf(it.uppercase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Defines rendering behavior of model in respect to other 3D scene objects.
     *
     * This is an Expression representation of "model-type".
     *
     */
    @MapboxExperimental
    val defaultModelTypeAsExpression: Expression?
      /**
       * Get default value of the ModelType property as an Expression
       *
       * @return ModelType
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-type").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultModelType?.let {
          return Expression.literal(it.value)
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
@MapboxExperimental
@LayersDsl
interface ModelLayerDsl {
  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  fun sourceLayer(sourceLayer: String): ModelLayer

  /**
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   *
   * @param slot value of slot
   */
  @MapboxExperimental
  fun slot(slot: String): ModelLayer

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
  fun filter(filter: Expression): ModelLayer

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): ModelLayer

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility as Expression
   */
  fun visibility(visibility: Expression): ModelLayer

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param minZoom value of minzoom
   */
  fun minZoom(minZoom: Double): ModelLayer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param maxZoom value of maxzoom
   */
  fun maxZoom(maxZoom: Double): ModelLayer

  // Property getters and setters

  /**
   * Model to render.
   *
   * @param modelId value of modelId
   */
  @MapboxExperimental
  fun modelId(modelId: String): ModelLayer

  /**
   * Model to render.
   *
   * @param modelId value of modelId as Expression
   */
  @MapboxExperimental
  fun modelId(modelId: Expression): ModelLayer

  /**
   * Intensity of the ambient occlusion if present in the 3D model.
   *
   * @param modelAmbientOcclusionIntensity value of modelAmbientOcclusionIntensity
   */
  @MapboxExperimental
  fun modelAmbientOcclusionIntensity(modelAmbientOcclusionIntensity: Double = 1.0): ModelLayer

  /**
   * Intensity of the ambient occlusion if present in the 3D model.
   *
   * @param modelAmbientOcclusionIntensity value of modelAmbientOcclusionIntensity as Expression
   */
  @MapboxExperimental
  fun modelAmbientOcclusionIntensity(modelAmbientOcclusionIntensity: Expression): ModelLayer

  /**
   * Intensity of the ambient occlusion if present in the 3D model.
   *
   * Set the ModelAmbientOcclusionIntensity property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun modelAmbientOcclusionIntensityTransition(options: StyleTransition): ModelLayer

  /**
   * Intensity of the ambient occlusion if present in the 3D model.
   *
   * DSL for [modelAmbientOcclusionIntensityTransition].
   */
  @MapboxExperimental
  fun modelAmbientOcclusionIntensityTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * Enable/Disable shadow casting for this layer
   *
   * @param modelCastShadows value of modelCastShadows
   */
  @MapboxExperimental
  fun modelCastShadows(modelCastShadows: Boolean = true): ModelLayer

  /**
   * Enable/Disable shadow casting for this layer
   *
   * @param modelCastShadows value of modelCastShadows as Expression
   */
  @MapboxExperimental
  fun modelCastShadows(modelCastShadows: Expression): ModelLayer

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
   *
   * @param modelColor value of modelColor
   */
  @MapboxExperimental
  fun modelColor(modelColor: String = "#ffffff"): ModelLayer

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
   *
   * @param modelColor value of modelColor as Expression
   */
  @MapboxExperimental
  fun modelColor(modelColor: Expression): ModelLayer

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
   *
   * @param modelColor value of modelColor
   */
  @MapboxExperimental
  fun modelColor(@ColorInt modelColor: Int): ModelLayer

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
   *
   * Set the ModelColor property transition options
   *
   * @param options transition options for String
   */
  @MapboxExperimental
  fun modelColorTransition(options: StyleTransition): ModelLayer

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
   *
   * DSL for [modelColorTransition].
   */
  @MapboxExperimental
  fun modelColorTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix.
   *
   * @param modelColorMixIntensity value of modelColorMixIntensity
   */
  @MapboxExperimental
  fun modelColorMixIntensity(modelColorMixIntensity: Double = 0.0): ModelLayer

  /**
   * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix.
   *
   * @param modelColorMixIntensity value of modelColorMixIntensity as Expression
   */
  @MapboxExperimental
  fun modelColorMixIntensity(modelColorMixIntensity: Expression): ModelLayer

  /**
   * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix.
   *
   * Set the ModelColorMixIntensity property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun modelColorMixIntensityTransition(options: StyleTransition): ModelLayer

  /**
   * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix.
   *
   * DSL for [modelColorMixIntensityTransition].
   */
  @MapboxExperimental
  fun modelColorMixIntensityTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source.
   *
   * @param modelEmissiveStrength value of modelEmissiveStrength
   */
  @MapboxExperimental
  fun modelEmissiveStrength(modelEmissiveStrength: Double = 0.0): ModelLayer

  /**
   * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source.
   *
   * @param modelEmissiveStrength value of modelEmissiveStrength as Expression
   */
  @MapboxExperimental
  fun modelEmissiveStrength(modelEmissiveStrength: Expression): ModelLayer

  /**
   * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source.
   *
   * Set the ModelEmissiveStrength property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun modelEmissiveStrengthTransition(options: StyleTransition): ModelLayer

  /**
   * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source.
   *
   * DSL for [modelEmissiveStrengthTransition].
   */
  @MapboxExperimental
  fun modelEmissiveStrengthTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)).
   *
   * @param modelHeightBasedEmissiveStrengthMultiplier value of modelHeightBasedEmissiveStrengthMultiplier
   */
  @MapboxExperimental
  fun modelHeightBasedEmissiveStrengthMultiplier(modelHeightBasedEmissiveStrengthMultiplier: List<Double> = listOf(1.0, 1.0, 1.0, 1.0, 0.0)): ModelLayer

  /**
   * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)).
   *
   * @param modelHeightBasedEmissiveStrengthMultiplier value of modelHeightBasedEmissiveStrengthMultiplier as Expression
   */
  @MapboxExperimental
  fun modelHeightBasedEmissiveStrengthMultiplier(modelHeightBasedEmissiveStrengthMultiplier: Expression): ModelLayer

  /**
   * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)).
   *
   * Set the ModelHeightBasedEmissiveStrengthMultiplier property transition options
   *
   * @param options transition options for List<Double>
   */
  @MapboxExperimental
  fun modelHeightBasedEmissiveStrengthMultiplierTransition(options: StyleTransition): ModelLayer

  /**
   * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)).
   *
   * DSL for [modelHeightBasedEmissiveStrengthMultiplierTransition].
   */
  @MapboxExperimental
  fun modelHeightBasedEmissiveStrengthMultiplierTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * The opacity of the model layer.
   *
   * @param modelOpacity value of modelOpacity
   */
  @MapboxExperimental
  fun modelOpacity(modelOpacity: Double = 1.0): ModelLayer

  /**
   * The opacity of the model layer.
   *
   * @param modelOpacity value of modelOpacity as Expression
   */
  @MapboxExperimental
  fun modelOpacity(modelOpacity: Expression): ModelLayer

  /**
   * The opacity of the model layer.
   *
   * Set the ModelOpacity property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun modelOpacityTransition(options: StyleTransition): ModelLayer

  /**
   * The opacity of the model layer.
   *
   * DSL for [modelOpacityTransition].
   */
  @MapboxExperimental
  fun modelOpacityTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * Enable/Disable shadow receiving for this layer
   *
   * @param modelReceiveShadows value of modelReceiveShadows
   */
  @MapboxExperimental
  fun modelReceiveShadows(modelReceiveShadows: Boolean = true): ModelLayer

  /**
   * Enable/Disable shadow receiving for this layer
   *
   * @param modelReceiveShadows value of modelReceiveShadows as Expression
   */
  @MapboxExperimental
  fun modelReceiveShadows(modelReceiveShadows: Expression): ModelLayer

  /**
   * The rotation of the model in euler angles [lon, lat, z].
   *
   * @param modelRotation value of modelRotation
   */
  @MapboxExperimental
  fun modelRotation(modelRotation: List<Double> = listOf(0.0, 0.0, 0.0)): ModelLayer

  /**
   * The rotation of the model in euler angles [lon, lat, z].
   *
   * @param modelRotation value of modelRotation as Expression
   */
  @MapboxExperimental
  fun modelRotation(modelRotation: Expression): ModelLayer

  /**
   * The rotation of the model in euler angles [lon, lat, z].
   *
   * Set the ModelRotation property transition options
   *
   * @param options transition options for List<Double>
   */
  @MapboxExperimental
  fun modelRotationTransition(options: StyleTransition): ModelLayer

  /**
   * The rotation of the model in euler angles [lon, lat, z].
   *
   * DSL for [modelRotationTransition].
   */
  @MapboxExperimental
  fun modelRotationTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source.
   *
   * @param modelRoughness value of modelRoughness
   */
  @MapboxExperimental
  fun modelRoughness(modelRoughness: Double = 1.0): ModelLayer

  /**
   * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source.
   *
   * @param modelRoughness value of modelRoughness as Expression
   */
  @MapboxExperimental
  fun modelRoughness(modelRoughness: Expression): ModelLayer

  /**
   * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source.
   *
   * Set the ModelRoughness property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun modelRoughnessTransition(options: StyleTransition): ModelLayer

  /**
   * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source.
   *
   * DSL for [modelRoughnessTransition].
   */
  @MapboxExperimental
  fun modelRoughnessTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * The scale of the model.
   *
   * @param modelScale value of modelScale
   */
  @MapboxExperimental
  fun modelScale(modelScale: List<Double> = listOf(1.0, 1.0, 1.0)): ModelLayer

  /**
   * The scale of the model.
   *
   * @param modelScale value of modelScale as Expression
   */
  @MapboxExperimental
  fun modelScale(modelScale: Expression): ModelLayer

  /**
   * The scale of the model.
   *
   * Set the ModelScale property transition options
   *
   * @param options transition options for List<Double>
   */
  @MapboxExperimental
  fun modelScaleTransition(options: StyleTransition): ModelLayer

  /**
   * The scale of the model.
   *
   * DSL for [modelScaleTransition].
   */
  @MapboxExperimental
  fun modelScaleTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * Defines scaling mode. Only applies to location-indicator type layers.
   *
   * @param modelScaleMode value of modelScaleMode
   */
  @MapboxExperimental
  fun modelScaleMode(modelScaleMode: ModelScaleMode = ModelScaleMode.MAP): ModelLayer

  /**
   * Defines scaling mode. Only applies to location-indicator type layers.
   *
   * @param modelScaleMode value of modelScaleMode as Expression
   */
  @MapboxExperimental
  fun modelScaleMode(modelScaleMode: Expression): ModelLayer

  /**
   * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets.
   *
   * @param modelTranslation value of modelTranslation
   */
  @MapboxExperimental
  fun modelTranslation(modelTranslation: List<Double> = listOf(0.0, 0.0, 0.0)): ModelLayer

  /**
   * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets.
   *
   * @param modelTranslation value of modelTranslation as Expression
   */
  @MapboxExperimental
  fun modelTranslation(modelTranslation: Expression): ModelLayer

  /**
   * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets.
   *
   * Set the ModelTranslation property transition options
   *
   * @param options transition options for List<Double>
   */
  @MapboxExperimental
  fun modelTranslationTransition(options: StyleTransition): ModelLayer

  /**
   * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets.
   *
   * DSL for [modelTranslationTransition].
   */
  @MapboxExperimental
  fun modelTranslationTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * Defines rendering behavior of model in respect to other 3D scene objects.
   *
   * @param modelType value of modelType
   */
  @MapboxExperimental
  fun modelType(modelType: ModelType = ModelType.COMMON_3D): ModelLayer

  /**
   * Defines rendering behavior of model in respect to other 3D scene objects.
   *
   * @param modelType value of modelType as Expression
   */
  @MapboxExperimental
  fun modelType(modelType: Expression): ModelLayer
}

/**
 * DSL function for creating a [ModelLayer].
 */
@MapboxExperimental
fun modelLayer(layerId: String, sourceId: String, block: ModelLayerDsl.() -> Unit): ModelLayer = ModelLayer(layerId, sourceId).apply(block)

// End of generated file.