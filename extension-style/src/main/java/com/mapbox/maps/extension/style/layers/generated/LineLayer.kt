// This file is generated.

package com.mapbox.maps.extension.style.layers.generated

import androidx.annotation.ColorInt
import androidx.annotation.UiThread
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.Layer
import com.mapbox.maps.extension.style.layers.properties.*
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.types.*
import com.mapbox.maps.extension.style.utils.ColorUtils.colorIntToRgbaExpression
import com.mapbox.maps.extension.style.utils.ColorUtils.rgbaExpressionToColorInt
import com.mapbox.maps.extension.style.utils.ColorUtils.rgbaExpressionToColorString
import com.mapbox.maps.extension.style.utils.silentUnwrap
import java.util.*

/**
 * A stroked line.
 *
 * @see <a href="https://www.mapbox.com/mapbox-gl-style-spec/#layers-line">The online documentation</a>
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
 */
@UiThread
class LineLayer(override val layerId: String, val sourceId: String) : LineLayerDsl, Layer() {
  init {
    internalSourceId = sourceId
  }

  /**
   * Set the sourceLayer property
   *
   * @param sourceLayer value of sourceLayer
   */
  override fun sourceLayer(sourceLayer: String) = apply {
    val param = PropertyValue("source-layer", sourceLayer)
    setProperty(param)
  }

  /**
   * Source layer.
   */
  val sourceLayer: String?
    /**
     * Get the sourceLayer property
     *
     * @return sourceLayer
     */
    get() {
      return getPropertyValue("source-layer")
    }

  /**
   * Set the filter property
   *
   * @param filter the expression filter to set
   */
  override fun filter(filter: Expression) = apply {
    val propertyValue = PropertyValue("filter", filter)
    setProperty(propertyValue)
  }

  /**
   * A expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `feature-state` expression is not supported in filter expressions.
   */
  val filter: Expression?
    /**
     * Get the filter property
     *
     * @return filter
     */
    get() = getPropertyValue<Expression>("filter")

