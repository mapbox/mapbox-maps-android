package com.mapbox.maps.compose.testapp.examples.style

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle

/**
 * Example to showcase usage of the configs of `MapboxStandardStyle`.
 */
@OptIn(MapboxExperimental::class)
public class StandardStyleActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
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

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column {
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  enablePlaceLabels = !enablePlaceLabels
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "showPlaceLabels: $enablePlaceLabels"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  enableRoadLabels = !enableRoadLabels
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "showRoadLabels: $enableRoadLabels"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  enablePointOfInterestLabels = !enablePointOfInterestLabels
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "showPointOfInterestLabels: $enablePointOfInterestLabels"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  enableTransitLabels = !enableTransitLabels
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "showTransitLabels: $enableTransitLabels"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
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
                  modifier = Modifier.padding(10.dp),
                  text = "lightPreset: $selectedLightPreset"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  selectedFont = when (selectedFont) {
                    "DIN Pro" -> "Roboto"
                    else -> "DIN Pro"
                  }
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "font: $selectedFont"
                )
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
              }
            },
            style = {
              MapboxStandardStyle {
                showPlaceLabels = BooleanValue(enablePlaceLabels)
                showRoadLabels = BooleanValue(enableRoadLabels)
                showPointOfInterestLabels = BooleanValue(enablePointOfInterestLabels)
                showTransitLabels = BooleanValue(enableTransitLabels)
                lightPreset = selectedLightPreset
                font = StringValue(selectedFont)
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