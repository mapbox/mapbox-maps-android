// This file is generated.

package com.mapbox.maps.testapp.compass.generated

import android.view.Gravity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.plugin.compass.getCompassPlugin
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
class CompassAttributeParserDefaultValueTest : BaseMapTest() {
  @Test
  fun testAttributeParser() {
    assertEquals(
      "enabled test failed..",
      true,
      mapView.getCompassPlugin().getSettings().enabled
    )
    assertEquals(
      "position test failed..",
      Gravity.TOP or Gravity.END,
      mapView.getCompassPlugin().getSettings().position
    )
    assertEquals(
      "marginLeft test failed..",
      4f * pixelRatio,
      mapView.getCompassPlugin().getSettings().marginLeft
    )
    assertEquals(
      "marginTop test failed..",
      4f * pixelRatio,
      mapView.getCompassPlugin().getSettings().marginTop
    )
    assertEquals(
      "marginRight test failed..",
      4f * pixelRatio,
      mapView.getCompassPlugin().getSettings().marginRight
    )
    assertEquals(
      "marginBottom test failed..",
      4f * pixelRatio,
      mapView.getCompassPlugin().getSettings().marginBottom
    )
    assertEquals(
      "opacity test failed..",
      1f,
      mapView.getCompassPlugin().getSettings().opacity
    )
    assertEquals(
      "rotation test failed..",
      0f,
      mapView.getCompassPlugin().getSettings().rotation
    )
    assertEquals(
      "visibility test failed..",
      true,
      mapView.getCompassPlugin().getSettings().visibility
    )
    assertEquals(
      "fadeWhenFacingNorth test failed..",
      true,
      mapView.getCompassPlugin().getSettings().fadeWhenFacingNorth
    )
    assertEquals(
      "clickable test failed..",
      true,
      mapView.getCompassPlugin().getSettings().clickable
    )
    assertEquals(
      "image test failed..",
      null,
      mapView.getCompassPlugin().getSettings().image
    )
  }
}

// End of generated file.