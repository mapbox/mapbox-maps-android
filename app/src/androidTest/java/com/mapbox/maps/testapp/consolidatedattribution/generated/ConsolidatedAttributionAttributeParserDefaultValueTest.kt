// This file is generated.

package com.mapbox.maps.testapp.consolidatedattribution.generated

import android.graphics.Color
import android.view.Gravity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.ConsolidatedAttributionCollapsedStyle
import com.mapbox.maps.plugin.ConsolidatedAttributionIntroExpansion
import com.mapbox.maps.plugin.ConsolidatedAttributionMaterial
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.consolidatedattribution.consolidatedAttribution
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
class ConsolidatedAttributionAttributeParserDefaultValueTest : BaseMapTest() {
  override fun initialiseMapView() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView = MapView(
          it,
          MapInitOptions(it, plugins = MapInitOptions.defaultPluginList + Plugin.Mapbox(Plugin.MAPBOX_CONSOLIDATED_ATTRIBUTION_PLUGIN_ID))
        )
        it.setContentView(mapView)
      }
    }
  }

  @Test
  fun testAttributeParser() {
    assertEquals(
      "enabled test failed..",
      false,
      mapView.consolidatedAttribution.getSettings().enabled
    )
    assertEquals(
      "position test failed..",
      Gravity.BOTTOM or Gravity.START,
      mapView.consolidatedAttribution.getSettings().position
    )
    assertEquals(
      "marginLeft test failed..",
      8f * pixelRatio,
      mapView.consolidatedAttribution.getSettings().marginLeft
    )
    assertEquals(
      "marginTop test failed..",
      8f * pixelRatio,
      mapView.consolidatedAttribution.getSettings().marginTop
    )
    assertEquals(
      "marginRight test failed..",
      8f * pixelRatio,
      mapView.consolidatedAttribution.getSettings().marginRight
    )
    assertEquals(
      "marginBottom test failed..",
      8f * pixelRatio,
      mapView.consolidatedAttribution.getSettings().marginBottom
    )
    assertEquals(
      "collapsedStyle test failed..",
      ConsolidatedAttributionCollapsedStyle.ICON_AND_WORDMARK,
      mapView.consolidatedAttribution.getSettings().collapsedStyle
    )
    assertEquals(
      "material test failed..",
      ConsolidatedAttributionMaterial.SOLID,
      mapView.consolidatedAttribution.getSettings().material
    )
    assertEquals(
      "introExpansion test failed..",
      ConsolidatedAttributionIntroExpansion.ONCE,
      mapView.consolidatedAttribution.getSettings().introExpansion
    )
    assertEquals(
      "tintColor test failed..",
      Color.parseColor("#00000000"),
      mapView.consolidatedAttribution.getSettings().tintColor
    )
  }
}

// End of generated file.