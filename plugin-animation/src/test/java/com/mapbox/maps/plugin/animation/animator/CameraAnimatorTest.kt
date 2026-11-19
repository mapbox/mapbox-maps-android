package com.mapbox.maps.plugin.animation.animator

import android.animation.Animator
import android.view.animation.Interpolator
import androidx.core.view.animation.PathInterpolatorCompat
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraPositionPoint
import com.mapbox.maps.CameraState
import com.mapbox.maps.CameraViewPoint
import com.mapbox.maps.CameraViewPointFollowsPositionPoint
import com.mapbox.maps.CameraViewPointState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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
  fun `CameraVerticalFovAnimator getAnimatedValueAt with startValue interpolates correctly`() {
    val options = CameraAnimatorOptions.Builder<Double>(
      targets = arrayOf(55.0)
    )
      .startValue(23.0)
      .build()
    val animator = CameraVerticalFovAnimator(options)
    animator.interpolator = linearInterpolator

    var result = animator.getAnimatedValueAt(0.25f)

    assertEquals(31.0, result, 0.001)
  }

  @Test
  fun `CameraPositionPointAnimator getAnimatedValueAt with startValue interpolates correctly`() {
    val options = CameraAnimatorOptions.Builder<CameraPositionPoint>(
      targets = arrayOf(CameraPositionPoint(1.0, -0.5))
    )
      .startValue(CameraPositionPoint(-1.0, 0.5))
      .build()
    val animator = CameraPositionPointAnimator(options)
    animator.interpolator = linearInterpolator

    val result = animator.getAnimatedValueAt(0.25f)

    assertEquals(CameraPositionPoint(-0.5, 0.25), result)
  }

  @Test
  fun `CameraPositionPointAnimator getAnimatedValueAt with startCameraState uses camera state value`() {
    val options = CameraAnimatorOptions.Builder<CameraPositionPoint>(
      targets = arrayOf(CameraPositionPoint(1.0, 1.0))
    )
      .build()
    val animator = CameraPositionPointAnimator(options)
    animator.interpolator = linearInterpolator

    val cameraState = mockk<CameraState> {
      every { positionPoint } returns CameraPositionPoint(0.0, -1.0)
    }

    val result = animator.getAnimatedValueAt(0.5f, cameraState)

    assertEquals(CameraPositionPoint(0.5, 0.0), result)
  }

  @Test
  fun `CameraPositionPointAnimator clamps overshooting fraction to valid range`() {
    val options = CameraAnimatorOptions.Builder<CameraPositionPoint>(
      targets = arrayOf(CameraPositionPoint(0.8, -0.8))
    )
      .startValue(CameraPositionPoint(0.0, 0.0))
      .build()
    val animator = CameraPositionPointAnimator(options)
    animator.interpolator = Interpolator { 1.5f * it }

    val result = animator.getAnimatedValueAt(1.0f)

    assertEquals(1.0, result.x, 0.0001)
    assertEquals(-1.0, result.y, 0.0001)
  }

  @Test
  fun `CameraPositionPointAnimator evaluator clamps undershooting fraction to valid range`() {
    val result = CameraPositionPointAnimator.POSITION_POINT_EVALUATOR.evaluate(
      -0.5f,
      CameraPositionPoint(0.9, -0.9),
      CameraPositionPoint(-0.9, 0.9)
    )

    assertEquals(1.0, result.x, 0.0001)
    assertEquals(-1.0, result.y, 0.0001)
  }

  private fun pinned(x: Double, y: Double) = CameraViewPointState.valueOf(CameraViewPoint(x, y))

  private fun follows() = CameraViewPointState.valueOf(CameraViewPointFollowsPositionPoint())

  private fun viewPointAnimator(
    start: CameraViewPointState,
    end: CameraViewPointState,
    followingPoint: CameraViewPoint = CameraViewPoint(0.0, 0.0)
  ) = CameraViewPointAnimator(
    CameraAnimatorOptions.Builder<CameraViewPointState>(targets = arrayOf(end)).startValue(start).build(),
    followingPoint
  ).also { it.interpolator = linearInterpolator }

  @Test
  fun `CameraViewPointAnimator interpolates between pinned view points`() {
    val animator = viewPointAnimator(pinned(-1.0, 0.5), pinned(1.0, -0.5))

    val result = animator.getAnimatedValueAt(0.25f)

    assertTrue(result.isCameraViewPoint)
    assertEquals(-0.5, result.cameraViewPoint.x, 0.0001)
    assertEquals(0.25, result.cameraViewPoint.y, 0.0001)
  }

  @Test
  fun `CameraViewPointAnimator writes the exact pinned target on the last frame`() {
    // Interpolating to these values yields 0.09999999999999998 and 0.8999999999999999, so only the pinned-target path is exact.
    val animator = viewPointAnimator(pinned(0.7, 0.2), pinned(0.1, 0.9))

    val result = animator.getAnimatedValueAt(1.0f)

    // State equality is tolerance-based, so compare the coordinates bit for bit.
    assertEquals(0.1, result.cameraViewPoint.x, 0.0)
    assertEquals(0.9, result.cameraViewPoint.y, 0.0)
  }

  @Test
  fun `CameraViewPointAnimator is never skipped even when start equals target`() {
    val animator = viewPointAnimator(pinned(0.3, 0.3), pinned(0.3, 0.3))
    animator.updateObjectValues { mockk<CameraState>(relaxed = true) }

    assertFalse(animator.skipped)
  }

  @Test
  fun `CameraViewPointAnimator releases to the following view point and ends on follows`() {
    val target = follows()
    val animator = viewPointAnimator(pinned(1.0, 1.0), target, followingPoint = CameraViewPoint(-1.0, 0.0))

    val halfway = animator.getAnimatedValueAt(0.5f)

    assertTrue(halfway.isCameraViewPoint)
    assertEquals(0.0, halfway.cameraViewPoint.x, 0.0001)
    assertEquals(0.5, halfway.cameraViewPoint.y, 0.0001)
    assertTrue(animator.getAnimatedValueAt(1.0f).isCameraViewPointFollowsPositionPoint)
    assertTrue(animator.releasesToFollow)
  }

  @Test
  fun `CameraViewPointAnimator starts from the following view point when released`() {
    val animator = viewPointAnimator(follows(), pinned(1.0, 1.0), followingPoint = CameraViewPoint(-1.0, -1.0))

    val quarter = animator.getAnimatedValueAt(0.25f)

    assertEquals(-0.5, quarter.cameraViewPoint.x, 0.0001)
    assertEquals(-0.5, quarter.cameraViewPoint.y, 0.0001)
    assertFalse(animator.releasesToFollow)
  }

  @Test
  fun `CameraViewPointAnimator clamps overshooting easing to valid range`() {
    val animator = viewPointAnimator(pinned(0.0, 0.0), pinned(0.8, -0.8))
    animator.interpolator = Interpolator { 1.5f * it }

    val result = animator.getAnimatedValueAt(0.9f)

    assertEquals(1.0, result.cameraViewPoint.x, 0.0001)
    assertEquals(-1.0, result.cameraViewPoint.y, 0.0001)
  }

  @Test
  fun `CameraViewPointAnimator getAnimatedValueAt with startCameraState uses camera state value`() {
    val animator = CameraViewPointAnimator(
      CameraAnimatorOptions.Builder<CameraViewPointState>(targets = arrayOf(pinned(1.0, 1.0))).build()
    ).also { it.interpolator = linearInterpolator }
    val cameraState = mockk<CameraState> {
      every { viewPoint } returns pinned(0.0, -1.0)
    }

    val result = animator.getAnimatedValueAt(0.5f, cameraState)

    assertEquals(0.5, result.cameraViewPoint.x, 0.0001)
    assertEquals(0.0, result.cameraViewPoint.y, 0.0001)
  }

  @Test
  fun `isSameAs compares view point states by kind and coordinates`() {
    assertTrue(pinned(0.5, 0.5).isSameAs(pinned(0.5, 0.5)))
    assertFalse(pinned(0.5, 0.5).isSameAs(pinned(0.5, 0.6)))
    assertTrue(follows().isSameAs(follows()))
    assertFalse(follows().isSameAs(pinned(0.0, 0.0)))
    assertFalse(pinned(0.0, 0.0).isSameAs(follows()))
    assertFalse(follows().isSameAs(null))
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
  fun `CameraVerticalFovAnimator getAnimatedValueAt with startCameraState uses camera state value`() {
    val options = CameraAnimatorOptions.Builder<Double>(
      targets = arrayOf(50.0),
    )
      .build()
    val animator = CameraVerticalFovAnimator(options)
    animator.interpolator = linearInterpolator

    val cameraState = mockk<CameraState> {
      every { verticalFov } returns 15.0
    }

    val result = animator.getAnimatedValueAt(0.25f, cameraState)

    assertEquals(23.75, result, 0.001)
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

  @Test
  fun `end sets endedNormally flag to true`() {
    val options = CameraAnimatorOptions.Builder<Double>(
      targets = arrayOf(10.0),
    )
      .startValue(1.0)
      .build()
    val animator = CameraZoomAnimator(options)

    // Register internal listener to allow animation to run
    val mockListener = mockk<Animator.AnimatorListener>(relaxed = true)
    animator.addInternalListener(mockListener)

    // Initially endedNormally should be false
    assertEquals(false, animator.endedManually)

    // Call end
    animator.end()

    // After end is called, endedNormally should be true
    assertEquals(true, animator.endedManually)
    assertEquals(false, animator.canceled)
  }

  @Test
  fun `cancel sets canceled flag to true`() {
    val options = CameraAnimatorOptions.Builder<Double>(
      targets = arrayOf(10.0),
    )
      .startValue(1.0)
      .build()
    val animator = CameraZoomAnimator(options)

    // Register internal listener
    val mockListener = mockk<Animator.AnimatorListener>(relaxed = true)
    animator.addInternalListener(mockListener)

    // Initially canceled should be false
    assertEquals(false, animator.canceled)

    // Call cancel
    animator.cancel()

    // After cancel is called, canceled should be true
    assertEquals(true, animator.canceled)
    assertEquals(false, animator.endedManually)
  }

  @Test
  fun `cancel does not set endedNormally flag`() {
    val options = CameraAnimatorOptions.Builder<Double>(
      targets = arrayOf(10.0),
    )
      .startValue(1.0)
      .build()
    val animator = CameraZoomAnimator(options)

    // Register internal listener
    val mockListener = mockk<Animator.AnimatorListener>(relaxed = true)
    animator.addInternalListener(mockListener)

    // Call cancel
    animator.cancel()

    // endedNormally should remain false when cancel() is called
    assertEquals(false, animator.endedManually)
    assertEquals(true, animator.canceled)
  }

  @Test
  fun `start resets endedNormally and canceled flags`() {
    val options = CameraAnimatorOptions.Builder<Double>(
      targets = arrayOf(10.0),
    )
      .startValue(1.0)
      .build()
    val animator = CameraZoomAnimator(options)

    // Register internal listener to allow animation
    val mockListener = mockk<Animator.AnimatorListener>(relaxed = true)
    animator.addInternalListener(mockListener)

    // Set flags by ending and canceling
    animator.end()
    assertEquals(true, animator.endedManually)

    animator.cancel()
    assertEquals(true, animator.canceled)

    // Start again - should reset flags
    animator.start()

    // Both flags should be reset to false
    assertEquals(false, animator.endedManually)
    assertEquals(false, animator.canceled)
  }

  @Test
  fun `end then cancel maintains canceled state`() {
    val options = CameraAnimatorOptions.Builder<Double>(
      targets = arrayOf(10.0),
    )
      .startValue(1.0)
      .build()
    val animator = CameraZoomAnimator(options)

    // Register internal listener
    val mockListener = mockk<Animator.AnimatorListener>(relaxed = true)
    animator.addInternalListener(mockListener)

    // Start, then end
    animator.start()
    animator.end()
    assertEquals(true, animator.endedManually)
    assertEquals(false, animator.canceled)

    // Then cancel
    animator.cancel()
    assertEquals(true, animator.canceled)
    // endedNormally remains true from previous end()
    assertEquals(true, animator.endedManually)
  }
}