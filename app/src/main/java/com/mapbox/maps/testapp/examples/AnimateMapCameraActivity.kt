package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityCameraAnimateBinding

/**
 * Example of map camera transition between two points.
 */
class AnimateMapCameraActivity : AppCompatActivity(), OnMapClickListener {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityCameraAnimateBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapboxMap = binding.mapView.getMapboxMap()
    mapboxMap.loadStyleUri(
      Style.MAPBOX_STREETS
    ) { // Toast instructing user to tap on the map
      Toast.makeText(
        this@AnimateMapCameraActivity,
        getString(R.string.tap_on_map_instruction),
        Toast.LENGTH_LONG
      ).show()
      mapboxMap.addOnMapClickListener(this@AnimateMapCameraActivity)
    }
  }

  override fun onMapClick(point: Point): Boolean {
    mapboxMap.flyTo(
      cameraOptions {
        center(Point.fromLngLat(-0.07520, 51.50550)) // Sets the new camera position
        zoom(17.0) // Sets the zoom
        bearing(180.0) // Rotate the camera
        pitch(30.0) // Set the camera pitch
      },
      mapAnimationOptions {
        duration(7000)
      }
    )
    return true
  }
}