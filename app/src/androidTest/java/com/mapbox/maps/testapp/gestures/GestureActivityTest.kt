package com.mapbox.maps.testapp.gestures

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.R
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.testapp.BaseMapTest
import com.mapbox.maps.testapp.gestures.GesturesUiTestUtils.move
import com.mapbox.maps.testapp.gestures.GesturesUiTestUtils.quickScale
import org.junit.Assert
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
    val initialZoom: Double = mapboxMap.getCameraOptions(null).zoom!!
    onView(withId(R.id.mapView)).perform(quickScale(maxHeight / 2f, withVelocity = false))
    rule.scenario.onActivity {
      it.runOnUiThread {
        Assert.assertTrue(mapboxMap.getCameraOptions(null).zoom!! > initialZoom)
      }
    }
  }

  @Test
  fun quickZoomDisabled_phantomQuickZoom_moveStillEnabled_15091() {
    // regression test for https://github.com/mapbox/mapbox-gl-native/issues/15091
    var initialCameraPosition: CameraOptions? = null
    rule.scenario.onActivity {
      it.runOnUiThread {
        // zoom in so we can move vertically
        mapboxMap.setCamera(CameraOptions.Builder().zoom(4.0).build())
        initialCameraPosition = mapboxMap.getCameraOptions(null)
        mapView.gestures.quickZoomEnabled = false
      }
    }

    onView(withId(R.id.mapView)).perform(quickScale(maxHeight / 2f))
    rule.scenario.onActivity {
      it.runOnUiThread {
        // camera did not move
        Assert.assertEquals(initialCameraPosition!!.zoom, mapboxMap.getCameraOptions(null).zoom)
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
        Assert.assertNotEquals(
          initialCameraPosition!!.center!!.latitude(),
          mapboxMap.getCameraOptions(null).center!!.latitude(),
          1.0
        )
        Assert.assertNotEquals(
          initialCameraPosition!!.center!!.longitude(),
          mapboxMap.getCameraOptions(null).center!!.longitude(),
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
        initialTarget = mapboxMap.getCameraOptions(null)!!.center
      }
    }

    onView(withId(R.id.mapView)).perform(quickScale(maxHeight / 2f))
    rule.scenario.onActivity {
      it.runOnUiThread {
        // camera did not move
        Assert.assertEquals(
          initialTarget!!.latitude(),
          mapboxMap.getCameraOptions(null).center!!.latitude(),
          1.0
        )
        Assert.assertEquals(
          initialTarget!!.longitude(),
          mapboxMap.getCameraOptions(null).center!!.longitude(),
          1.0
        )
      }
    }
  }

  @Test
  fun quickZoom_interrupted_moveStillEnabled_14598() {
    // test for https://github.com/mapbox/mapbox-gl-native/issues/14598
    onView(withId(R.id.mapView)).perform(quickScale(maxHeight / 2f, interrupt = true))

    var initialCameraPosition: CameraOptions? = null
    rule.scenario.onActivity {
      it.runOnUiThread {
        // zoom in so we can move vertically
        mapboxMap.setCamera(CameraOptions.Builder().zoom(4.0).build())
        initialCameraPosition = mapboxMap.getCameraOptions(null)
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
        Assert.assertNotEquals(
          initialCameraPosition!!.center!!.latitude(),
          mapboxMap.getCameraOptions(null).center!!.latitude(),
          1.0
        )
        Assert.assertNotEquals(
          initialCameraPosition!!.center!!.longitude(),
          mapboxMap.getCameraOptions(null).center!!.longitude(),
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
        initialZoom = mapboxMap.getCameraOptions(null).zoom
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
        Assert.assertTrue(mapboxMap.getCameraOptions(null).zoom!! < initialZoom!!)
      }
    }
  }

  @Test
  fun doubleTap_minimalMovement() {
    var initialZoom: Double? = null
    rule.scenario.onActivity {
      it.runOnUiThread {
        initialZoom = mapboxMap.getCameraOptions(null).zoom
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
        Assert.assertEquals(initialZoom!! + 1, mapboxMap.getCameraOptions(null).zoom!!, 0.1)
      }
    }
  }

  @Test
  fun doubleTap_overMaxThreshold_ignore_14013() {
    // test for https://github.com/mapbox/mapbox-gl-native/issues/14013
    var initialZoom: Double? = null
    rule.scenario.onActivity {
      it.runOnUiThread {
        initialZoom = mapboxMap.getCameraOptions(null).zoom
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
        Assert.assertEquals(initialZoom!!, mapboxMap.getCameraOptions(null).zoom!!, 0.01)
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

    var initialCameraPosition: CameraOptions? = null
    rule.scenario.onActivity {
      it.runOnUiThread {
        // zoom in so we can move vertically
        mapboxMap.setCamera(CameraOptions.Builder().zoom(4.0).build())
        initialCameraPosition = mapboxMap.getCameraOptions(null)
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
        Assert.assertNotEquals(
          initialCameraPosition!!.center!!.latitude(),
          mapboxMap.getCameraOptions(null).center!!.latitude(),
          1.0
        )
        Assert.assertNotEquals(
          initialCameraPosition!!.center!!.longitude(),
          mapboxMap.getCameraOptions(null).center!!.longitude(),
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
        Assert.assertEquals(3.0, mapboxMap.getCameraOptions(null).zoom!!, 0.0001)
      }
    }
  }
}