package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.webkit.URLUtil
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.testapp.databinding.ActivityStyleSwitchBinding

/**
 * Example of changing style for a map in runtime.
 */
class StyleSwitchActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap
  private lateinit var binding: ActivityStyleSwitchBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityStyleSwitchBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mapboxMap = binding.mapView.mapboxMap
    mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(-0.1213, 51.5015))
        .zoom(15.0)
        .bearing(57.0)
        .pitch(60.0)
        .build()
    )

    // Instead of this you can add your default style to the map layout with xml attribute `app:mapbox_styleUri="mapbox://styles/streets-v12"`
    mapboxMap.loadStyle(Style.STANDARD)

    binding.streetsButton.setOnClickListener {
      mapboxMap.loadStyle(Style.MAPBOX_STREETS)
    }
    binding.lightButton.setOnClickListener {
      mapboxMap.loadStyle(Style.LIGHT)
    }
    binding.darkButton.setOnClickListener {
      mapboxMap.loadStyle(Style.DARK)
    }
    binding.customStyleButton.setOnClickListener {
      showStyleInputDialog()
    }
    binding.satelliteButton.setOnClickListener {
      mapboxMap.loadStyle(Style.SATELLITE)
    }
    binding.outdoorsButton.setOnClickListener {
      mapboxMap.loadStyle(Style.OUTDOORS)
    }
    binding.standardButton.setOnClickListener {
      mapboxMap.loadStyle(Style.STANDARD)
    }
    binding.standardSatelliteButton.setOnClickListener {
      mapboxMap.loadStyle(Style.STANDARD_SATELLITE)
    }
  }

  private fun showStyleInputDialog() {
    val editText = EditText(this)
    val dialog = AlertDialog.Builder(this)
      .setTitle("Mapbox Style URL")
      .setMessage("Paste the “Share URL” for your public Mapbox style")
      .setView(editText)
      .setPositiveButton("Save") { _, _ ->
        val input = editText.text.toString()
        saveStyleURLInput(input)
      }
      .setNegativeButton("Cancel", null)
      .create()
    dialog.show()
  }

  private fun saveStyleURLInput(input: String) {
    if (input.isValidUri()) {
      mapboxMap.loadStyle(input)
    } else {
      Toast.makeText(this, "Invalid URL. Please check your Mapbox Studio Style URL.", Toast.LENGTH_SHORT).show()
    }
  }

  private fun String.isValidUri(): Boolean {
    val isMapboxStyleUri = startsWith("mapbox://", ignoreCase = true)
    val isMapboxAssetUri = startsWith("asset://", ignoreCase = true)
    val isMapboxFileUri = startsWith("file://", ignoreCase = true)
    return isMapboxStyleUri || isMapboxAssetUri || isMapboxFileUri || URLUtil.isValidUrl(this)
  }
}