package com.mapbox.maps.compose.testapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap

/**
 * Example to showcase usage of MapboxMap.
 */
@OptIn(MapboxExperimental::class)
public class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Home()
    }
  }

  @Preview(showBackground = true)
  @Composable
  private fun Home() {
    Box(Modifier.fillMaxSize()) {
      MapboxMap(
        Modifier.matchParentSize(),
        mapInitOptions = MapInitOptions(
          context = LocalContext.current,
          styleUri = Style.MAPBOX_STREETS,
          cameraOptions = cameraOptions {
            center(POINT)
            zoom(ZOOM)
          }
        ),
        onMapClickListener = {
          Toast.makeText(this@MainActivity, "Clicked on $it", Toast.LENGTH_SHORT).show()
          false
        },
        onMapLongClickListener = {
          Toast.makeText(this@MainActivity, "Long-clicked on $it", Toast.LENGTH_SHORT).show()
          false
        }
      ) {
        // Enable tile borders debug mode using MapEffect
        MapEffect(Unit) { map ->
          map.getMapboxMap().setDebug(
            listOf(MapDebugOptions.TILE_BORDERS, MapDebugOptions.TIMESTAMPS),
            true
          )
        }
      }
    }
  }

  private companion object {
    const val LATITUDE = 60.239
    const val LONGITUDE = 25.004
    const val ZOOM: Double = 12.0
    val POINT: Point = Point.fromLngLat(LONGITUDE, LATITUDE)
  }
}