package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_style_switch.*

/**
 * Example of changing style for a map in runtime.
 */
class StyleSwitchActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_style_switch)

    mapboxMap = mapView.getMapboxMap()

    mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)

    streets_button.setOnClickListener {
      mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)
    }
    light_button.setOnClickListener {
      mapboxMap.loadStyleUri(Style.LIGHT)
    }
    dark_button.setOnClickListener {
      mapboxMap.loadStyleUri(Style.DARK)
    }
    satellite_streets_button.setOnClickListener {
      mapboxMap.loadStyleUri(Style.SATELLITE_STREETS)
    }
    satellite_button.setOnClickListener {
      mapboxMap.loadStyleUri(Style.SATELLITE)
    }
    outdoors_button.setOnClickListener {
      mapboxMap.loadStyleUri(Style.OUTDOORS)
    }
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }
}