// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import androidx.annotation.RestrictTo
import com.mapbox.bindgen.Value
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.logW

/**
 * Typed feature state class needed for generic `MapboxMap.setFeatureState` and `MapboxMap.getFeatureState`
 * when working with StandardIndoorLabels in Mapbox Standard Style.
 */
class StandardIndoorLabelsState internal constructor(
  /**
   * For internal usage.
   * @suppress
   */
  @get:RestrictTo(RestrictTo.Scope.LIBRARY)
  override val internalState: Value,
) : FeatureState(internalState) {

  /**
   * When `true`, the feature is highlighted. Use this state to create a temporary effect (e.g. hover).
   *
   * Available global configuration(s): "colorIndoorLabelHighlight".
   *
   * Example of applying "colorIndoorLabelHighlight": style.setStyleImportConfigProperty(importId /* "basemap" if not specified explicitly */, "colorIndoorLabelHighlight", Value.valueOf(newValue))
   */
  @Suppress("UNCHECKED_CAST")
  val highlight: Boolean? get() = (internalState.contents as HashMap<String, Value>)["highlight"]?.contents as? Boolean

  /**
   * When `true`, the feature is selected. Use this state to create a permanent effect. Note: the `select` state has a higher priority than `highlight`.
   *
   * Available global configuration(s): "colorIndoorLabelSelect".
   *
   * Example of applying "colorIndoorLabelSelect": style.setStyleImportConfigProperty(importId /* "basemap" if not specified explicitly */, "colorIndoorLabelSelect", Value.valueOf(newValue))
   */
  @Suppress("UNCHECKED_CAST")
  val select: Boolean? get() = (internalState.contents as HashMap<String, Value>)["select"]?.contents as? Boolean

  /**
   * Builder class to build [StandardIndoorLabelsState].
   */
  class Builder : FeatureState.Builder() {

    /**
     * Set state for [highlight]. See the state definition below.
     *
     * When `true`, the feature is highlighted. Use this state to create a temporary effect (e.g. hover).
     * Available global configuration(s): "colorIndoorLabelHighlight".
     *
     * Example of applying "colorIndoorLabelHighlight": style.setStyleImportConfigProperty(importId /* "basemap" if not specified explicitly */, "colorIndoorLabelHighlight", Value.valueOf(newValue))
     */
    fun highlight(highlight: Boolean): Builder {
      rawStateMap["highlight"] = Value.valueOf(highlight)
      return this
    }

    /**
     * Set state for [select]. See the state definition below.
     *
     * When `true`, the feature is selected. Use this state to create a permanent effect. Note: the `select` state has a higher priority than `highlight`.
     * Available global configuration(s): "colorIndoorLabelSelect".
     *
     * Example of applying "colorIndoorLabelSelect": style.setStyleImportConfigProperty(importId /* "basemap" if not specified explicitly */, "colorIndoorLabelSelect", Value.valueOf(newValue))
     */
    fun select(select: Boolean): Builder {
      rawStateMap["select"] = Value.valueOf(select)
      return this
    }

    /**
     * Build an instance of [StandardIndoorLabelsState].
     */
    override fun build(): StandardIndoorLabelsState {
      if (rawStateMap.isEmpty()) {
        logW(TAG, "Constructing an empty StandardIndoorLabelsState. Is this intended?")
      }
      return StandardIndoorLabelsState(Value(rawStateMap))
    }
  }
}

/**
 * Builder function to create [StandardIndoorLabelsState].
 */
@JvmSynthetic
fun StandardIndoorLabelsState(init: StandardIndoorLabelsState.Builder.() -> Unit): StandardIndoorLabelsState =
  StandardIndoorLabelsState.Builder().apply(init).build()