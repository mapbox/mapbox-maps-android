// This file is generated.

package com.mapbox.maps.testapp.scalebar.generated

import android.graphics.Color
import android.view.Gravity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.plugin.scalebar.LocaleUnitResolver
import com.mapbox.maps.plugin.scalebar.scalebar
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
class ScaleBarAttributeParserDefaultValueTest : BaseMapTest() {
  @Test
  fun testAttributeParser() {
    assertEquals(
      "enabled test failed..",
      true,
      mapView.scalebar.getSettings().enabled
    )
    assertEquals(
      "position test failed..",
      Gravity.TOP or Gravity.START,
      mapView.scalebar.getSettings().position
    )
    assertEquals(
      "marginLeft test failed..",
      4f * pixelRatio,
      mapView.scalebar.getSettings().marginLeft
    )
    assertEquals(
      "marginTop test failed..",
      4f * pixelRatio,
      mapView.scalebar.getSettings().marginTop
    )
    assertEquals(
      "marginRight test failed..",
      4f * pixelRatio,
      mapView.scalebar.getSettings().marginRight
    )
    assertEquals(
      "marginBottom test failed..",
      4f * pixelRatio,
      mapView.scalebar.getSettings().marginBottom
    )
    assertEquals(
      "textColor test failed..",
      Color.BLACK,
      mapView.scalebar.getSettings().textColor
    )
    assertEquals(
      "primaryColor test failed..",
      Color.BLACK,
      mapView.scalebar.getSettings().primaryColor
    )
    assertEquals(
      "secondaryColor test failed..",
      Color.WHITE,
      mapView.scalebar.getSettings().secondaryColor
    )
    assertEquals(
      "borderWidth test failed..",
      2f * pixelRatio,
      mapView.scalebar.getSettings().borderWidth
    )
    assertEquals(
      "height test failed..",
      2f * pixelRatio,
      mapView.scalebar.getSettings().height
    )
    assertEquals(
      "textBarMargin test failed..",
      8f * pixelRatio,
      mapView.scalebar.getSettings().textBarMargin
    )
    assertEquals(
      "textBorderWidth test failed..",
      2f * pixelRatio,
      mapView.scalebar.getSettings().textBorderWidth
    )
    assertEquals(
      "textSize test failed..",
      8f * pixelRatio,
      mapView.scalebar.getSettings().textSize
    )
    assertEquals(
      "isMetricUnits test failed..",
      LocaleUnitResolver.isMetricSystem,
      mapView.scalebar.getSettings().isMetricUnits
    )
    assertEquals(
      "refreshInterval test failed..",
      15,
      mapView.scalebar.getSettings().refreshInterval
    )
    assertEquals(
      "showTextBorder test failed..",
      true,
      mapView.scalebar.getSettings().showTextBorder
    )
    assertEquals(
      "ratio test failed..",
      0.5f,
      mapView.scalebar.getSettings().ratio
    )
    assertEquals(
      "useContinuousRendering test failed..",
      false,
      mapView.scalebar.getSettings().useContinuousRendering
    )
  }
}

// End of generated file.