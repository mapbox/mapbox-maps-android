package com.mapbox.maps.plugin

import com.mapbox.common.ShadowLogger
import com.mapbox.maps.MapController
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.loader.MapboxMapStaticInitializer
import io.mockk.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class MapDelegateProviderTest {

  private lateinit var mapView: MapView
  private val mapboxMap = mockk<MapboxMap>()

  @Before
  fun setUp() {
    mockkStatic(MapboxMapStaticInitializer::class)
    every { MapboxMapStaticInitializer.loadMapboxMapNativeLib() } just Runs
    mapView = mockk(relaxed = true)
    every { mapView.getMapboxMap() } returns mapboxMap
  }

  @Test
  fun mapCameraDelegate() {
    val mapDelegateProvider = MapDelegateProviderImpl(mapboxMap, mockk(), mockk())
    assertNotNull(mapDelegateProvider.mapCameraDelegate)
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