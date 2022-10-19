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
 * Client-side hillshading visualization based on DEM data. Currently, the implementation only supports Mapbox Terrain RGB and Mapzen Terrarium tiles.
 *
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-hillshade)
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
 */
@UiThread
class HillshadeLayer(override val layerId: String, val sourceId: String) : HillshadeLayerDsl, Layer() {
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
   * Whether this layer is displayed.
   */
  override val visibility: Visibility?
    /**
     * Whether this layer is displayed.
     *
     * Use static method [HillshadeLayer.defaultVisibility] to get the default property value.
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
   * Use static method [HillshadeLayer.defaultVisibility] to get the default property value.
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
     * Use static method [HillshadeLayer.defaultMinZoom] to get the default property value.
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
   * Use static method [HillshadeLayer.defaultMinZoom] to get the default property value.
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
     * Use static method [HillshadeLayer.defaultMaxZoom] to get the default property value.
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
   * Use static method [HillshadeLayer.defaultMaxZoom] to get the default property value.
   *
   * @param value value of maxzoom
   */
  override fun maxZoom(maxZoom: Double) = apply {
    val param = PropertyValue("maxzoom", maxZoom)
    setProperty(param)
  }

  // Property getters and setters

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
   */
  val hillshadeAccentColor: String?
    /**
     * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
     *
     * Use static method [HillshadeLayer.defaultHillshadeAccentColor] to get the default property.
     *
     * @return String
     */
    get() {
      hillshadeAccentColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
   *
   * Use static method [HillshadeLayer.defaultHillshadeAccentColor] to set the default property.
   *
   * @param hillshadeAccentColor value of hillshadeAccentColor
   */
  override fun hillshadeAccentColor(hillshadeAccentColor: String) = apply {
    val propertyValue = PropertyValue("hillshade-accent-color", hillshadeAccentColor)
    setProperty(propertyValue)
  }

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
   *
   * This is an Expression representation of "hillshade-accent-color".
   *
   */
  val hillshadeAccentColorAsExpression: Expression?
    /**
     * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
     *
     * Get the HillshadeAccentColor property as an Expression
     *
     * Use static method [HillshadeLayer.defaultHillshadeAccentColorAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("hillshade-accent-color")?.let {
        return it
      }
      return null
    }

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
   *
   * Use static method [HillshadeLayer.defaultHillshadeAccentColorAsExpression] to set the default property.
   *
   * @param hillshadeAccentColor value of hillshadeAccentColor as Expression
   */
  override fun hillshadeAccentColor(hillshadeAccentColor: Expression) = apply {
    val propertyValue = PropertyValue("hillshade-accent-color", hillshadeAccentColor)
    setProperty(propertyValue)
  }

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
   */
  val hillshadeAccentColorAsColorInt: Int?
    /**
     * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
     *
     * Use static method [HillshadeLayer.defaultHillshadeAccentColorAsColorInt] to get the default property.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      hillshadeAccentColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
   *
   * Use static method [HillshadeLayer.defaultHillshadeAccentColorAsColorInt] to set the default property.
   *
   * @param hillshadeAccentColor value of hillshadeAccentColor
   */
  override fun hillshadeAccentColor(@ColorInt hillshadeAccentColor: Int) = apply {
    val propertyValue = PropertyValue("hillshade-accent-color", colorIntToRgbaExpression(hillshadeAccentColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for HillshadeAccentColor.
   */
  val hillshadeAccentColorTransition: StyleTransition?
    /**
     * Get the HillshadeAccentColor property transition options
     *
     * Use static method [HillshadeLayer.defaultHillshadeAccentColorTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("hillshade-accent-color-transition")
    }

  /**
   * Set the HillshadeAccentColor property transition options
   *
   * Use static method [HillshadeLayer.defaultHillshadeAccentColorTransition] to set the default property.
   *
   * @param options transition options for String
   */
  override fun hillshadeAccentColorTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("hillshade-accent-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [hillshadeAccentColorTransition].
   */
  override fun hillshadeAccentColorTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    hillshadeAccentColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Intensity of the hillshade
   */
  val hillshadeExaggeration: Double?
    /**
     * Intensity of the hillshade
     *
     * Use static method [HillshadeLayer.defaultHillshadeExaggeration] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("hillshade-exaggeration")
    }

  /**
   * Intensity of the hillshade
   *
   * Use static method [HillshadeLayer.defaultHillshadeExaggeration] to set the default property.
   *
   * @param hillshadeExaggeration value of hillshadeExaggeration
   */
  override fun hillshadeExaggeration(hillshadeExaggeration: Double) = apply {
    val propertyValue = PropertyValue("hillshade-exaggeration", hillshadeExaggeration)
    setProperty(propertyValue)
  }

  /**
   * Intensity of the hillshade
   *
   * This is an Expression representation of "hillshade-exaggeration".
   *
   */
  val hillshadeExaggerationAsExpression: Expression?
    /**
     * Intensity of the hillshade
     *
     * Get the HillshadeExaggeration property as an Expression
     *
     * Use static method [HillshadeLayer.defaultHillshadeExaggerationAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("hillshade-exaggeration")?.let {
        return it
      }
      hillshadeExaggeration?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Intensity of the hillshade
   *
   * Use static method [HillshadeLayer.defaultHillshadeExaggerationAsExpression] to set the default property.
   *
   * @param hillshadeExaggeration value of hillshadeExaggeration as Expression
   */
  override fun hillshadeExaggeration(hillshadeExaggeration: Expression) = apply {
    val propertyValue = PropertyValue("hillshade-exaggeration", hillshadeExaggeration)
    setProperty(propertyValue)
  }

  /**
   * Transition options for HillshadeExaggeration.
   */
  val hillshadeExaggerationTransition: StyleTransition?
    /**
     * Get the HillshadeExaggeration property transition options
     *
     * Use static method [HillshadeLayer.defaultHillshadeExaggerationTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("hillshade-exaggeration-transition")
    }

  /**
   * Set the HillshadeExaggeration property transition options
   *
   * Use static method [HillshadeLayer.defaultHillshadeExaggerationTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun hillshadeExaggerationTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("hillshade-exaggeration-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [hillshadeExaggerationTransition].
   */
  override fun hillshadeExaggerationTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    hillshadeExaggerationTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The shading color of areas that faces towards the light source.
   */
  val hillshadeHighlightColor: String?
    /**
     * The shading color of areas that faces towards the light source.
     *
     * Use static method [HillshadeLayer.defaultHillshadeHighlightColor] to get the default property.
     *
     * @return String
     */
    get() {
      hillshadeHighlightColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The shading color of areas that faces towards the light source.
   *
   * Use static method [HillshadeLayer.defaultHillshadeHighlightColor] to set the default property.
   *
   * @param hillshadeHighlightColor value of hillshadeHighlightColor
   */
  override fun hillshadeHighlightColor(hillshadeHighlightColor: String) = apply {
    val propertyValue = PropertyValue("hillshade-highlight-color", hillshadeHighlightColor)
    setProperty(propertyValue)
  }

  /**
   * The shading color of areas that faces towards the light source.
   *
   * This is an Expression representation of "hillshade-highlight-color".
   *
   */
  val hillshadeHighlightColorAsExpression: Expression?
    /**
     * The shading color of areas that faces towards the light source.
     *
     * Get the HillshadeHighlightColor property as an Expression
     *
     * Use static method [HillshadeLayer.defaultHillshadeHighlightColorAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("hillshade-highlight-color")?.let {
        return it
      }
      return null
    }

  /**
   * The shading color of areas that faces towards the light source.
   *
   * Use static method [HillshadeLayer.defaultHillshadeHighlightColorAsExpression] to set the default property.
   *
   * @param hillshadeHighlightColor value of hillshadeHighlightColor as Expression
   */
  override fun hillshadeHighlightColor(hillshadeHighlightColor: Expression) = apply {
    val propertyValue = PropertyValue("hillshade-highlight-color", hillshadeHighlightColor)
    setProperty(propertyValue)
  }

  /**
   * The shading color of areas that faces towards the light source.
   */
  val hillshadeHighlightColorAsColorInt: Int?
    /**
     * The shading color of areas that faces towards the light source.
     *
     * Use static method [HillshadeLayer.defaultHillshadeHighlightColorAsColorInt] to get the default property.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      hillshadeHighlightColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The shading color of areas that faces towards the light source.
   *
   * Use static method [HillshadeLayer.defaultHillshadeHighlightColorAsColorInt] to set the default property.
   *
   * @param hillshadeHighlightColor value of hillshadeHighlightColor
   */
  override fun hillshadeHighlightColor(@ColorInt hillshadeHighlightColor: Int) = apply {
    val propertyValue = PropertyValue("hillshade-highlight-color", colorIntToRgbaExpression(hillshadeHighlightColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for HillshadeHighlightColor.
   */
  val hillshadeHighlightColorTransition: StyleTransition?
    /**
     * Get the HillshadeHighlightColor property transition options
     *
     * Use static method [HillshadeLayer.defaultHillshadeHighlightColorTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("hillshade-highlight-color-transition")
    }

  /**
   * Set the HillshadeHighlightColor property transition options
   *
   * Use static method [HillshadeLayer.defaultHillshadeHighlightColorTransition] to set the default property.
   *
   * @param options transition options for String
   */
  override fun hillshadeHighlightColorTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("hillshade-highlight-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [hillshadeHighlightColorTransition].
   */
  override fun hillshadeHighlightColorTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    hillshadeHighlightColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Direction of light source when map is rotated.
   */
  val hillshadeIlluminationAnchor: HillshadeIlluminationAnchor?
    /**
     * Direction of light source when map is rotated.
     *
     * Use static method [HillshadeLayer.defaultHillshadeIlluminationAnchor] to get the default property.
     *
     * @return HillshadeIlluminationAnchor
     */
    get() {
      getPropertyValue<String?>("hillshade-illumination-anchor")?.let {
        return HillshadeIlluminationAnchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Direction of light source when map is rotated.
   *
   * Use static method [HillshadeLayer.defaultHillshadeIlluminationAnchor] to set the default property.
   *
   * @param hillshadeIlluminationAnchor value of hillshadeIlluminationAnchor
   */
  override fun hillshadeIlluminationAnchor(hillshadeIlluminationAnchor: HillshadeIlluminationAnchor) = apply {
    val propertyValue = PropertyValue("hillshade-illumination-anchor", hillshadeIlluminationAnchor)
    setProperty(propertyValue)
  }

  /**
   * Direction of light source when map is rotated.
   *
   * This is an Expression representation of "hillshade-illumination-anchor".
   *
   */
  val hillshadeIlluminationAnchorAsExpression: Expression?
    /**
     * Direction of light source when map is rotated.
     *
     * Get the HillshadeIlluminationAnchor property as an Expression
     *
     * Use static method [HillshadeLayer.defaultHillshadeIlluminationAnchorAsExpression] to get the default property.
     *
     * @return HillshadeIlluminationAnchor
     */
    get() {
      getPropertyValue<Expression>("hillshade-illumination-anchor")?.let {
        return it
      }
      hillshadeIlluminationAnchor?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Direction of light source when map is rotated.
   *
   * Use static method [HillshadeLayer.defaultHillshadeIlluminationAnchorAsExpression] to set the default property.
   *
   * @param hillshadeIlluminationAnchor value of hillshadeIlluminationAnchor as Expression
   */
  override fun hillshadeIlluminationAnchor(hillshadeIlluminationAnchor: Expression) = apply {
    val propertyValue = PropertyValue("hillshade-illumination-anchor", hillshadeIlluminationAnchor)
    setProperty(propertyValue)
  }

  /**
   * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map`.
   */
  val hillshadeIlluminationDirection: Double?
    /**
     * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map`.
     *
     * Use static method [HillshadeLayer.defaultHillshadeIlluminationDirection] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("hillshade-illumination-direction")
    }

  /**
   * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map`.
   *
   * Use static method [HillshadeLayer.defaultHillshadeIlluminationDirection] to set the default property.
   *
   * @param hillshadeIlluminationDirection value of hillshadeIlluminationDirection
   */
  override fun hillshadeIlluminationDirection(hillshadeIlluminationDirection: Double) = apply {
    val propertyValue = PropertyValue("hillshade-illumination-direction", hillshadeIlluminationDirection)
    setProperty(propertyValue)
  }

  /**
   * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map`.
   *
   * This is an Expression representation of "hillshade-illumination-direction".
   *
   */
  val hillshadeIlluminationDirectionAsExpression: Expression?
    /**
     * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map`.
     *
     * Get the HillshadeIlluminationDirection property as an Expression
     *
     * Use static method [HillshadeLayer.defaultHillshadeIlluminationDirectionAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("hillshade-illumination-direction")?.let {
        return it
      }
      hillshadeIlluminationDirection?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map`.
   *
   * Use static method [HillshadeLayer.defaultHillshadeIlluminationDirectionAsExpression] to set the default property.
   *
   * @param hillshadeIlluminationDirection value of hillshadeIlluminationDirection as Expression
   */
  override fun hillshadeIlluminationDirection(hillshadeIlluminationDirection: Expression) = apply {
    val propertyValue = PropertyValue("hillshade-illumination-direction", hillshadeIlluminationDirection)
    setProperty(propertyValue)
  }

  /**
   * The shading color of areas that face away from the light source.
   */
  val hillshadeShadowColor: String?
    /**
     * The shading color of areas that face away from the light source.
     *
     * Use static method [HillshadeLayer.defaultHillshadeShadowColor] to get the default property.
     *
     * @return String
     */
    get() {
      hillshadeShadowColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The shading color of areas that face away from the light source.
   *
   * Use static method [HillshadeLayer.defaultHillshadeShadowColor] to set the default property.
   *
   * @param hillshadeShadowColor value of hillshadeShadowColor
   */
  override fun hillshadeShadowColor(hillshadeShadowColor: String) = apply {
    val propertyValue = PropertyValue("hillshade-shadow-color", hillshadeShadowColor)
    setProperty(propertyValue)
  }

  /**
   * The shading color of areas that face away from the light source.
   *
   * This is an Expression representation of "hillshade-shadow-color".
   *
   */
  val hillshadeShadowColorAsExpression: Expression?
    /**
     * The shading color of areas that face away from the light source.
     *
     * Get the HillshadeShadowColor property as an Expression
     *
     * Use static method [HillshadeLayer.defaultHillshadeShadowColorAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("hillshade-shadow-color")?.let {
        return it
      }
      return null
    }

  /**
   * The shading color of areas that face away from the light source.
   *
   * Use static method [HillshadeLayer.defaultHillshadeShadowColorAsExpression] to set the default property.
   *
   * @param hillshadeShadowColor value of hillshadeShadowColor as Expression
   */
  override fun hillshadeShadowColor(hillshadeShadowColor: Expression) = apply {
    val propertyValue = PropertyValue("hillshade-shadow-color", hillshadeShadowColor)
    setProperty(propertyValue)
  }

  /**
   * The shading color of areas that face away from the light source.
   */
  val hillshadeShadowColorAsColorInt: Int?
    /**
     * The shading color of areas that face away from the light source.
     *
     * Use static method [HillshadeLayer.defaultHillshadeShadowColorAsColorInt] to get the default property.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      hillshadeShadowColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The shading color of areas that face away from the light source.
   *
   * Use static method [HillshadeLayer.defaultHillshadeShadowColorAsColorInt] to set the default property.
   *
   * @param hillshadeShadowColor value of hillshadeShadowColor
   */
  override fun hillshadeShadowColor(@ColorInt hillshadeShadowColor: Int) = apply {
    val propertyValue = PropertyValue("hillshade-shadow-color", colorIntToRgbaExpression(hillshadeShadowColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for HillshadeShadowColor.
   */
  val hillshadeShadowColorTransition: StyleTransition?
    /**
     * Get the HillshadeShadowColor property transition options
     *
     * Use static method [HillshadeLayer.defaultHillshadeShadowColorTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("hillshade-shadow-color-transition")
    }

  /**
   * Set the HillshadeShadowColor property transition options
   *
   * Use static method [HillshadeLayer.defaultHillshadeShadowColorTransition] to set the default property.
   *
   * @param options transition options for String
   */
  override fun hillshadeShadowColorTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("hillshade-shadow-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [hillshadeShadowColorTransition].
   */
  override fun hillshadeShadowColorTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    hillshadeShadowColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Get the type of this layer
   *
   * @return Type of the layer as [String]
   */
  override fun getType(): String {
    return "hillshade"
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
        StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "visibility").silentUnwrap<String>()?.let {
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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "minzoom").silentUnwrap()

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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "maxzoom").silentUnwrap()

    /**
     * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
     */
    val defaultHillshadeAccentColor: String?
      /**
       * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
       *
       * Get the default value of HillshadeAccentColor property
       *
       * @return String
       */
      get() {
        defaultHillshadeAccentColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
     *
     * This is an Expression representation of "hillshade-accent-color".
     *
     */
    val defaultHillshadeAccentColorAsExpression: Expression?
      /**
       * Get default value of the HillshadeAccentColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-accent-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
     */
    val defaultHillshadeAccentColorAsColorInt: Int?
      /**
       * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
       *
       * Get the default value of HillshadeAccentColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultHillshadeAccentColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for HillshadeAccentColor.
     */
    val defaultHillshadeAccentColorTransition: StyleTransition?
      /**
       * Get the HillshadeAccentColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-accent-color-transition").silentUnwrap()

    /**
     * Intensity of the hillshade
     */
    val defaultHillshadeExaggeration: Double?
      /**
       * Intensity of the hillshade
       *
       * Get the default value of HillshadeExaggeration property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-exaggeration").silentUnwrap()
      }

    /**
     * Intensity of the hillshade
     *
     * This is an Expression representation of "hillshade-exaggeration".
     *
     */
    val defaultHillshadeExaggerationAsExpression: Expression?
      /**
       * Get default value of the HillshadeExaggeration property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-exaggeration").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultHillshadeExaggeration?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for HillshadeExaggeration.
     */
    val defaultHillshadeExaggerationTransition: StyleTransition?
      /**
       * Get the HillshadeExaggeration property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-exaggeration-transition").silentUnwrap()

    /**
     * The shading color of areas that faces towards the light source.
     */
    val defaultHillshadeHighlightColor: String?
      /**
       * The shading color of areas that faces towards the light source.
       *
       * Get the default value of HillshadeHighlightColor property
       *
       * @return String
       */
      get() {
        defaultHillshadeHighlightColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The shading color of areas that faces towards the light source.
     *
     * This is an Expression representation of "hillshade-highlight-color".
     *
     */
    val defaultHillshadeHighlightColorAsExpression: Expression?
      /**
       * Get default value of the HillshadeHighlightColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-highlight-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The shading color of areas that faces towards the light source.
     */
    val defaultHillshadeHighlightColorAsColorInt: Int?
      /**
       * The shading color of areas that faces towards the light source.
       *
       * Get the default value of HillshadeHighlightColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultHillshadeHighlightColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for HillshadeHighlightColor.
     */
    val defaultHillshadeHighlightColorTransition: StyleTransition?
      /**
       * Get the HillshadeHighlightColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-highlight-color-transition").silentUnwrap()

    /**
     * Direction of light source when map is rotated.
     */
    val defaultHillshadeIlluminationAnchor: HillshadeIlluminationAnchor?
      /**
       * Direction of light source when map is rotated.
       *
       * Get the default value of HillshadeIlluminationAnchor property
       *
       * @return HillshadeIlluminationAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-illumination-anchor").silentUnwrap<String>()?.let {
          return HillshadeIlluminationAnchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Direction of light source when map is rotated.
     *
     * This is an Expression representation of "hillshade-illumination-anchor".
     *
     */
    val defaultHillshadeIlluminationAnchorAsExpression: Expression?
      /**
       * Get default value of the HillshadeIlluminationAnchor property as an Expression
       *
       * @return HillshadeIlluminationAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-illumination-anchor").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultHillshadeIlluminationAnchor?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map`.
     */
    val defaultHillshadeIlluminationDirection: Double?
      /**
       * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map`.
       *
       * Get the default value of HillshadeIlluminationDirection property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-illumination-direction").silentUnwrap()
      }

    /**
     * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map`.
     *
     * This is an Expression representation of "hillshade-illumination-direction".
     *
     */
    val defaultHillshadeIlluminationDirectionAsExpression: Expression?
      /**
       * Get default value of the HillshadeIlluminationDirection property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-illumination-direction").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultHillshadeIlluminationDirection?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * The shading color of areas that face away from the light source.
     */
    val defaultHillshadeShadowColor: String?
      /**
       * The shading color of areas that face away from the light source.
       *
       * Get the default value of HillshadeShadowColor property
       *
       * @return String
       */
      get() {
        defaultHillshadeShadowColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The shading color of areas that face away from the light source.
     *
     * This is an Expression representation of "hillshade-shadow-color".
     *
     */
    val defaultHillshadeShadowColorAsExpression: Expression?
      /**
       * Get default value of the HillshadeShadowColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-shadow-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The shading color of areas that face away from the light source.
     */
    val defaultHillshadeShadowColorAsColorInt: Int?
      /**
       * The shading color of areas that face away from the light source.
       *
       * Get the default value of HillshadeShadowColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultHillshadeShadowColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for HillshadeShadowColor.
     */
    val defaultHillshadeShadowColorTransition: StyleTransition?
      /**
       * Get the HillshadeShadowColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-shadow-color-transition").silentUnwrap()
  }
}

/**
 * This Interface contains all the functions that will be exposed to Koltin DSL.
 *
 * Separated the DSL receiver class to this interface to avoid IDE code suggestion for
 * property getters.
 */
@LayersDsl
interface HillshadeLayerDsl {
  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  fun sourceLayer(sourceLayer: String): HillshadeLayer

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): HillshadeLayer

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param value value of minzoom
   */
  fun minZoom(minZoom: Double): HillshadeLayer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param value value of maxzoom
   */
  fun maxZoom(maxZoom: Double): HillshadeLayer

  // Property getters and setters

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
   *
   * @param hillshadeAccentColor value of hillshadeAccentColor
   */
  fun hillshadeAccentColor(hillshadeAccentColor: String = "#000000"): HillshadeLayer

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
   *
   * @param hillshadeAccentColor value of hillshadeAccentColor as Expression
   */
  fun hillshadeAccentColor(hillshadeAccentColor: Expression): HillshadeLayer

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
   *
   * @param hillshadeAccentColor value of hillshadeAccentColor
   */
  fun hillshadeAccentColor(@ColorInt hillshadeAccentColor: Int): HillshadeLayer

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
   *
   * Set the HillshadeAccentColor property transition options
   *
   * @param options transition options for String
   */
  fun hillshadeAccentColorTransition(options: StyleTransition): HillshadeLayer

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
   *
   * DSL for [hillshadeAccentColorTransition].
   */
  fun hillshadeAccentColorTransition(block: StyleTransition.Builder.() -> Unit): HillshadeLayer

  /**
   * Intensity of the hillshade
   *
   * @param hillshadeExaggeration value of hillshadeExaggeration
   */
  fun hillshadeExaggeration(hillshadeExaggeration: Double = 0.5): HillshadeLayer

  /**
   * Intensity of the hillshade
   *
   * @param hillshadeExaggeration value of hillshadeExaggeration as Expression
   */
  fun hillshadeExaggeration(hillshadeExaggeration: Expression): HillshadeLayer

  /**
   * Intensity of the hillshade
   *
   * Set the HillshadeExaggeration property transition options
   *
   * @param options transition options for Double
   */
  fun hillshadeExaggerationTransition(options: StyleTransition): HillshadeLayer

  /**
   * Intensity of the hillshade
   *
   * DSL for [hillshadeExaggerationTransition].
   */
  fun hillshadeExaggerationTransition(block: StyleTransition.Builder.() -> Unit): HillshadeLayer

  /**
   * The shading color of areas that faces towards the light source.
   *
   * @param hillshadeHighlightColor value of hillshadeHighlightColor
   */
  fun hillshadeHighlightColor(hillshadeHighlightColor: String = "#FFFFFF"): HillshadeLayer

  /**
   * The shading color of areas that faces towards the light source.
   *
   * @param hillshadeHighlightColor value of hillshadeHighlightColor as Expression
   */
  fun hillshadeHighlightColor(hillshadeHighlightColor: Expression): HillshadeLayer

  /**
   * The shading color of areas that faces towards the light source.
   *
   * @param hillshadeHighlightColor value of hillshadeHighlightColor
   */
  fun hillshadeHighlightColor(@ColorInt hillshadeHighlightColor: Int): HillshadeLayer

  /**
   * The shading color of areas that faces towards the light source.
   *
   * Set the HillshadeHighlightColor property transition options
   *
   * @param options transition options for String
   */
  fun hillshadeHighlightColorTransition(options: StyleTransition): HillshadeLayer

  /**
   * The shading color of areas that faces towards the light source.
   *
   * DSL for [hillshadeHighlightColorTransition].
   */
  fun hillshadeHighlightColorTransition(block: StyleTransition.Builder.() -> Unit): HillshadeLayer

  /**
   * Direction of light source when map is rotated.
   *
   * @param hillshadeIlluminationAnchor value of hillshadeIlluminationAnchor
   */
  fun hillshadeIlluminationAnchor(hillshadeIlluminationAnchor: HillshadeIlluminationAnchor = HillshadeIlluminationAnchor.VIEWPORT): HillshadeLayer

  /**
   * Direction of light source when map is rotated.
   *
   * @param hillshadeIlluminationAnchor value of hillshadeIlluminationAnchor as Expression
   */
  fun hillshadeIlluminationAnchor(hillshadeIlluminationAnchor: Expression): HillshadeLayer

  /**
   * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map`.
   *
   * @param hillshadeIlluminationDirection value of hillshadeIlluminationDirection
   */
  fun hillshadeIlluminationDirection(hillshadeIlluminationDirection: Double = 335.0): HillshadeLayer

  /**
   * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map`.
   *
   * @param hillshadeIlluminationDirection value of hillshadeIlluminationDirection as Expression
   */
  fun hillshadeIlluminationDirection(hillshadeIlluminationDirection: Expression): HillshadeLayer

  /**
   * The shading color of areas that face away from the light source.
   *
   * @param hillshadeShadowColor value of hillshadeShadowColor
   */
  fun hillshadeShadowColor(hillshadeShadowColor: String = "#000000"): HillshadeLayer

  /**
   * The shading color of areas that face away from the light source.
   *
   * @param hillshadeShadowColor value of hillshadeShadowColor as Expression
   */
  fun hillshadeShadowColor(hillshadeShadowColor: Expression): HillshadeLayer

  /**
   * The shading color of areas that face away from the light source.
   *
   * @param hillshadeShadowColor value of hillshadeShadowColor
   */
  fun hillshadeShadowColor(@ColorInt hillshadeShadowColor: Int): HillshadeLayer

  /**
   * The shading color of areas that face away from the light source.
   *
   * Set the HillshadeShadowColor property transition options
   *
   * @param options transition options for String
   */
  fun hillshadeShadowColorTransition(options: StyleTransition): HillshadeLayer

  /**
   * The shading color of areas that face away from the light source.
   *
   * DSL for [hillshadeShadowColorTransition].
   */
  fun hillshadeShadowColorTransition(block: StyleTransition.Builder.() -> Unit): HillshadeLayer
}

/**
 * DSL function for creating a [HillshadeLayer].
 */
fun hillshadeLayer(layerId: String, sourceId: String, block: HillshadeLayerDsl.() -> Unit): HillshadeLayer = HillshadeLayer(layerId, sourceId).apply(block)

// End of generated file.