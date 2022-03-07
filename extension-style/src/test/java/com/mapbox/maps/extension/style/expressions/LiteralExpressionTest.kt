package com.mapbox.maps.extension.style.expressions

import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.dsl.generated.match
import com.mapbox.maps.extension.style.expressions.dsl.generated.sum
import org.junit.Assert.*
import org.junit.Test

class LiteralExpressionTest {
  @Test
  fun literal_get_double() {
    val expression = literal(1.0)
    assertTrue(expression.isLiteral())
    assertEquals(expression.getLiteral<Double>()!!, 1.0, 1E-10)
  }

  @Test
  fun literal_get_doubleList() {
    val expression = literal(listOf(1.0, 2.0, 3.0))
    assertTrue(expression.isLiteral())
    assertEquals(listOf(1.0, 2.0, 3.0), expression.getLiteral()!!)
  }

  @Test
  fun literal_get_long() {
    val expression = literal(123L)
    assertTrue(expression.isLiteral())
    assertEquals(123L, expression.getLiteral()!!)
  }

  @Test
  fun literal_get_longList() {
    val expression = literal(listOf(1L, 2L, 3L))
    assertTrue(expression.isLiteral())
    assertEquals(listOf(1L, 2L, 3L), expression.getLiteral()!!)
  }

  @Test
  fun literal_get_boolean() {
    val expression = literal(true)
    assertTrue(expression.isLiteral())
    assertEquals(true, expression.getLiteral()!!)
  }

  @Test
  fun literal_get_booleanList() {
    val expression = literal(listOf(true, true, false))
    assertTrue(expression.isLiteral())
    assertEquals(listOf(true, true, false), expression.getLiteral()!!)
  }

  @Test
  fun literal_get_string() {
    val expression = literal("abc")
    assertTrue(expression.isLiteral())
    assertEquals("abc", expression.getLiteral()!!)
  }

  @Test
  fun literal_get_stringList() {
    val expression = literal(listOf("abc", "def"))
    assertTrue(expression.isLiteral())
    assertEquals(listOf("abc", "def"), expression.getLiteral()!!)
  }

  @Test
  fun literal_NotLiteral() {
    val expression = sum {
      literal(12)
      literal(34)
    }
    assertFalse(expression.isLiteral())
    assertNull(expression.getLiteral())
  }

  @Test
  fun literal_inside_match_expression() {
    val expression = match {
      get {
        literal("ethnicity")
      }
      literal(listOf("white", "Black", "Hispanic", "Asian"))
      rgb(251.0, 176.0, 59.0)
      literal("Other")
      rgb(204.0, 204.0, 204.0)
      rgb(0.0, 0.0, 0.0)
    }
    assertEquals(
      """
      ["match",["get","ethnicity"],["white","Black","Hispanic","Asian"],["rgb",251.0,176.0,59.0],"Other",["rgb",204.0,204.0,204.0],["rgb",0.0,0.0,0.0]]
      """.trimIndent(),
      expression.toJson()
    )
  }
}