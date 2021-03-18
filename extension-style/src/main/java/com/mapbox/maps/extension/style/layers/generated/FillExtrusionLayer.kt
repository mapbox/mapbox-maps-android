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
 * An extruded (3D) polygon.
 *
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-fill-extrusion)
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
 */
@UiThread
class FillExtrusionLayer(override val layerId: String, val sourceId: String) : FillExtrusionLayerDsl, Layer() {
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
   * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
   */
  val fillExtrusionBase: Double?
    /**
     * Get the FillExtrusionBase property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-base")
    }

  /**
   * Set the FillExtrusionBase property
   *
   * @param fillExtrusionBase value of fillExtrusionBase
   */
  override fun fillExtrusionBase(fillExtrusionBase: Double) = apply {
    val propertyValue = PropertyValue("fill-extrusion-base", fillExtrusionBase)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "fill-extrusion-base".
   *
   * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
   */
  val fillExtrusionBaseAsExpression: Expression?
    /**
     * Get the FillExtrusionBase property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-base")?.let {
        return it
      }
      fillExtrusionBase?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the FillExtrusionBase property
   *
   * @param fillExtrusionBase value of fillExtrusionBase as Expression
   */
  override fun fillExtrusionBase(fillExtrusionBase: Expression) = apply {
    val propertyValue = PropertyValue("fill-extrusion-base", fillExtrusionBase)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionBase.
   */
  val fillExtrusionBaseTransition: StyleTransition?
    /**
     * Get the FillExtrusionBase property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-extrusion-base-transition")
    }

  /**
   * Set the FillExtrusionBase property transition options
   *
   * @param options transition options for Double
   */
  override fun fillExtrusionBaseTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("fill-extrusion-base-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionBaseTransition].
   */
  override fun fillExtrusionBaseTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    fillExtrusionBaseTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
   */
  val fillExtrusionColor: String?
    /**
     * Get the FillExtrusionColor property
     *
     * @return String
     */
    get() {
      fillExtrusionColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * Set the FillExtrusionColor property
   *
   * @param fillExtrusionColor value of fillExtrusionColor
   */
  override fun fillExtrusionColor(fillExtrusionColor: String) = apply {
    val propertyValue = PropertyValue("fill-extrusion-color", fillExtrusionColor)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "fill-extrusion-color".
   *
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
   */
  val fillExtrusionColorAsExpression: Expression?
    /**
     * Get the FillExtrusionColor property as an Expression
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-color")?.let {
        return it
      }
      return null
    }

  /**
   * Set the FillExtrusionColor property
   *
   * @param fillExtrusionColor value of fillExtrusionColor as Expression
   */
  override fun fillExtrusionColor(fillExtrusionColor: Expression) = apply {
    val propertyValue = PropertyValue("fill-extrusion-color", fillExtrusionColor)
    setProperty(propertyValue)
  }

  /**
   * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
   */
  val fillExtrusionColorAsColorInt: Int?
    /**
     * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      fillExtrusionColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * Set the FillExtrusionColor property.
   *
   * @param fillExtrusionColor value of fillExtrusionColor
   */
  override fun fillExtrusionColor(@ColorInt fillExtrusionColor: Int) = apply {
    val propertyValue = PropertyValue("fill-extrusion-color", colorIntToRgbaExpression(fillExtrusionColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionColor.
   */
  val fillExtrusionColorTransition: StyleTransition?
    /**
     * Get the FillExtrusionColor property transition options
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("fill-extrusion-color-transition")
    }

  /**
   * Set the FillExtrusionColor property transition options
   *
   * @param options transition options for String
   */
  override fun fillExtrusionColorTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("fill-extrusion-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionColorTransition].
   */
  override fun fillExtrusionColorTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    fillExtrusionColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The height with which to extrude this layer.
   */
  val fillExtrusionHeight: Double?
    /**
     * Get the FillExtrusionHeight property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-height")
    }

  /**
   * Set the FillExtrusionHeight property
   *
   * @param fillExtrusionHeight value of fillExtrusionHeight
   */
  override fun fillExtrusionHeight(fillExtrusionHeight: Double) = apply {
    val propertyValue = PropertyValue("fill-extrusion-height", fillExtrusionHeight)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "fill-extrusion-height".
   *
   * The height with which to extrude this layer.
   */
  val fillExtrusionHeightAsExpression: Expression?
    /**
     * Get the FillExtrusionHeight property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-height")?.let {
        return it
      }
      fillExtrusionHeight?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the FillExtrusionHeight property
   *
   * @param fillExtrusionHeight value of fillExtrusionHeight as Expression
   */
  override fun fillExtrusionHeight(fillExtrusionHeight: Expression) = apply {
    val propertyValue = PropertyValue("fill-extrusion-height", fillExtrusionHeight)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionHeight.
   */
  val fillExtrusionHeightTransition: StyleTransition?
    /**
     * Get the FillExtrusionHeight property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-extrusion-height-transition")
    }

  /**
   * Set the FillExtrusionHeight property transition options
   *
   * @param options transition options for Double
   */
  override fun fillExtrusionHeightTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("fill-extrusion-height-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionHeightTransition].
   */
  override fun fillExtrusionHeightTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    fillExtrusionHeightTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
   */
  val fillExtrusionOpacity: Double?
    /**
     * Get the FillExtrusionOpacity property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-extrusion-opacity")
    }

  /**
   * Set the FillExtrusionOpacity property
   *
   * @param fillExtrusionOpacity value of fillExtrusionOpacity
   */
  override fun fillExtrusionOpacity(fillExtrusionOpacity: Double) = apply {
    val propertyValue = PropertyValue("fill-extrusion-opacity", fillExtrusionOpacity)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "fill-extrusion-opacity".
   *
   * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
   */
  val fillExtrusionOpacityAsExpression: Expression?
    /**
     * Get the FillExtrusionOpacity property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-opacity")?.let {
        return it
      }
      fillExtrusionOpacity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the FillExtrusionOpacity property
   *
   * @param fillExtrusionOpacity value of fillExtrusionOpacity as Expression
   */
  override fun fillExtrusionOpacity(fillExtrusionOpacity: Expression) = apply {
    val propertyValue = PropertyValue("fill-extrusion-opacity", fillExtrusionOpacity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionOpacity.
   */
  val fillExtrusionOpacityTransition: StyleTransition?
    /**
     * Get the FillExtrusionOpacity property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-extrusion-opacity-transition")
    }

  /**
   * Set the FillExtrusionOpacity property transition options
   *
   * @param options transition options for Double
   */
  override fun fillExtrusionOpacityTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("fill-extrusion-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionOpacityTransition].
   */
  override fun fillExtrusionOpacityTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    fillExtrusionOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  val fillExtrusionPattern: String?
    /**
     * Get the FillExtrusionPattern property
     *
     * @return String
     */
    get() {
      return getPropertyValue<String>("fill-extrusion-pattern")
    }

  /**
   * Set the FillExtrusionPattern property
   *
   * @param fillExtrusionPattern value of fillExtrusionPattern
   */
  override fun fillExtrusionPattern(fillExtrusionPattern: String) = apply {
    val propertyValue = PropertyValue("fill-extrusion-pattern", fillExtrusionPattern)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "fill-extrusion-pattern".
   *
   * Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  val fillExtrusionPatternAsExpression: Expression?
    /**
     * Get the FillExtrusionPattern property as an Expression
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-pattern")?.let {
        return it
      }
      fillExtrusionPattern?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the FillExtrusionPattern property
   *
   * @param fillExtrusionPattern value of fillExtrusionPattern as Expression
   */
  override fun fillExtrusionPattern(fillExtrusionPattern: Expression) = apply {
    val propertyValue = PropertyValue("fill-extrusion-pattern", fillExtrusionPattern)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionPattern.
   */
  val fillExtrusionPatternTransition: StyleTransition?
    /**
     * Get the FillExtrusionPattern property transition options
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("fill-extrusion-pattern-transition")
    }

  /**
   * Set the FillExtrusionPattern property transition options
   *
   * @param options transition options for String
   */
  override fun fillExtrusionPatternTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("fill-extrusion-pattern-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionPatternTransition].
   */
  override fun fillExtrusionPatternTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    fillExtrusionPatternTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
   */
  val fillExtrusionTranslate: List<Double>?
    /**
     * Get the FillExtrusionTranslate property
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("fill-extrusion-translate")
    }

  /**
   * Set the FillExtrusionTranslate property
   *
   * @param fillExtrusionTranslate value of fillExtrusionTranslate
   */
  override fun fillExtrusionTranslate(fillExtrusionTranslate: List<Double>) = apply {
    val propertyValue = PropertyValue("fill-extrusion-translate", fillExtrusionTranslate)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "fill-extrusion-translate".
   *
   * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
   */
  val fillExtrusionTranslateAsExpression: Expression?
    /**
     * Get the FillExtrusionTranslate property as an Expression
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-translate")?.let {
        return it
      }
      fillExtrusionTranslate?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the FillExtrusionTranslate property
   *
   * @param fillExtrusionTranslate value of fillExtrusionTranslate as Expression
   */
  override fun fillExtrusionTranslate(fillExtrusionTranslate: Expression) = apply {
    val propertyValue = PropertyValue("fill-extrusion-translate", fillExtrusionTranslate)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillExtrusionTranslate.
   */
  val fillExtrusionTranslateTransition: StyleTransition?
    /**
     * Get the FillExtrusionTranslate property transition options
     *
     * @return transition options for List<Double>
     */
    get() {
      return getPropertyValue("fill-extrusion-translate-transition")
    }

  /**
   * Set the FillExtrusionTranslate property transition options
   *
   * @param options transition options for List<Double>
   */
  override fun fillExtrusionTranslateTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("fill-extrusion-translate-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillExtrusionTranslateTransition].
   */
  override fun fillExtrusionTranslateTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    fillExtrusionTranslateTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Controls the frame of reference for `fill-extrusion-translate`.
   */
  val fillExtrusionTranslateAnchor: FillExtrusionTranslateAnchor?
    /**
     * Get the FillExtrusionTranslateAnchor property
     *
     * @return FillExtrusionTranslateAnchor
     */
    get() {
      getPropertyValue<String?>("fill-extrusion-translate-anchor")?.let {
        return FillExtrusionTranslateAnchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Set the FillExtrusionTranslateAnchor property
   *
   * @param fillExtrusionTranslateAnchor value of fillExtrusionTranslateAnchor
   */
  override fun fillExtrusionTranslateAnchor(fillExtrusionTranslateAnchor: FillExtrusionTranslateAnchor) = apply {
    val propertyValue = PropertyValue("fill-extrusion-translate-anchor", fillExtrusionTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "fill-extrusion-translate-anchor".
   *
   * Controls the frame of reference for `fill-extrusion-translate`.
   */
  val fillExtrusionTranslateAnchorAsExpression: Expression?
    /**
     * Get the FillExtrusionTranslateAnchor property as an Expression
     *
     * @return FillExtrusionTranslateAnchor
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-translate-anchor")?.let {
        return it
      }
      fillExtrusionTranslateAnchor?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Set the FillExtrusionTranslateAnchor property
   *
   * @param fillExtrusionTranslateAnchor value of fillExtrusionTranslateAnchor as Expression
   */
  override fun fillExtrusionTranslateAnchor(fillExtrusionTranslateAnchor: Expression) = apply {
    val propertyValue = PropertyValue("fill-extrusion-translate-anchor", fillExtrusionTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down.
   */
  val fillExtrusionVerticalGradient: Boolean?
    /**
     * Get the FillExtrusionVerticalGradient property
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("fill-extrusion-vertical-gradient")
    }

  /**
   * Set the FillExtrusionVerticalGradient property
   *
   * @param fillExtrusionVerticalGradient value of fillExtrusionVerticalGradient
   */
  override fun fillExtrusionVerticalGradient(fillExtrusionVerticalGradient: Boolean) = apply {
    val propertyValue = PropertyValue("fill-extrusion-vertical-gradient", fillExtrusionVerticalGradient)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "fill-extrusion-vertical-gradient".
   *
   * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down.
   */
  val fillExtrusionVerticalGradientAsExpression: Expression?
    /**
     * Get the FillExtrusionVerticalGradient property as an Expression
     *
     * @return Boolean
     */
    get() {
      getPropertyValue<Expression>("fill-extrusion-vertical-gradient")?.let {
        return it
      }
      fillExtrusionVerticalGradient?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the FillExtrusionVerticalGradient property
   *
   * @param fillExtrusionVerticalGradient value of fillExtrusionVerticalGradient as Expression
   */
  override fun fillExtrusionVerticalGradient(fillExtrusionVerticalGradient: Expression) = apply {
    val propertyValue = PropertyValue("fill-extrusion-vertical-gradient", fillExtrusionVerticalGradient)
    setProperty(propertyValue)
  }

  /**
   * Get the type of this layer
   *
   * @return Type of the layer as [String]
   */
  override fun getType(): String {
    return "fill-extrusion"
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
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "visibility").silentUnwrap<String>()?.let {
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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "minzoom").silentUnwrap()

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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "maxzoom").silentUnwrap()

    /**
     * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
     */
    val defaultFillExtrusionBase: Double?
      /**
       * Get the default value of FillExtrusionBase property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-base").silentUnwrap()
      }

    /**
     * This is an Expression representation of "fill-extrusion-base".
     *
     * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
     */
    val defaultFillExtrusionBaseAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionBase property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-base").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionBase?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionBase.
     */
    val defaultFillExtrusionBaseTransition: StyleTransition?
      /**
       * Get the FillExtrusionBase property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-base-transition").silentUnwrap()

    /**
     * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
     */
    val defaultFillExtrusionColor: String?
      /**
       * Get the default value of FillExtrusionColor property
       *
       * @return String
       */
      get() {
        defaultFillExtrusionColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * This is an Expression representation of "fill-extrusion-color".
     *
     * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
     */
    val defaultFillExtrusionColorAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
     */
    val defaultFillExtrusionColorAsColorInt: Int?
      /**
       * Get the default value of FillExtrusionColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultFillExtrusionColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionColor.
     */
    val defaultFillExtrusionColorTransition: StyleTransition?
      /**
       * Get the FillExtrusionColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-color-transition").silentUnwrap()

    /**
     * The height with which to extrude this layer.
     */
    val defaultFillExtrusionHeight: Double?
      /**
       * Get the default value of FillExtrusionHeight property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-height").silentUnwrap()
      }

    /**
     * This is an Expression representation of "fill-extrusion-height".
     *
     * The height with which to extrude this layer.
     */
    val defaultFillExtrusionHeightAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionHeight property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-height").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionHeight?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionHeight.
     */
    val defaultFillExtrusionHeightTransition: StyleTransition?
      /**
       * Get the FillExtrusionHeight property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-height-transition").silentUnwrap()

    /**
     * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
     */
    val defaultFillExtrusionOpacity: Double?
      /**
       * Get the default value of FillExtrusionOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-opacity").silentUnwrap()
      }

    /**
     * This is an Expression representation of "fill-extrusion-opacity".
     *
     * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
     */
    val defaultFillExtrusionOpacityAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionOpacity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-opacity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionOpacity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionOpacity.
     */
    val defaultFillExtrusionOpacityTransition: StyleTransition?
      /**
       * Get the FillExtrusionOpacity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-opacity-transition").silentUnwrap()

    /**
     * Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     */
    val defaultFillExtrusionPattern: String?
      /**
       * Get the default value of FillExtrusionPattern property
       *
       * @return String
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-pattern").silentUnwrap()
      }

    /**
     * This is an Expression representation of "fill-extrusion-pattern".
     *
     * Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     */
    val defaultFillExtrusionPatternAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionPattern property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-pattern").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionPattern?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionPattern.
     */
    val defaultFillExtrusionPatternTransition: StyleTransition?
      /**
       * Get the FillExtrusionPattern property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-pattern-transition").silentUnwrap()

    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
     */
    val defaultFillExtrusionTranslate: List<Double>?
      /**
       * Get the default value of FillExtrusionTranslate property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-translate").silentUnwrap()
      }

    /**
     * This is an Expression representation of "fill-extrusion-translate".
     *
     * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
     */
    val defaultFillExtrusionTranslateAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionTranslate property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-translate").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionTranslate?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillExtrusionTranslate.
     */
    val defaultFillExtrusionTranslateTransition: StyleTransition?
      /**
       * Get the FillExtrusionTranslate property transition options
       *
       * @return transition options for List<Double>
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-translate-transition").silentUnwrap()

    /**
     * Controls the frame of reference for `fill-extrusion-translate`.
     */
    val defaultFillExtrusionTranslateAnchor: FillExtrusionTranslateAnchor?
      /**
       * Get the default value of FillExtrusionTranslateAnchor property
       *
       * @return FillExtrusionTranslateAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-translate-anchor").silentUnwrap<String>()?.let {
          return FillExtrusionTranslateAnchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * This is an Expression representation of "fill-extrusion-translate-anchor".
     *
     * Controls the frame of reference for `fill-extrusion-translate`.
     */
    val defaultFillExtrusionTranslateAnchorAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionTranslateAnchor property as an Expression
       *
       * @return FillExtrusionTranslateAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-translate-anchor").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionTranslateAnchor?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down.
     */
    val defaultFillExtrusionVerticalGradient: Boolean?
      /**
       * Get the default value of FillExtrusionVerticalGradient property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-vertical-gradient").silentUnwrap()
      }

    /**
     * This is an Expression representation of "fill-extrusion-vertical-gradient".
     *
     * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down.
     */
    val defaultFillExtrusionVerticalGradientAsExpression: Expression?
      /**
       * Get default value of the FillExtrusionVerticalGradient property as an Expression
       *
       * @return Boolean
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill-extrusion", "fill-extrusion-vertical-gradient").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillExtrusionVerticalGradient?.let {
          return Expression.literal(it)
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
interface FillExtrusionLayerDsl {
  /**
   * Set the sourceLayer property
   *
   * @param sourceLayer value of sourceLayer
   */
  fun sourceLayer(sourceLayer: String): FillExtrusionLayer

  /**
   * Set the filter property
   *
   * @param filter the expression filter to set
   */
  fun filter(filter: Expression): FillExtrusionLayer

  /**
   * Set the Visibility property
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): FillExtrusionLayer

  /**
   * Set the minzoom property
   *
   * @param value value of minzoom
   */
  fun minZoom(minZoom: Double): FillExtrusionLayer

  /**
   * Set the maxzoom property
   *
   * @param value value of maxzoom
   */
  fun maxZoom(maxZoom: Double): FillExtrusionLayer

  // Property getters and setters

  /**
   * Set the FillExtrusionBase property
   *
   * @param fillExtrusionBase value of fillExtrusionBase
   */
  fun fillExtrusionBase(fillExtrusionBase: Double = 0.0): FillExtrusionLayer

  /**
   * Set the FillExtrusionBase property
   *
   * @param fillExtrusionBase value of fillExtrusionBase as Expression
   */
  fun fillExtrusionBase(fillExtrusionBase: Expression): FillExtrusionLayer

  /**
   * Set the FillExtrusionBase property transition options
   *
   * @param options transition options for Double
   */
  fun fillExtrusionBaseTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * DSL for [fillExtrusionBaseTransition].
   */
  fun fillExtrusionBaseTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * Set the FillExtrusionColor property
   *
   * @param fillExtrusionColor value of fillExtrusionColor
   */
  fun fillExtrusionColor(fillExtrusionColor: String = "#000000"): FillExtrusionLayer

  /**
   * Set the FillExtrusionColor property
   *
   * @param fillExtrusionColor value of fillExtrusionColor as Expression
   */
  fun fillExtrusionColor(fillExtrusionColor: Expression): FillExtrusionLayer

  /**
   * Set the FillExtrusionColor property.
   *
   * @param fillExtrusionColor value of fillExtrusionColor
   */
  fun fillExtrusionColor(@ColorInt fillExtrusionColor: Int): FillExtrusionLayer

  /**
   * Set the FillExtrusionColor property transition options
   *
   * @param options transition options for String
   */
  fun fillExtrusionColorTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * DSL for [fillExtrusionColorTransition].
   */
  fun fillExtrusionColorTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * Set the FillExtrusionHeight property
   *
   * @param fillExtrusionHeight value of fillExtrusionHeight
   */
  fun fillExtrusionHeight(fillExtrusionHeight: Double = 0.0): FillExtrusionLayer

  /**
   * Set the FillExtrusionHeight property
   *
   * @param fillExtrusionHeight value of fillExtrusionHeight as Expression
   */
  fun fillExtrusionHeight(fillExtrusionHeight: Expression): FillExtrusionLayer

  /**
   * Set the FillExtrusionHeight property transition options
   *
   * @param options transition options for Double
   */
  fun fillExtrusionHeightTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * DSL for [fillExtrusionHeightTransition].
   */
  fun fillExtrusionHeightTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * Set the FillExtrusionOpacity property
   *
   * @param fillExtrusionOpacity value of fillExtrusionOpacity
   */
  fun fillExtrusionOpacity(fillExtrusionOpacity: Double = 1.0): FillExtrusionLayer

  /**
   * Set the FillExtrusionOpacity property
   *
   * @param fillExtrusionOpacity value of fillExtrusionOpacity as Expression
   */
  fun fillExtrusionOpacity(fillExtrusionOpacity: Expression): FillExtrusionLayer

  /**
   * Set the FillExtrusionOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun fillExtrusionOpacityTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * DSL for [fillExtrusionOpacityTransition].
   */
  fun fillExtrusionOpacityTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * Set the FillExtrusionPattern property
   *
   * @param fillExtrusionPattern value of fillExtrusionPattern
   */
  fun fillExtrusionPattern(fillExtrusionPattern: String): FillExtrusionLayer

  /**
   * Set the FillExtrusionPattern property
   *
   * @param fillExtrusionPattern value of fillExtrusionPattern as Expression
   */
  fun fillExtrusionPattern(fillExtrusionPattern: Expression): FillExtrusionLayer

  /**
   * Set the FillExtrusionPattern property transition options
   *
   * @param options transition options for String
   */
  fun fillExtrusionPatternTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * DSL for [fillExtrusionPatternTransition].
   */
  fun fillExtrusionPatternTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * Set the FillExtrusionTranslate property
   *
   * @param fillExtrusionTranslate value of fillExtrusionTranslate
   */
  fun fillExtrusionTranslate(fillExtrusionTranslate: List<Double> = listOf(0.0, 0.0)): FillExtrusionLayer

  /**
   * Set the FillExtrusionTranslate property
   *
   * @param fillExtrusionTranslate value of fillExtrusionTranslate as Expression
   */
  fun fillExtrusionTranslate(fillExtrusionTranslate: Expression): FillExtrusionLayer

  /**
   * Set the FillExtrusionTranslate property transition options
   *
   * @param options transition options for List<Double>
   */
  fun fillExtrusionTranslateTransition(options: StyleTransition): FillExtrusionLayer

  /**
   * DSL for [fillExtrusionTranslateTransition].
   */
  fun fillExtrusionTranslateTransition(block: StyleTransition.Builder.() -> Unit): FillExtrusionLayer

  /**
   * Set the FillExtrusionTranslateAnchor property
   *
   * @param fillExtrusionTranslateAnchor value of fillExtrusionTranslateAnchor
   */
  fun fillExtrusionTranslateAnchor(fillExtrusionTranslateAnchor: FillExtrusionTranslateAnchor = FillExtrusionTranslateAnchor.MAP): FillExtrusionLayer

  /**
   * Set the FillExtrusionTranslateAnchor property
   *
   * @param fillExtrusionTranslateAnchor value of fillExtrusionTranslateAnchor as Expression
   */
  fun fillExtrusionTranslateAnchor(fillExtrusionTranslateAnchor: Expression): FillExtrusionLayer

  /**
   * Set the FillExtrusionVerticalGradient property
   *
   * @param fillExtrusionVerticalGradient value of fillExtrusionVerticalGradient
   */
  fun fillExtrusionVerticalGradient(fillExtrusionVerticalGradient: Boolean = true): FillExtrusionLayer

  /**
   * Set the FillExtrusionVerticalGradient property
   *
   * @param fillExtrusionVerticalGradient value of fillExtrusionVerticalGradient as Expression
   */
  fun fillExtrusionVerticalGradient(fillExtrusionVerticalGradient: Expression): FillExtrusionLayer
}

/**
 * DSL function for [FillExtrusionLayer].
 */
fun fillExtrusionLayer(layerId: String, sourceId: String, block: FillExtrusionLayerDsl.() -> Unit): FillExtrusionLayer = FillExtrusionLayer(layerId, sourceId).apply(block)

// End of generated file.