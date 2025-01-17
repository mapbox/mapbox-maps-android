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
 * Layer that removes 3D content from map.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers/#clip)
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
 */
@UiThread
class ClipLayer(override val layerId: String, val sourceId: String) : ClipLayerDsl, Layer() {
  init {
    internalSourceId = sourceId
  }

  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  override fun sourceLayer(sourceLayer: String): ClipLayer = apply {
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
  override fun slot(slot: String): ClipLayer = apply {
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
  override fun filter(filter: Expression): ClipLayer = apply {
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
     * Use static method [ClipLayer.defaultVisibility] to get the default property value.
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
     * Use static method [ClipLayer.defaultVisibility] to get the default property value.
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
   * Use static method [ClipLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Visibility): ClipLayer = apply {
    val propertyValue = PropertyValue("visibility", visibility)
    setProperty(propertyValue)
  }

  /**
   * Whether this layer is displayed.
   *
   * Use static method [[ClipLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Expression): ClipLayer = apply {
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
     * Use static method [ClipLayer.defaultMinZoom] to get the default property value.
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
   * Use static method [ClipLayer.defaultMinZoom] to get the default property value.
   *
   * @param minZoom value of minzoom
   */
  override fun minZoom(minZoom: Double): ClipLayer = apply {
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
     * Use static method [ClipLayer.defaultMaxZoom] to get the default property value.
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
   * Use static method [ClipLayer.defaultMaxZoom] to get the default property value.
   *
   * @param maxZoom value of maxzoom
   */
  override fun maxZoom(maxZoom: Double): ClipLayer = apply {
    val param = PropertyValue("maxzoom", maxZoom)
    setProperty(param)
  }

  // Property getters and setters

  /**
   * Removes content from layers with the specified scope. By default all layers are affected. For example specifying `basemap` will only remove content from the Mapbox Standard style layers which have the same scope Default value: [].
   */
  val clipLayerScope: List<String>?
    /**
     * Removes content from layers with the specified scope. By default all layers are affected. For example specifying `basemap` will only remove content from the Mapbox Standard style layers which have the same scope Default value: [].
     *
     * Use static method [ClipLayer.defaultClipLayerScope] to get the default property.
     *
     * @return List<String>
     */
    get() {
      return getPropertyValue<List<String>>("clip-layer-scope")
    }

  /**
   * Removes content from layers with the specified scope. By default all layers are affected. For example specifying `basemap` will only remove content from the Mapbox Standard style layers which have the same scope Default value: [].
   *
   * Use static method [ClipLayer.defaultClipLayerScope] to set the default property.
   *
   * @param clipLayerScope value of clipLayerScope
   */
  override fun clipLayerScope(clipLayerScope: List<String>): ClipLayer = apply {
    val propertyValue = PropertyValue("clip-layer-scope", clipLayerScope)
    setProperty(propertyValue)
  }

  /**
   * Removes content from layers with the specified scope. By default all layers are affected. For example specifying `basemap` will only remove content from the Mapbox Standard style layers which have the same scope Default value: [].
   *
   * This is an Expression representation of "clip-layer-scope".
   *
   */
  val clipLayerScopeAsExpression: Expression?
    /**
     * Removes content from layers with the specified scope. By default all layers are affected. For example specifying `basemap` will only remove content from the Mapbox Standard style layers which have the same scope Default value: [].
     *
     * Get the ClipLayerScope property as an Expression
     *
     * Use static method [ClipLayer.defaultClipLayerScopeAsExpression] to get the default property.
     *
     * @return List<String>
     */
    get() {
      getPropertyValue<Expression>("clip-layer-scope")?.let {
        return it
      }
      clipLayerScope?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Removes content from layers with the specified scope. By default all layers are affected. For example specifying `basemap` will only remove content from the Mapbox Standard style layers which have the same scope Default value: [].
   *
   * Use static method [ClipLayer.defaultClipLayerScopeAsExpression] to set the default property.
   *
   * @param clipLayerScope value of clipLayerScope as Expression
   */
  override fun clipLayerScope(clipLayerScope: Expression): ClipLayer = apply {
    val propertyValue = PropertyValue("clip-layer-scope", clipLayerScope)
    setProperty(propertyValue)
  }

  /**
   * Layer types that will also be removed if fallen below this clip layer. Default value: [].
   */
  val clipLayerTypes: List<String>?
    /**
     * Layer types that will also be removed if fallen below this clip layer. Default value: [].
     *
     * Use static method [ClipLayer.defaultClipLayerTypes] to get the default property.
     *
     * @return List<String>
     */
    get() {
      return getPropertyValue<List<String>>("clip-layer-types")
    }

  /**
   * Layer types that will also be removed if fallen below this clip layer. Default value: [].
   *
   * Use static method [ClipLayer.defaultClipLayerTypes] to set the default property.
   *
   * @param clipLayerTypes value of clipLayerTypes
   */
  override fun clipLayerTypes(clipLayerTypes: List<String>): ClipLayer = apply {
    val propertyValue = PropertyValue("clip-layer-types", clipLayerTypes)
    setProperty(propertyValue)
  }

  /**
   * Layer types that will also be removed if fallen below this clip layer. Default value: [].
   *
   * This is an Expression representation of "clip-layer-types".
   *
   */
  val clipLayerTypesAsExpression: Expression?
    /**
     * Layer types that will also be removed if fallen below this clip layer. Default value: [].
     *
     * Get the ClipLayerTypes property as an Expression
     *
     * Use static method [ClipLayer.defaultClipLayerTypesAsExpression] to get the default property.
     *
     * @return List<String>
     */
    get() {
      getPropertyValue<Expression>("clip-layer-types")?.let {
        return it
      }
      clipLayerTypes?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Layer types that will also be removed if fallen below this clip layer. Default value: [].
   *
   * Use static method [ClipLayer.defaultClipLayerTypesAsExpression] to set the default property.
   *
   * @param clipLayerTypes value of clipLayerTypes as Expression
   */
  override fun clipLayerTypes(clipLayerTypes: Expression): ClipLayer = apply {
    val propertyValue = PropertyValue("clip-layer-types", clipLayerTypes)
    setProperty(propertyValue)
  }

  /**
   * Get the type of this layer
   *
   * @return Type of the layer as [String]
   */
  override fun getType(): String {
    return "clip"
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
        StyleManager.getStyleLayerPropertyDefaultValue("clip", "visibility").silentUnwrap<String>()?.let {
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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("clip", "minzoom").silentUnwrap()

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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("clip", "maxzoom").silentUnwrap()

    /**
     * Removes content from layers with the specified scope. By default all layers are affected. For example specifying `basemap` will only remove content from the Mapbox Standard style layers which have the same scope Default value: [].
     */
    val defaultClipLayerScope: List<String>?
      /**
       * Removes content from layers with the specified scope. By default all layers are affected. For example specifying `basemap` will only remove content from the Mapbox Standard style layers which have the same scope Default value: [].
       *
       * Get the default value of ClipLayerScope property
       *
       * @return List<String>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("clip", "clip-layer-scope").silentUnwrap()
      }

    /**
     * Removes content from layers with the specified scope. By default all layers are affected. For example specifying `basemap` will only remove content from the Mapbox Standard style layers which have the same scope Default value: [].
     *
     * This is an Expression representation of "clip-layer-scope".
     *
     */
    val defaultClipLayerScopeAsExpression: Expression?
      /**
       * Get default value of the ClipLayerScope property as an Expression
       *
       * @return List<String>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("clip", "clip-layer-scope").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultClipLayerScope?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Layer types that will also be removed if fallen below this clip layer. Default value: [].
     */
    val defaultClipLayerTypes: List<String>?
      /**
       * Layer types that will also be removed if fallen below this clip layer. Default value: [].
       *
       * Get the default value of ClipLayerTypes property
       *
       * @return List<String>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("clip", "clip-layer-types").silentUnwrap()
      }

    /**
     * Layer types that will also be removed if fallen below this clip layer. Default value: [].
     *
     * This is an Expression representation of "clip-layer-types".
     *
     */
    val defaultClipLayerTypesAsExpression: Expression?
      /**
       * Get default value of the ClipLayerTypes property as an Expression
       *
       * @return List<String>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("clip", "clip-layer-types").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultClipLayerTypes?.let {
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
interface ClipLayerDsl {
  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  fun sourceLayer(sourceLayer: String): ClipLayer

  /**
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   *
   * @param slot value of slot. Setting it to empty string removes the slot.
   */
  fun slot(slot: String): ClipLayer

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
  fun filter(filter: Expression): ClipLayer

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): ClipLayer

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility as Expression
   */
  fun visibility(visibility: Expression): ClipLayer

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param minZoom value of minzoom
   */
  fun minZoom(minZoom: Double): ClipLayer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param maxZoom value of maxzoom
   */
  fun maxZoom(maxZoom: Double): ClipLayer

  // Property getters and setters

  /**
   * Removes content from layers with the specified scope. By default all layers are affected. For example specifying `basemap` will only remove content from the Mapbox Standard style layers which have the same scope Default value: [].
   *
   * @param clipLayerScope value of clipLayerScope
   */
  fun clipLayerScope(clipLayerScope: List<String> = listOf("")): ClipLayer

  /**
   * Removes content from layers with the specified scope. By default all layers are affected. For example specifying `basemap` will only remove content from the Mapbox Standard style layers which have the same scope Default value: [].
   *
   * @param clipLayerScope value of clipLayerScope as Expression
   */
  fun clipLayerScope(clipLayerScope: Expression): ClipLayer

  /**
   * Layer types that will also be removed if fallen below this clip layer. Default value: [].
   *
   * @param clipLayerTypes value of clipLayerTypes
   */
  fun clipLayerTypes(clipLayerTypes: List<String> = listOf()): ClipLayer

  /**
   * Layer types that will also be removed if fallen below this clip layer. Default value: [].
   *
   * @param clipLayerTypes value of clipLayerTypes as Expression
   */
  fun clipLayerTypes(clipLayerTypes: Expression): ClipLayer
}

/**
 * DSL function for creating a [ClipLayer].
 */
fun clipLayer(layerId: String, sourceId: String, block: ClipLayerDsl.() -> Unit): ClipLayer = ClipLayer(layerId, sourceId).apply(block)

// End of generated file.