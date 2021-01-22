package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.maps.plugin.locationcomponent.utils.Utils
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.jvm.Throws

class UtilsTest {
  @Test
  @Throws(Exception::class)
  fun shortestRotation_doesReturnValueDistanceQuickestToZero() {
    var value: Float = Utils.shortestRotation(0.0f, 181.0f)
    assertEquals(360f, value)
    value = Utils.shortestRotation(0f, 179f)
    assertEquals(0f, value)
    value = Utils.shortestRotation(0f, 180f)
    assertEquals(0f, value)
  }

  @Test
  @Throws(Exception::class)
  fun shortestRotation_doesReturnValueDistanceQuickestToFifty() {
    var value: Float = Utils.shortestRotation(50f, 231f)
    assertEquals(410f, value)
    value = Utils.shortestRotation(50f, 229f)
    assertEquals(50f, value)
    value = Utils.shortestRotation(50f, 180f)
    assertEquals(50f, value)
  }
}