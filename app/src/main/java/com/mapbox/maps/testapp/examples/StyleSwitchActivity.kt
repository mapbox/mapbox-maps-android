package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

    mapboxMap.loadStyle(Style.MAPBOX_STREETS)

    binding.streetsButton.setOnClickListener {
      mapboxMap.loadStyle(Style.MAPBOX_STREETS)
    }
    binding.lightButton.setOnClickListener {
      mapboxMap.loadStyle(Style.LIGHT)
    }
    binding.darkButton.setOnClickListener {
      mapboxMap.loadStyle(Style.DARK)
    }
    binding.satelliteStreetsButton.setOnClickListener {
      mapboxMap.loadStyle(Style.SATELLITE_STREETS)
    }
    binding.satelliteButton.setOnClickListener {
      mapboxMap.loadStyle(Style.SATELLITE)
    }
    binding.outdoorsButton.setOnClickListener {
      mapboxMap.loadStyle(Style.OUTDOORS)
    }
  }
}