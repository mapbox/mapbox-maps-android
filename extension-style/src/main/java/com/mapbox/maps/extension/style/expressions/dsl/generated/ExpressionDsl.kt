// This file is generated.

package com.mapbox.maps.extension.style.expressions.dsl.generated

import androidx.annotation.ColorInt
import com.mapbox.geojson.GeoJson
import com.mapbox.geojson.Geometry
import com.mapbox.maps.extension.style.expressions.generated.Expression

// DSL functions for [Expression].

/**
 * For two inputs, returns the result of subtracting the second input from the first. For a single input, returns the result of subtracting it from 0.
 */
fun subtract(block: Expression.ExpressionBuilder.() -> Unit) = Expression.subtract(block)

/**
 * Logical negation. Returns `true` if the input is `false`, and `false` if the input is `true`.
 */
fun not(block: Expression.ExpressionBuilder.() -> Unit) = Expression.not(block)

/**
 * Returns `true` if the input values are not equal, `false` otherwise. The comparison is strictly typed: values of different runtime types are always considered unequal. Cases where the types are known to be different at parse time are considered invalid and will produce a parse error. Accepts an optional `collator` argument to control locale-dependent string comparisons.
 */
fun neq(block: Expression.ExpressionBuilder.() -> Unit) = Expression.neq(block)

/**
 * Returns the product of the inputs.
 */
fun product(block: Expression.ExpressionBuilder.() -> Unit) = Expression.product(block)

/**
 * Returns the result of floating point division of the first input by the second.
 */
fun division(block: Expression.ExpressionBuilder.() -> Unit) = Expression.division(block)

/**
 * Returns the remainder after integer division of the first input by the second.
 */
fun mod(block: Expression.ExpressionBuilder.() -> Unit) = Expression.mod(block)

/**
 * Returns the result of raising the first input to the power specified by the second.
 */
fun pow(block: Expression.ExpressionBuilder.() -> Unit) = Expression.pow(block)

/**
 * Returns the sum of the inputs.
 */
fun sum(block: Expression.ExpressionBuilder.() -> Unit) = Expression.sum(block)

/**
 * Returns `true` if the first input is strictly less than the second, `false` otherwise. The arguments are required to be either both strings or both numbers; if during evaluation they are not, expression evaluation produces an error. Cases where this constraint is known not to hold at parse time are considered in valid and will produce a parse error. Accepts an optional `collator` argument to control locale-dependent string comparisons.
 */
fun lt(block: Expression.ExpressionBuilder.() -> Unit) = Expression.lt(block)

/**
 * Returns `true` if the first input is less than or equal to the second, `false` otherwise. The arguments are required to be either both strings or both numbers; if during evaluation they are not, expression evaluation produces an error. Cases where this constraint is known not to hold at parse time are considered in valid and will produce a parse error. Accepts an optional `collator` argument to control locale-dependent string comparisons.
 */
fun lte(block: Expression.ExpressionBuilder.() -> Unit) = Expression.lte(block)

/**
 * Returns `true` if the input values are equal, `false` otherwise. The comparison is strictly typed: values of different runtime types are always considered unequal. Cases where the types are known to be different at parse time are considered invalid and will produce a parse error. Accepts an optional `collator` argument to control locale-dependent string comparisons.
 */
fun eq(block: Expression.ExpressionBuilder.() -> Unit) = Expression.eq(block)

/**
 * Returns `true` if the first input is strictly greater than the second, `false` otherwise. The arguments are required to be either both strings or both numbers; if during evaluation they are not, expression evaluation produces an error. Cases where this constraint is known not to hold at parse time are considered in valid and will produce a parse error. Accepts an optional `collator` argument to control locale-dependent string comparisons.
 */
fun gt(block: Expression.ExpressionBuilder.() -> Unit) = Expression.gt(block)

/**
 * Returns `true` if the first input is greater than or equal to the second, `false` otherwise. The arguments are required to be either both strings or both numbers; if during evaluation they are not, expression evaluation produces an error. Cases where this constraint is known not to hold at parse time are considered in valid and will produce a parse error. Accepts an optional `collator` argument to control locale-dependent string comparisons.
 */
fun gte(block: Expression.ExpressionBuilder.() -> Unit) = Expression.gte(block)

/**
 * Returns the absolute value of the input.
 */
