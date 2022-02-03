// This file is generated.

package com.mapbox.maps.testapp.locationcomponent.generated

import android.graphics.Color
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
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
class LocationComponentAttributeParserDefaultValueTest : BaseMapTest() {
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
      Color.parseColor("#4A90E2"),
      mapView.location.getSettings().pulsingColor
    )
    assertEquals(
      "pulsingMaxRadius test failed..",
      10f * pixelRatio,
      mapView.location.getSettings().pulsingMaxRadius
    )
    assertEquals(
      "layerAbove test failed..",
      null,
      mapView.location.getSettings().layerAbove
    )
    assertEquals(
      "layerBelow test failed..",
      null,
      mapView.location.getSettings().layerBelow
    )
  }
}

// End of generated file.