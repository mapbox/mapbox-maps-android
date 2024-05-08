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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.extension.compose.style.sources.generated.TileSize
import com.mapbox.maps.extension.compose.style.sources.generated.Url
import com.mapbox.maps.extension.compose.style.sources.generated.rememberRasterDemSourceState
import com.mapbox.maps.extension.compose.style.terrain.generated.Exaggeration
import com.mapbox.maps.extension.compose.style.terrain.generated.TerrainState
import com.mapbox.maps.extension.compose.style.terrain.generated.rememberTerrainState

/**
 * Example to showcase usage of terrain.
 */
@OptIn(MapboxExperimental::class)
public class TerrainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {

      val rasterDemSourceState = rememberRasterDemSourceState().apply {
        url = Url(TERRAIN_URL_TILE_RESOURCE)
        tileSize = TileSize(TILE_SIZE)
      }

      val customTerrainState = rememberTerrainState(rasterDemSourceState)

      var currentTerrainState by rememberSaveable(stateSaver = TerrainState.Saver) {
        mutableStateOf(customTerrainState)
      }

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column {
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                backgroundColor = if (currentTerrainState == TerrainState.disabled) Color.LightGray else MaterialTheme.colors.secondary,
                onClick = {
                  val currentExaggeration =
                    (currentTerrainState.exaggeration.value.contents as? Double) ?: 1.0
                  currentTerrainState.exaggeration = Exaggeration(currentExaggeration + 0.2)
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Increase exaggeration")
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  currentTerrainState = if (currentTerrainState == TerrainState.disabled) {
                    customTerrainState
                  } else {
                    TerrainState.disabled
                  }
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(
                  modifier = Modifier.padding(10.dp),
                  text = if (currentTerrainState == TerrainState.disabled)
                    "Enable terrain"
                  else
                    "Disable terrain"
                )
              }
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = MapViewportState().apply {
              setCameraOptions {
                zoom(ZOOM)
                center(CENTER)
                pitch(PITCH)
              }
            },
            style = {
              GenericStyle(
                style = Style.SATELLITE_STREETS,
                terrainState = currentTerrainState
              )
            }
          )
        }
      }
    }
  }

  private companion object {
    private const val TILE_SIZE = 514L
    private const val ZOOM: Double = 11.0
    private const val PITCH: Double = 80.0
    private val CENTER = Point.fromLngLat(138.7274, 35.3606)
    private const val TERRAIN_URL_TILE_RESOURCE = "mapbox://mapbox.mapbox-terrain-dem-v1"
  }
}