fun abs(block: Expression.ExpressionBuilder.() -> Unit) = Expression.abs(block)

/**
 * Returns the value of a cluster property accumulated so far. Can only be used in the `clusterProperties` option of a clustered GeoJSON source.
 */
fun accumulated() = Expression.accumulated()

/**
 * Returns the arccosine of the input.
 */
fun acos(block: Expression.ExpressionBuilder.() -> Unit) = Expression.acos(block)

/**
 * Returns `true` if all the inputs are `true`, `false` otherwise. The inputs are evaluated in order, and evaluation is short-circuiting: once an input expression evaluates to `false`, the result is `false` and no further input expressions are evaluated.
 */
fun all(block: Expression.ExpressionBuilder.() -> Unit) = Expression.all(block)

/**
 * Returns `true` if any of the inputs are `true`, `false` otherwise. The inputs are evaluated in order, and evaluation is short-circuiting: once an input expression evaluates to `true`, the result is `true` and no further input expressions are evaluated.
 */
fun any(block: Expression.ExpressionBuilder.() -> Unit) = Expression.any(block)

/**
 * Asserts that the input is an array (optionally with a specific item type and length).  If, when the input expression is evaluated, it is not of the asserted type, then this assertion will cause the whole expression to be aborted.
 */
fun array(block: Expression.ExpressionBuilder.() -> Unit) = Expression.array(block)

/**
 * Returns the arcsine of the input.
 */
fun asin(block: Expression.ExpressionBuilder.() -> Unit) = Expression.asin(block)

/**
 * Retrieves an item from an array.
 */
fun at(block: Expression.ExpressionBuilder.() -> Unit) = Expression.at(block)

/**
 * Returns the arctangent of the input.
 */
fun atan(block: Expression.ExpressionBuilder.() -> Unit) = Expression.atan(block)

/**
 * Asserts that the input value is a boolean. If multiple values are provided, each one is evaluated in order until a boolean is obtained. If none of the inputs are booleans, the expression is an error.
 */
fun boolean(block: Expression.ExpressionBuilder.() -> Unit) = Expression.boolean(block)

/**
 * Selects the first output whose corresponding test condition evaluates to true, or the fallback value otherwise.
 */
fun switchCase(block: Expression.ExpressionBuilder.() -> Unit) = Expression.switchCase(block)

/**
 * Returns the smallest integer that is greater than or equal to the input.
 */
fun ceil(block: Expression.ExpressionBuilder.() -> Unit) = Expression.ceil(block)

/**
 * Evaluates each expression in turn until the first valid value is obtained. Invalid values are `null` and [`'image'`](#types-image) expressions that are unavailable in the style. If all values are invalid, `coalesce` returns the first value listed.
 */
fun coalesce(block: Expression.ExpressionBuilder.() -> Unit) = Expression.coalesce(block)

/**
 * Returns a `collator` for use in locale-dependent comparison operations. The `case-sensitive` and `diacritic-sensitive` options default to `false`. The `locale` argument specifies the IETF language tag of the locale to use. If none is provided, the default locale is used. If the requested locale is not available, the `collator` will use a system-defined fallback locale. Use `resolved-locale` to test the results of locale fallback behavior.
 */
fun collator(block: Expression.CollatorBuilder.() -> Unit) = Expression.collator(block)

/**
 * Returns a `string` consisting of the concatenation of the inputs. Each input is converted to a string as if by `to-string`.
 */
fun concat(block: Expression.ExpressionBuilder.() -> Unit) = Expression.concat(block)

/**
 * Returns the cosine of the input.
 */
fun cos(block: Expression.ExpressionBuilder.() -> Unit) = Expression.cos(block)
/**
 * Returns the shortest distance in meters between the evaluated feature and the input geometry. The input value can be a valid GeoJSON of type `Point`, `MultiPoint`, `LineString`, `MultiLineString`, `Polygon`, `MultiPolygon`, `Feature`, or `FeatureCollection`. Distance values returned may vary in precision due to loss in precision from encoding geometries, particularly below zoom level 13.
 */
fun distance(geojson: GeoJson) = Expression.distance(geojson)

/**
 * Returns the distance of a `symbol` instance from the center of the map. The distance is measured in pixels divided by the height of the map container. It measures 0 at the center, decreases towards the camera and increase away from the camera. For example, if the height of the map is 1000px, a value of -1 means 1000px away from the center towards the camera, and a value of 1 means a distance of 1000px away from the camera from the center. `["distance-from-center"]` may only be used in the `filter` expression for a `symbol` layer.
 */
