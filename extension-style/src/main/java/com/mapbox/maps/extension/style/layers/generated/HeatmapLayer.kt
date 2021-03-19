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
   * Set the sourceLayer property
   *
   * @param sourceLayer value of sourceLayer
   */
  override fun sourceLayer(sourceLayer: String) = apply {
    val param = PropertyValue("source-layer", sourceLayer)
    setProperty(param)
  }

  /**
   * Source layer.
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
   * Set the filter property
   *
   * @param filter the expression filter to set
   */
  override fun filter(filter: Expression) = apply {
    val propertyValue = PropertyValue("filter", filter)
    setProperty(propertyValue)
  }

  /**
   * A expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `feature-state` expression is not supported in filter expressions.
   */
  val filter: Expression?
    /**
     * Get the filter property
     *
     * @return filter
     */
    get() = getPropertyValue<Expression>("filter")

  /**
   * Visibility of the layer.
   */
  override val visibility: Visibility?
    /**
     * Get the Visibility property
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
   * Set the Visibility property
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
     * @return minzoom
     */
    get() {
      return getPropertyValue("minzoom")
    }

  /**
   * Set the minzoom property
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
     * @return maxzoom
     */
    get() {
      return getPropertyValue("maxzoom")
    }

  /**
   * Set the maxzoom property
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
     * Get the HeatmapColor property
     *
     * @return Expression
     */
    get() {
      return getPropertyValue("heatmap-color")
    }

  /**
   * Set the HeatmapColor property
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
     * Get the HeatmapIntensity property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("heatmap-intensity")
    }

  /**
   * Set the HeatmapIntensity property
   *
   * @param heatmapIntensity value of heatmapIntensity
   */
  override fun heatmapIntensity(heatmapIntensity: Double) = apply {
    val propertyValue = PropertyValue("heatmap-intensity", heatmapIntensity)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "heatmap-intensity".
   *
   * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level.
   */
  val heatmapIntensityAsExpression: Expression?
    /**
     * Get the HeatmapIntensity property as an Expression
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
   * Set the HeatmapIntensity property
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
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("heatmap-intensity-transition")
    }

  /**
   * Set the HeatmapIntensity property transition options
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
     * Get the HeatmapOpacity property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("heatmap-opacity")
    }

  /**
   * Set the HeatmapOpacity property
   *
   * @param heatmapOpacity value of heatmapOpacity
   */
  override fun heatmapOpacity(heatmapOpacity: Double) = apply {
    val propertyValue = PropertyValue("heatmap-opacity", heatmapOpacity)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "heatmap-opacity".
   *
   * The global opacity at which the heatmap layer will be drawn.
   */
  val heatmapOpacityAsExpression: Expression?
    /**
     * Get the HeatmapOpacity property as an Expression
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
   * Set the HeatmapOpacity property
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
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("heatmap-opacity-transition")
    }

  /**
   * Set the HeatmapOpacity property transition options
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
   * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed.
   */
  val heatmapRadius: Double?
    /**
     * Get the HeatmapRadius property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("heatmap-radius")
    }

  /**
   * Set the HeatmapRadius property
   *
   * @param heatmapRadius value of heatmapRadius
   */
  override fun heatmapRadius(heatmapRadius: Double) = apply {
    val propertyValue = PropertyValue("heatmap-radius", heatmapRadius)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "heatmap-radius".
   *
   * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed.
   */
  val heatmapRadiusAsExpression: Expression?
    /**
     * Get the HeatmapRadius property as an Expression
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
   * Set the HeatmapRadius property
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
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("heatmap-radius-transition")
    }

  /**
   * Set the HeatmapRadius property transition options
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
     * Get the HeatmapWeight property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("heatmap-weight")
    }

  /**
   * Set the HeatmapWeight property
   *
   * @param heatmapWeight value of heatmapWeight
   */
  override fun heatmapWeight(heatmapWeight: Double) = apply {
    val propertyValue = PropertyValue("heatmap-weight", heatmapWeight)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "heatmap-weight".
   *
   * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering.
   */
  val heatmapWeightAsExpression: Expression?
    /**
     * Get the HeatmapWeight property as an Expression
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
   * Set the HeatmapWeight property
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
       * Get the default value of HeatmapIntensity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-intensity").silentUnwrap()
      }

    /**
     * This is an Expression representation of "heatmap-intensity".
     *
     * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level.
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
       * Get the default value of HeatmapOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-opacity").silentUnwrap()
      }

    /**
     * This is an Expression representation of "heatmap-opacity".
     *
     * The global opacity at which the heatmap layer will be drawn.
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
     * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed.
     */
    val defaultHeatmapRadius: Double?
      /**
       * Get the default value of HeatmapRadius property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-radius").silentUnwrap()
      }

    /**
     * This is an Expression representation of "heatmap-radius".
     *
     * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed.
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
       * Get the default value of HeatmapWeight property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("heatmap", "heatmap-weight").silentUnwrap()
      }

    /**
     * This is an Expression representation of "heatmap-weight".
     *
     * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering.
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
   * Set the sourceLayer property
   *
   * @param sourceLayer value of sourceLayer
   */
  fun sourceLayer(sourceLayer: String): HeatmapLayer

  /**
   * Set the filter property
   *
   * @param filter the expression filter to set
   */
  fun filter(filter: Expression): HeatmapLayer

  /**
   * Set the Visibility property
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): HeatmapLayer

  /**
   * Set the minzoom property
   *
   * @param value value of minzoom
   */
  fun minZoom(minZoom: Double): HeatmapLayer

  /**
   * Set the maxzoom property
   *
   * @param value value of maxzoom
   */
  fun maxZoom(maxZoom: Double): HeatmapLayer

  // Property getters and setters

  /**
   * Set the HeatmapColor property
   *
   * @param heatmapColor value of heatmapColor
   */
  fun heatmapColor(heatmapColor: Expression): HeatmapLayer

  /**
   * Set the HeatmapIntensity property
   *
   * @param heatmapIntensity value of heatmapIntensity
   */
  fun heatmapIntensity(heatmapIntensity: Double = 1.0): HeatmapLayer

  /**
   * Set the HeatmapIntensity property
   *
   * @param heatmapIntensity value of heatmapIntensity as Expression
   */
  fun heatmapIntensity(heatmapIntensity: Expression): HeatmapLayer

  /**
   * Set the HeatmapIntensity property transition options
   *
   * @param options transition options for Double
   */
  fun heatmapIntensityTransition(options: StyleTransition): HeatmapLayer

  /**
   * DSL for [heatmapIntensityTransition].
   */
  fun heatmapIntensityTransition(block: StyleTransition.Builder.() -> Unit): HeatmapLayer

  /**
   * Set the HeatmapOpacity property
   *
   * @param heatmapOpacity value of heatmapOpacity
   */
  fun heatmapOpacity(heatmapOpacity: Double = 1.0): HeatmapLayer

  /**
   * Set the HeatmapOpacity property
   *
   * @param heatmapOpacity value of heatmapOpacity as Expression
   */
  fun heatmapOpacity(heatmapOpacity: Expression): HeatmapLayer

  /**
   * Set the HeatmapOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun heatmapOpacityTransition(options: StyleTransition): HeatmapLayer

  /**
   * DSL for [heatmapOpacityTransition].
   */
  fun heatmapOpacityTransition(block: StyleTransition.Builder.() -> Unit): HeatmapLayer

  /**
   * Set the HeatmapRadius property
   *
   * @param heatmapRadius value of heatmapRadius
   */
  fun heatmapRadius(heatmapRadius: Double = 30.0): HeatmapLayer

  /**
   * Set the HeatmapRadius property
   *
   * @param heatmapRadius value of heatmapRadius as Expression
   */
  fun heatmapRadius(heatmapRadius: Expression): HeatmapLayer

  /**
   * Set the HeatmapRadius property transition options
   *
   * @param options transition options for Double
   */
  fun heatmapRadiusTransition(options: StyleTransition): HeatmapLayer

  /**
   * DSL for [heatmapRadiusTransition].
   */
  fun heatmapRadiusTransition(block: StyleTransition.Builder.() -> Unit): HeatmapLayer

  /**
   * Set the HeatmapWeight property
   *
   * @param heatmapWeight value of heatmapWeight
   */
  fun heatmapWeight(heatmapWeight: Double = 1.0): HeatmapLayer

  /**
   * Set the HeatmapWeight property
   *
   * @param heatmapWeight value of heatmapWeight as Expression
   */
  fun heatmapWeight(heatmapWeight: Expression): HeatmapLayer
}

/**
 * DSL function for [HeatmapLayer].
 */
fun heatmapLayer(layerId: String, sourceId: String, block: HeatmapLayerDsl.() -> Unit): HeatmapLayer = HeatmapLayer(layerId, sourceId).apply(block)

// End of generated file.