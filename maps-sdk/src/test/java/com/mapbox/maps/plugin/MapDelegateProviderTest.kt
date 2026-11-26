package com.mapbox.maps.plugin

import com.mapbox.common.Cancelable
import com.mapbox.maps.*
import com.mapbox.maps.geofencing.MapGeofencingConsent
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.attribution.attribution
import io.mockk.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
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
  private val indoor = mockk<IndoorManager>()

  @Before
  fun setUp() {
    mapView = mockk(relaxed = true)
    every { mapView.mapboxMap } returns mapboxMap
    every { mapboxMap.indoor } returns indoor
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
    val style = mockk<Style>()
    every { mapboxMap.getStyle(capture(captureCallback)) } answers {
      captureCallback.captured.onStyleLoaded(style)
    }
    val mapDelegateProvider = MapDelegateProviderImpl(mapboxMap, mockk(), mockk(), mockk())
    var received: MapboxStyleManager? = null
    mapDelegateProvider.getStyle { received = it }
    assertEquals(style, received)
  }

  @Test
  fun whenStyleReadyReturnsMapboxMapCancelable() {
    val styleRequest = mockk<Cancelable>(relaxed = true)
    every { mapboxMap.whenStyleReady(any()) } returns styleRequest
    val mapDelegateProvider = MapDelegateProviderImpl(mapboxMap, mockk(), mockk(), mockk())
    val callback = mockk<(MapboxStyleManager) -> Unit>(relaxed = true)

    mapDelegateProvider.whenStyleReady(callback).cancel()

    verify(exactly = 1) { styleRequest.cancel() }
    verify(exactly = 0) { callback.invoke(any()) }
  }

  @Test
  fun whenStyleReadyWithSameCallbackPassesEqualListeners() {
    val listeners = mutableListOf<Style.OnStyleLoaded>()
    every { mapboxMap.whenStyleReady(capture(listeners)) } returns mockk()
    val mapDelegateProvider = MapDelegateProviderImpl(mapboxMap, mockk(), mockk(), mockk())
    val callback: (MapboxStyleManager) -> Unit = {}
    val otherCallback: (MapboxStyleManager) -> Unit = {}

    mapDelegateProvider.whenStyleReady(callback)
    mapDelegateProvider.whenStyleReady(callback)
    mapDelegateProvider.whenStyleReady(otherCallback)

    // StyleObserver deduplicates pending requests by listener equals and hashCode
    assertEquals(listeners[0], listeners[1])
    assertEquals(listeners[0].hashCode(), listeners[1].hashCode())
    assertFalse(listeners[0] == listeners[2])
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