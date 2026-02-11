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

@OptIn(MapboxExperimental::class)
@RunWith(AndroidJUnit4::class)
@LargeTest
class SymbolScaleBehaviorTest {

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
  fun testSetSymbolScaleBehaviorWithFixedMode() {
    val expected = SymbolScaleBehavior.fixed(1.5f)
    mapboxMap.symbolScaleBehavior = expected
    val actual = mapboxMap.symbolScaleBehavior
    assertEquals(expected, actual)
  }

  @UiThreadTest
  @Test
  fun testSetSymbolScaleBehaviorWithSystemMode() {
    val expected = SymbolScaleBehavior.system
    mapboxMap.symbolScaleBehavior = expected
    val actual = mapboxMap.symbolScaleBehavior
    assertEquals(expected, actual)
  }

  @UiThreadTest
  @Test
  fun testSetSymbolScaleBehaviorWithCustomMapping() {
    // Function-based custom mapping
    val customMapping: (Float) -> Float = { systemScale ->
      when {
        systemScale <= 1.0f -> systemScale
        systemScale <= 1.5f -> 1.0f + (systemScale - 1.0f) * 0.6f
        else -> 1.5f
      }
    }
    val expected = SymbolScaleBehavior.system(customMapping)
    mapboxMap.symbolScaleBehavior = expected
    val actual = mapboxMap.symbolScaleBehavior
    // Custom mappings with functions can't be easily compared for equality,
    // but we can verify it was set and is in System mode
    assertEquals(true, actual.isSystem())
  }

  @UiThreadTest
  @Test
  fun testDefaultSymbolScaleBehavior() {
    // Verify default is Fixed(1.0)
    val defaultBehavior = mapboxMap.symbolScaleBehavior
    assertEquals(true, defaultBehavior.isFixed())
    assertEquals(1.0f, defaultBehavior.scaleFactor)
  }
}