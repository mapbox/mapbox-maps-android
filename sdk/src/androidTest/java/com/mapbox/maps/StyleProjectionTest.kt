package com.mapbox.maps

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.projection.generated.getProjection
import com.mapbox.maps.extension.style.projection.generated.projection
import com.mapbox.maps.extension.style.projection.generated.setProjection
import org.junit.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class StyleProjectionTest {

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
        mapboxMap.getStyle { style ->
          Assert.assertEquals(
            ProjectionName.MERCATOR,
            style.getProjection().name
          )
        }
      }
    }
  }

  @Test
  fun testGlobeProjectionLowZoom() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          addOnMapIdleListener {
            getStyle { style ->
              Assert.assertEquals(ProjectionName.GLOBE, style.getProjection().name)
              countDownLatch.countDown()
            }
          }
          setCamera(CameraOptions.Builder().zoom(3.0).build())
          getStyle { style ->
            style.setProjection(projection(ProjectionName.GLOBE))
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
          addOnMapIdleListener {
            getStyle { style ->
              // although actual projection looks like Mercator - we now report still it's Globe
              Assert.assertEquals(ProjectionName.GLOBE, style.getProjection().name)
              countDownLatch.countDown()
            }
          }
          setCamera(CameraOptions.Builder().zoom(13.0).build())
          getStyle { style ->
            style.setProjection(projection(ProjectionName.GLOBE))
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