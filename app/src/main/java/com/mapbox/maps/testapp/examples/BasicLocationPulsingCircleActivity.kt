package com.mapbox.maps.testapp.examples

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.locationcomponent.getLocationComponentPlugin
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.utils.LocationPermissionHelper
import kotlinx.android.synthetic.main.activity_location_layer_basic_pulsing_circle.*

/**
 * This activity shows a basic usage of the LocationComponent's pulsing circle. There's no
 * customization of the pulsing circle's color, radius, speed, etc.
 */
class BasicLocationPulsingCircleActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap
  private lateinit var locationPermissionHelper: LocationPermissionHelper
  private var lastStyleUri = Style.DARK

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_location_layer_basic_pulsing_circle)
    mapboxMap = mapView.getMapboxMap()
    locationPermissionHelper = LocationPermissionHelper(this)
    locationPermissionHelper.checkPermissions {
      onMapReady()
    }
  }

  private fun onMapReady() {
    mapboxMap.loadStyleUri(
      Style.MAPBOX_STREETS
    ) {
      lastStyleUri = it.styleURI
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_pulsing_location_mode, menu)
    return true
  }

  @SuppressLint("MissingPermission")
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.action_map_style_change -> {
        loadNewStyle()
        return true
      }
      R.id.action_component_disable -> {
        mapView.getLocationComponentPlugin().enabled = false
        return true
      }
      R.id.action_component_enabled -> {
        mapView.getLocationComponentPlugin().enabled = true
        return true
      }
      R.id.action_stop_pulsing -> {
        mapView.getLocationComponentPlugin().pulsingEnabled = false
        return true
      }
      R.id.action_start_pulsing -> {
        mapView.getLocationComponentPlugin().pulsingEnabled = true
        return true
      }
      else -> return super.onOptionsItemSelected(item)
    }
  }

  private fun loadNewStyle() {
    val styleUrl = if (lastStyleUri == Style.DARK) Style.LIGHT else Style.DARK
    mapboxMap.loadStyleUri(
      styleUrl
    ) { lastStyleUri = styleUrl }
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
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