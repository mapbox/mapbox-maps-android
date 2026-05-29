// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import com.mapbox.maps.MapboxDelicateApi
import com.mapbox.maps.interactions.FeatureStateKey

/**
 * State keys for [StandardIndoorLabelsState] that should be used in `removeFeatureState`.
 */
class StandardIndoorLabelsStateKey private constructor(
  key: String
) : FeatureStateKey<StandardIndoorLabelsState>(key) {

  /**
   * Static states.
   */
  companion object {

    /**
     * State key standing for "highlight" feature state.
     */
    val HIGHLIGHT: StandardIndoorLabelsStateKey = StandardIndoorLabelsStateKey("highlight")

    /**
     * State key standing for "select" feature state.
     */
    val SELECT: StandardIndoorLabelsStateKey = StandardIndoorLabelsStateKey("select")

    /**
     * Create custom [StandardIndoorLabelsStateKey] from given [key].
     *
     * Should not be used in most of the cases as this makes sense only when [StandardIndoorLabelsState]
     * state was constructed with custom state keys / values.
     */
    @MapboxDelicateApi
    fun create(key: String): StandardIndoorLabelsStateKey = StandardIndoorLabelsStateKey(key)
  }
}