package com.mapbox.maps.compose.testapp.examples.ornaments

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.ornaments.indoorselector.rememberIndoorSelectorState
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.extension.compose.style.standard.StandardStyleState

/**
 * Example of the [IndoorSelector] ornament backed by hoisted [rememberIndoorSelectorState].
 * Demonstrates toggling the indoor layer on/off and selecting a floor programmatically.
 */
@OptIn(MapboxExperimental::class, com.mapbox.annotation.MapboxExperimental::class)
public class IndoorExampleActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMapComposeTheme {
        ExampleScaffold {
          val indoorState = rememberIndoorSelectorState()
          var enabled by remember { mutableStateOf(true) }
          Box(modifier = Modifier.fillMaxSize()) {
            MapboxMap(
              modifier = Modifier.fillMaxSize(),
              mapViewportState = rememberMapViewportState {
                setCameraOptions {
                  center(Point.fromLngLat(LONGITUDE, LATITUDE))
                  zoom(ZOOM)
                  bearing(BEARING)
                  pitch(PITCH)
                }
              },
              indoorSelector = {
                if (!enabled) return@MapboxMap
                IndoorSelector(
                  state = indoorState,
                  alignment = Alignment.TopEnd,
                  contentPadding = PaddingValues(top = 80.dp, end = 20.dp),
                  onFloorClicked = { floor ->
                    Log.d("IndoorExample", "Floor clicked: ${floor.id}")
                  }
                )
              },
              style = {
                MapboxStandardStyle(
                  standardStyleState = remember(enabled) {
                    StandardStyleState().apply {
                      configurationsState.showIndoor = BooleanValue(enabled)
                    }
                  }
                )
              },
            )
            Row(
              modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            ) {
              Button(
                onClick = {
                  indoorState.floors.firstOrNull()?.let { indoorState.selectedFloorId = it.id }
                },
                enabled = indoorState.floors.isNotEmpty(),
              ) {
                Text("Top Floor")
              }
              Button(
                onClick = { enabled = !enabled },
                modifier = Modifier.padding(start = 8.dp),
              ) {
                Text(if (enabled) "Disable" else "Enable")
              }
            }
          }
        }
      }
    }
  }

  private companion object {
    const val LATITUDE = 35.55025
    const val LONGITUDE = 139.794131
    const val ZOOM = 16.0
    const val BEARING = 12.0
    const val PITCH = 60.0
  }
}