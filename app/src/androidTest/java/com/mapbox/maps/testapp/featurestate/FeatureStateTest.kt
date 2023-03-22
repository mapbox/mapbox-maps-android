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
import com.mapbox.maps.testapp.runOnUiThread
import com.mapbox.maps.testapp.withLatch
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test for FeatureState api.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class FeatureStateTest : BaseMapTest() {

  override fun loadMap() {
    withLatch { latch ->
      rule.runOnUiThread {
        mapboxMap = mapView.getMapboxMap()
        mapboxMap.loadStyle(
          style(Style.MAPBOX_STREETS) {
            +geoJsonSource(SOURCE_ID) {
              feature(
                Feature.fromGeometry(
                  Point.fromLngLat(0.0, 0.0),
                  null,
                  FEATURE_ID
                )
              )
            }
            +circleLayer("layer", SOURCE_ID) {
              circleColor("#000000")
              circleRadius(10.0)
            }
          }
        ) {
          latch.countDown()
        }
      }
    }
  }

  /**
   * set feature state {hover : true} to test.
   */
  @Test
  fun testEmptyFeatureState() {
    rule.runOnUiThread {
      mapboxMap.getFeatureState(
        sourceId = SOURCE_ID,
        featureId = FEATURE_ID,
      ) { stateMap ->
        assertEquals(STATE_EMPTY, stateMap.value)
      }
    }
  }

  /**
   * verify feature state value.
   */
  @Test
  fun testSetFeatureState() {
    withLatch { latch ->
      rule.runOnUiThread {
        mapboxMap.setFeatureState(
          sourceId = SOURCE_ID,
          featureId = FEATURE_ID,
          state = Value(hashMapOf("hover" to Value(true)))
        ) {
          mapboxMap.getFeatureState(
            sourceId = SOURCE_ID,
            featureId = FEATURE_ID
          ) { stateMap ->
            assertEquals(STATE_HOVER, stateMap.value)

            latch.countDown()
          }
        }
      }
    }
  }

  @Test
  fun testRemoveFeatureState() {
    withLatch { latch ->
      rule.runOnUiThread {
        mapboxMap.setFeatureState(
          sourceId = SOURCE_ID,
          featureId = FEATURE_ID,
          state = Value(hashMapOf("hover" to Value(true)))
        ) {
          mapboxMap.removeFeatureState(
            sourceId = SOURCE_ID,
            featureId = FEATURE_ID
          ) {
            mapboxMap.getFeatureState(
              sourceId = SOURCE_ID,
              featureId = FEATURE_ID
            ) { stateMap ->
              assertEquals(STATE_EMPTY, stateMap.value)

              latch.countDown()
            }

            latch.countDown()
          }
        }
      }
    }
  }

  @Test
  fun testResetFeatureState() {
    withLatch { latch ->
      rule.runOnUiThread {
        mapboxMap.setFeatureState(
          sourceId = SOURCE_ID,
          featureId = FEATURE_ID,
          state = Value(hashMapOf("hover" to Value(true)))
        ) {
          mapboxMap.resetFeatureStates(
            sourceId = SOURCE_ID,
          ) {
            mapboxMap.getFeatureState(
              sourceId = SOURCE_ID,
              featureId = FEATURE_ID
            ) { stateMap ->
              assertEquals(STATE_EMPTY, stateMap.value)

              latch.countDown()
            }

            latch.countDown()
          }
        }
      }
    }
  }

  companion object {
    private const val FEATURE_ID = "0"
    private const val SOURCE_ID = "source"
    private val STATE_EMPTY = Value(hashMapOf())
    private val STATE_HOVER = Value(hashMapOf("hover" to Value(true)))
  }
}