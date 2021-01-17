// This file is generated.

package com.mapbox.maps.testapp.compass.generated

import android.view.Gravity
import androidx.core.graphics.drawable.toBitmap
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.R
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
class CompassAttributeParserTest : BaseMapTest() {
  override fun initialiseMapView() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        it.setContentView(com.mapbox.maps.testapp.R.layout.generated_test_compass)
        mapView = it.findViewById(R.id.mapView)
      }
    }
  }

  @Test
  fun testAttributeParser() {
    assertEquals(
      "enabled test failed..",
      false,
      mapView.getCompassPlugin().getSettings().enabled
    )
    assertEquals(
      "position test failed..",
      Gravity.BOTTOM or Gravity.END,
      mapView.getCompassPlugin().getSettings().position
    )
    assertEquals(
      "marginLeft test failed..",
      10.0f * pixelRatio,
      mapView.getCompassPlugin().getSettings().marginLeft
    )
    assertEquals(
      "marginTop test failed..",
      10.0f * pixelRatio,
      mapView.getCompassPlugin().getSettings().marginTop
    )
    assertEquals(
      "marginRight test failed..",
      10.0f * pixelRatio,
      mapView.getCompassPlugin().getSettings().marginRight
    )
    assertEquals(
      "marginBottom test failed..",
      10.0f * pixelRatio,
      mapView.getCompassPlugin().getSettings().marginBottom
    )
    assertEquals(
      "opacity test failed..",
      0.9f,
      mapView.getCompassPlugin().getSettings().opacity
    )
    assertEquals(
      "rotation test failed..",
      0.9f,
      mapView.getCompassPlugin().getSettings().rotation
    )
    assertEquals(
      "visibility test failed..",
      false,
      mapView.getCompassPlugin().getSettings().visibility
    )
    assertEquals(
      "fadeWhenFacingNorth test failed..",
      false,
      mapView.getCompassPlugin().getSettings().fadeWhenFacingNorth
    )
    assertEquals(
      "clickable test failed..",
      false,
      mapView.getCompassPlugin().getSettings().clickable
    )
    assertEquals(
      "image test failed..",
      true,
      context.resources.getDrawable(R.drawable.mapbox_logo_icon, null).toBitmap()
        .sameAs(mapView.getCompassPlugin().getSettings().image?.toBitmap())
    )
  }
}

// End of generated file.