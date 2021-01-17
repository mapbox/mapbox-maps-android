// This file is generated.

package com.mapbox.maps.testapp.gestures.generated

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
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
class GesturesAttributeParserDefaultValueTest : BaseMapTest() {
  @Test
  fun testAttributeParser() {
    assertEquals(
      "rotateEnabled test failed..",
      true,
      mapView.getGesturesPlugin().getSettings().rotateEnabled
    )
    assertEquals(
      "zoomEnabled test failed..",
      true,
      mapView.getGesturesPlugin().getSettings().zoomEnabled
    )
    assertEquals(
      "scrollEnabled test failed..",
      true,
      mapView.getGesturesPlugin().getSettings().scrollEnabled
    )
    assertEquals(
      "pitchEnabled test failed..",
      true,
      mapView.getGesturesPlugin().getSettings().pitchEnabled
    )
    assertEquals(
      "doubleTapToZoomEnabled test failed..",
      true,
      mapView.getGesturesPlugin().getSettings().doubleTapToZoomEnabled
    )
    assertEquals(
      "quickZoomEnabled test failed..",
      true,
      mapView.getGesturesPlugin().getSettings().quickZoomEnabled
    )
    assertEquals(
      "focalPoint test failed..",
      null,
      mapView.getGesturesPlugin().getSettings().focalPoint
    )
    assertEquals(
      "scaleVelocityAnimationEnabled test failed..",
      true,
      mapView.getGesturesPlugin().getSettings().scaleVelocityAnimationEnabled
    )
    assertEquals(
      "rotateVelocityAnimationEnabled test failed..",
      true,
      mapView.getGesturesPlugin().getSettings().rotateVelocityAnimationEnabled
    )
    assertEquals(
      "flingVelocityAnimationEnabled test failed..",
      true,
      mapView.getGesturesPlugin().getSettings().flingVelocityAnimationEnabled
    )
    assertEquals(
      "increaseRotateThresholdWhenScaling test failed..",
      true,
      mapView.getGesturesPlugin().getSettings().increaseRotateThresholdWhenScaling
    )
    assertEquals(
      "disableRotateWhenScaling test failed..",
      true,
      mapView.getGesturesPlugin().getSettings().disableRotateWhenScaling
    )
    assertEquals(
      "increaseScaleThresholdWhenRotating test failed..",
      true,
      mapView.getGesturesPlugin().getSettings().increaseScaleThresholdWhenRotating
    )
    assertEquals(
      "zoomRate test failed..",
      1f,
      mapView.getGesturesPlugin().getSettings().zoomRate
    )
    assertEquals(
      "pixelRatio test failed..",
      pixelRatio,
      mapView.getGesturesPlugin().getSettings().pixelRatio
    )
  }
}

// End of generated file.