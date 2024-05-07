package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.lifecycleScope
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.logI
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Example of passing large geojson to verify it does not block UI thread
 * for parsing before passed to core.
 */
class LargeGeojsonPerformanceActivity : AppCompatActivity() {

  private var jsonUpdateCounter = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)

    mapView.mapboxMap.apply {
      subscribeSourceDataLoaded {
        if (it.dataId.isNullOrBlank().not()) {
          // log whenever update with corresponding data-id is rendered on the map
          logI(TAG, "GeoJsonSource was updated, data-id : ${it.dataId}")
        }
      }

      setCamera(
        CameraOptions.Builder()
          .center(Point.fromLngLat(LONGITUDE, LATITUDE))
          .zoom(START_ZOOM)
          .build()
      )
      loadStyle(
        style(Style.STANDARD) {
          // add an icon that uses very small geojson source
          +image(
            "icon",
            ContextCompat.getDrawable(
              this@LargeGeojsonPerformanceActivity,
              R.drawable.ic_blue_marker
            )!!.toBitmap()
          )
          +geoJsonSource("${SOURCE}_marker") {
            logI(TAG, "Update marker ${SOURCE}_marker, data-id : $jsonUpdateCounter")
            geometry(Point.fromLngLat(LONGITUDE, LATITUDE), jsonUpdateCounter++.toString())
          }
          +symbolLayer("${LAYER}_marker", "${SOURCE}_marker") {
            iconImage("icon")
            iconAnchor(IconAnchor.BOTTOM)
          }
        }
      ) {
        lifecycleScope.launch {
          // start an animation that uses UI thread to update map camera
          flyTo(
            CameraOptions.Builder().zoom(END_ZOOM).build(),
            MapAnimationOptions.mapAnimationOptions { duration(ANIMATION_DURATION_MS) }
          )
          // Converting a big String to Feature takes some time so we'll do it in a worker thread
          val routePoints = withContext(Dispatchers.Default) {
            Feature.fromJson(
              AnnotationUtils.loadStringFromAssets(
                this@LargeGeojsonPerformanceActivity,
                LARGE_GEOJSON_ASSET_NAME
              )
            )
          }

          addGeoJsonLines(routePoints, mapView.mapboxMap)
        }
      }
    }
  }

  private fun addGeoJsonLines(routePoints: Feature, mapboxStyleManager: MapboxStyleManager) {
    repeat(LARGE_SOURCE_COUNT) {
      mapboxStyleManager.addSource(
        geoJsonSource("${SOURCE}_$it") {
          logI(TAG, "Update routePoints ${SOURCE}_$it, data-id : $jsonUpdateCounter")
          feature(routePoints, jsonUpdateCounter++.toString())
        }
      )
      mapboxStyleManager.addLayer(
        lineLayer("${LAYER}_$it", "${SOURCE}_$it") {
          lineColor("blue")
          lineOffset(5.0 * it)
        }
      )
    }
  }

  companion object {
    private const val LARGE_GEOJSON_ASSET_NAME = "long_route.json"
    private const val ANIMATION_DURATION_MS = 10_000L
    private const val SOURCE = "source"
    private const val LAYER = "layer"
    private const val LARGE_SOURCE_COUNT = 6
    private const val LATITUDE = 51.1079
    private const val LONGITUDE = 17.0385
    private const val START_ZOOM = 5.9
    private const val END_ZOOM = 2.0
    private const val TAG = "LargeGeojsonUpdate"
  }
}