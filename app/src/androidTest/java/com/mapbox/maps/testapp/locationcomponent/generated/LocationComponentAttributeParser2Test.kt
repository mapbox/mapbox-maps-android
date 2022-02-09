// This file is generated.

package com.mapbox.maps.testapp.locationcomponent.generated

import android.graphics.Color
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.R
import com.mapbox.maps.plugin.locationcomponent.location2
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
class LocationComponentAttributeParser2Test : BaseMapTest() {
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
      "showAccuracyRing test failed..",
      false,
      mapView.location2.getSettings2().showAccuracyRing
    )
    assertEquals(
      "accuracyRingColor test failed..",
      Color.BLACK,
      mapView.location2.getSettings2().accuracyRingColor
    )
    assertEquals(
      "accuracyRingBorderColor test failed..",
      Color.BLACK,
      mapView.location2.getSettings2().accuracyRingBorderColor
    )
  }
}

// End of generated file.