package com.mapbox.maps.compose.testapp.examples.ornaments

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState

/**
 * Example to showcase the unified `ornaments` scope slot: hiding a default ornament, replacing
 * another with a custom one, and adding a new opt-in ornament (`indoorSelector`) — all without
 * growing the [MapboxMap] signature.
 */
@OptIn(MapboxExperimental::class)
public class OrnamentsScopeExampleActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMapComposeTheme {
        ExampleScaffold { scaffoldPadding ->
          MapboxMap(
            modifier = Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions {
                zoom(ZOOM)
                center(CityLocations.HELSINKI)
                bearing(30.0)
              }
            },
            ornaments = {
              Defaults {
                // Hide the scale bar.
                scaleBar = null
                // Replace the compass with a repositioned one, no bearing reset on click.
                compass = {
                  Compass(
                    modifier = Modifier.padding(scaffoldPadding),
                    resetToNorthUponClick = false,
                  )
                }
              }
              // Add an ornament that is not part of the default set.
              // Offset the floor selector so it does not sit on top of the compass, which keeps
              // the default top-end alignment.
              indoorSelector {
                IndoorSelector(
                  modifier = Modifier.padding(scaffoldPadding),
                  contentPadding = PaddingValues(top = 80.dp, end = 20.dp),
                )
              }
            },
          ) {
            // The trailing lambda still binds to `content`, not `ornaments`.
            MapEffect(Unit) { }
          }
        }
      }
    }
  }

  private companion object {
    const val ZOOM: Double = 9.0
  }
}