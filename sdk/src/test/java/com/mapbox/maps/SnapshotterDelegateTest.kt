package com.mapbox.maps

import com.mapbox.common.ShadowLogger
import com.mapbox.geojson.Point
import io.mockk.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.IllegalStateException

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class, ShadowMap::class])
class SnapshotterDelegateTest {

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
  fun getUri() {
    snapshotter.getStyleUri()
    verify { coreSnapshotter.styleURI }
  }

  @Test
  fun getJson() {
    snapshotter.getStyleJson()
    verify { coreSnapshotter.styleJSON }
  }

  @Test
  fun setJson() {
    snapshotter.setStyleJson("foobar")
    verify { coreSnapshotter.styleJSON = "foobar" }
  }

  @Test
  fun subscribe() {
    val observer = mockk<Observer>()
    val list = mutableListOf<String>()
    snapshotter.subscribe(observer, list)
    verify { coreSnapshotter.subscribe(observer, list) }
  }

  @Test
  fun unsubscribe() {
    val observer: Observer = mockk()
    val list: MutableList<String> = mockk()
    snapshotter.unsubscribe(observer, list)
    verify { coreSnapshotter.unsubscribe(observer, list) }
  }

  @Test
  fun unsubscribeObserver() {
    val observer: Observer = mockk()
    snapshotter.unsubscribe(observer)
    verify { coreSnapshotter.unsubscribe(observer) }
  }

  @Test
  fun setCameraOptions() {
    val options: CameraOptions = mockk()
    snapshotter.setCamera(options)
    verify { coreSnapshotter.setCamera(options) }
  }

  @Test
  fun getCameraState() {
    snapshotter.getCameraState()
    verify { coreSnapshotter.cameraState }
  }

  @Test
  fun setTileMode() {
    snapshotter.setTileMode(true)
    verify { coreSnapshotter.setTileMode(true) }
  }

  @Test
  fun getTileMode() {
    snapshotter.isInTileMode()
    verify { coreSnapshotter.isInTileMode }
  }

  @Test
  fun coordinateBoundsForCamera() {
    val camera = mockk<CameraOptions>()
    snapshotter.coordinateBoundsForCamera(camera)
    verify { coreSnapshotter.coordinateBoundsForCamera(camera) }
  }

  @Test
  fun cameraForCoordinates() {
    val coordinate = mockk<Point>()
    val padding = mockk<EdgeInsets>()
    snapshotter.cameraForCoordinates(listOf(coordinate), padding, 0.0, 0.0)
    verify { coreSnapshotter.cameraForCoordinates(listOf(coordinate), padding, 0.0, 0.0) }
  }

  @Test
  fun setSize() {
    val value = mockk<Size>()
    snapshotter.setSize(value)
    verify { coreSnapshotter.size = value }
  }

  @Test
  fun getSize() {
    snapshotter.getSize()
    verify { coreSnapshotter.size }
  }

  @Test
  fun start() {
    val callback = mockk<SnapshotCreatedListener>()
    every { coreSnapshotter.styleJSON } returns "foobar"
    snapshotter.start(callback)
    verify { coreSnapshotter.start(any()) }
  }

  @Test(expected = IllegalStateException::class)
  fun startException() {
    val callback = mockk<SnapshotCreatedListener>()
    snapshotter.start(callback)
    verify { coreSnapshotter.start(any()) }
  }

  @Test
  fun cancel() {
    snapshotter.cancel()
    verify { coreSnapshotter.cancel() }
  }

  @Test
  fun clearData() {
    val callback = mockk<AsyncOperationResultCallback>(relaxed = true)
    snapshotter.clearData(callback)
    verify { Map.clearData(resourceOptions, callback) }
  }
}