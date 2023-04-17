package com.mapbox.maps.compose.testapp.examples.animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
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
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.*
import com.mapbox.maps.extension.compose.animation.camera.animateBearing
import com.mapbox.maps.extension.compose.animation.camera.animateCenter
import com.mapbox.maps.extension.compose.animation.camera.animatePitch
import com.mapbox.maps.extension.compose.animation.camera.animateZoom

/**
 * Showcase the advanced camera animations with individually customised [AnimationSpec] for center,
 * bearing, zoom etc. running in the same transition.
 */
@OptIn(MapboxExperimental::class)
public class AdvancedCameraAnimationsActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var cameraCenter by remember {
        mutableStateOf(CityLocations.BERLIN)
      }
      var cameraBearing by remember {
        mutableStateOf(0.0)
      }
      var cameraZoom by remember {
        mutableStateOf(5.0)
      }
      var cameraPitch by remember {
        mutableStateOf(0.0)
      }
      val cameraTransition = updateTransition(
        targetState = CameraState(
          cameraCenter,
          EdgeInsets(0.0, 0.0, 0.0, 0.0),
          cameraZoom,
          cameraBearing,
          cameraPitch
        ),
        "My camera transition"
      )
      val animatedCenter by cameraTransition.animateCenter(
        transitionSpec = {
          tween(
            durationMillis = 2000,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
          )
        }
      )
      val animatedBearing by cameraTransition.animateBearing(
        transitionSpec = {
          tween(
            durationMillis = 1500,
            delayMillis = 500,
            easing = FastOutLinearInEasing
          )
        }
      )
      val animatedZoom by cameraTransition.animateZoom(
        transitionSpec = {
          tween(
            durationMillis = 500,
            delayMillis = 1500,
            easing = LinearOutSlowInEasing
          )
        }
      )
      val animatedPitch by cameraTransition.animatePitch(
        transitionSpec = {
          spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
          )
        }
      )

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            FloatingActionButton(
              onClick = {
                cameraCenter = with(cameraCenter) {
                  Point.fromLngLat(longitude() + 0.1, latitude() + 0.1)
                }
                cameraZoom = with(cameraZoom) {
                  (this + 1).coerceAtMost(20.0)
                }
                cameraBearing = with(cameraBearing) {
                  this + 10.0
                }
                cameraPitch = with(cameraPitch) {
                  (this + 10).coerceAtMost(70.0)
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
            cameraOptions = cameraOptions {
              center(animatedCenter)
              bearing(animatedBearing)
              zoom(animatedZoom)
              pitch(animatedPitch)
            }
          )
        }
      }
    }
  }
}