fun distanceFromCenter() = Expression.distanceFromCenter()

/**
 * Returns the input string converted to lowercase. Follows the Unicode Default Case Conversion algorithm and the locale-insensitive case mappings in the Unicode Character Database.
 */
fun downcase(block: Expression.ExpressionBuilder.() -> Unit) = Expression.downcase(block)

/**
 * Returns the mathematical constant e.
 */
fun e() = Expression.e()

/**
 * Retrieves a property value from the current feature's state. Returns `null` if the requested property is not present on the feature's state. A feature's state is not part of the GeoJSON or vector tile data, and must be set programmatically on each feature. Features are identified by their `id` attribute, which must be an integer or a string that can be cast to an integer. Note that ["feature-state"] can only be used with paint properties that support data-driven styling.
 */
fun featureState(block: Expression.ExpressionBuilder.() -> Unit) = Expression.featureState(block)

/**
 * Returns the largest integer that is less than or equal to the input.
 */
fun floor(block: Expression.ExpressionBuilder.() -> Unit) = Expression.floor(block)

/**
 * Returns a `formatted` string for displaying mixed-format text in the `text-field` property. The input may contain a string literal or expression, including an [`'image'`](#types-image) expression. Strings may be followed by a style override object that supports the following properties:
- `"text-font"`: Overrides the font stack specified by the root layout property.
- `"text-color"`: Overrides the color specified by the root paint property.
- `"font-scale"`: Applies a scaling factor on `text-size` as specified by the root layout property.
 */
fun format(block: Expression.FormatBuilder.() -> Unit) = Expression.format(block)

/**
 * Returns the feature's geometry type: `Point`, `LineString` or `Polygon`. `Multi*` feature types return the singular forms.
 */
fun geometryType() = Expression.geometryType()

/**
 * Retrieves a property value from the current feature's properties, or from another object if a second argument is provided. Returns `null` if the requested property is missing.
 */
fun get(block: Expression.ExpressionBuilder.() -> Unit) = Expression.get(block)

/**
 * Tests for the presence of an property value in the current feature's properties, or from another object if a second argument is provided.
 */
fun has(block: Expression.ExpressionBuilder.() -> Unit) = Expression.has(block)

/**
 * Returns the kernel density estimation of a pixel in a heatmap layer, which is a relative measure of how many data points are crowded around a particular pixel. Can only be used in the `heatmap-color` property.
 */
fun heatmapDensity() = Expression.heatmapDensity()

/**
 * Returns the feature's id, if it has one.
 */
fun id() = Expression.id()

/**
 * Returns a [`ResolvedImage`](/mapbox-gl-js/style-spec/types/#resolvedimage) for use in [`icon-image`](/mapbox-gl-js/style-spec/layers/#layout-symbol-icon-image), `*-pattern` entries, and as a section in the [`'format'`](#types-format) expression. A [`'coalesce'`](#coalesce) expression containing `image` expressions will evaluate to the first listed image that is currently in the style. This validation process is synchronous and requires the image to have been added to the style before requesting it in the `'image'` argument.
 */
fun image(block: Expression.ExpressionBuilder.() -> Unit) = Expression.image(block)

/**
 * Determines whether an item exists in an array or a substring exists in a string. In the specific case when the second and third arguments are string literals, you must wrap at least one of them in a [`literal`](#types-literal) expression to hint correct interpretation to the [type system](#type-system).
 */
fun inExpression(block: Expression.ExpressionBuilder.() -> Unit) = Expression.inExpression(block)

/**
 * Returns the first position at which an item can be found in an array or a substring can be found in a string, or `-1` if the input cannot be found. Accepts an optional index from where to begin the search.
 */
fun indexOf(block: Expression.ExpressionBuilder.() -> Unit) = Expression.indexOf(block)

/**
 * Produces continuous, smooth results by interpolating between pairs of input and output values ("stops"). The `input` may be any numeric expression (e.g., `["get", "population"]`). Stop inputs must be numeric literals in strictly ascending order. The output type must be `number`, `array<number>`, or `color`.

Interpolation types:
- `["linear"]`: Interpolates linearly between the pair of stops just less than and just greater than the input.
- `["exponential", base]`: Interpolates exponentially between the stops just less than and just greater than the input. `base` controls the rate at which the output increases: higher values make the output increase more towards the high end of the range. With values close to 1 the output increases linearly.
- `["cubic-bezier", x1, y1, x2, y2]`: Interpolates using the cubic bezier curve defined by the given control points.
 */
