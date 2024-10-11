// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import com.mapbox.maps.MapboxDelicateApi
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.interactions.FeatureStateKey

/**
 * State keys for [StandardPlaceLabelsState] that should be used in `removeFeatureState`.
 */
@MapboxExperimental
class StandardPlaceLabelsStateKey private constructor(
  key: String
) : FeatureStateKey<StandardPlaceLabelsState>(key) {

  /**
   * Static states.
   */
  companion object {

    /**
     * State key standing for "hide" feature state.
     */
    val HIDE: StandardPlaceLabelsStateKey = StandardPlaceLabelsStateKey("hide")

    /**
     * State key standing for "highlight" feature state.
     */
    val HIGHLIGHT: StandardPlaceLabelsStateKey = StandardPlaceLabelsStateKey("highlight")

    /**
     * State key standing for "select" feature state.
     */
    val SELECT: StandardPlaceLabelsStateKey = StandardPlaceLabelsStateKey("select")

    /**
     * Create custom [StandardPlaceLabelsStateKey] from given [key].
     *
     * Should not be used in most of the cases as this makes sense only when [StandardPlaceLabelsState]
     * state was constructed with custom state keys / values.
     */
    @MapboxDelicateApi
    fun create(key: String): StandardPlaceLabelsStateKey = StandardPlaceLabelsStateKey(key)
  }
}