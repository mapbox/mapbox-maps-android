// This file is generated.

package com.mapbox.maps.extension.style.layers.generated

import androidx.annotation.UiThread
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
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-heatmap)
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
  override fun sourceLayer(sourceLayer: String) = apply {
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
  override fun filter(filter: Expression) = apply {
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
     * Use static method [HeatmapLayer.defaultVisibility] to get the default property value.
     *
     * @return VISIBILITY
     */
    get() {
      val property: String? = getPropertyValue("visibility")
      property?.let {
        return Visibility.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Whether this layer is displayed.
   *
   * Use static method [HeatmapLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Visibility) = apply {
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
   * Use static method [HeatmapLayer.defaultMinZoom] to get the default property value.
   *
   * @param value value of minzoom
   */
  override fun minZoom(minZoom: Double) = apply {
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
   * Use static method [HeatmapLayer.defaultMaxZoom] to get the default property value.
   *
   * @param value value of maxzoom
   */
  override fun maxZoom(maxZoom: Double) = apply {
    val param = PropertyValue("maxzoom", maxZoom)
    setProperty(param)
  }

  // Property getters and setters

  /**
   * Defines the color of each pixel based on its density value in a heatmap.  Should be an expression that uses `["heatmap-density"]` as input.
   */
  val heatmapColor: Expression?
    /**
     * Defines the color of each pixel based on its density value in a heatmap.  Should be an expression that uses `["heatmap-density"]` as input.
     *
     * Use static method [HeatmapLayer.defaultHeatmapColor] to get the default property.
     *
     * @return Expression
     */
    get() {
      return getPropertyValue("heatmap-color")
    }

  /**
   * Defines the color of each pixel based on its density value in a heatmap.  Should be an expression that uses `["heatmap-density"]` as input.
   *
   * Use static method [HeatmapLayer.defaultHeatmapColor] to set the default property.
   *
   * @param heatmapColor value of heatmapColor
   */
  override fun heatmapColor(heatmapColor: Expression) = apply {
    val propertyValue = PropertyValue("heatmap-color", heatmapColor)
    setProperty(propertyValue)
  }

  /**
   * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level.
   */
  val heatmapIntensity: Double?
    /**
     * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level.
     *
     * Use static method [HeatmapLayer.defaultHeatmapIntensity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("heatmap-intensity")
    }

  /**
   * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level.
   *
   * Use static method [HeatmapLayer.defaultHeatmapIntensity] to set the default property.
   *
   * @param heatmapIntensity value of heatmapIntensity
   */
  override fun heatmapIntensity(heatmapIntensity: Double) = apply {
    val propertyValue = PropertyValue("heatmap-intensity", heatmapIntensity)
    setProperty(propertyValue)
  }

  /**
   * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level.
   *
   * This is an Expression representation of "heatmap-intensity".
   *
   */
  val heatmapIntensityAsExpression: Expression?
    /**
     * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level.
     *
     * Get the HeatmapIntensity property as an Expression
     *
     * Use static method [HeatmapLayer.defaultHeatmapIntensityAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("heatmap-intensity")?.let {
        return it
      }
      heatmapIntensity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level.
   *
   * Use static method [HeatmapLayer.defaultHeatmapIntensityAsExpression] to set the default property.
   *
   * @param heatmapIntensity value of heatmapIntensity as Expression
   */
  override fun heatmapIntensity(heatmapIntensity: Expression) = apply {
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
  override fun heatmapIntensityTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("heatmap-intensity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [heatmapIntensityTransition].
   */
  override fun heatmapIntensityTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    heatmapIntensityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The global opacity at which the heatmap layer will be drawn.
   */
  val heatmapOpacity: Double?
    /**
     * The global opacity at which the heatmap layer will be drawn.
     *
     * Use static method [HeatmapLayer.defaultHeatmapOpacity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("heatmap-opacity")
    }

  /**
   * The global opacity at which the heatmap layer will be drawn.
   *
   * Use static method [HeatmapLayer.defaultHeatmapOpacity] to set the default property.
   *
   * @param heatmapOpacity value of heatmapOpacity
   */
  override fun heatmapOpacity(heatmapOpacity: Double) = apply {
    val propertyValue = PropertyValue("heatmap-opacity", heatmapOpacity)
    setProperty(propertyValue)
  }

  /**
   * The global opacity at which the heatmap layer will be drawn.
   *
   * This is an Expression representation of "heatmap-opacity".
   *
   */
  val heatmapOpacityAsExpression: Expression?
    /**
     * The global opacity at which the heatmap layer will be drawn.
     *
     * Get the HeatmapOpacity property as an Expression
     *
     * Use static method [HeatmapLayer.defaultHeatmapOpacityAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("heatmap-opacity")?.let {
        return it
      }
      heatmapOpacity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The global opacity at which the heatmap layer will be drawn.
   *
   * Use static method [HeatmapLayer.defaultHeatmapOpacityAsExpression] to set the default property.
   *
   * @param heatmapOpacity value of heatmapOpacity as Expression
   */
  override fun heatmapOpacity(heatmapOpacity: Expression) = apply {
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
  override fun heatmapOpacityTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("heatmap-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [heatmapOpacityTransition].
   */
  override fun heatmapOpacityTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    heatmapOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius.
   */
  val heatmapRadius: Double?
    /**
     * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius.
     *
     * Use static method [HeatmapLayer.defaultHeatmapRadius] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("heatmap-radius")
    }

  /**
   * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius.
   *
   * Use static method [HeatmapLayer.defaultHeatmapRadius] to set the default property.
   *
   * @param heatmapRadius value of heatmapRadius
   */
  override fun heatmapRadius(heatmapRadius: Double) = apply {
    val propertyValue = PropertyValue("heatmap-radius", heatmapRadius)
    setProperty(propertyValue)
  }

  /**
   * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius.
   *
   * This is an Expression representation of "heatmap-radius".
   *
   */
  val heatmapRadiusAsExpression: Expression?
    /**
     * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius.
     *
     * Get the HeatmapRadius property as an Expression
     *
     * Use static method [HeatmapLayer.defaultHeatmapRadiusAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("heatmap-radius")?.let {
        return it
      }
      heatmapRadius?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius.
   *
   * Use static method [HeatmapLayer.defaultHeatmapRadiusAsExpression] to set the default property.
   *
   * @param heatmapRadius value of heatmapRadius as Expression
   */
  override fun heatmapRadius(heatmapRadius: Expression) = apply {
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
  override fun heatmapRadiusTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("heatmap-radius-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [heatmapRadiusTransition].
   */
  override fun heatmapRadiusTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    heatmapRadiusTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering.
   */
  val heatmapWeight: Double?
    /**
     * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering.
     *
     * Use static method [HeatmapLayer.defaultHeatmapWeight] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("heatmap-weight")
    }

  /**
   * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering.
   *
   * Use static method [HeatmapLayer.defaultHeatmapWeight] to set the default property.
   *
   * @param heatmapWeight value of heatmapWeight
   */
  override fun heatmapWeight(heatmapWeight: Double) = apply {
    val propertyValue = PropertyValue("heatmap-weight", heatmapWeight)
    setProperty(propertyValue)
  }

  /**
   * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering.
   *
   * This is an Expression representation of "heatmap-weight".
   *
   */
  val heatmapWeightAsExpression: Expression?
    /**
     * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering.
     *
     * Get the HeatmapWeight property as an Expression
     *
     * Use static method [HeatmapLayer.defaultHeatmapWeightAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("heatmap-weight")?.let {
        return it
      }
      heatmapWeight?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering.
   *
   * Use static method [HeatmapLayer.defaultHeatmapWeightAsExpression] to set the default property.
   *
   * @param heatmapWeight value of heatmapWeight as Expression
   */
  override fun heatmapWeight(heatmapWeight: Expression) = apply {
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
          return Visibility.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
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
     * Defines the color of each pixel based on its density value in a heatmap.  Should be an expression that uses `["heatmap-density"]` as input.
     */
    val defaultHeatmapColor: Expression?
      /**
       * Defines the color of each pixel based on its density value in a heatmap.  Should be an expression that uses `["heatmap-density"]` as input.
       *
       * Get the default value of HeatmapColor property
       *
       * @return Expression
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-color").silentUnwrap()
      }

    /**
     * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level.
     */
    val defaultHeatmapIntensity: Double?
      /**
       * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level.
       *
       * Get the default value of HeatmapIntensity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-intensity").silentUnwrap()
      }

    /**
     * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level.
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
     * The global opacity at which the heatmap layer will be drawn.
     */
    val defaultHeatmapOpacity: Double?
      /**
       * The global opacity at which the heatmap layer will be drawn.
       *
       * Get the default value of HeatmapOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-opacity").silentUnwrap()
      }

    /**
     * The global opacity at which the heatmap layer will be drawn.
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
     * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius.
     */
    val defaultHeatmapRadius: Double?
      /**
       * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius.
       *
       * Get the default value of HeatmapRadius property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-radius").silentUnwrap()
      }

    /**
     * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius.
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
     * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering.
     */
    val defaultHeatmapWeight: Double?
      /**
       * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering.
       *
       * Get the default value of HeatmapWeight property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-weight").silentUnwrap()
      }

    /**
     * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering.
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
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param value value of minzoom
   */
  fun minZoom(minZoom: Double): HeatmapLayer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param value value of maxzoom
   */
  fun maxZoom(maxZoom: Double): HeatmapLayer

  // Property getters and setters

  /**
   * Defines the color of each pixel based on its density value in a heatmap.  Should be an expression that uses `["heatmap-density"]` as input.
   *
   * @param heatmapColor value of heatmapColor
   */
  fun heatmapColor(heatmapColor: Expression): HeatmapLayer

  /**
   * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level.
   *
   * @param heatmapIntensity value of heatmapIntensity
   */
  fun heatmapIntensity(heatmapIntensity: Double = 1.0): HeatmapLayer

  /**
   * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level.
   *
   * @param heatmapIntensity value of heatmapIntensity as Expression
   */
  fun heatmapIntensity(heatmapIntensity: Expression): HeatmapLayer

  /**
   * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level.
   *
   * Set the HeatmapIntensity property transition options
   *
   * @param options transition options for Double
   */
  fun heatmapIntensityTransition(options: StyleTransition): HeatmapLayer

  /**
   * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level.
   *
   * DSL for [heatmapIntensityTransition].
   */
  fun heatmapIntensityTransition(block: StyleTransition.Builder.() -> Unit): HeatmapLayer

  /**
   * The global opacity at which the heatmap layer will be drawn.
   *
   * @param heatmapOpacity value of heatmapOpacity
   */
  fun heatmapOpacity(heatmapOpacity: Double = 1.0): HeatmapLayer

  /**
   * The global opacity at which the heatmap layer will be drawn.
   *
   * @param heatmapOpacity value of heatmapOpacity as Expression
   */
  fun heatmapOpacity(heatmapOpacity: Expression): HeatmapLayer

  /**
   * The global opacity at which the heatmap layer will be drawn.
   *
   * Set the HeatmapOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun heatmapOpacityTransition(options: StyleTransition): HeatmapLayer

  /**
   * The global opacity at which the heatmap layer will be drawn.
   *
   * DSL for [heatmapOpacityTransition].
   */
  fun heatmapOpacityTransition(block: StyleTransition.Builder.() -> Unit): HeatmapLayer

  /**
   * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius.
   *
   * @param heatmapRadius value of heatmapRadius
   */
  fun heatmapRadius(heatmapRadius: Double = 30.0): HeatmapLayer

  /**
   * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius.
   *
   * @param heatmapRadius value of heatmapRadius as Expression
   */
  fun heatmapRadius(heatmapRadius: Expression): HeatmapLayer

  /**
   * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius.
   *
   * Set the HeatmapRadius property transition options
   *
   * @param options transition options for Double
   */
  fun heatmapRadiusTransition(options: StyleTransition): HeatmapLayer

  /**
   * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius.
   *
   * DSL for [heatmapRadiusTransition].
   */
  fun heatmapRadiusTransition(block: StyleTransition.Builder.() -> Unit): HeatmapLayer

  /**
   * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering.
   *
   * @param heatmapWeight value of heatmapWeight
   */
  fun heatmapWeight(heatmapWeight: Double = 1.0): HeatmapLayer

  /**
   * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering.
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