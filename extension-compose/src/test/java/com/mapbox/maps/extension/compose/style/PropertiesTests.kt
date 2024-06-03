package com.mapbox.maps.extension.compose.style

import android.util.Range
import androidx.compose.ui.graphics.Color
import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.style.expressions.generated.Expression
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@Suppress("OPT_IN_USAGE")
public class PropertiesTests {
  @Test
  public fun `compose color to ColorValue and back`() {
    repeat(255) { value ->
      val composeColor = Color(value, value, value, value)
      val colorValue = ColorValue(composeColor)
      val colorOrNull = colorValue.colorOrNull
      assertNotNull(colorOrNull)
      assertEquals(
        "$colorValue ($value, $value, $value, $value) is not the same than $composeColor",
        composeColor,
        colorOrNull
      )
    }
  }

  @Test
  public fun `Raw RGBA Value to Color`() {
    repeat(255) { value ->
      val colorValue = ColorValue(Value.valueOf("rgba($value, $value, $value, ${value / 255F})"))
      assertEquals("rgba($value, $value, $value, ${value / 255F})", colorValue.value.contents)
      val colorOrNull = colorValue.colorOrNull
      assertNotNull(colorOrNull)
      assertEquals(Color(value, value, value, value), colorOrNull)
    }
  }

  @Test
  public fun `Raw RGB Value to Color`() {
    repeat(255) { value ->
      val colorValue = ColorValue(Value.valueOf("rgb($value, $value, $value)"))
      assertEquals("rgb($value, $value, $value)", colorValue.value.contents)
      val colorOrNull = colorValue.colorOrNull
      assertNotNull(colorOrNull)
      assertEquals(Color(value, value, value, 0xFF), colorOrNull)
    }
  }

  @Test
  public fun `RGB expression Value to Color`() {
    repeat(255) { value ->
      val valueDouble = value.toDouble()
      val colorValue = ColorValue(Expression.rgb(valueDouble, valueDouble, valueDouble))
      val colorOrNull = colorValue.colorOrNull
      assertNotNull(colorOrNull)
      assertEquals(Color(value, value, value, 0xFF), colorOrNull)
    }
  }

  @Test
  public fun `Custom expression ColorValue to Color is null`() {
    val colorValue = ColorValue(Expression.literal("Custom value"))
    assertNull(colorValue.colorOrNull)
  }

  @Test
  public fun `ColorValue default is different than INITIAL`() {
    assertNotEquals(ColorValue.default, ColorValue.INITIAL)
  }

  @Test
  public fun `DoubleValue to double`() {
    val doubleValue = DoubleValue(1.0)
    assertEquals(1.0, doubleValue.doubleOrNull)
  }

  @Test
  public fun `DoubleValue expression to double`() {
    val doubleValue = DoubleValue(Expression.literal(1.0))
    assertEquals(1.0, doubleValue.doubleOrNull)
  }

  @Test
  public fun `Custom expression DoubleValue to Double is null`() {
    val doubleValue = DoubleValue(Expression.literal("Custom value"))
    assertNull(doubleValue.doubleOrNull)
  }

  @Test
  public fun `DoubleValue default is different than INITIAL`() {
    assertNotEquals(DoubleValue.default, DoubleValue.INITIAL)
  }

  @Test
  public fun `RangeDoubleValue to Range`() {
    val rangeDoubleValue = RangeDoubleValue(1.0, 2.0)
    assertEquals(Range(1.0, 2.0), rangeDoubleValue.rangeOrNull)
  }

  @Test
  public fun `RangeDoubleValue with mixed upper and lower to Range null`() {
    val rangeDoubleValue = RangeDoubleValue(2.0, 1.0)
    assertNull(rangeDoubleValue.rangeOrNull)
  }

  @Test
  public fun `Custom expression RangeDoubleValue to Range is null`() {
    val rangeDoubleValue = RangeDoubleValue(Expression.literal("Custom value"))
    assertNull(rangeDoubleValue.rangeOrNull)
  }

  @Test
  public fun `RangeDoubleValue expression to RAnge`() {
    val rangeDoubleValue =
      RangeDoubleValue(Expression.array(Expression.literal(1.0), Expression.literal(2.0)))
    assertEquals(Range(1.0, 2.0), rangeDoubleValue.rangeOrNull)
  }

  @Test
  public fun `RangeDoubleValue expression with more than 2 returns null`() {
    val rangeDoubleValue = RangeDoubleValue(
      Expression.array(
        Expression.literal(1.0),
        Expression.literal(2.0),
        Expression.literal(3.0)
      )
    )
    assertNull(rangeDoubleValue.rangeOrNull)
  }

  @Test
  public fun `RangeDoubleValue array expression empty returns null`() {
    assertNull(RangeDoubleValue(Expression.array()).rangeOrNull)
  }

  @Test
  public fun `RangeDoubleValue default is different than INITIAL`() {
    assertNotEquals(RangeDoubleValue.default, RangeDoubleValue.INITIAL)
  }
}