// This file is generated.

package com.mapbox.maps.testapp.locationcomponent.generated

import android.graphics.Color
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.plugin.PuckBearingSource
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
class LocationComponentAttributeParser2DefaultValueTest : BaseMapTest() {
  @Test
  fun testAttributeParser() {
    assertEquals(
      "showAccuracyRing test failed..",
      false,
      mapView.location2.getSettings2().showAccuracyRing
    )
    assertEquals(
      "accuracyRingColor test failed..",
      Color.parseColor("#4d89cff0"),
      mapView.location2.getSettings2().accuracyRingColor
    )
    assertEquals(
      "accuracyRingBorderColor test failed..",
      Color.parseColor("#4d89cff0"),
      mapView.location2.getSettings2().accuracyRingBorderColor
    )
    assertEquals(
      "puckBearingEnabled test failed..",
      true,
      mapView.location2.getSettings2().puckBearingEnabled
    )
    assertEquals(
      "puckBearingSource test failed..",
      PuckBearingSource.HEADING,
      mapView.location2.getSettings2().puckBearingSource
    )
  }
}

// End of generated file.