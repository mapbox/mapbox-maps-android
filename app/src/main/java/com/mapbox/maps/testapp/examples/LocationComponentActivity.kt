package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.PresetPuckStyle
import com.mapbox.maps.plugin.ThreeDLocationPuck
import com.mapbox.maps.plugin.TwoDLocationPuck
import com.mapbox.maps.plugin.locationcomponent.getLocationComponentPlugin
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.utils.LocationPermissionHelper
import kotlinx.android.synthetic.main.activity_simple_map.*

class LocationComponentActivity : AppCompatActivity() {

  private var lastStyleUri = Style.DARK
  private lateinit var locationPermissionHelper: LocationPermissionHelper

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_location_component)
    locationPermissionHelper = LocationPermissionHelper(this)
    locationPermissionHelper.checkPermissions {
      mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_location_component, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.action_puck_style_preset_change -> {
        togglePresetPuckStyle()
        return true
      }
      R.id.action_3d_puck_style_change -> {
        toggle3DPuck()
        return true
      }
      R.id.action_2d_puck_style_change -> {
        toggle2DPuck()
        return true
      }
      R.id.action_map_style_change -> {
        toggleMapStyle()
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
      else -> return super.onOptionsItemSelected(item)
    }
  }

  private fun toggle3DPuck() {
    mapView.getLocationComponentPlugin().let {
      if (it.locationPuck == null) {
        it.locationPuck = ThreeDLocationPuck(
          modelUri = "asset://race_car_model.gltf",
          modelScale = listOf(0.1f, 0.1f, 0.1f)
        )
      } else {
        it.locationPuck = null
      }
    }
  }

  private fun toggle2DPuck() {
    mapView.getLocationComponentPlugin().let {
      if (it.locationPuck == null) {
        it.locationPuck = TwoDLocationPuck(
          bearingImage = ContextCompat.getDrawable(this, R.drawable.android_symbol)
        )
      } else {
        it.locationPuck = null
      }
    }
  }

  private fun toggleMapStyle() {
    val styleUrl = if (lastStyleUri == Style.DARK) Style.LIGHT else Style.DARK
    mapView.getMapboxMap().loadStyleUri(styleUrl) {
      lastStyleUri = styleUrl
    }
  }

  private fun togglePresetPuckStyle() {
    mapView.getLocationComponentPlugin().let {
      if (it.locationPuck != null) {
        it.locationPuck = null
      }
      when (it.presetPuckStyle) {
        PresetPuckStyle.PRECISE -> {
          it.presetPuckStyle = PresetPuckStyle.APPROXIMATE
        }
        PresetPuckStyle.APPROXIMATE -> {
          it.presetPuckStyle = PresetPuckStyle.HEADING_ARROW
        }
        PresetPuckStyle.HEADING_ARROW -> {
          it.presetPuckStyle = PresetPuckStyle.PRECISE
        }
      }
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