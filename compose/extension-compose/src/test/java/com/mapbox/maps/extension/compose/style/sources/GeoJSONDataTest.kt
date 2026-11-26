package com.mapbox.maps.extension.compose.style.sources

import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class GeoJSONDataTest {

  @Test
  fun `string data is equal when contents match`() {
    assertEquals(GeoJSONData("{}"), GeoJSONData("{}"))
    assertEquals(GeoJSONData("{}").hashCode(), GeoJSONData("{}").hashCode())
  }

  @Test
  fun `string data is not equal when contents differ`() {
    assertNotEquals(GeoJSONData("a"), GeoJSONData("b"))
  }

  @Test
  fun `DEFAULT equals empty string data`() {
    assertEquals(GeoJSONData.DEFAULT, GeoJSONData(""))
  }

  @Test
  fun `geometry data is equal for the same geometry`() {
    val point = Point.fromLngLat(10.0, 20.0)

    assertEquals(GeoJSONData(point), GeoJSONData(Point.fromLngLat(10.0, 20.0)))
    assertNotEquals(GeoJSONData(point), GeoJSONData(Point.fromLngLat(30.0, 40.0)))
  }

  @Test
  fun `different underlying types are not equal`() {
    val point = Point.fromLngLat(10.0, 20.0)

    // geometry vs string, even when conceptually "about" the same thing, differ by typeInfo
    assertNotEquals(GeoJSONData(point), GeoJSONData("{}"))
    // geometry vs feature wrapping the same point
    assertNotEquals(GeoJSONData(point), GeoJSONData(Feature.fromGeometry(point)))
  }

  @Test
  fun `toString of string data returns the contents`() {
    assertEquals("hello", GeoJSONData("hello").toString())
  }
}