package com.mapbox.maps.util

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraPositionPoint
import com.mapbox.maps.MapboxExperimental
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(MapboxExperimental::class)
class CameraOptionsUtilsTest {

  @Test
  fun `CameraOptions without fields is empty`() {
    assertTrue(CameraOptions.Builder().build().isEmpty)
  }

  @Test
  fun `CameraOptions with positionPoint is not empty`() {
    assertFalse(CameraOptions.Builder().positionPoint(CameraPositionPoint(0.0, 0.0)).build().isEmpty)
  }

  @Test
  fun `CameraOptions with verticalFov is not empty`() {
    assertFalse(CameraOptions.Builder().verticalFov(30.0).build().isEmpty)
  }
}