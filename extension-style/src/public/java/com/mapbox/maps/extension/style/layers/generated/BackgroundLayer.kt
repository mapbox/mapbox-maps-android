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
 * The background color or pattern of the map.
 *
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-background)
 *
 * @param layerId the ID of the layer
 */
@UiThread
class BackgroundLayer(override val layerId: String) : BackgroundLayerDsl, Layer() {

  /**
   * Whether this layer is displayed.
   */
  override val visibility: Visibility?
    /**
     * Whether this layer is displayed.
     *
     * Use static method [BackgroundLayer.defaultVisibility] to get the default property value.
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
   * Use static method [BackgroundLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Visibility): BackgroundLayer = apply {
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
     * Use static method [BackgroundLayer.defaultMinZoom] to get the default property value.
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
   * Use static method [BackgroundLayer.defaultMinZoom] to get the default property value.
   *
   * @param value value of minzoom
   */
  override fun minZoom(minZoom: Double): BackgroundLayer = apply {
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
     * Use static method [BackgroundLayer.defaultMaxZoom] to get the default property value.
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
   * Use static method [BackgroundLayer.defaultMaxZoom] to get the default property value.
   *
   * @param value value of maxzoom
   */
  override fun maxZoom(maxZoom: Double): BackgroundLayer = apply {
    val param = PropertyValue("maxzoom", maxZoom)
    setProperty(param)
  }

  // Property getters and setters

  /**
   * The color with which the background will be drawn.
   */
  val backgroundColor: String?
    /**
     * The color with which the background will be drawn.
     *
     * Use static method [BackgroundLayer.defaultBackgroundColor] to get the default property.
     *
     * @return String
     */
    get() {
      backgroundColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The color with which the background will be drawn.
   *
   * Use static method [BackgroundLayer.defaultBackgroundColor] to set the default property.
   *
   * @param backgroundColor value of backgroundColor
   */
  override fun backgroundColor(backgroundColor: String): BackgroundLayer = apply {
    val propertyValue = PropertyValue("background-color", backgroundColor)
    setProperty(propertyValue)
  }

  /**
   * The color with which the background will be drawn.
   *
   * This is an Expression representation of "background-color".
   *
   */
  val backgroundColorAsExpression: Expression?
    /**
     * The color with which the background will be drawn.
     *
     * Get the BackgroundColor property as an Expression
     *
     * Use static method [BackgroundLayer.defaultBackgroundColorAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("background-color")?.let {
        return it
      }
      return null
    }

  /**
   * The color with which the background will be drawn.
   *
   * Use static method [BackgroundLayer.defaultBackgroundColorAsExpression] to set the default property.
   *
   * @param backgroundColor value of backgroundColor as Expression
   */
  override fun backgroundColor(backgroundColor: Expression): BackgroundLayer = apply {
    val propertyValue = PropertyValue("background-color", backgroundColor)
    setProperty(propertyValue)
  }

  /**
   * The color with which the background will be drawn.
   */
  val backgroundColorAsColorInt: Int?
    /**
     * The color with which the background will be drawn.
     *
     * Use static method [BackgroundLayer.defaultBackgroundColorAsColorInt] to get the default property.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      backgroundColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The color with which the background will be drawn.
   *
   * Use static method [BackgroundLayer.defaultBackgroundColorAsColorInt] to set the default property.
   *
   * @param backgroundColor value of backgroundColor
   */
  override fun backgroundColor(@ColorInt backgroundColor: Int): BackgroundLayer = apply {
    val propertyValue = PropertyValue("background-color", colorIntToRgbaExpression(backgroundColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for BackgroundColor.
   */
  val backgroundColorTransition: StyleTransition?
    /**
     * Get the BackgroundColor property transition options
     *
     * Use static method [BackgroundLayer.defaultBackgroundColorTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("background-color-transition")
    }

  /**
   * Set the BackgroundColor property transition options
   *
   * Use static method [BackgroundLayer.defaultBackgroundColorTransition] to set the default property.
   *
   * @param options transition options for String
   */
  override fun backgroundColorTransition(options: StyleTransition): BackgroundLayer = apply {
    val propertyValue = PropertyValue("background-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [backgroundColorTransition].
   */
  override fun backgroundColorTransition(block: StyleTransition.Builder.() -> Unit): BackgroundLayer = apply {
    backgroundColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity at which the background will be drawn.
   */
  val backgroundOpacity: Double?
    /**
     * The opacity at which the background will be drawn.
     *
     * Use static method [BackgroundLayer.defaultBackgroundOpacity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("background-opacity")
    }

  /**
   * The opacity at which the background will be drawn.
   *
   * Use static method [BackgroundLayer.defaultBackgroundOpacity] to set the default property.
   *
   * @param backgroundOpacity value of backgroundOpacity
   */
  override fun backgroundOpacity(backgroundOpacity: Double): BackgroundLayer = apply {
    val propertyValue = PropertyValue("background-opacity", backgroundOpacity)
    setProperty(propertyValue)
  }

  /**
   * The opacity at which the background will be drawn.
   *
   * This is an Expression representation of "background-opacity".
   *
   */
  val backgroundOpacityAsExpression: Expression?
    /**
     * The opacity at which the background will be drawn.
     *
     * Get the BackgroundOpacity property as an Expression
     *
     * Use static method [BackgroundLayer.defaultBackgroundOpacityAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("background-opacity")?.let {
        return it
      }
      backgroundOpacity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The opacity at which the background will be drawn.
   *
   * Use static method [BackgroundLayer.defaultBackgroundOpacityAsExpression] to set the default property.
   *
   * @param backgroundOpacity value of backgroundOpacity as Expression
   */
  override fun backgroundOpacity(backgroundOpacity: Expression): BackgroundLayer = apply {
    val propertyValue = PropertyValue("background-opacity", backgroundOpacity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for BackgroundOpacity.
   */
  val backgroundOpacityTransition: StyleTransition?
    /**
     * Get the BackgroundOpacity property transition options
     *
     * Use static method [BackgroundLayer.defaultBackgroundOpacityTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("background-opacity-transition")
    }

  /**
   * Set the BackgroundOpacity property transition options
   *
   * Use static method [BackgroundLayer.defaultBackgroundOpacityTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun backgroundOpacityTransition(options: StyleTransition): BackgroundLayer = apply {
    val propertyValue = PropertyValue("background-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [backgroundOpacityTransition].
   */
  override fun backgroundOpacityTransition(block: StyleTransition.Builder.() -> Unit): BackgroundLayer = apply {
    backgroundOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Name of image in sprite to use for drawing an image background. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  val backgroundPattern: String?
    /**
     * Name of image in sprite to use for drawing an image background. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
     * Use static method [BackgroundLayer.defaultBackgroundPattern] to get the default property.
     *
     * @return String
     */
    get() {
      return getPropertyValue<String>("background-pattern")
    }

  /**
   * Name of image in sprite to use for drawing an image background. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * Use static method [BackgroundLayer.defaultBackgroundPattern] to set the default property.
   *
   * @param backgroundPattern value of backgroundPattern
   */
  override fun backgroundPattern(backgroundPattern: String): BackgroundLayer = apply {
    val propertyValue = PropertyValue("background-pattern", backgroundPattern)
    setProperty(propertyValue)
  }

  /**
   * Name of image in sprite to use for drawing an image background. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * This is an Expression representation of "background-pattern".
   *
   */
  val backgroundPatternAsExpression: Expression?
    /**
     * Name of image in sprite to use for drawing an image background. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
     * Get the BackgroundPattern property as an Expression
     *
     * Use static method [BackgroundLayer.defaultBackgroundPatternAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("background-pattern")?.let {
        return it
      }
      backgroundPattern?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Name of image in sprite to use for drawing an image background. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * Use static method [BackgroundLayer.defaultBackgroundPatternAsExpression] to set the default property.
   *
   * @param backgroundPattern value of backgroundPattern as Expression
   */
  override fun backgroundPattern(backgroundPattern: Expression): BackgroundLayer = apply {
    val propertyValue = PropertyValue("background-pattern", backgroundPattern)
    setProperty(propertyValue)
  }

  /**
   * Transition options for BackgroundPattern.
   */
  @Deprecated("This property has been deprecated and will do no operations")
  val backgroundPatternTransition: StyleTransition?
    /**
     * Get the BackgroundPattern property transition options
     *
     * Use static method [BackgroundLayer.defaultBackgroundPatternTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      logE("BackgroundLayer", "This property has been deprecated and will return null.")
      return null
    }

  /**
   * Set the BackgroundPattern property transition options
   *
   * Use static method [BackgroundLayer.defaultBackgroundPatternTransition] to set the default property.
   *
   * @param options transition options for String
   */
  @Deprecated("This property has been deprecated and will do no operations")
  override fun backgroundPatternTransition(options: StyleTransition): BackgroundLayer = apply {
    // no-op
  }

  /**
   * DSL for [backgroundPatternTransition].
   */
  @Deprecated("This property has been deprecated and will do no operations")
  override fun backgroundPatternTransition(block: StyleTransition.Builder.() -> Unit): BackgroundLayer = apply {
    // no-op
  }

  /**
   * Get the type of this layer
   *
   * @return Type of the layer as [String]
   */
  override fun getType(): String {
    return "background"
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
        StyleManager.getStyleLayerPropertyDefaultValue("background", "visibility").silentUnwrap<String>()?.let {
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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("background", "minzoom").silentUnwrap()

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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("background", "maxzoom").silentUnwrap()

    /**
     * The color with which the background will be drawn.
     */
    val defaultBackgroundColor: String?
      /**
       * The color with which the background will be drawn.
       *
       * Get the default value of BackgroundColor property
       *
       * @return String
       */
      get() {
        defaultBackgroundColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The color with which the background will be drawn.
     *
     * This is an Expression representation of "background-color".
     *
     */
    val defaultBackgroundColorAsExpression: Expression?
      /**
       * Get default value of the BackgroundColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("background", "background-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The color with which the background will be drawn.
     */
    val defaultBackgroundColorAsColorInt: Int?
      /**
       * The color with which the background will be drawn.
       *
       * Get the default value of BackgroundColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultBackgroundColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for BackgroundColor.
     */
    val defaultBackgroundColorTransition: StyleTransition?
      /**
       * Get the BackgroundColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("background", "background-color-transition").silentUnwrap()

    /**
     * The opacity at which the background will be drawn.
     */
    val defaultBackgroundOpacity: Double?
      /**
       * The opacity at which the background will be drawn.
       *
       * Get the default value of BackgroundOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("background", "background-opacity").silentUnwrap()
      }

    /**
     * The opacity at which the background will be drawn.
     *
     * This is an Expression representation of "background-opacity".
     *
     */
    val defaultBackgroundOpacityAsExpression: Expression?
      /**
       * Get default value of the BackgroundOpacity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("background", "background-opacity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultBackgroundOpacity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for BackgroundOpacity.
     */
    val defaultBackgroundOpacityTransition: StyleTransition?
      /**
       * Get the BackgroundOpacity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("background", "background-opacity-transition").silentUnwrap()

    /**
     * Name of image in sprite to use for drawing an image background. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     */
    val defaultBackgroundPattern: String?
      /**
       * Name of image in sprite to use for drawing an image background. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
       *
       * Get the default value of BackgroundPattern property
       *
       * @return String
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("background", "background-pattern").silentUnwrap()
      }

    /**
     * Name of image in sprite to use for drawing an image background. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
     *
     * This is an Expression representation of "background-pattern".
     *
     */
    val defaultBackgroundPatternAsExpression: Expression?
      /**
       * Get default value of the BackgroundPattern property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("background", "background-pattern").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultBackgroundPattern?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for BackgroundPattern.
     */
    @Deprecated("This property has been deprecated and will do no operations")
    val defaultBackgroundPatternTransition: StyleTransition?
      /**
       * Get the BackgroundPattern property transition options
       *
       * @return transition options for String
       */
      get() {
        logE("BackgroundLayer", "This property has been deprecated and will return null.")
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
interface BackgroundLayerDsl {

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): BackgroundLayer

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param value value of minzoom
   */
  fun minZoom(minZoom: Double): BackgroundLayer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param value value of maxzoom
   */
  fun maxZoom(maxZoom: Double): BackgroundLayer

  // Property getters and setters

  /**
   * The color with which the background will be drawn.
   *
   * @param backgroundColor value of backgroundColor
   */
  fun backgroundColor(backgroundColor: String = "#000000"): BackgroundLayer

  /**
   * The color with which the background will be drawn.
   *
   * @param backgroundColor value of backgroundColor as Expression
   */
  fun backgroundColor(backgroundColor: Expression): BackgroundLayer

  /**
   * The color with which the background will be drawn.
   *
   * @param backgroundColor value of backgroundColor
   */
  fun backgroundColor(@ColorInt backgroundColor: Int): BackgroundLayer

  /**
   * The color with which the background will be drawn.
   *
   * Set the BackgroundColor property transition options
   *
   * @param options transition options for String
   */
  fun backgroundColorTransition(options: StyleTransition): BackgroundLayer

  /**
   * The color with which the background will be drawn.
   *
   * DSL for [backgroundColorTransition].
   */
  fun backgroundColorTransition(block: StyleTransition.Builder.() -> Unit): BackgroundLayer

  /**
   * The opacity at which the background will be drawn.
   *
   * @param backgroundOpacity value of backgroundOpacity
   */
  fun backgroundOpacity(backgroundOpacity: Double = 1.0): BackgroundLayer

  /**
   * The opacity at which the background will be drawn.
   *
   * @param backgroundOpacity value of backgroundOpacity as Expression
   */
  fun backgroundOpacity(backgroundOpacity: Expression): BackgroundLayer

  /**
   * The opacity at which the background will be drawn.
   *
   * Set the BackgroundOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun backgroundOpacityTransition(options: StyleTransition): BackgroundLayer

  /**
   * The opacity at which the background will be drawn.
   *
   * DSL for [backgroundOpacityTransition].
   */
  fun backgroundOpacityTransition(block: StyleTransition.Builder.() -> Unit): BackgroundLayer

  /**
   * Name of image in sprite to use for drawing an image background. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * @param backgroundPattern value of backgroundPattern
   */
  fun backgroundPattern(backgroundPattern: String): BackgroundLayer

  /**
   * Name of image in sprite to use for drawing an image background. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * @param backgroundPattern value of backgroundPattern as Expression
   */
  fun backgroundPattern(backgroundPattern: Expression): BackgroundLayer

  /**
   * Name of image in sprite to use for drawing an image background. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * Set the BackgroundPattern property transition options
   *
   * @param options transition options for String
   */
  fun backgroundPatternTransition(options: StyleTransition): BackgroundLayer

  /**
   * Name of image in sprite to use for drawing an image background. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * DSL for [backgroundPatternTransition].
   */
  fun backgroundPatternTransition(block: StyleTransition.Builder.() -> Unit): BackgroundLayer
}

/**
 * DSL functions for creating a [BackgroundLayer].
 */
fun backgroundLayer(layerId: String, block: BackgroundLayerDsl.() -> Unit): BackgroundLayer = BackgroundLayer(layerId).apply(block)

// End of generated file.