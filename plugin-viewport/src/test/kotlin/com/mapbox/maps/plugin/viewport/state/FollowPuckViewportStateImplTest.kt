package com.mapbox.maps.plugin.viewport.state

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import com.mapbox.geojson.Point
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
import com.mapbox.maps.plugin.viewport.DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_PITCH
import com.mapbox.maps.plugin.viewport.DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_ZOOM
import com.mapbox.maps.plugin.viewport.LOCATION_COMPONENT_UTILS
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateBearing
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.transition.MapboxViewportTransitionFactory
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FollowPuckViewportStateImplTest {
  private val delegateProvider = mockk<MapDelegateProvider>()
  private val mapPluginProviderDelegate = mockk<MapPluginProviderDelegate>()
  private val cameraPlugin = mockk<CameraAnimationsPlugin>()
  private val locationPlugin = mockk<LocationComponentPlugin>()
  private val transitionFactory = mockk<MapboxViewportTransitionFactory>()
  private lateinit var followingState: FollowPuckViewportStateImpl

  @Before
  fun setup() {
    every { delegateProvider.mapPluginProviderDelegate } returns mapPluginProviderDelegate
    mockkStatic(CAMERA_ANIMATIONS_UTILS)
    mockkStatic(LOCATION_COMPONENT_UTILS)
    every { mapPluginProviderDelegate.location } returns locationPlugin
    every { mapPluginProviderDelegate.camera } returns cameraPlugin
    every { locationPlugin.addOnIndicatorBearingChangedListener(any()) } just runs
    every { locationPlugin.addOnIndicatorPositionChangedListener(any()) } just runs
    every { locationPlugin.removeOnIndicatorPositionChangedListener(any()) } just runs
    every { locationPlugin.removeOnIndicatorBearingChangedListener(any()) } just runs
    every { locationPlugin.enabled } returns true
    followingState = FollowPuckViewportStateImpl(
      delegateProvider,
      initialOptions = FollowPuckViewportStateOptions.Builder().build(),
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
    val dataObserver = mockk<ViewportStateDataObserver>()
    val testBearing = 10.0
    var testBearingSent = false
    val testCenter = Point.fromLngLat(0.0, 0.0)

    // stop observing after the testBearing value has been sent
    every { dataObserver.onNewData(any()) } answers { !testBearingSent }

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
    indicatorPositionChangedListenerSlot.captured.onIndicatorPositionChanged(testCenter)
    // first new data can already be fired when the first position is given. For example, we might
    // have a static location with no course.
    verify(exactly = 1) {
      dataObserver.onNewData(
        cameraOptions {
          center(testCenter)
          pitch(DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_PITCH)
          zoom(DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_ZOOM)
        }
      )
    }
    testBearingSent = true
    indicatorBearingChangedListenerSlot.captured.onIndicatorBearingChanged(testBearing)
    verify(exactly = 1) {
      dataObserver.onNewData(
        cameraOptions {
          bearing(testBearing)
          center(testCenter)
          pitch(DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_PITCH)
          zoom(DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_ZOOM)
        }
      )
    }

    // more indicator updates after the testBearing update shouldn't be notified.
    indicatorBearingChangedListenerSlot.captured.onIndicatorBearingChanged(testBearing)
    indicatorPositionChangedListenerSlot.captured.onIndicatorPositionChanged(testCenter)
    verify(exactly = 2) {
      dataObserver.onNewData(any())
    }
  }

  @Test
  fun testObserveDataSourceForFirstDataPointWithConstantBearing() {
    val indicatorBearingChangedListenerSlot = slot<OnIndicatorBearingChangedListener>()
    val indicatorPositionChangedListenerSlot = slot<OnIndicatorPositionChangedListener>()
    val dataObserver = mockk<ViewportStateDataObserver>()
    val testBearing = 10.0
    val testCenter = Point.fromLngLat(0.0, 0.0)
    val constantBearing = 45.0

    // set the bearing to be constant
    followingState.apply {
      options =
        options.toBuilder().bearing(FollowPuckViewportStateBearing.Constant(constantBearing))
          .build()
    }
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
    indicatorPositionChangedListenerSlot.captured.onIndicatorPositionChanged(testCenter)
    // first new data be fired when the first position is available, when the bearing is constant.
    verify(exactly = 1) {
      dataObserver.onNewData(
        cameraOptions {
          center(testCenter)
          bearing(constantBearing)
          zoom(DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_ZOOM)
          pitch(DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_PITCH)
        }
      )
    }
    // new bearing updates from location component shouldn't trigger new data.
    indicatorBearingChangedListenerSlot.captured.onIndicatorBearingChanged(testBearing)
    verify(exactly = 1) { dataObserver.onNewData(any()) }

    // more indicator updates after the initial update shouldn't be notified.
    indicatorBearingChangedListenerSlot.captured.onIndicatorBearingChanged(testBearing)
    indicatorPositionChangedListenerSlot.captured.onIndicatorPositionChanged(testCenter)
    verify(exactly = 1) {
      dataObserver.onNewData(any())
    }
  }

  @Test
  fun testObserveDataSourceForFirstDataPointWithNullBearingOption() {
    val indicatorBearingChangedListenerSlot = slot<OnIndicatorBearingChangedListener>()
    val indicatorPositionChangedListenerSlot = slot<OnIndicatorPositionChangedListener>()
    val dataObserver = mockk<ViewportStateDataObserver>()
    val testBearing = 10.0
    val testCenter = Point.fromLngLat(0.0, 0.0)

    // set the bearing to be constant
    followingState.apply {
      options =
        options.toBuilder().bearing(null)
          .build()
    }
    every { dataObserver.onNewData(any()) } returns true

    followingState.observeDataSource(dataObserver)
    verify {
      locationPlugin.addOnIndicatorBearingChangedListener(
        capture(indicatorBearingChangedListenerSlot)
      )
    }
    verify {
      locationPlugin.addOnIndicatorPositionChangedListener(
        capture(indicatorPositionChangedListenerSlot)
      )
    }
    indicatorPositionChangedListenerSlot.captured.onIndicatorPositionChanged(testCenter)
    // new bearing updates from location component shouldn't trigger new data.
    indicatorBearingChangedListenerSlot.captured.onIndicatorBearingChanged(testBearing)
    // first new data be fired when the first position is available, when the bearing is constant.
    verify(exactly = 1) {
      dataObserver.onNewData(
        cameraOptions {
          center(testCenter)
          bearing(null)
          zoom(DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_ZOOM)
          pitch(DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_PITCH)
        }
      )
    }
    verify(exactly = 1) {
      dataObserver.onNewData(any())
    }
  }

  @Test
  fun testObserveDataSourceWithOnlyExistingLocation() {
    val indicatorBearingChangedListenerSlot = slot<OnIndicatorBearingChangedListener>()
    val indicatorPositionChangedListenerSlot = slot<OnIndicatorPositionChangedListener>()
    val dataObserver = mockk<ViewportStateDataObserver>()
    val testBearing = 10.0
    val testCenter = Point.fromLngLat(0.0, 0.0)

    // keep observing after the first data point
    every { dataObserver.onNewData(any()) } returns true

    // immediately emmit location update when listener is added.
    every {
      locationPlugin.addOnIndicatorBearingChangedListener(
        capture(
          indicatorBearingChangedListenerSlot
        )
      )
    } answers {
      indicatorBearingChangedListenerSlot.captured.onIndicatorBearingChanged(testBearing)
    }
    every {
      locationPlugin.addOnIndicatorPositionChangedListener(
        capture(
          indicatorPositionChangedListenerSlot
        )
      )
    } answers {
      indicatorPositionChangedListenerSlot.captured.onIndicatorPositionChanged(testCenter)
    }

    followingState.observeDataSource(dataObserver)
    verify {
      locationPlugin.addOnIndicatorBearingChangedListener(any())
    }
    verify {
      locationPlugin.addOnIndicatorPositionChangedListener(any())
    }
    verify(exactly = 1) {
      dataObserver.onNewData(
        cameraOptions {
          bearing(testBearing)
          center(testCenter)
          pitch(DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_PITCH)
          zoom(DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_ZOOM)
        }
      )
    }

    // observing the data source again should only emit exactly one data point.
    followingState.observeDataSource(dataObserver)
    verify(exactly = 2) {
      dataObserver.onNewData(
        cameraOptions {
          bearing(testBearing)
          center(testCenter)
          pitch(DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_PITCH)
          zoom(DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_ZOOM)
        }
      )
    }
  }

  @Test
  fun testObserveDataSourceContinuously() {
    val indicatorBearingChangedListenerSlot = slot<OnIndicatorBearingChangedListener>()
    val indicatorPositionChangedListenerSlot = slot<OnIndicatorPositionChangedListener>()
    val dataObserver = mockk<ViewportStateDataObserver>()
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
          pitch(DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_PITCH)
          zoom(DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_ZOOM)
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
          pitch(DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_PITCH)
          zoom(DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_ZOOM)
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
    val childAnimator = mockk<ValueAnimator>()
    val animatorListenerSlot = slot<Animator.AnimatorListener>()

    every { transitionFactory.transitionLinear(any(), any()) } returns animatorSet
    every { animatorSet.addListener(any()) } just runs
    every { animatorSet.childAnimations } returns arrayListOf(childAnimator)
    every { animatorSet.start() } just runs
    every { animatorSet.setDuration(any()) } returns animatorSet
    // In real implementation when the set is cancel both onAnimationCancel and onAnimationEnd are called
    every { animatorSet.cancel() } answers {
      animatorListenerSlot.captured.onAnimationCancel(animatorSet)
      animatorListenerSlot.captured.onAnimationEnd(animatorSet)
    }
    every { cameraPlugin.registerAnimators(any()) } just runs
    every { cameraPlugin.unregisterAnimators(any()) } just runs

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
      cameraPlugin.registerAnimators(childAnimator)
      animatorSet.duration = 0
      animatorSet.start()
    }
    assertTrue(followingState.isFollowingStateRunning)

    // test stop updating camera
    followingState.stopUpdatingCamera()
    verify { animatorSet.cancel() }
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
    verify { cameraPlugin.unregisterAnimators(childAnimator) }
  }
}