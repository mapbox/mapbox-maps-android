package com.mapbox.maps

import android.content.Context
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.common.MapboxOptions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MapboxMapIntegrationTest {

  @get:Rule
  val rule = ActivityScenarioRule(EmptyActivity::class.java)

  private lateinit var mapView: MapView
  private lateinit var mapboxMap: MapboxMap

  @Before
  @UiThreadTest
  fun setUp() {
    rule.scenario.onActivity {
      mapView = MapView(it)
      mapboxMap = mapView.mapboxMap
      it.frameLayout.addView(mapView)
      mapView.onStart()
    }
  }

  @UiThreadTest
  @Test
  fun testDefaultMapboxOptions() {
    val context = mapView.context
    assertEquals(context.getTokenFromResource(), MapboxOptions.accessToken)
    assertEquals("${context.filesDir.absolutePath}/.mapbox/map_data/", MapboxMapsOptions.dataPath)
    assertEquals("https://api.mapbox.com", MapboxMapsOptions.baseUrl)
    assertNotNull(MapboxMapsOptions.tileStore)
    assertEquals(TileStoreUsageMode.READ_ONLY, MapboxMapsOptions.tileStoreUsageMode)
  }

  @UiThreadTest
  @Test
  fun testMapViewIsRenderingSupported() {
    assertEquals(true, MapView.isRenderingSupported())
  }

  @UiThreadTest
  @Test
  fun testMapViewIsTerrainRenderingSupported() {
    assertEquals(true, MapView.isTerrainRenderingSupported())
  }

  private fun Context.getTokenFromResource(): String {
    val resId = resources.getIdentifier(
      "mapbox_access_token",
      "string",
      packageName
    )
    return getString(resId)
  }
}