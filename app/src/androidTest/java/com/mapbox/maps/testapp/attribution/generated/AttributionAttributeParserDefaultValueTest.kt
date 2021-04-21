// This file is generated.

package com.mapbox.maps.testapp.attribution.generated

import android.graphics.Color
import android.view.Gravity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.plugin.attribution.attribution
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
class AttributionAttributeParserDefaultValueTest : BaseMapTest() {
  @Test
  fun testAttributeParser() {
    assertEquals(
      "enabled test failed..",
      true,
      mapView.attribution.getSettings().enabled
    )
    assertEquals(
      "iconColor test failed..",
      Color.parseColor("#FF1E8CAB"),
      mapView.attribution.getSettings().iconColor
    )
    assertEquals(
      "position test failed..",
      Gravity.BOTTOM or Gravity.START,
      mapView.attribution.getSettings().position
    )
    assertEquals(
      "marginLeft test failed..",
      92f * pixelRatio,
      mapView.attribution.getSettings().marginLeft
    )
    assertEquals(
      "marginTop test failed..",
      4f * pixelRatio,
      mapView.attribution.getSettings().marginTop
    )
    assertEquals(
      "marginRight test failed..",
      4f * pixelRatio,
      mapView.attribution.getSettings().marginRight
    )
    assertEquals(
      "marginBottom test failed..",
      4f * pixelRatio,
      mapView.attribution.getSettings().marginBottom
    )
    assertEquals(
      "clickable test failed..",
      true,
      mapView.attribution.getSettings().clickable
    )
  }
}

// End of generated file.