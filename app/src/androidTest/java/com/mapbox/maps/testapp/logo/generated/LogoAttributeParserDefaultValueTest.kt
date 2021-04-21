// This file is generated.

package com.mapbox.maps.testapp.logo.generated

import android.view.Gravity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
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
class LogoAttributeParserDefaultValueTest : BaseMapTest() {
  @Test
  fun testAttributeParser() {
    assertEquals(
      "enabled test failed..",
      true,
      mapView.logo.getSettings().enabled
    )
    assertEquals(
      "position test failed..",
      Gravity.BOTTOM or Gravity.START,
      mapView.logo.getSettings().position
    )
    assertEquals(
      "marginLeft test failed..",
      4f * pixelRatio,
      mapView.logo.getSettings().marginLeft
    )
    assertEquals(
      "marginTop test failed..",
      4f * pixelRatio,
      mapView.logo.getSettings().marginTop
    )
    assertEquals(
      "marginRight test failed..",
      4f * pixelRatio,
      mapView.logo.getSettings().marginRight
    )
    assertEquals(
      "marginBottom test failed..",
      4f * pixelRatio,
      mapView.logo.getSettings().marginBottom
    )
  }
}

// End of generated file.