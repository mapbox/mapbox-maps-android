package com.mapbox.maps.extension.style.layers

import android.util.Log
import com.mapbox.bindgen.Value
import com.mapbox.common.Logger
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.layers.generated.*
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
  fun bindTo(delegate: StyleManagerInterface) {
    bindTo(delegate, null)
  }

  /**
   * Bind the layer to the map controller.
   *
   * @param delegate The style controller
   * @param position the position that the current layer is added to
   */
  override fun bindTo(delegate: StyleManagerInterface, position: LayerPosition?) {
    this.delegate = delegate

    val expected = delegate.addStyleLayer(getCachedLayerProperties(), position)
    expected.error?.let {
      throw RuntimeException("Add layer failed: $it")
    }
  }

  // Layer Properties

  private fun getCachedLayerProperties(): Value {
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
        throw RuntimeException("Set layer property \"${property.propertyName}\" failed:\n$it\n${property.value}")
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
    throw RuntimeException("Couldn't get $propertyName: layer is not added to style yet.")
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

/**
 * Extension function to get a Layer provided by the Style Extension by layer id.
 *
 * @param layerId the layer id
 * @return StyleLayerPlugin
 */
fun StyleManagerInterface.getLayer(layerId: String): Layer? {
  val expected = this.getStyleLayerProperties(layerId)
  expected.value?.let { value ->
    @Suppress("UNCHECKED_CAST")
    val map = value.contents as HashMap<String, Value>
    val source = map["source"]?.contents?.let { it as String }
    val type = map["type"]?.contents?.let { it as String }
    return when (type) {
      "background" -> BackgroundLayer(layerId).also { it.delegate = this }
      "circle" -> CircleLayer(layerId, source!!).also { it.delegate = this }
      "fill-extrusion" -> FillExtrusionLayer(layerId, source!!).also { it.delegate = this }
      "fill" -> FillLayer(layerId, source!!).also { it.delegate = this }
      "heatmap" -> HeatmapLayer(layerId, source!!).also { it.delegate = this }
      "hillshade" -> HillshadeLayer(layerId, source!!).also { it.delegate = this }
      "line" -> LineLayer(layerId, source!!).also { it.delegate = this }
      "raster" -> RasterLayer(layerId, source!!).also { it.delegate = this }
      "symbol" -> SymbolLayer(layerId, source!!).also { it.delegate = this }
      "location-indicator" -> LocationIndicatorLayer(layerId).also { it.delegate = this }
      "sky" -> SkyLayer(layerId).also { it.delegate = this }
      else -> {
        Logger.e("StyleLayerPlugin", "Layer type: $type unknown.")
        null
      }
    }
  }
  expected.error?.let {
    Logger.e("StyleLayerPlugin", "Get layer $layerId failed: $it")
  }
  return null
}

/**
 * Tries to cast the Layer to T, throws ClassCastException if it's another type.
 *
 * @param layerId the layer id
 * @return T
 */
fun <T : Layer> StyleManagerInterface.getLayerAs(layerId: String): T {
  @Suppress("UNCHECKED_CAST")
  return getLayer(layerId) as T
}

/**
 * Extension function to add a Layer provided by the Style Extension to the Style.
 *
 * @param layer The layer to be added
 * @param below the layer id that the current layer is added below
 */
fun StyleManagerInterface.addLayerBelow(layer: StyleContract.StyleLayerExtension, below: String?) {
  layer.bindTo(this, LayerPosition(null, below, null))
}

/**
 * Extension function to add a Layer provided by the Style Extension to the Style.
 *
 * @param layer The layer to be added
 * @param above the layer id that the current layer is added above
 */
fun StyleManagerInterface.addLayerAbove(layer: StyleContract.StyleLayerExtension, above: String?) {
  layer.bindTo(this, LayerPosition(above, null, null))
}

/**
 * Extension function to add a Layer provided by the Style Extension to the Style.
 *
 * @param layer The layer to be added
 * @param index the index that the current layer is added on
 */
fun StyleManagerInterface.addLayerAt(layer: StyleContract.StyleLayerExtension, index: Int?) {
  layer.bindTo(this, LayerPosition(null, null, index))
}

/**
 * Extension function to add a Layer provided by the Style Extension to the Style.
 *
 * @param layer The layer to be added
 */
fun StyleManagerInterface.addLayer(layer: StyleContract.StyleLayerExtension) {
  layer.bindTo(this)
}