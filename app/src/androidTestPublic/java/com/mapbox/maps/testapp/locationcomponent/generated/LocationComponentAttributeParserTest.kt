// This file is generated.

package com.mapbox.maps.testapp.locationcomponent.generated

import android.graphics.Color
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.R
import com.mapbox.maps.plugin.PuckBearingSource
import com.mapbox.maps.plugin.locationcomponent.location
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
class LocationComponentAttributeParserTest : BaseMapTest() {
  override fun initialiseMapView() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        it.setContentView(com.mapbox.maps.testapp.R.layout.generated_test_locationcomponent)
        mapView = it.findViewById(R.id.mapView)
      }
    }
  }

  @Test
  fun testAttributeParser() {
    assertEquals(
      "enabled test failed..",
      false,
      mapView.location.getSettings().enabled
    )
    assertEquals(
      "pulsingEnabled test failed..",
      false,
      mapView.location.getSettings().pulsingEnabled
    )
    assertEquals(
      "pulsingColor test failed..",
      Color.BLACK,
      mapView.location.getSettings().pulsingColor
    )
    assertEquals(
      "pulsingMaxRadius test failed..",
      10.0f * pixelRatio,
      mapView.location.getSettings().pulsingMaxRadius
    )
    assertEquals(
      "showAccuracyRing test failed..",
      false,
      mapView.location.getSettings().showAccuracyRing
    )
    assertEquals(
      "accuracyRingColor test failed..",
      Color.BLACK,
      mapView.location.getSettings().accuracyRingColor
    )
    assertEquals(
      "accuracyRingBorderColor test failed..",
      Color.BLACK,
      mapView.location.getSettings().accuracyRingBorderColor
    )
    assertEquals(
      "layerAbove test failed..",
      "testString",
      mapView.location.getSettings().layerAbove
    )
    assertEquals(
      "layerBelow test failed..",
      "testString",
      mapView.location.getSettings().layerBelow
    )
    assertEquals(
      "puckBearingEnabled test failed..",
      false,
      mapView.location.getSettings().puckBearingEnabled
    )
    assertEquals(
      "puckBearingSource test failed..",
      PuckBearingSource.HEADING,
      mapView.location.getSettings().puckBearingSource
    )
    // TODO, test for location puck.
  }
}

// End of generated file.