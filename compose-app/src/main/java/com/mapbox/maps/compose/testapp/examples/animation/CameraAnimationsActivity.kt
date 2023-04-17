package com.mapbox.maps.compose.testapp.examples.animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.*
import com.mapbox.maps.extension.compose.animation.camera.animateCameraState
import com.mapbox.maps.extension.compose.animation.camera.copy

/**
 * Showcase the basic camera animations based on [animateCameraState] API.
 */
@OptIn(MapboxExperimental::class)
public class CameraAnimationsActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var cameraState by remember {
        mutableStateOf(
          CameraState(
            /* center = */
            CityLocations.BERLIN,
            /* padding = */
            EdgeInsets(0.0, 0.0, 0.0, 0.0),
            /* zoom = */
            5.0,
            /* bearing = */
            0.0,
            /* pitch = */
            0.0,
          )
        )
      }
      val animatedCameraOptions by animateCameraState(
        targetState = cameraState,
        durationMillis = 2000,
        easing = LinearEasing
      )
      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            FloatingActionButton(
              onClick = {
                cameraState = with(cameraState) {
                  copy(
                    center = Point.fromLngLat(center.longitude() + 0.1, center.latitude() + 0.1),
                    zoom = (zoom + 1).coerceAtMost(20.0),
                    bearing = bearing + 10.0,
                    pitch = (pitch + 10).coerceAtMost(70.0)
                  )
                }
              },
              shape = RoundedCornerShape(16.dp),
            ) {
              Text(modifier = Modifier.padding(10.dp), text = "Animate camera")
            }
          }
        ) {
          MapboxMap(
            Modifier.fillMaxSize(),
            cameraOptions = animatedCameraOptions
          )
        }
      }
    }
  }
}