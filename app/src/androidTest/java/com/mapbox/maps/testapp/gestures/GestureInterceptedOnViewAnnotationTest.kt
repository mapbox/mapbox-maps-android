package com.mapbox.maps.testapp.gestures

import android.view.LayoutInflater
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mapbox.geojson.Point
import com.mapbox.maps.MapIdleCallback
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.testapp.BaseMapTest
import com.mapbox.maps.testapp.test.R
import com.mapbox.maps.viewannotation.OnViewAnnotationUpdatedListener
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Tests gestures intercepted on view annotations.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class GestureInterceptedOnViewAnnotationTest : BaseMapTest() {

  private fun withLatch(
    count: Int = 1,
    timeoutMillis: Long = 3000,
    lambda: (CountDownLatch) -> Unit
  ) {
    val latch = CountDownLatch(count)
    lambda(latch)
    if (!latch.await(timeoutMillis, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun clickableViewAnnotationDoesNotTriggerLongTouchAfterwards() {
    val cameraCenter = Point.fromLngLat(-122.44121, 37.76132)

    var longClicks = 0

    withLatch { latch ->
      rule.scenario.onActivity {
        it.runOnUiThread {
          mapView.gestures.addOnMapLongClickListener {
            longClicks++
            return@addOnMapLongClickListener true
          }

          mapboxMap.setCamera(
            cameraOptions {
              center(cameraCenter)
            }
          )

          mapView.viewAnnotationManager.apply {

            // xml layout resource is only available within instrumentationContext,
            // while mapView uses targetContext hence fails to inflate if used via resId
            val viewAnnotation = LayoutInflater.from(InstrumentationRegistry.getInstrumentation().context).inflate(R.layout.view_annotation, mapView, false)

            addViewAnnotation(
              view = viewAnnotation,
              options = viewAnnotationOptions {
                geometry(cameraCenter)
              }
            )

            viewAnnotation.setOnClickListener { _ ->
              removeViewAnnotation(viewAnnotation)
            }

            addOnViewAnnotationUpdatedListener(
              object : OnViewAnnotationUpdatedListener {
                override fun onViewAnnotationPositionUpdated(
                  view: View,
                  leftTopCoordinate: ScreenCoordinate,
                  width: Int,
                  height: Int
                ) {}

                override fun onViewAnnotationVisibilityUpdated(view: View, visible: Boolean) {
                  if (view == viewAnnotation && visible) {
                    latch.countDown()
                  }
                }
              }
            )
          }
        }
      }
    }

    // click on view annotation - triggers closing on onClickListener
    onView(withId(R.id.view_annotation)).perform(GesturesUiTestUtils.click())

    // sleep to check that long-touch wont be triggered after next gestures
    Thread.sleep(2000)

    // scroll map
    onView(withId(com.mapbox.maps.R.id.mapView)).perform(
      GesturesUiTestUtils.move(
        deltaX = 100f,
        deltaY = 200f,
        withVelocity = false,
      )
    )

    // click on map
    onView(withId(com.mapbox.maps.R.id.mapView)).perform(GesturesUiTestUtils.click())

    withLatch { latch ->
      rule.scenario.onActivity {
        val listener = MapIdleCallback {
          latch.countDown()
        }

        rule.scenario.onActivity {
          it.runOnUiThread {
            mapboxMap.apply {
              subscribeMapIdle(listener)
            }
          }
        }
      }
    }

    assertEquals(0, longClicks)
  }
}