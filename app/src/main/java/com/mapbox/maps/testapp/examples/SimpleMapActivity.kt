package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.renderer.WidgetRenderer
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_simple_map.*

/**
 * Example of displaying a map.
 */
class SimpleMapActivity : AppCompatActivity() {

  private val widgetRenderer = WidgetRenderer()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple_map)
    mapView.getMapboxMap()
      .apply {
        setCamera(
          CameraOptions.Builder().center(Point.fromLngLat(LONGITUDE, LATITUDE)).zoom(9.0).build()
        )
      }

    mapView.getMapboxMap().addOnMapIdleListener {
      mapView.queueEvent({
        widgetRenderer.initialize()
      }, false)
      mapView.getMapboxMap().addOnRenderFrameStartedListener {
        Logger.e("KIRYLDD", "render frame started")
        mapView.queueEvent(
          {
            Logger.e("KIRYLDD", "queue from render frame started")
            widgetRenderer.render()
          }, true
        )
      }
    }

  }

  companion object {
    private const val LATITUDE = 40.0
    private const val LONGITUDE = -74.5
  }
}