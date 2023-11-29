package com.mapbox.maps.extension.style.sources

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class CustomGeometrySourceTest {
  private val style = mockk<MapboxStyleManager>(relaxUnitFun = true, relaxed = true)
  private val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)

  private val tileFunctionCallback: TileFunctionCallback = mockk()
  private val tileOptions: TileOptions = mockk()

  @Before
  fun prepareTest() {
    every { style.addStyleCustomGeometrySource(any(), any()) } returns expected
    every { style.setStyleCustomGeometrySourceTileData(any(), any(), any()) } returns expected
    every { style.invalidateStyleCustomGeometrySourceRegion(any(), any()) } returns expected
    every { style.invalidateStyleCustomGeometrySourceTile(any(), any()) } returns expected

    every { expected.error } returns null

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
}