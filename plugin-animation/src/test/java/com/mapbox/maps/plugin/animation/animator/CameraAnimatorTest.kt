package com.mapbox.maps.plugin.animation.animator

import androidx.core.view.animation.PathInterpolatorCompat
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CameraAnimatorTest {

  private val linearInterpolator = PathInterpolatorCompat.create(0f, 0f, 1f, 1f)

  @Test
  fun `CameraCenterAnimator getAnimatedValueAt with startValue interpolates correctly`() {
    val options = CameraAnimatorOptions.Builder<Point>(
      targets = arrayOf(Point.fromLngLat(10.0, 10.0)),
    )
      .startValue(Point.fromLngLat(0.0, 0.0))
      .build()
    val animator = CameraCenterAnimator(options = options, useShortestPath = true)
    animator.interpolator = linearInterpolator

    val result = animator.getAnimatedValueAt(0.5f)

    assertEquals(Point.fromLngLat(5.0, 5.0), result)
  }

  @Test
  fun `CameraZoomAnimator getAnimatedValueAt with startValue interpolates correctly`() {
    val options = CameraAnimatorOptions.Builder<Double>(
      targets = arrayOf(10.0),
    )
      .startValue(1.0)
      .build()
    val animator = CameraZoomAnimator(options)
    animator.interpolator = linearInterpolator

    val result = animator.getAnimatedValueAt(0.25f)

    assertEquals(3.25, result, 0.001)
  }

  @Test
  fun `CameraBearingAnimator getAnimatedValueAt with startValue interpolates correctly`() {
    val options = CameraAnimatorOptions.Builder<Double>(
      targets = arrayOf(90.0),
    )
      .startValue(0.0)
      .build()
    val animator = CameraBearingAnimator(options = options, useShortestPath = true)
    animator.interpolator = linearInterpolator

    val result = animator.getAnimatedValueAt(0.75f)

    assertEquals(67.5, result, 0.001)
  }

  @Test
  fun `CameraPitchAnimator getAnimatedValueAt with startValue interpolates correctly`() {
    val options = CameraAnimatorOptions.Builder<Double>(
      targets = arrayOf(60.0),
    )
      .startValue(0.0)
      .build()
    val animator = CameraPitchAnimator(options)
    animator.interpolator = linearInterpolator

    val result = animator.getAnimatedValueAt(1.0f)

    assertEquals(60.0, result, 0.001)
  }

  @Test
  fun `CameraAnchorAnimator getAnimatedValueAt with startValue interpolates correctly`() {
    val options = CameraAnimatorOptions.Builder<ScreenCoordinate>(
      targets = arrayOf(ScreenCoordinate(100.0, 100.0)),
    )
      .startValue(ScreenCoordinate(0.0, 0.0))
      .build()
    val animator = CameraAnchorAnimator(options)
    animator.interpolator = linearInterpolator

    val result = animator.getAnimatedValueAt(0.3f)

    assertEquals(30.0, result.x, 0.0001)
    assertEquals(30.0, result.y, 0.0001)
  }

  @Test
  fun `CameraPaddingAnimator getAnimatedValueAt with startValue interpolates correctly`() {
    val options = CameraAnimatorOptions.Builder<EdgeInsets>(
      targets = arrayOf(EdgeInsets(10.0, 20.0, 30.0, 40.0)),
    )
      .startValue(EdgeInsets(0.0, 0.0, 0.0, 0.0))
      .build()
    val animator = CameraPaddingAnimator(options)
    animator.interpolator = linearInterpolator

    val result = animator.getAnimatedValueAt(0.5f)

    assertEquals(EdgeInsets(5.0, 10.0, 15.0, 20.0), result)
  }

  @Test
  fun `CameraCenterAnimator getAnimatedValueAt with startCameraState uses camera state value`() {
    val options = CameraAnimatorOptions.Builder<Point>(
      targets = arrayOf(Point.fromLngLat(10.0, 10.0)),
    )
      .build()
    val animator = CameraCenterAnimator(options = options, useShortestPath = true)
    animator.interpolator = linearInterpolator

    val cameraState = mockk<CameraState> {
      every { center } returns Point.fromLngLat(2.0, 2.0)
    }

    val result = animator.getAnimatedValueAt(0.5f, cameraState)

    assertEquals(Point.fromLngLat(6.0, 6.0), result)
  }

  @Test
  fun `CameraZoomAnimator getAnimatedValueAt with startCameraState uses camera state value`() {
    val options = CameraAnimatorOptions.Builder<Double>(
      targets = arrayOf(15.0),
    )
      .build()
    val animator = CameraZoomAnimator(options)
    animator.interpolator = linearInterpolator

    val cameraState = mockk<CameraState> {
      every { zoom } returns 5.0
    }

    val result = animator.getAnimatedValueAt(0.25f, cameraState)

    assertEquals(7.5, result, 0.001)
  }

  @Test
  fun `CameraBearingAnimator getAnimatedValueAt with startCameraState uses camera state value`() {
    val options = CameraAnimatorOptions.Builder<Double>(
      targets = arrayOf(180.0),
    )
      .build()
    val animator = CameraBearingAnimator(options = options, useShortestPath = true)
    animator.interpolator = linearInterpolator

    val cameraState = mockk<CameraState> {
      every { bearing } returns 0.0
    }

    val result = animator.getAnimatedValueAt(0.75f, cameraState)

    assertEquals(135.0, result, 0.001)
  }

  @Test
  fun `CameraPitchAnimator getAnimatedValueAt with startCameraState uses camera state value`() {
    val options = CameraAnimatorOptions.Builder<Double>(
      targets = arrayOf(45.0),
    )
      .build()
    val animator = CameraPitchAnimator(options)
    animator.interpolator = linearInterpolator

    val cameraState = mockk<CameraState> {
      every { pitch } returns 15.0
    }

    val result = animator.getAnimatedValueAt(0.5f, cameraState)

    assertEquals(30.0, result, 0.001)
  }

  @Test
  fun `CameraAnchorAnimator getAnimatedValueAt with startCameraState uses default anchor`() {
    val options = CameraAnimatorOptions.Builder<ScreenCoordinate>(
      targets = arrayOf(ScreenCoordinate(200.0, 200.0)),
    )
      .build()
    val animator = CameraAnchorAnimator(options)
    animator.interpolator = linearInterpolator

    val cameraState = mockk<CameraState>()

    val result = animator.getAnimatedValueAt(0.5f, cameraState)

    // Anchor defaults to ScreenCoordinate(0.0, 0.0) from camera state
    assertEquals(ScreenCoordinate(100.0, 100.0), result)
  }

  @Test
  fun `CameraPaddingAnimator getAnimatedValueAt with startCameraState uses camera state value`() {
    val options = CameraAnimatorOptions.Builder<EdgeInsets>(
      targets = arrayOf(EdgeInsets(20.0, 30.0, 40.0, 50.0)),
    )
      .build()
    val animator = CameraPaddingAnimator(options)
    animator.interpolator = linearInterpolator

    val cameraState = mockk<CameraState> {
      every { padding } returns EdgeInsets(0.0, 10.0, 20.0, 30.0)
    }

    val result = animator.getAnimatedValueAt(0.25f, cameraState)

    assertEquals(EdgeInsets(5.0, 15.0, 25.0, 35.0), result)
  }

  @Test
  fun `CameraCenterAnimator getAnimatedValueAt prefers startValue over startCameraState`() {
    val options = CameraAnimatorOptions.Builder<Point>(
      targets = arrayOf(Point.fromLngLat(9.0, 9.0)),
    )
      .startValue(Point.fromLngLat(1.0, 1.0)) // Should use this
      .build()
    val animator = CameraCenterAnimator(options = options, useShortestPath = true)
    animator.interpolator = linearInterpolator

    val cameraState = mockk<CameraState> {
      every { center } returns Point.fromLngLat(100.0, 100.0) // Should be ignored
    }

    val result = animator.getAnimatedValueAt(0.5f, cameraState)

    assertEquals(Point.fromLngLat(5.0, 5.0), result) // (1+9)/2 = 5
  }

  @Test(expected = UnsupportedOperationException::class)
  fun `CameraCenterAnimator getAnimatedValueAt throws exception when multiple targets`() {
    val options = CameraAnimatorOptions.Builder<Point>(
      targets = arrayOf(Point.fromLngLat(5.0, 5.0), Point.fromLngLat(10.0, 10.0)),
    )
      .startValue(Point.fromLngLat(0.0, 0.0))
      .build()
    val animator = CameraCenterAnimator(options = options, useShortestPath = true)
    animator.interpolator = linearInterpolator

    animator.getAnimatedValueAt(0.5f)
  }

  @Test(expected = UnsupportedOperationException::class)
  fun `CameraCenterAnimator getAnimatedValueAt throws exception when no startValue and null startCameraState`() {
    val options = CameraAnimatorOptions.Builder<Point>(
      targets = arrayOf(Point.fromLngLat(10.0, 10.0)),
    )
      .build()
    val animator = CameraCenterAnimator(options = options, useShortestPath = true)
    animator.interpolator = linearInterpolator

    animator.getAnimatedValueAt(0.5f, null)
  }

  @Test
  fun `CameraCenterAnimator getAnimatedValueAt at fraction 0 returns start value`() {
    val options = CameraAnimatorOptions.Builder<Point>(
      targets = arrayOf(Point.fromLngLat(10.0, 15.0)),
    )
      .startValue(Point.fromLngLat(2.0, 3.0))
      .build()
    val animator = CameraCenterAnimator(options = options, useShortestPath = true)
    animator.interpolator = linearInterpolator

    val result = animator.getAnimatedValueAt(0.0f)

    assertEquals(Point.fromLngLat(2.0, 3.0), result)
  }

  @Test
  fun `CameraCenterAnimator getAnimatedValueAt at fraction 1 returns target value`() {
    val options = CameraAnimatorOptions.Builder<Point>(
      targets = arrayOf(Point.fromLngLat(10.0, 15.0)),
    )
      .startValue(Point.fromLngLat(2.0, 3.0))
      .build()
    val animator = CameraCenterAnimator(options = options, useShortestPath = true)
    animator.interpolator = linearInterpolator

    val result = animator.getAnimatedValueAt(1.0f)

    assertEquals(Point.fromLngLat(10.0, 15.0), result)
  }
}