package com.mapbox.maps.testapp.examples

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityTransparentBackgroundBinding

/**
 * Example of using custom video as a background for MapView.
 * This is possible when using `texture_view` rendering for MapView because it supports alpha channel translucency.
 */
class TransparentBackgroundActivity : AppCompatActivity() {

  private lateinit var binding: ActivityTransparentBackgroundBinding

  private fun initVideoView() {
    val path = "android.resource://" + packageName + "/" + R.raw.moving_background_water
    binding.videoView.apply {
      setVideoURI(Uri.parse(path))
      start()
      setOnCompletionListener { binding.videoView.start() }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityTransparentBackgroundBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.mapView.mapboxMap.loadStyle(
      """
      {
        "version": 8,
        "name": "Land",
        "metadata": {
          "mapbox:autocomposite": true
        },
        "sources": {
          "composite": {
            "url": "mapbox://mapbox.mapbox-terrain-v2",
            "type": "vector"
          }
        },
        "glyphs": "mapbox://fonts/mapbox/{fontstack}/{range}.pbf",
        "layers": [
          {
            "layout": {
              "visibility": "visible"
            },
            "type": "fill",
            "source": "composite",
            "id": "admin",
            "paint": {
              "fill-color": "hsl(359, 100%, 50%)",
              "fill-opacity": 1
            },
            "source-layer": "landcover"
          },
          {
            "layout": {
              "visibility": "visible"
            },
            "type": "fill",
            "source": "composite",
            "id": "layer-0",
            "paint": {
              "fill-opacity": 1,
              "fill-color": "hsl(359, 100%, 50%)"
            },
            "source-layer": "Layer_0"
          }
        ]
      }
      """.trimIndent()
    ) { initVideoView() }
  }

  override fun onDestroy() {
    super.onDestroy()
    binding.videoView.stopPlayback()
  }
}