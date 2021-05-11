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
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
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
//        loadStyleUri(Style.MAPBOX_STREETS) { style ->
//          buttonLoad.setOnClickListener {
//            flyTo(
//              CameraOptions.Builder()
//                .zoom(END_ZOOM)
//                .build(),
//              MapAnimationOptions.mapAnimationOptions {
//                duration(10_000L)
//              })
//            it.postDelayed({
//              loadGeoJson(style)
//            }, 1000)
//          }
//        }

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
            lineWidth(10.0)
          }
          +lineLayer("layer2", "source2") {
            lineColor("yellow")
            lineWidth(5.0)
          }
          +lineLayer("layer3", "source3") {
            lineColor("red")
          }
        })
      }
  }

  private fun loadGeoJson(style: Style) {
    val start = System.currentTimeMillis()

    // SYNC METHOD

//    style.addSource(geoJsonSource(SOURCE) {
//      featureCollection(routePoints)
//    })

    // ASYNC METHOD

//    geoJsonSource(
//      id = SOURCE,
//      block = {
//        featureCollection(routePoints)
//      },
//      result = {
//        style.addSource(it)
//        style.addLayer(lineLayer(LAYER, SOURCE) {
//          lineColor("blue")
//        })
//      }
//    )
//
//    // add some useless extra work to parse same geojson
//    for (i in 0..5) {
//      geoJsonSource(
//        id = i.toString(),
//        block = {
//          featureCollection(routePoints)
//        },
//        result = {
//          style.addSource(it)
//        }
//      )
//    }
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