package com.mapbox.maps.testapp.examples

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.android.core.location.LocationEngineRequest
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.location.LocationComponentActivationOptions
import com.mapbox.maps.plugin.location.LocationComponentOptions
import com.mapbox.maps.plugin.location.getLocationPlugin
import com.mapbox.maps.plugin.location.modes.CameraMode
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
  private var lastLocation: Location? = null
  private var lastStyleUri = Style.DARK

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_location_layer_basic_pulsing_circle)
    mapboxMap = mapView.getMapboxMap()
    if (savedInstanceState != null) {
      lastLocation = savedInstanceState.getParcelable(SAVED_STATE_LOCATION)
    }
    locationPermissionHelper = LocationPermissionHelper(this)
    locationPermissionHelper.checkPermissions {
      onMapReady()
    }
  }

  private fun onMapReady() {
    mapboxMap.loadStyleUri(
      Style.MAPBOX_STREETS
    ) {
      initLocationComponent(it)
      lastStyleUri = it.styleURI
    }
  }

  private fun initLocationComponent(style: Style) {
    val locationComponentOptions =
      LocationComponentOptions.builder(this@BasicLocationPulsingCircleActivity)
        .pulseEnabled(true)
        .build()

    val locationComponentActivationOptions =
      buildLocationComponentActivationOptions(style, locationComponentOptions)

    mapView.getLocationPlugin().apply {
      activateLocationComponent(locationComponentActivationOptions)
      enabled = true
      setCameraMode(CameraMode.TRACKING)
      forceLocationUpdate(lastLocation)
    }
  }

  private fun buildLocationComponentActivationOptions(
    style: Style,
    locationComponentOptions: LocationComponentOptions
  ): LocationComponentActivationOptions {
    return LocationComponentActivationOptions
      .builder(this, style)
      .locationComponentOptions(locationComponentOptions)
      .useDefaultLocationEngine(true)
      .locationEngineRequest(
        LocationEngineRequest.Builder(750)
          .setFastestInterval(750)
          .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
          .build()
      )
      .build()
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
        mapView.getLocationPlugin().enabled = false
        return true
      }
      R.id.action_component_enabled -> {
        mapView.getLocationPlugin().enabled = true
        return true
      }
      R.id.action_stop_pulsing -> {
        mapView.getLocationPlugin().applyStyle(
          LocationComponentOptions.builder(
            this@BasicLocationPulsingCircleActivity
          )
            .pulseEnabled(false)
            .build()
        )
        return true
      }
      R.id.action_start_pulsing -> {
        mapView.getLocationPlugin().applyStyle(
          LocationComponentOptions.builder(
            this@BasicLocationPulsingCircleActivity
          )
            .pulseEnabled(true)
            .build()
        )
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

  @SuppressLint("MissingPermission")
  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putParcelable(
      SAVED_STATE_LOCATION,
      mapView.getLocationPlugin().lastKnownLocation
    )
  }

  companion object {
    private const val SAVED_STATE_LOCATION = "saved_state_location"
  }
}