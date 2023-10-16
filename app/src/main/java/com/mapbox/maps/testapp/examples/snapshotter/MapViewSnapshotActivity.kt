package com.mapbox.maps.testapp.examples.snapshotter

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Snapshotter
import com.mapbox.maps.Style
import com.mapbox.maps.testapp.databinding.ActivityViewSnapshotBinding

/**
 * Example demonstrating taking simple snapshot or screenshot not using [Snapshotter] appearing
 * in bottom-left corner as [ImageView].
 */
class MapViewSnapshotActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityViewSnapshotBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mapboxMap = binding.mapView.mapboxMap
    mapboxMap.loadStyle(Style.STANDARD)

    binding.fab.setOnClickListener {
      binding.mapView.snapshot { bitmap ->
        binding.imageView.post {
          binding.imageView.setImageBitmap(bitmap)
        }
      }
    }
  }
}