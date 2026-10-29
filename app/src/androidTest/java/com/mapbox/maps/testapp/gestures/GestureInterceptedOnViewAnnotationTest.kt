package com.mapbox.maps.testapp.gestures

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mapbox.geojson.Point
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.testapp.BaseMapTest
import com.mapbox.maps.testapp.test.R
import com.mapbox.maps.viewannotation.OnViewAnnotationUpdatedListener
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
                  width: Double,
                  height: Double
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

    // sleep to make sure things settle down
    Thread.sleep(2000)

    assertEquals(0, longClicks)
  }

  @Test
  fun scrollableViewAnnotationScrollsContentInsteadOfPanningMap() {
    val cameraCenter = Point.fromLngLat(-122.44121, 37.76132)
    val scrollViewId = View.generateViewId()
    lateinit var scrollView: ScrollView

    withLatch { latch ->
      rule.scenario.onActivity { activity ->
        activity.runOnUiThread {
          mapboxMap.setCamera(cameraOptions { center(cameraCenter) })
          scrollView = ScrollView(activity).apply {
            id = scrollViewId
            // Fixed viewport height with much taller content, so the ScrollView is always scrollable.
            // A WRAP_CONTENT height could be sized to the full content and then nothing scrolls.
            layoutParams = ViewGroup.LayoutParams(300, 200)
            addView(
              LinearLayout(activity).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = ViewGroup.LayoutParams(
                  ViewGroup.LayoutParams.MATCH_PARENT,
                  ViewGroup.LayoutParams.WRAP_CONTENT
                )
                repeat(20) { i ->
                  addView(TextView(activity).apply { text = "Scrollable item #$i"; height = 80 })
                }
              }
            )
          }
          addAnnotationAndAwaitVisible(scrollView, cameraCenter, latch)
        }
      }
    }

    // Wait until the ScrollView is actually laid out at the annotation's constrained size and can
    // scroll, rather than a fixed sleep. This is the precondition the drag relies on and removes the
    // CI flake where the gesture fired before layout settled.
    waitUntilOnMain { scrollView.isLaidOut && scrollView.canScrollVertically(1) }

    var centerBefore = Point.fromLngLat(0.0, 0.0)
    InstrumentationRegistry.getInstrumentation().runOnMainSync { centerBefore = mapboxMap.cameraState.center }

    // Drag starting on the annotation: should scroll the ScrollView, NOT pan the map.
    // The drag is diagonal (non-zero deltaX) and slow (long duration, so small per-move steps)
    // on purpose: a straight vertical drag never exercised the axis mismatch where the router
    // crossed slop by Euclidean distance before the ScrollView crossed it on |dy| alone.
    onView(withId(scrollViewId)).perform(
      GesturesUiTestUtils.move(deltaX = 120f, deltaY = -150f, withVelocity = false, duration = 1000)
    )

    var scrollY = 0
    var centerAfter = Point.fromLngLat(0.0, 0.0)
    InstrumentationRegistry.getInstrumentation().runOnMainSync {
      scrollY = scrollView.scrollY
      centerAfter = mapboxMap.cameraState.center
    }

    assertTrue("ScrollView content should have scrolled, but scrollY=$scrollY", scrollY > 0)
    assertEquals(centerBefore.latitude(), centerAfter.latitude(), 1e-9)
    assertEquals(centerBefore.longitude(), centerAfter.longitude(), 1e-9)
  }

  @Test
  fun nonScrollableViewAnnotationStillPansMap() {
    val cameraCenter = Point.fromLngLat(-122.44121, 37.76132)
    val viewId = View.generateViewId()

    withLatch { latch ->
      rule.scenario.onActivity { activity ->
        activity.runOnUiThread {
          mapboxMap.setCamera(cameraOptions { center(cameraCenter) })
          // A plain, non-clickable TextView does not consume touches, so dragging it must still
          // pan the map.
          val staticView = TextView(activity).apply {
            id = viewId
            text = "Static annotation"
            layoutParams = ViewGroup.LayoutParams(
              ViewGroup.LayoutParams.WRAP_CONTENT,
              ViewGroup.LayoutParams.WRAP_CONTENT
            )
          }
          addAnnotationAndAwaitVisible(staticView, cameraCenter, latch)
        }
      }
    }

    viewId.loopFor(200)
    var centerBefore = Point.fromLngLat(0.0, 0.0)
    InstrumentationRegistry.getInstrumentation().runOnMainSync { centerBefore = mapboxMap.cameraState.center }

    onView(withId(viewId)).perform(
      GesturesUiTestUtils.move(deltaX = 0f, deltaY = -150f, withVelocity = false)
    )

    var centerAfter = Point.fromLngLat(0.0, 0.0)
    InstrumentationRegistry.getInstrumentation().runOnMainSync { centerAfter = mapboxMap.cameraState.center }
    assertTrue(
      "Map should pan when dragging a non-scrollable annotation",
      centerBefore.latitude() != centerAfter.latitude() ||
        centerBefore.longitude() != centerAfter.longitude()
    )
  }

  private fun waitUntilOnMain(timeoutMillis: Long = 3000, condition: () -> Boolean) {
    val deadline = SystemClock.uptimeMillis() + timeoutMillis
    while (SystemClock.uptimeMillis() < deadline) {
      var satisfied = false
      InstrumentationRegistry.getInstrumentation().runOnMainSync { satisfied = condition() }
      if (satisfied) {
        return
      }
      Thread.sleep(50)
    }
    throw TimeoutException("Condition not met within ${timeoutMillis}ms")
  }

  private fun addAnnotationAndAwaitVisible(view: View, point: Point, latch: CountDownLatch) {
    mapView.viewAnnotationManager.apply {
      addViewAnnotation(
        view = view,
        options = viewAnnotationOptions {
          geometry(point)
          width(300.0)
          height(200.0)
          allowOverlap(true)
        }
      )
      addOnViewAnnotationUpdatedListener(
        object : OnViewAnnotationUpdatedListener {
          // Count down on position (not just visibility): a position update means the annotation has
          // a real screen location, so a gesture aimed at it lands correctly. On slow emulators
          // visibility can fire before the view is placed, which made the drag miss.
          override fun onViewAnnotationPositionUpdated(
            updatedView: View,
            leftTopCoordinate: ScreenCoordinate,
            width: Double,
            height: Double
          ) {
            if (updatedView == view) {
              latch.countDown()
            }
          }

          override fun onViewAnnotationVisibilityUpdated(updatedView: View, visible: Boolean) {}
        }
      )
    }
  }
}