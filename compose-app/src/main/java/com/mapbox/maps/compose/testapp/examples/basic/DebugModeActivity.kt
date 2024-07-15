package com.mapbox.maps.compose.testapp.examples.basic

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mapbox.maps.MapDebugOptions
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.logI
import kotlinx.coroutines.launch

/**
 * Example to showcase usage of MapEffect to enable debug mode.
 */
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
    val mapState = rememberMapState()
    LaunchedEffect(Unit) {
      mapState.apply {
        launch {
          mapLoadedEvents.collect {
            logI("map events", "on map loaded")
          }
        }
        launch {
          mapIdleEvents.collect {
            logI("map events", "on map idle")
          }
        }
        launch {
          mapLoadingErrorEvents.collect {
            logI("map events", "on map loading error")
          }
        }
        launch {
          styleLoadedEvents.collect {
            logI("map events", "on style loaded")
          }
        }
        launch {
          sourceDataLoadedEvents.collect {
            logI("map events", "on source data loaded ${it.tileId}")
          }
        }
      }
    }
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
        },
        mapState = mapState,
      ) {
        // Enable debug mode using MapEffect
        MapEffect(Unit) { mapView ->
          mapView.mapboxMap.setDebug(
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