package com.mapbox.maps.plugin.animation

import android.animation.Animator.DURATION_INFINITE
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.animation.animator.CameraAnchorAnimator
import com.mapbox.maps.plugin.animation.animator.CameraAnimator
import com.mapbox.maps.plugin.animation.animator.CameraBearingAnimator
import com.mapbox.maps.plugin.animation.animator.CameraCenterAnimator
import com.mapbox.maps.plugin.animation.animator.CameraPaddingAnimator
import com.mapbox.maps.plugin.animation.animator.CameraPitchAnimator
import com.mapbox.maps.plugin.animation.animator.CameraZoomAnimator
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CameraAnimationsExtTest {

  private lateinit var mockCameraState: CameraState

  @Before
  fun setUp() {
    mockCameraState = mockk {
      every { center } returns Point.fromLngLat(0.0, 0.0)
      every { zoom } returns 10.0
      every { bearing } returns 0.0
      every { pitch } returns 0.0
      every { padding } returns EdgeInsets(0.0, 0.0, 0.0, 0.0)
    }
  }

  @Test
  fun `AnimatorSet calculateCameraAnimationHint returns null for empty AnimatorSet`() {
    val animatorSet = AnimatorSet()

    val result = animatorSet.calculateCameraAnimationHint(listOf(0.5f), mockCameraState)

    assertNull(result)
  }

  @Test
  fun `AnimatorSet calculateCameraAnimationHint returns null for AnimatorSet with non-zero startDelay`() {
    val animatorSet = AnimatorSet()
    animatorSet.startDelay = 100L

    val result = animatorSet.calculateCameraAnimationHint(listOf(0.5f), mockCameraState)

    assertNull(result)
  }

  @Test
  fun `AnimatorSet calculateCameraAnimationHint returns null for AnimatorSet with non-CameraAnimator children`() {
    val animatorSet = AnimatorSet()
    val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
    animatorSet.playTogether(valueAnimator)

    val result = animatorSet.calculateCameraAnimationHint(listOf(0.5f), mockCameraState)

    assertNull(result)
  }

  @Test
  fun `AnimatorSet calculateCameraAnimationHint returns valid hint for CameraAnimators`() {
    val centerAnimator = mockk<CameraCenterAnimator>() {
      every { duration } returns 1000L
      every { totalDuration } returns 1000L
      every { startDelay } returns 0L
      every { getAnimatedValueAt(0.5f, mockCameraState) } returns Point.fromLngLat(0.5, 0.5)
    }

    val zoomAnimator = mockk<CameraZoomAnimator>() {
      every { duration } returns 1000L
      every { totalDuration } returns 1000L
      every { startDelay } returns 0L
      every { getAnimatedValueAt(0.5f, mockCameraState) } returns 11.0
    }

    val animatorSet = spyk(AnimatorSet()) {
      every { childAnimations } returns arrayListOf(centerAnimator, zoomAnimator)
      every { duration } returns -1L // Let it use children's max duration
    }

    val result = animatorSet.calculateCameraAnimationHint(listOf(0.5f), mockCameraState)

    assertEquals(1, result?.stages?.size)
    assertEquals(500L, result?.stages?.get(0)?.progress)
    assertEquals(Point.fromLngLat(0.5, 0.5), result?.stages?.get(0)?.camera?.center)
    assertEquals(11.0, result?.stages?.get(0)?.camera?.zoom!!, 0.000001)
  }

  @Test
  fun `AnimatorSet calculateCameraAnimationHint uses AnimatorSet duration when set`() {
    val centerAnimator = mockk<CameraCenterAnimator>() {
      every { duration } returns 1000L
      every { totalDuration } returns 1000L
      every { startDelay } returns 0L
      every { getAnimatedValueAt(1f, mockCameraState) } returns Point.fromLngLat(0.5, 0.5)
    }

    val animatorSet = spyk(AnimatorSet()) {
      every { childAnimations } returns arrayListOf(centerAnimator)
      every { duration } returns 2000L // AnimatorSet has its own duration
    }

    val result = animatorSet.calculateCameraAnimationHint(listOf(0.5f), mockCameraState)

    assertEquals(1, result?.stages?.size)
    assertEquals(1000L, result?.stages?.get(0)?.progress) // 0.5 * 2000ms AnimatorSet duration
    assertEquals(Point.fromLngLat(0.5, 0.5), result?.stages?.get(0)?.camera?.center)
  }

  @Test
  fun `List calculateCameraAnimationHint returns null for empty list`() {
    val emptyList = emptyList<CameraAnimator<*>>()

    val result = emptyList.calculateCameraAnimationHint(listOf(0.5f), mockCameraState)

    assertNull(result)
  }

  @Test
  fun `List calculateCameraAnimationHint returns null for zero duration animations`() {
    val centerAnimator = mockk<CameraCenterAnimator>() {
      every { duration } returns 0L
      every { totalDuration } returns 0L
      every { startDelay } returns 0L
    }

    val result = listOf(centerAnimator).calculateCameraAnimationHint(listOf(0.5f), mockCameraState)

    assertNull(result)
  }

  @Test
  fun `List calculateCameraAnimationHint calculates correct progress for single fraction`() {
    val centerAnimator = mockk<CameraCenterAnimator>() {
      every { duration } returns 1000L
      every { totalDuration } returns 1000L
      every { startDelay } returns 0L
      every { getAnimatedValueAt(0.5f, mockCameraState) } returns Point.fromLngLat(0.5, 0.5)
    }

    val result = listOf(centerAnimator).calculateCameraAnimationHint(listOf(0.5f), mockCameraState)

    assertEquals(1, result?.stages?.size)
    assertEquals(500L, result?.stages?.get(0)?.progress)
    assertEquals(Point.fromLngLat(0.5, 0.5), result?.stages?.get(0)?.camera?.center)
  }

  @Test
  fun `List calculateCameraAnimationHint calculates correct progress for multiple fractions`() {
    val centerAnimator = mockk<CameraCenterAnimator>() {
      every { duration } returns 1000L
      every { totalDuration } returns 1000L
      every { startDelay } returns 0L
      every { getAnimatedValueAt(0.0f, mockCameraState) } returns Point.fromLngLat(0.0, 0.0)
      every { getAnimatedValueAt(0.5f, mockCameraState) } returns Point.fromLngLat(0.5, 0.5)
      every { getAnimatedValueAt(1.0f, mockCameraState) } returns Point.fromLngLat(1.0, 1.0)
    }

    val result = listOf(centerAnimator).calculateCameraAnimationHint(listOf(0.0f, 0.5f, 1.0f), mockCameraState)

    assertEquals(3, result?.stages?.size)
    assertEquals(0L, result?.stages?.get(0)?.progress)
    assertEquals(500L, result?.stages?.get(1)?.progress)
    assertEquals(1000L, result?.stages?.get(2)?.progress)
    assertEquals(Point.fromLngLat(0.0, 0.0), result?.stages?.get(0)?.camera?.center)
    assertEquals(Point.fromLngLat(0.5, 0.5), result?.stages?.get(1)?.camera?.center)
    assertEquals(Point.fromLngLat(1.0, 1.0), result?.stages?.get(2)?.camera?.center)
  }

  @Test
  fun `List calculateCameraAnimationHint calculates correct progress for multiple animators with different durations`() {
    val centerAnimator = mockk<CameraCenterAnimator>() {
      every { duration } returns 2000L
      every { totalDuration } returns 2000L
      every { startDelay } returns 0L
      every { getAnimatedValueAt(0.5f, mockCameraState) } returns Point.fromLngLat(0.5, 0.5)
    }

    val zoomAnimator = mockk<CameraZoomAnimator>() {
      every { duration } returns 1000L
      every { totalDuration } returns 1000L
      every { startDelay } returns 0L
      every { getAnimatedValueAt(1f, mockCameraState) } returns 11.0
    }

    val result = listOf(centerAnimator, zoomAnimator).calculateCameraAnimationHint(listOf(0.5f), mockCameraState)

    assertEquals(1, result?.stages?.size)
    assertEquals(1000L, result?.stages?.get(0)?.progress) // 0.5 * 2000ms (max safeTotalDuration)
    assertEquals(Point.fromLngLat(0.5, 0.5), result?.stages?.get(0)?.camera?.center)
    assertEquals(11.0, result?.stages?.get(0)?.camera?.zoom!!, 0.000001)
  }

  @Test
  fun `List calculateCameraAnimationHint handles animators with start delays correctly`() {
    val centerAnimator = mockk<CameraCenterAnimator>() {
      every { duration } returns 1000L
      every { totalDuration } returns 1500L // duration + startDelay
      every { startDelay } returns 500L
      // At 0.5 total progress (750ms), this animator will be at 250 ms out of 1000 ms.
      every { getAnimatedValueAt(0.25f, mockCameraState) } returns Point.fromLngLat(0.25, 0.25)
    }

    val zoomAnimator = mockk<CameraZoomAnimator>() {
      every { duration } returns 1000L
      every { totalDuration } returns 1000L // duration + startDelay
      every { startDelay } returns 0L
      // At 0.5 total progress (750ms), relative fraction = (750 - 0) / 1000 = 0.75
      every { getAnimatedValueAt(0.75f, mockCameraState) } returns 11.0
    }

    val result = listOf(centerAnimator, zoomAnimator).calculateCameraAnimationHint(listOf(0.5f), mockCameraState)

    assertEquals(1, result?.stages?.size)
    // Total duration is max(1500, 1000) = 1500ms, so 0.5 * 1500 = 750ms
    assertEquals(750L, result?.stages?.get(0)?.progress)
    // Verify the calculated values are used
    assertEquals(Point.fromLngLat(0.25, 0.25), result?.stages?.get(0)?.camera?.center)
    assertEquals(11.0, result?.stages?.get(0)?.camera?.zoom!!, 0.001)
  }

  @Test
  fun `List calculateCameraAnimationHint handles UnsupportedOperationException gracefully`() {
    val centerAnimator = mockk<CameraCenterAnimator>(relaxed = true) {
      every { duration } returns 1000L
      every { totalDuration } returns 1000L
      every { startDelay } returns 0L
      every { getAnimatedValueAt(0.5f, mockCameraState) } throws UnsupportedOperationException("Test exception")
    }

    val result = listOf(centerAnimator).calculateCameraAnimationHint(listOf(0.5f), mockCameraState)

    assertEquals(1, result?.stages?.size)
    assertEquals(500L, result?.stages?.get(0)?.progress)
    assertNull(result?.stages?.get(0)?.camera?.center)
  }

  @Test
  fun `List calculateCameraAnimationHint works with different camera animator types`() {
    val centerAnimator = mockk<CameraCenterAnimator>() {
      every { duration } returns 1000L
      every { totalDuration } returns 1000L
      every { startDelay } returns 0L
      every { getAnimatedValueAt(0.5f, mockCameraState) } returns Point.fromLngLat(0.5, 0.5)
    }

    val zoomAnimator = mockk<CameraZoomAnimator>() {
      every { duration } returns 1000L
      every { totalDuration } returns 1000L
      every { startDelay } returns 0L
      every { getAnimatedValueAt(0.5f, mockCameraState) } returns 11.0
    }

    val bearingAnimator = mockk<CameraBearingAnimator>() {
      every { duration } returns 1000L
      every { totalDuration } returns 1000L
      every { startDelay } returns 0L
      every { getAnimatedValueAt(0.5f, mockCameraState) } returns 22.5
    }

    val pitchAnimator = mockk<CameraPitchAnimator>() {
      every { duration } returns 1000L
      every { totalDuration } returns 1000L
      every { startDelay } returns 0L
      every { getAnimatedValueAt(0.5f, mockCameraState) } returns 15.0
    }

    val anchorAnimator = mockk<CameraAnchorAnimator>() {
      every { duration } returns 1000L
      every { totalDuration } returns 1000L
      every { startDelay } returns 0L
      every { getAnimatedValueAt(0.5f, mockCameraState) } returns ScreenCoordinate(100.0, 100.0)
    }

    val paddingAnimator = mockk<CameraPaddingAnimator>() {
      every { duration } returns 1000L
      every { totalDuration } returns 1000L
      every { startDelay } returns 0L
      every { getAnimatedValueAt(0.5f, mockCameraState) } returns EdgeInsets(5.0, 5.0, 5.0, 5.0)
    }

    val animators = listOf(centerAnimator, zoomAnimator, bearingAnimator, pitchAnimator, anchorAnimator, paddingAnimator)
    val result = animators.calculateCameraAnimationHint(listOf(0.5f), mockCameraState)

    assertEquals(1, result?.stages?.size)
    assertEquals(500L, result?.stages?.get(0)?.progress)
    assertEquals(Point.fromLngLat(0.5, 0.5), result?.stages?.get(0)?.camera?.center)
    assertEquals(11.0, result?.stages?.get(0)?.camera?.zoom ?: -1.0, 0.001)
    assertEquals(22.5, result?.stages?.get(0)?.camera?.bearing ?: -1.0, 0.001)
    assertEquals(15.0, result?.stages?.get(0)?.camera?.pitch ?: -1.0, 0.001)
    assertEquals(ScreenCoordinate(100.0, 100.0), result?.stages?.get(0)?.camera?.anchor)
    assertEquals(EdgeInsets(5.0, 5.0, 5.0, 5.0), result?.stages?.get(0)?.camera?.padding)
  }

  @Test
  fun `List calculateCameraAnimationHint handles null startCameraState`() {
    val centerAnimator = mockk<CameraCenterAnimator>() {
      every { duration } returns 1000L
      every { totalDuration } returns 1000L
      every { startDelay } returns 0L
      every { getAnimatedValueAt(0.5f, null) } returns Point.fromLngLat(0.75, 0.75)
    }

    val result = listOf(centerAnimator).calculateCameraAnimationHint(listOf(0.5f), null)

    assertEquals(1, result?.stages?.size)
    assertEquals(500L, result?.stages?.get(0)?.progress)
    assertEquals(Point.fromLngLat(0.75, 0.75), result?.stages?.get(0)?.camera?.center)
  }

  @Test
  fun `List calculateCameraAnimationHint handles negative duration gracefully`() {
    val centerAnimator = mockk<CameraCenterAnimator>() {
      every { duration } returns -1L
      every { totalDuration } returns -1L
      every { startDelay } returns 0L
    }

    val result = listOf(centerAnimator).calculateCameraAnimationHint(listOf(0.5f), mockCameraState)

    assertNull(result)
  }

  @Test
  fun `List calculateCameraAnimationHint works when some animators have zero duration but max duration is non-zero`() {
    val validAnimator = mockk<CameraCenterAnimator>() {
      every { duration } returns 1000L
      every { totalDuration } returns 1000L
      every { startDelay } returns 0L
      // At 0.5 total progress (500ms), relative fraction = (500 - 0) / 1000 = 0.5
      every { getAnimatedValueAt(0.5f, mockCameraState) } returns Point.fromLngLat(0.5, 0.5)
    }

    val zeroDurationAnimator = mockk<CameraZoomAnimator>() {
      every { duration } returns 0L
      every { totalDuration } returns 0L
      every { startDelay } returns 0L
      // For zero duration animator, relative fraction calculation: (500 - 0) / 0 = infinity
      // This gets clamped to 1.0f by coerceIn(0f, 1f)
      every { getAnimatedValueAt(1.0f, mockCameraState) } returns 12.0
    }

    val result = listOf(validAnimator, zeroDurationAnimator).calculateCameraAnimationHint(listOf(0.5f), mockCameraState)

    // Should work because max duration is 1000L (from validAnimator)
    assertEquals(1, result?.stages?.size)
    assertEquals(500L, result?.stages?.get(0)?.progress)

    // Check interpolated values for both animators
    val stage = result?.stages?.get(0)
    assertEquals(Point.fromLngLat(0.5, 0.5), stage?.camera?.center) // From validAnimator at 0.5 fraction
    assertEquals(12.0, stage?.camera?.zoom!!, 0.001) // From zeroDurationAnimator at 1.0 fraction (clamped)
  }

  @Test
  fun `List calculateCameraAnimationHint with empty fractions list`() {
    val centerAnimator = mockk<CameraCenterAnimator>() {
      every { duration } returns 1000L
      every { totalDuration } returns 1000L
      every { startDelay } returns 0L
    }

    val result = listOf(centerAnimator).calculateCameraAnimationHint(emptyList(), mockCameraState)

    assertEquals(0, result?.stages?.size)
  }

  @Test
  fun `List calculateCameraAnimationHint relative fraction calculation for animators with delays`() {
    val delayedAnimator = mockk<CameraCenterAnimator>() {
      every { duration } returns 500L
      every { totalDuration } returns 1000L
      every { startDelay } returns 500L
      every { getAnimatedValueAt(0f, mockCameraState) } returns Point.fromLngLat(0.5, 0.5)
      every { getAnimatedValueAt(0.5f, mockCameraState) } returns Point.fromLngLat(0.7, 0.7)
    }

    val result = listOf(delayedAnimator).calculateCameraAnimationHint(listOf(0.25f, 0.75f), mockCameraState)

    assertEquals(2, result?.stages?.size)
    // Total duration is 500+500=1000ms
    // At 0.25 total progress (250ms), animator hasn't started yet (starts at 500ms)
    assertEquals(250L, result?.stages?.get(0)?.progress)
    assertEquals(Point.fromLngLat(0.5, 0.5), result?.stages?.get(0)?.camera?.center)
    // At 0.75 total progress (750ms), animator is at (750-500)/500 = 0.5 relative progress
    assertEquals(750L, result?.stages?.get(1)?.progress)
    assertEquals(Point.fromLngLat(0.7, 0.7), result?.stages?.get(1)?.camera?.center)
  }

  @Test
  fun `List calculateCameraAnimationHint with different start delays chooses correct total duration`() {
    val shortAnimator = mockk<CameraCenterAnimator>() {
      every { duration } returns 500L
      every { totalDuration } returns 500L // duration + startDelay
      every { startDelay } returns 0L
      // At 0.5 total progress (600ms), relative fraction = (600 - 0) / 500 = 1.2
      // Gets clamped to 1.0f by coerceIn(0f, 1f)
      every { getAnimatedValueAt(1.0f, mockCameraState) } returns Point.fromLngLat(1.0, 1.0)
    }

    val delayedAnimator = mockk<CameraZoomAnimator>() {
      every { duration } returns 800L
      every { totalDuration } returns 1200L // duration + startDelay
      every { startDelay } returns 400L
      // At 0.5 total progress (600ms), relative fraction = (600 - 400) / 800 = 0.25
      every { getAnimatedValueAt(0.25f, mockCameraState) } returns 10.25
    }

    val result = listOf(shortAnimator, delayedAnimator).calculateCameraAnimationHint(listOf(0.5f), mockCameraState)

    assertEquals(1, result?.stages?.size)
    // Total duration is max(500, 1200) = 1200ms, so 0.5 * 1200 = 600ms
    assertEquals(600L, result?.stages?.get(0)?.progress)

    // Check interpolated values
    val stage = result?.stages?.get(0)
    assertEquals(Point.fromLngLat(1.0, 1.0), stage?.camera?.center) // shortAnimator finished (fraction 1.0)
    assertEquals(10.25, stage?.camera?.zoom ?: -1.0, 0.001) // delayedAnimator at 0.25 fraction
  }

  @Test
  fun `getRelativeFraction handles zero duration child animations correctly`() {
    val normalAnimator = mockk<CameraCenterAnimator>() {
      every { duration } returns 1000L
      every { totalDuration } returns 1000L
      every { startDelay } returns 0L
      every { getAnimatedValueAt(0.3f, mockCameraState) } returns Point.fromLngLat(0.3, 0.3)
    }

    val zeroDurationAnimator = mockk<CameraZoomAnimator>() {
      every { duration } returns 0L
      every { totalDuration } returns 0L
      every { startDelay } returns 0L
      // For zero duration, getRelativeFraction should return 1f regardless of totalFraction
      every { getAnimatedValueAt(1.0f, mockCameraState) } returns 15.0
    }

    val result = listOf(normalAnimator, zeroDurationAnimator)
      .calculateCameraAnimationHint(listOf(0.3f), mockCameraState)

    assertEquals(1, result?.stages?.size)
    // Total duration is max(1000, 0) = 1000ms, so 0.3 * 1000 = 300ms
    assertEquals(300L, result?.stages?.get(0)?.progress)

    val stage = result?.stages?.get(0)
    // Normal animator: relative fraction = (300 - 0) / 1000 = 0.3
    assertEquals(Point.fromLngLat(0.3, 0.3), stage?.camera?.center)
    // Zero duration animator: relative fraction = 1.0 (hardcoded for zero duration)
    assertEquals(15.0, stage?.camera?.zoom!!, 0.001)
  }

  @Test
  fun `List calculateCameraAnimationHint handles infinite duration animators correctly`() {
    val normalAnimator = mockk<CameraCenterAnimator>() {
      every { duration } returns 2000L
      every { totalDuration } returns 2000L
      every { startDelay } returns 0L
      every { getAnimatedValueAt(0.5f, mockCameraState) } returns Point.fromLngLat(0.5, 0.5)
    }

    val infiniteDurationAnimator = mockk<CameraZoomAnimator>() {
      every { duration } returns DURATION_INFINITE
      every { totalDuration } returns DURATION_INFINITE
      every { startDelay } returns 0L
      every { getAnimatedValueAt(1.0f, mockCameraState) } returns 20.0
    }

    val result = listOf(normalAnimator, infiniteDurationAnimator).calculateCameraAnimationHint(listOf(0.5f), mockCameraState)

    assertNull(result)
  }

  @Test
  fun `AnimatorSet calculateCameraAnimationHint returns null for infinite duration child animators`() {
    val normalAnimator = mockk<CameraCenterAnimator>() {
      every { duration } returns 1500L
      every { totalDuration } returns 1500L
      every { startDelay } returns 0L
      every { getAnimatedValueAt(0.6f, mockCameraState) } returns Point.fromLngLat(0.6, 0.6)
    }

    val infiniteDurationAnimator = mockk<CameraZoomAnimator>() {
      every { duration } returns DURATION_INFINITE
      every { totalDuration } returns DURATION_INFINITE
      every { startDelay } returns 0L
      every { getAnimatedValueAt(1.0f, mockCameraState) } returns 25.0
    }

    val animatorSet = spyk(AnimatorSet()) {
      every { childAnimations } returns arrayListOf(normalAnimator, infiniteDurationAnimator)
      every { duration } returns 1500L
    }

    val result = animatorSet.calculateCameraAnimationHint(listOf(0.6f), mockCameraState)

    assertNull(result)
  }
}