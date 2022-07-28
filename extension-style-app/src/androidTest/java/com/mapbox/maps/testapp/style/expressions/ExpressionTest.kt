package com.mapbox.maps.testapp.style.expressions

import android.graphics.Color
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Polygon
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.generated.*
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.collections.HashMap

/**
 * Basic smoke tests for Expressions
 */
@RunWith(AndroidJUnit4::class)
class ExpressionTest : BaseStyleTest() {

  /**
   * For two inputs, returns the result of subtracting the second input from the first. For a single input, returns the result of subtracting it from 0.
   */
  @Test
  @UiThreadTest
  fun subtractTest() {
    val expression = subtract {
      literal(10)
      literal(1)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(9.0, layer.textSize)
  }

  /**
   * Logical negation. Returns `true` if the input is `false`, and `false` if the input is `true`.
   */
  @Test
  @UiThreadTest
  fun notTest() {
    val expression = not {
      literal(true)
    }
    val layer = symbolLayer("id", "source") {
      iconAllowOverlap(expression)
    }
    setupLayer(layer)
    assertEquals(false, layer.iconAllowOverlap)
  }

  /**
   * Returns `true` if the input values are not equal, `false` otherwise. The comparison is strictly typed: values of different runtime types are always considered unequal. Cases where the types are known to be different at parse time are considered invalid and will produce a parse error. Accepts an optional `collator` argument to control locale-dependent string comparisons.
   */
  @Test
  @UiThreadTest
  fun neqTestEq() {
    val expression = neq {
      literal("abc")
      literal("abc")
    }
    val layer = symbolLayer("id", "source") {
      iconAllowOverlap(expression)
    }
    setupLayer(layer)
    assertEquals(false, layer.iconAllowOverlap)
  }

  @Test
  @UiThreadTest
  fun neqTestNeq() {
    val expression = neq {
      literal("abc")
      literal("def")
    }
    val layer = symbolLayer("id", "source") {
      iconAllowOverlap(expression)
    }
    setupLayer(layer)
    assertEquals(true, layer.iconAllowOverlap)
  }

  /**
   * Returns the product of the inputs.
   */
  @Test
  @UiThreadTest
  fun productTest() {
    val expression = product {
      literal(12)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(36.0, layer.textSize)
  }

  /**
   * Returns the result of floating point division of the first input by the second.
   */
  @Test
  @UiThreadTest
  fun divisionTest() {
    val expression = division {
      literal(12)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(4.0, layer.textSize)
  }

  /**
   * Returns the remainder after integer division of the first input by the second.
   */
  @Test
  @UiThreadTest
  fun modTest() {
    val expression = mod {
      literal(4)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(1.0, layer.textSize)
  }

  /**
   * Returns the result of raising the first input to the power specified by the second.
   */
  @Test
  @UiThreadTest
  fun powTest() {
    val expression = pow {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(8.0, layer.textSize)
  }

  /**
   * Returns the sum of the inputs.
   */
  @Test
  @UiThreadTest
  fun sumTest() {
    val expression = sum {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(5.0, layer.textSize)
  }

  /**
   * Returns `true` if the first input is strictly less than the second, `false` otherwise. The arguments are required to be either both strings or both numbers; if during evaluation they are not, expression evaluation produces an error. Cases where this constraint is known not to hold at parse time are considered in valid and will produce a parse error. Accepts an optional `collator` argument to control locale-dependent string comparisons.
   */
  @Test
  @UiThreadTest
  fun ltTest() {
    val expression = lt {
      literal(2)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {
      iconAllowOverlap(expression)
    }
    setupLayer(layer)
    assertEquals(true, layer.iconAllowOverlap)
  }

  /**
   * Returns `true` if the first input is less than or equal to the second, `false` otherwise. The arguments are required to be either both strings or both numbers; if during evaluation they are not, expression evaluation produces an error. Cases where this constraint is known not to hold at parse time are considered in valid and will produce a parse error. Accepts an optional `collator` argument to control locale-dependent string comparisons.
   */
  @Test
  @UiThreadTest
  fun lteTest() {
    val expression = lte {
      literal(3)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {
      iconAllowOverlap(expression)
    }
    setupLayer(layer)
    assertEquals(true, layer.iconAllowOverlap)
    layer.iconAllowOverlap(
      lte {
        literal(3)
        literal(4)
      }
    )
    assertEquals(true, layer.iconAllowOverlap)
    layer.iconAllowOverlap(
      lte {
        literal(4)
        literal(3)
      }
    )
    assertEquals(false, layer.iconAllowOverlap)
  }

  /**
   * Returns `true` if the input values are equal, `false` otherwise. The comparison is strictly typed: values of different runtime types are always considered unequal. Cases where the types are known to be different at parse time are considered invalid and will produce a parse error. Accepts an optional `collator` argument to control locale-dependent string comparisons.
   */
  @Test
  @UiThreadTest
  fun eqTest() {
    val expression = eq {
      literal(3)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {
      iconAllowOverlap(expression)
    }
    setupLayer(layer)
    assertEquals(true, layer.iconAllowOverlap)
    layer.iconAllowOverlap(
      eq {
        literal(4)
        literal(3)
      }
    )
    assertEquals(false, layer.iconAllowOverlap)
  }

  /**
   * Returns `true` if the first input is strictly greater than the second, `false` otherwise. The arguments are required to be either both strings or both numbers; if during evaluation they are not, expression evaluation produces an error. Cases where this constraint is known not to hold at parse time are considered in valid and will produce a parse error. Accepts an optional `collator` argument to control locale-dependent string comparisons.
   */
  @Test
  @UiThreadTest
  fun gtTest() {
    val expression = gt {
      literal(3)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {
      iconAllowOverlap(expression)
    }
    setupLayer(layer)
    assertEquals(false, layer.iconAllowOverlap)
    layer.iconAllowOverlap(
      gt {
        literal(4)
        literal(3)
      }
    )
    assertEquals(true, layer.iconAllowOverlap)
    layer.iconAllowOverlap(
      gt {
        literal(2)
        literal(3)
      }
    )
    assertEquals(false, layer.iconAllowOverlap)
  }

  /**
   * Returns `true` if the first input is greater than or equal to the second, `false` otherwise. The arguments are required to be either both strings or both numbers; if during evaluation they are not, expression evaluation produces an error. Cases where this constraint is known not to hold at parse time are considered in valid and will produce a parse error. Accepts an optional `collator` argument to control locale-dependent string comparisons.
   */
  @Test
  @UiThreadTest
  fun gteTest() {
    val expression = gte {
      literal(3)
      literal(3)
    }
    val layer = symbolLayer("id", "source") {
      iconAllowOverlap(expression)
    }
    setupLayer(layer)
    assertEquals(true, layer.iconAllowOverlap)
    layer.iconAllowOverlap(
      gte {
        literal(4)
        literal(3)
      }
    )
    assertEquals(true, layer.iconAllowOverlap)
    layer.iconAllowOverlap(
      gte {
        literal(2)
        literal(3)
      }
    )
    assertEquals(false, layer.iconAllowOverlap)
  }

  /**
   * Returns the absolute value of the input.
   */
  @Test
  @UiThreadTest
  fun absTest() {
    val expression = abs {
      literal(-3)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(3.0, layer.textSize)
  }

  /**
   * Gets the value of a cluster property accumulated so far. Can only be used in the `clusterProperties` option of a clustered GeoJSON source.
   */
  @Test
  @UiThreadTest
  fun accumulatedTest() {
    val source = geoJsonSource("id") {
      url("http://mock.geojson")
      clusterProperty(
        "max",
        max {
          accumulated()
          get {
            literal("max")
          }
        },
        get {
          literal("mag")
        }
      )
    }
    setupSource(source)
  }

  /**
   * Returns the arccosine of the input.
   */
  @Test
  @UiThreadTest
  fun acosTest() {
    val expression = acos {
      literal(1.0)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(0.0, layer.textSize)
  }

  /**
   * Returns `true` if all the inputs are `true`, `false` otherwise. The inputs are evaluated in order, and evaluation is short-circuiting: once an input expression evaluates to `false`, the result is `false` and no further input expressions are evaluated.
   */
  @Test
  @UiThreadTest
  fun allTest() {
    val expression = all {
      literal(true)
      literal(true)
      literal(false)
    }
    val layer = symbolLayer("id", "source") {
      iconAllowOverlap(expression)
    }
    setupLayer(layer)
    assertEquals(false, layer.iconAllowOverlap)
  }

  /**
   * Returns `true` if any of the inputs are `true`, `false` otherwise. The inputs are evaluated in order, and evaluation is short-circuiting: once an input expression evaluates to `true`, the result is `true` and no further input expressions are evaluated.
   */
  @Test
  @UiThreadTest
  fun anyTest() {
    val expression = any {
      literal(true)
      literal(true)
      literal(false)
    }
    val layer = symbolLayer("id", "source") {
      iconAllowOverlap(expression)
    }
    setupLayer(layer)
    assertEquals(true, layer.iconAllowOverlap)
  }

  /**
   * Asserts that the input is an array (optionally with a specific item type and length).  If, when the input expression is evaluated, it is not of the asserted type, then this assertion will cause the whole expression to be aborted.
   */
  @Test
  @UiThreadTest
  fun arrayTest() {
    val expression = array {
      literal("number")
      literal(2)
      get {
        literal("array_property")
      }
    }
    val layer = symbolLayer("id", "source") {
      iconOffset(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.iconOffsetAsExpression.toString())
  }

  /**
   * Returns the arcsine of the input.
   */
  @Test
  @UiThreadTest
  fun asinTest() {
    val expression = asin {
      literal(0.0)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(0.0, layer.textSize)
  }

  /**
   * Retrieves an item from an array.
   */
  @Test
  @UiThreadTest
  fun atTest() {
    val expression = at {
      literal(2)
      literal(listOf(1.0, 2.0, 3.0, 4.0))
    }
    val layer = symbolLayer("id", "source") {
      symbolSortKey(expression)
    }
    setupLayer(layer)
    assertEquals(3.0, layer.symbolSortKey)
  }

  /**
   * Returns the arctangent of the input.
   */
  @Test
  @UiThreadTest
  fun atanTest() {
    val expression = atan {
      literal(0)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(0.0, layer.textSize)
  }

  /**
   * Asserts that the input value is a boolean. If multiple values are provided, each one is evaluated in order until a boolean is obtained. If none of the inputs are booleans, the expression is an error.
   */
  @Test
  @UiThreadTest
  fun booleanTest() {
    val expression = boolean {
      literal(true)
    }
    val layer = symbolLayer("id", "source") {
      iconOptional(expression)
    }
    setupLayer(layer)
    assertEquals(true, layer.iconOptional)
  }

  /**
   * Selects the first output whose corresponding test condition evaluates to true, or the fallback value otherwise.
   */
  @Test
  @UiThreadTest
  fun switchCaseTest() {
    val expression = switchCase {
      eq {
        get {
          literal("count")
        }
        literal(1.0)
      }
      literal(1.0)
      eq {
        get {
          literal("count")
        }
        literal(2.0)
      }
      literal(2.0)
      literal(0.0)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.textSizeAsExpression.toString())
  }

  /**
   * Returns the smallest integer that is greater than or equal to the input.
   */
  @Test
  @UiThreadTest
  fun ceilTest() {
    val expression = ceil {
      literal(0.5)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(1.0, layer.textSize)
  }

  /**
   * Evaluates each expression in turn until the first non-null value is obtained, and returns that value.
   */
  @Test
  @UiThreadTest
  fun coalesceTest() {
    val expression = toColor {
      coalesce {
        get {
          literal("keyToNullValue")
        }
        get {
          literal("keyToNonNullValue")
        }
      }
    }
    val layer = symbolLayer("id", "source") {
      textColor(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.textColorAsExpression.toString())
  }

  /**
   * Returns a `collator` for use in locale-dependent comparison operations. The `case-sensitive` and `diacritic-sensitive` options default to `false`. The `locale` argument specifies the IETF language tag of the locale to use. If none is provided, the default locale is used. If the requested locale is not available, the `collator` will use a system-defined fallback locale. Use `resolved-locale` to test the results of locale fallback behavior.
   */
  @Test
  @UiThreadTest
  fun collatorTest() {
    val expression = switchCase {
      eq {
        literal("Abc")
        literal("abc")
        collator {
          caseSensitive(false)
          locale(Locale("en_US"))
        }
      }
      literal(1.0)
      literal(0.0)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(
      "[case, [==, Abc, abc, [collator, {diacritic-sensitive=false, locale=en_us, case-sensitive=false}]], 1.0, 0.0]",
      layer.textSizeAsExpression.toString()
    )
  }

  /**
   * Returns a `string` consisting of the concatenation of the inputs. Each input is converted to a string as if by `to-string`.
   */
  @Test
  @UiThreadTest
  fun concatTest() {
    val expression = concat {
      literal("this")
      literal("is")
      literal("test")
    }
    val layer = symbolLayer("id", "source") {
      textField(expression)
    }
    setupLayer(layer)
    assertEquals("thisistest", layer.textFieldAsString)
  }

  /**
   * Returns the cosine of the input.
   */
  @Test
  @UiThreadTest
  fun cosTest() {
    val expression = cos {
      pi()
    }
    val layer = symbolLayer("id", "source") {
      symbolSortKey(expression)
    }
    setupLayer(layer)
    assertEquals(-1.0, layer.symbolSortKey)
  }

  /**
   * Returns the input string converted to lowercase. Follows the Unicode Default Case Conversion algorithm and the locale-insensitive case mappings in the Unicode Character Database.
   */
  @Test
  @UiThreadTest
  fun downcaseTest() {
    val expression = downcase {
      literal("TeSt")
    }
    val layer = symbolLayer("id", "source") {
      textField(expression)
    }
    setupLayer(layer)
    assertEquals("test", layer.textFieldAsString)
  }

  /**
   * Returns the mathematical constant e.
   */
  @Test
  @UiThreadTest
  fun eTest() {
    val expression = e()
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(2.7182817459106445, layer.textSize)
  }

  /**
   * Retrieves a property value from the current feature's state. Returns null if the requested property is not present on the feature's state. A feature's state is not part of the GeoJSON or vector tile data, and must be set programmatically on each feature. Features are identified by their `id` attribute, which must be an integer or a string that can be cast to an integer. Note that ["feature-state"] can only be used with paint properties that support data-driven styling.
   */
  @Test
  @UiThreadTest
  fun featureStateTest() {
    val expression = switchCase {
      boolean {
        featureState {
          literal("hover")
        }
        literal(false)
      }
      literal(1.0)
      literal(0.5)
    }
    val layer = fillLayer("id", "source") {
      fillOpacity(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.fillOpacityAsExpression.toString())
  }

  /**
   * Returns the largest integer that is less than or equal to the input.
   */
  @Test
  @UiThreadTest
  fun floorTest() {
    val expression = floor {
      literal(1.6)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(1.0, layer.textSize)
  }

  /**
   * Returns `formatted` text containing annotations for use in mixed-format `text-field` entries. If set, the `text-font` argument overrides the font specified by the root layout properties. If set, the `font-scale` argument specifies a scaling factor relative to the `text-size` specified in the root layout properties. If set, the `text-color` argument overrides the color specified by the paint properties for this layer.
   */
  @Test
  @UiThreadTest
  fun formatTest() {
    val expression = format {
      formatSection(literal("text")) {
        fontScale(1.0)
        textFont(listOf("font1", "font2"))
        textColor(Color.BLUE)
      }
      formatSection(literal("text2")) {
        fontScale(2.0)
        textFont(listOf("font1", "font2"))
        textColor(Color.RED)
      }
    }
    val layer = symbolLayer("id", "source") {
      textField(expression)
    }
    setupLayer(layer)
    assertEquals(
      "[format, text, {font-scale=1.0, text-font=[literal, [font1, font2]], text-color=[rgba, 0.0, 0.0, 255.0, 1.0]}, text2, {font-scale=2.0, text-font=[literal, [font1, font2]], text-color=[rgba, 255.0, 0.0, 0.0, 1.0]}]",
      layer.textFieldAsExpression.toString()
    )
  }

  /**
   * Gets the feature's geometry type: Point, MultiPoint, LineString, MultiLineString, Polygon, MultiPolygon.
   */
  @Test
  @UiThreadTest
  fun geometryTypeTest() {
    val expression = format {
      formatSection(
        content = concat {
          get {
            literal("key-to-value")
          }
          literal(" ")
          geometryType()
        }
      )
    }
    val layer = symbolLayer("id", "source") {
      textField(expression)
    }
    setupLayer(layer)
    assertEquals(
      "[format, [concat, [get, key-to-value],  , [geometry-type]], {}]",
      layer.textFieldAsExpression.toString()
    )
  }

  /**
   * Retrieves a property value from the current feature's properties, or from another object if a second argument is provided. Returns null if the requested property is missing.
   */
  @Test
  @UiThreadTest
  fun getTest() {
    val expression = image {
      string {
        get {
          literal("key-to-value")
        }
      }
    }
    val layer = symbolLayer("id", "source") {
      iconImage(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.iconImageAsExpression.toString())
  }

  /**
   * Tests for the presence of an property value in the current feature's properties, or from another object if a second argument is provided.
   */
  @Test
  @UiThreadTest
  fun hasTest() {
    val expression = has {
      literal("key")
    }
    val layer = fillLayer("id", "source") {
      filter(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.filter.toString())
  }

  /**
   * Gets the kernel density estimation of a pixel in a heatmap layer, which is a relative measure of how many data points are crowded around a particular pixel. Can only be used in the `heatmap-color` property.
   */
  @Test
  @UiThreadTest
  fun heatmapDensityTest() {
    val expression = interpolate {
      linear()
      heatmapDensity()
      literal(1.0)
      rgba {
        literal(178.0)
        literal(24.0)
        literal(12.0)
        literal(1.0)
      }
    }
    val layer = heatmapLayer("id", "source") {
      heatmapColor(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.heatmapColor.toString())
  }

  /**
   * Gets the feature's id, if it has one.
   */
  @Test
  @UiThreadTest
  fun idTest() {
    val expression = format {
      formatSection(
        id()
      )
    }
    val layer = symbolLayer("id", "source") {
      textField(expression)
    }
    setupLayer(layer)
    assertEquals("[format, [id], {}]", layer.textFieldAsExpression.toString())
  }

  /**
   * Returns an `image` type for use in `icon-image` and `*-pattern` entries. If set, the `image` argument will check that the requested image exists in the style and will return either the resolved image name or `null`, depending on whether or not the image is currently in the style. This validation process is synchronous and requires the image to have been added to the style before requesting it in the `image` argument.
   */
  @Test
  @UiThreadTest
  fun imageTest() {
    val expression = image {
      string {
        get {
          literal("key")
        }
      }
    }
    val layer = symbolLayer("id", "source") {
      iconImage(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.iconImageAsExpression.toString())
  }

  /**
   * Determines whether an item exists in an array or a substring exists in a string.
   */
  @Test
  @UiThreadTest
  fun inExpressionTest() {
    val expression = inExpression {
      get {
        literal("key")
      }
      literal(listOf("abc", "def"))
    }
    val layer = symbolLayer("id", "source") {
      filter(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.filter.toString())
  }

  /**
   * Produces continuous, smooth results by interpolating between pairs of input and output values ("stops"). The `input` may be any numeric expression (e.g., `["get", "population"]`). Stop inputs must be numeric literals in strictly ascending order. The output type must be `number`, `array<number>`, or `color`.

   Interpolation types:
   - `["linear"]`: interpolates linearly between the pair of stops just less than and just greater than the input.
   - `["exponential", base]`: interpolates exponentially between the stops just less than and just greater than the input. `base` controls the rate at which the output increases: higher values make the output increase more towards the high end of the range. With values close to 1 the output increases linearly.
   - `["cubic-bezier", x1, y1, x2, y2]`: interpolates using the cubic bezier curve defined by the given control points.
   */
  @Test
  @UiThreadTest
  fun interpolateTest() {
    val expression = interpolate {
      exponential {
        literal(0.5)
      }
      zoom()
      stop {
        literal(1.0)
        rgba {
          literal(0.0)
          literal(0.0)
          literal(0.0)
          literal(0.0)
        }
      }
      stop {
        literal(10.0)
        rgba {
          literal(255.0)
          literal(255.0)
          literal(255.0)
          literal(1.0)
        }
      }
    }
    val layer = fillLayer("id", "source") {
      fillColor(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.fillColorAsExpression.toString())
  }

  /**
   * Returns `true` if the input string is expected to render legibly. Returns `false` if the input string contains sections that cannot be rendered without potential loss of meaning (e.g. Indic scripts that require complex text shaping, or right-to-left scripts if the the `mapbox-gl-rtl-text` plugin is not in use in Mapbox GL JS).
   */
  @Test
  @UiThreadTest
  fun isSupportedScriptTest() {
    val expression = isSupportedScript {
      string {
        get {
          literal("script")
        }
      }
    }
    val layer = symbolLayer("id", "source") {
      filter(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.filter.toString())
  }

  /**
   * Gets the length of an array or string.
   */
  @Test
  @UiThreadTest
  fun lengthTest() {
    val expression = length {
      literal(listOf("one", "two", "three"))
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(3.0, layer.textSize)
  }

  /**
   * Binds expressions to named variables, which can then be referenced in the result expression using ["var", "variable_name"].
   */
  @Test
  @UiThreadTest
  fun letExpressionTest() {
    val expression = letExpression {
      literal("two")
      literal(2.0)
      sum {
        literal(1.0)
        varExpression {
          literal("two")
        }
      }
    }

    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(3.0, layer.textSize)
  }

  /**
   * Gets the progress along a gradient line. Can only be used in the `line-gradient` property.
   */
  @Test
  @UiThreadTest
  fun lineProgressTest() {
    val expression = interpolate {
      linear()
      lineProgress()
      stop {
        literal(0.0)
        rgba {
          literal(0.0)
          literal(0.0)
          literal(255.0)
          literal(1.0)
        }
      }
      stop {
        literal(1.0)
        rgba {
          literal(255.0)
          literal(0.0)
          literal(0.0)
          literal(1.0)
        }
      }
    }

    val layer = lineLayer("id", "source") {
      lineGradient(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.lineGradient.toString())
  }

  /**
   * Provides a literal array or object value.
   */
  @Test
  @UiThreadTest
  fun literalDoubleTest() {
    val expression = literal(0.1)

    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(0.1, layer.textSize!!, 1E-5)
  }

  /**
   * Provides a literal array or object value.
   */
  @Test
  @UiThreadTest
  fun literalLongTest() {
    val expression = literal(12L)

    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(12.0, layer.textSize)
  }

  /**
   * Provides a literal array or object value.
   */
  @Test
  @UiThreadTest
  fun literalBooleanTest() {
    val expression = literal(true)

    val layer = symbolLayer("id", "source") {
      iconAllowOverlap(expression)
    }
    setupLayer(layer)
    assertEquals(true, layer.iconAllowOverlap)
  }

  /**
   * Provides a literal array or object value.
   */
  @Test
  @UiThreadTest
  fun literalStringTest() {
    val expression = literal("name")

    val layer = symbolLayer("id", "source") {
      textField(expression)
    }
    setupLayer(layer)
    assertEquals("name", layer.textFieldAsString)
  }

  /**
   * Provides a literal array or object value.
   */
  @Test
  @UiThreadTest
  fun literalDoubleArrayTest() {
    val expression = literal(listOf(1.0, 2.0))

    val layer = symbolLayer("id", "source") {
      iconOffset(expression)
    }
    setupLayer(layer)
    assertEquals(listOf(1.0, 2.0), layer.iconOffset!!)
  }

  /**
   * Provides a literal array or object value.
   */
  @Test
  @UiThreadTest
  fun literalLongArrayTest() {
    val expression = literal(listOf(11L, 22L))

    val layer = symbolLayer("id", "source") {
      iconOffset(expression)
    }
    setupLayer(layer)
    assertEquals(listOf(11.0, 22.0), layer.iconOffset!!)
  }

  /**
   * Provides a literal array or object value.
   */
  @Test
  @UiThreadTest
  fun literalBooleanArrayTest() {
    val expression = at {
      literal(2)
      literal(listOf(true, true, false, true))
    }
    val layer = symbolLayer("id", "source") {
      iconAllowOverlap(expression)
    }
    setupLayer(layer)
    assertEquals(false, layer.iconAllowOverlap)
  }

  /**
   * Provides a literal array or object value.
   */
  @Test
  @UiThreadTest
  fun literalStringArrayTest() {
    val expression = literal(listOf("text font a", "text font b"))
    val layer = symbolLayer("id", "source") {
      textFont(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.textFontAsExpression.toString())
  }

  /**
   * Returns the natural logarithm of the input.
   */
  @Test
  @UiThreadTest
  fun lnTest() {
    val expression = ln {
      e()
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(1.0, layer.textSize)
  }

  /**
   * Returns mathematical constant ln(2).
   */
  @Test
  @UiThreadTest
  fun ln2Test() {
    val expression = ln2()
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(0.6931471824645996, layer.textSize)
  }

  /**
   * Returns the base-ten logarithm of the input.
   */
  @Test
  @UiThreadTest
  fun log10Test() {
    val expression = log10 {
      literal(100)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(2.0, layer.textSize)
  }

  /**
   * Returns the base-two logarithm of the input.
   */
  @Test
  @UiThreadTest
  fun log2Test() {
    val expression = log2 {
      literal(8)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(3.0, layer.textSize)
  }

  /**
   * Selects the output whose label value matches the input value, or the fallback value if no match is found. The input can be any expression (e.g. `["get", "building_type"]`). Each label must be either:
   * a single literal value; or
   * an array of literal values, whose values must be all strings or all numbers (e.g. `[100, 101]` or `["c", "b"]`). The input matches if any of the values in the array matches, similar to the deprecated `"in"` operator.
   * Each label must be unique. If the input type does not match the type of the labels, the result will be the fallback value.
   */
  @Test
  @UiThreadTest
  fun matchTest() {
    val expression = match {
      get {
        literal("key")
      }
      literal(1)
      literal(1.0)
      literal(2)
      literal(2.0)
      literal(3.0)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.textSizeAsExpression.toString())
  }

  /**
   * Selects the output whose label value matches the input value, or the fallback value if no match is found. The input can be any expression (e.g. `["get", "building_type"]`). Each label must be either:
   * a single literal value; or
   * an array of literal values, whose values must be all strings or all numbers (e.g. `[100, 101]` or `["c", "b"]`). The input matches if any of the values in the array matches, similar to the deprecated `"in"` operator.
   * Each label must be unique. If the input type does not match the type of the labels, the result will be the fallback value.
   */
  @Test
  @UiThreadTest
  fun matchWithLableListTest() {
    val expression = match {
      get {
        literal("ethnicity")
      }
      literal(listOf("Asian", "Black", "Hispanic", "white"))
      rgba(251.0, 176.0, 59.0, 1.0)
      literal("Other")
      rgba(204.0, 204.0, 204.0, 1.0)
      rgba(0.0, 0.0, 0.0, 1.0)
    }
    val layer = symbolLayer("id", "source") {
      textColor(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toJson(), layer.textColorAsExpression?.toJson())
  }

  /**
   * Selects the output whose label value matches the input value, or the fallback value if no match is found. The input can be any expression (e.g. `["get", "building_type"]`). Each label must be either:
   * a single literal value; or
   * an array of literal values, whose values must be all strings or all numbers (e.g. `[100, 101]` or `["c", "b"]`). The input matches if any of the values in the array matches, similar to the deprecated `"in"` operator.
   * Each label must be unique. If the input type does not match the type of the labels, the result will be the fallback value.
   */
  @Test
  @UiThreadTest
  fun matchWithOutputListTest() {
    val expression = match {
      mod {
        number {
          id()
        }
        literal(4.0)
      }
      literal(0)
      literal(listOf(0.0, 0.0))
      literal(1)
      literal(listOf(0.0, 20.0))
      literal(2)
      literal(listOf(0.0, 40.0))
      literal(listOf(0.0, 60.0))
    }
    val layer = symbolLayer("id", "source") {
      this.iconOffset(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toJson(), layer.iconOffsetAsExpression?.toJson())
  }

  /**
   * Returns the maximum value of the inputs.
   */
  @Test
  @UiThreadTest
  fun maxTest() {
    val expression = max {
      literal(8)
      literal(10)
      literal(7)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.textSize)
  }

  /**
   * Returns the minimum value of the inputs.
   */
  @Test
  @UiThreadTest
  fun minTest() {
    val expression = min {
      literal(8)
      literal(10)
      literal(7)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(7.0, layer.textSize)
  }

  /**
   * Asserts that the input value is a number. If multiple values are provided, each one is evaluated in order until a number is obtained. If none of the inputs are numbers, the expression is an error.
   */
  @Test
  @UiThreadTest
  fun numberTest() {
    val expression = number {
      literal("string")
      literal(true)
      literal(1)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(1.0, layer.textSize)
  }

  /**
   * Converts the input number into a string representation using the providing formatting rules. If set, the `locale` argument specifies the locale to use, as a BCP 47 language tag. If set, the `currency` argument specifies an ISO 4217 code to use for currency-style formatting. If set, the `min-fraction-digits` and `max-fraction-digits` arguments specify the minimum and maximum number of fractional digits to include.
   */
  @Test
  @UiThreadTest
  fun numberFormatTest() {
    val expression = numberFormat(literal(123.456789)) {
      locale {
        literal("en-US")
      }
      minFractionDigits {
        literal(1)
      }
      maxFractionDigits {
        literal(4)
      }
    }
    val layer = symbolLayer("id", "source") {
      textField(expression)
    }
    setupLayer(layer)
    assertEquals("123.4568", layer.textFieldAsString)
    layer.textField(
      numberFormat(literal(1234567.1234567)) {
        locale {
          literal("en-US")
        }
        currency {
          literal("USD")
        }
      }
    )
    assertEquals("$1,234,567.12", layer.textFieldAsString)
  }

  /**
   * Asserts that the input value is an object. If multiple values are provided, each one is evaluated in order until an object is obtained. If none of the inputs are objects, the expression is an error.
   */
  @Test
  @UiThreadTest
  fun objectExpressionTest() {
    val map = HashMap<String, Value>()
    map["key1"] = literal(1)
    map["key2"] = literal(2)
    val expression = toBoolean {
      objectExpression {
        literal(map as HashMap<String, Any>)
      }
    }
    val layer = symbolLayer("id", "source") {
      iconAllowOverlap(expression)
    }
    setupLayer(layer)
    assertEquals(true, layer.iconAllowOverlap)
  }

  /**
   * Returns the mathematical constant pi.
   */
  @Test
  @UiThreadTest
  fun piTest() {
    val expression = pi()
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(3.1415927410125732, layer.textSize)
  }

  /**
   * Gets the feature properties object.  Note that in some cases, it may be more efficient to use ["get", "property_name"] directly.
   */
  @Test
  @UiThreadTest
  fun propertiesTest() {
    val expression = number {
      get {
        literal("key")
        properties()
      }
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.textSizeAsExpression.toString())
  }

  /**
   * Returns the IETF language tag of the locale being used by the provided `collator`. This can be used to determine the default system locale, or to determine if a requested locale was successfully loaded.
   */
  @Test
  @UiThreadTest
  fun resolvedLocaleTest() {
    val expression = number {
      switchCase {
        eq {
          literal("it")
          resolvedLocale {
            collator {
              locale(Locale("en-US"))
            }
          }
        }
        literal(1.0)
        literal(2.0)
      }
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(
      "[number, [case, [==, it, [resolved-locale, [collator, {diacritic-sensitive=false, locale=en-us, case-sensitive=false}]]], 1.0, 2.0]]",
      layer.textSizeAsExpression.toString()
    )
  }

  /**
   * Creates a color value from red, green, and blue components, which must range between 0 and 255, and an alpha component of 1. If any component is out of range, the expression is an error.
   */
  @Test
  @UiThreadTest
  fun rgbTest() {
    val expression = rgb {
      literal(0.0)
      literal(1.0)
      literal(2.0)
    }
    val layer = symbolLayer("id", "source") {
      textColor(expression)
    }
    setupLayer(layer)
    assertEquals(
      rgba {
        literal(0.0)
        literal(1.0)
        literal(2.0)
        literal(1.0)
      }.toString(),
      layer.textColorAsExpression.toString()
    )
  }

  /**
   * Creates a color value from red, green, blue components, which must range between 0 and 255, and an alpha component which must range between 0 and 1. If any component is out of range, the expression is an error.
   */
  @Test
  @UiThreadTest
  fun rgbaTest() {
    val expression = rgba {
      literal(0.0)
      literal(1.0)
      literal(2.0)
      literal(0.5)
    }
    val layer = symbolLayer("id", "source") {
      textColor(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.textColorAsExpression.toString())
  }

  /**
   * Rounds the input to the nearest integer. Halfway values are rounded away from zero. For example, `["round", -1.5]` evaluates to -2.
   */
  @Test
  @UiThreadTest
  fun roundTest() {
    val expression = round {
      literal(-1.5)
    }
    val layer = symbolLayer("id", "source") {
      symbolSortKey(expression)
    }
    setupLayer(layer)
    assertEquals(-2.0, layer.symbolSortKey)
  }

  /**
   * Returns the sine of the input.
   */
  @Test
  @UiThreadTest
  fun sinTest() {
    val expression = sin {
      division {
        pi()
        literal(2)
      }
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(1.0, layer.textSize)
  }

  /**
   * Returns the square root of the input.
   */
  @Test
  @UiThreadTest
  fun sqrtTest() {
    val expression = sqrt {
      literal(9)
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(3.0, layer.textSize)
  }

  /**
   * Produces discrete, stepped results by evaluating a piecewise-constant function defined by pairs of input and output values ("stops"). The `input` may be any numeric expression (e.g., `["get", "population"]`). Stop inputs must be numeric literals in strictly ascending order. Returns the output value of the stop just less than the input, or the first output if the input is less than the first stop.
   */
  @Test
  @UiThreadTest
  fun stepTest() {
    val expression = step {
      zoom()
      literal(0.0)
      stop(1.0) { literal(2.5) }
      stop(10.0) { literal(5.0) }
    }
    val layer = circleLayer("id", "source") {
      circleRadius(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.circleRadiusAsExpression.toString())
  }

  /**
   * Asserts that the input value is a string. If multiple values are provided, each one is evaluated in order until a string is obtained. If none of the inputs are strings, the expression is an error.
   */
  @Test
  @UiThreadTest
  fun stringTest() {
    val expression = string {
      literal(9)
      literal(true)
      literal("string")
    }
    val layer = symbolLayer("id", "source") {
      textField(expression)
    }
    setupLayer(layer)
    assertEquals("string", layer.textFieldAsString)
  }

  /**
   * Returns the tangent of the input.
   */
  @Test
  @UiThreadTest
  fun tanTest() {
    val expression = tan {
      division {
        pi()
        literal(4)
      }
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(1.0, layer.textSize)
  }

  /**
   * Converts the input value to a boolean. The result is `false` when then input is an empty string, 0, `false`, `null`, or `NaN`; otherwise it is `true`.
   */
  @Test
  @UiThreadTest
  fun toBooleanTest() {
    val expression = toBoolean {
      literal("abc")
    }
    val layer = symbolLayer("id", "source") {
      iconAllowOverlap(expression)
    }
    setupLayer(layer)
    assertEquals(true, layer.iconAllowOverlap)
    layer.iconAllowOverlap(
      toBoolean {
        literal("")
      }
    )
    assertEquals(false, layer.iconAllowOverlap)
  }

  /**
   * Converts the input value to a color. If multiple values are provided, each one is evaluated in order until the first successful conversion is obtained. If none of the inputs can be converted, the expression is an error.
   */
  @Test
  @UiThreadTest
  fun toColorTest() {
    val expression = toColor {
      literal("abc")
      literal("def")
      literal("blue")
    }
    val layer = symbolLayer("id", "source") {
      iconColor(expression)
    }
    setupLayer(layer)
    assertEquals(Color.BLUE, layer.iconColorAsColorInt)
  }

  /**
   * Converts the input value to a number, if possible. If the input is `null` or `false`, the result is 0. If the input is `true`, the result is 1. If the input is a string, it is converted to a number as specified by the ["ToNumber Applied to the String Type" algorithm](https://tc39.github.io/ecma262/#sec-tonumber-applied-to-the-string-type) of the ECMAScript Language Specification. If multiple values are provided, each one is evaluated in order until the first successful conversion is obtained. If none of the inputs can be converted, the expression is an error.
   */
  @Test
  @UiThreadTest
  fun toNumberTest() {
    val expression = toNumber {
      literal("2.0")
    }
    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(2.0, layer.textSize)
    // TODO this should work, created ticket to track it.
    //    layer.textSize(toNumber {
    //      literal(true)
    //    })
    //    assertEquals(1.0, layer.textSize)
  }

  /**
   * Returns a four-element array containing the input color's red, green, blue, and alpha components, in that order.
   */
  @Test
  @UiThreadTest
  fun toRgbaTest() {
    val expression = toRgba {
      literal("blue")
    }
    val layer = symbolLayer("id", "source") {
      iconTextFitPadding(expression)
    }
    setupLayer(layer)
    assertEquals(listOf(0.0, 0.0, 255.0, 1.0), layer.iconTextFitPadding!!)
  }

  /**
   * Converts the input value to a string. If the input is `null`, the result is `""`. If the input is a boolean, the result is `"true"` or `"false"`. If the input is a number, it is converted to a string as specified by the ["NumberToString" algorithm](https://tc39.github.io/ecma262/#sec-tostring-applied-to-the-number-type) of the ECMAScript Language Specification. If the input is a color, it is converted to a string of the form `"rgba(r,g,b,a)"`, where `r`, `g`, and `b` are numerals ranging from 0 to 255, and `a` ranges from 0 to 1. Otherwise, the input is converted to a string in the format specified by the [`JSON.stringify`](https://tc39.github.io/ecma262/#sec-json.stringify) function of the ECMAScript Language Specification.
   */
  @Test
  @UiThreadTest
  fun toStringTest() {
    val expression = toString {
      literal(true)
    }
    val layer = symbolLayer("id", "source") {
      textField(expression)
    }
    setupLayer(layer)
    assertEquals("true", layer.textFieldAsString)
    layer.textField(
      toString {
        literal(10)
      }
    )
    assertEquals("10", layer.textFieldAsString)
    layer.textField(
      toString {
        rgba {
          literal(0.0)
          literal(0.0)
          literal(0.0)
          literal(0.0)
        }
      }
    )
    assertEquals("rgba(0,0,0,0)", layer.textFieldAsString)
  }

  /**
   * Returns a string describing the type of the given value.
   */
  @Test
  @UiThreadTest
  fun typeofExpressionTest() {
    val expression = typeofExpression {
      literal(true)
    }
    val layer = symbolLayer("id", "source") {
      textField(expression)
    }
    setupLayer(layer)
    assertEquals("boolean", layer.textFieldAsString)
    layer.textField(
      typeofExpression {
        literal(10)
      }
    )
    assertEquals("number", layer.textFieldAsString)
    layer.textField(
      typeofExpression {
        rgba {
          literal(0.0)
          literal(0.0)
          literal(0.0)
          literal(0.0)
        }
      }
    )
    assertEquals("color", layer.textFieldAsString)
    layer.textField(
      typeofExpression {
        literal(listOf("abc", "def"))
      }
    )
    assertEquals("array<string, 2>", layer.textFieldAsString)
  }

  /**
   * Returns the input string converted to uppercase. Follows the Unicode Default Case Conversion algorithm and the locale-insensitive case mappings in the Unicode Character Database.
   */
  @Test
  @UiThreadTest
  fun upcaseTest() {
    val expression = upcase {
      literal("small")
    }
    val layer = symbolLayer("id", "source") {
      textField(expression)
    }
    setupLayer(layer)
    assertEquals("SMALL", layer.textFieldAsString)
  }

  /**
   * References variable bound using "let".
   */
  @Test
  @UiThreadTest
  fun varExpressionTest() {
    val expression = number {
      letExpression {
        literal("two")
        literal(2.0)
        sum {
          literal(1.0)
          varExpression {
            literal("two")
          }
        }
      }
    }

    val layer = symbolLayer("id", "source") {
      textSize(expression)
    }
    setupLayer(layer)
    assertEquals(3.0, layer.textSize)
  }

  /**
   * Returns `true` if the feature being evaluated is inside the pre-defined geometry boundary, `false` otherwise. The expression has one argument which must be a valid GeoJSON Polygon/Multi-Polygon object. The expression only evaluates on `Point` or `LineString` feature. For `Point` feature, The expression will return false if any point of the feature is on the boundary or outside the boundary. For `LineString` feature, the expression will return false if the line is fully outside the boundary, or the line is partially intersecting the boundary, which means either part of the line is outside of the boundary, or end point of the line lies on the boundary.
   */
  @Test
  @UiThreadTest
  fun withinTest() {
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
    val expression = within(polygon)
    val layer = symbolLayer("id", "source") {
      filter(expression)
    }
    setupLayer(layer)
    assertEquals(
      "[within, {coordinates=[[[30.0, 10.0], [40.0, 40.0], [20.0, 40.0], [10.0, 20.0], [30.0, 10.0]]], type=Polygon}]",
      layer.filter.toString()
    )
  }

  /**
   * Gets the current zoom level.  Note that in style layout and paint properties, ["zoom"] may only appear as the input to a top-level "step" or "interpolate" expression.
   */
  @Test
  @UiThreadTest
  fun zoomTest() {
    val expression = interpolate {
      exponential {
        literal(0.5)
      }
      zoom()
      stop {
        literal(1.0)
        rgba {
          literal(0.0)
          literal(0.0)
          literal(0.0)
          literal(0.0)
        }
      }
      stop {
        literal(10.0)
        rgba {
          literal(255.0)
          literal(255.0)
          literal(255.0)
          literal(1.0)
        }
      }
    }
    val layer = fillLayer("id", "source") {
      fillColor(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.fillColorAsExpression.toString())
  }

  /**
   * Convert a color int to the rgba expression.
   */
  @Test
  @UiThreadTest
  fun colorTest() {
    val expression = color(Color.RED)
    val layer = symbolLayer("id", "source") {
      textColor(expression)
    }
    setupLayer(layer)
    assertEquals(
      rgba {
        literal(255.0)
        literal(0.0)
        literal(0.0)
        literal(1.0)
      }.toString(),
      layer.textColorAsExpression.toString()
    )
  }

  /**
   * Test constructing an Expression from raw string.
   */
  @Test
  @UiThreadTest
  fun rawExpressionTest() {
    val expression = color(Color.RED)
    val layer = symbolLayer("id", "source") {
      textColor(Expression.fromRaw("[\"rgba\", 255.0, 0.0, 0.0, 1.0]"))
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.textColorAsExpression.toString())
  }

  /**
   * Returns the shortest distance in meters between the evaluated feature and the input geometry. The input value can be a valid GeoJSON of type Point, MultiPoint, LineString, MultiLineString, Polygon, MultiPolygon, Feature, or FeatureCollection. Distance values returned may vary in precision due to loss in precision from encoding geometries, particularly below zoom level 13.
   */
  @Test
  @UiThreadTest
  fun distanceTest() {
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
    val expression = lt {
      distance(polygon)
      literal(150)
    }
    val layer = symbolLayer("id", "source") {
      filter(expression)
    }
    setupLayer(layer)
    assertEquals(
      "[<, [distance, {coordinates=[[[30.0, 10.0], [40.0, 40.0], [20.0, 40.0], [10.0, 20.0], [30.0, 10.0]]], type=Polygon}], 150.0]",
      layer.filter.toString()
    )
  }
}