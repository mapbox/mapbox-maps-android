// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import androidx.annotation.RestrictTo
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.logW

/**
 * Typed feature state class needed for generic `MapboxMap.setFeatureState` and `MapboxMap.getFeatureState`
 * when working with StandardPlaceLabels in Mapbox Standard Style.
 */
@MapboxExperimental
class StandardPlaceLabelsState internal constructor(
  /**
   * For internal usage.
   * @suppress
   */
  @get:RestrictTo(RestrictTo.Scope.LIBRARY)
  override val internalState: Value,
) : FeatureState(internalState) {

  /**
   * When `true`, hides the label. Use this state when displaying a custom annotation on top.
   *
   */
  @Suppress("UNCHECKED_CAST")
  val hide: Boolean? get() = (internalState.contents as HashMap<String, Value>)["hide"]?.contents as? Boolean

  /**
   * When `true`, the feature is highlighted. Use this state to create a temporary effect (e.g. hover).
   *
   * Available global configuration(s): "placeLabelHighlightColor".
   *
   * Example of applying "placeLabelHighlightColor": style.setStyleImportConfigProperty(importId /* "basemap" if not specified explicitly */, "placeLabelHighlightColor", Value.valueOf("#FF0000"))
   */
  @Suppress("UNCHECKED_CAST")
  val highlight: Boolean? get() = (internalState.contents as HashMap<String, Value>)["highlight"]?.contents as? Boolean

  /**
   * When `true`, the feature is selected. Use this state to create a permanent effect. Note: the `select` state has a higher priority than `highlight`.
   *
   * Available global configuration(s): "placeLabelSelectColor".
   *
   * Example of applying "placeLabelSelectColor": style.setStyleImportConfigProperty(importId /* "basemap" if not specified explicitly */, "placeLabelSelectColor", Value.valueOf("#FF0000"))
   */
  @Suppress("UNCHECKED_CAST")
  val select: Boolean? get() = (internalState.contents as HashMap<String, Value>)["select"]?.contents as? Boolean

  /**
   * Builder class to build [StandardPlaceLabelsState].
   */
  class Builder : FeatureState.Builder() {

    /**
     * Set state for [hide]. See the state definition below.
     *
     * When `true`, hides the label. Use this state when displaying a custom annotation on top.
     */
    fun hide(hide: Boolean): Builder {
      rawStateMap["hide"] = Value.valueOf(hide)
      return this
    }

    /**
     * Set state for [highlight]. See the state definition below.
     *
     * When `true`, the feature is highlighted. Use this state to create a temporary effect (e.g. hover).
     * Available global configuration(s): "placeLabelHighlightColor".
     *
     * Example of applying "placeLabelHighlightColor": style.setStyleImportConfigProperty(importId /* "basemap" if not specified explicitly */, "placeLabelHighlightColor", Value.valueOf("#FF0000"))
     */
    fun highlight(highlight: Boolean): Builder {
      rawStateMap["highlight"] = Value.valueOf(highlight)
      return this
    }

    /**
     * Set state for [select]. See the state definition below.
     *
     * When `true`, the feature is selected. Use this state to create a permanent effect. Note: the `select` state has a higher priority than `highlight`.
     * Available global configuration(s): "placeLabelSelectColor".
     *
     * Example of applying "placeLabelSelectColor": style.setStyleImportConfigProperty(importId /* "basemap" if not specified explicitly */, "placeLabelSelectColor", Value.valueOf("#FF0000"))
     */
    fun select(select: Boolean): Builder {
      rawStateMap["select"] = Value.valueOf(select)
      return this
    }

    /**
     * Build an instance of [StandardPlaceLabelsState].
     */
    override fun build(): StandardPlaceLabelsState {
      if (rawStateMap.isEmpty()) {
        logW(TAG, "Constructing an empty StandardPlaceLabelsState. Is this intended?")
      }
      return StandardPlaceLabelsState(Value(rawStateMap))
    }
  }
}

/**
 * Builder function to create [StandardPlaceLabelsState].
 */
@MapboxExperimental
@JvmSynthetic
fun StandardPlaceLabelsState(init: StandardPlaceLabelsState.Builder.() -> Unit): StandardPlaceLabelsState =
  StandardPlaceLabelsState.Builder().apply(init).build()