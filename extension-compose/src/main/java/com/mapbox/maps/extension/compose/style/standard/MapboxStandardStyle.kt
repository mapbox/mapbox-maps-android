package com.mapbox.maps.extension.compose.style.standard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.mapbox.maps.Style
import com.mapbox.maps.TransitionOptions
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.extension.compose.style.MapboxStyleComposable
import com.mapbox.maps.extension.compose.style.atmosphere.generated.AtmosphereState
import com.mapbox.maps.extension.compose.style.imports.MapboxStyleImportComposable
import com.mapbox.maps.extension.compose.style.imports.StyleImportsScope
import com.mapbox.maps.extension.compose.style.lights.LightsState
import com.mapbox.maps.extension.compose.style.projection.generated.Projection
import com.mapbox.maps.extension.compose.style.rememberStyleState
import com.mapbox.maps.extension.compose.style.slotsContent
import com.mapbox.maps.extension.compose.style.styleImportsConfig
import com.mapbox.maps.extension.compose.style.terrain.generated.TerrainState
import com.mapbox.maps.extension.style.utils.transition

/**
 * The convenient composable function to set a Mapbox Standard style to the map, with available slots
 * and config options.
 *
 * @param styleImportsContent The style imports to be added to the current style, note layers and annotations shouldn't be added to this block.
 * @param topSlot The content to be set to the top slot of the Mapbox Standard style.
 * @param middleSlot The content to be set to the middle slot of the Mapbox Standard style.
 * @param bottomSlot The content to be set to the bottom slot of the Mapbox Standard style.
 * @param projection The projection to be set to the map. Defaults to [Projection.DEFAULT] meaning that projection value is taken from the Standard style definition.
 * @param atmosphereState The atmosphere to be set to the map By default, no changes to the current atmosphere.
 * @param terrainState The terrain to be set to the map. Defaults to initial state meaning no custom terrain is added, default value is taken from Standard style definition.
 * @param lightsState The lights to be set to the map. By default, no changes to the current lights defined in style.
 * @param styleTransition Transition options applied when loading the style.
 * @param standardStyleConfigurationState The configurations for [MapboxStandardStyle].
 */
@Composable
@MapboxStyleComposable
public fun MapboxStandardStyle(
  styleImportsContent: (@Composable @MapboxStyleImportComposable StyleImportsScope.() -> Unit)? = null,
  topSlot: (@Composable @MapboxMapComposable () -> Unit)? = null,
  middleSlot: (@Composable @MapboxMapComposable () -> Unit)? = null,
  bottomSlot: (@Composable @MapboxMapComposable () -> Unit)? = null,
  projection: Projection = Projection.INITIAL,
  atmosphereState: AtmosphereState = remember { AtmosphereState() },
  terrainState: TerrainState = TerrainState.INITIAL,
  lightsState: LightsState = LightsState.INITIAL,
  styleTransition: TransitionOptions = remember { transition { } },
  standardStyleConfigurationState: StandardStyleConfigurationState = remember {
    StandardStyleConfigurationState()
  }
) {
  GenericStyle(
    style = Style.STANDARD,
    styleImportsContent = styleImportsContent,
    slotsContent = slotsContent {
      topSlot?.let { slot("top", it) }
      middleSlot?.let { slot("middle", it) }
      bottomSlot?.let { slot("bottom", it) }
    },
    styleState = rememberStyleState {
      styleImportsConfig = styleImportsConfig {
        importConfig(importId = "basemap") {
          with(standardStyleConfigurationState) {
            if (showPlaceLabels.notInitial) {
              config(BaseStyleConfigurationState.CONFIG_SHOW_PLACE_LABELS, showPlaceLabels.value)
            }
            if (showRoadLabels.notInitial) {
              config(BaseStyleConfigurationState.CONFIG_SHOW_ROAD_LABELS, showRoadLabels.value)
            }
            if (showPointOfInterestLabels.notInitial) {
              config(
                BaseStyleConfigurationState.CONFIG_SHOW_POINT_OF_INTEREST_LABELS,
                showPointOfInterestLabels.value
              )
            }
            if (showTransitLabels.notInitial) {
              config(
                BaseStyleConfigurationState.CONFIG_SHOW_TRANSIT_LABELS,
                showTransitLabels.value
              )
            }
            if (lightPreset.notInitial) {
              config(BaseStyleConfigurationState.CONFIG_LIGHT_PRESET, lightPreset.value)
            }
            if (font.notInitial) {
              config(BaseStyleConfigurationState.CONFIG_FONT, font.value)
            }
            if (show3dObjects.notInitial) {
              config(StandardStyleConfigurationState.CONFIG_SHOW_3D_OBJECTS, show3dObjects.value)
            }
            if (theme.notInitial) {
              config(StandardStyleConfigurationState.CONFIG_THEME, theme.value)
            }
          }
        }
      }
      this.projection = projection
      this.atmosphereState = atmosphereState
      this.terrainState = terrainState
      this.lightsState = lightsState
      this.styleTransition = styleTransition
    },
  )
}

/**
 * The convenient composable function to set a Mapbox Standard style to the map, with available slots
 * and config options.
 *
 * @param styleImportsContent The style imports to be added to the current style, note layers and annotations shouldn't be added to this block.
 * @param topSlot The content to be set to the top slot of the Mapbox Standard style.
 * @param middleSlot The content to be set to the middle slot of the Mapbox Standard style.
 * @param bottomSlot The content to be set to the bottom slot of the Mapbox Standard style.
 * @param projection The projection to be set to the map. Defaults to [Projection.DEFAULT] meaning that projection value is taken from the Standard style definition.
 * @param atmosphereState The atmosphere to be set to the map By default, no changes to the current atmosphere.
 * @param terrainState The terrain to be set to the map. Defaults to initial state meaning no custom terrain is added, default value is taken from Standard style definition.
 * @param lightsState The lights to be set to the map. By default, no changes to the current lights defined in style.
 * @param styleTransition Transition options applied when loading the style.
 * @param init The lambda that will be applied to the remembered [StandardStyleConfigurationState].
 */
@Composable
@MapboxStyleComposable
public fun MapboxStandardStyle(
  styleImportsContent: (@Composable @MapboxStyleImportComposable StyleImportsScope.() -> Unit)? = null,
  topSlot: (@Composable @MapboxMapComposable () -> Unit)? = null,
  middleSlot: (@Composable @MapboxMapComposable () -> Unit)? = null,
  bottomSlot: (@Composable @MapboxMapComposable () -> Unit)? = null,
  projection: Projection = Projection.INITIAL,
  atmosphereState: AtmosphereState = remember { AtmosphereState() },
  terrainState: TerrainState = TerrainState.INITIAL,
  lightsState: LightsState = LightsState.INITIAL,
  styleTransition: TransitionOptions = remember { transition { } },
  init: StandardStyleConfigurationState.() -> Unit
) {
  MapboxStandardStyle(
    styleImportsContent = styleImportsContent,
    topSlot = topSlot,
    middleSlot = middleSlot,
    bottomSlot = bottomSlot,
    projection = projection,
    atmosphereState = atmosphereState,
    terrainState = terrainState,
    lightsState = lightsState,
    styleTransition = styleTransition,
    standardStyleConfigurationState = remember {
      StandardStyleConfigurationState()
    }.apply(init)
  )
}