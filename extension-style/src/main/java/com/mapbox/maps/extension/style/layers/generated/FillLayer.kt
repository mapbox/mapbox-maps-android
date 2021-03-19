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
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  val fillSortKey: Double?
    /**
     * Get the FillSortKey property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-sort-key")
    }

  /**
   * Set the FillSortKey property
   *
   * @param fillSortKey value of fillSortKey
   */
  override fun fillSortKey(fillSortKey: Double) = apply {
    val propertyValue = PropertyValue("fill-sort-key", fillSortKey)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "fill-sort-key".
   *
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  val fillSortKeyAsExpression: Expression?
    /**
     * Get the FillSortKey property as an Expression
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
   * Set the FillSortKey property
   *
   * @param fillSortKey value of fillSortKey as Expression
   */
  override fun fillSortKey(fillSortKey: Expression) = apply {
    val propertyValue = PropertyValue("fill-sort-key", fillSortKey)
    setProperty(propertyValue)
  }

  /**
   * Whether or not the fill should be antialiased.
   */
  val fillAntialias: Boolean?
    /**
     * Get the FillAntialias property
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("fill-antialias")
    }

  /**
   * Set the FillAntialias property
   *
   * @param fillAntialias value of fillAntialias
   */
  override fun fillAntialias(fillAntialias: Boolean) = apply {
    val propertyValue = PropertyValue("fill-antialias", fillAntialias)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "fill-antialias".
   *
   * Whether or not the fill should be antialiased.
   */
  val fillAntialiasAsExpression: Expression?
    /**
     * Get the FillAntialias property as an Expression
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
   * Set the FillAntialias property
   *
   * @param fillAntialias value of fillAntialias as Expression
   */
  override fun fillAntialias(fillAntialias: Expression) = apply {
    val propertyValue = PropertyValue("fill-antialias", fillAntialias)
    setProperty(propertyValue)
  }

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   */
  val fillColor: String?
    /**
     * Get the FillColor property
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
   * Set the FillColor property
   *
   * @param fillColor value of fillColor
   */
  override fun fillColor(fillColor: String) = apply {
    val propertyValue = PropertyValue("fill-color", fillColor)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "fill-color".
   *
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   */
  val fillColorAsExpression: Expression?
    /**
     * Get the FillColor property as an Expression
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
   * Set the FillColor property
   *
   * @param fillColor value of fillColor as Expression
   */
  override fun fillColor(fillColor: Expression) = apply {
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
   * Set the FillColor property.
   *
   * @param fillColor value of fillColor
   */
  override fun fillColor(@ColorInt fillColor: Int) = apply {
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
     * @return transition options for String
     */
    get() {
      return getPropertyValue("fill-color-transition")
    }

  /**
   * Set the FillColor property transition options
   *
   * @param options transition options for String
   */
  override fun fillColorTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("fill-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillColorTransition].
   */
  override fun fillColorTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    fillColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
   */
  val fillOpacity: Double?
    /**
     * Get the FillOpacity property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-opacity")
    }

  /**
   * Set the FillOpacity property
   *
   * @param fillOpacity value of fillOpacity
   */
  override fun fillOpacity(fillOpacity: Double) = apply {
    val propertyValue = PropertyValue("fill-opacity", fillOpacity)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "fill-opacity".
   *
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
   */
  val fillOpacityAsExpression: Expression?
    /**
     * Get the FillOpacity property as an Expression
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
   * Set the FillOpacity property
   *
   * @param fillOpacity value of fillOpacity as Expression
   */
  override fun fillOpacity(fillOpacity: Expression) = apply {
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
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-opacity-transition")
    }

  /**
   * Set the FillOpacity property transition options
   *
   * @param options transition options for Double
   */
  override fun fillOpacityTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("fill-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillOpacityTransition].
   */
  override fun fillOpacityTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    fillOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
   */
  val fillOutlineColor: String?
    /**
     * Get the FillOutlineColor property
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
   * Set the FillOutlineColor property
   *
   * @param fillOutlineColor value of fillOutlineColor
   */
  override fun fillOutlineColor(fillOutlineColor: String) = apply {
    val propertyValue = PropertyValue("fill-outline-color", fillOutlineColor)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "fill-outline-color".
   *
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
   */
  val fillOutlineColorAsExpression: Expression?
    /**
     * Get the FillOutlineColor property as an Expression
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
   * Set the FillOutlineColor property
   *
   * @param fillOutlineColor value of fillOutlineColor as Expression
   */
  override fun fillOutlineColor(fillOutlineColor: Expression) = apply {
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
   * Set the FillOutlineColor property.
   *
   * @param fillOutlineColor value of fillOutlineColor
   */
  override fun fillOutlineColor(@ColorInt fillOutlineColor: Int) = apply {
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
     * @return transition options for String
     */
    get() {
      return getPropertyValue("fill-outline-color-transition")
    }

  /**
   * Set the FillOutlineColor property transition options
   *
   * @param options transition options for String
   */
  override fun fillOutlineColorTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("fill-outline-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillOutlineColorTransition].
   */
  override fun fillOutlineColorTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    fillOutlineColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  val fillPattern: String?
    /**
     * Get the FillPattern property
     *
     * @return String
     */
    get() {
      return getPropertyValue<String>("fill-pattern")
    }

  /**
   * Set the FillPattern property
   *
   * @param fillPattern value of fillPattern
   */
  override fun fillPattern(fillPattern: String) = apply {
    val propertyValue = PropertyValue("fill-pattern", fillPattern)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "fill-pattern".
   *
   * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  val fillPatternAsExpression: Expression?
    /**
     * Get the FillPattern property as an Expression
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
   * Set the FillPattern property
   *
   * @param fillPattern value of fillPattern as Expression
   */
  override fun fillPattern(fillPattern: Expression) = apply {
    val propertyValue = PropertyValue("fill-pattern", fillPattern)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillPattern.
   */
  val fillPatternTransition: StyleTransition?
    /**
     * Get the FillPattern property transition options
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("fill-pattern-transition")
    }

  /**
   * Set the FillPattern property transition options
   *
   * @param options transition options for String
   */
  override fun fillPatternTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("fill-pattern-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillPatternTransition].
   */
  override fun fillPatternTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    fillPatternTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   */
  val fillTranslate: List<Double>?
    /**
     * Get the FillTranslate property
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("fill-translate")
    }

  /**
   * Set the FillTranslate property
   *
   * @param fillTranslate value of fillTranslate
   */
  override fun fillTranslate(fillTranslate: List<Double>) = apply {
    val propertyValue = PropertyValue("fill-translate", fillTranslate)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "fill-translate".
   *
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   */
  val fillTranslateAsExpression: Expression?
    /**
     * Get the FillTranslate property as an Expression
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
   * Set the FillTranslate property
   *
   * @param fillTranslate value of fillTranslate as Expression
   */
  override fun fillTranslate(fillTranslate: Expression) = apply {
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
     * @return transition options for List<Double>
     */
    get() {
      return getPropertyValue("fill-translate-transition")
    }

  /**
   * Set the FillTranslate property transition options
   *
   * @param options transition options for List<Double>
   */
  override fun fillTranslateTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("fill-translate-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillTranslateTransition].
   */
  override fun fillTranslateTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    fillTranslateTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Controls the frame of reference for `fill-translate`.
   */
  val fillTranslateAnchor: FillTranslateAnchor?
    /**
     * Get the FillTranslateAnchor property
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
   * Set the FillTranslateAnchor property
   *
   * @param fillTranslateAnchor value of fillTranslateAnchor
   */
  override fun fillTranslateAnchor(fillTranslateAnchor: FillTranslateAnchor) = apply {
    val propertyValue = PropertyValue("fill-translate-anchor", fillTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "fill-translate-anchor".
   *
   * Controls the frame of reference for `fill-translate`.
   */
  val fillTranslateAnchorAsExpression: Expression?
    /**
     * Get the FillTranslateAnchor property as an Expression
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
   * Set the FillTranslateAnchor property
   *
   * @param fillTranslateAnchor value of fillTranslateAnchor as Expression
   */
  override fun fillTranslateAnchor(fillTranslateAnchor: Expression) = apply {
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
       * Get the default value of FillSortKey property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-sort-key").silentUnwrap()
      }

    /**
     * This is an Expression representation of "fill-sort-key".
     *
     * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
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
       * Get the default value of FillAntialias property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-antialias").silentUnwrap()
      }

    /**
     * This is an Expression representation of "fill-antialias".
     *
     * Whether or not the fill should be antialiased.
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
     * This is an Expression representation of "fill-color".
     *
     * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
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
       * Get the default value of FillOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-opacity").silentUnwrap()
      }

    /**
     * This is an Expression representation of "fill-opacity".
     *
     * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
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
     * This is an Expression representation of "fill-outline-color".
     *
     * The outline color of the fill. Matches the value of `fill-color` if unspecified.
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
       * Get the default value of FillPattern property
       *
       * @return String
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-pattern").silentUnwrap()
      }

    /**
     * This is an Expression representation of "fill-pattern".
     *
     * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
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
    val defaultFillPatternTransition: StyleTransition?
      /**
       * Get the FillPattern property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-pattern-transition").silentUnwrap()

    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
     */
    val defaultFillTranslate: List<Double>?
      /**
       * Get the default value of FillTranslate property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate").silentUnwrap()
      }

    /**
     * This is an Expression representation of "fill-translate".
     *
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
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
     * This is an Expression representation of "fill-translate-anchor".
     *
     * Controls the frame of reference for `fill-translate`.
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
   * Set the sourceLayer property
   *
   * @param sourceLayer value of sourceLayer
   */
  fun sourceLayer(sourceLayer: String): FillLayer

  /**
   * Set the filter property
   *
   * @param filter the expression filter to set
   */
  fun filter(filter: Expression): FillLayer

  /**
   * Set the Visibility property
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): FillLayer

  /**
   * Set the minzoom property
   *
   * @param value value of minzoom
   */
  fun minZoom(minZoom: Double): FillLayer

  /**
   * Set the maxzoom property
   *
   * @param value value of maxzoom
   */
  fun maxZoom(maxZoom: Double): FillLayer

  // Property getters and setters

  /**
   * Set the FillSortKey property
   *
   * @param fillSortKey value of fillSortKey
   */
  fun fillSortKey(fillSortKey: Double): FillLayer

  /**
   * Set the FillSortKey property
   *
   * @param fillSortKey value of fillSortKey as Expression
   */
  fun fillSortKey(fillSortKey: Expression): FillLayer

  /**
   * Set the FillAntialias property
   *
   * @param fillAntialias value of fillAntialias
   */
  fun fillAntialias(fillAntialias: Boolean = true): FillLayer

  /**
   * Set the FillAntialias property
   *
   * @param fillAntialias value of fillAntialias as Expression
   */
  fun fillAntialias(fillAntialias: Expression): FillLayer

  /**
   * Set the FillColor property
   *
   * @param fillColor value of fillColor
   */
  fun fillColor(fillColor: String = "#000000"): FillLayer

  /**
   * Set the FillColor property
   *
   * @param fillColor value of fillColor as Expression
   */
  fun fillColor(fillColor: Expression): FillLayer

  /**
   * Set the FillColor property.
   *
   * @param fillColor value of fillColor
   */
  fun fillColor(@ColorInt fillColor: Int): FillLayer

  /**
   * Set the FillColor property transition options
   *
   * @param options transition options for String
   */
  fun fillColorTransition(options: StyleTransition): FillLayer

  /**
   * DSL for [fillColorTransition].
   */
  fun fillColorTransition(block: StyleTransition.Builder.() -> Unit): FillLayer

  /**
   * Set the FillOpacity property
   *
   * @param fillOpacity value of fillOpacity
   */
  fun fillOpacity(fillOpacity: Double = 1.0): FillLayer

  /**
   * Set the FillOpacity property
   *
   * @param fillOpacity value of fillOpacity as Expression
   */
  fun fillOpacity(fillOpacity: Expression): FillLayer

  /**
   * Set the FillOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun fillOpacityTransition(options: StyleTransition): FillLayer

  /**
   * DSL for [fillOpacityTransition].
   */
  fun fillOpacityTransition(block: StyleTransition.Builder.() -> Unit): FillLayer

  /**
   * Set the FillOutlineColor property
   *
   * @param fillOutlineColor value of fillOutlineColor
   */
  fun fillOutlineColor(fillOutlineColor: String): FillLayer

  /**
   * Set the FillOutlineColor property
   *
   * @param fillOutlineColor value of fillOutlineColor as Expression
   */
  fun fillOutlineColor(fillOutlineColor: Expression): FillLayer

  /**
   * Set the FillOutlineColor property.
   *
   * @param fillOutlineColor value of fillOutlineColor
   */
  fun fillOutlineColor(@ColorInt fillOutlineColor: Int): FillLayer

  /**
   * Set the FillOutlineColor property transition options
   *
   * @param options transition options for String
   */
  fun fillOutlineColorTransition(options: StyleTransition): FillLayer

  /**
   * DSL for [fillOutlineColorTransition].
   */
  fun fillOutlineColorTransition(block: StyleTransition.Builder.() -> Unit): FillLayer

  /**
   * Set the FillPattern property
   *
   * @param fillPattern value of fillPattern
   */
  fun fillPattern(fillPattern: String): FillLayer

  /**
   * Set the FillPattern property
   *
   * @param fillPattern value of fillPattern as Expression
   */
  fun fillPattern(fillPattern: Expression): FillLayer

  /**
   * Set the FillPattern property transition options
   *
   * @param options transition options for String
   */
  fun fillPatternTransition(options: StyleTransition): FillLayer

  /**
   * DSL for [fillPatternTransition].
   */
  fun fillPatternTransition(block: StyleTransition.Builder.() -> Unit): FillLayer

  /**
   * Set the FillTranslate property
   *
   * @param fillTranslate value of fillTranslate
   */
  fun fillTranslate(fillTranslate: List<Double> = listOf(0.0, 0.0)): FillLayer

  /**
   * Set the FillTranslate property
   *
   * @param fillTranslate value of fillTranslate as Expression
   */
  fun fillTranslate(fillTranslate: Expression): FillLayer

  /**
   * Set the FillTranslate property transition options
   *
   * @param options transition options for List<Double>
   */
  fun fillTranslateTransition(options: StyleTransition): FillLayer

  /**
   * DSL for [fillTranslateTransition].
   */
  fun fillTranslateTransition(block: StyleTransition.Builder.() -> Unit): FillLayer

  /**
   * Set the FillTranslateAnchor property
   *
   * @param fillTranslateAnchor value of fillTranslateAnchor
   */
  fun fillTranslateAnchor(fillTranslateAnchor: FillTranslateAnchor = FillTranslateAnchor.MAP): FillLayer

  /**
   * Set the FillTranslateAnchor property
   *
   * @param fillTranslateAnchor value of fillTranslateAnchor as Expression
   */
  fun fillTranslateAnchor(fillTranslateAnchor: Expression): FillLayer
}

/**
 * DSL function for [FillLayer].
 */
fun fillLayer(layerId: String, sourceId: String, block: FillLayerDsl.() -> Unit): FillLayer = FillLayer(layerId, sourceId).apply(block)

// End of generated file.