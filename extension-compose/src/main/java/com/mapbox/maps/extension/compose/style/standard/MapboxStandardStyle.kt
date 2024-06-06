package com.mapbox.maps.extension.compose.style.standard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.extension.compose.style.MapboxStyleComposable
import com.mapbox.maps.extension.compose.style.atmosphere.generated.AtmosphereState
import com.mapbox.maps.extension.compose.style.projection.generated.Projection
import com.mapbox.maps.extension.compose.style.slotsContent
import com.mapbox.maps.extension.compose.style.styleImportsConfig
import com.mapbox.maps.extension.compose.style.terrain.generated.TerrainState
import com.mapbox.maps.extension.style.expressions.generated.Expression

/**
 * Define the lightPreset style import config for [MapboxStandardStyle].
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LightPresetValue(public val value: Value) {
  /**
   * Construct the [LightPresetValue] with [String].
   */
  public constructor(value: String) : this(Value(value))

  /**
   * Construct the [LightPresetValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "lightPreset"

    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: LightPresetValue = LightPresetValue(Value.valueOf("LightPresetValue.INITIAL"))

    /**
     * Default value for [LightPresetValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: LightPresetValue = LightPresetValue(Value.nullValue())

    /**
     * The light preset for "day".
     */
    @JvmField
    public val DAY: LightPresetValue = LightPresetValue("day")

    /**
     * The light preset for "night".
     */
    @JvmField
    public val NIGHT: LightPresetValue = LightPresetValue("night")

    /**
     * The light preset for "dusk".
     */
    @JvmField
    public val DUSK: LightPresetValue = LightPresetValue("dusk")

    /**
     * The light preset for "dawn".
     */
    @JvmField
    public val DAWN: LightPresetValue = LightPresetValue("dawn")
  }
}

/**
 * The convenient composable function to set a Mapbox Standard style to the map, with available slots
 * and config options.
 *
 * @param topSlot The content to be set to the top slot of the Mapbox Standard style.
 * @param middleSlot The content to be set to the middle slot of the Mapbox Standard style.
 * @param bottomSlot The content to be set to the bottom slot of the Mapbox Standard style.
 * @param lightPreset The [LightPresetValue] settings of the Mapbox Standard Style, available lightPresets including "day", "night", "dawn", "dusk".
 * @param projection The projection to be set to the map. Defaults to [Projection.default] meaning that projection value is taken from the Standard style definition.
 * @param atmosphereState The atmosphere to be set to the map By default, no changes to the current atmosphere.
 * @param terrainState The terrain to be set to the map. Defaults to initial state meaning no custom terrain is added, default value is taken from Standard style definition.
 */
@Composable
@MapboxStyleComposable
@MapboxExperimental
public fun MapboxStandardStyle(
  topSlot: (@Composable @MapboxMapComposable () -> Unit)? = null,
  middleSlot: (@Composable @MapboxMapComposable () -> Unit)? = null,
  bottomSlot: (@Composable @MapboxMapComposable () -> Unit)? = null,
  lightPreset: LightPresetValue = LightPresetValue.INITIAL,
  projection: Projection = Projection.INITIAL,
  atmosphereState: AtmosphereState = remember { AtmosphereState() },
  terrainState: TerrainState = TerrainState.INITIAL,
) {
  GenericStyle(
    style = Style.STANDARD,
    slotsContent = slotsContent {
      topSlot?.let { slot("top", it) }
      middleSlot?.let { slot("middle", it) }
      bottomSlot?.let { slot("bottom", it) }
    },
    styleImportsConfig = styleImportsConfig {
      if (lightPreset.notInitial) {
        importConfig(importId = "basemap") {
          config(LightPresetValue.NAME, lightPreset.value)
        }
      }
    },
    projection = projection,
    atmosphereState = atmosphereState,
    terrainState = terrainState,
  )
}