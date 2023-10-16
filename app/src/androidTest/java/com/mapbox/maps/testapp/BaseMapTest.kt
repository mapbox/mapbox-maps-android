package com.mapbox.maps.testapp

import android.content.Context
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mapbox.maps.*
import com.mapbox.maps.R
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

/**
 * Instrumentation test for Layers to test Layer properties.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
abstract class BaseMapTest {
  protected lateinit var mapView: MapView
  protected lateinit var mapboxMap: MapboxMap
  protected lateinit var style: Style
  protected lateinit var context: Context
  protected var pixelRatio: Float = 1.0f

  @get:Rule
  var rule = ActivityScenarioRule(EmptyActivity::class.java)

  @Before
  fun before() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
    pixelRatio = context.resources.displayMetrics.density
    initialiseMapView()
    loadMap()
  }

  protected open fun initialiseMapView() {
    withLatch(
      timeoutMillis = 10000
    ) { latch ->
      rule.runOnUiThread {
        mapView = MapView(context)
        mapView.id = R.id.mapView
        it.setContentView(mapView)
        mapView.onStart()
        latch.countDown()
      }
    }
  }

  protected open fun loadMap() {
    withLatch(
      timeoutMillis = 10000
    ) { latch ->
      rule.runOnUiThread {
        mapboxMap = mapView.mapboxMap
        mapboxMap.loadStyle(
          Style.MAPBOX_STREETS
        ) { style ->
          this@BaseMapTest.style = style

          latch.countDown()
        }
      }
    }
  }
}