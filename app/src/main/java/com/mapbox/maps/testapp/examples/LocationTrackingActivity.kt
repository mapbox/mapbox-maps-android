package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.android.core.location.LocationEngineRequest
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.location.LocationComponentActivationOptions
import com.mapbox.maps.plugin.location.getLocationPlugin
import com.mapbox.maps.plugin.location.listeneres.OnCameraTrackingChangedListener
import com.mapbox.maps.plugin.location.modes.CameraMode
import com.mapbox.maps.plugin.location.modes.RenderMode
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.utils.LocationPermissionHelper
import kotlinx.android.synthetic.main.activity_location_layer_mode.*

/**
 * Tracks the user location on screen, simulates a navigation session.
 */
class LocationTrackingActivity : AppCompatActivity(), OnCameraTrackingChangedListener {

  private lateinit var locationPermissionHelper: LocationPermissionHelper
  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple_map)
    mapboxMap = mapView.getMapboxMap()
    locationPermissionHelper = LocationPermissionHelper(this)
    locationPermissionHelper.checkPermissions {
      onMapReady()
    }
  }

  private fun onMapReady() {
    mapboxMap.jumpTo(
      CameraOptions.Builder()
        .zoom(14.0)
        .build()
    )
    mapboxMap.loadStyleUri(
      Style.MAPBOX_STREETS
    ) {
      initLocationComponent(it)
      Style.MAPBOX_STREETS
    }
  }

  private fun initLocationComponent(style: Style) {
    val locationPluginImpl = mapView.getLocationPlugin()
    locationPluginImpl.activateLocationComponent(
      LocationComponentActivationOptions
        .builder(this, style)
        .useDefaultLocationEngine(true)
        .locationEngineRequest(
          LocationEngineRequest.Builder(750)
            .setFastestInterval(750)
            .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
            .build()
        )
        .build()
    )
    locationPluginImpl.addOnCameraTrackingChangedListener(this)
    locationPluginImpl.cameraMode = CameraMode.TRACKING_GPS // CameraMode.TRACKING_GPS_NORTH
    locationPluginImpl.renderMode = RenderMode.GPS
    locationPluginImpl.enabled = true
  }

  override fun onCameraTrackingDismissed() {
    Toast.makeText(this, "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
  }

  override fun onCameraTrackingChanged(currentMode: CameraMode) {
    Toast.makeText(this, "onCameraTrackingChanged $currentMode", Toast.LENGTH_SHORT).show()
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

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
  }
}