package com.mapbox.maps

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.extension.style.style
import junit.framework.TestCase.*
import org.junit.*
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
@LargeTest
class StyleLoadTest {

  @get:Rule
  val rule = ActivityScenarioRule(EmptyActivity::class.java)

  private lateinit var mapView: MapView
  private lateinit var mapboxMap: MapboxMap
  private lateinit var countDownLatch: CountDownLatch

  @Before
  @UiThreadTest
  fun setUp() {
    rule.scenario.onActivity {
      mapView = MapView(it, MapInitOptions(it, plugins = listOf()))
      mapboxMap = mapView.getMapboxMap()
      it.frameLayout.addView(mapView)
    }
  }

  @Test
  fun testStyleNull() {
    countDownLatch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.onStart()
        mapboxMap.getStyle {
          countDownLatch.countDown()
        }
      }
    }
    countDownLatch.throwExceptionOnTimeoutMs()
  }

  @Test
  fun testStyleIsValid() {
    countDownLatch = CountDownLatch(2)
    val styles = mutableListOf<Style>()
    rule.scenario.onActivity { style ->
      style.runOnUiThread {
        mapboxMap.loadStyleUri(
          Style.MAPBOX_STREETS
        ) { newStyle ->
          styles.add(newStyle)
          countDownLatch.countDown()

          mapboxMap.loadStyleUri(
            Style.MAPBOX_STREETS
          ) { newStyle2 ->
            styles.add(newStyle2)
            countDownLatch.countDown()
          }
        }
        mapView.onStart()
      }
    }
    countDownLatch.throwExceptionOnTimeoutMs()

    assertFalse(styles[0].isValid())
    assertTrue(styles[1].isValid())

    mapboxMap.onDestroy()

    assertFalse(styles[0].isValid())
    assertFalse(styles[1].isValid())
  }

  @Test
  fun testStyleAsyncGetter() {
    countDownLatch = CountDownLatch(2)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.getStyle { style ->
          assertTrue("Style should be fully loaded", style.isStyleLoaded)
          countDownLatch.countDown()
        }

        mapboxMap.loadStyleUri(
          Style.MAPBOX_STREETS
        ) { style ->
          assertTrue("Style should be fully loaded", style.isStyleLoaded)
          countDownLatch.countDown()
        }
        mapView.onStart()
      }
    }
    countDownLatch.throwExceptionOnTimeoutMs(10_000)
  }

  @Test
  fun testStyleIsLoadedOnStyleChange() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.loadStyleUri(
          Style.MAPBOX_STREETS
        ) { style ->
          assertTrue("Style should be fully loaded", style.isStyleLoaded)
          mapboxMap.loadStyleUri(Style.SATELLITE) { style2 ->
            assertTrue("Style should be still fully loaded although its flaw in our current implementation", style.isStyleLoaded)
            assertTrue("Style should be fully loaded again", style2.isStyleLoaded)
            assertTrue("New style should be valid", style2.isValid())
            assertFalse("Old style should not be valid", style.isValid())
            countDownLatch.countDown()
          }
          assertFalse("Map shouldn't be fully loaded", style.isStyleLoaded)
        }
        mapView.onStart()
      }
    }
    countDownLatch.throwExceptionOnTimeoutMs(30_000)
  }

  @Test
  fun testLoadMultipleStylesInARow() {
    countDownLatch = CountDownLatch(1)
    var loadedStyles = 0
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) { loadedStyles++ }
        mapboxMap.loadStyle(style("") {}) { loadedStyles++ }
        mapboxMap.loadStyleUri(Style.SATELLITE) { loadedStyles++ }
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) { loadedStyles++ }
        mapboxMap.loadStyle(style("") {}) { loadedStyles++ }
        mapboxMap.loadStyleJson(
          """
            {
              "version": 8,
              "name": "Land",
              "metadata": {
                "mapbox:autocomposite": true
              },
              "sources": {
                "composite": {
                  "url": "mapbox://mapbox.mapbox-terrain-v2",
                  "type": "vector"
                }
              },
              "sprite": "mapbox://sprites/mapbox/mapbox-terrain-v2",
              "glyphs": "mapbox://fonts/mapbox/{fontstack}/{range}.pbf"
            }
          """.trimIndent()
        ) { loadedStyles++ }
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) { loadedStyles++ }
        mapboxMap.loadStyleUri(Style.SATELLITE) { loadedStyles++ }
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) { style ->
          assertTrue("Style should be fully loaded", style.isStyleLoaded)
          assertTrue("Style should be valid", style.isValid())
          countDownLatch.countDown()
        }
        mapView.onStart()
      }
    }
    countDownLatch.throwExceptionOnTimeoutMs()
    assertEquals(loadedStyles, 1)
  }

  @After
  @UiThreadTest
  fun teardown() {
    mapView.onStop()
    mapView.onDestroy()
  }
}