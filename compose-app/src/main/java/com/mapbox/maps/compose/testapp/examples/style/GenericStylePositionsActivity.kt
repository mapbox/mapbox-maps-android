package com.mapbox.maps.compose.testapp.examples.style

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.extension.compose.style.MapboxStyleComposable
import com.mapbox.maps.extension.compose.style.layerPositionedContent
import com.mapbox.maps.extension.compose.style.layers.generated.BackgroundColor
import com.mapbox.maps.extension.compose.style.layers.generated.BackgroundLayer
import com.mapbox.maps.extension.compose.style.layers.generated.BackgroundOpacity
import com.mapbox.maps.extension.compose.style.layers.generated.CircleColor
import com.mapbox.maps.extension.compose.style.layers.generated.CircleLayer
import com.mapbox.maps.extension.compose.style.layers.generated.CircleRadius
import com.mapbox.maps.extension.compose.style.layers.generated.Transition
import com.mapbox.maps.extension.compose.style.slotsContent
import com.mapbox.maps.extension.compose.style.sources.generated.GeoJSONData
import com.mapbox.maps.extension.compose.style.sources.generated.rememberGeoJsonSourceState
import com.mapbox.maps.extension.compose.style.styleImportsConfig

/**
 * Example to showcase the usage of [slotsContent] and [layerPositionedContent] to arrange contents within the
 * base style using [GenericStyle] API.
 */
@OptIn(MapboxExperimental::class)
public class GenericStylePositionsActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var styleUri by remember {
        mutableStateOf(Style.LIGHT)
      }

      var showTopSlotContent by remember {
        mutableStateOf(true)
      }

      var showMiddleSlotContent by remember {
        mutableStateOf(true)
      }

      var showLayerPositionedContent by remember {
        mutableStateOf(true)
      }

      var lightPresetConfig by remember {
        mutableStateOf("day")
      }

      val mapViewportState = rememberMapViewportState {
        setCameraOptions {
          zoom(ZOOM)
          center(CityLocations.HELSINKI)
        }
      }

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column {
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                backgroundColor = if (styleUri == Style.STANDARD) MaterialTheme.colors.secondary else Color.LightGray,
                onClick = {
                  showTopSlotContent = !showTopSlotContent
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "Toggle top slot content - current: $showTopSlotContent"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                backgroundColor = if (styleUri == Style.STANDARD) MaterialTheme.colors.secondary else Color.LightGray,
                onClick = {
                  showMiddleSlotContent = !showMiddleSlotContent
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "Toggle middle slot content - current: $showMiddleSlotContent"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                backgroundColor = if (styleUri == Style.STANDARD) MaterialTheme.colors.secondary else Color.LightGray,
                onClick = {
                  lightPresetConfig = if (lightPresetConfig == "day") "night" else "day"
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "Toggle light preset - current: $lightPresetConfig"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                backgroundColor = if (styleUri != Style.STANDARD) MaterialTheme.colors.secondary else Color.LightGray,
                onClick = {
                  showLayerPositionedContent = !showLayerPositionedContent
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "Toggle positioned layer content - current $showLayerPositionedContent"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  styleUri = if (styleUri == Style.LIGHT) {
                    Style.STANDARD
                  } else {
                    Style.LIGHT
                  }
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "Toggle Style - current: ${styleUri.substringAfterLast('/')}"
                )
              }
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = mapViewportState,
            style = {
              MyStyle(
                styleUri = styleUri,
                showTopSlotContent = showTopSlotContent,
                showMiddleSlotContent = showMiddleSlotContent,
                showLayerPositionedContent = showLayerPositionedContent,
                lightPresetConfig = lightPresetConfig,
              )
            }
          )
        }
      }
    }
  }

  @Composable
  @MapboxStyleComposable
  private fun MyStyle(
    styleUri: String,
    showTopSlotContent: Boolean,
    showMiddleSlotContent: Boolean,
    showLayerPositionedContent: Boolean,
    lightPresetConfig: String
  ) {
    val geoJsonSource = rememberGeoJsonSourceState {
      data = GeoJSONData(CityLocations.HELSINKI)
    }
    GenericStyle(
      style = styleUri,
      slotsContent = slotsContent {
        // Only add background layer in slots for standard style, where the top and middle slot
        // are available.
        if (styleUri == Style.STANDARD) {
          if (showTopSlotContent) {
            slot("top") {
              CircleLayer(
                sourceState = geoJsonSource,
                circleColor = CircleColor(Color.Cyan),
                circleRadius = CircleRadius(50.0),
                circleRadiusTransition = Transition(duration = 1000L)
              )
            }
          }
          if (showMiddleSlotContent) {
            slot("middle") {
              BackgroundLayer(
                backgroundColor = BackgroundColor(Color.Red),
                backgroundOpacity = BackgroundOpacity(0.3)
              )
            }
          }
        }
      },
      layerPositionedContent = layerPositionedContent {
        // only add background layer below building layer if the style is not standard
        // style, where layers are available in runtime styling.
        if (styleUri != Style.STANDARD) {
          if (showLayerPositionedContent) {
            belowLayer("building") {
              BackgroundLayer(
                backgroundColor = BackgroundColor(Color.Yellow),
                backgroundOpacity = BackgroundOpacity(0.3)
              )
            }
          }
        }
      },
      styleImportsConfig = styleImportsConfig {
        importConfig("basemap") {
          config("lightPreset", Value(lightPresetConfig))
        }
      }
    )
  }

  private companion object {
    const val ZOOM: Double = 9.0
  }
}