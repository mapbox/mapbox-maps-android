package com.mapbox.maps.testapp.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.mapbox.maps.extension.compose.*
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.gestures.gestures

private const val LATITUDE = 60.239
private const val LONGITUDE = 25.004

class MainActivity : ComponentActivity() {
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
  var mapGesturesSettings by remember {
    mutableStateOf(GesturesSettings())
  }
  var mapStyleExtension by remember {
    mutableStateOf(style(Style.MAPBOX_STREETS) {})
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
        styleUri = mapStyleExtension.styleUri
      ),
      styleExtension = mapStyleExtension
    ) { mapView ->
      mapView.gestures.applySettings(mapGesturesSettings)
    }
    Column {
      Button(
        onClick = {
          mapGesturesSettings = mapGesturesSettings.copy(
            pinchToZoomEnabled = !mapGesturesSettings.pinchToZoomEnabled
          )
        }
      ) {
        Text(text = "Toggle pinchToZoomEnabled")
      }
      Button(
        onClick = {
          mapStyleExtension =
            style(if (mapStyleExtension.styleUri == Style.DARK) Style.LIGHT else Style.DARK) { }
        }
      ) {
        Text(text = "Toggle mapStyleUri")
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun DefaultMapPreview() {
  MapboxMap(
    modifier = Modifier.fillMaxSize(),
    mapInitOptions = MapInitOptions(
      context = LocalContext.current,
      mapOptions = MapOptions.Builder()
        .applyDefaultParams(LocalContext.current)
        .optimizeForTerrain(true)
        .contextMode(ContextMode.UNIQUE)
        .build(),
      cameraOptions = CameraOptions.Builder().center(Point.fromLngLat(LONGITUDE, LATITUDE))
        .zoom(12.0).build(),
      styleUri = Style.MAPBOX_STREETS
    )
  )
}