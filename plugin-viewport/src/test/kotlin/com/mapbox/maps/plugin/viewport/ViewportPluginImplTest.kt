package com.mapbox.maps.plugin.viewport

import android.os.Handler
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.Cancelable
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.ViewportStatusChangeReason
import com.mapbox.maps.plugin.viewport.state.ViewportState
import com.mapbox.maps.plugin.viewport.transition.ViewportTransition
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.unmockkStatic
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ViewportPluginImplTest {
  private val delegateProvider = mockk<MapDelegateProvider>(relaxed = true)
  private val mapPluginProviderDelegate = mockk<MapPluginProviderDelegate>(relaxed = true)
  private val cameraPlugin = mockk<CameraAnimationsPlugin>(relaxed = true)
  private val locationPlugin = mockk<LocationComponentPlugin>(relaxed = true)
  private val handler = mockk<Handler>(relaxed = true)
  private val completeSlot = slot<CompletionListener>()
  private val runnableSlot = slot<Runnable>()
  private val statusObserver = mockk<ViewportStatusObserver>(relaxed = true)
  private lateinit var viewportPlugin: ViewportPluginImpl

  @Before
  fun setup() {
    every { delegateProvider.mapPluginProviderDelegate } returns mapPluginProviderDelegate
    mockkStatic(CAMERA_ANIMATIONS_UTILS)
    mockkStatic(LOCATION_COMPONENT_UTILS)
    every { mapPluginProviderDelegate.location } returns locationPlugin
    every { mapPluginProviderDelegate.camera } returns cameraPlugin
    viewportPlugin = ViewportPluginImpl(handler)
    viewportPlugin.onDelegateProvider(delegateProvider)
    viewportPlugin.addStatusObserver(statusObserver)
  }

  @After
  fun cleanUp() {
    unmockkStatic(CAMERA_ANIMATIONS_UTILS)
    unmockkStatic(LOCATION_COMPONENT_UTILS)
    unmockkAll()
  }

  @Test
  fun testDelegateProvider() {
    viewportPlugin.defaultTransition
    verify { cameraPlugin.addCameraAnimationsLifecycleListener(any()) }
  }

  @Test
  fun testCleanUp() {
    viewportPlugin.cleanup()
    verify { cameraPlugin.removeCameraAnimationsLifecycleListener(any()) }
  }

  @Test
  fun testCreateFollowPuckViewportState() {
    val followPuckViewportStateOptions = FollowPuckViewportStateOptions.Builder().build()
    val followPuckState =
      viewportPlugin.makeFollowPuckViewportState(followPuckViewportStateOptions)
    assertEquals(followPuckState.options, followPuckViewportStateOptions)
  }

  @Test(expected = IllegalArgumentException::class)
  fun testCreateOverviewViewportStateEmptyGeometry() {
    val overviewViewportStateOptions = OverviewViewportStateOptions.Builder().build()
    viewportPlugin.makeOverviewViewportState(overviewViewportStateOptions)
  }

  @Test
  fun testCreateOverviewViewportState() {
    val overviewViewportStateOptions =
      OverviewViewportStateOptions.Builder().geometry(Point.fromLngLat(0.0, 0.0)).build()
    val overviewState = viewportPlugin.makeOverviewViewportState(overviewViewportStateOptions)
    assertEquals(overviewState.options, overviewViewportStateOptions)
  }

  @Test
  fun testCreateDefaultViewportTransition() {
    val defaultViewportTransitionOptions = DefaultViewportTransitionOptions.Builder().build()
    val defaultViewportTransition =
      viewportPlugin.makeDefaultViewportTransition(defaultViewportTransitionOptions)
    assertEquals(defaultViewportTransitionOptions, defaultViewportTransition.options)
  }

  @Test
  fun testCreateImmediateViewportTransition() {
    assertNotNull(viewportPlugin.makeImmediateViewportTransition())
  }

  @Test
  fun testTransitionToWithDefaultTransition() {
    val targetState = mockk<ViewportState>(relaxed = true)
    val transition = mockk<ViewportTransition>(relaxed = true)
    val transitionToCompletionListener = mockk<CompletionListener>(relaxed = true)
    viewportPlugin.defaultTransition = transition
    // enter transition status
    viewportPlugin.transitionTo(targetState, null, transitionToCompletionListener)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Idle,
          ViewportStatus.Transition(transition, targetState),
          ViewportStatusChangeReason.TRANSITION_STARTED
        )
      }
    }
    assertTrue(viewportPlugin.status is ViewportStatus.Transition)
    verify { transition.run(targetState, capture(completeSlot)) }
    // complete transition, enter state status
    completeSlot.captured.onComplete(true)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Transition(transition, targetState),
          ViewportStatus.State(targetState),
          ViewportStatusChangeReason.TRANSITION_SUCCEEDED
        )
      }
    }
    assertEquals(viewportPlugin.status, ViewportStatus.State(targetState))
    verifyOrder {
      targetState.startUpdatingCamera()
      transitionToCompletionListener.onComplete(true)
    }
  }

  @Test
  fun testTransitionToWithCustomisedTransition() {
    val targetState = mockk<ViewportState>(relaxed = true)
    val transition = mockk<ViewportTransition>(relaxed = true)
    val defaultTransition = mockk<ViewportTransition>(relaxed = true)
    val transitionToCompletionListener = mockk<CompletionListener>(relaxed = true)
    viewportPlugin.defaultTransition = defaultTransition
    // enter transition status
    viewportPlugin.transitionTo(targetState, transition, transitionToCompletionListener)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Idle,
          ViewportStatus.Transition(transition, targetState),
          ViewportStatusChangeReason.TRANSITION_STARTED
        )
      }
    }
    assertTrue(viewportPlugin.status is ViewportStatus.Transition)
    verify(exactly = 0) { defaultTransition.run(targetState, any()) }
    verify { transition.run(targetState, capture(completeSlot)) }
    // complete transition, enter state status
    completeSlot.captured.onComplete(true)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Transition(transition, targetState),
          ViewportStatus.State(targetState),
          ViewportStatusChangeReason.TRANSITION_SUCCEEDED
        )
      }
    }
    assertEquals(viewportPlugin.status, ViewportStatus.State(targetState))
    verifyOrder {
      targetState.startUpdatingCamera()
      transitionToCompletionListener.onComplete(true)
    }
  }

  @Test
  fun testTransitionToWithCanceledTransition() {
    val targetState = mockk<ViewportState>(relaxed = true)
    val transition = mockk<ViewportTransition>(relaxed = true)
    val transitionToCompletionListener = mockk<CompletionListener>(relaxed = true)
    viewportPlugin.defaultTransition = transition
    // enter transition status
    viewportPlugin.transitionTo(targetState, null, transitionToCompletionListener)
    verify { transition.run(targetState, capture(completeSlot)) }
    assertTrue(viewportPlugin.status is ViewportStatus.Transition)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Idle,
          ViewportStatus.Transition(transition, targetState),
          ViewportStatusChangeReason.TRANSITION_STARTED
        )
      }
    }
    // complete transition with canceled flag
    completeSlot.captured.onComplete(false)
    assertEquals(viewportPlugin.status, ViewportStatus.Idle)
    runHandlerAndTest {
      runHandlerAndTest {
        verify {
          statusObserver.onViewportStatusChanged(
            ViewportStatus.Transition(transition, targetState),
            ViewportStatus.Idle,
            ViewportStatusChangeReason.TRANSITION_FAILED
          )
        }
      }
    }
    verify { transitionToCompletionListener.onComplete(false) }
    verify(exactly = 0) { targetState.startUpdatingCamera() }
  }

  @Test
  fun testTransitionToSameStateWhileTransitionStatus() {
    val targetState = mockk<ViewportState>(relaxed = true)
    val transition = mockk<ViewportTransition>(relaxed = true)
    val transitionToCompletionListener = mockk<CompletionListener>(relaxed = true)
    viewportPlugin.defaultTransition = transition
    // enter transition status
    viewportPlugin.transitionTo(targetState)
    verify { transition.run(targetState, capture(completeSlot)) }
    assertTrue(viewportPlugin.status is ViewportStatus.Transition)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Idle,
          ViewportStatus.Transition(transition, targetState),
          ViewportStatusChangeReason.TRANSITION_STARTED
        )
      }
    }
    // start new transition to the same state
    viewportPlugin.transitionTo(targetState, null, transitionToCompletionListener)
    verify { transitionToCompletionListener.onComplete(false) }
  }

  @Test
  fun testTransitionToSameStateWhileStateStatus() {
    val targetState = mockk<ViewportState>(relaxed = true)
    val transition = mockk<ViewportTransition>(relaxed = true)
    val transitionToCompletionListener = mockk<CompletionListener>(relaxed = true)
    viewportPlugin.defaultTransition = transition
    // enter transition status
    viewportPlugin.transitionTo(targetState)
    verify { transition.run(targetState, capture(completeSlot)) }
    assertTrue(viewportPlugin.status is ViewportStatus.Transition)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Idle,
          ViewportStatus.Transition(transition, targetState),
          ViewportStatusChangeReason.TRANSITION_STARTED
        )
      }
    }
    // complete transition, enter state status
    completeSlot.captured.onComplete(true)
    assertEquals(viewportPlugin.status, ViewportStatus.State(targetState))
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Transition(transition, targetState),
          ViewportStatus.State(targetState),
          ViewportStatusChangeReason.TRANSITION_SUCCEEDED
        )
      }
    }
    verify(exactly = 1) { targetState.startUpdatingCamera() }
    // start new transition to the same state
    viewportPlugin.transitionTo(targetState, null, transitionToCompletionListener)
    verify { transitionToCompletionListener.onComplete(true) }
    // verify no more startUpdatingCamera is called
    verify(exactly = 1) { targetState.startUpdatingCamera() }
  }

  @Test
  fun testTransitionToDifferentStateWhileInState() {
    val targetState = mockk<ViewportState>(relaxed = true)
    val newState = mockk<ViewportState>(relaxed = true)
    val transition = mockk<ViewportTransition>(relaxed = true)
    val stateUpdatingCancelable = mockk<Cancelable>(relaxed = true)
    every { targetState.startUpdatingCamera() } returns stateUpdatingCancelable
    viewportPlugin.defaultTransition = transition
    // enter transition status
    viewportPlugin.transitionTo(targetState)
    assertTrue(viewportPlugin.status is ViewportStatus.Transition)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Idle,
          ViewportStatus.Transition(transition, targetState),
          ViewportStatusChangeReason.TRANSITION_STARTED
        )
      }
    }
    verify { transition.run(targetState, capture(completeSlot)) }
    // complete transition, enter state status
    completeSlot.captured.onComplete(true)
    assertEquals(viewportPlugin.status, ViewportStatus.State(targetState))
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Transition(transition, targetState),
          ViewportStatus.State(targetState),
          ViewportStatusChangeReason.TRANSITION_SUCCEEDED
        )
      }
    }
    // transition to new state
    viewportPlugin.transitionTo(newState)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.State(targetState),
          ViewportStatus.Transition(transition, newState),
          ViewportStatusChangeReason.TRANSITION_STARTED
        )
      }
    }
    verifyOrder {
      stateUpdatingCancelable.cancel()
      transition.run(newState, any())
    }
  }

  @Test
  fun testTransitionToDifferentStateWhileInTransition() {
    val targetState = mockk<ViewportState>(relaxed = true)
    val newState = mockk<ViewportState>(relaxed = true)
    val transition = mockk<ViewportTransition>(relaxed = true)
    val transitionUpdateCancelable = mockk<Cancelable>(relaxed = true)
    every { transition.run(any(), any()) } returns transitionUpdateCancelable
    viewportPlugin.defaultTransition = transition
    // enter transition status
    viewportPlugin.transitionTo(targetState)
    assertTrue(viewportPlugin.status is ViewportStatus.Transition)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Idle,
          ViewportStatus.Transition(transition, targetState),
          ViewportStatusChangeReason.TRANSITION_STARTED
        )
      }
    }
    verify { transition.run(targetState, capture(completeSlot)) }
    // transition to new state
    viewportPlugin.transitionTo(newState)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Transition(transition, targetState),
          ViewportStatus.Transition(transition, newState),
          ViewportStatusChangeReason.TRANSITION_STARTED
        )
      }
    }
    verifyOrder {
      transitionUpdateCancelable.cancel()
      transition.run(newState, any())
    }
  }

  @Test
  fun testIdle() {
    val targetState = mockk<ViewportState>(relaxed = true)
    val transition = mockk<ViewportTransition>(relaxed = true)
    val transitionUpdateCancelable = mockk<Cancelable>(relaxed = true)
    every { transition.run(any(), any()) } returns transitionUpdateCancelable
    viewportPlugin.defaultTransition = transition
    // enter transition status
    viewportPlugin.transitionTo(targetState)
    assertTrue(viewportPlugin.status is ViewportStatus.Transition)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Idle,
          ViewportStatus.Transition(transition, targetState),
          ViewportStatusChangeReason.TRANSITION_STARTED
        )
      }
    }
    verify { transition.run(targetState, capture(completeSlot)) }
    // transition to idle
    viewportPlugin.idle()
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Transition(transition, targetState),
          ViewportStatus.Idle,
          ViewportStatusChangeReason.IDLE_REQUESTED
        )
      }
    }
    verify { transitionUpdateCancelable.cancel() }
    assertEquals(viewportPlugin.status, ViewportStatus.Idle)
  }

  @Test
  fun testAddStatusObserver() {
    val testObserver = mockk<ViewportStatusObserver>(relaxed = true)
    val targetState = mockk<ViewportState>(relaxed = true)
    val defaultTransition = mockk<ViewportTransition>(relaxed = true)
    viewportPlugin.defaultTransition = defaultTransition
    viewportPlugin.addStatusObserver(testObserver)
    viewportPlugin.transitionTo(targetState)
    runHandlerAndTest {
      verify(exactly = 1) {
        testObserver.onViewportStatusChanged(
          ViewportStatus.Idle,
          ViewportStatus.Transition(defaultTransition, targetState),
          ViewportStatusChangeReason.TRANSITION_STARTED
        )
      }
    }
    viewportPlugin.removeStatusObserver(testObserver)
    viewportPlugin.idle()
    runHandlerAndTest {
      verify(exactly = 0) {
        testObserver.onViewportStatusChanged(
          ViewportStatus.Transition(defaultTransition, targetState),
          ViewportStatus.Idle,
          ViewportStatusChangeReason.IDLE_REQUESTED
        )
      }
    }
  }

  private fun runHandlerAndTest(test: () -> Unit) {
    verify { handler.post(capture(runnableSlot)) }
    runnableSlot.captured.run()
    test.invoke()
  }
}