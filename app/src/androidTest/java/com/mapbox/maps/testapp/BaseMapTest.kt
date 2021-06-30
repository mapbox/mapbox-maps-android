package com.mapbox.maps.testapp

import android.content.Context
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mapbox.maps.*
import com.mapbox.maps.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

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

  @After
  fun tearDown() {
    val latch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.onStop()
        mapView.onDestroy()
        latch.countDown()
      }
    }
    if (!latch.await(10000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  protected open fun initialiseMapView() {
    val latch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView = MapView(context)
        mapView.id = R.id.mapView
        it.setContentView(mapView)
        latch.countDown()
      }
    }
    if (!latch.await(10000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  protected open fun loadMap() {
    val latch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap = mapView.getMapboxMap()
        mapboxMap.loadStyleUri(
          Style.MAPBOX_STREETS
        ) { style ->
          this@BaseMapTest.style = style
          latch.countDown()
        }
        mapView.onStart()
      }
    }

    if (!latch.await(10000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }
}