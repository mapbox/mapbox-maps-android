package com.mapbox.maps.extension.compose.style

import android.util.Range
import androidx.compose.ui.graphics.Color
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
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
    val doubleMaxValue = DoubleValue(Double.MAX_VALUE)
    assertEquals(Double.MAX_VALUE, doubleMaxValue.doubleOrNull)
    val doubleMinValue = DoubleValue(Double.MIN_VALUE)
    assertEquals(Double.MIN_VALUE, doubleMinValue.doubleOrNull)
  }

  @Test
  public fun `DoubleValue expression to double`() {
    val doubleValue = DoubleValue(Expression.literal(1.0))
    assertEquals(1.0, doubleValue.doubleOrNull)
    val doubleLongExpressionValue = DoubleValue(Expression.literal(1L))
    assertEquals(1.0, doubleLongExpressionValue.doubleOrNull)
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
  public fun `LongValue to long`() {
    val longValue = LongValue(1L)
    assertEquals(1L, longValue.longOrNull)
    val maxLongValue = LongValue(Long.MAX_VALUE)
    assertEquals(Long.MAX_VALUE, maxLongValue.longOrNull)
    val minLongValue = LongValue(Long.MIN_VALUE)
    assertEquals(Long.MIN_VALUE, minLongValue.longOrNull)
  }

  @Test
  public fun `LongValue expression to long`() {
    val longExpressionValue = LongValue(Expression.literal(1L))
    assertEquals(1L, longExpressionValue.longOrNull)
    val longDoubleExpressionValue = LongValue(Expression.literal(1.0))
    assertEquals(1L, longDoubleExpressionValue.longOrNull)
  }

  @Test
  public fun `Custom expression LongValue to Long is null`() {
    val longValue = LongValue(Expression.literal("Custom value"))
    assertNull(longValue.longOrNull)
  }

  @Test
  public fun `LongValue default is different than INITIAL`() {
    assertNotEquals(LongValue.DEFAULT, LongValue.INITIAL)
  }

  @Test
  public fun `BooleanValue to boolean`() {
    val trueBooleanValue = BooleanValue(true)
    assertEquals(true, trueBooleanValue.booleanOrNull)
    val falseBooleanValue = BooleanValue(false)
    assertEquals(false, falseBooleanValue.booleanOrNull)
  }

  @Test
  public fun `BooleanValue expression to boolean`() {
    val trueBooleanExpressionValue = BooleanValue(Expression.literal(true))
    assertEquals(true, trueBooleanExpressionValue.booleanOrNull)
    val falseBooleanExpressionValue = BooleanValue(Expression.literal(false))
    assertEquals(false, falseBooleanExpressionValue.booleanOrNull)
  }

  @Test
  public fun `Custom expression BooleanValue to Boolean is null`() {
    val booleanValue = BooleanValue(Expression.literal("Custom value"))
    assertNull(booleanValue.booleanOrNull)
  }

  @Test
  public fun `BooleanValue default is different than INITIAL`() {
    assertNotEquals(BooleanValue.DEFAULT, BooleanValue.INITIAL)
  }

  @Test
  public fun `StringValue to string`() {
    val stringValue = StringValue("String value")
    assertEquals("String value", stringValue.stringOrNull)
    val emptyStringValue = StringValue("")
    assertEquals("", emptyStringValue.stringOrNull)
  }

  @Test
  public fun `StringValue expression to string`() {
    val stringExpressionValue = StringValue(Expression.literal("String value"))
    assertEquals("String value", stringExpressionValue.stringOrNull)
    val stringDoubleExpressionValue = StringValue(Expression.literal(""))
    assertEquals("", stringDoubleExpressionValue.stringOrNull)
  }

  @Test
  public fun `Non string expression StringValue to String is null`() {
    val stringValue = StringValue(Expression.literal(1L))
    assertNull(stringValue.stringOrNull)
  }

  @Test
  public fun `StringValue default is different than INITIAL`() {
    assertNotEquals(StringValue.DEFAULT, StringValue.INITIAL)
  }

  @Test
  public fun `DoubleRangeValue to Range`() {
    val doubleRangeValue = DoubleRangeValue(1.0, 2.0)
    assertEquals(Range(1.0, 2.0), doubleRangeValue.rangeOrNull)
  }

  @Test
  public fun `DoubleRangeValue with mixed upper and lower to Range null`() {
    val doubleRangeValue = DoubleRangeValue(2.0, 1.0)
    assertNull(doubleRangeValue.rangeOrNull)
  }

  @Test
  public fun `Custom expression DoubleRangeValue to Range is null`() {
    val doubleRangeValue = DoubleRangeValue(Expression.literal("Custom value"))
    assertNull(doubleRangeValue.rangeOrNull)
  }

  @Test
  public fun `DoubleRangeValue expression to Range`() {
    val doubleRangeValue =
      DoubleRangeValue(Expression.array(Expression.literal(1.0), Expression.literal(2.0)))
    assertEquals(Range(1.0, 2.0), doubleRangeValue.rangeOrNull)
  }

  @Test
  public fun `DoubleRangeValue expression with more than 2 returns null`() {
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
  public fun `DoubleRangeValue array expression empty returns null`() {
    assertNull(DoubleRangeValue(Expression.array()).rangeOrNull)
  }

  @Test
  public fun `DoubleRangeValue default is different than INITIAL`() {
    assertNotEquals(DoubleRangeValue.DEFAULT, DoubleRangeValue.INITIAL)
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
    val stringListValue = StringListValue(listOf("a", "b", "c"))
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
  public fun `StringListValue with non list values returns null stringListOrNull`() {
    val nullValueStringListValue = StringListValue(Value.nullValue())
    assertNull(nullValueStringListValue.stringListOrNull)
    val emptyArrayExpressionStringListValue = StringListValue(Expression.array { })
    assertNull(emptyArrayExpressionStringListValue.stringListOrNull)
    val stringLiteralExpressionStringListValue = StringListValue(Expression.Companion.literal("A string"))
    assertNull(stringLiteralExpressionStringListValue.stringListOrNull)
    val doubleArrayExpressionStringListValue = StringListValue(Expression.array { literal(1.0) })
    assertNull(doubleArrayExpressionStringListValue.stringListOrNull)
    val stringDoubleArrayExpressionStringListValue = StringListValue(
      Expression.array {
      literal("A string")
      literal(1.0)
    }
    )
    assertNull(stringDoubleArrayExpressionStringListValue.stringListOrNull)
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

  @Test
  public fun `PointListValue with vararg`() {
    val pointListValue = PointListValue(Point.fromLngLat(1.0, 2.0))
    assertEquals(1, pointListValue.pointsOrNull?.size)
    assertEquals(Point.fromLngLat(1.0, 2.0), pointListValue.pointsOrNull?.get(0))

    val multiplePointListValue = PointListValue(
      Point.fromLngLat(1.0, 2.0),
      Point.fromLngLat(3.0, 4.0),
    )
    assertEquals(2, multiplePointListValue.pointsOrNull?.size)
    assertEquals(Point.fromLngLat(1.0, 2.0), multiplePointListValue.pointsOrNull?.get(0))
    assertEquals(Point.fromLngLat(3.0, 4.0), multiplePointListValue.pointsOrNull?.get(1))

    val pairOfPointListValue = PointListValue(
      1.0 to 2.0,
      3.0 to 4.0
    )
    assertEquals(2, pairOfPointListValue.pointsOrNull?.size)
    assertEquals(Point.fromLngLat(1.0, 2.0), pairOfPointListValue.pointsOrNull?.get(0))
    assertEquals(Point.fromLngLat(3.0, 4.0), pairOfPointListValue.pointsOrNull?.get(1))

    val listDoubleOfPointListValue = PointListValue(
      listOf(
        listOf(1.0, 2.0),
        listOf(3.0, 4.0)
      )
    )
    assertEquals(2, listDoubleOfPointListValue.pointsOrNull?.size)
    assertEquals(Point.fromLngLat(1.0, 2.0), listDoubleOfPointListValue.pointsOrNull?.get(0))
    assertEquals(Point.fromLngLat(3.0, 4.0), listDoubleOfPointListValue.pointsOrNull?.get(1))
  }

  @Test
  public fun `PointListValue with Expressions`() {
    val emptyArrayExpression = PointListValue(Expression.array())
    assertNull(emptyArrayExpression.pointsOrNull)
    val nullValueExpression = PointListValue(Value.nullValue())
    assertNull(nullValueExpression.pointsOrNull)
  }

  @Test
  public fun `PointListValue default is different than INITIAL`() {
    assertNotEquals(PointListValue.DEFAULT, PointListValue.INITIAL)
  }
}