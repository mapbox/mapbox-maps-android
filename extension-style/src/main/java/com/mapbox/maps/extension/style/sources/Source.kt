package com.mapbox.maps.extension.style.sources

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import com.mapbox.bindgen.Value
import com.mapbox.common.Logger
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.sources.generated.*
import com.mapbox.maps.extension.style.utils.unwrap

/**
 * Base class for sources.
 *
 * Specification of a data source.
 * For vector and raster sources, either TileJSON or a URL to a TileJSON must be provided.
 * For image and video sources, a URL must be provided.
 * For GeoJSON sources, a URL or inline GeoJSON must be provided.
 */
abstract class Source(
  /**
   * The ID of the Source.
   */
  val sourceId: String
) : StyleContract.StyleSourceExtension {

  /**
   * Get the type of the current source as a String.
   */
  @SuppressWarnings("HiddenAbstractMethod")
  internal abstract fun getType(): String

  /**
   * Properties of this source.
   */
  internal val sourceProperties by lazy {
    HashMap<String, PropertyValue<*>>().also { map ->
      map["type"] = PropertyValue("type", getType())
    }
  }

  /**
   * Volatile properties of this source. These properties can only be set after the source is added to the style.
   */
  internal val volatileSourceProperties by lazy {
    HashMap<String, PropertyValue<*>>()
  }

  internal var delegate: StyleManagerInterface? = null

  /**
   * Add the source to the Style.
   *
   * @param delegate The style delegate
   */
  override fun bindTo(delegate: StyleInterface) {
    this.delegate = delegate
    val expected = delegate.addStyleSource(sourceId, getCachedSourceProperties())
    expected.error?.let {
      Log.e(TAG, getCachedSourceProperties().toString())
      throw RuntimeException("Add source failed: $it")
    }

    // Set volatile properties.
    volatileSourceProperties.forEach {
      updateProperty(it.value)
    }
  }

  // Source Properties

  private fun getCachedSourceProperties(): Value {
    val properties = HashMap<String, Value>()

    // For now only volatile property could be set during construction, all other properties
    // can be set only after source is created.
    sourceProperties.values.forEach {
      properties[it.propertyName] = it.value
    }
    return Value(properties)
  }

  internal fun setProperty(property: PropertyValue<*>) {
    sourceProperties[property.propertyName] = property
    updateProperty(property)
  }

  internal fun setVolatileProperty(property: PropertyValue<*>) {
    volatileSourceProperties[property.propertyName] = property
    updateProperty(property)
  }

  private fun updateProperty(property: PropertyValue<*>) {
    delegate?.let { styleDelegate ->
      val expected = styleDelegate.setStyleSourceProperty(
        sourceId,
        property.propertyName,
        property.value
      )
      expected.error?.let {
        throw RuntimeException("Set source property \"${property.propertyName}\" failed:\nError: $it\nValue set: ${property.value}")
      }
    }
  }

  internal inline fun <reified T> getPropertyValue(propertyName: String): T? {
    delegate?.let {
      return try {
        it.getStyleSourceProperty(sourceId, propertyName).unwrap()
      } catch (e: RuntimeException) {
        Log.e(TAG, "Get source property $propertyName failed: ${e.message}")
        Log.e(TAG, "Value returned: ${it.getStyleSourceProperty(sourceId, propertyName)}")
        null
      }
    }
    throw RuntimeException("Couldn't get $propertyName: source is not added to style yet.")
  }

  /**
   * Returns a human readable string that includes the cached properties of the source.
   *
   * @return String
   */
  override fun toString(): String {
    return "[sourceId = $sourceId, ${sourceProperties.values.joinToString { propertyValue -> "${propertyValue.propertyName} = ${propertyValue.value}" }}}]"
  }

  /**
   * Static variables and methods.
   */
  companion object {
    private const val TAG = "Mbgl-Source"
  }
}

/**
 * Extension function to get a Source provided by the Style Extension by source id.
 *
 * @param sourceId the source id
 * @return StyleSourcePlugin
 */
fun StyleManagerInterface.getSource(sourceId: String): Source? {
  val expected = this.getStyleSourceProperties(sourceId)
  expected.value?.let { value ->
    @Suppress("UNCHECKED_CAST")
    val map = value.contents as HashMap<String, Value>
    return when (val type = map["type"]?.contents?.let { it as String }) {
      "vector" -> VectorSource.Builder(sourceId).build().also { it.delegate = this }
      "geojson" -> GeoJsonSource.Builder(sourceId).build().also { it.delegate = this }
      "image" -> ImageSource.Builder(sourceId).build().also { it.delegate = this }
      "raster-dem" -> RasterDemSource.Builder(sourceId).build().also { it.delegate = this }
      "raster" -> RasterSource.Builder(sourceId).build().also { it.delegate = this }
      else -> {
        Logger.e("StyleSourcePlugin", "Source type: $type unknown.")
        null
      }
    }
  }
  expected.error?.let {
    Logger.e("StyleSourcePlugin", "Get source $sourceId failed: $it")
  }
  return null
}

/**
 * Tries to cast the Source to T.
 *
 * @param sourceId the layer id
 * @return T if Source is T and null otherwise
 */
@SuppressWarnings("ChangedType")
inline fun <reified T : Source> StyleManagerInterface.getSourceAs(sourceId: String): T? {
  val source = getSource(sourceId)
  if (source !is T) {
    Logger.w("StyleSourcePlugin", "Given sourceId = $sourceId is not requested type in getSourceAs.")
    return null
  }
  return source
}

/**
 * Extension function to add a Source provided by the Style Extension to the Style.
 *
 * @param source The light to be added
 */
fun StyleInterface.addSource(source: StyleContract.StyleSourceExtension) {
  source.bindTo(this)
}