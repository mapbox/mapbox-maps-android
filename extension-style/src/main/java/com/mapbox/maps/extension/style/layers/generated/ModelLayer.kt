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
 * Model layer
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
   * The opacity of the model layer.
   */
  val modelOpacity: Double?
    /**
     * Get the ModelOpacity property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("model-opacity")
    }

  /**
   * Set the ModelOpacity property
   *
   * @param modelOpacity value of modelOpacity
   */
  override fun modelOpacity(modelOpacity: Double) = apply {
    val propertyValue = PropertyValue("model-opacity", modelOpacity)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "model-opacity".
   *
   * The opacity of the model layer.
   */
  val modelOpacityAsExpression: Expression?
    /**
     * Get the ModelOpacity property as an Expression
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
   * Set the ModelOpacity property
   *
   * @param modelOpacity value of modelOpacity as Expression
   */
  override fun modelOpacity(modelOpacity: Expression) = apply {
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
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("model-opacity-transition")
    }

  /**
   * Set the ModelOpacity property transition options
   *
   * @param options transition options for Double
   */
  override fun modelOpacityTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("model-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelOpacityTransition].
   */
  override fun modelOpacityTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    modelOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The rotation of the model in euler angles [lon, lat, z].
   */
  val modelRotation: List<Double>?
    /**
     * Get the ModelRotation property
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("model-rotation")
    }

  /**
   * Set the ModelRotation property
   *
   * @param modelRotation value of modelRotation
   */
  override fun modelRotation(modelRotation: List<Double>) = apply {
    val propertyValue = PropertyValue("model-rotation", modelRotation)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "model-rotation".
   *
   * The rotation of the model in euler angles [lon, lat, z].
   */
  val modelRotationAsExpression: Expression?
    /**
     * Get the ModelRotation property as an Expression
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
   * Set the ModelRotation property
   *
   * @param modelRotation value of modelRotation as Expression
   */
  override fun modelRotation(modelRotation: Expression) = apply {
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
     * @return transition options for List<Double>
     */
    get() {
      return getPropertyValue("model-rotation-transition")
    }

  /**
   * Set the ModelRotation property transition options
   *
   * @param options transition options for List<Double>
   */
  override fun modelRotationTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("model-rotation-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [modelRotationTransition].
   */
  override fun modelRotationTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    modelRotationTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The scale of the model.
   */
  val modelScale: List<Double>?
    /**
     * Get the ModelScale property
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("model-scale")
    }

  /**
   * Set the ModelScale property
   *
   * @param modelScale value of modelScale
   */
  override fun modelScale(modelScale: List<Double>) = apply {
    val propertyValue = PropertyValue("model-scale", modelScale)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "model-scale".
   *
   * The scale of the model.
   */
  val modelScaleAsExpression: Expression?
    /**
     * Get the ModelScale property as an Expression
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
   * Set the ModelScale property
   *
   * @param modelScale value of modelScale as Expression
   */
  override fun modelScale(modelScale: Expression) = apply {
    val propertyValue = PropertyValue("model-scale", modelScale)
    setProperty(propertyValue)
  }

  /**
   * The translation of the model [lon, lat, z]
   */
  val modelTranslation: List<Double>?
    /**
     * Get the ModelTranslation property
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("model-translation")
    }

  /**
   * Set the ModelTranslation property
   *
   * @param modelTranslation value of modelTranslation
   */
  override fun modelTranslation(modelTranslation: List<Double>) = apply {
    val propertyValue = PropertyValue("model-translation", modelTranslation)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "model-translation".
   *
   * The translation of the model [lon, lat, z]
   */
  val modelTranslationAsExpression: Expression?
    /**
     * Get the ModelTranslation property as an Expression
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
   * Set the ModelTranslation property
   *
   * @param modelTranslation value of modelTranslation as Expression
   */
  override fun modelTranslation(modelTranslation: Expression) = apply {
    val propertyValue = PropertyValue("model-translation", modelTranslation)
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
     * The opacity of the model layer.
     */
    val defaultModelOpacity: Double?
      /**
       * Get the default value of ModelOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-opacity").silentUnwrap()
      }

    /**
     * This is an Expression representation of "model-opacity".
     *
     * The opacity of the model layer.
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
     * The rotation of the model in euler angles [lon, lat, z].
     */
    val defaultModelRotation: List<Double>?
      /**
       * Get the default value of ModelRotation property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-rotation").silentUnwrap()
      }

    /**
     * This is an Expression representation of "model-rotation".
     *
     * The rotation of the model in euler angles [lon, lat, z].
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
     * The scale of the model.
     */
    val defaultModelScale: List<Double>?
      /**
       * Get the default value of ModelScale property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-scale").silentUnwrap()
      }

    /**
     * This is an Expression representation of "model-scale".
     *
     * The scale of the model.
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
     * The translation of the model [lon, lat, z]
     */
    val defaultModelTranslation: List<Double>?
      /**
       * Get the default value of ModelTranslation property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("model", "model-translation").silentUnwrap()
      }

    /**
     * This is an Expression representation of "model-translation".
     *
     * The translation of the model [lon, lat, z]
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
   * Set the sourceLayer property
   *
   * @param sourceLayer value of sourceLayer
   */
  fun sourceLayer(sourceLayer: String): ModelLayer

  /**
   * Set the filter property
   *
   * @param filter the expression filter to set
   */
  fun filter(filter: Expression): ModelLayer

  /**
   * Set the Visibility property
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): ModelLayer

  /**
   * Set the minzoom property
   *
   * @param value value of minzoom
   */
  fun minZoom(minZoom: Double): ModelLayer

  /**
   * Set the maxzoom property
   *
   * @param value value of maxzoom
   */
  fun maxZoom(maxZoom: Double): ModelLayer

  // Property getters and setters

  /**
   * Set the ModelOpacity property
   *
   * @param modelOpacity value of modelOpacity
   */
  fun modelOpacity(modelOpacity: Double = 1.0): ModelLayer

  /**
   * Set the ModelOpacity property
   *
   * @param modelOpacity value of modelOpacity as Expression
   */
  fun modelOpacity(modelOpacity: Expression): ModelLayer

  /**
   * Set the ModelOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun modelOpacityTransition(options: StyleTransition): ModelLayer

  /**
   * DSL for [modelOpacityTransition].
   */
  fun modelOpacityTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * Set the ModelRotation property
   *
   * @param modelRotation value of modelRotation
   */
  fun modelRotation(modelRotation: List<Double> = listOf(0.0, 0.0, 0.0)): ModelLayer

  /**
   * Set the ModelRotation property
   *
   * @param modelRotation value of modelRotation as Expression
   */
  fun modelRotation(modelRotation: Expression): ModelLayer

  /**
   * Set the ModelRotation property transition options
   *
   * @param options transition options for List<Double>
   */
  fun modelRotationTransition(options: StyleTransition): ModelLayer

  /**
   * DSL for [modelRotationTransition].
   */
  fun modelRotationTransition(block: StyleTransition.Builder.() -> Unit): ModelLayer

  /**
   * Set the ModelScale property
   *
   * @param modelScale value of modelScale
   */
  fun modelScale(modelScale: List<Double> = listOf(1.0, 1.0, 1.0)): ModelLayer

  /**
   * Set the ModelScale property
   *
   * @param modelScale value of modelScale as Expression
   */
  fun modelScale(modelScale: Expression): ModelLayer

  /**
   * Set the ModelTranslation property
   *
   * @param modelTranslation value of modelTranslation
   */
  fun modelTranslation(modelTranslation: List<Double> = listOf(0.0, 0.0, 0.0)): ModelLayer

  /**
   * Set the ModelTranslation property
   *
   * @param modelTranslation value of modelTranslation as Expression
   */
  fun modelTranslation(modelTranslation: Expression): ModelLayer
}

/**
 * DSL function for [ModelLayer].
 */
fun modelLayer(layerId: String, sourceId: String, block: ModelLayerDsl.() -> Unit): ModelLayer = ModelLayer(layerId, sourceId).apply(block)

// End of generated file.