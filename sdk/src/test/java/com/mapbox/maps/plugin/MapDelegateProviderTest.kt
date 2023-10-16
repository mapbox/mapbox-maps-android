package com.mapbox.maps.plugin

import com.mapbox.maps.*
import io.mockk.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

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
    val mapDelegateProvider = MapDelegateProviderImpl(mapboxMap, mockk(), mockk())
    assertNotNull(mapDelegateProvider.mapCameraManagerDelegate)
  }

  @Test
  fun mapFeatureQueryDelegate() {
    val mapDelegateProvider = MapDelegateProviderImpl(mapboxMap, mockk(), mockk())
    assertEquals(mapboxMap, mapDelegateProvider.mapFeatureQueryDelegate)
  }

  @Test
  fun mapListenerDelegate() {
    val mapDelegateProvider = MapDelegateProviderImpl(mapboxMap, mockk(), mockk())
    assertEquals(mapboxMap, mapDelegateProvider.mapListenerDelegate)
  }

  @Test
  fun mapTransformDelegate() {
    val mapDelegateProvider = MapDelegateProviderImpl(mapboxMap, mockk(), mockk())
    assertEquals(mapboxMap, mapDelegateProvider.mapTransformDelegate)
  }

  @Test
  fun mapPluginProviderDelegate() {
    val expected = mockk<MapController>()
    val mapDelegateProvider = MapDelegateProviderImpl(mapboxMap, expected, mockk())
    assertEquals(expected, mapDelegateProvider.mapPluginProviderDelegate)
  }

  @Test
  fun styleExtensionDelegate() {
    val captureCallback = slot<Style.OnStyleLoaded>()
    every { mapboxMap.getStyle(capture(captureCallback)) } answers {
      captureCallback.captured.onStyleLoaded(mockk())
    }
    val mapDelegateProvider = MapDelegateProviderImpl(mapboxMap, mockk(), mockk())
    mapDelegateProvider.getStyle {}
    assertNotNull(captureCallback.captured)
  }
}