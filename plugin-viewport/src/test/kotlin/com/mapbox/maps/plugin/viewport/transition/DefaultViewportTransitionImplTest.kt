package com.mapbox.maps.plugin.viewport.transition

import android.animation.Animator
import android.animation.AnimatorSet
import com.mapbox.common.Cancelable
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.CameraAnimatorType
import com.mapbox.maps.plugin.animation.animator.CameraAnimator
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.viewport.CAMERA_ANIMATIONS_UTILS
import com.mapbox.maps.plugin.viewport.CompletionListener
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions
import com.mapbox.maps.plugin.viewport.state.ViewportState
import com.mapbox.maps.plugin.viewport.state.ViewportStateDataObserver
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DefaultViewportTransitionImplTest {
  private val delegateProvider = mockk<MapDelegateProvider>()
  private val mapPluginProviderDelegate = mockk<MapPluginProviderDelegate>()
  private val mapCameraManagerDelegate = mockk<MapCameraManagerDelegate>()
  private val cameraPlugin = mockk<CameraAnimationsPlugin>()
  private val transitionFactory = mockk<MapboxViewportTransitionFactory>()
  private val targetState = mockk<ViewportState>()
  private val completionListener = mockk<CompletionListener>()
  private val dataObserverSlot = slot<ViewportStateDataObserver>()
  private val cancelable = mockk<Cancelable>()
  private val cameraState =
    CameraState(Point.fromLngLat(0.0, 0.0), EdgeInsets(0.0, 0.0, 0.0, 0.0), 0.0, 0.0, 0.0)
  private val cameraOptions = cameraState.toCameraOptions()
  private val cachedAnchor = mockk<ScreenCoordinate>()
  private val animatorSet = mockk<AnimatorSet>()
  private val animator = mockk<CameraAnimator<*>>()
  private val animatorSetListenerSlot = slot<Animator.AnimatorListener>()
  private val animatorListenerSlot = slot<Animator.AnimatorListener>()
  private lateinit var defaultTransition: DefaultViewportTransitionImpl

  @Before
  fun setup() {
    every { delegateProvider.mapPluginProviderDelegate } returns mapPluginProviderDelegate
    every { delegateProvider.mapCameraManagerDelegate } returns mapCameraManagerDelegate
    every { mapCameraManagerDelegate.cameraState } returns cameraState
    every { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) } just runs
    every { cancelable.cancel() } just runs
    mockkStatic(CAMERA_ANIMATIONS_UTILS)
    every { mapPluginProviderDelegate.camera } returns cameraPlugin
    every { cameraPlugin.anchor } returns cachedAnchor
    every { cameraPlugin.anchor = any() } just runs
    every { cameraPlugin.registerAnimators(any()) } just runs
    every { cameraPlugin.unregisterAnimators(any()) } just runs

    every { transitionFactory.transitionFromHighZoomToLowZoom(any(), any()) } returns animatorSet
    every { transitionFactory.transitionFromLowZoomToHighZoom(any(), any()) } returns animatorSet
    every { animatorSet.addListener(any()) } just runs
    every { animatorSet.childAnimations } returns arrayListOf(animator)
    every { animatorSet.start() } just runs
    every { animatorSet.cancel() } just runs
    every { animator.addListener(any()) } just runs
    every { animator.type } returns CameraAnimatorType.BEARING
    every { animator.setObjectValues(0.0, 0.0) } just runs

    defaultTransition = DefaultViewportTransitionImpl(
      delegateProvider,
      options = DefaultViewportTransitionOptions.Builder().build(),
      transitionFactory
    )
  }

  @After
  fun cleanUp() {
    unmockkStatic(CAMERA_ANIMATIONS_UTILS)
    unmockkAll()
  }

  @Test
  fun testRun() {
    every { transitionFactory.transitionFromHighZoomToLowZoom(any(), any()) } returns animatorSet
    every { transitionFactory.transitionFromLowZoomToHighZoom(any(), any()) } returns animatorSet
    every { targetState.observeDataSource(any()) } returns cancelable
    every { completionListener.onComplete(any()) } just runs

    defaultTransition.run(targetState, completionListener)
    verify { targetState.observeDataSource(capture(dataObserverSlot)) }
    // verify the default state keep getting the data point and returned true
    assertTrue(dataObserverSlot.captured.onNewData(cameraOptions))

    verifySequence {
      animatorSet.addListener(capture(animatorSetListenerSlot))
      animatorSet.childAnimations
      animator.addListener(capture(animatorListenerSlot))
      cameraPlugin.anchor
      cameraPlugin.anchor = null
      animatorSet.childAnimations
      cameraPlugin.registerAnimators(animator)
      animatorSet.start()
    }

    // send second data point, should keep getting more data updates
    assertTrue(dataObserverSlot.captured.onNewData(cameraOptions))
    verifyOrder {
      animator.type
      animator.setObjectValues(0.0, 0.0)
    }
    verify(exactly = 1) { animator.setObjectValues(0.0, 0.0) }

    // finish the animator, should keep getting further data points and setCamera immediately
    animatorListenerSlot.captured.onAnimationEnd(animator)
    assertTrue(dataObserverSlot.captured.onNewData(cameraOptions))
    verify(exactly = 1) { animator.setObjectValues(0.0, 0.0) }
    verify(exactly = 1) { mapCameraManagerDelegate.setCamera(cameraOptions { bearing(cameraOptions.bearing) }) }

    // try to notify if animation is finished successfully
    animatorSetListenerSlot.captured.onAnimationEnd(mockk())
    // should stop observing data source and return false
    assertFalse(dataObserverSlot.captured.onNewData(cameraOptions))
    verify(exactly = 2) { mapCameraManagerDelegate.setCamera(cameraOptions { bearing(cameraOptions.bearing) }) }
    // verify cleanup
    verify { cameraPlugin.unregisterAnimators(animator) }
    verify { completionListener.onComplete(true) }
    verify { cameraPlugin.anchor = cachedAnchor }
  }

  @Test
  fun testRunCanceled() {
    every { transitionFactory.transitionFromHighZoomToLowZoom(any(), any()) } returns animatorSet
    every { transitionFactory.transitionFromLowZoomToHighZoom(any(), any()) } returns animatorSet
    every { targetState.observeDataSource(any()) } returns cancelable
    every { completionListener.onComplete(any()) } just runs

    val transitionCancelable = defaultTransition.run(targetState, completionListener)
    verify { targetState.observeDataSource(capture(dataObserverSlot)) }
    // verify the default state keep getting the data point and returned true
    assertTrue(dataObserverSlot.captured.onNewData(cameraOptions))

    verifySequence {
      animatorSet.addListener(capture(animatorSetListenerSlot))
      animatorSet.childAnimations
      animator.addListener(capture(animatorListenerSlot))
      cameraPlugin.anchor
      cameraPlugin.anchor = null
      animatorSet.childAnimations
      cameraPlugin.registerAnimators(animator)
      animatorSet.start()
    }
    // try to notify if animation is canceled
    transitionCancelable.cancel()
    verify { animatorSet.cancel() }
    verify { cancelable.cancel() }
    animatorSetListenerSlot.captured.onAnimationCancel(animator)
    animatorSetListenerSlot.captured.onAnimationEnd(animator)
    animatorListenerSlot.captured.onAnimationCancel(animator)
    animatorListenerSlot.captured.onAnimationEnd(animator)
    // should stop observing data source and return false
    assertFalse(dataObserverSlot.captured.onNewData(cameraOptions))
    verify(exactly = 0) { mapCameraManagerDelegate.setCamera(cameraOptions { bearing(cameraOptions.bearing) }) }
    verify { cameraPlugin.unregisterAnimators(animator) }
    verify { cameraPlugin.anchor = cachedAnchor }
  }

  @Test
  fun testRunAnimatorInterrupted() {
    every { transitionFactory.transitionFromHighZoomToLowZoom(any(), any()) } returns animatorSet
    every { transitionFactory.transitionFromLowZoomToHighZoom(any(), any()) } returns animatorSet
    every { targetState.observeDataSource(any()) } returns cancelable
    every { completionListener.onComplete(any()) } just runs

    val cancelable = defaultTransition.run(targetState, completionListener)
    verify { targetState.observeDataSource(capture(dataObserverSlot)) }
    // verify the default state keep getting the data point and returned true
    assertTrue(dataObserverSlot.captured.onNewData(cameraOptions))

    verifySequence {
      animatorSet.addListener(capture(animatorSetListenerSlot))
      animatorSet.childAnimations
      animator.addListener(capture(animatorListenerSlot))
      cameraPlugin.anchor
      cameraPlugin.anchor = null
      animatorSet.childAnimations
      cameraPlugin.registerAnimators(animator)
      animatorSet.start()
    }
    // try to notify if animation is canceled
    animatorSetListenerSlot.captured.onAnimationCancel(mockk())
    animatorSetListenerSlot.captured.onAnimationEnd(mockk())
    // should stop observing data source and return false
    assertFalse(dataObserverSlot.captured.onNewData(cameraOptions))
    verify { cameraPlugin.unregisterAnimators(animator) }
    verify { completionListener.onComplete(false) }
    verify { cameraPlugin.anchor = cachedAnchor }
  }
}