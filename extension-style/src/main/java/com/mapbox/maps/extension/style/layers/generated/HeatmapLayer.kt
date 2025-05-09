// This file is generated.

package com.mapbox.maps.extension.style.layers.generated

import androidx.annotation.UiThread
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.Layer
import com.mapbox.maps.extension.style.layers.properties.*
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.types.*
import com.mapbox.maps.extension.style.utils.silentUnwrap
import java.util.*

/**
 * A heatmap.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers/#heatmap)
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
 */
@UiThread
class HeatmapLayer(override val layerId: String, val sourceId: String) : HeatmapLayerDsl, Layer() {
  init {
    internalSourceId = sourceId
  }

  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  override fun sourceLayer(sourceLayer: String): HeatmapLayer = apply {
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
  override fun slot(slot: String): HeatmapLayer = apply {
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
  override fun filter(filter: Expression): HeatmapLayer = apply {
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
     * Use static method [HeatmapLayer.defaultVisibility] to get the default property value.
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
     * Use static method [HeatmapLayer.defaultVisibility] to get the default property value.
     *
     * @return VISIBILITY as expression
     */
    get() = getPropertyValue("visibility")

  /**
   * Whether this layer is displayed.
   *
   * Use static method [HeatmapLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Visibility): HeatmapLayer = apply {
    val propertyValue = PropertyValue("visibility", visibility)
    setProperty(propertyValue)
  }

  /**
   * Whether this layer is displayed.
   *
   * Use static method [[HeatmapLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Expression): HeatmapLayer = apply {
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
     * Use static method [HeatmapLayer.defaultMinZoom] to get the default property value.
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
   * Use static method [HeatmapLayer.defaultMinZoom] to get the default property value.
   *
   * @param minZoom value of minzoom
   */
  override fun minZoom(minZoom: Double): HeatmapLayer = apply {
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
     * Use static method [HeatmapLayer.defaultMaxZoom] to get the default property value.
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
   * Use static method [HeatmapLayer.defaultMaxZoom] to get the default property value.
   *
   * @param maxZoom value of maxzoom
   */
  override fun maxZoom(maxZoom: Double): HeatmapLayer = apply {
    val param = PropertyValue("maxzoom", maxZoom)
    setProperty(param)
  }

  // Property getters and setters

  /**
   * Defines the color of each pixel based on its density value in a heatmap. Should be an expression that uses `["heatmap-density"]` as input. Default value: ["interpolate",["linear"],["heatmap-density"],0,"rgba(0, 0, 255, 0)",0.1,"royalblue",0.3,"cyan",0.5,"lime",0.7,"yellow",1,"red"].
   */
  val heatmapColor: Expression?
    /**
     * Defines the color of each pixel based on its density value in a heatmap. Should be an expression that uses `["heatmap-density"]` as input. Default value: ["interpolate",["linear"],["heatmap-density"],0,"rgba(0, 0, 255, 0)",0.1,"royalblue",0.3,"cyan",0.5,"lime",0.7,"yellow",1,"red"].
     *
     * Use static method [HeatmapLayer.defaultHeatmapColor] to get the default property.
     *
     * @return Expression
     */
    get() {
      return getPropertyValue("heatmap-color")
    }

  /**
   * Defines the color of each pixel based on its density value in a heatmap. Should be an expression that uses `["heatmap-density"]` as input. Default value: ["interpolate",["linear"],["heatmap-density"],0,"rgba(0, 0, 255, 0)",0.1,"royalblue",0.3,"cyan",0.5,"lime",0.7,"yellow",1,"red"].
   *
   * Use static method [HeatmapLayer.defaultHeatmapColor] to set the default property.
   *
   * @param heatmapColor value of heatmapColor
   */
  override fun heatmapColor(heatmapColor: Expression): HeatmapLayer = apply {
    val propertyValue = PropertyValue("heatmap-color", heatmapColor)
    setProperty(propertyValue)
  }

  /**
   * Сolor theme override for [heatmapColor].
   */
  @MapboxExperimental
  val heatmapColorUseTheme: String?
    /**
     * Get the HeatmapColorUseTheme property
     *
     * Use static method [HeatmapLayer.defaultHeatmapColorUseTheme] to get the default property.
     *
     * @return current HeatmapColorUseTheme property as Expression
     */
    get() {
      return getPropertyValue("heatmap-color-use-theme")
    }

  /**
   * Set the HeatmapColorUseTheme as String
   *
   * Use static method [HeatmapLayer.defaultHeatmapColorUseTheme] to get the default property.
   *
   * @param heatmapColorUseTheme theme value for color. Overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  override fun heatmapColorUseTheme(heatmapColorUseTheme: String): HeatmapLayer = apply {
    val propertyValue = PropertyValue("heatmap-color-use-theme", heatmapColorUseTheme)
    setProperty(propertyValue)
  }

  /**
   * Сolor theme override for [heatmapColor].
   */
  @MapboxExperimental
  val heatmapColorUseThemeAsExpression: Expression?
    /**
     * Get the HeatmapColorUseTheme property
     *
     * Use static method [HeatmapLayer.defaultHeatmapColorUseTheme] to get the default property.
     *
     * @return current HeatmapColorUseTheme property as Expression
     */
    get() = getPropertyValueAsExpressionOrLiteralExpression("heatmap-color-use-theme")

  /**
   * Set the HeatmapColorUseTheme as Expression
   *
   * Use static method [HeatmapLayer.defaultHeatmapColorUseTheme] to get the default property.
   *
   * @param heatmapColorUseTheme theme value for color. Overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  override fun heatmapColorUseTheme(heatmapColorUseTheme: Expression): HeatmapLayer = apply {
    val propertyValue = PropertyValue("heatmap-color-use-theme", heatmapColorUseTheme)
    setProperty(propertyValue)
  }

  /**
   * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level. Default value: 1. Minimum value: 0.
   */
  val heatmapIntensity: Double?
    /**
     * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level. Default value: 1. Minimum value: 0.
     *
     * Use static method [HeatmapLayer.defaultHeatmapIntensity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("heatmap-intensity")
    }

  /**
   * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level. Default value: 1. Minimum value: 0.
   *
   * Use static method [HeatmapLayer.defaultHeatmapIntensity] to set the default property.
   *
   * @param heatmapIntensity value of heatmapIntensity
   */
  override fun heatmapIntensity(heatmapIntensity: Double): HeatmapLayer = apply {
    val propertyValue = PropertyValue("heatmap-intensity", heatmapIntensity)
    setProperty(propertyValue)
  }

  /**
   * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level. Default value: 1. Minimum value: 0.
   *
   * This is an Expression representation of "heatmap-intensity".
   *
   */
  val heatmapIntensityAsExpression: Expression?
    /**
     * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level. Default value: 1. Minimum value: 0.
     *
     * Get the HeatmapIntensity property as an Expression
     *
     * Use static method [HeatmapLayer.defaultHeatmapIntensityAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("heatmap-intensity")

  /**
   * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level. Default value: 1. Minimum value: 0.
   *
   * Use static method [HeatmapLayer.defaultHeatmapIntensityAsExpression] to set the default property.
   *
   * @param heatmapIntensity value of heatmapIntensity as Expression
   */
  override fun heatmapIntensity(heatmapIntensity: Expression): HeatmapLayer = apply {
    val propertyValue = PropertyValue("heatmap-intensity", heatmapIntensity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for HeatmapIntensity.
   */
  val heatmapIntensityTransition: StyleTransition?
    /**
     * Get the HeatmapIntensity property transition options
     *
     * Use static method [HeatmapLayer.defaultHeatmapIntensityTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("heatmap-intensity-transition")
    }

  /**
   * Set the HeatmapIntensity property transition options
   *
   * Use static method [HeatmapLayer.defaultHeatmapIntensityTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun heatmapIntensityTransition(options: StyleTransition): HeatmapLayer = apply {
    val propertyValue = PropertyValue("heatmap-intensity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [heatmapIntensityTransition].
   */
  override fun heatmapIntensityTransition(block: StyleTransition.Builder.() -> Unit): HeatmapLayer = apply {
    heatmapIntensityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The global opacity at which the heatmap layer will be drawn. Default value: 1. Value range: [0, 1]
   */
  val heatmapOpacity: Double?
    /**
     * The global opacity at which the heatmap layer will be drawn. Default value: 1. Value range: [0, 1]
     *
     * Use static method [HeatmapLayer.defaultHeatmapOpacity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("heatmap-opacity")
    }

  /**
   * The global opacity at which the heatmap layer will be drawn. Default value: 1. Value range: [0, 1]
   *
   * Use static method [HeatmapLayer.defaultHeatmapOpacity] to set the default property.
   *
   * @param heatmapOpacity value of heatmapOpacity
   */
  override fun heatmapOpacity(heatmapOpacity: Double): HeatmapLayer = apply {
    val propertyValue = PropertyValue("heatmap-opacity", heatmapOpacity)
    setProperty(propertyValue)
  }

  /**
   * The global opacity at which the heatmap layer will be drawn. Default value: 1. Value range: [0, 1]
   *
   * This is an Expression representation of "heatmap-opacity".
   *
   */
  val heatmapOpacityAsExpression: Expression?
    /**
     * The global opacity at which the heatmap layer will be drawn. Default value: 1. Value range: [0, 1]
     *
     * Get the HeatmapOpacity property as an Expression
     *
     * Use static method [HeatmapLayer.defaultHeatmapOpacityAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("heatmap-opacity")

  /**
   * The global opacity at which the heatmap layer will be drawn. Default value: 1. Value range: [0, 1]
   *
   * Use static method [HeatmapLayer.defaultHeatmapOpacityAsExpression] to set the default property.
   *
   * @param heatmapOpacity value of heatmapOpacity as Expression
   */
  override fun heatmapOpacity(heatmapOpacity: Expression): HeatmapLayer = apply {
    val propertyValue = PropertyValue("heatmap-opacity", heatmapOpacity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for HeatmapOpacity.
   */
  val heatmapOpacityTransition: StyleTransition?
    /**
     * Get the HeatmapOpacity property transition options
     *
     * Use static method [HeatmapLayer.defaultHeatmapOpacityTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("heatmap-opacity-transition")
    }

  /**
   * Set the HeatmapOpacity property transition options
   *
   * Use static method [HeatmapLayer.defaultHeatmapOpacityTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun heatmapOpacityTransition(options: StyleTransition): HeatmapLayer = apply {
    val propertyValue = PropertyValue("heatmap-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [heatmapOpacityTransition].
   */
  override fun heatmapOpacityTransition(block: StyleTransition.Builder.() -> Unit): HeatmapLayer = apply {
    heatmapOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius. Default value: 30. Minimum value: 1. The unit of heatmapRadius is in pixels.
   */
  val heatmapRadius: Double?
    /**
     * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius. Default value: 30. Minimum value: 1. The unit of heatmapRadius is in pixels.
     *
     * Use static method [HeatmapLayer.defaultHeatmapRadius] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("heatmap-radius")
    }

  /**
   * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius. Default value: 30. Minimum value: 1. The unit of heatmapRadius is in pixels.
   *
   * Use static method [HeatmapLayer.defaultHeatmapRadius] to set the default property.
   *
   * @param heatmapRadius value of heatmapRadius
   */
  override fun heatmapRadius(heatmapRadius: Double): HeatmapLayer = apply {
    val propertyValue = PropertyValue("heatmap-radius", heatmapRadius)
    setProperty(propertyValue)
  }

  /**
   * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius. Default value: 30. Minimum value: 1. The unit of heatmapRadius is in pixels.
   *
   * This is an Expression representation of "heatmap-radius".
   *
   */
  val heatmapRadiusAsExpression: Expression?
    /**
     * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius. Default value: 30. Minimum value: 1. The unit of heatmapRadius is in pixels.
     *
     * Get the HeatmapRadius property as an Expression
     *
     * Use static method [HeatmapLayer.defaultHeatmapRadiusAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("heatmap-radius")

  /**
   * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius. Default value: 30. Minimum value: 1. The unit of heatmapRadius is in pixels.
   *
   * Use static method [HeatmapLayer.defaultHeatmapRadiusAsExpression] to set the default property.
   *
   * @param heatmapRadius value of heatmapRadius as Expression
   */
  override fun heatmapRadius(heatmapRadius: Expression): HeatmapLayer = apply {
    val propertyValue = PropertyValue("heatmap-radius", heatmapRadius)
    setProperty(propertyValue)
  }

  /**
   * Transition options for HeatmapRadius.
   */
  val heatmapRadiusTransition: StyleTransition?
    /**
     * Get the HeatmapRadius property transition options
     *
     * Use static method [HeatmapLayer.defaultHeatmapRadiusTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("heatmap-radius-transition")
    }

  /**
   * Set the HeatmapRadius property transition options
   *
   * Use static method [HeatmapLayer.defaultHeatmapRadiusTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun heatmapRadiusTransition(options: StyleTransition): HeatmapLayer = apply {
    val propertyValue = PropertyValue("heatmap-radius-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [heatmapRadiusTransition].
   */
  override fun heatmapRadiusTransition(block: StyleTransition.Builder.() -> Unit): HeatmapLayer = apply {
    heatmapRadiusTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering. Default value: 1. Minimum value: 0.
   */
  val heatmapWeight: Double?
    /**
     * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering. Default value: 1. Minimum value: 0.
     *
     * Use static method [HeatmapLayer.defaultHeatmapWeight] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("heatmap-weight")
    }

  /**
   * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering. Default value: 1. Minimum value: 0.
   *
   * Use static method [HeatmapLayer.defaultHeatmapWeight] to set the default property.
   *
   * @param heatmapWeight value of heatmapWeight
   */
  override fun heatmapWeight(heatmapWeight: Double): HeatmapLayer = apply {
    val propertyValue = PropertyValue("heatmap-weight", heatmapWeight)
    setProperty(propertyValue)
  }

  /**
   * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering. Default value: 1. Minimum value: 0.
   *
   * This is an Expression representation of "heatmap-weight".
   *
   */
  val heatmapWeightAsExpression: Expression?
    /**
     * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering. Default value: 1. Minimum value: 0.
     *
     * Get the HeatmapWeight property as an Expression
     *
     * Use static method [HeatmapLayer.defaultHeatmapWeightAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("heatmap-weight")

  /**
   * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering. Default value: 1. Minimum value: 0.
   *
   * Use static method [HeatmapLayer.defaultHeatmapWeightAsExpression] to set the default property.
   *
   * @param heatmapWeight value of heatmapWeight as Expression
   */
  override fun heatmapWeight(heatmapWeight: Expression): HeatmapLayer = apply {
    val propertyValue = PropertyValue("heatmap-weight", heatmapWeight)
    setProperty(propertyValue)
  }

  /**
   * Get the type of this layer
   *
   * @return Type of the layer as [String]
   */
  override fun getType(): String {
    return "heatmap"
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
        StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "visibility").silentUnwrap<String>()?.let {
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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "minzoom").silentUnwrap()

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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "maxzoom").silentUnwrap()

    /**
     * Defines the color of each pixel based on its density value in a heatmap. Should be an expression that uses `["heatmap-density"]` as input. Default value: ["interpolate",["linear"],["heatmap-density"],0,"rgba(0, 0, 255, 0)",0.1,"royalblue",0.3,"cyan",0.5,"lime",0.7,"yellow",1,"red"].
     */
    val defaultHeatmapColor: Expression?
      /**
       * Defines the color of each pixel based on its density value in a heatmap. Should be an expression that uses `["heatmap-density"]` as input. Default value: ["interpolate",["linear"],["heatmap-density"],0,"rgba(0, 0, 255, 0)",0.1,"royalblue",0.3,"cyan",0.5,"lime",0.7,"yellow",1,"red"].
       *
       * Get the default value of HeatmapColor property
       *
       * @return Expression
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-color").silentUnwrap()
      }

    /**
     * Default color theme for [heatmapColor].
     */
    @MapboxExperimental
    val defaultHeatmapColorUseTheme: String?
      /**
       * Get default value of the HeatmapColor property as String
       *
       * @return Expression
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-color-use-theme").silentUnwrap()

    /**
     * Default color theme for [heatmapColor].
     */
    @MapboxExperimental
    val defaultHeatmapColorUseThemeAsExpression: Expression?
      /**
       * Get default value of the HeatmapColor property as Expression
       *
       * @return Expression
       */
      get() {
        return StyleManager
          .getStyleLayerPropertyDefaultValue("heatmap", "heatmap-color-use-theme")
          .silentUnwrap<Expression>() ?: defaultHeatmapColorUseTheme?.let { Expression.literal(it) }
      }

    /**
     * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level. Default value: 1. Minimum value: 0.
     */
    val defaultHeatmapIntensity: Double?
      /**
       * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level. Default value: 1. Minimum value: 0.
       *
       * Get the default value of HeatmapIntensity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-intensity").silentUnwrap()
      }

    /**
     * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level. Default value: 1. Minimum value: 0.
     *
     * This is an Expression representation of "heatmap-intensity".
     *
     */
    val defaultHeatmapIntensityAsExpression: Expression?
      /**
       * Get default value of the HeatmapIntensity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-intensity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultHeatmapIntensity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for HeatmapIntensity.
     */
    val defaultHeatmapIntensityTransition: StyleTransition?
      /**
       * Get the HeatmapIntensity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-intensity-transition").silentUnwrap()

    /**
     * The global opacity at which the heatmap layer will be drawn. Default value: 1. Value range: [0, 1]
     */
    val defaultHeatmapOpacity: Double?
      /**
       * The global opacity at which the heatmap layer will be drawn. Default value: 1. Value range: [0, 1]
       *
       * Get the default value of HeatmapOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-opacity").silentUnwrap()
      }

    /**
     * The global opacity at which the heatmap layer will be drawn. Default value: 1. Value range: [0, 1]
     *
     * This is an Expression representation of "heatmap-opacity".
     *
     */
    val defaultHeatmapOpacityAsExpression: Expression?
      /**
       * Get default value of the HeatmapOpacity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-opacity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultHeatmapOpacity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for HeatmapOpacity.
     */
    val defaultHeatmapOpacityTransition: StyleTransition?
      /**
       * Get the HeatmapOpacity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-opacity-transition").silentUnwrap()

    /**
     * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius. Default value: 30. Minimum value: 1. The unit of heatmapRadius is in pixels.
     */
    val defaultHeatmapRadius: Double?
      /**
       * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius. Default value: 30. Minimum value: 1. The unit of heatmapRadius is in pixels.
       *
       * Get the default value of HeatmapRadius property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-radius").silentUnwrap()
      }

    /**
     * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius. Default value: 30. Minimum value: 1. The unit of heatmapRadius is in pixels.
     *
     * This is an Expression representation of "heatmap-radius".
     *
     */
    val defaultHeatmapRadiusAsExpression: Expression?
      /**
       * Get default value of the HeatmapRadius property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-radius").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultHeatmapRadius?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for HeatmapRadius.
     */
    val defaultHeatmapRadiusTransition: StyleTransition?
      /**
       * Get the HeatmapRadius property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-radius-transition").silentUnwrap()

    /**
     * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering. Default value: 1. Minimum value: 0.
     */
    val defaultHeatmapWeight: Double?
      /**
       * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering. Default value: 1. Minimum value: 0.
       *
       * Get the default value of HeatmapWeight property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-weight").silentUnwrap()
      }

    /**
     * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering. Default value: 1. Minimum value: 0.
     *
     * This is an Expression representation of "heatmap-weight".
     *
     */
    val defaultHeatmapWeightAsExpression: Expression?
      /**
       * Get default value of the HeatmapWeight property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-weight").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultHeatmapWeight?.let {
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
interface HeatmapLayerDsl {
  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  fun sourceLayer(sourceLayer: String): HeatmapLayer

  /**
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   *
   * @param slot value of slot. Setting it to empty string removes the slot.
   */
  fun slot(slot: String): HeatmapLayer

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
  fun filter(filter: Expression): HeatmapLayer

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): HeatmapLayer

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility as Expression
   */
  fun visibility(visibility: Expression): HeatmapLayer

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param minZoom value of minzoom
   */
  fun minZoom(minZoom: Double): HeatmapLayer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param maxZoom value of maxzoom
   */
  fun maxZoom(maxZoom: Double): HeatmapLayer

  // Property getters and setters

  /**
   * Defines the color of each pixel based on its density value in a heatmap. Should be an expression that uses `["heatmap-density"]` as input. Default value: ["interpolate",["linear"],["heatmap-density"],0,"rgba(0, 0, 255, 0)",0.1,"royalblue",0.3,"cyan",0.5,"lime",0.7,"yellow",1,"red"].
   *
   * @param heatmapColor value of heatmapColor
   */
  fun heatmapColor(heatmapColor: Expression): HeatmapLayer

  /**
   * Set the heatmapColorUseTheme as String for [heatmapColor].
   *
   * @param heatmapColorUseTheme overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  fun heatmapColorUseTheme(heatmapColorUseTheme: String): HeatmapLayer

  /**
   * Set the heatmapColorUseTheme as Expression for [heatmapColor].
   *
   * @param heatmapColorUseTheme overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  fun heatmapColorUseTheme(heatmapColorUseTheme: Expression): HeatmapLayer

  /**
   * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level. Default value: 1. Minimum value: 0.
   *
   * @param heatmapIntensity value of heatmapIntensity
   */
  fun heatmapIntensity(heatmapIntensity: Double = 1.0): HeatmapLayer

  /**
   * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level. Default value: 1. Minimum value: 0.
   *
   * @param heatmapIntensity value of heatmapIntensity as Expression
   */
  fun heatmapIntensity(heatmapIntensity: Expression): HeatmapLayer

  /**
   * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level. Default value: 1. Minimum value: 0.
   *
   * Set the HeatmapIntensity property transition options
   *
   * @param options transition options for Double
   */
  fun heatmapIntensityTransition(options: StyleTransition): HeatmapLayer

  /**
   * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level. Default value: 1. Minimum value: 0.
   *
   * DSL for [heatmapIntensityTransition].
   */
  fun heatmapIntensityTransition(block: StyleTransition.Builder.() -> Unit): HeatmapLayer

  /**
   * The global opacity at which the heatmap layer will be drawn. Default value: 1. Value range: [0, 1]
   *
   * @param heatmapOpacity value of heatmapOpacity
   */
  fun heatmapOpacity(heatmapOpacity: Double = 1.0): HeatmapLayer

  /**
   * The global opacity at which the heatmap layer will be drawn. Default value: 1. Value range: [0, 1]
   *
   * @param heatmapOpacity value of heatmapOpacity as Expression
   */
  fun heatmapOpacity(heatmapOpacity: Expression): HeatmapLayer

  /**
   * The global opacity at which the heatmap layer will be drawn. Default value: 1. Value range: [0, 1]
   *
   * Set the HeatmapOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun heatmapOpacityTransition(options: StyleTransition): HeatmapLayer

  /**
   * The global opacity at which the heatmap layer will be drawn. Default value: 1. Value range: [0, 1]
   *
   * DSL for [heatmapOpacityTransition].
   */
  fun heatmapOpacityTransition(block: StyleTransition.Builder.() -> Unit): HeatmapLayer

  /**
   * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius. Default value: 30. Minimum value: 1. The unit of heatmapRadius is in pixels.
   *
   * @param heatmapRadius value of heatmapRadius
   */
  fun heatmapRadius(heatmapRadius: Double = 30.0): HeatmapLayer

  /**
   * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius. Default value: 30. Minimum value: 1. The unit of heatmapRadius is in pixels.
   *
   * @param heatmapRadius value of heatmapRadius as Expression
   */
  fun heatmapRadius(heatmapRadius: Expression): HeatmapLayer

  /**
   * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius. Default value: 30. Minimum value: 1. The unit of heatmapRadius is in pixels.
   *
   * Set the HeatmapRadius property transition options
   *
   * @param options transition options for Double
   */
  fun heatmapRadiusTransition(options: StyleTransition): HeatmapLayer

  /**
   * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius. Default value: 30. Minimum value: 1. The unit of heatmapRadius is in pixels.
   *
   * DSL for [heatmapRadiusTransition].
   */
  fun heatmapRadiusTransition(block: StyleTransition.Builder.() -> Unit): HeatmapLayer

  /**
   * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering. Default value: 1. Minimum value: 0.
   *
   * @param heatmapWeight value of heatmapWeight
   */
  fun heatmapWeight(heatmapWeight: Double = 1.0): HeatmapLayer

  /**
   * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering. Default value: 1. Minimum value: 0.
   *
   * @param heatmapWeight value of heatmapWeight as Expression
   */
  fun heatmapWeight(heatmapWeight: Expression): HeatmapLayer
}

/**
 * DSL function for creating a [HeatmapLayer].
 */
fun heatmapLayer(layerId: String, sourceId: String, block: HeatmapLayerDsl.() -> Unit): HeatmapLayer = HeatmapLayer(layerId, sourceId).apply(block)

// End of generated file.