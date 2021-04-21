// This file is generated.

package com.mapbox.maps.testapp.logo.generated

import android.view.Gravity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.R
import com.mapbox.maps.plugin.logo.logo
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
class LogoAttributeParserTest : BaseMapTest() {
  override fun initialiseMapView() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        it.setContentView(com.mapbox.maps.testapp.R.layout.generated_test_logo)
        mapView = it.findViewById(R.id.mapView)
      }
    }
  }

  @Test
  fun testAttributeParser() {
    assertEquals(
      "enabled test failed..",
      false,
      mapView.logo.getSettings().enabled
    )
    assertEquals(
      "position test failed..",
      Gravity.BOTTOM or Gravity.END,
      mapView.logo.getSettings().position
    )
    assertEquals(
      "marginLeft test failed..",
      10.0f * pixelRatio,
      mapView.logo.getSettings().marginLeft
    )
    assertEquals(
      "marginTop test failed..",
      10.0f * pixelRatio,
      mapView.logo.getSettings().marginTop
    )
    assertEquals(
      "marginRight test failed..",
      10.0f * pixelRatio,
      mapView.logo.getSettings().marginRight
    )
    assertEquals(
      "marginBottom test failed..",
      10.0f * pixelRatio,
      mapView.logo.getSettings().marginBottom
    )
  }
}

// End of generated file.