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
 * A filled circle.
 *
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-circle)
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
 */
@UiThread
class CircleLayer(override val layerId: String, val sourceId: String) : CircleLayerDsl, Layer() {
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
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  val circleSortKey: Double?
    /**
     * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("circle-sort-key")
    }

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * @param circleSortKey value of circleSortKey
   */
  override fun circleSortKey(circleSortKey: Double) = apply {
    val propertyValue = PropertyValue("circle-sort-key", circleSortKey)
    setProperty(propertyValue)
  }

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * This is an Expression representation of "circle-sort-key".
   *
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  val circleSortKeyAsExpression: Expression?
    /**
     * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
     *
     * Get the CircleSortKey property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("circle-sort-key")?.let {
        return it
      }
      circleSortKey?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * @param circleSortKey value of circleSortKey as Expression
   */
  override fun circleSortKey(circleSortKey: Expression) = apply {
    val propertyValue = PropertyValue("circle-sort-key", circleSortKey)
    setProperty(propertyValue)
  }

  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
   */
  val circleBlur: Double?
    /**
     * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("circle-blur")
    }

  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
   *
   * @param circleBlur value of circleBlur
   */
  override fun circleBlur(circleBlur: Double) = apply {
    val propertyValue = PropertyValue("circle-blur", circleBlur)
    setProperty(propertyValue)
  }

  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
   *
   * This is an Expression representation of "circle-blur".
   *
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
   */
  val circleBlurAsExpression: Expression?
    /**
     * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
     *
     * Get the CircleBlur property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("circle-blur")?.let {
        return it
      }
      circleBlur?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
   *
   * @param circleBlur value of circleBlur as Expression
   */
  override fun circleBlur(circleBlur: Expression) = apply {
    val propertyValue = PropertyValue("circle-blur", circleBlur)
    setProperty(propertyValue)
  }

