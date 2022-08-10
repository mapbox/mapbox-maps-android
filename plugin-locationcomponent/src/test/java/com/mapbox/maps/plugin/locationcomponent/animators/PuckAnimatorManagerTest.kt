package com.mapbox.maps.plugin.locationcomponent.animators

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Looper
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.locationcomponent.LocationLayerRenderer
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings2
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class PuckAnimatorManagerTest {

  private lateinit var puckAnimatorManager: PuckAnimatorManager

  private val bearingAnimator: PuckBearingAnimator = PuckBearingAnimator(mockk(relaxUnitFun = true))
  private val positionAnimator: PuckPositionAnimator =
    PuckPositionAnimator(mockk(relaxUnitFun = true))
  private val accuracyRadiusAnimator: PuckAccuracyRadiusAnimator =
    PuckAccuracyRadiusAnimator(mockk(relaxUnitFun = true))
  private val pulsingAnimator: PuckPulsingAnimator = mockk(relaxed = true)
  private val pixelRatio: Float = 1.0f

  @Before
  fun setUp() {
    puckAnimatorManager = PuckAnimatorManager(
      mockk(),
      mockk(),
      mockk(),
      bearingAnimator,
      positionAnimator,
      pulsingAnimator,
      accuracyRadiusAnimator,
      pixelRatio
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
      accuracyRadiusAnimator.setLocationLayerRenderer(locationLayerRenderer)
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
      accuracyRadiusAnimator.cancelRunning()
    }
  }

  @Test
  fun setUpdateListeners() {
    val positionUpdateListener: ((Point) -> Unit) = {}
    val bearingUpdateListener: ((Double) -> Unit) = {}
    val accuracyRadiusUpdateListener: ((Double) -> Unit) = {}
    puckAnimatorManager.setUpdateListeners(
      positionUpdateListener,
      bearingUpdateListener,
      accuracyRadiusUpdateListener
    )
    assertEquals(positionUpdateListener, positionAnimator.updateListener)
    assertEquals(bearingUpdateListener, bearingAnimator.updateListener)
    assertEquals(accuracyRadiusUpdateListener, accuracyRadiusAnimator.updateListener)
  }

  @Test
  fun animateBearing() {
    val options: (ValueAnimator.() -> Unit) = {}
    var counter = 0
    var animatedValue = 0.0
    val updateListener: ((Double) -> Unit) = {
      counter++
      animatedValue = it
    }
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    puckAnimatorManager.setUpdateListeners({}, updateListener, {})
    puckAnimatorManager.animateBearing(0.0, 10.0, options = options)
    verify {
      bearingAnimator.animate(0.0, 10.0, options = options)
    }
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    MatcherAssert.assertThat(counter, Matchers.greaterThan(0))
    assertEquals(10.0, animatedValue, 0.0001)
  }

  @Test
  fun animatePosition() {
    val options: (ValueAnimator.() -> Unit) = {}
    var counter = 0
    var animatedValue = START_POINT
    val updateListener: ((Point) -> Unit) = {
      counter++
      animatedValue = it
    }
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    puckAnimatorManager.setUpdateListeners(updateListener, {}, {})
    puckAnimatorManager.animatePosition(START_POINT, END_POINT, options = options)
    verify {
      positionAnimator.animate(START_POINT, END_POINT, options = options)
    }
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    MatcherAssert.assertThat(counter, Matchers.greaterThan(0))
    Assert.assertEquals(END_POINT, animatedValue)
  }

  @Test
  fun applyAccuracyRadiusSettingsOne() {
    val settings = mockk<LocationComponentSettings2>(relaxed = true)
    every { settings.accuracyRingColor } returns Color.BLUE
    every { settings.showAccuracyRing } returns true
    puckAnimatorManager.applySettings2(settings)
    assertTrue(accuracyRadiusAnimator.enabled)
    assertEquals(Color.BLUE, accuracyRadiusAnimator.accuracyCircleColor)
  }

  @Test
  fun applyAccuracyRadiusSettingsTwo() {
    val settings = mockk<LocationComponentSettings2>(relaxed = true)
    every { settings.accuracyRingColor } returns Color.GREEN
    every { settings.showAccuracyRing } returns false
    puckAnimatorManager.applySettings2(settings)
    assertFalse(accuracyRadiusAnimator.enabled)
    assertEquals(Color.GREEN, accuracyRadiusAnimator.accuracyCircleColor)
  }

  @Test
  fun applyAnimationSettingsOne() {
    val settings = mockk<LocationComponentSettings>(relaxed = true)
    every { settings.pulsingEnabled } returns true
    every { settings.pulsingMaxRadius } returns 20.0f

    puckAnimatorManager.applyPulsingAnimationSettings(settings)
    verify {
      pulsingAnimator.enabled = true
      pulsingAnimator.maxRadius = 20.0
      pulsingAnimator.animateInfinite()
    }
  }

  @Test
  fun applyAnimationSettingsTwo() {
    val settings = mockk<LocationComponentSettings>(relaxed = true)
    every { settings.pulsingEnabled } returns false
    every { settings.pulsingMaxRadius } returns 20.0f
    puckAnimatorManager.applyPulsingAnimationSettings(settings)
    verify {
      pulsingAnimator.enabled = false
      pulsingAnimator.maxRadius = 20.0
      pulsingAnimator.cancelRunning()
    }
  }

  @Test
  fun updateBearingAnimator() {
    val options: (ValueAnimator.() -> Unit) = {
      duration = 5000
    }
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    puckAnimatorManager.updateBearingAnimator(options)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    Assert.assertEquals(5000, bearingAnimator.duration)
  }

  @Test
  fun updatePositionAnimator() {
    val options: (ValueAnimator.() -> Unit) = {
      duration = 5000
    }
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    puckAnimatorManager.updatePositionAnimator(options)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    Assert.assertEquals(5000, positionAnimator.duration)
  }

  @Test
  fun updateAccuracyRadiusAnimator() {
    val options: (ValueAnimator.() -> Unit) = {
      duration = 5000
    }
    Shadows.shadowOf(Looper.getMainLooper()).pause()
    puckAnimatorManager.updateAccuracyRadiusAnimator(options)
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    assertEquals(5000, accuracyRadiusAnimator.duration)
  }

  @Test
  fun testUpdatePulsingPuckRadius() {
    val updatedRadius = 50.0
    val settings = mockk<LocationComponentSettings>(relaxed = true)
    every { settings.pulsingEnabled } returns true
    every { settings.pulsingMaxRadius } returns updatedRadius.toFloat()

    puckAnimatorManager.updatePulsingRadius(updatedRadius, settings)
    verify {
      pulsingAnimator.enabled = true
      pulsingAnimator.maxRadius = updatedRadius
      pulsingAnimator.animateInfinite()
    }
  }

  @Test
  fun testUpdatePulsingPuckRadiusFalse() {
    val updatedRadius = 50.0
    val settings = mockk<LocationComponentSettings>(relaxed = true)
    every { settings.pulsingEnabled } returns false
    every { settings.pulsingMaxRadius } returns updatedRadius.toFloat()

    puckAnimatorManager.updatePulsingRadius(updatedRadius, settings)
    verify {
      pulsingAnimator.enabled = false
      pulsingAnimator.cancelRunning()
    }
    verify(exactly = 0) { pulsingAnimator.maxRadius = updatedRadius }
  }

  companion object {
    private val START_POINT = Point.fromLngLat(-122.4194, 37.7749)
    private val END_POINT = Point.fromLngLat(-77.0369, 38.9072)
  }
}