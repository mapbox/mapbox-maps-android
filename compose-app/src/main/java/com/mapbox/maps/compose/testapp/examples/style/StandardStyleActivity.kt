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
      var showPlaceLabels by remember {
        mutableStateOf(true)
      }
      var showRoadLabels by remember {
        mutableStateOf(true)
      }
      var showPointOfInterestLabels by remember {
        mutableStateOf(true)
      }
      var showTransitLabels by remember {
        mutableStateOf(true)
      }
      var lightPreset by remember {
        mutableStateOf(LightPresetValue.DAY)
      }
      var font by remember {
        mutableStateOf("DIN Pro")
      }

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column {
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  showPlaceLabels = !showPlaceLabels
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "showPlaceLabels: $showPlaceLabels"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  showRoadLabels = !showRoadLabels
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "showRoadLabels: $showRoadLabels"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  showPointOfInterestLabels = !showPointOfInterestLabels
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "showPointOfInterestLabels: $showPointOfInterestLabels"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  showTransitLabels = !showTransitLabels
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "showTransitLabels: $showTransitLabels"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  lightPreset = when (lightPreset) {
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
                  text = "lightPreset: $lightPreset"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  font = when (font) {
                    "DIN Pro" -> "Roboto"
                    else -> "DIN Pro"
                  }
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "font: $font"
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
              MapboxStandardStyle(
                showPlaceLabels = BooleanValue(showPlaceLabels),
                showRoadLabels = BooleanValue(showRoadLabels),
                showPointOfInterestLabels = BooleanValue(showPointOfInterestLabels),
                showTransitLabels = BooleanValue(showTransitLabels),
                lightPreset = lightPreset,
                font = StringValue(font)
              )
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