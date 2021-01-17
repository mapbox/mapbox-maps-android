package com.mapbox.maps.plugin

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxMap
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MapCameraDelegateImplTest {

  private val mapboxMap = mockk<MapboxMap>(relaxUnitFun = true)
  private val cameraOptions = mockk<CameraOptions>(relaxed = true)

  @Before
  fun setUp() {
    every { mapboxMap.getCameraOptions(any()) } returns cameraOptions
  }

  @Test
  fun bearing() {
    val mapCameraDelegate = MapCameraDelegateImpl(mapboxMap)
    mapCameraDelegate.getBearing()
    verify { cameraOptions.bearing }
  }

  @Test
  fun bearingSet() {
    val mapCameraDelegate = MapCameraDelegateImpl(mapboxMap)
    mapCameraDelegate.setBearing(10.0)
    verify { mapboxMap.jumpTo(any()) }
  }

  @Test
  fun zoom() {
    val mapCameraDelegate = MapCameraDelegateImpl(mapboxMap)
    mapCameraDelegate.getZoom()
    verify { cameraOptions.zoom }
  }

  @Test
  fun anchor() {
    val mapCameraDelegate = MapCameraDelegateImpl(mapboxMap)
    mapCameraDelegate.getAnchor()
    verify { cameraOptions.anchor }
  }

  @Test
  fun latitude() {
    val mapCameraDelegate = MapCameraDelegateImpl(mapboxMap)
    mapCameraDelegate.getLat()
    verify { cameraOptions.center?.latitude() }
  }

  @Test
  fun longitude() {
    val mapCameraDelegate = MapCameraDelegateImpl(mapboxMap)
    mapCameraDelegate.getLon()
    verify { cameraOptions.center?.longitude() }
  }

  @Test
  fun padding() {
    val mapCameraDelegate = MapCameraDelegateImpl(mapboxMap)
    mapCameraDelegate.getPadding()
    verify { cameraOptions.padding }
  }

  @Test
  fun pitch() {
    val mapCameraDelegate = MapCameraDelegateImpl(mapboxMap)
    mapCameraDelegate.getPitch()
    verify { cameraOptions.pitch }
  }

  @Test
  fun cameraOptions() {
    val mapCameraDelegate = MapCameraDelegateImpl(mapboxMap)
    mapCameraDelegate.getCameraOptions()
    verify { mapboxMap.getCameraOptions(any()) }
  }
}