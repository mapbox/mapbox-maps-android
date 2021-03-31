// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.Value
import com.mapbox.maps.StyleManager
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.ShadowStyleManager
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.expressions.dsl.generated.sum
import com.mapbox.maps.extension.style.sources.TileSet
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
class RasterSourceTest {
  private val style = mockk<StyleInterface>(relaxUnitFun = true, relaxed = true)
  private val valueSlot = slot<Value>()
  private val expected = mockk<Expected<Void, String>>(relaxUnitFun = true, relaxed = true)
  private val expectedDelta = mockk<Expected<Byte, String>>(relaxUnitFun = true, relaxed = true)
  private val styleProperty = mockk<StylePropertyValue>()

  @Before
  fun prepareTest() {
    every { style.addStyleSource(any(), any()) } returns expected
    every { style.setStyleSourceProperty(any(), any(), any()) } returns expected
    every { style.getStyleSourceProperty(any(), any()) } returns styleProperty
    every { expected.error } returns null
    every { expectedDelta.error } returns null
    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT

    // For default property getters
    mockkStatic(StyleManager::class)
    every { StyleManager.getStyleSourcePropertyDefaultValue(any(), any()) } returns styleProperty
  }

  @Test
  fun getTypeTest() {
    val testSource = rasterSource("testId") {}
    assertEquals("raster", testSource.getType())
  }

  @Test
  fun urlSet() {
    val testSource = rasterSource("testId") {
      url("abc")
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("url=abc"))
  }

  @Test
  fun urlAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {
      url(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("url=[+, 2, 3]"))
  }

  @Test
  fun urlSetAfterBind() {
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)
    testSource.url("abc")

    verify { style.setStyleSourceProperty("testId", "url", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "abc")
  }

