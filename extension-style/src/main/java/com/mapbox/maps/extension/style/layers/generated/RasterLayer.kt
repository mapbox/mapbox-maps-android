// This file is generated.

package com.mapbox.maps.extension.style.layers.generated

import androidx.annotation.UiThread
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.Layer
import com.mapbox.maps.extension.style.layers.properties.*
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.types.*
import com.mapbox.maps.extension.style.utils.silentUnwrap
import java.util.*

/**
 * Raster map textures such as satellite imagery.
 *
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-raster)
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
 */
@UiThread
class RasterLayer(override val layerId: String, val sourceId: String) : RasterLayerDsl, Layer() {
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
     * Use static method [RasterLayer.defaultVisibility] to get the default property value.
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
   * Use static method [RasterLayer.defaultVisibility] to get the default property value.
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
     * Use static method [RasterLayer.defaultMinZoom] to get the default property value.
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
   * Use static method [RasterLayer.defaultMinZoom] to get the default property value.
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
     * Use static method [RasterLayer.defaultMaxZoom] to get the default property value.
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
   * Use static method [RasterLayer.defaultMaxZoom] to get the default property value.
   *
   * @param value value of maxzoom
   */
  override fun maxZoom(maxZoom: Double) = apply {
    val param = PropertyValue("maxzoom", maxZoom)
    setProperty(param)
  }

  // Property getters and setters

  /**
   * Increase or reduce the brightness of the image. The value is the maximum brightness.
   */
  val rasterBrightnessMax: Double?
    /**
     * Increase or reduce the brightness of the image. The value is the maximum brightness.
     *
     * Use static method [RasterLayer.defaultRasterBrightnessMax] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("raster-brightness-max")
    }

  /**
   * Increase or reduce the brightness of the image. The value is the maximum brightness.
   *
   * Use static method [RasterLayer.defaultRasterBrightnessMax] to set the default property.
   *
   * @param rasterBrightnessMax value of rasterBrightnessMax
   */
  override fun rasterBrightnessMax(rasterBrightnessMax: Double) = apply {
    val propertyValue = PropertyValue("raster-brightness-max", rasterBrightnessMax)
    setProperty(propertyValue)
  }

  /**
   * Increase or reduce the brightness of the image. The value is the maximum brightness.
   *
   * This is an Expression representation of "raster-brightness-max".
   *
   */
  val rasterBrightnessMaxAsExpression: Expression?
    /**
     * Increase or reduce the brightness of the image. The value is the maximum brightness.
     *
     * Get the RasterBrightnessMax property as an Expression
     *
     * Use static method [RasterLayer.defaultRasterBrightnessMaxAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("raster-brightness-max")?.let {
        return it
      }
      rasterBrightnessMax?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Increase or reduce the brightness of the image. The value is the maximum brightness.
   *
   * Use static method [RasterLayer.defaultRasterBrightnessMaxAsExpression] to set the default property.
   *
   * @param rasterBrightnessMax value of rasterBrightnessMax as Expression
   */
  override fun rasterBrightnessMax(rasterBrightnessMax: Expression) = apply {
    val propertyValue = PropertyValue("raster-brightness-max", rasterBrightnessMax)
    setProperty(propertyValue)
  }

