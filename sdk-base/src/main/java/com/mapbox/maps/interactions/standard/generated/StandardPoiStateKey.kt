// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import com.mapbox.maps.MapboxDelicateApi
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.interactions.FeatureStateKey

/**
 * State keys for [StandardPoiState] that should be used in `removeFeatureState`.
 */
@MapboxExperimental
class StandardPoiStateKey private constructor(
  key: String
) : FeatureStateKey<StandardPoiState>(key) {

  /**
   * Static states.
   */
  companion object {

    /**
     * State key standing for "hide" feature state.
     */
    val HIDE: StandardPoiStateKey = StandardPoiStateKey("hide")

    /**
     * Create custom [StandardPoiStateKey] from given [key].
     *
     * Should not be used in most of the cases as this makes sense only when [StandardPoiState]
     * state was constructed with custom state keys / values.
     */
    @MapboxDelicateApi
    fun create(key: String): StandardPoiStateKey = StandardPoiStateKey(key)
  }
}