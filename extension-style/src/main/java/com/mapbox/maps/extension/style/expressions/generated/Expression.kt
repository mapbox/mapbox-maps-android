// This file is generated.

package com.mapbox.maps.extension.style.expressions.generated

import androidx.annotation.ColorInt
import com.mapbox.bindgen.Value
import com.mapbox.geojson.GeoJson
import com.mapbox.geojson.Geometry
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleException
import com.mapbox.maps.extension.style.expressions.types.FormatSection
import com.mapbox.maps.extension.style.types.ExpressionDsl
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.extension.style.utils.take
import com.mapbox.maps.extension.style.utils.unwrapFromLiteralArray
import com.mapbox.maps.extension.style.utils.unwrapToExpression
import java.util.Locale
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * An expression defines a formula for computing the value of any layout property, paint property,
 * or filter within a map style. Expressions allow you to style data with multiple feature
 * properties at once, apply conditional logic, and manipulate data with mathematical, logical, and
 * string operators. This allows for sophisticated runtime styling.
 */
class Expression : Value {
  private constructor(builder: Builder) : super(builder.contents()) {
    if ("literal" == builder.operator) {
      this.literalValue = builder.arguments.last().literalValue
    }
  }

  private constructor(value: Double) : super(value) {
    this.literalValue = value
  }

  private constructor(value: Long) : super(value) {
    this.literalValue = value
  }

  private constructor(value: Boolean) : super(value) {
    this.literalValue = value
  }

  private constructor(value: String) : super(value) {
    this.literalValue = value
  }

  private constructor(value: DoubleArray) : super(value.map(::Value)) {
    this.literalValue = value
  }

  private constructor(value: LongArray) : super(value.map(::Value)) {
    this.literalValue = value
  }

  private constructor(value: BooleanArray) : super(value.map(::Value)) {
    this.literalValue = value
  }

  private constructor(value: Array<String>) : super(value.map(::Value)) {
    this.literalValue = value
  }

  internal constructor(value: Array<DoubleArray>) : super(
    value.map { doubleArray ->
      Value(
        doubleArray.map(::Value)
      )
    }
  ) {
    this.literalValue = value
  }

  internal constructor(value: List<Any>) : super(value.map { TypeUtils.wrapToValue(it) }) {
    this.literalValue = value
  }

  internal constructor(value: HashMap<String, Value>) : super(value) {
    this.literalValue = value
  }

  /**
   * Return Expression as literal Value.
   *
   * Return null if the expression is not literal.
   */
  var literalValue: Any? = null
    private set

  /**
   * Check if the expression is literal.
   *
   * @return boolean
   */
  fun isLiteral(): Boolean {
    return null != literalValue
  }

  /**
   * Get the literal value of the expression as given type.
   *
   * Return null if expression is not literal or can not be casted to given type.
   */
  inline fun <reified T> getLiteral(): T? {
    if (literalValue is T) {
      return literalValue as T
    }
    return null
  }

  /**
   * Abstract base builder.
   *
   * @param operator compulsory operator field for the builder
   */
  abstract class Builder(internal val operator: String) {
    internal val arguments = ArrayList<Expression>()

    internal fun contents(): List<Value> {
      val valueList = ArrayList<Value>()
      valueList.add(Value(operator))
      valueList.addAll(arguments)
      return valueList
    }

    /**
     * Build the Expression with the settings.
     *
     * @return Expression
     */
    open fun build(): Expression {
      if (this.operator == "match") {
        val newBuilder = ExpressionBuilder("match")
        val lastIndex = this.arguments.size - 1
        this.arguments.forEachIndexed { index, argument ->
          newBuilder.addArgument(
            // https://github.com/mapbox/mapbox-maps-android/issues/965
            // the match expression is an exception and it takes raw list instead of a list wrapped into
            // literal expression when literal array is used as the label, the last member of the arguments
            // will be the default output should shouldn't be unwrapped.
            if (index % 2 == 1 && index != lastIndex) {
              argument.unwrapFromLiteralArray()
            } else {
              argument
            }
          )
        }
        return Expression(newBuilder)
      }
      return Expression(this)
    }
  }

  /**
   * Builder for Expression.
   *
   * @param operator compulsory operator field for the builder
   */
  @ExpressionDsl
  open class ExpressionBuilder(operator: String) : Builder(operator) {

    /**
     * Add an Expression to the builder.
     *
     * @param expression
     */
    fun addArgument(expression: Expression): ExpressionBuilder = apply { this.arguments.add(expression) }

    /**
     * For two inputs, returns the result of subtracting the second input from the first. For a
     * single input, returns the result of subtracting it from 0.
     */
    fun subtract(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.subtract(block))
    }

