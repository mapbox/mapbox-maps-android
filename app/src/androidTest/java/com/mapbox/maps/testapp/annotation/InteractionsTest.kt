package com.mapbox.maps.testapp.annotation

import android.graphics.Color
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.DragInteraction
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.R
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.testapp.BaseMapTest
import com.mapbox.maps.testapp.gestures.GesturesUiTestUtils
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class InteractionsTest : BaseMapTest() {

  private lateinit var circleAnnotation: CircleAnnotation

  override fun loadMap() {
    super.loadMap()
    rule.scenario.onActivity {
      mapboxMap.setCamera(INITIAL_CAMERA)
    }
  }

  /**
   * Instrument test to make sure Interaction API works as expected.
   */
  @Test
  fun dragAnnotationTest() {
    val latch = CountDownLatch(1)
    rule.scenario.onActivity {
      mapView.annotations.createCircleAnnotationManager().apply {
        val circleAnnotationOptions: CircleAnnotationOptions = CircleAnnotationOptions()
          .withPoint(INITIAL_CAMERA.center!!)
          .withCircleColor(Color.YELLOW)
          .withCircleRadius(12.0)
          .withDraggable(true)
        circleAnnotation = create(circleAnnotationOptions)
      }
      mapView.mapboxMap.subscribeMapIdle {
        latch.countDown()
      }
      mapView.gestures.addOnMoveListener(object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
          // Make sure this user defined map surface `onMoveBegin` is not called due to the
          // `circleAnnotation` consuming it and not dispatching further
          throw RuntimeException("User defined map onMoveBegin must not be called!")
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
          // Make sure this user defined map surface `onMove` is not called due to the
          // `circleAnnotation` consuming all the `onMove` events
          throw RuntimeException("User defined map onMove must not be called!")
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {
        }
      })
    }
    Assert.assertTrue(latch.await(10_000, TimeUnit.MILLISECONDS))
    // simulate 1-finger pan gesture starting from the center of the MapView
    // to make sure we click the annotation
    val shiftX = 10f * pixelRatio
    Espresso
      .onView(ViewMatchers.withId(R.id.mapView))
      .perform(
        GesturesUiTestUtils.move(
          shiftX,
          0f
        )
      )
    // check that map camera did not change
    rule.scenario.onActivity {
      Assert.assertEquals(
        mapboxMap.cameraState.center.longitude(),
        INITIAL_CAMERA.center!!.longitude(),
        EPS
      )
      Assert.assertEquals(
        mapboxMap.cameraState.center.latitude(),
        INITIAL_CAMERA.center!!.latitude(),
        EPS
      )
    }
    // check that circle annotation was dragged
    Assert.assertEquals(
      24.938827583733797,
      circleAnnotation.point.longitude(),
      EPS
    )
    Assert.assertEquals(
      INITIAL_CAMERA.center!!.latitude(),
      circleAnnotation.point.latitude(),
      EPS
    )
  }

  @OptIn(MapboxExperimental::class)
  @Test
  fun mapMoveListenersTest() {
    val listOfMoveBeginEvents = mutableListOf<String>()
    rule.scenario.onActivity {
      mapView.gestures.addOnMoveListener(object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
          listOfMoveBeginEvents.add("1_gesture")
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
          return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
      })
      mapView.gestures.addOnMoveListener(object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
          listOfMoveBeginEvents.add("2_gesture")
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
          return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
      })
      mapView.mapboxMap.addInteraction(
        DragInteraction(
          onDragBegin = {
            listOfMoveBeginEvents.add("1_map_interaction")
            return@DragInteraction false
          },
          onDrag = { },
          onDragEnd = { }
        )
      )
      mapView.mapboxMap.addInteraction(
        DragInteraction(
          onDragBegin = {
            listOfMoveBeginEvents.add("2_map_interaction")
            return@DragInteraction false
          },
          onDrag = { },
          onDragEnd = { }
        )
      )
    }
    Espresso
      .onView(ViewMatchers.withId(R.id.mapView))
      .perform(
        GesturesUiTestUtils.move(
          200f,
          0f
        )
      )
    // interactions are LIFO while map gesture listeners are FIFO
    // also user defined interactions are always triggered before map gesture listeners
    Assert.assertArrayEquals(
      arrayOf("2_map_interaction", "1_map_interaction", "1_gesture", "2_gesture"),
      listOfMoveBeginEvents.toTypedArray()
    )
  }

  private companion object {
    private val INITIAL_CAMERA = cameraOptions {
      center(Point.fromLngLat(24.9384, 60.1699))
      zoom(14.0)
      pitch(0.0)
      bearing(0.0)
    }
    private const val EPS = 0.0001
  }
}