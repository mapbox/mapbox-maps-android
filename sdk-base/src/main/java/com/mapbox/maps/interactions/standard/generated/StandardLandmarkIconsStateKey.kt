// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import com.mapbox.maps.MapboxDelicateApi
import com.mapbox.maps.interactions.FeatureStateKey

/**
 * State keys for [StandardLandmarkIconsState] that should be used in `removeFeatureState`.
 */
class StandardLandmarkIconsStateKey private constructor(
  key: String
) : FeatureStateKey<StandardLandmarkIconsState>(key) {

  /**
   * Static states.
   */
  companion object {

    /**
     * Create custom [StandardLandmarkIconsStateKey] from given [key].
     *
     * Should not be used in most of the cases as this makes sense only when [StandardLandmarkIconsState]
     * state was constructed with custom state keys / values.
     */
    @MapboxDelicateApi
    fun create(key: String): StandardLandmarkIconsStateKey = StandardLandmarkIconsStateKey(key)
  }
}