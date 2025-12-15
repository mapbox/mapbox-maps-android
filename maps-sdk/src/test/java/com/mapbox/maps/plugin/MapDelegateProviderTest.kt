package com.mapbox.maps.plugin

import com.mapbox.maps.*
import com.mapbox.maps.geofencing.MapGeofencingConsent
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.attribution.attribution
import io.mockk.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
class MapDelegateProviderTest {

  private lateinit var mapView: MapView
  private val mapboxMap = mockk<MapboxMap>()

  @Before
  fun setUp() {
    mapView = mockk(relaxed = true)
    every { mapView.mapboxMap } returns mapboxMap
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logI(any(), any()) } just Runs
  }

  @After
  fun cleanUp() {
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun mapCameraDelegate() {
    val mapDelegateProvider = MapDelegateProviderImpl(mapboxMap, mockk(), mockk(), mockk())
    assertNotNull(mapDelegateProvider.mapCameraManagerDelegate)
  }

  @Test
  fun mapFeatureQueryDelegate() {
    val mapDelegateProvider = MapDelegateProviderImpl(mapboxMap, mockk(), mockk(), mockk())
    assertEquals(mapboxMap, mapDelegateProvider.mapFeatureQueryDelegate)
  }

  @Test
  fun mapListenerDelegate() {
    val mapDelegateProvider = MapDelegateProviderImpl(mapboxMap, mockk(), mockk(), mockk())
    assertEquals(mapboxMap, mapDelegateProvider.mapListenerDelegate)
  }

  @Test
  fun mapTransformDelegate() {
    val mapDelegateProvider = MapDelegateProviderImpl(mapboxMap, mockk(), mockk(), mockk())
    assertEquals(mapboxMap, mapDelegateProvider.mapTransformDelegate)
  }

  @Test
  fun mapPluginProviderDelegate() {
    val expected = mockk<MapController>()
    val mapDelegateProvider = MapDelegateProviderImpl(mapboxMap, expected, mockk(), mockk())
    assertEquals(expected, mapDelegateProvider.mapPluginProviderDelegate)
  }

  @Test
  fun styleExtensionDelegate() {
    val captureCallback = slot<Style.OnStyleLoaded>()
    every { mapboxMap.getStyle(capture(captureCallback)) } answers {
      captureCallback.captured.onStyleLoaded(mockk())
    }
    val mapDelegateProvider = MapDelegateProviderImpl(mapboxMap, mockk(), mockk(), mockk())
    mapDelegateProvider.getStyle {}
    assertNotNull(captureCallback.captured)
  }

  @Test
  fun mapAttributionDelegate() {
    val expectedTelemetry = mockk<MapTelemetry>()
    val expectedMapGeofencingConsent = mockk<MapGeofencingConsent>()
    val mapController = mockk<MapController>()
    every { mapController.attribution } returns mockk()
    val mapDelegateProvider =
      MapDelegateProviderImpl(
        mapboxMap,
        mapController, expectedTelemetry, expectedMapGeofencingConsent
      )

    assertEquals(expectedTelemetry, mapDelegateProvider.mapAttributionDelegate.telemetry())
    assertEquals(expectedMapGeofencingConsent, mapDelegateProvider.mapAttributionDelegate.geofencingConsent())
  }
}