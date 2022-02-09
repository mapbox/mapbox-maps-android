package com.mapbox.maps.plugin.locationcomponent.animators

import android.animation.ValueAnimator
import android.os.Looper
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorAccuracyRadiusChangedListener
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
class PuckAccuracyRadiusAnimatorTest {

  private lateinit var accuracyRadiusAnimator: PuckAccuracyRadiusAnimator
  private lateinit var userConfiguredAnimator: ValueAnimator

  private val accuracyRadiusChangedListener = mockk<OnIndicatorAccuracyRadiusChangedListener>(relaxed = true)

  @Before
  fun setUp() {
    val animator = PuckAccuracyRadiusAnimator(accuracyRadiusChangedListener)
    userConfiguredAnimator = spyk(animator.clone())
    animator.userConfiguredAnimator = userConfiguredAnimator
    accuracyRadiusAnimator = spyk(animator)
  }

  @Test
  fun animatePredefined() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    accuracyRadiusAnimator.animate(0.0, 10.0)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 0) { accuracyRadiusAnimator.cancel() }
    verify(exactly = 0) { userConfiguredAnimator.cancel() }
    verify(exactly = 1) { accuracyRadiusAnimator.start() }
    verify(exactly = 0) { userConfiguredAnimator.start() }
  }

  @Test
  fun animateCustomAnimatorOptions() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    accuracyRadiusAnimator.animate(0.0, 10.0, options = {})
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 1) { userConfiguredAnimator.start() }
    verify(exactly = 0) { accuracyRadiusAnimator.cancel() }
    verify(exactly = 0) { userConfiguredAnimator.cancel() }
    verify(exactly = 0) { accuracyRadiusAnimator.start() }
  }

  @Test
  fun animateInterruptDefaultWithCustom() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    accuracyRadiusAnimator.animate(0.0, 5.0)
    accuracyRadiusAnimator.animate(
      0.0, 10.0,
      options = { duration = 50 }
    )
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 1) { accuracyRadiusAnimator.cancel() }
    verify(exactly = 0) { userConfiguredAnimator.cancel() }
    verify(exactly = 1) { accuracyRadiusAnimator.start() }
    verify(exactly = 1) { userConfiguredAnimator.start() }
  }

  @Test
  fun cancelRunningWhenNotRunning() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    accuracyRadiusAnimator.cancelRunning()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 0) { accuracyRadiusAnimator.cancel() }
    verify(exactly = 0) { userConfiguredAnimator.cancel() }
  }

  @Test
  fun cancelRunningWhenRunning() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    accuracyRadiusAnimator.animate(0.0, 10.0)
    accuracyRadiusAnimator.cancelRunning()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 1) { accuracyRadiusAnimator.cancel() }
    verify(exactly = 0) { userConfiguredAnimator.cancel() }
  }

  @Test
  fun cancelRunningWhenRunningCustom() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    accuracyRadiusAnimator.animate(
      0.0, 10.0,
      options = { duration = 50 }
    )
    accuracyRadiusAnimator.cancelRunning()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 0) { accuracyRadiusAnimator.cancel() }
    verify(exactly = 1) { userConfiguredAnimator.cancel() }
  }

  @Test
  fun updateOptions() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    accuracyRadiusAnimator.updateOptions {
      duration = 2000
    }
    accuracyRadiusAnimator.animate(0.0, 10.0)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { accuracyRadiusAnimator.start() }
    Assert.assertEquals(2000, accuracyRadiusAnimator.duration)
  }
}