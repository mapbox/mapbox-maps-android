package com.mapbox.maps.extension.style.layers

import android.util.Log
import com.mapbox.bindgen.Value
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxStyleException
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.StyleInterface
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

  internal var delegate: StyleManagerInterface? = null

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
   * Get the Visibility property
   *
   * @return property wrapper value around VISIBILITY
   */
  abstract val visibility: Visibility?

  /**
   * Set the Visibility property
   *
   * @param visibility value of Visibility
   */
  abstract fun visibility(visibility: Visibility): Layer

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
   * Bind the layer to the Style.
   *
   * @param delegate The style controller
   */
  fun bindTo(delegate: StyleInterface) {
    bindTo(delegate, null)
  }

  /**
   * Bind the layer to the map controller.
   *
   * @param delegate The style controller
   * @param position the position that the current layer is added to
   */
  override fun bindTo(delegate: StyleInterface, position: LayerPosition?) {
    this.delegate = delegate

    val expected = delegate.addStyleLayer(getCachedLayerProperties(), position)
    expected.error?.let {
      throw MapboxStyleException("Add layer failed: $it")
    }
  }

  // Layer Properties

  internal fun getCachedLayerProperties(): Value {
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
        Log.e(TAG, "Get layer property failed: ${e.message}")
        Log.e(TAG, it.getStyleLayerProperty(layerId, propertyName).toString())
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