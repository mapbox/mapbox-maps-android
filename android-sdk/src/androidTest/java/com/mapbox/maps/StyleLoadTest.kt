package com.mapbox.maps

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.sources.generated.rasterDemSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.extension.style.terrain.generated.terrain
import junit.framework.TestCase.*
import org.junit.*
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import kotlin.random.Random

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
    countDownLatch = CountDownLatch(1)
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
          loadedStyles++
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

  @Test
  fun testTerrainDoesNotCrashOnMultipleStyleLoad() {
    fun loadStyleWithTerrain(
      uri: String = Style.DARK,
      terrainSource: String = "TERRAIN_SOURCE",
      demSourceUri: String = "mapbox://mapbox.mapbox-terrain-dem-v1",
      counter: CountDownLatch
    ) {
      mapboxMap.loadStyle(
        styleExtension = style(uri) {
          // we need terrain for the drape-mipmap parameter to be effective to reduce flickering
          +rasterDemSource(terrainSource) {
            url(demSourceUri)
            // 514 specifies padded DEM tile and provides better performance than 512 tiles.
            tileSize(514)
            maxzoom(3)
          }
          +terrain(terrainSource) {
            exaggeration(literal(0))
          }
        }
      )
      counter.countDown()
    }

    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.onStart()

        countDownLatch = CountDownLatch(300)
        for (i in 0 until 100) {
          loadStyleWithTerrain(counter = countDownLatch)
        }

        val random = Random(0)
        for (i in 0 until 100) {
          loadStyleWithTerrain(counter = countDownLatch)
          Thread.sleep(random.nextLong(from = 0, until = 5))
        }

        for (i in 0 until 100) {
          mapView.postDelayed(
            {
              loadStyleWithTerrain(counter = countDownLatch)
            },
            i.toLong()
          )
        }
      }
    }
    countDownLatch.throwExceptionOnTimeoutMs()
  }

  @After
  @UiThreadTest
  fun teardown() {
    mapView.onStop()
    mapView.onDestroy()
  }
}