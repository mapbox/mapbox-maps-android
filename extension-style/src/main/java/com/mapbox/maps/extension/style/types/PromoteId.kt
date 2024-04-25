package com.mapbox.maps.extension.style.types

import androidx.annotation.Keep
import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.VectorSource

/**
 * Holds a property type to promote a specific feature for feature state API.
 *
 * @param sourceId source layer id of the feature, either source [GeoJsonSource] or [VectorSource].
 * @param propertyName feature property name.
 *
 * For more information see [The online documentation](https://docs.mapbox.com/mapbox-gl-js/style-spec/types/#promoteId).
 */
@Keep
data class PromoteId @JvmOverloads constructor(
  val propertyName: String,
  val sourceId: String? = null
) {
  internal fun toValue(): Value {
    sourceId?.let {
      return Value(hashMapOf(sourceId to Value(propertyName)))
    }
    return Value(propertyName)
  }

  /**
   * Static variables and methods.
   */
  companion object {
    /**
     * Construct a [PromoteId] object from a Property returned from the core.
     *
     * @param propertyName feature property name.
     * Can be either of type [String] or [HashMap] of <String,String>
     * Throws [RuntimeException] exception if couldn't construct to [PromoteId].
     */
    internal fun fromProperty(propertyName: Any) = when (propertyName) {
      is String -> {
        PromoteId(propertyName)
      }
      is HashMap<*, *> -> {
        @Suppress("UNCHECKED_CAST")
        try {
          val propertyMap = propertyName as HashMap<String, String>
          if (propertyMap.keys.isNotEmpty()) {
            val key = propertyMap.keys.iterator().next()
            PromoteId(propertyMap[key] ?: "", key)
          } else {
            PromoteId("")
          }
        } catch (e: RuntimeException) {
          throw IllegalArgumentException("$propertyName must be in the format HashMap<String,String>")
        }
      }
      else -> throw UnsupportedOperationException("Wrapping ${propertyName::class.java.simpleName} to PromoteId is not supported.")
    }
  }
}