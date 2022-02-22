package com.mapbox.maps.plugin.locationcomponent

import android.content.Context
import android.location.Location
import com.mapbox.android.core.location.*
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.common.ShadowLogger
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.PuckBearingSource
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class DefaultLocationProviderTest {
  private val context = mockk<Context>(relaxed = true)
  private val locationEngine = mockk<LocationEngine>(relaxed = true)
  private val locationConsumer1 = mockk<LocationConsumer>(relaxed = true)
  private val locationConsumer2 = mockk<LocationConsumer>(relaxed = true)
  private val locationCompassEngine = mockk<LocationCompassEngine>(relaxed = true)
  private val locationEngineRequestSlot = CapturingSlot<LocationEngineRequest>()
  private val locationEngineCallbackSlot =
    CapturingSlot<LocationEngineCallback<LocationEngineResult>>()

  private lateinit var defaultLocationProvider: DefaultLocationProvider

  @Before
  fun setup() {
    mockkStatic(LocationEngineProvider::class)
    every { LocationEngineProvider.getBestLocationEngine(context.applicationContext) } returns locationEngine
    defaultLocationProvider = DefaultLocationProvider(context, locationCompassEngine)
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
    verify(exactly = 0) {
      locationEngine.getLastLocation(any())
    }
  }

  @Test
  fun testAddLocationConsumerWithoutPermission() {
    mockkStatic(PermissionsManager::class)
    every { PermissionsManager.areLocationPermissionsGranted(any()) } returns false
    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    verify(exactly = 0) {
      locationEngine.requestLocationUpdates(
        any(),
        any(),
        any()
      )
    }
    verify(exactly = 0) {
      locationEngine.getLastLocation(any())
    }
    verify(exactly = 0) {
      locationCompassEngine.addCompassListener(any())
    }
  }

  @Test
  fun testAddLocationConsumerWithPermission() {
    defaultLocationProvider.updatePuckBearingSource(PuckBearingSource.HEADING)
    mockkStatic(PermissionsManager::class)
    every { PermissionsManager.areLocationPermissionsGranted(any()) } returns true
    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
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
    verify(exactly = 1) {
      locationEngine.getLastLocation(any())
    }
    verify(exactly = 1) {
      locationCompassEngine.addCompassListener(any())
    }
  }

  @Test
  fun testAddTwoLocationConsumer() {
    defaultLocationProvider.updatePuckBearingSource(PuckBearingSource.HEADING)
    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    defaultLocationProvider.registerLocationConsumer(locationConsumer2)
    verify(exactly = 1) {
      locationEngine.requestLocationUpdates(
        capture(locationEngineRequestSlot),
        any(),
        any()
      )
    }
    verify(exactly = 2) {
      locationEngine.getLastLocation(any())
    }
    verify(exactly = 1) {
      locationCompassEngine.addCompassListener(any())
    }
  }

  @Test
  fun testUpdatePuckBearingSourceWithConsumer() {
    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    verify(exactly = 0) {
      locationCompassEngine.addCompassListener(any())
    }
    defaultLocationProvider.updatePuckBearingSource(PuckBearingSource.HEADING)
    verify(exactly = 1) {
      locationCompassEngine.addCompassListener(any())
    }
  }

  @Test
  fun testRemoveLocationConsumer() {
    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    defaultLocationProvider.registerLocationConsumer(locationConsumer2)
    defaultLocationProvider.unRegisterLocationConsumer(locationConsumer2)
    verify(exactly = 0) {
      locationEngine.removeLocationUpdates(any() as LocationEngineCallback<LocationEngineResult>)
    }
    verify(exactly = 0) {
      locationCompassEngine.addCompassListener(any())
    }
  }

  @Test
  fun testRemoveAllLocationConsumer() {
    defaultLocationProvider.updatePuckBearingSource(PuckBearingSource.HEADING)
    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    defaultLocationProvider.registerLocationConsumer(locationConsumer2)
    defaultLocationProvider.unRegisterLocationConsumer(locationConsumer2)
    defaultLocationProvider.unRegisterLocationConsumer(locationConsumer1)
    verify(exactly = 1) {
      locationEngine.removeLocationUpdates(any() as LocationEngineCallback<LocationEngineResult>)
    }
    verify(exactly = 1) {
      locationCompassEngine.addCompassListener(any())
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

    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    defaultLocationProvider.registerLocationConsumer(locationConsumer2)
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

  @Test
  fun testLocationUpdateWithCompass() {
    defaultLocationProvider.updatePuckBearingSource(PuckBearingSource.HEADING)
    val locationEngineResult = mockk<LocationEngineResult>(relaxed = true)
    val location = mockk<Location>(relaxed = true)
    every { locationEngineResult.lastLocation } returns location
    every { location.longitude } returns 12.0
    every { location.latitude } returns 34.0
    every { location.bearing } returns 90.0f

    defaultLocationProvider.registerLocationConsumer(locationConsumer1)
    defaultLocationProvider.registerLocationConsumer(locationConsumer2)
    verify(exactly = 1) {
      locationEngine.requestLocationUpdates(
        any(),
        capture(locationEngineCallbackSlot),
        any()
      )
    }
    locationEngineCallbackSlot.captured.onSuccess(locationEngineResult)
    verify { locationConsumer1.onLocationUpdated(Point.fromLngLat(12.0, 34.0)) }
    verify(exactly = 0) { locationConsumer1.onBearingUpdated(90.0) }
    verify { locationConsumer2.onLocationUpdated(Point.fromLngLat(12.0, 34.0)) }
    verify(exactly = 0) { locationConsumer2.onBearingUpdated(90.0) }
    defaultLocationProvider.locationCompassListener.onCompassChanged(90.0f)
    verify { locationConsumer1.onBearingUpdated(90.0) }
    verify { locationConsumer2.onBearingUpdated(90.0) }
  }
}