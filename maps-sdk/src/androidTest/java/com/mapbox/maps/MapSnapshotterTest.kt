package com.mapbox.maps

import android.os.Handler
import android.os.Looper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
@LargeTest
class MapSnapshotterTest {

  private lateinit var mapSnapshotter: MapSnapshotter

  @Test
  fun sanity() {
    val latch = CountDownLatch(1)
    Handler(Looper.getMainLooper()).post {
      val context = InstrumentationRegistry.getInstrumentation().targetContext
      val snapshotterOptions = MapSnapshotOptions.Builder()
        .size(Size(600.0f, 512.0f))
        .pixelRatio(1.0f)
        .build()

      mapSnapshotter = MapSnapshotter(snapshotterOptions).apply {
        setCamera(
          CameraOptions.Builder().zoom(14.0).center(
            com.mapbox.geojson.Point.fromLngLat(
              4.895033, 52.374724
            )
          ).build()
        )
        styleURI = Style.MAPBOX_STREETS
        start {
          if (it.isValue) {
            val image = it.value!!.moveImage()!!
            if (image.data.buffer.hasRemaining() && image.width == 600 && image.height == 512) {
              latch.countDown()
            }
          }
        }
      }
    }
    if (!latch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }
}