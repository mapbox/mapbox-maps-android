package com.mapbox.maps.compose.testapp.examples.location

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.examples.utils.RequestLocationPermission
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.ViewportStatus
import kotlinx.coroutines.launch

/**
 * Example to showcase usage of Location Component.
 */
@OptIn(MapboxExperimental::class)
public class LocationComponentActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val context = LocalContext.current
      val snackbarHostState = remember { SnackbarHostState() }
      val scope = rememberCoroutineScope()
      var permissionRequestCount by remember {
        mutableStateOf(1)
      }
      var showMap by remember {
        mutableStateOf(false)
      }
      var showRequestPermissionButton by remember {
        mutableStateOf(false)
      }
      val mapViewportState = rememberMapViewportState {
        setCameraOptions {
          center(CityLocations.HELSINKI)
          zoom(ZOOM)
          pitch(PITCH)
        }
      }

      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            // Show locate button when viewport is in Idle state, e.g. camera is controlled by gestures.
            if (mapViewportState.mapViewportStatus == ViewportStatus.Idle) {
              FloatingActionButton(
                onClick = {
                  mapViewportState.transitionToFollowPuckState()
                }
              ) {
                Image(
                  painter = painterResource(id = android.R.drawable.ic_menu_mylocation),
                  contentDescription = "Locate button"
                )
              }
            }
          },
          snackbarHost = {
            SnackbarHost(snackbarHostState)
          }
        ) {
          RequestLocationPermission(
            requestCount = permissionRequestCount,
            onPermissionDenied = {
              scope.launch {
                snackbarHostState.showSnackbar("You need to accept location permissions.")
              }
              showRequestPermissionButton = true
            },
            onPermissionReady = {
              showRequestPermissionButton = false
              showMap = true
            }
          )
          if (showMap) {
            MapboxMap(
              Modifier.fillMaxSize(),
              mapViewportState = mapViewportState,
            ) {
              MapEffect(Unit) { mapView ->
                mapView.location.updateSettings {
                  locationPuck = createDefault2DPuck(withBearing = true)
                  puckBearingEnabled = true
                  puckBearing = PuckBearing.HEADING
                  enabled = true
                }
                mapViewportState.transitionToFollowPuckState()
              }
            }
          }
          if (showRequestPermissionButton) {
            Box(modifier = Modifier.fillMaxSize()) {
              Column(modifier = Modifier.align(Alignment.Center)) {
                Button(
                  modifier = Modifier.align(Alignment.CenterHorizontally),
                  onClick = {
                    permissionRequestCount += 1
                  }
                ) {
                  Text("Request permission again ($permissionRequestCount)")
                }
                Button(
                  modifier = Modifier.align(Alignment.CenterHorizontally),
                  onClick = {
                    context.startActivity(
                      Intent(
                        android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", packageName, null)
                      )
                    )
                  }
                ) {
                  Text("Show App Settings page")
                }
              }
            }
          }
        }
      }
    }
  }

  private companion object {
    const val ZOOM: Double = 0.0
    const val PITCH: Double = 0.0
  }
}