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
import com.mapbox.maps.Style
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.examples.utils.offset
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.extension.compose.style.MapboxStyleComposable
import com.mapbox.maps.extension.compose.style.Transition
import com.mapbox.maps.extension.compose.style.layerPositionedContent
import com.mapbox.maps.extension.compose.style.layers.generated.BackgroundLayer
import com.mapbox.maps.extension.compose.style.layers.generated.CircleLayer
import com.mapbox.maps.extension.compose.style.rememberStyleState
import com.mapbox.maps.extension.compose.style.slotsContent
import com.mapbox.maps.extension.compose.style.sources.GeoJSONData
import com.mapbox.maps.extension.compose.style.sources.generated.rememberGeoJsonSourceState
import com.mapbox.maps.extension.compose.style.styleImportsConfig

/**
 * Example to showcase the usage of [slotsContent] and [layerPositionedContent] to arrange contents within the
 * base style using [GenericStyle] API.
 */
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

      var showBottomAnnotation by remember {
        mutableStateOf(true)
      }

      var showMiddleCircleLayer by remember {
        mutableStateOf(true)
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
              if (styleUri == Style.STANDARD) {
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
              } else {
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
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  showBottomAnnotation = !showBottomAnnotation
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "Toggle bottom annotation - current: $showBottomAnnotation"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  showMiddleCircleLayer = !showMiddleCircleLayer
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "Toggle middle annotation - current: $showMiddleCircleLayer"
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
          ) {
            if (showBottomAnnotation) {
              CircleAnnotation(
                point = CityLocations.HELSINKI.offset(0.005),
              ) {
                circleRadius = 30.0
                circleColor = Color.Red
              }
            }
            if (showMiddleCircleLayer) {
              CircleLayer(
                sourceState = rememberGeoJsonSourceState {
                  data = GeoJSONData(CityLocations.HELSINKI)
                }
              ) {
                circleColor = ColorValue(Color.Yellow)
                circleRadius = DoubleValue(30.0)
              }
            }
            CircleAnnotation(
              point = CityLocations.HELSINKI.offset(-0.005)
            ) {
              circleRadius = 30.0
              circleColor = Color.Blue
            }
          }
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
    GenericStyle(
      style = styleUri,
      slotsContent = slotsContent {
        // Only add slots contents for standard style, where the top and middle slot are available.
        if (styleUri == Style.STANDARD) {
          if (showTopSlotContent) {
            slot("top") {
              CircleAnnotation(
                point = CityLocations.HELSINKI.offset(0.008),
              ) {
                circleRadius = 30.0
                circleColor = Color.Magenta
              }
              CircleLayer(
                sourceState = rememberGeoJsonSourceState {
                  data = GeoJSONData(CityLocations.HELSINKI.offset(-0.0010))
                }
              ) {
                circleColor = ColorValue(Color.DarkGray)
                circleRadius = DoubleValue(30.0)
                circleColorTransition = Transition(durationMillis = 1000L)
              }
              CircleAnnotation(
                point = CityLocations.HELSINKI.offset(-0.008)
              ) {
                circleRadius = 30.0
                circleColor = Color.Green
              }
            }
          }
          if (showMiddleSlotContent) {
            slot("middle") {
              BackgroundLayer {
                backgroundColor = ColorValue(Color.Red)
                backgroundOpacity = DoubleValue(0.3)
              }
            }
          }
        }
      },
      layerPositionedContent = layerPositionedContent {
        // only add background layer below building layer if the style is not standard
        // style, where layers are available in runtime styling.
        if (styleUri != Style.STANDARD) {
          belowLayer("building") {
            if (showLayerPositionedContent) {
              CircleAnnotation(
                point = CityLocations.HELSINKI.offset(0.008),
              ) {
                circleRadius = 30.0
                circleColor = Color.Magenta
              }
              CircleLayer(
                layerId = "middle-circle-layer",
                sourceState = rememberGeoJsonSourceState {
                  data = GeoJSONData(CityLocations.HELSINKI.offset(-0.0016))
                }
              ) {
                circleColor = ColorValue(Color.DarkGray)
                circleRadius = DoubleValue(30.0)
              }
              CircleAnnotation(
                point = CityLocations.HELSINKI.offset(-0.008)
              ) {
                circleRadius = 30.0
                circleColor = Color.Green
              }
            }
          }
        }
      },
      styleState = rememberStyleState {
        styleImportsConfig = styleImportsConfig {
          importConfig("basemap") {
            config("lightPreset", Value(lightPresetConfig))
          }
        }
      }
    )
  }

  private companion object {
    const val ZOOM: Double = 9.0
  }
}