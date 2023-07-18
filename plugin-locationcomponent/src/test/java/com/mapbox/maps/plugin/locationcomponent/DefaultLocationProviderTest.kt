package com.mapbox.maps.plugin.locationcomponent

import android.animation.ValueAnimator
import android.content.Context
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.common.Cancelable
import com.mapbox.common.location.AccuracyLevel
import com.mapbox.common.location.DeviceLocationProvider
import com.mapbox.common.location.GetLocationCallback
import com.mapbox.common.location.Location
import com.mapbox.common.location.LocationError
import com.mapbox.common.location.LocationErrorCode.*
import com.mapbox.common.location.LocationObserver
import com.mapbox.common.location.LocationProviderRequest
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class DefaultLocationProviderTest {
  private val context = mockk<Context>(relaxed = true)
  private val locationService = mockk<LocationService>(relaxed = true)
  private val deviceLocationProvider = mockk<DeviceLocationProvider>(relaxed = true)
  private val locationConsumer1 = mockk<LocationConsumer>(relaxed = true)
  private val locationConsumer2 = mockk<LocationConsumer>(relaxed = true)
  private val locationCompassEngine = mockk<LocationCompassEngine>(relaxed = true)
  private val locationEngineRequestSlot = CapturingSlot<LocationProviderRequest>()

  private val location = mockk<Location>(relaxed = true).apply {
    every { longitude } returns 12.0
    every { latitude } returns 34.0
    every { altitude } returns 10.0
    every { bearing } returns 90.0
  }

  private lateinit var defaultLocationProvider: DefaultLocationProvider

  private lateinit var dispatcher: TestDispatcher

  @Before
  fun setup() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logW(any(), any()) } just Runs
    every { logE(any(), any()) } just Runs
    every { locationService.getDeviceLocationProvider(capture(locationEngineRequestSlot)) } returns ExpectedFactory.createValue(
      deviceLocationProvider
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
        deviceLocationProvider.addLocationObserver(any())
      }
      verify(exactly = 0) {
        deviceLocationProvider.getLastLocation(any())
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
      deviceLocationProvider.addLocationObserver(any())
    }
    val capturedValueMap = locationEngineRequestSlot.captured
    assertEquals(
      LocationComponentConstants.DEFAULT_INTERVAL_MILLIS,
      capturedValueMap.interval!!.interval
    )
    assertEquals(
      LocationComponentConstants.DEFAULT_FASTEST_INTERVAL_MILLIS,
      capturedValueMap.interval!!.minimumInterval
    )
    assertEquals(
      AccuracyLevel.HIGH,
      capturedValueMap.accuracy
    )
    verify(exactly = 1) {
      deviceLocationProvider.getLastLocation(any())
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
      deviceLocationProvider.addLocationObserver(any())
    }
    verify(exactly = 1) {
      deviceLocationProvider.getLastLocation(any())
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
      deviceLocationProvider.removeLocationObserver(any())
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
      deviceLocationProvider.removeLocationObserver(any())
    }
    verify(exactly = 1) {
      locationCompassEngine.addCompassListener(any())
    }
  }

  @Test
  fun testLocationUpdate() = runTest(dispatcher) {
    every { deviceLocationProvider.getLastLocation(any()) } returns Cancelable { }

    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    defaultLocationProvider.registerLocationConsumer(locationConsumer2)
    // Advance to let the consumers register
    advanceUntilIdle()
    verify(exactly = 1) { deviceLocationProvider.addLocationObserver(any()) }

    val getLocationCallbackSlot = CapturingSlot<GetLocationCallback>()
    verify(exactly = 1) { deviceLocationProvider.getLastLocation(capture(getLocationCallbackSlot)) }
    getLocationCallbackSlot.captured.run(location)
    // Advance to let the last location to be send through the Flow
    advanceUntilIdle()

    verify { locationConsumer1.onLocationUpdated(Point.fromLngLat(12.0, 34.0, 10.0)) }
    verify { locationConsumer1.onBearingUpdated(90.0) }
    verify { locationConsumer2.onLocationUpdated(Point.fromLngLat(12.0, 34.0, 10.0)) }
    verify { locationConsumer2.onBearingUpdated(90.0) }
  }

  @Test
  fun `last location is discarded if fresher is received`() = runTest(dispatcher) {
    var lastLocationCancelled = false
    every { deviceLocationProvider.getLastLocation(any()) } returns Cancelable {
      lastLocationCancelled = true
    }

    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    // Let the first consumer register
    advanceUntilIdle()

    val liveTrackingClientObserverSlot = CapturingSlot<LocationObserver>()
    verify(exactly = 1) { deviceLocationProvider.addLocationObserver(capture(liveTrackingClientObserverSlot)) }
    advanceUntilIdle()

    verify(exactly = 1) { deviceLocationProvider.getLastLocation(any()) }
    // Advance to let the last location to be send through the Flow
    advanceUntilIdle()

    val lastLocationAsPoint =
      Point.fromLngLat(location.longitude, location.latitude, location.altitude!!)
    verify(exactly = 0) { locationConsumer1.onLocationUpdated(lastLocationAsPoint) }
    verify(exactly = 0) { locationConsumer1.onBearingUpdated(90.0) }

    val location2 = mockk<Location>().apply {
      every { longitude } returns 11.0
      every { latitude } returns 22.0
      every { altitude } returns 33.0
      every { bearing } returns 44.0
      every { horizontalAccuracy } returns 3.0
    }
    // Use the captured live tracking client observer to send a second location
    liveTrackingClientObserverSlot.captured.onLocationUpdateReceived(listOf(location2))
    advanceUntilIdle()

    assertTrue(lastLocationCancelled)
    val location2AsPoint =
      Point.fromLngLat(location2.longitude, location2.latitude, location2.altitude!!)
    // Verify that the second location is received by the first consumer
    verify(exactly = 1) { locationConsumer1.onLocationUpdated(location2AsPoint) }
    verify(exactly = 1) { locationConsumer1.onBearingUpdated(44.0) }
  }

  @Test
  fun `second listener gets only most recent location`() = runTest(dispatcher) {
    every { deviceLocationProvider.getLastLocation(any()) } returns Cancelable { }

    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    // Let the first consumer register
    advanceUntilIdle()

    val liveTrackingClientObserverSlot = CapturingSlot<LocationObserver>()
    verify(exactly = 1) { deviceLocationProvider.addLocationObserver(capture(liveTrackingClientObserverSlot)) }

    val getLocationCallbackSlot = CapturingSlot<GetLocationCallback>()
    verify(exactly = 1) { deviceLocationProvider.getLastLocation(capture(getLocationCallbackSlot)) }
    getLocationCallbackSlot.captured.run(location)
    // Advance to let the last location to be send through the Flow
    advanceUntilIdle()

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
    // Use the captured live tracking client observer to send a second location
    liveTrackingClientObserverSlot.captured.onLocationUpdateReceived(listOf(location2))
    advanceUntilIdle()
    val location2AsPoint =
      Point.fromLngLat(location2.longitude, location2.latitude, location2.altitude!!)
    // Verify that the second location is received by the first consumer
    verify(exactly = 1) { locationConsumer1.onLocationUpdated(location2AsPoint) }
    verify(exactly = 1) { locationConsumer1.onBearingUpdated(44.0) }

    // Finally, register the second consumer
    defaultLocationProvider.registerLocationConsumer(locationConsumer2)
    advanceUntilIdle()
    // Verify that getting the last location has not been called a second time
    verify(exactly = 1) { deviceLocationProvider.getLastLocation(any()) }
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
    every { deviceLocationProvider.getLastLocation(any()) } returns Cancelable { }

    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    advanceUntilIdle()
    verify(exactly = 1) { deviceLocationProvider.addLocationObserver(any()) }

    val compassListenerSlot = CapturingSlot<LocationCompassEngine.CompassListener>()
    verify(exactly = 1) { locationCompassEngine.addCompassListener(capture(compassListenerSlot)) }

    val getLocationCallbackSlot = CapturingSlot<GetLocationCallback>()
    verify(exactly = 1) { deviceLocationProvider.getLastLocation(capture(getLocationCallbackSlot)) }
    getLocationCallbackSlot.captured.run(location)
    // Advance to let the last location to be send through the Flow
    advanceUntilIdle()

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
      locationService.getDeviceLocationProvider(any<LocationProviderRequest>())
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