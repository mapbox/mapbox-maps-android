package com.mapbox.maps.extension.style.utils

import android.graphics.Color
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.Value
import com.mapbox.common.ValueConverter
import com.mapbox.geojson.Feature
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.ShadowValueConverter
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.properties.generated.Visibility
import com.mapbox.maps.extension.style.types.StyleTransition
import com.mapbox.maps.extension.style.types.formatted
import com.mapbox.maps.extension.style.types.transitionOptions
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowValueConverter::class])
class TypeUtilsTest {
  private val expected = mockk<Expected<String, Value>>(relaxed = true)

  @Before
  fun prepare() {
    mockkStatic(ValueConverter::class)
    every { ValueConverter.fromJson(any()) } returns expected
    every { expected.error } returns null
    every { expected.value } returns Value(0)
  }

  @Test
  fun wrapToValue_Int() {
    testwrapToValue(123)
  }

  @Test
  fun wrapToValue_Long() {
    testwrapToValue(123L)
  }

  @Test
  fun wrapToValue_String() {
    testwrapToValue("abc")
  }

  @Test
  fun wrapToValue_Boolean() {
    testwrapToValue(true)
  }

  @Test
  fun wrapToValue_Double() {
    testwrapToValue(0.01)
  }

  @Test
  fun wrapToValue_Float() {
    val result = TypeUtils.wrapToValue(0.01f)
    assertEquals(0.01f.toDouble(), result.contents)
  }

  private fun testwrapToValue(value: Any) {
    val result = TypeUtils.wrapToValue(value)
    assertEquals(value.toString(), result.toString())
  }

  @Test
  fun wrapToValue_IntArray() {
    val result = TypeUtils.wrapToValue(intArrayOf(1, 2, 3))
    assertEquals("[1, 2, 3]", result.toString())
  }

  @Test
  fun wrapToValue_BooleanArray() {
    val result = TypeUtils.wrapToValue(booleanArrayOf(true, true, false))
    assertEquals("[true, true, false]", result.toString())
  }

  @Test
  fun wrapToValue_DoubleArray() {
    val result = TypeUtils.wrapToValue(doubleArrayOf(0.01, 0.02, 0.03))
    assertEquals("[0.01, 0.02, 0.03]", result.toString())
  }

  @Test
  fun wrapToValue_FloatArray() {
    val result = TypeUtils.wrapToValue(floatArrayOf(1.0f, 2.0f, 3.0f))
    assertEquals("[1.0, 2.0, 3.0]", result.toString())
  }

  @Test
  fun wrapToValue_LongArray() {
    val result = TypeUtils.wrapToValue(longArrayOf(1L, 2L, 3L))
    assertEquals("[1, 2, 3]", result.toString())
  }

  @Test
  fun wrapToValue_StringArray() {
    val result = TypeUtils.wrapToValue(arrayOf("ABC", "DEF", "GHI"))
    assertEquals("[ABC, DEF, GHI]", result.toString())
  }

  @Test
  fun wrapToValue_MixedArray() {
    val result = TypeUtils.wrapToValue(arrayOf("ABC", 66, "GHI"))
    assertEquals("[ABC, 66, GHI]", result.toString())
  }

  @Test
  fun wrapToValue_ArrayList() {
    val arraylist = ArrayList<String>()
    arraylist.add("abc")
    arraylist.add("def")
    val result = TypeUtils.wrapToValue(arraylist)
    assertEquals("[abc, def]", result.toString())
  }

  @Test(expected = RuntimeException::class)
  fun wrapToValue_NonStringArray() {
    TypeUtils.wrapToValue(arrayOf(MockData(1, "a")))
  }

  @Test(expected = RuntimeException::class)
  fun wrapToValue_UnsupportedClass() {
    TypeUtils.wrapToValue(MockData(1, "a"))
  }

  @Test
  fun wrapToValue_StringHashMap() {
    val map = HashMap<String, String>()
    map["abc"] = "def"
    map["abcd"] = "defg"
    val result = TypeUtils.wrapToValue(map)
    assertEquals("{abc=def, abcd=defg}", result.toString())
  }

  @Test(expected = ClassCastException::class)
  fun wrapToValue_WrongKeyHashMap() {
    val map = HashMap<Int, String>()
    map[1] = "def"
    map[2] = "defg"
    TypeUtils.wrapToValue(map)
  }

