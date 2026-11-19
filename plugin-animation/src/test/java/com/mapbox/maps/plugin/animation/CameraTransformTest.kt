package com.mapbox.maps.plugin.animation

import com.mapbox.maps.CameraPositionPoint
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.Size
import org.junit.Assert.assertEquals
import org.junit.Test

class CameraTransformTest {

  private val mapSize = Size(200f, 400f)

  @Test
  fun followingViewPointWithoutPaddingIsPositionPoint() {
    val result = CameraTransform.getFollowingViewPoint(
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      CameraPositionPoint(0.5, -0.25),
      mapSize
    )

    assertEquals(0.5, result.x, EPS)
    assertEquals(-0.25, result.y, EPS)
  }

  @Test
  fun followingViewPointAccountsForPadding() {
    val result = CameraTransform.getFollowingViewPoint(
      EdgeInsets(100.0, 0.0, 0.0, 0.0),
      CameraPositionPoint(0.0, 0.0),
      mapSize
    )

    assertEquals(0.0, result.x, EPS)
    assertEquals(0.25, result.y, EPS)
  }

  @Test
  fun followingViewPointIsClampedToViewport() {
    val result = CameraTransform.getFollowingViewPoint(
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      CameraPositionPoint(-1.5, 1.5),
      mapSize
    )

    assertEquals(-1.0, result.x, EPS)
    assertEquals(1.0, result.y, EPS)
  }

  @Test
  fun followingViewPointIsClampedForPaddingLargerThanMap() {
    val result = CameraTransform.getFollowingViewPoint(
      EdgeInsets(0.0, 500.0, 0.0, 0.0),
      CameraPositionPoint(0.0, 0.0),
      mapSize
    )

    assertEquals(1.0, result.x, EPS)
    assertEquals(0.0, result.y, EPS)
  }

  @Test
  fun followingViewPointOfEmptyMapIsCenter() {
    val result = CameraTransform.getFollowingViewPoint(
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      CameraPositionPoint(0.5, 0.5),
      Size(0f, 0f)
    )

    assertEquals(0.0, result.x, EPS)
    assertEquals(0.0, result.y, EPS)
  }

  private companion object {
    const val EPS = 1e-9
  }
}