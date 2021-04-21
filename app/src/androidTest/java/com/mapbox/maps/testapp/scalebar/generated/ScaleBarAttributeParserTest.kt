// This file is generated.

package com.mapbox.maps.testapp.scalebar.generated

import android.graphics.Color
import android.view.Gravity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.R
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
class ScaleBarAttributeParserTest : BaseMapTest() {
  override fun initialiseMapView() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        it.setContentView(com.mapbox.maps.testapp.R.layout.generated_test_scalebar)
        mapView = it.findViewById(R.id.mapView)
      }
    }
  }

  @Test
  fun testAttributeParser() {
    assertEquals(
      "enabled test failed..",
      false,
      mapView.scalebar.getSettings().enabled
    )
    assertEquals(
      "position test failed..",
      Gravity.BOTTOM or Gravity.END,
      mapView.scalebar.getSettings().position
    )
    assertEquals(
      "marginLeft test failed..",
      10.0f * pixelRatio,
      mapView.scalebar.getSettings().marginLeft
    )
    assertEquals(
      "marginTop test failed..",
      10.0f * pixelRatio,
      mapView.scalebar.getSettings().marginTop
    )
    assertEquals(
      "marginRight test failed..",
      10.0f * pixelRatio,
      mapView.scalebar.getSettings().marginRight
    )
    assertEquals(
      "marginBottom test failed..",
      10.0f * pixelRatio,
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
      Color.BLACK,
      mapView.scalebar.getSettings().secondaryColor
    )
    assertEquals(
      "borderWidth test failed..",
      10.0f * pixelRatio,
      mapView.scalebar.getSettings().borderWidth
    )
    assertEquals(
      "height test failed..",
      10.0f * pixelRatio,
      mapView.scalebar.getSettings().height
    )
    assertEquals(
      "textBarMargin test failed..",
      10.0f * pixelRatio,
      mapView.scalebar.getSettings().textBarMargin
    )
    assertEquals(
      "textBorderWidth test failed..",
      10.0f * pixelRatio,
      mapView.scalebar.getSettings().textBorderWidth
    )
    assertEquals(
      "textSize test failed..",
      10.0f * pixelRatio,
      mapView.scalebar.getSettings().textSize
    )
    assertEquals(
      "isMetricUnits test failed..",
      false,
      mapView.scalebar.getSettings().isMetricUnits
    )
    assertEquals(
      "refreshInterval test failed..",
      1000000L,
      mapView.scalebar.getSettings().refreshInterval
    )
    assertEquals(
      "showTextBorder test failed..",
      false,
      mapView.scalebar.getSettings().showTextBorder
    )
    assertEquals(
      "ratio test failed..",
      0.9f,
      mapView.scalebar.getSettings().ratio
    )
  }
}

// End of generated file.