  @Test
  fun wrapToValue_IntHashMap() {
    val map = HashMap<String, Int>()
    map["abc"] = 1
    map["def"] = 2
    val result = TypeUtils.wrapToValue(map)
    assertEquals("{abc=1, def=2}", result.toString())
  }

  @Test
  fun wrapToValue_NestedHashMap() {
    val map = HashMap<String, Any>()
    map["abc"] = 1
    map["def"] = 2
    val map1 = HashMap<String, Any>()
    map1["abc"] = "def"
    map1["abcd"] = "defg"
    map1["nested"] = map
    val result = TypeUtils.wrapToValue(map1)
    assertEquals("{abc=def, nested={abc=1, def=2}, abcd=defg}", result.toString())
  }

  @Test
  fun wrapToValue_Json() {
    // TODO add support of Json after introducing ValueConverter.
    val innerObject = JsonObject()
    innerObject.addProperty("name", "john")
    val jsonObject = JsonObject()
    jsonObject.add("publisher", innerObject)

//    val result = TypeUtils.wrapToValue(jsonObject)
  }

  @Test
  fun wrapToValue_JsonPrimitiveNumber() {
    val jsonPrimitive = JsonPrimitive(1)

    val result = TypeUtils.wrapToValue(jsonPrimitive)
    assertEquals(1.0, result.contents)
  }

  @Test
  fun wrapToValue_JsonPrimitiveBoolean() {
    val jsonPrimitive = JsonPrimitive(true)

    val result = TypeUtils.wrapToValue(jsonPrimitive)
    assertEquals(true, result.contents)
  }

  @Test
  fun wrapToValue_JsonPrimitiveString() {
    val jsonPrimitive = JsonPrimitive("abc")

    val result = TypeUtils.wrapToValue(jsonPrimitive)
    assertEquals("abc", result.contents)
  }

  @Test
  fun wrapToValue_Expression() {
    val result = TypeUtils.wrapToValue(pi())
    assertEquals(pi().toString(), result.toString())
  }

  @Test
  fun wrapToValue_Formatted() {
    val result = TypeUtils.wrapToValue(
      formatted {
        formattedSection("abc") {
          fontScale = 1.0
        }
      }
    )
    assertEquals("[[abc, {font-scale=1.0}]]", result.contents.toString())
  }

  @Test
  fun wrapToValue_StyleTransition() {
    val result = TypeUtils.wrapToValue(
      transitionOptions {
        duration(100)
        delay(100)
      }
    )
    assertEquals("{duration=100, delay=100}", result.contents.toString())
  }

  @Test
  fun wrapToValue_LayerProperty() {
    val result = TypeUtils.wrapToValue(Visibility.VISIBLE)
    assertEquals("visible", result.contents.toString())
  }

  @Test
  fun unwrapStyleProperty_constant_Long() {
    val styleProperty = StylePropertyValue(Value(123), StylePropertyValueKind.CONSTANT)

    val result = styleProperty.unwrap<Long>()
    assertEquals(123L, result)
  }

  @Test
  fun unwrapStyleProperty_constant_Double() {
    val styleProperty = StylePropertyValue(Value(0.1), StylePropertyValueKind.CONSTANT)

    val result = styleProperty.unwrap<Double>()
    assertEquals(0.1, result, 1E-10)
  }

  @Test
  fun unwrapStyleProperty_constant_Boolean() {
    val styleProperty = StylePropertyValue(Value(true), StylePropertyValueKind.CONSTANT)

    val result = styleProperty.unwrap<Boolean>()
    assertEquals(true, result)
  }

  @Test
  fun unwrapStyleProperty_constant_String() {
    val styleProperty = StylePropertyValue(Value("abc"), StylePropertyValueKind.CONSTANT)

    val result = styleProperty.unwrap<String>()
    assertEquals("abc", result)
  }

  @Test
  fun unwrapStyleProperty_constant_LongArray() {
    val array = longArrayOf(1L, 2L, 3L)
    val styleProperty =
      StylePropertyValue(TypeUtils.wrapToValue(array), StylePropertyValueKind.CONSTANT)

    val result = styleProperty.unwrap<List<Long>>()
    assertTrue(array contentEquals result.toTypedArray().toLongArray())
  }

