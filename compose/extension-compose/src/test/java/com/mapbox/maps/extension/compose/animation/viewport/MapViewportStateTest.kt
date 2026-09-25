package com.mapbox.maps.extension.compose.animation.viewport

import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.extension.compose.ShadowLogConfiguration
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.viewport.CompletionListener
import com.mapbox.maps.plugin.viewport.ViewportPlugin
import com.mapbox.maps.plugin.viewport.viewport
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
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

  private companion object {
    const val CAMERA_ANIMATIONS_UTILS = "com.mapbox.maps.plugin.animation.CameraAnimationsUtils"
    const val VIEWPORT_UTILS = "com.mapbox.maps.plugin.viewport.ViewportUtils"
  }
}