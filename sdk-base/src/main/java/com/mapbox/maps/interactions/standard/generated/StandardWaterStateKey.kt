// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import com.mapbox.maps.MapboxDelicateApi
import com.mapbox.maps.interactions.FeatureStateKey

/**
 * State keys for [StandardWaterState] that should be used in `removeFeatureState`.
 */
class StandardWaterStateKey private constructor(
  key: String
) : FeatureStateKey<StandardWaterState>(key) {

  /**
   * Static states.
   */
  companion object {

    /**
     * Create custom [StandardWaterStateKey] from given [key].
     *
     * Should not be used in most of the cases as this makes sense only when [StandardWaterState]
     * state was constructed with custom state keys / values.
     */
    @MapboxDelicateApi
    fun create(key: String): StandardWaterStateKey = StandardWaterStateKey(key)
  }
}