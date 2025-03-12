package com.mapbox.maps.extension.compose.style.standard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.key
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.extension.compose.style.MapboxStyleComposable
import com.mapbox.maps.extension.compose.style.imports.MapboxStyleImportComposable
import com.mapbox.maps.extension.compose.style.imports.StyleImportsScope
import com.mapbox.maps.extension.compose.style.rememberStyleState
import com.mapbox.maps.extension.compose.style.slotsContent
import com.mapbox.maps.extension.compose.style.styleImportsConfig

/**
 * The convenient composable function to set a Mapbox Standard style to the map, with available slots
 * and config options.
 *
 * **Important: this style should not be used in production as the style definition on backend is a subject to change after v11.8.0 stable release!**
 *
 * @param styleImportsContent The style imports to be added to the current style, note layers and annotations shouldn't be added to this block.
 * @param topSlot The content to be set to the top slot of the Mapbox Standard style.
 * @param middleSlot The content to be set to the middle slot of the Mapbox Standard style.
 * @param bottomSlot The content to be set to the bottom slot of the Mapbox Standard style.
 * @param experimentalStandardStyleState The state holder for the experimental Mapbox Standard Style.
 */
@Composable
@MapboxStyleComposable
@MapboxExperimental
public fun MapboxStandardStyleExperimental(
  styleImportsContent: (@Composable @MapboxStyleImportComposable StyleImportsScope.() -> Unit)? = null,
  topSlot: (@Composable @MapboxMapComposable () -> Unit)? = null,
  middleSlot: (@Composable @MapboxMapComposable () -> Unit)? = null,
  bottomSlot: (@Composable @MapboxMapComposable () -> Unit)? = null,
  experimentalStandardStyleState: ExperimentalStandardStyleState = rememberExperimentalStandardStyleState(),
) {
  GenericStyle(
    style = Style.STANDARD_EXPERIMENTAL,
    styleImportsContent = styleImportsContent,
    slotsContent = slotsContent {
      topSlot?.let { slot("top", it) }
      middleSlot?.let { slot("middle", it) }
      bottomSlot?.let { slot("bottom", it) }
    },
    styleState = rememberStyleState {
      styleImportsConfig = styleImportsConfig {
        importConfig(importId = "basemap") {
          with(experimentalStandardStyleState.configurationsState) {
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
      this.projection = experimentalStandardStyleState.projection
      this.atmosphereState = experimentalStandardStyleState.atmosphereState
      this.terrainState = experimentalStandardStyleState.terrainState
      this.lightsState = experimentalStandardStyleState.lightsState
      this.styleTransition = experimentalStandardStyleState.styleTransition
    }
  )
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of Style composable outside of MapboxMapComposable")
  key(experimentalStandardStyleState.interactionsState) {
    experimentalStandardStyleState.interactionsState.BindTo(mapboxMap = mapApplier.mapView.mapboxMap)
  }
}