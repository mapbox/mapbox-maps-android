// This file is generated.

package com.mapbox.maps.extension.style.layers.generated

import androidx.annotation.ColorInt
import androidx.annotation.UiThread
import com.mapbox.maps.MapboxExperimental
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
  override fun sourceLayer(sourceLayer: String): LineLayer = apply {
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
  override fun filter(filter: Expression): LineLayer = apply {
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
     * Use static method [LineLayer.defaultVisibility] to get the default property value.
     *
     * @return VISIBILITY
     */
    get() {
      val property: String? = getPropertyValue("visibility")
      property?.let {
        return Visibility.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Whether this layer is displayed.
   *
   * Use static method [LineLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Visibility): LineLayer = apply {
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
     * Use static method [LineLayer.defaultMinZoom] to get the default property value.
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
   * Use static method [LineLayer.defaultMinZoom] to get the default property value.
   *
   * @param minZoom value of minzoom
   */
  override fun minZoom(minZoom: Double): LineLayer = apply {
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
     * Use static method [LineLayer.defaultMaxZoom] to get the default property value.
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
   * Use static method [LineLayer.defaultMaxZoom] to get the default property value.
   *
   * @param maxZoom value of maxzoom
   */
  override fun maxZoom(maxZoom: Double): LineLayer = apply {
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
     * Use static method [LineLayer.defaultLineCap] to get the default property.
     *
     * @return LineCap
     */
    get() {
      getPropertyValue<String?>("line-cap")?.let {
        return LineCap.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * The display of line endings.
   *
   * Use static method [LineLayer.defaultLineCap] to set the default property.
   *
   * @param lineCap value of lineCap
   */
  override fun lineCap(lineCap: LineCap): LineLayer = apply {
    val propertyValue = PropertyValue("line-cap", lineCap)
    setProperty(propertyValue)
  }

  /**
   * The display of line endings.
   *
   * This is an Expression representation of "line-cap".
   *
   */
  val lineCapAsExpression: Expression?
    /**
     * The display of line endings.
     *
     * Get the LineCap property as an Expression
     *
     * Use static method [LineLayer.defaultLineCapAsExpression] to get the default property.
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
   * Use static method [LineLayer.defaultLineCapAsExpression] to set the default property.
   *
   * @param lineCap value of lineCap as Expression
   */
  override fun lineCap(lineCap: Expression): LineLayer = apply {
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
     * Use static method [LineLayer.defaultLineJoin] to get the default property.
     *
     * @return LineJoin
     */
    get() {
      getPropertyValue<String?>("line-join")?.let {
        return LineJoin.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * The display of lines when joining.
   *
   * Use static method [LineLayer.defaultLineJoin] to set the default property.
   *
   * @param lineJoin value of lineJoin
   */
  override fun lineJoin(lineJoin: LineJoin): LineLayer = apply {
    val propertyValue = PropertyValue("line-join", lineJoin)
    setProperty(propertyValue)
  }

  /**
   * The display of lines when joining.
   *
   * This is an Expression representation of "line-join".
   *
   */
  val lineJoinAsExpression: Expression?
    /**
     * The display of lines when joining.
     *
     * Get the LineJoin property as an Expression
     *
     * Use static method [LineLayer.defaultLineJoinAsExpression] to get the default property.
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
   * Use static method [LineLayer.defaultLineJoinAsExpression] to set the default property.
   *
   * @param lineJoin value of lineJoin as Expression
   */
  override fun lineJoin(lineJoin: Expression): LineLayer = apply {
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
     * Use static method [LineLayer.defaultLineMiterLimit] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-miter-limit")
    }

  /**
   * Used to automatically convert miter joins to bevel joins for sharp angles.
   *
   * Use static method [LineLayer.defaultLineMiterLimit] to set the default property.
   *
   * @param lineMiterLimit value of lineMiterLimit
   */
  override fun lineMiterLimit(lineMiterLimit: Double): LineLayer = apply {
    val propertyValue = PropertyValue("line-miter-limit", lineMiterLimit)
    setProperty(propertyValue)
  }

  /**
   * Used to automatically convert miter joins to bevel joins for sharp angles.
   *
   * This is an Expression representation of "line-miter-limit".
   *
   */
  val lineMiterLimitAsExpression: Expression?
    /**
     * Used to automatically convert miter joins to bevel joins for sharp angles.
     *
     * Get the LineMiterLimit property as an Expression
     *
     * Use static method [LineLayer.defaultLineMiterLimitAsExpression] to get the default property.
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
   * Use static method [LineLayer.defaultLineMiterLimitAsExpression] to set the default property.
   *
   * @param lineMiterLimit value of lineMiterLimit as Expression
   */
  override fun lineMiterLimit(lineMiterLimit: Expression): LineLayer = apply {
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
     * Use static method [LineLayer.defaultLineRoundLimit] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-round-limit")
    }

  /**
   * Used to automatically convert round joins to miter joins for shallow angles.
   *
   * Use static method [LineLayer.defaultLineRoundLimit] to set the default property.
   *
   * @param lineRoundLimit value of lineRoundLimit
   */
  override fun lineRoundLimit(lineRoundLimit: Double): LineLayer = apply {
    val propertyValue = PropertyValue("line-round-limit", lineRoundLimit)
    setProperty(propertyValue)
  }

  /**
   * Used to automatically convert round joins to miter joins for shallow angles.
   *
   * This is an Expression representation of "line-round-limit".
   *
   */
  val lineRoundLimitAsExpression: Expression?
    /**
     * Used to automatically convert round joins to miter joins for shallow angles.
     *
     * Get the LineRoundLimit property as an Expression
     *
     * Use static method [LineLayer.defaultLineRoundLimitAsExpression] to get the default property.
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
   * Use static method [LineLayer.defaultLineRoundLimitAsExpression] to set the default property.
   *
   * @param lineRoundLimit value of lineRoundLimit as Expression
   */
  override fun lineRoundLimit(lineRoundLimit: Expression): LineLayer = apply {
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
     * Use static method [LineLayer.defaultLineSortKey] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-sort-key")
    }

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * Use static method [LineLayer.defaultLineSortKey] to set the default property.
   *
   * @param lineSortKey value of lineSortKey
   */
  override fun lineSortKey(lineSortKey: Double): LineLayer = apply {
    val propertyValue = PropertyValue("line-sort-key", lineSortKey)
    setProperty(propertyValue)
  }

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * This is an Expression representation of "line-sort-key".
   *
   */
  val lineSortKeyAsExpression: Expression?
    /**
     * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
     *
     * Get the LineSortKey property as an Expression
     *
     * Use static method [LineLayer.defaultLineSortKeyAsExpression] to get the default property.
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
   * Use static method [LineLayer.defaultLineSortKeyAsExpression] to set the default property.
   *
   * @param lineSortKey value of lineSortKey as Expression
   */
  override fun lineSortKey(lineSortKey: Expression): LineLayer = apply {
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
     * Use static method [LineLayer.defaultLineBlur] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-blur")
    }

  /**
   * Blur applied to the line, in pixels.
   *
   * Use static method [LineLayer.defaultLineBlur] to set the default property.
   *
   * @param lineBlur value of lineBlur
   */
  override fun lineBlur(lineBlur: Double): LineLayer = apply {
    val propertyValue = PropertyValue("line-blur", lineBlur)
    setProperty(propertyValue)
  }

  /**
   * Blur applied to the line, in pixels.
   *
   * This is an Expression representation of "line-blur".
   *
   */
  val lineBlurAsExpression: Expression?
    /**
     * Blur applied to the line, in pixels.
     *
     * Get the LineBlur property as an Expression
     *
     * Use static method [LineLayer.defaultLineBlurAsExpression] to get the default property.
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
   * Use static method [LineLayer.defaultLineBlurAsExpression] to set the default property.
   *
   * @param lineBlur value of lineBlur as Expression
   */
  override fun lineBlur(lineBlur: Expression): LineLayer = apply {
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
     * Use static method [LineLayer.defaultLineBlurTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("line-blur-transition")
    }

  /**
   * Set the LineBlur property transition options
   *
   * Use static method [LineLayer.defaultLineBlurTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun lineBlurTransition(options: StyleTransition): LineLayer = apply {
    val propertyValue = PropertyValue("line-blur-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineBlurTransition].
   */
  override fun lineBlurTransition(block: StyleTransition.Builder.() -> Unit): LineLayer = apply {
    lineBlurTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
   */
  val lineBorderColor: String?
    /**
     * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
     *
     * Use static method [LineLayer.defaultLineBorderColor] to get the default property.
     *
     * @return String
     */
    get() {
      lineBorderColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
   *
   * Use static method [LineLayer.defaultLineBorderColor] to set the default property.
   *
   * @param lineBorderColor value of lineBorderColor
   */
  override fun lineBorderColor(lineBorderColor: String): LineLayer = apply {
    val propertyValue = PropertyValue("line-border-color", lineBorderColor)
    setProperty(propertyValue)
  }

  /**
   * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
   *
   * This is an Expression representation of "line-border-color".
   *
   */
  val lineBorderColorAsExpression: Expression?
    /**
     * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
     *
     * Get the LineBorderColor property as an Expression
     *
     * Use static method [LineLayer.defaultLineBorderColorAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("line-border-color")?.let {
        return it
      }
      return null
    }

  /**
   * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
   *
   * Use static method [LineLayer.defaultLineBorderColorAsExpression] to set the default property.
   *
   * @param lineBorderColor value of lineBorderColor as Expression
   */
  override fun lineBorderColor(lineBorderColor: Expression): LineLayer = apply {
    val propertyValue = PropertyValue("line-border-color", lineBorderColor)
    setProperty(propertyValue)
  }

  /**
   * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
   */
  val lineBorderColorAsColorInt: Int?
    /**
     * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
     *
     * Use static method [LineLayer.defaultLineBorderColorAsColorInt] to get the default property.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      lineBorderColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
   *
   * Use static method [LineLayer.defaultLineBorderColorAsColorInt] to set the default property.
   *
   * @param lineBorderColor value of lineBorderColor
   */
  override fun lineBorderColor(@ColorInt lineBorderColor: Int): LineLayer = apply {
    val propertyValue = PropertyValue("line-border-color", colorIntToRgbaExpression(lineBorderColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for LineBorderColor.
   */
  val lineBorderColorTransition: StyleTransition?
    /**
     * Get the LineBorderColor property transition options
     *
     * Use static method [LineLayer.defaultLineBorderColorTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("line-border-color-transition")
    }

  /**
   * Set the LineBorderColor property transition options
   *
   * Use static method [LineLayer.defaultLineBorderColorTransition] to set the default property.
   *
   * @param options transition options for String
   */
  override fun lineBorderColorTransition(options: StyleTransition): LineLayer = apply {
    val propertyValue = PropertyValue("line-border-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineBorderColorTransition].
   */
  override fun lineBorderColorTransition(block: StyleTransition.Builder.() -> Unit): LineLayer = apply {
    lineBorderColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The width of the line border. A value of zero means no border.
   */
  val lineBorderWidth: Double?
    /**
     * The width of the line border. A value of zero means no border.
     *
     * Use static method [LineLayer.defaultLineBorderWidth] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-border-width")
    }

  /**
   * The width of the line border. A value of zero means no border.
   *
   * Use static method [LineLayer.defaultLineBorderWidth] to set the default property.
   *
   * @param lineBorderWidth value of lineBorderWidth
   */
  override fun lineBorderWidth(lineBorderWidth: Double): LineLayer = apply {
    val propertyValue = PropertyValue("line-border-width", lineBorderWidth)
    setProperty(propertyValue)
  }

  /**
   * The width of the line border. A value of zero means no border.
   *
   * This is an Expression representation of "line-border-width".
   *
   */
  val lineBorderWidthAsExpression: Expression?
    /**
     * The width of the line border. A value of zero means no border.
     *
     * Get the LineBorderWidth property as an Expression
     *
     * Use static method [LineLayer.defaultLineBorderWidthAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("line-border-width")?.let {
        return it
      }
      lineBorderWidth?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The width of the line border. A value of zero means no border.
   *
   * Use static method [LineLayer.defaultLineBorderWidthAsExpression] to set the default property.
   *
   * @param lineBorderWidth value of lineBorderWidth as Expression
   */
  override fun lineBorderWidth(lineBorderWidth: Expression): LineLayer = apply {
    val propertyValue = PropertyValue("line-border-width", lineBorderWidth)
    setProperty(propertyValue)
  }

  /**
   * Transition options for LineBorderWidth.
   */
  val lineBorderWidthTransition: StyleTransition?
    /**
     * Get the LineBorderWidth property transition options
     *
     * Use static method [LineLayer.defaultLineBorderWidthTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("line-border-width-transition")
    }

  /**
   * Set the LineBorderWidth property transition options
   *
   * Use static method [LineLayer.defaultLineBorderWidthTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun lineBorderWidthTransition(options: StyleTransition): LineLayer = apply {
    val propertyValue = PropertyValue("line-border-width-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineBorderWidthTransition].
   */
  override fun lineBorderWidthTransition(block: StyleTransition.Builder.() -> Unit): LineLayer = apply {
    lineBorderWidthTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The color with which the line will be drawn.
   */
  val lineColor: String?
    /**
     * The color with which the line will be drawn.
     *
     * Use static method [LineLayer.defaultLineColor] to get the default property.
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
   * Use static method [LineLayer.defaultLineColor] to set the default property.
   *
   * @param lineColor value of lineColor
   */
  override fun lineColor(lineColor: String): LineLayer = apply {
    val propertyValue = PropertyValue("line-color", lineColor)
    setProperty(propertyValue)
  }

  /**
   * The color with which the line will be drawn.
   *
   * This is an Expression representation of "line-color".
   *
   */
  val lineColorAsExpression: Expression?
    /**
     * The color with which the line will be drawn.
     *
     * Get the LineColor property as an Expression
     *
     * Use static method [LineLayer.defaultLineColorAsExpression] to get the default property.
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
   * Use static method [LineLayer.defaultLineColorAsExpression] to set the default property.
   *
   * @param lineColor value of lineColor as Expression
   */
  override fun lineColor(lineColor: Expression): LineLayer = apply {
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
     * Use static method [LineLayer.defaultLineColorAsColorInt] to get the default property.
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
   * Use static method [LineLayer.defaultLineColorAsColorInt] to set the default property.
   *
   * @param lineColor value of lineColor
   */
  override fun lineColor(@ColorInt lineColor: Int): LineLayer = apply {
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
     * Use static method [LineLayer.defaultLineColorTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("line-color-transition")
    }

  /**
   * Set the LineColor property transition options
   *
   * Use static method [LineLayer.defaultLineColorTransition] to set the default property.
   *
   * @param options transition options for String
   */
  override fun lineColorTransition(options: StyleTransition): LineLayer = apply {
    val propertyValue = PropertyValue("line-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineColorTransition].
   */
  override fun lineColorTransition(block: StyleTransition.Builder.() -> Unit): LineLayer = apply {
    lineColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  val lineDasharray: List<Double>?
    /**
     * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
     * Use static method [LineLayer.defaultLineDasharray] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("line-dasharray")
    }

  /**
   * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * Use static method [LineLayer.defaultLineDasharray] to set the default property.
   *
   * @param lineDasharray value of lineDasharray
   */
  override fun lineDasharray(lineDasharray: List<Double>): LineLayer = apply {
    val propertyValue = PropertyValue("line-dasharray", lineDasharray)
    setProperty(propertyValue)
  }

  /**
   * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * This is an Expression representation of "line-dasharray".
   *
   */
  val lineDasharrayAsExpression: Expression?
    /**
     * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
     * Get the LineDasharray property as an Expression
     *
     * Use static method [LineLayer.defaultLineDasharrayAsExpression] to get the default property.
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
   * Use static method [LineLayer.defaultLineDasharrayAsExpression] to set the default property.
   *
   * @param lineDasharray value of lineDasharray as Expression
   */
  override fun lineDasharray(lineDasharray: Expression): LineLayer = apply {
    val propertyValue = PropertyValue("line-dasharray", lineDasharray)
    setProperty(propertyValue)
  }

  /**
   * Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded.
   */
  @MapboxExperimental
  val lineDepthOcclusionFactor: Double?
    /**
     * Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded.
     *
     * Use static method [LineLayer.defaultLineDepthOcclusionFactor] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-depth-occlusion-factor")
    }

  /**
   * Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded.
   *
   * Use static method [LineLayer.defaultLineDepthOcclusionFactor] to set the default property.
   *
   * @param lineDepthOcclusionFactor value of lineDepthOcclusionFactor
   */
  @MapboxExperimental
  override fun lineDepthOcclusionFactor(lineDepthOcclusionFactor: Double): LineLayer = apply {
    val propertyValue = PropertyValue("line-depth-occlusion-factor", lineDepthOcclusionFactor)
    setProperty(propertyValue)
  }

  /**
   * Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded.
   *
   * This is an Expression representation of "line-depth-occlusion-factor".
   *
   */
  @MapboxExperimental
  val lineDepthOcclusionFactorAsExpression: Expression?
    /**
     * Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded.
     *
     * Get the LineDepthOcclusionFactor property as an Expression
     *
     * Use static method [LineLayer.defaultLineDepthOcclusionFactorAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("line-depth-occlusion-factor")?.let {
        return it
      }
      lineDepthOcclusionFactor?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded.
   *
   * Use static method [LineLayer.defaultLineDepthOcclusionFactorAsExpression] to set the default property.
   *
   * @param lineDepthOcclusionFactor value of lineDepthOcclusionFactor as Expression
   */
  @MapboxExperimental
  override fun lineDepthOcclusionFactor(lineDepthOcclusionFactor: Expression): LineLayer = apply {
    val propertyValue = PropertyValue("line-depth-occlusion-factor", lineDepthOcclusionFactor)
    setProperty(propertyValue)
  }

  /**
   * Transition options for LineDepthOcclusionFactor.
   */
  @MapboxExperimental
  val lineDepthOcclusionFactorTransition: StyleTransition?
    /**
     * Get the LineDepthOcclusionFactor property transition options
     *
     * Use static method [LineLayer.defaultLineDepthOcclusionFactorTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("line-depth-occlusion-factor-transition")
    }

  /**
   * Set the LineDepthOcclusionFactor property transition options
   *
   * Use static method [LineLayer.defaultLineDepthOcclusionFactorTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun lineDepthOcclusionFactorTransition(options: StyleTransition): LineLayer = apply {
    val propertyValue = PropertyValue("line-depth-occlusion-factor-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineDepthOcclusionFactorTransition].
   */
  @MapboxExperimental
  override fun lineDepthOcclusionFactorTransition(block: StyleTransition.Builder.() -> Unit): LineLayer = apply {
    lineDepthOcclusionFactorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Emission strength
   */
  @MapboxExperimental
  val lineEmissiveStrength: Double?
    /**
     * Emission strength
     *
     * Use static method [LineLayer.defaultLineEmissiveStrength] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-emissive-strength")
    }

  /**
   * Emission strength
   *
   * Use static method [LineLayer.defaultLineEmissiveStrength] to set the default property.
   *
   * @param lineEmissiveStrength value of lineEmissiveStrength
   */
  @MapboxExperimental
  override fun lineEmissiveStrength(lineEmissiveStrength: Double): LineLayer = apply {
    val propertyValue = PropertyValue("line-emissive-strength", lineEmissiveStrength)
    setProperty(propertyValue)
  }

  /**
   * Emission strength
   *
   * This is an Expression representation of "line-emissive-strength".
   *
   */
  @MapboxExperimental
  val lineEmissiveStrengthAsExpression: Expression?
    /**
     * Emission strength
     *
     * Get the LineEmissiveStrength property as an Expression
     *
     * Use static method [LineLayer.defaultLineEmissiveStrengthAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("line-emissive-strength")?.let {
        return it
      }
      lineEmissiveStrength?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Emission strength
   *
   * Use static method [LineLayer.defaultLineEmissiveStrengthAsExpression] to set the default property.
   *
   * @param lineEmissiveStrength value of lineEmissiveStrength as Expression
   */
  @MapboxExperimental
  override fun lineEmissiveStrength(lineEmissiveStrength: Expression): LineLayer = apply {
    val propertyValue = PropertyValue("line-emissive-strength", lineEmissiveStrength)
    setProperty(propertyValue)
  }

  /**
   * Transition options for LineEmissiveStrength.
   */
  @MapboxExperimental
  val lineEmissiveStrengthTransition: StyleTransition?
    /**
     * Get the LineEmissiveStrength property transition options
     *
     * Use static method [LineLayer.defaultLineEmissiveStrengthTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("line-emissive-strength-transition")
    }

  /**
   * Set the LineEmissiveStrength property transition options
   *
   * Use static method [LineLayer.defaultLineEmissiveStrengthTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun lineEmissiveStrengthTransition(options: StyleTransition): LineLayer = apply {
    val propertyValue = PropertyValue("line-emissive-strength-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineEmissiveStrengthTransition].
   */
  @MapboxExperimental
  override fun lineEmissiveStrengthTransition(block: StyleTransition.Builder.() -> Unit): LineLayer = apply {
    lineEmissiveStrengthTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
   */
  val lineGapWidth: Double?
    /**
     * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
     *
     * Use static method [LineLayer.defaultLineGapWidth] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-gap-width")
    }

  /**
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
   *
   * Use static method [LineLayer.defaultLineGapWidth] to set the default property.
   *
   * @param lineGapWidth value of lineGapWidth
   */
  override fun lineGapWidth(lineGapWidth: Double): LineLayer = apply {
    val propertyValue = PropertyValue("line-gap-width", lineGapWidth)
    setProperty(propertyValue)
  }

  /**
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
   *
   * This is an Expression representation of "line-gap-width".
   *
   */
  val lineGapWidthAsExpression: Expression?
    /**
     * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
     *
     * Get the LineGapWidth property as an Expression
     *
     * Use static method [LineLayer.defaultLineGapWidthAsExpression] to get the default property.
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
   * Use static method [LineLayer.defaultLineGapWidthAsExpression] to set the default property.
   *
   * @param lineGapWidth value of lineGapWidth as Expression
   */
  override fun lineGapWidth(lineGapWidth: Expression): LineLayer = apply {
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
     * Use static method [LineLayer.defaultLineGapWidthTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("line-gap-width-transition")
    }

  /**
   * Set the LineGapWidth property transition options
   *
   * Use static method [LineLayer.defaultLineGapWidthTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun lineGapWidthTransition(options: StyleTransition): LineLayer = apply {
    val propertyValue = PropertyValue("line-gap-width-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineGapWidthTransition].
   */
  override fun lineGapWidthTransition(block: StyleTransition.Builder.() -> Unit): LineLayer = apply {
    lineGapWidthTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Defines a gradient with which to color a line feature. Can only be used with GeoJSON sources that specify `"lineMetrics": true`.
   */
  val lineGradient: Expression?
    /**
     * Defines a gradient with which to color a line feature. Can only be used with GeoJSON sources that specify `"lineMetrics": true`.
     *
     * Use static method [LineLayer.defaultLineGradient] to get the default property.
     *
     * @return Expression
     */
    get() {
      return getPropertyValue("line-gradient")
    }

  /**
   * Defines a gradient with which to color a line feature. Can only be used with GeoJSON sources that specify `"lineMetrics": true`.
   *
   * Use static method [LineLayer.defaultLineGradient] to set the default property.
   *
   * @param lineGradient value of lineGradient
   */
  override fun lineGradient(lineGradient: Expression): LineLayer = apply {
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
     * Use static method [LineLayer.defaultLineOffset] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-offset")
    }

  /**
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
   *
   * Use static method [LineLayer.defaultLineOffset] to set the default property.
   *
   * @param lineOffset value of lineOffset
   */
  override fun lineOffset(lineOffset: Double): LineLayer = apply {
    val propertyValue = PropertyValue("line-offset", lineOffset)
    setProperty(propertyValue)
  }

  /**
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
   *
   * This is an Expression representation of "line-offset".
   *
   */
  val lineOffsetAsExpression: Expression?
    /**
     * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
     *
     * Get the LineOffset property as an Expression
     *
     * Use static method [LineLayer.defaultLineOffsetAsExpression] to get the default property.
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
   * Use static method [LineLayer.defaultLineOffsetAsExpression] to set the default property.
   *
   * @param lineOffset value of lineOffset as Expression
   */
  override fun lineOffset(lineOffset: Expression): LineLayer = apply {
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
     * Use static method [LineLayer.defaultLineOffsetTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("line-offset-transition")
    }

  /**
   * Set the LineOffset property transition options
   *
   * Use static method [LineLayer.defaultLineOffsetTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun lineOffsetTransition(options: StyleTransition): LineLayer = apply {
    val propertyValue = PropertyValue("line-offset-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineOffsetTransition].
   */
  override fun lineOffsetTransition(block: StyleTransition.Builder.() -> Unit): LineLayer = apply {
    lineOffsetTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity at which the line will be drawn.
   */
  val lineOpacity: Double?
    /**
     * The opacity at which the line will be drawn.
     *
     * Use static method [LineLayer.defaultLineOpacity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-opacity")
    }

  /**
   * The opacity at which the line will be drawn.
   *
   * Use static method [LineLayer.defaultLineOpacity] to set the default property.
   *
   * @param lineOpacity value of lineOpacity
   */
  override fun lineOpacity(lineOpacity: Double): LineLayer = apply {
    val propertyValue = PropertyValue("line-opacity", lineOpacity)
    setProperty(propertyValue)
  }

  /**
   * The opacity at which the line will be drawn.
   *
   * This is an Expression representation of "line-opacity".
   *
   */
  val lineOpacityAsExpression: Expression?
    /**
     * The opacity at which the line will be drawn.
     *
     * Get the LineOpacity property as an Expression
     *
     * Use static method [LineLayer.defaultLineOpacityAsExpression] to get the default property.
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
   * Use static method [LineLayer.defaultLineOpacityAsExpression] to set the default property.
   *
   * @param lineOpacity value of lineOpacity as Expression
   */
  override fun lineOpacity(lineOpacity: Expression): LineLayer = apply {
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
     * Use static method [LineLayer.defaultLineOpacityTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("line-opacity-transition")
    }

  /**
   * Set the LineOpacity property transition options
   *
   * Use static method [LineLayer.defaultLineOpacityTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun lineOpacityTransition(options: StyleTransition): LineLayer = apply {
    val propertyValue = PropertyValue("line-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineOpacityTransition].
   */
  override fun lineOpacityTransition(block: StyleTransition.Builder.() -> Unit): LineLayer = apply {
    lineOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  val linePattern: String?
    /**
     * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
     * Use static method [LineLayer.defaultLinePattern] to get the default property.
     *
     * @return String
     */
    get() {
      return getPropertyValue<String>("line-pattern")
    }

  /**
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * Use static method [LineLayer.defaultLinePattern] to set the default property.
   *
   * @param linePattern value of linePattern
   */
  override fun linePattern(linePattern: String): LineLayer = apply {
    val propertyValue = PropertyValue("line-pattern", linePattern)
    setProperty(propertyValue)
  }

  /**
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * This is an Expression representation of "line-pattern".
   *
   */
  val linePatternAsExpression: Expression?
    /**
     * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
     * Get the LinePattern property as an Expression
     *
     * Use static method [LineLayer.defaultLinePatternAsExpression] to get the default property.
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
   * Use static method [LineLayer.defaultLinePatternAsExpression] to set the default property.
   *
   * @param linePattern value of linePattern as Expression
   */
  override fun linePattern(linePattern: Expression): LineLayer = apply {
    val propertyValue = PropertyValue("line-pattern", linePattern)
    setProperty(propertyValue)
  }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   */
  val lineTranslate: List<Double>?
    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
     *
     * Use static method [LineLayer.defaultLineTranslate] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("line-translate")
    }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * Use static method [LineLayer.defaultLineTranslate] to set the default property.
   *
   * @param lineTranslate value of lineTranslate
   */
  override fun lineTranslate(lineTranslate: List<Double>): LineLayer = apply {
    val propertyValue = PropertyValue("line-translate", lineTranslate)
    setProperty(propertyValue)
  }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * This is an Expression representation of "line-translate".
   *
   */
  val lineTranslateAsExpression: Expression?
    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
     *
     * Get the LineTranslate property as an Expression
     *
     * Use static method [LineLayer.defaultLineTranslateAsExpression] to get the default property.
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
   * Use static method [LineLayer.defaultLineTranslateAsExpression] to set the default property.
   *
   * @param lineTranslate value of lineTranslate as Expression
   */
  override fun lineTranslate(lineTranslate: Expression): LineLayer = apply {
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
     * Use static method [LineLayer.defaultLineTranslateTransition] to get the default property.
     *
     * @return transition options for List<Double>
     */
    get() {
      return getPropertyValue("line-translate-transition")
    }

  /**
   * Set the LineTranslate property transition options
   *
   * Use static method [LineLayer.defaultLineTranslateTransition] to set the default property.
   *
   * @param options transition options for List<Double>
   */
  override fun lineTranslateTransition(options: StyleTransition): LineLayer = apply {
    val propertyValue = PropertyValue("line-translate-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineTranslateTransition].
   */
  override fun lineTranslateTransition(block: StyleTransition.Builder.() -> Unit): LineLayer = apply {
    lineTranslateTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Controls the frame of reference for `line-translate`.
   */
  val lineTranslateAnchor: LineTranslateAnchor?
    /**
     * Controls the frame of reference for `line-translate`.
     *
     * Use static method [LineLayer.defaultLineTranslateAnchor] to get the default property.
     *
     * @return LineTranslateAnchor
     */
    get() {
      getPropertyValue<String?>("line-translate-anchor")?.let {
        return LineTranslateAnchor.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Controls the frame of reference for `line-translate`.
   *
   * Use static method [LineLayer.defaultLineTranslateAnchor] to set the default property.
   *
   * @param lineTranslateAnchor value of lineTranslateAnchor
   */
  override fun lineTranslateAnchor(lineTranslateAnchor: LineTranslateAnchor): LineLayer = apply {
    val propertyValue = PropertyValue("line-translate-anchor", lineTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * Controls the frame of reference for `line-translate`.
   *
   * This is an Expression representation of "line-translate-anchor".
   *
   */
  val lineTranslateAnchorAsExpression: Expression?
    /**
     * Controls the frame of reference for `line-translate`.
     *
     * Get the LineTranslateAnchor property as an Expression
     *
     * Use static method [LineLayer.defaultLineTranslateAnchorAsExpression] to get the default property.
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
   * Use static method [LineLayer.defaultLineTranslateAnchorAsExpression] to set the default property.
   *
   * @param lineTranslateAnchor value of lineTranslateAnchor as Expression
   */
  override fun lineTranslateAnchor(lineTranslateAnchor: Expression): LineLayer = apply {
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
     * Use static method [LineLayer.defaultLineTrimOffset] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("line-trim-offset")
    }

  /**
   * The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
   *
   * Use static method [LineLayer.defaultLineTrimOffset] to set the default property.
   *
   * @param lineTrimOffset value of lineTrimOffset
   */
  override fun lineTrimOffset(lineTrimOffset: List<Double>): LineLayer = apply {
    val propertyValue = PropertyValue("line-trim-offset", lineTrimOffset)
    setProperty(propertyValue)
  }

  /**
   * The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
   *
   * This is an Expression representation of "line-trim-offset".
   *
   */
  val lineTrimOffsetAsExpression: Expression?
    /**
     * The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
     *
     * Get the LineTrimOffset property as an Expression
     *
     * Use static method [LineLayer.defaultLineTrimOffsetAsExpression] to get the default property.
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
   * Use static method [LineLayer.defaultLineTrimOffsetAsExpression] to set the default property.
   *
   * @param lineTrimOffset value of lineTrimOffset as Expression
   */
  override fun lineTrimOffset(lineTrimOffset: Expression): LineLayer = apply {
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
     * Use static method [LineLayer.defaultLineWidth] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("line-width")
    }

  /**
   * Stroke thickness.
   *
   * Use static method [LineLayer.defaultLineWidth] to set the default property.
   *
   * @param lineWidth value of lineWidth
   */
  override fun lineWidth(lineWidth: Double): LineLayer = apply {
    val propertyValue = PropertyValue("line-width", lineWidth)
    setProperty(propertyValue)
  }

  /**
   * Stroke thickness.
   *
   * This is an Expression representation of "line-width".
   *
   */
  val lineWidthAsExpression: Expression?
    /**
     * Stroke thickness.
     *
     * Get the LineWidth property as an Expression
     *
     * Use static method [LineLayer.defaultLineWidthAsExpression] to get the default property.
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
   * Use static method [LineLayer.defaultLineWidthAsExpression] to set the default property.
   *
   * @param lineWidth value of lineWidth as Expression
   */
  override fun lineWidth(lineWidth: Expression): LineLayer = apply {
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
     * Use static method [LineLayer.defaultLineWidthTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("line-width-transition")
    }

  /**
   * Set the LineWidth property transition options
   *
   * Use static method [LineLayer.defaultLineWidthTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun lineWidthTransition(options: StyleTransition): LineLayer = apply {
    val propertyValue = PropertyValue("line-width-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [lineWidthTransition].
   */
  override fun lineWidthTransition(block: StyleTransition.Builder.() -> Unit): LineLayer = apply {
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
          return Visibility.valueOf(it.uppercase(Locale.US).replace('-', '_'))
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
          return LineCap.valueOf(it.uppercase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * The display of line endings.
     *
     * This is an Expression representation of "line-cap".
     *
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
          return LineJoin.valueOf(it.uppercase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * The display of lines when joining.
     *
     * This is an Expression representation of "line-join".
     *
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
     * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
     */
    val defaultLineBorderColor: String?
      /**
       * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
       *
       * Get the default value of LineBorderColor property
       *
       * @return String
       */
      get() {
        defaultLineBorderColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
     *
     * This is an Expression representation of "line-border-color".
     *
     */
    val defaultLineBorderColorAsExpression: Expression?
      /**
       * Get default value of the LineBorderColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-border-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
     */
    val defaultLineBorderColorAsColorInt: Int?
      /**
       * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
       *
       * Get the default value of LineBorderColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultLineBorderColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for LineBorderColor.
     */
    val defaultLineBorderColorTransition: StyleTransition?
      /**
       * Get the LineBorderColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("line", "line-border-color-transition").silentUnwrap()

    /**
     * The width of the line border. A value of zero means no border.
     */
    val defaultLineBorderWidth: Double?
      /**
       * The width of the line border. A value of zero means no border.
       *
       * Get the default value of LineBorderWidth property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-border-width").silentUnwrap()
      }

    /**
     * The width of the line border. A value of zero means no border.
     *
     * This is an Expression representation of "line-border-width".
     *
     */
    val defaultLineBorderWidthAsExpression: Expression?
      /**
       * Get default value of the LineBorderWidth property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-border-width").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLineBorderWidth?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for LineBorderWidth.
     */
    val defaultLineBorderWidthTransition: StyleTransition?
      /**
       * Get the LineBorderWidth property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("line", "line-border-width-transition").silentUnwrap()

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
     * Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded.
     */
    @MapboxExperimental
    val defaultLineDepthOcclusionFactor: Double?
      /**
       * Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded.
       *
       * Get the default value of LineDepthOcclusionFactor property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-depth-occlusion-factor").silentUnwrap()
      }

    /**
     * Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded.
     *
     * This is an Expression representation of "line-depth-occlusion-factor".
     *
     */
    @MapboxExperimental
    val defaultLineDepthOcclusionFactorAsExpression: Expression?
      /**
       * Get default value of the LineDepthOcclusionFactor property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-depth-occlusion-factor").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLineDepthOcclusionFactor?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for LineDepthOcclusionFactor.
     */
    @MapboxExperimental
    val defaultLineDepthOcclusionFactorTransition: StyleTransition?
      /**
       * Get the LineDepthOcclusionFactor property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("line", "line-depth-occlusion-factor-transition").silentUnwrap()

    /**
     * Emission strength
     */
    @MapboxExperimental
    val defaultLineEmissiveStrength: Double?
      /**
       * Emission strength
       *
       * Get the default value of LineEmissiveStrength property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("line", "line-emissive-strength").silentUnwrap()
      }

    /**
     * Emission strength
     *
     * This is an Expression representation of "line-emissive-strength".
     *
     */
    @MapboxExperimental
    val defaultLineEmissiveStrengthAsExpression: Expression?
      /**
       * Get default value of the LineEmissiveStrength property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("line", "line-emissive-strength").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLineEmissiveStrength?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for LineEmissiveStrength.
     */
    @MapboxExperimental
    val defaultLineEmissiveStrengthTransition: StyleTransition?
      /**
       * Get the LineEmissiveStrength property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("line", "line-emissive-strength-transition").silentUnwrap()

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
          return LineTranslateAnchor.valueOf(it.uppercase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Controls the frame of reference for `line-translate`.
     *
     * This is an Expression representation of "line-translate-anchor".
     *
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
   * @param minZoom value of minzoom
   */
  fun minZoom(minZoom: Double): LineLayer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param maxZoom value of maxzoom
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
   * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
   *
   * @param lineBorderColor value of lineBorderColor
   */
  fun lineBorderColor(lineBorderColor: String = "rgba(0, 0, 0, 0)"): LineLayer

  /**
   * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
   *
   * @param lineBorderColor value of lineBorderColor as Expression
   */
  fun lineBorderColor(lineBorderColor: Expression): LineLayer

  /**
   * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
   *
   * @param lineBorderColor value of lineBorderColor
   */
  fun lineBorderColor(@ColorInt lineBorderColor: Int): LineLayer

  /**
   * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
   *
   * Set the LineBorderColor property transition options
   *
   * @param options transition options for String
   */
  fun lineBorderColorTransition(options: StyleTransition): LineLayer

  /**
   * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
   *
   * DSL for [lineBorderColorTransition].
   */
  fun lineBorderColorTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

  /**
   * The width of the line border. A value of zero means no border.
   *
   * @param lineBorderWidth value of lineBorderWidth
   */
  fun lineBorderWidth(lineBorderWidth: Double = 0.0): LineLayer

  /**
   * The width of the line border. A value of zero means no border.
   *
   * @param lineBorderWidth value of lineBorderWidth as Expression
   */
  fun lineBorderWidth(lineBorderWidth: Expression): LineLayer

  /**
   * The width of the line border. A value of zero means no border.
   *
   * Set the LineBorderWidth property transition options
   *
   * @param options transition options for Double
   */
  fun lineBorderWidthTransition(options: StyleTransition): LineLayer

  /**
   * The width of the line border. A value of zero means no border.
   *
   * DSL for [lineBorderWidthTransition].
   */
  fun lineBorderWidthTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

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
   * Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded.
   *
   * @param lineDepthOcclusionFactor value of lineDepthOcclusionFactor
   */
  @MapboxExperimental
  fun lineDepthOcclusionFactor(lineDepthOcclusionFactor: Double = 1.0): LineLayer

  /**
   * Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded.
   *
   * @param lineDepthOcclusionFactor value of lineDepthOcclusionFactor as Expression
   */
  @MapboxExperimental
  fun lineDepthOcclusionFactor(lineDepthOcclusionFactor: Expression): LineLayer

  /**
   * Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded.
   *
   * Set the LineDepthOcclusionFactor property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun lineDepthOcclusionFactorTransition(options: StyleTransition): LineLayer

  /**
   * Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded.
   *
   * DSL for [lineDepthOcclusionFactorTransition].
   */
  @MapboxExperimental
  fun lineDepthOcclusionFactorTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

  /**
   * Emission strength
   *
   * @param lineEmissiveStrength value of lineEmissiveStrength
   */
  @MapboxExperimental
  fun lineEmissiveStrength(lineEmissiveStrength: Double = 0.0): LineLayer

  /**
   * Emission strength
   *
   * @param lineEmissiveStrength value of lineEmissiveStrength as Expression
   */
  @MapboxExperimental
  fun lineEmissiveStrength(lineEmissiveStrength: Expression): LineLayer

  /**
   * Emission strength
   *
   * Set the LineEmissiveStrength property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun lineEmissiveStrengthTransition(options: StyleTransition): LineLayer

  /**
   * Emission strength
   *
   * DSL for [lineEmissiveStrengthTransition].
   */
  @MapboxExperimental
  fun lineEmissiveStrengthTransition(block: StyleTransition.Builder.() -> Unit): LineLayer

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