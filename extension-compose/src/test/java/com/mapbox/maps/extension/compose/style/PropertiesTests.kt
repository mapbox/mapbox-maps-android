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
    assertNotEquals(ColorValue.DEFAULT, ColorValue.INITIAL)
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
    assertNotEquals(DoubleValue.DEFAULT, DoubleValue.INITIAL)
  }

  @Test
  public fun `RangeDoubleValue to Range`() {
    val doubleRangeValue = DoubleRangeValue(1.0, 2.0)
    assertEquals(Range(1.0, 2.0), doubleRangeValue.rangeOrNull)
  }

  @Test
  public fun `RangeDoubleValue with mixed upper and lower to Range null`() {
    val doubleRangeValue = DoubleRangeValue(2.0, 1.0)
    assertNull(doubleRangeValue.rangeOrNull)
  }

  @Test
  public fun `Custom expression RangeDoubleValue to Range is null`() {
    val doubleRangeValue = DoubleRangeValue(Expression.literal("Custom value"))
    assertNull(doubleRangeValue.rangeOrNull)
  }

  @Test
  public fun `RangeDoubleValue expression to RAnge`() {
    val doubleRangeValue =
      DoubleRangeValue(Expression.array(Expression.literal(1.0), Expression.literal(2.0)))
    assertEquals(Range(1.0, 2.0), doubleRangeValue.rangeOrNull)
  }

  @Test
  public fun `RangeDoubleValue expression with more than 2 returns null`() {
    val doubleRangeValue = DoubleRangeValue(
      Expression.array(
        Expression.literal(1.0),
        Expression.literal(2.0),
        Expression.literal(3.0)
      )
    )
    assertNull(doubleRangeValue.rangeOrNull)
  }

  @Test
  public fun `RangeDoubleValue array expression empty returns null`() {
    assertNull(DoubleRangeValue(Expression.array()).rangeOrNull)
  }

  @Test
  public fun `RangeDoubleValue default is different than INITIAL`() {
    assertNotEquals(DoubleRangeValue.DEFAULT, DoubleRangeValue.INITIAL)
  }

  @Test
  public fun `LongValue to long`() {
    val longValue = LongValue(1)
    assertEquals(1L, longValue.longOrNull)
  }

  @Test
  public fun `BooleanValue to Boolean`() {
    val booleanValue = BooleanValue(true)
    assertEquals(true, booleanValue.booleanOrNull)
  }

  @Test
  public fun `StringValue to String`() {
    val stringValue = StringValue("abc")
    assertEquals("abc", stringValue.stringOrNull)
  }

  @Test
  public fun `DoubleListValue with vararg to DoubleList getter`() {
    val doubleListValue = DoubleListValue(1.0, 2.0, 3.0)
    assertEquals(3, doubleListValue.doubleListOrNull!!.size)
    assertEquals(1.0, doubleListValue.doubleListOrNull!![0], EPS)
    assertEquals(2.0, doubleListValue.doubleListOrNull!![1], EPS)
    assertEquals(3.0, doubleListValue.doubleListOrNull!![2], EPS)
  }

  @Test
  public fun `DoubleListValue with list to DoubleList getter`() {
    val doubleListValue = DoubleListValue(listOf(1.0, 2.0, 3.0))
    assertEquals(3, doubleListValue.doubleListOrNull!!.size)
    assertEquals(1.0, doubleListValue.doubleListOrNull!![0], EPS)
    assertEquals(2.0, doubleListValue.doubleListOrNull!![1], EPS)
    assertEquals(3.0, doubleListValue.doubleListOrNull!![2], EPS)
  }

  @Test
  public fun `DoubleListValue with Expression to DoubleList getter`() {
    val doubleListValue = DoubleListValue(
      Expression.array {
        literal(1.0)
        literal(2.0)
        literal(3.0)
      }
    )
    assertEquals(3, doubleListValue.doubleListOrNull!!.size)
    assertEquals(1.0, doubleListValue.doubleListOrNull!![0], EPS)
    assertEquals(2.0, doubleListValue.doubleListOrNull!![1], EPS)
    assertEquals(3.0, doubleListValue.doubleListOrNull!![2], EPS)
  }

  @Test
  public fun `StringListValue with vararg to StringList getter`() {
    val stringListValue = StringListValue("a", "b", "c")
    assertEquals(3, stringListValue.stringListOrNull!!.size)
    assertEquals("a", stringListValue.stringListOrNull!![0])
    assertEquals("b", stringListValue.stringListOrNull!![1])
    assertEquals("c", stringListValue.stringListOrNull!![2])
  }

  @Test
  public fun `StringListValue with list to StringList getter`() {
    val stringListValue = StringListValue("a", "b", "c")
    assertEquals(3, stringListValue.stringListOrNull!!.size)
    assertEquals("a", stringListValue.stringListOrNull!![0])
    assertEquals("b", stringListValue.stringListOrNull!![1])
    assertEquals("c", stringListValue.stringListOrNull!![2])
  }

  @Test
  public fun `StringListValue with Expression to StringList getter`() {
    val stringListValue = StringListValue(
      Expression.array {
        literal("a")
        literal("b")
        literal("c")
      }
    )
    assertEquals(3, stringListValue.stringListOrNull!!.size)
    assertEquals("a", stringListValue.stringListOrNull!![0])
    assertEquals("b", stringListValue.stringListOrNull!![1])
    assertEquals("c", stringListValue.stringListOrNull!![2])
  }

  @Test
  public fun `Transition duration and delay getter`() {
    val transition = Transition(100, 1000)
    assertEquals(100, transition.durationMillis)
    assertEquals(1000, transition.delayMillis)
  }

  private companion object {
    private const val EPS = 0.0000001
  }
}