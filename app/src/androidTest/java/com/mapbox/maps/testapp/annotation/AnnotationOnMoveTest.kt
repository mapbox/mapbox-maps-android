package com.mapbox.maps.testapp.annotation

import android.graphics.Color
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.R
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.TopPriorityOnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.testapp.BaseMapTest
import com.mapbox.maps.testapp.gestures.GesturesUiTestUtils
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class AnnotationOnMoveTest : BaseMapTest() {

  private lateinit var circleAnnotation: CircleAnnotation

  override fun loadMap() {
    super.loadMap()
    rule.scenario.onActivity {
      mapboxMap.setCamera(INITIAL_CAMERA)
    }
  }

  /**
   * Assuming we perform async QRF in annotation manager's `onMoveBegin` we have to be sure that:
   * a) map is not moved until QRF is executed (this is verified in gesture plugin implementation)
   * b) no user explicit `OnMoveListener.onMove`s are called until QRF is executed
   *  or after QRF showed that we tapped on draggable annotation.
   */
  @Test
  fun annotationsOnMoveTest() {
    var userDefinedMoveBeginCalled = false
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
          userDefinedMoveBeginCalled = true

          // simulate situation that gestures plugin received explicit `onMove` during async QRF;
          // otherwise QRF in reality works too fast and finishes async before first `onMove` is called by our gestures library
          val gesturesImpl = Class.forName("com.mapbox.maps.plugin.gestures.GesturesPluginImpl")
          val internalHandleMove = gesturesImpl.getDeclaredMethod(
            "handleMove\$plugin_gestures_debug",
            MoveGestureDetector::class.java,
            Float::class.java,
            Float::class.java
          )
          internalHandleMove.isAccessible = true
          internalHandleMove.invoke(
            mapView.gestures,
            detector,
            10.0f,
            0f
          )
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
          // Make sure this user defined `onMove` is not called due to the
          // `circleAnnotation` consuming all the `onMove` events, either
          // because the QRF is being done or because the circle annotation is being dragged
          throw RuntimeException("User defined onMove must not be called!")
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
    Assert.assertTrue(userDefinedMoveBeginCalled)
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

  @Test
  fun addOnMoveListenerOrderingOneTest() {
    val listOfMoveBeginEvents = mutableListOf<String>()
    rule.scenario.onActivity {
      mapView.gestures.addOnMoveListener(object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
          listOfMoveBeginEvents.add("1_normal")
        }

        override fun onMove(detector: MoveGestureDetector): Boolean { return false }

        override fun onMoveEnd(detector: MoveGestureDetector) { }
      })
      mapView.gestures.addOnMoveListener(object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
          listOfMoveBeginEvents.add("2_normal")
        }

        override fun onMove(detector: MoveGestureDetector): Boolean { return false }

        override fun onMoveEnd(detector: MoveGestureDetector) { }
      })
      mapView.gestures.addOnMoveListener(object : TopPriorityOnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
          listOfMoveBeginEvents.add("1_priority")
        }

        override fun onMove(detector: MoveGestureDetector): Boolean { return false }

        override fun onMoveEnd(detector: MoveGestureDetector) { }
      })
      mapView.gestures.addOnMoveListener(object : TopPriorityOnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
          listOfMoveBeginEvents.add("2_priority")
        }

        override fun onMove(detector: MoveGestureDetector): Boolean { return false }

        override fun onMoveEnd(detector: MoveGestureDetector) { }
      })
    }
    Espresso
      .onView(ViewMatchers.withId(R.id.mapView))
      .perform(
        GesturesUiTestUtils.move(
          200f,
          0f
        )
      )
    Assert.assertArrayEquals(
      arrayOf("1_priority", "2_priority", "1_normal", "2_normal"),
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