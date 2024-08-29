package com.mapbox.maps.interactions

import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import java.util.Objects

/**
 * Helper wrapper class needed for generic `MapboxMap.setFeatureState`.
 */
@MapboxExperimental
open class FeatureStateValue(
  /**
   * Property key.
   */
  open val key: String,
  /**
   * Property value.
   */
  open val value: Value
) {

  /**
   * Override equals method.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as FeatureStateValue

    if (key != other.key) return false
    if (value != other.value) return false

    return true
  }

  /**
   * Override hashCode method.
   */
  override fun hashCode() = Objects.hash(key, value)

  /**
   * Override toString method.
   */
  override fun toString() = "FeatureStateValue(key=$key, value=$value)"
}