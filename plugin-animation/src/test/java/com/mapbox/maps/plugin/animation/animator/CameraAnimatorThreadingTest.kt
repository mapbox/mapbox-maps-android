package com.mapbox.maps.plugin.animation.animator

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions
import com.mapbox.maps.threading.AnimationThreadController
import io.mockk.every
import io.mockk.just
import io.mockk.mockkObject
import io.mockk.runs
import io.mockk.unmockkObject
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Guards the threading contract of [CameraAnimator.setObjectValues]: writes must go through
 * [AnimationThreadController.postOnAnimatorThread] so they never race with the animator thread.
 * The device test `CameraAnimatorThreadRaceTest` covers the real thread; this one runs in CI.
 */
@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
class CameraAnimatorThreadingTest {

  private val posted = mutableListOf<() -> Unit>()

  @Before
  fun setUp() {
    mockkObject(AnimationThreadController)
    every { AnimationThreadController.postOnAnimatorThread(capture(posted)) } just runs
  }

  @After
  fun tearDown() {
    unmockkObject(AnimationThreadController)
  }

  @Test
  fun `setObjectValues posts the write to the animator thread`() {
    val animator = CameraZoomAnimator(cameraAnimatorOptions(SEED))

    animator.setObjectValues(START, TARGET)

    verify(exactly = 1) { AnimationThreadController.postOnAnimatorThread(any()) }
    assertEquals("values must not change before the posted write runs", SEED, animator.endValue(), 0.0)

    posted.single().invoke()

    assertEquals("posted write must install the new values", TARGET, animator.endValue(), 0.0)
  }

  @Test
  fun `setObjectValues is synchronous when the controller runs the block inline`() {
    every { AnimationThreadController.postOnAnimatorThread(any()) } answers { firstArg<() -> Unit>().invoke() }
    val animator = CameraZoomAnimator(cameraAnimatorOptions(SEED))

    animator.setObjectValues(START, TARGET)

    assertEquals(TARGET, animator.endValue(), 0.0)
  }

  /** Evaluates the animator at the end of its range, which reads the real keyframes. */
  private fun CameraZoomAnimator.endValue(): Double {
    setCurrentFraction(1f)
    return animatedValue as Double
  }

  private companion object {
    const val SEED = 5.0
    const val START = 1.0
    const val TARGET = 9.0
  }
}