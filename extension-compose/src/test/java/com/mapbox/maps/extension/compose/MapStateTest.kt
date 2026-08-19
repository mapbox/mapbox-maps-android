package com.mapbox.maps.extension.compose

import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CoordinateInfo
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.OverscaledTileID
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.TileCoverOptions
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(shadows = [ShadowLogConfiguration::class])
@RunWith(RobolectricTestRunner::class)
internal class MapStateTest {

  private lateinit var mapState: MapState
  private lateinit var mapboxMap: MapboxMap

  @Before
  fun setup() {
    mapboxMap = mockk(relaxed = true)
    mapState = MapState()
    mapState.updateMap(mapboxMap)
  }

  @Test
  fun `coordinateForPixel delegates to mapboxMap`() = runBlocking {
    val pixel = ScreenCoordinate(100.0, 200.0)
    val expected = Point.fromLngLat(10.0, 20.0)
    every { mapboxMap.coordinateForPixel(any()) } returns expected

    val result = mapState.coordinateForPixel(pixel)

    verify { mapboxMap.coordinateForPixel(eq(pixel)) }
    assertEquals(expected, result)
  }

  @Test
  fun `pixelsForCoordinates delegates to mapboxMap`() = runBlocking {
    val coordinates = listOf(Point.fromLngLat(10.0, 20.0), Point.fromLngLat(30.0, 40.0))
    val expected = listOf(ScreenCoordinate(100.0, 200.0), ScreenCoordinate(300.0, 400.0))
    every { mapboxMap.pixelsForCoordinates(any()) } returns expected

    val result = mapState.pixelsForCoordinates(coordinates)

    verify { mapboxMap.pixelsForCoordinates(eq(coordinates)) }
    assertEquals(expected, result)
  }

  @Test
  fun `coordinatesForPixels delegates to mapboxMap`() = runBlocking {
    val pixels = listOf(ScreenCoordinate(100.0, 200.0), ScreenCoordinate(300.0, 400.0))
    val expected = listOf(Point.fromLngLat(10.0, 20.0), Point.fromLngLat(30.0, 40.0))
    every { mapboxMap.coordinatesForPixels(any()) } returns expected

    val result = mapState.coordinatesForPixels(pixels)

    verify { mapboxMap.coordinatesForPixels(eq(pixels)) }
    assertEquals(expected, result)
  }

  @Test
  fun `coordinateInfoForPixel delegates to mapboxMap`() = runBlocking {
    val pixel = ScreenCoordinate(100.0, 200.0)
    val expected = mockk<CoordinateInfo>()
    every { mapboxMap.coordinateInfoForPixel(any()) } returns expected

    val result = mapState.coordinateInfoForPixel(pixel)

    verify { mapboxMap.coordinateInfoForPixel(eq(pixel)) }
    assertEquals(expected, result)
  }

  @Test
  fun `coordinatesInfoForPixels delegates to mapboxMap`() = runBlocking {
    val pixels = listOf(ScreenCoordinate(100.0, 200.0), ScreenCoordinate(300.0, 400.0))
    val expected = listOf(mockk<CoordinateInfo>(), mockk<CoordinateInfo>())
    every { mapboxMap.coordinatesInfoForPixels(any()) } returns expected

    val result = mapState.coordinatesInfoForPixels(pixels)

    verify { mapboxMap.coordinatesInfoForPixels(eq(pixels)) }
    assertEquals(expected, result)
  }

  @OptIn(MapboxExperimental::class)
  @Test
  fun `tileCover delegates to mapboxMap`() = runBlocking {
    val tileCoverOptions = TileCoverOptions.Builder().build()
    val expected = listOf<OverscaledTileID>()
    every { mapboxMap.tileCover(any(), any()) } returns expected

    val result = mapState.tileCover(tileCoverOptions)

    verify { mapboxMap.tileCover(eq(tileCoverOptions), isNull()) }
    assertEquals(expected, result)
  }

  @OptIn(MapboxExperimental::class)
  @Test
  fun `tileCover passes cameraOptions to mapboxMap`() = runBlocking {
    val tileCoverOptions = TileCoverOptions.Builder().build()
    val cameraOptions = CameraOptions.Builder().zoom(5.0).build()
    val expected = listOf<OverscaledTileID>()
    every { mapboxMap.tileCover(any(), any()) } returns expected

    val result = mapState.tileCover(tileCoverOptions, cameraOptions)

    verify { mapboxMap.tileCover(eq(tileCoverOptions), eq(cameraOptions)) }
    assertEquals(expected, result)
  }
}