package com.mapbox.maps.compose.testapp.examples.annotation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations.HELSINKI
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.Marker
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.extension.compose.style.standard.rememberStandardStyleState

/**
 * Example to showcase usage of Markers.
 */
public class MarkersActivity : ComponentActivity() {
  @OptIn(MapboxExperimental::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val tappedPoints = remember { mutableStateListOf<Point>() }
      var markerColor by remember { mutableStateOf(Color(0xffcfdaf7)) }
      var strokeColor by remember { mutableStateOf(Color(0xff3a59fa)) }
      var showStroke by remember { mutableStateOf(true) }
      var showText by remember { mutableStateOf(true) }

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            SelectionBox(
              markerColor = markerColor,
              onMarkerColorChange = { markerColor = it },
              strokeColor = strokeColor,
              onStrokeColorChange = { strokeColor = it },
              showText = showText,
              onShowTextToggle = { showText = !showText },
              showStroke = showStroke,
              onShowStrokeToggle = { showStroke = !showStroke }
            )
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions {
                zoom(ZOOM)
                pitch(PITCH)
                center(HELSINKI)
              }
            },
            style = {
              MapboxStandardStyle(
                standardStyleState = rememberStandardStyleState {
                  interactionsState.onMapClicked { context ->
                    tappedPoints.add(context.coordinateInfo.coordinate)
                    return@onMapClicked true
                  }
                  interactionsState.onMapLongClicked { _ ->
                    tappedPoints.clear()
                    return@onMapLongClicked true
                  }
                }
              )
            }
          ) {
            Marker(
              point = HELSINKI,
              color = markerColor,
              stroke = if (showStroke) strokeColor else null,
              text = if (showText) "Central Helsinki" else null
            )
            tappedPoints.forEach { it ->
              Marker(
                point = it,
                color = markerColor,
                stroke = if (showStroke) strokeColor else null,
                text = if (showText) String.format("%.3f, %.3f", it.latitude(), it.longitude()) else null
              )
            }
          }
        }
      }
    }
  }

  private companion object {
    const val ZOOM: Double = 16.0
    const val PITCH: Double = 60.0
  }
}

// Add box to customize the markers
@Composable
private fun SelectionBox(
  markerColor: Color,
  onMarkerColorChange: (Color) -> Unit,
  strokeColor: Color,
  onStrokeColorChange: (Color) -> Unit,
  showText: Boolean,
  onShowTextToggle: () -> Unit,
  showStroke: Boolean,
  onShowStrokeToggle: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 15.dp),
    contentAlignment = Alignment.BottomCenter
  ) {
    Column(
      modifier = Modifier
        .widthIn(max = 340.dp)
        .background(Color(0xCC222222), shape = RoundedCornerShape(16.dp))
        .padding(10.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "Tap map to add a Marker",
        color = Color.White,
        modifier = Modifier.padding(bottom = 4.dp)
      )

      // Marker Color Selector
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 4.dp)
      ) {
        Text(
          text = "Marker:",
          color = Color.White,
          modifier = Modifier.padding(end = 4.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
          colorOptions.forEach { color ->
            Box(
              modifier = Modifier
                .size(20.dp)
                .background(color, shape = RoundedCornerShape(10.dp))
                .clickable { onMarkerColorChange(color) }
                .border(
                  width = if (markerColor == color) 2.dp else 0.dp,
                  color = if (markerColor == color) Color.White else Color.Transparent,
                  shape = RoundedCornerShape(10.dp)
                )
            )
          }
        }
      }

      // Stroke Color Selector
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 2.dp)
      ) {
        Text(
          text = "Stroke:",
          color = Color.White,
          modifier = Modifier.padding(end = 4.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
          colorOptions.forEach { color ->
            Box(
              modifier = Modifier
                .size(20.dp)
                .background(color, shape = RoundedCornerShape(10.dp))
                .clickable { onStrokeColorChange(color) }
                .border(
                  width = if (strokeColor == color) 2.dp else 0.dp,
                  color = if (strokeColor == color) Color.White else Color.Transparent,
                  shape = RoundedCornerShape(10.dp)
                )
            )
          }
        }
      }

      // Toggle Buttons
      Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(top = 8.dp)
      ) {
        FloatingActionButton(
          modifier = Modifier.size(width = 150.dp, height = 32.dp),
          backgroundColor = if (showText) Color(0xFF1976D2) else Color(0xFFB0BEC5),
          onClick = onShowTextToggle,
          shape = RoundedCornerShape(16.dp)
        ) {
          Text(
            text = if (showText) "Hide text" else "Show text",
            color = Color.White
          )
        }
        FloatingActionButton(
          modifier = Modifier.size(width = 150.dp, height = 32.dp),
          backgroundColor = if (showStroke) Color(0xFF1976D2) else Color(0xFFB0BEC5),
          onClick = onShowStrokeToggle,
          shape = RoundedCornerShape(16.dp)
        ) {
          Text(
            text = if (showStroke) "Hide stroke" else "Show stroke",
            color = Color.White
          )
        }
      }
    }
  }
}

private val colorOptions = listOf(
  Color.Red, Color.Green, Color(0xff4264fb), Color.Yellow, Color.Magenta, Color.Cyan,
  Color.Black, Color.White, Color.Gray, Color(0xff0f38bf)
)