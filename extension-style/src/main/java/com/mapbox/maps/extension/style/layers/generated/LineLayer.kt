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
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-line)
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
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  override fun sourceLayer(sourceLayer: String) = apply {
    val param = PropertyValue("source-layer", sourceLayer)
    setProperty(param)
  }

  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
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
   * A filter is a property at the layer level that determines which features should be rendered in a style layer.
   *
   * Filters are written as expressions, which give you fine-grained control over which features to include: the
   * style layer only displays the features that match the filter condition that you define.
   *
   * Note: Zoom expressions in filters are only evaluated at integer zoom levels. The `feature-state` expression
   * is not supported in filter expressions.
   *
   * @param filter the expression filter to set
   */
  override fun filter(filter: Expression) = apply {
    val propertyValue = PropertyValue("filter", filter)
    setProperty(propertyValue)
  }

  /**
   * A filter is a property at the layer level that determines which features should be rendered in a style layer.
   *
   * Filters are written as expressions, which give you fine-grained control over which features to include: the
   * style layer only displays the features that match the filter condition that you define.
   *
   * Note: Zoom expressions in filters are only evaluated at integer zoom levels. The `feature-state` expression
   * is not supported in filter expressions.
   */
  val filter: Expression?
    /**
     * Get the filter property
     *
     * @return filter
     */
    get() = getPropertyValue<Expression>("filter")

  /**
   * Whether this layer is displayed.
   */
  override val visibility: Visibility?
    /**
     * Whether this layer is displayed.
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
   * Whether this layer is displayed.
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
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
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
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
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
     * The display of line endings.
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
   * The display of line endings.
   *
   * @param lineCap value of lineCap
   */
  override fun lineCap(lineCap: LineCap) = apply {
    val propertyValue = PropertyValue("line-cap", lineCap)
    setProperty(propertyValue)
  }

  /**
   * The display of line endings.
   *
   * This is an Expression representation of "line-cap".
   *
   * The display of line endings.
   */
  val lineCapAsExpression: Expression?
    /**
     * The display of line endings.
     *
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
   * The display of line endings.
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
     * The display of lines when joining.
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
   * The display of lines when joining.
   *
   * @param lineJoin value of lineJoin
   */
  override fun lineJoin(lineJoin: LineJoin) = apply {
    val propertyValue = PropertyValue("line-join", lineJoin)
    setProperty(propertyValue)
  }

  /**
   * The display of lines when joining.
   *
   * This is an Expression representation of "line-join".
   *
   * The display of lines when joining.
   */
  val lineJoinAsExpression: Expression?
    /**
     * The display of lines when joining.
     *
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
   * The display of lines when joining.
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
     * Used to automatically convert miter joins to bevel joins for sharp angles.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-miter-limit")
    }

  /**
   * Used to automatically convert miter joins to bevel joins for sharp angles.
   *
   * @param lineMiterLimit value of lineMiterLimit
   */
  override fun lineMiterLimit(lineMiterLimit: Double) = apply {
    val propertyValue = PropertyValue("line-miter-limit", lineMiterLimit)
    setProperty(propertyValue)
  }

  /**
   * Used to automatically convert miter joins to bevel joins for sharp angles.
   *
   * This is an Expression representation of "line-miter-limit".
   *
   * Used to automatically convert miter joins to bevel joins for sharp angles.
   */
  val lineMiterLimitAsExpression: Expression?
    /**
     * Used to automatically convert miter joins to bevel joins for sharp angles.
     *
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
   * Used to automatically convert miter joins to bevel joins for sharp angles.
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
     * Used to automatically convert round joins to miter joins for shallow angles.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-round-limit")
    }

  /**
   * Used to automatically convert round joins to miter joins for shallow angles.
   *
   * @param lineRoundLimit value of lineRoundLimit
   */
  override fun lineRoundLimit(lineRoundLimit: Double) = apply {
    val propertyValue = PropertyValue("line-round-limit", lineRoundLimit)
    setProperty(propertyValue)
  }

  /**
   * Used to automatically convert round joins to miter joins for shallow angles.
   *
   * This is an Expression representation of "line-round-limit".
   *
   * Used to automatically convert round joins to miter joins for shallow angles.
   */
  val lineRoundLimitAsExpression: Expression?
    /**
     * Used to automatically convert round joins to miter joins for shallow angles.
     *
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
   * Used to automatically convert round joins to miter joins for shallow angles.
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
     * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-sort-key")
    }

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * @param lineSortKey value of lineSortKey
   */
  override fun lineSortKey(lineSortKey: Double) = apply {
    val propertyValue = PropertyValue("line-sort-key", lineSortKey)
    setProperty(propertyValue)
  }

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * This is an Expression representation of "line-sort-key".
   *
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  val lineSortKeyAsExpression: Expression?
    /**
     * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
     *
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
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
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
     * Blur applied to the line, in pixels.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-blur")
    }

  /**
   * Blur applied to the line, in pixels.
   *
   * @param lineBlur value of lineBlur
   */
  override fun lineBlur(lineBlur: Double) = apply {
    val propertyValue = PropertyValue("line-blur", lineBlur)
    setProperty(propertyValue)
  }

  /**
   * Blur applied to the line, in pixels.
   *
   * This is an Expression representation of "line-blur".
   *
   * Blur applied to the line, in pixels.
   */
  val lineBlurAsExpression: Expression?
    /**
     * Blur applied to the line, in pixels.
     *
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
   * Blur applied to the line, in pixels.
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
     * The color with which the line will be drawn.
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
   * The color with which the line will be drawn.
   *
   * @param lineColor value of lineColor
   */
  override fun lineColor(lineColor: String) = apply {
    val propertyValue = PropertyValue("line-color", lineColor)
    setProperty(propertyValue)
  }

  /**
   * The color with which the line will be drawn.
   *
   * This is an Expression representation of "line-color".
   *
   * The color with which the line will be drawn.
   */
  val lineColorAsExpression: Expression?
    /**
     * The color with which the line will be drawn.
     *
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
   * The color with which the line will be drawn.
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
   * The color with which the line will be drawn.
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
     * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("line-dasharray")
    }

  /**
   * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * @param lineDasharray value of lineDasharray
   */
  override fun lineDasharray(lineDasharray: List<Double>) = apply {
    val propertyValue = PropertyValue("line-dasharray", lineDasharray)
    setProperty(propertyValue)
  }

  /**
   * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * This is an Expression representation of "line-dasharray".
   *
   * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  val lineDasharrayAsExpression: Expression?
    /**
     * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
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
   * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
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
     * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-gap-width")
    }

  /**
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
   *
   * @param lineGapWidth value of lineGapWidth
   */
  override fun lineGapWidth(lineGapWidth: Double) = apply {
    val propertyValue = PropertyValue("line-gap-width", lineGapWidth)
    setProperty(propertyValue)
  }

  /**
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
   *
   * This is an Expression representation of "line-gap-width".
   *
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
   */
  val lineGapWidthAsExpression: Expression?
    /**
     * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
     *
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
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
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
     * Defines a gradient with which to color a line feature. Can only be used with GeoJSON sources that specify `"lineMetrics": true`.
     *
     * @return Expression
     */
    get() {
      return getPropertyValue("line-gradient")
    }

  /**
   * Defines a gradient with which to color a line feature. Can only be used with GeoJSON sources that specify `"lineMetrics": true`.
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
     * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-offset")
    }

  /**
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
   *
   * @param lineOffset value of lineOffset
   */
  override fun lineOffset(lineOffset: Double) = apply {
    val propertyValue = PropertyValue("line-offset", lineOffset)
    setProperty(propertyValue)
  }

  /**
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
   *
   * This is an Expression representation of "line-offset".
   *
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
   */
  val lineOffsetAsExpression: Expression?
    /**
     * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
     *
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
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
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
     * The opacity at which the line will be drawn.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-opacity")
    }

  /**
   * The opacity at which the line will be drawn.
   *
   * @param lineOpacity value of lineOpacity
   */
  override fun lineOpacity(lineOpacity: Double) = apply {
    val propertyValue = PropertyValue("line-opacity", lineOpacity)
    setProperty(propertyValue)
  }

  /**
   * The opacity at which the line will be drawn.
   *
   * This is an Expression representation of "line-opacity".
   *
   * The opacity at which the line will be drawn.
   */
  val lineOpacityAsExpression: Expression?
    /**
     * The opacity at which the line will be drawn.
     *
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
   * The opacity at which the line will be drawn.
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
     * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
     * @return String
     */
    get() {
      return getPropertyValue<String>("line-pattern")
    }

  /**
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * @param linePattern value of linePattern
   */
  override fun linePattern(linePattern: String) = apply {
    val propertyValue = PropertyValue("line-pattern", linePattern)
    setProperty(propertyValue)
  }

  /**
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * This is an Expression representation of "line-pattern".
   *
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  val linePatternAsExpression: Expression?
    /**
     * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
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
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
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
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("line-translate")
    }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * @param lineTranslate value of lineTranslate
   */
  override fun lineTranslate(lineTranslate: List<Double>) = apply {
    val propertyValue = PropertyValue("line-translate", lineTranslate)
    setProperty(propertyValue)
  }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * This is an Expression representation of "line-translate".
   *
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   */
  val lineTranslateAsExpression: Expression?
    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
     *
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
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
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
     * Controls the frame of reference for `line-translate`.
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
   * Controls the frame of reference for `line-translate`.
   *
   * @param lineTranslateAnchor value of lineTranslateAnchor
   */
  override fun lineTranslateAnchor(lineTranslateAnchor: LineTranslateAnchor) = apply {
    val propertyValue = PropertyValue("line-translate-anchor", lineTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * Controls the frame of reference for `line-translate`.
   *
   * This is an Expression representation of "line-translate-anchor".
   *
   * Controls the frame of reference for `line-translate`.
   */
  val lineTranslateAnchorAsExpression: Expression?
    /**
     * Controls the frame of reference for `line-translate`.
     *
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
   * Controls the frame of reference for `line-translate`.
   *
   * @param lineTranslateAnchor value of lineTranslateAnchor as Expression
   */
  override fun lineTranslateAnchor(lineTranslateAnchor: Expression) = apply {
    val propertyValue = PropertyValue("line-translate-anchor", lineTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
   */
  val lineTrimOffset: List<Double>?
    /**
     * The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("line-trim-offset")
    }

  /**
   * The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
   *
   * @param lineTrimOffset value of lineTrimOffset
   */
  override fun lineTrimOffset(lineTrimOffset: List<Double>) = apply {
    val propertyValue = PropertyValue("line-trim-offset", lineTrimOffset)
    setProperty(propertyValue)
  }

  /**
   * The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
   *
   * This is an Expression representation of "line-trim-offset".
   *
   * The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
   */
  val lineTrimOffsetAsExpression: Expression?
    /**
     * The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
     *
     * Get the LineTrimOffset property as an Expression
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("line-trim-offset")?.let {
        return it
      }
      lineTrimOffset?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
   *
   * @param lineTrimOffset value of lineTrimOffset as Expression
   */
  override fun lineTrimOffset(lineTrimOffset: Expression) = apply {
    val propertyValue = PropertyValue("line-trim-offset", lineTrimOffset)
    setProperty(propertyValue)
  }

  /**
   * Stroke thickness.
   */
  val lineWidth: Double?
    /**
     * Stroke thickness.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-width")
    }

  /**
   * Stroke thickness.
   *
   * @param lineWidth value of lineWidth
   */
  override fun lineWidth(lineWidth: Double) = apply {
    val propertyValue = PropertyValue("line-width", lineWidth)
    setProperty(propertyValue)
  }

  /**
   * Stroke thickness.
   *
   * This is an Expression representation of "line-width".
   *
   * Stroke thickness.
   */
  val lineWidthAsExpression: Expression?
    /**
     * Stroke thickness.
     *
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
   * Stroke thickness.
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
       * The display of line endings.
       *
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
     * The display of line endings.
     *
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
       * The display of lines when joining.
       *
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
     * The display of lines when joining.
     *
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
       * Used to automatically convert miter joins to bevel joins for sharp angles.
       *
       * Get the default value of LineMiterLimit property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-miter-limit").silentUnwrap()
      }

    /**
     * Used to automatically convert miter joins to bevel joins for sharp angles.
     *
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
       * Used to automatically convert round joins to miter joins for shallow angles.
       *
       * Get the default value of LineRoundLimit property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-round-limit").silentUnwrap()
      }

    /**
     * Used to automatically convert round joins to miter joins for shallow angles.
     *
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
       * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
       *
       * Get the default value of LineSortKey property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-sort-key").silentUnwrap()
      }

    /**
     * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
     *
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
       * Blur applied to the line, in pixels.
       *
       * Get the default value of LineBlur property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-blur").silentUnwrap()
      }

    /**
     * Blur applied to the line, in pixels.
     *
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
       * The color with which the line will be drawn.
       *
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
     * The color with which the line will be drawn.
     *
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
       * The color with which the line will be drawn.
       *
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
       * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
       *
       * Get the default value of LineDasharray property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-dasharray").silentUnwrap()
      }

    /**
     * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
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
       * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
       *
       * Get the default value of LineGapWidth property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-gap-width").silentUnwrap()
      }

    /**
     * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
     *
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
       * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
       *
       * Get the default value of LineOffset property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-offset").silentUnwrap()
      }

    /**
     * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
     *
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
       * The opacity at which the line will be drawn.
       *
       * Get the default value of LineOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-opacity").silentUnwrap()
      }

    /**
     * The opacity at which the line will be drawn.
     *
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
       * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
       *
       * Get the default value of LinePattern property
       *
       * @return String
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-pattern").silentUnwrap()
      }

    /**
     * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
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
       * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
       *
       * Get the default value of LineTranslate property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-translate").silentUnwrap()
      }

    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
     *
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
       * Controls the frame of reference for `line-translate`.
       *
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
     * Controls the frame of reference for `line-translate`.
     *
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
     * The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
     */
    val defaultLineTrimOffset: List<Double>?
      /**
       * The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
       *
       * Get the default value of LineTrimOffset property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-offset").silentUnwrap()
      }

    /**
     * The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
     *
     * This is an Expression representation of "line-trim-offset".
     *
     * The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
     */
    val defaultLineTrimOffsetAsExpression: Expression?
      /**
       * Get default value of the LineTrimOffset property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-offset").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLineTrimOffset?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Stroke thickness.
     */
    val defaultLineWidth: Double?
      /**
       * Stroke thickness.
       *
       * Get the default value of LineWidth property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-width").silentUnwrap()
      }

    /**
     * Stroke thickness.
     *
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
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  fun sourceLayer(sourceLayer: String): LineLayer

  /**
   * A filter is a property at the layer level that determines which features should be rendered in a style layer.
   *
   * Filters are written as expressions, which give you fine-grained control over which features to include: the
   * style layer only displays the features that match the filter condition that you define.
   *
   * Note: Zoom expressions in filters are only evaluated at integer zoom levels. The `feature-state` expression
   * is not supported in filter expressions.
   *
   * @param filter the expression filter to set
   */
  fun filter(filter: Expression): LineLayer

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): LineLayer

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param value value of minzoom
   */
  fun minZoom(minZoom: Double): LineLayer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param value value of maxzoom
   */
  fun maxZoom(maxZoom: Double): LineLayer

  // Property getters and setters

  /**
   * The display of line endings.
   *
   * @param lineCap value of lineCap
   */
  fun lineCap(lineCap: LineCap = LineCap.BUTT): LineLayer

  /**
   * The display of line endings.
   *
   * @param lineCap value of lineCap as Expression
   */
  fun lineCap(lineCap: Expression): LineLayer

  /**
   * The display of lines when joining.
   *
   * @param lineJoin value of lineJoin
   */
  fun lineJoin(lineJoin: LineJoin = LineJoin.MITER): LineLayer

  /**
   * The display of lines when joining.
   *
   * @param lineJoin value of lineJoin as Expression
   */
  fun lineJoin(lineJoin: Expression): LineLayer

  /**
   * Used to automatically convert miter joins to bevel joins for sharp angles.
   *
   * @param lineMiterLimit value of lineMiterLimit
   */
  fun lineMiterLimit(lineMiterLimit: Double = 2.0): LineLayer

  /**
   * Used to automatically convert miter joins to bevel joins for sharp angles.
   *
   * @param lineMiterLimit value of lineMiterLimit as Expression
   */
  fun lineMiterLimit(lineMiterLimit: Expression): LineLayer

  /**
   * Used to automatically convert round joins to miter joins for shallow angles.
   *
   * @param lineRoundLimit value of lineRoundLimit
   */
  fun lineRoundLimit(lineRoundLimit: Double = 1.05): LineLayer

  /**
   * Used to automatically convert round joins to miter joins for shallow angles.
   *
   * @param lineRoundLimit value of lineRoundLimit as Expression
   */
  fun lineRoundLimit(lineRoundLimit: Expression): LineLayer

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * @param lineSortKey value of lineSortKey
   */
  fun lineSortKey(lineSortKey: Double): LineLayer

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * @param lineSortKey value of lineSortKey as Expression
   */
  fun lineSortKey(lineSortKey: Expression): LineLayer

  /**
   * Blur applied to the line, in pixels.
   *
   * @param lineBlur value of lineBlur
   */
  fun lineBlur(lineBlur: Double = 0.0): LineLayer

  /**
   * Blur applied to the line, in pixels.
   *
   * @param lineBlur value of lineBlur as Expression
   */
  fun lineBlur(lineBlur: Expression): LineLayer

  /**
   * Blur applied to the line, in pixels.
   *
   * Set the LineBlur property transition options
   *
   * @param options transition options for Double
   */
  fun lineBlurTransition(options: StyleTransition): LineLayer

  /**
   * Blur applied to the line, in pixels.
   *
   * DSL for [lineBlurTransition].
   */
  fun lineBlurTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

  /**
   * The color with which the line will be drawn.
   *
   * @param lineColor value of lineColor
   */
  fun lineColor(lineColor: String = "#000000"): LineLayer

  /**
   * The color with which the line will be drawn.
   *
   * @param lineColor value of lineColor as Expression
   */
  fun lineColor(lineColor: Expression): LineLayer

  /**
   * The color with which the line will be drawn.
   *
   * @param lineColor value of lineColor
   */
  fun lineColor(@ColorInt lineColor: Int): LineLayer

  /**
   * The color with which the line will be drawn.
   *
   * Set the LineColor property transition options
   *
   * @param options transition options for String
   */
  fun lineColorTransition(options: StyleTransition): LineLayer

  /**
   * The color with which the line will be drawn.
   *
   * DSL for [lineColorTransition].
   */
  fun lineColorTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

  /**
   * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * @param lineDasharray value of lineDasharray
   */
  fun lineDasharray(lineDasharray: List<Double>): LineLayer

  /**
   * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * @param lineDasharray value of lineDasharray as Expression
   */
  fun lineDasharray(lineDasharray: Expression): LineLayer

  /**
   * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * Set the LineDasharray property transition options
   *
   * @param options transition options for List<Double>
   */
  fun lineDasharrayTransition(options: StyleTransition): LineLayer

  /**
   * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * DSL for [lineDasharrayTransition].
   */
  fun lineDasharrayTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

  /**
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
   *
   * @param lineGapWidth value of lineGapWidth
   */
  fun lineGapWidth(lineGapWidth: Double = 0.0): LineLayer

  /**
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
   *
   * @param lineGapWidth value of lineGapWidth as Expression
   */
  fun lineGapWidth(lineGapWidth: Expression): LineLayer

  /**
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
   *
   * Set the LineGapWidth property transition options
   *
   * @param options transition options for Double
   */
  fun lineGapWidthTransition(options: StyleTransition): LineLayer

  /**
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
   *
   * DSL for [lineGapWidthTransition].
   */
  fun lineGapWidthTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

  /**
   * Defines a gradient with which to color a line feature. Can only be used with GeoJSON sources that specify `"lineMetrics": true`.
   *
   * @param lineGradient value of lineGradient
   */
  fun lineGradient(lineGradient: Expression): LineLayer

  /**
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
   *
   * @param lineOffset value of lineOffset
   */
  fun lineOffset(lineOffset: Double = 0.0): LineLayer

  /**
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
   *
   * @param lineOffset value of lineOffset as Expression
   */
  fun lineOffset(lineOffset: Expression): LineLayer

  /**
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
   *
   * Set the LineOffset property transition options
   *
   * @param options transition options for Double
   */
  fun lineOffsetTransition(options: StyleTransition): LineLayer

  /**
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
   *
   * DSL for [lineOffsetTransition].
   */
  fun lineOffsetTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

  /**
   * The opacity at which the line will be drawn.
   *
   * @param lineOpacity value of lineOpacity
   */
  fun lineOpacity(lineOpacity: Double = 1.0): LineLayer

  /**
   * The opacity at which the line will be drawn.
   *
   * @param lineOpacity value of lineOpacity as Expression
   */
  fun lineOpacity(lineOpacity: Expression): LineLayer

  /**
   * The opacity at which the line will be drawn.
   *
   * Set the LineOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun lineOpacityTransition(options: StyleTransition): LineLayer

  /**
   * The opacity at which the line will be drawn.
   *
   * DSL for [lineOpacityTransition].
   */
  fun lineOpacityTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

  /**
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * @param linePattern value of linePattern
   */
  fun linePattern(linePattern: String): LineLayer

  /**
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * @param linePattern value of linePattern as Expression
   */
  fun linePattern(linePattern: Expression): LineLayer

  /**
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * Set the LinePattern property transition options
   *
   * @param options transition options for String
   */
  fun linePatternTransition(options: StyleTransition): LineLayer

  /**
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * DSL for [linePatternTransition].
   */
  fun linePatternTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * @param lineTranslate value of lineTranslate
   */
  fun lineTranslate(lineTranslate: List<Double> = listOf(0.0, 0.0)): LineLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * @param lineTranslate value of lineTranslate as Expression
   */
  fun lineTranslate(lineTranslate: Expression): LineLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * Set the LineTranslate property transition options
   *
   * @param options transition options for List<Double>
   */
  fun lineTranslateTransition(options: StyleTransition): LineLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * DSL for [lineTranslateTransition].
   */
  fun lineTranslateTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

  /**
   * Controls the frame of reference for `line-translate`.
   *
   * @param lineTranslateAnchor value of lineTranslateAnchor
   */
  fun lineTranslateAnchor(lineTranslateAnchor: LineTranslateAnchor = LineTranslateAnchor.MAP): LineLayer

  /**
   * Controls the frame of reference for `line-translate`.
   *
   * @param lineTranslateAnchor value of lineTranslateAnchor as Expression
   */
  fun lineTranslateAnchor(lineTranslateAnchor: Expression): LineLayer

  /**
   * The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
   *
   * @param lineTrimOffset value of lineTrimOffset
   */
  fun lineTrimOffset(lineTrimOffset: List<Double> = listOf(0.0, 0.0)): LineLayer

  /**
   * The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
   *
   * @param lineTrimOffset value of lineTrimOffset as Expression
   */
  fun lineTrimOffset(lineTrimOffset: Expression): LineLayer

  /**
   * Stroke thickness.
   *
   * @param lineWidth value of lineWidth
   */
  fun lineWidth(lineWidth: Double = 1.0): LineLayer

  /**
   * Stroke thickness.
   *
   * @param lineWidth value of lineWidth as Expression
   */
  fun lineWidth(lineWidth: Expression): LineLayer

  /**
   * Stroke thickness.
   *
   * Set the LineWidth property transition options
   *
   * @param options transition options for Double
   */
  fun lineWidthTransition(options: StyleTransition): LineLayer

  /**
   * Stroke thickness.
   *
   * DSL for [lineWidthTransition].
   */
  fun lineWidthTransition(block: StyleTransition.Builder.() -> Unit): LineLayer
}

/**
 * DSL function for creating a [LineLayer].
 */
fun lineLayer(layerId: String, sourceId: String, block: LineLayerDsl.() -> Unit): LineLayer = LineLayer(layerId, sourceId).apply(block)

// End of generated file.