    /**
     * Logical negation. Returns `true` if the input is `false`, and `false` if the input is `true`.
     */
    fun not(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.not(block))
    }

    /**
     * Returns `true` if the input values are not equal, `false` otherwise. The comparison is strictly typed:
     * values of different runtime types are always considered unequal. Cases where the types are known to
     * be different at parse time are considered invalid and will produce a parse error. Accepts an
     * optional `collator` argument to control locale-dependent string comparisons.
     */
    fun neq(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.neq(block))
    }

    /**
     * Returns the product of the inputs.
     */
    fun product(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.product(block))
    }

    /**
     * Returns the result of floating point division of the first input by the second.
     */
    fun division(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.division(block))
    }

    /**
     * Returns the remainder after integer division of the first input by the second.
     */
    fun mod(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.mod(block))
    }

    /**
     * Returns the result of raising the first input to the power specified by the second.
     */
    fun pow(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.pow(block))
    }

    /**
     * Returns the sum of the inputs.
     */
    fun sum(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.sum(block))
    }

    /**
     * Returns `true` if the first input is strictly less than the second, `false` otherwise. The arguments
     * are required to be either both strings or both numbers; if during evaluation they are not,
     * expression evaluation produces an error. Cases where this constraint is known not to hold at parse
     * time are considered in valid and will produce a parse error. Accepts an optional `collator` argument
     * to control locale-dependent string comparisons.
     */
    fun lt(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.lt(block))
    }

    /**
     * Returns `true` if the first input is less than or equal to the second, `false` otherwise.
     * The arguments are required to be either both strings or both numbers; if during evaluation they
     * are not, expression evaluation produces an error. Cases where this constraint is known not to hold
     * at parse time are considered in valid and will produce a parse error. Accepts an optional
     * `collator` argument to control locale-dependent string comparisons.
     */
    fun lte(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.lte(block))
    }

    /**
     * Returns `true` if the input values are equal, `false` otherwise. The comparison is strictly typed: values
     * of different runtime types are always considered unequal. Cases where the types are known to be
     * different at parse time are considered invalid and will produce a parse error. Accepts an optional
     * `collator` argument to control locale-dependent string comparisons.
     */
    fun eq(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.eq(block))
    }

    /**
     * Returns `true` if the first input is strictly greater than the second, `false` otherwise. The arguments
     * are required to be either both strings or both numbers; if during evaluation they are not,
     * expression evaluation produces an error. Cases where this constraint is known not to hold at parse
     * time are considered in valid and will produce a parse error. Accepts an optional `collator` argument
     * to control locale-dependent string comparisons.
     */
    fun gt(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.gt(block))
    }

    /**
     * Returns `true` if the first input is greater than or equal to the second, `false` otherwise.
     * The arguments are required to be either both strings or both numbers; if during evaluation they
     * are not, expression evaluation produces an error. Cases where this constraint is known not to hold
     * at parse time are considered in valid and will produce a parse error. Accepts an optional
     * `collator` argument to control locale-dependent string comparisons.
     */
    fun gte(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.gte(block))
    }

    /**
     * Returns the absolute value of the input.
     */
    fun abs(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.abs(block))
    }

    /**
     * Returns the value of a cluster property accumulated so far. Can only be used in the
     * `clusterProperties` option of a clustered GeoJSON source.
     */
    fun accumulated(): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.accumulated())
    }

    /**
     * Returns the arccosine of the input, in radians between −π/2 and π/2.
     */
    fun acos(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.acos(block))
    }

    /**
     * Returns a string which matches one of the values specified in the text-anchor layout property, depending
     * on the best-fit anchor for the symbol during rendering. Using this expression the content of the
     * layer can be dynamically configured for the specific anchor type.
     */
    @MapboxExperimental
    fun activeAnchor(): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.activeAnchor())
    }

    /**
     * Returns `true` if all the inputs are `true`, `false` otherwise. The inputs are evaluated in order,
     * and evaluation is short-circuiting: once an input expression evaluates to `false`, the result is `false` and
     * no further input expressions are evaluated.
     */
    fun all(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.all(block))
    }

    /**
     * Returns `true` if any of the inputs are `true`, `false` otherwise. The inputs are evaluated in
     * order, and evaluation is short-circuiting: once an input expression evaluates to `true`, the result is `true`
     * and no further input expressions are evaluated.
     */
    fun any(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.any(block))
    }

    /**
     * Asserts that the input is an array (optionally with a specific item type and length). If,
     * when the input expression is evaluated, it is not of the asserted type, then this assertion
     * will cause the whole expression to be aborted.
     */
    fun array(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.array(block))
    }

    /**
     * Returns the arcsine of the input, in radians between −π/2 and π/2.
     */
    fun asin(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.asin(block))
    }

    /**
     * Retrieves an item from an array.
     */
    fun at(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.at(block))
    }

    /**
     * Retrieves an item from an array. If the array contains numeric values and the provided index
     * is non-integer, the expression returns an interpolated value between adjacent items.
     */
    fun atInterpolated(block: InterpolatorBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.atInterpolated(block))
    }

    /**
     * Returns the arctangent of the input, in radians between −π/2 and π/2.
     */
    fun atan(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.atan(block))
    }

    /**
     * Asserts that the input value is a boolean. If multiple values are provided, each one is
     * evaluated in order until a boolean is obtained. If none of the inputs are booleans, the
     * expression is an error.
     */
    fun boolean(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.boolean(block))
    }

    /**
     * Selects the first output whose corresponding test condition evaluates to true, or the fallback value otherwise.
     */
    fun switchCase(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.switchCase(block))
    }

    /**
     * Returns the smallest integer that is greater than or equal to the input.
     */
    fun ceil(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.ceil(block))
    }

    /**
     * Evaluates each expression in turn until the first valid value is obtained. Invalid values are `null`
     * and [`'image'`](#types-image) expressions that are unavailable in the style. If all values are invalid, `coalesce` returns
     * the first value listed.
     */
    fun coalesce(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.coalesce(block))
    }

    /**
     * Returns a `collator` for use in locale-dependent comparison operations. The `case-sensitive` and `diacritic-sensitive` options default to
     * `false`. The `locale` argument specifies the IETF language tag of the locale to use. If none
     * is provided, the default locale is used. If the requested locale is not available, the `collator`
     * will use a system-defined fallback locale. Use `resolved-locale` to test the results of locale fallback behavior.
     */
    fun collator(block: CollatorBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(
        CollatorBuilder().apply(block).build()
      )
    }

    /**
     * Returns a `string` consisting of the concatenation of the inputs. Each input is converted to a
     * string as if by `to-string`.
     */
    fun concat(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.concat(block))
    }

    /**
     * Retrieves the configuration value for the given option. Returns null if the requested option is missing.
     */
    fun config(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.config(block))
    }

    /**
     * Returns the cosine of the input, interpreted as radians.
     */
    fun cos(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.cos(block))
    }

    /**
     * Returns the shortest distance in meters between the evaluated feature and the input geometry. The input
     * value can be a valid GeoJSON of type `Point`, `MultiPoint`, `LineString`, `MultiLineString`, `Polygon`, `MultiPolygon`, `Feature`, or
     * `FeatureCollection`. Distance values returned may vary in precision due to loss in precision from encoding geometries,
     * particularly below zoom level 13.
     */
    fun distance(geojson: GeoJson): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.distance(geojson))
    }

    /**
     * Returns the distance of a `symbol` instance from the center of the map. The distance is
     * measured in pixels divided by the height of the map container. It measures 0 at the
     * center, decreases towards the camera and increase away from the camera. For example, if the height
     * of the map is 1000px, a value of -1 means 1000px away from the center towards
     * the camera, and a value of 1 means a distance of 1000px away from the camera
     * from the center. `["distance-from-center"]` may only be used in the `filter` expression for a `symbol` layer.
     */
    fun distanceFromCenter(): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.distanceFromCenter())
    }

    /**
     * Returns the input string converted to lowercase. Follows the Unicode Default Case Conversion algorithm and the
     * locale-insensitive case mappings in the Unicode Character Database.
     */
    fun downcase(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.downcase(block))
    }

    /**
     * Returns the mathematical constant e.
     */
    fun e(): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.e())
    }

    /**
     * Retrieves a property value from the current feature's state. Returns `null` if the requested property is
     * not present on the feature's state. A feature's state is not part of the GeoJSON or
     * vector tile data, and must be set programmatically on each feature. Features are identified by their
     * `id` attribute, which must be an integer or a string that can be cast to an
     * integer. Note that ["feature-state"] can only be used with paint properties that support data-driven styling.
     */
    fun featureState(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.featureState(block))
    }

    /**
     * Returns the largest integer that is less than or equal to the input.
     */
    fun floor(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.floor(block))
    }

    /**
     * Returns a `formatted` string for displaying mixed-format text in the `text-field` property. The input may contain
     * a string literal or expression, including an [`'image'`](#types-image) expression. Strings may be followed by a style
     * override object that supports the following properties:
     * - `"text-font"`: Overrides the font stack specified by the root layout property.
     * - `"text-color"`: Overrides the color specified by the root paint property.
     * - `"font-scale"`: Applies a scaling factor on `text-size` as specified by the root layout property.
     */
    fun format(block: FormatBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.format(block))
    }

    /**
     * Returns the feature's geometry type: `Point`, `LineString` or `Polygon`. `Multi-` feature types return the singular forms.
     */
    fun geometryType(): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.geometryType())
    }

    /**
     * Retrieves a property value from the current feature's properties, or from another object if a second
     * argument is provided. Returns `null` if the requested property is missing.
     */
    fun get(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.get(block))
    }

    /**
     * Tests for the presence of an property value in the current feature's properties, or from another
     * object if a second argument is provided.
     */
    fun has(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.has(block))
    }

    /**
     * Returns the kernel density estimation of a pixel in a heatmap layer, which is a relative
     * measure of how many data points are crowded around a particular pixel. Can only be used
     * in the `heatmap-color` property.
     */
    fun heatmapDensity(): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.heatmapDensity())
    }

    /**
     * Creates a color value from hue (range 0-360), saturation and lightness components (range 0-100), and an
     * alpha component of 1. If any component is out of range, the expression is an error.
     */
    fun hsl(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.hsl(block))
    }

    /**
     * Creates a color value from hue (range 0-360), saturation and lightness components (range 0-100), and an
     * alpha component (range 0-1). If any component is out of range, the expression is an error.
     */
    fun hsla(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.hsla(block))
    }

    /**
     * Returns the feature's id, if it has one.
     */
    fun id(): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.id())
    }

    /**
     * Returns a [`ResolvedImage`](/style-spec/reference/types/#resolvedimage) for use in [`icon-image`](/style-spec/reference/layers/#layout-symbol-icon-image), `--pattern` entries, and as a section in the [`'format'`](#types-format)
     * expression.
     *
     * A [`'coalesce'`](#coalesce) expression containing `image` expressions will evaluate to the first listed image that is currently
     * in the style. This validation process is synchronous and requires the image to have been added
     * to the style before requesting it in the `'image'` argument.
     *
     * Every image name can be followed by an optional [`ImageOptions`](/style-spec/reference/types/#imageoptions) object, which will be used for
     * vector images only.
     *
     * To implement crossfading between two images within a symbol layer using the [`icon-image-cross-fade`](/style-spec/reference/layers/#paint-symbol-icon-image-cross-fade) attribute, include a
     * second image as the second argument in the `'image'` expression.
     */
    fun image(block: ImageBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.image(block))
    }

    /**
     * Determines whether an item exists in an array or a substring exists in a string. In
     * the specific case when the second and third arguments are string literals, you must wrap at
     * least one of them in a [`literal`](#types-literal) expression to hint correct interpretation to the [type system](#type-system).
     */
    fun inExpression(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.inExpression(block))
    }

    /**
     * Returns the first position at which an item can be found in an array or a
     * substring can be found in a string, or `-1` if the input cannot be found. Accepts
     * an optional index from where to begin the search.
     */
    fun indexOf(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.indexOf(block))
    }

    /**
     * Produces continuous, smooth results by interpolating between pairs of input and output values ("stops"). The `input`
     * may be any numeric expression (e.g., `["get", "population"]`). Stop inputs must be numeric literals in strictly
     * ascending order. The output type must be `number`, `array<number>`, or `color`.
     *
     * Interpolation types:
     * - `["linear"]`: Interpolates linearly between the pair of stops just less than and just greater than
     * the input.
     * - `["exponential", base]`: Interpolates exponentially between the stops just less than and just greater than the
     * input. `base` controls the rate at which the output increases: higher values make the output increase
     * more towards the high end of the range. With values close to 1 the output increases
     * linearly.
     * - `["cubic-bezier", x1, y1, x2, y2]`: Interpolates using the cubic bezier curve defined by the given
     * control points.
     */
    fun interpolate(block: InterpolatorBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.interpolate(block))
    }

    /**
     * Returns `true` if the input string is expected to render legibly. Returns `false` if the input
     * string contains sections that cannot be rendered without potential loss of meaning (e.g. Indic scripts that
     * require complex text shaping, or right-to-left scripts if the the `mapbox-gl-rtl-text` plugin is not in use
     * in Mapbox GL JS).
     */
    fun isSupportedScript(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.isSupportedScript(block))
    }

    /**
     * Returns the length of an array or string.
     */
    fun length(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.length(block))
    }

    /**
     * Binds expressions to named variables, which can then be referenced in the result expression using ["var",
     * "variable_name"].
     */
    fun letExpression(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.letExpression(block))
    }

    /**
     * Returns the progress along a gradient line. Can only be used in the `line-gradient` and `line-z-offset`
     * properties.
     */
    fun lineProgress(): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.lineProgress())
    }

    /**
     * Provides a literal array or object value.
     */
    fun literal(value: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression(value))
    }

    /**
     * Provides a literal array or object value.
     */
    fun literal(value: Long): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression(value))
    }

    /**
     * Provides a literal array or object value.
     */
    fun literal(value: Boolean): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression(value))
    }

    /**
     * Provides a literal array or object value.
     */
    fun literal(value: String): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression(value))
    }

    /**
     * Provides a literal array or object value.
     */
    fun literal(value: HashMap<String, Any>): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Companion.literal(value))
    }

    /**
     * Provides a literal array or object value.
     */
    fun literal(value: List<Any>): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Companion.literal(value))
    }

    /**
     * Returns the natural logarithm of the input.
     */
    fun ln(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.ln(block))
    }

    /**
     * Returns mathematical constant ln(2).
     */
    fun ln2(): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.ln2())
    }

    /**
     * Returns the base-ten logarithm of the input.
     */
    fun log10(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.log10(block))
    }

    /**
     * Returns the base-two logarithm of the input.
     */
    fun log2(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.log2(block))
    }

    /**
     * Selects the output for which the label value matches the input value, or the fallback value
     * if no match is found. The input can be any expression (for example, `["get", "building_type"]`). Each
     * label must be unique, and must be either:
     *  - a single literal value; or
     *  - an array of literal values, the values of which must be all strings or
     * all numbers (for example `[100, 101]` or `["c", "b"]`).
     *
     * The input matches if any of the values in the array matches using strict equality, similar
     * to the `"in"` operator.
     * If the input type does not match the type of the labels, the result will be
     * the fallback value.
     */
    fun match(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.match(block))
    }

    /**
     * Returns the maximum value of the inputs.
     */
    fun max(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.max(block))
    }

    /**
     * Returns a requested property of the light configuration based on the supplied options. Currently the only
     * supported option is `brightness` which returns the global brightness value of the lights on a scale
     * of 0 to 1, where 0 means total darkness and 1 means full brightness. This expression
     * works only with 3D light, i.e. when `lights` root property is defined.
     */
    fun measureLight(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.measureLight(block))
    }

    /**
     * Returns the minimum value of the inputs.
     */
    fun min(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.min(block))
    }

    /**
     * Asserts that the input value is a number. If multiple values are provided, each one is
     * evaluated in order until a number is obtained. If none of the inputs are numbers, the
     * expression is an error.
     */
    fun number(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.number(block))
    }

    /**
     * Converts the input number into a string representation using the providing formatting rules. If set, the
     * `locale` argument specifies the locale to use, as a BCP 47 language tag. If set, the
     * `currency` argument specifies an ISO 4217 code to use for currency-style formatting. If set, the `unit`
     * argument specifies a [simple ECMAScript unit](https://tc39.es/proposal-unified-intl-numberformat/section6/locales-currencies-tz_proposed_out.html#sec-issanctionedsimpleunitidentifier) to use for unit-style formatting. If set, the `min-fraction-digits` and
     * `max-fraction-digits` arguments specify the minimum and maximum number of fractional digits to include.
     */
    fun numberFormat(input: Expression, block: NumberFormatBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.numberFormat(input, block))
    }

    /**
     * Asserts that the input value is an object. If multiple values are provided, each one is
     * evaluated in order until an object is obtained. If none of the inputs are objects, the
     * expression is an error.
     */
    fun objectExpression(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.objectExpression(block))
    }

    /**
     * Returns the mathematical constant pi.
     */
    fun pi(): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.pi())
    }

    /**
     * Returns the current pitch in degrees. `["pitch"]` may only be used in the `filter` expression for
     * a `symbol` layer.
     */
    fun pitch(): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.pitch())
    }

    /**
     * Returns the feature properties object. Note that in some cases, it may be more efficient to
     * use `["get", "property_name"]` directly.
     */
    fun properties(): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.properties())
    }

    /**
     * Returns a random value in the specified range (first two input numbers) based on a supplied
     * seed (third input). The seed can be an expression or a constant number or string value.
     */
    fun random(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.random(block))
    }

    /**
     * Returns the length of the particle velocity vector. Can only be used in the `raster-particle-color` property.
     */
    fun rasterParticleSpeed(): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.rasterParticleSpeed())
    }

    /**
     * Returns the raster value of a pixel computed via `raster-color-mix`. Can only be used in the
     * `raster-color` property.
     */
    fun rasterValue(): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.rasterValue())
    }

    /**
     * Returns the IETF language tag of the locale being used by the provided `collator`. This can
     * be used to determine the default system locale, or to determine if a requested locale was
     * successfully loaded.
     */
    fun resolvedLocale(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.resolvedLocale(block))
    }

    /**
     * Creates a color value from red, green, and blue components, which must range between 0 and
     * 255, and an alpha component of 1. If any component is out of range, the expression
     * is an error.
     */
    fun rgb(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.rgb(block))
    }

    /**
     * Creates a color value from red, green, blue components, which must range between 0 and 255,
     * and an alpha component which must range between 0 and 1. If any component is out
     * of range, the expression is an error.
     */
    fun rgba(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.rgba(block))
    }

    /**
     * Rounds the input to the nearest integer. Halfway values are rounded away from zero. For example,
     * `["round", -1.5]` evaluates to -2.
     */
    fun round(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.round(block))
    }

    /**
     * Returns the sine of the input, interpreted as radians.
     */
    fun sin(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.sin(block))
    }

    /**
     * Returns the distance of a point on the sky from the sun position. Returns 0 at
     * sun position and 1 when the distance reaches `sky-gradient-radius`. Can only be used in the `sky-gradient`
     * property.
     */
    fun skyRadialProgress(): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.skyRadialProgress())
    }

    /**
     * Returns an item from an array or a substring from a string from a specified start
     * index, or between a start index and an end index if set. The return value is
     * inclusive of the start index but not of the end index.
     */
    fun slice(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.slice(block))
    }

    /**
     * Returns the square root of the input.
     */
    fun sqrt(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.sqrt(block))
    }

    /**
     * Produces discrete, stepped results by evaluating a piecewise-constant function defined by pairs of input and output
     * values ("stops"). The `input` may be any numeric expression (e.g., `["get", "population"]`). Stop inputs must be
     * numeric literals in strictly ascending order. Returns the output value of the stop just less than
     * the input, or the first output if the input is less than the first stop.
     */
    fun step(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.step(block))
    }

    /**
     * Asserts that the input value is a string. If multiple values are provided, each one is
     * evaluated in order until a string is obtained. If none of the inputs are strings, the
     * expression is an error.
     */
    fun string(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.string(block))
    }

    /**
     * Returns the tangent of the input, interpreted as radians.
     */
    fun tan(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.tan(block))
    }

    /**
     * Converts the input value to a boolean. The result is `false` when then input is an
     * empty string, 0, `false`, `null`, or `NaN`; otherwise it is `true`.
     */
    fun toBoolean(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.toBoolean(block))
    }

    /**
     * Converts the input value to a color. If multiple values are provided, each one is evaluated
     * in order until the first successful conversion is obtained. If none of the inputs can be
     * converted, the expression is an error.
     */
    fun toColor(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.toColor(block))
    }

    /**
     * Returns a four-element array containing the input color's Hue, Saturation, Luminance and alpha components, in that
     * order.
     */
    fun toHsla(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.toHsla(block))
    }

    /**
     * Converts the input value to a number, if possible. If the input is `null` or `false`,
     * the result is 0. If the input is `true`, the result is 1. If the input
     * is a string, it is converted to a number as specified by the ["ToNumber Applied to
     * the String Type" algorithm](https://tc39.github.io/ecma262/#sec-tonumber-applied-to-the-string-type) of the ECMAScript Language Specification. If multiple values are provided, each one
     * is evaluated in order until the first successful conversion is obtained. If none of the inputs
     * can be converted, the expression is an error.
     */
    fun toNumber(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.toNumber(block))
    }

    /**
     * Returns a four-element array containing the input color's red, green, blue, and alpha components, in that
     * order.
     */
    fun toRgba(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.toRgba(block))
    }

    /**
     * Converts the input value to a string. If the input is `null`, the result is `""`.
     * If the input is a [`boolean`](#types-boolean), the result is `"true"` or `"false"`. If the input is
     * a number, it is converted to a string as specified by the ["NumberToString" algorithm](https://tc39.github.io/ecma262/#sec-tostring-applied-to-the-number-type) of the
     * ECMAScript Language Specification. If the input is a [`color`](#color), it is converted to a string of
     * the form `"rgba(r,g,b,a)"`, where `r`, `g`, and `b` are numerals ranging from 0 to 255, and
     * `a` ranges from 0 to 1. If the input is an [`'image'`](#types-image) expression, `'to-string'` returns the
     * image name. Otherwise, the input is converted to a string in the format specified by the
     * [`JSON.stringify`](https://tc39.github.io/ecma262/#sec-json.stringify) function of the ECMAScript Language Specification.
     */
    fun toString(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.toString(block))
    }

    /**
     * Returns a string describing the type of the given value.
     */
    fun typeofExpression(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.typeofExpression(block))
    }

    /**
     * Returns the input string converted to uppercase. Follows the Unicode Default Case Conversion algorithm and the
     * locale-insensitive case mappings in the Unicode Character Database.
     */
    fun upcase(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.upcase(block))
    }

    /**
     * References variable bound using "let".
     */
    fun varExpression(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.varExpression(block))
    }

    /**
     * Returns `true` if the evaluated feature is fully contained inside a boundary of the input geometry,
     * `false` otherwise. The input value can be a valid GeoJSON of type `Polygon`, `MultiPolygon`, `Feature`, or
     * `FeatureCollection`. Supported features for evaluation:
     * - `Point`: Returns `false` if a point is on the boundary or falls outside the boundary.
     * - `LineString`: Returns `false` if any part of a line falls outside the boundary, the line
     * intersects the boundary, or a line's endpoint is on the boundary.
     */

    fun within(geometry: Geometry): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.within(geometry))
    }

    /**
     * Returns the current worldview being used.
     */
    @MapboxExperimental
    fun worldview(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.worldview(block))
    }

    /**
     * Returns the current zoom level. Note that in style layout and paint properties, ["zoom"] may only
     * appear as the input to a top-level "step" or "interpolate" expression.
     */
    fun zoom(): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.zoom())
    }

    /**
     * Add a RGB color expression from numbers to the current expression builder.
     */
    fun rgb(red: Double, green: Double, blue: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.rgb(red, green, blue))
    }

    /**
     * Add a RGBA color expression from numbers to the current expression builder.
     */
    fun rgba(red: Double, green: Double, blue: Double, alpha: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.rgba(red, green, blue, alpha))
    }

    /**
     * Add a color expression to the current expression builder.
     */
    fun color(@ColorInt intColor: Int): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.color(intColor))
    }

    /**
     * Add a pair of input and output values.
     * The `input` may be any numeric expression (e.g., `["get", "population"]`).
     * Stop inputs must be numeric literals in strictly ascending order.
     * Returns the output value of the stop just less than the input,
     * or the first output if the input is less than the first stop.
     *
     * It is to be used as part of parameters in the step expression.
     */
    fun stop(block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.apply(block)
    }

    /**
     * Add a pair of input and output values.
     * The `input` may be any numeric expression (e.g., `["get", "population"]`).
     * Stop inputs must be numeric literals in strictly ascending order.
     * Returns the output value of the stop just less than the input,
     * or the first output if the input is less than the first stop.
     * It is to be used as part of parameters in the step expression.
     *
     * @param input as a double
     * @param output as Expression DSL block
     */
    fun stop(input: Double, block: ExpressionBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.addArgument(Companion.literal(input)).apply(block)
    }

    /**
     * Add a pair of input and output values.
     * The `input` may be any numeric expression (e.g., `["get", "population"]`).
     * Stop inputs must be numeric literals in strictly ascending order.
     * Returns the output value of the stop just less than the input,
     * or the first output if the input is less than the first stop.
     *
     * @param input as an Expression
     * @param output as an Expression
     */
    fun stop(input: Expression, output: Expression): ExpressionBuilder = apply {
      this@ExpressionBuilder.addArgument(input).addArgument(output)
    }

    /**
     * Add a pair of input and output values.
     * The `input` may be any numeric expression (e.g., `["get", "population"]`).
     * Stop inputs must be numeric literals in strictly ascending order.
     * Returns the output value of the stop just less than the input,
     * or the first output if the input is less than the first stop.
     *
     * @param input as a double
     * @param output as a double
     */
    fun stop(input: Double, output: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.addArgument(Companion.literal(input)).addArgument(Companion.literal(output))
    }

    /**
     * Add a pair of input and output values.
     * The `input` may be any numeric expression (e.g., `["get", "population"]`).
     * Stop inputs must be numeric literals in strictly ascending order.
     * Returns the output value of the stop just less than the input,
     * or the first output if the input is less than the first stop.
     *
     * @param pair an input and output argument pair
     */
    fun stop(pair: Pair<Expression, Expression>): ExpressionBuilder = apply {
      this@ExpressionBuilder.addArgument(pair.first).addArgument(pair.second)
    }

    /**
     * Retrieves a property value from the current feature's properties.
     * Expression evaluates to null if the requested property is missing.
     */
    fun get(key: String): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.get(key))
    }

    /**
     * Retrieves a property value from the current feature's properties, or from another object if a second
     * argument is provided. Expression evaluates to null if the requested property is missing.
     */
    fun get(key: String, expression: Expression): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.get(key, expression))
    }

    /**
     * Logical negation. Returns `true` if the input is `false`, and `false` if the input is `true`.
     */
    fun not(bool: Boolean): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.not(bool))
    }

    /**
     * Retrieves an item from an array.
     */
    fun at(index: Double, array: Expression): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.at(index, array))
    }

    /**
     * Determines whether an item exists in an array or a substring exists in a string.
     */
    fun inExpression(needle: String, haystack: Expression): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.inExpression(needle, haystack))
    }

    /**
     * Determines whether an item exists in an array or a substring exists in a string.
     */
    fun inExpression(needle: Double, haystack: Expression): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.inExpression(needle, haystack))
    }

    /**
     * Tests for the presence of an property value in the current feature's properties, or from another
     * object if a second argument is provided.
     */
    fun has(string: String, expression: Expression): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.has(string, expression))
    }

    /**
     * Tests for the presence of an property value in the current feature's properties
     */
    fun has(string: String): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.has(string))
    }

    /**
     * Gets the length of an string.
     */
    fun length(string: String): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.length(string))
    }

    /**
     * Returns the sum of the inputs.
     */
    fun sum(vararg double: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.sum(*double))
    }

    /**
     * Returns the product of the inputs.
     */
    fun product(vararg double: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.product(*double))
    }

    /**
     * Returns the result of subtracting the second input from the first.
     */
    fun subtract(first: Double, second: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.subtract(first, second))
    }

    /**
     * Returns the result of subtracting it from 0.
     */
    fun subtract(value: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.subtract(value))
    }

    /**
     * Returns the result of floating point division of the first input by the second.
     */
    fun division(first: Double, second: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.division(first, second))
    }

    /**
     * Returns the remainder after integer division of the first input by the second.
     */
    fun mod(first: Double, second: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.mod(first, second))
    }

    /**
     * Returns the result of raising the first input to the power specified by the second.
     */
    fun pow(first: Double, second: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.pow(first, second))
    }

    /**
     * Returns the square root of the input.
     */
    fun sqrt(value: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.sqrt(value))
    }

    /**
     * Returns the base-ten logarithm of the input.
     */
    fun log10(value: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.log10(value))
    }

    /**
     * Returns the natural logarithm of the input.
     */
    fun ln(value: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.ln(value))
    }

    /**
     * Returns the base-two logarithm of the input.
     */
    fun log2(value: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.log2(value))
    }

    /**
     * Returns the sine of the input.
     */
    fun sin(value: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.sin(value))
    }

    /**
     * Returns the cosine of the input.
     */
    fun cos(value: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.cos(value))
    }

    /**
     * Returns the tangent of the input.
     */
    fun tan(value: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.tan(value))
    }

    /**
     * Returns the arcsine of the input.
     */
    fun asin(value: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.asin(value))
    }

    /**
     * Returns the arccosine of the input.
     */
    fun acos(value: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.acos(value))
    }

    /**
     * Returns the arctangent of the input.
     */
    fun atan(value: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.atan(value))
    }

    /**
     * Returns the minimum value of the inputs.
     */
    fun min(vararg values: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.min(*values))
    }

    /**
     * Returns the maximum value of the inputs.
     */
    fun max(vararg values: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.max(*values))
    }

    /**
     * Rounds the input to the nearest integer. Halfway values are rounded away from zero. For example,
     * `["round", -1.5]` evaluates to -2.
     */
    fun round(value: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.round(value))
    }

    /**
     * Returns the absolute value of the input.
     */
    fun abs(value: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.abs(value))
    }

    /**
     * Returns the smallest integer that is greater than or equal to the input.
     */
    fun ceil(value: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.ceil(value))
    }

    /**
     * Returns the largest integer that is less than or equal to the input.
     */
    fun floor(value: Double): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.floor(value))
    }

    /**
     * Returns `true` if the input string is expected to render legibly. Returns `false` if the input
     * string contains sections that cannot be rendered without potential loss of meaning (e.g. Indic scripts that
     * require complex text shaping, or right-to-left scripts if the the `mapbox-gl-rtl-text` plugin is not in use
     * in Mapbox GL JS).
     */
    fun isSupportedScript(script: String): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.isSupportedScript(script))
    }

    /**
     * Returns the input string converted to uppercase. Follows the Unicode Default Case Conversion algorithm and the
     * locale-insensitive case mappings in the Unicode Character Database.
     */
    fun upcase(value: String): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.upcase(value))
    }

    /**
     * Returns the input string converted to lowercase. Follows the Unicode Default Case Conversion algorithm and the
     * locale-insensitive case mappings in the Unicode Character Database.
     */
    fun downcase(value: String): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.downcase(value))
    }

    /**
     * Returns a `string` consisting of the concatenation of the inputs.
     */
    fun concat(vararg values: String): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.concat(*values))
    }

    /**
     * Converts the input number into a string representation using the providing formatting rules. If set, the
     * `locale` argument specifies the locale to use, as a BCP 47 language tag. If set, the
     * `currency` argument specifies an ISO 4217 code to use for currency-style formatting. If set, the `min-fraction-digits`
     * and `max-fraction-digits` arguments specify the minimum and maximum number of fractional digits to include.
     */
    fun numberFormat(value: Double, block: NumberFormatBuilder.() -> Unit): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.numberFormat(value, block))
    }

    /**
     * References variable bound using "let".
     */
    fun varExpression(value: String): ExpressionBuilder = apply {
      this@ExpressionBuilder.arguments.add(Expression.varExpression(value))
    }
  }

  /**
   * Builder for Interpolate Expression.
   *
   * @param operator compulsory operator field for the builder
   */
  @ExpressionDsl
  class InterpolatorBuilder(operator: String) : ExpressionBuilder(operator) {
    /**
     * Interpolates linearly between the pair of stops just less than and just greater than the input.
     */
    fun linear(): InterpolatorBuilder = apply {
      this.arguments.add(Expression.linear())
    }

    /**
     * Interpolates exponentially between the stops just less than and just greater than the input.
     * `base` controls the rate at which the output increases:
     * higher values make the output increase more towards the high end of the range.
     * With values close to 1 the output increases linearly.
     */
    fun exponential(block: ExpressionBuilder.() -> Unit): InterpolatorBuilder = apply {
      this.arguments.add(Expression.exponential(block))
    }

    /**
     * Interpolates exponentially between the stops just less than and just greater than the input.
     * `base` controls the rate at which the output increases:
     * higher values make the output increase more towards the high end of the range.
     * With values close to 1 the output increases linearly.
     */
    fun exponential(value: Double): InterpolatorBuilder = apply {
      this.arguments.add(Expression.exponential(value))
    }

    /**
     * Interpolates exponentially between the stops just less than and just greater than the input.
     * `base` controls the rate at which the output increases:
     * higher values make the output increase more towards the high end of the range.
     * With values close to 1 the output increases linearly.
     */
    fun exponential(value: Expression): InterpolatorBuilder = apply {
      this.arguments.add(Expression.exponential(value))
    }

    /**
     * Interpolates using the cubic bezier curve defined by the given control points.
     */
    fun cubicBezier(block: ExpressionBuilder.() -> Unit): InterpolatorBuilder = apply {
      this.arguments.add(Expression.cubicBezier(block))
    }

    /**
     * Interpolates using the cubic bezier curve defined by the given control points.
     */
    fun cubicBezier(x1: Expression, x2: Expression, x3: Expression, x4: Expression): InterpolatorBuilder = apply {
      this.arguments.add(Companion.cubicBezier(x1, x2, x3, x4))
    }

    /**
     * Interpolates using the cubic bezier curve defined by the given control points.
     */
    fun cubicBezier(x1: Double, x2: Double, x3: Double, x4: Double): InterpolatorBuilder = apply {
      this.arguments.add(Companion.cubicBezier(x1, x2, x3, x4))
    }
  }

  /**
   * Builder for Number Format expression.
   *
   * @param input the input as a number
   */
  @ExpressionDsl
  class NumberFormatBuilder(private val input: Expression) : Builder("number-format") {

    private val options = HashMap<String, Value>()

    /**
     * Number formatting option for specifying the locale to use, as a BCP 47 language tag.
     * @param locale the locale to use while performing number formatting
     * @return number format builder
     */
    fun locale(locale: String): NumberFormatBuilder = apply {
      this.options["locale"] = literal(locale)
    }

    /**
     * Number formatting option for specifying the locale to use, as a BCP 47 language tag.
     * @param locale the locale to use while performing number formatting
     * @return number format builder
     */
    fun locale(locale: Expression): NumberFormatBuilder = apply {
      this.options["locale"] = locale
    }

    /**
     * Number formatting option for specifying the locale to use, as a BCP 47 language tag.
     *
     * @return number format builder
     */
    fun locale(block: ExpressionBuilder.() -> Unit): NumberFormatBuilder = apply {
      this.options["locale"] = string(block)
    }

    /**
     * Number formatting option for specifying the currency to use, an ISO 4217 code.
     *
     * @param currency the currency to use while performing number formatting
     * @return number format option
     */
    fun currency(currency: String): NumberFormatBuilder = apply {
      this.options["currency"] = literal(currency)
    }

    /**
     * Number formatting option for specifying the currency to use, an ISO 4217 code.
     *
     * @param currency the currency to use while performing number formatting
     * @return number format option
     */
    fun currency(currency: Expression): NumberFormatBuilder = apply {
      this.options["currency"] = currency
    }

    /**
     * Number formatting option for specifying the currency to use, an ISO 4217 code.
     *
     * @return number format option
     */
    fun currency(block: ExpressionBuilder.() -> Unit): NumberFormatBuilder = apply {
      this.options["currency"] = string(block)
    }

    /**
     * Number formatting options for specifying the minimum fraction digits to include.
     *
     * @param minFractionDigits the amount of minimum fraction digits to include
     * @return number format option
     */
    fun minFractionDigits(minFractionDigits: Int): NumberFormatBuilder = apply {
      this.options["min-fraction-digits"] = literal(minFractionDigits.toLong())
    }

    /**
     * Number formatting options for specifying the minimum fraction digits to include.
     *
     * @param minFractionDigits the amount of minimum fraction digits to include
     * @return number format option
     */
    fun minFractionDigits(minFractionDigits: Expression): NumberFormatBuilder = apply {
      this.options["min-fraction-digits"] = minFractionDigits
    }

    /**
     * Number formatting options for specifying the minimum fraction digits to include.
     *
     * @return number format option
     */
    fun minFractionDigits(block: ExpressionBuilder.() -> Unit): NumberFormatBuilder = apply {
      this.options["min-fraction-digits"] = number(block)
    }

    /**
     * Number formatting options for specifying the minimum fraction digits to include.
     *
     * @param maxFractionDigits the amount of minimum fraction digits to include
     * @return number format option
     */
    fun maxFractionDigits(maxFractionDigits: Int): NumberFormatBuilder = apply {
      this.options["max-fraction-digits"] = literal(maxFractionDigits.toLong())
    }

    /**
     * Number formatting options for specifying the minimum fraction digits to include.
     *
     * @param maxFractionDigits the amount of minimum fraction digits to include
     * @return number format option
     */
    fun maxFractionDigits(maxFractionDigits: Expression): NumberFormatBuilder = apply {
      this.options["max-fraction-digits"] = maxFractionDigits
    }

    /**
     * Number formatting options for specifying the minimum fraction digits to include.
     *
     * @return number format option
     */
    fun maxFractionDigits(block: ExpressionBuilder.() -> Unit): NumberFormatBuilder = apply {
      this.options["max-fraction-digits"] = number(block)
    }

    /**
     * Build the number-format expression.
     */
    override fun build(): Expression {
      this.arguments.add(input)
      this.arguments.add(Expression(options))
      return super.build()
    }
  }

  /**
   * Builder for a Formatted Section.
   *
   * @param content the content of the FormattedSection as a text string literal or an image as Expression
   */
  @ExpressionDsl
  class FormatSectionBuilder(private val content: Expression) {

    private val options = HashMap<String, Value>()

    /**
     * If set, the font-scale argument specifies a scaling factor relative to the text-size
     * specified in the root layout properties.
     *
     * "font-scale" is required to be of a resulting type number.
     *
     * @param fontScale
     */
    fun fontScale(fontScale: Double): FormatSectionBuilder = apply {
      this.options["font-scale"] = literal(fontScale)
    }

    /**
     * If set, the font-scale argument specifies a scaling factor relative to the text-size
     * specified in the root layout properties.
     *
     * "font-scale" is required to be of a resulting type number.
     *
     * @param fontScale
     */
    fun fontScale(fontScale: Expression): FormatSectionBuilder = apply {
      this.options["font-scale"] = fontScale
    }

    /**
     * If set, the font-scale argument specifies a scaling factor relative to the text-size
     * specified in the root layout properties.
     *
     * "font-scale" is required to be of a resulting type number.
     */
    fun fontScale(block: ExpressionBuilder.() -> Unit): FormatSectionBuilder = apply {
      this.options["font-scale"] = number(block)
    }

    /**
     * If set, the text-font argument overrides the font specified by the root layout properties.
     *
     * "text-font" is required to be a literal array.
     *
     * The requested font stack has to be a part of the used style.
     * For more information see [The online documentation](https://www.mapbox.com/help/define-font-stack/).
     *
     * @param textFont
     */
    fun textFont(textFont: List<String>): FormatSectionBuilder = apply {
      this.options["text-font"] = literal(textFont)
    }

    /**
     * If set, the text-font argument overrides the font specified by the root layout properties.
     *
     * "text-font" is required to be a literal array.
     *
     * The requested font stack has to be a part of the used style.
     * For more information see [The online documentation](https://www.mapbox.com/help/define-font-stack/).
     *
     * @param textFont
     */
    fun textFont(textFont: Expression): FormatSectionBuilder = apply {
      this.options["text-font"] = textFont
    }

    /**
     * If set, the text-font argument overrides the font specified by the root layout properties.
     *
     * "text-font" is required to be a literal array.
     *
     * The requested font stack has to be a part of the used style.
     * For more information see [The online documentation](https://www.mapbox.com/help/define-font-stack/).
     */
    fun textFont(block: ExpressionBuilder.() -> Unit): FormatSectionBuilder = apply {
      this.options["text-font"] =
        ExpressionBuilder("array").addArgument(literal("string")).apply(block).build()
    }

    /**
     * If set, the text-color argument overrides the color specified by the root paint properties.
     *
     * @param textColor
     */
    fun textColor(@ColorInt textColor: Int): FormatSectionBuilder = apply {
      this.options["text-color"] = color(textColor)
    }

    /**
     * If set, the text-color argument overrides the color specified by the root paint properties.
     *
     * @param textColor
     */
    fun textColor(textColor: String): FormatSectionBuilder = apply {
      this.options["text-color"] = literal(textColor)
    }

    /**
     * If set, the text-color argument overrides the color specified by the root paint properties.
     *
     * @param textColor
     */
    fun textColor(textColor: Expression): FormatSectionBuilder = apply {
      this.options["text-color"] = textColor
    }

    /**
     * If set, the text-color argument overrides the color specified by the root paint properties.
     */
    fun textColor(block: ExpressionBuilder.() -> Unit): FormatSectionBuilder = apply {
      this.options["text-color"] = toColor(block)
    }

    /**
     * Build a list of expressions as one format section.
     */
    fun build(): List<Expression> {
      return listOf(content, Expression(options))
    }
  }

  /**
   * Builder for Formatted TextField.
   */
  @ExpressionDsl
  class FormatBuilder : Builder("format") {
    private val sections = ArrayList<Expression>()

    /**
     * A component of the Formatted expression.
     *
     * @param content displayed string or image as an Expression
     */
    fun formatSection(content: Expression): FormatBuilder = apply {
      sections.addAll(FormatSectionBuilder(content).build())
    }

    /**
     * A component of the Formatted expression.
     *
     * @param content displayed string or image as an Expression
     */
    fun formatSection(content: Expression, block: FormatSectionBuilder.() -> Unit): FormatBuilder = apply {
      sections.addAll(FormatSectionBuilder(content).apply(block).build())
    }

    /**
     * A component of the Formatted expression.
     *
     * @param text displayed string
     */
    fun formatSection(text: String): FormatBuilder = apply {
      sections.addAll(FormatSectionBuilder(literal(text)).build())
    }

    /**
     * A component of the Formatted expression.
     *
     * @param text displayed string
     */
    fun formatSection(text: String, block: FormatSectionBuilder.() -> Unit): FormatBuilder = apply {
      sections.addAll(FormatSectionBuilder(literal(text)).apply(block).build())
    }

    /**
     * Build the Format expression.
     */
    override fun build(): Expression {
      this.arguments.addAll(sections)
      return super.build()
    }
  }

  /**
   * Builder for Collator expression.
   */
  @ExpressionDsl
  class CollatorBuilder : Builder("collator") {
    private val options = HashMap<String, Value>()

    /**
     * Set the case-sensitive option, default to false.
     */
    fun caseSensitive(caseSensitive: Expression): CollatorBuilder = apply {
      options["case-sensitive"] = caseSensitive
    }

    /**
     * Set the case-sensitive option, default to false.
     */
    fun caseSensitive(block: ExpressionBuilder.() -> Unit): CollatorBuilder = apply {
      options["case-sensitive"] = boolean(block)
    }

    /**
     * Set the case-sensitive option, default to false.
     */
    fun caseSensitive(caseSensitive: Boolean = false): CollatorBuilder = apply {
      options["case-sensitive"] = literal(caseSensitive)
    }

    /**
     * Set the diacritic-sensitive option, default to false.
     */
    fun diacriticSensitive(diacriticSensitive: Expression): CollatorBuilder = apply {
      options["diacritic-sensitive"] = diacriticSensitive
    }

    /**
     * Set the  diacritic-sensitive option, default to false.
     */
    fun diacriticSensitive(block: ExpressionBuilder.() -> Unit): CollatorBuilder = apply {
      options["diacritic-sensitive"] = boolean(block)
    }

    /**
     * Set the diacritic-sensitive option, default to false.
     */
    fun diacriticSensitive(diacriticSensitive: Boolean = false): CollatorBuilder = apply {
      options["diacritic-sensitive"] = literal(diacriticSensitive)
    }

    /**
     * Set the locale option.
     */
    fun locale(locale: Expression): CollatorBuilder = apply {
      options["locale"] = locale
    }

    /**
     * Set the locale option.
     */
    fun locale(block: ExpressionBuilder.() -> Unit): CollatorBuilder = apply {
      options["locale"] = string(block)
    }

    /**
     * Set the locale option.
     */
    fun locale(locale: String): CollatorBuilder = apply {
      options["locale"] = literal(locale)
    }

    /**
     * Set the locale option.
     */
    fun locale(locale: Locale): CollatorBuilder = apply {
      val localeStringBuilder = StringBuilder()

      localeStringBuilder.append(locale.language)

      val country = locale.country
      if (country.isNotEmpty()) {
        localeStringBuilder.append("-")
        localeStringBuilder.append(country)
      }

      options["locale"] = literal(localeStringBuilder.toString())
    }

    /**
     * Build the Collator expression.
     */
    override fun build(): Expression {
      this.arguments.add(Expression(options))
      return super.build()
    }
  }

  /**
   * Builder for Image expression options
   */
  @ExpressionDsl
  class ImageBuilder : ExpressionBuilder("image") {

    /**
     * Parameters to apply to the image.
     * Currently, only applies to vector images and only color parameters are supported.
     *
     * @param pairs options for the image
     */
    @MapboxExperimental
    fun imageOptions(vararg pairs: Pair<String, Expression>): ImageBuilder = apply {
      arguments.add(Expression(hashMapOf("params" to valueOf(hashMapOf(*pairs)))))
    }
  }

  /**
   * Static variables and methods.
   */
  companion object {
    /**
     * Construct Expression from a raw string.
     */
    @JvmStatic
    fun fromRaw(expression: String): Expression =
      fromJson(expression).take().unwrapToExpression()

    /**
     * For two inputs, returns the result of subtracting the second input from the first. For a
     * single input, returns the result of subtracting it from 0.
     */
    @JvmStatic
    fun subtract(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("-")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "-".
     */
    fun subtract(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("-").apply(block).build()

    /**
     * Logical negation. Returns `true` if the input is `false`, and `false` if the input is `true`.
     */
    @JvmStatic
    fun not(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("!")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "!".
     */
    fun not(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("!").apply(block).build()

    /**
     * Returns `true` if the input values are not equal, `false` otherwise. The comparison is strictly typed:
     * values of different runtime types are always considered unequal. Cases where the types are known to
     * be different at parse time are considered invalid and will produce a parse error. Accepts an
     * optional `collator` argument to control locale-dependent string comparisons.
     */
    @JvmStatic
    fun neq(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("!=")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "!=".
     */
    fun neq(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("!=").apply(block).build()

    /**
     * Returns the product of the inputs.
     */
    @JvmStatic
    fun product(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("*")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "*".
     */
    fun product(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("*").apply(block).build()

    /**
     * Returns the result of floating point division of the first input by the second.
     */
    @JvmStatic
    fun division(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("/")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "/".
     */
    fun division(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("/").apply(block).build()

    /**
     * Returns the remainder after integer division of the first input by the second.
     */
    @JvmStatic
    fun mod(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("%")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "%".
     */
    fun mod(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("%").apply(block).build()

    /**
     * Returns the result of raising the first input to the power specified by the second.
     */
    @JvmStatic
    fun pow(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("^")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "^".
     */
    fun pow(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("^").apply(block).build()

    /**
     * Returns the sum of the inputs.
     */
    @JvmStatic
    fun sum(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("+")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "+".
     */
    fun sum(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("+").apply(block).build()

    /**
     * Returns `true` if the first input is strictly less than the second, `false` otherwise. The arguments
     * are required to be either both strings or both numbers; if during evaluation they are not,
     * expression evaluation produces an error. Cases where this constraint is known not to hold at parse
     * time are considered in valid and will produce a parse error. Accepts an optional `collator` argument
     * to control locale-dependent string comparisons.
     */
    @JvmStatic
    fun lt(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("<")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "<".
     */
    fun lt(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("<").apply(block).build()

    /**
     * Returns `true` if the first input is less than or equal to the second, `false` otherwise.
     * The arguments are required to be either both strings or both numbers; if during evaluation they
     * are not, expression evaluation produces an error. Cases where this constraint is known not to hold
     * at parse time are considered in valid and will produce a parse error. Accepts an optional
     * `collator` argument to control locale-dependent string comparisons.
     */
    @JvmStatic
    fun lte(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("<=")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "<=".
     */
    fun lte(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("<=").apply(block).build()

    /**
     * Returns `true` if the input values are equal, `false` otherwise. The comparison is strictly typed: values
     * of different runtime types are always considered unequal. Cases where the types are known to be
     * different at parse time are considered invalid and will produce a parse error. Accepts an optional
     * `collator` argument to control locale-dependent string comparisons.
     */
    @JvmStatic
    fun eq(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("==")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "==".
     */
    fun eq(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("==").apply(block).build()

    /**
     * Returns `true` if the first input is strictly greater than the second, `false` otherwise. The arguments
     * are required to be either both strings or both numbers; if during evaluation they are not,
     * expression evaluation produces an error. Cases where this constraint is known not to hold at parse
     * time are considered in valid and will produce a parse error. Accepts an optional `collator` argument
     * to control locale-dependent string comparisons.
     */
    @JvmStatic
    fun gt(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder(">")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for ">".
     */
    fun gt(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder(">").apply(block).build()

    /**
     * Returns `true` if the first input is greater than or equal to the second, `false` otherwise.
     * The arguments are required to be either both strings or both numbers; if during evaluation they
     * are not, expression evaluation produces an error. Cases where this constraint is known not to hold
     * at parse time are considered in valid and will produce a parse error. Accepts an optional
     * `collator` argument to control locale-dependent string comparisons.
     */
    @JvmStatic
    fun gte(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder(">=")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for ">=".
     */
    fun gte(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder(">=").apply(block).build()

    /**
     * Returns the absolute value of the input.
     */
    @JvmStatic
    fun abs(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("abs")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "abs".
     */
    fun abs(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("abs").apply(block).build()

    /**
     * Returns the value of a cluster property accumulated so far. Can only be used in the
     * `clusterProperties` option of a clustered GeoJSON source.
     */
    @JvmStatic
    fun accumulated(): Expression = ExpressionBuilder("accumulated").build()

    /**
     * Returns the arccosine of the input, in radians between −π/2 and π/2.
     */
    @JvmStatic
    fun acos(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("acos")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "acos".
     */
    fun acos(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("acos").apply(block).build()

    /**
     * Returns a string which matches one of the values specified in the text-anchor layout property, depending
     * on the best-fit anchor for the symbol during rendering. Using this expression the content of the
     * layer can be dynamically configured for the specific anchor type.
     */
    @JvmStatic
    @MapboxExperimental
    fun activeAnchor(): Expression = ExpressionBuilder("active-anchor").build()

    /**
     * Returns `true` if all the inputs are `true`, `false` otherwise. The inputs are evaluated in order,
     * and evaluation is short-circuiting: once an input expression evaluates to `false`, the result is `false` and
     * no further input expressions are evaluated.
     */
    @JvmStatic
    fun all(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("all")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "all".
     */
    fun all(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("all").apply(block).build()

    /**
     * Returns `true` if any of the inputs are `true`, `false` otherwise. The inputs are evaluated in
     * order, and evaluation is short-circuiting: once an input expression evaluates to `true`, the result is `true`
     * and no further input expressions are evaluated.
     */
    @JvmStatic
    fun any(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("any")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "any".
     */
    fun any(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("any").apply(block).build()

    /**
     * Asserts that the input is an array (optionally with a specific item type and length). If,
     * when the input expression is evaluated, it is not of the asserted type, then this assertion
     * will cause the whole expression to be aborted.
     */
    @JvmStatic
    fun array(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("array")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "array".
     */
    fun array(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("array").apply(block).build()

    /**
     * Returns the arcsine of the input, in radians between −π/2 and π/2.
     */
    @JvmStatic
    fun asin(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("asin")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "asin".
     */
    fun asin(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("asin").apply(block).build()

    /**
     * Retrieves an item from an array.
     */
    @JvmStatic
    fun at(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("at")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "at".
     */
    fun at(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("at").apply(block).build()

    /**
     * Retrieves an item from an array. If the array contains numeric values and the provided index
     * is non-integer, the expression returns an interpolated value between adjacent items.
     */
    @JvmStatic
    fun atInterpolated(vararg expressions: Expression): Expression {
      val builder = InterpolatorBuilder("at-interpolated")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "at-interpolated".
     */
    fun atInterpolated(block: InterpolatorBuilder.() -> Unit): Expression =
      InterpolatorBuilder("at-interpolated").apply(block).build()

    /**
     * Returns the arctangent of the input, in radians between −π/2 and π/2.
     */
    @JvmStatic
    fun atan(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("atan")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "atan".
     */
    fun atan(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("atan").apply(block).build()

    /**
     * Asserts that the input value is a boolean. If multiple values are provided, each one is
     * evaluated in order until a boolean is obtained. If none of the inputs are booleans, the
     * expression is an error.
     */
    @JvmStatic
    fun boolean(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("boolean")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "boolean".
     */
    fun boolean(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("boolean").apply(block).build()

    /**
     * Selects the first output whose corresponding test condition evaluates to true, or the fallback value otherwise.
     */
    @JvmStatic
    fun switchCase(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("case")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "case".
     */
    fun switchCase(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("case").apply(block).build()

    /**
     * Returns the smallest integer that is greater than or equal to the input.
     */
    @JvmStatic
    fun ceil(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("ceil")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "ceil".
     */
    fun ceil(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("ceil").apply(block).build()

    /**
     * Evaluates each expression in turn until the first valid value is obtained. Invalid values are `null`
     * and [`'image'`](#types-image) expressions that are unavailable in the style. If all values are invalid, `coalesce` returns
     * the first value listed.
     */
    @JvmStatic
    fun coalesce(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("coalesce")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "coalesce".
     */
    fun coalesce(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("coalesce").apply(block).build()

    /**
     * Returns a `collator` for use in locale-dependent comparison operations. The `case-sensitive` and `diacritic-sensitive` options default to
     * `false`. The `locale` argument specifies the IETF language tag of the locale to use. If none
     * is provided, the default locale is used. If the requested locale is not available, the `collator`
     * will use a system-defined fallback locale. Use `resolved-locale` to test the results of locale fallback behavior.
     */
    @JvmStatic
    fun collator(
      caseSensitive: Boolean = false,
      diacriticSensitive: Boolean = false,
      locale: Locale
    ): Expression {
      return CollatorBuilder().caseSensitive(caseSensitive).diacriticSensitive(diacriticSensitive)
        .locale(locale).build()
    }

    /**
     * Returns a `collator` for use in locale-dependent comparison operations. The `case-sensitive` and `diacritic-sensitive` options default to
     * `false`. The `locale` argument specifies the IETF language tag of the locale to use. If none
     * is provided, the default locale is used. If the requested locale is not available, the `collator`
     * will use a system-defined fallback locale. Use `resolved-locale` to test the results of locale fallback behavior.
     */
    @JvmStatic
    fun collator(
      caseSensitive: Boolean = false,
      diacriticSensitive: Boolean = false,
      locale: String
    ): Expression {
      return CollatorBuilder().caseSensitive(caseSensitive).diacriticSensitive(diacriticSensitive)
        .locale(locale).build()
    }

    /**
     * Returns a `collator` for use in locale-dependent comparison operations. The `case-sensitive` and `diacritic-sensitive` options default to
     * `false`. The `locale` argument specifies the IETF language tag of the locale to use. If none
     * is provided, the default locale is used. If the requested locale is not available, the `collator`
     * will use a system-defined fallback locale. Use `resolved-locale` to test the results of locale fallback behavior.
     */
    @JvmStatic
    fun collator(
      caseSensitive: Expression,
      diacriticSensitive: Expression,
      locale: Expression
    ): Expression {
      return CollatorBuilder().caseSensitive(caseSensitive).diacriticSensitive(diacriticSensitive)
        .locale(locale).build()
    }

    /**
     * Dsl function for "collator".
     */
    fun collator(block: CollatorBuilder.() -> Unit): Expression {
      return CollatorBuilder().apply(block).build()
    }

    /**
     * Returns a `string` consisting of the concatenation of the inputs. Each input is converted to a
     * string as if by `to-string`.
     */
    @JvmStatic
    fun concat(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("concat")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "concat".
     */
    fun concat(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("concat").apply(block).build()

    /**
     * Retrieves the configuration value for the given option. Returns null if the requested option is missing.
     */
    @JvmStatic
    fun config(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("config")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "config".
     */
    fun config(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("config").apply(block).build()

    /**
     * Returns the cosine of the input, interpreted as radians.
     */
    @JvmStatic
    fun cos(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("cos")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "cos".
     */
    fun cos(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("cos").apply(block).build()

    /**
     * Returns the shortest distance in meters between the evaluated feature and the input geometry. The input
     * value can be a valid GeoJSON of type `Point`, `MultiPoint`, `LineString`, `MultiLineString`, `Polygon`, `MultiPolygon`, `Feature`, or
     * `FeatureCollection`. Distance values returned may vary in precision due to loss in precision from encoding geometries,
     * particularly below zoom level 13.
     */
    @JvmStatic
    fun distance(geojson: GeoJson): Expression {
      val builder = ExpressionBuilder("distance")
      val expected = fromJson(geojson.toJson())
      expected.value?.let {
        @Suppress("UNCHECKED_CAST")
        builder.addArgument(Expression(it.contents as HashMap<String, Value>))
        return builder.build()
      }
      throw MapboxStyleException(expected.error)
    }

    /**
     * Returns the distance of a `symbol` instance from the center of the map. The distance is
     * measured in pixels divided by the height of the map container. It measures 0 at the
     * center, decreases towards the camera and increase away from the camera. For example, if the height
     * of the map is 1000px, a value of -1 means 1000px away from the center towards
     * the camera, and a value of 1 means a distance of 1000px away from the camera
     * from the center. `["distance-from-center"]` may only be used in the `filter` expression for a `symbol` layer.
     */
    @JvmStatic
    fun distanceFromCenter(): Expression = ExpressionBuilder("distance-from-center").build()

    /**
     * Returns the input string converted to lowercase. Follows the Unicode Default Case Conversion algorithm and the
     * locale-insensitive case mappings in the Unicode Character Database.
     */
    @JvmStatic
    fun downcase(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("downcase")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "downcase".
     */
    fun downcase(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("downcase").apply(block).build()

    /**
     * Returns the mathematical constant e.
     */
    @JvmStatic
    fun e(): Expression = ExpressionBuilder("e").build()

    /**
     * Retrieves a property value from the current feature's state. Returns `null` if the requested property is
     * not present on the feature's state. A feature's state is not part of the GeoJSON or
     * vector tile data, and must be set programmatically on each feature. Features are identified by their
     * `id` attribute, which must be an integer or a string that can be cast to an
     * integer. Note that ["feature-state"] can only be used with paint properties that support data-driven styling.
     */
    @JvmStatic
    fun featureState(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("feature-state")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "feature-state".
     */
    fun featureState(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("feature-state").apply(block).build()

    /**
     * Returns the largest integer that is less than or equal to the input.
     */
    @JvmStatic
    fun floor(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("floor")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "floor".
     */
    fun floor(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("floor").apply(block).build()

    /**
     * Returns a `formatted` string for displaying mixed-format text in the `text-field` property. The input may contain
     * a string literal or expression, including an [`'image'`](#types-image) expression. Strings may be followed by a style
     * override object that supports the following properties:
     * - `"text-font"`: Overrides the font stack specified by the root layout property.
     * - `"text-color"`: Overrides the color specified by the root paint property.
     * - `"font-scale"`: Applies a scaling factor on `text-size` as specified by the root layout property.
     */
    @JvmStatic
    fun format(vararg formatSections: FormatSection): Expression {
      val builder = FormatBuilder()
      formatSections.forEach { section ->
        val sectionBuilder = FormatSectionBuilder(section.content)
        section.fontScale?.let { sectionBuilder.fontScale(it) }
        section.textFont?.let { sectionBuilder.textFont(it) }
        section.textColor?.let { sectionBuilder.textColor(it) }
        builder.arguments.addAll(sectionBuilder.build())
      }
      return builder.build()
    }

    /**
     * DSL function for "format".
     */
    fun format(block: FormatBuilder.() -> Unit): Expression =
      FormatBuilder().apply(block).build()

    /**
     * Returns the feature's geometry type: `Point`, `LineString` or `Polygon`. `Multi-` feature types return the singular forms.
     */
    @JvmStatic
    fun geometryType(): Expression = ExpressionBuilder("geometry-type").build()

    /**
     * Retrieves a property value from the current feature's properties, or from another object if a second
     * argument is provided. Returns `null` if the requested property is missing.
     */
    @JvmStatic
    fun get(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("get")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "get".
     */
    fun get(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("get").apply(block).build()

    /**
     * Tests for the presence of an property value in the current feature's properties, or from another
     * object if a second argument is provided.
     */
    @JvmStatic
    fun has(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("has")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "has".
     */
    fun has(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("has").apply(block).build()

    /**
     * Returns the kernel density estimation of a pixel in a heatmap layer, which is a relative
     * measure of how many data points are crowded around a particular pixel. Can only be used
     * in the `heatmap-color` property.
     */
    @JvmStatic
    fun heatmapDensity(): Expression = ExpressionBuilder("heatmap-density").build()

    /**
     * Creates a color value from hue (range 0-360), saturation and lightness components (range 0-100), and an
     * alpha component of 1. If any component is out of range, the expression is an error.
     */
    @JvmStatic
    fun hsl(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("hsl")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "hsl".
     */
    fun hsl(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("hsl").apply(block).build()

    /**
     * Creates a color value from hue (range 0-360), saturation and lightness components (range 0-100), and an
     * alpha component (range 0-1). If any component is out of range, the expression is an error.
     */
    @JvmStatic
    fun hsla(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("hsla")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "hsla".
     */
    fun hsla(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("hsla").apply(block).build()

    /**
     * Returns the feature's id, if it has one.
     */
    @JvmStatic
    fun id(): Expression = ExpressionBuilder("id").build()

    /**
     * Returns a [`ResolvedImage`](/style-spec/reference/types/#resolvedimage) for use in [`icon-image`](/style-spec/reference/layers/#layout-symbol-icon-image), `--pattern` entries, and as a section in the [`'format'`](#types-format)
     * expression.
     *
     * A [`'coalesce'`](#coalesce) expression containing `image` expressions will evaluate to the first listed image that is currently
     * in the style. This validation process is synchronous and requires the image to have been added
     * to the style before requesting it in the `'image'` argument.
     *
     * Every image name can be followed by an optional [`ImageOptions`](/style-spec/reference/types/#imageoptions) object, which will be used for
     * vector images only.
     *
     * To implement crossfading between two images within a symbol layer using the [`icon-image-cross-fade`](/style-spec/reference/layers/#paint-symbol-icon-image-cross-fade) attribute, include a
     * second image as the second argument in the `'image'` expression.
     */
    @JvmStatic
    fun image(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("image")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * Returns a [`ResolvedImage`](/style-spec/reference/types/#resolvedimage) for use in [`icon-image`](/style-spec/reference/layers/#layout-symbol-icon-image), `--pattern` entries, and as a section in the [`'format'`](#types-format)
     * expression.
     *
     * A [`'coalesce'`](#coalesce) expression containing `image` expressions will evaluate to the first listed image that is currently
     * in the style. This validation process is synchronous and requires the image to have been added
     * to the style before requesting it in the `'image'` argument.
     *
     * Every image name can be followed by an optional [`ImageOptions`](/style-spec/reference/types/#imageoptions) object, which will be used for
     * vector images only.
     *
     * To implement crossfading between two images within a symbol layer using the [`icon-image-cross-fade`](/style-spec/reference/layers/#paint-symbol-icon-image-cross-fade) attribute, include a
     * second image as the second argument in the `'image'` expression.
     */
    @MapboxExperimental
    @JvmStatic
    fun image(
      image: Expression,
      options: Map<String, Expression>
    ): Expression {
      val builder = ImageBuilder()
      builder.addArgument(image)
      builder.imageOptions(*options.map { it.key to it.value }.toTypedArray())
      return builder.build()
    }

    /**
     * Returns a [`ResolvedImage`](/style-spec/reference/types/#resolvedimage) for use in [`icon-image`](/style-spec/reference/layers/#layout-symbol-icon-image), `--pattern` entries, and as a section in the [`'format'`](#types-format)
     * expression.
     *
     * A [`'coalesce'`](#coalesce) expression containing `image` expressions will evaluate to the first listed image that is currently
     * in the style. This validation process is synchronous and requires the image to have been added
     * to the style before requesting it in the `'image'` argument.
     *
     * Every image name can be followed by an optional [`ImageOptions`](/style-spec/reference/types/#imageoptions) object, which will be used for
     * vector images only.
     *
     * To implement crossfading between two images within a symbol layer using the [`icon-image-cross-fade`](/style-spec/reference/layers/#paint-symbol-icon-image-cross-fade) attribute, include a
     * second image as the second argument in the `'image'` expression.
     */
    @MapboxExperimental
    @JvmStatic
    fun image(
      image: Expression,
      options: Map<String, Expression>,
      image2: Expression,
      options2: Map<String, Expression>
    ): Expression {
      val builder = ImageBuilder()
        builder.addArgument(image)
        builder.imageOptions(*options.map { it.key to it.value }.toTypedArray())
        builder.addArgument(image2)
        builder.imageOptions(*options2.map { it.key to it.value }.toTypedArray())
        return builder.build()
    }

    /**
     * DSL function for "image".
     */
    fun image(block: ImageBuilder.() -> Unit): Expression =
      ImageBuilder().apply(block).build()

    /**
     * Determines whether an item exists in an array or a substring exists in a string. In
     * the specific case when the second and third arguments are string literals, you must wrap at
     * least one of them in a [`literal`](#types-literal) expression to hint correct interpretation to the [type system](#type-system).
     */
    @JvmStatic
    fun inExpression(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("in")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "in".
     */
    fun inExpression(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("in").apply(block).build()

    /**
     * Returns the first position at which an item can be found in an array or a
     * substring can be found in a string, or `-1` if the input cannot be found. Accepts
     * an optional index from where to begin the search.
     */
    @JvmStatic
    fun indexOf(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("index-of")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "index-of".
     */
    fun indexOf(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("index-of").apply(block).build()

    /**
     * Produces continuous, smooth results by interpolating between pairs of input and output values ("stops"). The `input`
     * may be any numeric expression (e.g., `["get", "population"]`). Stop inputs must be numeric literals in strictly
     * ascending order. The output type must be `number`, `array<number>`, or `color`.
     *
     * Interpolation types:
     * - `["linear"]`: Interpolates linearly between the pair of stops just less than and just greater than
     * the input.
     * - `["exponential", base]`: Interpolates exponentially between the stops just less than and just greater than the
     * input. `base` controls the rate at which the output increases: higher values make the output increase
     * more towards the high end of the range. With values close to 1 the output increases
     * linearly.
     * - `["cubic-bezier", x1, y1, x2, y2]`: Interpolates using the cubic bezier curve defined by the given
     * control points.
     */
    @JvmStatic
    fun interpolate(vararg expressions: Expression): Expression {
      val builder = InterpolatorBuilder("interpolate")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "interpolate".
     */
    fun interpolate(block: InterpolatorBuilder.() -> Unit): Expression =
      InterpolatorBuilder("interpolate").apply(block).build()

    /**
     * Returns `true` if the input string is expected to render legibly. Returns `false` if the input
     * string contains sections that cannot be rendered without potential loss of meaning (e.g. Indic scripts that
     * require complex text shaping, or right-to-left scripts if the the `mapbox-gl-rtl-text` plugin is not in use
     * in Mapbox GL JS).
     */
    @JvmStatic
    fun isSupportedScript(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("is-supported-script")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "is-supported-script".
     */
    fun isSupportedScript(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("is-supported-script").apply(block).build()

    /**
     * Returns the length of an array or string.
     */
    @JvmStatic
    fun length(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("length")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "length".
     */
    fun length(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("length").apply(block).build()

    /**
     * Binds expressions to named variables, which can then be referenced in the result expression using ["var",
     * "variable_name"].
     */
    @JvmStatic
    fun letExpression(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("let")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "let".
     */
    fun letExpression(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("let").apply(block).build()

    /**
     * Returns the progress along a gradient line. Can only be used in the `line-gradient` and `line-z-offset`
     * properties.
     */
    @JvmStatic
    fun lineProgress(): Expression = ExpressionBuilder("line-progress").build()

    /**
     * Provides a literal array or object value.
     */
    @JvmStatic
    fun literal(value: Double): Expression {
      return Expression(value)
    }

    /**
     * Provides a literal array or object value.
     */
    @JvmStatic
    fun literal(value: Long): Expression {
      return Expression(value)
    }

    /**
     * Provides a literal array or object value.
     */
    @JvmStatic
    fun literal(value: Boolean): Expression {
      return Expression(value)
    }

    /**
     * Provides a literal array or object value.
     */
    @JvmStatic
    fun literal(value: String): Expression {
      return Expression(value)
    }

    /**
     * Provides a literal array or object value.
     */
    @JvmStatic
    internal fun literal(value: List<Any>): Expression {
      return ExpressionBuilder("literal").addArgument(Expression(value)).build()
    }

    /**
     * Provides a literal array or object value.
     */
    @JvmStatic
    internal fun literal(value: HashMap<String, Any>): Expression {
      val map = HashMap<String, Value>()
      for ((k, v) in value) {
        map[k] = TypeUtils.wrapToValue(v)
      }
      return ExpressionBuilder("literal").addArgument(Expression(map)).build()
    }

    /**
     * Returns the natural logarithm of the input.
     */
    @JvmStatic
    fun ln(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("ln")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "ln".
     */
    fun ln(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("ln").apply(block).build()

    /**
     * Returns mathematical constant ln(2).
     */
    @JvmStatic
    fun ln2(): Expression = ExpressionBuilder("ln2").build()

    /**
     * Returns the base-ten logarithm of the input.
     */
    @JvmStatic
    fun log10(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("log10")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "log10".
     */
    fun log10(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("log10").apply(block).build()

    /**
     * Returns the base-two logarithm of the input.
     */
    @JvmStatic
    fun log2(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("log2")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "log2".
     */
    fun log2(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("log2").apply(block).build()

    /**
     * Selects the output for which the label value matches the input value, or the fallback value
     * if no match is found. The input can be any expression (for example, `["get", "building_type"]`). Each
     * label must be unique, and must be either:
     *  - a single literal value; or
     *  - an array of literal values, the values of which must be all strings or
     * all numbers (for example `[100, 101]` or `["c", "b"]`).
     *
     * The input matches if any of the values in the array matches using strict equality, similar
     * to the `"in"` operator.
     * If the input type does not match the type of the labels, the result will be
     * the fallback value.
     */
    @JvmStatic
    fun match(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("match")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "match".
     */
    fun match(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("match").apply(block).build()

    /**
     * Returns the maximum value of the inputs.
     */
    @JvmStatic
    fun max(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("max")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "max".
     */
    fun max(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("max").apply(block).build()

    /**
     * Returns a requested property of the light configuration based on the supplied options. Currently the only
     * supported option is `brightness` which returns the global brightness value of the lights on a scale
     * of 0 to 1, where 0 means total darkness and 1 means full brightness. This expression
     * works only with 3D light, i.e. when `lights` root property is defined.
     */
    @JvmStatic
    fun measureLight(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("measure-light")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "measure-light".
     */
    fun measureLight(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("measure-light").apply(block).build()

    /**
     * Returns the minimum value of the inputs.
     */
    @JvmStatic
    fun min(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("min")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "min".
     */
    fun min(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("min").apply(block).build()

    /**
     * Asserts that the input value is a number. If multiple values are provided, each one is
     * evaluated in order until a number is obtained. If none of the inputs are numbers, the
     * expression is an error.
     */
    @JvmStatic
    fun number(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("number")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "number".
     */
    fun number(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("number").apply(block).build()

    /**
     * Converts the input number into a string representation using the providing formatting rules. If set, the
     * `locale` argument specifies the locale to use, as a BCP 47 language tag. If set, the
     * `currency` argument specifies an ISO 4217 code to use for currency-style formatting. If set, the `unit`
     * argument specifies a [simple ECMAScript unit](https://tc39.es/proposal-unified-intl-numberformat/section6/locales-currencies-tz_proposed_out.html#sec-issanctionedsimpleunitidentifier) to use for unit-style formatting. If set, the `min-fraction-digits` and
     * `max-fraction-digits` arguments specify the minimum and maximum number of fractional digits to include.
     */
    @JvmStatic
    fun numberFormat(
      number: Expression,
      locale: Expression? = null,
      currency: Expression? = null,
      minFractionDigits: Expression? = null,
      maxFractionDigits: Expression? = null
    ): Expression {
      val builder = NumberFormatBuilder(number)
      locale?.let { builder.locale(it) }
      currency?.let { builder.currency(it) }
      minFractionDigits?.let { builder.minFractionDigits(it) }
      maxFractionDigits?.let { builder.maxFractionDigits(it) }
      return builder.build()
    }

    /**
     * DSL function for "number-format".
     */
    fun numberFormat(number: Expression, block: NumberFormatBuilder.() -> Unit): Expression =
      NumberFormatBuilder(number).apply(block).build()

    /**
     * Asserts that the input value is an object. If multiple values are provided, each one is
     * evaluated in order until an object is obtained. If none of the inputs are objects, the
     * expression is an error.
     */
    @JvmStatic
    fun objectExpression(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("object")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "object".
     */
    fun objectExpression(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("object").apply(block).build()

    /**
     * Returns the mathematical constant pi.
     */
    @JvmStatic
    fun pi(): Expression = ExpressionBuilder("pi").build()

    /**
     * Returns the current pitch in degrees. `["pitch"]` may only be used in the `filter` expression for
     * a `symbol` layer.
     */
    @JvmStatic
    fun pitch(): Expression = ExpressionBuilder("pitch").build()

    /**
     * Returns the feature properties object. Note that in some cases, it may be more efficient to
     * use `["get", "property_name"]` directly.
     */
    @JvmStatic
    fun properties(): Expression = ExpressionBuilder("properties").build()

    /**
     * Returns a random value in the specified range (first two input numbers) based on a supplied
     * seed (third input). The seed can be an expression or a constant number or string value.
     */
    @JvmStatic
    fun random(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("random")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "random".
     */
    fun random(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("random").apply(block).build()

    /**
     * Returns the length of the particle velocity vector. Can only be used in the `raster-particle-color` property.
     */
    @JvmStatic
    fun rasterParticleSpeed(): Expression = ExpressionBuilder("raster-particle-speed").build()

    /**
     * Returns the raster value of a pixel computed via `raster-color-mix`. Can only be used in the
     * `raster-color` property.
     */
    @JvmStatic
    fun rasterValue(): Expression = ExpressionBuilder("raster-value").build()

    /**
     * Returns the IETF language tag of the locale being used by the provided `collator`. This can
     * be used to determine the default system locale, or to determine if a requested locale was
     * successfully loaded.
     */
    @JvmStatic
    fun resolvedLocale(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("resolved-locale")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "resolved-locale".
     */
    fun resolvedLocale(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("resolved-locale").apply(block).build()

    /**
     * Creates a color value from red, green, and blue components, which must range between 0 and
     * 255, and an alpha component of 1. If any component is out of range, the expression
     * is an error.
     */
    @JvmStatic
    fun rgb(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("rgb")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "rgb".
     */
    fun rgb(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("rgb").apply(block).build()

    /**
     * Creates a color value from red, green, blue components, which must range between 0 and 255,
     * and an alpha component which must range between 0 and 1. If any component is out
     * of range, the expression is an error.
     */
    @JvmStatic
    fun rgba(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("rgba")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "rgba".
     */
    fun rgba(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("rgba").apply(block).build()

    /**
     * Rounds the input to the nearest integer. Halfway values are rounded away from zero. For example,
     * `["round", -1.5]` evaluates to -2.
     */
    @JvmStatic
    fun round(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("round")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "round".
     */
    fun round(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("round").apply(block).build()

    /**
     * Returns the sine of the input, interpreted as radians.
     */
    @JvmStatic
    fun sin(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("sin")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "sin".
     */
    fun sin(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("sin").apply(block).build()

    /**
     * Returns the distance of a point on the sky from the sun position. Returns 0 at
     * sun position and 1 when the distance reaches `sky-gradient-radius`. Can only be used in the `sky-gradient`
     * property.
     */
    @JvmStatic
    fun skyRadialProgress(): Expression = ExpressionBuilder("sky-radial-progress").build()

    /**
     * Returns an item from an array or a substring from a string from a specified start
     * index, or between a start index and an end index if set. The return value is
     * inclusive of the start index but not of the end index.
     */
    @JvmStatic
    fun slice(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("slice")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "slice".
     */
    fun slice(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("slice").apply(block).build()

    /**
     * Returns the square root of the input.
     */
    @JvmStatic
    fun sqrt(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("sqrt")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "sqrt".
     */
    fun sqrt(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("sqrt").apply(block).build()

    /**
     * Produces discrete, stepped results by evaluating a piecewise-constant function defined by pairs of input and output
     * values ("stops"). The `input` may be any numeric expression (e.g., `["get", "population"]`). Stop inputs must be
     * numeric literals in strictly ascending order. Returns the output value of the stop just less than
     * the input, or the first output if the input is less than the first stop.
     */
    @JvmStatic
    fun step(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("step")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "step".
     */
    fun step(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("step").apply(block).build()

    /**
     * Asserts that the input value is a string. If multiple values are provided, each one is
     * evaluated in order until a string is obtained. If none of the inputs are strings, the
     * expression is an error.
     */
    @JvmStatic
    fun string(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("string")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "string".
     */
    fun string(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("string").apply(block).build()

    /**
     * Returns the tangent of the input, interpreted as radians.
     */
    @JvmStatic
    fun tan(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("tan")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "tan".
     */
    fun tan(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("tan").apply(block).build()

    /**
     * Converts the input value to a boolean. The result is `false` when then input is an
     * empty string, 0, `false`, `null`, or `NaN`; otherwise it is `true`.
     */
    @JvmStatic
    fun toBoolean(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("to-boolean")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "to-boolean".
     */
    fun toBoolean(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("to-boolean").apply(block).build()

    /**
     * Converts the input value to a color. If multiple values are provided, each one is evaluated
     * in order until the first successful conversion is obtained. If none of the inputs can be
     * converted, the expression is an error.
     */
    @JvmStatic
    fun toColor(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("to-color")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "to-color".
     */
    fun toColor(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("to-color").apply(block).build()

    /**
     * Returns a four-element array containing the input color's Hue, Saturation, Luminance and alpha components, in that
     * order.
     */
    @JvmStatic
    fun toHsla(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("to-hsla")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "to-hsla".
     */
    fun toHsla(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("to-hsla").apply(block).build()

    /**
     * Converts the input value to a number, if possible. If the input is `null` or `false`,
     * the result is 0. If the input is `true`, the result is 1. If the input
     * is a string, it is converted to a number as specified by the ["ToNumber Applied to
     * the String Type" algorithm](https://tc39.github.io/ecma262/#sec-tonumber-applied-to-the-string-type) of the ECMAScript Language Specification. If multiple values are provided, each one
     * is evaluated in order until the first successful conversion is obtained. If none of the inputs
     * can be converted, the expression is an error.
     */
    @JvmStatic
    fun toNumber(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("to-number")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "to-number".
     */
    fun toNumber(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("to-number").apply(block).build()

    /**
     * Returns a four-element array containing the input color's red, green, blue, and alpha components, in that
     * order.
     */
    @JvmStatic
    fun toRgba(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("to-rgba")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "to-rgba".
     */
    fun toRgba(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("to-rgba").apply(block).build()

    /**
     * Converts the input value to a string. If the input is `null`, the result is `""`.
     * If the input is a [`boolean`](#types-boolean), the result is `"true"` or `"false"`. If the input is
     * a number, it is converted to a string as specified by the ["NumberToString" algorithm](https://tc39.github.io/ecma262/#sec-tostring-applied-to-the-number-type) of the
     * ECMAScript Language Specification. If the input is a [`color`](#color), it is converted to a string of
     * the form `"rgba(r,g,b,a)"`, where `r`, `g`, and `b` are numerals ranging from 0 to 255, and
     * `a` ranges from 0 to 1. If the input is an [`'image'`](#types-image) expression, `'to-string'` returns the
     * image name. Otherwise, the input is converted to a string in the format specified by the
     * [`JSON.stringify`](https://tc39.github.io/ecma262/#sec-json.stringify) function of the ECMAScript Language Specification.
     */
    @JvmStatic
    fun toString(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("to-string")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "to-string".
     */
    fun toString(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("to-string").apply(block).build()

    /**
     * Returns a string describing the type of the given value.
     */
    @JvmStatic
    fun typeofExpression(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("typeof")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "typeof".
     */
    fun typeofExpression(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("typeof").apply(block).build()

    /**
     * Returns the input string converted to uppercase. Follows the Unicode Default Case Conversion algorithm and the
     * locale-insensitive case mappings in the Unicode Character Database.
     */
    @JvmStatic
    fun upcase(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("upcase")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "upcase".
     */
    fun upcase(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("upcase").apply(block).build()

    /**
     * References variable bound using "let".
     */
    @JvmStatic
    fun varExpression(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("var")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "var".
     */
    fun varExpression(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("var").apply(block).build()

    /**
     * Returns `true` if the evaluated feature is fully contained inside a boundary of the input geometry,
     * `false` otherwise. The input value can be a valid GeoJSON of type `Polygon`, `MultiPolygon`, `Feature`, or
     * `FeatureCollection`. Supported features for evaluation:
     * - `Point`: Returns `false` if a point is on the boundary or falls outside the boundary.
     * - `LineString`: Returns `false` if any part of a line falls outside the boundary, the line
     * intersects the boundary, or a line's endpoint is on the boundary.
     */
    @JvmStatic
    fun within(geometry: Geometry): Expression {
      val builder = ExpressionBuilder("within")
      val expected = fromJson(geometry.toJson())
      expected.value?.let {
        @Suppress("UNCHECKED_CAST")
        builder.addArgument(Expression(it.contents as HashMap<String, Value>))
        return builder.build()
      }
      throw MapboxStyleException(expected.error)
    }

    /**
     * Returns the current worldview being used.
     */
    @JvmStatic
    @MapboxExperimental
    fun worldview(vararg expressions: Expression): Expression {
      val builder = ExpressionBuilder("worldview")
      expressions.forEach {
        builder.addArgument(it)
      }
      return builder.build()
    }

    /**
     * DSL function for "worldview".
     */
    fun worldview(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("worldview").apply(block).build()

    /**
     * Returns the current zoom level. Note that in style layout and paint properties, ["zoom"] may only
     * appear as the input to a top-level "step" or "interpolate" expression.
     */
    @JvmStatic
    fun zoom(): Expression = ExpressionBuilder("zoom").build()

    /**
     * Construct a RGB color expression from numbers.
     */
    @JvmStatic
    fun rgb(red: Double, green: Double, blue: Double): Expression {
      return ExpressionBuilder("rgb")
        .addArgument(literal(red))
        .addArgument(literal(green))
        .addArgument(literal(blue))
        .build()
    }

    /**
     * Construct a RGBA color expression from numbers.
     */
    @JvmStatic
    fun rgba(red: Double, green: Double, blue: Double, alpha: Double): Expression {
      return ExpressionBuilder("rgba")
        .addArgument(literal(red))
        .addArgument(literal(green))
        .addArgument(literal(blue))
        .addArgument(literal(alpha))
        .build()
    }

    /**
     * Produces discrete, stepped results by evaluating a piecewise-constant function defined by pairs of input and output
     * values ("stops"). The `input` may be any numeric expression (e.g., `["get", "population"]`). Stop inputs must be
     * numeric literals in strictly ascending order. Returns the output value of the stop just less than
     * the input, or the first output if the input is less than the first stop.
     */
    @JvmStatic
    fun step(input: Expression, output: Expression, vararg stops: Pair<Expression, Expression>): Expression {
      val builder = ExpressionBuilder("step")
        .addArgument(input)
        .addArgument(output)
      stops.forEach {
        builder.addArgument(it.first)
        builder.addArgument(it.second)
      }
      return builder.build()
    }

    /**
     * Selects the first output whose corresponding test condition evaluates to true, or the fallback value otherwise.
     */
    @JvmStatic
    fun switchCase(vararg stops: Pair<Expression, Expression>, fallback: Expression): Expression {
      val builder = ExpressionBuilder("case")
      stops.forEach {
        builder.addArgument(it.first)
        builder.addArgument(it.second)
      }
      builder.addArgument(fallback)
      return builder.build()
    }

    /**
     * Selects the output for which the label value matches the input value, or the fallback value
     * if no match is found. The input can be any expression (for example, `["get", "building_type"]`). Each
     * label must be unique, and must be either:
     * - a single literal value; or
     * - an array of literal values, the values of which must be all strings or
     * all numbers (for example `[100, 101]` or `["c", "b"]`).
     *
     * The input matches if any of the values in the array matches using strict equality, similar
     * to the `"in"` operator.
     * If the input type does not match the type of the labels, the result will be
     * the fallback value.
     */
    @JvmStatic
    fun match(input: Expression, vararg stops: Pair<Expression, Expression>, fallback: Expression): Expression {
      val builder = ExpressionBuilder("match")
      builder.addArgument(input)
      stops.forEach {
        builder.addArgument(it.first)
        builder.addArgument(it.second)
      }
      builder.addArgument(fallback)
      return builder.build()
    }

    /**
     * Produces continuous, smooth results by interpolating between pairs of input and output values ("stops"). The `input`
     * may be any numeric expression (e.g., `["get", "population"]`). Stop inputs must be numeric literals in strictly
     * ascending order. The output type must be `number`, `array<number>`, or `color`.
     *
     * [linearInterpolator] interpolates linearly between the pair of stops just less than and just greater than
     * the input.
     */
    @JvmStatic
    fun linearInterpolator(
      input: Expression,
      vararg stops: Pair<Expression, Expression>
    ): Expression {
      val builder = InterpolatorBuilder("interpolate").apply { linear() }
      builder.addArgument(input)
      stops.forEach {
        builder.addArgument(it.first)
        builder.addArgument(it.second)
      }
      return builder.build()
    }

    /**
     * Produces continuous, smooth results by interpolating between pairs of input and output values ("stops"). The `input`
     * may be any numeric expression (e.g., `["get", "population"]`). Stop inputs must be numeric literals in strictly
     * ascending order. The output type must be `number`, `array<number>`, or `color`.
     *
     * [exponentialInterpolator] interpolates exponentially between the stops just less than and just greater than the
     * input. `base` controls the rate at which the output increases: higher values make the output increase
     * more towards the high end of the range. With values close to 1 the output increases
     * linearly.
     */
    @JvmStatic
    fun exponentialInterpolator(
      base: Expression,
      input: Expression,
      vararg stops: Pair<Expression, Expression>
    ): Expression {
      val builder = InterpolatorBuilder("interpolate").apply { exponential(base) }
      builder.addArgument(input)
      stops.forEach {
        builder.addArgument(it.first)
        builder.addArgument(it.second)
      }
      return builder.build()
    }

    /**
     * Produces continuous, smooth results by interpolating between pairs of input and output values ("stops"). The `input`
     * may be any numeric expression (e.g., `["get", "population"]`). Stop inputs must be numeric literals in strictly
     * ascending order. The output type must be `number`, `array<number>`, or `color`.
     *
     * [exponentialInterpolator] interpolates exponentially between the stops just less than and just greater than the
     * input. `base` controls the rate at which the output increases: higher values make the output increase
     * more towards the high end of the range. With values close to 1 the output increases
     * linearly.
     */
    @JvmStatic
    fun exponentialInterpolator(
      base: Double,
      input: Expression,
      vararg stops: Pair<Expression, Expression>
    ): Expression {
      val builder = InterpolatorBuilder("interpolate").apply { exponential(base) }
      builder.addArgument(input)
      stops.forEach {
        builder.addArgument(it.first)
        builder.addArgument(it.second)
      }
      return builder.build()
    }

    /**
     * Produces continuous, smooth results by interpolating between pairs of input and output values ("stops"). The `input`
     * may be any numeric expression (e.g., `["get", "population"]`). Stop inputs must be numeric literals in strictly
     * ascending order. The output type must be `number`, `array<number>`, or `color`.
     *
     * [cubicBezierInterpolator] interpolates using the cubic bezier curve defined by the given control points.
     */
    @JvmStatic
    fun cubicBezierInterpolator(
      x1: Expression,
      y1: Expression,
      x2: Expression,
      y2: Expression,
      input: Expression,
      vararg stops: Pair<Expression, Expression>
    ): Expression {
      val builder = InterpolatorBuilder("interpolate").apply { cubicBezier(x1, y1, x2, y2) }
      builder.addArgument(input)
      stops.forEach {
        builder.addArgument(it.first)
        builder.addArgument(it.second)
      }
      return builder.build()
    }

    /**
     * Produces continuous, smooth results by interpolating between pairs of input and output values ("stops"). The `input`
     * may be any numeric expression (e.g., `["get", "population"]`). Stop inputs must be numeric literals in strictly
     * ascending order. The output type must be `number`, `array<number>`, or `color`.
     *
     * [cubicBezierInterpolator] interpolates using the cubic bezier curve defined by the given control points.
     */
    @JvmStatic
    fun cubicBezierInterpolator(
      x1: Double,
      y1: Double,
      x2: Double,
      y2: Double,
      input: Expression,
      vararg stops: Pair<Expression, Expression>
    ): Expression {
      val builder = InterpolatorBuilder("interpolate").apply { cubicBezier(x1, y1, x2, y2) }
      builder.addArgument(input)
      stops.forEach {
        builder.addArgument(it.first)
        builder.addArgument(it.second)
      }
      return builder.build()
    }

    /**
     * Interpolates linearly between the pair of stops just less than and just greater than the input.
     */
    @JvmStatic
    fun linear(): Expression = ExpressionBuilder("linear").build()

    /**
     * Interpolates exponentially between the stops just less than and just greater than the input.
     * `base` controls the rate at which the output increases:
     * higher values make the output increase more towards the high end of the range.
     * With values close to 1 the output increases linearly.
     */
    @JvmStatic
    fun exponential(expression: Expression): Expression =
      ExpressionBuilder("exponential").addArgument(expression).build()

    /**
     * Interpolates exponentially between the stops just less than and just greater than the input.
     * `base` controls the rate at which the output increases:
     * higher values make the output increase more towards the high end of the range.
     * With values close to 1 the output increases linearly.
     */
    @JvmStatic
    fun exponential(value: Double): Expression =
      ExpressionBuilder("exponential").addArgument(literal(value)).build()

    /**
     * Interpolates exponentially between the stops just less than and just greater than the input.
     * `base` controls the rate at which the output increases:
     * higher values make the output increase more towards the high end of the range.
     * With values close to 1 the output increases linearly.
     */
    fun exponential(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("exponential").apply(block).build()

    /**
     * Interpolates using the cubic bezier curve defined by the given control points.
     */
    @JvmStatic
    fun cubicBezier(x1: Expression, x2: Expression, x3: Expression, x4: Expression): Expression =
      ExpressionBuilder("cubic-bezier").addArgument(x1).addArgument(x2)
        .addArgument(x3).addArgument(x4).build()

    /**
     * Interpolates using the cubic bezier curve defined by the given control points.
     */
    @JvmStatic
    fun cubicBezier(x1: Double, x2: Double, x3: Double, x4: Double): Expression =
      ExpressionBuilder("cubic-bezier").addArgument(literal(x1)).addArgument(literal(x2))
        .addArgument(literal(x3)).addArgument(literal(x4)).build()

    /**
     * Interpolates using the cubic bezier curve defined by the given control points.
     */
    fun cubicBezier(block: ExpressionBuilder.() -> Unit): Expression =
      ExpressionBuilder("cubic-bezier").apply(block).build()

    /**
     * Convert a color int to the rgba expression.
     */
    @JvmStatic
    fun color(@ColorInt intColor: Int): Expression {
      val array: FloatArray = ColorUtils.colorToRgbaArray(intColor)
      return rgba(
        literal(array[0].toDouble()),
        literal(array[1].toDouble()),
        literal(array[2].toDouble()),
        literal(array[3].toDouble())
      )
    }

    /**
     * Retrieves a property value from the current feature's properties.
     * Expression evaluates to null if the requested property is missing.
     */
    @JvmStatic
    fun get(key: String): Expression {
      return get(literal(key))
    }

    /**
     * Retrieves a property value from the current feature's properties, or from another object if a second
     * argument is provided. Expression evaluates to null if the requested property is missing.
     */
    @JvmStatic
    fun get(key: String, expression: Expression): Expression {
      return get(literal(key), expression)
    }

    /**
     * Logical negation. Returns `true` if the input is `false`, and `false` if the input is `true`.
     */
    @JvmStatic
    fun not(bool: Boolean): Expression {
      return not(literal(bool))
    }

    /**
     * Retrieves an item from an array.
     */
    @JvmStatic
    fun at(index: Double, array: Expression): Expression {
      return at(literal(index), array)
    }

    /**
     * Determines whether an item exists in an array or a substring exists in a string.
     */
    @JvmStatic
    fun inExpression(needle: String, haystack: Expression): Expression {
      return inExpression(literal(needle), haystack)
    }

    /**
     * Determines whether an item exists in an array or a substring exists in a string.
     */
    @JvmStatic
    fun inExpression(needle: Double, haystack: Expression): Expression {
      return inExpression(literal(needle), haystack)
    }

    /**
     * Tests for the presence of an property value in the current feature's properties, or from another
     * object if a second argument is provided.
     */
    @JvmStatic
    fun has(string: String, expression: Expression): Expression {
      return has(literal(string), expression)
    }

    /**
     * Tests for the presence of an property value in the current feature's properties
     */
    @JvmStatic
    fun has(string: String): Expression {
      return has(literal(string))
    }

    /**
     * Gets the length of an string.
     */
    @JvmStatic
    fun length(string: String): Expression {
      return length(literal(string))
    }

    /**
     * Returns the sum of the inputs.
     */
    @JvmStatic
    fun sum(vararg double: Double): Expression {
      val expressionBuilder = ExpressionBuilder("+")
      for (value in double) {
        expressionBuilder.addArgument(literal(value))
      }
      return expressionBuilder.build()
    }

    /**
     * Returns the product of the inputs.
     */
    @JvmStatic
    fun product(vararg double: Double): Expression {
      val expressionBuilder = ExpressionBuilder("*")
      for (value in double) {
        expressionBuilder.addArgument(literal(value))
      }
      return expressionBuilder.build()
    }

    /**
     * Returns the result of subtracting the second input from the first.
     */
    @JvmStatic
    fun subtract(first: Double, second: Double): Expression {
      return subtract(literal(first), literal(second))
    }

    /**
     * Returns the result of subtracting it from 0.
     */
    @JvmStatic
    fun subtract(value: Double): Expression {
      return subtract(literal(value))
    }

    /**
     * Returns the result of floating point division of the first input by the second.
     */
    @JvmStatic
    fun division(first: Double, second: Double): Expression {
      return division(literal(first), literal(second))
    }

    /**
     * Returns the remainder after integer division of the first input by the second.
     */
    @JvmStatic
    fun mod(first: Double, second: Double): Expression {
      return mod(literal(first), literal(second))
    }

    /**
     * Returns the result of raising the first input to the power specified by the second.
     */
    @JvmStatic
    fun pow(first: Double, second: Double): Expression {
      return pow(literal(first), literal(second))
    }

    /**
     * Returns the square root of the input.
     */
    @JvmStatic
    fun sqrt(value: Double): Expression {
      return sqrt(literal(value))
    }

    /**
     * Returns the base-ten logarithm of the input.
     */
    @JvmStatic
    fun log10(value: Double): Expression {
      return log10(literal(value))
    }

    /**
     * Returns the natural logarithm of the input.
     */
    @JvmStatic
    fun ln(value: Double): Expression {
      return ln(literal(value))
    }

    /**
     * Returns the base-two logarithm of the input.
     */
    @JvmStatic
    fun log2(value: Double): Expression {
      return log2(literal(value))
    }

    /**
     * Returns the sine of the input.
     */
    @JvmStatic
    fun sin(value: Double): Expression {
      return sin(literal(value))
    }

    /**
     * Returns the cosine of the input.
     */
    @JvmStatic
    fun cos(value: Double): Expression {
      return cos(literal(value))
    }

    /**
     * Returns the tangent of the input.
     */
    @JvmStatic
    fun tan(value: Double): Expression {
      return tan(literal(value))
    }

    /**
     * Returns the arcsine of the input.
     */
    @JvmStatic
    fun asin(value: Double): Expression {
      return asin(literal(value))
    }

    /**
     * Returns the arccosine of the input.
     */
    @JvmStatic
    fun acos(value: Double): Expression {
      return acos(literal(value))
    }

    /**
     * Returns the arctangent of the input.
     */
    @JvmStatic
    fun atan(value: Double): Expression {
      return atan(literal(value))
    }

    /**
     * Returns the minimum value of the inputs.
     */
    @JvmStatic
    fun min(vararg values: Double): Expression {
      val expressionBuilder = ExpressionBuilder("min")
      for (value in values) {
        expressionBuilder.addArgument(literal(value))
      }
      return expressionBuilder.build()
    }

    /**
     * Returns the maximum value of the inputs.
     */
    @JvmStatic
    fun max(vararg values: Double): Expression {
      val expressionBuilder = ExpressionBuilder("max")
      for (value in values) {
        expressionBuilder.addArgument(literal(value))
      }
      return expressionBuilder.build()
    }

    /**
     * Rounds the input to the nearest integer. Halfway values are rounded away from zero. For example,
     * `["round", -1.5]` evaluates to -2.
     */
    @JvmStatic
    fun round(value: Double): Expression {
      return round(literal(value))
    }

    /**
     * Returns the absolute value of the input.
     */
    @JvmStatic
    fun abs(value: Double): Expression {
      return abs(literal(value))
    }

    /**
     * Returns the smallest integer that is greater than or equal to the input.
     */
    @JvmStatic
    fun ceil(value: Double): Expression {
      return ceil(literal(value))
    }

    /**
     * Returns the largest integer that is less than or equal to the input.
     */
    @JvmStatic
    fun floor(value: Double): Expression {
      return floor(literal(value))
    }

    /**
     * Returns `true` if the input string is expected to render legibly. Returns `false` if the input
     * string contains sections that cannot be rendered without potential loss of meaning (e.g. Indic scripts that
     * require complex text shaping, or right-to-left scripts if the the `mapbox-gl-rtl-text` plugin is not in use
     * in Mapbox GL JS).
     */
    @JvmStatic
    fun isSupportedScript(script: String): Expression {
      return isSupportedScript(literal(script))
    }

    /**
     * Returns the input string converted to uppercase. Follows the Unicode Default Case Conversion algorithm and the
     * locale-insensitive case mappings in the Unicode Character Database.
     */
    @JvmStatic
    fun upcase(value: String): Expression {
      return upcase(literal(value))
    }

    /**
     * Returns the input string converted to lowercase. Follows the Unicode Default Case Conversion algorithm and the
     * locale-insensitive case mappings in the Unicode Character Database.
     */
    @JvmStatic
    fun downcase(value: String): Expression {
      return downcase(literal(value))
    }

    /**
     * Returns a `string` consisting of the concatenation of the inputs.
     */
    @JvmStatic
    fun concat(vararg values: String): Expression {
      val expressionBuilder = ExpressionBuilder("concat")
      for (value in values) {
        expressionBuilder.addArgument(literal(value))
      }
      return expressionBuilder.build()
    }

    /**
     * Converts the input number into a string representation using the providing formatting rules. If set, the
     * `locale` argument specifies the locale to use, as a BCP 47 language tag. If set, the
     * `currency` argument specifies an ISO 4217 code to use for currency-style formatting. If set, the `min-fraction-digits`
     * and `max-fraction-digits` arguments specify the minimum and maximum number of fractional digits to include.
     */
    @JvmStatic
    fun numberFormat(value: Double, block: NumberFormatBuilder.() -> Unit): Expression {
      return numberFormat(literal(value), block)
    }

    /**
     * References variable bound using "let".
     */
    @JvmStatic
    fun varExpression(value: String): Expression {
      return varExpression(literal(value))
    }
  }
}

// End of generated file.