package com.mapbox.maps.testapp.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.annotation.CircleAnnotation
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings

private const val LATITUDE = 60.239
private const val LONGITUDE = 25.004

public class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
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
      // compassState, ScaleBarState..
      style = mapStyleState,
      cameraOptions = CameraOptions.Builder().zoom(zoomLevel.toDouble()).build()
    ) {
      MapEffect(key1 = "Observe render frame") { map ->
        logE("compose", "MapEffect with key=Observe render frame")
        map.getMapboxMap().addOnRenderFrameFinishedListener {
          logE("compose", "frame finished: $it")
        }
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
          mapStyleState = style(if (mapStyleState.styleUri == Style.DARK) Style.LIGHT else Style.DARK) { }
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
    }
  }
}