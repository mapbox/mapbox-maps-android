package com.mapbox.maps.testapp.examples.snapshotter

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Snapshotter
import com.mapbox.maps.Style
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_view_snapshot.*

/**
 * Example demonstrating taking simple snapshot or screenshot not using [Snapshotter] appearing
 * in bottom-left corner as [ImageView].
 */
class MapViewSnapshotActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_view_snapshot)

    mapboxMap = mapView.getMapboxMap()
    mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)

    fab.setOnClickListener {
      val bitmap = mapView.snapshot()
      imageView.setImageBitmap(bitmap)
    }
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