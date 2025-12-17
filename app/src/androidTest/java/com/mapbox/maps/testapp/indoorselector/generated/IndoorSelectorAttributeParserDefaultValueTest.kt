// This file is generated.

package com.mapbox.maps.testapp.indoorselector.generated

import android.view.Gravity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.plugin.indoorselector.indoorSelector
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
class IndoorSelectorAttributeParserDefaultValueTest : BaseMapTest() {
  @Test
  fun testAttributeParser() {
    assertEquals(
      "enabled test failed..",
      true,
      mapView.indoorSelector.getSettings().enabled
    )
    assertEquals(
      "position test failed..",
      Gravity.TOP or Gravity.END,
      mapView.indoorSelector.getSettings().position
    )
    assertEquals(
      "marginLeft test failed..",
      8f * pixelRatio,
      mapView.indoorSelector.getSettings().marginLeft
    )
    assertEquals(
      "marginTop test failed..",
      60f * pixelRatio,
      mapView.indoorSelector.getSettings().marginTop
    )
    assertEquals(
      "marginRight test failed..",
      8f * pixelRatio,
      mapView.indoorSelector.getSettings().marginRight
    )
    assertEquals(
      "marginBottom test failed..",
      8f * pixelRatio,
      mapView.indoorSelector.getSettings().marginBottom
    )
  }
}

// End of generated file.