  @Test
  fun unwrapStyleProperty_constant_DoubleArray() {
    val array = doubleArrayOf(1.0, 2.0, 3.0)
    val styleProperty =
      StylePropertyValue(TypeUtils.wrapToValue(array), StylePropertyValueKind.CONSTANT)

    val result = styleProperty.unwrap<List<Double>>()
    assertTrue(array contentEquals result.toTypedArray().toDoubleArray())
  }

  @Test
  fun unwrapStyleProperty_constant_BooleanArray() {
    val array = booleanArrayOf(true, true, false)
    val styleProperty =
      StylePropertyValue(TypeUtils.wrapToValue(array), StylePropertyValueKind.CONSTANT)

    val result = styleProperty.unwrap<List<Boolean>>()
    assertTrue(array contentEquals result.toTypedArray().toBooleanArray())
  }

  @Test
  fun unwrapStyleProperty_constant_StringArray() {
    val array = arrayOf("a", "b")
    val styleProperty =
      StylePropertyValue(TypeUtils.wrapToValue(array), StylePropertyValueKind.CONSTANT)

    val result = styleProperty.unwrap<List<String>>()
    assertTrue(array contentEquals result.toTypedArray())
  }

  @Test
  fun unwrapStyleProperty_constant_HashMapString() {
    val map = HashMap<String, String>()
    map["abc"] = "def"
    map["ddd"] = "fff"

    val styleProperty =
      StylePropertyValue(TypeUtils.wrapToValue(map), StylePropertyValueKind.CONSTANT)

    val result = styleProperty.unwrap<HashMap<String, String>>()
    assertTrue(map.keys == result.keys)
    map.keys.forEach {
      assertEquals(map[it], result[it])
    }
  }

  @Test
  fun unwrapStyleProperty_constant_HashMapDouble() {
    val map = HashMap<String, Double>()
    map["abc"] = 1.0
    map["ddd"] = 2.0

    val styleProperty =
      StylePropertyValue(TypeUtils.wrapToValue(map), StylePropertyValueKind.CONSTANT)

    val result = styleProperty.unwrap<HashMap<String, Double>>()
    assertTrue(map.keys == result.keys)
    map.keys.forEach {
      assertEquals(map[it], result[it])
    }
  }

  @Test
  fun unwrapStyleProperty_constant_nestedHashMap() {
    val map = HashMap<String, Any>()
    map["abc"] = 1
    map["def"] = 2
    val map1 = HashMap<String, Any>()
    map1["abc"] = "def"
    map1["abcd"] = "defg"
    map1["nested"] = map

    val styleProperty =
      StylePropertyValue(TypeUtils.wrapToValue(map1), StylePropertyValueKind.CONSTANT)

    val result = styleProperty.unwrap<HashMap<String, Any>>()

    assertTrue(map1.keys == result.keys)
    map.keys.forEach {
      assertEquals(map1[it], result[it])
    }
  }

  @Test(expected = RuntimeException::class)
  fun unwrapStyleProperty_constant_mismatch_type() {
    val styleProperty = StylePropertyValue(Value(123), StylePropertyValueKind.CONSTANT)

    val result = styleProperty.unwrap<Int>()
    assertEquals(123, result)
  }

  @Test
  fun silentUnwrapStyleProperty_constant_mismatch_type() {
    val styleProperty = StylePropertyValue(Value(123), StylePropertyValueKind.CONSTANT)

    val result = styleProperty.silentUnwrap<String>()
    assertEquals(null, result)
  }

  @Test
  fun unwrapTransitionAsHashMap() {
    val styleProperty = StylePropertyValue(
      TypeUtils.wrapToValue(
        transitionOptions {
          delay(100)
          duration(200)
        }
      ),
      StylePropertyValueKind.TRANSITION
    )

    val result = styleProperty.unwrap<StyleTransition>()
    assertEquals(100L, result.delay)
    assertEquals(200L, result.duration)
  }

  @Test
  fun unwrapTransitionAsList() {
    val styleProperty = StylePropertyValue(
      TypeUtils.wrapToValue(longArrayOf(102, 103)),
      StylePropertyValueKind.TRANSITION
    )

    val result = styleProperty.unwrap<StyleTransition>()
    assertEquals(102L, result.duration)
    assertEquals(103L, result.delay)
  }

