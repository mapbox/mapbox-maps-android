package com.mapbox.maps.extension.style.sources

import android.util.Log
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxStyleException
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.utils.unwrap
import com.mapbox.maps.logE

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

  internal var delegate: StyleInterface? = null

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
      throw MapboxStyleException("Add source failed: $it")
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

  internal fun setProperty(property: PropertyValue<*>, throwRuntimeException: Boolean = true) {
    sourceProperties[property.propertyName] = property
    updateProperty(property, throwRuntimeException)
  }

  internal fun setVolatileProperty(property: PropertyValue<*>) {
    volatileSourceProperties[property.propertyName] = property
    updateProperty(property)
  }

  private fun updateProperty(property: PropertyValue<*>, throwRuntimeException: Boolean = true) {
    delegate?.let { styleDelegate ->
      try {
        val expected = styleDelegate.setStyleSourceProperty(
          sourceId,
          property.propertyName,
          property.value
        )
        expected.error?.let { error ->
          "Set source property \"${property.propertyName}\" failed:\nError: $error\nValue set: ${property.value}".let {
            if (throwRuntimeException) {
              throw MapboxStyleException(it)
            } else {
              logE(TAG, it)
            }
          }
        }
      } catch (e: IllegalStateException) {
        if (throwRuntimeException) {
          throw e
        } else {
          logE(TAG, e.message ?: "")
        }
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
    throw MapboxStyleException("Couldn't get $propertyName: source is not added to style yet.")
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