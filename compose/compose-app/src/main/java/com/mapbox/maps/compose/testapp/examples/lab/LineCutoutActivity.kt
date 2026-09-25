package com.mapbox.maps.compose.testapp.examples.lab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Feature
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxDelicateApi
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.DoubleValue
import com.mapbox.maps.extension.compose.style.layers.generated.LineCapValue
import com.mapbox.maps.extension.compose.style.layers.generated.LineJoinValue
import com.mapbox.maps.extension.compose.style.layers.generated.LineLayer
import com.mapbox.maps.extension.compose.style.sources.GeoJSONData
import com.mapbox.maps.extension.compose.style.sources.generated.rememberGeoJsonSourceState
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle

/**
 * Example demonstrating the line cutout API.
 *
 * Shows a route from BMW Headquarters through Olympiapark to Olympiastadion in Munich.
 * Two sliders let you interact with lineCutoutOpacity and lineCutoutFadeWidth in real time.
 */
@OptIn(MapboxExperimental::class)
public class LineCutoutActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var cutoutOpacity by remember { mutableStateOf(0.5f) }
      var fadeWidth by remember { mutableStateOf(0.5f) }

      MapboxMapComposeTheme {
        ExampleScaffold(
          bottomBar = {
            Surface(
              modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 8.dp),
              shape = RoundedCornerShape(14.dp),
              elevation = 4.dp
            ) {
              Column(modifier = Modifier.padding(16.dp)) {
                LineCutoutSlider(
                  title = "Cutout opacity",
                  value = cutoutOpacity,
                  onValueChange = { cutoutOpacity = it }
                )
                LineCutoutSlider(
                  title = "Fade width",
                  value = fadeWidth,
                  onValueChange = { fadeWidth = it }
                )
              }
            }
          }
        ) { scaffoldPadding ->
          LineCutoutExample(
            cutoutOpacity = cutoutOpacity,
            fadeWidth = fadeWidth,
            scaffoldPadding = scaffoldPadding
          )
        }
      }
    }
  }

  @OptIn(MapboxExperimental::class, MapboxDelicateApi::class)
  @Composable
  private fun LineCutoutExample(
    cutoutOpacity: Float,
    fadeWidth: Float,
    scaffoldPadding: PaddingValues
  ) {
    val routeSourceState = rememberGeoJsonSourceState {
      data = GeoJSONData(
        Feature.fromGeometry(LineString.fromLngLats(ROUTE_COORDINATES))
      )
    }

    MapboxMap(
      modifier = Modifier.fillMaxSize(),
      mapViewportState = rememberMapViewportState {
        setCameraOptions(CAMERA_OPTIONS)
      },
      logo = {
        Logo(modifier = Modifier.padding(bottom = scaffoldPadding.calculateBottomPadding()))
      },
      attribution = {
        Attribution(modifier = Modifier.padding(bottom = scaffoldPadding.calculateBottomPadding()))
      },
      style = {
        MapboxStandardStyle(
          middleSlot = {
            // Visible route line
            LineLayer(
              sourceState = routeSourceState,
              layerId = "route-line"
            ) {
              lineCap = LineCapValue.ROUND
              lineJoin = LineJoinValue.ROUND
              lineWidth = DoubleValue(8.0)
              lineColor = ColorValue(Color.Blue)
              lineEmissiveStrength = DoubleValue(1.0)
            }

            // Cutout layer — transparent wide line that makes 3D buildings see-through
            LineLayer(
              sourceState = routeSourceState,
              layerId = "cutout-line"
            ) {
              lineWidth = DoubleValue(40.0)
              lineColor = ColorValue(Color.Transparent)
              lineCutoutOpacity = DoubleValue(cutoutOpacity.toDouble())
              lineCutoutFadeWidth = DoubleValue(fadeWidth.toDouble())
            }
          }
        )
      }
    )
  }

  @Composable
  private fun LineCutoutSlider(
    title: String,
    value: Float,
    onValueChange: (Float) -> Unit
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(title, modifier = Modifier.weight(1.2f))
      Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = 0f..1f,
        modifier = Modifier.weight(2f)
      )
      Text(
        "%.2f".format(value),
        modifier = Modifier.padding(start = 8.dp)
      )
    }
  }

  private companion object {
    // Route from BMW Headquarters through Olympiapark to Olympiastadion, Munich
    val ROUTE_COORDINATES = listOf(
      Point.fromLngLat(11.56024, 48.17691),
      Point.fromLngLat(11.55910, 48.17660),
      Point.fromLngLat(11.55376, 48.17620),
      Point.fromLngLat(11.55153, 48.17355),
      Point.fromLngLat(11.55004, 48.17493),
    )

    val CAMERA_OPTIONS = cameraOptions {
      center(Point.fromLngLat(11.55670, 48.17664))
      zoom(15.48)
      bearing(48.6)
      pitch(76.5)
    }
  }
}