package com.mapbox.maps.testapp.observable

import androidx.annotation.UiThread
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Event
import com.mapbox.maps.Observer
import com.mapbox.maps.extension.observable.subscribeResourceRequest
import com.mapbox.maps.extension.observable.unsubscribeResourceRequest
import com.mapbox.maps.testapp.BaseMapTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ObservableExtensionTest : BaseMapTest() {
  @Before
  fun setUp() {
    super.before()
  }

  @Test
  @UiThread
  fun subscribeResourceRequest() {
    val latch = CountDownLatch(1)

    val observer: Observer = object : Observer() {
      override fun notify(event: Event) {
        assertEquals("resource-request", event.type)
        latch.countDown()
      }
    }
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.subscribeResourceRequest(observer)
        mapboxMap.setCamera(
          CameraOptions.Builder().center(Point.fromLngLat(0.0, 0.0)).zoom(16.0).build()
        )
      }
    }
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    mapboxMap.unsubscribeResourceRequest(observer)
  }
}