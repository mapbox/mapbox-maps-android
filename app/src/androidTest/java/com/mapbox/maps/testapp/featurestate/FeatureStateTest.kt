package com.mapbox.maps.testapp.featurestate

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
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

  override fun loadMap() {
    val countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap = mapView.getMapboxMap()
        mapboxMap.loadStyle(
          style(Style.MAPBOX_STREETS) {
            +geoJsonSource("source") {
              feature(
                Feature.fromGeometry(
                  Point.fromLngLat(0.0, 0.0),
                  null,
                  FEATURE_ID
                )
              )
            }
            +circleLayer("layer", "source") {
              circleColor("#000000")
              circleRadius(10.0)
            }
          }
        ) {
          // set feature state { "hover": true } to the first feature using feature ID
          mapView.postDelayed(
            {
              setFeatureState()
              countDownLatch.countDown()
            },
            1_000L
          )
        }
      }
    }
    mapView.onStart()
    if (!countDownLatch.await(5, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  /**
   * set feature state {hover : true} to test.
   */
  private fun setFeatureState() {
    mapboxMap.setFeatureState(
      sourceId = "source",
      featureId = FEATURE_ID,
      state = Value(
        hashMapOf(
          "hover" to Value(true)
        )
      )
    )
  }

  /**
   * verify feature state value.
   */
  @Test
  fun testHoverFeatureState() {
    val countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.getFeatureState(
          sourceId = "source",
          featureId = FEATURE_ID
        ) { stateMap ->
          stateMap.value?.let { value ->
            Assert.assertEquals(
              hashMapOf("hover" to true).toString(), value.toString()
            )
            countDownLatch.countDown()
          }
        }
      }
    }
    if (!countDownLatch.await(5, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  companion object {
    private const val FEATURE_ID = "0"
  }
}