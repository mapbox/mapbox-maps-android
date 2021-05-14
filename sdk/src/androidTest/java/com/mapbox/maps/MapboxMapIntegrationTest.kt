package com.mapbox.maps

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Assert.assertEquals
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
      mapboxMap = mapView.getMapboxMap()
      it.frameLayout.addView(mapView)
      mapView.onStart()
    }
  }

  @UiThreadTest
  @Test
  fun testGetResourceOptions() {
    val defaultOptions = MapInitOptions.getDefaultResourceOptions(mapView.context)
    val currentOptions = mapboxMap.getResourceOptions()
    assertEquals(defaultOptions.accessToken, currentOptions.accessToken)
    assertEquals(defaultOptions.cachePath, currentOptions.cachePath)
    assertEquals(defaultOptions.cacheSize, currentOptions.cacheSize)
  }

  @UiThreadTest
  @Test
  fun testMapViewIsRenderingSupported() {
    assertEquals(true, MapView.isRenderingSupported())
  }
}