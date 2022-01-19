package com.mapbox.maps.testapp.examples

import android.opengl.GLES20
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView

/**
 * Example of displaying a map.
 */
class SimpleMapActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    val runnable = Runnable {
      Logger.e("Mbgl-Kiryl", "Event")
      Logger.e("Mbgl-Kiryl", GLES20.glGetString(GLES20.GL_RENDERER))
    }
    Logger.e("Mbgl-Kiryl", "Runnable $runnable")
    mapView.queueEvent(runnable, needRender = false)
    mapView.getMapboxMap()
      .apply {
        setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(LONGITUDE, LATITUDE))
            .zoom(9.0)
            .build()
        )
      }
  }

  companion object {
    private const val LATITUDE = 40.0
    private const val LONGITUDE = -74.5
  }
}