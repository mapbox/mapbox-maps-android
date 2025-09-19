// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import androidx.annotation.RestrictTo
import com.mapbox.bindgen.Value
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.logW

/**
 * Typed feature state class needed for generic `MapboxMap.setFeatureState` and `MapboxMap.getFeatureState`
 * when working with StandardLandmarkIcons in Mapbox Standard Style.
 */
class StandardLandmarkIconsState internal constructor(
  /**
   * For internal usage.
   * @suppress
   */
  @get:RestrictTo(RestrictTo.Scope.LIBRARY)
  override val internalState: Value,
) : FeatureState(internalState) {

  /**
   * Builder class to build [StandardLandmarkIconsState].
   */
  class Builder : FeatureState.Builder() {

    /**
     * Build an instance of [StandardLandmarkIconsState].
     */
    override fun build(): StandardLandmarkIconsState {
      if (rawStateMap.isEmpty()) {
        logW(TAG, "Constructing an empty StandardLandmarkIconsState. Is this intended?")
      }
      return StandardLandmarkIconsState(Value(rawStateMap))
    }
  }
}

/**
 * Builder function to create [StandardLandmarkIconsState].
 */
@JvmSynthetic
fun StandardLandmarkIconsState(init: StandardLandmarkIconsState.Builder.() -> Unit): StandardLandmarkIconsState =
  StandardLandmarkIconsState.Builder().apply(init).build()