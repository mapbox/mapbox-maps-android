package com.mapbox.maps.testapp.compose

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.annotation.CircleAnnotation
import com.mapbox.maps.extension.compose.viewport.MapViewport
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.locationcomponent.R
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions

private const val LATITUDE = 60.239
private const val LONGITUDE = 25.004

public class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      RequestLocationPermission()
      HomeScreen()
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreen() {
  var mapGesturesState by remember {
    mutableStateOf(GesturesSettings())
  }
  var mapStyleState by remember {
    mutableStateOf(style(Style.MAPBOX_STREETS) {})
  }
  var annotationPoint by remember {
    mutableStateOf(Point.fromLngLat(LONGITUDE, LATITUDE))
  }
  var annotationSize by remember {
    mutableStateOf(10.0)
  }

  var annotationPoint1 by remember {
    mutableStateOf(Point.fromLngLat(LONGITUDE + 0.01, LATITUDE))
  }
  var annotationSize1 by remember {
    mutableStateOf(5.0)
  }
  var zoom by remember {
    mutableStateOf(12.0f)
  }
  val zoomLevel: Float by animateFloatAsState(
    zoom,
    animationSpec = tween(
      durationMillis = 1000,
      easing = LinearOutSlowInEasing
    )
  )
  var mapViewport by remember {
    mutableStateOf<MapViewport>(MapViewport.Idle)
  }
  Box(Modifier.fillMaxSize()) {
    MapboxMap(
      modifier = Modifier.matchParentSize(),
      mapInitOptions = MapInitOptions(
        context = LocalContext.current,
        mapOptions = MapOptions.Builder()
          .applyDefaultParams(LocalContext.current)
          .optimizeForTerrain(true)
          .contextMode(ContextMode.UNIQUE)
          .build(),
        cameraOptions = CameraOptions.Builder().center(Point.fromLngLat(LONGITUDE, LATITUDE))
          .zoom(12.0).build(),
      ),
      gesturesSettings = mapGesturesState,
      locationComponentSettings = LocationComponentSettings(
        enabled = true,
        locationPuck = LocationPuck2D(
          topImage = ResourcesCompat.getDrawable(
            LocalContext.current.resources,
            R.drawable.mapbox_user_icon,
            null
          ),
          bearingImage = ResourcesCompat.getDrawable(
            LocalContext.current.resources,
            R.drawable.mapbox_user_stroke_icon,
            null
          ),
          shadowImage = ResourcesCompat.getDrawable(
            LocalContext.current.resources,
            R.drawable.mapbox_user_icon_shadow,
            null
          ),
        )
      ),
      mapViewport = mapViewport,
      style = mapStyleState,
      cameraOptions = CameraOptions.Builder().zoom(zoomLevel.toDouble()).build()
    ) {
      MapEffect(key1 = "Show Debug") { map ->
        logE("compose", "MapEffect with key=Show Debug")
        map.getMapboxMap().setDebug(listOf(MapDebugOptions.TILE_BORDERS), true)
      }
      CircleAnnotation(
        point = annotationPoint,
        circleRadius = annotationSize,
        onClick = {
          annotationPoint =
            Point.fromLngLat(annotationPoint.longitude(), annotationPoint.latitude() + 0.01)
          annotationSize += 5.0
        }
      )
      CircleAnnotation(
        point = annotationPoint1,
        circleRadius = annotationSize1,
        onClick = {
          annotationPoint1 =
            Point.fromLngLat(annotationPoint1.longitude(), annotationPoint1.latitude() + 0.01)
          annotationSize1 += 10.0
        }
      )
    }

    Column {
      Button(
        onClick = {
          mapGesturesState = mapGesturesState.copy(scrollEnabled = !mapGesturesState.scrollEnabled)
        }
      ) {
        Text(text = "Toggle scrollEnabled")
      }
      Button(
        onClick = {
          mapStyleState =
            style(if (mapStyleState.styleUri == Style.DARK) Style.LIGHT else Style.DARK) { }
        }
      ) {
        Text(text = "Toggle mapStyleUri")
      }
      Button(
        onClick = {
          zoom -= 1f
        }
      ) {
        Text(text = "Zoom out - current: $zoomLevel")
      }
      Button(
        onClick = {
          zoom += 1f
        }
      ) {
        Text(text = "Zoom in - current: $zoomLevel")
      }
      Button(
        onClick = {
          mapViewport = if (mapViewport is MapViewport.OverviewState) {
            MapViewport.FollowPuckState(
              FollowPuckViewportStateOptions.Builder().build()
            )
          } else {
            MapViewport.OverviewState(
              OverviewViewportStateOptions.Builder().geometry(
                Point.fromLngLat(
                  LONGITUDE, LATITUDE
                )
              ).build()
            )
          }
        }
      ) {
        Text(text = "Toggle viewport - current: $mapViewport")
      }
    }
  }
}

@Composable
private fun RequestLocationPermission() {
  val launcher = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission()
  ) { isGranted: Boolean ->
    if (isGranted) {
      // Permission Accepted: Do something
      logD("compose","PERMISSION GRANTED")

    } else {
      // Permission Denied: Do something
      logE("compose","PERMISSION DENIED")
    }
  }
  val context = LocalContext.current
  when (PackageManager.PERMISSION_GRANTED) {
    ContextCompat.checkSelfPermission(
      context,
      Manifest.permission.ACCESS_FINE_LOCATION
    ) -> {
      Toast.makeText(context, "Location Permission granted", Toast.LENGTH_SHORT).show()
    }
    else -> {
      // Asking for permission
      SideEffect {
        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
      }
    }
  }
}