  /**
   * Transition options for RasterBrightnessMax.
   */
  val rasterBrightnessMaxTransition: StyleTransition?
    /**
     * Get the RasterBrightnessMax property transition options
     *
     * Use static method [RasterLayer.defaultRasterBrightnessMaxTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("raster-brightness-max-transition")
    }

  /**
   * Set the RasterBrightnessMax property transition options
   *
   * Use static method [RasterLayer.defaultRasterBrightnessMaxTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun rasterBrightnessMaxTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("raster-brightness-max-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [rasterBrightnessMaxTransition].
   */
  override fun rasterBrightnessMaxTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    rasterBrightnessMaxTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Increase or reduce the brightness of the image. The value is the minimum brightness.
   */
  val rasterBrightnessMin: Double?
    /**
     * Increase or reduce the brightness of the image. The value is the minimum brightness.
     *
     * Use static method [RasterLayer.defaultRasterBrightnessMin] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("raster-brightness-min")
    }

  /**
   * Increase or reduce the brightness of the image. The value is the minimum brightness.
   *
   * Use static method [RasterLayer.defaultRasterBrightnessMin] to set the default property.
   *
   * @param rasterBrightnessMin value of rasterBrightnessMin
   */
  override fun rasterBrightnessMin(rasterBrightnessMin: Double) = apply {
    val propertyValue = PropertyValue("raster-brightness-min", rasterBrightnessMin)
    setProperty(propertyValue)
  }

  /**
   * Increase or reduce the brightness of the image. The value is the minimum brightness.
   *
   * This is an Expression representation of "raster-brightness-min".
   *
   */
  val rasterBrightnessMinAsExpression: Expression?
    /**
     * Increase or reduce the brightness of the image. The value is the minimum brightness.
     *
     * Get the RasterBrightnessMin property as an Expression
     *
     * Use static method [RasterLayer.defaultRasterBrightnessMinAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("raster-brightness-min")?.let {
        return it
      }
      rasterBrightnessMin?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Increase or reduce the brightness of the image. The value is the minimum brightness.
   *
   * Use static method [RasterLayer.defaultRasterBrightnessMinAsExpression] to set the default property.
   *
   * @param rasterBrightnessMin value of rasterBrightnessMin as Expression
   */
  override fun rasterBrightnessMin(rasterBrightnessMin: Expression) = apply {
    val propertyValue = PropertyValue("raster-brightness-min", rasterBrightnessMin)
    setProperty(propertyValue)
  }

  /**
   * Transition options for RasterBrightnessMin.
   */
  val rasterBrightnessMinTransition: StyleTransition?
    /**
     * Get the RasterBrightnessMin property transition options
     *
     * Use static method [RasterLayer.defaultRasterBrightnessMinTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("raster-brightness-min-transition")
    }

  /**
   * Set the RasterBrightnessMin property transition options
   *
   * Use static method [RasterLayer.defaultRasterBrightnessMinTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun rasterBrightnessMinTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("raster-brightness-min-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [rasterBrightnessMinTransition].
   */
  override fun rasterBrightnessMinTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    rasterBrightnessMinTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Increase or reduce the contrast of the image.
   */
  val rasterContrast: Double?
    /**
     * Increase or reduce the contrast of the image.
     *
     * Use static method [RasterLayer.defaultRasterContrast] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("raster-contrast")
    }

  /**
   * Increase or reduce the contrast of the image.
   *
   * Use static method [RasterLayer.defaultRasterContrast] to set the default property.
   *
   * @param rasterContrast value of rasterContrast
   */
  override fun rasterContrast(rasterContrast: Double) = apply {
    val propertyValue = PropertyValue("raster-contrast", rasterContrast)
    setProperty(propertyValue)
  }

  /**
   * Increase or reduce the contrast of the image.
   *
   * This is an Expression representation of "raster-contrast".
   *
   */
  val rasterContrastAsExpression: Expression?
    /**
     * Increase or reduce the contrast of the image.
     *
     * Get the RasterContrast property as an Expression
     *
     * Use static method [RasterLayer.defaultRasterContrastAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("raster-contrast")?.let {
        return it
      }
      rasterContrast?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Increase or reduce the contrast of the image.
   *
   * Use static method [RasterLayer.defaultRasterContrastAsExpression] to set the default property.
   *
   * @param rasterContrast value of rasterContrast as Expression
   */
  override fun rasterContrast(rasterContrast: Expression) = apply {
    val propertyValue = PropertyValue("raster-contrast", rasterContrast)
    setProperty(propertyValue)
  }