fun interpolate(block: Expression.InterpolatorBuilder.() -> Unit) = Expression.interpolate(block)

/**
 * Returns `true` if the input string is expected to render legibly. Returns `false` if the input string contains sections that cannot be rendered without potential loss of meaning (e.g. Indic scripts that require complex text shaping, or right-to-left scripts if the the `mapbox-gl-rtl-text` plugin is not in use in Mapbox GL JS).
 */
fun isSupportedScript(block: Expression.ExpressionBuilder.() -> Unit) = Expression.isSupportedScript(block)

/**
 * Returns the length of an array or string.
 */
fun length(block: Expression.ExpressionBuilder.() -> Unit) = Expression.length(block)

/**
 * Binds expressions to named variables, which can then be referenced in the result expression using ["var", "variable_name"].
 */
fun letExpression(block: Expression.ExpressionBuilder.() -> Unit) = Expression.letExpression(block)

/**
 * Returns the progress along a gradient line. Can only be used in the `line-gradient` property.
 */
fun lineProgress() = Expression.lineProgress()

/**
 * Provides a literal array or object value.
 */
fun literal(value: Double) = Expression.literal(value)

/**
 * Provides a literal array or object value.
 */
fun literal(value: Long) = Expression.literal(value)

/**
 * Provides a literal array or object value.
 */
fun literal(value: Boolean) = Expression.literal(value)

/**
 * Provides a literal array or object value.
 */
fun literal(value: String) = Expression.literal(value)

/**
 * Provides a literal array or object value.
 */
fun literal(value: List<Any>) = Expression.literal(value)

/**
 * Provides a literal array or object value.
 */
fun literal(value: HashMap<String, Any>) = Expression.literal(value)

/**
 * Returns the natural logarithm of the input.
 */
fun ln(block: Expression.ExpressionBuilder.() -> Unit) = Expression.ln(block)

/**
 * Returns mathematical constant ln(2).
 */
fun ln2() = Expression.ln2()

/**
 * Returns the base-ten logarithm of the input.
 */
fun log10(block: Expression.ExpressionBuilder.() -> Unit) = Expression.log10(block)

/**
 * Returns the base-two logarithm of the input.
 */
fun log2(block: Expression.ExpressionBuilder.() -> Unit) = Expression.log2(block)

/**
 * Selects the output for which the label value matches the input value, or the fallback value if no match is found. The input can be any expression (for example, `["get", "building_type"]`). Each label must be unique, and must be either:
 - a single literal value; or
 - an array of literal values, the values of which must be all strings or all numbers (for example `[100, 101]` or `["c", "b"]`).

The input matches if any of the values in the array matches using strict equality, similar to the `"in"` operator.
If the input type does not match the type of the labels, the result will be the fallback value.
 */
fun match(block: Expression.ExpressionBuilder.() -> Unit) = Expression.match(block)

/**
 * Returns the maximum value of the inputs.
 */
fun max(block: Expression.ExpressionBuilder.() -> Unit) = Expression.max(block)

/**
 * Returns the minimum value of the inputs.
 */
fun min(block: Expression.ExpressionBuilder.() -> Unit) = Expression.min(block)

/**
 * Asserts that the input value is a number. If multiple values are provided, each one is evaluated in order until a number is obtained. If none of the inputs are numbers, the expression is an error.
 */
fun number(block: Expression.ExpressionBuilder.() -> Unit) = Expression.number(block)

/**
 * Converts the input number into a string representation using the providing formatting rules. If set, the `locale` argument specifies the locale to use, as a BCP 47 language tag. If set, the `currency` argument specifies an ISO 4217 code to use for currency-style formatting. If set, the `unit` argument specifies a [simple ECMAScript unit](https://tc39.es/proposal-unified-intl-numberformat/section6/locales-currencies-tz_proposed_out.html#sec-issanctionedsimpleunitidentifier) to use for unit-style formatting. If set, the `min-fraction-digits` and `max-fraction-digits` arguments specify the minimum and maximum number of fractional digits to include.
 */
