// This file is generated.

package com.mapbox.maps.testapp.locationcomponent.generated

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.R
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
      mapView.getLocationComponentPlugin().getSettings().enabled
    )
    assertEquals(
      "staleStateEnabled test failed..",
      false,
      mapView.getLocationComponentPlugin().getSettings().staleStateEnabled
    )
    assertEquals(
      "staleStateTimeout test failed..",
      1000000L,
      mapView.getLocationComponentPlugin().getSettings().staleStateTimeout
    )
    assertEquals(
      "minZoomIconScale test failed..",
      0.9f,
      mapView.getLocationComponentPlugin().getSettings().minZoomIconScale
    )
    assertEquals(
      "maxZoomIconScale test failed..",
      0.9f,
      mapView.getLocationComponentPlugin().getSettings().maxZoomIconScale
    )
    assertEquals(
      "layerAbove test failed..",
      "testString",
      mapView.getLocationComponentPlugin().getSettings().layerAbove
    )
    assertEquals(
      "layerBelow test failed..",
      "testString",
      mapView.getLocationComponentPlugin().getSettings().layerBelow
    )
    // TODO, test for location puck.
    assertEquals(
      "presetPuckStyle test failed..",
      PresetPuckStyle.PRECISE,
      mapView.getLocationComponentPlugin().getSettings().presetPuckStyle
    )
  }
}

// End of generated file.