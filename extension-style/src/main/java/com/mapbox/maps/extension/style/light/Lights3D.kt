package com.mapbox.maps.extension.style.light

import com.mapbox.bindgen.Value
import com.mapbox.common.toValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleException
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.types.StyleTransition
import com.mapbox.maps.extension.style.utils.unwrap
import com.mapbox.maps.logE
import kotlin.collections.HashMap

/**
 * Global Light for 3D rendering. Must contain exactly one directional light and one ambient light.
 *
 * Check the [online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#light).
 */
@MapboxExperimental
abstract class Lights3D {
  /**
   * The ID of the Light.
   */
  abstract val lightId: String

  /**
   * Get the type of current light as a String.
   */
  @SuppressWarnings("HiddenAbstractMethod")
  internal abstract fun getType(): String

  internal var delegate: Style? = null

  /**
   * Properties of the light.
   */
  internal val lightProperties by lazy { HashMap<String, PropertyValue<*>>() }

  /**
   * Base properties of Light.
   */
  internal val internalLightProperties by lazy {
    HashMap<String, Value>().also { map ->
      map["id"] = lightId.toValue()
      map["type"] = getType().toValue()
    }
  }

  private fun updateProperty(propertyValue: PropertyValue<*>) {
    val expected = delegate?.setStyleLightProperty(
      lightId,
      propertyValue.propertyName,
      propertyValue.value
    )
    expected?.error?.let {
      throw MapboxStyleException("Set lights3D property failed: $it")
    }
  }

  internal inline fun <reified T> getPropertyValue(propertyName: String): T? {
    delegate?.let {
      return try {
        it.getStyleLightProperty(lightId, propertyName).unwrap()
      } catch (e: RuntimeException) {
        logE(TAG, "Get lights3D property $propertyName failed: ${e.message}")
        logE(TAG, it.getStyleLightProperty(lightId, propertyName).toString())
        null
      }
    }
    throw MapboxStyleException("Get property $propertyName failed: lights3D is not added to style yet.")
  }

  internal fun setProperty(property: PropertyValue<*>) {
    lightProperties[property.propertyName] = property
    updateProperty(property)
  }

  internal fun getTransitionProperty(transitionName: String): StyleTransition? {
    delegate?.let {
      return try {
        @Suppress("UNCHECKED_CAST")
        val styleLights3DProperty =
          it.getStyleLightProperty(lightId, transitionName).value.contents as HashMap<String, Value>
        val duration = styleLights3DProperty["duration"]?.contents as Long
        val delay = styleLights3DProperty["delay"]?.contents as Long
        StyleTransition.Builder().delay(delay).duration(duration).build()
      } catch (e: RuntimeException) {
        logE(TAG, "Get lights3D property failed: ${e.message}")
        logE(TAG, it.getStyleLightProperty(lightId, transitionName).toString())
        null
      }
    }
    throw MapboxStyleException("Get property $transitionName failed: lights3D is not added to style yet.")
  }

  /**
   * Get a string representation of the 3D Lights.
   *
   * @return the string representation
   */
  override fun toString(): String {
    return "[${internalLightProperties.map { "${it.key} = ${it.value}" }.joinToString(", ")}, ${lightProperties.values.joinToString { propertyValue -> "${propertyValue.propertyName} = ${propertyValue.value}" }}}]"
  }

  /**
   * Static variables and methods.
   */
  companion object {
    private const val TAG = "Mbgl-Lights3D"
  }
}