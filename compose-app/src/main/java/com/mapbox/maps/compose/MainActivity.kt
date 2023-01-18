package com.mapbox.maps.compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.mapbox.maps.Style

private const val LATITUDE = 60.239
private const val LONGITUDE = 25.004

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMap(Modifier.fillMaxSize()) {
        setCenter(Longitude(LONGITUDE), Latitude(LATITUDE), 9.0)
        loadStyleUri(Style.DARK)
        addOnMapClickListener {
          Log.e("tag", it.toString())
          true
        }
      }
    }
  }
}
