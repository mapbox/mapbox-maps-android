package com.mapbox.maps.extension.compose.animation.viewport

import androidx.compose.runtime.saveable.SaverScope
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.extension.compose.ShadowLogConfiguration
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.viewport.CompletionListener
import com.mapbox.maps.plugin.viewport.ViewportPlugin
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.state.ViewportState
import com.mapbox.maps.plugin.viewport.state.ViewportStateDataObserver
import com.mapbox.maps.plugin.viewport.transition.ViewportTransition
import com.mapbox.maps.plugin.viewport.viewport
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(shadows = [ShadowLogConfiguration::class])
@RunWith(RobolectricTestRunner::class)
internal class MapViewportStateTest {

  private lateinit var mapViewportState: MapViewportState
  private lateinit var mapView: MapView
  private lateinit var viewportPlugin: ViewportPlugin
  private lateinit var cameraPlugin: CameraAnimationsPlugin
  private lateinit var mapboxMap: MapboxMap

  @Before
  fun setup() {
    mapView = mockk(relaxed = true)
    viewportPlugin = mockk(relaxed = true)
    cameraPlugin = mockk(relaxed = true)
    mapboxMap = mockk(relaxed = true)

    mockkStatic(CAMERA_ANIMATIONS_UTILS)
    mockkStatic(VIEWPORT_UTILS)

    every { mapView.camera } returns cameraPlugin
    every { mapView.viewport } returns viewportPlugin
    every { mapView.mapboxMap } returns mapboxMap

    mapViewportState = MapViewportState()
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  private fun drainChannel() = runBlocking {
    val job = launch(start = CoroutineStart.UNDISPATCHED) {
      mapViewportState.drainActionQueue(mapView)
    }
    job.cancel()
    job.join()
  }

  @Test
  fun `easeTo enqueues action that calls viewport transitionTo`() {
    val cameraOptions = CameraOptions.Builder().zoom(10.0).build()

    mapViewportState.easeTo(cameraOptions)
    drainChannel()

    verify { viewportPlugin.transitionTo(any(), any(), any()) }
  }

  @Test
  fun `easeTo with animation options enqueues action that calls viewport transitionTo`() {
    val cameraOptions = CameraOptions.Builder().zoom(5.0).build()
    mapViewportState.easeTo(cameraOptions, animationOptions = null, completionListener = null)
    drainChannel()

    verify { viewportPlugin.transitionTo(any(), any(), any()) }
  }

  @Test
  fun `flyTo enqueues action that calls viewport transitionTo`() {
    val cameraOptions = CameraOptions.Builder().zoom(15.0).build()

    mapViewportState.flyTo(cameraOptions)
    drainChannel()

    verify { viewportPlugin.transitionTo(any(), any(), any()) }
  }

  @Test
  fun `flyTo with completion listener enqueues action that calls viewport transitionTo`() {
    val cameraOptions = CameraOptions.Builder().zoom(12.0).build()
    val completionListener = mockk<CompletionListener>(relaxed = true)

    mapViewportState.flyTo(cameraOptions, completionListener = completionListener)
    drainChannel()

    verify { viewportPlugin.transitionTo(any(), any(), any()) }
  }

  @Test
  fun `rotateBy enqueues action that calls viewport transitionTo`() {
    val first = ScreenCoordinate(100.0, 200.0)
    val second = ScreenCoordinate(200.0, 300.0)

    mapViewportState.rotateBy(first, second)
    drainChannel()

    verify { viewportPlugin.transitionTo(any(), any(), any()) }
  }

  @Test
  fun `moveBy enqueues action that calls viewport transitionTo`() {
    val coord = ScreenCoordinate(50.0, 75.0)

    mapViewportState.moveBy(coord)
    drainChannel()

    verify { viewportPlugin.transitionTo(any(), any(), any()) }
  }

  @Test
  fun `pitchBy enqueues action that calls viewport transitionTo`() {
    mapViewportState.pitchBy(45.0)
    drainChannel()

    verify { viewportPlugin.transitionTo(any(), any(), any()) }
  }

  @Test
  fun `scaleBy enqueues action that calls viewport transitionTo`() {
    mapViewportState.scaleBy(2.0)
    drainChannel()

    verify { viewportPlugin.transitionTo(any(), any(), any()) }
  }

  @Test
  fun `scaleBy with focal point enqueues action that calls viewport transitionTo`() {
    val focalPoint = ScreenCoordinate(320.0, 240.0)

    mapViewportState.scaleBy(0.5, focalPoint)
    drainChannel()

    verify { viewportPlugin.transitionTo(any(), any(), any()) }
  }

  @Test
  fun `styleDefaultCameraOptions returns null when not bound to map`() {
    val result = mapViewportState.styleDefaultCameraOptions

    assertNull(result)
  }

  @Test
  fun `cameraForCoordinates enqueues action that calls mapboxMap cameraForCoordinates`() =
    runBlocking {
      val coordinates = listOf(Point.fromLngLat(10.0, 20.0))
      val resultCamera = CameraOptions.Builder().zoom(5.0).build()
      every { mapboxMap.cameraForCoordinates(any(), any(), any(), any(), any(), any()) } answers {
        @Suppress("UNCHECKED_CAST")
        val callback = args[5] as (CameraOptions) -> Unit
        callback(resultCamera)
      }

      val mainJob = launch(start = CoroutineStart.UNDISPATCHED) {
        mapViewportState.cameraForCoordinates(coordinates)
      }
      val drainJob = launch(start = CoroutineStart.UNDISPATCHED) {
        mapViewportState.drainActionQueue(mapView)
      }
      mainJob.join()
      drainJob.cancel()
      drainJob.join()

      verify { mapboxMap.cameraForCoordinates(eq(coordinates), any(), any(), any(), any(), any()) }
    }

  @Test
  fun `cameraForCoordinates passes coordinates and camera options to mapboxMap`() = runBlocking {
    val coordinates = listOf(Point.fromLngLat(10.0, 20.0), Point.fromLngLat(30.0, 40.0))
    val camera = CameraOptions.Builder().zoom(8.0).build()
    val resultCamera = CameraOptions.Builder().zoom(8.0).build()
    every { mapboxMap.cameraForCoordinates(any(), any(), any(), any(), any(), any()) } answers {
      @Suppress("UNCHECKED_CAST")
      val callback = args[5] as (CameraOptions) -> Unit
      callback(resultCamera)
    }

    val mainJob = launch(start = CoroutineStart.UNDISPATCHED) {
      mapViewportState.cameraForCoordinates(coordinates, camera)
    }
    val drainJob = launch(start = CoroutineStart.UNDISPATCHED) {
      mapViewportState.drainActionQueue(mapView)
    }
    mainJob.join()
    drainJob.cancel()
    drainJob.join()

    verify { mapboxMap.cameraForCoordinates(eq(coordinates), eq(camera), any(), any(), any(), any()) }
  }

  @Test
  fun `setCameraOptions enqueues an immediate viewport transition`() {
    val cameraOptions = CameraOptions.Builder().zoom(14.0).build()

    mapViewportState.setCameraOptions(cameraOptions)
    drainChannel()

    verify { viewportPlugin.makeImmediateViewportTransition() }
    verify { viewportPlugin.transitionTo(any(), any(), any()) }
  }

  @Test
  fun `setCameraOptions block enqueues an immediate viewport transition`() {
    mapViewportState.setCameraOptions { zoom(9.0) }
    drainChannel()

    verify { viewportPlugin.makeImmediateViewportTransition() }
    verify { viewportPlugin.transitionTo(any(), any(), any()) }
  }

  @Test
  fun `setCameraOptions target state delivers camera options and eases immediately`() {
    val cameraOptions = CameraOptions.Builder().zoom(11.0).build()
    val stateSlot = slot<ViewportState>()

    mapViewportState.setCameraOptions(cameraOptions)
    drainChannel()
    verify { viewportPlugin.transitionTo(capture(stateSlot), any(), any()) }

    // observeDataSource synchronously delivers the target camera options
    val observer = mockk<ViewportStateDataObserver>(relaxed = true)
    stateSlot.captured.observeDataSource(observer)
    verify { observer.onNewData(cameraOptions) }

    // startUpdatingCamera eases to the target camera immediately (duration 0)
    stateSlot.captured.startUpdatingCamera()
    verify { cameraPlugin.easeTo(eq(cameraOptions), any()) }
  }

  @Test
  fun `easeTo transition runs the camera easeTo animation`() {
    val cameraOptions = CameraOptions.Builder().zoom(13.0).build()
    val stateSlot = slot<ViewportState>()
    val transitionSlot = slot<ViewportTransition>()

    mapViewportState.easeTo(cameraOptions)
    drainChannel()
    verify { viewportPlugin.transitionTo(capture(stateSlot), capture(transitionSlot), any()) }

    transitionSlot.captured.run(stateSlot.captured, mockk(relaxed = true))
    verify { cameraPlugin.easeTo(eq(cameraOptions), any(), any()) }
  }

  @Test
  fun `flyTo transition runs the camera flyTo animation`() {
    val cameraOptions = CameraOptions.Builder().zoom(16.0).build()
    val stateSlot = slot<ViewportState>()
    val transitionSlot = slot<ViewportTransition>()

    mapViewportState.flyTo(cameraOptions)
    drainChannel()
    verify { viewportPlugin.transitionTo(capture(stateSlot), capture(transitionSlot), any()) }

    transitionSlot.captured.run(stateSlot.captured, mockk(relaxed = true))
    verify { cameraPlugin.flyTo(eq(cameraOptions), any(), any()) }
  }

  @Test
  fun `rotateBy transition runs the camera rotateBy animation`() {
    val first = ScreenCoordinate(100.0, 200.0)
    val second = ScreenCoordinate(200.0, 300.0)
    val transitionSlot = slot<ViewportTransition>()

    mapViewportState.rotateBy(first, second)
    drainChannel()
    verify { viewportPlugin.transitionTo(any(), capture(transitionSlot), any()) }

    // RelativeAnimationViewportTransition ignores the target state, so a bare mock is fine
    transitionSlot.captured.run(mockk(relaxed = true), mockk(relaxed = true))
    verify { cameraPlugin.rotateBy(eq(first), eq(second), any(), any()) }
  }

  @Test
  fun `moveBy transition runs the camera moveBy animation`() {
    val coord = ScreenCoordinate(50.0, 75.0)
    val transitionSlot = slot<ViewportTransition>()

    mapViewportState.moveBy(coord)
    drainChannel()
    verify { viewportPlugin.transitionTo(any(), capture(transitionSlot), any()) }

    transitionSlot.captured.run(mockk(relaxed = true), mockk(relaxed = true))
    verify { cameraPlugin.moveBy(eq(coord), any(), any()) }
  }

  @Test
  fun `pitchBy transition runs the camera pitchBy animation`() {
    val transitionSlot = slot<ViewportTransition>()

    mapViewportState.pitchBy(30.0)
    drainChannel()
    verify { viewportPlugin.transitionTo(any(), capture(transitionSlot), any()) }

    transitionSlot.captured.run(mockk(relaxed = true), mockk(relaxed = true))
    verify { cameraPlugin.pitchBy(eq(30.0), any(), any()) }
  }

  @Test
  fun `scaleBy transition runs the camera scaleBy animation`() {
    val focalPoint = ScreenCoordinate(320.0, 240.0)
    val transitionSlot = slot<ViewportTransition>()

    mapViewportState.scaleBy(2.0, focalPoint)
    drainChannel()
    verify { viewportPlugin.transitionTo(any(), capture(transitionSlot), any()) }

    transitionSlot.captured.run(mockk(relaxed = true), mockk(relaxed = true))
    verify { cameraPlugin.scaleBy(eq(2.0), eq(focalPoint), any(), any()) }
  }

  @Test
  fun `transitionToOverviewState enqueues an overview transition`() {
    val options = OverviewViewportStateOptions.Builder()
      .geometry(Point.fromLngLat(10.0, 20.0))
      .build()

    mapViewportState.transitionToOverviewState(options)
    drainChannel()

    verify { viewportPlugin.makeOverviewViewportState(options) }
    verify { viewportPlugin.makeDefaultViewportTransition(any()) }
    verify { viewportPlugin.transitionTo(any(), any(), any()) }
  }

  @Test
  fun `transitionToFollowPuckState enqueues a follow puck transition`() {
    // A real FollowPuckViewportStateOptions can't be used: its default bearing
    // (SyncWithLocationPuck) has a self-recursive hashCode that overflows when mockk records it.
    val options = mockk<FollowPuckViewportStateOptions>(relaxed = true)

    mapViewportState.transitionToFollowPuckState(options)
    drainChannel()

    verify { viewportPlugin.makeFollowPuckViewportState(options) }
    verify { viewportPlugin.makeDefaultViewportTransition(any()) }
    verify { viewportPlugin.transitionTo(any(), any(), any()) }
  }

  @Test
  fun `idle enqueues action that sets the viewport to idle`() {
    mapViewportState.idle()
    drainChannel()

    verify { viewportPlugin.idle() }
  }

  @Test
  fun `cameraState is null when constructed with the default initial camera state`() {
    assertNull(MapViewportState().cameraState)
  }

  @Test
  fun `cameraState reflects a provided initial camera state`() {
    val initial = cameraStateOf(zoom = 7.0)

    assertEquals(initial, MapViewportState(initial).cameraState)
  }

  @Test
  fun `viewport status fields are null before being bound to a map`() {
    assertNull(mapViewportState.mapViewportStatus)
    assertNull(mapViewportState.mapViewportStatusChangedReason)
  }

  @Test
  fun `Saver round-trips the camera state`() {
    val cameraState = cameraStateOf(zoom = 5.0)
    val state = MapViewportState(cameraState)

    val scope = SaverScope { true }
    val saved = with(MapViewportState.Saver) { scope.save(state) }
    assertEquals(cameraState, saved)

    val restored = MapViewportState.Saver.restore(saved!!)
    assertEquals(cameraState, restored?.cameraState)
  }

  private fun cameraStateOf(zoom: Double) = CameraState(
    Point.fromLngLat(1.0, 2.0),
    EdgeInsets(0.0, 0.0, 0.0, 0.0),
    zoom,
    10.0,
    20.0,
  )

  private companion object {
    const val CAMERA_ANIMATIONS_UTILS = "com.mapbox.maps.plugin.animation.CameraAnimationsUtils"
    const val VIEWPORT_UTILS = "com.mapbox.maps.plugin.viewport.ViewportUtils"
  }
}