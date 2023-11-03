package com.mapbox.maps.plugin.viewport.state

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import com.mapbox.common.Cancelable
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxMapException
import com.mapbox.maps.RenderFrameStartedCallback
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.logI
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapListenerDelegate
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.viewport.CAMERA_ANIMATIONS_UTILS
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.transition.MapboxViewportTransitionFactory
import io.mockk.Runs
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
class OverviewViewportStateImplTest {
  private val delegateProvider = mockk<MapDelegateProvider>()
  private val mapPluginProviderDelegate = mockk<MapPluginProviderDelegate>()
  private val mapListenerDelegate = mockk<MapListenerDelegate>()
  private val cancelable = mockk<Cancelable>()
  private val mapCameraDelegate = mockk<MapCameraManagerDelegate>()
  private val cameraPlugin = mockk<CameraAnimationsPlugin>()
  private val cameraOptions = mockk<CameraOptions>()
  private val transitionFactory = mockk<MapboxViewportTransitionFactory>()
  private lateinit var overviewState: OverviewViewportStateImpl

  @Before
  fun setup() {
    every { delegateProvider.mapPluginProviderDelegate } returns mapPluginProviderDelegate
    mockkStatic(CAMERA_ANIMATIONS_UTILS)
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logI(any(), any()) } just Runs
    every { mapPluginProviderDelegate.camera } returns cameraPlugin
    every { delegateProvider.mapCameraManagerDelegate } returns mapCameraDelegate
    every { delegateProvider.mapListenerDelegate } returns mapListenerDelegate
    every { mapListenerDelegate.subscribeRenderFrameStarted(any()) } returns cancelable
    every {
      mapCameraDelegate.cameraForCoordinates(
        any(),
        any(),
        any(),
        any(),
        any()
      )
    } returns cameraOptions
    every { cameraPlugin.registerAnimators(any()) } just runs
    every { cameraPlugin.unregisterAnimators(any()) } just runs
    overviewState = OverviewViewportStateImpl(
      delegateProvider,
      initialOptions = OverviewViewportStateOptions.Builder().geometry(Point.fromLngLat(0.0, 0.0))
        .build(),
      transitionFactory
    )
  }

  @After
  fun cleanUp() {
    unmockkStatic(CAMERA_ANIMATIONS_UTILS)
    unmockkStatic("com.mapbox.maps.MapboxLogger")
    unmockkAll()
  }

  @Test
  fun testObserveDataSourceForFirstDataPoint() {
    val dataObserver = mockk<ViewportStateDataObserver>()
    val testGeometry = Point.fromLngLat(0.0, 0.0)

    // stop observing after the first data point
    every { dataObserver.onNewData(any()) } returns false

    overviewState.observeDataSource(dataObserver)
    verify {
      mapCameraDelegate.cameraForCoordinates(
        coordinates = listOf(testGeometry),
        camera = CameraOptions.Builder().padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
          .bearing(0.0).pitch(0.0).build(),
        coordinatesPadding = EdgeInsets(0.0, 0.0, 0.0, 0.0),
        maxZoom = null,
        offset = ScreenCoordinate(0.0, 0.0)
      )
    }
    verify(exactly = 1) {
      dataObserver.onNewData(cameraOptions)
    }
    overviewState.options =
      overviewState.options.toBuilder().geometry(Point.fromLngLat(1.0, 1.0)).build()
    verify(exactly = 1) {
      dataObserver.onNewData(cameraOptions)
    }
  }

  @Test
  fun testObserveDataSourceContinuously() {
    val dataObserver = mockk<ViewportStateDataObserver>()
    val testGeometry = Point.fromLngLat(0.0, 0.0)

    // keep observing after the first data point
    every { dataObserver.onNewData(any()) } returns true

    overviewState.observeDataSource(dataObserver)
    verify {
      mapCameraDelegate.cameraForCoordinates(
        coordinates = listOf(testGeometry),
        camera = CameraOptions.Builder().padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
          .bearing(0.0).pitch(0.0).build(),
        coordinatesPadding = EdgeInsets(0.0, 0.0, 0.0, 0.0),
        maxZoom = null,
        offset = ScreenCoordinate(0.0, 0.0)
      )
    }
    verify(exactly = 1) {
      dataObserver.onNewData(cameraOptions)
    }
    overviewState.options =
      overviewState.options.toBuilder().geometry(Point.fromLngLat(1.0, 1.0)).build()
    verify(exactly = 2) {
      dataObserver.onNewData(cameraOptions)
    }
  }

  @Test
  fun testStartStopUpdatingCamera() {
    val animatorSet = mockk<AnimatorSet>()
    val animator = mockk<ValueAnimator>()
    val animatorListenerSlot = slot<Animator.AnimatorListener>()

    every { transitionFactory.transitionLinear(any(), any()) } returns animatorSet
    every { animatorSet.addListener(any()) } just runs
    every { animatorSet.childAnimations } returns arrayListOf(animator)
    every { animatorSet.start() } just runs
    every { animatorSet.cancel() } just runs

    // test start updating camera
    overviewState.startUpdatingCamera()
    assertTrue(overviewState.isOverviewStateRunning)

    // test updating overview state option to trigger an animation
    overviewState.options =
      overviewState.options.toBuilder().geometry(Point.fromLngLat(1.0, 1.0)).build()

    verifySequence {
      animatorSet.addListener(capture(animatorListenerSlot))
      animatorSet.childAnimations
      cameraPlugin.registerAnimators(animator)
      animatorSet.start()
    }

    // test stop updating camera
    overviewState.stopUpdatingCamera()
    assertFalse(overviewState.isOverviewStateRunning)
    verify { animatorSet.cancel() }
    verify { cameraPlugin.unregisterAnimators(animator) }
  }

  @Test
  fun testStartCancelUpdatingCamera() {
    val animatorSet = mockk<AnimatorSet>()
    val animator = mockk<ValueAnimator>()
    val animatorListenerSlot = slot<Animator.AnimatorListener>()

    every { transitionFactory.transitionLinear(any(), any()) } returns animatorSet
    every { animatorSet.addListener(any()) } just runs
    every { animatorSet.childAnimations } returns arrayListOf(animator)
    every { animatorSet.start() } just runs
    every { animatorSet.cancel() } just runs

    // test start updating camera
    overviewState.startUpdatingCamera()
    assertTrue(overviewState.isOverviewStateRunning)

    // test updating overview state option to trigger an animation
    overviewState.options =
      overviewState.options.toBuilder().geometry(Point.fromLngLat(1.0, 1.0)).build()

    verifySequence {
      animatorSet.addListener(capture(animatorListenerSlot))
      animatorSet.childAnimations
      cameraPlugin.registerAnimators(animator)
      animatorSet.start()
    }

    // test stop updating camera
    overviewState.stopUpdatingCamera()
    assertFalse(overviewState.isOverviewStateRunning)
    verify { animatorSet.cancel() }
    verify { cameraPlugin.unregisterAnimators(animator) }
  }

  @Test
  fun testCameraForCoordinatesException() {
    val dataObserver = mockk<ViewportStateDataObserver>()
    val testGeometry = Point.fromLngLat(0.0, 0.0)

    every {
      mapCameraDelegate.cameraForCoordinates(
        any(),
        any(),
        any(),
        any(),
        any()
      )
    } throws MapboxMapException("Mock exception")

    // keep observing after the first data point
    every { dataObserver.onNewData(any()) } returns true

    overviewState.observeDataSource(dataObserver)
    verify(exactly = 1) {
      mapCameraDelegate.cameraForCoordinates(
        coordinates = listOf(testGeometry),
        camera = CameraOptions.Builder().padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
          .bearing(0.0).pitch(0.0).build(),
        coordinatesPadding = EdgeInsets(0.0, 0.0, 0.0, 0.0),
        maxZoom = null,
        offset = ScreenCoordinate(0.0, 0.0)
      )
    }
    verify(exactly = 0) {
      dataObserver.onNewData(cameraOptions)
    }
    verify(exactly = 1) { logI(any(), any()) }
    overviewState.options =
      overviewState.options.toBuilder().geometry(Point.fromLngLat(1.0, 1.0)).build()
    verify(exactly = 2) {
      mapCameraDelegate.cameraForCoordinates(
        coordinates = any(),
        camera = any(),
        coordinatesPadding = any(),
        maxZoom = any(),
        offset = any()
      )
    }
    verify(exactly = 0) {
      dataObserver.onNewData(cameraOptions)
    }
    verify(exactly = 2) { logI(any(), any()) }
  }

  @Test
  fun testTriggerNotifyingObserversOnFirstRenderFrameStarted() {
    val dataObserver = mockk<ViewportStateDataObserver>()
    val testGeometry = Point.fromLngLat(0.0, 0.0)
    val callbacks = mutableListOf<RenderFrameStartedCallback>()
    every { cancelable.cancel() } just runs

    verify { mapListenerDelegate.subscribeRenderFrameStarted(capture(callbacks)) }

    // keep observing after the first data point
    every { dataObserver.onNewData(any()) } returns true

    overviewState.observeDataSource(dataObserver)
    verify(exactly = 1) {
      mapCameraDelegate.cameraForCoordinates(
        coordinates = listOf(testGeometry),
        camera = CameraOptions.Builder().padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
          .bearing(0.0).pitch(0.0).build(),
        coordinatesPadding = EdgeInsets(0.0, 0.0, 0.0, 0.0),
        maxZoom = null,
        offset = ScreenCoordinate(0.0, 0.0)
      )
    }
    verify(exactly = 1) {
      dataObserver.onNewData(cameraOptions)
    }

    callbacks.first().run(mockk())
    verify { cancelable.cancel() }
    verify(exactly = 2) {
      mapCameraDelegate.cameraForCoordinates(
        coordinates = listOf(testGeometry),
        camera = CameraOptions.Builder().padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
          .bearing(0.0).pitch(0.0).build(),
        coordinatesPadding = EdgeInsets(0.0, 0.0, 0.0, 0.0),
        maxZoom = null,
        offset = ScreenCoordinate(0.0, 0.0)
      )
    }
    verify(exactly = 2) {
      dataObserver.onNewData(cameraOptions)
    }
  }
}