package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.plugin.animation.getCameraAnimationsPlugin
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_simple_map.*

/**
 * Example of displaying a map.
 */
class SimpleMapActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple_map)
    mapView.getMapboxMap().apply {
      setCamera(
        CameraOptions.Builder().center(Point.fromLngLat(LONGITUDE, LATITUDE)).zoom(9.0).build()
      )
      loadStyleUri(Style.MAPBOX_STREETS)
      subscribe(
        object : Observer() {
          override fun notify(event: Event) {
            Logger.e("KIRYLDD", "type ${event.type}, data ${event.data.toJson()}")
          }
        }, listOf(
          MapEvents.CAMERA_CHANGED,
          MapEvents.MAP_IDLE,
          MapEvents.MAP_LOADED,
          MapEvents.MAP_LOADING_ERROR,
          MapEvents.RENDER_FRAME_FINISHED,
          MapEvents.RENDER_FRAME_STARTED,
//          MapEvents.RESOURCE_REQUEST,
          MapEvents.SOURCE_ADDED,
          MapEvents.SOURCE_DATA_LOADED,
          MapEvents.SOURCE_REMOVED,
//          MapEvents.STYLE_DATA_LOADED,
//          MapEvents.STYLE_IMAGE_MISSING,
//          MapEvents.STYLE_IMAGE_REMOVE_UNUSED,
          MapEvents.STYLE_LOADED)
      )
    }
    btnMoveCamera.setOnClickListener {
      mapView.getCameraAnimationsPlugin().easeTo(
        CameraOptions.Builder()
          .center(Point.fromLngLat(LONGITUDE, LATITUDE * 2))
          .zoom(4.0)
          .build()
      )
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

  companion object {
    private const val LATITUDE = 40.0
    private const val LONGITUDE = -74.5
  }
}