  @Test
  fun urlAsExpressionSetAfterBind() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)
    testSource.url(expression)

    verify { style.setStyleSourceProperty("testId", "url", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun urlGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("abc")
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals("abc".toString(), testSource.url?.toString())
    verify { style.getStyleSourceProperty("testId", "url") }
  }

  @Test
  fun urlAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.urlAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "url") }
  }

  @Test
  fun tilesSet() {
    val testSource = rasterSource("testId") {
      tiles(listOf("a", "b", "c"))
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("tiles=[a, b, c]"))
  }

  @Test
  fun tilesAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {
      tiles(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("tiles=[+, 2, 3]"))
  }

  @Test
  fun tilesSetAfterBind() {
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)
    testSource.tiles(listOf("a", "b", "c"))

    verify { style.setStyleSourceProperty("testId", "tiles", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[a, b, c]")
  }

  @Test
  fun tilesAsExpressionSetAfterBind() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)
    testSource.tiles(expression)

    verify { style.setStyleSourceProperty("testId", "tiles", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun tilesGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf("a", "b", "c"))
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(listOf("a", "b", "c").toString(), testSource.tiles?.toString())
    verify { style.getStyleSourceProperty("testId", "tiles") }
  }

  @Test
  fun tilesAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.tilesAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "tiles") }
  }

  @Test
  fun boundsSet() {
    val testSource = rasterSource("testId") {
      bounds(listOf(0.0, 1.0, 2.0, 3.0))
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("bounds=[0.0, 1.0, 2.0, 3.0]"))
  }

  @Test
  fun boundsAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {
      bounds(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("bounds=[+, 2, 3]"))
  }

  @Test
  fun boundsGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(listOf(0.0, 1.0, 2.0, 3.0))
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(listOf(0.0, 1.0, 2.0, 3.0).toString(), testSource.bounds?.toString())
    verify { style.getStyleSourceProperty("testId", "bounds") }
  }

  @Test
  fun boundsAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.boundsAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "bounds") }
  }

  @Test
  fun minzoomSet() {
    val testSource = rasterSource("testId") {
      minzoom(1L)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("minzoom=1"))
  }

  @Test
  fun minzoomAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {
      minzoom(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("minzoom=[+, 2, 3]"))
  }

  @Test
  fun minzoomSetAfterBind() {
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)
    testSource.minzoom(1L)

    verify { style.setStyleSourceProperty("testId", "minzoom", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1")
  }

  @Test
  fun minzoomAsExpressionSetAfterBind() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)
    testSource.minzoom(expression)

    verify { style.setStyleSourceProperty("testId", "minzoom", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun minzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.minzoom?.toString())
    verify { style.getStyleSourceProperty("testId", "minzoom") }
  }

  @Test
  fun minzoomAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.minzoomAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "minzoom") }
  }

  @Test
  fun maxzoomSet() {
    val testSource = rasterSource("testId") {
      maxzoom(1L)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("maxzoom=1"))
  }

  @Test
  fun maxzoomAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {
      maxzoom(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("maxzoom=[+, 2, 3]"))
  }

  @Test
  fun maxzoomSetAfterBind() {
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)
    testSource.maxzoom(1L)

    verify { style.setStyleSourceProperty("testId", "maxzoom", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1")
  }

  @Test
  fun maxzoomAsExpressionSetAfterBind() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)
    testSource.maxzoom(expression)

    verify { style.setStyleSourceProperty("testId", "maxzoom", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun maxzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.maxzoom?.toString())
    verify { style.getStyleSourceProperty("testId", "maxzoom") }
  }

  @Test
  fun maxzoomAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.maxzoomAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "maxzoom") }
  }

  @Test
  fun tileSizeSet() {
    val testSource = rasterSource("testId") {
      tileSize(1L)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("tileSize=1"))
  }

  @Test
  fun tileSizeAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {
      tileSize(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("tileSize=[+, 2, 3]"))
  }

  @Test
  fun tileSizeGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.tileSize?.toString())
    verify { style.getStyleSourceProperty("testId", "tileSize") }
  }

  @Test
  fun tileSizeAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.tileSizeAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "tileSize") }
  }

  @Test
  fun schemeSet() {
    val testSource = rasterSource("testId") {
      scheme(Scheme.XYZ)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("scheme=xyz"))
  }

  @Test
  fun schemeAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {
      scheme(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("scheme=[+, 2, 3]"))
  }

  @Test
  fun schemeGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("xyz")
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(Scheme.XYZ, testSource.scheme)
    verify { style.getStyleSourceProperty("testId", "scheme") }
  }

  @Test
  fun schemeAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.schemeAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "scheme") }
  }

  @Test
  fun attributionSet() {
    val testSource = rasterSource("testId") {
      attribution("abc")
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("attribution=abc"))
  }

  @Test
  fun attributionAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {
      attribution(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("attribution=[+, 2, 3]"))
  }

  @Test
  fun attributionGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("abc")
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals("abc".toString(), testSource.attribution?.toString())
    verify { style.getStyleSourceProperty("testId", "attribution") }
  }

  @Test
  fun attributionAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.attributionAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "attribution") }
  }

  @Test
  fun volatileSet() {
    val testSource = rasterSource("testId") {
      volatile(true)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("volatile=true"))
  }

  @Test
  fun volatileAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {
      volatile(expression)
    }
    testSource.bindTo(style)

    verify { style.addStyleSource("testId", capture(valueSlot)) }
    assertTrue(valueSlot.captured.toString().contains("volatile=[+, 2, 3]"))
  }

  @Test
  fun volatileSetAfterBind() {
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)
    testSource.volatile(true)

    verify { style.setStyleSourceProperty("testId", "volatile", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "true")
  }

  @Test
  fun volatileAsExpressionSetAfterBind() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)
    testSource.volatile(expression)

    verify { style.setStyleSourceProperty("testId", "volatile", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun volatileGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(true.toString(), testSource.volatile?.toString())
    verify { style.getStyleSourceProperty("testId", "volatile") }
  }

  @Test
  fun volatileAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.volatileAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "volatile") }
  }

  @Test
  fun prefetchZoomDeltaSet() {
    val testSource = rasterSource("testId") {
      prefetchZoomDelta(1L)
    }
    testSource.bindTo(style)

    verify { style.setStyleSourceProperty("testId", "prefetch-zoom-delta", capture(valueSlot)) }
    assertEquals("1", valueSlot.captured.toString())
  }

  @Test
  fun prefetchZoomDeltaAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {
      prefetchZoomDelta(expression)
    }
    testSource.bindTo(style)

    verify { style.setStyleSourceProperty("testId", "prefetch-zoom-delta", capture(valueSlot)) }
    assertEquals("[+, 2, 3]", valueSlot.captured.toString())
  }

  @Test
  fun prefetchZoomDeltaSetAfterBind() {
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)
    testSource.prefetchZoomDelta(1L)

    verify { style.setStyleSourceProperty("testId", "prefetch-zoom-delta", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1")
  }

  @Test
  fun prefetchZoomDeltaAsExpressionSetAfterBind() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)
    testSource.prefetchZoomDelta(expression)

    verify { style.setStyleSourceProperty("testId", "prefetch-zoom-delta", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun prefetchZoomDeltaGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.prefetchZoomDelta?.toString())
    verify { style.getStyleSourceProperty("testId", "prefetch-zoom-delta") }
  }

  @Test
  fun prefetchZoomDeltaAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.prefetchZoomDeltaAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "prefetch-zoom-delta") }
  }

  @Test
  fun minimumTileUpdateIntervalSet() {
    val testSource = rasterSource("testId") {
      minimumTileUpdateInterval(1.0)
    }
    testSource.bindTo(style)

    verify { style.setStyleSourceProperty("testId", "minimum-tile-update-interval", capture(valueSlot)) }
    assertEquals("1.0", valueSlot.captured.toString())
  }

  @Test
  fun minimumTileUpdateIntervalAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {
      minimumTileUpdateInterval(expression)
    }
    testSource.bindTo(style)

    verify { style.setStyleSourceProperty("testId", "minimum-tile-update-interval", capture(valueSlot)) }
    assertEquals("[+, 2, 3]", valueSlot.captured.toString())
  }

  @Test
  fun minimumTileUpdateIntervalSetAfterBind() {
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)
    testSource.minimumTileUpdateInterval(1.0)

    verify { style.setStyleSourceProperty("testId", "minimum-tile-update-interval", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1.0")
  }

  @Test
  fun minimumTileUpdateIntervalAsExpressionSetAfterBind() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)
    testSource.minimumTileUpdateInterval(expression)

    verify { style.setStyleSourceProperty("testId", "minimum-tile-update-interval", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun minimumTileUpdateIntervalGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1.0.toString(), testSource.minimumTileUpdateInterval?.toString())
    verify { style.getStyleSourceProperty("testId", "minimum-tile-update-interval") }
  }

  @Test
  fun minimumTileUpdateIntervalAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.minimumTileUpdateIntervalAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "minimum-tile-update-interval") }
  }

  @Test
  fun maxOverscaleFactorForParentTilesSet() {
    val testSource = rasterSource("testId") {
      maxOverscaleFactorForParentTiles(1L)
    }
    testSource.bindTo(style)

    verify { style.setStyleSourceProperty("testId", "max-overscale-factor-for-parent-tiles", capture(valueSlot)) }
    assertEquals("1", valueSlot.captured.toString())
  }

  @Test
  fun maxOverscaleFactorForParentTilesAsExpressionSet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {
      maxOverscaleFactorForParentTiles(expression)
    }
    testSource.bindTo(style)

    verify { style.setStyleSourceProperty("testId", "max-overscale-factor-for-parent-tiles", capture(valueSlot)) }
    assertEquals("[+, 2, 3]", valueSlot.captured.toString())
  }

  @Test
  fun maxOverscaleFactorForParentTilesSetAfterBind() {
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)
    testSource.maxOverscaleFactorForParentTiles(1L)

    verify { style.setStyleSourceProperty("testId", "max-overscale-factor-for-parent-tiles", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "1")
  }

  @Test
  fun maxOverscaleFactorForParentTilesAsExpressionSetAfterBind() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)
    testSource.maxOverscaleFactorForParentTiles(expression)

    verify { style.setStyleSourceProperty("testId", "max-overscale-factor-for-parent-tiles", capture(valueSlot)) }
    assertEquals(valueSlot.captured.toString(), "[+, 2, 3]")
  }

  @Test
  fun maxOverscaleFactorForParentTilesGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(1L.toString(), testSource.maxOverscaleFactorForParentTiles?.toString())
    verify { style.getStyleSourceProperty("testId", "max-overscale-factor-for-parent-tiles") }
  }

  @Test
  fun maxOverscaleFactorForParentTilesAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION
    val testSource = rasterSource("testId") {}
    testSource.bindTo(style)

    assertEquals(expression.toString(), testSource.maxOverscaleFactorForParentTilesAsExpression?.toString())
    verify { style.getStyleSourceProperty("testId", "max-overscale-factor-for-parent-tiles") }
  }

  @Test
  fun tileSetTest() {
    val testSource = rasterSource("testId") {
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
    val testSource = rasterSource("testId") {
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
  fun defaultMinzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), RasterSource.defaultMinzoom?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("raster", "minzoom") }
  }

  @Test
  fun defaultMinzoomAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterSource.defaultMinzoomAsExpression?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("raster", "minzoom") }
  }

  @Test
  fun defaultMaxzoomGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), RasterSource.defaultMaxzoom?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("raster", "maxzoom") }
  }

  @Test
  fun defaultMaxzoomAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterSource.defaultMaxzoomAsExpression?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("raster", "maxzoom") }
  }

  @Test
  fun defaultSchemeGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue("xyz")

    assertEquals(Scheme.XYZ.toString(), RasterSource.defaultScheme?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("raster", "scheme") }
  }

  @Test
  fun defaultSchemeAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterSource.defaultSchemeAsExpression?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("raster", "scheme") }
  }

  @Test
  fun defaultVolatileGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(true)

    assertEquals(true.toString(), RasterSource.defaultVolatile?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("raster", "volatile") }
  }

  @Test
  fun defaultVolatileAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterSource.defaultVolatileAsExpression?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("raster", "volatile") }
  }

  @Test
  fun defaultPrefetchZoomDeltaGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1L)

    assertEquals(1L.toString(), RasterSource.defaultPrefetchZoomDelta?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("raster", "prefetch-zoom-delta") }
  }

  @Test
  fun defaultPrefetchZoomDeltaAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterSource.defaultPrefetchZoomDeltaAsExpression?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("raster", "prefetch-zoom-delta") }
  }

  @Test
  fun defaultMinimumTileUpdateIntervalGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(1.0)

    assertEquals(1.0.toString(), RasterSource.defaultMinimumTileUpdateInterval?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("raster", "minimum-tile-update-interval") }
  }

  @Test
  fun defaultMinimumTileUpdateIntervalAsExpressionGet() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    every { styleProperty.value } returns TypeUtils.wrapToValue(expression)
    every { styleProperty.kind } returns StylePropertyValueKind.EXPRESSION

    assertEquals(expression.toString(), RasterSource.defaultMinimumTileUpdateIntervalAsExpression?.toString())
    verify { StyleManager.getStyleSourcePropertyDefaultValue("raster", "minimum-tile-update-interval") }
  }
}

// End of generated file.