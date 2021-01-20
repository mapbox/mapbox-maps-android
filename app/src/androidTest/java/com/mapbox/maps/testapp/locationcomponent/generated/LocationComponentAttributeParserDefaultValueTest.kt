// This file is generated.

package com.mapbox.maps.testapp.locationcomponent.generated

import android.graphics.Color
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.plugin.PresetPuckStyle
import com.mapbox.maps.plugin.locationcomponent.getLocationComponentPlugin
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
      mapView.getLocationComponentPlugin().getSettings().enabled
    )
    assertEquals(
      "staleStateEnabled test failed..",
      true,
      mapView.getLocationComponentPlugin().getSettings().staleStateEnabled
    )
    assertEquals(
      "staleStateTimeout test failed..",
      30000,
      mapView.getLocationComponentPlugin().getSettings().staleStateTimeout
    )
    assertEquals(
      "minZoomIconScale test failed..",
      0.6f,
      mapView.getLocationComponentPlugin().getSettings().minZoomIconScale
    )
    assertEquals(
      "maxZoomIconScale test failed..",
      1f,
      mapView.getLocationComponentPlugin().getSettings().maxZoomIconScale
    )
    assertEquals(
      "pulsingEnabled test failed..",
      false,
      mapView.getLocationComponentPlugin().getSettings().pulsingEnabled
    )
    assertEquals(
      "pulsingColor test failed..",
      Color.BLUE,
      mapView.getLocationComponentPlugin().getSettings().pulsingColor
    )
    assertEquals(
      "pulsingMaxRadius test failed..",
      10f * pixelRatio,
      mapView.getLocationComponentPlugin().getSettings().pulsingMaxRadius
    )
    assertEquals(
      "layerAbove test failed..",
      null,
      mapView.getLocationComponentPlugin().getSettings().layerAbove
    )
    assertEquals(
      "layerBelow test failed..",
      null,
      mapView.getLocationComponentPlugin().getSettings().layerBelow
    )
    assertEquals(
      "locationPuck test failed..",
      null,
      mapView.getLocationComponentPlugin().getSettings().locationPuck
    )
    assertEquals(
      "presetPuckStyle test failed..",
      PresetPuckStyle.PRECISE,
      mapView.getLocationComponentPlugin().getSettings().presetPuckStyle
    )
  }
}

// End of generated file.