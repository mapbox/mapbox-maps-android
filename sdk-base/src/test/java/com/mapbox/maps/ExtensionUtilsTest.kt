package com.mapbox.maps

import com.mapbox.geojson.Point
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

@OptIn(MapboxExperimental::class)
class ExtensionUtilsTest {

  private val cameraState = CameraState(
    Point.fromLngLat(10.0, 20.0),
    EdgeInsets(1.0, 2.0, 3.0, 4.0),
    5.0,
    6.0,
    7.0,
    8.0,
    CameraPositionPoint(0.5, -0.5),
    CameraViewPointState.valueOf(CameraViewPoint(-0.25, 0.75))
  )

  @Test
  fun `toCameraOptions keeps positionPoint and viewPoint`() {
    val result = cameraState.toCameraOptions()

    assertEquals(cameraState.center, result.center)
    assertEquals(cameraState.verticalFov, result.verticalFov)
    assertEquals(CameraPositionPoint(0.5, -0.5), result.positionPoint)
    assertEquals(CameraViewPoint(-0.25, 0.75), result.viewPoint?.cameraViewPoint)
  }

  @Test
  fun `toCameraOptions with anchor keeps positionPoint and drops center`() {
    val result = cameraState.toCameraOptions(ScreenCoordinate(1.0, 2.0))

    assertNull(result.center)
    assertEquals(ScreenCoordinate(1.0, 2.0), result.anchor)
    assertEquals(CameraPositionPoint(0.5, -0.5), result.positionPoint)
    assertEquals(CameraViewPoint(-0.25, 0.75), result.viewPoint?.cameraViewPoint)
  }
}