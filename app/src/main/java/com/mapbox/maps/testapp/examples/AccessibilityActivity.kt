package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.testapp.databinding.ActivityAccessibilityBinding

var mapView: MapView? = null

class AccessibilityActivity: AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityAccessibilityBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapView = binding.mapView
    mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)
  }

  override fun onStart() {
    super.onStart()
    mapView?.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView?.onStop()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView?.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView?.onDestroy()
  }

  companion object {
    const val TAG = "AccessibilityActivity"
  }
}