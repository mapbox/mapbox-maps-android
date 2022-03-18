package com.mapbox.maps.extension.style.sources

import android.util.Log
import com.mapbox.bindgen.Value
import com.mapbox.common.Logger
import com.mapbox.maps.MapboxStyleException
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.utils.unwrap
import java.lang.reflect.Method

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

  private var styleObjectIsValidMethod: Method? = null

  /**
   * Add the source to the Style.
   *
   * @param delegate The style delegate
   */
  override fun bindTo(delegate: StyleInterface) {
    this.delegate = delegate
    // geojson source is the only one holding async logic -
    // in order to avoid accessing native style object when MapView is destroyed we need to check
    // if delegate is still valid.
    // Sadly due to current architecture it's now easily achievable with reflection only
    // as this module does not have dependency on main SDK module and Style object
    if (this is GeoJsonSource) {
      try {
        val clazz = Class.forName(delegate.javaClass.name)
        styleObjectIsValidMethod = clazz.getMethod("isValid")
      } catch (e: Exception) {
        // if exception did occur - nothing critical will happen,
        // we will simply most likely access native object after MapView destruction leading
        // to printing some logs, no actual leak will be introduced
        Logger.e(TAG, e.message ?: "")
      }
    }
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
        if (this is GeoJsonSource) {
          val isNativeStyleValid = styleObjectIsValidMethod?.invoke(styleDelegate)
          // explicitly reset native reference and return
          if (isNativeStyleValid == false) {
            delegate = null
            return@let
          }
        }
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
              Logger.e(TAG, it)
            }
          }
        }
      } catch (e: IllegalStateException) {
        if (throwRuntimeException) {
          throw e
        } else {
          Logger.e(TAG, e.message ?: "")
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