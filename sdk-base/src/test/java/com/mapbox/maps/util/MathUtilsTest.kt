package com.mapbox.maps.util

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

internal class MathUtilsTest {

  @Test
  fun prepareOptimalBearingPathTest() {
    var value = MathUtils.prepareOptimalBearingPath(doubleArrayOf(-0.0, -10.0, 10.0, 270.0, 560.0))
    assertArrayEquals(doubleArrayOf(0.0, -10.0, 10.0, -90.0, -160.0), value, EPS)
    value = MathUtils.prepareOptimalBearingPath(doubleArrayOf(0.0, 90.0, 270.0))
    assertArrayEquals(doubleArrayOf(0.0, 90.0, 270.0), value, EPS)
    value = MathUtils.prepareOptimalBearingPath(doubleArrayOf(20.0, 720.0, 0.0))
    assertArrayEquals(doubleArrayOf(20.0, 0.0, 0.0), value, EPS)
  }

  @Test
  fun shortestRotation_doesReturnValueDistanceQuickestToZero() {
    var value = MathUtils.shortestRotation(0.0, 181.0)
    assertEquals(360.0, value, EPS)
    value = MathUtils.shortestRotation(0.0, 179.0)
    assertEquals(0.0, value, EPS)
    value = MathUtils.shortestRotation(0.0, 180.0)
    assertEquals(0.0, value, EPS)
  }

  @Test
  fun shortestRotation_doesReturnValueDistanceQuickestToFifty() {
    var value = MathUtils.shortestRotation(50.0, 231.0)
    assertEquals(410.0, value, EPS)
    value = MathUtils.shortestRotation(50.0, 229.0)
    assertEquals(50.0, value, EPS)
    value = MathUtils.shortestRotation(50.0, 180.0)
    assertEquals(50.0, value, EPS)
  }

  companion object {
    const val EPS = 0.0001
  }
}