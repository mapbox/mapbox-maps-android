package com.mapbox.maps.plugin.viewport.transition

import android.animation.Animator
import android.animation.AnimatorSet
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraViewPoint
import com.mapbox.maps.CameraViewPointState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorType
import com.mapbox.maps.plugin.animation.CameraAnimatorsFactory
import com.mapbox.maps.plugin.animation.animator.CameraAnimator
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.animation.createCameraAnimationPlugin
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.viewport.CAMERA_ANIMATIONS_UTILS
import com.mapbox.maps.plugin.viewport.DEFAULT_STATE_ANIMATION_DURATION_MS
import com.mapbox.maps.plugin.viewport.DEFAULT_TRANSITION_MAX_DURATION_MS
import com.mapbox.maps.plugin.viewport.TRANSITION_UTILS
import com.mapbox.maps.plugin.viewport.ViewportPluginImpl.Companion.VIEWPORT_CAMERA_OWNER
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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

  private val animatorsFactory = mockk<CameraAnimatorsFactory>()

  private fun mockViewPointAnimators(): Pair<CameraAnimator<*>, CameraAnimator<*>> {
    val viewPointAnimator = mockk<CameraAnimator<*>>(relaxed = true) {
      every { type } returns CameraAnimatorType.VIEW_POINT
    }
    val paddingAnimator = mockk<CameraAnimator<*>>(relaxed = true) {
      every { type } returns CameraAnimatorType.PADDING
    }
    // getCameraAnimatorsFactory() is JvmSynthetic and cannot be mocked, so a real plugin carries the mock factory.
    val realPlugin = createCameraAnimationPlugin()
    realPlugin.javaClass.getMethod("setCameraAnimationsFactory", CameraAnimatorsFactory::class.java)
      .invoke(realPlugin, animatorsFactory)
    every { mapPluginProviderDelegate.camera } returns realPlugin
    transitionsFactory = MapboxViewportTransitionFactory(delegateProvider)
    every {
      animatorsFactory.getEaseTo(any(), any())
    } returns arrayOf(paddingAnimator, viewPointAnimator)
    return viewPointAnimator to paddingAnimator
  }

  private val viewPointTarget = CameraViewPointState.valueOf(CameraViewPoint(0.0, 0.8))

  @Test
  fun `transitions - view point animator is created from target view point`() {
    every { mapCameraDelegate.cameraState } returns mockk(relaxed = true)
    val (viewPointAnimator, paddingAnimator) = mockViewPointAnimators()
    val cameraOptions = CameraOptions.Builder().viewPoint(viewPointTarget).build()

    listOf(
      { transitionsFactory.transitionFromLowZoomToHighZoom(cameraOptions, DEFAULT_TRANSITION_MAX_DURATION_MS) },
      { transitionsFactory.transitionFromHighZoomToLowZoom(cameraOptions, DEFAULT_TRANSITION_MAX_DURATION_MS) },
      { transitionsFactory.transitionLinear(cameraOptions, DEFAULT_STATE_ANIMATION_DURATION_MS) },
    ).forEach { it() }

    val animatorsList = mutableListOf<List<Animator>>()
    verify(exactly = 3) { createAnimatorSet(capture(animatorsList)) }
    animatorsList.forEach {
      assertTrue(it.contains(viewPointAnimator))
      assertFalse(it.contains(paddingAnimator))
    }
    verify(exactly = 3) {
      animatorsFactory.getEaseTo(
        match {
          it.viewPoint!!.cameraViewPoint == viewPointTarget.cameraViewPoint
        },
        VIEWPORT_CAMERA_OWNER
      )
    }
  }

  @Test
  fun `transitions - view point animator is timed like the padding animator`() {
    every { mapCameraDelegate.cameraState } returns mockk(relaxed = true)
    val (viewPointAnimator, _) = mockViewPointAnimators()
    val cameraOptions = CameraOptions.Builder()
      .viewPoint(viewPointTarget)
      .padding(EdgeInsets(100.0, 0.0, 0.0, 0.0))
      .build()

    listOf(
      { transitionsFactory.transitionFromLowZoomToHighZoom(cameraOptions, DEFAULT_TRANSITION_MAX_DURATION_MS) },
      { transitionsFactory.transitionFromHighZoomToLowZoom(cameraOptions, DEFAULT_TRANSITION_MAX_DURATION_MS) },
      { transitionsFactory.transitionLinear(cameraOptions, DEFAULT_STATE_ANIMATION_DURATION_MS) },
    ).forEach { transition ->
      clearMocks(viewPointAnimator, answers = false)
      val animators = slot<List<Animator>>()
      every { createAnimatorSet(capture(animators)) } returns animatorSet
      transition()

      val padding = animators.captured.single { (it as? CameraAnimator<*>)?.type == CameraAnimatorType.PADDING }
      verify { viewPointAnimator.startDelay = padding.startDelay }
      verify { viewPointAnimator.duration = padding.duration }
      verify { viewPointAnimator.interpolator = padding.interpolator }
    }
  }

  @Test
  fun `transitions - view point animator is skipped without target view point`() {
    every { mapCameraDelegate.cameraState } returns mockk(relaxed = true)
    val (viewPointAnimator, _) = mockViewPointAnimators()

    transitionsFactory.transitionFromHighZoomToLowZoom(
      TEST_CAMERA_OPTIONS,
      DEFAULT_TRANSITION_MAX_DURATION_MS
    )

    val animators = slot<List<Animator>>()
    verify { createAnimatorSet(capture(animators)) }
    assertFalse(animators.captured.contains(viewPointAnimator))
    verify(exactly = 0) { animatorsFactory.getEaseTo(any(), any()) }
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