  @Test(expected = RuntimeException::class)
  fun unwrapTransitionException() {
    val styleProperty =
      StylePropertyValue(TypeUtils.wrapToValue("test"), StylePropertyValueKind.TRANSITION)

    styleProperty.unwrap<StyleTransition>()
  }

  @Test
  fun unwrapExpression() {
    val expression = sum {
      pi()
      literal(true)
      literal("abc")
    }
    val styleProperty =
      StylePropertyValue(TypeUtils.wrapToValue(expression), StylePropertyValueKind.EXPRESSION)

    val result = styleProperty.unwrap<Expression>()
    assertEquals("[+, [pi], true, abc]", result.toString())
  }

  @Test
  fun unwrapFormatExpression() {
    val expression = format {
      formatSection("cyan") {
        fontScale(0.9)
        textFont(listOf("Open Sans Regular", "Arial Unicode MS Regular"))
        textColor(Color.CYAN)
      }
      formatSection("black") {
        fontScale(
          get {
            literal("scale")
          }
        )
        textFont(listOf("Open Sans Regular", "Arial Unicode MS Regular"))
        textColor(
          rgba {
            literal(0.0)
            literal(0.0)
            literal(0.0)
            literal(1.0)
          }
        )
      }
    }
    val styleProperty =
      StylePropertyValue(TypeUtils.wrapToValue(expression), StylePropertyValueKind.EXPRESSION)

    val result = styleProperty.unwrap<Expression>()
    assertEquals(
      expression.toString(),
      result.toString()
    )
  }

  @Test
  fun unwrapExpressionLiteralLong() {
    val styleProperty =
      StylePropertyValue(TypeUtils.wrapToValue(literal(1)), StylePropertyValueKind.EXPRESSION)

    val result = styleProperty.unwrap<Expression>()
    assertEquals("1", result.toString())
  }

  @Test
  fun unwrapExpressionLiteralDouble() {
    val styleProperty =
      StylePropertyValue(TypeUtils.wrapToValue(literal(1.0)), StylePropertyValueKind.EXPRESSION)

    val result = styleProperty.unwrap<Expression>()
    assertEquals("1.0", result.toString())
  }

  @Test
  fun unwrapExpressionLiteralBoolean() {
    val styleProperty =
      StylePropertyValue(TypeUtils.wrapToValue(literal(true)), StylePropertyValueKind.EXPRESSION)

    val result = styleProperty.unwrap<Expression>()
    assertEquals("true", result.toString())
  }

  @Test
  fun unwrapExpressionLiteralString() {
    val styleProperty =
      StylePropertyValue(TypeUtils.wrapToValue(literal("abc")), StylePropertyValueKind.EXPRESSION)

    val result = styleProperty.unwrap<Expression>()
    assertEquals("abc", result.toString())
  }

  @Test
  fun unwrapExpressionNested() {
    val expression = interpolate {
      linear()
      heatmapDensity()
      stop {
        literal(0)
        rgba {
          literal(33)
          literal(102)
          literal(172)
          literal(0)
        }
      }
      stop {
        literal(0.2)
        rgb {
          literal(103)
          literal(169)
          literal(207)
        }
      }
      stop {
        literal(0.4)
        rgb {
          literal(209)
          literal(229)
          literal(240)
        }
      }
      stop {
        literal(0.6)
        rgb {
          literal(253)
          literal(219)
          literal(240)
        }
      }
      stop {
        literal(0.8)
        rgb {
          literal(239)
          literal(138)
          literal(98)
        }
      }
      stop {
        literal(1)
        rgb {
          literal(178)
          literal(24)
          literal(43)
        }
      }
    }
    val styleProperty =
      StylePropertyValue(TypeUtils.wrapToValue(expression), StylePropertyValueKind.EXPRESSION)

    val result = styleProperty.unwrap<Expression>()
    assertEquals(expression.toString(), result.toString())
  }

  @Test
  fun featureTest() {
    val featureString =
      "{\"type\":\"Feature\",\"bbox\":[100.0,0.0,-100.0,105.0,1.0,0.0],\"geometry\":{\"type\":\"Point\",\"coordinates\":[125.6,10.1]},\"properties\":{\"name\":\"Dinagat Islands\"}}"
    val actual = Feature.fromJson(featureString).toValue()
    assertEquals(Value.valueOf(featureString), actual)
  }

  internal data class MockData(val a: Int, val b: String)
}