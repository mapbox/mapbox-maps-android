package com.mapbox.maps.extension.style.layers

import android.util.Log
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxStyleException
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.generated.BackgroundLayer
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.layers.properties.generated.Visibility
import com.mapbox.maps.extension.style.utils.unwrap

/**
 * Base class for the different Layer types
 */
abstract class Layer : StyleContract.StyleLayerExtension {
  /**
   * The ID of the Layer.
   */
  abstract val layerId: String

  /**
   * Source ID of the Layer, could be null for [BackgroundLayer]
   */
  internal var internalSourceId: String? = null

  internal var delegate: MapboxStyleManager? = null

  internal var appliedLayerPropertiesValue: Value? = null

  /**
   * Properties of this layer.
   */
  private val layerProperties by lazy {
    HashMap<String, PropertyValue<*>>().also { map ->
      map["id"] = PropertyValue("id", layerId)
      map["type"] = PropertyValue("type", getType())
      internalSourceId?.let {
        map["source"] = PropertyValue("source", it)
      }
    }
  }

  /**
   * Get the type of current layer as a String.
   */
  @SuppressWarnings("HiddenAbstractMethod")
  internal abstract fun getType(): String

  /**
   * Get the Visibility property.
   *
   * @return property wrapper value around VISIBILITY
   */
  abstract val visibility: Visibility?

  /**
   * Get the Visibility property as Expression.
   *
   * @return property wrapper value around VISIBILITY
   */
  abstract val visibilityAsExpression: Expression?

  /**
   * Set the Visibility property.
   *
   * @param visibility value of Visibility
   */
  abstract fun visibility(visibility: Visibility): Layer

  /**
   * Set the Visibility property as expression.
   *
   * @param visibility value of Visibility as expression
   */
  abstract fun visibility(visibility: Expression): Layer

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   */
  abstract val minZoom: Double?

  /**
   * Set the minzoom property
   *
   * @param minZoom value of minzoom
   */
  abstract fun minZoom(minZoom: Double): Layer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   */
  abstract val maxZoom: Double?

  /**
   * Set the maxzoom property
   *
   * @param maxZoom value of maxzoom
   */
  abstract fun maxZoom(maxZoom: Double): Layer

  /**
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   *
   * @param slot value of slot. Setting it to empty string removes the slot.
   */
  abstract fun slot(slot: String): Layer

  /**
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   */
  abstract val slot: String?

  protected open fun addPersistentLayer(
    delegate: MapboxStyleManager,
    position: LayerPosition?
  ): Expected<String, None> {
    return delegate.addPersistentStyleLayer(getCachedLayerProperties(), position)
  }

  protected open fun addLayer(
    delegate: MapboxStyleManager,
    propertiesValue: Value,
    position: LayerPosition?
  ): Expected<String, None> {
    return delegate.addStyleLayer(propertiesValue, position)
  }

  /**
   * Bind the layer to the Style.
   *
   * @param delegate The style controller
   */
  fun bindTo(delegate: MapboxStyleManager) {
    bindTo(delegate, null)
  }

  /**
   * Bind the layer to the map controller.
   *
   * @param delegate The style controller
   * @param position the position that the current layer is added to
   */
  override fun bindTo(delegate: MapboxStyleManager, position: LayerPosition?) {
    this.delegate = delegate

    val propertiesValue = appliedLayerPropertiesValue ?: getCachedLayerProperties()
    val expected = addLayer(delegate, propertiesValue, position)
    expected.error?.let {
      throw MapboxStyleException("Add layer failed: $it")
    }

    if (appliedLayerPropertiesValue != null) {
      layerProperties.values.filter { it.propertyName != "id" && it.propertyName != "type" && it.propertyName != "source" }
        .forEach {
          delegate.setStyleLayerProperty(layerId, it.propertyName, it.value)
        }
    }
  }

  /**
   * Bind the layer to the map controller persistently.
   *
   * Whenever a new style is being parsed and currently used style has persistent layers,
   * an engine will try to do following:
   *   - keep the persistent layer at its relative position
   *   - keep the source used by a persistent layer
   *   - keep images added through `addStyleImage` method
   *
   * In cases when a new style has the same layer, source or image resource, style's resources would be
   * used instead and `MapLoadingError` event will be emitted.
   *
   * @param style The style
   * @param position the position that the current layer is added to
   */
  internal fun bindPersistentlyTo(style: MapboxStyleManager, position: LayerPosition? = null) {
    this.delegate = style
    val expected = addPersistentLayer(style, position)
    expected.error?.let {
      throw MapboxStyleException("Add persistent layer failed: $it")
    }
  }

  // Layer Properties
  protected fun getCachedLayerProperties(): Value {
    val properties = HashMap<String, Value>()
    layerProperties.values.forEach {
      properties[it.propertyName] = it.value
    }
    return Value(properties)
  }

  internal fun setProperty(property: PropertyValue<*>) {
    layerProperties[property.propertyName] = property
    updateProperty(property)
  }

  private fun updateProperty(property: PropertyValue<*>) {
    delegate?.let { styleDelegate ->
      val expected = styleDelegate.setStyleLayerProperty(
        layerId,
        property.propertyName,
        property.value
      )
      expected.error?.let {
        throw MapboxStyleException("Set layer property \"${property.propertyName}\" failed:\n$it\n${property.value}")
      }
    }
  }

  internal inline fun <reified T> getPropertyValue(propertyName: String): T? {
    delegate?.let {
      return try {
        it.getStyleLayerProperty(layerId, propertyName).unwrap()
      } catch (e: RuntimeException) {
        // logging an error is misleading as it is valid to set a property
        // with a concrete value (e.g. Double) and then read it as an expression
        if (T::class != Expression::class) {
          Log.e(
            TAG,
            "Get layer property=$propertyName for layerId=$layerId failed: ${e.message}. " +
              "Value obtained: ${it.getStyleLayerProperty(layerId, propertyName)}"
          )
        }
        null
      }
    }
    throw MapboxStyleException("Couldn't get $propertyName: layer is not added to style yet.")
  }

  /**
   * Returns a human readable string that includes the cached properties of the layer.
   *
   * @return String
   */
  override fun toString(): String {
    return "[${layerProperties.values.joinToString { propertyValue -> "${propertyValue.propertyName} = ${propertyValue.value}" }}}]"
  }

  /**
   * Static variables and methods.
   */
  companion object {
    private const val TAG = "Mbgl-Layer"
  }
}