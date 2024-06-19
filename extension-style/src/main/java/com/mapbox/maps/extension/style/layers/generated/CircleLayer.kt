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
  override fun sourceLayer(sourceLayer: String): CircleLayer = apply {
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
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   *
   * @param slot value of slot. Setting it to empty string removes the slot.
   */
  override fun slot(slot: String): CircleLayer = apply {
    val param = PropertyValue("slot", slot)
    setProperty(param)
  }

  /**
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   */
  override val slot: String?
    /**
     * Get the slot property
     *
     * @return slot
     */
    get() {
      return getPropertyValue("slot")
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
  override fun filter(filter: Expression): CircleLayer = apply {
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
     * Use static method [CircleLayer.defaultVisibility] to get the default property value.
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
   */
  override val visibilityAsExpression: Expression?
    /**
     * Whether this layer is displayed.
     *
     * Use static method [CircleLayer.defaultVisibility] to get the default property value.
     *
     * @return VISIBILITY as expression
     */
    get() {
      getPropertyValue<Expression>("visibility")?.let {
        return it
      }
      return null
    }

  /**
   * Whether this layer is displayed.
   *
   * Use static method [CircleLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Visibility): CircleLayer = apply {
    val propertyValue = PropertyValue("visibility", visibility)
    setProperty(propertyValue)
  }

  /**
   * Whether this layer is displayed.
   *
   * Use static method [[CircleLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Expression): CircleLayer = apply {
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
     * Use static method [CircleLayer.defaultMinZoom] to get the default property value.
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
   * Use static method [CircleLayer.defaultMinZoom] to get the default property value.
   *
   * @param minZoom value of minzoom
   */
  override fun minZoom(minZoom: Double): CircleLayer = apply {
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
     * Use static method [CircleLayer.defaultMaxZoom] to get the default property value.
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
   * Use static method [CircleLayer.defaultMaxZoom] to get the default property value.
   *
   * @param maxZoom value of maxzoom
   */
  override fun maxZoom(maxZoom: Double): CircleLayer = apply {
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
     * Use static method [CircleLayer.defaultCircleSortKey] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("circle-sort-key")
    }

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * Use static method [CircleLayer.defaultCircleSortKey] to set the default property.
   *
   * @param circleSortKey value of circleSortKey
   */
  override fun circleSortKey(circleSortKey: Double): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-sort-key", circleSortKey)
    setProperty(propertyValue)
  }

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * This is an Expression representation of "circle-sort-key".
   *
   */
  val circleSortKeyAsExpression: Expression?
    /**
     * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
     *
     * Get the CircleSortKey property as an Expression
     *
     * Use static method [CircleLayer.defaultCircleSortKeyAsExpression] to get the default property.
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
   * Use static method [CircleLayer.defaultCircleSortKeyAsExpression] to set the default property.
   *
   * @param circleSortKey value of circleSortKey as Expression
   */
  override fun circleSortKey(circleSortKey: Expression): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-sort-key", circleSortKey)
    setProperty(propertyValue)
  }

  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity. Default value: 0.
   */
  val circleBlur: Double?
    /**
     * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity. Default value: 0.
     *
     * Use static method [CircleLayer.defaultCircleBlur] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("circle-blur")
    }

  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity. Default value: 0.
   *
   * Use static method [CircleLayer.defaultCircleBlur] to set the default property.
   *
   * @param circleBlur value of circleBlur
   */
  override fun circleBlur(circleBlur: Double): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-blur", circleBlur)
    setProperty(propertyValue)
  }

  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity. Default value: 0.
   *
   * This is an Expression representation of "circle-blur".
   *
   */
  val circleBlurAsExpression: Expression?
    /**
     * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity. Default value: 0.
     *
     * Get the CircleBlur property as an Expression
     *
     * Use static method [CircleLayer.defaultCircleBlurAsExpression] to get the default property.
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
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity. Default value: 0.
   *
   * Use static method [CircleLayer.defaultCircleBlurAsExpression] to set the default property.
   *
   * @param circleBlur value of circleBlur as Expression
   */
  override fun circleBlur(circleBlur: Expression): CircleLayer = apply {
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
     * Use static method [CircleLayer.defaultCircleBlurTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("circle-blur-transition")
    }

  /**
   * Set the CircleBlur property transition options
   *
   * Use static method [CircleLayer.defaultCircleBlurTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun circleBlurTransition(options: StyleTransition): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-blur-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [circleBlurTransition].
   */
  override fun circleBlurTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer = apply {
    circleBlurTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The fill color of the circle. Default value: "#000000".
   */
  val circleColor: String?
    /**
     * The fill color of the circle. Default value: "#000000".
     *
     * Use static method [CircleLayer.defaultCircleColor] to get the default property.
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
   * The fill color of the circle. Default value: "#000000".
   *
   * Use static method [CircleLayer.defaultCircleColor] to set the default property.
   *
   * @param circleColor value of circleColor
   */
  override fun circleColor(circleColor: String): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-color", circleColor)
    setProperty(propertyValue)
  }

  /**
   * The fill color of the circle. Default value: "#000000".
   *
   * This is an Expression representation of "circle-color".
   *
   */
  val circleColorAsExpression: Expression?
    /**
     * The fill color of the circle. Default value: "#000000".
     *
     * Get the CircleColor property as an Expression
     *
     * Use static method [CircleLayer.defaultCircleColorAsExpression] to get the default property.
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
   * The fill color of the circle. Default value: "#000000".
   *
   * Use static method [CircleLayer.defaultCircleColorAsExpression] to set the default property.
   *
   * @param circleColor value of circleColor as Expression
   */
  override fun circleColor(circleColor: Expression): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-color", circleColor)
    setProperty(propertyValue)
  }

  /**
   * The fill color of the circle. Default value: "#000000".
   */
  val circleColorAsColorInt: Int?
    /**
     * The fill color of the circle. Default value: "#000000".
     *
     * Use static method [CircleLayer.defaultCircleColorAsColorInt] to get the default property.
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
   * The fill color of the circle. Default value: "#000000".
   *
   * Use static method [CircleLayer.defaultCircleColorAsColorInt] to set the default property.
   *
   * @param circleColor value of circleColor
   */
  override fun circleColor(@ColorInt circleColor: Int): CircleLayer = apply {
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
     * Use static method [CircleLayer.defaultCircleColorTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("circle-color-transition")
    }

  /**
   * Set the CircleColor property transition options
   *
   * Use static method [CircleLayer.defaultCircleColorTransition] to set the default property.
   *
   * @param options transition options for String
   */
  override fun circleColorTransition(options: StyleTransition): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [circleColorTransition].
   */
  override fun circleColorTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer = apply {
    circleColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0.
   */
  val circleEmissiveStrength: Double?
    /**
     * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0.
     *
     * Use static method [CircleLayer.defaultCircleEmissiveStrength] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("circle-emissive-strength")
    }

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0.
   *
   * Use static method [CircleLayer.defaultCircleEmissiveStrength] to set the default property.
   *
   * @param circleEmissiveStrength value of circleEmissiveStrength
   */
  override fun circleEmissiveStrength(circleEmissiveStrength: Double): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-emissive-strength", circleEmissiveStrength)
    setProperty(propertyValue)
  }

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0.
   *
   * This is an Expression representation of "circle-emissive-strength".
   *
   */
  val circleEmissiveStrengthAsExpression: Expression?
    /**
     * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0.
     *
     * Get the CircleEmissiveStrength property as an Expression
     *
     * Use static method [CircleLayer.defaultCircleEmissiveStrengthAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("circle-emissive-strength")?.let {
        return it
      }
      circleEmissiveStrength?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0.
   *
   * Use static method [CircleLayer.defaultCircleEmissiveStrengthAsExpression] to set the default property.
   *
   * @param circleEmissiveStrength value of circleEmissiveStrength as Expression
   */
  override fun circleEmissiveStrength(circleEmissiveStrength: Expression): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-emissive-strength", circleEmissiveStrength)
    setProperty(propertyValue)
  }

  /**
   * Transition options for CircleEmissiveStrength.
   */
  val circleEmissiveStrengthTransition: StyleTransition?
    /**
     * Get the CircleEmissiveStrength property transition options
     *
     * Use static method [CircleLayer.defaultCircleEmissiveStrengthTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("circle-emissive-strength-transition")
    }

  /**
   * Set the CircleEmissiveStrength property transition options
   *
   * Use static method [CircleLayer.defaultCircleEmissiveStrengthTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun circleEmissiveStrengthTransition(options: StyleTransition): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-emissive-strength-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [circleEmissiveStrengthTransition].
   */
  override fun circleEmissiveStrengthTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer = apply {
    circleEmissiveStrengthTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity at which the circle will be drawn. Default value: 1. Value range: [0, 1]
   */
  val circleOpacity: Double?
    /**
     * The opacity at which the circle will be drawn. Default value: 1. Value range: [0, 1]
     *
     * Use static method [CircleLayer.defaultCircleOpacity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("circle-opacity")
    }

  /**
   * The opacity at which the circle will be drawn. Default value: 1. Value range: [0, 1]
   *
   * Use static method [CircleLayer.defaultCircleOpacity] to set the default property.
   *
   * @param circleOpacity value of circleOpacity
   */
  override fun circleOpacity(circleOpacity: Double): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-opacity", circleOpacity)
    setProperty(propertyValue)
  }

  /**
   * The opacity at which the circle will be drawn. Default value: 1. Value range: [0, 1]
   *
   * This is an Expression representation of "circle-opacity".
   *
   */
  val circleOpacityAsExpression: Expression?
    /**
     * The opacity at which the circle will be drawn. Default value: 1. Value range: [0, 1]
     *
     * Get the CircleOpacity property as an Expression
     *
     * Use static method [CircleLayer.defaultCircleOpacityAsExpression] to get the default property.
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
   * The opacity at which the circle will be drawn. Default value: 1. Value range: [0, 1]
   *
   * Use static method [CircleLayer.defaultCircleOpacityAsExpression] to set the default property.
   *
   * @param circleOpacity value of circleOpacity as Expression
   */
  override fun circleOpacity(circleOpacity: Expression): CircleLayer = apply {
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
     * Use static method [CircleLayer.defaultCircleOpacityTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("circle-opacity-transition")
    }

  /**
   * Set the CircleOpacity property transition options
   *
   * Use static method [CircleLayer.defaultCircleOpacityTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun circleOpacityTransition(options: StyleTransition): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [circleOpacityTransition].
   */
  override fun circleOpacityTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer = apply {
    circleOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Orientation of circle when map is pitched. Default value: "viewport".
   */
  val circlePitchAlignment: CirclePitchAlignment?
    /**
     * Orientation of circle when map is pitched. Default value: "viewport".
     *
     * Use static method [CircleLayer.defaultCirclePitchAlignment] to get the default property.
     *
     * @return CirclePitchAlignment
     */
    get() {
      getPropertyValue<String?>("circle-pitch-alignment")?.let {
        return CirclePitchAlignment.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Orientation of circle when map is pitched. Default value: "viewport".
   *
   * Use static method [CircleLayer.defaultCirclePitchAlignment] to set the default property.
   *
   * @param circlePitchAlignment value of circlePitchAlignment
   */
  override fun circlePitchAlignment(circlePitchAlignment: CirclePitchAlignment): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-pitch-alignment", circlePitchAlignment)
    setProperty(propertyValue)
  }

  /**
   * Orientation of circle when map is pitched. Default value: "viewport".
   *
   * This is an Expression representation of "circle-pitch-alignment".
   *
   */
  val circlePitchAlignmentAsExpression: Expression?
    /**
     * Orientation of circle when map is pitched. Default value: "viewport".
     *
     * Get the CirclePitchAlignment property as an Expression
     *
     * Use static method [CircleLayer.defaultCirclePitchAlignmentAsExpression] to get the default property.
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
   * Orientation of circle when map is pitched. Default value: "viewport".
   *
   * Use static method [CircleLayer.defaultCirclePitchAlignmentAsExpression] to set the default property.
   *
   * @param circlePitchAlignment value of circlePitchAlignment as Expression
   */
  override fun circlePitchAlignment(circlePitchAlignment: Expression): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-pitch-alignment", circlePitchAlignment)
    setProperty(propertyValue)
  }

  /**
   * Controls the scaling behavior of the circle when the map is pitched. Default value: "map".
   */
  val circlePitchScale: CirclePitchScale?
    /**
     * Controls the scaling behavior of the circle when the map is pitched. Default value: "map".
     *
     * Use static method [CircleLayer.defaultCirclePitchScale] to get the default property.
     *
     * @return CirclePitchScale
     */
    get() {
      getPropertyValue<String?>("circle-pitch-scale")?.let {
        return CirclePitchScale.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Controls the scaling behavior of the circle when the map is pitched. Default value: "map".
   *
   * Use static method [CircleLayer.defaultCirclePitchScale] to set the default property.
   *
   * @param circlePitchScale value of circlePitchScale
   */
  override fun circlePitchScale(circlePitchScale: CirclePitchScale): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-pitch-scale", circlePitchScale)
    setProperty(propertyValue)
  }

  /**
   * Controls the scaling behavior of the circle when the map is pitched. Default value: "map".
   *
   * This is an Expression representation of "circle-pitch-scale".
   *
   */
  val circlePitchScaleAsExpression: Expression?
    /**
     * Controls the scaling behavior of the circle when the map is pitched. Default value: "map".
     *
     * Get the CirclePitchScale property as an Expression
     *
     * Use static method [CircleLayer.defaultCirclePitchScaleAsExpression] to get the default property.
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
   * Controls the scaling behavior of the circle when the map is pitched. Default value: "map".
   *
   * Use static method [CircleLayer.defaultCirclePitchScaleAsExpression] to set the default property.
   *
   * @param circlePitchScale value of circlePitchScale as Expression
   */
  override fun circlePitchScale(circlePitchScale: Expression): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-pitch-scale", circlePitchScale)
    setProperty(propertyValue)
  }

  /**
   * Circle radius. Default value: 5. Minimum value: 0.
   */
  val circleRadius: Double?
    /**
     * Circle radius. Default value: 5. Minimum value: 0.
     *
     * Use static method [CircleLayer.defaultCircleRadius] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("circle-radius")
    }

  /**
   * Circle radius. Default value: 5. Minimum value: 0.
   *
   * Use static method [CircleLayer.defaultCircleRadius] to set the default property.
   *
   * @param circleRadius value of circleRadius
   */
  override fun circleRadius(circleRadius: Double): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-radius", circleRadius)
    setProperty(propertyValue)
  }

  /**
   * Circle radius. Default value: 5. Minimum value: 0.
   *
   * This is an Expression representation of "circle-radius".
   *
   */
  val circleRadiusAsExpression: Expression?
    /**
     * Circle radius. Default value: 5. Minimum value: 0.
     *
     * Get the CircleRadius property as an Expression
     *
     * Use static method [CircleLayer.defaultCircleRadiusAsExpression] to get the default property.
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
   * Circle radius. Default value: 5. Minimum value: 0.
   *
   * Use static method [CircleLayer.defaultCircleRadiusAsExpression] to set the default property.
   *
   * @param circleRadius value of circleRadius as Expression
   */
  override fun circleRadius(circleRadius: Expression): CircleLayer = apply {
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
     * Use static method [CircleLayer.defaultCircleRadiusTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("circle-radius-transition")
    }

  /**
   * Set the CircleRadius property transition options
   *
   * Use static method [CircleLayer.defaultCircleRadiusTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun circleRadiusTransition(options: StyleTransition): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-radius-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [circleRadiusTransition].
   */
  override fun circleRadiusTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer = apply {
    circleRadiusTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The stroke color of the circle. Default value: "#000000".
   */
  val circleStrokeColor: String?
    /**
     * The stroke color of the circle. Default value: "#000000".
     *
     * Use static method [CircleLayer.defaultCircleStrokeColor] to get the default property.
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
   * The stroke color of the circle. Default value: "#000000".
   *
   * Use static method [CircleLayer.defaultCircleStrokeColor] to set the default property.
   *
   * @param circleStrokeColor value of circleStrokeColor
   */
  override fun circleStrokeColor(circleStrokeColor: String): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-stroke-color", circleStrokeColor)
    setProperty(propertyValue)
  }

  /**
   * The stroke color of the circle. Default value: "#000000".
   *
   * This is an Expression representation of "circle-stroke-color".
   *
   */
  val circleStrokeColorAsExpression: Expression?
    /**
     * The stroke color of the circle. Default value: "#000000".
     *
     * Get the CircleStrokeColor property as an Expression
     *
     * Use static method [CircleLayer.defaultCircleStrokeColorAsExpression] to get the default property.
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
   * The stroke color of the circle. Default value: "#000000".
   *
   * Use static method [CircleLayer.defaultCircleStrokeColorAsExpression] to set the default property.
   *
   * @param circleStrokeColor value of circleStrokeColor as Expression
   */
  override fun circleStrokeColor(circleStrokeColor: Expression): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-stroke-color", circleStrokeColor)
    setProperty(propertyValue)
  }

  /**
   * The stroke color of the circle. Default value: "#000000".
   */
  val circleStrokeColorAsColorInt: Int?
    /**
     * The stroke color of the circle. Default value: "#000000".
     *
     * Use static method [CircleLayer.defaultCircleStrokeColorAsColorInt] to get the default property.
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
   * The stroke color of the circle. Default value: "#000000".
   *
   * Use static method [CircleLayer.defaultCircleStrokeColorAsColorInt] to set the default property.
   *
   * @param circleStrokeColor value of circleStrokeColor
   */
  override fun circleStrokeColor(@ColorInt circleStrokeColor: Int): CircleLayer = apply {
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
     * Use static method [CircleLayer.defaultCircleStrokeColorTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("circle-stroke-color-transition")
    }

  /**
   * Set the CircleStrokeColor property transition options
   *
   * Use static method [CircleLayer.defaultCircleStrokeColorTransition] to set the default property.
   *
   * @param options transition options for String
   */
  override fun circleStrokeColorTransition(options: StyleTransition): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-stroke-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [circleStrokeColorTransition].
   */
  override fun circleStrokeColorTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer = apply {
    circleStrokeColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity of the circle's stroke. Default value: 1. Value range: [0, 1]
   */
  val circleStrokeOpacity: Double?
    /**
     * The opacity of the circle's stroke. Default value: 1. Value range: [0, 1]
     *
     * Use static method [CircleLayer.defaultCircleStrokeOpacity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("circle-stroke-opacity")
    }

  /**
   * The opacity of the circle's stroke. Default value: 1. Value range: [0, 1]
   *
   * Use static method [CircleLayer.defaultCircleStrokeOpacity] to set the default property.
   *
   * @param circleStrokeOpacity value of circleStrokeOpacity
   */
  override fun circleStrokeOpacity(circleStrokeOpacity: Double): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-stroke-opacity", circleStrokeOpacity)
    setProperty(propertyValue)
  }

  /**
   * The opacity of the circle's stroke. Default value: 1. Value range: [0, 1]
   *
   * This is an Expression representation of "circle-stroke-opacity".
   *
   */
  val circleStrokeOpacityAsExpression: Expression?
    /**
     * The opacity of the circle's stroke. Default value: 1. Value range: [0, 1]
     *
     * Get the CircleStrokeOpacity property as an Expression
     *
     * Use static method [CircleLayer.defaultCircleStrokeOpacityAsExpression] to get the default property.
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
   * The opacity of the circle's stroke. Default value: 1. Value range: [0, 1]
   *
   * Use static method [CircleLayer.defaultCircleStrokeOpacityAsExpression] to set the default property.
   *
   * @param circleStrokeOpacity value of circleStrokeOpacity as Expression
   */
  override fun circleStrokeOpacity(circleStrokeOpacity: Expression): CircleLayer = apply {
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
     * Use static method [CircleLayer.defaultCircleStrokeOpacityTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("circle-stroke-opacity-transition")
    }

  /**
   * Set the CircleStrokeOpacity property transition options
   *
   * Use static method [CircleLayer.defaultCircleStrokeOpacityTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun circleStrokeOpacityTransition(options: StyleTransition): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-stroke-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [circleStrokeOpacityTransition].
   */
  override fun circleStrokeOpacityTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer = apply {
    circleStrokeOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`. Default value: 0. Minimum value: 0.
   */
  val circleStrokeWidth: Double?
    /**
     * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`. Default value: 0. Minimum value: 0.
     *
     * Use static method [CircleLayer.defaultCircleStrokeWidth] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("circle-stroke-width")
    }

  /**
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`. Default value: 0. Minimum value: 0.
   *
   * Use static method [CircleLayer.defaultCircleStrokeWidth] to set the default property.
   *
   * @param circleStrokeWidth value of circleStrokeWidth
   */
  override fun circleStrokeWidth(circleStrokeWidth: Double): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-stroke-width", circleStrokeWidth)
    setProperty(propertyValue)
  }

  /**
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`. Default value: 0. Minimum value: 0.
   *
   * This is an Expression representation of "circle-stroke-width".
   *
   */
  val circleStrokeWidthAsExpression: Expression?
    /**
     * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`. Default value: 0. Minimum value: 0.
     *
     * Get the CircleStrokeWidth property as an Expression
     *
     * Use static method [CircleLayer.defaultCircleStrokeWidthAsExpression] to get the default property.
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
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`. Default value: 0. Minimum value: 0.
   *
   * Use static method [CircleLayer.defaultCircleStrokeWidthAsExpression] to set the default property.
   *
   * @param circleStrokeWidth value of circleStrokeWidth as Expression
   */
  override fun circleStrokeWidth(circleStrokeWidth: Expression): CircleLayer = apply {
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
     * Use static method [CircleLayer.defaultCircleStrokeWidthTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("circle-stroke-width-transition")
    }

  /**
   * Set the CircleStrokeWidth property transition options
   *
   * Use static method [CircleLayer.defaultCircleStrokeWidthTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun circleStrokeWidthTransition(options: StyleTransition): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-stroke-width-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [circleStrokeWidthTransition].
   */
  override fun circleStrokeWidthTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer = apply {
    circleStrokeWidthTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0].
   */
  val circleTranslate: List<Double>?
    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0].
     *
     * Use static method [CircleLayer.defaultCircleTranslate] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("circle-translate")
    }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0].
   *
   * Use static method [CircleLayer.defaultCircleTranslate] to set the default property.
   *
   * @param circleTranslate value of circleTranslate
   */
  override fun circleTranslate(circleTranslate: List<Double>): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-translate", circleTranslate)
    setProperty(propertyValue)
  }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0].
   *
   * This is an Expression representation of "circle-translate".
   *
   */
  val circleTranslateAsExpression: Expression?
    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0].
     *
     * Get the CircleTranslate property as an Expression
     *
     * Use static method [CircleLayer.defaultCircleTranslateAsExpression] to get the default property.
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
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0].
   *
   * Use static method [CircleLayer.defaultCircleTranslateAsExpression] to set the default property.
   *
   * @param circleTranslate value of circleTranslate as Expression
   */
  override fun circleTranslate(circleTranslate: Expression): CircleLayer = apply {
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
     * Use static method [CircleLayer.defaultCircleTranslateTransition] to get the default property.
     *
     * @return transition options for List<Double>
     */
    get() {
      return getPropertyValue("circle-translate-transition")
    }

  /**
   * Set the CircleTranslate property transition options
   *
   * Use static method [CircleLayer.defaultCircleTranslateTransition] to set the default property.
   *
   * @param options transition options for List<Double>
   */
  override fun circleTranslateTransition(options: StyleTransition): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-translate-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [circleTranslateTransition].
   */
  override fun circleTranslateTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer = apply {
    circleTranslateTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Controls the frame of reference for `circle-translate`. Default value: "map".
   */
  val circleTranslateAnchor: CircleTranslateAnchor?
    /**
     * Controls the frame of reference for `circle-translate`. Default value: "map".
     *
     * Use static method [CircleLayer.defaultCircleTranslateAnchor] to get the default property.
     *
     * @return CircleTranslateAnchor
     */
    get() {
      getPropertyValue<String?>("circle-translate-anchor")?.let {
        return CircleTranslateAnchor.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Controls the frame of reference for `circle-translate`. Default value: "map".
   *
   * Use static method [CircleLayer.defaultCircleTranslateAnchor] to set the default property.
   *
   * @param circleTranslateAnchor value of circleTranslateAnchor
   */
  override fun circleTranslateAnchor(circleTranslateAnchor: CircleTranslateAnchor): CircleLayer = apply {
    val propertyValue = PropertyValue("circle-translate-anchor", circleTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * Controls the frame of reference for `circle-translate`. Default value: "map".
   *
   * This is an Expression representation of "circle-translate-anchor".
   *
   */
  val circleTranslateAnchorAsExpression: Expression?
    /**
     * Controls the frame of reference for `circle-translate`. Default value: "map".
     *
     * Get the CircleTranslateAnchor property as an Expression
     *
     * Use static method [CircleLayer.defaultCircleTranslateAnchorAsExpression] to get the default property.
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
   * Controls the frame of reference for `circle-translate`. Default value: "map".
   *
   * Use static method [CircleLayer.defaultCircleTranslateAnchorAsExpression] to set the default property.
   *
   * @param circleTranslateAnchor value of circleTranslateAnchor as Expression
   */
  override fun circleTranslateAnchor(circleTranslateAnchor: Expression): CircleLayer = apply {
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
     * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity. Default value: 0.
     */
    val defaultCircleBlur: Double?
      /**
       * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity. Default value: 0.
       *
       * Get the default value of CircleBlur property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-blur").silentUnwrap()
      }

    /**
     * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity. Default value: 0.
     *
     * This is an Expression representation of "circle-blur".
     *
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
     * The fill color of the circle. Default value: "#000000".
     */
    val defaultCircleColor: String?
      /**
       * The fill color of the circle. Default value: "#000000".
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
     * The fill color of the circle. Default value: "#000000".
     *
     * This is an Expression representation of "circle-color".
     *
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
     * The fill color of the circle. Default value: "#000000".
     */
    val defaultCircleColorAsColorInt: Int?
      /**
       * The fill color of the circle. Default value: "#000000".
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
     * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0.
     */
    val defaultCircleEmissiveStrength: Double?
      /**
       * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0.
       *
       * Get the default value of CircleEmissiveStrength property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-emissive-strength").silentUnwrap()
      }

    /**
     * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0.
     *
     * This is an Expression representation of "circle-emissive-strength".
     *
     */
    val defaultCircleEmissiveStrengthAsExpression: Expression?
      /**
       * Get default value of the CircleEmissiveStrength property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-emissive-strength").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultCircleEmissiveStrength?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for CircleEmissiveStrength.
     */
    val defaultCircleEmissiveStrengthTransition: StyleTransition?
      /**
       * Get the CircleEmissiveStrength property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-emissive-strength-transition").silentUnwrap()

    /**
     * The opacity at which the circle will be drawn. Default value: 1. Value range: [0, 1]
     */
    val defaultCircleOpacity: Double?
      /**
       * The opacity at which the circle will be drawn. Default value: 1. Value range: [0, 1]
       *
       * Get the default value of CircleOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-opacity").silentUnwrap()
      }

    /**
     * The opacity at which the circle will be drawn. Default value: 1. Value range: [0, 1]
     *
     * This is an Expression representation of "circle-opacity".
     *
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
     * Orientation of circle when map is pitched. Default value: "viewport".
     */
    val defaultCirclePitchAlignment: CirclePitchAlignment?
      /**
       * Orientation of circle when map is pitched. Default value: "viewport".
       *
       * Get the default value of CirclePitchAlignment property
       *
       * @return CirclePitchAlignment
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-pitch-alignment").silentUnwrap<String>()?.let {
          return CirclePitchAlignment.valueOf(it.uppercase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Orientation of circle when map is pitched. Default value: "viewport".
     *
     * This is an Expression representation of "circle-pitch-alignment".
     *
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
     * Controls the scaling behavior of the circle when the map is pitched. Default value: "map".
     */
    val defaultCirclePitchScale: CirclePitchScale?
      /**
       * Controls the scaling behavior of the circle when the map is pitched. Default value: "map".
       *
       * Get the default value of CirclePitchScale property
       *
       * @return CirclePitchScale
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-pitch-scale").silentUnwrap<String>()?.let {
          return CirclePitchScale.valueOf(it.uppercase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Controls the scaling behavior of the circle when the map is pitched. Default value: "map".
     *
     * This is an Expression representation of "circle-pitch-scale".
     *
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
     * Circle radius. Default value: 5. Minimum value: 0.
     */
    val defaultCircleRadius: Double?
      /**
       * Circle radius. Default value: 5. Minimum value: 0.
       *
       * Get the default value of CircleRadius property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-radius").silentUnwrap()
      }

    /**
     * Circle radius. Default value: 5. Minimum value: 0.
     *
     * This is an Expression representation of "circle-radius".
     *
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
     * The stroke color of the circle. Default value: "#000000".
     */
    val defaultCircleStrokeColor: String?
      /**
       * The stroke color of the circle. Default value: "#000000".
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
     * The stroke color of the circle. Default value: "#000000".
     *
     * This is an Expression representation of "circle-stroke-color".
     *
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
     * The stroke color of the circle. Default value: "#000000".
     */
    val defaultCircleStrokeColorAsColorInt: Int?
      /**
       * The stroke color of the circle. Default value: "#000000".
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
     * The opacity of the circle's stroke. Default value: 1. Value range: [0, 1]
     */
    val defaultCircleStrokeOpacity: Double?
      /**
       * The opacity of the circle's stroke. Default value: 1. Value range: [0, 1]
       *
       * Get the default value of CircleStrokeOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-opacity").silentUnwrap()
      }

    /**
     * The opacity of the circle's stroke. Default value: 1. Value range: [0, 1]
     *
     * This is an Expression representation of "circle-stroke-opacity".
     *
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
     * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`. Default value: 0. Minimum value: 0.
     */
    val defaultCircleStrokeWidth: Double?
      /**
       * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`. Default value: 0. Minimum value: 0.
       *
       * Get the default value of CircleStrokeWidth property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-stroke-width").silentUnwrap()
      }

    /**
     * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`. Default value: 0. Minimum value: 0.
     *
     * This is an Expression representation of "circle-stroke-width".
     *
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
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0].
     */
    val defaultCircleTranslate: List<Double>?
      /**
       * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0].
       *
       * Get the default value of CircleTranslate property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate").silentUnwrap()
      }

    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0].
     *
     * This is an Expression representation of "circle-translate".
     *
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
     * Controls the frame of reference for `circle-translate`. Default value: "map".
     */
    val defaultCircleTranslateAnchor: CircleTranslateAnchor?
      /**
       * Controls the frame of reference for `circle-translate`. Default value: "map".
       *
       * Get the default value of CircleTranslateAnchor property
       *
       * @return CircleTranslateAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate-anchor").silentUnwrap<String>()?.let {
          return CircleTranslateAnchor.valueOf(it.uppercase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Controls the frame of reference for `circle-translate`. Default value: "map".
     *
     * This is an Expression representation of "circle-translate-anchor".
     *
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
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   *
   * @param slot value of slot. Setting it to empty string removes the slot.
   */
  fun slot(slot: String): CircleLayer

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
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility as Expression
   */
  fun visibility(visibility: Expression): CircleLayer

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param minZoom value of minzoom
   */
  fun minZoom(minZoom: Double): CircleLayer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param maxZoom value of maxzoom
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
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity. Default value: 0.
   *
   * @param circleBlur value of circleBlur
   */
  fun circleBlur(circleBlur: Double = 0.0): CircleLayer

  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity. Default value: 0.
   *
   * @param circleBlur value of circleBlur as Expression
   */
  fun circleBlur(circleBlur: Expression): CircleLayer

  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity. Default value: 0.
   *
   * Set the CircleBlur property transition options
   *
   * @param options transition options for Double
   */
  fun circleBlurTransition(options: StyleTransition): CircleLayer

  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity. Default value: 0.
   *
   * DSL for [circleBlurTransition].
   */
  fun circleBlurTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer

  /**
   * The fill color of the circle. Default value: "#000000".
   *
   * @param circleColor value of circleColor
   */
  fun circleColor(circleColor: String = "#000000"): CircleLayer

  /**
   * The fill color of the circle. Default value: "#000000".
   *
   * @param circleColor value of circleColor as Expression
   */
  fun circleColor(circleColor: Expression): CircleLayer

  /**
   * The fill color of the circle. Default value: "#000000".
   *
   * @param circleColor value of circleColor
   */
  fun circleColor(@ColorInt circleColor: Int): CircleLayer

  /**
   * The fill color of the circle. Default value: "#000000".
   *
   * Set the CircleColor property transition options
   *
   * @param options transition options for String
   */
  fun circleColorTransition(options: StyleTransition): CircleLayer

  /**
   * The fill color of the circle. Default value: "#000000".
   *
   * DSL for [circleColorTransition].
   */
  fun circleColorTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0.
   *
   * @param circleEmissiveStrength value of circleEmissiveStrength
   */
  fun circleEmissiveStrength(circleEmissiveStrength: Double = 0.0): CircleLayer

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0.
   *
   * @param circleEmissiveStrength value of circleEmissiveStrength as Expression
   */
  fun circleEmissiveStrength(circleEmissiveStrength: Expression): CircleLayer

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0.
   *
   * Set the CircleEmissiveStrength property transition options
   *
   * @param options transition options for Double
   */
  fun circleEmissiveStrengthTransition(options: StyleTransition): CircleLayer

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0.
   *
   * DSL for [circleEmissiveStrengthTransition].
   */
  fun circleEmissiveStrengthTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer

  /**
   * The opacity at which the circle will be drawn. Default value: 1. Value range: [0, 1]
   *
   * @param circleOpacity value of circleOpacity
   */
  fun circleOpacity(circleOpacity: Double = 1.0): CircleLayer

  /**
   * The opacity at which the circle will be drawn. Default value: 1. Value range: [0, 1]
   *
   * @param circleOpacity value of circleOpacity as Expression
   */
  fun circleOpacity(circleOpacity: Expression): CircleLayer

  /**
   * The opacity at which the circle will be drawn. Default value: 1. Value range: [0, 1]
   *
   * Set the CircleOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun circleOpacityTransition(options: StyleTransition): CircleLayer

  /**
   * The opacity at which the circle will be drawn. Default value: 1. Value range: [0, 1]
   *
   * DSL for [circleOpacityTransition].
   */
  fun circleOpacityTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer

  /**
   * Orientation of circle when map is pitched. Default value: "viewport".
   *
   * @param circlePitchAlignment value of circlePitchAlignment
   */
  fun circlePitchAlignment(circlePitchAlignment: CirclePitchAlignment = CirclePitchAlignment.VIEWPORT): CircleLayer

  /**
   * Orientation of circle when map is pitched. Default value: "viewport".
   *
   * @param circlePitchAlignment value of circlePitchAlignment as Expression
   */
  fun circlePitchAlignment(circlePitchAlignment: Expression): CircleLayer

  /**
   * Controls the scaling behavior of the circle when the map is pitched. Default value: "map".
   *
   * @param circlePitchScale value of circlePitchScale
   */
  fun circlePitchScale(circlePitchScale: CirclePitchScale = CirclePitchScale.MAP): CircleLayer

  /**
   * Controls the scaling behavior of the circle when the map is pitched. Default value: "map".
   *
   * @param circlePitchScale value of circlePitchScale as Expression
   */
  fun circlePitchScale(circlePitchScale: Expression): CircleLayer

  /**
   * Circle radius. Default value: 5. Minimum value: 0.
   *
   * @param circleRadius value of circleRadius
   */
  fun circleRadius(circleRadius: Double = 5.0): CircleLayer

  /**
   * Circle radius. Default value: 5. Minimum value: 0.
   *
   * @param circleRadius value of circleRadius as Expression
   */
  fun circleRadius(circleRadius: Expression): CircleLayer

  /**
   * Circle radius. Default value: 5. Minimum value: 0.
   *
   * Set the CircleRadius property transition options
   *
   * @param options transition options for Double
   */
  fun circleRadiusTransition(options: StyleTransition): CircleLayer

  /**
   * Circle radius. Default value: 5. Minimum value: 0.
   *
   * DSL for [circleRadiusTransition].
   */
  fun circleRadiusTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer

  /**
   * The stroke color of the circle. Default value: "#000000".
   *
   * @param circleStrokeColor value of circleStrokeColor
   */
  fun circleStrokeColor(circleStrokeColor: String = "#000000"): CircleLayer

  /**
   * The stroke color of the circle. Default value: "#000000".
   *
   * @param circleStrokeColor value of circleStrokeColor as Expression
   */
  fun circleStrokeColor(circleStrokeColor: Expression): CircleLayer

  /**
   * The stroke color of the circle. Default value: "#000000".
   *
   * @param circleStrokeColor value of circleStrokeColor
   */
  fun circleStrokeColor(@ColorInt circleStrokeColor: Int): CircleLayer

  /**
   * The stroke color of the circle. Default value: "#000000".
   *
   * Set the CircleStrokeColor property transition options
   *
   * @param options transition options for String
   */
  fun circleStrokeColorTransition(options: StyleTransition): CircleLayer

  /**
   * The stroke color of the circle. Default value: "#000000".
   *
   * DSL for [circleStrokeColorTransition].
   */
  fun circleStrokeColorTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer

  /**
   * The opacity of the circle's stroke. Default value: 1. Value range: [0, 1]
   *
   * @param circleStrokeOpacity value of circleStrokeOpacity
   */
  fun circleStrokeOpacity(circleStrokeOpacity: Double = 1.0): CircleLayer

  /**
   * The opacity of the circle's stroke. Default value: 1. Value range: [0, 1]
   *
   * @param circleStrokeOpacity value of circleStrokeOpacity as Expression
   */
  fun circleStrokeOpacity(circleStrokeOpacity: Expression): CircleLayer

  /**
   * The opacity of the circle's stroke. Default value: 1. Value range: [0, 1]
   *
   * Set the CircleStrokeOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun circleStrokeOpacityTransition(options: StyleTransition): CircleLayer

  /**
   * The opacity of the circle's stroke. Default value: 1. Value range: [0, 1]
   *
   * DSL for [circleStrokeOpacityTransition].
   */
  fun circleStrokeOpacityTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer

  /**
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`. Default value: 0. Minimum value: 0.
   *
   * @param circleStrokeWidth value of circleStrokeWidth
   */
  fun circleStrokeWidth(circleStrokeWidth: Double = 0.0): CircleLayer

  /**
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`. Default value: 0. Minimum value: 0.
   *
   * @param circleStrokeWidth value of circleStrokeWidth as Expression
   */
  fun circleStrokeWidth(circleStrokeWidth: Expression): CircleLayer

  /**
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`. Default value: 0. Minimum value: 0.
   *
   * Set the CircleStrokeWidth property transition options
   *
   * @param options transition options for Double
   */
  fun circleStrokeWidthTransition(options: StyleTransition): CircleLayer

  /**
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`. Default value: 0. Minimum value: 0.
   *
   * DSL for [circleStrokeWidthTransition].
   */
  fun circleStrokeWidthTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0].
   *
   * @param circleTranslate value of circleTranslate
   */
  fun circleTranslate(circleTranslate: List<Double> = listOf(0.0, 0.0)): CircleLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0].
   *
   * @param circleTranslate value of circleTranslate as Expression
   */
  fun circleTranslate(circleTranslate: Expression): CircleLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0].
   *
   * Set the CircleTranslate property transition options
   *
   * @param options transition options for List<Double>
   */
  fun circleTranslateTransition(options: StyleTransition): CircleLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0].
   *
   * DSL for [circleTranslateTransition].
   */
  fun circleTranslateTransition(block: StyleTransition.Builder.() -> Unit): CircleLayer

  /**
   * Controls the frame of reference for `circle-translate`. Default value: "map".
   *
   * @param circleTranslateAnchor value of circleTranslateAnchor
   */
  fun circleTranslateAnchor(circleTranslateAnchor: CircleTranslateAnchor = CircleTranslateAnchor.MAP): CircleLayer

  /**
   * Controls the frame of reference for `circle-translate`. Default value: "map".
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