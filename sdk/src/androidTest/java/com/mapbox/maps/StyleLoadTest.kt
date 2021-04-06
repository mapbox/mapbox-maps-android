package com.mapbox.maps

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import junit.framework.TestCase.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
@LargeTest
class StyleLoadTest {

  @get:Rule
  val rule = ActivityScenarioRule(EmptyActivity::class.java)

  private lateinit var mapView: MapView
  private lateinit var mapboxMap: MapboxMap
  private lateinit var countDownLatch: CountDownLatch

  @Before
  @UiThreadTest
  fun setUp() {
    rule.scenario.onActivity {
      mapView = MapView(it)
      mapboxMap = mapView.getMapboxMap()
      it.frameLayout.addView(mapView)
      mapView.onStart()
    }
  }

  @Test
  fun testStyleNull() {
    countDownLatch = CountDownLatch(1)
    var callbackInvoked = false
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.getStyle { callbackInvoked = true }
      }
    }
    if (!countDownLatch.await(3, TimeUnit.SECONDS)) {
      assertTrue(callbackInvoked)
    }
  }

  @Test
  fun testStyleAsyncGetter() {
    countDownLatch = CountDownLatch(2)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.getStyle { style ->
          assertNotNull("Style should but non null", style)
          assertTrue("Style should be fully loaded", style.fullyLoaded)
          countDownLatch.countDown()
        }

        mapboxMap.loadStyleUri(
          Style.MAPBOX_STREETS
        ) { style ->
          assertNotNull("Style should but non null", style)
          assertTrue("Style should be fully loaded", style.fullyLoaded)
          countDownLatch.countDown()
        }
      }
    }
    if (!countDownLatch.await(30, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun testFullyLoadedFalse() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.loadStyleUri(
          Style.MAPBOX_STREETS
        ) { style ->
          assertNotNull("Style should but non null", style)
          assertTrue("Style should be fully loaded", style.fullyLoaded)
          mapboxMap.loadStyleUri(Style.DARK)
          assertFalse("Map shouldn't be fully loaded", style.fullyLoaded)
          countDownLatch.countDown()
        }
      }
    }
    countDownLatch = CountDownLatch(1)
    if (!countDownLatch.await(30, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @After
  @UiThreadTest
  fun teardown() {
    mapView.onStop()
    mapView.onDestroy()
  }
}