fun numberFormat(input: Expression, block: Expression.NumberFormatBuilder.() -> Unit) = Expression.numberFormat(input, block)

/**
 * Asserts that the input value is an object. If multiple values are provided, each one is evaluated in order until an object is obtained. If none of the inputs are objects, the expression is an error.
 */
fun objectExpression(block: Expression.ExpressionBuilder.() -> Unit) = Expression.objectExpression(block)

/**
 * Returns the mathematical constant pi.
 */
fun pi() = Expression.pi()

/**
 * Returns the current pitch in degrees. `["pitch"]` may only be used in the `filter` expression for a `symbol` layer.
 */
fun pitch() = Expression.pitch()

/**
 * Returns the feature properties object.  Note that in some cases, it may be more efficient to use `["get", "property_name"]` directly.
 */
fun properties() = Expression.properties()

/**
 * Returns the IETF language tag of the locale being used by the provided `collator`. This can be used to determine the default system locale, or to determine if a requested locale was successfully loaded.
 */
fun resolvedLocale(block: Expression.ExpressionBuilder.() -> Unit) = Expression.resolvedLocale(block)

/**
 * Creates a color value from red, green, and blue components, which must range between 0 and 255, and an alpha component of 1. If any component is out of range, the expression is an error.
 */
fun rgb(block: Expression.ExpressionBuilder.() -> Unit) = Expression.rgb(block)

/**
 * Creates a color value from red, green, blue components, which must range between 0 and 255, and an alpha component which must range between 0 and 1. If any component is out of range, the expression is an error.
 */
fun rgba(block: Expression.ExpressionBuilder.() -> Unit) = Expression.rgba(block)

/**
 * Rounds the input to the nearest integer. Halfway values are rounded away from zero. For example, `["round", -1.5]` evaluates to -2.
 */
fun round(block: Expression.ExpressionBuilder.() -> Unit) = Expression.round(block)

/**
 * Returns the sine of the input.
 */
fun sin(block: Expression.ExpressionBuilder.() -> Unit) = Expression.sin(block)

/**
 * Returns the distance of a point on the sky from the sun position. Returns 0 at sun position and 1 when the distance reaches `sky-gradient-radius`. Can only be used in the `sky-gradient` property.
 */
fun skyRadialProgress() = Expression.skyRadialProgress()

/**
 * Returns an item from an array or a substring from a string from a specified start index, or between a start index and an end index if set. The return value is inclusive of the start index but not of the end index.
 */
fun slice(block: Expression.ExpressionBuilder.() -> Unit) = Expression.slice(block)

/**
 * Returns the square root of the input.
 */
fun sqrt(block: Expression.ExpressionBuilder.() -> Unit) = Expression.sqrt(block)

/**
 * Produces discrete, stepped results by evaluating a piecewise-constant function defined by pairs of input and output values ("stops"). The `input` may be any numeric expression (e.g., `["get", "population"]`). Stop inputs must be numeric literals in strictly ascending order. Returns the output value of the stop just less than the input, or the first output if the input is less than the first stop.
 */
fun step(block: Expression.ExpressionBuilder.() -> Unit) = Expression.step(block)

/**
 * Asserts that the input value is a string. If multiple values are provided, each one is evaluated in order until a string is obtained. If none of the inputs are strings, the expression is an error.
 */
fun string(block: Expression.ExpressionBuilder.() -> Unit) = Expression.string(block)

/**
 * Returns the tangent of the input.
 */
fun tan(block: Expression.ExpressionBuilder.() -> Unit) = Expression.tan(block)

/**
 * Converts the input value to a boolean. The result is `false` when then input is an empty string, 0, `false`, `null`, or `NaN`; otherwise it is `true`.
 */
fun toBoolean(block: Expression.ExpressionBuilder.() -> Unit) = Expression.toBoolean(block)

/**
 * Converts the input value to a color. If multiple values are provided, each one is evaluated in order until the first successful conversion is obtained. If none of the inputs can be converted, the expression is an error.
 */
fun toColor(block: Expression.ExpressionBuilder.() -> Unit) = Expression.toColor(block)

/**
 * Converts the input value to a number, if possible. If the input is `null` or `false`, the result is 0. If the input is `true`, the result is 1. If the input is a string, it is converted to a number as specified by the ["ToNumber Applied to the String Type" algorithm](https://tc39.github.io/ecma262/#sec-tonumber-applied-to-the-string-type) of the ECMAScript Language Specification. If multiple values are provided, each one is evaluated in order until the first successful conversion is obtained. If none of the inputs can be converted, the expression is an error.
 */
