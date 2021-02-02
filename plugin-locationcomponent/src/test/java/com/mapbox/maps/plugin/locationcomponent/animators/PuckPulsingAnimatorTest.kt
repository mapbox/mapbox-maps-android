package com.mapbox.maps.plugin.locationcomponent.animators

import android.animation.ValueAnimator
import android.os.Looper
import io.mockk.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class PuckPulsingAnimatorTest {

  private lateinit var pulsingAnimator: PuckPulsingAnimator

  @Before
  fun setUp() {
    val animator = PuckPulsingAnimator()
    pulsingAnimator = spyk(animator)
  }

  @Test
  fun creation() {
    Assert.assertEquals(PuckPulsingAnimator.PULSING_DEFAULT_DURATION, pulsingAnimator.duration)
    Assert.assertEquals(ValueAnimator.RESTART, pulsingAnimator.repeatMode)
  }

  @Test
  fun animatePredefined() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    pulsingAnimator.animate(0.0, 10.0)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 0) { pulsingAnimator.cancel() }
    verify(exactly = 1) { pulsingAnimator.start() }
  }

  @Test
  fun animateInfinite() {
    every { pulsingAnimator.animate(any(), any()) } just Runs
    pulsingAnimator.animateInfinite()
    verify(exactly = 1) { pulsingAnimator.animate(0.0, any()) }
  }

  @Test
  fun cancelRunningWhenNotRunning() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    pulsingAnimator.cancelRunning()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 0) { pulsingAnimator.cancel() }
  }

  @Test
  fun cancelRunningWhenRunning() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    pulsingAnimator.animate(0.0, 10.0)
    pulsingAnimator.cancelRunning()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 1) { pulsingAnimator.cancel() }
  }

  @Test
  fun updateOptions() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    pulsingAnimator.updateOptions {
      duration = 2000
    }
    pulsingAnimator.animate(0.0, 10.0)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { pulsingAnimator.start() }
    Assert.assertEquals(2000, pulsingAnimator.duration)
  }
}