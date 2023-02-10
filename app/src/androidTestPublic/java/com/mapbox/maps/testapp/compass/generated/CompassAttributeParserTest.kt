// This file is generated.

package com.mapbox.maps.testapp.compass.generated

import android.view.Gravity
import androidx.core.graphics.drawable.toBitmap
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.R
import com.mapbox.maps.plugin.compass.compass
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
      mapView.compass.getSettings().enabled
    )
    assertEquals(
      "position test failed..",
      Gravity.BOTTOM or Gravity.END,
      mapView.compass.getSettings().position
    )
    assertEquals(
      "marginLeft test failed..",
      10.0f * pixelRatio,
      mapView.compass.getSettings().marginLeft
    )
    assertEquals(
      "marginTop test failed..",
      10.0f * pixelRatio,
      mapView.compass.getSettings().marginTop
    )
    assertEquals(
      "marginRight test failed..",
      10.0f * pixelRatio,
      mapView.compass.getSettings().marginRight
    )
    assertEquals(
      "marginBottom test failed..",
      10.0f * pixelRatio,
      mapView.compass.getSettings().marginBottom
    )
    assertEquals(
      "opacity test failed..",
      0.9f,
      mapView.compass.getSettings().opacity
    )
    assertEquals(
      "rotation test failed..",
      0.9f,
      mapView.compass.getSettings().rotation
    )
    assertEquals(
      "visibility test failed..",
      false,
      mapView.compass.getSettings().visibility
    )
    assertEquals(
      "fadeWhenFacingNorth test failed..",
      false,
      mapView.compass.getSettings().fadeWhenFacingNorth
    )
    assertEquals(
      "clickable test failed..",
      false,
      mapView.compass.getSettings().clickable
    )
    assertEquals(
      "image test failed..",
      true,
      context.resources.getDrawable(R.drawable.mapbox_logo_icon, null).toBitmap()
        .sameAs(mapView.compass.getSettings().image?.toBitmap())
    )
  }
}

// End of generated file.