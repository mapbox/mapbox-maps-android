package com.mapbox.maps.extension.compose.style.standard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.extension.compose.style.MapboxStyleComposable
import com.mapbox.maps.extension.compose.style.atmosphere.generated.AtmosphereState
import com.mapbox.maps.extension.compose.style.projection.Projection
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
public data class LightPreset(public val value: Value) {
  /**
   * Construct the LightPreset with [String].
   */
  public constructor(value: String) : this(Value(value))

  /**
   * Construct the LightPreset with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "lightPreset"

    /**
     * Default value for [LightPreset], setting default will result in restoring the property value defined in the style.
     */
    public val default: LightPreset = LightPreset(Value.nullValue())

    /**
     * The light preset for "day".
     */
    @JvmField
    public val DAY: LightPreset = LightPreset("day")

    /**
     * The light preset for "night".
     */
    @JvmField
    public val NIGHT: LightPreset = LightPreset("night")

    /**
     * The light preset for "dusk".
     */
    @JvmField
    public val DUSK: LightPreset = LightPreset("dusk")

    /**
     * The light preset for "dawn".
     */
    @JvmField
    public val DAWN: LightPreset = LightPreset("dawn")
  }
}

/**
 * The convenient composable function to set a Mapbox Standard style to the map, with available slots
 * and config options.
 *
 * @param topSlot The content to be set to the top slot of the Mapbox Standard style.
 * @param middleSlot The content to be set to the middle slot of the Mapbox Standard style.
 * @param bottomSlot The content to be set to the bottom slot of the Mapbox Standard style.
 * @param lightPreset The [LightPreset] settings of the Mapbox Standard Style, available lightPresets including "day", "night", "dawn", "dusk".
 * @param projection The projection to be set to the map. Defaults to [Projection.default] meaning that projection value is taken from the Standard style definition.
 * @param atmosphereState The atmosphere to be set to the map. Defaults to [AtmosphereState.default] meaning that atmosphere is the default defined in Standard style definition.
 * @param terrainState The terrain to be set to the map. Defaults to initial state meaning no custom terrain is added, default value is taken from Standard style definition.
 */
@Composable
@MapboxStyleComposable
@MapboxExperimental
public fun MapboxStandardStyle(
  topSlot: (@Composable @MapboxMapComposable () -> Unit)? = null,
  middleSlot: (@Composable @MapboxMapComposable () -> Unit)? = null,
  bottomSlot: (@Composable @MapboxMapComposable () -> Unit)? = null,
  lightPreset: LightPreset = LightPreset.default,
  projection: Projection = Projection.default,
  atmosphereState: AtmosphereState = AtmosphereState.default,
  terrainState: TerrainState = TerrainState.initial,
) {
  GenericStyle(
    style = Style.STANDARD,
    slotsContent = slotsContent {
      topSlot?.let { slot("top", it) }
      middleSlot?.let { slot("middle", it) }
      bottomSlot?.let { slot("bottom", it) }
    },
    styleImportsConfig = styleImportsConfig {
      importConfig(importId = "basemap") {
        config(LightPreset.NAME, lightPreset.value)
      }
    },
    projection = projection,
    atmosphereState = atmosphereState,
    terrainState = terrainState,
  )
}