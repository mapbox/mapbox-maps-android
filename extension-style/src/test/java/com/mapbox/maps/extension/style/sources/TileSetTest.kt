package com.mapbox.maps.extension.style.sources

import com.mapbox.maps.extension.style.sources.generated.Encoding
import com.mapbox.maps.extension.style.sources.generated.Scheme
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TileSetTest {
  lateinit var builder: TileSet.Builder

  @Before
  fun prepareTest() {
    builder = TileSet.Builder("abc", listOf("a", "b", "c"))
  }

  @After
  fun cleanupTest() {
    builder.parameters.clear()
  }
  @Test
  fun tileSetBuilderTest() {
    val tileset = builder.build()
    assertEquals(tileset["tilejson"].toString(), "abc")
    assertEquals(tileset["tiles"].toString(), "[a, b, c]")
  }
  @Test
  fun nameTest() {
    val tileset = builder.name("testName").build()
    assertEquals(tileset["name"].toString(), "testName")
  }
  @Test
  fun descriptionTest() {
    val tileset = builder.description("testdescription").build()
    assertEquals(tileset["description"].toString(), "testdescription")
  }
  @Test
  fun versionTest() {
    val tileset = builder.version("testversion").build()
    assertEquals(tileset["version"].toString(), "testversion")
  }
  @Test
  fun attributionTest() {
    val tileset = builder.attribution("testattribution").build()
    assertEquals(tileset["attribution"].toString(), "testattribution")
  }
  @Test
  fun templateTest() {
    val tileset = builder.template("testtemplate").build()
    assertEquals(tileset["template"].toString(), "testtemplate")
  }
  @Test
  fun legendTest() {
    val tileset = builder.legend("testlegend").build()
    assertEquals(tileset["legend"].toString(), "testlegend")
  }
  @Test
  fun schemeTestTMS() {
    val tileset = builder.scheme(Scheme.TMS).build()
    assertEquals(tileset["scheme"].toString(), "tms")
  }
  @Test
  fun schemeTestXYZ() {
    val tileset = builder.scheme(Scheme.XYZ).build()
    assertEquals(tileset["scheme"].toString(), "xyz")
  }
  @Test
  fun gridsTest() {
    val tileset = builder.grids(listOf("abc", "def")).build()
    assertEquals(tileset["grids"].toString(), "[abc, def]")
  }
  @Test
  fun dataTest() {
    val tileset = builder.data(listOf("abc", "def")).build()
    assertEquals(tileset["data"].toString(), "[abc, def]")
  }
  @Test
  fun minZoomTest() {
    val tileset = builder.minZoom(1).build()
    assertEquals(tileset["minzoom"].toString(), "1")
  }
  @Test
  fun maxZoomTest() {
    val tileset = builder.maxZoom(1).build()
    assertEquals(tileset["maxzoom"].toString(), "1")
  }
  @Test
  fun boundsTest() {
    val tileset = builder.bounds(listOf(-180.0, -90.0, 180.0, 90.0)).build()
    assertEquals(tileset["bounds"].toString(), "[-180.0, -90.0, 180.0, 90.0]")
  }
  @Test
  fun centerTest() {
    val tileset = builder.center(listOf(-180.0, -90.0)).build()
    assertEquals(tileset["center"].toString(), "[-180.0, -90.0]")
  }
  @Test
  fun encodingTestMapbox() {
    val tileset = TileSet.RasterDemBuilder("abc", listOf("a", "b", "c"))
      .encoding(Encoding.MAPBOX).build()
    assertEquals(tileset["encoding"].toString(), "mapbox")
  }
  @Test
  fun encodingTestTerrarium() {
    val tileset = TileSet.RasterDemBuilder("abc", listOf("a", "b", "c"))
      .encoding(Encoding.TERRARIUM).build()
    assertEquals(tileset["encoding"].toString(), "terrarium")
  }
}