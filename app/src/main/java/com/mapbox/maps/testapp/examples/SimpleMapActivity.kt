package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.RenderedQueryGeometry
import com.mapbox.maps.RenderedQueryOptions
import com.mapbox.maps.renderer.egl.EGLConfigChooser
import com.mapbox.maps.testapp.examples.markersandcallouts.ViewAnnotationShowcaseActivity

/**
 * Example of displaying a map.
 */
class SimpleMapActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    EGLConfigChooser.STENCIL_SIZE = 23423
    setContentView(mapView)

    mapView.postDelayed(1_000) {
      mapView.getMapboxMap().executeOnRenderThread {
        mapView.getMapboxMap().queryRenderedFeatures(
          RenderedQueryGeometry(mapView.getMapboxMap().pixelForCoordinate(Point.fromLngLat(0.0, 0.0))), RenderedQueryOptions(listOf(
            "layer"
          ), null)
        ) {

        }
      }
    }
  }
}