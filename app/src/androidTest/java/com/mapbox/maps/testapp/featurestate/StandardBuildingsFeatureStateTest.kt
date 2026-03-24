package com.mapbox.maps.testapp.featurestate

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.interactions.standard.generated.StandardBuildings
import com.mapbox.maps.interactions.standard.generated.StandardBuildingsState
import com.mapbox.maps.testapp.BaseMapTest
import com.mapbox.maps.testapp.runOnUiThread
import com.mapbox.maps.testapp.withLatch
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test verifying that feature IDs returned by [com.mapbox.maps.MapboxMap.queryRenderedFeatures]
 * for StandardBuildings are compatible with [com.mapbox.maps.MapboxMap.setFeatureState] and
 * [com.mapbox.maps.MapboxMap.getFeatureState].
 *
 * Regression test for: double feature IDs being formatted as "12345.000000" instead of "12345"
 * due to a format mismatch in JNI marshaling (feature.hpp).
 */
@OptIn(MapboxExperimental::class)
@RunWith(AndroidJUnit4::class)
class StandardBuildingsFeatureStateTest : BaseMapTest() {

  override fun loadMap() {
    withLatch(timeoutMillis = 30000) { latch ->
      rule.runOnUiThread {
        mapboxMap = mapView.mapboxMap
        mapboxMap.setCamera(
          cameraOptions {
            center(Point.fromLngLat(24.94180921290157, 60.171227338006844))
            zoom(16.0)
          }
        )
        mapboxMap.loadStyle(Style.STANDARD) { style ->
          this@StandardBuildingsFeatureStateTest.style = style
          // Wait for the map to finish rendering tiles before querying features
          mapboxMap.subscribeMapIdle {
            latch.countDown()
          }
        }
      }
    }
  }

  /**
   * Verifies that a building queried via [com.mapbox.maps.MapboxMap.queryRenderedFeatures] can
   * have its state set and retrieved using the typed featureset API.
   *
   * If the feature ID format is wrong (e.g. "12345.000000"), setFeatureState will target a
   * different feature and getFeatureState will return null for select, causing the assertion
   * to fail.
   */
  @Test
  fun testQueryRenderedBuildingIdCompatibleWithSetFeatureState() {
    withLatch(timeoutMillis = 10000) { latch ->
      rule.runOnUiThread {
        mapboxMap.queryRenderedFeatures(StandardBuildings()) { buildings ->
          val building = buildings.firstOrNull { it.id != null }
          assertNotNull("Expected at least one rendered building with an ID", building)
          building!!

          mapboxMap.setFeatureState(building, StandardBuildingsState { select(true) }) {
            mapboxMap.getFeatureState(building) { state ->
              assertEquals(true, state.select)
              latch.countDown()
            }
          }
        }
      }
    }
  }
}