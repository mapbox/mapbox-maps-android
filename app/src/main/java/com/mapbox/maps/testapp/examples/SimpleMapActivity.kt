package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.testapp.R

class SimpleMapActivity : AppCompatActivity() {
  private var toggle = false
  private lateinit var mapView: MapView
  private lateinit var mapboxMap: MapboxMap
  private lateinit var handler: Handler

  private val loc1 = Point.fromLngLat(
    -123.1487,
    49.1550
  )

  private val loc2 = Point.fromLngLat(
    -122.9805,
    49.2488
  )

  private val automateMap = object : Runnable {
    override fun run() {
      toggle = !toggle

      mapboxMap.easeTo(
        cameraOptions {
          center(if (toggle) loc1 else loc2)
        },
        MapAnimationOptions.mapAnimationOptions {
          duration(ANIMATION_DURATION)
        }
      )

      handler.postDelayed(this, ANIMATION_DURATION)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    handler = Handler(Looper.getMainLooper())

    mapView = findViewById(R.id.mapView)
    mapboxMap = mapView.getMapboxMap()

    mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) {
      handler.post(automateMap)
    }
  }

  companion object {
    private const val TAG = "maps-sample-app"
    private const val ANIMATION_DURATION = 1500L
  }
}