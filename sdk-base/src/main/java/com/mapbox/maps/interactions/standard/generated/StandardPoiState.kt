// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import androidx.annotation.RestrictTo
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.logW

/**
 * Typed feature state class needed for generic `MapboxMap.setFeatureState` and `MapboxMap.getFeatureState`
 * when working with StandardPoi in Mapbox Standard Style.
 */
@MapboxExperimental
class StandardPoiState internal constructor(
  /**
   * For internal usage.
   * @suppress
   */
  @get:RestrictTo(RestrictTo.Scope.LIBRARY)
  override val internalState: Value,
) : FeatureState(internalState) {

  /**
   * When `true`, hides the icon and text. Use this state when displaying a custom annotation on top.
   *
   */
  @Suppress("UNCHECKED_CAST")
  val hide: Boolean? get() = (internalState.contents as HashMap<String, Value>)["hide"]?.contents as? Boolean

  /**
   * Builder class to build [StandardPoiState].
   */
  class Builder : FeatureState.Builder() {

    /**
     * Set state for [hide]. See the state definition below.
     *
     * When `true`, hides the icon and text. Use this state when displaying a custom annotation on top.
     */
    fun hide(hide: Boolean): Builder {
      rawStateMap["hide"] = Value.valueOf(hide)
      return this
    }

    /**
     * Build an instance of [StandardPoiState].
     */
    override fun build(): StandardPoiState {
      if (rawStateMap.isEmpty()) {
        logW(TAG, "Constructing an empty StandardPoiState. Is this intended?")
      }
      return StandardPoiState(Value(rawStateMap))
    }
  }
}

/**
 * Builder function to create [StandardPoiState].
 */
@MapboxExperimental
@JvmSynthetic
fun StandardPoiState(init: StandardPoiState.Builder.() -> Unit): StandardPoiState =
  StandardPoiState.Builder().apply(init).build()