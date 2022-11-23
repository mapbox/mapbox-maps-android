package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import com.mapbox.bindgen.Value
import com.mapbox.common.SettingsServiceFactory
import com.mapbox.common.SettingsServiceStorageType
import com.mapbox.geojson.Feature
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.observable.model.SourceDataType
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.logE
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.delegates.listeners.OnSourceDataLoadedListener
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import java.util.*

/**
 * Example of passing large geojson to verify it does not block UI thread
 * for parsing before passed to core.
 */
class LargeGeojsonPerformanceActivity : AppCompatActivity() {

  private val listener = OnSourceDataLoadedListener {
    if (it.type == SourceDataType.METADATA && it.id.startsWith("SOURCE_", ignoreCase = true)) {
      logE("KIRYLDD", "Geojson data applied for ${it.id} at ${System.currentTimeMillis()}")
    }
  }

  private val featureList = mutableListOf<Feature>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)

    val routePoints = Feature.fromJson(
      AnnotationUtils.loadStringFromAssets(
        this@LargeGeojsonPerformanceActivity, LARGE_GEOJSON_ASSET_NAME
      )!!
    )
    featureList.add(routePoints)
    val data = (routePoints.geometry() as LineString).coordinates()
    val freshCopy1 = LinkedList(data)
    val freshCopy2 = LinkedList(data)
    val freshCopy3 = LinkedList(data)
    val freshCopy4 = LinkedList(data)
    val freshCopy5 = LinkedList(data)
    freshCopy1.removeAt(10)
    featureList.add(Feature.fromGeometry(LineString.fromLngLats(freshCopy1)))
    freshCopy2.removeAt(20)
    featureList.add(Feature.fromGeometry(LineString.fromLngLats(freshCopy2)))
    freshCopy3.removeAt(30)
    featureList.add(Feature.fromGeometry(LineString.fromLngLats(freshCopy3)))
    freshCopy4.removeAt(40)
    featureList.add(Feature.fromGeometry(LineString.fromLngLats(freshCopy4)))
    freshCopy5.removeAt(50)
    featureList.add(Feature.fromGeometry(LineString.fromLngLats(freshCopy5)))

    mapView.getMapboxMap()
      .apply {
        addOnSourceDataLoadedListener(listener)
        setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(LONGITUDE, LATITUDE))
            .zoom(START_ZOOM)
            .build()
        )
        // start loading style with multiple very heavy geojson's
        loadStyle(
          style(Style.MAPBOX_STREETS) {}
        ) {
          mapView.postDelayed(0L) {
            // start an animation that uses UI thread to update map camera
            flyTo(
              CameraOptions.Builder()
                .zoom(END_ZOOM)
                .build(),
              MapAnimationOptions.mapAnimationOptions {
                duration(ANIMATION_DURATION_MS)
              }
            )
            // also add couple of additional large geojson's when style is fully loaded
            loadAdditionalGeoJsonAfter(it)
          }
        }
      }
  }

  private fun loadAdditionalGeoJsonAfter(style: Style) {
    for (i in 0 until LARGE_SOURCE_COUNT + 1) {
      SettingsServiceFactory
        .getInstance(SettingsServiceStorageType.NON_PERSISTENT)
        .set("geojson_allow_direct_setter", Value(true))
      logE("KIRYLDD", "Geojson data setting started for ${SOURCE}_$i at ${System.currentTimeMillis()}")
      style.addSource(
        geoJsonSource("${SOURCE}_$i") {
          feature(featureList[i])
        }
      )
      style.addLayer(
        lineLayer("${LAYER}_$i", "${SOURCE}_$i") {
          lineColor("blue")
          lineOffset(5.0 * i)
        }
      )
    }
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