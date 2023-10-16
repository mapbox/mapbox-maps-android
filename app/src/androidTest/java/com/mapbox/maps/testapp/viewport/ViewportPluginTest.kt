package com.mapbox.maps.testapp.viewport

import android.os.Handler
import android.os.Looper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Point
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.locationcomponent.LocationConsumer
import com.mapbox.maps.plugin.locationcomponent.LocationProvider
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.ViewportPlugin
import com.mapbox.maps.plugin.viewport.transition.ViewportTransition
import com.mapbox.maps.plugin.viewport.viewport
import com.mapbox.maps.testapp.BaseMapTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ViewportPluginTest : BaseMapTest() {

  private val handler = Handler(Looper.getMainLooper())
  private lateinit var viewportPlugin: ViewportPlugin
  private lateinit var immediateViewportTransition: ViewportTransition

  private val locationProvider = object : LocationProvider {
    val locationConsumers = CopyOnWriteArraySet<LocationConsumer>()
    override fun registerLocationConsumer(locationConsumer: LocationConsumer) {
      locationConsumers.add(locationConsumer)
    }

    override fun unRegisterLocationConsumer(locationConsumer: LocationConsumer) {
      locationConsumers.remove(locationConsumer)
    }
  }

  @Before
  fun setUp() {
    super.before()
    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        viewportPlugin = mapView.viewport
        immediateViewportTransition = viewportPlugin.makeImmediateViewportTransition()
        mapView.mapboxMap.setCamera(START_CAMERA_OPTION)
        mapView.location.apply {
          enabled = true
          setLocationProvider(locationProvider)
        }
      }
    }
  }

  @After
  fun cleanUp() {
    locationProvider.locationConsumers.clear()
  }

  @Test
  fun transitionToDefaultTransition() {
    val latch = CountDownLatch(1)
    handler.post {
      assertEquals(0.0, mapView.mapboxMap.cameraState.bearing, EPS)
      mapView.mapboxMap.cameraState.center.assertEquals(NULL_ISLAND)
      // immediate update location puck to test location.
      locationProvider.locationConsumers.forEach {
        it.onLocationUpdated(
          TEST_POINT,
          options = { duration = 0 }
        )
      }
      locationProvider.locationConsumers.forEach {
        it.onBearingUpdated(
          TEST_BEARING,
          options = { duration = 0 }
        )
      }
      // transition to the followPuckViewportState with default transition
      viewportPlugin.transitionTo(viewportPlugin.makeFollowPuckViewportState()) {
        latch.countDown()
      }
    }

    // Wait for 5 seconds since the default transition time is 3.5 seconds
    if (!latch.await(5, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
    handler.post {
      val cameraState = mapView.mapboxMap.cameraState
      cameraState.center.assertEquals(TEST_POINT)
      assertEquals(TEST_BEARING, cameraState.bearing, EPS)
    }
  }

  @Test
  fun transitionToImmediateTransition() {
    val latch = CountDownLatch(1)
    handler.post {
      assertEquals(0.0, mapView.mapboxMap.cameraState.bearing, EPS)
      mapView.mapboxMap.cameraState.center.assertEquals(NULL_ISLAND)
      // immediate update location puck to test location.
      locationProvider.locationConsumers.forEach {
        it.onLocationUpdated(
          TEST_POINT,
          options = { duration = 0 }
        )
      }
      locationProvider.locationConsumers.forEach {
        it.onBearingUpdated(
          TEST_BEARING,
          options = { duration = 0 }
        )
      }
      // immediately transition to the followPuckViewportState
      viewportPlugin.transitionTo(viewportPlugin.makeFollowPuckViewportState(), immediateViewportTransition) {
        latch.countDown()
      }
    }

    if (!latch.await(200, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    handler.post {
      val cameraState = mapView.mapboxMap.cameraState
      cameraState.center.assertEquals(TEST_POINT)
      assertEquals(TEST_BEARING, cameraState.bearing, EPS)
    }
  }

  @Test
  fun testFollowPuckViewportState() {
    val latch = CountDownLatch(1)
    handler.post {
      assertEquals(0.0, mapView.mapboxMap.cameraState.bearing, EPS)
      mapView.mapboxMap.cameraState.center.assertEquals(NULL_ISLAND)
      // immediate update location puck to test location.
      locationProvider.locationConsumers.forEach {
        it.onLocationUpdated(
          TEST_POINT,
          options = { duration = 0 }
        )
      }
      locationProvider.locationConsumers.forEach {
        it.onBearingUpdated(
          TEST_BEARING,
          options = { duration = 0 }
        )
      }
      // immediately transition to the followPuckViewportState
      viewportPlugin.transitionTo(viewportPlugin.makeFollowPuckViewportState(), immediateViewportTransition) {
        latch.countDown()
      }
    }

    if (!latch.await(200, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    val latch2 = CountDownLatch(1)
    handler.post {
      // emit new bearing and location updates, location component plugin should be driving the animation.
      // and viewport plugin should do animation with 0 duration on each animated location puck position
      locationProvider.locationConsumers.forEach {
        it.onBearingUpdated(
          TEST_BEARING + 90.0,
          options = { duration = 1000 }
        )
      }
      locationProvider.locationConsumers.forEach {
        it.onLocationUpdated(
          TEST_POINT_MOVED,
          options = { duration = 1000 }
        )
      }
    }

    // The location update will be animated with 1 second duration, we wait for 2 seconds for the animation to finish
    latch2.await(2, TimeUnit.SECONDS)

    // validate the camera is at the moved location
    handler.post {
      val cameraState = mapView.mapboxMap.cameraState
      cameraState.center.assertEquals(TEST_POINT_MOVED)
      assertEquals(TEST_BEARING + 90.0, cameraState.bearing, EPS)
    }
  }

  private fun Point.assertEquals(other: Point) {
    assertEquals(other.longitude(), this.longitude(), EPS)
    assertEquals(other.latitude(), this.latitude(), EPS)
    assertEquals(other.altitude(), this.altitude(), EPS)
  }

  private companion object {
    val TEST_POINT: Point = Point.fromLngLat(24.9384, 60.1699)
    val TEST_POINT_MOVED: Point = Point.fromLngLat(24.94284, 60.1699)
    val NULL_ISLAND: Point = Point.fromLngLat(0.0, 0.0)
    val START_CAMERA_OPTION = cameraOptions { center(NULL_ISLAND) }
    const val EPS = 0.000001
    const val TEST_BEARING = 45.0
  }
}