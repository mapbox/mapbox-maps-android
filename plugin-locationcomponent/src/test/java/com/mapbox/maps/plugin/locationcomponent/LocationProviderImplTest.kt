package com.mapbox.maps.plugin.locationcomponent

import android.content.Context
import android.location.Location
import com.mapbox.android.core.location.*
import com.mapbox.geojson.Point
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocationProviderImplTest {
  private val context = mockk<Context>(relaxed = true)
  private val locationEngine = mockk<LocationEngine>(relaxed = true)
  private val locationConsumer1 = mockk<LocationConsumer>(relaxed = true)
  private val locationConsumer2 = mockk<LocationConsumer>(relaxed = true)

  private val locationEngineRequestSlot = CapturingSlot<LocationEngineRequest>()
  private val locationEngineCallbackSlot =
    CapturingSlot<LocationEngineCallback<LocationEngineResult>>()

  private lateinit var locationProviderImpl: LocationProviderImpl

  @Before
  fun setup() {
    mockkStatic(LocationEngineProvider::class)
    every { LocationEngineProvider.getBestLocationEngine(context) } returns locationEngine
    locationProviderImpl = LocationProviderImpl(context)
  }

  @Test
  fun testEmptyConsumer() {
    verify(exactly = 0) {
      locationEngine.requestLocationUpdates(
        capture(locationEngineRequestSlot),
        any(),
        any()
      )
    }
  }

  @Test
  fun testAddLocationConsumer() {
    locationProviderImpl.registerLocationConsumer(locationConsumer1)
    verify {
      locationEngine.requestLocationUpdates(
        capture(locationEngineRequestSlot),
        any(),
        any()
      )
    }
    assertEquals(
      LocationComponentConstants.DEFAULT_INTERVAL_MILLIS,
      locationEngineRequestSlot.captured.interval
    )
    assertEquals(
      LocationComponentConstants.DEFAULT_FASTEST_INTERVAL_MILLIS,
      locationEngineRequestSlot.captured.fastestInterval
    )
    assertEquals(
      LocationEngineRequest.PRIORITY_HIGH_ACCURACY,
      locationEngineRequestSlot.captured.priority
    )
  }

  @Test
  fun testAddTwoLocationConsumer() {
    locationProviderImpl.registerLocationConsumer(locationConsumer1)
    locationProviderImpl.registerLocationConsumer(locationConsumer2)
    verify(exactly = 1) {
      locationEngine.requestLocationUpdates(
        capture(locationEngineRequestSlot),
        any(),
        any()
      )
    }
  }

  @Test
  fun testRemoveLocationConsumer() {
    locationProviderImpl.registerLocationConsumer(locationConsumer1)
    locationProviderImpl.registerLocationConsumer(locationConsumer2)
    locationProviderImpl.unRegisterLocationConsumer(locationConsumer2)
    verify(exactly = 0) {
      locationEngine.removeLocationUpdates(any() as LocationEngineCallback<LocationEngineResult>)
    }
  }

  @Test
  fun testRemoveAllLocationConsumer() {
    locationProviderImpl.registerLocationConsumer(locationConsumer1)
    locationProviderImpl.registerLocationConsumer(locationConsumer2)
    locationProviderImpl.unRegisterLocationConsumer(locationConsumer2)
    locationProviderImpl.unRegisterLocationConsumer(locationConsumer1)
    verify(exactly = 1) {
      locationEngine.removeLocationUpdates(any() as LocationEngineCallback<LocationEngineResult>)
    }
  }

  @Test
  fun testLocationUpdate() {
    val locationEngineResult = mockk<LocationEngineResult>(relaxed = true)
    val location = mockk<Location>(relaxed = true)
    every { locationEngineResult.lastLocation } returns location
    every { location.longitude } returns 12.0
    every { location.latitude } returns 34.0
    every { location.bearing } returns 90.0f

    locationProviderImpl.registerLocationConsumer(locationConsumer1)
    locationProviderImpl.registerLocationConsumer(locationConsumer2)
    verify(exactly = 1) {
      locationEngine.requestLocationUpdates(
        any(),
        capture(locationEngineCallbackSlot),
        any()
      )
    }
    locationEngineCallbackSlot.captured.onSuccess(locationEngineResult)
    verify { locationConsumer1.onLocationUpdated(Point.fromLngLat(12.0, 34.0)) }
    verify { locationConsumer1.onBearingUpdated(90.0) }
    verify { locationConsumer2.onLocationUpdated(Point.fromLngLat(12.0, 34.0)) }
    verify { locationConsumer2.onBearingUpdated(90.0) }
  }
}