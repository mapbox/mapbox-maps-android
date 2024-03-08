package com.mapbox.maps.testapp

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.mapbox.maps.MapView

class EmptyFragmentActivity : FragmentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
  }
}