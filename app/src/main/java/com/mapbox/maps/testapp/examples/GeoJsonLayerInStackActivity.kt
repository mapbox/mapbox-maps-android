package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style

class GeoJsonLayerInStackActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    mapboxMap = mapView.mapboxMap

    mapboxMap.loadStyle(
      style(style = Style.MAPBOX_STREETS) {
        +geoJsonSource("urban-areas") {
          data("https://d2ad6b4ur7yvpq.cloudfront.net/naturalearth-3.3.0/ne_50m_urban_areas.geojson")
        }
        +layerAtPosition(
          fillLayer(layerId = "urban-areas-fill", sourceId = "urban-areas") {
            fillColor("#ff0088")
            fillOpacity(0.4)
          },
          below = "water"
        )
      }
    )

    mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(-84.381546, 33.749909))
        .zoom(8.471903)
        .build()
    )
  }
}