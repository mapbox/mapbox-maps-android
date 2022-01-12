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
import com.mapbox.maps.plugin.viewport.data.FollowingViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
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
import org.junit.Assert.assertNull
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
    assert(viewportPlugin.status == ViewportStatus.State(null))
    verify { cameraPlugin.removeCameraAnimationsLifecycleListener(any()) }
  }

  @Test
  fun testCreateFollowingViewportState() {
    val followingViewportStateOptions = FollowingViewportStateOptions.Builder().build()
    val followingState =
      viewportPlugin.makeFollowingViewportState(followingViewportStateOptions)
    assertEquals(followingState.options, followingViewportStateOptions)
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
  fun testTransitionTo() {
    val targetState = mockk<ViewportState>(relaxed = true)
    val transition = mockk<ViewportTransition>(relaxed = true)
    val transitionToCompletionListener = mockk<CompletionListener>(relaxed = true)
    viewportPlugin.defaultTransition = transition
    // enter transition status
    viewportPlugin.transitionTo(targetState, transitionToCompletionListener)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.State(null),
          ViewportStatus.Transition(transition, null, targetState),
          VIEWPORT_STATUS_OBSERVER_REASON_PROGRAMMATIC
        )
      }
    }
    assertTrue(viewportPlugin.status is ViewportStatus.Transition)
    verify { transition.run(any(), targetState, capture(completeSlot)) }
    // complete transition, enter state status
    completeSlot.captured.onComplete(true)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Transition(transition, null, targetState),
          ViewportStatus.State(targetState),
          VIEWPORT_STATUS_OBSERVER_REASON_PROGRAMMATIC
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
    viewportPlugin.transitionTo(targetState, transitionToCompletionListener)
    verify { transition.run(any(), targetState, capture(completeSlot)) }
    assertTrue(viewportPlugin.status is ViewportStatus.Transition)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.State(null),
          ViewportStatus.Transition(transition, null, targetState),
          VIEWPORT_STATUS_OBSERVER_REASON_PROGRAMMATIC
        )
      }
    }
    // complete transition with canceled flag
    completeSlot.captured.onComplete(false)
    assertEquals(viewportPlugin.status, ViewportStatus.State(null))
    runHandlerAndTest {
      runHandlerAndTest {
        verify {
          statusObserver.onViewportStatusChanged(
            ViewportStatus.Transition(transition, null, targetState),
            ViewportStatus.State(null),
            VIEWPORT_STATUS_OBSERVER_REASON_PROGRAMMATIC
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
    verify { transition.run(any(), targetState, capture(completeSlot)) }
    assertTrue(viewportPlugin.status is ViewportStatus.Transition)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.State(null),
          ViewportStatus.Transition(transition, null, targetState),
          VIEWPORT_STATUS_OBSERVER_REASON_PROGRAMMATIC
        )
      }
    }
    // start new transition to the same state
    viewportPlugin.transitionTo(targetState, transitionToCompletionListener)
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
    verify { transition.run(any(), targetState, capture(completeSlot)) }
    assertTrue(viewportPlugin.status is ViewportStatus.Transition)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.State(null),
          ViewportStatus.Transition(transition, null, targetState),
          VIEWPORT_STATUS_OBSERVER_REASON_PROGRAMMATIC
        )
      }
    }
    // complete transition, enter state status
    completeSlot.captured.onComplete(true)
    assertEquals(viewportPlugin.status, ViewportStatus.State(targetState))
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Transition(transition, null, targetState),
          ViewportStatus.State(targetState),
          VIEWPORT_STATUS_OBSERVER_REASON_PROGRAMMATIC
        )
      }
    }
    verify(exactly = 1) { targetState.startUpdatingCamera() }
    // start new transition to the same state
    viewportPlugin.transitionTo(targetState, transitionToCompletionListener)
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
          ViewportStatus.State(null),
          ViewportStatus.Transition(transition, null, targetState),
          VIEWPORT_STATUS_OBSERVER_REASON_PROGRAMMATIC
        )
      }
    }
    verify { transition.run(any(), targetState, capture(completeSlot)) }
    // complete transition, enter state status
    completeSlot.captured.onComplete(true)
    assertEquals(viewportPlugin.status, ViewportStatus.State(targetState))
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Transition(transition, null, targetState),
          ViewportStatus.State(targetState),
          VIEWPORT_STATUS_OBSERVER_REASON_PROGRAMMATIC
        )
      }
    }
    // transition to new state
    viewportPlugin.transitionTo(newState)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.State(targetState),
          ViewportStatus.Transition(transition, targetState, newState),
          VIEWPORT_STATUS_OBSERVER_REASON_PROGRAMMATIC
        )
      }
    }
    verifyOrder {
      stateUpdatingCancelable.cancel()
      transition.run(any(), newState, any())
    }
  }

  @Test
  fun testTransitionToDifferentStateWhileInTransition() {
    val targetState = mockk<ViewportState>(relaxed = true)
    val newState = mockk<ViewportState>(relaxed = true)
    val transition = mockk<ViewportTransition>(relaxed = true)
    val transitionUpdateCancelable = mockk<Cancelable>(relaxed = true)
    every { transition.run(any(), any(), any()) } returns transitionUpdateCancelable
    viewportPlugin.defaultTransition = transition
    // enter transition status
    viewportPlugin.transitionTo(targetState)
    assertTrue(viewportPlugin.status is ViewportStatus.Transition)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.State(null),
          ViewportStatus.Transition(transition, null, targetState),
          VIEWPORT_STATUS_OBSERVER_REASON_PROGRAMMATIC
        )
      }
    }
    verify { transition.run(any(), targetState, capture(completeSlot)) }
    // transition to new state
    viewportPlugin.transitionTo(newState)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Transition(transition, null, targetState),
          ViewportStatus.Transition(transition, targetState, newState),
          VIEWPORT_STATUS_OBSERVER_REASON_PROGRAMMATIC
        )
      }
    }
    verifyOrder {
      transitionUpdateCancelable.cancel()
      transition.run(any(), newState, any())
    }
  }

  @Test
  fun testIdle() {
    val targetState = mockk<ViewportState>(relaxed = true)
    val transition = mockk<ViewportTransition>(relaxed = true)
    val transitionUpdateCancelable = mockk<Cancelable>(relaxed = true)
    every { transition.run(any(), any(), any()) } returns transitionUpdateCancelable
    viewportPlugin.defaultTransition = transition
    // enter transition status
    viewportPlugin.transitionTo(targetState)
    assertTrue(viewportPlugin.status is ViewportStatus.Transition)
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.State(null),
          ViewportStatus.Transition(transition, null, targetState),
          VIEWPORT_STATUS_OBSERVER_REASON_PROGRAMMATIC
        )
      }
    }
    verify { transition.run(any(), targetState, capture(completeSlot)) }
    // transition to idle
    viewportPlugin.idle()
    runHandlerAndTest {
      verify {
        statusObserver.onViewportStatusChanged(
          ViewportStatus.Transition(transition, null, targetState),
          ViewportStatus.State(null),
          VIEWPORT_STATUS_OBSERVER_REASON_PROGRAMMATIC
        )
      }
    }
    verify { transitionUpdateCancelable.cancel() }
    assertEquals(viewportPlugin.status, ViewportStatus.State(null))
  }

  @Test
  fun testSetGetAndRemoveTransition() {
    val targetState = mockk<ViewportState>(relaxed = true)
    val fromState = mockk<ViewportState>(relaxed = true)
    val transition = mockk<ViewportTransition>(relaxed = true)
    viewportPlugin.setTransition(transition, fromState, targetState)
    assertEquals(transition, viewportPlugin.getTransition(fromState, targetState))
    viewportPlugin.removeTransition(fromState, targetState)
    assertNull(viewportPlugin.getTransition(fromState, targetState))
  }

  @Test
  fun testSetTransitionForStateTransition() {
    val targetState = mockk<ViewportState>(relaxed = true)
    val fromState = mockk<ViewportState>(relaxed = true)
    val customisedTransition = mockk<ViewportTransition>(relaxed = true)
    val defaultTransition = mockk<ViewportTransition>(relaxed = true)
    viewportPlugin.defaultTransition = defaultTransition
    viewportPlugin.setTransition(customisedTransition, fromState, targetState)
    assertEquals(customisedTransition, viewportPlugin.getTransition(fromState, targetState))
    // enter transition status
    viewportPlugin.transitionTo(fromState)
    assertTrue(viewportPlugin.status is ViewportStatus.Transition)
    verify { defaultTransition.run(any(), fromState, capture(completeSlot)) }
    // complete transition, enter fromState
    completeSlot.captured.onComplete(true)
    assertEquals(viewportPlugin.status, ViewportStatus.State(fromState))
    // transition from fromState to targetState
    viewportPlugin.transitionTo(targetState)
    // verify using customised transition
    verify { customisedTransition.run(any(), any(), any()) }
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
          ViewportStatus.State(null),
          ViewportStatus.Transition(defaultTransition, null, targetState),
          VIEWPORT_STATUS_OBSERVER_REASON_PROGRAMMATIC
        )
      }
    }
    viewportPlugin.removeStatusObserver(testObserver)
    viewportPlugin.idle()
    runHandlerAndTest {
      verify(exactly = 0) {
        testObserver.onViewportStatusChanged(
          ViewportStatus.Transition(defaultTransition, null, targetState),
          ViewportStatus.State(null),
          VIEWPORT_STATUS_OBSERVER_REASON_PROGRAMMATIC
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