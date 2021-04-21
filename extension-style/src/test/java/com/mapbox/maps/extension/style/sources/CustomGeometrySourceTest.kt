package com.mapbox.maps.extension.style.sources

import com.mapbox.bindgen.Expected
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.StyleInterface
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class CustomGeometrySourceTest {
  private val style = mockk<StyleInterface>(relaxUnitFun = true, relaxed = true)
  private val expected = mockk<Expected<Void, String>>(relaxUnitFun = true, relaxed = true)

  private val fetchTileFunctionCallback: FetchTileFunctionCallback = mockk()
  private val cancelTileFunctionCallback: CancelTileFunctionCallback = mockk()
  private val zoomRange: List<Byte> = mockk()
  private val tileOptions: TileOptions = mockk()

  @Before
  fun prepareTest() {
    every { style.addStyleCustomGeometrySource(any(), any()) } returns expected
    every { style.setStyleCustomGeometrySourceTileData(any(), any(), any()) } returns expected
    every { style.invalidateStyleCustomGeometrySourceRegion(any(), any()) } returns expected
    every { style.invalidateStyleCustomGeometrySourceTile(any(), any()) } returns expected

    every { expected.error } returns null
  }

  @Test
  fun bindTest() {
    val testSource = customGeometrySource("testId") {
      fetchTileFunction(fetchTileFunctionCallback)
      cancelTileFunction(cancelTileFunctionCallback)
      zoomRange(zoomRange)
      tileOptions(tileOptions)
    }
    testSource.bindTo(style)
    verify { style.addStyleCustomGeometrySource("testId", any()) }
  }

  @Test
  fun setTileDataTest() {
    val tileData = mutableListOf<Feature>(Feature.fromGeometry(Point.fromLngLat(0.0, 0.0)))
    val testSource = customGeometrySource("testId") {
      fetchTileFunction(fetchTileFunctionCallback)
      cancelTileFunction(cancelTileFunctionCallback)
      zoomRange(zoomRange)
      tileOptions(tileOptions)
    }
    val tileID: CanonicalTileID = mockk()
    testSource.bindTo(style)
    testSource.setTileData(tileID, tileData)
    verify { style.setStyleCustomGeometrySourceTileData("testId", tileID, tileData) }
  }

  @Test(expected = RuntimeException::class)
  fun setTileDataBeforeBindTest() {
    val tileData = mutableListOf<Feature>(Feature.fromGeometry(Point.fromLngLat(0.0, 0.0)))
    val testSource = customGeometrySource("testId") {
      fetchTileFunction(fetchTileFunctionCallback)
      cancelTileFunction(cancelTileFunctionCallback)
      zoomRange(zoomRange)
      tileOptions(tileOptions)
    }
    val tileID: CanonicalTileID = mockk()
    testSource.setTileData(tileID, tileData)
    verify { style.setStyleCustomGeometrySourceTileData("testId", tileID, tileData) }
  }

  @Test
  fun invalidRegionTest() {
    val testSource = customGeometrySource("testId") {
      fetchTileFunction(fetchTileFunctionCallback)
      cancelTileFunction(cancelTileFunctionCallback)
      zoomRange(zoomRange)
      tileOptions(tileOptions)
    }
    val coordinateBounds: CoordinateBounds = mockk()
    testSource.bindTo(style)
    testSource.invalidRegion(coordinateBounds)
    verify { style.invalidateStyleCustomGeometrySourceRegion("testId", coordinateBounds) }
  }

  @Test(expected = RuntimeException::class)
  fun invalidRegionBeforeBindTest() {
    val testSource = customGeometrySource("testId") {
      fetchTileFunction(fetchTileFunctionCallback)
      cancelTileFunction(cancelTileFunctionCallback)
      zoomRange(zoomRange)
      tileOptions(tileOptions)
    }
    val coordinateBounds: CoordinateBounds = mockk()
    testSource.invalidRegion(coordinateBounds)
    verify { style.invalidateStyleCustomGeometrySourceRegion("testId", coordinateBounds) }
  }

  @Test
  fun invalidTileTest() {
    val testSource = customGeometrySource("testId") {
      fetchTileFunction(fetchTileFunctionCallback)
      cancelTileFunction(cancelTileFunctionCallback)
      zoomRange(zoomRange)
      tileOptions(tileOptions)
    }
    val tileID: CanonicalTileID = mockk()
    testSource.bindTo(style)
    testSource.invalidTile(tileID)
    verify { style.invalidateStyleCustomGeometrySourceTile("testId", tileID) }
  }

  @Test(expected = RuntimeException::class)
  fun invalidTileBeforeBindTest() {
    val testSource = customGeometrySource("testId") {
      fetchTileFunction(fetchTileFunctionCallback)
      cancelTileFunction(cancelTileFunctionCallback)
      zoomRange(zoomRange)
      tileOptions(tileOptions)
    }
    val tileID: CanonicalTileID = mockk()
    testSource.invalidTile(tileID)
    verify { style.invalidateStyleCustomGeometrySourceTile("testId", tileID) }
  }
}