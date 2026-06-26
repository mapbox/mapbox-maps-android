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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.LongValue
import com.mapbox.maps.extension.compose.style.StringListValue
import com.mapbox.maps.extension.compose.style.layers.generated.RasterLayer
import com.mapbox.maps.extension.compose.style.sources.generated.rememberRasterSourceState
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle

/**
 * Demonstrates [rememberRasterSourceState] — state is created once and properties are
 * updated imperatively via [androidx.compose.runtime.LaunchedEffect] without recreating the source.
 */
public class RasterTileOverlayActivity : ComponentActivity() {

  private data class TileOverlay(val name: String, val url: String)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      var currentOverlay by remember { mutableStateOf(OVERLAYS[0]) }
      var useLargeTiles by remember { mutableStateOf(true) }

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            val next = if (currentOverlay == OVERLAYS[0]) OVERLAYS[1] else OVERLAYS[0]
            Column {
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = { useLargeTiles = !useLargeTiles },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "Tiles: ${if (useLargeTiles) TILE_SIZE_LARGE else TILE_SIZE}px"
                )
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  currentOverlay = next
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = "Switch to ${next.name}"
                )
              }
            }
          }
        ) {
          val rasterSourceState = rememberRasterSourceState()
          LaunchedEffect(currentOverlay.url, useLargeTiles) {
            rasterSourceState.apply {
              tiles = StringListValue(listOf(currentOverlay.url))
              tileSize = LongValue(if (useLargeTiles) TILE_SIZE_LARGE else TILE_SIZE)
            }
          }

          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions {
                center(Point.fromLngLat(LONGITUDE, LATITUDE))
                zoom(ZOOM)
              }
            },
            style = { MapboxStandardStyle() }
          ) {

            RasterLayer(
              sourceState = rasterSourceState
            )
          }
        }
      }
    }
  }

  private companion object {
    val OVERLAYS = listOf(
      TileOverlay(
        name = "OpenStreetMap",
        url = "https://tile.openstreetmap.org/{z}/{x}/{y}.png"
      ),
      TileOverlay(
        name = "ThunderForest",
        url = "https://api.thunderforest.com/landscape/{z}/{x}/{y}.png"
      ),
    )

    const val TILE_SIZE = 256L
    const val TILE_SIZE_LARGE = 512L
    const val LONGITUDE = 24.9384
    const val LATITUDE = 60.1699
    const val ZOOM = 12.0
  }
}