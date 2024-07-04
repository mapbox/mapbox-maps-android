package com.mapbox.maps.plugin.viewport.state

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.viewport.CAMERA_ANIMATIONS_UTILS
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
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
class OverviewViewportStateImplTest {
  private val delegateProvider = mockk<MapDelegateProvider>()
  private val mapPluginProviderDelegate = mockk<MapPluginProviderDelegate>()
  private val mapCameraDelegate = mockk<MapCameraManagerDelegate>()
  private val cameraPlugin = mockk<CameraAnimationsPlugin>()
  private val cameraOptions = mockk<CameraOptions>()
  private val transitionFactory = mockk<MapboxViewportTransitionFactory>()
  private lateinit var overviewState: OverviewViewportStateImpl
  private val cameraForCoordinateSlot: MutableList<(CameraOptions) -> Unit> = mutableListOf()
  private val initialGeometry = Point.fromLngLat(0.0, 0.0)

  @Before
  fun setup() {
    every { delegateProvider.mapPluginProviderDelegate } returns mapPluginProviderDelegate
    mockkStatic(CAMERA_ANIMATIONS_UTILS)
    every { mapPluginProviderDelegate.camera } returns cameraPlugin
    every { delegateProvider.mapCameraManagerDelegate } returns mapCameraDelegate
    every {
      mapCameraDelegate.cameraForCoordinates(
        any(),
        any(),
        any(),
        any(),
        any(),
        any()
      )
    } just runs
    every { cameraPlugin.registerAnimators(any()) } just runs
    every { cameraPlugin.unregisterAnimators(any()) } just runs
    overviewState = OverviewViewportStateImpl(
      delegateProvider,
      initialOptions = OverviewViewportStateOptions.Builder().geometry(initialGeometry)
        .build(),
      transitionFactory
    )
  }

  @After
  fun cleanUp() {
    unmockkStatic(CAMERA_ANIMATIONS_UTILS)
    unmockkAll()
    cameraForCoordinateSlot.clear()
  }

