package com.mapbox.maps.plugin.locationcomponent.animators

import android.animation.ValueAnimator
import android.os.Looper
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
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
class PuckPositionAnimatorTest {

  private lateinit var positionAnimator: PuckPositionAnimator
  private lateinit var userConfiguredAnimator: ValueAnimator

  private val positionChangeListener = mockk<OnIndicatorPositionChangedListener>(relaxed = true)

  @Before
  fun setUp() {
    val animator = PuckPositionAnimator(positionChangeListener)
    userConfiguredAnimator = spyk(animator.clone())
    animator.userConfiguredAnimator = userConfiguredAnimator
    positionAnimator = spyk(animator)
  }

  @Test
  fun animateUpdateListener() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    positionAnimator.animate(START_POINT, END_POINT)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify {
      positionChangeListener.onIndicatorPositionChanged(any())
    }
    Assert.assertEquals(END_POINT, positionAnimator.animatedValue as Point)
  }

  @Test
  fun animatePredefined() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    positionAnimator.animate(START_POINT, END_POINT)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 0) { positionAnimator.cancel() }
    verify(exactly = 0) { userConfiguredAnimator.cancel() }
    verify(exactly = 1) { positionAnimator.start() }
    verify(exactly = 0) { userConfiguredAnimator.start() }
  }

  @Test
  fun animateCustomAnimatorOptions() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    positionAnimator.animate(START_POINT, END_POINT, options = {})
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 1) { userConfiguredAnimator.start() }
    verify(exactly = 0) { positionAnimator.cancel() }
    verify(exactly = 0) { userConfiguredAnimator.cancel() }
    verify(exactly = 0) { positionAnimator.start() }
  }

  @Test
  fun animateInterruptDefaultWithCustom() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    positionAnimator.animate(START_POINT, END_POINT)
    positionAnimator.animate(
      START_POINT, END_POINT,
      options = { duration = 50 }
    )
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 1) { positionAnimator.cancel() }
    verify(exactly = 0) { userConfiguredAnimator.cancel() }
    verify(exactly = 1) { positionAnimator.start() }
    verify(exactly = 1) { userConfiguredAnimator.start() }
  }

  @Test
  fun cancelRunningWhenNotRunning() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    positionAnimator.cancelRunning()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 0) { positionAnimator.cancel() }
    verify(exactly = 0) { userConfiguredAnimator.cancel() }
  }

  @Test
  fun cancelRunningWhenRunning() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    positionAnimator.animate(START_POINT, END_POINT)
    positionAnimator.cancelRunning()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 1) { positionAnimator.cancel() }
    verify(exactly = 0) { userConfiguredAnimator.cancel() }
  }

  @Test
  fun cancelRunningWhenRunningCustom() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    positionAnimator.animate(
      START_POINT, END_POINT,
      options = { duration = 50 }
    )
    positionAnimator.cancelRunning()
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify(exactly = 0) { positionAnimator.cancel() }
    verify(exactly = 1) { userConfiguredAnimator.cancel() }
  }

  @Test
  fun updateOptions() {
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    positionAnimator.updateOptions {
      duration = 2000
    }
    positionAnimator.animate(START_POINT, END_POINT)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    verify { positionAnimator.start() }
    Assert.assertEquals(2000, positionAnimator.duration)
  }

  companion object {
    private val START_POINT = Point.fromLngLat(-122.4194, 37.7749)
    private val END_POINT = Point.fromLngLat(-77.0369, 38.9072)
  }
}