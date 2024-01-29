// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.StyleManager
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.TileCacheBudget
import com.mapbox.maps.TileCacheBudgetInMegabytes
import com.mapbox.maps.extension.style.ShadowStyleManager
import com.mapbox.maps.extension.style.utils.TypeUtils
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowStyleManager::class])
class RasterArraySourceTest {
  private val style = mockk<MapboxStyleManager>(relaxUnitFun = true, relaxed = true)
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

  private fun mockkStyle(style: MapboxStyleManager) {
    every { style.addStyleSource(any(), any()) } returns expected
    every { style.setStyleSourceProperty(any(), any(), any()) } returns expected
    every { style.getStyleSourceProperty(any(), any()) } returns styleProperty
  }

  @Test
  fun getTypeTest() {
    val testSource = rasterArraySource("testId") {}
    assertEquals("raster-array", testSource.getType())
  }

  @Test
  fun urlSet() {
    val testSource = rasterArraySource("testId") {
      url("abc")
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("url=abc"))
  }

  @Test
  fun urlSetAfterBind() {
    val testSource = rasterArraySource("testId") {}
    testSource.bindTo(style)
    testSource.url("abc")

    verify { style.setStyleSourceProperty("testId", "url", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "abc")
  }

  @Test
  fun urlGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("abc")
    val testSource = rasterArraySource("testId") {}
    testSource.bindTo(style)

    assertEquals("abc".toString(), testSource.url?.toString())
    verify { style.getStyleSourceProperty("testId", "url") }
  }

  @Test
  fun tilesSet() {
    val testSource = rasterArraySource("testId") {
      tiles(listOf("a", "b", "c"))
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("tiles=[a, b, c]"))
  }

  @Test
  fun tilesSetAfterBind() {
    val testSource = rasterArraySource("testId") {}
    testSource.bindTo(style)
    testSource.tiles(listOf("a", "b", "c"))

    verify { style.setStyleSourceProperty("testId", "tiles", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[a, b, c]")
  }

  @Test
  fun tilesGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf("a", "b", "c"))
    val testSource = rasterArraySource("testId") {}
    testSource.bindTo(style)

    assertEquals(listOf("a", "b", "c").toString(), testSource.tiles?.toString())
    verify { style.getStyleSourceProperty("testId", "tiles") }
  }

  @Test
  fun boundsSet() {
    val testSource = rasterArraySource("testId") {
      bounds(listOf(0.0, 1.0, 2.0, 3.0))
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("bounds=[0.0, 1.0, 2.0, 3.0]"))
  }

  @Test
  fun boundsGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0, 2.0, 3.0))
    val testSource = rasterArraySource("testId") {}
    testSource.bindTo(style)

    assertEquals(listOf(0.0, 1.0, 2.0, 3.0).toString(), testSource.bounds?.toString())
    verify { style.getStyleSourceProperty("testId", "bounds") }
  }

  @Test
  fun minzoomSet() {
    val testSource = rasterArraySource("testId") {
      minzoom(1L)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("minzoom=1"))
  }

  @Test
  fun minzoomSetAfterBind() {
    val testSource = rasterArraySource("testId") {}
    testSource.bindTo(style)
    testSource.minzoom(1L)

    verify { style.setStyleSourceProperty("testId", "minzoom", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1")
  }

  @Test
  fun minzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = rasterArraySource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.minzoom?.toString())
    verify { style.getStyleSourceProperty("testId", "minzoom") }
  }

  @Test
  fun maxzoomSet() {
    val testSource = rasterArraySource("testId") {
      maxzoom(1L)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("maxzoom=1"))
  }

  @Test
  fun maxzoomSetAfterBind() {
    val testSource = rasterArraySource("testId") {}
    testSource.bindTo(style)
    testSource.maxzoom(1L)

    verify { style.setStyleSourceProperty("testId", "maxzoom", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1")
  }

  @Test
  fun maxzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = rasterArraySource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.maxzoom?.toString())
    verify { style.getStyleSourceProperty("testId", "maxzoom") }
  }

  @Test
  fun tileSizeSet() {
    val testSource = rasterArraySource("testId") {
      tileSize(1L)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("tileSize=1"))
  }

  @Test
  fun tileSizeGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = rasterArraySource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.tileSize?.toString())
    verify { style.getStyleSourceProperty("testId", "tileSize") }
  }

  @Test
  fun attributionSet() {
    val testSource = rasterArraySource("testId") {
      attribution("abc")
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("attribution=abc"))
  }

  @Test
  fun attributionGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("abc")
    val testSource = rasterArraySource("testId") {}
    testSource.bindTo(style)

    assertEquals("abc".toString(), testSource.attribution?.toString())
    verify { style.getStyleSourceProperty("testId", "attribution") }
  }

  @Test
  fun tileCacheBudgetSet() {
    val testSource = rasterArraySource("testId") {
      tileCacheBudget(TileCacheBudget(TileCacheBudgetInMegabytes(100)))
    }
    testSource.bindTo(style)

    verify { style.setStyleSourceProperty("testId", "tile-cache-budget", capture(valueSlot)) }
    assertEquals("{megabytes=100}", valueSlot.captured.toString())
  }

  @Test
  fun tileCacheBudgetSetAfterBind() {
    val testSource = rasterArraySource("testId") {}
    testSource.bindTo(style)
    testSource.tileCacheBudget(TileCacheBudget(TileCacheBudgetInMegabytes(100)))

    verify { style.setStyleSourceProperty("testId", "tile-cache-budget", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "{megabytes=100}")
  }

  @Test
  fun tileCacheBudgetGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(TileCacheBudget(TileCacheBudgetInMegabytes(100)))
    val testSource = rasterArraySource("testId") {}
    testSource.bindTo(style)

    val tileCacheBudget = testSource.tileCacheBudget!!
    assertEquals(TileCacheBudget.Type.TILE_CACHE_BUDGET_IN_MEGABYTES, tileCacheBudget.typeInfo)
    assertEquals(100L, tileCacheBudget.tileCacheBudgetInMegabytes.size)
    verify { style.getStyleSourceProperty("testId", "tile-cache-budget") }
  }
  // Default source property getters tests

  @Test
  fun defaultMinzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), RasterArraySource.defaultMinzoom?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("raster-array", "minzoom") }
  }

  @Test
  fun defaultMaxzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), RasterArraySource.defaultMaxzoom?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("raster-array", "maxzoom") }
  }
}

// End of generated file.