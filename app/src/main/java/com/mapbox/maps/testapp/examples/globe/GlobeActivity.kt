package com.mapbox.maps.testapp.examples.globe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.atmosphere.generated.atmosphere
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.projection.generated.projection
import com.mapbox.maps.extension.style.style

/**
 * This example uses the Style DSL [projection] to display the map as a 3D globe instead of the default Mercator projection.
 * A starry sky and atmosphere effects are added with Style DSL [atmosphere].
 */
class GlobeActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)

    mapView.getMapboxMap().apply {
      setCamera(
        cameraOptions {
          center(CENTER)
          zoom(ZOOM)
        }
      )
      loadStyle(
        style(Style.SATELLITE_STREETS) {
          +atmosphere { }
          +projection(ProjectionName.GLOBE)
        }
      )
    }
  }

  private companion object {
    private const val ZOOM = 0.45
    private val CENTER = Point.fromLngLat(30.0, 50.0)
  }
}