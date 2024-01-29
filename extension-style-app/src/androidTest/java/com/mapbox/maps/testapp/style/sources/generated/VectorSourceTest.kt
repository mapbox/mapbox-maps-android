// This file is generated.

package com.mapbox.maps.testapp.style.sources.generated

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.TileCacheBudget
import com.mapbox.maps.TileCacheBudgetInMegabytes
import com.mapbox.maps.extension.style.sources.TileSet
import com.mapbox.maps.extension.style.sources.generated.*
import com.mapbox.maps.extension.style.types.PromoteId
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for VectorSource.
 */
@RunWith(AndroidJUnit4::class)
class VectorSourceTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun urlTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
      url("abc")
    }
    setupSource(testSource)
    assertEquals("abc", testSource.url)
  }

  @Test
  @UiThreadTest
  fun urlAfterBindTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.url("abc")
    assertEquals("abc", testSource.url)
  }

  @Test
  @UiThreadTest
  @Ignore("https://github.com/mapbox/mapbox-maps-android/issues/499")
  fun tilesTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
      tiles(listOf("a", "b", "c"))
    }
    setupSource(testSource)
    assertEquals(listOf("a", "b", "c"), testSource.tiles)
  }

  @Test
  @UiThreadTest
  fun tilesAfterBindTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.tiles(listOf("a", "b", "c"))
    assertEquals(listOf("a", "b", "c"), testSource.tiles)
  }

  @Test
  @UiThreadTest
  @Ignore("https://github.com/mapbox/mapbox-maps-android/issues/499")
  fun boundsTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
      bounds(listOf(0.0, 1.0, 2.0, 3.0))
    }
    setupSource(testSource)
    assertEquals(listOf(0.0, 1.0, 2.0, 3.0), testSource.bounds)
  }

  @Test
  @UiThreadTest
  @Ignore("https://github.com/mapbox/mapbox-maps-android/issues/499")
  fun schemeTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
      scheme(Scheme.XYZ)
    }
    setupSource(testSource)
    assertEquals(Scheme.XYZ, testSource.scheme)
  }

  @Test
  @UiThreadTest
  fun minzoomTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
      minzoom(1L)
    }
    setupSource(testSource)
    assertEquals(1L, testSource.minzoom)
  }

  @Test
  @UiThreadTest
  fun minzoomAfterBindTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.minzoom(1L)
    assertEquals(1L, testSource.minzoom)
  }

  @Test
  @UiThreadTest
  fun maxzoomTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
      maxzoom(1L)
    }
    setupSource(testSource)
    assertEquals(1L, testSource.maxzoom)
  }

  @Test
  @UiThreadTest
  fun maxzoomAfterBindTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.maxzoom(1L)
    assertEquals(1L, testSource.maxzoom)
  }

  @Test
  @UiThreadTest
  @Ignore("https://github.com/mapbox/mapbox-maps-android/issues/499")
  fun attributionTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
      attribution("abc")
    }
    setupSource(testSource)
    assertEquals("abc", testSource.attribution)
  }

  @Test
  @UiThreadTest
  fun promoteIdTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
      promoteId(PromoteId(propertyName = "abc"))
    }
    setupSource(testSource)
    assertEquals(PromoteId(propertyName = "abc"), testSource.promoteId)
  }

  @Test
  @UiThreadTest
  fun volatileTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
      volatile(true)
    }
    setupSource(testSource)
    assertEquals(true, testSource.volatile)
  }

  @Test
  @UiThreadTest
  fun volatileAfterBindTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.volatile(true)
    assertEquals(true, testSource.volatile)
  }

  @Test
  @UiThreadTest
  fun prefetchZoomDeltaTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
      prefetchZoomDelta(1L)
    }
    setupSource(testSource)
    assertEquals(1L, testSource.prefetchZoomDelta)
  }

  @Test
  @UiThreadTest
  fun prefetchZoomDeltaAfterBindTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.prefetchZoomDelta(1L)
    assertEquals(1L, testSource.prefetchZoomDelta)
  }

  @Test
  @UiThreadTest
  fun tileCacheBudgetTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
      tileCacheBudget(TileCacheBudget(TileCacheBudgetInMegabytes(100)))
    }
    setupSource(testSource)
    val tileCacheBudget = testSource.tileCacheBudget!!
    assertEquals(TileCacheBudget.Type.TILE_CACHE_BUDGET_IN_MEGABYTES, tileCacheBudget.typeInfo)
    assertEquals(100L, tileCacheBudget.tileCacheBudgetInMegabytes.size)
  }

  @Test
  @UiThreadTest
  fun tileCacheBudgetAfterBindTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.tileCacheBudget(TileCacheBudget(TileCacheBudgetInMegabytes(100)))
    val tileCacheBudget = testSource.tileCacheBudget!!
    assertEquals(TileCacheBudget.Type.TILE_CACHE_BUDGET_IN_MEGABYTES, tileCacheBudget.typeInfo)
    assertEquals(100L, tileCacheBudget.tileCacheBudgetInMegabytes.size)
  }

  @Test
  @UiThreadTest
  fun minimumTileUpdateIntervalTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
      minimumTileUpdateInterval(1.0)
    }
    setupSource(testSource)
    assertEquals(1.0, testSource.minimumTileUpdateInterval)
  }

  @Test
  @UiThreadTest
  fun minimumTileUpdateIntervalAfterBindTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.minimumTileUpdateInterval(1.0)
    assertEquals(1.0, testSource.minimumTileUpdateInterval)
  }

  @Test
  @UiThreadTest
  fun maxOverscaleFactorForParentTilesTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
      maxOverscaleFactorForParentTiles(1L)
    }
    setupSource(testSource)
    assertEquals(1L, testSource.maxOverscaleFactorForParentTiles)
  }

  @Test
  @UiThreadTest
  fun maxOverscaleFactorForParentTilesAfterBindTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.maxOverscaleFactorForParentTiles(1L)
    assertEquals(1L, testSource.maxOverscaleFactorForParentTiles)
  }

  @Test
  @UiThreadTest
  fun tileRequestsDelayTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
      tileRequestsDelay(1.0)
    }
    setupSource(testSource)
    assertEquals(1.0, testSource.tileRequestsDelay)
  }

  @Test
  @UiThreadTest
  fun tileRequestsDelayAfterBindTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.tileRequestsDelay(1.0)
    assertEquals(1.0, testSource.tileRequestsDelay)
  }

  @Test
  @UiThreadTest
  fun tileNetworkRequestsDelayTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
      tileNetworkRequestsDelay(1.0)
    }
    setupSource(testSource)
    assertEquals(1.0, testSource.tileNetworkRequestsDelay)
  }

  @Test
  @UiThreadTest
  fun tileNetworkRequestsDelayAfterBindTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
    }
    setupSource(testSource)
    testSource.tileNetworkRequestsDelay(1.0)
    assertEquals(1.0, testSource.tileNetworkRequestsDelay)
  }

  @Test
  @UiThreadTest
  fun tileSetTest() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
      tileSet(TileSet.Builder("abc", listOf("a", "b", "c")).build())
    }
    setupSource(testSource)
  }

  @Test
  @UiThreadTest
  fun tileSetTestDsl() {
    val testSource = vectorSource(SOURCE_ID) {
      url(TEST_URI)
      tileSet("abc", listOf("a", "b", "c")) {
        attribution("testattribution")
      }
    }
    setupSource(testSource)
  }

  // Default source properties getter tests

  @Test
  @UiThreadTest
  fun defaultSourcePropertiesTest() {
    assertNotNull("defaultScheme should not be null", VectorSource.defaultScheme)
    assertNotNull("defaultMinzoom should not be null", VectorSource.defaultMinzoom)
    assertNotNull("defaultMaxzoom should not be null", VectorSource.defaultMaxzoom)
    assertNotNull("defaultVolatile should not be null", VectorSource.defaultVolatile)
    assertNotNull("defaultPrefetchZoomDelta should not be null", VectorSource.defaultPrefetchZoomDelta)
    assertNotNull("defaultMinimumTileUpdateInterval should not be null", VectorSource.defaultMinimumTileUpdateInterval)
    assertNotNull("defaultTileRequestsDelay should not be null", VectorSource.defaultTileRequestsDelay)
    assertNotNull("defaultTileNetworkRequestsDelay should not be null", VectorSource.defaultTileNetworkRequestsDelay)
  }

  private companion object {
    const val TEST_URI = "https://raw.githubusercontent.com/mapbox/mapbox-gl-native-android/master/MapboxGLAndroidSDKTestApp/src/main/assets/earthquakes.geojson"
    const val SOURCE_ID = "testId"
  }
}

// End of generated file.