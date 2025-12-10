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
 * A 3D model
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers/#model)
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
 */
@UiThread
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
   * @param slot value of slot. Setting it to empty string removes the slot.
   */
  override fun slot(slot: String): ModelLayer = apply {
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
    get() = getPropertyValue("slot")

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
    get() = getPropertyValue("filter")

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
    get() = getPropertyValue("visibility")

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
    get() = getPropertyValue("minzoom")

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
    get() = getPropertyValue("maxzoom")

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
   * If true, the models will be reduced in density based on the zoom level. This is useful for large datasets that may be slow to render. Default value: true.
   */
  @MapboxExperimental
  val modelAllowDensityReduction: Boolean?
    /**
     * If true, the models will be reduced in density based on the zoom level. This is useful for large datasets that may be slow to render. Default value: true.
     *
     * Use static method [ModelLayer.defaultModelAllowDensityReduction] to get the default property.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("model-allow-density-reduction")
    }

  /**
   * If true, the models will be reduced in density based on the zoom level. This is useful for large datasets that may be slow to render. Default value: true.
   *
   * Use static method [ModelLayer.defaultModelAllowDensityReduction] to set the default property.
   *
   * @param modelAllowDensityReduction value of modelAllowDensityReduction
   */
  @MapboxExperimental
  override fun modelAllowDensityReduction(modelAllowDensityReduction: Boolean): ModelLayer = apply {
    val propertyValue = PropertyValue("model-allow-density-reduction", modelAllowDensityReduction)
    setProperty(propertyValue)
  }

  /**
   * If true, the models will be reduced in density based on the zoom level. This is useful for large datasets that may be slow to render. Default value: true.
   *
   * This is an Expression representation of "model-allow-density-reduction".
   *
   */
  @MapboxExperimental
  val modelAllowDensityReductionAsExpression: Expression?
    /**
     * If true, the models will be reduced in density based on the zoom level. This is useful for large datasets that may be slow to render. Default value: true.
     *
     * Get the ModelAllowDensityReduction property as an Expression
     *
     * Use static method [ModelLayer.defaultModelAllowDensityReductionAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("model-allow-density-reduction")

  /**
   * If true, the models will be reduced in density based on the zoom level. This is useful for large datasets that may be slow to render. Default value: true.
   *
   * Use static method [ModelLayer.defaultModelAllowDensityReductionAsExpression] to set the default property.
   *
   * @param modelAllowDensityReduction value of modelAllowDensityReduction as Expression
   */
  @MapboxExperimental
  override fun modelAllowDensityReduction(modelAllowDensityReduction: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-allow-density-reduction", modelAllowDensityReduction)
    setProperty(propertyValue)
  }

  /**
   * Model to render. It can be either a string referencing an element to the models root property or an internal or external URL Default value: "".
   */
  val modelId: String?
    /**
     * Model to render. It can be either a string referencing an element to the models root property or an internal or external URL Default value: "".
     *
     * Use static method [ModelLayer.defaultModelId] to get the default property.
     *
     * @return String
     */
    get() {
      return getPropertyValue("model-id")
    }

  /**
   * Model to render. It can be either a string referencing an element to the models root property or an internal or external URL Default value: "".
   *
   * Use static method [ModelLayer.defaultModelId] to set the default property.
   *
   * @param modelId value of modelId
   */
  override fun modelId(modelId: String): ModelLayer = apply {
    val propertyValue = PropertyValue("model-id", modelId)
    setProperty(propertyValue)
  }

  /**
   * Model to render. It can be either a string referencing an element to the models root property or an internal or external URL Default value: "".
   *
   * This is an Expression representation of "model-id".
   *
   */
  val modelIdAsExpression: Expression?
    /**
     * Model to render. It can be either a string referencing an element to the models root property or an internal or external URL Default value: "".
     *
     * Get the ModelId property as an Expression
     *
     * Use static method [ModelLayer.defaultModelIdAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("model-id")

  /**
   * Model to render. It can be either a string referencing an element to the models root property or an internal or external URL Default value: "".
   *
   * Use static method [ModelLayer.defaultModelIdAsExpression] to set the default property.
   *
   * @param modelId value of modelId as Expression
   */
  override fun modelId(modelId: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-id", modelId)
    setProperty(propertyValue)
  }

  /**
   * Intensity of the ambient occlusion if present in the 3D model. Default value: 1. Value range: [0, 1]
   */
  val modelAmbientOcclusionIntensity: Double?
    /**
     * Intensity of the ambient occlusion if present in the 3D model. Default value: 1. Value range: [0, 1]
     *
     * Use static method [ModelLayer.defaultModelAmbientOcclusionIntensity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("model-ambient-occlusion-intensity")
    }

  /**
   * Intensity of the ambient occlusion if present in the 3D model. Default value: 1. Value range: [0, 1]
   *
   * Use static method [ModelLayer.defaultModelAmbientOcclusionIntensity] to set the default property.
   *
   * @param modelAmbientOcclusionIntensity value of modelAmbientOcclusionIntensity
   */
  override fun modelAmbientOcclusionIntensity(modelAmbientOcclusionIntensity: Double): ModelLayer = apply {
    val propertyValue = PropertyValue("model-ambient-occlusion-intensity", modelAmbientOcclusionIntensity)
    setProperty(propertyValue)
  }

  /**
   * Intensity of the ambient occlusion if present in the 3D model. Default value: 1. Value range: [0, 1]
   *
   * This is an Expression representation of "model-ambient-occlusion-intensity".
   *
   */
  val modelAmbientOcclusionIntensityAsExpression: Expression?
    /**
     * Intensity of the ambient occlusion if present in the 3D model. Default value: 1. Value range: [0, 1]
     *
     * Get the ModelAmbientOcclusionIntensity property as an Expression
     *
     * Use static method [ModelLayer.defaultModelAmbientOcclusionIntensityAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("model-ambient-occlusion-intensity")

  /**
   * Intensity of the ambient occlusion if present in the 3D model. Default value: 1. Value range: [0, 1]
   *
   * Use static method [ModelLayer.defaultModelAmbientOcclusionIntensityAsExpression] to set the default property.
   *
   * @param modelAmbientOcclusionIntensity value of modelAmbientOcclusionIntensity as Expression
   */
  override fun modelAmbientOcclusionIntensity(modelAmbientOcclusionIntensity: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-ambient-occlusion-intensity", modelAmbientOcclusionIntensity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelAmbientOcclusionIntensity.
   */
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
  override fun modelAmbientOcclusionIntensityTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-ambient-occlusion-intensity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelAmbientOcclusionIntensityTransition].
   */
  override fun modelAmbientOcclusionIntensityTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelAmbientOcclusionIntensityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Enable/Disable shadow casting for this layer Default value: true.
   */
  val modelCastShadows: Boolean?
    /**
     * Enable/Disable shadow casting for this layer Default value: true.
     *
     * Use static method [ModelLayer.defaultModelCastShadows] to get the default property.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("model-cast-shadows")
    }

  /**
   * Enable/Disable shadow casting for this layer Default value: true.
   *
   * Use static method [ModelLayer.defaultModelCastShadows] to set the default property.
   *
   * @param modelCastShadows value of modelCastShadows
   */
  override fun modelCastShadows(modelCastShadows: Boolean): ModelLayer = apply {
    val propertyValue = PropertyValue("model-cast-shadows", modelCastShadows)
    setProperty(propertyValue)
  }

  /**
   * Enable/Disable shadow casting for this layer Default value: true.
   *
   * This is an Expression representation of "model-cast-shadows".
   *
   */
  val modelCastShadowsAsExpression: Expression?
    /**
     * Enable/Disable shadow casting for this layer Default value: true.
     *
     * Get the ModelCastShadows property as an Expression
     *
     * Use static method [ModelLayer.defaultModelCastShadowsAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("model-cast-shadows")

  /**
   * Enable/Disable shadow casting for this layer Default value: true.
   *
   * Use static method [ModelLayer.defaultModelCastShadowsAsExpression] to set the default property.
   *
   * @param modelCastShadows value of modelCastShadows as Expression
   */
  override fun modelCastShadows(modelCastShadows: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-cast-shadows", modelCastShadows)
    setProperty(propertyValue)
  }

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
   */
  val modelColor: String?
    /**
     * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
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
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
   *
   * Use static method [ModelLayer.defaultModelColor] to set the default property.
   *
   * @param modelColor value of modelColor
   */
  override fun modelColor(modelColor: String): ModelLayer = apply {
    val propertyValue = PropertyValue("model-color", modelColor)
    setProperty(propertyValue)
  }

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
   *
   * This is an Expression representation of "model-color".
   *
   */
  val modelColorAsExpression: Expression?
    /**
     * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
     *
     * Get the ModelColor property as an Expression
     *
     * Use static method [ModelLayer.defaultModelColorAsExpression] to get the default property.
     */
    get() =
      getPropertyValue("model-color")

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
   *
   * Use static method [ModelLayer.defaultModelColorAsExpression] to set the default property.
   *
   * @param modelColor value of modelColor as Expression
   */
  override fun modelColor(modelColor: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-color", modelColor)
    setProperty(propertyValue)
  }

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
   */
  val modelColorAsColorInt: Int?
    /**
     * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
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
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
   *
   * Use static method [ModelLayer.defaultModelColorAsColorInt] to set the default property.
   *
   * @param modelColor value of modelColor
   */
  override fun modelColor(@ColorInt modelColor: Int): ModelLayer = apply {
    val propertyValue = PropertyValue("model-color", colorIntToRgbaExpression(modelColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelColor.
   */
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
  override fun modelColorTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelColorTransition].
   */
  override fun modelColorTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Сolor theme override for [modelColor].
   */
  @MapboxExperimental
  val modelColorUseTheme: String?
    /**
     * Get the ModelColorUseTheme property
     *
     * Use static method [ModelLayer.defaultModelColorUseTheme] to get the default property.
     *
     * @return current ModelColorUseTheme property as String
     */
    get() {
      return getPropertyValue("model-color-use-theme")
    }

  /**
   * Set the ModelColorUseTheme as String
   *
   * Use static method [ModelLayer.defaultModelColorUseTheme] to get the default property.
   *
   * @param modelColorUseTheme theme value for color. Overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  override fun modelColorUseTheme(modelColorUseTheme: String): ModelLayer = apply {
    val propertyValue = PropertyValue("model-color-use-theme", modelColorUseTheme)
    setProperty(propertyValue)
  }

  /**
   * Сolor theme override for [modelColor].
   */
  @MapboxExperimental
  val modelColorUseThemeAsExpression: Expression?
    /**
     * Get the ModelColorUseTheme property
     *
     * Use static method [ModelLayer.defaultModelColorUseTheme] to get the default property.
     *
     * @return current ModelColorUseTheme property as String
     */
    get() = getPropertyValueAsExpressionOrLiteralExpression("model-color-use-theme")

  /**
   * Set the ModelColorUseTheme as Expression
   *
   * Use static method [ModelLayer.defaultModelColorUseTheme] to get the default property.
   *
   * @param modelColorUseTheme theme value for color. Overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  override fun modelColorUseTheme(modelColorUseTheme: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-color-use-theme", modelColorUseTheme)
    setProperty(propertyValue)
  }

  /**
   * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 1]
   */
  val modelColorMixIntensity: Double?
    /**
     * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 1]
     *
     * Use static method [ModelLayer.defaultModelColorMixIntensity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("model-color-mix-intensity")
    }

  /**
   * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 1]
   *
   * Use static method [ModelLayer.defaultModelColorMixIntensity] to set the default property.
   *
   * @param modelColorMixIntensity value of modelColorMixIntensity
   */
  override fun modelColorMixIntensity(modelColorMixIntensity: Double): ModelLayer = apply {
    val propertyValue = PropertyValue("model-color-mix-intensity", modelColorMixIntensity)
    setProperty(propertyValue)
  }

  /**
   * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 1]
   *
   * This is an Expression representation of "model-color-mix-intensity".
   *
   */
  val modelColorMixIntensityAsExpression: Expression?
    /**
     * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 1]
     *
     * Get the ModelColorMixIntensity property as an Expression
     *
     * Use static method [ModelLayer.defaultModelColorMixIntensityAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("model-color-mix-intensity")

  /**
   * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 1]
   *
   * Use static method [ModelLayer.defaultModelColorMixIntensityAsExpression] to set the default property.
   *
   * @param modelColorMixIntensity value of modelColorMixIntensity as Expression
   */
  override fun modelColorMixIntensity(modelColorMixIntensity: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-color-mix-intensity", modelColorMixIntensity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelColorMixIntensity.
   */
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
  override fun modelColorMixIntensityTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-color-mix-intensity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelColorMixIntensityTransition].
   */
  override fun modelColorMixIntensityTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelColorMixIntensityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
   */
  val modelCutoffFadeRange: Double?
    /**
     * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
     *
     * Use static method [ModelLayer.defaultModelCutoffFadeRange] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("model-cutoff-fade-range")
    }

  /**
   * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
   *
   * Use static method [ModelLayer.defaultModelCutoffFadeRange] to set the default property.
   *
   * @param modelCutoffFadeRange value of modelCutoffFadeRange
   */
  override fun modelCutoffFadeRange(modelCutoffFadeRange: Double): ModelLayer = apply {
    val propertyValue = PropertyValue("model-cutoff-fade-range", modelCutoffFadeRange)
    setProperty(propertyValue)
  }

  /**
   * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
   *
   * This is an Expression representation of "model-cutoff-fade-range".
   *
   */
  val modelCutoffFadeRangeAsExpression: Expression?
    /**
     * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
     *
     * Get the ModelCutoffFadeRange property as an Expression
     *
     * Use static method [ModelLayer.defaultModelCutoffFadeRangeAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("model-cutoff-fade-range")

  /**
   * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
   *
   * Use static method [ModelLayer.defaultModelCutoffFadeRangeAsExpression] to set the default property.
   *
   * @param modelCutoffFadeRange value of modelCutoffFadeRange as Expression
   */
  override fun modelCutoffFadeRange(modelCutoffFadeRange: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-cutoff-fade-range", modelCutoffFadeRange)
    setProperty(propertyValue)
  }

  /**
   * Selects the base of the model. Some modes might require precomputed elevation data in the tileset. Default value: "ground".
   */
  val modelElevationReference: ModelElevationReference?
    /**
     * Selects the base of the model. Some modes might require precomputed elevation data in the tileset. Default value: "ground".
     *
     * Use static method [ModelLayer.defaultModelElevationReference] to get the default property.
     *
     * @return ModelElevationReference
     */
    get() {
      getPropertyValue<String?>("model-elevation-reference")?.let {
        return ModelElevationReference.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Selects the base of the model. Some modes might require precomputed elevation data in the tileset. Default value: "ground".
   *
   * Use static method [ModelLayer.defaultModelElevationReference] to set the default property.
   *
   * @param modelElevationReference value of modelElevationReference
   */
  override fun modelElevationReference(modelElevationReference: ModelElevationReference): ModelLayer = apply {
    val propertyValue = PropertyValue("model-elevation-reference", modelElevationReference)
    setProperty(propertyValue)
  }

  /**
   * Selects the base of the model. Some modes might require precomputed elevation data in the tileset. Default value: "ground".
   *
   * This is an Expression representation of "model-elevation-reference".
   *
   */
  val modelElevationReferenceAsExpression: Expression?
    /**
     * Selects the base of the model. Some modes might require precomputed elevation data in the tileset. Default value: "ground".
     *
     * Get the ModelElevationReference property as an Expression
     *
     * Use static method [ModelLayer.defaultModelElevationReferenceAsExpression] to get the default property.
     */
    get() =
      getPropertyValue("model-elevation-reference")
        ?: modelElevationReference?.let {
          Expression.literal(it.value)
        }

  /**
   * Selects the base of the model. Some modes might require precomputed elevation data in the tileset. Default value: "ground".
   *
   * Use static method [ModelLayer.defaultModelElevationReferenceAsExpression] to set the default property.
   *
   * @param modelElevationReference value of modelElevationReference as Expression
   */
  override fun modelElevationReference(modelElevationReference: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-elevation-reference", modelElevationReference)
    setProperty(propertyValue)
  }

  /**
   * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are only supported as a global layer value (and not for each feature) when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 5]
   */
  val modelEmissiveStrength: Double?
    /**
     * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are only supported as a global layer value (and not for each feature) when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 5]
     *
     * Use static method [ModelLayer.defaultModelEmissiveStrength] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("model-emissive-strength")
    }

  /**
   * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are only supported as a global layer value (and not for each feature) when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 5]
   *
   * Use static method [ModelLayer.defaultModelEmissiveStrength] to set the default property.
   *
   * @param modelEmissiveStrength value of modelEmissiveStrength
   */
  override fun modelEmissiveStrength(modelEmissiveStrength: Double): ModelLayer = apply {
    val propertyValue = PropertyValue("model-emissive-strength", modelEmissiveStrength)
    setProperty(propertyValue)
  }

  /**
   * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are only supported as a global layer value (and not for each feature) when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 5]
   *
   * This is an Expression representation of "model-emissive-strength".
   *
   */
  val modelEmissiveStrengthAsExpression: Expression?
    /**
     * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are only supported as a global layer value (and not for each feature) when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 5]
     *
     * Get the ModelEmissiveStrength property as an Expression
     *
     * Use static method [ModelLayer.defaultModelEmissiveStrengthAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("model-emissive-strength")

  /**
   * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are only supported as a global layer value (and not for each feature) when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 5]
   *
   * Use static method [ModelLayer.defaultModelEmissiveStrengthAsExpression] to set the default property.
   *
   * @param modelEmissiveStrength value of modelEmissiveStrength as Expression
   */
  override fun modelEmissiveStrength(modelEmissiveStrength: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-emissive-strength", modelEmissiveStrength)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelEmissiveStrength.
   */
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
  override fun modelEmissiveStrengthTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-emissive-strength-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelEmissiveStrengthTransition].
   */
  override fun modelEmissiveStrengthTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelEmissiveStrengthTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)). Default value: [1,1,1,1,0].
   */
  val modelHeightBasedEmissiveStrengthMultiplier: List<Double>?
    /**
     * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)). Default value: [1,1,1,1,0].
     *
     * Use static method [ModelLayer.defaultModelHeightBasedEmissiveStrengthMultiplier] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue("model-height-based-emissive-strength-multiplier")
    }

  /**
   * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)). Default value: [1,1,1,1,0].
   *
   * Use static method [ModelLayer.defaultModelHeightBasedEmissiveStrengthMultiplier] to set the default property.
   *
   * @param modelHeightBasedEmissiveStrengthMultiplier value of modelHeightBasedEmissiveStrengthMultiplier
   */
  override fun modelHeightBasedEmissiveStrengthMultiplier(modelHeightBasedEmissiveStrengthMultiplier: List<Double>): ModelLayer = apply {
    val propertyValue = PropertyValue("model-height-based-emissive-strength-multiplier", modelHeightBasedEmissiveStrengthMultiplier)
    setProperty(propertyValue)
  }

  /**
   * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)). Default value: [1,1,1,1,0].
   *
   * This is an Expression representation of "model-height-based-emissive-strength-multiplier".
   *
   */
  val modelHeightBasedEmissiveStrengthMultiplierAsExpression: Expression?
    /**
     * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)). Default value: [1,1,1,1,0].
     *
     * Get the ModelHeightBasedEmissiveStrengthMultiplier property as an Expression
     *
     * Use static method [ModelLayer.defaultModelHeightBasedEmissiveStrengthMultiplierAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("model-height-based-emissive-strength-multiplier")

  /**
   * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)). Default value: [1,1,1,1,0].
   *
   * Use static method [ModelLayer.defaultModelHeightBasedEmissiveStrengthMultiplierAsExpression] to set the default property.
   *
   * @param modelHeightBasedEmissiveStrengthMultiplier value of modelHeightBasedEmissiveStrengthMultiplier as Expression
   */
  override fun modelHeightBasedEmissiveStrengthMultiplier(modelHeightBasedEmissiveStrengthMultiplier: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-height-based-emissive-strength-multiplier", modelHeightBasedEmissiveStrengthMultiplier)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelHeightBasedEmissiveStrengthMultiplier.
   */
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
  override fun modelHeightBasedEmissiveStrengthMultiplierTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-height-based-emissive-strength-multiplier-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelHeightBasedEmissiveStrengthMultiplierTransition].
   */
  override fun modelHeightBasedEmissiveStrengthMultiplierTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelHeightBasedEmissiveStrengthMultiplierTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity of the model layer. Except for zoom, expressions that are data-driven are not supported if using GeoJSON or vector tile as the model layer source. Default value: 1. Value range: [0, 1]
   */
  val modelOpacity: Double?
    /**
     * The opacity of the model layer. Except for zoom, expressions that are data-driven are not supported if using GeoJSON or vector tile as the model layer source. Default value: 1. Value range: [0, 1]
     *
     * Use static method [ModelLayer.defaultModelOpacity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("model-opacity")
    }

  /**
   * The opacity of the model layer. Except for zoom, expressions that are data-driven are not supported if using GeoJSON or vector tile as the model layer source. Default value: 1. Value range: [0, 1]
   *
   * Use static method [ModelLayer.defaultModelOpacity] to set the default property.
   *
   * @param modelOpacity value of modelOpacity
   */
  override fun modelOpacity(modelOpacity: Double): ModelLayer = apply {
    val propertyValue = PropertyValue("model-opacity", modelOpacity)
    setProperty(propertyValue)
  }

  /**
   * The opacity of the model layer. Except for zoom, expressions that are data-driven are not supported if using GeoJSON or vector tile as the model layer source. Default value: 1. Value range: [0, 1]
   *
   * This is an Expression representation of "model-opacity".
   *
   */
  val modelOpacityAsExpression: Expression?
    /**
     * The opacity of the model layer. Except for zoom, expressions that are data-driven are not supported if using GeoJSON or vector tile as the model layer source. Default value: 1. Value range: [0, 1]
     *
     * Get the ModelOpacity property as an Expression
     *
     * Use static method [ModelLayer.defaultModelOpacityAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("model-opacity")

  /**
   * The opacity of the model layer. Except for zoom, expressions that are data-driven are not supported if using GeoJSON or vector tile as the model layer source. Default value: 1. Value range: [0, 1]
   *
   * Use static method [ModelLayer.defaultModelOpacityAsExpression] to set the default property.
   *
   * @param modelOpacity value of modelOpacity as Expression
   */
  override fun modelOpacity(modelOpacity: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-opacity", modelOpacity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelOpacity.
   */
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
  override fun modelOpacityTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelOpacityTransition].
   */
  override fun modelOpacityTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Enable/Disable shadow receiving for this layer Default value: true.
   */
  val modelReceiveShadows: Boolean?
    /**
     * Enable/Disable shadow receiving for this layer Default value: true.
     *
     * Use static method [ModelLayer.defaultModelReceiveShadows] to get the default property.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("model-receive-shadows")
    }

  /**
   * Enable/Disable shadow receiving for this layer Default value: true.
   *
   * Use static method [ModelLayer.defaultModelReceiveShadows] to set the default property.
   *
   * @param modelReceiveShadows value of modelReceiveShadows
   */
  override fun modelReceiveShadows(modelReceiveShadows: Boolean): ModelLayer = apply {
    val propertyValue = PropertyValue("model-receive-shadows", modelReceiveShadows)
    setProperty(propertyValue)
  }

  /**
   * Enable/Disable shadow receiving for this layer Default value: true.
   *
   * This is an Expression representation of "model-receive-shadows".
   *
   */
  val modelReceiveShadowsAsExpression: Expression?
    /**
     * Enable/Disable shadow receiving for this layer Default value: true.
     *
     * Get the ModelReceiveShadows property as an Expression
     *
     * Use static method [ModelLayer.defaultModelReceiveShadowsAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("model-receive-shadows")

  /**
   * Enable/Disable shadow receiving for this layer Default value: true.
   *
   * Use static method [ModelLayer.defaultModelReceiveShadowsAsExpression] to set the default property.
   *
   * @param modelReceiveShadows value of modelReceiveShadows as Expression
   */
  override fun modelReceiveShadows(modelReceiveShadows: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-receive-shadows", modelReceiveShadows)
    setProperty(propertyValue)
  }

  /**
   * The rotation of the model in euler angles [lon, lat, z]. Default value: [0,0,0]. The unit of modelRotation is in degrees.
   */
  val modelRotation: List<Double>?
    /**
     * The rotation of the model in euler angles [lon, lat, z]. Default value: [0,0,0]. The unit of modelRotation is in degrees.
     *
     * Use static method [ModelLayer.defaultModelRotation] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue("model-rotation")
    }

  /**
   * The rotation of the model in euler angles [lon, lat, z]. Default value: [0,0,0]. The unit of modelRotation is in degrees.
   *
   * Use static method [ModelLayer.defaultModelRotation] to set the default property.
   *
   * @param modelRotation value of modelRotation
   */
  override fun modelRotation(modelRotation: List<Double>): ModelLayer = apply {
    val propertyValue = PropertyValue("model-rotation", modelRotation)
    setProperty(propertyValue)
  }

  /**
   * The rotation of the model in euler angles [lon, lat, z]. Default value: [0,0,0]. The unit of modelRotation is in degrees.
   *
   * This is an Expression representation of "model-rotation".
   *
   */
  val modelRotationAsExpression: Expression?
    /**
     * The rotation of the model in euler angles [lon, lat, z]. Default value: [0,0,0]. The unit of modelRotation is in degrees.
     *
     * Get the ModelRotation property as an Expression
     *
     * Use static method [ModelLayer.defaultModelRotationAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("model-rotation")

  /**
   * The rotation of the model in euler angles [lon, lat, z]. Default value: [0,0,0]. The unit of modelRotation is in degrees.
   *
   * Use static method [ModelLayer.defaultModelRotationAsExpression] to set the default property.
   *
   * @param modelRotation value of modelRotation as Expression
   */
  override fun modelRotation(modelRotation: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-rotation", modelRotation)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelRotation.
   */
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
  override fun modelRotationTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-rotation-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelRotationTransition].
   */
  override fun modelRotationTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelRotationTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source. Default value: 1. Value range: [0, 1]
   */
  val modelRoughness: Double?
    /**
     * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source. Default value: 1. Value range: [0, 1]
     *
     * Use static method [ModelLayer.defaultModelRoughness] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("model-roughness")
    }

  /**
   * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source. Default value: 1. Value range: [0, 1]
   *
   * Use static method [ModelLayer.defaultModelRoughness] to set the default property.
   *
   * @param modelRoughness value of modelRoughness
   */
  override fun modelRoughness(modelRoughness: Double): ModelLayer = apply {
    val propertyValue = PropertyValue("model-roughness", modelRoughness)
    setProperty(propertyValue)
  }

  /**
   * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source. Default value: 1. Value range: [0, 1]
   *
   * This is an Expression representation of "model-roughness".
   *
   */
  val modelRoughnessAsExpression: Expression?
    /**
     * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source. Default value: 1. Value range: [0, 1]
     *
     * Get the ModelRoughness property as an Expression
     *
     * Use static method [ModelLayer.defaultModelRoughnessAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("model-roughness")

  /**
   * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source. Default value: 1. Value range: [0, 1]
   *
   * Use static method [ModelLayer.defaultModelRoughnessAsExpression] to set the default property.
   *
   * @param modelRoughness value of modelRoughness as Expression
   */
  override fun modelRoughness(modelRoughness: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-roughness", modelRoughness)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelRoughness.
   */
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
  override fun modelRoughnessTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-roughness-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelRoughnessTransition].
   */
  override fun modelRoughnessTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelRoughnessTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The scale of the model. Expressions that are zoom-dependent are not supported if using GeoJSON or vector tile as the model layer source. Default value: [1,1,1].
   */
  val modelScale: List<Double>?
    /**
     * The scale of the model. Expressions that are zoom-dependent are not supported if using GeoJSON or vector tile as the model layer source. Default value: [1,1,1].
     *
     * Use static method [ModelLayer.defaultModelScale] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue("model-scale")
    }

  /**
   * The scale of the model. Expressions that are zoom-dependent are not supported if using GeoJSON or vector tile as the model layer source. Default value: [1,1,1].
   *
   * Use static method [ModelLayer.defaultModelScale] to set the default property.
   *
   * @param modelScale value of modelScale
   */
  override fun modelScale(modelScale: List<Double>): ModelLayer = apply {
    val propertyValue = PropertyValue("model-scale", modelScale)
    setProperty(propertyValue)
  }

  /**
   * The scale of the model. Expressions that are zoom-dependent are not supported if using GeoJSON or vector tile as the model layer source. Default value: [1,1,1].
   *
   * This is an Expression representation of "model-scale".
   *
   */
  val modelScaleAsExpression: Expression?
    /**
     * The scale of the model. Expressions that are zoom-dependent are not supported if using GeoJSON or vector tile as the model layer source. Default value: [1,1,1].
     *
     * Get the ModelScale property as an Expression
     *
     * Use static method [ModelLayer.defaultModelScaleAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("model-scale")

  /**
   * The scale of the model. Expressions that are zoom-dependent are not supported if using GeoJSON or vector tile as the model layer source. Default value: [1,1,1].
   *
   * Use static method [ModelLayer.defaultModelScaleAsExpression] to set the default property.
   *
   * @param modelScale value of modelScale as Expression
   */
  override fun modelScale(modelScale: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-scale", modelScale)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelScale.
   */
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
  override fun modelScaleTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-scale-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelScaleTransition].
   */
  override fun modelScaleTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelScaleTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Defines scaling mode. Only applies to location-indicator type layers. Default value: "map".
   */
  val modelScaleMode: ModelScaleMode?
    /**
     * Defines scaling mode. Only applies to location-indicator type layers. Default value: "map".
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
   * Defines scaling mode. Only applies to location-indicator type layers. Default value: "map".
   *
   * Use static method [ModelLayer.defaultModelScaleMode] to set the default property.
   *
   * @param modelScaleMode value of modelScaleMode
   */
  override fun modelScaleMode(modelScaleMode: ModelScaleMode): ModelLayer = apply {
    val propertyValue = PropertyValue("model-scale-mode", modelScaleMode)
    setProperty(propertyValue)
  }

  /**
   * Defines scaling mode. Only applies to location-indicator type layers. Default value: "map".
   *
   * This is an Expression representation of "model-scale-mode".
   *
   */
  val modelScaleModeAsExpression: Expression?
    /**
     * Defines scaling mode. Only applies to location-indicator type layers. Default value: "map".
     *
     * Get the ModelScaleMode property as an Expression
     *
     * Use static method [ModelLayer.defaultModelScaleModeAsExpression] to get the default property.
     */
    get() =
      getPropertyValue("model-scale-mode")
        ?: modelScaleMode?.let {
          Expression.literal(it.value)
        }

  /**
   * Defines scaling mode. Only applies to location-indicator type layers. Default value: "map".
   *
   * Use static method [ModelLayer.defaultModelScaleModeAsExpression] to set the default property.
   *
   * @param modelScaleMode value of modelScaleMode as Expression
   */
  override fun modelScaleMode(modelScaleMode: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-scale-mode", modelScaleMode)
    setProperty(propertyValue)
  }

  /**
   * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets. Default value: [0,0,0].
   */
  val modelTranslation: List<Double>?
    /**
     * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets. Default value: [0,0,0].
     *
     * Use static method [ModelLayer.defaultModelTranslation] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue("model-translation")
    }

  /**
   * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets. Default value: [0,0,0].
   *
   * Use static method [ModelLayer.defaultModelTranslation] to set the default property.
   *
   * @param modelTranslation value of modelTranslation
   */
  override fun modelTranslation(modelTranslation: List<Double>): ModelLayer = apply {
    val propertyValue = PropertyValue("model-translation", modelTranslation)
    setProperty(propertyValue)
  }

  /**
   * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets. Default value: [0,0,0].
   *
   * This is an Expression representation of "model-translation".
   *
   */
  val modelTranslationAsExpression: Expression?
    /**
     * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets. Default value: [0,0,0].
     *
     * Get the ModelTranslation property as an Expression
     *
     * Use static method [ModelLayer.defaultModelTranslationAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("model-translation")

  /**
   * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets. Default value: [0,0,0].
   *
   * Use static method [ModelLayer.defaultModelTranslationAsExpression] to set the default property.
   *
   * @param modelTranslation value of modelTranslation as Expression
   */
  override fun modelTranslation(modelTranslation: Expression): ModelLayer = apply {
    val propertyValue = PropertyValue("model-translation", modelTranslation)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ModelTranslation.
   */
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
  override fun modelTranslationTransition(options: StyleTransition): ModelLayer = apply {
    val propertyValue = PropertyValue("model-translation-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelTranslationTransition].
   */
  override fun modelTranslationTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer = apply {
    modelTranslationTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Defines rendering behavior of model in respect to other 3D scene objects. Default value: "common-3d".
   */
  val modelType: ModelType?
    /**
     * Defines rendering behavior of model in respect to other 3D scene objects. Default value: "common-3d".
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
   * Defines rendering behavior of model in respect to other 3D scene objects. Default value: "common-3d".
   *
   * Use static method [ModelLayer.defaultModelType] to set the default property.
   *
   * @param modelType value of modelType
   */
  override fun modelType(modelType: ModelType): ModelLayer = apply {
    val propertyValue = PropertyValue("model-type", modelType)
    setProperty(propertyValue)
  }

  /**
   * Defines rendering behavior of model in respect to other 3D scene objects. Default value: "common-3d".
   *
   * This is an Expression representation of "model-type".
   *
   */
  val modelTypeAsExpression: Expression?
    /**
     * Defines rendering behavior of model in respect to other 3D scene objects. Default value: "common-3d".
     *
     * Get the ModelType property as an Expression
     *
     * Use static method [ModelLayer.defaultModelTypeAsExpression] to get the default property.
     */
    get() =
      getPropertyValue("model-type")
        ?: modelType?.let {
          Expression.literal(it.value)
        }

  /**
   * Defines rendering behavior of model in respect to other 3D scene objects. Default value: "common-3d".
   *
   * Use static method [ModelLayer.defaultModelTypeAsExpression] to set the default property.
   *
   * @param modelType value of modelType as Expression
   */
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
     * If true, the models will be reduced in density based on the zoom level. This is useful for large datasets that may be slow to render. Default value: true.
     */
    @MapboxExperimental
    val defaultModelAllowDensityReduction: Boolean?
      /**
       * If true, the models will be reduced in density based on the zoom level. This is useful for large datasets that may be slow to render. Default value: true.
       *
       * Get the default value of ModelAllowDensityReduction property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-allow-density-reduction").silentUnwrap()
      }

    /**
     * If true, the models will be reduced in density based on the zoom level. This is useful for large datasets that may be slow to render. Default value: true.
     *
     * This is an Expression representation of "model-allow-density-reduction".
     *
     */
    @MapboxExperimental
    val defaultModelAllowDensityReductionAsExpression: Expression?
      /**
       * Get default value of the ModelAllowDensityReduction property as an Expression
       *
       * @return Boolean
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-allow-density-reduction").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultModelAllowDensityReduction?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Model to render. It can be either a string referencing an element to the models root property or an internal or external URL Default value: "".
     */
    val defaultModelId: String?
      /**
       * Model to render. It can be either a string referencing an element to the models root property or an internal or external URL Default value: "".
       *
       * Get the default value of ModelId property
       *
       * @return String
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-id").silentUnwrap()
      }

    /**
     * Model to render. It can be either a string referencing an element to the models root property or an internal or external URL Default value: "".
     *
     * This is an Expression representation of "model-id".
     *
     */
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
     * Intensity of the ambient occlusion if present in the 3D model. Default value: 1. Value range: [0, 1]
     */
    val defaultModelAmbientOcclusionIntensity: Double?
      /**
       * Intensity of the ambient occlusion if present in the 3D model. Default value: 1. Value range: [0, 1]
       *
       * Get the default value of ModelAmbientOcclusionIntensity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-ambient-occlusion-intensity").silentUnwrap()
      }

    /**
     * Intensity of the ambient occlusion if present in the 3D model. Default value: 1. Value range: [0, 1]
     *
     * This is an Expression representation of "model-ambient-occlusion-intensity".
     *
     */
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
    val defaultModelAmbientOcclusionIntensityTransition: StyleTransition?
      /**
       * Get the ModelAmbientOcclusionIntensity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-ambient-occlusion-intensity-transition").silentUnwrap()

    /**
     * Enable/Disable shadow casting for this layer Default value: true.
     */
    val defaultModelCastShadows: Boolean?
      /**
       * Enable/Disable shadow casting for this layer Default value: true.
       *
       * Get the default value of ModelCastShadows property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-cast-shadows").silentUnwrap()
      }

    /**
     * Enable/Disable shadow casting for this layer Default value: true.
     *
     * This is an Expression representation of "model-cast-shadows".
     *
     */
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
     * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
     */
    val defaultModelColor: String?
      /**
       * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
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
     * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
     *
     * This is an Expression representation of "model-color".
     *
     */
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
     * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
     */
    val defaultModelColorAsColorInt: Int?
      /**
       * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
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
    val defaultModelColorTransition: StyleTransition?
      /**
       * Get the ModelColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-color-transition").silentUnwrap()

    /**
     * Default color theme for [modelColor].
     */
    @MapboxExperimental
    val defaultModelColorUseTheme: String?
      /**
       * Get default value of the ModelColor property as String
       *
       * @return String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-color-use-theme").silentUnwrap()

    /**
     * Default color theme for [modelColor].
     */
    @MapboxExperimental
    val defaultModelColorUseThemeAsExpression: Expression?
      /**
       * Get default value of the ModelColor property as Expression
       *
       * @return String
       */
      get() {
        return StyleManager
          .getStyleLayerPropertyDefaultValue("model", "model-color-use-theme")
          .silentUnwrap<Expression>() ?: defaultModelColorUseTheme?.let { Expression.literal(it) }
      }

    /**
     * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 1]
     */
    val defaultModelColorMixIntensity: Double?
      /**
       * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 1]
       *
       * Get the default value of ModelColorMixIntensity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-color-mix-intensity").silentUnwrap()
      }

    /**
     * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 1]
     *
     * This is an Expression representation of "model-color-mix-intensity".
     *
     */
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
    val defaultModelColorMixIntensityTransition: StyleTransition?
      /**
       * Get the ModelColorMixIntensity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-color-mix-intensity-transition").silentUnwrap()

    /**
     * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
     */
    val defaultModelCutoffFadeRange: Double?
      /**
       * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
       *
       * Get the default value of ModelCutoffFadeRange property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-cutoff-fade-range").silentUnwrap()
      }

    /**
     * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
     *
     * This is an Expression representation of "model-cutoff-fade-range".
     *
     */
    val defaultModelCutoffFadeRangeAsExpression: Expression?
      /**
       * Get default value of the ModelCutoffFadeRange property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-cutoff-fade-range").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultModelCutoffFadeRange?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Selects the base of the model. Some modes might require precomputed elevation data in the tileset. Default value: "ground".
     */
    val defaultModelElevationReference: ModelElevationReference?
      /**
       * Selects the base of the model. Some modes might require precomputed elevation data in the tileset. Default value: "ground".
       *
       * Get the default value of ModelElevationReference property
       *
       * @return ModelElevationReference
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-elevation-reference").silentUnwrap<String>()?.let {
          return ModelElevationReference.valueOf(it.uppercase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Selects the base of the model. Some modes might require precomputed elevation data in the tileset. Default value: "ground".
     *
     * This is an Expression representation of "model-elevation-reference".
     *
     */
    val defaultModelElevationReferenceAsExpression: Expression?
      /**
       * Get default value of the ModelElevationReference property as an Expression
       *
       * @return ModelElevationReference
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("model", "model-elevation-reference").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultModelElevationReference?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are only supported as a global layer value (and not for each feature) when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 5]
     */
    val defaultModelEmissiveStrength: Double?
      /**
       * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are only supported as a global layer value (and not for each feature) when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 5]
       *
       * Get the default value of ModelEmissiveStrength property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-emissive-strength").silentUnwrap()
      }

    /**
     * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are only supported as a global layer value (and not for each feature) when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 5]
     *
     * This is an Expression representation of "model-emissive-strength".
     *
     */
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
    val defaultModelEmissiveStrengthTransition: StyleTransition?
      /**
       * Get the ModelEmissiveStrength property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-emissive-strength-transition").silentUnwrap()

    /**
     * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)). Default value: [1,1,1,1,0].
     */
    val defaultModelHeightBasedEmissiveStrengthMultiplier: List<Double>?
      /**
       * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)). Default value: [1,1,1,1,0].
       *
       * Get the default value of ModelHeightBasedEmissiveStrengthMultiplier property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-height-based-emissive-strength-multiplier").silentUnwrap()
      }

    /**
     * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)). Default value: [1,1,1,1,0].
     *
     * This is an Expression representation of "model-height-based-emissive-strength-multiplier".
     *
     */
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
    val defaultModelHeightBasedEmissiveStrengthMultiplierTransition: StyleTransition?
      /**
       * Get the ModelHeightBasedEmissiveStrengthMultiplier property transition options
       *
       * @return transition options for List<Double>
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-height-based-emissive-strength-multiplier-transition").silentUnwrap()

    /**
     * The opacity of the model layer. Except for zoom, expressions that are data-driven are not supported if using GeoJSON or vector tile as the model layer source. Default value: 1. Value range: [0, 1]
     */
    val defaultModelOpacity: Double?
      /**
       * The opacity of the model layer. Except for zoom, expressions that are data-driven are not supported if using GeoJSON or vector tile as the model layer source. Default value: 1. Value range: [0, 1]
       *
       * Get the default value of ModelOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-opacity").silentUnwrap()
      }

    /**
     * The opacity of the model layer. Except for zoom, expressions that are data-driven are not supported if using GeoJSON or vector tile as the model layer source. Default value: 1. Value range: [0, 1]
     *
     * This is an Expression representation of "model-opacity".
     *
     */
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
    val defaultModelOpacityTransition: StyleTransition?
      /**
       * Get the ModelOpacity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-opacity-transition").silentUnwrap()

    /**
     * Enable/Disable shadow receiving for this layer Default value: true.
     */
    val defaultModelReceiveShadows: Boolean?
      /**
       * Enable/Disable shadow receiving for this layer Default value: true.
       *
       * Get the default value of ModelReceiveShadows property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-receive-shadows").silentUnwrap()
      }

    /**
     * Enable/Disable shadow receiving for this layer Default value: true.
     *
     * This is an Expression representation of "model-receive-shadows".
     *
     */
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
     * The rotation of the model in euler angles [lon, lat, z]. Default value: [0,0,0]. The unit of modelRotation is in degrees.
     */
    val defaultModelRotation: List<Double>?
      /**
       * The rotation of the model in euler angles [lon, lat, z]. Default value: [0,0,0]. The unit of modelRotation is in degrees.
       *
       * Get the default value of ModelRotation property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-rotation").silentUnwrap()
      }

    /**
     * The rotation of the model in euler angles [lon, lat, z]. Default value: [0,0,0]. The unit of modelRotation is in degrees.
     *
     * This is an Expression representation of "model-rotation".
     *
     */
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
    val defaultModelRotationTransition: StyleTransition?
      /**
       * Get the ModelRotation property transition options
       *
       * @return transition options for List<Double>
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-rotation-transition").silentUnwrap()

    /**
     * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source. Default value: 1. Value range: [0, 1]
     */
    val defaultModelRoughness: Double?
      /**
       * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source. Default value: 1. Value range: [0, 1]
       *
       * Get the default value of ModelRoughness property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-roughness").silentUnwrap()
      }

    /**
     * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source. Default value: 1. Value range: [0, 1]
     *
     * This is an Expression representation of "model-roughness".
     *
     */
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
    val defaultModelRoughnessTransition: StyleTransition?
      /**
       * Get the ModelRoughness property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-roughness-transition").silentUnwrap()

    /**
     * The scale of the model. Expressions that are zoom-dependent are not supported if using GeoJSON or vector tile as the model layer source. Default value: [1,1,1].
     */
    val defaultModelScale: List<Double>?
      /**
       * The scale of the model. Expressions that are zoom-dependent are not supported if using GeoJSON or vector tile as the model layer source. Default value: [1,1,1].
       *
       * Get the default value of ModelScale property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-scale").silentUnwrap()
      }

    /**
     * The scale of the model. Expressions that are zoom-dependent are not supported if using GeoJSON or vector tile as the model layer source. Default value: [1,1,1].
     *
     * This is an Expression representation of "model-scale".
     *
     */
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
    val defaultModelScaleTransition: StyleTransition?
      /**
       * Get the ModelScale property transition options
       *
       * @return transition options for List<Double>
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-scale-transition").silentUnwrap()

    /**
     * Defines scaling mode. Only applies to location-indicator type layers. Default value: "map".
     */
    val defaultModelScaleMode: ModelScaleMode?
      /**
       * Defines scaling mode. Only applies to location-indicator type layers. Default value: "map".
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
     * Defines scaling mode. Only applies to location-indicator type layers. Default value: "map".
     *
     * This is an Expression representation of "model-scale-mode".
     *
     */
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
     * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets. Default value: [0,0,0].
     */
    val defaultModelTranslation: List<Double>?
      /**
       * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets. Default value: [0,0,0].
       *
       * Get the default value of ModelTranslation property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-translation").silentUnwrap()
      }

    /**
     * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets. Default value: [0,0,0].
     *
     * This is an Expression representation of "model-translation".
     *
     */
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
    val defaultModelTranslationTransition: StyleTransition?
      /**
       * Get the ModelTranslation property transition options
       *
       * @return transition options for List<Double>
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("model", "model-translation-transition").silentUnwrap()

    /**
     * Defines rendering behavior of model in respect to other 3D scene objects. Default value: "common-3d".
     */
    val defaultModelType: ModelType?
      /**
       * Defines rendering behavior of model in respect to other 3D scene objects. Default value: "common-3d".
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
     * Defines rendering behavior of model in respect to other 3D scene objects. Default value: "common-3d".
     *
     * This is an Expression representation of "model-type".
     *
     */
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
   * @param slot value of slot. Setting it to empty string removes the slot.
   */
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
   * If true, the models will be reduced in density based on the zoom level. This is useful for large datasets that may be slow to render. Default value: true.
   *
   * @param modelAllowDensityReduction value of modelAllowDensityReduction
   */
  @MapboxExperimental
  fun modelAllowDensityReduction(modelAllowDensityReduction: Boolean = true): ModelLayer

  /**
   * If true, the models will be reduced in density based on the zoom level. This is useful for large datasets that may be slow to render. Default value: true.
   *
   * @param modelAllowDensityReduction value of modelAllowDensityReduction as Expression
   */
  @MapboxExperimental
  fun modelAllowDensityReduction(modelAllowDensityReduction: Expression): ModelLayer

  /**
   * Model to render. It can be either a string referencing an element to the models root property or an internal or external URL Default value: "".
   *
   * @param modelId value of modelId
   */
  fun modelId(modelId: String = ""): ModelLayer

  /**
   * Model to render. It can be either a string referencing an element to the models root property or an internal or external URL Default value: "".
   *
   * @param modelId value of modelId as Expression
   */
  fun modelId(modelId: Expression): ModelLayer

  /**
   * Intensity of the ambient occlusion if present in the 3D model. Default value: 1. Value range: [0, 1]
   *
   * @param modelAmbientOcclusionIntensity value of modelAmbientOcclusionIntensity
   */
  fun modelAmbientOcclusionIntensity(modelAmbientOcclusionIntensity: Double = 1.0): ModelLayer

  /**
   * Intensity of the ambient occlusion if present in the 3D model. Default value: 1. Value range: [0, 1]
   *
   * @param modelAmbientOcclusionIntensity value of modelAmbientOcclusionIntensity as Expression
   */
  fun modelAmbientOcclusionIntensity(modelAmbientOcclusionIntensity: Expression): ModelLayer

  /**
   * Intensity of the ambient occlusion if present in the 3D model. Default value: 1. Value range: [0, 1]
   *
   * Set the ModelAmbientOcclusionIntensity property transition options
   *
   * @param options transition options for Double
   */
  fun modelAmbientOcclusionIntensityTransition(options: StyleTransition): ModelLayer

  /**
   * Intensity of the ambient occlusion if present in the 3D model. Default value: 1. Value range: [0, 1]
   *
   * DSL for [modelAmbientOcclusionIntensityTransition].
   */
  fun modelAmbientOcclusionIntensityTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * Enable/Disable shadow casting for this layer Default value: true.
   *
   * @param modelCastShadows value of modelCastShadows
   */
  fun modelCastShadows(modelCastShadows: Boolean = true): ModelLayer

  /**
   * Enable/Disable shadow casting for this layer Default value: true.
   *
   * @param modelCastShadows value of modelCastShadows as Expression
   */
  fun modelCastShadows(modelCastShadows: Expression): ModelLayer

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
   *
   * @param modelColor value of modelColor
   */
  fun modelColor(modelColor: String = "#ffffff"): ModelLayer

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
   *
   * @param modelColor value of modelColor as Expression
   */
  fun modelColor(modelColor: Expression): ModelLayer

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
   *
   * @param modelColor value of modelColor
   */
  fun modelColor(@ColorInt modelColor: Int): ModelLayer

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
   *
   * Set the ModelColor property transition options
   *
   * @param options transition options for String
   */
  fun modelColorTransition(options: StyleTransition): ModelLayer

  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
   *
   * DSL for [modelColorTransition].
   */
  fun modelColorTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * Set the modelColorUseTheme as String for [modelColor].
   *
   * @param modelColorUseTheme overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  fun modelColorUseTheme(modelColorUseTheme: String): ModelLayer

  /**
   * Set the modelColorUseTheme as Expression for [modelColor].
   *
   * @param modelColorUseTheme overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  fun modelColorUseTheme(modelColorUseTheme: Expression): ModelLayer

  /**
   * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 1]
   *
   * @param modelColorMixIntensity value of modelColorMixIntensity
   */
  fun modelColorMixIntensity(modelColorMixIntensity: Double = 0.0): ModelLayer

  /**
   * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 1]
   *
   * @param modelColorMixIntensity value of modelColorMixIntensity as Expression
   */
  fun modelColorMixIntensity(modelColorMixIntensity: Expression): ModelLayer

  /**
   * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 1]
   *
   * Set the ModelColorMixIntensity property transition options
   *
   * @param options transition options for Double
   */
  fun modelColorMixIntensityTransition(options: StyleTransition): ModelLayer

  /**
   * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 1]
   *
   * DSL for [modelColorMixIntensityTransition].
   */
  fun modelColorMixIntensityTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
   *
   * @param modelCutoffFadeRange value of modelCutoffFadeRange
   */
  fun modelCutoffFadeRange(modelCutoffFadeRange: Double = 0.0): ModelLayer

  /**
   * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled. Default value: 0. Value range: [0, 1]
   *
   * @param modelCutoffFadeRange value of modelCutoffFadeRange as Expression
   */
  fun modelCutoffFadeRange(modelCutoffFadeRange: Expression): ModelLayer

  /**
   * Selects the base of the model. Some modes might require precomputed elevation data in the tileset. Default value: "ground".
   *
   * @param modelElevationReference value of modelElevationReference
   */
  fun modelElevationReference(modelElevationReference: ModelElevationReference = ModelElevationReference.GROUND): ModelLayer

  /**
   * Selects the base of the model. Some modes might require precomputed elevation data in the tileset. Default value: "ground".
   *
   * @param modelElevationReference value of modelElevationReference as Expression
   */
  fun modelElevationReference(modelElevationReference: Expression): ModelLayer

  /**
   * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are only supported as a global layer value (and not for each feature) when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 5]
   *
   * @param modelEmissiveStrength value of modelEmissiveStrength
   */
  fun modelEmissiveStrength(modelEmissiveStrength: Double = 0.0): ModelLayer

  /**
   * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are only supported as a global layer value (and not for each feature) when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 5]
   *
   * @param modelEmissiveStrength value of modelEmissiveStrength as Expression
   */
  fun modelEmissiveStrength(modelEmissiveStrength: Expression): ModelLayer

  /**
   * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are only supported as a global layer value (and not for each feature) when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 5]
   *
   * Set the ModelEmissiveStrength property transition options
   *
   * @param options transition options for Double
   */
  fun modelEmissiveStrengthTransition(options: StyleTransition): ModelLayer

  /**
   * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are only supported as a global layer value (and not for each feature) when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 5]
   *
   * DSL for [modelEmissiveStrengthTransition].
   */
  fun modelEmissiveStrengthTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)). Default value: [1,1,1,1,0].
   *
   * @param modelHeightBasedEmissiveStrengthMultiplier value of modelHeightBasedEmissiveStrengthMultiplier
   */
  fun modelHeightBasedEmissiveStrengthMultiplier(modelHeightBasedEmissiveStrengthMultiplier: List<Double> = listOf(1.0, 1.0, 1.0, 1.0, 0.0)): ModelLayer

  /**
   * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)). Default value: [1,1,1,1,0].
   *
   * @param modelHeightBasedEmissiveStrengthMultiplier value of modelHeightBasedEmissiveStrengthMultiplier as Expression
   */
  fun modelHeightBasedEmissiveStrengthMultiplier(modelHeightBasedEmissiveStrengthMultiplier: Expression): ModelLayer

  /**
   * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)). Default value: [1,1,1,1,0].
   *
   * Set the ModelHeightBasedEmissiveStrengthMultiplier property transition options
   *
   * @param options transition options for List<Double>
   */
  fun modelHeightBasedEmissiveStrengthMultiplierTransition(options: StyleTransition): ModelLayer

  /**
   * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)). Default value: [1,1,1,1,0].
   *
   * DSL for [modelHeightBasedEmissiveStrengthMultiplierTransition].
   */
  fun modelHeightBasedEmissiveStrengthMultiplierTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * The opacity of the model layer. Except for zoom, expressions that are data-driven are not supported if using GeoJSON or vector tile as the model layer source. Default value: 1. Value range: [0, 1]
   *
   * @param modelOpacity value of modelOpacity
   */
  fun modelOpacity(modelOpacity: Double = 1.0): ModelLayer

  /**
   * The opacity of the model layer. Except for zoom, expressions that are data-driven are not supported if using GeoJSON or vector tile as the model layer source. Default value: 1. Value range: [0, 1]
   *
   * @param modelOpacity value of modelOpacity as Expression
   */
  fun modelOpacity(modelOpacity: Expression): ModelLayer

  /**
   * The opacity of the model layer. Except for zoom, expressions that are data-driven are not supported if using GeoJSON or vector tile as the model layer source. Default value: 1. Value range: [0, 1]
   *
   * Set the ModelOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun modelOpacityTransition(options: StyleTransition): ModelLayer

  /**
   * The opacity of the model layer. Except for zoom, expressions that are data-driven are not supported if using GeoJSON or vector tile as the model layer source. Default value: 1. Value range: [0, 1]
   *
   * DSL for [modelOpacityTransition].
   */
  fun modelOpacityTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * Enable/Disable shadow receiving for this layer Default value: true.
   *
   * @param modelReceiveShadows value of modelReceiveShadows
   */
  fun modelReceiveShadows(modelReceiveShadows: Boolean = true): ModelLayer

  /**
   * Enable/Disable shadow receiving for this layer Default value: true.
   *
   * @param modelReceiveShadows value of modelReceiveShadows as Expression
   */
  fun modelReceiveShadows(modelReceiveShadows: Expression): ModelLayer

  /**
   * The rotation of the model in euler angles [lon, lat, z]. Default value: [0,0,0]. The unit of modelRotation is in degrees.
   *
   * @param modelRotation value of modelRotation
   */
  fun modelRotation(modelRotation: List<Double> = listOf(0.0, 0.0, 0.0)): ModelLayer

  /**
   * The rotation of the model in euler angles [lon, lat, z]. Default value: [0,0,0]. The unit of modelRotation is in degrees.
   *
   * @param modelRotation value of modelRotation as Expression
   */
  fun modelRotation(modelRotation: Expression): ModelLayer

  /**
   * The rotation of the model in euler angles [lon, lat, z]. Default value: [0,0,0]. The unit of modelRotation is in degrees.
   *
   * Set the ModelRotation property transition options
   *
   * @param options transition options for List<Double>
   */
  fun modelRotationTransition(options: StyleTransition): ModelLayer

  /**
   * The rotation of the model in euler angles [lon, lat, z]. Default value: [0,0,0]. The unit of modelRotation is in degrees.
   *
   * DSL for [modelRotationTransition].
   */
  fun modelRotationTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source. Default value: 1. Value range: [0, 1]
   *
   * @param modelRoughness value of modelRoughness
   */
  fun modelRoughness(modelRoughness: Double = 1.0): ModelLayer

  /**
   * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source. Default value: 1. Value range: [0, 1]
   *
   * @param modelRoughness value of modelRoughness as Expression
   */
  fun modelRoughness(modelRoughness: Expression): ModelLayer

  /**
   * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source. Default value: 1. Value range: [0, 1]
   *
   * Set the ModelRoughness property transition options
   *
   * @param options transition options for Double
   */
  fun modelRoughnessTransition(options: StyleTransition): ModelLayer

  /**
   * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source. Default value: 1. Value range: [0, 1]
   *
   * DSL for [modelRoughnessTransition].
   */
  fun modelRoughnessTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * The scale of the model. Expressions that are zoom-dependent are not supported if using GeoJSON or vector tile as the model layer source. Default value: [1,1,1].
   *
   * @param modelScale value of modelScale
   */
  fun modelScale(modelScale: List<Double> = listOf(1.0, 1.0, 1.0)): ModelLayer

  /**
   * The scale of the model. Expressions that are zoom-dependent are not supported if using GeoJSON or vector tile as the model layer source. Default value: [1,1,1].
   *
   * @param modelScale value of modelScale as Expression
   */
  fun modelScale(modelScale: Expression): ModelLayer

  /**
   * The scale of the model. Expressions that are zoom-dependent are not supported if using GeoJSON or vector tile as the model layer source. Default value: [1,1,1].
   *
   * Set the ModelScale property transition options
   *
   * @param options transition options for List<Double>
   */
  fun modelScaleTransition(options: StyleTransition): ModelLayer

  /**
   * The scale of the model. Expressions that are zoom-dependent are not supported if using GeoJSON or vector tile as the model layer source. Default value: [1,1,1].
   *
   * DSL for [modelScaleTransition].
   */
  fun modelScaleTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * Defines scaling mode. Only applies to location-indicator type layers. Default value: "map".
   *
   * @param modelScaleMode value of modelScaleMode
   */
  fun modelScaleMode(modelScaleMode: ModelScaleMode = ModelScaleMode.MAP): ModelLayer

  /**
   * Defines scaling mode. Only applies to location-indicator type layers. Default value: "map".
   *
   * @param modelScaleMode value of modelScaleMode as Expression
   */
  fun modelScaleMode(modelScaleMode: Expression): ModelLayer

  /**
   * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets. Default value: [0,0,0].
   *
   * @param modelTranslation value of modelTranslation
   */
  fun modelTranslation(modelTranslation: List<Double> = listOf(0.0, 0.0, 0.0)): ModelLayer

  /**
   * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets. Default value: [0,0,0].
   *
   * @param modelTranslation value of modelTranslation as Expression
   */
  fun modelTranslation(modelTranslation: Expression): ModelLayer

  /**
   * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets. Default value: [0,0,0].
   *
   * Set the ModelTranslation property transition options
   *
   * @param options transition options for List<Double>
   */
  fun modelTranslationTransition(options: StyleTransition): ModelLayer

  /**
   * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets. Default value: [0,0,0].
   *
   * DSL for [modelTranslationTransition].
   */
  fun modelTranslationTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * Defines rendering behavior of model in respect to other 3D scene objects. Default value: "common-3d".
   *
   * @param modelType value of modelType
   */
  fun modelType(modelType: ModelType = ModelType.COMMON_3D): ModelLayer

  /**
   * Defines rendering behavior of model in respect to other 3D scene objects. Default value: "common-3d".
   *
   * @param modelType value of modelType as Expression
   */
  fun modelType(modelType: Expression): ModelLayer
}

/**
 * DSL function for creating a [ModelLayer].
 */
fun modelLayer(layerId: String, sourceId: String, block: ModelLayerDsl.() -> Unit): ModelLayer = ModelLayer(layerId, sourceId).apply(block)

// End of generated file.