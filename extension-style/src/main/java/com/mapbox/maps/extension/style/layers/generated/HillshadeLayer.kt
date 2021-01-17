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
 * @see <a href="https://www.mapbox.com/mapbox-gl-style-spec/#layers-hillshade">The online documentation</a>
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
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
   */
  val hillshadeAccentColor: String?
    /**
     * Get the HillshadeAccentColor property
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
   * Set the HillshadeAccentColor property
   *
   * @param hillshadeAccentColor value of hillshadeAccentColor
   */
  override fun hillshadeAccentColor(hillshadeAccentColor: String) = apply {
    val propertyValue = PropertyValue("hillshade-accent-color", hillshadeAccentColor)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "hillshade-accent-color".
   *
   * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
   */
  val hillshadeAccentColorAsExpression: Expression?
    /**
     * Get the HillshadeAccentColor property as an Expression
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
   * Set the HillshadeAccentColor property
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
   * Set the HillshadeAccentColor property.
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
     * @return transition options for String
     */
    get() {
      return getPropertyValue("hillshade-accent-color-transition")
    }

  /**
   * Set the HillshadeAccentColor property transition options
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
     * Get the HillshadeExaggeration property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("hillshade-exaggeration")
    }

  /**
   * Set the HillshadeExaggeration property
   *
   * @param hillshadeExaggeration value of hillshadeExaggeration
   */
  override fun hillshadeExaggeration(hillshadeExaggeration: Double) = apply {
    val propertyValue = PropertyValue("hillshade-exaggeration", hillshadeExaggeration)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "hillshade-exaggeration".
   *
   * Intensity of the hillshade
   */
  val hillshadeExaggerationAsExpression: Expression?
    /**
     * Get the HillshadeExaggeration property as an Expression
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
   * Set the HillshadeExaggeration property
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
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("hillshade-exaggeration-transition")
    }

  /**
   * Set the HillshadeExaggeration property transition options
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
     * Get the HillshadeHighlightColor property
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
   * Set the HillshadeHighlightColor property
   *
   * @param hillshadeHighlightColor value of hillshadeHighlightColor
   */
  override fun hillshadeHighlightColor(hillshadeHighlightColor: String) = apply {
    val propertyValue = PropertyValue("hillshade-highlight-color", hillshadeHighlightColor)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "hillshade-highlight-color".
   *
   * The shading color of areas that faces towards the light source.
   */
  val hillshadeHighlightColorAsExpression: Expression?
    /**
     * Get the HillshadeHighlightColor property as an Expression
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
   * Set the HillshadeHighlightColor property
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
   * Set the HillshadeHighlightColor property.
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
     * @return transition options for String
     */
    get() {
      return getPropertyValue("hillshade-highlight-color-transition")
    }

  /**
   * Set the HillshadeHighlightColor property transition options
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
     * Get the HillshadeIlluminationAnchor property
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
   * Set the HillshadeIlluminationAnchor property
   *
   * @param hillshadeIlluminationAnchor value of hillshadeIlluminationAnchor
   */
  override fun hillshadeIlluminationAnchor(hillshadeIlluminationAnchor: HillshadeIlluminationAnchor) = apply {
    val propertyValue = PropertyValue("hillshade-illumination-anchor", hillshadeIlluminationAnchor)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "hillshade-illumination-anchor".
   *
   * Direction of light source when map is rotated.
   */
  val hillshadeIlluminationAnchorAsExpression: Expression?
    /**
     * Get the HillshadeIlluminationAnchor property as an Expression
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
   * Set the HillshadeIlluminationAnchor property
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
     * Get the HillshadeIlluminationDirection property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("hillshade-illumination-direction")
    }

  /**
   * Set the HillshadeIlluminationDirection property
   *
   * @param hillshadeIlluminationDirection value of hillshadeIlluminationDirection
   */
  override fun hillshadeIlluminationDirection(hillshadeIlluminationDirection: Double) = apply {
    val propertyValue = PropertyValue("hillshade-illumination-direction", hillshadeIlluminationDirection)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "hillshade-illumination-direction".
   *
   * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map`.
   */
  val hillshadeIlluminationDirectionAsExpression: Expression?
    /**
     * Get the HillshadeIlluminationDirection property as an Expression
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
   * Set the HillshadeIlluminationDirection property
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
     * Get the HillshadeShadowColor property
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
   * Set the HillshadeShadowColor property
   *
   * @param hillshadeShadowColor value of hillshadeShadowColor
   */
  override fun hillshadeShadowColor(hillshadeShadowColor: String) = apply {
    val propertyValue = PropertyValue("hillshade-shadow-color", hillshadeShadowColor)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "hillshade-shadow-color".
   *
   * The shading color of areas that face away from the light source.
   */
  val hillshadeShadowColorAsExpression: Expression?
    /**
     * Get the HillshadeShadowColor property as an Expression
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
   * Set the HillshadeShadowColor property
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
   * Set the HillshadeShadowColor property.
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
     * @return transition options for String
     */
    get() {
      return getPropertyValue("hillshade-shadow-color-transition")
    }

  /**
   * Set the HillshadeShadowColor property transition options
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
     * This is an Expression representation of "hillshade-accent-color".
     *
     * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
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
       * Get the default value of HillshadeExaggeration property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-exaggeration").silentUnwrap()
      }

    /**
     * This is an Expression representation of "hillshade-exaggeration".
     *
     * Intensity of the hillshade
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
     * This is an Expression representation of "hillshade-highlight-color".
     *
     * The shading color of areas that faces towards the light source.
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
     * This is an Expression representation of "hillshade-illumination-anchor".
     *
     * Direction of light source when map is rotated.
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
       * Get the default value of HillshadeIlluminationDirection property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("hillshade", "hillshade-illumination-direction").silentUnwrap()
      }

    /**
     * This is an Expression representation of "hillshade-illumination-direction".
     *
     * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map`.
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
     * This is an Expression representation of "hillshade-shadow-color".
     *
     * The shading color of areas that face away from the light source.
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
   * Set the sourceLayer property
   *
   * @param sourceLayer value of sourceLayer
   */
  fun sourceLayer(sourceLayer: String): HillshadeLayer

  /**
   * Set the Visibility property
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): HillshadeLayer

  /**
   * Set the minzoom property
   *
   * @param value value of minzoom
   */
  fun minZoom(minZoom: Double): HillshadeLayer

  /**
   * Set the maxzoom property
   *
   * @param value value of maxzoom
   */
  fun maxZoom(maxZoom: Double): HillshadeLayer

  // Property getters and setters

  /**
   * Set the HillshadeAccentColor property
   *
   * @param hillshadeAccentColor value of hillshadeAccentColor
   */
  fun hillshadeAccentColor(hillshadeAccentColor: String = "#000000"): HillshadeLayer

  /**
   * Set the HillshadeAccentColor property
   *
   * @param hillshadeAccentColor value of hillshadeAccentColor as Expression
   */
  fun hillshadeAccentColor(hillshadeAccentColor: Expression): HillshadeLayer

  /**
   * Set the HillshadeAccentColor property.
   *
   * @param hillshadeAccentColor value of hillshadeAccentColor
   */
  fun hillshadeAccentColor(@ColorInt hillshadeAccentColor: Int): HillshadeLayer

  /**
   * Set the HillshadeAccentColor property transition options
   *
   * @param options transition options for String
   */
  fun hillshadeAccentColorTransition(options: StyleTransition): HillshadeLayer

  /**
   * DSL for [hillshadeAccentColorTransition].
   */
  fun hillshadeAccentColorTransition(block: StyleTransition.Builder.() -> Unit): HillshadeLayer

  /**
   * Set the HillshadeExaggeration property
   *
   * @param hillshadeExaggeration value of hillshadeExaggeration
   */
  fun hillshadeExaggeration(hillshadeExaggeration: Double = 0.5): HillshadeLayer

  /**
   * Set the HillshadeExaggeration property
   *
   * @param hillshadeExaggeration value of hillshadeExaggeration as Expression
   */
  fun hillshadeExaggeration(hillshadeExaggeration: Expression): HillshadeLayer

  /**
   * Set the HillshadeExaggeration property transition options
   *
   * @param options transition options for Double
   */
  fun hillshadeExaggerationTransition(options: StyleTransition): HillshadeLayer

  /**
   * DSL for [hillshadeExaggerationTransition].
   */
  fun hillshadeExaggerationTransition(block: StyleTransition.Builder.() -> Unit): HillshadeLayer

  /**
   * Set the HillshadeHighlightColor property
   *
   * @param hillshadeHighlightColor value of hillshadeHighlightColor
   */
  fun hillshadeHighlightColor(hillshadeHighlightColor: String = "#FFFFFF"): HillshadeLayer

  /**
   * Set the HillshadeHighlightColor property
   *
   * @param hillshadeHighlightColor value of hillshadeHighlightColor as Expression
   */
  fun hillshadeHighlightColor(hillshadeHighlightColor: Expression): HillshadeLayer

  /**
   * Set the HillshadeHighlightColor property.
   *
   * @param hillshadeHighlightColor value of hillshadeHighlightColor
   */
  fun hillshadeHighlightColor(@ColorInt hillshadeHighlightColor: Int): HillshadeLayer

  /**
   * Set the HillshadeHighlightColor property transition options
   *
   * @param options transition options for String
   */
  fun hillshadeHighlightColorTransition(options: StyleTransition): HillshadeLayer

  /**
   * DSL for [hillshadeHighlightColorTransition].
   */
  fun hillshadeHighlightColorTransition(block: StyleTransition.Builder.() -> Unit): HillshadeLayer

  /**
   * Set the HillshadeIlluminationAnchor property
   *
   * @param hillshadeIlluminationAnchor value of hillshadeIlluminationAnchor
   */
  fun hillshadeIlluminationAnchor(hillshadeIlluminationAnchor: HillshadeIlluminationAnchor = HillshadeIlluminationAnchor.VIEWPORT): HillshadeLayer

  /**
   * Set the HillshadeIlluminationAnchor property
   *
   * @param hillshadeIlluminationAnchor value of hillshadeIlluminationAnchor as Expression
   */
  fun hillshadeIlluminationAnchor(hillshadeIlluminationAnchor: Expression): HillshadeLayer

  /**
   * Set the HillshadeIlluminationDirection property
   *
   * @param hillshadeIlluminationDirection value of hillshadeIlluminationDirection
   */
  fun hillshadeIlluminationDirection(hillshadeIlluminationDirection: Double = 335.0): HillshadeLayer

  /**
   * Set the HillshadeIlluminationDirection property
   *
   * @param hillshadeIlluminationDirection value of hillshadeIlluminationDirection as Expression
   */
  fun hillshadeIlluminationDirection(hillshadeIlluminationDirection: Expression): HillshadeLayer

  /**
   * Set the HillshadeShadowColor property
   *
   * @param hillshadeShadowColor value of hillshadeShadowColor
   */
  fun hillshadeShadowColor(hillshadeShadowColor: String = "#000000"): HillshadeLayer

  /**
   * Set the HillshadeShadowColor property
   *
   * @param hillshadeShadowColor value of hillshadeShadowColor as Expression
   */
  fun hillshadeShadowColor(hillshadeShadowColor: Expression): HillshadeLayer

  /**
   * Set the HillshadeShadowColor property.
   *
   * @param hillshadeShadowColor value of hillshadeShadowColor
   */
  fun hillshadeShadowColor(@ColorInt hillshadeShadowColor: Int): HillshadeLayer

  /**
   * Set the HillshadeShadowColor property transition options
   *
   * @param options transition options for String
   */
  fun hillshadeShadowColorTransition(options: StyleTransition): HillshadeLayer

  /**
   * DSL for [hillshadeShadowColorTransition].
   */
  fun hillshadeShadowColorTransition(block: StyleTransition.Builder.() -> Unit): HillshadeLayer
}

/**
 * DSL function for [HillshadeLayer].
 */
fun hillshadeLayer(layerId: String, sourceId: String, block: HillshadeLayerDsl.() -> Unit): HillshadeLayer = HillshadeLayer(layerId, sourceId).apply(block)

// End of generated file.