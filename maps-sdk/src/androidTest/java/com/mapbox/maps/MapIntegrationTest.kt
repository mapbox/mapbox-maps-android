package com.mapbox.maps

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Point
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
@LargeTest
class MapIntegrationTest {

  @get:Rule
  val rule = ActivityScenarioRule(EmptyActivity::class.java)

  private lateinit var mapView: MapView
  private lateinit var mapboxMap: MapboxMap
  private lateinit var countDownLatch: CountDownLatch

  @Test
  fun testSetCameraOnStart() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView = MapView(it)
        mapboxMap = mapView.mapboxMap
        it.frameLayout.addView(mapView)
        mapView.onStart()
        mapboxMap.setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(17.045988781311138, 51.12341909978734))
            .bearing(27.254667247679752)
            .pitch(0.0)
            .padding(EdgeInsets(140.0, 140.0, 140.0, 140.0))
            .zoom(13.282170222962456)
            .build()
        )
        countDownLatch.countDown()
      }
    }
    if (!countDownLatch.await(5, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @After
  @UiThreadTest
  fun teardown() {
    mapView.onStop()
    mapView.onDestroy()
  }
}