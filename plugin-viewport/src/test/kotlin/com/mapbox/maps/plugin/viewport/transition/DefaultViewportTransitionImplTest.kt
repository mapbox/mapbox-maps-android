package com.mapbox.maps.plugin.viewport.transition

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.Cancelable
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.viewport.CAMERA_ANIMATIONS_UTILS
import com.mapbox.maps.plugin.viewport.CompletionListener
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions
import com.mapbox.maps.plugin.viewport.state.ViewportState
import com.mapbox.maps.plugin.viewport.state.ViewportStateDataObserver
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.unmockkStatic
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DefaultViewportTransitionImplTest {
  private val delegateProvider = mockk<MapDelegateProvider>(relaxed = true)
  private val mapPluginProviderDelegate = mockk<MapPluginProviderDelegate>(relaxed = true)
  private val cameraPlugin = mockk<CameraAnimationsPlugin>(relaxed = true)
  private val transitionFactory = mockk<MapboxViewportTransitionFactory>()
  private val previousState = mockk<ViewportState>()
  private val targetState = mockk<ViewportState>()
  private val completionListener = mockk<CompletionListener>()
  private val dataObserverSlot = slot<ViewportStateDataObserver>()
  private val cancelable = mockk<Cancelable>()
  private val cameraOptions = mockk<CameraOptions>(relaxed = true)
  private lateinit var defaultTransition: DefaultViewportTransitionImpl

  @Before
  fun setup() {
    every { delegateProvider.mapPluginProviderDelegate } returns mapPluginProviderDelegate
    every { cancelable.cancel() } just runs
    mockkStatic(CAMERA_ANIMATIONS_UTILS)
    every { mapPluginProviderDelegate.camera } returns cameraPlugin
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
    val animatorSet = mockk<AnimatorSet>()
    val animator = mockk<ValueAnimator>()
    val animatorListenerSlot = slot<Animator.AnimatorListener>()

    every { transitionFactory.transitionFromHighZoomToLowZoom(any(), any()) } returns animatorSet
    every { transitionFactory.transitionFromLowZoomToHighZoom(any(), any()) } returns animatorSet
    every { animatorSet.addListener(any()) } just runs
    every { animatorSet.childAnimations } returns arrayListOf(animator)
    every { animatorSet.start() } just runs
    every { targetState.observeDataSource(any()) } returns cancelable
    every { completionListener.onComplete(any()) } just runs

    defaultTransition.run(previousState, targetState, completionListener)
    verify { targetState.observeDataSource(capture(dataObserverSlot)) }
    // verify the default state only get the first data point and returned false
    assertFalse(dataObserverSlot.captured.onNewData(cameraOptions))

    verifySequence {
      animatorSet.addListener(capture(animatorListenerSlot))
      animatorSet.childAnimations
      cameraPlugin.registerAnimators(animator)
      animatorSet.start()
    }
    // try to notify if animation is finished successfully
    animatorListenerSlot.captured.onAnimationEnd(mockk())
    verify { cameraPlugin.unregisterAnimators(animator) }
    verify { completionListener.onComplete(true) }
  }

  @Test
  fun testRunCanceled() {
    val animatorSet = mockk<AnimatorSet>()
    val animator = mockk<ValueAnimator>()
    val animatorListenerSlot = slot<Animator.AnimatorListener>()

    every { transitionFactory.transitionFromHighZoomToLowZoom(any(), any()) } returns animatorSet
    every { transitionFactory.transitionFromLowZoomToHighZoom(any(), any()) } returns animatorSet
    every { animatorSet.addListener(any()) } just runs
    every { animatorSet.childAnimations } returns arrayListOf(animator)
    every { animatorSet.start() } just runs
    every { animatorSet.cancel() } just runs
    every { targetState.observeDataSource(any()) } returns cancelable
    every { completionListener.onComplete(any()) } just runs

    val transitionCancelable = defaultTransition.run(previousState, targetState, completionListener)
    verify { targetState.observeDataSource(capture(dataObserverSlot)) }
    // verify the default state only get the first data point and returned false
    assertFalse(dataObserverSlot.captured.onNewData(cameraOptions))

    verifySequence {
      animatorSet.addListener(capture(animatorListenerSlot))
      animatorSet.childAnimations
      cameraPlugin.registerAnimators(animator)
      animatorSet.start()
    }
    // try to notify if animation is canceled
    transitionCancelable.cancel()
    verify { animatorSet.cancel() }
    verify { cameraPlugin.unregisterAnimators(animator) }
  }

  @Test
  fun testRunAnimatorInterrupted() {
    val animatorSet = mockk<AnimatorSet>()
    val animator = mockk<ValueAnimator>()
    val animatorListenerSlot = slot<Animator.AnimatorListener>()

    every { transitionFactory.transitionFromHighZoomToLowZoom(any(), any()) } returns animatorSet
    every { transitionFactory.transitionFromLowZoomToHighZoom(any(), any()) } returns animatorSet
    every { animatorSet.addListener(any()) } just runs
    every { animatorSet.childAnimations } returns arrayListOf(animator)
    every { animatorSet.start() } just runs
    every { animatorSet.cancel() } just runs
    every { targetState.observeDataSource(any()) } returns cancelable
    every { completionListener.onComplete(any()) } just runs

    val cancelable = defaultTransition.run(previousState, targetState, completionListener)
    verify { targetState.observeDataSource(capture(dataObserverSlot)) }
    // verify the default state only get the first data point and returned false
    assertFalse(dataObserverSlot.captured.onNewData(cameraOptions))

    verifySequence {
      animatorSet.addListener(capture(animatorListenerSlot))
      animatorSet.childAnimations
      cameraPlugin.registerAnimators(animator)
      animatorSet.start()
    }
    // try to notify if animation is canceled
    animatorListenerSlot.captured.onAnimationCancel(mockk())
    animatorListenerSlot.captured.onAnimationEnd(mockk())
    verify { cameraPlugin.unregisterAnimators(animator) }
    verify { completionListener.onComplete(false) }
  }
}