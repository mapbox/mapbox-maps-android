package com.mapbox.maps

import com.mapbox.maps.shadows.ShadowObservable
import io.mockk.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.IllegalStateException

@RunWith(RobolectricTestRunner::class)
@Config(
  shadows = [
    ShadowMap::class,
    ShadowMapSnapshotter::class,
    ShadowObservable::class,
    ShadowCameraManager::class,
    ShadowStyleManager::class,
  ]
)
class SnapshotterTest {

  private lateinit var snapshotter: Snapshotter
  private lateinit var coreSnapshotter: MapSnapshotter
  private val mapSnapshotOptions = mockk<MapSnapshotOptions>(relaxed = true)

  @Before
  fun setUp() {
    mockkStatic(Map::class)
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logI(any(), any()) } just Runs
    coreSnapshotter = mockk(relaxed = true)
    snapshotter = Snapshotter(
      mockk(relaxed = true),
      mapSnapshotOptions,
      mockk(relaxed = true),
      coreSnapshotter
    )
  }

  @After
  fun cleanUp() {
    unmockkStatic(Map::class)
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun getStyleURI() {
    snapshotter.setStyleUri("foo")
    verify { coreSnapshotter.styleURI = "foo" }
  }

  @Test
  fun setStyleURI() {
    snapshotter.getStyleUri()
    verify { coreSnapshotter.styleURI }
  }

  @Test
  fun getStyleJSON() {
    snapshotter.getStyleJson()
    verify { coreSnapshotter.styleJSON }
  }

  @Test
  fun setStyleJSON() {
    snapshotter.setStyleJson("foo")
    verify { coreSnapshotter.styleJSON = "foo" }
  }

  @Test
  fun setSize() {
    val size = Size(4.0f, 3.0f)
    snapshotter.setSize(size)
    verify { coreSnapshotter.size = size }
  }

  @Test
  fun getSize() {
    snapshotter.getSize()
    verify { coreSnapshotter.size }
  }

  @Test
  fun setCamera() {
    val options = CameraOptions.Builder().build()
    snapshotter.setCamera(options)
    verify { coreSnapshotter.setCamera(options) }
  }

  @Test
  fun getCameraState() {
    snapshotter.getCameraState()
    verify { coreSnapshotter.cameraState }
  }

  @Test
  fun coordinateBoundsForCamera() {
    val camera = mockk<CameraOptions>()
    snapshotter.coordinateBoundsForCamera(camera)
    verify { coreSnapshotter.coordinateBoundsForCamera(any()) }
  }

  @Test
  fun start() {
    every { coreSnapshotter.styleJSON } returns "foobar"
    snapshotter.start(mockk(), mockk())
    verify { coreSnapshotter.start(any()) }
  }

  @Test(expected = IllegalStateException::class)
  fun startWithException() {
    every { coreSnapshotter.styleJSON } returns ""
    snapshotter.start(mockk(), mockk())
    verify { coreSnapshotter.start(any()) }
  }

  @Test
  fun tileCover() {
    val tileCoverOptions = TileCoverOptions.Builder().build()
    val cameraOptions = CameraOptions.Builder().build()
    snapshotter.tileCover(tileCoverOptions, cameraOptions)
    verify { coreSnapshotter.tileCover(tileCoverOptions, cameraOptions) }
  }
  @Test
  fun cancel() {
    snapshotter.cancel()
    verify { coreSnapshotter.cancel() }
  }

  @Test
  fun destroy() {
    snapshotter.destroy()
    verify { coreSnapshotter.cancel() }
    Assert.assertNull(snapshotter.snapshotStyleCallback)
  }
}