  /**
   * Transition options for RasterContrast.
   */
  val rasterContrastTransition: StyleTransition?
    /**
     * Get the RasterContrast property transition options
     *
     * Use static method [RasterLayer.defaultRasterContrastTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("raster-contrast-transition")
    }

  /**
   * Set the RasterContrast property transition options
   *
   * Use static method [RasterLayer.defaultRasterContrastTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun rasterContrastTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("raster-contrast-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [rasterContrastTransition].
   */
  override fun rasterContrastTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    rasterContrastTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Fade duration when a new tile is added.
   */
  val rasterFadeDuration: Double?
    /**
     * Fade duration when a new tile is added.
     *
     * Use static method [RasterLayer.defaultRasterFadeDuration] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("raster-fade-duration")
    }

  /**
   * Fade duration when a new tile is added.
   *
   * Use static method [RasterLayer.defaultRasterFadeDuration] to set the default property.
   *
   * @param rasterFadeDuration value of rasterFadeDuration
   */
  override fun rasterFadeDuration(rasterFadeDuration: Double) = apply {
    val propertyValue = PropertyValue("raster-fade-duration", rasterFadeDuration)
    setProperty(propertyValue)
  }

  /**
   * Fade duration when a new tile is added.
   *
   * This is an Expression representation of "raster-fade-duration".
   *
   */
  val rasterFadeDurationAsExpression: Expression?
    /**
     * Fade duration when a new tile is added.
     *
     * Get the RasterFadeDuration property as an Expression
     *
     * Use static method [RasterLayer.defaultRasterFadeDurationAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("raster-fade-duration")?.let {
        return it
      }
      rasterFadeDuration?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Fade duration when a new tile is added.
   *
   * Use static method [RasterLayer.defaultRasterFadeDurationAsExpression] to set the default property.
   *
   * @param rasterFadeDuration value of rasterFadeDuration as Expression
   */
  override fun rasterFadeDuration(rasterFadeDuration: Expression) = apply {
    val propertyValue = PropertyValue("raster-fade-duration", rasterFadeDuration)
    setProperty(propertyValue)
  }

  /**
   * Rotates hues around the color wheel.
   */
  val rasterHueRotate: Double?
    /**
     * Rotates hues around the color wheel.
     *
     * Use static method [RasterLayer.defaultRasterHueRotate] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("raster-hue-rotate")
    }

  /**
   * Rotates hues around the color wheel.
   *
   * Use static method [RasterLayer.defaultRasterHueRotate] to set the default property.
   *
   * @param rasterHueRotate value of rasterHueRotate
   */
  override fun rasterHueRotate(rasterHueRotate: Double) = apply {
    val propertyValue = PropertyValue("raster-hue-rotate", rasterHueRotate)
    setProperty(propertyValue)
  }

  /**
   * Rotates hues around the color wheel.
   *
   * This is an Expression representation of "raster-hue-rotate".
   *
   */
  val rasterHueRotateAsExpression: Expression?
    /**
     * Rotates hues around the color wheel.
     *
     * Get the RasterHueRotate property as an Expression
     *
     * Use static method [RasterLayer.defaultRasterHueRotateAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("raster-hue-rotate")?.let {
        return it
      }
      rasterHueRotate?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Rotates hues around the color wheel.
   *
   * Use static method [RasterLayer.defaultRasterHueRotateAsExpression] to set the default property.
   *
   * @param rasterHueRotate value of rasterHueRotate as Expression
   */
  override fun rasterHueRotate(rasterHueRotate: Expression) = apply {
    val propertyValue = PropertyValue("raster-hue-rotate", rasterHueRotate)
    setProperty(propertyValue)
  }

