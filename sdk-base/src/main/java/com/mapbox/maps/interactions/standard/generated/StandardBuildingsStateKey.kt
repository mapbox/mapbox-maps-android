// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import com.mapbox.maps.MapboxDelicateApi
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.interactions.FeatureStateKey

/**
 * State keys for [StandardBuildingsState] that should be used in `removeFeatureState`.
 */
@MapboxExperimental
class StandardBuildingsStateKey private constructor(
  key: String
) : FeatureStateKey<StandardBuildingsState>(key) {

  /**
   * Static states.
   */
  companion object {

    /**
     * State key standing for "highlight" feature state.
     */
    val HIGHLIGHT: StandardBuildingsStateKey = StandardBuildingsStateKey("highlight")

    /**
     * State key standing for "select" feature state.
     */
    val SELECT: StandardBuildingsStateKey = StandardBuildingsStateKey("select")

    /**
     * Create custom [StandardBuildingsStateKey] from given [key].
     *
     * Should not be used in most of the cases as this makes sense only when [StandardBuildingsState]
     * state was constructed with custom state keys / values.
     */
    @MapboxDelicateApi
    fun create(key: String): StandardBuildingsStateKey = StandardBuildingsStateKey(key)
  }
}