package com.mapbox.maps.plugin.viewport.state

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.viewport.CAMERA_ANIMATIONS_UTILS
import com.mapbox.maps.plugin.viewport.DEFAULT_OVERVIEW_VIEWPORT_STATE_VERTICAL_FOV
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
  private val cameraOptions = mockk<CameraOptions>(relaxed = true)
  private val transitionFactory = mockk<MapboxViewportTransitionFactory>()
  private val styleManager = mockk<MapboxStyleManager>(relaxed = true)
  private val getStyleCallbackSlot: MutableList<(MapboxStyleManager) -> Unit> = mutableListOf()
  private lateinit var overviewState: OverviewViewportStateImpl
  private val cameraForCoordinateSlot: MutableList<(CameraOptions) -> Unit> = mutableListOf()
  private val initialGeometry = Point.fromLngLat(0.0, 0.0)
  private val newStateGeometry = Point.fromLngLat(10.0, 20.0)

  @Before
  fun setup() {
    every { delegateProvider.mapPluginProviderDelegate } returns mapPluginProviderDelegate
    mockkStatic(CAMERA_ANIMATIONS_UTILS)
    every { mapPluginProviderDelegate.camera } returns cameraPlugin
    every { delegateProvider.mapCameraManagerDelegate } returns mapCameraDelegate
    every { delegateProvider.getStyle(capture(getStyleCallbackSlot)) } answers {
      getStyleCallbackSlot.last().invoke(styleManager)
    }
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
    getStyleCallbackSlot.clear()
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
          .bearing(0.0).pitch(0.0).verticalFov(DEFAULT_OVERVIEW_VIEWPORT_STATE_VERTICAL_FOV).build(),
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
          .bearing(0.0).pitch(0.0).verticalFov(DEFAULT_OVERVIEW_VIEWPORT_STATE_VERTICAL_FOV).build(),
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
          .bearing(0.0).pitch(0.0).verticalFov(DEFAULT_OVERVIEW_VIEWPORT_STATE_VERTICAL_FOV).build(),
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
          .bearing(0.0).pitch(0.0).verticalFov(DEFAULT_OVERVIEW_VIEWPORT_STATE_VERTICAL_FOV).build(),
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
          .bearing(0.0).pitch(0.0).verticalFov(DEFAULT_OVERVIEW_VIEWPORT_STATE_VERTICAL_FOV).build(),
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
          .bearing(0.0).pitch(0.0).verticalFov(DEFAULT_OVERVIEW_VIEWPORT_STATE_VERTICAL_FOV).build(),
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
          .bearing(0.0).pitch(0.0).verticalFov(DEFAULT_OVERVIEW_VIEWPORT_STATE_VERTICAL_FOV).build(),
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
          .bearing(0.0).pitch(0.0).verticalFov(DEFAULT_OVERVIEW_VIEWPORT_STATE_VERTICAL_FOV).build(),
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
          .bearing(0.0).pitch(0.0).verticalFov(DEFAULT_OVERVIEW_VIEWPORT_STATE_VERTICAL_FOV).build(),
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

  private fun createStateWithDeferredStyle(): OverviewViewportStateImpl {
    every { delegateProvider.getStyle(capture(getStyleCallbackSlot)) } just runs
    return OverviewViewportStateImpl(
      delegateProvider,
      OverviewViewportStateOptions.Builder().geometry(newStateGeometry).build(),
      transitionFactory
    )
  }

  private fun verifyCameraForCoordinates(geometry: Point, exactly: Int) {
    verify(exactly = exactly) {
      mapCameraDelegate.cameraForCoordinates(
        coordinates = listOf(geometry),
        camera = CameraOptions.Builder().padding(EdgeInsets(0.0, 0.0, 0.0, 0.0))
          .bearing(0.0).pitch(0.0).verticalFov(DEFAULT_OVERVIEW_VIEWPORT_STATE_VERTICAL_FOV).build(),
        coordinatesPadding = EdgeInsets(0.0, 0.0, 0.0, 0.0),
        maxZoom = null,
        offset = ScreenCoordinate(0.0, 0.0),
        capture(cameraForCoordinateSlot)
      )
    }
  }

  /**
   * When the style is not loaded yet, the framing is still computed immediately,
   * without waiting for the style. This lets a transition start without a pending
   * window that a competing camera action could cancel.
   * */
  @Test
  fun testFramingRecomputedWhenStyleLoads() {
    createStateWithDeferredStyle()

    verifyCameraForCoordinates(newStateGeometry, exactly = 1)
    verify { delegateProvider.getStyle(any()) }

    // Once the style loads, the framing is recomputed so it reflects the style's projection.
    getStyleCallbackSlot.last().invoke(styleManager)
    verifyCameraForCoordinates(newStateGeometry, exactly = 2)
  }

  @Test
  fun testFramingEvaluatedOnceWhenStyleAlreadyLoaded() {
    every {
      mapCameraDelegate.cameraForCoordinates(
        any(),
        any(),
        any(),
        any(),
        any(),
        capture(cameraForCoordinateSlot)
      )
    } answers {
      cameraForCoordinateSlot.last().invoke(cameraOptions)
    }

    val state = OverviewViewportStateImpl(
      delegateProvider,
      OverviewViewportStateOptions.Builder().geometry(newStateGeometry).build(),
      transitionFactory
    )

    verifyCameraForCoordinates(newStateGeometry, exactly = 1)

    val dataObserver = mockk<ViewportStateDataObserver>()
    every { dataObserver.onNewData(any()) } returns true
    state.observeDataSource(dataObserver)
    verify(exactly = 1) {
      dataObserver.onNewData(cameraOptions)
    }
  }

  @Test
  fun testParkedStateReframesWhenStyleLoads() {
    val animatorSet = mockk<AnimatorSet>()
    val animator = mockk<ValueAnimator>()
    every { transitionFactory.transitionLinear(any(), any()) } returns animatorSet
    every { animatorSet.addListener(any()) } just runs
    every { animatorSet.childAnimations } returns arrayListOf(animator)
    every { animatorSet.start() } just runs
    every { animatorSet.cancel() } just runs

    val state = createStateWithDeferredStyle()
    state.startUpdatingCamera()

    verifyCameraForCoordinates(newStateGeometry, exactly = 1)
    cameraForCoordinateSlot.last().invoke(cameraOptions)

    getStyleCallbackSlot.last().invoke(styleManager)
    verifyCameraForCoordinates(newStateGeometry, exactly = 2)
    cameraForCoordinateSlot.last().invoke(cameraOptions)

    verify(exactly = 2) {
      transitionFactory.transitionLinear(any(), any())
    }
  }

  @Test
  fun testReframesWhenOverviewReRequestedAfterProjectionChange() {
    val state = createStateWithDeferredStyle()
    verifyCameraForCoordinates(newStateGeometry, exactly = 1)

    // style.setProjection(...) happened externally; re-request overview with the same options.
    state.options = state.options.toBuilder().build()

    verifyCameraForCoordinates(newStateGeometry, exactly = 2)
  }

  @Test
  fun testOnlyLatestGeometryEvaluatedWhenStyleLoadsAfterMultipleOptionChanges() {
    val state = createStateWithDeferredStyle()
    val geometryA = Point.fromLngLat(1.0, 1.0)
    val geometryB = Point.fromLngLat(2.0, 2.0)

    state.options = state.options.toBuilder().geometry(geometryA).build()
    state.options = state.options.toBuilder().geometry(geometryB).build()

    // The style loads: every pending getStyle callback fires
    getStyleCallbackSlot.toList().forEach { it.invoke(styleManager) }

    // Only the latest geometry is recomputed on style load
    verifyCameraForCoordinates(geometryB, exactly = 2)
    verifyCameraForCoordinates(geometryA, exactly = 1)
    verifyCameraForCoordinates(newStateGeometry, exactly = 1)
  }
}