  /**
   * Transition options for RasterHueRotate.
   */
  val rasterHueRotateTransition: StyleTransition?
    /**
     * Get the RasterHueRotate property transition options
     *
     * Use static method [RasterLayer.defaultRasterHueRotateTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("raster-hue-rotate-transition")
    }

  /**
   * Set the RasterHueRotate property transition options
   *
   * Use static method [RasterLayer.defaultRasterHueRotateTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun rasterHueRotateTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("raster-hue-rotate-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [rasterHueRotateTransition].
   */
  override fun rasterHueRotateTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    rasterHueRotateTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity at which the image will be drawn.
   */
  val rasterOpacity: Double?
    /**
     * The opacity at which the image will be drawn.
     *
     * Use static method [RasterLayer.defaultRasterOpacity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("raster-opacity")
    }

  /**
   * The opacity at which the image will be drawn.
   *
   * Use static method [RasterLayer.defaultRasterOpacity] to set the default property.
   *
   * @param rasterOpacity value of rasterOpacity
   */
  override fun rasterOpacity(rasterOpacity: Double) = apply {
    val propertyValue = PropertyValue("raster-opacity", rasterOpacity)
    setProperty(propertyValue)
  }

  /**
   * The opacity at which the image will be drawn.
   *
   * This is an Expression representation of "raster-opacity".
   *
   */
  val rasterOpacityAsExpression: Expression?
    /**
     * The opacity at which the image will be drawn.
     *
     * Get the RasterOpacity property as an Expression
     *
     * Use static method [RasterLayer.defaultRasterOpacityAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("raster-opacity")?.let {
        return it
      }
      rasterOpacity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The opacity at which the image will be drawn.
   *
   * Use static method [RasterLayer.defaultRasterOpacityAsExpression] to set the default property.
   *
   * @param rasterOpacity value of rasterOpacity as Expression
   */
  override fun rasterOpacity(rasterOpacity: Expression) = apply {
    val propertyValue = PropertyValue("raster-opacity", rasterOpacity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for RasterOpacity.
   */
  val rasterOpacityTransition: StyleTransition?
    /**
     * Get the RasterOpacity property transition options
     *
     * Use static method [RasterLayer.defaultRasterOpacityTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("raster-opacity-transition")
    }

  /**
   * Set the RasterOpacity property transition options
   *
   * Use static method [RasterLayer.defaultRasterOpacityTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun rasterOpacityTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("raster-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [rasterOpacityTransition].
   */
  override fun rasterOpacityTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    rasterOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The resampling/interpolation method to use for overscaling, also known as texture magnification filter
   */
  val rasterResampling: RasterResampling?
    /**
     * The resampling/interpolation method to use for overscaling, also known as texture magnification filter
     *
     * Use static method [RasterLayer.defaultRasterResampling] to get the default property.
     *
     * @return RasterResampling
     */
    get() {
      getPropertyValue<String?>("raster-resampling")?.let {
        return RasterResampling.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * The resampling/interpolation method to use for overscaling, also known as texture magnification filter
   *
   * Use static method [RasterLayer.defaultRasterResampling] to set the default property.
   *
   * @param rasterResampling value of rasterResampling
   */
  override fun rasterResampling(rasterResampling: RasterResampling) = apply {
    val propertyValue = PropertyValue("raster-resampling", rasterResampling)
    setProperty(propertyValue)
  }

  /**
   * The resampling/interpolation method to use for overscaling, also known as texture magnification filter
   *
   * This is an Expression representation of "raster-resampling".
   *
   */
  val rasterResamplingAsExpression: Expression?
    /**
     * The resampling/interpolation method to use for overscaling, also known as texture magnification filter
     *
     * Get the RasterResampling property as an Expression
     *
     * Use static method [RasterLayer.defaultRasterResamplingAsExpression] to get the default property.
     *
     * @return RasterResampling
     */
    get() {
      getPropertyValue<Expression>("raster-resampling")?.let {
        return it
      }
      rasterResampling?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * The resampling/interpolation method to use for overscaling, also known as texture magnification filter
   *
   * Use static method [RasterLayer.defaultRasterResamplingAsExpression] to set the default property.
   *
   * @param rasterResampling value of rasterResampling as Expression
   */
  override fun rasterResampling(rasterResampling: Expression) = apply {
    val propertyValue = PropertyValue("raster-resampling", rasterResampling)
    setProperty(propertyValue)
  }

  /**
   * Increase or reduce the saturation of the image.
   */
  val rasterSaturation: Double?
    /**
     * Increase or reduce the saturation of the image.
     *
     * Use static method [RasterLayer.defaultRasterSaturation] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("raster-saturation")
    }

  /**
   * Increase or reduce the saturation of the image.
   *
   * Use static method [RasterLayer.defaultRasterSaturation] to set the default property.
   *
   * @param rasterSaturation value of rasterSaturation
   */
  override fun rasterSaturation(rasterSaturation: Double) = apply {
    val propertyValue = PropertyValue("raster-saturation", rasterSaturation)
    setProperty(propertyValue)
  }

  /**
   * Increase or reduce the saturation of the image.
   *
   * This is an Expression representation of "raster-saturation".
   *
   */
  val rasterSaturationAsExpression: Expression?
    /**
     * Increase or reduce the saturation of the image.
     *
     * Get the RasterSaturation property as an Expression
     *
     * Use static method [RasterLayer.defaultRasterSaturationAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("raster-saturation")?.let {
        return it
      }
      rasterSaturation?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Increase or reduce the saturation of the image.
   *
   * Use static method [RasterLayer.defaultRasterSaturationAsExpression] to set the default property.
   *
   * @param rasterSaturation value of rasterSaturation as Expression
   */
  override fun rasterSaturation(rasterSaturation: Expression) = apply {
    val propertyValue = PropertyValue("raster-saturation", rasterSaturation)
    setProperty(propertyValue)
  }

  /**
   * Transition options for RasterSaturation.
   */
  val rasterSaturationTransition: StyleTransition?
    /**
     * Get the RasterSaturation property transition options
     *
     * Use static method [RasterLayer.defaultRasterSaturationTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("raster-saturation-transition")
    }

  /**
   * Set the RasterSaturation property transition options
   *
   * Use static method [RasterLayer.defaultRasterSaturationTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun rasterSaturationTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("raster-saturation-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [rasterSaturationTransition].
   */
  override fun rasterSaturationTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    rasterSaturationTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Get the type of this layer
   *
   * @return Type of the layer as [String]
   */
  override fun getType(): String {
    return "raster"
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
        StyleManager.getStyleLayerPropertyDefaultValue("raster", "visibility").silentUnwrap<String>()?.let {
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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("raster", "minzoom").silentUnwrap()

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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("raster", "maxzoom").silentUnwrap()

    /**
     * Increase or reduce the brightness of the image. The value is the maximum brightness.
     */
    val defaultRasterBrightnessMax: Double?
      /**
       * Increase or reduce the brightness of the image. The value is the maximum brightness.
       *
       * Get the default value of RasterBrightnessMax property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-brightness-max").silentUnwrap()
      }

    /**
     * Increase or reduce the brightness of the image. The value is the maximum brightness.
     *
     * This is an Expression representation of "raster-brightness-max".
     *
     */
    val defaultRasterBrightnessMaxAsExpression: Expression?
      /**
       * Get default value of the RasterBrightnessMax property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-brightness-max").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultRasterBrightnessMax?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for RasterBrightnessMax.
     */
    val defaultRasterBrightnessMaxTransition: StyleTransition?
      /**
       * Get the RasterBrightnessMax property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-brightness-max-transition").silentUnwrap()

    /**
     * Increase or reduce the brightness of the image. The value is the minimum brightness.
     */
    val defaultRasterBrightnessMin: Double?
      /**
       * Increase or reduce the brightness of the image. The value is the minimum brightness.
       *
       * Get the default value of RasterBrightnessMin property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-brightness-min").silentUnwrap()
      }

    /**
     * Increase or reduce the brightness of the image. The value is the minimum brightness.
     *
     * This is an Expression representation of "raster-brightness-min".
     *
     */
    val defaultRasterBrightnessMinAsExpression: Expression?
      /**
       * Get default value of the RasterBrightnessMin property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-brightness-min").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultRasterBrightnessMin?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for RasterBrightnessMin.
     */
    val defaultRasterBrightnessMinTransition: StyleTransition?
      /**
       * Get the RasterBrightnessMin property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-brightness-min-transition").silentUnwrap()

    /**
     * Increase or reduce the contrast of the image.
     */
    val defaultRasterContrast: Double?
      /**
       * Increase or reduce the contrast of the image.
       *
       * Get the default value of RasterContrast property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-contrast").silentUnwrap()
      }

    /**
     * Increase or reduce the contrast of the image.
     *
     * This is an Expression representation of "raster-contrast".
     *
     */
    val defaultRasterContrastAsExpression: Expression?
      /**
       * Get default value of the RasterContrast property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-contrast").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultRasterContrast?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for RasterContrast.
     */
    val defaultRasterContrastTransition: StyleTransition?
      /**
       * Get the RasterContrast property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-contrast-transition").silentUnwrap()

    /**
     * Fade duration when a new tile is added.
     */
    val defaultRasterFadeDuration: Double?
      /**
       * Fade duration when a new tile is added.
       *
       * Get the default value of RasterFadeDuration property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-fade-duration").silentUnwrap()
      }

    /**
     * Fade duration when a new tile is added.
     *
     * This is an Expression representation of "raster-fade-duration".
     *
     */
    val defaultRasterFadeDurationAsExpression: Expression?
      /**
       * Get default value of the RasterFadeDuration property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-fade-duration").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultRasterFadeDuration?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Rotates hues around the color wheel.
     */
    val defaultRasterHueRotate: Double?
      /**
       * Rotates hues around the color wheel.
       *
       * Get the default value of RasterHueRotate property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-hue-rotate").silentUnwrap()
      }

    /**
     * Rotates hues around the color wheel.
     *
     * This is an Expression representation of "raster-hue-rotate".
     *
     */
    val defaultRasterHueRotateAsExpression: Expression?
      /**
       * Get default value of the RasterHueRotate property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-hue-rotate").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultRasterHueRotate?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for RasterHueRotate.
     */
    val defaultRasterHueRotateTransition: StyleTransition?
      /**
       * Get the RasterHueRotate property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-hue-rotate-transition").silentUnwrap()

    /**
     * The opacity at which the image will be drawn.
     */
    val defaultRasterOpacity: Double?
      /**
       * The opacity at which the image will be drawn.
       *
       * Get the default value of RasterOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-opacity").silentUnwrap()
      }

    /**
     * The opacity at which the image will be drawn.
     *
     * This is an Expression representation of "raster-opacity".
     *
     */
    val defaultRasterOpacityAsExpression: Expression?
      /**
       * Get default value of the RasterOpacity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-opacity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultRasterOpacity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for RasterOpacity.
     */
    val defaultRasterOpacityTransition: StyleTransition?
      /**
       * Get the RasterOpacity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-opacity-transition").silentUnwrap()

    /**
     * The resampling/interpolation method to use for overscaling, also known as texture magnification filter
     */
    val defaultRasterResampling: RasterResampling?
      /**
       * The resampling/interpolation method to use for overscaling, also known as texture magnification filter
       *
       * Get the default value of RasterResampling property
       *
       * @return RasterResampling
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-resampling").silentUnwrap<String>()?.let {
          return RasterResampling.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * The resampling/interpolation method to use for overscaling, also known as texture magnification filter
     *
     * This is an Expression representation of "raster-resampling".
     *
     */
    val defaultRasterResamplingAsExpression: Expression?
      /**
       * Get default value of the RasterResampling property as an Expression
       *
       * @return RasterResampling
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-resampling").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultRasterResampling?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * Increase or reduce the saturation of the image.
     */
    val defaultRasterSaturation: Double?
      /**
       * Increase or reduce the saturation of the image.
       *
       * Get the default value of RasterSaturation property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-saturation").silentUnwrap()
      }

    /**
     * Increase or reduce the saturation of the image.
     *
     * This is an Expression representation of "raster-saturation".
     *
     */
    val defaultRasterSaturationAsExpression: Expression?
      /**
       * Get default value of the RasterSaturation property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-saturation").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultRasterSaturation?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for RasterSaturation.
     */
    val defaultRasterSaturationTransition: StyleTransition?
      /**
       * Get the RasterSaturation property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("raster", "raster-saturation-transition").silentUnwrap()
  }
}

/**
 * This Interface contains all the functions that will be exposed to Koltin DSL.
 *
 * Separated the DSL receiver class to this interface to avoid IDE code suggestion for
 * property getters.
 */
@LayersDsl
interface RasterLayerDsl {
  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  fun sourceLayer(sourceLayer: String): RasterLayer

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): RasterLayer

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param value value of minzoom
   */
  fun minZoom(minZoom: Double): RasterLayer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param value value of maxzoom
   */
  fun maxZoom(maxZoom: Double): RasterLayer

  // Property getters and setters

  /**
   * Increase or reduce the brightness of the image. The value is the maximum brightness.
   *
   * @param rasterBrightnessMax value of rasterBrightnessMax
   */
  fun rasterBrightnessMax(rasterBrightnessMax: Double = 1.0): RasterLayer

  /**
   * Increase or reduce the brightness of the image. The value is the maximum brightness.
   *
   * @param rasterBrightnessMax value of rasterBrightnessMax as Expression
   */
  fun rasterBrightnessMax(rasterBrightnessMax: Expression): RasterLayer

  /**
   * Increase or reduce the brightness of the image. The value is the maximum brightness.
   *
   * Set the RasterBrightnessMax property transition options
   *
   * @param options transition options for Double
   */
  fun rasterBrightnessMaxTransition(options: StyleTransition): RasterLayer

  /**
   * Increase or reduce the brightness of the image. The value is the maximum brightness.
   *
   * DSL for [rasterBrightnessMaxTransition].
   */
  fun rasterBrightnessMaxTransition(block: StyleTransition.Builder.() -> Unit): RasterLayer

  /**
   * Increase or reduce the brightness of the image. The value is the minimum brightness.
   *
   * @param rasterBrightnessMin value of rasterBrightnessMin
   */
  fun rasterBrightnessMin(rasterBrightnessMin: Double = 0.0): RasterLayer

  /**
   * Increase or reduce the brightness of the image. The value is the minimum brightness.
   *
   * @param rasterBrightnessMin value of rasterBrightnessMin as Expression
   */
  fun rasterBrightnessMin(rasterBrightnessMin: Expression): RasterLayer

  /**
   * Increase or reduce the brightness of the image. The value is the minimum brightness.
   *
   * Set the RasterBrightnessMin property transition options
   *
   * @param options transition options for Double
   */
  fun rasterBrightnessMinTransition(options: StyleTransition): RasterLayer

  /**
   * Increase or reduce the brightness of the image. The value is the minimum brightness.
   *
   * DSL for [rasterBrightnessMinTransition].
   */
  fun rasterBrightnessMinTransition(block: StyleTransition.Builder.() -> Unit): RasterLayer

  /**
   * Increase or reduce the contrast of the image.
   *
   * @param rasterContrast value of rasterContrast
   */
  fun rasterContrast(rasterContrast: Double = 0.0): RasterLayer

  /**
   * Increase or reduce the contrast of the image.
   *
   * @param rasterContrast value of rasterContrast as Expression
   */
  fun rasterContrast(rasterContrast: Expression): RasterLayer

  /**
   * Increase or reduce the contrast of the image.
   *
   * Set the RasterContrast property transition options
   *
   * @param options transition options for Double
   */
  fun rasterContrastTransition(options: StyleTransition): RasterLayer

  /**
   * Increase or reduce the contrast of the image.
   *
   * DSL for [rasterContrastTransition].
   */
  fun rasterContrastTransition(block: StyleTransition.Builder.() -> Unit): RasterLayer

  /**
   * Fade duration when a new tile is added.
   *
   * @param rasterFadeDuration value of rasterFadeDuration
   */
  fun rasterFadeDuration(rasterFadeDuration: Double = 300.0): RasterLayer

  /**
   * Fade duration when a new tile is added.
   *
   * @param rasterFadeDuration value of rasterFadeDuration as Expression
   */
  fun rasterFadeDuration(rasterFadeDuration: Expression): RasterLayer

  /**
   * Rotates hues around the color wheel.
   *
   * @param rasterHueRotate value of rasterHueRotate
   */
  fun rasterHueRotate(rasterHueRotate: Double = 0.0): RasterLayer

  /**
   * Rotates hues around the color wheel.
   *
   * @param rasterHueRotate value of rasterHueRotate as Expression
   */
  fun rasterHueRotate(rasterHueRotate: Expression): RasterLayer

  /**
   * Rotates hues around the color wheel.
   *
   * Set the RasterHueRotate property transition options
   *
   * @param options transition options for Double
   */
  fun rasterHueRotateTransition(options: StyleTransition): RasterLayer

  /**
   * Rotates hues around the color wheel.
   *
   * DSL for [rasterHueRotateTransition].
   */
  fun rasterHueRotateTransition(block: StyleTransition.Builder.() -> Unit): RasterLayer

  /**
   * The opacity at which the image will be drawn.
   *
   * @param rasterOpacity value of rasterOpacity
   */
  fun rasterOpacity(rasterOpacity: Double = 1.0): RasterLayer

  /**
   * The opacity at which the image will be drawn.
   *
   * @param rasterOpacity value of rasterOpacity as Expression
   */
  fun rasterOpacity(rasterOpacity: Expression): RasterLayer

  /**
   * The opacity at which the image will be drawn.
   *
   * Set the RasterOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun rasterOpacityTransition(options: StyleTransition): RasterLayer

  /**
   * The opacity at which the image will be drawn.
   *
   * DSL for [rasterOpacityTransition].
   */
  fun rasterOpacityTransition(block: StyleTransition.Builder.() -> Unit): RasterLayer

  /**
   * The resampling/interpolation method to use for overscaling, also known as texture magnification filter
   *
   * @param rasterResampling value of rasterResampling
   */
  fun rasterResampling(rasterResampling: RasterResampling = RasterResampling.LINEAR): RasterLayer

  /**
   * The resampling/interpolation method to use for overscaling, also known as texture magnification filter
   *
   * @param rasterResampling value of rasterResampling as Expression
   */
  fun rasterResampling(rasterResampling: Expression): RasterLayer

  /**
   * Increase or reduce the saturation of the image.
   *
   * @param rasterSaturation value of rasterSaturation
   */
  fun rasterSaturation(rasterSaturation: Double = 0.0): RasterLayer

  /**
   * Increase or reduce the saturation of the image.
   *
   * @param rasterSaturation value of rasterSaturation as Expression
   */
  fun rasterSaturation(rasterSaturation: Expression): RasterLayer

  /**
   * Increase or reduce the saturation of the image.
   *
   * Set the RasterSaturation property transition options
   *
   * @param options transition options for Double
   */
  fun rasterSaturationTransition(options: StyleTransition): RasterLayer

  /**
   * Increase or reduce the saturation of the image.
   *
   * DSL for [rasterSaturationTransition].
   */
  fun rasterSaturationTransition(block: StyleTransition.Builder.() -> Unit): RasterLayer
}

/**
 * DSL function for creating a [RasterLayer].
 */
fun rasterLayer(layerId: String, sourceId: String, block: RasterLayerDsl.() -> Unit): RasterLayer = RasterLayer(layerId, sourceId).apply(block)

// End of generated file.