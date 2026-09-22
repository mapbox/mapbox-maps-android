package com.mapbox.maps.testapp.auto.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.locationcomponent.location2
import com.mapbox.maps.testapp.auto.R

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // $ANDROID_HOME/extras/google/auto/desktop-head-unit --usb
    setContentView(
      MapView(this, mapInitOptions = MapInitOptions(this, cameraOptions = cameraOptions {
        center(Point.fromLngLat(24.9384, 60.1699))
        zoom(17.0)
      })).apply {
        location.enabled = true
        location.createDefault2DPuck(this@MainActivity, true)
      }
    )
  }
}