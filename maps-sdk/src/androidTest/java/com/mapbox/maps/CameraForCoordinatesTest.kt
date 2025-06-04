package com.mapbox.maps

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.geojson.Point
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.util.isEmpty
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class CameraForCoordinatesTest {

  @get:Rule
  val rule = ActivityScenarioRule(EmptyActivity::class.java)

  private lateinit var mapView: MapView
  private lateinit var mapboxMap: MapboxMap
  private lateinit var countDownLatch: CountDownLatch

  @Before
  fun setUp() {
    val latch = CountDownLatch(2)
    rule.scenario.onActivity {
      mapView = MapView(it, MapInitOptions(it))
      mapboxMap = mapView.mapboxMap
      // Hardcoded width and height to avoid issues with different screen sizes
      it.frameLayout.addView(mapView, 200, 200)
      mapboxMap.getStyle {
        latch.countDown()
      }
      mapboxMap.subscribeMapIdle {
        latch.countDown()
      }
    }
    latch.throwExceptionOnTimeoutMs(timeoutMs = 30_000L)
  }

  @OptIn(MapboxDelicateApi::class)
  @Test
  fun basicCameraForCoordinates() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.onStart()
        val points: List<Point> = listOf(
          Point.fromLngLat(0.0, 0.0),
          Point.fromLngLat(1.0, 1.0),
          Point.fromLngLat(2.0, 2.0),
          Point.fromLngLat(4.0, 4.0),
        )
        val cameraForCoordinates = mapboxMap.cameraForCoordinates(
          coordinates = points,
          camera = cameraOptions { },
          coordinatesPadding = null,
          maxZoom = null,
          offset = null,
        )
        Assert.assertFalse(cameraForCoordinates.isEmpty)
        Assert.assertEquals(2.001, cameraForCoordinates.center!!.latitude(), 0.001)
        Assert.assertEquals(2.001, cameraForCoordinates.center!!.longitude(), 0.001)
        Assert.assertEquals(3.738, cameraForCoordinates.zoom!!, 0.001)
        countDownLatch.countDown()
      }
    }
    countDownLatch.throwExceptionOnTimeoutMs()
  }

  @OptIn(MapboxDelicateApi::class)
  @Test
  fun antimeridianCameraForCoordinates() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.onStart()
        val points: List<Point> = listOf(
          Point.fromLngLat(-180.0, 10.0),
          Point.fromLngLat(180.0, 20.0),
        )
        val cameraForCoordinates = mapboxMap.cameraForCoordinates(
          coordinates = points,
          camera = cameraOptions { },
          coordinatesPadding = null,
          maxZoom = null,
          offset = null,
        )
        Assert.assertFalse(cameraForCoordinates.isEmpty)
        Assert.assertEquals(-180.0, cameraForCoordinates.center!!.longitude(), 0.001)
        Assert.assertEquals(85.902, cameraForCoordinates.center!!.latitude(), 0.001)
        Assert.assertEquals(0.0, cameraForCoordinates.zoom!!, 0.001)
        countDownLatch.countDown()
      }
    }
    countDownLatch.throwExceptionOnTimeoutMs()
  }

  @OptIn(MapboxDelicateApi::class)
  @Test
  fun antimeridian2CameraForCoordinates() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.onStart()
        val points: List<Point> = listOf(
          Point.fromLngLat(-185.0, 19.5),
          Point.fromLngLat(-150.0, 20.0),
        )
        val cameraForCoordinates = mapboxMap.cameraForCoordinates(
          coordinates = points,
          camera = cameraOptions { },
          coordinatesPadding = null,
          maxZoom = null,
          offset = null,
        )
        Assert.assertFalse(cameraForCoordinates.isEmpty)
        Assert.assertEquals(-167.5, cameraForCoordinates.center!!.longitude(), 0.001)
        Assert.assertEquals(20.179, cameraForCoordinates.center!!.latitude(), 0.001)
        Assert.assertEquals(0.478, cameraForCoordinates.zoom!!, 0.001)
        mapboxMap.setCamera(cameraForCoordinates)
        countDownLatch.countDown()
      }
    }
    countDownLatch.throwExceptionOnTimeoutMs()
  }

  @After
  @UiThreadTest
  fun teardown() {
    mapView.onStop()
    mapView.onDestroy()
  }
}