  @Test
  fun testObserveDataSourceForFirstDataPoint() {
    val dataObserver = mockk<ViewportStateDataObserver>()

    // stop observing after the first data point
    every { dataObserver.onNewData(any()) } returns false

    verify(exactly = 1) {
      mapCameraDelegate.cameraForCoordinates(
        coordinates = listOf(initialGeometry),
        camera = CameraOptions.Builder().padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
          .bearing(0.0).pitch(0.0).build(),
        coordinatesPadding = EdgeInsets(0.0, 0.0, 0.0, 0.0),
        maxZoom = null,
        offset = ScreenCoordinate(0.0, 0.0),
        capture(cameraForCoordinateSlot)
      )
    }

    overviewState.observeDataSource(dataObserver)

    cameraForCoordinateSlot.last().invoke(cameraOptions)
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
  fun testObserveDataSourceForFirstDataPointWithCachedCamera() {
    val dataObserver = mockk<ViewportStateDataObserver>()

    // stop observing after the first data point
    every { dataObserver.onNewData(any()) } returns false

    verify(exactly = 1) {
      mapCameraDelegate.cameraForCoordinates(
        coordinates = listOf(initialGeometry),
        camera = CameraOptions.Builder().padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
          .bearing(0.0).pitch(0.0).build(),
        coordinatesPadding = EdgeInsets(0.0, 0.0, 0.0, 0.0),
        maxZoom = null,
        offset = ScreenCoordinate(0.0, 0.0),
        capture(cameraForCoordinateSlot)
      )
    }
    cameraForCoordinateSlot.last().invoke(cameraOptions)

    overviewState.observeDataSource(dataObserver)

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
  fun testObserveDataSourceForFirstDataPointCancelBeforeEvaluateViewportDataResult() {
    val dataObserver = mockk<ViewportStateDataObserver>()

    // stop observing after the first data point
    every { dataObserver.onNewData(any()) } returns false

    verify {
      mapCameraDelegate.cameraForCoordinates(
        coordinates = listOf(initialGeometry),
        camera = CameraOptions.Builder().padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
          .bearing(0.0).pitch(0.0).build(),
        coordinatesPadding = EdgeInsets(0.0, 0.0, 0.0, 0.0),
        maxZoom = null,
        offset = ScreenCoordinate(0.0, 0.0),
        capture(cameraForCoordinateSlot)
      )
    }

    val cancelable = overviewState.observeDataSource(dataObserver)
    cancelable.cancel()

    assertTrue(overviewState.dataSourceUpdateObservers.isEmpty())

    cameraForCoordinateSlot.last().invoke(cameraOptions)
    verify(exactly = 0) {
      dataObserver.onNewData(cameraOptions)
    }
    overviewState.options =
      overviewState.options.toBuilder().geometry(Point.fromLngLat(1.0, 1.0)).build()
    verify(exactly = 0) {
      dataObserver.onNewData(cameraOptions)
    }
  }

  @Test
  fun testObserveDataSourceForFirstDataPointNewGeometryBeforeEvaluateViewportDataResult() {
    val dataObserver = mockk<ViewportStateDataObserver>()
    val testCameraOptions = cameraOptions { center(initialGeometry) }

    // stop observing after the first data point
    every { dataObserver.onNewData(any()) } returns false
    verify(exactly = 1) {
      mapCameraDelegate.cameraForCoordinates(
        coordinates = listOf(initialGeometry),
        camera = CameraOptions.Builder().padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
          .bearing(0.0).pitch(0.0).build(),
        coordinatesPadding = EdgeInsets(0.0, 0.0, 0.0, 0.0),
        maxZoom = null,
        offset = ScreenCoordinate(0.0, 0.0),
        capture(cameraForCoordinateSlot)
      )
    }
    overviewState.observeDataSource(dataObserver)

    verify(exactly = 0) {
      dataObserver.onNewData(cameraOptions)
    }

    // new geometry set before the async cameraForCoordinates return
    val newGeometry = Point.fromLngLat(1.0, 1.0)
    val newCameraOptions = cameraOptions { center(Point.fromLngLat(1.0, 1.0)) }
    overviewState.options = overviewState.options.toBuilder().geometry(newGeometry).build()

    verify(exactly = 1) {
      mapCameraDelegate.cameraForCoordinates(
        coordinates = listOf(newGeometry),
        camera = CameraOptions.Builder().padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
          .bearing(0.0).pitch(0.0).build(),
        coordinatesPadding = EdgeInsets(0.0, 0.0, 0.0, 0.0),
        maxZoom = null,
        offset = ScreenCoordinate(0.0, 0.0),
        capture(cameraForCoordinateSlot)
      )
    }
    // now assuming map is ready and the cameraForCoordinates calls the result callback in sequence:
    cameraForCoordinateSlot.first().invoke(testCameraOptions)
    cameraForCoordinateSlot.last().invoke(newCameraOptions)

    // we should only get one viewport data, which is the new camera options
    verify(exactly = 0) {
      dataObserver.onNewData(testCameraOptions)
    }
    verify(exactly = 1) {
      dataObserver.onNewData(newCameraOptions)
    }
  }

  @Test
  fun testObserveDataSourceContinuously() {
    val dataObserver = mockk<ViewportStateDataObserver>()

    // keep observing after the first data point
    every { dataObserver.onNewData(any()) } returns true
    verify(exactly = 1) {
      mapCameraDelegate.cameraForCoordinates(
        coordinates = listOf(initialGeometry),
        camera = CameraOptions.Builder().padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
          .bearing(0.0).pitch(0.0).build(),
        coordinatesPadding = EdgeInsets(0.0, 0.0, 0.0, 0.0),
        maxZoom = null,
        offset = ScreenCoordinate(0.0, 0.0),
        capture(cameraForCoordinateSlot)
      )
    }
    overviewState.observeDataSource(dataObserver)

    cameraForCoordinateSlot.last().invoke(cameraOptions)
    verify(exactly = 1) {
      dataObserver.onNewData(cameraOptions)
    }
    overviewState.options =
      overviewState.options.toBuilder().geometry(Point.fromLngLat(1.0, 1.0)).build()
    verify(exactly = 1) {
      mapCameraDelegate.cameraForCoordinates(
        coordinates = listOf(Point.fromLngLat(1.0, 1.0)),
        camera = CameraOptions.Builder().padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
          .bearing(0.0).pitch(0.0).build(),
        coordinatesPadding = EdgeInsets(0.0, 0.0, 0.0, 0.0),
        maxZoom = null,
        offset = ScreenCoordinate(0.0, 0.0),
        capture(cameraForCoordinateSlot)
      )
    }
    cameraForCoordinateSlot.last().invoke(cameraOptions)
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

    verify(exactly = 1) {
      mapCameraDelegate.cameraForCoordinates(
        coordinates = listOf(Point.fromLngLat(1.0, 1.0)),
        camera = CameraOptions.Builder().padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
          .bearing(0.0).pitch(0.0).build(),
        coordinatesPadding = EdgeInsets(0.0, 0.0, 0.0, 0.0),
        maxZoom = null,
        offset = ScreenCoordinate(0.0, 0.0),
        capture(cameraForCoordinateSlot)
      )
    }
    cameraForCoordinateSlot.last().invoke(cameraOptions)

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

    verify(exactly = 1) {
      mapCameraDelegate.cameraForCoordinates(
        coordinates = listOf(Point.fromLngLat(1.0, 1.0)),
        camera = CameraOptions.Builder().padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
          .bearing(0.0).pitch(0.0).build(),
        coordinatesPadding = EdgeInsets(0.0, 0.0, 0.0, 0.0),
        maxZoom = null,
        offset = ScreenCoordinate(0.0, 0.0),
        capture(cameraForCoordinateSlot)
      )
    }
    cameraForCoordinateSlot.last().invoke(cameraOptions)

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
}