package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivitySimpleMapBinding
import com.mapbox.maps.testapp.utils.LocationPermissionHelper
import com.mapbox.maps.toJson

/**
 * Tracks the user location on screen, simulates a navigation session.
 */
class LocationTrackingActivity : AppCompatActivity() {

  private lateinit var locationPermissionHelper: LocationPermissionHelper

  private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
    binding.mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
  }

  private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
    binding.mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
    binding.mapView.gestures.focalPoint = binding.mapView.getMapboxMap().pixelForCoordinate(it)
  }

  private val onMoveListener = object : OnMoveListener {
    override fun onMoveBegin(detector: MoveGestureDetector) {
      onCameraTrackingDismissed()
    }

    override fun onMove(detector: MoveGestureDetector): Boolean {
      return false
    }

    override fun onMoveEnd(detector: MoveGestureDetector) {}
  }
  private lateinit var binding: ActivitySimpleMapBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySimpleMapBinding.inflate(layoutInflater)
    setContentView(binding.root)
    locationPermissionHelper = LocationPermissionHelper(this)
    locationPermissionHelper.checkPermissions {
      onMapReady()
    }
  }

  private fun onMapReady() {
    binding.mapView.getMapboxMap().setCamera(
      CameraOptions.Builder()
        .zoom(14.0)
        .build()
    )
    binding.mapView.getMapboxMap().loadStyleUri(
      Style.MAPBOX_STREETS
    ) {
      initLocationComponent()
      setupGesturesListener()
    }
  }

  private fun setupGesturesListener() {
    binding.mapView.gestures.addOnMoveListener(onMoveListener)
  }

  private fun initLocationComponent() {
    val locationComponentPlugin = binding.mapView.location
    locationComponentPlugin.updateSettings {
      this.enabled = true
      this.locationPuck = LocationPuck2D(
        bearingImage = AppCompatResources.getDrawable(
          this@LocationTrackingActivity,
          R.drawable.mapbox_user_puck_icon,
        ),
        shadowImage = AppCompatResources.getDrawable(
          this@LocationTrackingActivity,
          R.drawable.mapbox_user_icon_shadow,
        ),
        scaleExpression = interpolate {
          linear()
          zoom()
          stop {
            literal(0.0)
            literal(0.6)
          }
          stop {
            literal(20.0)
            literal(1.0)
          }
        }.toJson()
      )
    }
    locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
    locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
  }

  private fun onCameraTrackingDismissed() {
    Toast.makeText(this, "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
    binding.mapView.location
      .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
    binding.mapView.location
      .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    binding.mapView.gestures.removeOnMoveListener(onMoveListener)
  }

  override fun onDestroy() {
    super.onDestroy()
    binding.mapView.location
      .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    binding.mapView.location
      .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
    binding.mapView.gestures.removeOnMoveListener(onMoveListener)
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