fun toNumber(block: Expression.ExpressionBuilder.() -> Unit) = Expression.toNumber(block)

/**
 * Returns a four-element array containing the input color's red, green, blue, and alpha components, in that order.
 */
fun toRgba(block: Expression.ExpressionBuilder.() -> Unit) = Expression.toRgba(block)

/**
 * Converts the input value to a string. If the input is `null`, the result is `""`. If the input is a [`boolean`](#types-boolean), the result is `"true"` or `"false"`. If the input is a number, it is converted to a string as specified by the ["NumberToString" algorithm](https://tc39.github.io/ecma262/#sec-tostring-applied-to-the-number-type) of the ECMAScript Language Specification. If the input is a [`color`](#color), it is converted to a string of the form `"rgba(r,g,b,a)"`, where `r`, `g`, and `b` are numerals ranging from 0 to 255, and `a` ranges from 0 to 1. If the input is an [`'image'`](#types-image) expression, `'to-string'` returns the image name. Otherwise, the input is converted to a string in the format specified by the [`JSON.stringify`](https://tc39.github.io/ecma262/#sec-json.stringify) function of the ECMAScript Language Specification.
 */
fun toString(block: Expression.ExpressionBuilder.() -> Unit) = Expression.toString(block)

/**
 * Returns a string describing the type of the given value.
 */
fun typeofExpression(block: Expression.ExpressionBuilder.() -> Unit) = Expression.typeofExpression(block)

/**
 * Returns the input string converted to uppercase. Follows the Unicode Default Case Conversion algorithm and the locale-insensitive case mappings in the Unicode Character Database.
 */
fun upcase(block: Expression.ExpressionBuilder.() -> Unit) = Expression.upcase(block)

/**
 * References variable bound using "let".
 */
fun varExpression(block: Expression.ExpressionBuilder.() -> Unit) = Expression.varExpression(block)
/**
 * Returns `true` if the evaluated feature is fully contained inside a boundary of the input geometry, `false` otherwise. The input value can be a valid GeoJSON of type `Polygon`, `MultiPolygon`, `Feature`, or `FeatureCollection`. Supported features for evaluation:
- `Point`: Returns `false` if a point is on the boundary or falls outside the boundary.
- `LineString`: Returns `false` if any part of a line falls outside the boundary, the line intersects the boundary, or a line's endpoint is on the boundary.
 */
fun within(geometry: Geometry) = Expression.within(geometry)

/**
 * Returns the current zoom level.  Note that in style layout and paint properties, ["zoom"] may only appear as the input to a top-level "step" or "interpolate" expression.
 */
fun zoom() = Expression.zoom()

/**
 * Construct a RGB color expression from numbers.
 */
fun rgb(red: Double, green: Double, blue: Double) = Expression.rgb(red, green, blue)

/**
 * Construct a RGBA color expression from numbers.
 */
fun rgba(red: Double, green: Double, blue: Double, alpha: Double) = Expression.rgba(red, green, blue, alpha)

/**
 * Convert a color int to the rgba expression.
 */
fun color(@ColorInt intColor: Int) = Expression.color(intColor)

/**
 * Retrieves a property value from the current feature's properties.
 * Returns null if the requested property is missing.
 */
fun get(key: String) = Expression.get(key)

/**
 * Retrieves a property value from the current feature's properties, or from another object if a second
 * argument is provided. Returns null if the requested property is missing.
 */
fun get(key: String, expression: Expression) = Expression.get(key, expression)

/**
 * Logical negation. Returns `true` if the input is `false`, and `false` if the input is `true`.
 */
fun not(bool: Boolean) = Expression.not(bool)

/**
 * Retrieves an item from an array.
 */
fun at(index: Double, array: Expression) = Expression.at(index, array)

/**
 * Determines whether an item exists in an array or a substring exists in a string.
 */
fun inExpression(needle: String, haystack: Expression) = Expression.inExpression(needle, haystack)

/**
 * Determines whether an item exists in an array or a substring exists in a string.
 */
fun inExpression(needle: Double, haystack: Expression) = Expression.inExpression(needle, haystack)

