package com.mapbox.maps

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.mapbox.maps.plugin.MapProjection
import org.junit.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class GlobeIntegrationTest {

  @get:Rule
  val rule = ActivityScenarioRule(EmptyActivity::class.java)

  private lateinit var mapView: MapView
  private lateinit var mapboxMap: MapboxMap
  private lateinit var countDownLatch: CountDownLatch

  @Before
  @UiThreadTest
  fun setUp() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      mapView = MapView(it)
      mapboxMap = mapView.getMapboxMap()
      it.frameLayout.addView(mapView)
      mapView.onStart()
      countDownLatch.countDown()
    }
    if (!countDownLatch.await(LATCH_TIMEOUT_SEC, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun testDefaultProjection() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        Assert.assertEquals(MapProjection.Mercator, mapboxMap.getMapProjection())
      }
    }
  }

  @Test
  fun testGlobeProjectionLowZoom() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          setMapProjection(MapProjection.Globe)
          setCamera(CameraOptions.Builder().zoom(MapProjection.TRANSITION_ZOOM_LEVEL - 2.0).build())
          addOnMapIdleListener {
            Assert.assertEquals(MapProjection.Globe, mapboxMap.getMapProjection())
            countDownLatch.countDown()
          }
        }
      }
    }
    if (!countDownLatch.await(LATCH_TIMEOUT_SEC, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun testGlobeProjectionHighZoom() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          setMapProjection(MapProjection.Globe)
          setCamera(CameraOptions.Builder().zoom(MapProjection.TRANSITION_ZOOM_LEVEL + 2.0).build())
          addOnMapIdleListener {
            Assert.assertEquals(MapProjection.Mercator, mapboxMap.getMapProjection())
            countDownLatch.countDown()
          }
        }
      }
    }
    if (!countDownLatch.await(LATCH_TIMEOUT_SEC, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun testGlobeProjectionTransitionZoom() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          setMapProjection(MapProjection.Globe)
          setCamera(CameraOptions.Builder().zoom(MapProjection.TRANSITION_ZOOM_LEVEL).build())
          addOnMapIdleListener {
            Assert.assertEquals(MapProjection.Mercator, mapboxMap.getMapProjection())
            countDownLatch.countDown()
          }
        }
      }
    }
    if (!countDownLatch.await(LATCH_TIMEOUT_SEC, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @After
  @UiThreadTest
  fun teardown() {
    mapView.onStop()
    mapView.onDestroy()
  }

  companion object {
    private const val LATCH_TIMEOUT_SEC = 5L
  }
}