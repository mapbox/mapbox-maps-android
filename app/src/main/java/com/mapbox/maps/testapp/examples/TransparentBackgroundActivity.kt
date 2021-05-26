package com.mapbox.maps.testapp.examples

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_transparent_background.*

/**
 * Example of using custom video as a background for MapView.
 * This is possible when using `texture_view` rendering for MapView because it supports alpha channel translucency.
 */
class TransparentBackgroundActivity : AppCompatActivity() {

  private fun initVideoView() {
    val path = "android.resource://" + packageName + "/" + R.raw.moving_background_water
    videoView.apply {
      setVideoURI(Uri.parse(path))
      start()
      setOnCompletionListener { videoView.start() }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_transparent_background)

    mapView.getMapboxMap().loadStyleJson(
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
        "sprite": "mapbox://sprites/mapbox/mapbox-terrain-v2",
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
    videoView.stopPlayback()
    mapView.onDestroy()
  }
}