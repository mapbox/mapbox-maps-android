package com.mapbox.maps.extension.style.utils

import android.graphics.Color
import com.mapbox.maps.extension.style.expressions.dsl.generated.rgba
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

// Run with Robolectric due to `Method argb in android.graphics.Color not mocked`
@RunWith(RobolectricTestRunner::class)
class ColorUtilsTest {
  @Test
  fun rgbaToColor_decimalComponent() {
    val input = "rgba(255,128.0000952303,0,0.7)"
    val result = ColorUtils.rgbaToColor(input)
    assertEquals(Color.argb((0.7 * 255).toInt(), 255, 128, 0), result)
  }

  @Test
  fun rgbaToColor_decimalComponent_floor() {
    val input = "rgba(255,128.70123,0,0.7)"
    val result = ColorUtils.rgbaToColor(input)
    assertEquals(Color.argb((0.7 * 255).toInt(), 255, 128, 0), result)
  }

  @Test
  fun rgbaExpressionToColorIntTest() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    val result = ColorUtils.rgbaExpressionToColorInt(expression)
    assertEquals(Color.RED, result)
  }

  @Test
  fun rgbaExpressionToColorStringTest() {
    val expression = rgba {
      literal(255)
      literal(0)
      literal(0)
      literal(1.0)
    }
    val result = ColorUtils.rgbaExpressionToColorString(expression)
    assertEquals("rgba(255, 0, 0, 1)", result)
  }

  @Test
  fun colorIntToRgbaExpressionTest() {
    val expression = rgba {
      literal(0)
      literal(0)
      literal(0)
      literal(1.0)
    }
    val result = ColorUtils.colorIntToRgbaExpression(Color.BLACK)
    assertEquals(expression.toString(), result.toString())
  }

  @Test
  fun colorToRgbaString() {
    val result = ColorUtils.colorToRgbaString(Color.BLUE)
    assertEquals("rgba(0, 0, 255, 1)", result)
  }

  @Test
  fun colorToRgbaArray() {
    val result = ColorUtils.colorToRgbaArray(Color.RED)
    assertTrue(floatArrayOf(255f, 0f, 0f, 1f) contentEquals result)
  }

  @Test
  fun colorToGlRgbaArray() {
    val result = ColorUtils.colorToGlRgbaArray(Color.RED)
    assertTrue(floatArrayOf(1f, 0f, 0f, 1f) contentEquals result)
  }
}