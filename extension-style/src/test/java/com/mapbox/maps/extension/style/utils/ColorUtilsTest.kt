package com.mapbox.maps.extension.style.utils

import android.graphics.Color
import android.os.Build.VERSION_CODES
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.mapbox.maps.extension.style.expressions.dsl.generated.rgba
import com.mapbox.maps.extension.style.expressions.generated.Expression
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt

// Run with Robolectric due to `Method argb in android.graphics.Color not mocked`
@RunWith(RobolectricTestRunner::class)
// Version O is required to use Color.arg(float, float, float, float) method
@Config(sdk = [VERSION_CODES.O])
class ColorUtilsTest {
  @Test
  fun `255 RGBA strings to Color int`() {
    repeat(255) { value ->
      val input = "rgba($value,$value,$value,${value / 255F})"
      val result = ColorUtils.rgbaToColor(input)
      assertEquals(Color.argb(value, value, value, value), result)
    }
  }

  @Test
  fun `255 RGBA expressions to Color int`() {
    repeat(255) { value ->
      val input = Expression.rgba {
        this.literal(value.toLong())
        this.literal(value.toLong())
        this.literal(value.toLong())
        this.literal(value / 255.0)
      }
      val result = ColorUtils.rgbaExpressionToColorInt(input)
      assertEquals(Color.argb(value, value, value, value), result)
    }
  }

  @Test
  fun `255 RGBA expressions to Color string`() {
    repeat(255) { value ->
      val input = Expression.rgba {
        this.literal(value.toLong())
        this.literal(value.toLong())
        this.literal(value.toLong())
        this.literal(value / 255.0)
      }
      val result = ColorUtils.rgbaExpressionToColorString(input)
      val decimalFormat = NumberFormat.getNumberInstance(Locale.US) as DecimalFormat
      decimalFormat.applyPattern("#.########")
      val alphaString = decimalFormat.format(value / 255.0)
      assertEquals("rgba($value, $value, $value, $alphaString)", result)
    }
  }

  @Test
  fun `255 RGB strings to Color int`() {
    repeat(255) { value ->
      val input = "rgb($value,$value,$value)"
      val result = ColorUtils.rgbaToColor(input)
      assertEquals(Color.rgb(value, value, value), result)
    }
  }

  @Test
  fun `255 RGB expressions to Color int`() {
    repeat(255) { value ->
      val input = Expression.rgb {
        this.literal(value.toLong())
        this.literal(value.toLong())
        this.literal(value.toLong())
      }
      val result = ColorUtils.rgbaExpressionToColorInt(input)
      assertEquals(Color.rgb(value, value, value), result)
    }
  }

  @Test
  fun `255 RGB expressions to Color string`() {
    repeat(255) { value ->
      val input = Expression.rgb {
        this.literal(value.toLong())
        this.literal(value.toLong())
        this.literal(value.toLong())
      }
      val result = ColorUtils.rgbaExpressionToColorString(input)
      assertEquals("rgba($value, $value, $value, 1)", result)
    }
  }

  @Test
  fun rgbaToColor_decimalComponent() {
    val input = "rgba(255,128.0000952303,0,0.7)"
    val result = ColorUtils.rgbaToColor(input)
    assertEquals(Color.argb(0.7f, 1.0F, 0.5f, 0f), result)
    assertEquals(Color.argb(179, 255, 128, 0), result)
  }

  @Test
  fun rgbaToColor_decimalComponent_floor() {
    val input = "rgba(255,128.70123,0,0.7)"
    val result = ColorUtils.rgbaToColor(input)
    assertEquals(Color.argb(0.7f, 1.0F, 0.5f, 0f), result)
    assertEquals(Color.argb(179, 255, 128, 0), result)
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
    repeat(255) { value ->
      val expression = rgba {
        literal(value.toLong())
        literal(value.toLong())
        literal(value.toLong())
        // We only care about 8 decimal values
        literal(((value / 255.0) * 1e8).roundToInt() / 1e8)
      }
      val result =
        ColorUtils.colorIntToRgbaExpression(Color.argb(value, value, value, value)).toString()
      assertEquals(expression.toString(), result)
    }
  }

  @Test
  fun `color to RGBA string and back`() {
    repeat(255) { value ->
      val color = Color.argb(value, value, value, value)
      val colorString = ColorUtils.colorToRgbaString(color)
      val backToColor = ColorUtils.rgbaToColor(colorString)!!
      assertEquals(
        "Expected argb(${color.alpha}, ${color.red}, ${color.green}, ${color.blue}) but got argb(${backToColor.alpha}, ${backToColor.red}, ${backToColor.green}, ${backToColor.blue})",
        color,
        backToColor
      )
    }
  }

  @Test
  fun colorToRgbaArray() {
    repeat(255) { value ->
      val color = Color.argb(value, value, value, value)
      val result = ColorUtils.colorToRgbaArray(color)
      val expectedArray = floatArrayOf(value.toFloat(), value.toFloat(), value.toFloat(), value / 255F)
      assertTrue("${expectedArray.joinToString()} different than ${result.joinToString()}", expectedArray contentEquals result)
    }
  }

  @Test
  fun colorToGlRgbaArray() {
    val result = ColorUtils.colorToGlRgbaArray(Color.RED)
    assertTrue(floatArrayOf(1f, 0f, 0f, 1f) contentEquals result)
  }
}