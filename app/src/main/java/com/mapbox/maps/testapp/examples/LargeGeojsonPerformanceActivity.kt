package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import kotlinx.android.synthetic.main.activity_large_geojson.*

/**
 * Example of passing large geojson to verify it does not block UI thread.
 */
class LargeGeojsonPerformanceActivity : AppCompatActivity() {

  private lateinit var routePoints: FeatureCollection

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_large_geojson)

    routePoints = FeatureCollection.fromFeature(
      Feature.fromGeometry(
        LineString.fromPolyline(
          DirectionsResponse.fromJson(
            AnnotationUtils.loadStringFromAssets(
              this@LargeGeojsonPerformanceActivity, "long_route.json"
            )
          ).routes()[0].geometry()!!,
          6
        )
      )
    )

    mapView.getMapboxMap()
      .apply {
        setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(LONGITUDE, LATITUDE))
            .zoom(START_ZOOM)
            .build()
        )
        loadStyle(style(Style.DARK) {
          +geoJsonSource("source") {
            featureCollection(routePoints)
          }
          +geoJsonSource("source2") {
            featureCollection(routePoints)
          }
          +geoJsonSource("source3") {
            featureCollection(routePoints)
          }
          +lineLayer("layer", "source") {
            lineColor("blue")
          }
          +lineLayer("layer2", "source2") {
            lineColor("yellow")
            lineOffset(5.0)
          }
          +lineLayer("layer3", "source3") {
            lineColor("red")
            lineOffset(10.0)
          }
        }) {
          loadAdditionalGeoJsonAfter(it)
        }
      }
  }

  private fun loadAdditionalGeoJsonAfter(style: Style) {

    // async method with adding layer in callback

    geoJsonSource(
      id = "source5",
      block = {
        featureCollection(routePoints)
      },
      onGeoJsonParsed = {
        style.addSource(it)
        style.addLayer(lineLayer("layer5", "source5") {
          lineColor("green")
          lineOffset(20.0)
        })
      }
    )

    // async method with adding layer instantly

    style.addSource(geoJsonSource(id = "source4") {
      featureCollection(routePoints)
    })
    style.addLayer(lineLayer("layer4", "source4") {
      lineColor("pink")
      lineOffset(30.0)
    })

    buttonLoad.visibility = View.GONE
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
    private const val LATITUDE = 51.1079
    private const val LONGITUDE = 17.0385
    private const val START_ZOOM = 6.0
    private const val END_ZOOM = 2.0
    private const val SOURCE = "source"
    private const val LAYER = "layer"
  }
}