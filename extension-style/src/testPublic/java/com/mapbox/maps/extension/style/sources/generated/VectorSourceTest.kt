// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.StyleManager
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.ShadowStyleManager
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.sources.TileSet
import com.mapbox.maps.extension.style.types.PromoteId
import com.mapbox.maps.extension.style.utils.TypeUtils
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowStyleManager::class])
class VectorSourceTest {
  private val style = mockk<StyleInterface>(relaxUnitFun = true, relaxed = true)
  private val valueSlot = slot<Value>()
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)
  private val expectedDelta = mockk<Expected<String, Byte>>(relaxUnitFun = true, relaxed = true)
  private val styleProperty = mockk<StylePropertyValue>()

  @Before
  fun prepareTest() {
    every { expected.error } returns null
    every { expectedDelta.error } returns null
    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT

    mockkStyle(style)

    // For default property getters
    mockkStatic(StyleManager::class)
    every { StyleManager.getStyleSourcePropertyDefaultValue(any(), any()) } returns styleProperty
  }

  private fun mockkStyle(style: StyleInterface) {
    every { style.addStyleSource(any(), any()) } returns expected
    every { style.setStyleSourceProperty(any(), any(), any()) } returns expected
    every { style.getStyleSourceProperty(any(), any()) } returns styleProperty
  }

  @Test
  fun getTypeTest() {
    val testSource = vectorSource("testId") {}
    assertEquals("vector", testSource.getType())
  }

  @Test
  fun urlSet() {
    val testSource = vectorSource("testId") {
      url("abc")
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("url=abc"))
  }

  @Test
  fun urlSetAfterBind() {
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)
    testSource.url("abc")

    verify { style.setStyleSourceProperty("testId", "url", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "abc")
  }

  @Test
  fun urlGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("abc")
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)

    assertEquals("abc".toString(), testSource.url?.toString())
    verify { style.getStyleSourceProperty("testId", "url") }
  }

  @Test
  fun tilesSet() {
    val testSource = vectorSource("testId") {
      tiles(listOf("a", "b", "c"))
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("tiles=[a, b, c]"))
  }

  @Test
  fun tilesSetAfterBind() {
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)
    testSource.tiles(listOf("a", "b", "c"))

    verify { style.setStyleSourceProperty("testId", "tiles", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[a, b, c]")
  }

  @Test
  fun tilesGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf("a", "b", "c"))
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)

    assertEquals(listOf("a", "b", "c").toString(), testSource.tiles?.toString())
    verify { style.getStyleSourceProperty("testId", "tiles") }
  }

  @Test
  fun boundsSet() {
    val testSource = vectorSource("testId") {
      bounds(listOf(0.0, 1.0, 2.0, 3.0))
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("bounds=[0.0, 1.0, 2.0, 3.0]"))
  }

  @Test
  fun boundsGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0, 2.0, 3.0))
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)

    assertEquals(listOf(0.0, 1.0, 2.0, 3.0).toString(), testSource.bounds?.toString())
    verify { style.getStyleSourceProperty("testId", "bounds") }
  }

  @Test
  fun schemeSet() {
    val testSource = vectorSource("testId") {
      scheme(Scheme.XYZ)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("scheme=xyz"))
  }

  @Test
  fun schemeGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("xyz")
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)

    assertEquals(Scheme.XYZ, testSource.scheme)
    verify { style.getStyleSourceProperty("testId", "scheme") }
  }

  @Test
  fun minzoomSet() {
    val testSource = vectorSource("testId") {
      minzoom(1L)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("minzoom=1"))
  }

  @Test
  fun minzoomSetAfterBind() {
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)
    testSource.minzoom(1L)

    verify { style.setStyleSourceProperty("testId", "minzoom", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1")
  }

  @Test
  fun minzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.minzoom?.toString())
    verify { style.getStyleSourceProperty("testId", "minzoom") }
  }

  @Test
  fun maxzoomSet() {
    val testSource = vectorSource("testId") {
      maxzoom(1L)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("maxzoom=1"))
  }

  @Test
  fun maxzoomSetAfterBind() {
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)
    testSource.maxzoom(1L)

    verify { style.setStyleSourceProperty("testId", "maxzoom", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1")
  }

  @Test
  fun maxzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.maxzoom?.toString())
    verify { style.getStyleSourceProperty("testId", "maxzoom") }
  }

  @Test
  fun attributionSet() {
    val testSource = vectorSource("testId") {
      attribution("abc")
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("attribution=abc"))
  }

  @Test
  fun attributionGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("abc")
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)

    assertEquals("abc".toString(), testSource.attribution?.toString())
    verify { style.getStyleSourceProperty("testId", "attribution") }
  }

  @Test
  fun promoteIdSet() {
    val testSource = vectorSource("testId") {
      promoteId(PromoteId(propertyName = "abc"))
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("promoteId=abc"))
  }

  @Test
  fun promoteIdGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("abc")
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)

    assertEquals(PromoteId(propertyName = "abc"), testSource.promoteId)
    verify { style.getStyleSourceProperty("testId", "promoteId") }
  }

  @Test
  fun volatileSet() {
    val testSource = vectorSource("testId") {
      volatile(true)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("volatile=true"))
  }

  @Test
  fun volatileSetAfterBind() {
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)
    testSource.volatile(true)

    verify { style.setStyleSourceProperty("testId", "volatile", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "true")
  }

  @Test
  fun volatileGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)

    assertEquals(true.toString(), testSource.volatile?.toString())
    verify { style.getStyleSourceProperty("testId", "volatile") }
  }

  @Test
  fun prefetchZoomDeltaSet() {
    val testSource = vectorSource("testId") {
      prefetchZoomDelta(1L)
    }
    testSource.bindTo(style)

    verify { style.setStyleSourceProperty("testId", "prefetch-zoom-delta", capture(valueSlot)) }
    assertEquals("1", valueSlot.captured.toString())
  }

  @Test
  fun prefetchZoomDeltaSetAfterBind() {
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)
    testSource.prefetchZoomDelta(1L)

    verify { style.setStyleSourceProperty("testId", "prefetch-zoom-delta", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1")
  }

  @Test
  fun prefetchZoomDeltaGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.prefetchZoomDelta?.toString())
    verify { style.getStyleSourceProperty("testId", "prefetch-zoom-delta") }
  }

  @Test
  fun minimumTileUpdateIntervalSet() {
    val testSource = vectorSource("testId") {
      minimumTileUpdateInterval(1.0)
    }
    testSource.bindTo(style)

    verify { style.setStyleSourceProperty("testId", "minimum-tile-update-interval", capture(valueSlot)) }
    assertEquals("1.0", valueSlot.captured.toString())
  }

  @Test
  fun minimumTileUpdateIntervalSetAfterBind() {
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)
    testSource.minimumTileUpdateInterval(1.0)

    verify { style.setStyleSourceProperty("testId", "minimum-tile-update-interval", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun minimumTileUpdateIntervalGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1.0.toString(), testSource.minimumTileUpdateInterval?.toString())
    verify { style.getStyleSourceProperty("testId", "minimum-tile-update-interval") }
  }

  @Test
  fun maxOverscaleFactorForParentTilesSet() {
    val testSource = vectorSource("testId") {
      maxOverscaleFactorForParentTiles(1L)
    }
    testSource.bindTo(style)

    verify { style.setStyleSourceProperty("testId", "max-overscale-factor-for-parent-tiles", capture(valueSlot)) }
    assertEquals("1", valueSlot.captured.toString())
  }

  @Test
  fun maxOverscaleFactorForParentTilesSetAfterBind() {
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)
    testSource.maxOverscaleFactorForParentTiles(1L)

    verify { style.setStyleSourceProperty("testId", "max-overscale-factor-for-parent-tiles", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1")
  }

  @Test
  fun maxOverscaleFactorForParentTilesGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.maxOverscaleFactorForParentTiles?.toString())
    verify { style.getStyleSourceProperty("testId", "max-overscale-factor-for-parent-tiles") }
  }

  @Test
  fun tileRequestsDelaySet() {
    val testSource = vectorSource("testId") {
      tileRequestsDelay(1.0)
    }
    testSource.bindTo(style)

    verify { style.setStyleSourceProperty("testId", "tile-requests-delay", capture(valueSlot)) }
    assertEquals("1.0", valueSlot.captured.toString())
  }

  @Test
  fun tileRequestsDelaySetAfterBind() {
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)
    testSource.tileRequestsDelay(1.0)

    verify { style.setStyleSourceProperty("testId", "tile-requests-delay", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun tileRequestsDelayGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1.0.toString(), testSource.tileRequestsDelay?.toString())
    verify { style.getStyleSourceProperty("testId", "tile-requests-delay") }
  }

  @Test
  fun tileNetworkRequestsDelaySet() {
    val testSource = vectorSource("testId") {
      tileNetworkRequestsDelay(1.0)
    }
    testSource.bindTo(style)

    verify { style.setStyleSourceProperty("testId", "tile-network-requests-delay", capture(valueSlot)) }
    assertEquals("1.0", valueSlot.captured.toString())
  }

  @Test
  fun tileNetworkRequestsDelaySetAfterBind() {
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)
    testSource.tileNetworkRequestsDelay(1.0)

    verify { style.setStyleSourceProperty("testId", "tile-network-requests-delay", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun tileNetworkRequestsDelayGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val testSource = vectorSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1.0.toString(), testSource.tileNetworkRequestsDelay?.toString())
    verify { style.getStyleSourceProperty("testId", "tile-network-requests-delay") }
  }

  @Test
  fun tileSetTest() {
    val testSource = vectorSource("testId") {
      tileSet(
        TileSet.Builder("testjson", listOf("tile1", "tile2")).description("description")
          .data(listOf("data")).build()
      )
    }
    testSource.bindTo(style)
    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("tiles="))
    assertTrue(valueSlot.captured.toString().contains("tilejson="))
    assertTrue(valueSlot.captured.toString().contains("description="))
    assertTrue(valueSlot.captured.toString().contains("data="))
  }

  @Test
  fun tileSetDslTest() {
    val testSource = vectorSource("testId") {
      tileSet(tilejson = "testjson", tiles = listOf("tile1", "tile2")) {
        description("description")
        data(listOf("data"))
      }
    }
    testSource.bindTo(style)
    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("tiles="))
    assertTrue(valueSlot.captured.toString().contains("tilejson="))
    assertTrue(valueSlot.captured.toString().contains("description="))
    assertTrue(valueSlot.captured.toString().contains("data="))
  }
  // Default source property getters tests

  @Test
  fun defaultSchemeGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("xyz")

    assertEquals(Scheme.XYZ.toString(), VectorSource.defaultScheme?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("vector", "scheme") }
  }

  @Test
  fun defaultMinzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), VectorSource.defaultMinzoom?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("vector", "minzoom") }
  }

  @Test
  fun defaultMaxzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), VectorSource.defaultMaxzoom?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("vector", "maxzoom") }
  }

  @Test
  fun defaultVolatileGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)

    assertEquals(true.toString(), VectorSource.defaultVolatile?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("vector", "volatile") }
  }

  @Test
  fun defaultPrefetchZoomDeltaGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), VectorSource.defaultPrefetchZoomDelta?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("vector", "prefetch-zoom-delta") }
  }

  @Test
  fun defaultMinimumTileUpdateIntervalGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    assertEquals(1.0.toString(), VectorSource.defaultMinimumTileUpdateInterval?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("vector", "minimum-tile-update-interval") }
  }

  @Test
  fun defaultTileRequestsDelayGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    assertEquals(1.0.toString(), VectorSource.defaultTileRequestsDelay?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("vector", "tile-requests-delay") }
  }

  @Test
  fun defaultTileNetworkRequestsDelayGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    assertEquals(1.0.toString(), VectorSource.defaultTileNetworkRequestsDelay?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("vector", "tile-network-requests-delay") }
  }
}

// End of generated file.