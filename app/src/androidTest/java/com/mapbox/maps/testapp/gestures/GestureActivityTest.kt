package com.mapbox.maps.testapp.gestures

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.android.gestures.StandardScaleGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraState
import com.mapbox.maps.R
import com.mapbox.maps.plugin.gestures.OnScaleListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.testapp.BaseMapTest
import com.mapbox.maps.testapp.gestures.GesturesUiTestUtils.move
import com.mapbox.maps.testapp.gestures.GesturesUiTestUtils.quickScale
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class GestureActivityTest : BaseMapTest() {

  private var maxWidth: Int = 0
  private var maxHeight: Int = 0

  @Before
  fun setUp() {
    super.before()
    maxWidth = mapView.measuredWidth
    maxHeight = mapView.measuredHeight
  }

  @Test
  fun sanity_quickZoom() {
    val initialZoom: Double = mapboxMap.cameraState.zoom
    onView(withId(R.id.mapView)).perform(quickScale(maxHeight / 2f, withVelocity = false))
    rule.scenario.onActivity {
      it.runOnUiThread {
        assertTrue(mapboxMap.cameraState.zoom > initialZoom)
      }
    }
  }

  @Test
  fun quickZoomDisabled_phantomQuickZoom_moveStillEnabled_15091() {
    // regression test for https://github.com/mapbox/mapbox-gl-native/issues/15091
    var initialCameraPosition: CameraState? = null
    rule.scenario.onActivity {
      it.runOnUiThread {
        // zoom in so we can move vertically
        mapboxMap.setCamera(CameraOptions.Builder().zoom(4.0).build())
        initialCameraPosition = mapboxMap.cameraState
        mapView.gestures.quickZoomEnabled = false
      }
    }

    onView(withId(R.id.mapView)).perform(quickScale(maxHeight / 2f))
    rule.scenario.onActivity {
      it.runOnUiThread {
        // camera did not move
        assertEquals(initialCameraPosition!!.zoom, mapboxMap.cameraState.zoom, 0.0001)
      }
    }

    // move to expected target
    onView(withId(R.id.mapView)).perform(
      move(
        -maxWidth / 2f,
        -maxHeight / 2f,
        withVelocity = false
      )
    )
    rule.scenario.onActivity {
      it.runOnUiThread {
        assertNotEquals(
          initialCameraPosition!!.center.latitude(),
          mapboxMap.cameraState.center.latitude(),
          1.0
        )
        assertNotEquals(
          initialCameraPosition!!.center.longitude(),
          mapboxMap.cameraState.center.longitude(),
          1.0
        )
      }
    }
  }

  @Test
  fun quickZoom_doNotMove_14227() {
    // test for https://github.com/mapbox/mapbox-gl-native/issues/14227
    var initialTarget: Point? = null
    rule.scenario.onActivity {
      it.runOnUiThread {
        initialTarget = mapboxMap.cameraState.center
      }
    }

    onView(withId(R.id.mapView)).perform(quickScale(maxHeight / 2f))
    rule.scenario.onActivity {
      it.runOnUiThread {
        // camera did not move
        assertEquals(
          initialTarget!!.latitude(),
          mapboxMap.cameraState.center.latitude(),
          1.0
        )
        assertEquals(
          initialTarget!!.longitude(),
          mapboxMap.cameraState.center.longitude(),
          1.0
        )
      }
    }
  }

  @Test
  fun quickZoom_interrupted_moveStillEnabled_14598() {
    // test for https://github.com/mapbox/mapbox-gl-native/issues/14598
    onView(withId(R.id.mapView)).perform(quickScale(maxHeight / 2f, interrupt = true))

    var initialCameraPosition: CameraState? = null
    rule.scenario.onActivity {
      it.runOnUiThread {
        // zoom in so we can move vertically
        mapboxMap.setCamera(CameraOptions.Builder().zoom(4.0).build())
        initialCameraPosition = mapboxMap.cameraState
        mapView.gestures.quickZoomEnabled = false
      }
    }

    // move to expected target
    onView(withId(R.id.mapView)).perform(
      move(
        -maxWidth / 2f,
        -maxHeight / 2f,
        withVelocity = false
      )
    )
    rule.scenario.onActivity {
      it.runOnUiThread {
        assertNotEquals(
          initialCameraPosition!!.center.latitude(),
          mapboxMap.cameraState.center.latitude(),
          1.0
        )
        assertNotEquals(
          initialCameraPosition!!.center.longitude(),
          mapboxMap.cameraState.center.longitude(),
          1.0
        )
      }
    }
  }

  @Test
  fun quickZoom_ignoreDoubleTap() {
    // test for https://github.com/mapbox/mapbox-gl-native/issues/14013
    var initialZoom: Double? = null
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.setCamera(CameraOptions.Builder().zoom(4.0).build())
        initialZoom = mapboxMap.cameraState.zoom
      }
    }
    onView(withId(R.id.mapView)).perform(
      quickScale(
        -10000.0f,
        withVelocity = false,
        duration = 1000L
      )
    )
    R.id.mapView.loopFor(3000)
    rule.scenario.onActivity {
      it.runOnUiThread {
        assertTrue(mapboxMap.cameraState.zoom < initialZoom!!)
      }
    }
  }

  @Test
  fun doubleTap_minimalMovement() {
    var initialZoom: Double? = null
    rule.scenario.onActivity {
      it.runOnUiThread {
        initialZoom = mapboxMap.cameraState.zoom
      }
    }
    onView(withId(R.id.mapView)).perform(
      quickScale(
        5.0f,
        withVelocity = false,
        duration = 50L
      )
    )
    R.id.mapView.loopFor(3000)
    rule.scenario.onActivity {
      it.runOnUiThread {
        assertEquals(initialZoom!! + 1, mapboxMap.cameraState.zoom, 0.1)
      }
    }
  }

  @Test
  fun doubleTap_overMaxThreshold_ignore_14013() {
    // test for https://github.com/mapbox/mapbox-gl-native/issues/14013
    var initialZoom: Double? = null
    rule.scenario.onActivity {
      it.runOnUiThread {
        initialZoom = mapboxMap.cameraState.zoom
        mapView.gestures.quickZoomEnabled = false
      }
    }
    onView(withId(R.id.mapView)).perform(
      quickScale(
        10000000.0f,
        withVelocity = false,
        duration = 50L
      )
    )
    R.id.mapView.loopFor(3000)
    rule.scenario.onActivity {
      it.runOnUiThread {
        assertEquals(initialZoom!!, mapboxMap.cameraState.zoom, 0.01)
      }
    }
  }

  @Test
  fun doubleTap_interrupted_moveStillEnabled() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.setCamera(CameraOptions.Builder().zoom(4.0).build())
      }
    }

    onView(withId(R.id.mapView)).perform(
      quickScale(
        10000.0f,
        withVelocity = false,
        duration = 50L,
        interrupt = true
      )
    )

    var initialCameraPosition: CameraState? = null
    rule.scenario.onActivity {
      it.runOnUiThread {
        // zoom in so we can move vertically
        mapboxMap.setCamera(CameraOptions.Builder().zoom(4.0).build())
        initialCameraPosition = mapboxMap.cameraState
        mapView.gestures.quickZoomEnabled = false
      }
    }

    // move to expected target
    onView(withId(R.id.mapView)).perform(
      move(
        -maxWidth / 2f,
        -maxHeight / 2f,
        withVelocity = false
      )
    )
    rule.scenario.onActivity {
      it.runOnUiThread {
        assertNotEquals(
          initialCameraPosition!!.center.latitude(),
          mapboxMap.cameraState.center.latitude(),
          1.0
        )
        assertNotEquals(
          initialCameraPosition!!.center.longitude(),
          mapboxMap.cameraState.center.longitude(),
          1.0
        )
      }
    }
  }

  @Test
  fun quickZoom_roundTripping() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.setCamera(CameraOptions.Builder().zoom(3.0).build())
      }
    }
    onView(withId(R.id.mapView)).perform(quickScale(300f, withVelocity = false, duration = 750L))
    onView(withId(R.id.mapView)).perform(quickScale(-300f, withVelocity = false, duration = 750L))

    rule.scenario.onActivity {
      it.runOnUiThread {
        assertEquals(3.0, mapboxMap.cameraState.zoom, 0.0001)
      }
    }
  }

  @Test
  fun doubleTap_scaleListener_test() {
    var onScaleBegin = false
    var onScale = false
    var onScaleEnd = false
    val scaleListener = object : OnScaleListener {
      override fun onScaleBegin(detector: StandardScaleGestureDetector) {
        onScaleBegin = true
      }

      override fun onScale(detector: StandardScaleGestureDetector) {
        onScale = true
      }

      override fun onScaleEnd(detector: StandardScaleGestureDetector) {
        onScaleEnd = true
      }
    }
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.gestures.addOnScaleListener(scaleListener)
      }
    }
    onView(withId(R.id.mapView)).perform(
      quickScale(
        300.0f,
        withVelocity = false,
        duration = 50L
      )
    )
    R.id.mapView.loopFor(2000)
    rule.scenario.onActivity {
      it.runOnUiThread {
        assertTrue(onScaleBegin)
        assertTrue(onScale)
        assertTrue(onScaleEnd)
        mapView.gestures.removeOnScaleListener(scaleListener)
      }
    }
  }

  /**
   * test for quick zoom out gesture, when user click on map with double finger.
   */
  @Test
  fun doubleFingerTap_scaleListener_test() {
    var onScaleBegin = false
    var onScale = false
    var onScaleEnd = false
    val scaleListener = object : OnScaleListener {
      override fun onScaleBegin(detector: StandardScaleGestureDetector) {
        onScaleBegin = true
      }

      override fun onScale(detector: StandardScaleGestureDetector) {
        onScale = true
      }

      override fun onScaleEnd(detector: StandardScaleGestureDetector) {
        onScaleEnd = true
      }
    }
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.gestures.addOnScaleListener(scaleListener)
      }
    }
    onView(withId(R.id.mapView)).perform(
      quickScale(
        -300.0f,
        withVelocity = false,
        duration = 50L
      )
    )
    R.id.mapView.loopFor(2000)
    rule.scenario.onActivity {
      it.runOnUiThread {
        assertTrue(onScaleBegin)
        assertTrue(onScale)
        assertTrue(onScaleEnd)
        mapView.gestures.removeOnScaleListener(scaleListener)
      }
    }
  }

  @Test
  fun doubleTap_interrupted_scaleListener_test() {
    var onScaleBegin = false
    var onScale = false
    var onScaleEnd = false
    val scaleListener = object : OnScaleListener {
      override fun onScaleBegin(detector: StandardScaleGestureDetector) {
        onScaleBegin = true
      }

      override fun onScale(detector: StandardScaleGestureDetector) {
        onScale = true
      }

      override fun onScaleEnd(detector: StandardScaleGestureDetector) {
        onScaleEnd = true
      }
    }
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.gestures.addOnScaleListener(scaleListener)
      }
    }

    onView(withId(R.id.mapView)).perform(
      quickScale(
        10000.0f,
        withVelocity = false,
        duration = 1500L,
        interrupt = true
      )
    )

    // interrupt doubleTap with move gesture.
    onView(withId(R.id.mapView)).perform(
      move(
        -maxWidth / 2f,
        -maxHeight / 2f,
        withVelocity = false
      )
    )
    R.id.mapView.loopFor(2000)
    rule.scenario.onActivity {
      it.runOnUiThread {
        assertTrue(onScaleBegin)
        assertTrue(onScale)
        assertTrue(onScaleEnd)
        mapView.gestures.removeOnScaleListener(scaleListener)
      }
    }
  }
}