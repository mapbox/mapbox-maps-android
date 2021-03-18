package com.mapbox.maps

import android.os.Handler
import android.os.Looper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mapbox.maps.test.R
import org.junit.Ignore
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
  @Ignore("Flaky test #339")
  fun sanity() {
    val latch = CountDownLatch(1)
    Handler(Looper.getMainLooper()).post {
      val context = InstrumentationRegistry.getInstrumentation().targetContext
      val snapshotterOptions = MapSnapshotOptions.Builder()
        .resourceOptions(
          ResourceOptions.Builder()
            .accessToken(context.getString(R.string.mapbox_access_token))
            .cachePath(context.filesDir.absolutePath + "/$DATABASE_NAME")
            .assetPath(context.filesDir.absolutePath)
            .cacheSize(50000L)
            .build()
        )
        .size(Size(512.0f, 512.0f))
        .pixelRatio(1.0f)
        .build()

      mapSnapshotter = MapSnapshotter(snapshotterOptions)
      mapSnapshotter.setCamera(
        CameraOptions.Builder().zoom(14.0).center(
          com.mapbox.geojson.Point.fromLngLat(
            4.895033, 52.374724
          )
        ).build()
      )
      mapSnapshotter.styleURI = Style.MAPBOX_STREETS
      mapSnapshotter.start {
        latch.countDown()
      }
    }
    if (!latch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }
}