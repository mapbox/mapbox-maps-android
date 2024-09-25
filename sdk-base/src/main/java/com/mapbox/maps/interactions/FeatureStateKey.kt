package com.mapbox.maps.interactions

import com.mapbox.maps.MapboxExperimental
import java.util.Objects

/**
 * Base class for holding the typed feature state keys that could be used in `removeFeatureState`.
 *
 * Refer to static [create] method to create an instance of [FeatureStateKey].
 */
@MapboxExperimental
open class FeatureStateKey<FS : FeatureState> internal constructor(
  /**
   * The feature state key to remove from the feature.
   */
  val key: String
) {
  /**
   * Override equals method.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as FeatureStateKey<*>

    return key == other.key
  }

  /**
   * Override hashCode method.
   */
  override fun hashCode(): Int =
    Objects.hash(key)

  /**
   * Static methods.
   */
  companion object {

    /**
     * Create an instance of [FeatureStateKey] to be passed to `removeFeatureState`.
     */
    @JvmStatic
    fun create(key: String): FeatureStateKey<FeatureState> = FeatureStateKey(key)
  }
}