package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityLocationComponentBinding
import com.mapbox.maps.testapp.utils.LocationPermissionHelper
import java.lang.ref.WeakReference

class LocationComponentActivity : AppCompatActivity() {

  private var lastStyleUri = Style.DARK
  private lateinit var locationPermissionHelper: LocationPermissionHelper
  private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
    // Jump to the current indicator position
    binding.mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
    // Set the gestures plugin's focal point to the current indicator location.
    binding.mapView.gestures.focalPoint = binding.mapView.getMapboxMap().pixelForCoordinate(it)
  }
  private lateinit var binding: ActivityLocationComponentBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLocationComponentBinding.inflate(layoutInflater)
    setContentView(binding.root)
    locationPermissionHelper = LocationPermissionHelper(WeakReference(this))
    locationPermissionHelper.checkPermissions {
      binding.mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) {
        // Disable scroll gesture, since we are updating the camera position based on the indicator location.
        binding.mapView.gestures.scrollEnabled = false
        binding.mapView.gestures.addOnMapClickListener { point ->
          binding.mapView.location
            .isLocatedAt(point) { isPuckLocatedAtPoint ->
              if (isPuckLocatedAtPoint) {
                Toast.makeText(this, "Clicked on location puck", Toast.LENGTH_SHORT).show()
              }
            }
          true
        }
        binding.mapView.gestures.addOnMapLongClickListener { point ->
          binding.mapView.location
            .isLocatedAt(point) { isPuckLocatedAtPoint ->
              if (isPuckLocatedAtPoint) {
                Toast.makeText(this, "Long-clicked on location puck", Toast.LENGTH_SHORT).show()
              }
            }
          true
        }
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_location_component, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.action_customise_location_puck_change -> {
        toggleCustomisedPuck()
        return true
      }
      R.id.action_map_style_change -> {
        toggleMapStyle()
        return true
      }
      R.id.action_component_disable -> {
        binding.mapView.location.enabled = false
        return true
      }
      R.id.action_component_enabled -> {
        binding.mapView.location.enabled = true
        return true
      }
      else -> return super.onOptionsItemSelected(item)
    }
  }

  private fun toggleCustomisedPuck() {
    binding.mapView.location.let {
      when (it.locationPuck) {
        is LocationPuck3D -> it.locationPuck = LocationPuck2D(
          topImage = AppCompatResources.getDrawable(
            this,
            com.mapbox.maps.plugin.locationcomponent.R.drawable.mapbox_user_icon
          ),
          bearingImage = AppCompatResources.getDrawable(
            this,
            com.mapbox.maps.plugin.locationcomponent.R.drawable.mapbox_user_bearing_icon
          ),
          shadowImage = AppCompatResources.getDrawable(
            this,
            com.mapbox.maps.plugin.locationcomponent.R.drawable.mapbox_user_stroke_icon
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
        is LocationPuck2D -> it.locationPuck = LocationPuck3D(
          modelUri = "asset://sportcar.glb",
          modelScale = listOf(0.1f, 0.1f, 0.1f),
          modelTranslation = listOf(0.1f, 0.1f, 0.1f),
          modelRotation = listOf(0.0f, 0.0f, 180.0f)
        )
      }
    }
  }

  private fun toggleMapStyle() {
    val styleUrl = if (lastStyleUri == Style.DARK) Style.LIGHT else Style.DARK
    binding.mapView.getMapboxMap().loadStyleUri(styleUrl) {
      lastStyleUri = styleUrl
    }
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
    binding.mapView.location
      .addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
  }

  override fun onStop() {
    super.onStop()
    binding.mapView.location
      .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
  }
}