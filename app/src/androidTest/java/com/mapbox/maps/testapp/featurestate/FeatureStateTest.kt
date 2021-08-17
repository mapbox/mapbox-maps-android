package com.mapbox.maps.testapp.featurestate

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.QueryFeaturesCallback
import com.mapbox.maps.RenderedQueryOptions
import com.mapbox.maps.ScreenBox
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.testapp.BaseMapTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Instrumented test for FeatureState api.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class FeatureStateTest : BaseMapTest() {
  private lateinit var countDownLatch: CountDownLatch

  override fun loadMap() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap = mapView.getMapboxMap()
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) { style ->
          // create a geojson source with generatedId = true
          val source = geoJsonSource("source") {
            generateId(true)
          }
          source.feature(
            Feature.fromGeometry(
              Point.fromLngLat(0.0, 0.0)
            )
          )
          // circle layer that will use the geojson source
          val layer = circleLayer("layer", "source") {
            circleColor("#000000")
            circleRadius(10.0)
          }
          style.addSource(source)
          style.addLayer(layer)
          // set feature state { "hover": true } to the first feature using feature ID
          mapView.postDelayed(
            {
              queryRenderFeatures { result ->
                result.value?.takeIf { it.isNotEmpty() }?.let {
                  it.first().feature.id()?.let { featureId ->
                    setFeatureState(featureId, true)
                    countDownLatch.countDown()
                  }
                }
              }
            },
            1_000L
          )
        }
        mapView.onStart()
      }
    }
    if (!countDownLatch.await(5, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  /**
   * query features from mapview
   */
  private fun queryRenderFeatures(callback: QueryFeaturesCallback) {
    mapboxMap.queryRenderedFeatures(
      ScreenBox(
        ScreenCoordinate(mapView.left.toDouble(), mapView.top.toDouble()),
        ScreenCoordinate(mapView.right.toDouble(), mapView.bottom.toDouble())
      ),
      RenderedQueryOptions(listOf("layer"), null),
      callback
    )
  }

  /**
   * set feature state {hover : true} to test.
   */
  private fun setFeatureState(featureId: String, hover: Boolean) {
    mapboxMap.setFeatureState(
      sourceId = "source",
      featureId = featureId,
      state = Value(
        hashMapOf(
          "hover" to Value(hover)
        )
      )
    )
  }

  /**
   * verify feature state value.
   */
  @Test
  fun testHoverFeatureState() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        queryRenderFeatures { result ->
          result.value?.takeIf { it.isNotEmpty() }?.let {
            it.first().feature.id()?.let { featureId ->
              mapboxMap.getFeatureState("source", null, featureId) { stateMap ->
                stateMap.value?.let { value ->
                  Assert.assertEquals(
                    hashMapOf("hover" to true).toString(), value.toString()
                  )
                  countDownLatch.countDown()
                }
              }
            }
          }
        }
      }
    }
    if (!countDownLatch.await(5, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }
}