/**
 * Tests for the presence of an property value in the current feature's properties, or from another
 * object if a second argument is provided.
 */
fun has(string: String, expression: Expression) = Expression.has(string, expression)

/**
 * Tests for the presence of an property value in the current feature's properties
 */
fun has(string: String) = Expression.has(string)

/**
 * Gets the length of an string.
 */
fun length(string: String) = Expression.length(string)

/**
 * Returns the sum of the inputs.
 */
fun sum(vararg double: Double) = Expression.sum(*double)

/**
 * Returns the product of the inputs.
 */
fun product(vararg double: Double) = Expression.product(*double)

/**
 * Returns the result of subtracting the second input from the first.
 */
fun subtract(first: Double, second: Double) = Expression.subtract(first, second)

/**
 * Returns the result of subtracting it from 0.
 */
fun subtract(value: Double) = Expression.subtract(value)

/**
 * Returns the result of floating point division of the first input by the second.
 */
fun division(first: Double, second: Double) = Expression.division(first, second)

/**
 * Returns the remainder after integer division of the first input by the second.
 */
fun mod(first: Double, second: Double) = Expression.mod(first, second)

/**
 * Returns the result of raising the first input to the power specified by the second.
 */
fun pow(first: Double, second: Double) = Expression.pow(first, second)

/**
 * Returns the square root of the input.
 */
fun sqrt(value: Double) = Expression.sqrt(value)

/**
 * Returns the base-ten logarithm of the input.
 */
fun log10(value: Double) = Expression.log10(value)

/**
 * Returns the natural logarithm of the input.
 */
fun ln(value: Double) = Expression.ln(value)

/**
 * Returns the base-two logarithm of the input.
 */
fun log2(value: Double) = Expression.log2(value)

/**
 * Returns the sine of the input.
 */
fun sin(value: Double) = Expression.sin(value)

/**
 * Returns the cosine of the input.
 */
fun cos(value: Double) = Expression.cos(value)

/**
 * Returns the tangent of the input.
 */
fun tan(value: Double) = Expression.tan(value)

/**
 * Returns the arcsine of the input.
 */
fun asin(value: Double) = Expression.asin(value)

/**
 * Returns the arccosine of the input.
 */
fun acos(value: Double) = Expression.acos(value)

/**
 * Returns the arctangent of the input.
 */
fun atan(value: Double) = Expression.atan(value)

/**
 * Returns the minimum value of the inputs.
 */
fun min(vararg values: Double) = Expression.min(*values)

/**
 * Returns the maximum value of the inputs.
 */
fun max(vararg values: Double) = Expression.max(*values)

/**
 * Rounds the input to the nearest integer. Halfway values are rounded away from zero. For example,
 * `["round", -1.5]` evaluates to -2.
 */
fun round(value: Double) = Expression.round(value)

/**
 * Returns the absolute value of the input.
 */
fun abs(value: Double) = Expression.abs(value)

/**
 * Returns the smallest integer that is greater than or equal to the input.
 */
fun ceil(value: Double) = Expression.ceil(value)

/**
 * Returns the largest integer that is less than or equal to the input.
 */
fun floor(value: Double) = Expression.floor(value)

/**
 * Returns `true` if the input string is expected to render legibly. Returns `false` if the input
 * string contains sections that cannot be rendered without potential loss of meaning (e.g. Indic scripts that
 * require complex text shaping, or right-to-left scripts if the the `mapbox-gl-rtl-text` plugin is not in use
 * in Mapbox GL JS).
 */
fun isSupportedScript(script: String) = Expression.isSupportedScript(script)

/**
 * Returns the input string converted to uppercase. Follows the Unicode Default Case Conversion algorithm and the
 * locale-insensitive case mappings in the Unicode Character Database.
 */
fun upcase(value: String) = Expression.upcase(value)

/**
 * Returns the input string converted to lowercase. Follows the Unicode Default Case Conversion algorithm and the
 * locale-insensitive case mappings in the Unicode Character Database.
 */
fun downcase(value: String) = Expression.downcase(value)

/**
 * Returns a `string` consisting of the concatenation of the inputs. Each input is converted to a
 * string as if by `to-string`.
 */
fun concat(vararg values: String) = Expression.concat(*values)

/**
 * References variable bound using "let".
 */
fun varExpression(value: String) = Expression.varExpression(value)

// End of generated file.