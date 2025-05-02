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
 * A filled polygon with an optional stroked border.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers/#fill)
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
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   *
   * @param slot value of slot. Setting it to empty string removes the slot.
   */
  override fun slot(slot: String): FillLayer = apply {
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
    get() = getPropertyValue("slot")

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
    get() = getPropertyValue("filter")

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
     * Use static method [FillLayer.defaultVisibility] to get the default property value.
     *
     * @return VISIBILITY as expression
     */
    get() = getPropertyValue("visibility")

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
   * Whether this layer is displayed.
   *
   * Use static method [[FillLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Expression): FillLayer = apply {
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
    get() = getPropertyValue("minzoom")

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * Use static method [FillLayer.defaultMinZoom] to get the default property value.
   *
   * @param minZoom value of minzoom
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
    get() = getPropertyValue("maxzoom")

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * Use static method [FillLayer.defaultMaxZoom] to get the default property value.
   *
   * @param maxZoom value of maxzoom
   */
  override fun maxZoom(maxZoom: Double): FillLayer = apply {
    val param = PropertyValue("maxzoom", maxZoom)
    setProperty(param)
  }

  // Property getters and setters

  /**
   * Determines whether bridge guard rails are added for elevated roads. Default value: "true".
   */
  @MapboxExperimental
  val fillConstructBridgeGuardRail: Boolean?
    /**
     * Determines whether bridge guard rails are added for elevated roads. Default value: "true".
     *
     * Use static method [FillLayer.defaultFillConstructBridgeGuardRail] to get the default property.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("fill-construct-bridge-guard-rail")
    }

  /**
   * Determines whether bridge guard rails are added for elevated roads. Default value: "true".
   *
   * Use static method [FillLayer.defaultFillConstructBridgeGuardRail] to set the default property.
   *
   * @param fillConstructBridgeGuardRail value of fillConstructBridgeGuardRail
   */
  @MapboxExperimental
  override fun fillConstructBridgeGuardRail(fillConstructBridgeGuardRail: Boolean): FillLayer = apply {
    val propertyValue = PropertyValue("fill-construct-bridge-guard-rail", fillConstructBridgeGuardRail)
    setProperty(propertyValue)
  }

  /**
   * Determines whether bridge guard rails are added for elevated roads. Default value: "true".
   *
   * This is an Expression representation of "fill-construct-bridge-guard-rail".
   *
   */
  @MapboxExperimental
  val fillConstructBridgeGuardRailAsExpression: Expression?
    /**
     * Determines whether bridge guard rails are added for elevated roads. Default value: "true".
     *
     * Get the FillConstructBridgeGuardRail property as an Expression
     *
     * Use static method [FillLayer.defaultFillConstructBridgeGuardRailAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("fill-construct-bridge-guard-rail")

  /**
   * Determines whether bridge guard rails are added for elevated roads. Default value: "true".
   *
   * Use static method [FillLayer.defaultFillConstructBridgeGuardRailAsExpression] to set the default property.
   *
   * @param fillConstructBridgeGuardRail value of fillConstructBridgeGuardRail as Expression
   */
  @MapboxExperimental
  override fun fillConstructBridgeGuardRail(fillConstructBridgeGuardRail: Expression): FillLayer = apply {
    val propertyValue = PropertyValue("fill-construct-bridge-guard-rail", fillConstructBridgeGuardRail)
    setProperty(propertyValue)
  }

  /**
   * Selects the base of fill-elevation. Some modes might require precomputed elevation data in the tileset. Default value: "none".
   */
  @MapboxExperimental
  val fillElevationReference: FillElevationReference?
    /**
     * Selects the base of fill-elevation. Some modes might require precomputed elevation data in the tileset. Default value: "none".
     *
     * Use static method [FillLayer.defaultFillElevationReference] to get the default property.
     *
     * @return FillElevationReference
     */
    get() {
      getPropertyValue<String?>("fill-elevation-reference")?.let {
        return FillElevationReference.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Selects the base of fill-elevation. Some modes might require precomputed elevation data in the tileset. Default value: "none".
   *
   * Use static method [FillLayer.defaultFillElevationReference] to set the default property.
   *
   * @param fillElevationReference value of fillElevationReference
   */
  @MapboxExperimental
  override fun fillElevationReference(fillElevationReference: FillElevationReference): FillLayer = apply {
    val propertyValue = PropertyValue("fill-elevation-reference", fillElevationReference)
    setProperty(propertyValue)
  }

  /**
   * Selects the base of fill-elevation. Some modes might require precomputed elevation data in the tileset. Default value: "none".
   *
   * This is an Expression representation of "fill-elevation-reference".
   *
   */
  @MapboxExperimental
  val fillElevationReferenceAsExpression: Expression?
    /**
     * Selects the base of fill-elevation. Some modes might require precomputed elevation data in the tileset. Default value: "none".
     *
     * Get the FillElevationReference property as an Expression
     *
     * Use static method [FillLayer.defaultFillElevationReferenceAsExpression] to get the default property.
     */
    get() =
      getPropertyValue("fill-elevation-reference")
        ?: fillElevationReference?.let {
          Expression.literal(it.value)
        }

  /**
   * Selects the base of fill-elevation. Some modes might require precomputed elevation data in the tileset. Default value: "none".
   *
   * Use static method [FillLayer.defaultFillElevationReferenceAsExpression] to set the default property.
   *
   * @param fillElevationReference value of fillElevationReference as Expression
   */
  @MapboxExperimental
  override fun fillElevationReference(fillElevationReference: Expression): FillLayer = apply {
    val propertyValue = PropertyValue("fill-elevation-reference", fillElevationReference)
    setProperty(propertyValue)
  }

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
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("fill-sort-key")

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
   * Whether or not the fill should be antialiased. Default value: true.
   */
  val fillAntialias: Boolean?
    /**
     * Whether or not the fill should be antialiased. Default value: true.
     *
     * Use static method [FillLayer.defaultFillAntialias] to get the default property.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("fill-antialias")
    }

  /**
   * Whether or not the fill should be antialiased. Default value: true.
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
   * Whether or not the fill should be antialiased. Default value: true.
   *
   * This is an Expression representation of "fill-antialias".
   *
   */
  val fillAntialiasAsExpression: Expression?
    /**
     * Whether or not the fill should be antialiased. Default value: true.
     *
     * Get the FillAntialias property as an Expression
     *
     * Use static method [FillLayer.defaultFillAntialiasAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("fill-antialias")

  /**
   * Whether or not the fill should be antialiased. Default value: true.
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
   * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
   */
  @MapboxExperimental
  val fillBridgeGuardRailColor: String?
    /**
     * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
     *
     * Use static method [FillLayer.defaultFillBridgeGuardRailColor] to get the default property.
     *
     * @return String
     */
    get() {
      fillBridgeGuardRailColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
   *
   * Use static method [FillLayer.defaultFillBridgeGuardRailColor] to set the default property.
   *
   * @param fillBridgeGuardRailColor value of fillBridgeGuardRailColor
   */
  @MapboxExperimental
  override fun fillBridgeGuardRailColor(fillBridgeGuardRailColor: String): FillLayer = apply {
    val propertyValue = PropertyValue("fill-bridge-guard-rail-color", fillBridgeGuardRailColor)
    setProperty(propertyValue)
  }

  /**
   * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
   *
   * This is an Expression representation of "fill-bridge-guard-rail-color".
   *
   */
  @MapboxExperimental
  val fillBridgeGuardRailColorAsExpression: Expression?
    /**
     * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
     *
     * Get the FillBridgeGuardRailColor property as an Expression
     *
     * Use static method [FillLayer.defaultFillBridgeGuardRailColorAsExpression] to get the default property.
     */
    get() =
      getPropertyValue("fill-bridge-guard-rail-color")

  /**
   * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
   *
   * Use static method [FillLayer.defaultFillBridgeGuardRailColorAsExpression] to set the default property.
   *
   * @param fillBridgeGuardRailColor value of fillBridgeGuardRailColor as Expression
   */
  @MapboxExperimental
  override fun fillBridgeGuardRailColor(fillBridgeGuardRailColor: Expression): FillLayer = apply {
    val propertyValue = PropertyValue("fill-bridge-guard-rail-color", fillBridgeGuardRailColor)
    setProperty(propertyValue)
  }

  /**
   * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
   */
  @MapboxExperimental
  val fillBridgeGuardRailColorAsColorInt: Int?
    /**
     * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
     *
     * Use static method [FillLayer.defaultFillBridgeGuardRailColorAsColorInt] to get the default property.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      fillBridgeGuardRailColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
   *
   * Use static method [FillLayer.defaultFillBridgeGuardRailColorAsColorInt] to set the default property.
   *
   * @param fillBridgeGuardRailColor value of fillBridgeGuardRailColor
   */
  @MapboxExperimental
  override fun fillBridgeGuardRailColor(@ColorInt fillBridgeGuardRailColor: Int): FillLayer = apply {
    val propertyValue = PropertyValue("fill-bridge-guard-rail-color", colorIntToRgbaExpression(fillBridgeGuardRailColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillBridgeGuardRailColor.
   */
  @MapboxExperimental
  val fillBridgeGuardRailColorTransition: StyleTransition?
    /**
     * Get the FillBridgeGuardRailColor property transition options
     *
     * Use static method [FillLayer.defaultFillBridgeGuardRailColorTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("fill-bridge-guard-rail-color-transition")
    }

  /**
   * Set the FillBridgeGuardRailColor property transition options
   *
   * Use static method [FillLayer.defaultFillBridgeGuardRailColorTransition] to set the default property.
   *
   * @param options transition options for String
   */
  @MapboxExperimental
  override fun fillBridgeGuardRailColorTransition(options: StyleTransition): FillLayer = apply {
    val propertyValue = PropertyValue("fill-bridge-guard-rail-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillBridgeGuardRailColorTransition].
   */
  @MapboxExperimental
  override fun fillBridgeGuardRailColorTransition(block: StyleTransition.Builder.() -> Unit): FillLayer = apply {
    fillBridgeGuardRailColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Сolor theme override for [fillBridgeGuardRailColor].
   */
  @MapboxExperimental
  val fillBridgeGuardRailColorUseTheme: String?
    /**
     * Get the FillBridgeGuardRailColorUseTheme property
     *
     * Use static method [FillLayer.defaultFillBridgeGuardRailColorUseTheme] to get the default property.
     *
     * @return current FillBridgeGuardRailColorUseTheme property as String
     */
    get() {
      return getPropertyValue("fill-bridge-guard-rail-color-use-theme")
    }

  /**
   * Set the FillBridgeGuardRailColorUseTheme as String
   *
   * Use static method [FillLayer.defaultFillBridgeGuardRailColorUseTheme] to get the default property.
   *
   * @param fillBridgeGuardRailColorUseTheme theme value for color. Overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  override fun fillBridgeGuardRailColorUseTheme(fillBridgeGuardRailColorUseTheme: String): FillLayer = apply {
    val propertyValue = PropertyValue("fill-bridge-guard-rail-color-use-theme", fillBridgeGuardRailColorUseTheme)
    setProperty(propertyValue)
  }

  /**
   * Сolor theme override for [fillBridgeGuardRailColor].
   */
  @MapboxExperimental
  val fillBridgeGuardRailColorUseThemeAsExpression: Expression?
    /**
     * Get the FillBridgeGuardRailColorUseTheme property
     *
     * Use static method [FillLayer.defaultFillBridgeGuardRailColorUseTheme] to get the default property.
     *
     * @return current FillBridgeGuardRailColorUseTheme property as String
     */
    get() = getPropertyValueAsExpressionOrLiteralExpression("fill-bridge-guard-rail-color-use-theme")

  /**
   * Set the FillBridgeGuardRailColorUseTheme as Expression
   *
   * Use static method [FillLayer.defaultFillBridgeGuardRailColorUseTheme] to get the default property.
   *
   * @param fillBridgeGuardRailColorUseTheme theme value for color. Overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  override fun fillBridgeGuardRailColorUseTheme(fillBridgeGuardRailColorUseTheme: Expression): FillLayer = apply {
    val propertyValue = PropertyValue("fill-bridge-guard-rail-color-use-theme", fillBridgeGuardRailColorUseTheme)
    setProperty(propertyValue)
  }

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
   */
  val fillColor: String?
    /**
     * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
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
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
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
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
   *
   * This is an Expression representation of "fill-color".
   *
   */
  val fillColorAsExpression: Expression?
    /**
     * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
     *
     * Get the FillColor property as an Expression
     *
     * Use static method [FillLayer.defaultFillColorAsExpression] to get the default property.
     */
    get() =
      getPropertyValue("fill-color")

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
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
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
   */
  val fillColorAsColorInt: Int?
    /**
     * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
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
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
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
   * Сolor theme override for [fillColor].
   */
  @MapboxExperimental
  val fillColorUseTheme: String?
    /**
     * Get the FillColorUseTheme property
     *
     * Use static method [FillLayer.defaultFillColorUseTheme] to get the default property.
     *
     * @return current FillColorUseTheme property as String
     */
    get() {
      return getPropertyValue("fill-color-use-theme")
    }

  /**
   * Set the FillColorUseTheme as String
   *
   * Use static method [FillLayer.defaultFillColorUseTheme] to get the default property.
   *
   * @param fillColorUseTheme theme value for color. Overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  override fun fillColorUseTheme(fillColorUseTheme: String): FillLayer = apply {
    val propertyValue = PropertyValue("fill-color-use-theme", fillColorUseTheme)
    setProperty(propertyValue)
  }

  /**
   * Сolor theme override for [fillColor].
   */
  @MapboxExperimental
  val fillColorUseThemeAsExpression: Expression?
    /**
     * Get the FillColorUseTheme property
     *
     * Use static method [FillLayer.defaultFillColorUseTheme] to get the default property.
     *
     * @return current FillColorUseTheme property as String
     */
    get() = getPropertyValueAsExpressionOrLiteralExpression("fill-color-use-theme")

  /**
   * Set the FillColorUseTheme as Expression
   *
   * Use static method [FillLayer.defaultFillColorUseTheme] to get the default property.
   *
   * @param fillColorUseTheme theme value for color. Overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  override fun fillColorUseTheme(fillColorUseTheme: Expression): FillLayer = apply {
    val propertyValue = PropertyValue("fill-color-use-theme", fillColorUseTheme)
    setProperty(propertyValue)
  }

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillEmissiveStrength is in intensity.
   */
  val fillEmissiveStrength: Double?
    /**
     * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillEmissiveStrength is in intensity.
     *
     * Use static method [FillLayer.defaultFillEmissiveStrength] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-emissive-strength")
    }

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillEmissiveStrength is in intensity.
   *
   * Use static method [FillLayer.defaultFillEmissiveStrength] to set the default property.
   *
   * @param fillEmissiveStrength value of fillEmissiveStrength
   */
  override fun fillEmissiveStrength(fillEmissiveStrength: Double): FillLayer = apply {
    val propertyValue = PropertyValue("fill-emissive-strength", fillEmissiveStrength)
    setProperty(propertyValue)
  }

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillEmissiveStrength is in intensity.
   *
   * This is an Expression representation of "fill-emissive-strength".
   *
   */
  val fillEmissiveStrengthAsExpression: Expression?
    /**
     * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillEmissiveStrength is in intensity.
     *
     * Get the FillEmissiveStrength property as an Expression
     *
     * Use static method [FillLayer.defaultFillEmissiveStrengthAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("fill-emissive-strength")

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillEmissiveStrength is in intensity.
   *
   * Use static method [FillLayer.defaultFillEmissiveStrengthAsExpression] to set the default property.
   *
   * @param fillEmissiveStrength value of fillEmissiveStrength as Expression
   */
  override fun fillEmissiveStrength(fillEmissiveStrength: Expression): FillLayer = apply {
    val propertyValue = PropertyValue("fill-emissive-strength", fillEmissiveStrength)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillEmissiveStrength.
   */
  val fillEmissiveStrengthTransition: StyleTransition?
    /**
     * Get the FillEmissiveStrength property transition options
     *
     * Use static method [FillLayer.defaultFillEmissiveStrengthTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-emissive-strength-transition")
    }

  /**
   * Set the FillEmissiveStrength property transition options
   *
   * Use static method [FillLayer.defaultFillEmissiveStrengthTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun fillEmissiveStrengthTransition(options: StyleTransition): FillLayer = apply {
    val propertyValue = PropertyValue("fill-emissive-strength-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillEmissiveStrengthTransition].
   */
  override fun fillEmissiveStrengthTransition(block: StyleTransition.Builder.() -> Unit): FillLayer = apply {
    fillEmissiveStrengthTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used. Default value: 1. Value range: [0, 1]
   */
  val fillOpacity: Double?
    /**
     * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used. Default value: 1. Value range: [0, 1]
     *
     * Use static method [FillLayer.defaultFillOpacity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-opacity")
    }

  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used. Default value: 1. Value range: [0, 1]
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
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used. Default value: 1. Value range: [0, 1]
   *
   * This is an Expression representation of "fill-opacity".
   *
   */
  val fillOpacityAsExpression: Expression?
    /**
     * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used. Default value: 1. Value range: [0, 1]
     *
     * Get the FillOpacity property as an Expression
     *
     * Use static method [FillLayer.defaultFillOpacityAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("fill-opacity")

  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used. Default value: 1. Value range: [0, 1]
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
     */
    get() =
      getPropertyValue("fill-outline-color")

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
   * Сolor theme override for [fillOutlineColor].
   */
  @MapboxExperimental
  val fillOutlineColorUseTheme: String?
    /**
     * Get the FillOutlineColorUseTheme property
     *
     * Use static method [FillLayer.defaultFillOutlineColorUseTheme] to get the default property.
     *
     * @return current FillOutlineColorUseTheme property as String
     */
    get() {
      return getPropertyValue("fill-outline-color-use-theme")
    }

  /**
   * Set the FillOutlineColorUseTheme as String
   *
   * Use static method [FillLayer.defaultFillOutlineColorUseTheme] to get the default property.
   *
   * @param fillOutlineColorUseTheme theme value for color. Overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  override fun fillOutlineColorUseTheme(fillOutlineColorUseTheme: String): FillLayer = apply {
    val propertyValue = PropertyValue("fill-outline-color-use-theme", fillOutlineColorUseTheme)
    setProperty(propertyValue)
  }

  /**
   * Сolor theme override for [fillOutlineColor].
   */
  @MapboxExperimental
  val fillOutlineColorUseThemeAsExpression: Expression?
    /**
     * Get the FillOutlineColorUseTheme property
     *
     * Use static method [FillLayer.defaultFillOutlineColorUseTheme] to get the default property.
     *
     * @return current FillOutlineColorUseTheme property as String
     */
    get() = getPropertyValueAsExpressionOrLiteralExpression("fill-outline-color-use-theme")

  /**
   * Set the FillOutlineColorUseTheme as Expression
   *
   * Use static method [FillLayer.defaultFillOutlineColorUseTheme] to get the default property.
   *
   * @param fillOutlineColorUseTheme theme value for color. Overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  override fun fillOutlineColorUseTheme(fillOutlineColorUseTheme: Expression): FillLayer = apply {
    val propertyValue = PropertyValue("fill-outline-color-use-theme", fillOutlineColorUseTheme)
    setProperty(propertyValue)
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
      return getPropertyValue("fill-pattern")
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
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("fill-pattern")

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
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of fillTranslate is in pixels.
   */
  val fillTranslate: List<Double>?
    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of fillTranslate is in pixels.
     *
     * Use static method [FillLayer.defaultFillTranslate] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue("fill-translate")
    }

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of fillTranslate is in pixels.
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
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of fillTranslate is in pixels.
   *
   * This is an Expression representation of "fill-translate".
   *
   */
  val fillTranslateAsExpression: Expression?
    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of fillTranslate is in pixels.
     *
     * Get the FillTranslate property as an Expression
     *
     * Use static method [FillLayer.defaultFillTranslateAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("fill-translate")

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of fillTranslate is in pixels.
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
   * Controls the frame of reference for `fill-translate`. Default value: "map".
   */
  val fillTranslateAnchor: FillTranslateAnchor?
    /**
     * Controls the frame of reference for `fill-translate`. Default value: "map".
     *
     * Use static method [FillLayer.defaultFillTranslateAnchor] to get the default property.
     *
     * @return FillTranslateAnchor
     */
    get() {
      getPropertyValue<String?>("fill-translate-anchor")?.let {
        return FillTranslateAnchor.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Controls the frame of reference for `fill-translate`. Default value: "map".
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
   * Controls the frame of reference for `fill-translate`. Default value: "map".
   *
   * This is an Expression representation of "fill-translate-anchor".
   *
   */
  val fillTranslateAnchorAsExpression: Expression?
    /**
     * Controls the frame of reference for `fill-translate`. Default value: "map".
     *
     * Get the FillTranslateAnchor property as an Expression
     *
     * Use static method [FillLayer.defaultFillTranslateAnchorAsExpression] to get the default property.
     */
    get() =
      getPropertyValue("fill-translate-anchor")
        ?: fillTranslateAnchor?.let {
          Expression.literal(it.value)
        }

  /**
   * Controls the frame of reference for `fill-translate`. Default value: "map".
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
   * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
   */
  @MapboxExperimental
  val fillTunnelStructureColor: String?
    /**
     * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
     *
     * Use static method [FillLayer.defaultFillTunnelStructureColor] to get the default property.
     *
     * @return String
     */
    get() {
      fillTunnelStructureColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
   *
   * Use static method [FillLayer.defaultFillTunnelStructureColor] to set the default property.
   *
   * @param fillTunnelStructureColor value of fillTunnelStructureColor
   */
  @MapboxExperimental
  override fun fillTunnelStructureColor(fillTunnelStructureColor: String): FillLayer = apply {
    val propertyValue = PropertyValue("fill-tunnel-structure-color", fillTunnelStructureColor)
    setProperty(propertyValue)
  }

  /**
   * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
   *
   * This is an Expression representation of "fill-tunnel-structure-color".
   *
   */
  @MapboxExperimental
  val fillTunnelStructureColorAsExpression: Expression?
    /**
     * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
     *
     * Get the FillTunnelStructureColor property as an Expression
     *
     * Use static method [FillLayer.defaultFillTunnelStructureColorAsExpression] to get the default property.
     */
    get() =
      getPropertyValue("fill-tunnel-structure-color")

  /**
   * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
   *
   * Use static method [FillLayer.defaultFillTunnelStructureColorAsExpression] to set the default property.
   *
   * @param fillTunnelStructureColor value of fillTunnelStructureColor as Expression
   */
  @MapboxExperimental
  override fun fillTunnelStructureColor(fillTunnelStructureColor: Expression): FillLayer = apply {
    val propertyValue = PropertyValue("fill-tunnel-structure-color", fillTunnelStructureColor)
    setProperty(propertyValue)
  }

  /**
   * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
   */
  @MapboxExperimental
  val fillTunnelStructureColorAsColorInt: Int?
    /**
     * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
     *
     * Use static method [FillLayer.defaultFillTunnelStructureColorAsColorInt] to get the default property.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      fillTunnelStructureColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
   *
   * Use static method [FillLayer.defaultFillTunnelStructureColorAsColorInt] to set the default property.
   *
   * @param fillTunnelStructureColor value of fillTunnelStructureColor
   */
  @MapboxExperimental
  override fun fillTunnelStructureColor(@ColorInt fillTunnelStructureColor: Int): FillLayer = apply {
    val propertyValue = PropertyValue("fill-tunnel-structure-color", colorIntToRgbaExpression(fillTunnelStructureColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillTunnelStructureColor.
   */
  @MapboxExperimental
  val fillTunnelStructureColorTransition: StyleTransition?
    /**
     * Get the FillTunnelStructureColor property transition options
     *
     * Use static method [FillLayer.defaultFillTunnelStructureColorTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("fill-tunnel-structure-color-transition")
    }

  /**
   * Set the FillTunnelStructureColor property transition options
   *
   * Use static method [FillLayer.defaultFillTunnelStructureColorTransition] to set the default property.
   *
   * @param options transition options for String
   */
  @MapboxExperimental
  override fun fillTunnelStructureColorTransition(options: StyleTransition): FillLayer = apply {
    val propertyValue = PropertyValue("fill-tunnel-structure-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillTunnelStructureColorTransition].
   */
  @MapboxExperimental
  override fun fillTunnelStructureColorTransition(block: StyleTransition.Builder.() -> Unit): FillLayer = apply {
    fillTunnelStructureColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Сolor theme override for [fillTunnelStructureColor].
   */
  @MapboxExperimental
  val fillTunnelStructureColorUseTheme: String?
    /**
     * Get the FillTunnelStructureColorUseTheme property
     *
     * Use static method [FillLayer.defaultFillTunnelStructureColorUseTheme] to get the default property.
     *
     * @return current FillTunnelStructureColorUseTheme property as String
     */
    get() {
      return getPropertyValue("fill-tunnel-structure-color-use-theme")
    }

  /**
   * Set the FillTunnelStructureColorUseTheme as String
   *
   * Use static method [FillLayer.defaultFillTunnelStructureColorUseTheme] to get the default property.
   *
   * @param fillTunnelStructureColorUseTheme theme value for color. Overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  override fun fillTunnelStructureColorUseTheme(fillTunnelStructureColorUseTheme: String): FillLayer = apply {
    val propertyValue = PropertyValue("fill-tunnel-structure-color-use-theme", fillTunnelStructureColorUseTheme)
    setProperty(propertyValue)
  }

  /**
   * Сolor theme override for [fillTunnelStructureColor].
   */
  @MapboxExperimental
  val fillTunnelStructureColorUseThemeAsExpression: Expression?
    /**
     * Get the FillTunnelStructureColorUseTheme property
     *
     * Use static method [FillLayer.defaultFillTunnelStructureColorUseTheme] to get the default property.
     *
     * @return current FillTunnelStructureColorUseTheme property as String
     */
    get() = getPropertyValueAsExpressionOrLiteralExpression("fill-tunnel-structure-color-use-theme")

  /**
   * Set the FillTunnelStructureColorUseTheme as Expression
   *
   * Use static method [FillLayer.defaultFillTunnelStructureColorUseTheme] to get the default property.
   *
   * @param fillTunnelStructureColorUseTheme theme value for color. Overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  override fun fillTunnelStructureColorUseTheme(fillTunnelStructureColorUseTheme: Expression): FillLayer = apply {
    val propertyValue = PropertyValue("fill-tunnel-structure-color-use-theme", fillTunnelStructureColorUseTheme)
    setProperty(propertyValue)
  }

  /**
   * Specifies an uniform elevation in meters. Note: If the value is zero, the layer will be rendered on the ground. Non-zero values will elevate the layer from the sea level, which can cause it to be rendered below the terrain. Default value: 0. Minimum value: 0.
   */
  @MapboxExperimental
  val fillZOffset: Double?
    /**
     * Specifies an uniform elevation in meters. Note: If the value is zero, the layer will be rendered on the ground. Non-zero values will elevate the layer from the sea level, which can cause it to be rendered below the terrain. Default value: 0. Minimum value: 0.
     *
     * Use static method [FillLayer.defaultFillZOffset] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("fill-z-offset")
    }

  /**
   * Specifies an uniform elevation in meters. Note: If the value is zero, the layer will be rendered on the ground. Non-zero values will elevate the layer from the sea level, which can cause it to be rendered below the terrain. Default value: 0. Minimum value: 0.
   *
   * Use static method [FillLayer.defaultFillZOffset] to set the default property.
   *
   * @param fillZOffset value of fillZOffset
   */
  @MapboxExperimental
  override fun fillZOffset(fillZOffset: Double): FillLayer = apply {
    val propertyValue = PropertyValue("fill-z-offset", fillZOffset)
    setProperty(propertyValue)
  }

  /**
   * Specifies an uniform elevation in meters. Note: If the value is zero, the layer will be rendered on the ground. Non-zero values will elevate the layer from the sea level, which can cause it to be rendered below the terrain. Default value: 0. Minimum value: 0.
   *
   * This is an Expression representation of "fill-z-offset".
   *
   */
  @MapboxExperimental
  val fillZOffsetAsExpression: Expression?
    /**
     * Specifies an uniform elevation in meters. Note: If the value is zero, the layer will be rendered on the ground. Non-zero values will elevate the layer from the sea level, which can cause it to be rendered below the terrain. Default value: 0. Minimum value: 0.
     *
     * Get the FillZOffset property as an Expression
     *
     * Use static method [FillLayer.defaultFillZOffsetAsExpression] to get the default property.
     */
    get() =
      getPropertyValueAsExpressionOrLiteralExpression("fill-z-offset")

  /**
   * Specifies an uniform elevation in meters. Note: If the value is zero, the layer will be rendered on the ground. Non-zero values will elevate the layer from the sea level, which can cause it to be rendered below the terrain. Default value: 0. Minimum value: 0.
   *
   * Use static method [FillLayer.defaultFillZOffsetAsExpression] to set the default property.
   *
   * @param fillZOffset value of fillZOffset as Expression
   */
  @MapboxExperimental
  override fun fillZOffset(fillZOffset: Expression): FillLayer = apply {
    val propertyValue = PropertyValue("fill-z-offset", fillZOffset)
    setProperty(propertyValue)
  }

  /**
   * Transition options for FillZOffset.
   */
  @MapboxExperimental
  val fillZOffsetTransition: StyleTransition?
    /**
     * Get the FillZOffset property transition options
     *
     * Use static method [FillLayer.defaultFillZOffsetTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("fill-z-offset-transition")
    }

  /**
   * Set the FillZOffset property transition options
   *
   * Use static method [FillLayer.defaultFillZOffsetTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun fillZOffsetTransition(options: StyleTransition): FillLayer = apply {
    val propertyValue = PropertyValue("fill-z-offset-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [fillZOffsetTransition].
   */
  @MapboxExperimental
  override fun fillZOffsetTransition(block: StyleTransition.Builder.() -> Unit): FillLayer = apply {
    fillZOffsetTransition(StyleTransition.Builder().apply(block).build())
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
     * Determines whether bridge guard rails are added for elevated roads. Default value: "true".
     */
    @MapboxExperimental
    val defaultFillConstructBridgeGuardRail: Boolean?
      /**
       * Determines whether bridge guard rails are added for elevated roads. Default value: "true".
       *
       * Get the default value of FillConstructBridgeGuardRail property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-construct-bridge-guard-rail").silentUnwrap()
      }

    /**
     * Determines whether bridge guard rails are added for elevated roads. Default value: "true".
     *
     * This is an Expression representation of "fill-construct-bridge-guard-rail".
     *
     */
    @MapboxExperimental
    val defaultFillConstructBridgeGuardRailAsExpression: Expression?
      /**
       * Get default value of the FillConstructBridgeGuardRail property as an Expression
       *
       * @return Boolean
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-construct-bridge-guard-rail").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillConstructBridgeGuardRail?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Selects the base of fill-elevation. Some modes might require precomputed elevation data in the tileset. Default value: "none".
     */
    @MapboxExperimental
    val defaultFillElevationReference: FillElevationReference?
      /**
       * Selects the base of fill-elevation. Some modes might require precomputed elevation data in the tileset. Default value: "none".
       *
       * Get the default value of FillElevationReference property
       *
       * @return FillElevationReference
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-elevation-reference").silentUnwrap<String>()?.let {
          return FillElevationReference.valueOf(it.uppercase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Selects the base of fill-elevation. Some modes might require precomputed elevation data in the tileset. Default value: "none".
     *
     * This is an Expression representation of "fill-elevation-reference".
     *
     */
    @MapboxExperimental
    val defaultFillElevationReferenceAsExpression: Expression?
      /**
       * Get default value of the FillElevationReference property as an Expression
       *
       * @return FillElevationReference
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-elevation-reference").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillElevationReference?.let {
          return Expression.literal(it.value)
        }
        return null
      }

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
     * Whether or not the fill should be antialiased. Default value: true.
     */
    val defaultFillAntialias: Boolean?
      /**
       * Whether or not the fill should be antialiased. Default value: true.
       *
       * Get the default value of FillAntialias property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-antialias").silentUnwrap()
      }

    /**
     * Whether or not the fill should be antialiased. Default value: true.
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
     * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
     */
    @MapboxExperimental
    val defaultFillBridgeGuardRailColor: String?
      /**
       * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
       *
       * Get the default value of FillBridgeGuardRailColor property
       *
       * @return String
       */
      get() {
        defaultFillBridgeGuardRailColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
     *
     * This is an Expression representation of "fill-bridge-guard-rail-color".
     *
     */
    @MapboxExperimental
    val defaultFillBridgeGuardRailColorAsExpression: Expression?
      /**
       * Get default value of the FillBridgeGuardRailColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-bridge-guard-rail-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
     */
    @MapboxExperimental
    val defaultFillBridgeGuardRailColorAsColorInt: Int?
      /**
       * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
       *
       * Get the default value of FillBridgeGuardRailColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultFillBridgeGuardRailColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for FillBridgeGuardRailColor.
     */
    @MapboxExperimental
    val defaultFillBridgeGuardRailColorTransition: StyleTransition?
      /**
       * Get the FillBridgeGuardRailColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-bridge-guard-rail-color-transition").silentUnwrap()

    /**
     * Default color theme for [fillBridgeGuardRailColor].
     */
    @MapboxExperimental
    val defaultFillBridgeGuardRailColorUseTheme: String?
      /**
       * Get default value of the FillBridgeGuardRailColor property as String
       *
       * @return String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-bridge-guard-rail-color-use-theme").silentUnwrap()

    /**
     * Default color theme for [fillBridgeGuardRailColor].
     */
    @MapboxExperimental
    val defaultFillBridgeGuardRailColorUseThemeAsExpression: Expression?
      /**
       * Get default value of the FillBridgeGuardRailColor property as Expression
       *
       * @return String
       */
      get() {
        return StyleManager
          .getStyleLayerPropertyDefaultValue("fill", "fill-bridge-guard-rail-color-use-theme")
          .silentUnwrap<Expression>() ?: defaultFillBridgeGuardRailColorUseTheme?.let { Expression.literal(it) }
      }

    /**
     * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
     */
    val defaultFillColor: String?
      /**
       * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
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
     * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
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
     * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
     */
    val defaultFillColorAsColorInt: Int?
      /**
       * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
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
     * Default color theme for [fillColor].
     */
    @MapboxExperimental
    val defaultFillColorUseTheme: String?
      /**
       * Get default value of the FillColor property as String
       *
       * @return String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-color-use-theme").silentUnwrap()

    /**
     * Default color theme for [fillColor].
     */
    @MapboxExperimental
    val defaultFillColorUseThemeAsExpression: Expression?
      /**
       * Get default value of the FillColor property as Expression
       *
       * @return String
       */
      get() {
        return StyleManager
          .getStyleLayerPropertyDefaultValue("fill", "fill-color-use-theme")
          .silentUnwrap<Expression>() ?: defaultFillColorUseTheme?.let { Expression.literal(it) }
      }

    /**
     * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillEmissiveStrength is in intensity.
     */
    val defaultFillEmissiveStrength: Double?
      /**
       * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillEmissiveStrength is in intensity.
       *
       * Get the default value of FillEmissiveStrength property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-emissive-strength").silentUnwrap()
      }

    /**
     * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillEmissiveStrength is in intensity.
     *
     * This is an Expression representation of "fill-emissive-strength".
     *
     */
    val defaultFillEmissiveStrengthAsExpression: Expression?
      /**
       * Get default value of the FillEmissiveStrength property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-emissive-strength").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillEmissiveStrength?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillEmissiveStrength.
     */
    val defaultFillEmissiveStrengthTransition: StyleTransition?
      /**
       * Get the FillEmissiveStrength property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-emissive-strength-transition").silentUnwrap()

    /**
     * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used. Default value: 1. Value range: [0, 1]
     */
    val defaultFillOpacity: Double?
      /**
       * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used. Default value: 1. Value range: [0, 1]
       *
       * Get the default value of FillOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-opacity").silentUnwrap()
      }

    /**
     * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used. Default value: 1. Value range: [0, 1]
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
     * Default color theme for [fillOutlineColor].
     */
    @MapboxExperimental
    val defaultFillOutlineColorUseTheme: String?
      /**
       * Get default value of the FillOutlineColor property as String
       *
       * @return String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-outline-color-use-theme").silentUnwrap()

    /**
     * Default color theme for [fillOutlineColor].
     */
    @MapboxExperimental
    val defaultFillOutlineColorUseThemeAsExpression: Expression?
      /**
       * Get default value of the FillOutlineColor property as Expression
       *
       * @return String
       */
      get() {
        return StyleManager
          .getStyleLayerPropertyDefaultValue("fill", "fill-outline-color-use-theme")
          .silentUnwrap<Expression>() ?: defaultFillOutlineColorUseTheme?.let { Expression.literal(it) }
      }

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
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of fillTranslate is in pixels.
     */
    val defaultFillTranslate: List<Double>?
      /**
       * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of fillTranslate is in pixels.
       *
       * Get the default value of FillTranslate property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate").silentUnwrap()
      }

    /**
     * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of fillTranslate is in pixels.
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
     * Controls the frame of reference for `fill-translate`. Default value: "map".
     */
    val defaultFillTranslateAnchor: FillTranslateAnchor?
      /**
       * Controls the frame of reference for `fill-translate`. Default value: "map".
       *
       * Get the default value of FillTranslateAnchor property
       *
       * @return FillTranslateAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate-anchor").silentUnwrap<String>()?.let {
          return FillTranslateAnchor.valueOf(it.uppercase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Controls the frame of reference for `fill-translate`. Default value: "map".
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

    /**
     * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
     */
    @MapboxExperimental
    val defaultFillTunnelStructureColor: String?
      /**
       * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
       *
       * Get the default value of FillTunnelStructureColor property
       *
       * @return String
       */
      get() {
        defaultFillTunnelStructureColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
     *
     * This is an Expression representation of "fill-tunnel-structure-color".
     *
     */
    @MapboxExperimental
    val defaultFillTunnelStructureColorAsExpression: Expression?
      /**
       * Get default value of the FillTunnelStructureColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-tunnel-structure-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
     */
    @MapboxExperimental
    val defaultFillTunnelStructureColorAsColorInt: Int?
      /**
       * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
       *
       * Get the default value of FillTunnelStructureColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultFillTunnelStructureColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for FillTunnelStructureColor.
     */
    @MapboxExperimental
    val defaultFillTunnelStructureColorTransition: StyleTransition?
      /**
       * Get the FillTunnelStructureColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-tunnel-structure-color-transition").silentUnwrap()

    /**
     * Default color theme for [fillTunnelStructureColor].
     */
    @MapboxExperimental
    val defaultFillTunnelStructureColorUseTheme: String?
      /**
       * Get default value of the FillTunnelStructureColor property as String
       *
       * @return String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-tunnel-structure-color-use-theme").silentUnwrap()

    /**
     * Default color theme for [fillTunnelStructureColor].
     */
    @MapboxExperimental
    val defaultFillTunnelStructureColorUseThemeAsExpression: Expression?
      /**
       * Get default value of the FillTunnelStructureColor property as Expression
       *
       * @return String
       */
      get() {
        return StyleManager
          .getStyleLayerPropertyDefaultValue("fill", "fill-tunnel-structure-color-use-theme")
          .silentUnwrap<Expression>() ?: defaultFillTunnelStructureColorUseTheme?.let { Expression.literal(it) }
      }

    /**
     * Specifies an uniform elevation in meters. Note: If the value is zero, the layer will be rendered on the ground. Non-zero values will elevate the layer from the sea level, which can cause it to be rendered below the terrain. Default value: 0. Minimum value: 0.
     */
    @MapboxExperimental
    val defaultFillZOffset: Double?
      /**
       * Specifies an uniform elevation in meters. Note: If the value is zero, the layer will be rendered on the ground. Non-zero values will elevate the layer from the sea level, which can cause it to be rendered below the terrain. Default value: 0. Minimum value: 0.
       *
       * Get the default value of FillZOffset property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-z-offset").silentUnwrap()
      }

    /**
     * Specifies an uniform elevation in meters. Note: If the value is zero, the layer will be rendered on the ground. Non-zero values will elevate the layer from the sea level, which can cause it to be rendered below the terrain. Default value: 0. Minimum value: 0.
     *
     * This is an Expression representation of "fill-z-offset".
     *
     */
    @MapboxExperimental
    val defaultFillZOffsetAsExpression: Expression?
      /**
       * Get default value of the FillZOffset property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-z-offset").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultFillZOffset?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for FillZOffset.
     */
    @MapboxExperimental
    val defaultFillZOffsetTransition: StyleTransition?
      /**
       * Get the FillZOffset property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-z-offset-transition").silentUnwrap()
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
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   *
   * @param slot value of slot. Setting it to empty string removes the slot.
   */
  fun slot(slot: String): FillLayer

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
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility as Expression
   */
  fun visibility(visibility: Expression): FillLayer

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param minZoom value of minzoom
   */
  fun minZoom(minZoom: Double): FillLayer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param maxZoom value of maxzoom
   */
  fun maxZoom(maxZoom: Double): FillLayer

  // Property getters and setters

  /**
   * Determines whether bridge guard rails are added for elevated roads. Default value: "true".
   *
   * @param fillConstructBridgeGuardRail value of fillConstructBridgeGuardRail
   */
  @MapboxExperimental
  fun fillConstructBridgeGuardRail(fillConstructBridgeGuardRail: Boolean = true): FillLayer

  /**
   * Determines whether bridge guard rails are added for elevated roads. Default value: "true".
   *
   * @param fillConstructBridgeGuardRail value of fillConstructBridgeGuardRail as Expression
   */
  @MapboxExperimental
  fun fillConstructBridgeGuardRail(fillConstructBridgeGuardRail: Expression): FillLayer

  /**
   * Selects the base of fill-elevation. Some modes might require precomputed elevation data in the tileset. Default value: "none".
   *
   * @param fillElevationReference value of fillElevationReference
   */
  @MapboxExperimental
  fun fillElevationReference(fillElevationReference: FillElevationReference = FillElevationReference.NONE): FillLayer

  /**
   * Selects the base of fill-elevation. Some modes might require precomputed elevation data in the tileset. Default value: "none".
   *
   * @param fillElevationReference value of fillElevationReference as Expression
   */
  @MapboxExperimental
  fun fillElevationReference(fillElevationReference: Expression): FillLayer

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
   * Whether or not the fill should be antialiased. Default value: true.
   *
   * @param fillAntialias value of fillAntialias
   */
  fun fillAntialias(fillAntialias: Boolean = true): FillLayer

  /**
   * Whether or not the fill should be antialiased. Default value: true.
   *
   * @param fillAntialias value of fillAntialias as Expression
   */
  fun fillAntialias(fillAntialias: Expression): FillLayer

  /**
   * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
   *
   * @param fillBridgeGuardRailColor value of fillBridgeGuardRailColor
   */
  @MapboxExperimental
  fun fillBridgeGuardRailColor(fillBridgeGuardRailColor: String = "rgba(241, 236, 225, 255)"): FillLayer

  /**
   * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
   *
   * @param fillBridgeGuardRailColor value of fillBridgeGuardRailColor as Expression
   */
  @MapboxExperimental
  fun fillBridgeGuardRailColor(fillBridgeGuardRailColor: Expression): FillLayer

  /**
   * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
   *
   * @param fillBridgeGuardRailColor value of fillBridgeGuardRailColor
   */
  @MapboxExperimental
  fun fillBridgeGuardRailColor(@ColorInt fillBridgeGuardRailColor: Int): FillLayer

  /**
   * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
   *
   * Set the FillBridgeGuardRailColor property transition options
   *
   * @param options transition options for String
   */
  @MapboxExperimental
  fun fillBridgeGuardRailColorTransition(options: StyleTransition): FillLayer

  /**
   * The color of bridge guard rail. Default value: "rgba(241, 236, 225, 255)".
   *
   * DSL for [fillBridgeGuardRailColorTransition].
   */
  @MapboxExperimental
  fun fillBridgeGuardRailColorTransition(block: StyleTransition.Builder.() -> Unit): FillLayer

  /**
   * Set the fillBridgeGuardRailColorUseTheme as String for [fillBridgeGuardRailColor].
   *
   * @param fillBridgeGuardRailColorUseTheme overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  fun fillBridgeGuardRailColorUseTheme(fillBridgeGuardRailColorUseTheme: String): FillLayer

  /**
   * Set the fillBridgeGuardRailColorUseTheme as Expression for [fillBridgeGuardRailColor].
   *
   * @param fillBridgeGuardRailColorUseTheme overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  fun fillBridgeGuardRailColorUseTheme(fillBridgeGuardRailColorUseTheme: Expression): FillLayer

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
   *
   * @param fillColor value of fillColor
   */
  fun fillColor(fillColor: String = "#000000"): FillLayer

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
   *
   * @param fillColor value of fillColor as Expression
   */
  fun fillColor(fillColor: Expression): FillLayer

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
   *
   * @param fillColor value of fillColor
   */
  fun fillColor(@ColorInt fillColor: Int): FillLayer

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
   *
   * Set the FillColor property transition options
   *
   * @param options transition options for String
   */
  fun fillColorTransition(options: StyleTransition): FillLayer

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
   *
   * DSL for [fillColorTransition].
   */
  fun fillColorTransition(block: StyleTransition.Builder.() -> Unit): FillLayer

  /**
   * Set the fillColorUseTheme as String for [fillColor].
   *
   * @param fillColorUseTheme overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  fun fillColorUseTheme(fillColorUseTheme: String): FillLayer

  /**
   * Set the fillColorUseTheme as Expression for [fillColor].
   *
   * @param fillColorUseTheme overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  fun fillColorUseTheme(fillColorUseTheme: Expression): FillLayer

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillEmissiveStrength is in intensity.
   *
   * @param fillEmissiveStrength value of fillEmissiveStrength
   */
  fun fillEmissiveStrength(fillEmissiveStrength: Double = 0.0): FillLayer

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillEmissiveStrength is in intensity.
   *
   * @param fillEmissiveStrength value of fillEmissiveStrength as Expression
   */
  fun fillEmissiveStrength(fillEmissiveStrength: Expression): FillLayer

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillEmissiveStrength is in intensity.
   *
   * Set the FillEmissiveStrength property transition options
   *
   * @param options transition options for Double
   */
  fun fillEmissiveStrengthTransition(options: StyleTransition): FillLayer

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of fillEmissiveStrength is in intensity.
   *
   * DSL for [fillEmissiveStrengthTransition].
   */
  fun fillEmissiveStrengthTransition(block: StyleTransition.Builder.() -> Unit): FillLayer

  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used. Default value: 1. Value range: [0, 1]
   *
   * @param fillOpacity value of fillOpacity
   */
  fun fillOpacity(fillOpacity: Double = 1.0): FillLayer

  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used. Default value: 1. Value range: [0, 1]
   *
   * @param fillOpacity value of fillOpacity as Expression
   */
  fun fillOpacity(fillOpacity: Expression): FillLayer

  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used. Default value: 1. Value range: [0, 1]
   *
   * Set the FillOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun fillOpacityTransition(options: StyleTransition): FillLayer

  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used. Default value: 1. Value range: [0, 1]
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
   * Set the fillOutlineColorUseTheme as String for [fillOutlineColor].
   *
   * @param fillOutlineColorUseTheme overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  fun fillOutlineColorUseTheme(fillOutlineColorUseTheme: String): FillLayer

  /**
   * Set the fillOutlineColorUseTheme as Expression for [fillOutlineColor].
   *
   * @param fillOutlineColorUseTheme overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  fun fillOutlineColorUseTheme(fillOutlineColorUseTheme: Expression): FillLayer

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
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of fillTranslate is in pixels.
   *
   * @param fillTranslate value of fillTranslate
   */
  fun fillTranslate(fillTranslate: List<Double> = listOf(0.0, 0.0)): FillLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of fillTranslate is in pixels.
   *
   * @param fillTranslate value of fillTranslate as Expression
   */
  fun fillTranslate(fillTranslate: Expression): FillLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of fillTranslate is in pixels.
   *
   * Set the FillTranslate property transition options
   *
   * @param options transition options for List<Double>
   */
  fun fillTranslateTransition(options: StyleTransition): FillLayer

  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of fillTranslate is in pixels.
   *
   * DSL for [fillTranslateTransition].
   */
  fun fillTranslateTransition(block: StyleTransition.Builder.() -> Unit): FillLayer

  /**
   * Controls the frame of reference for `fill-translate`. Default value: "map".
   *
   * @param fillTranslateAnchor value of fillTranslateAnchor
   */
  fun fillTranslateAnchor(fillTranslateAnchor: FillTranslateAnchor = FillTranslateAnchor.MAP): FillLayer

  /**
   * Controls the frame of reference for `fill-translate`. Default value: "map".
   *
   * @param fillTranslateAnchor value of fillTranslateAnchor as Expression
   */
  fun fillTranslateAnchor(fillTranslateAnchor: Expression): FillLayer

  /**
   * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
   *
   * @param fillTunnelStructureColor value of fillTunnelStructureColor
   */
  @MapboxExperimental
  fun fillTunnelStructureColor(fillTunnelStructureColor: String = "rgba(241, 236, 225, 255)"): FillLayer

  /**
   * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
   *
   * @param fillTunnelStructureColor value of fillTunnelStructureColor as Expression
   */
  @MapboxExperimental
  fun fillTunnelStructureColor(fillTunnelStructureColor: Expression): FillLayer

  /**
   * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
   *
   * @param fillTunnelStructureColor value of fillTunnelStructureColor
   */
  @MapboxExperimental
  fun fillTunnelStructureColor(@ColorInt fillTunnelStructureColor: Int): FillLayer

  /**
   * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
   *
   * Set the FillTunnelStructureColor property transition options
   *
   * @param options transition options for String
   */
  @MapboxExperimental
  fun fillTunnelStructureColorTransition(options: StyleTransition): FillLayer

  /**
   * The color of tunnel structures (tunnel entrance and tunnel walls). Default value: "rgba(241, 236, 225, 255)".
   *
   * DSL for [fillTunnelStructureColorTransition].
   */
  @MapboxExperimental
  fun fillTunnelStructureColorTransition(block: StyleTransition.Builder.() -> Unit): FillLayer

  /**
   * Set the fillTunnelStructureColorUseTheme as String for [fillTunnelStructureColor].
   *
   * @param fillTunnelStructureColorUseTheme overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  fun fillTunnelStructureColorUseTheme(fillTunnelStructureColorUseTheme: String): FillLayer

  /**
   * Set the fillTunnelStructureColorUseTheme as Expression for [fillTunnelStructureColor].
   *
   * @param fillTunnelStructureColorUseTheme overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  fun fillTunnelStructureColorUseTheme(fillTunnelStructureColorUseTheme: Expression): FillLayer

  /**
   * Specifies an uniform elevation in meters. Note: If the value is zero, the layer will be rendered on the ground. Non-zero values will elevate the layer from the sea level, which can cause it to be rendered below the terrain. Default value: 0. Minimum value: 0.
   *
   * @param fillZOffset value of fillZOffset
   */
  @MapboxExperimental
  fun fillZOffset(fillZOffset: Double = 0.0): FillLayer

  /**
   * Specifies an uniform elevation in meters. Note: If the value is zero, the layer will be rendered on the ground. Non-zero values will elevate the layer from the sea level, which can cause it to be rendered below the terrain. Default value: 0. Minimum value: 0.
   *
   * @param fillZOffset value of fillZOffset as Expression
   */
  @MapboxExperimental
  fun fillZOffset(fillZOffset: Expression): FillLayer

  /**
   * Specifies an uniform elevation in meters. Note: If the value is zero, the layer will be rendered on the ground. Non-zero values will elevate the layer from the sea level, which can cause it to be rendered below the terrain. Default value: 0. Minimum value: 0.
   *
   * Set the FillZOffset property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun fillZOffsetTransition(options: StyleTransition): FillLayer

  /**
   * Specifies an uniform elevation in meters. Note: If the value is zero, the layer will be rendered on the ground. Non-zero values will elevate the layer from the sea level, which can cause it to be rendered below the terrain. Default value: 0. Minimum value: 0.
   *
   * DSL for [fillZOffsetTransition].
   */
  @MapboxExperimental
  fun fillZOffsetTransition(block: StyleTransition.Builder.() -> Unit): FillLayer
}

/**
 * DSL function for creating a [FillLayer].
 */
fun fillLayer(layerId: String, sourceId: String, block: FillLayerDsl.() -> Unit): FillLayer = FillLayer(layerId, sourceId).apply(block)

// End of generated file.