// This file is generated.

package com.mapbox.maps.testapp.gestures.generated

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.R
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.gestures.getGesturesPlugin
import com.mapbox.maps.testapp.BaseMapTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class GesturesAttributeParserTest : BaseMapTest() {
  override fun initialiseMapView() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        it.setContentView(com.mapbox.maps.testapp.R.layout.generated_test_gestures)
        mapView = it.findViewById(R.id.mapView)
      }
    }
  }

  @Test
  fun testAttributeParser() {
    assertEquals(
      "rotateEnabled test failed..",
      false,
      mapView.getGesturesPlugin().getSettings().rotateEnabled
    )
    assertEquals(
      "zoomEnabled test failed..",
      false,
      mapView.getGesturesPlugin().getSettings().zoomEnabled
    )
    assertEquals(
      "scrollEnabled test failed..",
      false,
      mapView.getGesturesPlugin().getSettings().scrollEnabled
    )
    assertEquals(
      "pitchEnabled test failed..",
      false,
      mapView.getGesturesPlugin().getSettings().pitchEnabled
    )
    assertEquals(
      "doubleTapToZoomEnabled test failed..",
      false,
      mapView.getGesturesPlugin().getSettings().doubleTapToZoomEnabled
    )
    assertEquals(
      "quickZoomEnabled test failed..",
      false,
      mapView.getGesturesPlugin().getSettings().quickZoomEnabled
    )
    assertEquals(
      "focalPoint test failed..",
      ScreenCoordinate(10.0, 20.0),
      mapView.getGesturesPlugin().getSettings().focalPoint
    )
    assertEquals(
      "scaleVelocityAnimationEnabled test failed..",
      false,
      mapView.getGesturesPlugin().getSettings().scaleVelocityAnimationEnabled
    )
    assertEquals(
      "rotateVelocityAnimationEnabled test failed..",
      false,
      mapView.getGesturesPlugin().getSettings().rotateVelocityAnimationEnabled
    )
    assertEquals(
      "flingVelocityAnimationEnabled test failed..",
      false,
      mapView.getGesturesPlugin().getSettings().flingVelocityAnimationEnabled
    )
    assertEquals(
      "increaseRotateThresholdWhenScaling test failed..",
      false,
      mapView.getGesturesPlugin().getSettings().increaseRotateThresholdWhenScaling
    )
    assertEquals(
      "disableRotateWhenScaling test failed..",
      false,
      mapView.getGesturesPlugin().getSettings().disableRotateWhenScaling
    )
    assertEquals(
      "increaseScaleThresholdWhenRotating test failed..",
      false,
      mapView.getGesturesPlugin().getSettings().increaseScaleThresholdWhenRotating
    )
    assertEquals(
      "zoomRate test failed..",
      0.9f,
      mapView.getGesturesPlugin().getSettings().zoomRate
    )
    assertEquals(
      "pixelRatio test failed..",
      0.9f,
      mapView.getGesturesPlugin().getSettings().pixelRatio
    )
  }
}

// End of generated file.