  /**
   * Visibility of the layer.
   */
  override val visibility: Visibility?
    /**
     * Get the Visibility property
     *
     * @return VISIBILITY
     */
    get() {
      val property: String? = getPropertyValue("visibility")
      property?.let {
        return Visibility.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Set the Visibility property
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Visibility) = apply {
    val propertyValue = PropertyValue("visibility", visibility)
    setProperty(propertyValue)
  }

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   */
  override val minZoom: Double?
    /**
     * Get the minzoom property
     *
     * @return minzoom
     */
    get() {
      return getPropertyValue("minzoom")
    }

  /**
   * Set the minzoom property
   *
   * @param value value of minzoom
   */
  override fun minZoom(minZoom: Double) = apply {
    val param = PropertyValue("minzoom", minZoom)
    setProperty(param)
  }

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   */
  override val maxZoom: Double?
    /**
     * Get the maxzoom property
     *
     * @return maxzoom
     */
    get() {
      return getPropertyValue("maxzoom")
    }

  /**
   * Set the maxzoom property
   *
   * @param value value of maxzoom
   */
  override fun maxZoom(maxZoom: Double) = apply {
    val param = PropertyValue("maxzoom", maxZoom)
    setProperty(param)
  }

  // Property getters and setters

  /**
   * The display of line endings.
   */
  val lineCap: LineCap?
    /**
     * Get the LineCap property
     *
     * @return LineCap
     */
    get() {
      getPropertyValue<String?>("line-cap")?.let {
        return LineCap.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Set the LineCap property
   *
   * @param lineCap value of lineCap
   */
  override fun lineCap(lineCap: LineCap) = apply {
    val propertyValue = PropertyValue("line-cap", lineCap)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "line-cap".
   *
   * The display of line endings.
   */
  val lineCapAsExpression: Expression?
    /**
     * Get the LineCap property as an Expression
     *
     * @return LineCap
     */
    get() {
      getPropertyValue<Expression>("line-cap")?.let {
        return it
      }
      lineCap?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Set the LineCap property
   *
   * @param lineCap value of lineCap as Expression
   */
  override fun lineCap(lineCap: Expression) = apply {
    val propertyValue = PropertyValue("line-cap", lineCap)
    setProperty(propertyValue)
  }

  /**
   * The display of lines when joining.
   */
  val lineJoin: LineJoin?
    /**
     * Get the LineJoin property
     *
     * @return LineJoin
     */
    get() {
      getPropertyValue<String?>("line-join")?.let {
        return LineJoin.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Set the LineJoin property
   *
   * @param lineJoin value of lineJoin
   */
  override fun lineJoin(lineJoin: LineJoin) = apply {
    val propertyValue = PropertyValue("line-join", lineJoin)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "line-join".
   *
   * The display of lines when joining.
   */
  val lineJoinAsExpression: Expression?
    /**
     * Get the LineJoin property as an Expression
     *
     * @return LineJoin
     */
    get() {
      getPropertyValue<Expression>("line-join")?.let {
        return it
      }
      lineJoin?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Set the LineJoin property
   *
   * @param lineJoin value of lineJoin as Expression
   */
  override fun lineJoin(lineJoin: Expression) = apply {
    val propertyValue = PropertyValue("line-join", lineJoin)
    setProperty(propertyValue)
  }

  /**
   * Used to automatically convert miter joins to bevel joins for sharp angles.
   */
  val lineMiterLimit: Double?
    /**
     * Get the LineMiterLimit property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-miter-limit")
    }

  /**
   * Set the LineMiterLimit property
   *
   * @param lineMiterLimit value of lineMiterLimit
   */
  override fun lineMiterLimit(lineMiterLimit: Double) = apply {
    val propertyValue = PropertyValue("line-miter-limit", lineMiterLimit)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "line-miter-limit".
   *
   * Used to automatically convert miter joins to bevel joins for sharp angles.
   */
  val lineMiterLimitAsExpression: Expression?
    /**
     * Get the LineMiterLimit property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("line-miter-limit")?.let {
        return it
      }
      lineMiterLimit?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the LineMiterLimit property
   *
   * @param lineMiterLimit value of lineMiterLimit as Expression
   */
  override fun lineMiterLimit(lineMiterLimit: Expression) = apply {
    val propertyValue = PropertyValue("line-miter-limit", lineMiterLimit)
    setProperty(propertyValue)
  }

  /**
   * Used to automatically convert round joins to miter joins for shallow angles.
   */
  val lineRoundLimit: Double?
    /**
     * Get the LineRoundLimit property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-round-limit")
    }

  /**
   * Set the LineRoundLimit property
   *
   * @param lineRoundLimit value of lineRoundLimit
   */
  override fun lineRoundLimit(lineRoundLimit: Double) = apply {
    val propertyValue = PropertyValue("line-round-limit", lineRoundLimit)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "line-round-limit".
   *
   * Used to automatically convert round joins to miter joins for shallow angles.
   */
  val lineRoundLimitAsExpression: Expression?
    /**
     * Get the LineRoundLimit property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("line-round-limit")?.let {
        return it
      }
      lineRoundLimit?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the LineRoundLimit property
   *
   * @param lineRoundLimit value of lineRoundLimit as Expression
   */
  override fun lineRoundLimit(lineRoundLimit: Expression) = apply {
    val propertyValue = PropertyValue("line-round-limit", lineRoundLimit)
    setProperty(propertyValue)
  }

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  val lineSortKey: Double?
    /**
     * Get the LineSortKey property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-sort-key")
    }

  /**
   * Set the LineSortKey property
   *
   * @param lineSortKey value of lineSortKey
   */
  override fun lineSortKey(lineSortKey: Double) = apply {
    val propertyValue = PropertyValue("line-sort-key", lineSortKey)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "line-sort-key".
   *
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  val lineSortKeyAsExpression: Expression?
    /**
     * Get the LineSortKey property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("line-sort-key")?.let {
        return it
      }
      lineSortKey?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the LineSortKey property
   *
   * @param lineSortKey value of lineSortKey as Expression
   */
  override fun lineSortKey(lineSortKey: Expression) = apply {
    val propertyValue = PropertyValue("line-sort-key", lineSortKey)
    setProperty(propertyValue)
  }

  /**
   * Blur applied to the line, in pixels.
   */
  val lineBlur: Double?
    /**
     * Get the LineBlur property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-blur")
    }

  /**
   * Set the LineBlur property
   *
   * @param lineBlur value of lineBlur
   */
  override fun lineBlur(lineBlur: Double) = apply {
    val propertyValue = PropertyValue("line-blur", lineBlur)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "line-blur".
   *
   * Blur applied to the line, in pixels.
   */
  val lineBlurAsExpression: Expression?
    /**
     * Get the LineBlur property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("line-blur")?.let {
        return it
      }
      lineBlur?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the LineBlur property
   *
   * @param lineBlur value of lineBlur as Expression
   */
  override fun lineBlur(lineBlur: Expression) = apply {
    val propertyValue = PropertyValue("line-blur", lineBlur)
    setProperty(propertyValue)
  }

  /**
   * Transition options for LineBlur.
   */
  val lineBlurTransition: StyleTransition?
    /**
     * Get the LineBlur property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("line-blur-transition")
    }

  /**
   * Set the LineBlur property transition options
   *
   * @param options transition options for Double
   */
  override fun lineBlurTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("line-blur-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineBlurTransition].
   */
  override fun lineBlurTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    lineBlurTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The color with which the line will be drawn.
   */
  val lineColor: String?
    /**
     * Get the LineColor property
     *
     * @return String
     */
    get() {
      lineColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * Set the LineColor property
   *
   * @param lineColor value of lineColor
   */
  override fun lineColor(lineColor: String) = apply {
    val propertyValue = PropertyValue("line-color", lineColor)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "line-color".
   *
   * The color with which the line will be drawn.
   */
  val lineColorAsExpression: Expression?
    /**
     * Get the LineColor property as an Expression
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("line-color")?.let {
        return it
      }
      return null
    }

  /**
   * Set the LineColor property
   *
   * @param lineColor value of lineColor as Expression
   */
  override fun lineColor(lineColor: Expression) = apply {
    val propertyValue = PropertyValue("line-color", lineColor)
    setProperty(propertyValue)
  }

  /**
   * The color with which the line will be drawn.
   */
  val lineColorAsColorInt: Int?
    /**
     * The color with which the line will be drawn.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      lineColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * Set the LineColor property.
   *
   * @param lineColor value of lineColor
   */
  override fun lineColor(@ColorInt lineColor: Int) = apply {
    val propertyValue = PropertyValue("line-color", colorIntToRgbaExpression(lineColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for LineColor.
   */
  val lineColorTransition: StyleTransition?
    /**
     * Get the LineColor property transition options
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("line-color-transition")
    }

  /**
   * Set the LineColor property transition options
   *
   * @param options transition options for String
   */
  override fun lineColorTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("line-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineColorTransition].
   */
  override fun lineColorTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    lineColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  val lineDasharray: List<Double>?
    /**
     * Get the LineDasharray property
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("line-dasharray")
    }

  /**
   * Set the LineDasharray property
   *
   * @param lineDasharray value of lineDasharray
   */
  override fun lineDasharray(lineDasharray: List<Double>) = apply {
    val propertyValue = PropertyValue("line-dasharray", lineDasharray)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "line-dasharray".
   *
   * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  val lineDasharrayAsExpression: Expression?
    /**
     * Get the LineDasharray property as an Expression
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("line-dasharray")?.let {
        return it
      }
      lineDasharray?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the LineDasharray property
   *
   * @param lineDasharray value of lineDasharray as Expression
   */
  override fun lineDasharray(lineDasharray: Expression) = apply {
    val propertyValue = PropertyValue("line-dasharray", lineDasharray)
    setProperty(propertyValue)
  }

  /**
   * Transition options for LineDasharray.
   */
  val lineDasharrayTransition: StyleTransition?
    /**
     * Get the LineDasharray property transition options
     *
     * @return transition options for List<Double>
     */
    get() {
      return getPropertyValue("line-dasharray-transition")
    }

  /**
   * Set the LineDasharray property transition options
   *
   * @param options transition options for List<Double>
   */
  override fun lineDasharrayTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("line-dasharray-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineDasharrayTransition].
   */
  override fun lineDasharrayTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    lineDasharrayTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
   */
  val lineGapWidth: Double?
    /**
     * Get the LineGapWidth property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-gap-width")
    }

  /**
   * Set the LineGapWidth property
   *
   * @param lineGapWidth value of lineGapWidth
   */
  override fun lineGapWidth(lineGapWidth: Double) = apply {
    val propertyValue = PropertyValue("line-gap-width", lineGapWidth)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "line-gap-width".
   *
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
   */
  val lineGapWidthAsExpression: Expression?
    /**
     * Get the LineGapWidth property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("line-gap-width")?.let {
        return it
      }
      lineGapWidth?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the LineGapWidth property
   *
   * @param lineGapWidth value of lineGapWidth as Expression
   */
  override fun lineGapWidth(lineGapWidth: Expression) = apply {
    val propertyValue = PropertyValue("line-gap-width", lineGapWidth)
    setProperty(propertyValue)
  }

  /**
   * Transition options for LineGapWidth.
   */
  val lineGapWidthTransition: StyleTransition?
    /**
     * Get the LineGapWidth property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("line-gap-width-transition")
    }

  /**
   * Set the LineGapWidth property transition options
   *
   * @param options transition options for Double
   */
  override fun lineGapWidthTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("line-gap-width-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineGapWidthTransition].
   */
  override fun lineGapWidthTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    lineGapWidthTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Defines a gradient with which to color a line feature. Can only be used with GeoJSON sources that specify `"lineMetrics": true`.
   */
  val lineGradient: Expression?
    /**
     * Get the LineGradient property
     *
     * @return Expression
     */
    get() {
      return getPropertyValue("line-gradient")
    }

  /**
   * Set the LineGradient property
   *
   * @param lineGradient value of lineGradient
   */
  override fun lineGradient(lineGradient: Expression) = apply {
    val propertyValue = PropertyValue("line-gradient", lineGradient)
    setProperty(propertyValue)
  }

  /**
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
   */
  val lineOffset: Double?
    /**
     * Get the LineOffset property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-offset")
    }

  /**
   * Set the LineOffset property
   *
   * @param lineOffset value of lineOffset
   */
  override fun lineOffset(lineOffset: Double) = apply {
    val propertyValue = PropertyValue("line-offset", lineOffset)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "line-offset".
   *
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
   */
  val lineOffsetAsExpression: Expression?
    /**
     * Get the LineOffset property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("line-offset")?.let {
        return it
      }
      lineOffset?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the LineOffset property
   *
   * @param lineOffset value of lineOffset as Expression
   */
  override fun lineOffset(lineOffset: Expression) = apply {
    val propertyValue = PropertyValue("line-offset", lineOffset)
    setProperty(propertyValue)
  }

  /**
   * Transition options for LineOffset.
   */
  val lineOffsetTransition: StyleTransition?
    /**
     * Get the LineOffset property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("line-offset-transition")
    }

  /**
   * Set the LineOffset property transition options
   *
   * @param options transition options for Double
   */
  override fun lineOffsetTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("line-offset-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineOffsetTransition].
   */
  override fun lineOffsetTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    lineOffsetTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity at which the line will be drawn.
   */
  val lineOpacity: Double?
    /**
     * Get the LineOpacity property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-opacity")
    }

  /**
   * Set the LineOpacity property
   *
   * @param lineOpacity value of lineOpacity
   */
  override fun lineOpacity(lineOpacity: Double) = apply {
    val propertyValue = PropertyValue("line-opacity", lineOpacity)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "line-opacity".
   *
   * The opacity at which the line will be drawn.
   */
  val lineOpacityAsExpression: Expression?
    /**
     * Get the LineOpacity property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("line-opacity")?.let {
        return it
      }
      lineOpacity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the LineOpacity property
   *
   * @param lineOpacity value of lineOpacity as Expression
   */
  override fun lineOpacity(lineOpacity: Expression) = apply {
    val propertyValue = PropertyValue("line-opacity", lineOpacity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for LineOpacity.
   */
  val lineOpacityTransition: StyleTransition?
    /**
     * Get the LineOpacity property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("line-opacity-transition")
    }

  /**
   * Set the LineOpacity property transition options
   *
   * @param options transition options for Double
   */
  override fun lineOpacityTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("line-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineOpacityTransition].
   */
  override fun lineOpacityTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    lineOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  val linePattern: String?
    /**
     * Get the LinePattern property
     *
     * @return String
     */
    get() {
      return getPropertyValue<String>("line-pattern")
    }

  /**
   * Set the LinePattern property
   *
   * @param linePattern value of linePattern
   */
  override fun linePattern(linePattern: String) = apply {
    val propertyValue = PropertyValue("line-pattern", linePattern)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "line-pattern".
   *
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  val linePatternAsExpression: Expression?
    /**
     * Get the LinePattern property as an Expression
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("line-pattern")?.let {
        return it
      }
      linePattern?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the LinePattern property
   *
   * @param linePattern value of linePattern as Expression
   */
  override fun linePattern(linePattern: Expression) = apply {
    val propertyValue = PropertyValue("line-pattern", linePattern)
    setProperty(propertyValue)
  }

  /**
   * Transition options for LinePattern.
   */
  val linePatternTransition: StyleTransition?
    /**
     * Get the LinePattern property transition options
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("line-pattern-transition")
    }

  /**
   * Set the LinePattern property transition options
   *
   * @param options transition options for String
   */
  override fun linePatternTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("line-pattern-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [linePatternTransition].
   */
  override fun linePatternTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    linePatternTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   */
  val lineTranslate: List<Double>?
    /**
     * Get the LineTranslate property
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("line-translate")
    }

  /**
   * Set the LineTranslate property
   *
   * @param lineTranslate value of lineTranslate
   */
  override fun lineTranslate(lineTranslate: List<Double>) = apply {
    val propertyValue = PropertyValue("line-translate", lineTranslate)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "line-translate".
   *
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   */
  val lineTranslateAsExpression: Expression?
    /**
     * Get the LineTranslate property as an Expression
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("line-translate")?.let {
        return it
      }
      lineTranslate?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the LineTranslate property
   *
   * @param lineTranslate value of lineTranslate as Expression
   */
  override fun lineTranslate(lineTranslate: Expression) = apply {
    val propertyValue = PropertyValue("line-translate", lineTranslate)
    setProperty(propertyValue)
  }

  /**
   * Transition options for LineTranslate.
   */
  val lineTranslateTransition: StyleTransition?
    /**
     * Get the LineTranslate property transition options
     *
     * @return transition options for List<Double>
     */
    get() {
      return getPropertyValue("line-translate-transition")
    }

  /**
   * Set the LineTranslate property transition options
   *
   * @param options transition options for List<Double>
   */
  override fun lineTranslateTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("line-translate-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineTranslateTransition].
   */
  override fun lineTranslateTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    lineTranslateTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Controls the frame of reference for `line-translate`.
   */
  val lineTranslateAnchor: LineTranslateAnchor?
    /**
     * Get the LineTranslateAnchor property
     *
     * @return LineTranslateAnchor
     */
    get() {
      getPropertyValue<String?>("line-translate-anchor")?.let {
        return LineTranslateAnchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Set the LineTranslateAnchor property
   *
   * @param lineTranslateAnchor value of lineTranslateAnchor
   */
  override fun lineTranslateAnchor(lineTranslateAnchor: LineTranslateAnchor) = apply {
    val propertyValue = PropertyValue("line-translate-anchor", lineTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "line-translate-anchor".
   *
   * Controls the frame of reference for `line-translate`.
   */
  val lineTranslateAnchorAsExpression: Expression?
    /**
     * Get the LineTranslateAnchor property as an Expression
     *
     * @return LineTranslateAnchor
     */
    get() {
      getPropertyValue<Expression>("line-translate-anchor")?.let {
        return it
      }
      lineTranslateAnchor?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Set the LineTranslateAnchor property
   *
   * @param lineTranslateAnchor value of lineTranslateAnchor as Expression
   */
  override fun lineTranslateAnchor(lineTranslateAnchor: Expression) = apply {
    val propertyValue = PropertyValue("line-translate-anchor", lineTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * Stroke thickness.
   */
  val lineWidth: Double?
    /**
     * Get the LineWidth property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-width")
    }

  /**
   * Set the LineWidth property
   *
   * @param lineWidth value of lineWidth
   */
  override fun lineWidth(lineWidth: Double) = apply {
    val propertyValue = PropertyValue("line-width", lineWidth)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "line-width".
   *
   * Stroke thickness.
   */
  val lineWidthAsExpression: Expression?
    /**
     * Get the LineWidth property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("line-width")?.let {
        return it
      }
      lineWidth?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the LineWidth property
   *
   * @param lineWidth value of lineWidth as Expression
   */
  override fun lineWidth(lineWidth: Expression) = apply {
    val propertyValue = PropertyValue("line-width", lineWidth)
    setProperty(propertyValue)
  }

  /**
   * Transition options for LineWidth.
   */
  val lineWidthTransition: StyleTransition?
    /**
     * Get the LineWidth property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("line-width-transition")
    }

  /**
   * Set the LineWidth property transition options
   *
   * @param options transition options for Double
   */
  override fun lineWidthTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("line-width-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineWidthTransition].
   */
  override fun lineWidthTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    lineWidthTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Get the type of this layer
   *
   * @return Type of the layer as [String]
   */
  override fun getType(): String {
    return "line"
  }
  /**
   * Static variables and methods.
   */
  companion object {
    // Default values for layer properties
    /**
     * Visibility of the layer.
     */
    val defaultVisibility: Visibility?
      /**
       * Get the default Visibility property
       *
       * @return VISIBILITY
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "visibility").silentUnwrap<String>()?.let {
          return Visibility.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
     *
     * Range:
     *       minimum: 0
     *       maximum: 24
     */
    val defaultMinZoom: Double?
      /**
       * Get the minzoom property
       *
       * @return minzoom
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("line", "minzoom").silentUnwrap()

    /**
     * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
     *
     * Range:
     *       minimum: 0
     *       maximum: 24
     */
    val defaultMaxZoom: Double?
      /**
       * Get the maxzoom property
       *
       * @return maxzoom
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("line", "maxzoom").silentUnwrap()

    /**
     * The display of line endings.
     */
    val defaultLineCap: LineCap?
      /**
       * Get the default value of LineCap property
       *
       * @return LineCap
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-cap").silentUnwrap<String>()?.let {
          return LineCap.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * This is an Expression representation of "line-cap".
     *
     * The display of line endings.
     */
    val defaultLineCapAsExpression: Expression?
      /**
       * Get default value of the LineCap property as an Expression
       *
       * @return LineCap
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-cap").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLineCap?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * The display of lines when joining.
     */
    val defaultLineJoin: LineJoin?
      /**
       * Get the default value of LineJoin property
       *
       * @return LineJoin
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-join").silentUnwrap<String>()?.let {
          return LineJoin.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * This is an Expression representation of "line-join".
     *
     * The display of lines when joining.
     */
    val defaultLineJoinAsExpression: Expression?
      /**
       * Get default value of the LineJoin property as an Expression
       *
       * @return LineJoin
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-join").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLineJoin?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * Used to automatically convert miter joins to bevel joins for sharp angles.
     */
    val defaultLineMiterLimit: Double?
      /**
       * Get the default value of LineMiterLimit property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-miter-limit").silentUnwrap()
      }

    /**
     * This is an Expression representation of "line-miter-limit".
     *
     * Used to automatically convert miter joins to bevel joins for sharp angles.
     */
    val defaultLineMiterLimitAsExpression: Expression?
      /**
       * Get default value of the LineMiterLimit property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-miter-limit").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLineMiterLimit?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Used to automatically convert round joins to miter joins for shallow angles.
     */
    val defaultLineRoundLimit: Double?
      /**
       * Get the default value of LineRoundLimit property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-round-limit").silentUnwrap()
      }

    /**
     * This is an Expression representation of "line-round-limit".
     *
     * Used to automatically convert round joins to miter joins for shallow angles.
     */
    val defaultLineRoundLimitAsExpression: Expression?
      /**
       * Get default value of the LineRoundLimit property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-round-limit").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLineRoundLimit?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
     */
    val defaultLineSortKey: Double?
      /**
       * Get the default value of LineSortKey property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-sort-key").silentUnwrap()
      }

    /**
     * This is an Expression representation of "line-sort-key".
     *
     * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
     */
    val defaultLineSortKeyAsExpression: Expression?
      /**
       * Get default value of the LineSortKey property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-sort-key").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLineSortKey?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Blur applied to the line, in pixels.
     */
    val defaultLineBlur: Double?
      /**
       * Get the default value of LineBlur property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-blur").silentUnwrap()
      }

    /**
     * This is an Expression representation of "line-blur".
     *
     * Blur applied to the line, in pixels.
     */
    val defaultLineBlurAsExpression: Expression?
      /**
       * Get default value of the LineBlur property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-blur").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLineBlur?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for LineBlur.
     */
    val defaultLineBlurTransition: StyleTransition?
      /**
       * Get the LineBlur property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("line", "line-blur-transition").silentUnwrap()

    /**
     * The color with which the line will be drawn.
     */
    val defaultLineColor: String?
      /**
       * Get the default value of LineColor property
       *
       * @return String
       */
      get() {
        defaultLineColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * This is an Expression representation of "line-color".
     *
     * The color with which the line will be drawn.
     */
    val defaultLineColorAsExpression: Expression?
      /**
       * Get default value of the LineColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The color with which the line will be drawn.
     */
    val defaultLineColorAsColorInt: Int?
      /**
       * Get the default value of LineColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultLineColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for LineColor.
     */
    val defaultLineColorTransition: StyleTransition?
      /**
       * Get the LineColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("line", "line-color-transition").silentUnwrap()

    /**
     * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     */
    val defaultLineDasharray: List<Double>?
      /**
       * Get the default value of LineDasharray property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-dasharray").silentUnwrap()
      }

    /**
     * This is an Expression representation of "line-dasharray".
     *
     * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     */
    val defaultLineDasharrayAsExpression: Expression?
      /**
       * Get default value of the LineDasharray property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-dasharray").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLineDasharray?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for LineDasharray.
     */
    val defaultLineDasharrayTransition: StyleTransition?
      /**
       * Get the LineDasharray property transition options
       *
       * @return transition options for List<Double>
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("line", "line-dasharray-transition").silentUnwrap()

    /**
     * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
     */
    val defaultLineGapWidth: Double?
      /**
       * Get the default value of LineGapWidth property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-gap-width").silentUnwrap()
      }

    /**
     * This is an Expression representation of "line-gap-width".
     *
     * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
     */
    val defaultLineGapWidthAsExpression: Expression?
      /**
       * Get default value of the LineGapWidth property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-gap-width").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLineGapWidth?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for LineGapWidth.
     */
    val defaultLineGapWidthTransition: StyleTransition?
      /**
       * Get the LineGapWidth property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("line", "line-gap-width-transition").silentUnwrap()

    /**
     * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
     */
    val defaultLineOffset: Double?
      /**
       * Get the default value of LineOffset property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-offset").silentUnwrap()
      }

    /**
     * This is an Expression representation of "line-offset".
     *
     * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
     */
    val defaultLineOffsetAsExpression: Expression?
      /**
       * Get default value of the LineOffset property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-offset").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLineOffset?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for LineOffset.
     */
    val defaultLineOffsetTransition: StyleTransition?
      /**
       * Get the LineOffset property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("line", "line-offset-transition").silentUnwrap()

    /**
     * The opacity at which the line will be drawn.
     */
    val defaultLineOpacity: Double?
      /**
       * Get the default value of LineOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-opacity").silentUnwrap()
      }

    /**
     * This is an Expression representation of "line-opacity".
     *
     * The opacity at which the line will be drawn.
     */
    val defaultLineOpacityAsExpression: Expression?
      /**
       * Get default value of the LineOpacity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-opacity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLineOpacity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for LineOpacity.
     */
    val defaultLineOpacityTransition: StyleTransition?
      /**
       * Get the LineOpacity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("line", "line-opacity-transition").silentUnwrap()

    /**
     * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     */
    val defaultLinePattern: String?
      /**
       * Get the default value of LinePattern property
       *
       * @return String
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-pattern").silentUnwrap()
      }

    /**
     * This is an Expression representation of "line-pattern".
     *
     * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     */
    val defaultLinePatternAsExpression: Expression?
      /**
       * Get default value of the LinePattern property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-pattern").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLinePattern?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for LinePattern.
     */
    val defaultLinePatternTransition: StyleTransition?
      /**
       * Get the LinePattern property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("line", "line-pattern-transition").silentUnwrap()

    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
     */
    val defaultLineTranslate: List<Double>?
      /**
       * Get the default value of LineTranslate property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-translate").silentUnwrap()
      }

    /**
     * This is an Expression representation of "line-translate".
     *
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
     */
    val defaultLineTranslateAsExpression: Expression?
      /**
       * Get default value of the LineTranslate property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-translate").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLineTranslate?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for LineTranslate.
     */
    val defaultLineTranslateTransition: StyleTransition?
      /**
       * Get the LineTranslate property transition options
       *
       * @return transition options for List<Double>
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("line", "line-translate-transition").silentUnwrap()

    /**
     * Controls the frame of reference for `line-translate`.
     */
    val defaultLineTranslateAnchor: LineTranslateAnchor?
      /**
       * Get the default value of LineTranslateAnchor property
       *
       * @return LineTranslateAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-translate-anchor").silentUnwrap<String>()?.let {
          return LineTranslateAnchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * This is an Expression representation of "line-translate-anchor".
     *
     * Controls the frame of reference for `line-translate`.
     */
    val defaultLineTranslateAnchorAsExpression: Expression?
      /**
       * Get default value of the LineTranslateAnchor property as an Expression
       *
       * @return LineTranslateAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-translate-anchor").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLineTranslateAnchor?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * Stroke thickness.
     */
    val defaultLineWidth: Double?
      /**
       * Get the default value of LineWidth property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-width").silentUnwrap()
      }

    /**
     * This is an Expression representation of "line-width".
     *
     * Stroke thickness.
     */
    val defaultLineWidthAsExpression: Expression?
      /**
       * Get default value of the LineWidth property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-width").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLineWidth?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for LineWidth.
     */
    val defaultLineWidthTransition: StyleTransition?
      /**
       * Get the LineWidth property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("line", "line-width-transition").silentUnwrap()
  }
}

/**
 * This Interface contains all the functions that will be exposed to Koltin DSL.
 *
 * Separated the DSL receiver class to this interface to avoid IDE code suggestion for
 * property getters.
 */
@LayersDsl
interface LineLayerDsl {
  /**
   * Set the sourceLayer property
   *
   * @param sourceLayer value of sourceLayer
   */
  fun sourceLayer(sourceLayer: String): LineLayer

  /**
   * Set the filter property
   *
   * @param filter the expression filter to set
   */
  fun filter(filter: Expression): LineLayer

  /**
   * Set the Visibility property
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): LineLayer

  /**
   * Set the minzoom property
   *
   * @param value value of minzoom
   */
  fun minZoom(minZoom: Double): LineLayer

  /**
   * Set the maxzoom property
   *
   * @param value value of maxzoom
   */
  fun maxZoom(maxZoom: Double): LineLayer

  // Property getters and setters

  /**
   * Set the LineCap property
   *
   * @param lineCap value of lineCap
   */
  fun lineCap(lineCap: LineCap = LineCap.BUTT): LineLayer

  /**
   * Set the LineCap property
   *
   * @param lineCap value of lineCap as Expression
   */
  fun lineCap(lineCap: Expression): LineLayer

  /**
   * Set the LineJoin property
   *
   * @param lineJoin value of lineJoin
   */
  fun lineJoin(lineJoin: LineJoin = LineJoin.MITER): LineLayer

  /**
   * Set the LineJoin property
   *
   * @param lineJoin value of lineJoin as Expression
   */
  fun lineJoin(lineJoin: Expression): LineLayer

  /**
   * Set the LineMiterLimit property
   *
   * @param lineMiterLimit value of lineMiterLimit
   */
  fun lineMiterLimit(lineMiterLimit: Double = 2.0): LineLayer

  /**
   * Set the LineMiterLimit property
   *
   * @param lineMiterLimit value of lineMiterLimit as Expression
   */
  fun lineMiterLimit(lineMiterLimit: Expression): LineLayer

  /**
   * Set the LineRoundLimit property
   *
   * @param lineRoundLimit value of lineRoundLimit
   */
  fun lineRoundLimit(lineRoundLimit: Double = 1.05): LineLayer

  /**
   * Set the LineRoundLimit property
   *
   * @param lineRoundLimit value of lineRoundLimit as Expression
   */
  fun lineRoundLimit(lineRoundLimit: Expression): LineLayer

  /**
   * Set the LineSortKey property
   *
   * @param lineSortKey value of lineSortKey
   */
  fun lineSortKey(lineSortKey: Double): LineLayer

  /**
   * Set the LineSortKey property
   *
   * @param lineSortKey value of lineSortKey as Expression
   */
  fun lineSortKey(lineSortKey: Expression): LineLayer

  /**
   * Set the LineBlur property
   *
   * @param lineBlur value of lineBlur
   */
  fun lineBlur(lineBlur: Double = 0.0): LineLayer

  /**
   * Set the LineBlur property
   *
   * @param lineBlur value of lineBlur as Expression
   */
  fun lineBlur(lineBlur: Expression): LineLayer

  /**
   * Set the LineBlur property transition options
   *
   * @param options transition options for Double
   */
  fun lineBlurTransition(options: StyleTransition): LineLayer

  /**
   * DSL for [lineBlurTransition].
   */
  fun lineBlurTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

  /**
   * Set the LineColor property
   *
   * @param lineColor value of lineColor
   */
  fun lineColor(lineColor: String = "#000000"): LineLayer

  /**
   * Set the LineColor property
   *
   * @param lineColor value of lineColor as Expression
   */
  fun lineColor(lineColor: Expression): LineLayer

  /**
   * Set the LineColor property.
   *
   * @param lineColor value of lineColor
   */
  fun lineColor(@ColorInt lineColor: Int): LineLayer

  /**
   * Set the LineColor property transition options
   *
   * @param options transition options for String
   */
  fun lineColorTransition(options: StyleTransition): LineLayer

  /**
   * DSL for [lineColorTransition].
   */
  fun lineColorTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

  /**
   * Set the LineDasharray property
   *
   * @param lineDasharray value of lineDasharray
   */
  fun lineDasharray(lineDasharray: List<Double>): LineLayer

  /**
   * Set the LineDasharray property
   *
   * @param lineDasharray value of lineDasharray as Expression
   */
  fun lineDasharray(lineDasharray: Expression): LineLayer

  /**
   * Set the LineDasharray property transition options
   *
   * @param options transition options for List<Double>
   */
  fun lineDasharrayTransition(options: StyleTransition): LineLayer

  /**
   * DSL for [lineDasharrayTransition].
   */
  fun lineDasharrayTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

  /**
   * Set the LineGapWidth property
   *
   * @param lineGapWidth value of lineGapWidth
   */
  fun lineGapWidth(lineGapWidth: Double = 0.0): LineLayer

  /**
   * Set the LineGapWidth property
   *
   * @param lineGapWidth value of lineGapWidth as Expression
   */
  fun lineGapWidth(lineGapWidth: Expression): LineLayer

  /**
   * Set the LineGapWidth property transition options
   *
   * @param options transition options for Double
   */
  fun lineGapWidthTransition(options: StyleTransition): LineLayer

  /**
   * DSL for [lineGapWidthTransition].
   */
  fun lineGapWidthTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

  /**
   * Set the LineGradient property
   *
   * @param lineGradient value of lineGradient
   */
  fun lineGradient(lineGradient: Expression): LineLayer

  /**
   * Set the LineOffset property
   *
   * @param lineOffset value of lineOffset
   */
  fun lineOffset(lineOffset: Double = 0.0): LineLayer

  /**
   * Set the LineOffset property
   *
   * @param lineOffset value of lineOffset as Expression
   */
  fun lineOffset(lineOffset: Expression): LineLayer

  /**
   * Set the LineOffset property transition options
   *
   * @param options transition options for Double
   */
  fun lineOffsetTransition(options: StyleTransition): LineLayer

  /**
   * DSL for [lineOffsetTransition].
   */
  fun lineOffsetTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

  /**
   * Set the LineOpacity property
   *
   * @param lineOpacity value of lineOpacity
   */
  fun lineOpacity(lineOpacity: Double = 1.0): LineLayer

  /**
   * Set the LineOpacity property
   *
   * @param lineOpacity value of lineOpacity as Expression
   */
  fun lineOpacity(lineOpacity: Expression): LineLayer

  /**
   * Set the LineOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun lineOpacityTransition(options: StyleTransition): LineLayer

  /**
   * DSL for [lineOpacityTransition].
   */
  fun lineOpacityTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

  /**
   * Set the LinePattern property
   *
   * @param linePattern value of linePattern
   */
  fun linePattern(linePattern: String): LineLayer

  /**
   * Set the LinePattern property
   *
   * @param linePattern value of linePattern as Expression
   */
  fun linePattern(linePattern: Expression): LineLayer

  /**
   * Set the LinePattern property transition options
   *
   * @param options transition options for String
   */
  fun linePatternTransition(options: StyleTransition): LineLayer

  /**
   * DSL for [linePatternTransition].
   */
  fun linePatternTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

  /**
   * Set the LineTranslate property
   *
   * @param lineTranslate value of lineTranslate
   */
  fun lineTranslate(lineTranslate: List<Double> = listOf(0.0, 0.0)): LineLayer

  /**
   * Set the LineTranslate property
   *
   * @param lineTranslate value of lineTranslate as Expression
   */
  fun lineTranslate(lineTranslate: Expression): LineLayer

  /**
   * Set the LineTranslate property transition options
   *
   * @param options transition options for List<Double>
   */
  fun lineTranslateTransition(options: StyleTransition): LineLayer

  /**
   * DSL for [lineTranslateTransition].
   */
  fun lineTranslateTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

  /**
   * Set the LineTranslateAnchor property
   *
   * @param lineTranslateAnchor value of lineTranslateAnchor
   */
  fun lineTranslateAnchor(lineTranslateAnchor: LineTranslateAnchor = LineTranslateAnchor.MAP): LineLayer

  /**
   * Set the LineTranslateAnchor property
   *
   * @param lineTranslateAnchor value of lineTranslateAnchor as Expression
   */
  fun lineTranslateAnchor(lineTranslateAnchor: Expression): LineLayer

  /**
   * Set the LineWidth property
   *
   * @param lineWidth value of lineWidth
   */
  fun lineWidth(lineWidth: Double = 1.0): LineLayer

  /**
   * Set the LineWidth property
   *
   * @param lineWidth value of lineWidth as Expression
   */
  fun lineWidth(lineWidth: Expression): LineLayer

  /**
   * Set the LineWidth property transition options
   *
   * @param options transition options for Double
   */
  fun lineWidthTransition(options: StyleTransition): LineLayer

  /**
   * DSL for [lineWidthTransition].
   */
  fun lineWidthTransition(block: StyleTransition.Builder.() -> Unit): LineLayer
}

/**
 * DSL function for [LineLayer].
 */
fun lineLayer(layerId: String, sourceId: String, block: LineLayerDsl.() -> Unit): LineLayer = LineLayer(layerId, sourceId).apply(block)

// End of generated file.