package com.mapbox.maps.testapp.viewport

import android.os.Handler
import android.os.Looper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.locationcomponent.LocationConsumer
import com.mapbox.maps.plugin.locationcomponent.LocationProvider
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.ViewportPlugin
import com.mapbox.maps.plugin.viewport.state.FollowPuckViewportState
import com.mapbox.maps.plugin.viewport.transition.ViewportTransition
import com.mapbox.maps.plugin.viewport.viewport
import com.mapbox.maps.testapp.BaseMapTest
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
  private val followPuckViewportState: FollowPuckViewportState by lazy {
    viewportPlugin.makeFollowPuckViewportState()
  }
  private val immediateViewportTransition: ViewportTransition by lazy {
    viewportPlugin.makeImmediateViewportTransition()
  }

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
        mapView.location.apply {
          enabled = true
          setLocationProvider(locationProvider)
        }
      }
    }
  }

  @Test
  fun transitionToDefaultTransition() {
    val testBearing = 45.0
    handler.post {
      viewportPlugin.transitionTo(followPuckViewportState)
      assertEquals(0.0, mapView.getMapboxMap().cameraState.bearing, EPS)
      mapView.getMapboxMap().cameraState.center.assertEquals(NULL_ISLAND)
      locationProvider.locationConsumers.forEach { it.onLocationUpdated(TEST_POINT) }
      locationProvider.locationConsumers.forEach { it.onBearingUpdated(testBearing) }
    }

    val latch = CountDownLatch(1)
    latch.await(4000, TimeUnit.MILLISECONDS)
    handler.post {
      mapView.getMapboxMap().cameraState.center.assertEquals(TEST_POINT)
    }
  }

  @Test
  fun transitionToImmediateTransition() {
    val testBearing = 45.0
    handler.post {
      viewportPlugin.transitionTo(followPuckViewportState, immediateViewportTransition)
      assertEquals(0.0, mapView.getMapboxMap().cameraState.bearing, EPS)
      mapView.getMapboxMap().cameraState.center.assertEquals(NULL_ISLAND)
      locationProvider.locationConsumers.forEach { it.onLocationUpdated(TEST_POINT) }
      locationProvider.locationConsumers.forEach { it.onBearingUpdated(testBearing) }
    }
    val latch = CountDownLatch(1)
    latch.await(200, TimeUnit.MILLISECONDS)
    handler.post {
      mapView.getMapboxMap().cameraState.center.assertEquals(TEST_POINT)
    }
  }

  private fun Point.assertEquals(other: Point) {
    assertEquals(this.longitude(), other.longitude(), EPS)
    assertEquals(this.latitude(), other.latitude(), EPS)
    assertEquals(this.altitude(), other.altitude(), EPS)
  }

  private companion object {
    val TEST_POINT = Point.fromLngLat(24.9384, 60.1699)
    val NULL_ISLAND = Point.fromLngLat(0.0, 0.0)
    const val EPS = 0.000001
  }
}