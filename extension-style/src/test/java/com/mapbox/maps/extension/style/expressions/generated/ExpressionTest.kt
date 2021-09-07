// This file is generated.

package com.mapbox.maps.extension.style.expressions.generated

import android.graphics.Color
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Polygon
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.expressions.types.FormatSection
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*

@RunWith(RobolectricTestRunner::class)
class ExpressionTest {

  @Before
  fun prepareTest() {
    val map = HashMap<String, Value>()
    map["type"] = Value("Polygon")
    map["coordinates"] = Value(listOf(Value(30), Value(30)))
    mockkStatic(Value::class)
    every { Value.fromJson(any()) } returns ExpectedFactory.createValue<String, Value>(Value(map))
  }

  @Test
  fun dsl_expression_subtract() {
    val expression = subtract {
      // test builder function
      subtract {}
    }
    assertEquals("assert - expression", "[-, [-]]", expression.toString())
  }

  @Test
  fun expression_subtract() {
    val expression = Expression.subtract(Expression.literal("abc"))
    assertEquals("assert - expression", "[-, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_not() {
    val expression = not {
      // test builder function
      not {}
    }
    assertEquals("assert ! expression", "[!, [!]]", expression.toString())
  }

  @Test
  fun expression_not() {
    val expression = Expression.not(Expression.literal("abc"))
    assertEquals("assert ! expression", "[!, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_neq() {
    val expression = neq {
      // test builder function
      neq {}
    }
    assertEquals("assert != expression", "[!=, [!=]]", expression.toString())
  }

  @Test
  fun expression_neq() {
    val expression = Expression.neq(Expression.literal("abc"))
    assertEquals("assert != expression", "[!=, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_product() {
    val expression = product {
      // test builder function
      product {}
    }
    assertEquals("assert * expression", "[*, [*]]", expression.toString())
  }

  @Test
  fun expression_product() {
    val expression = Expression.product(Expression.literal("abc"))
    assertEquals("assert * expression", "[*, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_division() {
    val expression = division {
      // test builder function
      division {}
    }
    assertEquals("assert / expression", "[/, [/]]", expression.toString())
  }

  @Test
  fun expression_division() {
    val expression = Expression.division(Expression.literal("abc"))
    assertEquals("assert / expression", "[/, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_mod() {
    val expression = mod {
      // test builder function
      mod {}
    }
    assertEquals("assert % expression", "[%, [%]]", expression.toString())
  }

  @Test
  fun expression_mod() {
    val expression = Expression.mod(Expression.literal("abc"))
    assertEquals("assert % expression", "[%, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_pow() {
    val expression = pow {
      // test builder function
      pow {}
    }
    assertEquals("assert ^ expression", "[^, [^]]", expression.toString())
  }

  @Test
  fun expression_pow() {
    val expression = Expression.pow(Expression.literal("abc"))
    assertEquals("assert ^ expression", "[^, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_sum() {
    val expression = sum {
      // test builder function
      sum {}
    }
    assertEquals("assert + expression", "[+, [+]]", expression.toString())
  }

  @Test
  fun expression_sum() {
    val expression = Expression.sum(Expression.literal("abc"))
    assertEquals("assert + expression", "[+, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_lt() {
    val expression = lt {
      // test builder function
      lt {}
    }
    assertEquals("assert < expression", "[<, [<]]", expression.toString())
  }

  @Test
  fun expression_lt() {
    val expression = Expression.lt(Expression.literal("abc"))
    assertEquals("assert < expression", "[<, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_lte() {
    val expression = lte {
      // test builder function
      lte {}
    }
    assertEquals("assert <= expression", "[<=, [<=]]", expression.toString())
  }

  @Test
  fun expression_lte() {
    val expression = Expression.lte(Expression.literal("abc"))
    assertEquals("assert <= expression", "[<=, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_eq() {
    val expression = eq {
      // test builder function
      eq {}
    }
    assertEquals("assert == expression", "[==, [==]]", expression.toString())
  }

  @Test
  fun expression_eq() {
    val expression = Expression.eq(Expression.literal("abc"))
    assertEquals("assert == expression", "[==, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_gt() {
    val expression = gt {
      // test builder function
      gt {}
    }
    assertEquals("assert > expression", "[>, [>]]", expression.toString())
  }

  @Test
  fun expression_gt() {
    val expression = Expression.gt(Expression.literal("abc"))
    assertEquals("assert > expression", "[>, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_gte() {
    val expression = gte {
      // test builder function
      gte {}
    }
    assertEquals("assert >= expression", "[>=, [>=]]", expression.toString())
  }

  @Test
  fun expression_gte() {
    val expression = Expression.gte(Expression.literal("abc"))
    assertEquals("assert >= expression", "[>=, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_abs() {
    val expression = abs {
      // test builder function
      abs {}
    }
    assertEquals("assert abs expression", "[abs, [abs]]", expression.toString())
  }

  @Test
  fun expression_abs() {
    val expression = Expression.abs(Expression.literal("abc"))
    assertEquals("assert abs expression", "[abs, abc]", expression.toString())
  }

  @Test
  fun expression_accumulated() {
    val expression = accumulated()
    assertEquals("assert accumulated expression", "[accumulated]", expression.toString())
  }

  @Test
  fun dsl_expression_acos() {
    val expression = acos {
      // test builder function
      acos {}
    }
    assertEquals("assert acos expression", "[acos, [acos]]", expression.toString())
  }

  @Test
  fun expression_acos() {
    val expression = Expression.acos(Expression.literal("abc"))
    assertEquals("assert acos expression", "[acos, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_all() {
    val expression = all {
      // test builder function
      all {}
    }
    assertEquals("assert all expression", "[all, [all]]", expression.toString())
  }

  @Test
  fun expression_all() {
    val expression = Expression.all(Expression.literal("abc"))
    assertEquals("assert all expression", "[all, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_any() {
    val expression = any {
      // test builder function
      any {}
    }
    assertEquals("assert any expression", "[any, [any]]", expression.toString())
  }

  @Test
  fun expression_any() {
    val expression = Expression.any(Expression.literal("abc"))
    assertEquals("assert any expression", "[any, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_array() {
    val expression = array {
      // test builder function
      array {}
    }
    assertEquals("assert array expression", "[array, [array]]", expression.toString())
  }

  @Test
  fun expression_array() {
    val expression = Expression.array(Expression.literal("abc"))
    assertEquals("assert array expression", "[array, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_asin() {
    val expression = asin {
      // test builder function
      asin {}
    }
    assertEquals("assert asin expression", "[asin, [asin]]", expression.toString())
  }

  @Test
  fun expression_asin() {
    val expression = Expression.asin(Expression.literal("abc"))
    assertEquals("assert asin expression", "[asin, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_at() {
    val expression = at {
      // test builder function
      at {}
    }
    assertEquals("assert at expression", "[at, [at]]", expression.toString())
  }

  @Test
  fun expression_at() {
    val expression = Expression.at(Expression.literal("abc"))
    assertEquals("assert at expression", "[at, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_atan() {
    val expression = atan {
      // test builder function
      atan {}
    }
    assertEquals("assert atan expression", "[atan, [atan]]", expression.toString())
  }

  @Test
  fun expression_atan() {
    val expression = Expression.atan(Expression.literal("abc"))
    assertEquals("assert atan expression", "[atan, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_boolean() {
    val expression = boolean {
      // test builder function
      boolean {}
    }
    assertEquals("assert boolean expression", "[boolean, [boolean]]", expression.toString())
  }

  @Test
  fun expression_boolean() {
    val expression = Expression.boolean(Expression.literal("abc"))
    assertEquals("assert boolean expression", "[boolean, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_switchCase() {
    val expression = switchCase {
      // test builder function
      switchCase {}
    }
    assertEquals("assert case expression", "[case, [case]]", expression.toString())
  }

  @Test
  fun expression_switchCase() {
    val expression = Expression.switchCase(Expression.literal("abc"))
    assertEquals("assert case expression", "[case, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_ceil() {
    val expression = ceil {
      // test builder function
      ceil {}
    }
    assertEquals("assert ceil expression", "[ceil, [ceil]]", expression.toString())
  }

  @Test
  fun expression_ceil() {
    val expression = Expression.ceil(Expression.literal("abc"))
    assertEquals("assert ceil expression", "[ceil, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_coalesce() {
    val expression = coalesce {
      // test builder function
      coalesce {}
    }
    assertEquals("assert coalesce expression", "[coalesce, [coalesce]]", expression.toString())
  }

  @Test
  fun expression_coalesce() {
    val expression = Expression.coalesce(Expression.literal("abc"))
    assertEquals("assert coalesce expression", "[coalesce, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_collator() {
    val expression = collator {
      caseSensitive(true)
      diacriticSensitive(true)
      locale(Locale("en-US"))
    }
    assertEquals(
      "assert collator expression",
      "[collator, {diacritic-sensitive=true, case-sensitive=true, locale=en-us}]",
      expression.toString()
    )
  }

  @Test
  fun expression_collator() {
    val expression = Expression.collator(
      caseSensitive = true,
      diacriticSensitive = true,
      locale = Locale("en-US")
    )
    assertEquals(
      "assert collator expression",
      "[collator, {diacritic-sensitive=true, case-sensitive=true, locale=en-us}]",
      expression.toString()
    )
  }

  @Test
  fun dsl_expression_concat() {
    val expression = concat {
      // test builder function
      concat {}
    }
    assertEquals("assert concat expression", "[concat, [concat]]", expression.toString())
  }

  @Test
  fun expression_concat() {
    val expression = Expression.concat(Expression.literal("abc"))
    assertEquals("assert concat expression", "[concat, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_cos() {
    val expression = cos {
      // test builder function
      cos {}
    }
    assertEquals("assert cos expression", "[cos, [cos]]", expression.toString())
  }

  @Test
  fun expression_cos() {
    val expression = Expression.cos(Expression.literal("abc"))
    assertEquals("assert cos expression", "[cos, abc]", expression.toString())
  }

  @Test
  fun expression_distance() {
    val polygon = Polygon.fromJson(
      """
        {
            "type": "Polygon",
            "coordinates": [
                [[30, 10], [40, 40], [20, 40], [10, 20], [30, 10]]
            ]
        }
      """.trimIndent()
    )
    val expression = Expression.distance(polygon)
    assertEquals("assert distance expression", "[distance, {coordinates=[30, 30], type=Polygon}]", expression.toString())
  }

  @Test
  fun dsl_expression_downcase() {
    val expression = downcase {
      // test builder function
      downcase {}
    }
    assertEquals("assert downcase expression", "[downcase, [downcase]]", expression.toString())
  }

  @Test
  fun expression_downcase() {
    val expression = Expression.downcase(Expression.literal("abc"))
    assertEquals("assert downcase expression", "[downcase, abc]", expression.toString())
  }

  @Test
  fun expression_e() {
    val expression = e()
    assertEquals("assert e expression", "[e]", expression.toString())
  }

  @Test
  fun dsl_expression_featureState() {
    val expression = featureState {
      // test builder function
      featureState {}
    }
    assertEquals("assert feature-state expression", "[feature-state, [feature-state]]", expression.toString())
  }

  @Test
  fun expression_featureState() {
    val expression = Expression.featureState(Expression.literal("abc"))
    assertEquals("assert feature-state expression", "[feature-state, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_floor() {
    val expression = floor {
      // test builder function
      floor {}
    }
    assertEquals("assert floor expression", "[floor, [floor]]", expression.toString())
  }

  @Test
  fun expression_floor() {
    val expression = Expression.floor(Expression.literal("abc"))
    assertEquals("assert floor expression", "[floor, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_format() {
    val expression = format {
      formatSection("test") {
        fontScale(1.0)
        textFont(listOf("font1", "font2"))
        textColor("blue")
      }
    }
    assertEquals(
      "assert format expression",
      "[format, test, {text-color=blue, font-scale=1.0, text-font=[literal, [font1, font2]]}]",
      expression.toString()
    )
  }

  @Test
  fun expression_format() {
    val expression = Expression.format(
      FormatSection(
        content = literal("test"),
        fontScale = literal(1.0),
        textFont = literal(listOf("font1", "font2")),
        textColor = literal("blue")
      )
    )
    assertEquals(
      "assert format expression",
      "[format, test, {text-color=blue, font-scale=1.0, text-font=[literal, [font1, font2]]}]",
      expression.toString()
    )
  }

  @Test
  fun expression_geometryType() {
    val expression = geometryType()
    assertEquals("assert geometry-type expression", "[geometry-type]", expression.toString())
  }

  @Test
  fun dsl_expression_get() {
    val expression = get {
      // test builder function
      get {}
    }
    assertEquals("assert get expression", "[get, [get]]", expression.toString())
  }

  @Test
  fun expression_get() {
    val expression = Expression.get(Expression.literal("abc"))
    assertEquals("assert get expression", "[get, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_has() {
    val expression = has {
      // test builder function
      has {}
    }
    assertEquals("assert has expression", "[has, [has]]", expression.toString())
  }

  @Test
  fun expression_has() {
    val expression = Expression.has(Expression.literal("abc"))
    assertEquals("assert has expression", "[has, abc]", expression.toString())
  }

  @Test
  fun expression_heatmapDensity() {
    val expression = heatmapDensity()
    assertEquals("assert heatmap-density expression", "[heatmap-density]", expression.toString())
  }

  @Test
  fun expression_id() {
    val expression = id()
    assertEquals("assert id expression", "[id]", expression.toString())
  }

  @Test
  fun dsl_expression_image() {
    val expression = image {
      // test builder function
      image {}
    }
    assertEquals("assert image expression", "[image, [image]]", expression.toString())
  }

  @Test
  fun expression_image() {
    val expression = Expression.image(Expression.literal("abc"))
    assertEquals("assert image expression", "[image, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_inExpression() {
    val expression = inExpression {
      // test builder function
      inExpression {}
    }
    assertEquals("assert in expression", "[in, [in]]", expression.toString())
  }

  @Test
  fun expression_inExpression() {
    val expression = Expression.inExpression(Expression.literal("abc"))
    assertEquals("assert in expression", "[in, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_indexOf() {
    val expression = indexOf {
      // test builder function
      indexOf {}
    }
    assertEquals("assert index-of expression", "[index-of, [index-of]]", expression.toString())
  }

  @Test
  fun expression_indexOf() {
    val expression = Expression.indexOf(Expression.literal("abc"))
    assertEquals("assert index-of expression", "[index-of, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_interpolate_exponential() {
    val expression = interpolate {
      exponential {
        literal(0.5)
      }
      zoom()
      stop {
        literal(1.0)
        color(Color.RED)
      }
      stop {
        literal(5.0)
        color(Color.BLUE)
      }
      stop {
        literal(10.0)
        color(Color.GREEN)
      }
    }
    assertEquals("assert interpolate expression", "[interpolate, [exponential, 0.5], [zoom], 1.0, [rgba, 255.0, 0.0, 0.0, 1.0], 5.0, [rgba, 0.0, 0.0, 255.0, 1.0], 10.0, [rgba, 0.0, 255.0, 0.0, 1.0]]", expression.toString())
  }

  @Test
  fun dsl_expression_interpolate_cubicBezier() {
    val expression = interpolate {
      cubicBezier {
        literal(0.42)
        literal(0.0)
        literal(1.0)
        literal(1.0)
      }
      zoom()
      stop {
        literal(1.0)
        color(Color.RED)
      }
      stop {
        literal(5.0)
        color(Color.BLUE)
      }
      stop {
        literal(10.0)
        color(Color.GREEN)
      }
    }
    assertEquals("assert interpolate expression", "[interpolate, [cubic-bezier, 0.42, 0.0, 1.0, 1.0], [zoom], 1.0, [rgba, 255.0, 0.0, 0.0, 1.0], 5.0, [rgba, 0.0, 0.0, 255.0, 1.0], 10.0, [rgba, 0.0, 255.0, 0.0, 1.0]]", expression.toString())
  }

  @Test
  fun dls_expression_interpolate_linear() {
    val expression = interpolate {
      linear()
      zoom()
      stop {
        literal(12)
        step {
          get { literal("stroke-width") }
          color(Color.BLACK)
          stop {
            literal(1.0)
            color(Color.RED)
          }
          stop {
            literal(2.0)
            color(Color.WHITE)
          }
          stop {
            literal(3.0)
            color(Color.BLUE)
          }
        }
      }
      stop {
        literal(15)
        step {
          get { literal("stroke-width") }
          color(Color.BLACK)
          stop {
            literal(1.0)
            color(Color.YELLOW)
          }
          stop {
            literal(2.0)
            color(Color.LTGRAY)
          }
          stop {
            literal(3.0)
            color(Color.BLUE)
          }
        }
      }
      stop {
        literal(18)
        step {
          get { literal("stroke-width") }
          color(Color.BLACK)
          stop {
            literal(1.0)
            color(Color.WHITE)
          }
          stop {
            literal(2.0)
            color(Color.GRAY)
          }
          stop {
            literal(3.0)
            color(Color.GREEN)
          }
        }
      }
    }
    assertEquals("assert interpolate expression", "[interpolate, [linear], [zoom], 12, [step, [get, stroke-width], [rgba, 0.0, 0.0, 0.0, 1.0], 1.0, [rgba, 255.0, 0.0, 0.0, 1.0], 2.0, [rgba, 255.0, 255.0, 255.0, 1.0], 3.0, [rgba, 0.0, 0.0, 255.0, 1.0]], 15, [step, [get, stroke-width], [rgba, 0.0, 0.0, 0.0, 1.0], 1.0, [rgba, 255.0, 255.0, 0.0, 1.0], 2.0, [rgba, 204.0, 204.0, 204.0, 1.0], 3.0, [rgba, 0.0, 0.0, 255.0, 1.0]], 18, [step, [get, stroke-width], [rgba, 0.0, 0.0, 0.0, 1.0], 1.0, [rgba, 255.0, 255.0, 255.0, 1.0], 2.0, [rgba, 136.0, 136.0, 136.0, 1.0], 3.0, [rgba, 0.0, 255.0, 0.0, 1.0]]]", expression.toString())
  }

  @Test
  fun expression_interpolate_exponential() {
    val expression = Expression.interpolate(
      Expression.exponential(literal(0.5)),
      Expression.zoom(),
      Expression.literal(1.0),
      Expression.color(Color.RED),
      Expression.literal(5.0),
      Expression.color(Color.BLUE),
      Expression.literal(10.0),
      Expression.color(Color.GREEN)
    )
    assertEquals("assert interpolate expression", "[interpolate, [exponential, 0.5], [zoom], 1.0, [rgba, 255.0, 0.0, 0.0, 1.0], 5.0, [rgba, 0.0, 0.0, 255.0, 1.0], 10.0, [rgba, 0.0, 255.0, 0.0, 1.0]]", expression.toString())
  }

  @Test
  fun expression_interpolate_cubicBezier() {
    val expression = Expression.interpolate(
      Expression.cubicBezier(
        Expression.literal(0.42),
        Expression.literal(0.0),
        Expression.literal(1.0),
        Expression.literal(1.0)
      ),
      Expression.zoom(),
      Expression.literal(1.0),
      Expression.color(Color.RED),
      Expression.literal(5.0),
      Expression.color(Color.BLUE),
      Expression.literal(10.0),
      Expression.color(Color.GREEN)
    )
    assertEquals("assert interpolate expression", "[interpolate, [cubic-bezier, 0.42, 0.0, 1.0, 1.0], [zoom], 1.0, [rgba, 255.0, 0.0, 0.0, 1.0], 5.0, [rgba, 0.0, 0.0, 255.0, 1.0], 10.0, [rgba, 0.0, 255.0, 0.0, 1.0]]", expression.toString())
  }

  @Test
  fun expression_interpolate_linear() {
    val expression = Expression.interpolate(
      Expression.linear(),
      Expression.zoom(),
      Expression.literal(12),
      Expression.step(
        Expression.get(Expression.literal("stroke-width")),
        Expression.color(Color.BLACK),
        Expression.literal(1.0),
        Expression.color(Color.RED),
        Expression.literal(2.0),
        Expression.color(Color.WHITE),
        Expression.literal(3.0),
        Expression.color(Color.BLUE)
      ),
      Expression.literal(15),
      Expression.step(
        Expression.get(Expression.literal("stroke-width")),
        Expression.color(Color.BLACK),
        Expression.literal(1.0),
        Expression.color(Color.YELLOW),
        Expression.literal(2.0),
        Expression.color(Color.LTGRAY),
        Expression.literal(3.0),
        Expression.color(Color.BLUE)
      ),
      Expression.literal(18),
      Expression.step(
        Expression.get(Expression.literal("stroke-width")),
        Expression.color(Color.BLACK),
        Expression.literal(1.0),
        Expression.color(Color.WHITE),
        Expression.literal(2.0),
        Expression.color(Color.GRAY),
        Expression.literal(3.0),
        Expression.color(Color.GREEN)
      )
    )
    assertEquals("assert interpolate expression", "[interpolate, [linear], [zoom], 12, [step, [get, stroke-width], [rgba, 0.0, 0.0, 0.0, 1.0], 1.0, [rgba, 255.0, 0.0, 0.0, 1.0], 2.0, [rgba, 255.0, 255.0, 255.0, 1.0], 3.0, [rgba, 0.0, 0.0, 255.0, 1.0]], 15, [step, [get, stroke-width], [rgba, 0.0, 0.0, 0.0, 1.0], 1.0, [rgba, 255.0, 255.0, 0.0, 1.0], 2.0, [rgba, 204.0, 204.0, 204.0, 1.0], 3.0, [rgba, 0.0, 0.0, 255.0, 1.0]], 18, [step, [get, stroke-width], [rgba, 0.0, 0.0, 0.0, 1.0], 1.0, [rgba, 255.0, 255.0, 255.0, 1.0], 2.0, [rgba, 136.0, 136.0, 136.0, 1.0], 3.0, [rgba, 0.0, 255.0, 0.0, 1.0]]]", expression.toString())
  }

  @Test
  fun dsl_expression_isSupportedScript() {
    val expression = isSupportedScript {
      // test builder function
      isSupportedScript {}
    }
    assertEquals("assert is-supported-script expression", "[is-supported-script, [is-supported-script]]", expression.toString())
  }

  @Test
  fun expression_isSupportedScript() {
    val expression = Expression.isSupportedScript(Expression.literal("abc"))
    assertEquals("assert is-supported-script expression", "[is-supported-script, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_length() {
    val expression = length {
      // test builder function
      length {}
    }
    assertEquals("assert length expression", "[length, [length]]", expression.toString())
  }

  @Test
  fun expression_length() {
    val expression = Expression.length(Expression.literal("abc"))
    assertEquals("assert length expression", "[length, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_letExpression() {
    val expression = letExpression {
      // test builder function
      letExpression {}
    }
    assertEquals("assert let expression", "[let, [let]]", expression.toString())
  }

  @Test
  fun expression_letExpression() {
    val expression = Expression.letExpression(Expression.literal("abc"))
    assertEquals("assert let expression", "[let, abc]", expression.toString())
  }

  @Test
  fun expression_lineProgress() {
    val expression = lineProgress()
    assertEquals("assert line-progress expression", "[line-progress]", expression.toString())
  }

  @Test
  fun expression_literal_Double() {
    val expression = literal(0.1)
    assertEquals("assert literal expression", "0.1", expression.toString())
  }

  @Test
  fun expression_literal_Long() {
    val expression = literal(123)
    assertEquals("assert literal expression", "123", expression.toString())
  }

  @Test
  fun expression_literal_Boolean() {
    val expression = literal(true)
    assertEquals("assert literal expression", "true", expression.toString())
  }

  @Test
  fun expression_literal_String() {
    val expression = literal("abc")
    assertEquals("assert literal expression", "abc", expression.toString())
  }

  @Test
  fun expression_literal_DoubleArray() {
    val expression = literal(listOf(0.1, 0.2, 0.3))
    assertEquals("assert literal expression", "[literal, [0.1, 0.2, 0.3]]", expression.toString())
  }

  @Test
  fun expression_literal_LongArray() {
    val expression = literal(listOf(1, 2, 3))
    assertEquals("assert literal expression", "[literal, [1, 2, 3]]", expression.toString())
  }

  @Test
  fun expression_literal_BooleanArray() {
    val expression = literal(listOf(true, false, true))
    assertEquals("assert literal expression", "[literal, [true, false, true]]", expression.toString())
  }

  @Test
  fun expression_literal_StringList() {
    val expression = literal(listOf("apple", "orange"))
    assertEquals("assert literal expression", "[literal, [apple, orange]]", expression.toString())
  }

  @Test
  fun dsl_expression_ln() {
    val expression = ln {
      // test builder function
      ln {}
    }
    assertEquals("assert ln expression", "[ln, [ln]]", expression.toString())
  }

  @Test
  fun expression_ln() {
    val expression = Expression.ln(Expression.literal("abc"))
    assertEquals("assert ln expression", "[ln, abc]", expression.toString())
  }

  @Test
  fun expression_ln2() {
    val expression = ln2()
    assertEquals("assert ln2 expression", "[ln2]", expression.toString())
  }

  @Test
  fun dsl_expression_log10() {
    val expression = log10 {
      // test builder function
      log10 {}
    }
    assertEquals("assert log10 expression", "[log10, [log10]]", expression.toString())
  }

  @Test
  fun expression_log10() {
    val expression = Expression.log10(Expression.literal("abc"))
    assertEquals("assert log10 expression", "[log10, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_log2() {
    val expression = log2 {
      // test builder function
      log2 {}
    }
    assertEquals("assert log2 expression", "[log2, [log2]]", expression.toString())
  }

  @Test
  fun expression_log2() {
    val expression = Expression.log2(Expression.literal("abc"))
    assertEquals("assert log2 expression", "[log2, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_match() {
    val expression = match {
      // test builder function
      match {}
    }
    assertEquals("assert match expression", "[match, [match]]", expression.toString())
  }

  @Test
  fun expression_match() {
    val expression = Expression.match(Expression.literal("abc"))
    assertEquals("assert match expression", "[match, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_max() {
    val expression = max {
      // test builder function
      max {}
    }
    assertEquals("assert max expression", "[max, [max]]", expression.toString())
  }

  @Test
  fun expression_max() {
    val expression = Expression.max(Expression.literal("abc"))
    assertEquals("assert max expression", "[max, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_min() {
    val expression = min {
      // test builder function
      min {}
    }
    assertEquals("assert min expression", "[min, [min]]", expression.toString())
  }

  @Test
  fun expression_min() {
    val expression = Expression.min(Expression.literal("abc"))
    assertEquals("assert min expression", "[min, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_number() {
    val expression = number {
      // test builder function
      number {}
    }
    assertEquals("assert number expression", "[number, [number]]", expression.toString())
  }

  @Test
  fun expression_number() {
    val expression = Expression.number(Expression.literal("abc"))
    assertEquals("assert number expression", "[number, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_numberFormat() {
    val expression = numberFormat(literal(10)) {
      locale("en-US")
      currency("USD")
      minFractionDigits(1)
      maxFractionDigits(5)
    }
    assertEquals(
      "assert number-format expression",
      "[number-format, 10, {min-fraction-digits=1, max-fraction-digits=5, currency=USD, locale=en-US}]",
      expression.toString()
    )
  }

  @Test
  fun expression_numberFormat() {
    val expression = Expression.numberFormat(
      number = literal(10),
      locale = literal("en-US"),
      currency = literal("USD"),
      minFractionDigits = literal(1),
      maxFractionDigits = literal(5)
    )
    assertEquals(
      "assert number-format expression",
      "[number-format, 10, {min-fraction-digits=1, max-fraction-digits=5, currency=USD, locale=en-US}]",
      expression.toString()
    )
  }

  @Test
  fun dsl_expression_objectExpression() {
    val expression = objectExpression {
      // test builder function
      objectExpression {}
    }
    assertEquals("assert object expression", "[object, [object]]", expression.toString())
  }

  @Test
  fun expression_objectExpression() {
    val expression = Expression.objectExpression(Expression.literal("abc"))
    assertEquals("assert object expression", "[object, abc]", expression.toString())
  }

  @Test
  fun expression_pi() {
    val expression = pi()
    assertEquals("assert pi expression", "[pi]", expression.toString())
  }

  @Test
  fun expression_properties() {
    val expression = properties()
    assertEquals("assert properties expression", "[properties]", expression.toString())
  }

  @Test
  fun dsl_expression_resolvedLocale() {
    val expression = resolvedLocale {
      // test builder function
      resolvedLocale {}
    }
    assertEquals("assert resolved-locale expression", "[resolved-locale, [resolved-locale]]", expression.toString())
  }

  @Test
  fun expression_resolvedLocale() {
    val expression = Expression.resolvedLocale(Expression.literal("abc"))
    assertEquals("assert resolved-locale expression", "[resolved-locale, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_rgb() {
    val expression = rgb {
      // test builder function
      rgb {}
    }
    assertEquals("assert rgb expression", "[rgb, [rgb]]", expression.toString())
  }

  @Test
  fun expression_rgb() {
    val expression = Expression.rgb(Expression.literal("abc"))
    assertEquals("assert rgb expression", "[rgb, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_rgba() {
    val expression = rgba {
      // test builder function
      rgba {}
    }
    assertEquals("assert rgba expression", "[rgba, [rgba]]", expression.toString())
  }

  @Test
  fun expression_rgba() {
    val expression = Expression.rgba(Expression.literal("abc"))
    assertEquals("assert rgba expression", "[rgba, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_round() {
    val expression = round {
      // test builder function
      round {}
    }
    assertEquals("assert round expression", "[round, [round]]", expression.toString())
  }

  @Test
  fun expression_round() {
    val expression = Expression.round(Expression.literal("abc"))
    assertEquals("assert round expression", "[round, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_sin() {
    val expression = sin {
      // test builder function
      sin {}
    }
    assertEquals("assert sin expression", "[sin, [sin]]", expression.toString())
  }

  @Test
  fun expression_sin() {
    val expression = Expression.sin(Expression.literal("abc"))
    assertEquals("assert sin expression", "[sin, abc]", expression.toString())
  }

  @Test
  fun expression_skyRadialProgress() {
    val expression = skyRadialProgress()
    assertEquals("assert sky-radial-progress expression", "[sky-radial-progress]", expression.toString())
  }

  @Test
  fun dsl_expression_slice() {
    val expression = slice {
      // test builder function
      slice {}
    }
    assertEquals("assert slice expression", "[slice, [slice]]", expression.toString())
  }

  @Test
  fun expression_slice() {
    val expression = Expression.slice(Expression.literal("abc"))
    assertEquals("assert slice expression", "[slice, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_sqrt() {
    val expression = sqrt {
      // test builder function
      sqrt {}
    }
    assertEquals("assert sqrt expression", "[sqrt, [sqrt]]", expression.toString())
  }

  @Test
  fun expression_sqrt() {
    val expression = Expression.sqrt(Expression.literal("abc"))
    assertEquals("assert sqrt expression", "[sqrt, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_step() {
    val expression = step {
      // test builder function
      step {}
    }
    assertEquals("assert step expression", "[step, [step]]", expression.toString())
  }

  @Test
  fun expression_step() {
    val expression = Expression.step(Expression.literal("abc"))
    assertEquals("assert step expression", "[step, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_string() {
    val expression = string {
      // test builder function
      string {}
    }
    assertEquals("assert string expression", "[string, [string]]", expression.toString())
  }

  @Test
  fun expression_string() {
    val expression = Expression.string(Expression.literal("abc"))
    assertEquals("assert string expression", "[string, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_tan() {
    val expression = tan {
      // test builder function
      tan {}
    }
    assertEquals("assert tan expression", "[tan, [tan]]", expression.toString())
  }

  @Test
  fun expression_tan() {
    val expression = Expression.tan(Expression.literal("abc"))
    assertEquals("assert tan expression", "[tan, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_toBoolean() {
    val expression = toBoolean {
      // test builder function
      toBoolean {}
    }
    assertEquals("assert to-boolean expression", "[to-boolean, [to-boolean]]", expression.toString())
  }

  @Test
  fun expression_toBoolean() {
    val expression = Expression.toBoolean(Expression.literal("abc"))
    assertEquals("assert to-boolean expression", "[to-boolean, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_toColor() {
    val expression = toColor {
      // test builder function
      toColor {}
    }
    assertEquals("assert to-color expression", "[to-color, [to-color]]", expression.toString())
  }

  @Test
  fun expression_toColor() {
    val expression = Expression.toColor(Expression.literal("abc"))
    assertEquals("assert to-color expression", "[to-color, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_toNumber() {
    val expression = toNumber {
      // test builder function
      toNumber {}
    }
    assertEquals("assert to-number expression", "[to-number, [to-number]]", expression.toString())
  }

  @Test
  fun expression_toNumber() {
    val expression = Expression.toNumber(Expression.literal("abc"))
    assertEquals("assert to-number expression", "[to-number, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_toRgba() {
    val expression = toRgba {
      // test builder function
      toRgba {}
    }
    assertEquals("assert to-rgba expression", "[to-rgba, [to-rgba]]", expression.toString())
  }

  @Test
  fun expression_toRgba() {
    val expression = Expression.toRgba(Expression.literal("abc"))
    assertEquals("assert to-rgba expression", "[to-rgba, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_toString() {
    val expression = toString {
      // test builder function
      toString {}
    }
    assertEquals("assert to-string expression", "[to-string, [to-string]]", expression.toString())
  }

  @Test
  fun expression_toString() {
    val expression = Expression.toString(Expression.literal("abc"))
    assertEquals("assert to-string expression", "[to-string, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_typeofExpression() {
    val expression = typeofExpression {
      // test builder function
      typeofExpression {}
    }
    assertEquals("assert typeof expression", "[typeof, [typeof]]", expression.toString())
  }

  @Test
  fun expression_typeofExpression() {
    val expression = Expression.typeofExpression(Expression.literal("abc"))
    assertEquals("assert typeof expression", "[typeof, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_upcase() {
    val expression = upcase {
      // test builder function
      upcase {}
    }
    assertEquals("assert upcase expression", "[upcase, [upcase]]", expression.toString())
  }

  @Test
  fun expression_upcase() {
    val expression = Expression.upcase(Expression.literal("abc"))
    assertEquals("assert upcase expression", "[upcase, abc]", expression.toString())
  }

  @Test
  fun dsl_expression_varExpression() {
    val expression = varExpression {
      // test builder function
      varExpression {}
    }
    assertEquals("assert var expression", "[var, [var]]", expression.toString())
  }

  @Test
  fun expression_varExpression() {
    val expression = Expression.varExpression(Expression.literal("abc"))
    assertEquals("assert var expression", "[var, abc]", expression.toString())
  }

  @Test
  fun expression_within() {
    val polygon = Polygon.fromJson(
      """
        {
            "type": "Polygon",
            "coordinates": [
                [[30, 10], [40, 40], [20, 40], [10, 20], [30, 10]]
            ]
        }
      """.trimIndent()
    )
    val expression = Expression.within(polygon)
    assertEquals("assert within expression", "[within, {coordinates=[30, 30], type=Polygon}]", expression.toString())
  }

  @Test
  fun expression_zoom() {
    val expression = zoom()
    assertEquals("assert zoom expression", "[zoom]", expression.toString())
  }

  @Test
  fun expression_rgb_numbers() {
    val expression = rgb(255.0, 0.0, 0.0)
    assertEquals("assert color expression", "[rgb, 255.0, 0.0, 0.0]", expression.toString())
  }

  @Test
  fun expression_rgba_numbers() {
    val expression = rgba(255.0, 0.0, 0.0, 1.0)
    assertEquals("assert color expression", "[rgba, 255.0, 0.0, 0.0, 1.0]", expression.toString())
  }

  @Test
  fun expression_color() {
    val expression = color(Color.RED)
    assertEquals("assert color expression", "[rgba, 255.0, 0.0, 0.0, 1.0]", expression.toString())
  }

  @Test
  fun expression_raw() {
    val expression = sum {
      literal(1.0)
      literal(2.0)
    }
    every { Value.fromJson(any()) } returns ExpectedFactory.createValue(Value("[+, 1.0, 2.0]"))
    assertEquals(expression.toString(), Expression.fromRaw("[+, 1.0, 2.0]").toString())
    verify { Value.fromJson("[+, 1.0, 2.0]") }
  }

  @Test
  fun expression_convenience_get() {
    val expression = get("foobar")
    assertEquals("[get, foobar]", expression.toString())
  }

  @Test
  fun expression_convenience_get_multi() {
    val expression = get("foobar", get("fallback"))
    assertEquals("[get, foobar, [get, fallback]]", expression.toString())
  }

  @Test
  fun expression_convenience_not() {
    val expression = not(true)
    assertEquals("[!, true]", expression.toString())
  }

  @Test
  fun expression_convenience_at() {
    val expression = at(1.0, get("array"))
    assertEquals("[at, 1.0, [get, array]]", expression.toString())
  }

  @Test
  fun expression_convenience_inExpression() {
    val expression = inExpression(1.0, get("array"))
    assertEquals("[in, 1.0, [get, array]]", expression.toString())
  }

  @Test
  fun expression_convenience_inExpression_multi() {
    val expression = inExpression("foobar", get("array"))
    assertEquals("[in, foobar, [get, array]]", expression.toString())
  }

  @Test
  fun expression_convenience_has_multi() {
    val expression = has("foobar", get("array"))
    assertEquals("[has, foobar, [get, array]]", expression.toString())
  }

  @Test
  fun expression_convenience_has() {
    val expression = has("foobar")
    assertEquals("[has, foobar]", expression.toString())
  }

  @Test
  fun expression_convenience_length() {
    val expression = length("foobar")
    assertEquals("[length, foobar]", expression.toString())
  }

  @Test
  fun expression_convenience_sum() {
    val expression = sum(1.0, 2.0, 3.0)
    assertEquals("[+, 1.0, 2.0, 3.0]", expression.toString())
  }

  @Test
  fun expression_convenience_product() {
    val expression = product(1.0, 2.0, 3.0)
    assertEquals("[*, 1.0, 2.0, 3.0]", expression.toString())
  }

  @Test
  fun expression_convenience_subtract_multi() {
    val expression = subtract(1.0, 2.0)
    assertEquals("[-, 1.0, 2.0]", expression.toString())
  }

  @Test
  fun expression_convenience_subtract() {
    val expression = subtract(1.0)
    assertEquals("[-, 1.0]", expression.toString())
  }

  @Test
  fun expression_convenience_division() {
    val expression = division(1.0, 2.0)
    assertEquals("[/, 1.0, 2.0]", expression.toString())
  }

  @Test
  fun expression_convenience_mod() {
    val expression = mod(1.0, 2.0)
    assertEquals("[%, 1.0, 2.0]", expression.toString())
  }

  @Test
  fun expression_convenience_pow() {
    val expression = pow(1.0, 2.0)
    assertEquals("[^, 1.0, 2.0]", expression.toString())
  }

  @Test
  fun expression_convenience_sqrt() {
    val expression = sqrt(1.0)
    assertEquals("[sqrt, 1.0]", expression.toString())
  }

  @Test
  fun expression_convenience_log10() {
    val expression = log10(1.0)
    assertEquals("[log10, 1.0]", expression.toString())
  }

  @Test
  fun expression_convenience_ln() {
    val expression = ln(1.0)
    assertEquals("[ln, 1.0]", expression.toString())
  }

  @Test
  fun expression_convenience_log2() {
    val expression = log2(1.0)
    assertEquals("[log2, 1.0]", expression.toString())
  }

  @Test
  fun expression_convenience_sin() {
    val expression = sin(1.0)
    assertEquals("[sin, 1.0]", expression.toString())
  }

  @Test
  fun expression_convenience_cos() {
    val expression = cos(1.0)
    assertEquals("[cos, 1.0]", expression.toString())
  }

  @Test
  fun expression_convenience_tan() {
    val expression = tan(1.0)
    assertEquals("[tan, 1.0]", expression.toString())
  }

  @Test
  fun expression_convenience_asin() {
    val expression = asin(1.0)
    assertEquals("[asin, 1.0]", expression.toString())
  }

  @Test
  fun expression_convenience_acos() {
    val expression = acos(1.0)
    assertEquals("[acos, 1.0]", expression.toString())
  }

  @Test
  fun expression_convenience_atan() {
    val expression = atan(1.0)
    assertEquals("[atan, 1.0]", expression.toString())
  }

  @Test
  fun expression_convenience_min() {
    val expression = min(1.0, 2.0)
    assertEquals("[min, 1.0, 2.0]", expression.toString())
  }

  @Test
  fun expression_convenience_max() {
    val expression = max(1.0, 2.0)
    assertEquals("[max, 1.0, 2.0]", expression.toString())
  }

  @Test
  fun expression_convenience_round() {
    val expression = round(1.0)
    assertEquals("[round, 1.0]", expression.toString())
  }

  @Test
  fun expression_convenience_abs() {
    val expression = abs(1.0)
    assertEquals("[abs, 1.0]", expression.toString())
  }

  @Test
  fun expression_convenience_ceil() {
    val expression = ceil(1.0)
    assertEquals("[ceil, 1.0]", expression.toString())
  }

  @Test
  fun expression_convenience_floor() {
    val expression = floor(1.0)
    assertEquals("[floor, 1.0]", expression.toString())
  }

  @Test
  fun expression_convenience_isSupportedScript() {
    val expression = isSupportedScript("foo")
    assertEquals("[is-supported-script, foo]", expression.toString())
  }

  @Test
  fun expression_convenience_upcase() {
    val expression = upcase("foo")
    assertEquals("[upcase, foo]", expression.toString())
  }

  @Test
  fun expression_convenience_downcase() {
    val expression = downcase("foo")
    assertEquals("[downcase, foo]", expression.toString())
  }

  @Test
  fun expression_convenience_concat() {
    val expression = concat("foo", "bar")
    assertEquals("[concat, foo, bar]", expression.toString())
  }

  @Test
  fun expression_convenience_varExpression() {
    val expression = varExpression("foo")
    assertEquals("[var, foo]", expression.toString())
  }
}

// End of generated file.