package com.mapbox.maps.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style

private const val LATITUDE = 60.239
private const val LONGITUDE = 25.004

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapboxMap()
        }
    }
}

@Composable
private fun MapboxMap() {
    val mapView = MapView()
    AndroidView(
        factory = { mapView },
        modifier =  Modifier.fillMaxSize()
    ) { mapView ->
        mapView.getMapboxMap()
          .apply {
            loadStyleUri(Style.MAPBOX_STREETS)
            setCamera(
              CameraOptions.Builder()
                .center(Point.fromLngLat(LONGITUDE, LATITUDE))
                .zoom(9.0)
                .build()
            )
          }
    }
}

@Composable
private fun MapView(): MapView {
    val context = LocalContext.current
    return MapView(context)
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MapboxMap()
}
