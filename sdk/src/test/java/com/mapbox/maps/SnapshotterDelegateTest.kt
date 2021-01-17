package com.mapbox.maps

import com.mapbox.common.ShadowLogger
import com.mapbox.geojson.Point
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.IllegalStateException

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class SnapshotterDelegateTest {

  private lateinit var snapshotter: Snapshotter
  private lateinit var coreSnapshotter: MapSnapshotterInterface

  @Before
  fun setUp() {
    coreSnapshotter = mockk(relaxed = true)
    snapshotter = Snapshotter(mockk(relaxed = true), coreSnapshotter)
  }

  @Test
  fun getUri() {
    snapshotter.getUri()
    verify { coreSnapshotter.styleURI }
  }

  @Test
  fun getJson() {
    snapshotter.getJson()
    verify { coreSnapshotter.styleJSON }
  }

  @Test
  fun setJson() {
    snapshotter.setJson("foobar")
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
    snapshotter.setCameraOptions(options)
    verify { coreSnapshotter.cameraOptions = options }
  }

  @Test
  fun getCameraOptions() {
    snapshotter.getCameraOptions()
    verify { coreSnapshotter.cameraOptions }
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
  fun getRegion() {
    snapshotter.getRegion()
    verify { coreSnapshotter.region }
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
    val callback = mockk<Snapshotter.SnapshotReadyCallback>()
    every { coreSnapshotter.styleJSON } returns "foobar"
    snapshotter.start(callback)
    verify { coreSnapshotter.start(any()) }
  }

  @Test(expected = IllegalStateException::class)
  fun startException() {
    val callback = mockk<Snapshotter.SnapshotReadyCallback>()
    snapshotter.start(callback)
    verify { coreSnapshotter.start(any()) }
  }

  @Test
  fun cancel() {
    snapshotter.cancel()
    verify { coreSnapshotter.cancel() }
  }
}