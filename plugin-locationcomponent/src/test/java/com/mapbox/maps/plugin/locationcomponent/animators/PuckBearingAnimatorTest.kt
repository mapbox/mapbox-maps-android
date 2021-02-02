package com.mapbox.maps.plugin.locationcomponent.animators

import android.animation.ValueAnimator
import android.os.Looper
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
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
class PuckBearingAnimatorTest {

  private lateinit var bearingAnimator: PuckBearingAnimator
  private lateinit var userConfiguredAnimator: ValueAnimator

  private val bearingChangeListener = mockk<OnIndicatorBearingChangedListener>(relaxed = true)

  @Before
  fun setUp() {
    val animator = PuckBearingAnimator(bearingChangeListener)
    userConfiguredAnimator = spyk(animator.clone())
    animator.userConfiguredAnimator = userConfiguredAnimator
    bearingAnimator = spyk(animator)
  }

  @Test
  fun animateUpdateListener() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    bearingAnimator.animate(0.0, 10.0)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify {
      bearingChangeListener.onIndicatorBearingChanged(any())
    }
    Assert.assertEquals(10.0, bearingAnimator.animatedValue as Double, 0.0001)
  }

  @Test
  fun animatePredefined() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    bearingAnimator.animate(0.0, 10.0)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 0) { bearingAnimator.cancel() }
    verify(exactly = 0) { userConfiguredAnimator.cancel() }
    verify(exactly = 1) { bearingAnimator.start() }
    verify(exactly = 0) { userConfiguredAnimator.start() }
  }

  @Test
  fun animateCustomAnimatorOptions() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    bearingAnimator.animate(0.0, 10.0, options = {})
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 1) { userConfiguredAnimator.start() }
    verify(exactly = 0) { bearingAnimator.cancel() }
    verify(exactly = 0) { userConfiguredAnimator.cancel() }
    verify(exactly = 0) { bearingAnimator.start() }
  }

  @Test
  fun animateInterruptDefaultWithCustom() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    bearingAnimator.animate(0.0, 5.0)
    bearingAnimator.animate(
      0.0, 10.0,
      options = { duration = 50 }
    )
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 1) { bearingAnimator.cancel() }
    verify(exactly = 0) { userConfiguredAnimator.cancel() }
    verify(exactly = 1) { bearingAnimator.start() }
    verify(exactly = 1) { userConfiguredAnimator.start() }
  }

  @Test
  fun cancelRunningWhenNotRunning() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    bearingAnimator.cancelRunning()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 0) { bearingAnimator.cancel() }
    verify(exactly = 0) { userConfiguredAnimator.cancel() }
  }

  @Test
  fun cancelRunningWhenRunning() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    bearingAnimator.animate(0.0, 10.0)
    bearingAnimator.cancelRunning()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 1) { bearingAnimator.cancel() }
    verify(exactly = 0) { userConfiguredAnimator.cancel() }
  }

  @Test
  fun cancelRunningWhenRunningCustom() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    bearingAnimator.animate(
      0.0, 10.0,
      options = { duration = 50 }
    )
    bearingAnimator.cancelRunning()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 0) { bearingAnimator.cancel() }
    verify(exactly = 1) { userConfiguredAnimator.cancel() }
  }

  @Test
  fun updateOptions() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    bearingAnimator.updateOptions {
      duration = 2000
    }
    bearingAnimator.animate(0.0, 10.0)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { bearingAnimator.start() }
    Assert.assertEquals(2000, bearingAnimator.duration)
  }
}