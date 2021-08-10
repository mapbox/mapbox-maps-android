package com.mapbox.maps.testapp.examples

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils

/**
 * Example of passing large geojson to verify it does not block UI thread
 * for parsing before passed to core.
 */
class LargeGeojsonPerformanceActivity : AppCompatActivity() {

  private lateinit var routePoints: FeatureCollection

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)

    routePoints = FeatureCollection.fromFeature(
      Feature.fromGeometry(
        LineString.fromPolyline(
          DirectionsResponse.fromJson(
            AnnotationUtils.loadStringFromAssets(
              this@LargeGeojsonPerformanceActivity, LARGE_GEOJSON_ASSET_NAME
            )
          ).routes()[0].geometry()!!,
          Constants.PRECISION_6
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
        // start an animation that uses UI thread to update map camera
        flyTo(
          CameraOptions.Builder()
            .zoom(END_ZOOM)
            .build(),
          MapAnimationOptions.mapAnimationOptions {
            duration(ANIMATION_DURATION_MS)
          }
        )
        // start loading style with multiple very heavy geojson's
        loadStyle(
          style(Style.MAPBOX_STREETS) {
            for (i in 0 until LARGE_SOURCE_COUNT) {
              +geoJsonSource("${SOURCE}_$i") {
                featureCollection(routePoints)
              }
              +lineLayer("${LAYER}_$i", "${SOURCE}_$i") {
                lineColor("blue")
                lineOffset(5.0 * i)
              }
            }
            // add an icon that uses very small geojson source
            +image("icon") {
              bitmap(BitmapFactory.decodeResource(resources, R.drawable.blue_marker_view))
            }
            +geoJsonSource("${SOURCE}_marker") {
              geometry(Point.fromLngLat(LONGITUDE, LATITUDE))
            }
            +symbolLayer("${LAYER}_marker", "${SOURCE}_marker") {
              iconImage("icon")
              iconAnchor(IconAnchor.BOTTOM)
            }
          }
        ) {
          // also add couple of additional large geojson's when style is fully loaded
          loadAdditionalGeoJsonAfter(it)
        }
      }
  }

  private fun loadAdditionalGeoJsonAfter(style: Style) {
    // async method with adding layer in callback when geojson is completely parsed
    geoJsonSource(
      id = "${SOURCE}_$LARGE_SOURCE_COUNT",
      config = {
        featureCollection(routePoints)
      },
      onGeoJsonParsed = {
        style.addSource(it)
        style.addLayer(
          lineLayer("${LAYER}_$LARGE_SOURCE_COUNT", "${SOURCE}_$LARGE_SOURCE_COUNT") {
            lineColor("green")
            lineOffset(5.0 * LARGE_SOURCE_COUNT)
          }
        )
      }
    )
    // async method with adding layer instantly without waiting for geojson to actually parse
    style.addSource(
      geoJsonSource(id = "${SOURCE}_${LARGE_SOURCE_COUNT + 1}") {
        featureCollection(routePoints)
      }
    )
    style.addLayer(
      lineLayer("${LAYER}_${LARGE_SOURCE_COUNT + 1}", "${SOURCE}_${LARGE_SOURCE_COUNT + 1}") {
        lineColor("red")
        lineOffset(5.0 * (LARGE_SOURCE_COUNT + 1))
      }
    )
  }

  companion object {
    private const val LARGE_GEOJSON_ASSET_NAME = "long_route.json"
    private const val ANIMATION_DURATION_MS = 10_000L
    private const val SOURCE = "source"
    private const val LAYER = "layer"
    private const val LARGE_SOURCE_COUNT = 5
    private const val LATITUDE = 51.1079
    private const val LONGITUDE = 17.0385
    private const val START_ZOOM = 6.0
    private const val END_ZOOM = 2.0
  }
}