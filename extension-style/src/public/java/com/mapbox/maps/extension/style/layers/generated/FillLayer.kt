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
import com.mapbox.maps.logE
import java.util.*

/**
 * A filled polygon with an optional stroked border.
 *
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-fill)
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
 */
@UiThread
class FillLayer(override val layerId: String, val sourceId: String) : FillLayerDsl, Layer() {
  init {
    internalSourceId = sourceId
  }

  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  override fun sourceLayer(sourceLayer: String): FillLayer = apply {
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
  override fun filter(filter: Expression): FillLayer = apply {
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
     * Use static method [FillLayer.defaultVisibility] to get the default property value.
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
   * Use static method [FillLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Visibility): FillLayer = apply {
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
     * Use static method [FillLayer.defaultMinZoom] to get the default property value.
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
   * Use static method [FillLayer.defaultMinZoom] to get the default property value.
   *
   * @param value value of minzoom
   */
  override fun minZoom(minZoom: Double): FillLayer = apply {
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
     * Use static method [FillLayer.defaultMaxZoom] to get the default property value.
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
   * Use static method [FillLayer.defaultMaxZoom] to get the default property value.
   *
   * @param value value of maxzoom
   */
  override fun maxZoom(maxZoom: Double): FillLayer = apply {
    val param = PropertyValue("maxzoom", maxZoom)
    setProperty(param)
  }

  // Property getters and setters

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  val fillSortKey: Double?
    /**
     * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
     *
     * Use static method [FillLayer.defaultFillSortKey] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-sort-key")
    }

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * Use static method [FillLayer.defaultFillSortKey] to set the default property.
   *
   * @param fillSortKey value of fillSortKey
   */
  override fun fillSortKey(fillSortKey: Double): FillLayer = apply {
    val propertyValue = PropertyValue("fill-sort-key", fillSortKey)
    setProperty(propertyValue)
  }

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * This is an Expression representation of "fill-sort-key".
   *
   */
  val fillSortKeyAsExpression: Expression?
    /**
     * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
     *
     * Get the FillSortKey property as an Expression
     *
     * Use static method [FillLayer.defaultFillSortKeyAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-sort-key")?.let {
        return it
      }
      fillSortKey?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * Use static method [FillLayer.defaultFillSortKeyAsExpression] to set the default property.
   *
   * @param fillSortKey value of fillSortKey as Expression
   */
  override fun fillSortKey(fillSortKey: Expression): FillLayer = apply {
    val propertyValue = PropertyValue("fill-sort-key", fillSortKey)
    setProperty(propertyValue)
  }

  /**
   * Whether or not the fill should be antialiased.
   */
  val fillAntialias: Boolean?
    /**
     * Whether or not the fill should be antialiased.
     *
     * Use static method [FillLayer.defaultFillAntialias] to get the default property.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("fill-antialias")
    }

  /**
   * Whether or not the fill should be antialiased.
   *
   * Use static method [FillLayer.defaultFillAntialias] to set the default property.
   *
   * @param fillAntialias value of fillAntialias
   */
  override fun fillAntialias(fillAntialias: Boolean): FillLayer = apply {
    val propertyValue = PropertyValue("fill-antialias", fillAntialias)
    setProperty(propertyValue)
  }

  /**
   * Whether or not the fill should be antialiased.
   *
   * This is an Expression representation of "fill-antialias".
   *
   */
  val fillAntialiasAsExpression: Expression?
    /**
     * Whether or not the fill should be antialiased.
     *
     * Get the FillAntialias property as an Expression
     *
     * Use static method [FillLayer.defaultFillAntialiasAsExpression] to get the default property.
     *
     * @return Boolean
     */
    get() {
      getPropertyValue<Expression>("fill-antialias")?.let {
        return it
      }
      fillAntialias?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Whether or not the fill should be antialiased.
   *
   * Use static method [FillLayer.defaultFillAntialiasAsExpression] to set the default property.
   *
   * @param fillAntialias value of fillAntialias as Expression
   */
  override fun fillAntialias(fillAntialias: Expression): FillLayer = apply {
    val propertyValue = PropertyValue("fill-antialias", fillAntialias)
    setProperty(propertyValue)
  }

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   */
  val fillColor: String?
    /**
     * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
     *
     * Use static method [FillLayer.defaultFillColor] to get the default property.
     *
     * @return String
     */
    get() {
      fillColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   *
   * Use static method [FillLayer.defaultFillColor] to set the default property.
   *
   * @param fillColor value of fillColor
   */
  override fun fillColor(fillColor: String): FillLayer = apply {
    val propertyValue = PropertyValue("fill-color", fillColor)
    setProperty(propertyValue)
  }

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   *
   * This is an Expression representation of "fill-color".
   *
   */
  val fillColorAsExpression: Expression?
    /**
     * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
     *
     * Get the FillColor property as an Expression
     *
     * Use static method [FillLayer.defaultFillColorAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("fill-color")?.let {
        return it
      }
      return null
    }

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   *
   * Use static method [FillLayer.defaultFillColorAsExpression] to set the default property.
   *
   * @param fillColor value of fillColor as Expression
   */
  override fun fillColor(fillColor: Expression): FillLayer = apply {
    val propertyValue = PropertyValue("fill-color", fillColor)
    setProperty(propertyValue)
  }

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   */
  val fillColorAsColorInt: Int?
    /**
     * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
     *
     * Use static method [FillLayer.defaultFillColorAsColorInt] to get the default property.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      fillColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   *
   * Use static method [FillLayer.defaultFillColorAsColorInt] to set the default property.
   *
   * @param fillColor value of fillColor
   */
  override fun fillColor(@ColorInt fillColor: Int): FillLayer = apply {
    val propertyValue = PropertyValue("fill-color", colorIntToRgbaExpression(fillColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillColor.
   */
  val fillColorTransition: StyleTransition?
    /**
     * Get the FillColor property transition options
     *
     * Use static method [FillLayer.defaultFillColorTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("fill-color-transition")
    }

  /**
   * Set the FillColor property transition options
   *
   * Use static method [FillLayer.defaultFillColorTransition] to set the default property.
   *
   * @param options transition options for String
   */
  override fun fillColorTransition(options: StyleTransition): FillLayer = apply {
    val propertyValue = PropertyValue("fill-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillColorTransition].
   */
  override fun fillColorTransition(block: StyleTransition.Builder.() -> Unit): FillLayer = apply {
    fillColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
   */
  val fillOpacity: Double?
    /**
     * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
     *
     * Use static method [FillLayer.defaultFillOpacity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-opacity")
    }

  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
   *
   * Use static method [FillLayer.defaultFillOpacity] to set the default property.
   *
   * @param fillOpacity value of fillOpacity
   */
  override fun fillOpacity(fillOpacity: Double): FillLayer = apply {
    val propertyValue = PropertyValue("fill-opacity", fillOpacity)
    setProperty(propertyValue)
  }

  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
   *
   * This is an Expression representation of "fill-opacity".
   *
   */
  val fillOpacityAsExpression: Expression?
    /**
     * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
     *
     * Get the FillOpacity property as an Expression
     *
     * Use static method [FillLayer.defaultFillOpacityAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-opacity")?.let {
        return it
      }
      fillOpacity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
   *
   * Use static method [FillLayer.defaultFillOpacityAsExpression] to set the default property.
   *
   * @param fillOpacity value of fillOpacity as Expression
   */
  override fun fillOpacity(fillOpacity: Expression): FillLayer = apply {
    val propertyValue = PropertyValue("fill-opacity", fillOpacity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillOpacity.
   */
  val fillOpacityTransition: StyleTransition?
    /**
     * Get the FillOpacity property transition options
     *
     * Use static method [FillLayer.defaultFillOpacityTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-opacity-transition")
    }

  /**
   * Set the FillOpacity property transition options
   *
   * Use static method [FillLayer.defaultFillOpacityTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun fillOpacityTransition(options: StyleTransition): FillLayer = apply {
    val propertyValue = PropertyValue("fill-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillOpacityTransition].
   */
  override fun fillOpacityTransition(block: StyleTransition.Builder.() -> Unit): FillLayer = apply {
    fillOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
   */
  val fillOutlineColor: String?
    /**
     * The outline color of the fill. Matches the value of `fill-color` if unspecified.
     *
     * Use static method [FillLayer.defaultFillOutlineColor] to get the default property.
     *
     * @return String
     */
    get() {
      fillOutlineColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
   *
   * Use static method [FillLayer.defaultFillOutlineColor] to set the default property.
   *
   * @param fillOutlineColor value of fillOutlineColor
   */
  override fun fillOutlineColor(fillOutlineColor: String): FillLayer = apply {
    val propertyValue = PropertyValue("fill-outline-color", fillOutlineColor)
    setProperty(propertyValue)
  }

  /**
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
   *
   * This is an Expression representation of "fill-outline-color".
   *
   */
  val fillOutlineColorAsExpression: Expression?
    /**
     * The outline color of the fill. Matches the value of `fill-color` if unspecified.
     *
     * Get the FillOutlineColor property as an Expression
     *
     * Use static method [FillLayer.defaultFillOutlineColorAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("fill-outline-color")?.let {
        return it
      }
      return null
    }

  /**
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
   *
   * Use static method [FillLayer.defaultFillOutlineColorAsExpression] to set the default property.
   *
   * @param fillOutlineColor value of fillOutlineColor as Expression
   */
  override fun fillOutlineColor(fillOutlineColor: Expression): FillLayer = apply {
    val propertyValue = PropertyValue("fill-outline-color", fillOutlineColor)
    setProperty(propertyValue)
  }

  /**
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
   */
  val fillOutlineColorAsColorInt: Int?
    /**
     * The outline color of the fill. Matches the value of `fill-color` if unspecified.
     *
     * Use static method [FillLayer.defaultFillOutlineColorAsColorInt] to get the default property.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      fillOutlineColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
   *
   * Use static method [FillLayer.defaultFillOutlineColorAsColorInt] to set the default property.
   *
   * @param fillOutlineColor value of fillOutlineColor
   */
  override fun fillOutlineColor(@ColorInt fillOutlineColor: Int): FillLayer = apply {
    val propertyValue = PropertyValue("fill-outline-color", colorIntToRgbaExpression(fillOutlineColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillOutlineColor.
   */
  val fillOutlineColorTransition: StyleTransition?
    /**
     * Get the FillOutlineColor property transition options
     *
     * Use static method [FillLayer.defaultFillOutlineColorTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("fill-outline-color-transition")
    }

  /**
   * Set the FillOutlineColor property transition options
   *
   * Use static method [FillLayer.defaultFillOutlineColorTransition] to set the default property.
   *
   * @param options transition options for String
   */
  override fun fillOutlineColorTransition(options: StyleTransition): FillLayer = apply {
    val propertyValue = PropertyValue("fill-outline-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillOutlineColorTransition].
   */
  override fun fillOutlineColorTransition(block: StyleTransition.Builder.() -> Unit): FillLayer = apply {
    fillOutlineColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  val fillPattern: String?
    /**
     * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
     * Use static method [FillLayer.defaultFillPattern] to get the default property.
     *
     * @return String
     */
    get() {
      return getPropertyValue<String>("fill-pattern")
    }

  /**
   * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * Use static method [FillLayer.defaultFillPattern] to set the default property.
   *
   * @param fillPattern value of fillPattern
   */
  override fun fillPattern(fillPattern: String): FillLayer = apply {
    val propertyValue = PropertyValue("fill-pattern", fillPattern)
    setProperty(propertyValue)
  }

  /**
   * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * This is an Expression representation of "fill-pattern".
   *
   */
  val fillPatternAsExpression: Expression?
    /**
     * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
     * Get the FillPattern property as an Expression
     *
     * Use static method [FillLayer.defaultFillPatternAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("fill-pattern")?.let {
        return it
      }
      fillPattern?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * Use static method [FillLayer.defaultFillPatternAsExpression] to set the default property.
   *
   * @param fillPattern value of fillPattern as Expression
   */
  override fun fillPattern(fillPattern: Expression): FillLayer = apply {
    val propertyValue = PropertyValue("fill-pattern", fillPattern)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillPattern.
   */
  @Deprecated("This property has been deprecated and will do no operations")
  val fillPatternTransition: StyleTransition?
    /**
     * Get the FillPattern property transition options
     *
     * Use static method [FillLayer.defaultFillPatternTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      logE("FillLayer", "This property has been deprecated and will return null.")
      return null
    }

  /**
   * Set the FillPattern property transition options
   *
   * Use static method [FillLayer.defaultFillPatternTransition] to set the default property.
   *
   * @param options transition options for String
   */
  @Deprecated("This property has been deprecated and will do no operations")
  override fun fillPatternTransition(options: StyleTransition): FillLayer = apply {
    // no-op
  }

  /**
   * DSL for [fillPatternTransition].
   */
  @Deprecated("This property has been deprecated and will do no operations")
  override fun fillPatternTransition(block: StyleTransition.Builder.() -> Unit): FillLayer = apply {
    // no-op
  }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   */
  val fillTranslate: List<Double>?
    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
     *
     * Use static method [FillLayer.defaultFillTranslate] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("fill-translate")
    }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * Use static method [FillLayer.defaultFillTranslate] to set the default property.
   *
   * @param fillTranslate value of fillTranslate
   */
  override fun fillTranslate(fillTranslate: List<Double>): FillLayer = apply {
    val propertyValue = PropertyValue("fill-translate", fillTranslate)
    setProperty(propertyValue)
  }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * This is an Expression representation of "fill-translate".
   *
   */
  val fillTranslateAsExpression: Expression?
    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
     *
     * Get the FillTranslate property as an Expression
     *
     * Use static method [FillLayer.defaultFillTranslateAsExpression] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("fill-translate")?.let {
        return it
      }
      fillTranslate?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * Use static method [FillLayer.defaultFillTranslateAsExpression] to set the default property.
   *
   * @param fillTranslate value of fillTranslate as Expression
   */
  override fun fillTranslate(fillTranslate: Expression): FillLayer = apply {
    val propertyValue = PropertyValue("fill-translate", fillTranslate)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillTranslate.
   */
  val fillTranslateTransition: StyleTransition?
    /**
     * Get the FillTranslate property transition options
     *
     * Use static method [FillLayer.defaultFillTranslateTransition] to get the default property.
     *
     * @return transition options for List<Double>
     */
    get() {
      return getPropertyValue("fill-translate-transition")
    }

  /**
   * Set the FillTranslate property transition options
   *
   * Use static method [FillLayer.defaultFillTranslateTransition] to set the default property.
   *
   * @param options transition options for List<Double>
   */
  override fun fillTranslateTransition(options: StyleTransition): FillLayer = apply {
    val propertyValue = PropertyValue("fill-translate-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillTranslateTransition].
   */
  override fun fillTranslateTransition(block: StyleTransition.Builder.() -> Unit): FillLayer = apply {
    fillTranslateTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Controls the frame of reference for `fill-translate`.
   */
  val fillTranslateAnchor: FillTranslateAnchor?
    /**
     * Controls the frame of reference for `fill-translate`.
     *
     * Use static method [FillLayer.defaultFillTranslateAnchor] to get the default property.
     *
     * @return FillTranslateAnchor
     */
    get() {
      getPropertyValue<String?>("fill-translate-anchor")?.let {
        return FillTranslateAnchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Controls the frame of reference for `fill-translate`.
   *
   * Use static method [FillLayer.defaultFillTranslateAnchor] to set the default property.
   *
   * @param fillTranslateAnchor value of fillTranslateAnchor
   */
  override fun fillTranslateAnchor(fillTranslateAnchor: FillTranslateAnchor): FillLayer = apply {
    val propertyValue = PropertyValue("fill-translate-anchor", fillTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * Controls the frame of reference for `fill-translate`.
   *
   * This is an Expression representation of "fill-translate-anchor".
   *
   */
  val fillTranslateAnchorAsExpression: Expression?
    /**
     * Controls the frame of reference for `fill-translate`.
     *
     * Get the FillTranslateAnchor property as an Expression
     *
     * Use static method [FillLayer.defaultFillTranslateAnchorAsExpression] to get the default property.
     *
     * @return FillTranslateAnchor
     */
    get() {
      getPropertyValue<Expression>("fill-translate-anchor")?.let {
        return it
      }
      fillTranslateAnchor?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Controls the frame of reference for `fill-translate`.
   *
   * Use static method [FillLayer.defaultFillTranslateAnchorAsExpression] to set the default property.
   *
   * @param fillTranslateAnchor value of fillTranslateAnchor as Expression
   */
  override fun fillTranslateAnchor(fillTranslateAnchor: Expression): FillLayer = apply {
    val propertyValue = PropertyValue("fill-translate-anchor", fillTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * Get the type of this layer
   *
   * @return Type of the layer as [String]
   */
  override fun getType(): String {
    return "fill"
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
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "visibility").silentUnwrap<String>()?.let {
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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill", "minzoom").silentUnwrap()

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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill", "maxzoom").silentUnwrap()

    /**
     * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
     */
    val defaultFillSortKey: Double?
      /**
       * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
       *
       * Get the default value of FillSortKey property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-sort-key").silentUnwrap()
      }

    /**
     * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
     *
     * This is an Expression representation of "fill-sort-key".
     *
     */
    val defaultFillSortKeyAsExpression: Expression?
      /**
       * Get default value of the FillSortKey property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-sort-key").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillSortKey?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Whether or not the fill should be antialiased.
     */
    val defaultFillAntialias: Boolean?
      /**
       * Whether or not the fill should be antialiased.
       *
       * Get the default value of FillAntialias property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-antialias").silentUnwrap()
      }

    /**
     * Whether or not the fill should be antialiased.
     *
     * This is an Expression representation of "fill-antialias".
     *
     */
    val defaultFillAntialiasAsExpression: Expression?
      /**
       * Get default value of the FillAntialias property as an Expression
       *
       * @return Boolean
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-antialias").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillAntialias?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
     */
    val defaultFillColor: String?
      /**
       * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
       *
       * Get the default value of FillColor property
       *
       * @return String
       */
      get() {
        defaultFillColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
     *
     * This is an Expression representation of "fill-color".
     *
     */
    val defaultFillColorAsExpression: Expression?
      /**
       * Get default value of the FillColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
     */
    val defaultFillColorAsColorInt: Int?
      /**
       * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
       *
       * Get the default value of FillColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultFillColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for FillColor.
     */
    val defaultFillColorTransition: StyleTransition?
      /**
       * Get the FillColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-color-transition").silentUnwrap()

    /**
     * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
     */
    val defaultFillOpacity: Double?
      /**
       * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
       *
       * Get the default value of FillOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-opacity").silentUnwrap()
      }

    /**
     * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
     *
     * This is an Expression representation of "fill-opacity".
     *
     */
    val defaultFillOpacityAsExpression: Expression?
      /**
       * Get default value of the FillOpacity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-opacity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillOpacity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillOpacity.
     */
    val defaultFillOpacityTransition: StyleTransition?
      /**
       * Get the FillOpacity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-opacity-transition").silentUnwrap()

    /**
     * The outline color of the fill. Matches the value of `fill-color` if unspecified.
     */
    val defaultFillOutlineColor: String?
      /**
       * The outline color of the fill. Matches the value of `fill-color` if unspecified.
       *
       * Get the default value of FillOutlineColor property
       *
       * @return String
       */
      get() {
        defaultFillOutlineColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The outline color of the fill. Matches the value of `fill-color` if unspecified.
     *
     * This is an Expression representation of "fill-outline-color".
     *
     */
    val defaultFillOutlineColorAsExpression: Expression?
      /**
       * Get default value of the FillOutlineColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-outline-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The outline color of the fill. Matches the value of `fill-color` if unspecified.
     */
    val defaultFillOutlineColorAsColorInt: Int?
      /**
       * The outline color of the fill. Matches the value of `fill-color` if unspecified.
       *
       * Get the default value of FillOutlineColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultFillOutlineColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for FillOutlineColor.
     */
    val defaultFillOutlineColorTransition: StyleTransition?
      /**
       * Get the FillOutlineColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-outline-color-transition").silentUnwrap()

    /**
     * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     */
    val defaultFillPattern: String?
      /**
       * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
       *
       * Get the default value of FillPattern property
       *
       * @return String
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-pattern").silentUnwrap()
      }

    /**
     * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
     * This is an Expression representation of "fill-pattern".
     *
     */
    val defaultFillPatternAsExpression: Expression?
      /**
       * Get default value of the FillPattern property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-pattern").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillPattern?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillPattern.
     */
    @Deprecated("This property has been deprecated and will do no operations")
    val defaultFillPatternTransition: StyleTransition?
      /**
       * Get the FillPattern property transition options
       *
       * @return transition options for String
       */
      get() {
        logE("FillLayer", "This property has been deprecated and will return null.")
        return null
      }

    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
     */
    val defaultFillTranslate: List<Double>?
      /**
       * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
       *
       * Get the default value of FillTranslate property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate").silentUnwrap()
      }

    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
     *
     * This is an Expression representation of "fill-translate".
     *
     */
    val defaultFillTranslateAsExpression: Expression?
      /**
       * Get default value of the FillTranslate property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillTranslate?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillTranslate.
     */
    val defaultFillTranslateTransition: StyleTransition?
      /**
       * Get the FillTranslate property transition options
       *
       * @return transition options for List<Double>
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate-transition").silentUnwrap()

    /**
     * Controls the frame of reference for `fill-translate`.
     */
    val defaultFillTranslateAnchor: FillTranslateAnchor?
      /**
       * Controls the frame of reference for `fill-translate`.
       *
       * Get the default value of FillTranslateAnchor property
       *
       * @return FillTranslateAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate-anchor").silentUnwrap<String>()?.let {
          return FillTranslateAnchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Controls the frame of reference for `fill-translate`.
     *
     * This is an Expression representation of "fill-translate-anchor".
     *
     */
    val defaultFillTranslateAnchorAsExpression: Expression?
      /**
       * Get default value of the FillTranslateAnchor property as an Expression
       *
       * @return FillTranslateAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate-anchor").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillTranslateAnchor?.let {
          return Expression.literal(it.value)
        }
        return null
      }
  }
}

/**
 * This Interface contains all the functions that will be exposed to Koltin DSL.
 *
 * Separated the DSL receiver class to this interface to avoid IDE code suggestion for
 * property getters.
 */
@LayersDsl
interface FillLayerDsl {
  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  fun sourceLayer(sourceLayer: String): FillLayer

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
  fun filter(filter: Expression): FillLayer

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): FillLayer

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param value value of minzoom
   */
  fun minZoom(minZoom: Double): FillLayer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param value value of maxzoom
   */
  fun maxZoom(maxZoom: Double): FillLayer

  // Property getters and setters

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * @param fillSortKey value of fillSortKey
   */
  fun fillSortKey(fillSortKey: Double): FillLayer

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * @param fillSortKey value of fillSortKey as Expression
   */
  fun fillSortKey(fillSortKey: Expression): FillLayer

  /**
   * Whether or not the fill should be antialiased.
   *
   * @param fillAntialias value of fillAntialias
   */
  fun fillAntialias(fillAntialias: Boolean = true): FillLayer

  /**
   * Whether or not the fill should be antialiased.
   *
   * @param fillAntialias value of fillAntialias as Expression
   */
  fun fillAntialias(fillAntialias: Expression): FillLayer

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   *
   * @param fillColor value of fillColor
   */
  fun fillColor(fillColor: String = "#000000"): FillLayer

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   *
   * @param fillColor value of fillColor as Expression
   */
  fun fillColor(fillColor: Expression): FillLayer

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   *
   * @param fillColor value of fillColor
   */
  fun fillColor(@ColorInt fillColor: Int): FillLayer

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   *
   * Set the FillColor property transition options
   *
   * @param options transition options for String
   */
  fun fillColorTransition(options: StyleTransition): FillLayer

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   *
   * DSL for [fillColorTransition].
   */
  fun fillColorTransition(block: StyleTransition.Builder.() -> Unit): FillLayer

  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
   *
   * @param fillOpacity value of fillOpacity
   */
  fun fillOpacity(fillOpacity: Double = 1.0): FillLayer

  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
   *
   * @param fillOpacity value of fillOpacity as Expression
   */
  fun fillOpacity(fillOpacity: Expression): FillLayer

  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
   *
   * Set the FillOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun fillOpacityTransition(options: StyleTransition): FillLayer

  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
   *
   * DSL for [fillOpacityTransition].
   */
  fun fillOpacityTransition(block: StyleTransition.Builder.() -> Unit): FillLayer

  /**
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
   *
   * @param fillOutlineColor value of fillOutlineColor
   */
  fun fillOutlineColor(fillOutlineColor: String): FillLayer

  /**
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
   *
   * @param fillOutlineColor value of fillOutlineColor as Expression
   */
  fun fillOutlineColor(fillOutlineColor: Expression): FillLayer

  /**
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
   *
   * @param fillOutlineColor value of fillOutlineColor
   */
  fun fillOutlineColor(@ColorInt fillOutlineColor: Int): FillLayer

  /**
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
   *
   * Set the FillOutlineColor property transition options
   *
   * @param options transition options for String
   */
  fun fillOutlineColorTransition(options: StyleTransition): FillLayer

  /**
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
   *
   * DSL for [fillOutlineColorTransition].
   */
  fun fillOutlineColorTransition(block: StyleTransition.Builder.() -> Unit): FillLayer

  /**
   * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * @param fillPattern value of fillPattern
   */
  fun fillPattern(fillPattern: String): FillLayer

  /**
   * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * @param fillPattern value of fillPattern as Expression
   */
  fun fillPattern(fillPattern: Expression): FillLayer

  /**
   * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * Set the FillPattern property transition options
   *
   * @param options transition options for String
   */
  fun fillPatternTransition(options: StyleTransition): FillLayer

  /**
   * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * DSL for [fillPatternTransition].
   */
  fun fillPatternTransition(block: StyleTransition.Builder.() -> Unit): FillLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * @param fillTranslate value of fillTranslate
   */
  fun fillTranslate(fillTranslate: List<Double> = listOf(0.0, 0.0)): FillLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * @param fillTranslate value of fillTranslate as Expression
   */
  fun fillTranslate(fillTranslate: Expression): FillLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * Set the FillTranslate property transition options
   *
   * @param options transition options for List<Double>
   */
  fun fillTranslateTransition(options: StyleTransition): FillLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * DSL for [fillTranslateTransition].
   */
  fun fillTranslateTransition(block: StyleTransition.Builder.() -> Unit): FillLayer

  /**
   * Controls the frame of reference for `fill-translate`.
   *
   * @param fillTranslateAnchor value of fillTranslateAnchor
   */
  fun fillTranslateAnchor(fillTranslateAnchor: FillTranslateAnchor = FillTranslateAnchor.MAP): FillLayer

  /**
   * Controls the frame of reference for `fill-translate`.
   *
   * @param fillTranslateAnchor value of fillTranslateAnchor as Expression
   */
  fun fillTranslateAnchor(fillTranslateAnchor: Expression): FillLayer
}

/**
 * DSL function for creating a [FillLayer].
 */
fun fillLayer(layerId: String, sourceId: String, block: FillLayerDsl.() -> Unit): FillLayer = FillLayer(layerId, sourceId).apply(block)

// End of generated file.