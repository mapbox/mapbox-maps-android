package com.mapbox.maps.compose.testapp.examples.style

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.StringValue
import com.mapbox.maps.extension.compose.style.standard.LightPresetValue
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardSatelliteStyle
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyleExperimental
import com.mapbox.maps.extension.compose.style.standard.ThemeValue
import com.mapbox.maps.extension.compose.style.standard.rememberExperimentalStandardStyleState
import com.mapbox.maps.extension.style.utils.transition

/**
 * Example to showcase usage of the configs of `MapboxStandardStyle`.
 */
public class StandardStyleActivity : ComponentActivity() {
  @OptIn(MapboxExperimental::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var isStandardSatellite by remember {
        mutableStateOf(false)
      }
      var enablePlaceLabels by remember {
        mutableStateOf(true)
      }
      var enableRoadLabels by remember {
        mutableStateOf(true)
      }
      var enablePointOfInterestLabels by remember {
        mutableStateOf(true)
      }
      var enableTransitLabels by remember {
        mutableStateOf(true)
      }
      var selectedLightPreset by remember {
        mutableStateOf(LightPresetValue.DAY)
      }
      var selectedFont by remember {
        mutableStateOf("DIN Pro")
      }
      var selectedTheme by remember {
        mutableStateOf(ThemeValue.DEFAULT)
      }
      var enable3dObjects by remember {
        mutableStateOf(true)
      }
      var enableRoadsAndTransit by remember {
        mutableStateOf(true)
      }
      var enablePedestrianRoads by remember {
        mutableStateOf(true)
      }

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column(
              modifier = Modifier.width(IntrinsicSize.Max)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth()
              ) {
                FloatingActionButton(
                  modifier = Modifier
                    .defaultMinSize(
                      minWidth = ButtonDefaults.MinWidth,
                      minHeight = ButtonDefaults.MinHeight
                    )
                    .padding(end = 4.dp),
                  onClick = {
                    enablePlaceLabels = !enablePlaceLabels
                  },
                  shape = RoundedCornerShape(16.dp),
                ) {
                  Text(
                    modifier = Modifier.padding(6.dp),
                    text = "showPlaceLabels: $enablePlaceLabels"
                  )
                }
                FloatingActionButton(
                  modifier = Modifier
                  .defaultMinSize(
                    minWidth = ButtonDefaults.MinWidth,
                    minHeight = ButtonDefaults.MinHeight
                  ),
                  onClick = {
                    enableRoadLabels = !enableRoadLabels
                  },
                  shape = RoundedCornerShape(16.dp),
                ) {
                  Text(
                    modifier = Modifier.padding(6.dp),
                    text = "showRoadLabels: $enableRoadLabels"
                  )
                }
              }
              Row(
                modifier = Modifier.fillMaxWidth()
              ) {
                FloatingActionButton(
                modifier = Modifier
                  .defaultMinSize(
                    minWidth = ButtonDefaults.MinWidth,
                    minHeight = ButtonDefaults.MinHeight
                  )
                  .padding(end = 4.dp),
                  onClick = {
                    enableTransitLabels = !enableTransitLabels
                  },
                  shape = RoundedCornerShape(16.dp),
                ) {
                  Text(
                    modifier = Modifier.padding(6.dp),
                    text = "showTransitLabels: $enableTransitLabels"
                  )
                }
                FloatingActionButton(
                  modifier = Modifier
                    .defaultMinSize(
                      minWidth = ButtonDefaults.MinWidth,
                      minHeight = ButtonDefaults.MinHeight
                    ),
                  onClick = {
                    selectedFont = when (selectedFont) {
                      "DIN Pro" -> "Roboto"
                      else -> "DIN Pro"
                    }
                  },
                  shape = RoundedCornerShape(16.dp),
                ) {
                  Text(
                    modifier = Modifier.padding(6.dp),
                    text = "font: $selectedFont"
                  )
                }
              }
              FloatingActionButton(
                modifier = Modifier
                  .defaultMinSize(
                    minWidth = ButtonDefaults.MinWidth,
                    minHeight = ButtonDefaults.MinHeight
                  ),
                onClick = {
                  enablePointOfInterestLabels = !enablePointOfInterestLabels
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(6.dp),
                  text = "showPointOfInterestLabels: $enablePointOfInterestLabels"
                )
              }
              Row(
                modifier = Modifier.fillMaxWidth()
              ) {
                FloatingActionButton(
                  modifier = Modifier
                      .defaultMinSize(
                          minWidth = ButtonDefaults.MinWidth,
                          minHeight = ButtonDefaults.MinHeight
                      )
                      .padding(end = 4.dp),
                  onClick = {
                    selectedLightPreset = when (selectedLightPreset) {
                      LightPresetValue.DAY -> LightPresetValue.DUSK
                      LightPresetValue.DUSK -> LightPresetValue.NIGHT
                      LightPresetValue.NIGHT -> LightPresetValue.DAWN
                      LightPresetValue.DAWN -> LightPresetValue.DAY
                      else -> LightPresetValue.NIGHT
                    }
                  },
                  shape = RoundedCornerShape(16.dp),
                ) {
                  Text(
                    modifier = Modifier.padding(6.dp),
                    text = "lightPreset: ${selectedLightPreset.presetNameOrNull}"
                  )
                }
                FloatingActionButton(
                  modifier = Modifier
                      .defaultMinSize(
                          minWidth = ButtonDefaults.MinWidth,
                          minHeight = ButtonDefaults.MinHeight
                      )
                      .padding(end = 4.dp),
                  onClick = {
                    if (!isStandardSatellite) {
                      selectedTheme = when (selectedTheme) {
                        ThemeValue.DEFAULT -> ThemeValue.FADED
                        ThemeValue.FADED -> ThemeValue.MONOCHROME
                        ThemeValue.MONOCHROME -> ThemeValue.DEFAULT
                        else -> ThemeValue.DEFAULT
                      }
                    }
                  },
                  shape = RoundedCornerShape(16.dp),
                  backgroundColor = if (isStandardSatellite) Color.Gray else MaterialTheme.colors.secondary,
                ) {
                  Text(
                    modifier = Modifier.padding(6.dp),
                    text = "selectedTheme: ${selectedTheme.presetNameOrNull}"
                  )
                }
              }
              Row(
                modifier = Modifier.fillMaxWidth()
              ) {
                FloatingActionButton(
                  modifier = Modifier
                      .defaultMinSize(
                          minWidth = ButtonDefaults.MinWidth,
                          minHeight = ButtonDefaults.MinHeight
                      )
                      .padding(end = 4.dp),
                  onClick = {
                    if (!isStandardSatellite) {
                      enable3dObjects = !enable3dObjects
                    }
                  },
                  shape = RoundedCornerShape(16.dp),
                  backgroundColor = if (isStandardSatellite) Color.Gray else MaterialTheme.colors.secondary,
                ) {
                  Text(
                    modifier = Modifier.padding(6.dp),
                    text = "show3dObjects:$enable3dObjects"
                  )
                }
                FloatingActionButton(
                  modifier = Modifier
                    .defaultMinSize(
                      minWidth = ButtonDefaults.MinWidth,
                      minHeight = ButtonDefaults.MinHeight
                    ),
                  onClick = {
                    isStandardSatellite = !isStandardSatellite
                  },
                  shape = RoundedCornerShape(16.dp),
                ) {
                  Text(
                    modifier = Modifier.padding(6.dp),
                    text = "selectedStyle:${
                      if (isStandardSatellite) {
                        "STND_SATELLITE"
                      } else {
                        "STANDARD"
                      }
                    }"
                  )
                }
              }
              Row(
                modifier = Modifier.fillMaxWidth()
              ) {
                FloatingActionButton(
                  modifier = Modifier
                      .defaultMinSize(
                          minWidth = ButtonDefaults.MinWidth,
                          minHeight = ButtonDefaults.MinHeight
                      )
                      .padding(end = 4.dp),
                  onClick = {
                    if (isStandardSatellite) {
                      enableRoadsAndTransit = !enableRoadsAndTransit
                    }
                  },
                  shape = RoundedCornerShape(16.dp),
                  backgroundColor = if (!isStandardSatellite) Color.Gray else MaterialTheme.colors.secondary,
                ) {
                  Text(
                    modifier = Modifier.padding(6.dp),
                    text = "roadsAndTransit: $enableRoadsAndTransit"
                  )
                }
                FloatingActionButton(
                  modifier = Modifier
                    .defaultMinSize(
                      minWidth = ButtonDefaults.MinWidth,
                      minHeight = ButtonDefaults.MinHeight
                    ),
                  onClick = {
                    if (isStandardSatellite) {
                      enablePedestrianRoads = !enablePedestrianRoads
                    }
                  },
                  shape = RoundedCornerShape(16.dp),
                  backgroundColor = if (!isStandardSatellite) Color.Gray else MaterialTheme.colors.secondary,
                ) {
                  Text(
                    modifier = Modifier.padding(6.dp),
                    text = "pedestrianRoads: $enablePedestrianRoads"
                  )
                }
              }
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions {
                zoom(ZOOM)
                center(CityLocations.HELSINKI)
                pitch(40.0)
              }
            },
            style =
            {
              if (isStandardSatellite) {
                MapboxStandardSatelliteStyle(
                  styleTransition = remember {
                    transition {
                      duration(1_000)
                      enablePlacementTransitions(true)
                    }
                  }
                ) {
                  showPlaceLabels = BooleanValue(enablePlaceLabels)
                  showRoadLabels = BooleanValue(enableRoadLabels)
                  showPointOfInterestLabels = BooleanValue(enablePointOfInterestLabels)
                  showTransitLabels = BooleanValue(enableTransitLabels)
                  lightPreset = selectedLightPreset
                  font = StringValue(selectedFont)
                  showRoadsAndTransit = BooleanValue(enableRoadsAndTransit)
                  showPedestrianRoads = BooleanValue(enablePedestrianRoads)
                }
              } else {
                MapboxStandardStyleExperimental(
                  experimentalStandardStyleState = rememberExperimentalStandardStyleState {
                    interactionsState.onBuildingsClicked { clickedBuilding, _ ->
                      clickedBuilding.setStandardBuildingsState {
                        highlight(true)
                      }
                      return@onBuildingsClicked true
                    }
                    styleTransition = transition {
                      duration(1_000)
                      enablePlacementTransitions(true)
                    }
                    configurationsState.apply {
                      showPlaceLabels = BooleanValue(enablePlaceLabels)
                      showRoadLabels = BooleanValue(enableRoadLabels)
                      showPointOfInterestLabels = BooleanValue(enablePointOfInterestLabels)
                      showTransitLabels = BooleanValue(enableTransitLabels)
                      lightPreset = selectedLightPreset
                      font = StringValue(selectedFont)
                      theme = selectedTheme
                      show3dObjects = BooleanValue(enable3dObjects)
                    }
                  }
                )
              }
            }
          )
        }
      }
    }
  }

  private companion object {
    const val ZOOM: Double = 9.0
  }
}