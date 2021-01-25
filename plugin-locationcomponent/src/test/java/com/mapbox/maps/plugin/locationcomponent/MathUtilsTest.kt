package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.maps.plugin.locationcomponent.utils.MathUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.jvm.Throws

internal class MathUtilsTest {
  @Test
  @Throws(Exception::class)
  fun shortestRotation_doesReturnValueDistanceQuickestToZero() {
    var value = MathUtils.shortestRotation(0.0, 181.0)
    assertEquals(360.0, value, EPS)
    value = MathUtils.shortestRotation(0.0, 179.0)
    assertEquals(0.0, value, EPS)
    value = MathUtils.shortestRotation(0.0, 180.0)
    assertEquals(0.0, value, EPS)
  }

  @Test
  @Throws(Exception::class)
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