  /**
   * Transition options for CircleBlur.
   */
  val circleBlurTransition: StyleTransition?
    /**
     * Get the CircleBlur property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("circle-blur-transition")
    }

  /**
   * Set the CircleBlur property transition options
   *
   * @param options transition options for Double
   */
  override fun circleBlurTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("circle-blur-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [circleBlurTransition].
   */
  override fun circleBlurTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    circleBlurTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The fill color of the circle.
   */
  val circleColor: String?
    /**
     * The fill color of the circle.
     *
     * @return String
     */
    get() {
      circleColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The fill color of the circle.
   *
   * @param circleColor value of circleColor
   */
  override fun circleColor(circleColor: String) = apply {
    val propertyValue = PropertyValue("circle-color", circleColor)
    setProperty(propertyValue)
  }

  /**
   * The fill color of the circle.
   *
   * This is an Expression representation of "circle-color".
   *
   * The fill color of the circle.
   */
  val circleColorAsExpression: Expression?
    /**
     * The fill color of the circle.
     *
     * Get the CircleColor property as an Expression
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("circle-color")?.let {
        return it
      }
      return null
    }

  /**
   * The fill color of the circle.
   *
   * @param circleColor value of circleColor as Expression
   */
  override fun circleColor(circleColor: Expression) = apply {
    val propertyValue = PropertyValue("circle-color", circleColor)
    setProperty(propertyValue)
  }

  /**
   * The fill color of the circle.
   */
  val circleColorAsColorInt: Int?
    /**
     * The fill color of the circle.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      circleColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The fill color of the circle.
   *
   * @param circleColor value of circleColor
   */
  override fun circleColor(@ColorInt circleColor: Int) = apply {
    val propertyValue = PropertyValue("circle-color", colorIntToRgbaExpression(circleColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for CircleColor.
   */
  val circleColorTransition: StyleTransition?
    /**
     * Get the CircleColor property transition options
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("circle-color-transition")
    }

  /**
   * Set the CircleColor property transition options
   *
   * @param options transition options for String
   */
  override fun circleColorTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("circle-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [circleColorTransition].
   */
  override fun circleColorTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    circleColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity at which the circle will be drawn.
   */
  val circleOpacity: Double?
    /**
     * The opacity at which the circle will be drawn.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("circle-opacity")
    }

  /**
   * The opacity at which the circle will be drawn.
   *
   * @param circleOpacity value of circleOpacity
   */
  override fun circleOpacity(circleOpacity: Double) = apply {
    val propertyValue = PropertyValue("circle-opacity", circleOpacity)
    setProperty(propertyValue)
  }

  /**
   * The opacity at which the circle will be drawn.
   *
   * This is an Expression representation of "circle-opacity".
   *
   * The opacity at which the circle will be drawn.
   */
  val circleOpacityAsExpression: Expression?
    /**
     * The opacity at which the circle will be drawn.
     *
     * Get the CircleOpacity property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("circle-opacity")?.let {
        return it
      }
      circleOpacity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The opacity at which the circle will be drawn.
   *
   * @param circleOpacity value of circleOpacity as Expression
   */
  override fun circleOpacity(circleOpacity: Expression) = apply {
    val propertyValue = PropertyValue("circle-opacity", circleOpacity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for CircleOpacity.
   */
  val circleOpacityTransition: StyleTransition?
    /**
     * Get the CircleOpacity property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("circle-opacity-transition")
    }

  /**
   * Set the CircleOpacity property transition options
   *
   * @param options transition options for Double
   */
  override fun circleOpacityTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("circle-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [circleOpacityTransition].
   */
  override fun circleOpacityTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    circleOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Orientation of circle when map is pitched.
   */
  val circlePitchAlignment: CirclePitchAlignment?
    /**
     * Orientation of circle when map is pitched.
     *
     * @return CirclePitchAlignment
     */
    get() {
      getPropertyValue<String?>("circle-pitch-alignment")?.let {
        return CirclePitchAlignment.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Orientation of circle when map is pitched.
   *
   * @param circlePitchAlignment value of circlePitchAlignment
   */
  override fun circlePitchAlignment(circlePitchAlignment: CirclePitchAlignment) = apply {
    val propertyValue = PropertyValue("circle-pitch-alignment", circlePitchAlignment)
    setProperty(propertyValue)
  }

  /**
   * Orientation of circle when map is pitched.
   *
   * This is an Expression representation of "circle-pitch-alignment".
   *
   * Orientation of circle when map is pitched.
   */
  val circlePitchAlignmentAsExpression: Expression?
    /**
     * Orientation of circle when map is pitched.
     *
     * Get the CirclePitchAlignment property as an Expression
     *
     * @return CirclePitchAlignment
     */
    get() {
      getPropertyValue<Expression>("circle-pitch-alignment")?.let {
        return it
      }
      circlePitchAlignment?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Orientation of circle when map is pitched.
   *
   * @param circlePitchAlignment value of circlePitchAlignment as Expression
   */
  override fun circlePitchAlignment(circlePitchAlignment: Expression) = apply {
    val propertyValue = PropertyValue("circle-pitch-alignment", circlePitchAlignment)
    setProperty(propertyValue)
  }

  /**
   * Controls the scaling behavior of the circle when the map is pitched.
   */
  val circlePitchScale: CirclePitchScale?
    /**
     * Controls the scaling behavior of the circle when the map is pitched.
     *
     * @return CirclePitchScale
     */
    get() {
      getPropertyValue<String?>("circle-pitch-scale")?.let {
        return CirclePitchScale.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Controls the scaling behavior of the circle when the map is pitched.
   *
   * @param circlePitchScale value of circlePitchScale
   */
  override fun circlePitchScale(circlePitchScale: CirclePitchScale) = apply {
    val propertyValue = PropertyValue("circle-pitch-scale", circlePitchScale)
    setProperty(propertyValue)
  }

  /**
   * Controls the scaling behavior of the circle when the map is pitched.
   *
   * This is an Expression representation of "circle-pitch-scale".
   *
   * Controls the scaling behavior of the circle when the map is pitched.
   */
  val circlePitchScaleAsExpression: Expression?
    /**
     * Controls the scaling behavior of the circle when the map is pitched.
     *
     * Get the CirclePitchScale property as an Expression
     *
     * @return CirclePitchScale
     */
    get() {
      getPropertyValue<Expression>("circle-pitch-scale")?.let {
        return it
      }
      circlePitchScale?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Controls the scaling behavior of the circle when the map is pitched.
   *
   * @param circlePitchScale value of circlePitchScale as Expression
   */
  override fun circlePitchScale(circlePitchScale: Expression) = apply {
    val propertyValue = PropertyValue("circle-pitch-scale", circlePitchScale)
    setProperty(propertyValue)
  }

  /**
   * Circle radius.
   */
  val circleRadius: Double?
    /**
     * Circle radius.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("circle-radius")
    }

  /**
   * Circle radius.
   *
   * @param circleRadius value of circleRadius
   */
  override fun circleRadius(circleRadius: Double) = apply {
    val propertyValue = PropertyValue("circle-radius", circleRadius)
    setProperty(propertyValue)
  }

  /**
   * Circle radius.
   *
   * This is an Expression representation of "circle-radius".
   *
   * Circle radius.
   */
  val circleRadiusAsExpression: Expression?
    /**
     * Circle radius.
     *
     * Get the CircleRadius property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("circle-radius")?.let {
        return it
      }
      circleRadius?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Circle radius.
   *
   * @param circleRadius value of circleRadius as Expression
   */
  override fun circleRadius(circleRadius: Expression) = apply {
    val propertyValue = PropertyValue("circle-radius", circleRadius)
    setProperty(propertyValue)
  }

  /**
   * Transition options for CircleRadius.
   */
  val circleRadiusTransition: StyleTransition?
    /**
     * Get the CircleRadius property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("circle-radius-transition")
    }

  /**
   * Set the CircleRadius property transition options
   *
   * @param options transition options for Double
   */
  override fun circleRadiusTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("circle-radius-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [circleRadiusTransition].
   */
  override fun circleRadiusTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    circleRadiusTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The stroke color of the circle.
   */
  val circleStrokeColor: String?
    /**
     * The stroke color of the circle.
     *
     * @return String
     */
    get() {
      circleStrokeColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The stroke color of the circle.
   *
   * @param circleStrokeColor value of circleStrokeColor
   */
  override fun circleStrokeColor(circleStrokeColor: String) = apply {
    val propertyValue = PropertyValue("circle-stroke-color", circleStrokeColor)
    setProperty(propertyValue)
  }

  /**
   * The stroke color of the circle.
   *
   * This is an Expression representation of "circle-stroke-color".
   *
   * The stroke color of the circle.
   */
  val circleStrokeColorAsExpression: Expression?
    /**
     * The stroke color of the circle.
     *
     * Get the CircleStrokeColor property as an Expression
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("circle-stroke-color")?.let {
        return it
      }
      return null
    }

  /**
   * The stroke color of the circle.
   *
   * @param circleStrokeColor value of circleStrokeColor as Expression
   */
  override fun circleStrokeColor(circleStrokeColor: Expression) = apply {
    val propertyValue = PropertyValue("circle-stroke-color", circleStrokeColor)
    setProperty(propertyValue)
  }

  /**
   * The stroke color of the circle.
   */
  val circleStrokeColorAsColorInt: Int?
    /**
     * The stroke color of the circle.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      circleStrokeColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The stroke color of the circle.
   *
   * @param circleStrokeColor value of circleStrokeColor
   */
  override fun circleStrokeColor(@ColorInt circleStrokeColor: Int) = apply {
    val propertyValue = PropertyValue("circle-stroke-color", colorIntToRgbaExpression(circleStrokeColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for CircleStrokeColor.
   */
  val circleStrokeColorTransition: StyleTransition?
    /**
     * Get the CircleStrokeColor property transition options
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("circle-stroke-color-transition")
    }

  /**
   * Set the CircleStrokeColor property transition options
   *
   * @param options transition options for String
   */
  override fun circleStrokeColorTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("circle-stroke-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [circleStrokeColorTransition].
   */
  override fun circleStrokeColorTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    circleStrokeColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity of the circle's stroke.
   */
  val circleStrokeOpacity: Double?
    /**
     * The opacity of the circle's stroke.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("circle-stroke-opacity")
    }

  /**
   * The opacity of the circle's stroke.
   *
   * @param circleStrokeOpacity value of circleStrokeOpacity
   */
  override fun circleStrokeOpacity(circleStrokeOpacity: Double) = apply {
    val propertyValue = PropertyValue("circle-stroke-opacity", circleStrokeOpacity)
    setProperty(propertyValue)
  }

  /**
   * The opacity of the circle's stroke.
   *
   * This is an Expression representation of "circle-stroke-opacity".
   *
   * The opacity of the circle's stroke.
   */
  val circleStrokeOpacityAsExpression: Expression?
    /**
     * The opacity of the circle's stroke.
     *
     * Get the CircleStrokeOpacity property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("circle-stroke-opacity")?.let {
        return it
      }
      circleStrokeOpacity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The opacity of the circle's stroke.
   *
   * @param circleStrokeOpacity value of circleStrokeOpacity as Expression
   */
  override fun circleStrokeOpacity(circleStrokeOpacity: Expression) = apply {
    val propertyValue = PropertyValue("circle-stroke-opacity", circleStrokeOpacity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for CircleStrokeOpacity.
   */
  val circleStrokeOpacityTransition: StyleTransition?
    /**
     * Get the CircleStrokeOpacity property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("circle-stroke-opacity-transition")
    }

  /**
   * Set the CircleStrokeOpacity property transition options
   *
   * @param options transition options for Double
   */
  override fun circleStrokeOpacityTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("circle-stroke-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [circleStrokeOpacityTransition].
   */
  override fun circleStrokeOpacityTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    circleStrokeOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
   */
  val circleStrokeWidth: Double?
    /**
     * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("circle-stroke-width")
    }

  /**
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
   *
   * @param circleStrokeWidth value of circleStrokeWidth
   */
  override fun circleStrokeWidth(circleStrokeWidth: Double) = apply {
    val propertyValue = PropertyValue("circle-stroke-width", circleStrokeWidth)
    setProperty(propertyValue)
  }

  /**
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
   *
   * This is an Expression representation of "circle-stroke-width".
   *
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
   */
  val circleStrokeWidthAsExpression: Expression?
    /**
     * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
     *
     * Get the CircleStrokeWidth property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("circle-stroke-width")?.let {
        return it
      }
      circleStrokeWidth?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
   *
   * @param circleStrokeWidth value of circleStrokeWidth as Expression
   */
  override fun circleStrokeWidth(circleStrokeWidth: Expression) = apply {
    val propertyValue = PropertyValue("circle-stroke-width", circleStrokeWidth)
    setProperty(propertyValue)
  }

  /**
   * Transition options for CircleStrokeWidth.
   */
  val circleStrokeWidthTransition: StyleTransition?
    /**
     * Get the CircleStrokeWidth property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("circle-stroke-width-transition")
    }

  /**
   * Set the CircleStrokeWidth property transition options
   *
   * @param options transition options for Double
   */
  override fun circleStrokeWidthTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("circle-stroke-width-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [circleStrokeWidthTransition].
   */
  override fun circleStrokeWidthTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    circleStrokeWidthTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   */
  val circleTranslate: List<Double>?
    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("circle-translate")
    }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * @param circleTranslate value of circleTranslate
   */
  override fun circleTranslate(circleTranslate: List<Double>) = apply {
    val propertyValue = PropertyValue("circle-translate", circleTranslate)
    setProperty(propertyValue)
  }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * This is an Expression representation of "circle-translate".
   *
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   */
  val circleTranslateAsExpression: Expression?
    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
     *
     * Get the CircleTranslate property as an Expression
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("circle-translate")?.let {
        return it
      }
      circleTranslate?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * @param circleTranslate value of circleTranslate as Expression
   */
  override fun circleTranslate(circleTranslate: Expression) = apply {
    val propertyValue = PropertyValue("circle-translate", circleTranslate)
    setProperty(propertyValue)
  }

  /**
   * Transition options for CircleTranslate.
   */
  val circleTranslateTransition: StyleTransition?
    /**
     * Get the CircleTranslate property transition options
     *
     * @return transition options for List<Double>
     */
    get() {
      return getPropertyValue("circle-translate-transition")
    }

  /**
   * Set the CircleTranslate property transition options
   *
   * @param options transition options for List<Double>
   */
  override fun circleTranslateTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("circle-translate-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [circleTranslateTransition].
   */
  override fun circleTranslateTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    circleTranslateTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Controls the frame of reference for `circle-translate`.
   */
  val circleTranslateAnchor: CircleTranslateAnchor?
    /**
     * Controls the frame of reference for `circle-translate`.
     *
     * @return CircleTranslateAnchor
     */
    get() {
      getPropertyValue<String?>("circle-translate-anchor")?.let {
        return CircleTranslateAnchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Controls the frame of reference for `circle-translate`.
   *
   * @param circleTranslateAnchor value of circleTranslateAnchor
   */
  override fun circleTranslateAnchor(circleTranslateAnchor: CircleTranslateAnchor) = apply {
    val propertyValue = PropertyValue("circle-translate-anchor", circleTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * Controls the frame of reference for `circle-translate`.
   *
   * This is an Expression representation of "circle-translate-anchor".
   *
   * Controls the frame of reference for `circle-translate`.
   */
  val circleTranslateAnchorAsExpression: Expression?
    /**
     * Controls the frame of reference for `circle-translate`.
     *
     * Get the CircleTranslateAnchor property as an Expression
     *
     * @return CircleTranslateAnchor
     */
    get() {
      getPropertyValue<Expression>("circle-translate-anchor")?.let {
        return it
      }
      circleTranslateAnchor?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Controls the frame of reference for `circle-translate`.
   *
   * @param circleTranslateAnchor value of circleTranslateAnchor as Expression
   */
  override fun circleTranslateAnchor(circleTranslateAnchor: Expression) = apply {
    val propertyValue = PropertyValue("circle-translate-anchor", circleTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * Get the type of this layer
   *
   * @return Type of the layer as [String]
   */
  override fun getType(): String {
    return "circle"
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
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "visibility").silentUnwrap<String>()?.let {
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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("circle", "minzoom").silentUnwrap()

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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("circle", "maxzoom").silentUnwrap()

    /**
     * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
     */
    val defaultCircleSortKey: Double?
      /**
       * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
       *
       * Get the default value of CircleSortKey property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-sort-key").silentUnwrap()
      }

    /**
     * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
     *
     * This is an Expression representation of "circle-sort-key".
     *
     * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
     */
    val defaultCircleSortKeyAsExpression: Expression?
      /**
       * Get default value of the CircleSortKey property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-sort-key").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultCircleSortKey?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
     */
    val defaultCircleBlur: Double?
      /**
       * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
       *
       * Get the default value of CircleBlur property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-blur").silentUnwrap()
      }

    /**
     * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
     *
     * This is an Expression representation of "circle-blur".
     *
     * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
     */
    val defaultCircleBlurAsExpression: Expression?
      /**
       * Get default value of the CircleBlur property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-blur").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultCircleBlur?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for CircleBlur.
     */
    val defaultCircleBlurTransition: StyleTransition?
      /**
       * Get the CircleBlur property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-blur-transition").silentUnwrap()

    /**
     * The fill color of the circle.
     */
    val defaultCircleColor: String?
      /**
       * The fill color of the circle.
       *
       * Get the default value of CircleColor property
       *
       * @return String
       */
      get() {
        defaultCircleColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The fill color of the circle.
     *
     * This is an Expression representation of "circle-color".
     *
     * The fill color of the circle.
     */
    val defaultCircleColorAsExpression: Expression?
      /**
       * Get default value of the CircleColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The fill color of the circle.
     */
    val defaultCircleColorAsColorInt: Int?
      /**
       * The fill color of the circle.
       *
       * Get the default value of CircleColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultCircleColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for CircleColor.
     */
    val defaultCircleColorTransition: StyleTransition?
      /**
       * Get the CircleColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-color-transition").silentUnwrap()

    /**
     * The opacity at which the circle will be drawn.
     */
    val defaultCircleOpacity: Double?
      /**
       * The opacity at which the circle will be drawn.
       *
       * Get the default value of CircleOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-opacity").silentUnwrap()
      }

    /**
     * The opacity at which the circle will be drawn.
     *
     * This is an Expression representation of "circle-opacity".
     *
     * The opacity at which the circle will be drawn.
     */
    val defaultCircleOpacityAsExpression: Expression?
      /**
       * Get default value of the CircleOpacity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-opacity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultCircleOpacity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for CircleOpacity.
     */
    val defaultCircleOpacityTransition: StyleTransition?
      /**
       * Get the CircleOpacity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-opacity-transition").silentUnwrap()

    /**
     * Orientation of circle when map is pitched.
     */
    val defaultCirclePitchAlignment: CirclePitchAlignment?
      /**
       * Orientation of circle when map is pitched.
       *
       * Get the default value of CirclePitchAlignment property
       *
       * @return CirclePitchAlignment
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-pitch-alignment").silentUnwrap<String>()?.let {
          return CirclePitchAlignment.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Orientation of circle when map is pitched.
     *
     * This is an Expression representation of "circle-pitch-alignment".
     *
     * Orientation of circle when map is pitched.
     */
    val defaultCirclePitchAlignmentAsExpression: Expression?
      /**
       * Get default value of the CirclePitchAlignment property as an Expression
       *
       * @return CirclePitchAlignment
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-pitch-alignment").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultCirclePitchAlignment?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * Controls the scaling behavior of the circle when the map is pitched.
     */
    val defaultCirclePitchScale: CirclePitchScale?
      /**
       * Controls the scaling behavior of the circle when the map is pitched.
       *
       * Get the default value of CirclePitchScale property
       *
       * @return CirclePitchScale
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-pitch-scale").silentUnwrap<String>()?.let {
          return CirclePitchScale.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Controls the scaling behavior of the circle when the map is pitched.
     *
     * This is an Expression representation of "circle-pitch-scale".
     *
     * Controls the scaling behavior of the circle when the map is pitched.
     */
    val defaultCirclePitchScaleAsExpression: Expression?
      /**
       * Get default value of the CirclePitchScale property as an Expression
       *
       * @return CirclePitchScale
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-pitch-scale").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultCirclePitchScale?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * Circle radius.
     */
    val defaultCircleRadius: Double?
      /**
       * Circle radius.
       *
       * Get the default value of CircleRadius property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-radius").silentUnwrap()
      }

    /**
     * Circle radius.
     *
     * This is an Expression representation of "circle-radius".
     *
     * Circle radius.
     */
    val defaultCircleRadiusAsExpression: Expression?
      /**
       * Get default value of the CircleRadius property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-radius").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultCircleRadius?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for CircleRadius.
     */
    val defaultCircleRadiusTransition: StyleTransition?
      /**
       * Get the CircleRadius property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-radius-transition").silentUnwrap()

    /**
     * The stroke color of the circle.
     */
    val defaultCircleStrokeColor: String?
      /**
       * The stroke color of the circle.
       *
       * Get the default value of CircleStrokeColor property
       *
       * @return String
       */
      get() {
        defaultCircleStrokeColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The stroke color of the circle.
     *
     * This is an Expression representation of "circle-stroke-color".
     *
     * The stroke color of the circle.
     */
    val defaultCircleStrokeColorAsExpression: Expression?
      /**
       * Get default value of the CircleStrokeColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The stroke color of the circle.
     */
    val defaultCircleStrokeColorAsColorInt: Int?
      /**
       * The stroke color of the circle.
       *
       * Get the default value of CircleStrokeColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultCircleStrokeColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for CircleStrokeColor.
     */
    val defaultCircleStrokeColorTransition: StyleTransition?
      /**
       * Get the CircleStrokeColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-color-transition").silentUnwrap()

    /**
     * The opacity of the circle's stroke.
     */
    val defaultCircleStrokeOpacity: Double?
      /**
       * The opacity of the circle's stroke.
       *
       * Get the default value of CircleStrokeOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-opacity").silentUnwrap()
      }

    /**
     * The opacity of the circle's stroke.
     *
     * This is an Expression representation of "circle-stroke-opacity".
     *
     * The opacity of the circle's stroke.
     */
    val defaultCircleStrokeOpacityAsExpression: Expression?
      /**
       * Get default value of the CircleStrokeOpacity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-opacity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultCircleStrokeOpacity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for CircleStrokeOpacity.
     */
    val defaultCircleStrokeOpacityTransition: StyleTransition?
      /**
       * Get the CircleStrokeOpacity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-opacity-transition").silentUnwrap()

    /**
     * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
     */
    val defaultCircleStrokeWidth: Double?
      /**
       * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
       *
       * Get the default value of CircleStrokeWidth property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-width").silentUnwrap()
      }

    /**
     * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
     *
     * This is an Expression representation of "circle-stroke-width".
     *
     * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
     */
    val defaultCircleStrokeWidthAsExpression: Expression?
      /**
       * Get default value of the CircleStrokeWidth property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-width").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultCircleStrokeWidth?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for CircleStrokeWidth.
     */
    val defaultCircleStrokeWidthTransition: StyleTransition?
      /**
       * Get the CircleStrokeWidth property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-width-transition").silentUnwrap()

    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
     */
    val defaultCircleTranslate: List<Double>?
      /**
       * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
       *
       * Get the default value of CircleTranslate property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate").silentUnwrap()
      }

    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
     *
     * This is an Expression representation of "circle-translate".
     *
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
     */
    val defaultCircleTranslateAsExpression: Expression?
      /**
       * Get default value of the CircleTranslate property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultCircleTranslate?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for CircleTranslate.
     */
    val defaultCircleTranslateTransition: StyleTransition?
      /**
       * Get the CircleTranslate property transition options
       *
       * @return transition options for List<Double>
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate-transition").silentUnwrap()

    /**
     * Controls the frame of reference for `circle-translate`.
     */
    val defaultCircleTranslateAnchor: CircleTranslateAnchor?
      /**
       * Controls the frame of reference for `circle-translate`.
       *
       * Get the default value of CircleTranslateAnchor property
       *
       * @return CircleTranslateAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate-anchor").silentUnwrap<String>()?.let {
          return CircleTranslateAnchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Controls the frame of reference for `circle-translate`.
     *
     * This is an Expression representation of "circle-translate-anchor".
     *
     * Controls the frame of reference for `circle-translate`.
     */
    val defaultCircleTranslateAnchorAsExpression: Expression?
      /**
       * Get default value of the CircleTranslateAnchor property as an Expression
       *
       * @return CircleTranslateAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate-anchor").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultCircleTranslateAnchor?.let {
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
interface CircleLayerDsl {
  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  fun sourceLayer(sourceLayer: String): CircleLayer

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
  fun filter(filter: Expression): CircleLayer

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): CircleLayer

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param value value of minzoom
   */
  fun minZoom(minZoom: Double): CircleLayer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param value value of maxzoom
   */
  fun maxZoom(maxZoom: Double): CircleLayer

  // Property getters and setters

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * @param circleSortKey value of circleSortKey
   */
  fun circleSortKey(circleSortKey: Double): CircleLayer

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * @param circleSortKey value of circleSortKey as Expression
   */
  fun circleSortKey(circleSortKey: Expression): CircleLayer

  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
   *
   * @param circleBlur value of circleBlur
   */
  fun circleBlur(circleBlur: Double = 0.0): CircleLayer

  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
   *
   * @param circleBlur value of circleBlur as Expression
   */
  fun circleBlur(circleBlur: Expression): CircleLayer

  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
   *
   * Set the CircleBlur property transition options
   *
   * @param options transition options for Double
   */
  fun circleBlurTransition(options: StyleTransition): CircleLayer

  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
   *
   * DSL for [circleBlurTransition].
   */
  fun circleBlurTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer

  /**
   * The fill color of the circle.
   *
   * @param circleColor value of circleColor
   */
  fun circleColor(circleColor: String = "#000000"): CircleLayer

  /**
   * The fill color of the circle.
   *
   * @param circleColor value of circleColor as Expression
   */
  fun circleColor(circleColor: Expression): CircleLayer

  /**
   * The fill color of the circle.
   *
   * @param circleColor value of circleColor
   */
  fun circleColor(@ColorInt circleColor: Int): CircleLayer

  /**
   * The fill color of the circle.
   *
   * Set the CircleColor property transition options
   *
   * @param options transition options for String
   */
  fun circleColorTransition(options: StyleTransition): CircleLayer

  /**
   * The fill color of the circle.
   *
   * DSL for [circleColorTransition].
   */
  fun circleColorTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer

  /**
   * The opacity at which the circle will be drawn.
   *
   * @param circleOpacity value of circleOpacity
   */
  fun circleOpacity(circleOpacity: Double = 1.0): CircleLayer

  /**
   * The opacity at which the circle will be drawn.
   *
   * @param circleOpacity value of circleOpacity as Expression
   */
  fun circleOpacity(circleOpacity: Expression): CircleLayer

  /**
   * The opacity at which the circle will be drawn.
   *
   * Set the CircleOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun circleOpacityTransition(options: StyleTransition): CircleLayer

  /**
   * The opacity at which the circle will be drawn.
   *
   * DSL for [circleOpacityTransition].
   */
  fun circleOpacityTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer

  /**
   * Orientation of circle when map is pitched.
   *
   * @param circlePitchAlignment value of circlePitchAlignment
   */
  fun circlePitchAlignment(circlePitchAlignment: CirclePitchAlignment = CirclePitchAlignment.VIEWPORT): CircleLayer

  /**
   * Orientation of circle when map is pitched.
   *
   * @param circlePitchAlignment value of circlePitchAlignment as Expression
   */
  fun circlePitchAlignment(circlePitchAlignment: Expression): CircleLayer

  /**
   * Controls the scaling behavior of the circle when the map is pitched.
   *
   * @param circlePitchScale value of circlePitchScale
   */
  fun circlePitchScale(circlePitchScale: CirclePitchScale = CirclePitchScale.MAP): CircleLayer

  /**
   * Controls the scaling behavior of the circle when the map is pitched.
   *
   * @param circlePitchScale value of circlePitchScale as Expression
   */
  fun circlePitchScale(circlePitchScale: Expression): CircleLayer

  /**
   * Circle radius.
   *
   * @param circleRadius value of circleRadius
   */
  fun circleRadius(circleRadius: Double = 5.0): CircleLayer

  /**
   * Circle radius.
   *
   * @param circleRadius value of circleRadius as Expression
   */
  fun circleRadius(circleRadius: Expression): CircleLayer

  /**
   * Circle radius.
   *
   * Set the CircleRadius property transition options
   *
   * @param options transition options for Double
   */
  fun circleRadiusTransition(options: StyleTransition): CircleLayer

  /**
   * Circle radius.
   *
   * DSL for [circleRadiusTransition].
   */
  fun circleRadiusTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer

  /**
   * The stroke color of the circle.
   *
   * @param circleStrokeColor value of circleStrokeColor
   */
  fun circleStrokeColor(circleStrokeColor: String = "#000000"): CircleLayer

  /**
   * The stroke color of the circle.
   *
   * @param circleStrokeColor value of circleStrokeColor as Expression
   */
  fun circleStrokeColor(circleStrokeColor: Expression): CircleLayer

  /**
   * The stroke color of the circle.
   *
   * @param circleStrokeColor value of circleStrokeColor
   */
  fun circleStrokeColor(@ColorInt circleStrokeColor: Int): CircleLayer

  /**
   * The stroke color of the circle.
   *
   * Set the CircleStrokeColor property transition options
   *
   * @param options transition options for String
   */
  fun circleStrokeColorTransition(options: StyleTransition): CircleLayer

  /**
   * The stroke color of the circle.
   *
   * DSL for [circleStrokeColorTransition].
   */
  fun circleStrokeColorTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer

  /**
   * The opacity of the circle's stroke.
   *
   * @param circleStrokeOpacity value of circleStrokeOpacity
   */
  fun circleStrokeOpacity(circleStrokeOpacity: Double = 1.0): CircleLayer

  /**
   * The opacity of the circle's stroke.
   *
   * @param circleStrokeOpacity value of circleStrokeOpacity as Expression
   */
  fun circleStrokeOpacity(circleStrokeOpacity: Expression): CircleLayer

  /**
   * The opacity of the circle's stroke.
   *
   * Set the CircleStrokeOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun circleStrokeOpacityTransition(options: StyleTransition): CircleLayer

  /**
   * The opacity of the circle's stroke.
   *
   * DSL for [circleStrokeOpacityTransition].
   */
  fun circleStrokeOpacityTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer

  /**
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
   *
   * @param circleStrokeWidth value of circleStrokeWidth
   */
  fun circleStrokeWidth(circleStrokeWidth: Double = 0.0): CircleLayer

  /**
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
   *
   * @param circleStrokeWidth value of circleStrokeWidth as Expression
   */
  fun circleStrokeWidth(circleStrokeWidth: Expression): CircleLayer

  /**
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
   *
   * Set the CircleStrokeWidth property transition options
   *
   * @param options transition options for Double
   */
  fun circleStrokeWidthTransition(options: StyleTransition): CircleLayer

  /**
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
   *
   * DSL for [circleStrokeWidthTransition].
   */
  fun circleStrokeWidthTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * @param circleTranslate value of circleTranslate
   */
  fun circleTranslate(circleTranslate: List<Double> = listOf(0.0, 0.0)): CircleLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * @param circleTranslate value of circleTranslate as Expression
   */
  fun circleTranslate(circleTranslate: Expression): CircleLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * Set the CircleTranslate property transition options
   *
   * @param options transition options for List<Double>
   */
  fun circleTranslateTransition(options: StyleTransition): CircleLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
   *
   * DSL for [circleTranslateTransition].
   */
  fun circleTranslateTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer

  /**
   * Controls the frame of reference for `circle-translate`.
   *
   * @param circleTranslateAnchor value of circleTranslateAnchor
   */
  fun circleTranslateAnchor(circleTranslateAnchor: CircleTranslateAnchor = CircleTranslateAnchor.MAP): CircleLayer

  /**
   * Controls the frame of reference for `circle-translate`.
   *
   * @param circleTranslateAnchor value of circleTranslateAnchor as Expression
   */
  fun circleTranslateAnchor(circleTranslateAnchor: Expression): CircleLayer
}

/**
 * DSL function for creating a [CircleLayer].
 */
fun circleLayer(layerId: String, sourceId: String, block: CircleLayerDsl.() -> Unit): CircleLayer = CircleLayer(layerId, sourceId).apply(block)

// End of generated file.