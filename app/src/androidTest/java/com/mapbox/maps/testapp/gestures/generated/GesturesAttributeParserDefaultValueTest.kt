// This file is generated.

package com.mapbox.maps.testapp.gestures.generated

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.plugin.PanScrollMode
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
      "zoomEnabled test failed..",
      true,
      mapView.gestures.getSettings().zoomEnabled
    )
    assertEquals(
      "scrollEnabled test failed..",
      true,
      mapView.gestures.getSettings().scrollEnabled
    )
    assertEquals(
      "pitchEnabled test failed..",
      true,
      mapView.gestures.getSettings().pitchEnabled
    )
    assertEquals(
      "panScrollMode test failed..",
      PanScrollMode.HORIZONTAL_AND_VERTICAL,
      mapView.gestures.getSettings().panScrollMode
    )
    assertEquals(
      "doubleTapToZoomEnabled test failed..",
      true,
      mapView.gestures.getSettings().doubleTapToZoomEnabled
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
      "scaleVelocityAnimationEnabled test failed..",
      true,
      mapView.gestures.getSettings().scaleVelocityAnimationEnabled
    )
    assertEquals(
      "rotateVelocityAnimationEnabled test failed..",
      true,
      mapView.gestures.getSettings().rotateVelocityAnimationEnabled
    )
    assertEquals(
      "flingVelocityAnimationEnabled test failed..",
      true,
      mapView.gestures.getSettings().flingVelocityAnimationEnabled
    )
    assertEquals(
      "increaseRotateThresholdWhenScaling test failed..",
      true,
      mapView.gestures.getSettings().increaseRotateThresholdWhenScaling
    )
    assertEquals(
      "disableRotateWhenScaling test failed..",
      true,
      mapView.gestures.getSettings().disableRotateWhenScaling
    )
    assertEquals(
      "increaseScaleThresholdWhenRotating test failed..",
      true,
      mapView.gestures.getSettings().increaseScaleThresholdWhenRotating
    )
    assertEquals(
      "zoomRate test failed..",
      1f,
      mapView.gestures.getSettings().zoomRate
    )
    assertEquals(
      "pixelRatio test failed..",
      pixelRatio,
      mapView.gestures.getSettings().pixelRatio
    )
  }
}

// End of generated file.