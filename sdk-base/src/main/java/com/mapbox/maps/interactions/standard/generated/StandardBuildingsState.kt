// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import androidx.annotation.RestrictTo
import com.mapbox.bindgen.Value
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.logW

/**
 * Typed feature state class needed for generic `MapboxMap.setFeatureState` and `MapboxMap.getFeatureState`
 * when working with StandardBuildings in Mapbox Standard Style.
 */
class StandardBuildingsState internal constructor(
  /**
   * For internal usage.
   * @suppress
   */
  @get:RestrictTo(RestrictTo.Scope.LIBRARY)
  override val internalState: Value,
) : FeatureState(internalState) {

  /**
   * When `true`, the building is highlighted. Use this state to create a temporary effect (e.g. hover).
   *
   * Available global configuration(s): "colorBuildingHighlight".
   *
   * Example of applying "colorBuildingHighlight": style.setStyleImportConfigProperty(importId /* "basemap" if not specified explicitly */, "colorBuildingHighlight", Value.valueOf(newValue))
   */
  @Suppress("UNCHECKED_CAST")
  val highlight: Boolean? get() = (internalState.contents as HashMap<String, Value>)["highlight"]?.contents as? Boolean

  /**
   * When `true`, the building is selected. Use this state to create a permanent effect. Note: the `select` state has a higher priority than `highlight`.
   *
   * Available global configuration(s): "colorBuildingSelect".
   *
   * Example of applying "colorBuildingSelect": style.setStyleImportConfigProperty(importId /* "basemap" if not specified explicitly */, "colorBuildingSelect", Value.valueOf(newValue))
   */
  @Suppress("UNCHECKED_CAST")
  val select: Boolean? get() = (internalState.contents as HashMap<String, Value>)["select"]?.contents as? Boolean

  /**
   * Builder class to build [StandardBuildingsState].
   */
  class Builder : FeatureState.Builder() {

    /**
     * Set state for [highlight]. See the state definition below.
     *
     * When `true`, the building is highlighted. Use this state to create a temporary effect (e.g. hover).
     * Available global configuration(s): "colorBuildingHighlight".
     *
     * Example of applying "colorBuildingHighlight": style.setStyleImportConfigProperty(importId /* "basemap" if not specified explicitly */, "colorBuildingHighlight", Value.valueOf(newValue))
     */
    fun highlight(highlight: Boolean): Builder {
      rawStateMap["highlight"] = Value.valueOf(highlight)
      return this
    }

    /**
     * Set state for [select]. See the state definition below.
     *
     * When `true`, the building is selected. Use this state to create a permanent effect. Note: the `select` state has a higher priority than `highlight`.
     * Available global configuration(s): "colorBuildingSelect".
     *
     * Example of applying "colorBuildingSelect": style.setStyleImportConfigProperty(importId /* "basemap" if not specified explicitly */, "colorBuildingSelect", Value.valueOf(newValue))
     */
    fun select(select: Boolean): Builder {
      rawStateMap["select"] = Value.valueOf(select)
      return this
    }

    /**
     * Build an instance of [StandardBuildingsState].
     */
    override fun build(): StandardBuildingsState {
      if (rawStateMap.isEmpty()) {
        logW(TAG, "Constructing an empty StandardBuildingsState. Is this intended?")
      }
      return StandardBuildingsState(Value(rawStateMap))
    }
  }
}

/**
 * Builder function to create [StandardBuildingsState].
 */
@JvmSynthetic
fun StandardBuildingsState(init: StandardBuildingsState.Builder.() -> Unit): StandardBuildingsState =
  StandardBuildingsState.Builder().apply(init).build()