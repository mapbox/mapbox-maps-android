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

    mapboxMap = binding.mapView.getMapboxMap()

    mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)

    binding.streetsButton.setOnClickListener {
      mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)
    }
    binding.lightButton.setOnClickListener {
      mapboxMap.loadStyleUri(Style.LIGHT)
    }
    binding.darkButton.setOnClickListener {
      mapboxMap.loadStyleUri(Style.DARK)
    }
    binding.satelliteStreetsButton.setOnClickListener {
      mapboxMap.loadStyleUri(Style.SATELLITE_STREETS)
    }
    binding.satelliteButton.setOnClickListener {
      mapboxMap.loadStyleUri(Style.SATELLITE)
    }
    binding.outdoorsButton.setOnClickListener {
      mapboxMap.loadStyleUri(Style.OUTDOORS)
    }
  }
}