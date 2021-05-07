package com.mapbox.maps.dsl

import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import org.junit.Assert
import org.junit.Test

class CameraOptionsKtxTest {

  @Test
  fun `cameraOptions with mutation`() {
    val expected = CameraState(
      Point.fromLngLat(10.0, 20.0),
      EdgeInsets(1.0, 2.0, 3.0, 4.0),
      5.0,
      6.0,
      7.0,
    )

    val result = cameraOptions {
      center(expected.center)
      padding(expected.padding)
      zoom(expected.zoom)
      bearing(expected.bearing)
      pitch(expected.pitch)
    }

    Assert.assertEquals(expected.center, result.center)
    Assert.assertEquals(expected.padding, result.padding)
    Assert.assertEquals(expected.zoom, result.zoom)
    Assert.assertEquals(expected.bearing, result.bearing)
    Assert.assertEquals(expected.pitch, result.pitch)
  }

  @Test
  fun `cameraOptions without mutation is equal to the base cameraState`() {
    val expected = CameraState(
      Point.fromLngLat(10.0, 20.0),
      EdgeInsets(1.0, 2.0, 3.0, 4.0),
      5.0,
      6.0,
      7.0,
    )

    val result = cameraOptions(expected) {
      // no impl
    }

    Assert.assertEquals(expected.center, result.center)
    Assert.assertEquals(expected.padding, result.padding)
    Assert.assertEquals(expected.zoom, result.zoom)
    Assert.assertEquals(expected.bearing, result.bearing)
    Assert.assertEquals(expected.pitch, result.pitch)
  }

  @Test
  fun `cameraOptions with mutation on a base cameraState`() {
    val base = CameraState(
      Point.fromLngLat(10.0, 20.0),
      EdgeInsets(1.0, 2.0, 3.0, 4.0),
      5.0,
      6.0,
      7.0
    )
    val expected = CameraOptions.Builder()
      .center(Point.fromLngLat(20.0, 30.0))
      .padding(EdgeInsets(8.0, 9.0, 10.0, 11.0))
      .zoom(12.0)
      .bearing(13.0)
      .pitch(14.0)
      .build()

    val result = cameraOptions(base) {
      center(expected.center)
      padding(expected.padding)
      zoom(expected.zoom)
      bearing(expected.bearing)
      pitch(expected.pitch)
    }

    Assert.assertEquals(expected, result)
  }
}