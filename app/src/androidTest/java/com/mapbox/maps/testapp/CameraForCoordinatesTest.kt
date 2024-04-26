package com.mapbox.maps.testapp

import android.widget.FrameLayout
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.util.isEmpty
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
class CameraForCoordinatesTest {

  @get:Rule
  var testActivity = ActivityScenarioRule(EmptyActivity::class.java)

  private val initialCamera = cameraOptions {
    center(Point.fromLngLat(24.9384, 60.1699))
    zoom(4.0)
  }
  private val firstCoordinate = Point.fromLngLat(21.9384, 57.1699)
  private val secondCoordinate = Point.fromLngLat(23.9384, 59.1699)
  private val expectedBearing = 60.0
  private val expectedPitch = 60.0
  private val targetCamera = cameraOptions {
    zoom(10.0)
    bearing(expectedBearing)
    pitch(expectedPitch)
  }
  private val expectedPoint = Point.fromLngLat(22.9384, 58.183962708517015)

  @Test
  fun cameraForCoordinatesBeforeMapSizeCalculated() {
    val latch = CountDownLatch(1)
    var actualCamera: CameraOptions? = null
    testActivity.scenario.onActivity { activity ->
      val mapView = MapView(activity, MapInitOptions(activity, cameraOptions = initialCamera))
      activity.setContentView(mapView)
      mapView.mapboxMap.cameraForCoordinates(
        coordinates = listOf(firstCoordinate, secondCoordinate),
        camera = targetCamera,
        coordinatesPadding = null,
        maxZoom = null,
        offset = null
      ) {
        actualCamera = it
        latch.countDown()
      }
      assert(
        mapView.mapboxMap.cameraForCoordinates(
          coordinates = listOf(firstCoordinate, secondCoordinate),
          camera = targetCamera,
          coordinatesPadding = null,
          maxZoom = null,
          offset = null
        ).isEmpty
      )
    }
    if (!latch.await(LATCH_TIMEOUT_MS, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
    Assert.assertEquals(expectedBearing, actualCamera!!.bearing!!, EPS)
    Assert.assertEquals(expectedPitch, actualCamera!!.pitch!!, EPS)
    Assert.assertEquals(expectedPoint.latitude(), actualCamera!!.center!!.latitude(), EPS)
    Assert.assertEquals(expectedPoint.longitude(), actualCamera!!.center!!.longitude(), EPS)
  }

  @Test
  fun cameraForCoordinatesMultipleCallsBeforeMapSizeCalculated() {
    val latch = CountDownLatch(2)
    var actualCamera: CameraOptions? = null
    var secondActualCamera: CameraOptions? = null
    testActivity.scenario.onActivity { activity ->
      val mapView = MapView(activity, MapInitOptions(activity, cameraOptions = initialCamera))
      activity.setContentView(mapView)
      mapView.mapboxMap.cameraForCoordinates(
        coordinates = listOf(firstCoordinate, secondCoordinate),
        camera = targetCamera,
        coordinatesPadding = null,
        maxZoom = null,
        offset = null
      ) {
        actualCamera = it
        latch.countDown()
      }
      mapView.mapboxMap.cameraForCoordinates(
        coordinates = listOf(
          Point.fromLngLat(17.9384, 53.1699),
          Point.fromLngLat(19.9384, 55.1699),
        ),
        camera = targetCamera,
        coordinatesPadding = null,
        maxZoom = null,
        offset = null
      ) {
        secondActualCamera = it
        latch.countDown()
      }
    }
    if (!latch.await(LATCH_TIMEOUT_MS, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
    Assert.assertEquals(expectedBearing, actualCamera!!.bearing!!, EPS)
    Assert.assertEquals(expectedPitch, actualCamera!!.pitch!!, EPS)
    Assert.assertEquals(expectedPoint.latitude(), actualCamera!!.center!!.latitude(), EPS)
    Assert.assertEquals(expectedPoint.longitude(), actualCamera!!.center!!.longitude(), EPS)

    Assert.assertEquals(expectedBearing, secondActualCamera!!.bearing!!, EPS)
    Assert.assertEquals(expectedPitch, secondActualCamera!!.pitch!!, EPS)
    Assert.assertEquals(54.181989706019365, secondActualCamera!!.center!!.latitude(), EPS)
    Assert.assertEquals(18.9384, secondActualCamera!!.center!!.longitude(), EPS)
  }

  @Test
  fun cameraForCoordinatesAfterMapSizeCalculated() {
    val latch = CountDownLatch(1)
    var actualAsyncCamera: CameraOptions? = null
    var actualSyncCamera: CameraOptions? = null
    testActivity.scenario.onActivity { activity ->
      val mapView = MapView(activity, MapInitOptions(activity, cameraOptions = initialCamera))
      activity.setContentView(mapView)
      mapView.queueEvent(
        {
          activity.runOnUiThread {
            actualSyncCamera = mapView.mapboxMap.cameraForCoordinates(
              coordinates = listOf(firstCoordinate, secondCoordinate),
              camera = targetCamera,
              coordinatesPadding = null,
              maxZoom = null,
              offset = null
            )
            mapView.mapboxMap.cameraForCoordinates(
              coordinates = listOf(firstCoordinate, secondCoordinate),
              camera = targetCamera,
              coordinatesPadding = null,
              maxZoom = null,
              offset = null
            ) {
              actualAsyncCamera = it
              latch.countDown()
            }
          }
        },
        needRender = true
      )
    }
    if (!latch.await(LATCH_TIMEOUT_MS, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
    Assert.assertEquals(expectedBearing, actualAsyncCamera!!.bearing!!, EPS)
    Assert.assertEquals(expectedPitch, actualAsyncCamera!!.pitch!!, EPS)
    Assert.assertEquals(expectedPoint.latitude(), actualAsyncCamera!!.center!!.latitude(), EPS)
    Assert.assertEquals(expectedPoint.longitude(), actualAsyncCamera!!.center!!.longitude(), EPS)

    Assert.assertEquals(actualAsyncCamera, actualSyncCamera)
  }

  @Test
  fun cameraForCoordinatesViewResize() {
    val latch = CountDownLatch(1)
    var actualAsyncCamera: CameraOptions? = null
    testActivity.scenario.onActivity { activity ->
      val mapView = MapView(activity, MapInitOptions(activity, cameraOptions = initialCamera))
      val root = FrameLayout(activity)
      activity.setContentView(root)

      root.addView(mapView)
      mapView.layoutParams.height = 400
      mapView.layoutParams.width = 300

      mapView.queueEvent(
        {
          activity.runOnUiThread {
            // trigger this manually for test for reliability
            // in reality MapView will be resized when calling `requestLayout`
            mapView.onSizeChanged(root.width, root.height)
            assert(
              mapView.mapboxMap.cameraForCoordinates(
                coordinates = listOf(firstCoordinate, secondCoordinate),
                camera = targetCamera,
                coordinatesPadding = null,
                maxZoom = null,
                offset = null
              ).isEmpty
            )
            mapView.queueEvent(
              {
                activity.runOnUiThread {
                  mapView.mapboxMap.cameraForCoordinates(
                    coordinates = listOf(firstCoordinate, secondCoordinate),
                    camera = targetCamera,
                    coordinatesPadding = null,
                    maxZoom = null,
                    offset = null
                  ) {
                    actualAsyncCamera = it
                    latch.countDown()
                  }
                }
              },
              needRender = true
            )
          }
        },
        needRender = true
      )
    }
    if (!latch.await(LATCH_TIMEOUT_MS, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
    Assert.assertEquals(expectedBearing, actualAsyncCamera!!.bearing!!, EPS)
    Assert.assertEquals(expectedPitch, actualAsyncCamera!!.pitch!!, EPS)
    Assert.assertEquals(expectedPoint.latitude(), actualAsyncCamera!!.center!!.latitude(), EPS)
    Assert.assertEquals(expectedPoint.longitude(), actualAsyncCamera!!.center!!.longitude(), EPS)
  }

  private companion object {
    private const val EPS = 0.0001
    private const val LATCH_TIMEOUT_MS = 5L
  }
}