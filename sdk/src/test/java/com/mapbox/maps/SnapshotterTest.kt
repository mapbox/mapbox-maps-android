package com.mapbox.maps

import com.mapbox.common.ShadowLogger
import io.mockk.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.IllegalStateException

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class, ShadowMap::class])
class SnapshotterTest {

  private lateinit var snapshotter: Snapshotter
  private lateinit var coreSnapshotter: MapSnapshotterInterface
  private val mapSnapshotOptions = mockk<MapSnapshotOptions>(relaxed = true)
  private val resourceOptions = mockk<ResourceOptions>(relaxed = true)

  @Before
  fun setUp() {
    mockkStatic(Map::class)
    every { Map.clearData(any(), any()) } just runs
    coreSnapshotter = mockk(relaxed = true)
    every { mapSnapshotOptions.resourceOptions } returns resourceOptions
    snapshotter = Snapshotter(mockk(relaxed = true), mapSnapshotOptions, coreSnapshotter, mockk(relaxed = true), mockk(relaxed = true))
  }

  @Test
  fun subscribe() {
    val observer = mockk<Observer>()
    val list = mockk<MutableList<String>>()
    snapshotter.subscribe(observer, list)
    verify { coreSnapshotter.subscribe(observer, list) }
  }

  @Test
  fun unsubscribe() {
    val observer = mockk<Observer>()
    val list = mockk<MutableList<String>>()
    snapshotter.unsubscribe(observer, list)
    verify { coreSnapshotter.unsubscribe(observer, list) }
  }

  @Test
  fun unsubscribeSingle() {
    val observer = mockk<Observer>()
    snapshotter.unsubscribe(observer)
    verify { coreSnapshotter.unsubscribe(observer) }
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
  fun isInTileMode() {
    snapshotter.isInTileMode()
    verify { coreSnapshotter.isInTileMode }
  }

  @Test
  fun setTileMode() {
    snapshotter.setTileMode(true)
    verify { coreSnapshotter.setTileMode(true) }
  }

  @Test
  fun start() {
    every { coreSnapshotter.styleJSON } returns "foobar"
    snapshotter.start(mockk())
    verify { coreSnapshotter.start(any()) }
  }

  @Test(expected = IllegalStateException::class)
  fun startWithException() {
    every { coreSnapshotter.styleJSON } returns ""
    snapshotter.start(mockk())
    verify { coreSnapshotter.start(any()) }
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
    Assert.assertNull(snapshotter.snapshotCreatedCallback)
  }

  @Test
  fun clearData() {
    val callback = mockk<AsyncOperationResultCallback>(relaxed = true)
    snapshotter.clearData(callback)
    verify { Map.clearData(resourceOptions, callback) }
  }
}