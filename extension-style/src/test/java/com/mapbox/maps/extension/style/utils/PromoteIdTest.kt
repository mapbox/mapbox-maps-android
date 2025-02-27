package com.mapbox.maps.extension.style.utils

import com.mapbox.maps.extension.style.expressions.dsl.generated.coalesce
import com.mapbox.maps.extension.style.types.PromoteId
import org.junit.Assert.assertEquals
import org.junit.Test

class PromoteIdTest {

  @Test
  fun fromProperty_String() {
    val expected = PromoteId("property")
    assertEquals(expected, PromoteId.fromProperty("property"))
  }

  @Test
  fun fromProperty_EmptyString() {
    val expected = PromoteId("")
    assertEquals(expected, PromoteId.fromProperty(""))
  }

  @Test
  fun fromProperty_HashMap() {
    val expected = PromoteId("abc", "source")
    val actual = PromoteId.fromProperty(hashMapOf("source" to "abc"))
    assertEquals(expected, actual)
  }

  @Test
  fun fromProperty_EmptyHashMap() {
    val expected = PromoteId("")
    val actual = PromoteId.fromProperty(hashMapOf<String, String>())
    assertEquals(expected, actual)
  }

  @Test
  fun fromProperty_NullSource() {
    val expected = PromoteId("abc", null)
    assertEquals(expected, PromoteId.fromProperty("abc"))
  }

  @Test
  fun fromProperty_EmptyPropertyAndValidSource() {
    val expected = PromoteId("", "source")
    val actual = PromoteId.fromProperty(hashMapOf("source" to ""))
    assertEquals(expected, actual)
  }

  @Test(expected = UnsupportedOperationException::class)
  fun fromProperty_UnsupportedType() {
    PromoteId.fromProperty(intArrayOf(1, 2, 3))
  }

  @Test(expected = UnsupportedOperationException::class)
  fun fromProperty_UnsupportedDataClassType() {
    PromoteId.fromProperty(MockType("source", "abc"))
  }

  @Test(expected = RuntimeException::class)
  fun fromProperty_InvalidHashMapType() {
    PromoteId.fromProperty(hashMapOf(1 to 2))
  }

  @Test
  fun fromProperty_Expression() {
    val expected = PromoteId(
      coalesce {
        get { literal("building_id") }
        id()
      },
      "building"
    )
    val actual = PromoteId.fromProperty(
      hashMapOf(
        "building" to listOf(
          "coalesce", listOf("get", "building_id"), listOf("id")
        )
      )
    )

    assertEquals(expected, actual)
  }

  @Test
  fun fromProperty_ExpressionNullSource() {
    val expected = PromoteId(
      coalesce {
        get { literal("building_id") }
        id()
      },
    )
    val actual = PromoteId.fromProperty(
      listOf(
        "coalesce", listOf("get", "building_id"), listOf("id")
      )
    )

    assertEquals(expected, actual)
  }

  private data class MockType(val a: String, val b: String)
}