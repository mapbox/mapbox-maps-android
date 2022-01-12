package com.mapbox.maps.plugin.viewport.state

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import com.mapbox.geojson.Point
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.CAMERA_ANIMATIONS_UTILS
import com.mapbox.maps.plugin.viewport.DEFAULT_FOLLOW_VIEWPORT_STATE_PITCH
import com.mapbox.maps.plugin.viewport.DEFAULT_FOLLOW_VIEWPORT_STATE_ZOOM
import com.mapbox.maps.plugin.viewport.LOCATION_COMPONENT_UTILS
import com.mapbox.maps.plugin.viewport.data.FollowingViewportStateOptions
import com.mapbox.maps.plugin.viewport.transition.MapboxViewportTransitionFactory
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FollowingViewportStateImplTest {
  private val delegateProvider = mockk<MapDelegateProvider>(relaxed = true)
  private val mapPluginProviderDelegate = mockk<MapPluginProviderDelegate>(relaxed = true)
  private val cameraPlugin = mockk<CameraAnimationsPlugin>(relaxed = true)
  private val locationPlugin = mockk<LocationComponentPlugin>(relaxed = true)
  private val transitionFactory = mockk<MapboxViewportTransitionFactory>()
  private lateinit var followingState: FollowingViewportStateImpl

  @Before
  fun setup() {
    every { delegateProvider.mapPluginProviderDelegate } returns mapPluginProviderDelegate
    mockkStatic(CAMERA_ANIMATIONS_UTILS)
    mockkStatic(LOCATION_COMPONENT_UTILS)
    every { mapPluginProviderDelegate.location } returns locationPlugin
    every { mapPluginProviderDelegate.camera } returns cameraPlugin
    every { locationPlugin.addOnIndicatorBearingChangedListener(any()) } just runs
    every { locationPlugin.addOnIndicatorPositionChangedListener(any()) } just runs
    every { locationPlugin.enabled } returns true
    followingState = FollowingViewportStateImpl(
      delegateProvider,
      initialOptions = FollowingViewportStateOptions.Builder().build(),
      transitionFactory
    )
  }

  @After
  fun cleanUp() {
    unmockkStatic(CAMERA_ANIMATIONS_UTILS)
    unmockkStatic(LOCATION_COMPONENT_UTILS)
    unmockkAll()
  }

  @Test
  fun testObserveDataSourceForFirstDataPoint() {
    val indicatorBearingChangedListenerSlot = slot<OnIndicatorBearingChangedListener>()
    val indicatorPositionChangedListenerSlot = slot<OnIndicatorPositionChangedListener>()
    val dataObserver = mockk<ViewportStateDataObserver>(relaxed = true)
    val testBearing = 10.0
    val testCenter = Point.fromLngLat(0.0, 0.0)

    // stop observing after the first data point
    every { dataObserver.onNewData(any()) } returns false

    followingState.observeDataSource(dataObserver)
    verify {
      locationPlugin.addOnIndicatorBearingChangedListener(
        capture(
          indicatorBearingChangedListenerSlot
        )
      )
    }
    verify {
      locationPlugin.addOnIndicatorPositionChangedListener(
        capture(
          indicatorPositionChangedListenerSlot
        )
      )
    }
    indicatorBearingChangedListenerSlot.captured.onIndicatorBearingChanged(testBearing)
    indicatorPositionChangedListenerSlot.captured.onIndicatorPositionChanged(testCenter)
    verify(exactly = 1) {
      dataObserver.onNewData(
        cameraOptions {
          bearing(testBearing)
          center(testCenter)
          pitch(DEFAULT_FOLLOW_VIEWPORT_STATE_PITCH)
          zoom(DEFAULT_FOLLOW_VIEWPORT_STATE_ZOOM)
          padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
        }
      )
    }

    indicatorBearingChangedListenerSlot.captured.onIndicatorBearingChanged(testBearing)
    indicatorPositionChangedListenerSlot.captured.onIndicatorPositionChanged(testCenter)
    verify(exactly = 1) {
      dataObserver.onNewData(
        cameraOptions {
          bearing(testBearing)
          center(testCenter)
          pitch(DEFAULT_FOLLOW_VIEWPORT_STATE_PITCH)
          zoom(DEFAULT_FOLLOW_VIEWPORT_STATE_ZOOM)
          padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
        }
      )
    }
  }

  @Test
  fun testObserveDataSourceContinuously() {
    val indicatorBearingChangedListenerSlot = slot<OnIndicatorBearingChangedListener>()
    val indicatorPositionChangedListenerSlot = slot<OnIndicatorPositionChangedListener>()
    val dataObserver = mockk<ViewportStateDataObserver>(relaxed = true)
    val testBearing = 10.0
    val testCenter = Point.fromLngLat(0.0, 0.0)

    // keep observing after the first data point
    every { dataObserver.onNewData(any()) } returns true

    followingState.observeDataSource(dataObserver)
    verify {
      locationPlugin.addOnIndicatorBearingChangedListener(
        capture(
          indicatorBearingChangedListenerSlot
        )
      )
    }
    verify {
      locationPlugin.addOnIndicatorPositionChangedListener(
        capture(
          indicatorPositionChangedListenerSlot
        )
      )
    }
    indicatorBearingChangedListenerSlot.captured.onIndicatorBearingChanged(testBearing)
    indicatorPositionChangedListenerSlot.captured.onIndicatorPositionChanged(testCenter)
    verify(exactly = 1) {
      dataObserver.onNewData(
        cameraOptions {
          bearing(testBearing)
          center(testCenter)
          pitch(DEFAULT_FOLLOW_VIEWPORT_STATE_PITCH)
          zoom(DEFAULT_FOLLOW_VIEWPORT_STATE_ZOOM)
          padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
        }
      )
    }
    verify(exactly = 0) { locationPlugin.removeOnIndicatorBearingChangedListener(any()) }
    verify(exactly = 0) { locationPlugin.removeOnIndicatorPositionChangedListener(any()) }
    indicatorBearingChangedListenerSlot.captured.onIndicatorBearingChanged(testBearing)
    indicatorPositionChangedListenerSlot.captured.onIndicatorPositionChanged(testCenter)
    verify(exactly = 3) {
      dataObserver.onNewData(
        cameraOptions {
          bearing(testBearing)
          center(testCenter)
          pitch(DEFAULT_FOLLOW_VIEWPORT_STATE_PITCH)
          zoom(DEFAULT_FOLLOW_VIEWPORT_STATE_ZOOM)
          padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
        }
      )
    }
  }

  @Test
  fun testStartStopUpdatingCamera() {
    val indicatorBearingChangedListenerSlot = slot<OnIndicatorBearingChangedListener>()
    val indicatorPositionChangedListenerSlot = slot<OnIndicatorPositionChangedListener>()
    val testBearing = 10.0
    val testCenter = Point.fromLngLat(0.0, 0.0)
    val animatorSet = mockk<AnimatorSet>()
    val animator = mockk<ValueAnimator>()
    val animatorListenerSlot = slot<Animator.AnimatorListener>()

    every { transitionFactory.transitionLinear(any(), any()) } returns animatorSet
    every { animatorSet.addListener(any()) } just runs
    every { animatorSet.childAnimations } returns arrayListOf(animator)
    every { animatorSet.start() } just runs

    // test start updating camera
    followingState.startUpdatingCamera()
    verify {
      locationPlugin.addOnIndicatorBearingChangedListener(
        capture(
          indicatorBearingChangedListenerSlot
        )
      )
    }
    verify {
      locationPlugin.addOnIndicatorPositionChangedListener(
        capture(
          indicatorPositionChangedListenerSlot
        )
      )
    }
    indicatorBearingChangedListenerSlot.captured.onIndicatorBearingChanged(testBearing)
    indicatorPositionChangedListenerSlot.captured.onIndicatorPositionChanged(testCenter)
    verifySequence {
      animatorSet.addListener(capture(animatorListenerSlot))
      animatorSet.childAnimations
      cameraPlugin.registerAnimators(animator)
      animatorSet.start()
    }
    assertFalse(followingState.isFollowingStateRunning)
    animatorListenerSlot.captured.onAnimationEnd(mockk())
    assertTrue(followingState.isFollowingStateRunning)

    // test stop updating camera
    followingState.stopUpdatingCamera()
    verify {
      locationPlugin.removeOnIndicatorBearingChangedListener(
        indicatorBearingChangedListenerSlot.captured
      )
    }
    verify {
      locationPlugin.removeOnIndicatorPositionChangedListener(
        indicatorPositionChangedListenerSlot.captured
      )
    }
    assertFalse(followingState.isFollowingStateRunning)
    verify { cameraPlugin.unregisterAnimators(animator) }
  }
}