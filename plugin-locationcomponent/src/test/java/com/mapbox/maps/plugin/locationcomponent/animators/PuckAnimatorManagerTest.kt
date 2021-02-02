package com.mapbox.maps.plugin.locationcomponent.animators

import android.animation.ValueAnimator
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.locationcomponent.LocationLayerRenderer
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class PuckAnimatorManagerTest {

  private lateinit var puckAnimatorManager: PuckAnimatorManager

  private val bearingAnimator: PuckBearingAnimator = mockk(relaxed = true)
  private val positionAnimator: PuckPositionAnimator = mockk(relaxed = true)
  private val pulsingAnimator: PuckPulsingAnimator = mockk(relaxed = true)

  @Before
  fun setUp() {
    puckAnimatorManager = PuckAnimatorManager(
      mockk(),
      mockk(),
      bearingAnimator,
      positionAnimator,
      pulsingAnimator
    )
  }

  @Test
  fun setLocationLayerRenderer() {
    val locationLayerRenderer = mockk<LocationLayerRenderer>()
    puckAnimatorManager.setLocationLayerRenderer(locationLayerRenderer)
    verify {
      bearingAnimator.setLocationLayerRenderer(locationLayerRenderer)
      positionAnimator.setLocationLayerRenderer(locationLayerRenderer)
      pulsingAnimator.setLocationLayerRenderer(locationLayerRenderer)
    }
  }

  @Test
  fun onStartOne() {
    every { pulsingAnimator.enabled } returns true
    puckAnimatorManager.onStart()
    verify(exactly = 1) { pulsingAnimator.animateInfinite() }
  }

  @Test
  fun onStartTwo() {
    every { pulsingAnimator.enabled } returns false
    puckAnimatorManager.onStart()
    verify(exactly = 0) { pulsingAnimator.animateInfinite() }
  }

  @Test
  fun onStop() {
    puckAnimatorManager.onStop()
    verify {
      bearingAnimator.cancelRunning()
      positionAnimator.cancelRunning()
      pulsingAnimator.cancelRunning()
    }
  }

  @Test
  fun animateBearing() {
    val options: (ValueAnimator.() -> Unit) = {}
    puckAnimatorManager.animateBearing(0.0, 10.0, options = options)
    verify {
      bearingAnimator.animate(0.0, 10.0, options = options)
    }
  }

  @Test
  fun animatePosition() {
    val options: (ValueAnimator.() -> Unit) = {}
    puckAnimatorManager.animatePosition(START_POINT, END_POINT, options = options)
    verify {
      positionAnimator.animate(START_POINT, END_POINT, options = options)
    }
  }

  @Test
  fun applyPulsingAnimationSettingsOne() {
    val settings = mockk<LocationComponentSettings>(relaxed = true)
    every { settings.pulsingEnabled } returns true
    every { settings.pulsingMaxRadius } returns 20.0f
    puckAnimatorManager.applyPulsingAnimationSettings(settings)
    verify {
      pulsingAnimator.maxRadius = 20.0
      pulsingAnimator.animateInfinite()
    }
  }

  @Test
  fun applyPulsingAnimationSettingsTwo() {
    val settings = mockk<LocationComponentSettings>(relaxed = true)
    every { settings.pulsingEnabled } returns false
    every { settings.pulsingMaxRadius } returns 20.0f
    puckAnimatorManager.applyPulsingAnimationSettings(settings)
    verify {
      pulsingAnimator.maxRadius = 20.0
      pulsingAnimator.cancelRunning()
    }
  }

  @Test
  fun updateBearingAnimator() {
    val options: (ValueAnimator.() -> Unit) = {}
    puckAnimatorManager.updateBearingAnimator(options)
    verify { bearingAnimator.updateOptions(options) }
  }

  @Test
  fun updatePositionAnimator() {
    val options: (ValueAnimator.() -> Unit) = {}
    puckAnimatorManager.updatePositionAnimator(options)
    verify { positionAnimator.updateOptions(options) }
  }

  companion object {
    private val START_POINT = Point.fromLngLat(-122.4194, 37.7749)
    private val END_POINT = Point.fromLngLat(-77.0369, 38.9072)
  }
}