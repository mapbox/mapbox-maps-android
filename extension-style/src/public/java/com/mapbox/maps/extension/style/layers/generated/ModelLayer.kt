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