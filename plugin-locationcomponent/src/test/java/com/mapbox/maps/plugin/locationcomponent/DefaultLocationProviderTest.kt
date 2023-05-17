package com.mapbox.maps.plugin.locationcomponent

import android.animation.ValueAnimator
import android.content.Context
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.Value
import com.mapbox.common.location.LiveTrackingClient
import com.mapbox.common.location.LiveTrackingClientAccuracyCategory
import com.mapbox.common.location.LiveTrackingClientObserver
import com.mapbox.common.location.LiveTrackingClientSettings
import com.mapbox.common.location.Location
import com.mapbox.common.location.LocationError
import com.mapbox.common.location.LocationErrorCode.*
import com.mapbox.common.location.LocationService
import com.mapbox.common.location.LocationServiceFactory
import com.mapbox.geojson.Point
import com.mapbox.maps.logE
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.PuckBearing
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

private typealias LocationUpdate = Expected<LocationError, List<Location>>

private typealias LocationServiceUpdate = Expected<LocationError, Location>

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class DefaultLocationProviderTest {
  private val context = mockk<Context>(relaxed = true)
  private val locationService = mockk<LocationService>(relaxed = true)
  private val liveTrackingClient = mockk<LiveTrackingClient>(relaxed = true)
  private val locationConsumer1 = mockk<LocationConsumer>(relaxed = true)
  private val locationConsumer2 = mockk<LocationConsumer>(relaxed = true)
  private val locationCompassEngine = mockk<LocationCompassEngine>(relaxed = true)
  private val locationEngineRequestSlot = CapturingSlot<Value>()

  private lateinit var defaultLocationProvider: DefaultLocationProvider

  private lateinit var dispatcher: TestDispatcher

  @Before
  fun setup() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logW(any(), any()) } just Runs
    every { logE(any(), any()) } just Runs
    every { locationService.getLiveTrackingClient(null, null) } returns ExpectedFactory.createValue(
      liveTrackingClient
    )
    every { context.applicationContext } returns context
    dispatcher = StandardTestDispatcher()
    defaultLocationProvider =
      DefaultLocationProvider(context, locationCompassEngine, locationService, dispatcher)
  }

  @After
  fun cleanUp() {
    unmockkStatic(LocationServiceFactory::class)
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun testAddLocationConsumerWithoutPermission() = runTest(dispatcher) {
    mockkObject(PermissionsManager)
    every { PermissionsManager.areLocationPermissionsGranted(context) } returns false
    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    try {
      var areLocationPermissionsGrantedCalls = 0
      var delayTimeMillis = DefaultLocationProvider.INIT_UPDATE_DELAY
      repeat(10) {
        advanceTimeBy(delayTimeMillis)
        areLocationPermissionsGrantedCalls++
        delayTimeMillis =
          (delayTimeMillis * 2).coerceAtMost(DefaultLocationProvider.MAX_UPDATE_DELAY)
        verify(exactly = areLocationPermissionsGrantedCalls) {
          PermissionsManager.areLocationPermissionsGranted(context)
        }
      }

      verify(exactly = 0) {
        liveTrackingClient.registerObserver(/* observer = */ any())
      }
      verify(exactly = 0) {
        liveTrackingClient.start(capture(locationEngineRequestSlot), any())
      }
      verify(exactly = 0) {
        locationService.lastLocation
      }
      verify(exactly = 0) {
        locationCompassEngine.addCompassListener(any())
      }
    } finally {
      // Always unregister so the shared DefaultLocationProvider.locationUpdatesFlow is freed and
      // test can complete
      defaultLocationProvider.unRegisterLocationConsumer(locationConsumer1)
    }
  }

  @Test
  fun testAddLocationConsumerWithPermission() = runTest(dispatcher) {
    defaultLocationProvider.updatePuckBearing(PuckBearing.HEADING)
    mockkObject(PermissionsManager)
    every { PermissionsManager.areLocationPermissionsGranted(context) } returns true
    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    advanceUntilIdle()
    verify(exactly = 1) {
      liveTrackingClient.registerObserver(any())
    }
    verify(exactly = 1) {
      liveTrackingClient.start(capture(locationEngineRequestSlot), any())
    }
    @Suppress("UNCHECKED_CAST")
    val capturedValueMap = locationEngineRequestSlot.captured.contents as Map<String, Value>
    assertEquals(
      LocationComponentConstants.DEFAULT_INTERVAL_MILLIS,
      capturedValueMap[LiveTrackingClientSettings.INTERVAL]!!.contents
    )
    assertEquals(
      LocationComponentConstants.DEFAULT_FASTEST_INTERVAL_MILLIS,
      capturedValueMap[LiveTrackingClientSettings.MINIMUM_INTERVAL]!!.contents
    )
    assertEquals(
      LiveTrackingClientAccuracyCategory.HIGH,
      capturedValueMap[LiveTrackingClientSettings.ACCURACY_CATEGORY]!!.contents
    )
    verify(exactly = 1) {
      locationService.lastLocation
    }
    verify(exactly = 1) {
      locationCompassEngine.addCompassListener(any())
    }
  }

  @Test
  fun testAddTwoLocationConsumer() = runTest(dispatcher) {
    defaultLocationProvider.updatePuckBearing(PuckBearing.HEADING)
    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    advanceUntilIdle()
    defaultLocationProvider.registerLocationConsumer(locationConsumer2)
    advanceUntilIdle()
    verify(exactly = 1) {
      liveTrackingClient.registerObserver(any())
    }
    verify(exactly = 1) {
      liveTrackingClient.start(capture(locationEngineRequestSlot), any())
    }
    verify(exactly = 1) {
      locationService.lastLocation
    }
    verify(exactly = 1) {
      locationCompassEngine.addCompassListener(any())
    }
  }

  @Test
  fun testUpdatePuckBearingWithConsumer() = runTest(dispatcher) {
    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    advanceUntilIdle()
    verify(exactly = 0) {
      locationCompassEngine.addCompassListener(any())
    }
    defaultLocationProvider.updatePuckBearing(PuckBearing.HEADING)
    advanceUntilIdle()
    verify(exactly = 1) {
      locationCompassEngine.addCompassListener(any())
    }
  }

  @Test
  fun testRemoveOneOfTwoLocationConsumers() = runTest(dispatcher) {
    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    advanceUntilIdle()
    defaultLocationProvider.registerLocationConsumer(locationConsumer2)
    advanceUntilIdle()
    defaultLocationProvider.unRegisterLocationConsumer(locationConsumer2)
    advanceUntilIdle()
    verify(exactly = 0) {
      liveTrackingClient.unregisterObserver(any())
    }
    verify(exactly = 0) {
      liveTrackingClient.stop(any())
    }
    verify(exactly = 0) {
      locationCompassEngine.addCompassListener(any())
    }
  }

  @Test
  fun testRemoveAllLocationConsumer() = runTest(dispatcher) {
    defaultLocationProvider.updatePuckBearing(PuckBearing.HEADING)
    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    advanceUntilIdle()
    defaultLocationProvider.registerLocationConsumer(locationConsumer2)
    advanceUntilIdle()
    defaultLocationProvider.unRegisterLocationConsumer(locationConsumer2)
    advanceUntilIdle()
    defaultLocationProvider.unRegisterLocationConsumer(locationConsumer1)
    advanceUntilIdle()
    verify(exactly = 1) {
      liveTrackingClient.unregisterObserver(any())
    }
    verify(exactly = 1) {
      liveTrackingClient.stop(any())
    }
    verify(exactly = 1) {
      locationCompassEngine.addCompassListener(any())
    }
  }

  @Test
  fun testLocationUpdate() = runTest(dispatcher) {
    val location = mockk<Location>(relaxed = true)
    every { location.longitude } returns 12.0
    every { location.latitude } returns 34.0
    every { location.altitude } returns 10.0
    every { location.bearing } returns 90.0

    val lastLocationResult: LocationServiceUpdate = ExpectedFactory.createValue(location)
    every { locationService.lastLocation } returns lastLocationResult

    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    defaultLocationProvider.registerLocationConsumer(locationConsumer2)
    advanceUntilIdle()
    verify(exactly = 1) { liveTrackingClient.registerObserver(any()) }
    verify(exactly = 1) { locationService.lastLocation }
    verify { locationConsumer1.onLocationUpdated(Point.fromLngLat(12.0, 34.0, 10.0)) }
    verify { locationConsumer1.onBearingUpdated(90.0) }
    verify { locationConsumer2.onLocationUpdated(Point.fromLngLat(12.0, 34.0, 10.0)) }
    verify { locationConsumer2.onBearingUpdated(90.0) }
  }

  @Test
  fun `second listener gets only most recent location`() = runTest(dispatcher) {
    val location = mockk<Location>(relaxed = true).apply {
      every { longitude } returns 12.0
      every { latitude } returns 34.0
      every { altitude } returns 10.0
      every { bearing } returns 90.0
    }

    val lastLocationResult: LocationServiceUpdate =
      ExpectedFactory.createValue(location)
    every { locationService.lastLocation } returns lastLocationResult

    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    advanceUntilIdle()

    val liveTrackingClientObserverSlot = CapturingSlot<LiveTrackingClientObserver>()
    verify(exactly = 1) { liveTrackingClient.registerObserver(capture(liveTrackingClientObserverSlot)) }
    verify(exactly = 1) { locationService.lastLocation }

    val locationAsPoint =
      Point.fromLngLat(location.longitude, location.latitude, location.altitude!!)
    verify { locationConsumer1.onLocationUpdated(locationAsPoint) }
    verify { locationConsumer1.onBearingUpdated(90.0) }

    val location2 = mockk<Location>().apply {
      every { longitude } returns 11.0
      every { latitude } returns 22.0
      every { altitude } returns 33.0
      every { bearing } returns 44.0
      every { horizontalAccuracy } returns 3.0
    }
    val locationUpdate: LocationUpdate = ExpectedFactory.createValue(listOf(location2))
    // Use the captured live tracking client observer to send a second location
    liveTrackingClientObserverSlot.captured.onLocationUpdateReceived(locationUpdate)
    advanceUntilIdle()
    val location2AsPoint =
      Point.fromLngLat(location2.longitude, location2.latitude, location2.altitude!!)
    // Verify that the second location is received by the first consumer
    verify(exactly = 1) { locationConsumer1.onLocationUpdated(location2AsPoint) }
    verify(exactly = 1) { locationConsumer1.onBearingUpdated(44.0) }

    // Finally, register the second consumer
    defaultLocationProvider.registerLocationConsumer(locationConsumer2)
    advanceUntilIdle()
    // Verify that the first location is not received at all by the second consumer
    verify(exactly = 0) { locationConsumer2.onLocationUpdated(locationAsPoint) }
    verify(exactly = 0) { locationConsumer2.onBearingUpdated(90.0) }
    // Verify that the second location is received by the second consumer
    verify(exactly = 1) { locationConsumer2.onLocationUpdated(location2AsPoint) }
    verify(exactly = 1) { locationConsumer2.onBearingUpdated(44.0) }
  }

  @Test
  fun testLocationUpdateWithCompass() = runTest(dispatcher) {
    defaultLocationProvider.updatePuckBearing(PuckBearing.HEADING)
    val location = mockk<Location>(relaxed = true)
    every { location.longitude } returns 12.0
    every { location.latitude } returns 34.0
    every { location.altitude } returns 10.0
    every { location.bearing } returns 89.0
    every { locationService.lastLocation } returns ExpectedFactory.createValue(location)

    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    advanceUntilIdle()
    verify(exactly = 1) { liveTrackingClient.registerObserver(any()) }

    val compassListenerSlot = CapturingSlot<LocationCompassEngine.CompassListener>()
    verify(exactly = 1) { locationCompassEngine.addCompassListener(capture(compassListenerSlot)) }
    verify(exactly = 1) { locationService.lastLocation }

    verify {
      locationConsumer1.onLocationUpdated(Point.fromLngLat(12.0, 34.0, 10.0))
    }
    // We shouldn't have received any bearing update since we're using HEADING puck bearing source
    verify(exactly = 0) { locationConsumer1.onBearingUpdated(any()) }

    // Using the captured compass listener, check that we emit a bearing update
    compassListenerSlot.captured.onCompassChanged(91.0f)
    advanceUntilIdle()
    verify(exactly = 1) { locationConsumer1.onBearingUpdated(91.0) }
  }

  @Test
  fun `live tracking client not available`() = runTest(dispatcher) {
    val locationService = mockk<LocationService>(relaxed = true)
    every {
      locationService.getLiveTrackingClient(
        any(),
        any()
      )
    } returns ExpectedFactory.createError(
      LocationError(NOT_AVAILABLE, "Not available")
    )
    var receivedError: LocationError? = null
    val defaultLocationProvider =
      DefaultLocationProvider(context, locationCompassEngine, locationService, dispatcher)
    defaultLocationProvider.registerLocationConsumer(object : EmptyLocationConsumer() {
      override fun onError(error: LocationError) {
        receivedError = error
      }
    })
    advanceUntilIdle()
    assertNotNull(receivedError)
    assertEquals(
      LocationError(
        NOT_AVAILABLE,
        DefaultLocationProvider.LIVE_TRACKING_CLIENT_NOT_AVAILABLE
      ),
        receivedError
    )
  }

  @Test
  fun `live tracking client error`() = runTest(dispatcher) {
    val location = mockk<Location>(relaxed = true)
    every { location.longitude } returns 12.0
    every { location.latitude } returns 34.0
    every { location.altitude } returns 10.0
    every { location.bearing } returns 89.0
    every { locationService.lastLocation } returns ExpectedFactory.createValue(location)

    var receivedPoint: Point? = null
    var receivedError: LocationError? = null
    defaultLocationProvider.registerLocationConsumer(object : EmptyLocationConsumer() {
      override fun onLocationUpdated(vararg location: Point, options: (ValueAnimator.() -> Unit)?) {
        receivedPoint = location.last()
      }

      override fun onError(error: LocationError) {
        receivedError = error
      }
    })
    advanceUntilIdle()

    val clientObserverSlot = CapturingSlot<LiveTrackingClientObserver>()
    verify(exactly = 1) { liveTrackingClient.registerObserver(capture(clientObserverSlot)) }

    // Make sure we got a valid location
    with(receivedPoint!!) {
      assertNotNull(this)
      assertEquals(12.0, longitude(), 0.1)
      assertEquals(34.0, latitude(), 0.1)
      assertEquals(10.0, altitude(), 0.1)
    }

    // No error was emitted yet
    assertNull(receivedError)

    // Send an error through live tracking client observer
    val locationError = LocationError(FAILED_TO_DETECT_LOCATION, "Unable to provide location")
    clientObserverSlot.captured.onLocationUpdateReceived(ExpectedFactory.createError(locationError))
    advanceUntilIdle()
    // Make sure we got the error
    assertEquals(locationError, receivedError)
  }
}

private open class EmptyLocationConsumer : LocationConsumer {
  override fun onLocationUpdated(vararg location: Point, options: (ValueAnimator.() -> Unit)?) {
  }

  override fun onBearingUpdated(vararg bearing: Double, options: (ValueAnimator.() -> Unit)?) {
  }

  override fun onPuckLocationAnimatorDefaultOptionsUpdated(options: ValueAnimator.() -> Unit) {
  }

  override fun onPuckBearingAnimatorDefaultOptionsUpdated(options: ValueAnimator.() -> Unit) {
  }

  override fun onError(error: LocationError) {
  }

  override fun onHorizontalAccuracyRadiusUpdated(
    vararg radius: Double,
    options: (ValueAnimator.() -> Unit)?
  ) {
  }

  override fun onPuckAccuracyRadiusAnimatorDefaultOptionsUpdated(options: ValueAnimator.() -> Unit) {
  }
}