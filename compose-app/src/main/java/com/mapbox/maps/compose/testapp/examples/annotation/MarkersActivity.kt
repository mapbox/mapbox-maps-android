package com.mapbox.maps.compose.testapp.examples.annotation

import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations.HELSINKI
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.Marker
import com.mapbox.maps.extension.compose.annotation.MarkerAnimationEffect
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle

/**
 * Example to showcase usage of Markers with animation.
 *
 * Demonstrates three preset markers with different animation types:
 * - Wiggle+Scale on appear, Scale on disappear
 * - Scale on appear, Scale on disappear
 * - Fade on appear, Fade on disappear
 *
 * Click the map to add new markers with animations.
 * Long press the map to remove all markers (demonstrates disappear animations).
 */
public class MarkersActivity : ComponentActivity() {
  @OptIn(MapboxExperimental::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val clickedPoints = remember { mutableStateListOf<Point>() }
      var markerColor by remember { mutableStateOf(Color(0xffcfdaf7)) }
      var strokeColor by remember { mutableStateOf(Color(0xff3a59fa)) }
      var showStroke by remember { mutableStateOf(true) }
      var showText by remember { mutableStateOf(true) }
      var showPresetMarkers by remember { mutableStateOf(true) }

      MapboxMapComposeTheme {
        ExampleScaffold {
          Box(modifier = Modifier.fillMaxSize()) {
            val mapState = rememberMapState { }
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
                MapboxStandardStyle()
              },
              mapState = mapState,
              onMapClickListener = { point ->
                clickedPoints.add(point)
                true
              },
              onMapLongClickListener = {
                clickedPoints.clear()
                showPresetMarkers = false
                true
              }
            ) {
            // Three preset markers demonstrating different animation types
            if (showPresetMarkers) {
              // Marker 1: Wiggle+Scale
              val point1 = Point.fromLngLat(HELSINKI.longitude() - 0.01, HELSINKI.latitude())
              Marker(
                point = point1,
                color = markerColor,
                stroke = if (showStroke) strokeColor else null,
                text = if (showText) "Wiggle+Scale" else null,
                appearAnimation = listOf(MarkerAnimationEffect.wiggle, MarkerAnimationEffect.scale),
                disappearAnimation = listOf(MarkerAnimationEffect.wiggle, MarkerAnimationEffect.scale(from = 1f, to = 0f)),
                onClick = {
                  Log.d(TAG, "Marker clicked at: ${point1.latitude()}, ${point1.longitude()}")
                }
              )

              // Marker 2: Scale
              val point2 = Point.fromLngLat(HELSINKI.longitude(), HELSINKI.latitude())
              Marker(
                point = point2,
                color = markerColor,
                stroke = if (showStroke) strokeColor else null,
                text = if (showText) "Scale" else null,
                appearAnimation = listOf(MarkerAnimationEffect.scale),
                disappearAnimation = listOf(MarkerAnimationEffect.scale(from = 1f, to = 0f)),
                onClick = {
                  Log.d(TAG, "Marker clicked at: ${point2.latitude()}, ${point2.longitude()}")
                }
              )

              // Marker 3: Fade
              val point3 = Point.fromLngLat(HELSINKI.longitude() + 0.01, HELSINKI.latitude())
              Marker(
                point = point3,
                color = markerColor,
                stroke = if (showStroke) strokeColor else null,
                text = if (showText) "Fade" else null,
                appearAnimation = listOf(MarkerAnimationEffect.fadeIn),
                disappearAnimation = listOf(MarkerAnimationEffect.fadeOut),
                onClick = {
                  Log.d(TAG, "Marker clicked at: ${point3.latitude()}, ${point3.longitude()}")
                }
              )
            }

            // User-added markers
            clickedPoints.forEach { point ->
              val coordText = remember(point) {
                "%.2f, %.2f".format(point.latitude(), point.longitude())
              }
              Marker(
                point = point,
                color = markerColor,
                stroke = if (showStroke) strokeColor else null,
                text = if (showText) coordText else null,
                appearAnimation = listOf(MarkerAnimationEffect.wiggle, MarkerAnimationEffect.scale),
                disappearAnimation = listOf(MarkerAnimationEffect.wiggle, MarkerAnimationEffect.scale(from = 1f, to = 0f)),
                onClick = {
                  Log.d(TAG, "Marker clicked at: ${point.latitude()}, ${point.longitude()}")
                }
              )
            }
          }

            // Centered info box overlay
            Box(
              modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
            ) {
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
          }
        }
      }
    }
  }

  private companion object {
    const val TAG = "MarkersActivity"
    const val ZOOM: Double = 12.5
    const val PITCH: Double = 45.0
  }
}

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
  Column(
    modifier = Modifier
      .background(Color.Black.copy(alpha = 0.8f), shape = RoundedCornerShape(12.dp))
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Header
    Text(
      text = "Marker Animations",
      color = Color.White,
      style = androidx.compose.material.MaterialTheme.typography.subtitle1,
      fontWeight = FontWeight.Bold
    )

    // Instructions
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
      Text(
        text = "Tap map to add markers",
        color = Color.White.copy(alpha = 0.9f),
        style = androidx.compose.material.MaterialTheme.typography.body2
      )
      Text(
        text = "Long press to remove all markers",
        color = Color.White.copy(alpha = 0.9f),
        style = androidx.compose.material.MaterialTheme.typography.body2
      )
    }

    androidx.compose.material.Divider(
      color = Color.White.copy(alpha = 0.3f),
      thickness = 1.dp
    )

    // Color Selectors
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Text(
          text = "Color",
          color = Color.White,
          style = androidx.compose.material.MaterialTheme.typography.body2,
          modifier = Modifier.width(50.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          MarkerColors.OPTIONS.forEach { color ->
            Box(
              modifier = Modifier
                .size(24.dp)
                .background(color, shape = RoundedCornerShape(12.dp))
                .clickable { onMarkerColorChange(color) }
                .border(
                  width = if (markerColor == color) 2.dp else 0.dp,
                  color = if (markerColor == color) Color.White else Color.Transparent,
                  shape = RoundedCornerShape(12.dp)
                )
            )
          }
        }
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Text(
          text = "Stroke",
          color = Color.White,
          style = androidx.compose.material.MaterialTheme.typography.body2,
          modifier = Modifier.width(50.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          MarkerColors.OPTIONS.forEach { color ->
            Box(
              modifier = Modifier
                .size(24.dp)
                .background(color, shape = RoundedCornerShape(12.dp))
                .clickable { onStrokeColorChange(color) }
                .border(
                  width = if (strokeColor == color) 2.dp else 0.dp,
                  color = if (strokeColor == color) Color.White else Color.Transparent,
                  shape = RoundedCornerShape(12.dp)
                )
            )
          }
        }
      }
    }

    // Toggle Buttons
    Row(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.padding(top = 4.dp)
    ) {
      FloatingActionButton(
        modifier = Modifier.weight(1f).size(height = 36.dp, width = 0.dp),
        backgroundColor = if (showText) Color(0xFF2196F3) else Color(0xFF424242),
        onClick = onShowTextToggle,
        shape = RoundedCornerShape(18.dp)
      ) {
        Text(
          text = "Text",
          color = Color.White,
          style = androidx.compose.material.MaterialTheme.typography.button
        )
      }
      FloatingActionButton(
        modifier = Modifier.weight(1f).size(height = 36.dp, width = 0.dp),
        backgroundColor = if (showStroke) Color(0xFF2196F3) else Color(0xFF424242),
        onClick = onShowStrokeToggle,
        shape = RoundedCornerShape(18.dp)
      ) {
        Text(
          text = "Stroke",
          color = Color.White,
          style = androidx.compose.material.MaterialTheme.typography.button
        )
      }
    }
  }
}

private object MarkerColors {
  val OPTIONS = listOf(
    Color.Red, Color.Green, Color(0xff4264fb), Color.Yellow, Color.Magenta, Color.Cyan,
    Color.Black, Color.White, Color.Gray, Color(0xff0f38bf)
  )
}