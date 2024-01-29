package com.mapbox.maps.extension.style.sources

import android.graphics.Bitmap
import com.mapbox.bindgen.DataRef
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.utils.TypeUtils
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@MapboxExperimental
class CustomRasterSourceTest {
  private val style = mockk<MapboxStyleManager>(relaxUnitFun = true, relaxed = true)
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)

  private val tileFunctionCallback: TileFunctionCallback = mockk()
  private val styleProperty = mockk<StylePropertyValue>()

  @Before
  fun prepareTest() {
    every { style.addStyleCustomRasterSource(any(), any()) } returns expected
    every { style.setStyleCustomRasterSourceTileData(any(), any(), any()) } returns expected
    every { style.invalidateStyleCustomRasterSourceRegion(any(), any()) } returns expected
    every { style.invalidateStyleCustomRasterSourceTile(any(), any()) } returns expected

    every { expected.error } returns null
    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT
    every { style.getStyleSourceProperty(any(), any()) } returns styleProperty
    every { style.setStyleSourceProperty(any(), any(), any()) } returns expected

    testSource = customRasterSource("testId") {
      fetchTileFunction(tileFunctionCallback)
      cancelTileFunction(tileFunctionCallback)
      minZoom(0)
      maxZoom(20)
      tileSize(4)
    }
  }

  private lateinit var testSource: CustomRasterSource

  @Test
  fun bindTest() {
    testSource.bindTo(style)
    verify { style.addStyleCustomRasterSource("testId", any()) }
  }

  @Test
  fun setTileDataTest() {
    val tileID: CanonicalTileID = mockk()
    val image: Image = mockk()
    testSource.bindTo(style)
    testSource.setTileData(tileID, image)
    verify { style.setStyleCustomRasterSourceTileData("testId", tileID, image) }
  }

  @Test(expected = MapboxStyleException::class)
  fun setTileDataBeforeBindTest() {
    val tileID: CanonicalTileID = mockk()
    val image: Image = mockk()
    testSource.setTileData(tileID, image)
    verify { style.setStyleCustomRasterSourceTileData("testId", tileID, image) }
  }

  @Test
  fun setTileDataBitmapTest() {
    val tileID: CanonicalTileID = mockk()
    val bitmap = mockk<Bitmap>()
    val bitmapWidth = 64
    val bitmapHeight = 64
    val bitmapByteCount = bitmapWidth * bitmapHeight * 4
    every { bitmap.config } returns Bitmap.Config.ARGB_8888
    every { bitmap.byteCount } returns bitmapByteCount
    every { bitmap.width } returns bitmapWidth
    every { bitmap.height } returns bitmapHeight
    every { bitmap.copyPixelsToBuffer(any()) } just Runs
    mockkStatic(DataRef::class)
    val nativeDataRef = mockk<DataRef>(relaxed = true)
    every { DataRef.allocateNative(bitmapByteCount) } returns nativeDataRef
    testSource.bindTo(style)
    testSource.setTileData(tileID, bitmap)
    verify {
      style.setStyleCustomRasterSourceTileData(
        "testId",
        tileID,
        Image(
          bitmapWidth,
          bitmapHeight,
          nativeDataRef
        )
      )
    }
    unmockkStatic(DataRef::class)
  }

  @Test
  fun invalidateRegionTest() {
    val coordinateBounds: CoordinateBounds = mockk()
    testSource.bindTo(style)
    testSource.invalidateRegion(coordinateBounds)
    verify { style.invalidateStyleCustomRasterSourceRegion("testId", coordinateBounds) }
  }

  @Test(expected = MapboxStyleException::class)
  fun invalidateRegionBeforeBindTest() {
    val coordinateBounds: CoordinateBounds = mockk()
    testSource.invalidateRegion(coordinateBounds)
    verify { style.invalidateStyleCustomRasterSourceRegion("testId", coordinateBounds) }
  }

  @Test
  fun invalidateTileTest() {
    val tileID: CanonicalTileID = mockk()
    testSource.bindTo(style)
    testSource.invalidateTile(tileID)
    verify { style.invalidateStyleCustomRasterSourceTile("testId", tileID) }
  }

  @Test(expected = MapboxStyleException::class)
  fun invalidateTileBeforeBindTest() {
    val tileID: CanonicalTileID = mockk()
    testSource.invalidateTile(tileID)
    verify { style.invalidateStyleCustomRasterSourceTile("testId", tileID) }
  }

  @Test
  fun tileCacheBudgetSet() {
    val valueSlot = slot<Value>()
    testSource.bindTo(style)
    testSource.setTileCacheBudget(TileCacheBudget(TileCacheBudgetInMegabytes(100)))
    verify { style.setStyleSourceProperty("testId", "tile-cache-budget", capture(valueSlot)) }
    Assert.assertEquals("{megabytes=100}", valueSlot.captured.toString())
  }

  @Test
  fun tileCacheBudgetGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(TileCacheBudget(TileCacheBudgetInMegabytes(100)))
    testSource.bindTo(style)
    val tileCacheBudget = testSource.tileCacheBudget!!
    Assert.assertEquals(TileCacheBudget.Type.TILE_CACHE_BUDGET_IN_MEGABYTES, tileCacheBudget.typeInfo)
    Assert.assertEquals(100L, tileCacheBudget.tileCacheBudgetInMegabytes.size)
    verify { style.getStyleSourceProperty("testId", "tile-cache-budget") }
  }
}