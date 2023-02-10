// This file is generated.

package com.mapbox.maps.testapp.gestures.generated

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
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
class GesturesAttributeParserDefaultValueTest : BaseMapTest() {
  @Test
  fun testAttributeParser() {
    assertEquals(
      "rotateEnabled test failed..",
      true,
      mapView.gestures.getSettings().rotateEnabled
    )
    assertEquals(
      "pinchToZoomEnabled test failed..",
      true,
      mapView.gestures.getSettings().pinchToZoomEnabled
    )
    assertEquals(
      "scrollEnabled test failed..",
      true,
      mapView.gestures.getSettings().scrollEnabled
    )
    assertEquals(
      "simultaneousRotateAndPinchToZoomEnabled test failed..",
      true,
      mapView.gestures.getSettings().simultaneousRotateAndPinchToZoomEnabled
    )
    assertEquals(
      "pitchEnabled test failed..",
      true,
      mapView.gestures.getSettings().pitchEnabled
    )
    assertEquals(
      "scrollMode test failed..",
      ScrollMode.HORIZONTAL_AND_VERTICAL,
      mapView.gestures.getSettings().scrollMode
    )
    assertEquals(
      "doubleTapToZoomInEnabled test failed..",
      true,
      mapView.gestures.getSettings().doubleTapToZoomInEnabled
    )
    assertEquals(
      "doubleTouchToZoomOutEnabled test failed..",
      true,
      mapView.gestures.getSettings().doubleTouchToZoomOutEnabled
    )
    assertEquals(
      "quickZoomEnabled test failed..",
      true,
      mapView.gestures.getSettings().quickZoomEnabled
    )
    assertEquals(
      "focalPoint test failed..",
      null,
      mapView.gestures.getSettings().focalPoint
    )
    assertEquals(
      "pinchToZoomDecelerationEnabled test failed..",
      true,
      mapView.gestures.getSettings().pinchToZoomDecelerationEnabled
    )
    assertEquals(
      "rotateDecelerationEnabled test failed..",
      true,
      mapView.gestures.getSettings().rotateDecelerationEnabled
    )
    assertEquals(
      "scrollDecelerationEnabled test failed..",
      true,
      mapView.gestures.getSettings().scrollDecelerationEnabled
    )
    assertEquals(
      "increaseRotateThresholdWhenPinchingToZoom test failed..",
      true,
      mapView.gestures.getSettings().increaseRotateThresholdWhenPinchingToZoom
    )
    assertEquals(
      "increasePinchToZoomThresholdWhenRotating test failed..",
      true,
      mapView.gestures.getSettings().increasePinchToZoomThresholdWhenRotating
    )
    assertEquals(
      "zoomAnimationAmount test failed..",
      1f,
      mapView.gestures.getSettings().zoomAnimationAmount
    )
    assertEquals(
      "pinchScrollEnabled test failed..",
      true,
      mapView.gestures.getSettings().pinchScrollEnabled
    )
  }
}

// End of generated file.