package com.mapbox.maps.compose.testapp.examples.basic

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mapbox.maps.*
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState

/**
 * Example to showcase usage of MapEffect to enable debug mode.
 */
@OptIn(MapboxExperimental::class)
public class DebugModeActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMapComposeTheme {
        MapScreen()
      }
    }
  }

  @Preview(showBackground = true)
  @Composable
  private fun MapScreen() {
    ExampleScaffold {
      MapboxMap(
        Modifier.fillMaxSize(),
        mapViewportState = MapViewportState().apply {
          setCameraOptions {
            center(CityLocations.BERLIN)
            zoom(ZOOM)
            pitch(0.0)
            this.bearing(0.0)
          }
        },
        onMapClickListener = {
          Toast.makeText(this@DebugModeActivity, "Clicked on $it", Toast.LENGTH_SHORT).show()
          false
        },
        onMapLongClickListener = {
          Toast.makeText(this@DebugModeActivity, "Long-clicked on $it", Toast.LENGTH_SHORT).show()
          false
        }
      ) {
        // Enable debug mode using MapEffect
        MapEffect(Unit) { map ->
          map.getMapboxMap().setDebug(
            listOf(
              MapDebugOptions.TILE_BORDERS,
              MapDebugOptions.PARSE_STATUS,
              MapDebugOptions.TIMESTAMPS,
              MapDebugOptions.COLLISION,
              MapDebugOptions.STENCIL_CLIP,
              MapDebugOptions.DEPTH_BUFFER,
              MapDebugOptions.MODEL_BOUNDS,
              MapDebugOptions.TERRAIN_WIREFRAME,
              MapDebugOptions.LAYERS2_DWIREFRAME,
              MapDebugOptions.LAYERS3_DWIREFRAME,
            ),
            true
          )
        }
      }
    }
  }

  private companion object {
    const val ZOOM: Double = 9.0
  }
}