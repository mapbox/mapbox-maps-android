package com.mapbox.maps.plugin.viewport.transition

import android.animation.AnimatorSet
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.viewport.CAMERA_ANIMATIONS_UTILS
import com.mapbox.maps.plugin.viewport.DEFAULT_STATE_ANIMATION_DURATION_MS
import com.mapbox.maps.plugin.viewport.DEFAULT_TRANSITION_MAX_DURATION_MS
import com.mapbox.maps.plugin.viewport.TRANSITION_UTILS
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MapboxViewportTransitionFactoryTest {
  private val mapCameraDelegate = mockk<MapCameraManagerDelegate>()
  private val mapPluginProviderDelegate = mockk<MapPluginProviderDelegate>()
  private val cameraPlugin = mockk<CameraAnimationsPlugin>()
  private val delegateProvider = mockk<MapDelegateProvider>()
  private val animatorSet = mockk<AnimatorSet>()
  private val constrainedSet = mockk<AnimatorSet>()
  private lateinit var transitionsFactory: MapboxViewportTransitionFactory

  @Before
  fun setup() {
    mockkStatic(TRANSITION_UTILS)
    mockkStatic(CAMERA_ANIMATIONS_UTILS)
    every { delegateProvider.mapPluginProviderDelegate } returns mapPluginProviderDelegate
    every { mapPluginProviderDelegate.camera } returns cameraPlugin
    every { delegateProvider.mapCameraManagerDelegate } returns mapCameraDelegate
    every { createAnimatorSet(any()) } returns animatorSet
    every { animatorSet.constrainDurationTo(any()) } returns constrainedSet
    every { screenDistanceFromMapCenterToTarget(mapCameraDelegate, any(), any()) } returns 1000.0
    every {
      cameraPlugin.createCenterAnimator(
        options = any(),
        useShortestPath = any(),
        block = any()
      )
    } returns mockk()
    every { cameraPlugin.createBearingAnimator(any(), any(), any()) } returns mockk()
    every { cameraPlugin.createPitchAnimator(any(), any()) } returns mockk()
    every { cameraPlugin.createZoomAnimator(any(), any()) } returns mockk()
    every { cameraPlugin.createPaddingAnimator(any(), any()) } returns mockk()
    transitionsFactory = MapboxViewportTransitionFactory(delegateProvider)
  }

  @Test
  fun `transitionFromLowZoomToHighZoom - bearing and center is normalized`() {
    every { mapCameraDelegate.cameraState } returns mockk {
      every { bearing } returns 10.0
      every { center } returns Point.fromLngLat(-170.0, 0.0)
    }
    val cameraOptions = CameraOptions.Builder()
      .bearing(350.0)
      .center(Point.fromLngLat(170.0, 10.0))
      .build()

    val valueSlot = slot<CameraAnimatorOptions<Double>>()
    every {
      cameraPlugin.createBearingAnimator(capture(valueSlot), any(), any())
    } returns mockk()
    transitionsFactory.transitionFromLowZoomToHighZoom(
      cameraOptions,
      DEFAULT_TRANSITION_MAX_DURATION_MS
    )

    assertEquals(-10.0, valueSlot.captured.targets.last(), EPS)
    verify { normalizeBearing(10.0, 350.0) }
    verify {
      cameraPlugin.createCenterAnimator(
        options = any(),
        useShortestPath = true,
        block = any()
      )
    }
  }

  @Test
  fun `transitionFromHighZoomToLowZoom - bearing and center is normalized`() {
    every { mapCameraDelegate.cameraState } returns mockk {
      every { bearing } returns 10.0
      every { center } returns Point.fromLngLat(-170.0, 0.0)
    }

    val cameraOptions = CameraOptions.Builder()
      .bearing(350.0)
      .center(Point.fromLngLat(170.0, 10.0))
      .build()

    val valueSlot = slot<CameraAnimatorOptions<Double>>()
    every {
      cameraPlugin.createBearingAnimator(capture(valueSlot), any(), any())
    } returns mockk()
    transitionsFactory.transitionFromHighZoomToLowZoom(
      cameraOptions,
      DEFAULT_TRANSITION_MAX_DURATION_MS
    )

    assertEquals(-10.0, valueSlot.captured.targets.last(), EPS)
    verify { normalizeBearing(10.0, 350.0) }
    verify {
      cameraPlugin.createCenterAnimator(
        options = any(),
        useShortestPath = true,
        block = any()
      )
    }
  }

  @Test
  fun `transitionLinear - bearing and center is normalized`() {
    every { mapCameraDelegate.cameraState } returns mockk {
      every { bearing } returns 10.0
      every { center } returns Point.fromLngLat(-170.0, 0.0)
    }
    val cameraOptions = CameraOptions.Builder()
      .bearing(350.0)
      .center(Point.fromLngLat(170.0, 10.0))
      .build()

    val valueSlot = slot<CameraAnimatorOptions<Double>>()
    every {
      cameraPlugin.createBearingAnimator(capture(valueSlot), any(), any())
    } returns mockk()
    transitionsFactory.transitionLinear(cameraOptions, DEFAULT_STATE_ANIMATION_DURATION_MS)

    assertEquals(-10.0, valueSlot.captured.targets.last(), EPS)
    verify { normalizeBearing(10.0, 350.0) }
    verify {
      cameraPlugin.createCenterAnimator(
        options = any(),
        useShortestPath = true,
        block = any()
      )
    }
  }

  @Test
  fun `transitionFromLowZoomToHighZoom - duration constrained`() {
    every { mapCameraDelegate.cameraState } returns mockk {
      every { center } returns mockk()
      every { zoom } returns 0.0
      every { bearing } returns 0.0
    }

    val animator =
      transitionsFactory.transitionFromLowZoomToHighZoom(
        TEST_CAMERA_OPTIONS,
        DEFAULT_TRANSITION_MAX_DURATION_MS
      )

    verify { animatorSet.constrainDurationTo(DEFAULT_TRANSITION_MAX_DURATION_MS) }
    assertEquals(constrainedSet, animator)
  }

  @Test
  fun `transitionFromHighZoomToLowZoom - duration constrained`() {
    every { mapCameraDelegate.cameraState } returns mockk {
      every { center } returns mockk()
      every { zoom } returns 0.0
      every { bearing } returns 0.0
    }

    val animator =
      transitionsFactory.transitionFromHighZoomToLowZoom(
        TEST_CAMERA_OPTIONS,
        DEFAULT_TRANSITION_MAX_DURATION_MS
      )

    verify { animatorSet.constrainDurationTo(DEFAULT_TRANSITION_MAX_DURATION_MS) }
    assertEquals(constrainedSet, animator)
  }

  @After
  fun tearDown() {
    unmockkStatic(TRANSITION_UTILS)
    unmockkStatic(CAMERA_ANIMATIONS_UTILS)
  }

  private companion object {
    const val EPS = 0.0000000001
    val TEST_CAMERA_OPTIONS: CameraOptions = CameraOptions.Builder()
      .center(Point.fromLngLat(0.0, 0.0))
      .zoom(0.0)
      .bearing(0.0)
      .pitch(0.0)
      .padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
      .build()
  }
}