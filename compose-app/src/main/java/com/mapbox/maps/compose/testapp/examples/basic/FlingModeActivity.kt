package com.mapbox.maps.compose.testapp.examples.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.plugin.ScrollMode
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings

/**
 * Playground for fling deceleration and scroll mode using GesturesSettings in Compose.
 *
 */
@OptIn(MapboxExperimental::class)
public class FlingModeActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var useNativeFling by remember { mutableStateOf(true) }
      var scrollMode by remember { mutableStateOf(ScrollMode.HORIZONTAL_AND_VERTICAL) }

      val mapState = rememberMapState()

      LaunchedEffect(useNativeFling, scrollMode) {
        mapState.gesturesSettings = GesturesSettings {
          useNativeFlingDeceleration = useNativeFling
          this.scrollMode = scrollMode
        }
      }

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.End) {
              ExtendedFloatingActionButton(
                onClick = {
                  scrollMode = when (scrollMode) {
                    ScrollMode.HORIZONTAL_AND_VERTICAL -> ScrollMode.HORIZONTAL
                    ScrollMode.HORIZONTAL -> ScrollMode.VERTICAL
                    ScrollMode.VERTICAL -> ScrollMode.HORIZONTAL_AND_VERTICAL
                  }
                },
                text = {
                  Text(
                    text = when (scrollMode) {
                      ScrollMode.HORIZONTAL_AND_VERTICAL -> "Scroll: H+V"
                      ScrollMode.HORIZONTAL -> "Scroll: H only"
                      ScrollMode.VERTICAL -> "Scroll: V only"
                    }
                  )
                }
              )

              Spacer(Modifier.height(12.dp))

              ExtendedFloatingActionButton(
                text = { Text(if (useNativeFling) "Native fling" else "Legacy fling") },
                onClick = { useNativeFling = !useNativeFling }
              )
            }
          },
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = rememberMapViewportState {
              setCameraOptions {
                zoom(ZOOM)
                center(CityLocations.HELSINKI)
              }
            },
            mapState = mapState,
          )
        }
      }
    }
  }

  private companion object {
    const val ZOOM: Double = 12.0
  }
}