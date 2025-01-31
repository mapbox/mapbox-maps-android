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
 * Client-side hillshading visualization based on DEM data. Currently, the implementation only supports Mapbox Terrain RGB and Mapzen Terrarium tiles.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers/#hillshade)
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
  override fun sourceLayer(sourceLayer: String): HillshadeLayer = apply {
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
  override fun slot(slot: String): HillshadeLayer = apply {
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
     * Use static method [HillshadeLayer.defaultVisibility] to get the default property value.
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
   * Use static method [HillshadeLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Visibility): HillshadeLayer = apply {
    val propertyValue = PropertyValue("visibility", visibility)
    setProperty(propertyValue)
  }

  /**
   * Whether this layer is displayed.
   *
   * Use static method [[HillshadeLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Expression): HillshadeLayer = apply {
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
   * @param minZoom value of minzoom
   */
  override fun minZoom(minZoom: Double): HillshadeLayer = apply {
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
   * @param maxZoom value of maxzoom
   */
  override fun maxZoom(maxZoom: Double): HillshadeLayer = apply {
    val param = PropertyValue("maxzoom", maxZoom)
    setProperty(param)
  }

  // Property getters and setters

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
   */
  val hillshadeAccentColor: String?
    /**
     * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
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
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
   *
   * Use static method [HillshadeLayer.defaultHillshadeAccentColor] to set the default property.
   *
   * @param hillshadeAccentColor value of hillshadeAccentColor
   */
  override fun hillshadeAccentColor(hillshadeAccentColor: String): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-accent-color", hillshadeAccentColor)
    setProperty(propertyValue)
  }

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
   *
   * This is an Expression representation of "hillshade-accent-color".
   *
   */
  val hillshadeAccentColorAsExpression: Expression?
    /**
     * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
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
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
   *
   * Use static method [HillshadeLayer.defaultHillshadeAccentColorAsExpression] to set the default property.
   *
   * @param hillshadeAccentColor value of hillshadeAccentColor as Expression
   */
  override fun hillshadeAccentColor(hillshadeAccentColor: Expression): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-accent-color", hillshadeAccentColor)
    setProperty(propertyValue)
  }

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
   */
  val hillshadeAccentColorAsColorInt: Int?
    /**
     * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
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
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
   *
   * Use static method [HillshadeLayer.defaultHillshadeAccentColorAsColorInt] to set the default property.
   *
   * @param hillshadeAccentColor value of hillshadeAccentColor
   */
  override fun hillshadeAccentColor(@ColorInt hillshadeAccentColor: Int): HillshadeLayer = apply {
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
  override fun hillshadeAccentColorTransition(options: StyleTransition): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-accent-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [hillshadeAccentColorTransition].
   */
  override fun hillshadeAccentColorTransition(block: StyleTransition.Builder.() -> Unit): HillshadeLayer = apply {
    hillshadeAccentColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Сolor theme override for [hillshadeAccentColor].
   */
  @MapboxExperimental
  val hillshadeAccentColorUseTheme: String?
    /**
     * Get the HillshadeAccentColorUseTheme property
     *
     * Use static method [HillshadeLayer.defaultHillshadeAccentColorUseTheme] to get the default property.
     *
     * @return current HillshadeAccentColorUseTheme property as String
     */
    get() {
      return getPropertyValue("hillshade-accent-color-use-theme")
    }

  /**
   * Set the HillshadeAccentColorUseTheme as String
   *
   * Use static method [HillshadeLayer.defaultHillshadeAccentColorUseTheme] to get the default property.
   *
   * @param hillshadeAccentColorUseTheme theme value for color. Overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  override fun hillshadeAccentColorUseTheme(hillshadeAccentColorUseTheme: String): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-accent-color-use-theme", hillshadeAccentColorUseTheme)
    setProperty(propertyValue)
  }

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of hillshadeEmissiveStrength is in intensity.
   */
  val hillshadeEmissiveStrength: Double?
    /**
     * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of hillshadeEmissiveStrength is in intensity.
     *
     * Use static method [HillshadeLayer.defaultHillshadeEmissiveStrength] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("hillshade-emissive-strength")
    }

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of hillshadeEmissiveStrength is in intensity.
   *
   * Use static method [HillshadeLayer.defaultHillshadeEmissiveStrength] to set the default property.
   *
   * @param hillshadeEmissiveStrength value of hillshadeEmissiveStrength
   */
  override fun hillshadeEmissiveStrength(hillshadeEmissiveStrength: Double): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-emissive-strength", hillshadeEmissiveStrength)
    setProperty(propertyValue)
  }

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of hillshadeEmissiveStrength is in intensity.
   *
   * This is an Expression representation of "hillshade-emissive-strength".
   *
   */
  val hillshadeEmissiveStrengthAsExpression: Expression?
    /**
     * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of hillshadeEmissiveStrength is in intensity.
     *
     * Get the HillshadeEmissiveStrength property as an Expression
     *
     * Use static method [HillshadeLayer.defaultHillshadeEmissiveStrengthAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("hillshade-emissive-strength")?.let {
        return it
      }
      hillshadeEmissiveStrength?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of hillshadeEmissiveStrength is in intensity.
   *
   * Use static method [HillshadeLayer.defaultHillshadeEmissiveStrengthAsExpression] to set the default property.
   *
   * @param hillshadeEmissiveStrength value of hillshadeEmissiveStrength as Expression
   */
  override fun hillshadeEmissiveStrength(hillshadeEmissiveStrength: Expression): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-emissive-strength", hillshadeEmissiveStrength)
    setProperty(propertyValue)
  }

  /**
   * Transition options for HillshadeEmissiveStrength.
   */
  val hillshadeEmissiveStrengthTransition: StyleTransition?
    /**
     * Get the HillshadeEmissiveStrength property transition options
     *
     * Use static method [HillshadeLayer.defaultHillshadeEmissiveStrengthTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("hillshade-emissive-strength-transition")
    }

  /**
   * Set the HillshadeEmissiveStrength property transition options
   *
   * Use static method [HillshadeLayer.defaultHillshadeEmissiveStrengthTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun hillshadeEmissiveStrengthTransition(options: StyleTransition): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-emissive-strength-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [hillshadeEmissiveStrengthTransition].
   */
  override fun hillshadeEmissiveStrengthTransition(block: StyleTransition.Builder.() -> Unit): HillshadeLayer = apply {
    hillshadeEmissiveStrengthTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Intensity of the hillshade Default value: 0.5. Value range: [0, 1]
   */
  val hillshadeExaggeration: Double?
    /**
     * Intensity of the hillshade Default value: 0.5. Value range: [0, 1]
     *
     * Use static method [HillshadeLayer.defaultHillshadeExaggeration] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("hillshade-exaggeration")
    }

  /**
   * Intensity of the hillshade Default value: 0.5. Value range: [0, 1]
   *
   * Use static method [HillshadeLayer.defaultHillshadeExaggeration] to set the default property.
   *
   * @param hillshadeExaggeration value of hillshadeExaggeration
   */
  override fun hillshadeExaggeration(hillshadeExaggeration: Double): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-exaggeration", hillshadeExaggeration)
    setProperty(propertyValue)
  }

  /**
   * Intensity of the hillshade Default value: 0.5. Value range: [0, 1]
   *
   * This is an Expression representation of "hillshade-exaggeration".
   *
   */
  val hillshadeExaggerationAsExpression: Expression?
    /**
     * Intensity of the hillshade Default value: 0.5. Value range: [0, 1]
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
   * Intensity of the hillshade Default value: 0.5. Value range: [0, 1]
   *
   * Use static method [HillshadeLayer.defaultHillshadeExaggerationAsExpression] to set the default property.
   *
   * @param hillshadeExaggeration value of hillshadeExaggeration as Expression
   */
  override fun hillshadeExaggeration(hillshadeExaggeration: Expression): HillshadeLayer = apply {
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
  override fun hillshadeExaggerationTransition(options: StyleTransition): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-exaggeration-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [hillshadeExaggerationTransition].
   */
  override fun hillshadeExaggerationTransition(block: StyleTransition.Builder.() -> Unit): HillshadeLayer = apply {
    hillshadeExaggerationTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
   */
  val hillshadeHighlightColor: String?
    /**
     * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
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
   * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
   *
   * Use static method [HillshadeLayer.defaultHillshadeHighlightColor] to set the default property.
   *
   * @param hillshadeHighlightColor value of hillshadeHighlightColor
   */
  override fun hillshadeHighlightColor(hillshadeHighlightColor: String): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-highlight-color", hillshadeHighlightColor)
    setProperty(propertyValue)
  }

  /**
   * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
   *
   * This is an Expression representation of "hillshade-highlight-color".
   *
   */
  val hillshadeHighlightColorAsExpression: Expression?
    /**
     * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
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
   * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
   *
   * Use static method [HillshadeLayer.defaultHillshadeHighlightColorAsExpression] to set the default property.
   *
   * @param hillshadeHighlightColor value of hillshadeHighlightColor as Expression
   */
  override fun hillshadeHighlightColor(hillshadeHighlightColor: Expression): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-highlight-color", hillshadeHighlightColor)
    setProperty(propertyValue)
  }

  /**
   * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
   */
  val hillshadeHighlightColorAsColorInt: Int?
    /**
     * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
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
   * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
   *
   * Use static method [HillshadeLayer.defaultHillshadeHighlightColorAsColorInt] to set the default property.
   *
   * @param hillshadeHighlightColor value of hillshadeHighlightColor
   */
  override fun hillshadeHighlightColor(@ColorInt hillshadeHighlightColor: Int): HillshadeLayer = apply {
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
  override fun hillshadeHighlightColorTransition(options: StyleTransition): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-highlight-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [hillshadeHighlightColorTransition].
   */
  override fun hillshadeHighlightColorTransition(block: StyleTransition.Builder.() -> Unit): HillshadeLayer = apply {
    hillshadeHighlightColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Сolor theme override for [hillshadeHighlightColor].
   */
  @MapboxExperimental
  val hillshadeHighlightColorUseTheme: String?
    /**
     * Get the HillshadeHighlightColorUseTheme property
     *
     * Use static method [HillshadeLayer.defaultHillshadeHighlightColorUseTheme] to get the default property.
     *
     * @return current HillshadeHighlightColorUseTheme property as String
     */
    get() {
      return getPropertyValue("hillshade-highlight-color-use-theme")
    }

  /**
   * Set the HillshadeHighlightColorUseTheme as String
   *
   * Use static method [HillshadeLayer.defaultHillshadeHighlightColorUseTheme] to get the default property.
   *
   * @param hillshadeHighlightColorUseTheme theme value for color. Overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  override fun hillshadeHighlightColorUseTheme(hillshadeHighlightColorUseTheme: String): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-highlight-color-use-theme", hillshadeHighlightColorUseTheme)
    setProperty(propertyValue)
  }

  /**
   * Direction of light source when map is rotated. Default value: "viewport".
   */
  val hillshadeIlluminationAnchor: HillshadeIlluminationAnchor?
    /**
     * Direction of light source when map is rotated. Default value: "viewport".
     *
     * Use static method [HillshadeLayer.defaultHillshadeIlluminationAnchor] to get the default property.
     *
     * @return HillshadeIlluminationAnchor
     */
    get() {
      getPropertyValue<String?>("hillshade-illumination-anchor")?.let {
        return HillshadeIlluminationAnchor.valueOf(it.uppercase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Direction of light source when map is rotated. Default value: "viewport".
   *
   * Use static method [HillshadeLayer.defaultHillshadeIlluminationAnchor] to set the default property.
   *
   * @param hillshadeIlluminationAnchor value of hillshadeIlluminationAnchor
   */
  override fun hillshadeIlluminationAnchor(hillshadeIlluminationAnchor: HillshadeIlluminationAnchor): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-illumination-anchor", hillshadeIlluminationAnchor)
    setProperty(propertyValue)
  }

  /**
   * Direction of light source when map is rotated. Default value: "viewport".
   *
   * This is an Expression representation of "hillshade-illumination-anchor".
   *
   */
  val hillshadeIlluminationAnchorAsExpression: Expression?
    /**
     * Direction of light source when map is rotated. Default value: "viewport".
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
   * Direction of light source when map is rotated. Default value: "viewport".
   *
   * Use static method [HillshadeLayer.defaultHillshadeIlluminationAnchorAsExpression] to set the default property.
   *
   * @param hillshadeIlluminationAnchor value of hillshadeIlluminationAnchor as Expression
   */
  override fun hillshadeIlluminationAnchor(hillshadeIlluminationAnchor: Expression): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-illumination-anchor", hillshadeIlluminationAnchor)
    setProperty(propertyValue)
  }

  /**
   * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map` and no 3d lights enabled. If `hillshade-illumination-anchor` is set to `map` and 3d lights enabled, the direction from 3d lights is used instead. Default value: 335. Value range: [0, 359]
   */
  val hillshadeIlluminationDirection: Double?
    /**
     * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map` and no 3d lights enabled. If `hillshade-illumination-anchor` is set to `map` and 3d lights enabled, the direction from 3d lights is used instead. Default value: 335. Value range: [0, 359]
     *
     * Use static method [HillshadeLayer.defaultHillshadeIlluminationDirection] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("hillshade-illumination-direction")
    }

  /**
   * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map` and no 3d lights enabled. If `hillshade-illumination-anchor` is set to `map` and 3d lights enabled, the direction from 3d lights is used instead. Default value: 335. Value range: [0, 359]
   *
   * Use static method [HillshadeLayer.defaultHillshadeIlluminationDirection] to set the default property.
   *
   * @param hillshadeIlluminationDirection value of hillshadeIlluminationDirection
   */
  override fun hillshadeIlluminationDirection(hillshadeIlluminationDirection: Double): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-illumination-direction", hillshadeIlluminationDirection)
    setProperty(propertyValue)
  }

  /**
   * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map` and no 3d lights enabled. If `hillshade-illumination-anchor` is set to `map` and 3d lights enabled, the direction from 3d lights is used instead. Default value: 335. Value range: [0, 359]
   *
   * This is an Expression representation of "hillshade-illumination-direction".
   *
   */
  val hillshadeIlluminationDirectionAsExpression: Expression?
    /**
     * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map` and no 3d lights enabled. If `hillshade-illumination-anchor` is set to `map` and 3d lights enabled, the direction from 3d lights is used instead. Default value: 335. Value range: [0, 359]
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
   * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map` and no 3d lights enabled. If `hillshade-illumination-anchor` is set to `map` and 3d lights enabled, the direction from 3d lights is used instead. Default value: 335. Value range: [0, 359]
   *
   * Use static method [HillshadeLayer.defaultHillshadeIlluminationDirectionAsExpression] to set the default property.
   *
   * @param hillshadeIlluminationDirection value of hillshadeIlluminationDirection as Expression
   */
  override fun hillshadeIlluminationDirection(hillshadeIlluminationDirection: Expression): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-illumination-direction", hillshadeIlluminationDirection)
    setProperty(propertyValue)
  }

  /**
   * The shading color of areas that face away from the light source. Default value: "#000000".
   */
  val hillshadeShadowColor: String?
    /**
     * The shading color of areas that face away from the light source. Default value: "#000000".
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
   * The shading color of areas that face away from the light source. Default value: "#000000".
   *
   * Use static method [HillshadeLayer.defaultHillshadeShadowColor] to set the default property.
   *
   * @param hillshadeShadowColor value of hillshadeShadowColor
   */
  override fun hillshadeShadowColor(hillshadeShadowColor: String): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-shadow-color", hillshadeShadowColor)
    setProperty(propertyValue)
  }

  /**
   * The shading color of areas that face away from the light source. Default value: "#000000".
   *
   * This is an Expression representation of "hillshade-shadow-color".
   *
   */
  val hillshadeShadowColorAsExpression: Expression?
    /**
     * The shading color of areas that face away from the light source. Default value: "#000000".
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
   * The shading color of areas that face away from the light source. Default value: "#000000".
   *
   * Use static method [HillshadeLayer.defaultHillshadeShadowColorAsExpression] to set the default property.
   *
   * @param hillshadeShadowColor value of hillshadeShadowColor as Expression
   */
  override fun hillshadeShadowColor(hillshadeShadowColor: Expression): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-shadow-color", hillshadeShadowColor)
    setProperty(propertyValue)
  }

  /**
   * The shading color of areas that face away from the light source. Default value: "#000000".
   */
  val hillshadeShadowColorAsColorInt: Int?
    /**
     * The shading color of areas that face away from the light source. Default value: "#000000".
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
   * The shading color of areas that face away from the light source. Default value: "#000000".
   *
   * Use static method [HillshadeLayer.defaultHillshadeShadowColorAsColorInt] to set the default property.
   *
   * @param hillshadeShadowColor value of hillshadeShadowColor
   */
  override fun hillshadeShadowColor(@ColorInt hillshadeShadowColor: Int): HillshadeLayer = apply {
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
  override fun hillshadeShadowColorTransition(options: StyleTransition): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-shadow-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [hillshadeShadowColorTransition].
   */
  override fun hillshadeShadowColorTransition(block: StyleTransition.Builder.() -> Unit): HillshadeLayer = apply {
    hillshadeShadowColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Сolor theme override for [hillshadeShadowColor].
   */
  @MapboxExperimental
  val hillshadeShadowColorUseTheme: String?
    /**
     * Get the HillshadeShadowColorUseTheme property
     *
     * Use static method [HillshadeLayer.defaultHillshadeShadowColorUseTheme] to get the default property.
     *
     * @return current HillshadeShadowColorUseTheme property as String
     */
    get() {
      return getPropertyValue("hillshade-shadow-color-use-theme")
    }

  /**
   * Set the HillshadeShadowColorUseTheme as String
   *
   * Use static method [HillshadeLayer.defaultHillshadeShadowColorUseTheme] to get the default property.
   *
   * @param hillshadeShadowColorUseTheme theme value for color. Overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  override fun hillshadeShadowColorUseTheme(hillshadeShadowColorUseTheme: String): HillshadeLayer = apply {
    val propertyValue = PropertyValue("hillshade-shadow-color-use-theme", hillshadeShadowColorUseTheme)
    setProperty(propertyValue)
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
     * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
     */
    val defaultHillshadeAccentColor: String?
      /**
       * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
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
     * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
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
     * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
     */
    val defaultHillshadeAccentColorAsColorInt: Int?
      /**
       * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
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
     * Default color theme for [hillshadeAccentColor].
     */
    @MapboxExperimental
    val defaultHillshadeAccentColorUseTheme: String?
      /**
       * Get default value of the HillshadeAccentColor property as String
       *
       * @return String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-accent-color-use-theme").silentUnwrap()

    /**
     * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of hillshadeEmissiveStrength is in intensity.
     */
    val defaultHillshadeEmissiveStrength: Double?
      /**
       * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of hillshadeEmissiveStrength is in intensity.
       *
       * Get the default value of HillshadeEmissiveStrength property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-emissive-strength").silentUnwrap()
      }

    /**
     * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of hillshadeEmissiveStrength is in intensity.
     *
     * This is an Expression representation of "hillshade-emissive-strength".
     *
     */
    val defaultHillshadeEmissiveStrengthAsExpression: Expression?
      /**
       * Get default value of the HillshadeEmissiveStrength property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-emissive-strength").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultHillshadeEmissiveStrength?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for HillshadeEmissiveStrength.
     */
    val defaultHillshadeEmissiveStrengthTransition: StyleTransition?
      /**
       * Get the HillshadeEmissiveStrength property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-emissive-strength-transition").silentUnwrap()

    /**
     * Intensity of the hillshade Default value: 0.5. Value range: [0, 1]
     */
    val defaultHillshadeExaggeration: Double?
      /**
       * Intensity of the hillshade Default value: 0.5. Value range: [0, 1]
       *
       * Get the default value of HillshadeExaggeration property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-exaggeration").silentUnwrap()
      }

    /**
     * Intensity of the hillshade Default value: 0.5. Value range: [0, 1]
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
     * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
     */
    val defaultHillshadeHighlightColor: String?
      /**
       * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
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
     * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
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
     * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
     */
    val defaultHillshadeHighlightColorAsColorInt: Int?
      /**
       * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
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
     * Default color theme for [hillshadeHighlightColor].
     */
    @MapboxExperimental
    val defaultHillshadeHighlightColorUseTheme: String?
      /**
       * Get default value of the HillshadeHighlightColor property as String
       *
       * @return String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-highlight-color-use-theme").silentUnwrap()

    /**
     * Direction of light source when map is rotated. Default value: "viewport".
     */
    val defaultHillshadeIlluminationAnchor: HillshadeIlluminationAnchor?
      /**
       * Direction of light source when map is rotated. Default value: "viewport".
       *
       * Get the default value of HillshadeIlluminationAnchor property
       *
       * @return HillshadeIlluminationAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-illumination-anchor").silentUnwrap<String>()?.let {
          return HillshadeIlluminationAnchor.valueOf(it.uppercase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Direction of light source when map is rotated. Default value: "viewport".
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
     * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map` and no 3d lights enabled. If `hillshade-illumination-anchor` is set to `map` and 3d lights enabled, the direction from 3d lights is used instead. Default value: 335. Value range: [0, 359]
     */
    val defaultHillshadeIlluminationDirection: Double?
      /**
       * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map` and no 3d lights enabled. If `hillshade-illumination-anchor` is set to `map` and 3d lights enabled, the direction from 3d lights is used instead. Default value: 335. Value range: [0, 359]
       *
       * Get the default value of HillshadeIlluminationDirection property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-illumination-direction").silentUnwrap()
      }

    /**
     * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map` and no 3d lights enabled. If `hillshade-illumination-anchor` is set to `map` and 3d lights enabled, the direction from 3d lights is used instead. Default value: 335. Value range: [0, 359]
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
     * The shading color of areas that face away from the light source. Default value: "#000000".
     */
    val defaultHillshadeShadowColor: String?
      /**
       * The shading color of areas that face away from the light source. Default value: "#000000".
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
     * The shading color of areas that face away from the light source. Default value: "#000000".
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
     * The shading color of areas that face away from the light source. Default value: "#000000".
     */
    val defaultHillshadeShadowColorAsColorInt: Int?
      /**
       * The shading color of areas that face away from the light source. Default value: "#000000".
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

    /**
     * Default color theme for [hillshadeShadowColor].
     */
    @MapboxExperimental
    val defaultHillshadeShadowColorUseTheme: String?
      /**
       * Get default value of the HillshadeShadowColor property as String
       *
       * @return String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-shadow-color-use-theme").silentUnwrap()
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
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   *
   * @param slot value of slot. Setting it to empty string removes the slot.
   */
  fun slot(slot: String): HillshadeLayer

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): HillshadeLayer

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility as Expression
   */
  fun visibility(visibility: Expression): HillshadeLayer

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param minZoom value of minzoom
   */
  fun minZoom(minZoom: Double): HillshadeLayer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param maxZoom value of maxzoom
   */
  fun maxZoom(maxZoom: Double): HillshadeLayer

  // Property getters and setters

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
   *
   * @param hillshadeAccentColor value of hillshadeAccentColor
   */
  fun hillshadeAccentColor(hillshadeAccentColor: String = "#000000"): HillshadeLayer

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
   *
   * @param hillshadeAccentColor value of hillshadeAccentColor as Expression
   */
  fun hillshadeAccentColor(hillshadeAccentColor: Expression): HillshadeLayer

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
   *
   * @param hillshadeAccentColor value of hillshadeAccentColor
   */
  fun hillshadeAccentColor(@ColorInt hillshadeAccentColor: Int): HillshadeLayer

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
   *
   * Set the HillshadeAccentColor property transition options
   *
   * @param options transition options for String
   */
  fun hillshadeAccentColorTransition(options: StyleTransition): HillshadeLayer

  /**
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges. Default value: "#000000".
   *
   * DSL for [hillshadeAccentColorTransition].
   */
  fun hillshadeAccentColorTransition(block: StyleTransition.Builder.() -> Unit): HillshadeLayer

  /**
   * Set the hillshadeAccentColorUseTheme as String for [hillshadeAccentColor].
   *
   * @param hillshadeAccentColorUseTheme overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  fun hillshadeAccentColorUseTheme(hillshadeAccentColorUseTheme: String): HillshadeLayer

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of hillshadeEmissiveStrength is in intensity.
   *
   * @param hillshadeEmissiveStrength value of hillshadeEmissiveStrength
   */
  fun hillshadeEmissiveStrength(hillshadeEmissiveStrength: Double = 0.0): HillshadeLayer

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of hillshadeEmissiveStrength is in intensity.
   *
   * @param hillshadeEmissiveStrength value of hillshadeEmissiveStrength as Expression
   */
  fun hillshadeEmissiveStrength(hillshadeEmissiveStrength: Expression): HillshadeLayer

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of hillshadeEmissiveStrength is in intensity.
   *
   * Set the HillshadeEmissiveStrength property transition options
   *
   * @param options transition options for Double
   */
  fun hillshadeEmissiveStrengthTransition(options: StyleTransition): HillshadeLayer

  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of hillshadeEmissiveStrength is in intensity.
   *
   * DSL for [hillshadeEmissiveStrengthTransition].
   */
  fun hillshadeEmissiveStrengthTransition(block: StyleTransition.Builder.() -> Unit): HillshadeLayer

  /**
   * Intensity of the hillshade Default value: 0.5. Value range: [0, 1]
   *
   * @param hillshadeExaggeration value of hillshadeExaggeration
   */
  fun hillshadeExaggeration(hillshadeExaggeration: Double = 0.5): HillshadeLayer

  /**
   * Intensity of the hillshade Default value: 0.5. Value range: [0, 1]
   *
   * @param hillshadeExaggeration value of hillshadeExaggeration as Expression
   */
  fun hillshadeExaggeration(hillshadeExaggeration: Expression): HillshadeLayer

  /**
   * Intensity of the hillshade Default value: 0.5. Value range: [0, 1]
   *
   * Set the HillshadeExaggeration property transition options
   *
   * @param options transition options for Double
   */
  fun hillshadeExaggerationTransition(options: StyleTransition): HillshadeLayer

  /**
   * Intensity of the hillshade Default value: 0.5. Value range: [0, 1]
   *
   * DSL for [hillshadeExaggerationTransition].
   */
  fun hillshadeExaggerationTransition(block: StyleTransition.Builder.() -> Unit): HillshadeLayer

  /**
   * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
   *
   * @param hillshadeHighlightColor value of hillshadeHighlightColor
   */
  fun hillshadeHighlightColor(hillshadeHighlightColor: String = "#FFFFFF"): HillshadeLayer

  /**
   * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
   *
   * @param hillshadeHighlightColor value of hillshadeHighlightColor as Expression
   */
  fun hillshadeHighlightColor(hillshadeHighlightColor: Expression): HillshadeLayer

  /**
   * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
   *
   * @param hillshadeHighlightColor value of hillshadeHighlightColor
   */
  fun hillshadeHighlightColor(@ColorInt hillshadeHighlightColor: Int): HillshadeLayer

  /**
   * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
   *
   * Set the HillshadeHighlightColor property transition options
   *
   * @param options transition options for String
   */
  fun hillshadeHighlightColorTransition(options: StyleTransition): HillshadeLayer

  /**
   * The shading color of areas that faces towards the light source. Default value: "#FFFFFF".
   *
   * DSL for [hillshadeHighlightColorTransition].
   */
  fun hillshadeHighlightColorTransition(block: StyleTransition.Builder.() -> Unit): HillshadeLayer

  /**
   * Set the hillshadeHighlightColorUseTheme as String for [hillshadeHighlightColor].
   *
   * @param hillshadeHighlightColorUseTheme overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  fun hillshadeHighlightColorUseTheme(hillshadeHighlightColorUseTheme: String): HillshadeLayer

  /**
   * Direction of light source when map is rotated. Default value: "viewport".
   *
   * @param hillshadeIlluminationAnchor value of hillshadeIlluminationAnchor
   */
  fun hillshadeIlluminationAnchor(hillshadeIlluminationAnchor: HillshadeIlluminationAnchor = HillshadeIlluminationAnchor.VIEWPORT): HillshadeLayer

  /**
   * Direction of light source when map is rotated. Default value: "viewport".
   *
   * @param hillshadeIlluminationAnchor value of hillshadeIlluminationAnchor as Expression
   */
  fun hillshadeIlluminationAnchor(hillshadeIlluminationAnchor: Expression): HillshadeLayer

  /**
   * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map` and no 3d lights enabled. If `hillshade-illumination-anchor` is set to `map` and 3d lights enabled, the direction from 3d lights is used instead. Default value: 335. Value range: [0, 359]
   *
   * @param hillshadeIlluminationDirection value of hillshadeIlluminationDirection
   */
  fun hillshadeIlluminationDirection(hillshadeIlluminationDirection: Double = 335.0): HillshadeLayer

  /**
   * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map` and no 3d lights enabled. If `hillshade-illumination-anchor` is set to `map` and 3d lights enabled, the direction from 3d lights is used instead. Default value: 335. Value range: [0, 359]
   *
   * @param hillshadeIlluminationDirection value of hillshadeIlluminationDirection as Expression
   */
  fun hillshadeIlluminationDirection(hillshadeIlluminationDirection: Expression): HillshadeLayer

  /**
   * The shading color of areas that face away from the light source. Default value: "#000000".
   *
   * @param hillshadeShadowColor value of hillshadeShadowColor
   */
  fun hillshadeShadowColor(hillshadeShadowColor: String = "#000000"): HillshadeLayer

  /**
   * The shading color of areas that face away from the light source. Default value: "#000000".
   *
   * @param hillshadeShadowColor value of hillshadeShadowColor as Expression
   */
  fun hillshadeShadowColor(hillshadeShadowColor: Expression): HillshadeLayer

  /**
   * The shading color of areas that face away from the light source. Default value: "#000000".
   *
   * @param hillshadeShadowColor value of hillshadeShadowColor
   */
  fun hillshadeShadowColor(@ColorInt hillshadeShadowColor: Int): HillshadeLayer

  /**
   * The shading color of areas that face away from the light source. Default value: "#000000".
   *
   * Set the HillshadeShadowColor property transition options
   *
   * @param options transition options for String
   */
  fun hillshadeShadowColorTransition(options: StyleTransition): HillshadeLayer

  /**
   * The shading color of areas that face away from the light source. Default value: "#000000".
   *
   * DSL for [hillshadeShadowColorTransition].
   */
  fun hillshadeShadowColorTransition(block: StyleTransition.Builder.() -> Unit): HillshadeLayer

  /**
   * Set the hillshadeShadowColorUseTheme as String for [hillshadeShadowColor].
   *
   * @param hillshadeShadowColorUseTheme overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  fun hillshadeShadowColorUseTheme(hillshadeShadowColorUseTheme: String): HillshadeLayer
}

/**
 * DSL function for creating a [HillshadeLayer].
 */
fun hillshadeLayer(layerId: String, sourceId: String, block: HillshadeLayerDsl.() -> Unit): HillshadeLayer = HillshadeLayer(layerId, sourceId).apply(block)

// End of generated file.