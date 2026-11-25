// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import androidx.annotation.RestrictTo
import com.mapbox.bindgen.Value
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.logW

/**
 * Typed feature state class needed for generic `MapboxMap.setFeatureState` and `MapboxMap.getFeatureState`
 * when working with StandardWater in Mapbox Standard Style.
 */
class StandardWaterState internal constructor(
  /**
   * For internal usage.
   * @suppress
   */
  @get:RestrictTo(RestrictTo.Scope.LIBRARY)
  override val internalState: Value,
) : FeatureState(internalState) {

  /**
   * Builder class to build [StandardWaterState].
   */
  class Builder : FeatureState.Builder() {

    /**
     * Build an instance of [StandardWaterState].
     */
    override fun build(): StandardWaterState {
      if (rawStateMap.isEmpty()) {
        logW(TAG, "Constructing an empty StandardWaterState. Is this intended?")
      }
      return StandardWaterState(Value(rawStateMap))
    }
  }
}

/**
 * Builder function to create [StandardWaterState].
 */
@JvmSynthetic
fun StandardWaterState(init: StandardWaterState.Builder.() -> Unit): StandardWaterState =
  StandardWaterState.Builder().apply(init).build()