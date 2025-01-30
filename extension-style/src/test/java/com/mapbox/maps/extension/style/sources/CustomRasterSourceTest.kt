package com.mapbox.maps.extension.style.sources

import android.graphics.Bitmap
import com.mapbox.bindgen.DataRef
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.CanonicalTileID
import com.mapbox.maps.CustomRasterSourceTileData
import com.mapbox.maps.Image
import com.mapbox.maps.MapboxDelicateApi
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleException
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.toMapboxImage
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@MapboxExperimental
class CustomRasterSourceTest {
  private val style = mockk<MapboxStyleManager>(relaxUnitFun = true, relaxed = true)
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)

  private val styleProperty = mockk<StylePropertyValue>()
  private lateinit var testSource: CustomRasterSource

  @Before
  fun prepareTest() {
    every { style.addStyleCustomRasterSource(any(), any()) } returns expected
    every { style.setStyleCustomRasterSourceTileData(any(), any()) } returns expected

    every { expected.error } returns null
    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT
    every { style.getStyleSourceProperty(any(), any()) } returns styleProperty
    every { style.setStyleSourceProperty(any(), any(), any()) } returns expected

    testSource = customRasterSource("testId") {
      // mandatory builder argument
      clientCallback(mockk())
    }
    testSource.setMaxOverscaleFactorForParentTiles(83)
  }

  @Test
  fun bindTest() {
    testSource.bindTo(style)
    verify { style.addStyleCustomRasterSource("testId", any()) }
  }

  @Test
  fun setTileDataTest() {
    val tileData = listOf<CustomRasterSourceTileData>()
    testSource.bindTo(style)
    testSource.setTileData(tileData)
    verify { style.setStyleCustomRasterSourceTileData("testId", tileData) }
  }

  @Test(expected = MapboxStyleException::class)
  fun setTileDataBeforeBindTest() {
    val tileData = listOf<CustomRasterSourceTileData>()
    testSource.setTileData(tileData)
    verify { style.setStyleCustomRasterSourceTileData("testId", tileData) }
  }

  @OptIn(MapboxDelicateApi::class)
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
    val tileData = listOf(CustomRasterSourceTileData(tileID, bitmap.toMapboxImage()))
    testSource.setTileData(tileData)
    verify {
      style.setStyleCustomRasterSourceTileData(
        "testId",
        listOf(
          CustomRasterSourceTileData(
            tileID,
            Image(
              bitmapWidth,
              bitmapHeight,
              nativeDataRef
            )
          )
        )
      )
    }
    unmockkStatic(DataRef::class)
  }

  @Test
  fun maxOverscaleFactorForParentTilesSet() {
    testSource.bindTo(style)
    testSource.setMaxOverscaleFactorForParentTiles(83)
    verify { style.setStyleSourceProperty("testId", "max-overscale-factor-for-parent-tiles", Value.valueOf(83L)) }
  }

  @Test
  fun maxOverscaleFactorForParentTilesGet() {
    every { styleProperty.value } returns TypeUtils.wrapToValue(83)
    testSource.bindTo(style)
    val maxOverscaleFactorForParentTiles = testSource.maxOverscaleFactorForParentTiles
    Assert.assertEquals(83L, maxOverscaleFactorForParentTiles)
    verify { style.getStyleSourceProperty("testId", "max-overscale-factor-for-parent-tiles") }
  }
}