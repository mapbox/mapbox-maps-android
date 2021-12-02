// This file is generated.

package com.mapbox.maps.testapp.gestures.generated

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.R
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.ScrollMode
import com.mapbox.maps.plugin.gestures.gestures
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
      mapView.gestures.getSettings().rotateEnabled
    )
    assertEquals(
      "pinchToZoomEnabled test failed..",
      false,
      mapView.gestures.getSettings().pinchToZoomEnabled
    )
    assertEquals(
      "scrollEnabled test failed..",
      false,
      mapView.gestures.getSettings().scrollEnabled
    )
    assertEquals(
      "simultaneousRotateAndPinchToZoomEnabled test failed..",
      false,
      mapView.gestures.getSettings().simultaneousRotateAndPinchToZoomEnabled
    )
    assertEquals(
      "pitchEnabled test failed..",
      false,
      mapView.gestures.getSettings().pitchEnabled
    )
    assertEquals(
      "scrollMode test failed..",
      ScrollMode.HORIZONTAL_AND_VERTICAL,
      mapView.gestures.getSettings().scrollMode
    )
    assertEquals(
      "doubleTapToZoomInEnabled test failed..",
      false,
      mapView.gestures.getSettings().doubleTapToZoomInEnabled
    )
    assertEquals(
      "doubleTouchToZoomOutEnabled test failed..",
      false,
      mapView.gestures.getSettings().doubleTouchToZoomOutEnabled
    )
    assertEquals(
      "quickZoomEnabled test failed..",
      false,
      mapView.gestures.getSettings().quickZoomEnabled
    )
    assertEquals(
      "focalPoint test failed..",
      ScreenCoordinate(10.0, 20.0),
      mapView.gestures.getSettings().focalPoint
    )
    assertEquals(
      "pinchToZoomDecelerationEnabled test failed..",
      false,
      mapView.gestures.getSettings().pinchToZoomDecelerationEnabled
    )
    assertEquals(
      "rotateDecelerationEnabled test failed..",
      false,
      mapView.gestures.getSettings().rotateDecelerationEnabled
    )
    assertEquals(
      "scrollDecelerationEnabled test failed..",
      false,
      mapView.gestures.getSettings().scrollDecelerationEnabled
    )
    assertEquals(
      "increaseRotateThresholdWhenPinchingToZoom test failed..",
      false,
      mapView.gestures.getSettings().increaseRotateThresholdWhenPinchingToZoom
    )
    assertEquals(
      "increasePinchToZoomThresholdWhenRotating test failed..",
      false,
      mapView.gestures.getSettings().increasePinchToZoomThresholdWhenRotating
    )
    assertEquals(
      "zoomAnimationAmount test failed..",
      0.9f,
      mapView.gestures.getSettings().zoomAnimationAmount
    )
  }
}

// End of generated file.