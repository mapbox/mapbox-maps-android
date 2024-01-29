package com.mapbox.maps.extension.style.sources

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.utils.TypeUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CustomGeometrySourceTest {
  private val style = mockk<MapboxStyleManager>(relaxUnitFun = true, relaxed = true)
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)

  private val tileFunctionCallback: TileFunctionCallback = mockk()
  private val tileOptions: TileOptions = mockk()
  private val styleProperty = mockk<StylePropertyValue>()

  @Before
  fun prepareTest() {
    every { style.addStyleCustomGeometrySource(any(), any()) } returns expected
    every { style.setStyleCustomGeometrySourceTileData(any(), any(), any()) } returns expected
    every { style.invalidateStyleCustomGeometrySourceRegion(any(), any()) } returns expected
    every { style.invalidateStyleCustomGeometrySourceTile(any(), any()) } returns expected

    every { expected.error } returns null

    every { styleProperty.kind } returns StylePropertyValueKind.CONSTANT
    every { style.getStyleSourceProperty(any(), any()) } returns styleProperty
    every { style.setStyleSourceProperty(any(), any(), any()) } returns expected

    testSource = customGeometrySource("testId") {
      fetchTileFunction(tileFunctionCallback)
      cancelTileFunction(tileFunctionCallback)
      minZoom(0)
      maxZoom(20)
      tileOptions(tileOptions)
    }
  }

  private lateinit var testSource: CustomGeometrySource

  @Test
  fun bindTest() {
    testSource.bindTo(style)
    verify { style.addStyleCustomGeometrySource("testId", any()) }
  }

  @Test
  fun setTileDataTest() {
    val tileData = mutableListOf<Feature>(Feature.fromGeometry(Point.fromLngLat(0.0, 0.0)))
    val tileID: CanonicalTileID = mockk()
    testSource.bindTo(style)
    testSource.setTileData(tileID, tileData)
    verify { style.setStyleCustomGeometrySourceTileData("testId", tileID, tileData) }
  }

  @Test(expected = MapboxStyleException::class)
  fun setTileDataBeforeBindTest() {
    val tileData = mutableListOf<Feature>(Feature.fromGeometry(Point.fromLngLat(0.0, 0.0)))
    val tileID: CanonicalTileID = mockk()
    testSource.setTileData(tileID, tileData)
    verify { style.setStyleCustomGeometrySourceTileData("testId", tileID, tileData) }
  }

  @Test
  fun invalidateRegionTest() {
    val coordinateBounds: CoordinateBounds = mockk()
    testSource.bindTo(style)
    testSource.invalidateRegion(coordinateBounds)
    verify { style.invalidateStyleCustomGeometrySourceRegion("testId", coordinateBounds) }
  }

  @Test(expected = MapboxStyleException::class)
  fun invalidateRegionBeforeBindTest() {
    val coordinateBounds: CoordinateBounds = mockk()
    testSource.invalidateRegion(coordinateBounds)
    verify { style.invalidateStyleCustomGeometrySourceRegion("testId", coordinateBounds) }
  }

  @Test
  fun invalidateTileTest() {
    val tileID: CanonicalTileID = mockk()
    testSource.bindTo(style)
    testSource.invalidateTile(tileID)
    verify { style.invalidateStyleCustomGeometrySourceTile("testId", tileID) }
  }

  @Test(expected = MapboxStyleException::class)
  fun invalidateTileBeforeBindTest() {
    val tileID: CanonicalTileID = mockk()
    testSource.invalidateTile(tileID)
    verify { style.invalidateStyleCustomGeometrySourceTile("testId", tileID) }
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