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
 * Location Indicator layer.
 *
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-location-indicator)
 *
 * @param layerId the ID of the layer
 */
@UiThread
class LocationIndicatorLayer(override val layerId: String) : LocationIndicatorLayerDsl, Layer() {

  /**
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   *
   * @param slot value of slot
   */
  @MapboxExperimental
  fun slot(slot: String): LocationIndicatorLayer = apply {
    val param = PropertyValue("slot", slot)
    setProperty(param)
  }

  /**
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   */
  @MapboxExperimental
  val slot: String?
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
     * Use static method [LocationIndicatorLayer.defaultVisibility] to get the default property value.
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
     * Use static method [LocationIndicatorLayer.defaultVisibility] to get the default property value.
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
   * Use static method [LocationIndicatorLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Visibility): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("visibility", visibility)
    setProperty(propertyValue)
  }

  /**
   * Whether this layer is displayed.
   *
   * Use static method [[LocationIndicatorLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Expression): LocationIndicatorLayer = apply {
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
     * Use static method [LocationIndicatorLayer.defaultMinZoom] to get the default property value.
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
   * Use static method [LocationIndicatorLayer.defaultMinZoom] to get the default property value.
   *
   * @param minZoom value of minzoom
   */
  override fun minZoom(minZoom: Double): LocationIndicatorLayer = apply {
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
     * Use static method [LocationIndicatorLayer.defaultMaxZoom] to get the default property value.
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
   * Use static method [LocationIndicatorLayer.defaultMaxZoom] to get the default property value.
   *
   * @param maxZoom value of maxzoom
   */
  override fun maxZoom(maxZoom: Double): LocationIndicatorLayer = apply {
    val param = PropertyValue("maxzoom", maxZoom)
    setProperty(param)
  }

  // Property getters and setters

  /**
   * Name of image in sprite to use as the middle of the location indicator.
   */
  val bearingImage: String?
    /**
     * Name of image in sprite to use as the middle of the location indicator.
     *
     * Use static method [LocationIndicatorLayer.defaultBearingImage] to get the default property.
     *
     * @return String
     */
    get() {
      return getPropertyValue<String>("bearing-image")
    }

  /**
   * Name of image in sprite to use as the middle of the location indicator.
   *
   * Use static method [LocationIndicatorLayer.defaultBearingImage] to set the default property.
   *
   * @param bearingImage value of bearingImage
   */
  override fun bearingImage(bearingImage: String): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("bearing-image", bearingImage)
    setProperty(propertyValue)
  }

  /**
   * Name of image in sprite to use as the middle of the location indicator.
   *
   * This is an Expression representation of "bearing-image".
   *
   */
  val bearingImageAsExpression: Expression?
    /**
     * Name of image in sprite to use as the middle of the location indicator.
     *
     * Get the BearingImage property as an Expression
     *
     * Use static method [LocationIndicatorLayer.defaultBearingImageAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("bearing-image")?.let {
        return it
      }
      bearingImage?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Name of image in sprite to use as the middle of the location indicator.
   *
   * Use static method [LocationIndicatorLayer.defaultBearingImageAsExpression] to set the default property.
   *
   * @param bearingImage value of bearingImage as Expression
   */
  override fun bearingImage(bearingImage: Expression): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("bearing-image", bearingImage)
    setProperty(propertyValue)
  }

  /**
   * Name of image in sprite to use as the background of the location indicator.
   */
  val shadowImage: String?
    /**
     * Name of image in sprite to use as the background of the location indicator.
     *
     * Use static method [LocationIndicatorLayer.defaultShadowImage] to get the default property.
     *
     * @return String
     */
    get() {
      return getPropertyValue<String>("shadow-image")
    }

  /**
   * Name of image in sprite to use as the background of the location indicator.
   *
   * Use static method [LocationIndicatorLayer.defaultShadowImage] to set the default property.
   *
   * @param shadowImage value of shadowImage
   */
  override fun shadowImage(shadowImage: String): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("shadow-image", shadowImage)
    setProperty(propertyValue)
  }

  /**
   * Name of image in sprite to use as the background of the location indicator.
   *
   * This is an Expression representation of "shadow-image".
   *
   */
  val shadowImageAsExpression: Expression?
    /**
     * Name of image in sprite to use as the background of the location indicator.
     *
     * Get the ShadowImage property as an Expression
     *
     * Use static method [LocationIndicatorLayer.defaultShadowImageAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("shadow-image")?.let {
        return it
      }
      shadowImage?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Name of image in sprite to use as the background of the location indicator.
   *
   * Use static method [LocationIndicatorLayer.defaultShadowImageAsExpression] to set the default property.
   *
   * @param shadowImage value of shadowImage as Expression
   */
  override fun shadowImage(shadowImage: Expression): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("shadow-image", shadowImage)
    setProperty(propertyValue)
  }

  /**
   * Name of image in sprite to use as the top of the location indicator.
   */
  val topImage: String?
    /**
     * Name of image in sprite to use as the top of the location indicator.
     *
     * Use static method [LocationIndicatorLayer.defaultTopImage] to get the default property.
     *
     * @return String
     */
    get() {
      return getPropertyValue<String>("top-image")
    }

  /**
   * Name of image in sprite to use as the top of the location indicator.
   *
   * Use static method [LocationIndicatorLayer.defaultTopImage] to set the default property.
   *
   * @param topImage value of topImage
   */
  override fun topImage(topImage: String): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("top-image", topImage)
    setProperty(propertyValue)
  }

  /**
   * Name of image in sprite to use as the top of the location indicator.
   *
   * This is an Expression representation of "top-image".
   *
   */
  val topImageAsExpression: Expression?
    /**
     * Name of image in sprite to use as the top of the location indicator.
     *
     * Get the TopImage property as an Expression
     *
     * Use static method [LocationIndicatorLayer.defaultTopImageAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("top-image")?.let {
        return it
      }
      topImage?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Name of image in sprite to use as the top of the location indicator.
   *
   * Use static method [LocationIndicatorLayer.defaultTopImageAsExpression] to set the default property.
   *
   * @param topImage value of topImage as Expression
   */
  override fun topImage(topImage: Expression): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("top-image", topImage)
    setProperty(propertyValue)
  }

  /**
   * The accuracy, in meters, of the position source used to retrieve the position of the location indicator.
   */
  val accuracyRadius: Double?
    /**
     * The accuracy, in meters, of the position source used to retrieve the position of the location indicator.
     *
     * Use static method [LocationIndicatorLayer.defaultAccuracyRadius] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("accuracy-radius")
    }

  /**
   * The accuracy, in meters, of the position source used to retrieve the position of the location indicator.
   *
   * Use static method [LocationIndicatorLayer.defaultAccuracyRadius] to set the default property.
   *
   * @param accuracyRadius value of accuracyRadius
   */
  override fun accuracyRadius(accuracyRadius: Double): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("accuracy-radius", accuracyRadius)
    setProperty(propertyValue)
  }

  /**
   * The accuracy, in meters, of the position source used to retrieve the position of the location indicator.
   *
   * This is an Expression representation of "accuracy-radius".
   *
   */
  val accuracyRadiusAsExpression: Expression?
    /**
     * The accuracy, in meters, of the position source used to retrieve the position of the location indicator.
     *
     * Get the AccuracyRadius property as an Expression
     *
     * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("accuracy-radius")?.let {
        return it
      }
      accuracyRadius?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The accuracy, in meters, of the position source used to retrieve the position of the location indicator.
   *
   * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusAsExpression] to set the default property.
   *
   * @param accuracyRadius value of accuracyRadius as Expression
   */
  override fun accuracyRadius(accuracyRadius: Expression): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("accuracy-radius", accuracyRadius)
    setProperty(propertyValue)
  }

  /**
   * Transition options for AccuracyRadius.
   */
  val accuracyRadiusTransition: StyleTransition?
    /**
     * Get the AccuracyRadius property transition options
     *
     * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("accuracy-radius-transition")
    }

  /**
   * Set the AccuracyRadius property transition options
   *
   * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun accuracyRadiusTransition(options: StyleTransition): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("accuracy-radius-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [accuracyRadiusTransition].
   */
  override fun accuracyRadiusTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer = apply {
    accuracyRadiusTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
   */
  val accuracyRadiusBorderColor: String?
    /**
     * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
     *
     * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusBorderColor] to get the default property.
     *
     * @return String
     */
    get() {
      accuracyRadiusBorderColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
   *
   * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusBorderColor] to set the default property.
   *
   * @param accuracyRadiusBorderColor value of accuracyRadiusBorderColor
   */
  override fun accuracyRadiusBorderColor(accuracyRadiusBorderColor: String): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("accuracy-radius-border-color", accuracyRadiusBorderColor)
    setProperty(propertyValue)
  }

  /**
   * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
   *
   * This is an Expression representation of "accuracy-radius-border-color".
   *
   */
  val accuracyRadiusBorderColorAsExpression: Expression?
    /**
     * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
     *
     * Get the AccuracyRadiusBorderColor property as an Expression
     *
     * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusBorderColorAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("accuracy-radius-border-color")?.let {
        return it
      }
      return null
    }

  /**
   * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
   *
   * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusBorderColorAsExpression] to set the default property.
   *
   * @param accuracyRadiusBorderColor value of accuracyRadiusBorderColor as Expression
   */
  override fun accuracyRadiusBorderColor(accuracyRadiusBorderColor: Expression): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("accuracy-radius-border-color", accuracyRadiusBorderColor)
    setProperty(propertyValue)
  }

  /**
   * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
   */
  val accuracyRadiusBorderColorAsColorInt: Int?
    /**
     * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
     *
     * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusBorderColorAsColorInt] to get the default property.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      accuracyRadiusBorderColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
   *
   * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusBorderColorAsColorInt] to set the default property.
   *
   * @param accuracyRadiusBorderColor value of accuracyRadiusBorderColor
   */
  override fun accuracyRadiusBorderColor(@ColorInt accuracyRadiusBorderColor: Int): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("accuracy-radius-border-color", colorIntToRgbaExpression(accuracyRadiusBorderColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for AccuracyRadiusBorderColor.
   */
  val accuracyRadiusBorderColorTransition: StyleTransition?
    /**
     * Get the AccuracyRadiusBorderColor property transition options
     *
     * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusBorderColorTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("accuracy-radius-border-color-transition")
    }

  /**
   * Set the AccuracyRadiusBorderColor property transition options
   *
   * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusBorderColorTransition] to set the default property.
   *
   * @param options transition options for String
   */
  override fun accuracyRadiusBorderColorTransition(options: StyleTransition): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("accuracy-radius-border-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [accuracyRadiusBorderColorTransition].
   */
  override fun accuracyRadiusBorderColorTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer = apply {
    accuracyRadiusBorderColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
   */
  val accuracyRadiusColor: String?
    /**
     * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
     *
     * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusColor] to get the default property.
     *
     * @return String
     */
    get() {
      accuracyRadiusColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
   *
   * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusColor] to set the default property.
   *
   * @param accuracyRadiusColor value of accuracyRadiusColor
   */
  override fun accuracyRadiusColor(accuracyRadiusColor: String): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("accuracy-radius-color", accuracyRadiusColor)
    setProperty(propertyValue)
  }

  /**
   * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
   *
   * This is an Expression representation of "accuracy-radius-color".
   *
   */
  val accuracyRadiusColorAsExpression: Expression?
    /**
     * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
     *
     * Get the AccuracyRadiusColor property as an Expression
     *
     * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusColorAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("accuracy-radius-color")?.let {
        return it
      }
      return null
    }

  /**
   * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
   *
   * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusColorAsExpression] to set the default property.
   *
   * @param accuracyRadiusColor value of accuracyRadiusColor as Expression
   */
  override fun accuracyRadiusColor(accuracyRadiusColor: Expression): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("accuracy-radius-color", accuracyRadiusColor)
    setProperty(propertyValue)
  }

  /**
   * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
   */
  val accuracyRadiusColorAsColorInt: Int?
    /**
     * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
     *
     * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusColorAsColorInt] to get the default property.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      accuracyRadiusColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
   *
   * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusColorAsColorInt] to set the default property.
   *
   * @param accuracyRadiusColor value of accuracyRadiusColor
   */
  override fun accuracyRadiusColor(@ColorInt accuracyRadiusColor: Int): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("accuracy-radius-color", colorIntToRgbaExpression(accuracyRadiusColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for AccuracyRadiusColor.
   */
  val accuracyRadiusColorTransition: StyleTransition?
    /**
     * Get the AccuracyRadiusColor property transition options
     *
     * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusColorTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("accuracy-radius-color-transition")
    }

  /**
   * Set the AccuracyRadiusColor property transition options
   *
   * Use static method [LocationIndicatorLayer.defaultAccuracyRadiusColorTransition] to set the default property.
   *
   * @param options transition options for String
   */
  override fun accuracyRadiusColorTransition(options: StyleTransition): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("accuracy-radius-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [accuracyRadiusColorTransition].
   */
  override fun accuracyRadiusColorTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer = apply {
    accuracyRadiusColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The bearing of the location indicator.
   */
  val bearing: Double?
    /**
     * The bearing of the location indicator.
     *
     * Use static method [LocationIndicatorLayer.defaultBearing] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("bearing")
    }

  /**
   * The bearing of the location indicator.
   *
   * Use static method [LocationIndicatorLayer.defaultBearing] to set the default property.
   *
   * @param bearing value of bearing
   */
  override fun bearing(bearing: Double): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("bearing", bearing)
    setProperty(propertyValue)
  }

  /**
   * The bearing of the location indicator.
   *
   * This is an Expression representation of "bearing".
   *
   */
  val bearingAsExpression: Expression?
    /**
     * The bearing of the location indicator.
     *
     * Get the Bearing property as an Expression
     *
     * Use static method [LocationIndicatorLayer.defaultBearingAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("bearing")?.let {
        return it
      }
      bearing?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The bearing of the location indicator.
   *
   * Use static method [LocationIndicatorLayer.defaultBearingAsExpression] to set the default property.
   *
   * @param bearing value of bearing as Expression
   */
  override fun bearing(bearing: Expression): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("bearing", bearing)
    setProperty(propertyValue)
  }

  /**
   * Transition options for Bearing.
   */
  val bearingTransition: StyleTransition?
    /**
     * Get the Bearing property transition options
     *
     * Use static method [LocationIndicatorLayer.defaultBearingTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("bearing-transition")
    }

  /**
   * Set the Bearing property transition options
   *
   * Use static method [LocationIndicatorLayer.defaultBearingTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun bearingTransition(options: StyleTransition): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("bearing-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [bearingTransition].
   */
  override fun bearingTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer = apply {
    bearingTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The size of the bearing image, as a scale factor applied to the size of the specified image.
   */
  val bearingImageSize: Double?
    /**
     * The size of the bearing image, as a scale factor applied to the size of the specified image.
     *
     * Use static method [LocationIndicatorLayer.defaultBearingImageSize] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("bearing-image-size")
    }

  /**
   * The size of the bearing image, as a scale factor applied to the size of the specified image.
   *
   * Use static method [LocationIndicatorLayer.defaultBearingImageSize] to set the default property.
   *
   * @param bearingImageSize value of bearingImageSize
   */
  override fun bearingImageSize(bearingImageSize: Double): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("bearing-image-size", bearingImageSize)
    setProperty(propertyValue)
  }

  /**
   * The size of the bearing image, as a scale factor applied to the size of the specified image.
   *
   * This is an Expression representation of "bearing-image-size".
   *
   */
  val bearingImageSizeAsExpression: Expression?
    /**
     * The size of the bearing image, as a scale factor applied to the size of the specified image.
     *
     * Get the BearingImageSize property as an Expression
     *
     * Use static method [LocationIndicatorLayer.defaultBearingImageSizeAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("bearing-image-size")?.let {
        return it
      }
      bearingImageSize?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The size of the bearing image, as a scale factor applied to the size of the specified image.
   *
   * Use static method [LocationIndicatorLayer.defaultBearingImageSizeAsExpression] to set the default property.
   *
   * @param bearingImageSize value of bearingImageSize as Expression
   */
  override fun bearingImageSize(bearingImageSize: Expression): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("bearing-image-size", bearingImageSize)
    setProperty(propertyValue)
  }

  /**
   * Transition options for BearingImageSize.
   */
  val bearingImageSizeTransition: StyleTransition?
    /**
     * Get the BearingImageSize property transition options
     *
     * Use static method [LocationIndicatorLayer.defaultBearingImageSizeTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("bearing-image-size-transition")
    }

  /**
   * Set the BearingImageSize property transition options
   *
   * Use static method [LocationIndicatorLayer.defaultBearingImageSizeTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun bearingImageSizeTransition(options: StyleTransition): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("bearing-image-size-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [bearingImageSizeTransition].
   */
  override fun bearingImageSizeTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer = apply {
    bearingImageSizeTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
   */
  val emphasisCircleColor: String?
    /**
     * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
     *
     * Use static method [LocationIndicatorLayer.defaultEmphasisCircleColor] to get the default property.
     *
     * @return String
     */
    get() {
      emphasisCircleColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
   *
   * Use static method [LocationIndicatorLayer.defaultEmphasisCircleColor] to set the default property.
   *
   * @param emphasisCircleColor value of emphasisCircleColor
   */
  override fun emphasisCircleColor(emphasisCircleColor: String): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("emphasis-circle-color", emphasisCircleColor)
    setProperty(propertyValue)
  }

  /**
   * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
   *
   * This is an Expression representation of "emphasis-circle-color".
   *
   */
  val emphasisCircleColorAsExpression: Expression?
    /**
     * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
     *
     * Get the EmphasisCircleColor property as an Expression
     *
     * Use static method [LocationIndicatorLayer.defaultEmphasisCircleColorAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("emphasis-circle-color")?.let {
        return it
      }
      return null
    }

  /**
   * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
   *
   * Use static method [LocationIndicatorLayer.defaultEmphasisCircleColorAsExpression] to set the default property.
   *
   * @param emphasisCircleColor value of emphasisCircleColor as Expression
   */
  override fun emphasisCircleColor(emphasisCircleColor: Expression): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("emphasis-circle-color", emphasisCircleColor)
    setProperty(propertyValue)
  }

  /**
   * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
   */
  val emphasisCircleColorAsColorInt: Int?
    /**
     * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
     *
     * Use static method [LocationIndicatorLayer.defaultEmphasisCircleColorAsColorInt] to get the default property.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      emphasisCircleColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
   *
   * Use static method [LocationIndicatorLayer.defaultEmphasisCircleColorAsColorInt] to set the default property.
   *
   * @param emphasisCircleColor value of emphasisCircleColor
   */
  override fun emphasisCircleColor(@ColorInt emphasisCircleColor: Int): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("emphasis-circle-color", colorIntToRgbaExpression(emphasisCircleColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for EmphasisCircleColor.
   */
  val emphasisCircleColorTransition: StyleTransition?
    /**
     * Get the EmphasisCircleColor property transition options
     *
     * Use static method [LocationIndicatorLayer.defaultEmphasisCircleColorTransition] to get the default property.
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("emphasis-circle-color-transition")
    }

  /**
   * Set the EmphasisCircleColor property transition options
   *
   * Use static method [LocationIndicatorLayer.defaultEmphasisCircleColorTransition] to set the default property.
   *
   * @param options transition options for String
   */
  override fun emphasisCircleColorTransition(options: StyleTransition): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("emphasis-circle-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [emphasisCircleColorTransition].
   */
  override fun emphasisCircleColorTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer = apply {
    emphasisCircleColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
   */
  val emphasisCircleRadius: Double?
    /**
     * The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
     *
     * Use static method [LocationIndicatorLayer.defaultEmphasisCircleRadius] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("emphasis-circle-radius")
    }

  /**
   * The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
   *
   * Use static method [LocationIndicatorLayer.defaultEmphasisCircleRadius] to set the default property.
   *
   * @param emphasisCircleRadius value of emphasisCircleRadius
   */
  override fun emphasisCircleRadius(emphasisCircleRadius: Double): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("emphasis-circle-radius", emphasisCircleRadius)
    setProperty(propertyValue)
  }

  /**
   * The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
   *
   * This is an Expression representation of "emphasis-circle-radius".
   *
   */
  val emphasisCircleRadiusAsExpression: Expression?
    /**
     * The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
     *
     * Get the EmphasisCircleRadius property as an Expression
     *
     * Use static method [LocationIndicatorLayer.defaultEmphasisCircleRadiusAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("emphasis-circle-radius")?.let {
        return it
      }
      emphasisCircleRadius?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
   *
   * Use static method [LocationIndicatorLayer.defaultEmphasisCircleRadiusAsExpression] to set the default property.
   *
   * @param emphasisCircleRadius value of emphasisCircleRadius as Expression
   */
  override fun emphasisCircleRadius(emphasisCircleRadius: Expression): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("emphasis-circle-radius", emphasisCircleRadius)
    setProperty(propertyValue)
  }

  /**
   * Transition options for EmphasisCircleRadius.
   */
  val emphasisCircleRadiusTransition: StyleTransition?
    /**
     * Get the EmphasisCircleRadius property transition options
     *
     * Use static method [LocationIndicatorLayer.defaultEmphasisCircleRadiusTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("emphasis-circle-radius-transition")
    }

  /**
   * Set the EmphasisCircleRadius property transition options
   *
   * Use static method [LocationIndicatorLayer.defaultEmphasisCircleRadiusTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun emphasisCircleRadiusTransition(options: StyleTransition): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("emphasis-circle-radius-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [emphasisCircleRadiusTransition].
   */
  override fun emphasisCircleRadiusTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer = apply {
    emphasisCircleRadiusTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The displacement off the center of the top image and the shadow image when the pitch of the map is greater than 0. This helps producing a three-dimensional appearence.
   */
  val imagePitchDisplacement: Double?
    /**
     * The displacement off the center of the top image and the shadow image when the pitch of the map is greater than 0. This helps producing a three-dimensional appearence.
     *
     * Use static method [LocationIndicatorLayer.defaultImagePitchDisplacement] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("image-pitch-displacement")
    }

  /**
   * The displacement off the center of the top image and the shadow image when the pitch of the map is greater than 0. This helps producing a three-dimensional appearence.
   *
   * Use static method [LocationIndicatorLayer.defaultImagePitchDisplacement] to set the default property.
   *
   * @param imagePitchDisplacement value of imagePitchDisplacement
   */
  override fun imagePitchDisplacement(imagePitchDisplacement: Double): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("image-pitch-displacement", imagePitchDisplacement)
    setProperty(propertyValue)
  }

  /**
   * The displacement off the center of the top image and the shadow image when the pitch of the map is greater than 0. This helps producing a three-dimensional appearence.
   *
   * This is an Expression representation of "image-pitch-displacement".
   *
   */
  val imagePitchDisplacementAsExpression: Expression?
    /**
     * The displacement off the center of the top image and the shadow image when the pitch of the map is greater than 0. This helps producing a three-dimensional appearence.
     *
     * Get the ImagePitchDisplacement property as an Expression
     *
     * Use static method [LocationIndicatorLayer.defaultImagePitchDisplacementAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("image-pitch-displacement")?.let {
        return it
      }
      imagePitchDisplacement?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The displacement off the center of the top image and the shadow image when the pitch of the map is greater than 0. This helps producing a three-dimensional appearence.
   *
   * Use static method [LocationIndicatorLayer.defaultImagePitchDisplacementAsExpression] to set the default property.
   *
   * @param imagePitchDisplacement value of imagePitchDisplacement as Expression
   */
  override fun imagePitchDisplacement(imagePitchDisplacement: Expression): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("image-pitch-displacement", imagePitchDisplacement)
    setProperty(propertyValue)
  }

  /**
   * An array of [latitude, longitude, altitude] position of the location indicator.
   */
  val location: List<Double>?
    /**
     * An array of [latitude, longitude, altitude] position of the location indicator.
     *
     * Use static method [LocationIndicatorLayer.defaultLocation] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("location")
    }

  /**
   * An array of [latitude, longitude, altitude] position of the location indicator.
   *
   * Use static method [LocationIndicatorLayer.defaultLocation] to set the default property.
   *
   * @param location value of location
   */
  override fun location(location: List<Double>): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("location", location)
    setProperty(propertyValue)
  }

  /**
   * An array of [latitude, longitude, altitude] position of the location indicator.
   *
   * This is an Expression representation of "location".
   *
   */
  val locationAsExpression: Expression?
    /**
     * An array of [latitude, longitude, altitude] position of the location indicator.
     *
     * Get the Location property as an Expression
     *
     * Use static method [LocationIndicatorLayer.defaultLocationAsExpression] to get the default property.
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("location")?.let {
        return it
      }
      location?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * An array of [latitude, longitude, altitude] position of the location indicator.
   *
   * Use static method [LocationIndicatorLayer.defaultLocationAsExpression] to set the default property.
   *
   * @param location value of location as Expression
   */
  override fun location(location: Expression): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("location", location)
    setProperty(propertyValue)
  }

  /**
   * Transition options for Location.
   */
  val locationTransition: StyleTransition?
    /**
     * Get the Location property transition options
     *
     * Use static method [LocationIndicatorLayer.defaultLocationTransition] to get the default property.
     *
     * @return transition options for List<Double>
     */
    get() {
      return getPropertyValue("location-transition")
    }

  /**
   * Set the Location property transition options
   *
   * Use static method [LocationIndicatorLayer.defaultLocationTransition] to set the default property.
   *
   * @param options transition options for List<Double>
   */
  override fun locationTransition(options: StyleTransition): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("location-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [locationTransition].
   */
  override fun locationTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer = apply {
    locationTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity of the entire location indicator layer.
   */
  val locationIndicatorOpacity: Double?
    /**
     * The opacity of the entire location indicator layer.
     *
     * Use static method [LocationIndicatorLayer.defaultLocationIndicatorOpacity] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("location-indicator-opacity")
    }

  /**
   * The opacity of the entire location indicator layer.
   *
   * Use static method [LocationIndicatorLayer.defaultLocationIndicatorOpacity] to set the default property.
   *
   * @param locationIndicatorOpacity value of locationIndicatorOpacity
   */
  override fun locationIndicatorOpacity(locationIndicatorOpacity: Double): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("location-indicator-opacity", locationIndicatorOpacity)
    setProperty(propertyValue)
  }

  /**
   * The opacity of the entire location indicator layer.
   *
   * This is an Expression representation of "location-indicator-opacity".
   *
   */
  val locationIndicatorOpacityAsExpression: Expression?
    /**
     * The opacity of the entire location indicator layer.
     *
     * Get the LocationIndicatorOpacity property as an Expression
     *
     * Use static method [LocationIndicatorLayer.defaultLocationIndicatorOpacityAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("location-indicator-opacity")?.let {
        return it
      }
      locationIndicatorOpacity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The opacity of the entire location indicator layer.
   *
   * Use static method [LocationIndicatorLayer.defaultLocationIndicatorOpacityAsExpression] to set the default property.
   *
   * @param locationIndicatorOpacity value of locationIndicatorOpacity as Expression
   */
  override fun locationIndicatorOpacity(locationIndicatorOpacity: Expression): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("location-indicator-opacity", locationIndicatorOpacity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for LocationIndicatorOpacity.
   */
  val locationIndicatorOpacityTransition: StyleTransition?
    /**
     * Get the LocationIndicatorOpacity property transition options
     *
     * Use static method [LocationIndicatorLayer.defaultLocationIndicatorOpacityTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("location-indicator-opacity-transition")
    }

  /**
   * Set the LocationIndicatorOpacity property transition options
   *
   * Use static method [LocationIndicatorLayer.defaultLocationIndicatorOpacityTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun locationIndicatorOpacityTransition(options: StyleTransition): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("location-indicator-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [locationIndicatorOpacityTransition].
   */
  override fun locationIndicatorOpacityTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer = apply {
    locationIndicatorOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection.
   */
  val perspectiveCompensation: Double?
    /**
     * The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection.
     *
     * Use static method [LocationIndicatorLayer.defaultPerspectiveCompensation] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("perspective-compensation")
    }

  /**
   * The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection.
   *
   * Use static method [LocationIndicatorLayer.defaultPerspectiveCompensation] to set the default property.
   *
   * @param perspectiveCompensation value of perspectiveCompensation
   */
  override fun perspectiveCompensation(perspectiveCompensation: Double): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("perspective-compensation", perspectiveCompensation)
    setProperty(propertyValue)
  }

  /**
   * The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection.
   *
   * This is an Expression representation of "perspective-compensation".
   *
   */
  val perspectiveCompensationAsExpression: Expression?
    /**
     * The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection.
     *
     * Get the PerspectiveCompensation property as an Expression
     *
     * Use static method [LocationIndicatorLayer.defaultPerspectiveCompensationAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("perspective-compensation")?.let {
        return it
      }
      perspectiveCompensation?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection.
   *
   * Use static method [LocationIndicatorLayer.defaultPerspectiveCompensationAsExpression] to set the default property.
   *
   * @param perspectiveCompensation value of perspectiveCompensation as Expression
   */
  override fun perspectiveCompensation(perspectiveCompensation: Expression): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("perspective-compensation", perspectiveCompensation)
    setProperty(propertyValue)
  }

  /**
   * The size of the shadow image, as a scale factor applied to the size of the specified image.
   */
  val shadowImageSize: Double?
    /**
     * The size of the shadow image, as a scale factor applied to the size of the specified image.
     *
     * Use static method [LocationIndicatorLayer.defaultShadowImageSize] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("shadow-image-size")
    }

  /**
   * The size of the shadow image, as a scale factor applied to the size of the specified image.
   *
   * Use static method [LocationIndicatorLayer.defaultShadowImageSize] to set the default property.
   *
   * @param shadowImageSize value of shadowImageSize
   */
  override fun shadowImageSize(shadowImageSize: Double): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("shadow-image-size", shadowImageSize)
    setProperty(propertyValue)
  }

  /**
   * The size of the shadow image, as a scale factor applied to the size of the specified image.
   *
   * This is an Expression representation of "shadow-image-size".
   *
   */
  val shadowImageSizeAsExpression: Expression?
    /**
     * The size of the shadow image, as a scale factor applied to the size of the specified image.
     *
     * Get the ShadowImageSize property as an Expression
     *
     * Use static method [LocationIndicatorLayer.defaultShadowImageSizeAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("shadow-image-size")?.let {
        return it
      }
      shadowImageSize?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The size of the shadow image, as a scale factor applied to the size of the specified image.
   *
   * Use static method [LocationIndicatorLayer.defaultShadowImageSizeAsExpression] to set the default property.
   *
   * @param shadowImageSize value of shadowImageSize as Expression
   */
  override fun shadowImageSize(shadowImageSize: Expression): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("shadow-image-size", shadowImageSize)
    setProperty(propertyValue)
  }

  /**
   * Transition options for ShadowImageSize.
   */
  val shadowImageSizeTransition: StyleTransition?
    /**
     * Get the ShadowImageSize property transition options
     *
     * Use static method [LocationIndicatorLayer.defaultShadowImageSizeTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("shadow-image-size-transition")
    }

  /**
   * Set the ShadowImageSize property transition options
   *
   * Use static method [LocationIndicatorLayer.defaultShadowImageSizeTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun shadowImageSizeTransition(options: StyleTransition): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("shadow-image-size-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [shadowImageSizeTransition].
   */
  override fun shadowImageSizeTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer = apply {
    shadowImageSizeTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The size of the top image, as a scale factor applied to the size of the specified image.
   */
  val topImageSize: Double?
    /**
     * The size of the top image, as a scale factor applied to the size of the specified image.
     *
     * Use static method [LocationIndicatorLayer.defaultTopImageSize] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("top-image-size")
    }

  /**
   * The size of the top image, as a scale factor applied to the size of the specified image.
   *
   * Use static method [LocationIndicatorLayer.defaultTopImageSize] to set the default property.
   *
   * @param topImageSize value of topImageSize
   */
  override fun topImageSize(topImageSize: Double): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("top-image-size", topImageSize)
    setProperty(propertyValue)
  }

  /**
   * The size of the top image, as a scale factor applied to the size of the specified image.
   *
   * This is an Expression representation of "top-image-size".
   *
   */
  val topImageSizeAsExpression: Expression?
    /**
     * The size of the top image, as a scale factor applied to the size of the specified image.
     *
     * Get the TopImageSize property as an Expression
     *
     * Use static method [LocationIndicatorLayer.defaultTopImageSizeAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("top-image-size")?.let {
        return it
      }
      topImageSize?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The size of the top image, as a scale factor applied to the size of the specified image.
   *
   * Use static method [LocationIndicatorLayer.defaultTopImageSizeAsExpression] to set the default property.
   *
   * @param topImageSize value of topImageSize as Expression
   */
  override fun topImageSize(topImageSize: Expression): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("top-image-size", topImageSize)
    setProperty(propertyValue)
  }

  /**
   * Transition options for TopImageSize.
   */
  val topImageSizeTransition: StyleTransition?
    /**
     * Get the TopImageSize property transition options
     *
     * Use static method [LocationIndicatorLayer.defaultTopImageSizeTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("top-image-size-transition")
    }

  /**
   * Set the TopImageSize property transition options
   *
   * Use static method [LocationIndicatorLayer.defaultTopImageSizeTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  override fun topImageSizeTransition(options: StyleTransition): LocationIndicatorLayer = apply {
    val propertyValue = PropertyValue("top-image-size-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [topImageSizeTransition].
   */
  override fun topImageSizeTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer = apply {
    topImageSizeTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Get the type of this layer
   *
   * @return Type of the layer as [String]
   */
  override fun getType(): String {
    return "location-indicator"
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
        StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "visibility").silentUnwrap<String>()?.let {
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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "minzoom").silentUnwrap()

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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "maxzoom").silentUnwrap()

    /**
     * Name of image in sprite to use as the middle of the location indicator.
     */
    val defaultBearingImage: String?
      /**
       * Name of image in sprite to use as the middle of the location indicator.
       *
       * Get the default value of BearingImage property
       *
       * @return String
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing-image").silentUnwrap()
      }

    /**
     * Name of image in sprite to use as the middle of the location indicator.
     *
     * This is an Expression representation of "bearing-image".
     *
     */
    val defaultBearingImageAsExpression: Expression?
      /**
       * Get default value of the BearingImage property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing-image").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultBearingImage?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Name of image in sprite to use as the background of the location indicator.
     */
    val defaultShadowImage: String?
      /**
       * Name of image in sprite to use as the background of the location indicator.
       *
       * Get the default value of ShadowImage property
       *
       * @return String
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "shadow-image").silentUnwrap()
      }

    /**
     * Name of image in sprite to use as the background of the location indicator.
     *
     * This is an Expression representation of "shadow-image".
     *
     */
    val defaultShadowImageAsExpression: Expression?
      /**
       * Get default value of the ShadowImage property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "shadow-image").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultShadowImage?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Name of image in sprite to use as the top of the location indicator.
     */
    val defaultTopImage: String?
      /**
       * Name of image in sprite to use as the top of the location indicator.
       *
       * Get the default value of TopImage property
       *
       * @return String
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "top-image").silentUnwrap()
      }

    /**
     * Name of image in sprite to use as the top of the location indicator.
     *
     * This is an Expression representation of "top-image".
     *
     */
    val defaultTopImageAsExpression: Expression?
      /**
       * Get default value of the TopImage property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "top-image").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTopImage?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * The accuracy, in meters, of the position source used to retrieve the position of the location indicator.
     */
    val defaultAccuracyRadius: Double?
      /**
       * The accuracy, in meters, of the position source used to retrieve the position of the location indicator.
       *
       * Get the default value of AccuracyRadius property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius").silentUnwrap()
      }

    /**
     * The accuracy, in meters, of the position source used to retrieve the position of the location indicator.
     *
     * This is an Expression representation of "accuracy-radius".
     *
     */
    val defaultAccuracyRadiusAsExpression: Expression?
      /**
       * Get default value of the AccuracyRadius property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultAccuracyRadius?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for AccuracyRadius.
     */
    val defaultAccuracyRadiusTransition: StyleTransition?
      /**
       * Get the AccuracyRadius property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius-transition").silentUnwrap()

    /**
     * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
     */
    val defaultAccuracyRadiusBorderColor: String?
      /**
       * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
       *
       * Get the default value of AccuracyRadiusBorderColor property
       *
       * @return String
       */
      get() {
        defaultAccuracyRadiusBorderColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
     *
     * This is an Expression representation of "accuracy-radius-border-color".
     *
     */
    val defaultAccuracyRadiusBorderColorAsExpression: Expression?
      /**
       * Get default value of the AccuracyRadiusBorderColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius-border-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
     */
    val defaultAccuracyRadiusBorderColorAsColorInt: Int?
      /**
       * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
       *
       * Get the default value of AccuracyRadiusBorderColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultAccuracyRadiusBorderColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for AccuracyRadiusBorderColor.
     */
    val defaultAccuracyRadiusBorderColorTransition: StyleTransition?
      /**
       * Get the AccuracyRadiusBorderColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius-border-color-transition").silentUnwrap()

    /**
     * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
     */
    val defaultAccuracyRadiusColor: String?
      /**
       * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
       *
       * Get the default value of AccuracyRadiusColor property
       *
       * @return String
       */
      get() {
        defaultAccuracyRadiusColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
     *
     * This is an Expression representation of "accuracy-radius-color".
     *
     */
    val defaultAccuracyRadiusColorAsExpression: Expression?
      /**
       * Get default value of the AccuracyRadiusColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
     */
    val defaultAccuracyRadiusColorAsColorInt: Int?
      /**
       * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
       *
       * Get the default value of AccuracyRadiusColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultAccuracyRadiusColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for AccuracyRadiusColor.
     */
    val defaultAccuracyRadiusColorTransition: StyleTransition?
      /**
       * Get the AccuracyRadiusColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius-color-transition").silentUnwrap()

    /**
     * The bearing of the location indicator.
     */
    val defaultBearing: Double?
      /**
       * The bearing of the location indicator.
       *
       * Get the default value of Bearing property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing").silentUnwrap()
      }

    /**
     * The bearing of the location indicator.
     *
     * This is an Expression representation of "bearing".
     *
     */
    val defaultBearingAsExpression: Expression?
      /**
       * Get default value of the Bearing property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultBearing?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for Bearing.
     */
    val defaultBearingTransition: StyleTransition?
      /**
       * Get the Bearing property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing-transition").silentUnwrap()

    /**
     * The size of the bearing image, as a scale factor applied to the size of the specified image.
     */
    val defaultBearingImageSize: Double?
      /**
       * The size of the bearing image, as a scale factor applied to the size of the specified image.
       *
       * Get the default value of BearingImageSize property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing-image-size").silentUnwrap()
      }

    /**
     * The size of the bearing image, as a scale factor applied to the size of the specified image.
     *
     * This is an Expression representation of "bearing-image-size".
     *
     */
    val defaultBearingImageSizeAsExpression: Expression?
      /**
       * Get default value of the BearingImageSize property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing-image-size").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultBearingImageSize?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for BearingImageSize.
     */
    val defaultBearingImageSizeTransition: StyleTransition?
      /**
       * Get the BearingImageSize property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing-image-size-transition").silentUnwrap()

    /**
     * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
     */
    val defaultEmphasisCircleColor: String?
      /**
       * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
       *
       * Get the default value of EmphasisCircleColor property
       *
       * @return String
       */
      get() {
        defaultEmphasisCircleColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
     *
     * This is an Expression representation of "emphasis-circle-color".
     *
     */
    val defaultEmphasisCircleColorAsExpression: Expression?
      /**
       * Get default value of the EmphasisCircleColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "emphasis-circle-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
     */
    val defaultEmphasisCircleColorAsColorInt: Int?
      /**
       * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
       *
       * Get the default value of EmphasisCircleColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultEmphasisCircleColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for EmphasisCircleColor.
     */
    val defaultEmphasisCircleColorTransition: StyleTransition?
      /**
       * Get the EmphasisCircleColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "emphasis-circle-color-transition").silentUnwrap()

    /**
     * The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
     */
    val defaultEmphasisCircleRadius: Double?
      /**
       * The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
       *
       * Get the default value of EmphasisCircleRadius property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "emphasis-circle-radius").silentUnwrap()
      }

    /**
     * The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
     *
     * This is an Expression representation of "emphasis-circle-radius".
     *
     */
    val defaultEmphasisCircleRadiusAsExpression: Expression?
      /**
       * Get default value of the EmphasisCircleRadius property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "emphasis-circle-radius").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultEmphasisCircleRadius?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for EmphasisCircleRadius.
     */
    val defaultEmphasisCircleRadiusTransition: StyleTransition?
      /**
       * Get the EmphasisCircleRadius property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "emphasis-circle-radius-transition").silentUnwrap()

    /**
     * The displacement off the center of the top image and the shadow image when the pitch of the map is greater than 0. This helps producing a three-dimensional appearence.
     */
    val defaultImagePitchDisplacement: Double?
      /**
       * The displacement off the center of the top image and the shadow image when the pitch of the map is greater than 0. This helps producing a three-dimensional appearence.
       *
       * Get the default value of ImagePitchDisplacement property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "image-pitch-displacement").silentUnwrap()
      }

    /**
     * The displacement off the center of the top image and the shadow image when the pitch of the map is greater than 0. This helps producing a three-dimensional appearence.
     *
     * This is an Expression representation of "image-pitch-displacement".
     *
     */
    val defaultImagePitchDisplacementAsExpression: Expression?
      /**
       * Get default value of the ImagePitchDisplacement property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "image-pitch-displacement").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultImagePitchDisplacement?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * An array of [latitude, longitude, altitude] position of the location indicator.
     */
    val defaultLocation: List<Double>?
      /**
       * An array of [latitude, longitude, altitude] position of the location indicator.
       *
       * Get the default value of Location property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "location").silentUnwrap()
      }

    /**
     * An array of [latitude, longitude, altitude] position of the location indicator.
     *
     * This is an Expression representation of "location".
     *
     */
    val defaultLocationAsExpression: Expression?
      /**
       * Get default value of the Location property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "location").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLocation?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for Location.
     */
    val defaultLocationTransition: StyleTransition?
      /**
       * Get the Location property transition options
       *
       * @return transition options for List<Double>
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "location-transition").silentUnwrap()

    /**
     * The opacity of the entire location indicator layer.
     */
    val defaultLocationIndicatorOpacity: Double?
      /**
       * The opacity of the entire location indicator layer.
       *
       * Get the default value of LocationIndicatorOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "location-indicator-opacity").silentUnwrap()
      }

    /**
     * The opacity of the entire location indicator layer.
     *
     * This is an Expression representation of "location-indicator-opacity".
     *
     */
    val defaultLocationIndicatorOpacityAsExpression: Expression?
      /**
       * Get default value of the LocationIndicatorOpacity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "location-indicator-opacity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultLocationIndicatorOpacity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for LocationIndicatorOpacity.
     */
    val defaultLocationIndicatorOpacityTransition: StyleTransition?
      /**
       * Get the LocationIndicatorOpacity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "location-indicator-opacity-transition").silentUnwrap()

    /**
     * The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection.
     */
    val defaultPerspectiveCompensation: Double?
      /**
       * The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection.
       *
       * Get the default value of PerspectiveCompensation property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "perspective-compensation").silentUnwrap()
      }

    /**
     * The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection.
     *
     * This is an Expression representation of "perspective-compensation".
     *
     */
    val defaultPerspectiveCompensationAsExpression: Expression?
      /**
       * Get default value of the PerspectiveCompensation property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "perspective-compensation").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultPerspectiveCompensation?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * The size of the shadow image, as a scale factor applied to the size of the specified image.
     */
    val defaultShadowImageSize: Double?
      /**
       * The size of the shadow image, as a scale factor applied to the size of the specified image.
       *
       * Get the default value of ShadowImageSize property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "shadow-image-size").silentUnwrap()
      }

    /**
     * The size of the shadow image, as a scale factor applied to the size of the specified image.
     *
     * This is an Expression representation of "shadow-image-size".
     *
     */
    val defaultShadowImageSizeAsExpression: Expression?
      /**
       * Get default value of the ShadowImageSize property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "shadow-image-size").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultShadowImageSize?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for ShadowImageSize.
     */
    val defaultShadowImageSizeTransition: StyleTransition?
      /**
       * Get the ShadowImageSize property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "shadow-image-size-transition").silentUnwrap()

    /**
     * The size of the top image, as a scale factor applied to the size of the specified image.
     */
    val defaultTopImageSize: Double?
      /**
       * The size of the top image, as a scale factor applied to the size of the specified image.
       *
       * Get the default value of TopImageSize property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "top-image-size").silentUnwrap()
      }

    /**
     * The size of the top image, as a scale factor applied to the size of the specified image.
     *
     * This is an Expression representation of "top-image-size".
     *
     */
    val defaultTopImageSizeAsExpression: Expression?
      /**
       * Get default value of the TopImageSize property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "top-image-size").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTopImageSize?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for TopImageSize.
     */
    val defaultTopImageSizeTransition: StyleTransition?
      /**
       * Get the TopImageSize property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "top-image-size-transition").silentUnwrap()
  }
}

/**
 * This Interface contains all the functions that will be exposed to Koltin DSL.
 *
 * Separated the DSL receiver class to this interface to avoid IDE code suggestion for
 * property getters.
 */
@LayersDsl
interface LocationIndicatorLayerDsl {

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): LocationIndicatorLayer

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility as Expression
   */
  fun visibility(visibility: Expression): LocationIndicatorLayer

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param minZoom value of minzoom
   */
  fun minZoom(minZoom: Double): LocationIndicatorLayer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param maxZoom value of maxzoom
   */
  fun maxZoom(maxZoom: Double): LocationIndicatorLayer

  // Property getters and setters

  /**
   * Name of image in sprite to use as the middle of the location indicator.
   *
   * @param bearingImage value of bearingImage
   */
  fun bearingImage(bearingImage: String): LocationIndicatorLayer

  /**
   * Name of image in sprite to use as the middle of the location indicator.
   *
   * @param bearingImage value of bearingImage as Expression
   */
  fun bearingImage(bearingImage: Expression): LocationIndicatorLayer

  /**
   * Name of image in sprite to use as the background of the location indicator.
   *
   * @param shadowImage value of shadowImage
   */
  fun shadowImage(shadowImage: String): LocationIndicatorLayer

  /**
   * Name of image in sprite to use as the background of the location indicator.
   *
   * @param shadowImage value of shadowImage as Expression
   */
  fun shadowImage(shadowImage: Expression): LocationIndicatorLayer

  /**
   * Name of image in sprite to use as the top of the location indicator.
   *
   * @param topImage value of topImage
   */
  fun topImage(topImage: String): LocationIndicatorLayer

  /**
   * Name of image in sprite to use as the top of the location indicator.
   *
   * @param topImage value of topImage as Expression
   */
  fun topImage(topImage: Expression): LocationIndicatorLayer

  /**
   * The accuracy, in meters, of the position source used to retrieve the position of the location indicator.
   *
   * @param accuracyRadius value of accuracyRadius
   */
  fun accuracyRadius(accuracyRadius: Double = 0.0): LocationIndicatorLayer

  /**
   * The accuracy, in meters, of the position source used to retrieve the position of the location indicator.
   *
   * @param accuracyRadius value of accuracyRadius as Expression
   */
  fun accuracyRadius(accuracyRadius: Expression): LocationIndicatorLayer

  /**
   * The accuracy, in meters, of the position source used to retrieve the position of the location indicator.
   *
   * Set the AccuracyRadius property transition options
   *
   * @param options transition options for Double
   */
  fun accuracyRadiusTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * The accuracy, in meters, of the position source used to retrieve the position of the location indicator.
   *
   * DSL for [accuracyRadiusTransition].
   */
  fun accuracyRadiusTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer

  /**
   * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
   *
   * @param accuracyRadiusBorderColor value of accuracyRadiusBorderColor
   */
  fun accuracyRadiusBorderColor(accuracyRadiusBorderColor: String = "#ffffff"): LocationIndicatorLayer

  /**
   * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
   *
   * @param accuracyRadiusBorderColor value of accuracyRadiusBorderColor as Expression
   */
  fun accuracyRadiusBorderColor(accuracyRadiusBorderColor: Expression): LocationIndicatorLayer

  /**
   * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
   *
   * @param accuracyRadiusBorderColor value of accuracyRadiusBorderColor
   */
  fun accuracyRadiusBorderColor(@ColorInt accuracyRadiusBorderColor: Int): LocationIndicatorLayer

  /**
   * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
   *
   * Set the AccuracyRadiusBorderColor property transition options
   *
   * @param options transition options for String
   */
  fun accuracyRadiusBorderColorTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
   *
   * DSL for [accuracyRadiusBorderColorTransition].
   */
  fun accuracyRadiusBorderColorTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer

  /**
   * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
   *
   * @param accuracyRadiusColor value of accuracyRadiusColor
   */
  fun accuracyRadiusColor(accuracyRadiusColor: String = "#ffffff"): LocationIndicatorLayer

  /**
   * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
   *
   * @param accuracyRadiusColor value of accuracyRadiusColor as Expression
   */
  fun accuracyRadiusColor(accuracyRadiusColor: Expression): LocationIndicatorLayer

  /**
   * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
   *
   * @param accuracyRadiusColor value of accuracyRadiusColor
   */
  fun accuracyRadiusColor(@ColorInt accuracyRadiusColor: Int): LocationIndicatorLayer

  /**
   * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
   *
   * Set the AccuracyRadiusColor property transition options
   *
   * @param options transition options for String
   */
  fun accuracyRadiusColorTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
   *
   * DSL for [accuracyRadiusColorTransition].
   */
  fun accuracyRadiusColorTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer

  /**
   * The bearing of the location indicator.
   *
   * @param bearing value of bearing
   */
  fun bearing(bearing: Double = 0.0): LocationIndicatorLayer

  /**
   * The bearing of the location indicator.
   *
   * @param bearing value of bearing as Expression
   */
  fun bearing(bearing: Expression): LocationIndicatorLayer

  /**
   * The bearing of the location indicator.
   *
   * Set the Bearing property transition options
   *
   * @param options transition options for Double
   */
  fun bearingTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * The bearing of the location indicator.
   *
   * DSL for [bearingTransition].
   */
  fun bearingTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer

  /**
   * The size of the bearing image, as a scale factor applied to the size of the specified image.
   *
   * @param bearingImageSize value of bearingImageSize
   */
  fun bearingImageSize(bearingImageSize: Double = 1.0): LocationIndicatorLayer

  /**
   * The size of the bearing image, as a scale factor applied to the size of the specified image.
   *
   * @param bearingImageSize value of bearingImageSize as Expression
   */
  fun bearingImageSize(bearingImageSize: Expression): LocationIndicatorLayer

  /**
   * The size of the bearing image, as a scale factor applied to the size of the specified image.
   *
   * Set the BearingImageSize property transition options
   *
   * @param options transition options for Double
   */
  fun bearingImageSizeTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * The size of the bearing image, as a scale factor applied to the size of the specified image.
   *
   * DSL for [bearingImageSizeTransition].
   */
  fun bearingImageSizeTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer

  /**
   * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
   *
   * @param emphasisCircleColor value of emphasisCircleColor
   */
  fun emphasisCircleColor(emphasisCircleColor: String = "#ffffff"): LocationIndicatorLayer

  /**
   * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
   *
   * @param emphasisCircleColor value of emphasisCircleColor as Expression
   */
  fun emphasisCircleColor(emphasisCircleColor: Expression): LocationIndicatorLayer

  /**
   * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
   *
   * @param emphasisCircleColor value of emphasisCircleColor
   */
  fun emphasisCircleColor(@ColorInt emphasisCircleColor: Int): LocationIndicatorLayer

  /**
   * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
   *
   * Set the EmphasisCircleColor property transition options
   *
   * @param options transition options for String
   */
  fun emphasisCircleColorTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
   *
   * DSL for [emphasisCircleColorTransition].
   */
  fun emphasisCircleColorTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer

  /**
   * The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
   *
   * @param emphasisCircleRadius value of emphasisCircleRadius
   */
  fun emphasisCircleRadius(emphasisCircleRadius: Double = 0.0): LocationIndicatorLayer

  /**
   * The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
   *
   * @param emphasisCircleRadius value of emphasisCircleRadius as Expression
   */
  fun emphasisCircleRadius(emphasisCircleRadius: Expression): LocationIndicatorLayer

  /**
   * The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
   *
   * Set the EmphasisCircleRadius property transition options
   *
   * @param options transition options for Double
   */
  fun emphasisCircleRadiusTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
   *
   * DSL for [emphasisCircleRadiusTransition].
   */
  fun emphasisCircleRadiusTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer

  /**
   * The displacement off the center of the top image and the shadow image when the pitch of the map is greater than 0. This helps producing a three-dimensional appearence.
   *
   * @param imagePitchDisplacement value of imagePitchDisplacement
   */
  fun imagePitchDisplacement(imagePitchDisplacement: Double = 0.0): LocationIndicatorLayer

  /**
   * The displacement off the center of the top image and the shadow image when the pitch of the map is greater than 0. This helps producing a three-dimensional appearence.
   *
   * @param imagePitchDisplacement value of imagePitchDisplacement as Expression
   */
  fun imagePitchDisplacement(imagePitchDisplacement: Expression): LocationIndicatorLayer

  /**
   * An array of [latitude, longitude, altitude] position of the location indicator.
   *
   * @param location value of location
   */
  fun location(location: List<Double> = listOf(0.0, 0.0, 0.0)): LocationIndicatorLayer

  /**
   * An array of [latitude, longitude, altitude] position of the location indicator.
   *
   * @param location value of location as Expression
   */
  fun location(location: Expression): LocationIndicatorLayer

  /**
   * An array of [latitude, longitude, altitude] position of the location indicator.
   *
   * Set the Location property transition options
   *
   * @param options transition options for List<Double>
   */
  fun locationTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * An array of [latitude, longitude, altitude] position of the location indicator.
   *
   * DSL for [locationTransition].
   */
  fun locationTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer

  /**
   * The opacity of the entire location indicator layer.
   *
   * @param locationIndicatorOpacity value of locationIndicatorOpacity
   */
  fun locationIndicatorOpacity(locationIndicatorOpacity: Double = 1.0): LocationIndicatorLayer

  /**
   * The opacity of the entire location indicator layer.
   *
   * @param locationIndicatorOpacity value of locationIndicatorOpacity as Expression
   */
  fun locationIndicatorOpacity(locationIndicatorOpacity: Expression): LocationIndicatorLayer

  /**
   * The opacity of the entire location indicator layer.
   *
   * Set the LocationIndicatorOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun locationIndicatorOpacityTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * The opacity of the entire location indicator layer.
   *
   * DSL for [locationIndicatorOpacityTransition].
   */
  fun locationIndicatorOpacityTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer

  /**
   * The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection.
   *
   * @param perspectiveCompensation value of perspectiveCompensation
   */
  fun perspectiveCompensation(perspectiveCompensation: Double = 0.85): LocationIndicatorLayer

  /**
   * The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection.
   *
   * @param perspectiveCompensation value of perspectiveCompensation as Expression
   */
  fun perspectiveCompensation(perspectiveCompensation: Expression): LocationIndicatorLayer

  /**
   * The size of the shadow image, as a scale factor applied to the size of the specified image.
   *
   * @param shadowImageSize value of shadowImageSize
   */
  fun shadowImageSize(shadowImageSize: Double = 1.0): LocationIndicatorLayer

  /**
   * The size of the shadow image, as a scale factor applied to the size of the specified image.
   *
   * @param shadowImageSize value of shadowImageSize as Expression
   */
  fun shadowImageSize(shadowImageSize: Expression): LocationIndicatorLayer

  /**
   * The size of the shadow image, as a scale factor applied to the size of the specified image.
   *
   * Set the ShadowImageSize property transition options
   *
   * @param options transition options for Double
   */
  fun shadowImageSizeTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * The size of the shadow image, as a scale factor applied to the size of the specified image.
   *
   * DSL for [shadowImageSizeTransition].
   */
  fun shadowImageSizeTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer

  /**
   * The size of the top image, as a scale factor applied to the size of the specified image.
   *
   * @param topImageSize value of topImageSize
   */
  fun topImageSize(topImageSize: Double = 1.0): LocationIndicatorLayer

  /**
   * The size of the top image, as a scale factor applied to the size of the specified image.
   *
   * @param topImageSize value of topImageSize as Expression
   */
  fun topImageSize(topImageSize: Expression): LocationIndicatorLayer

  /**
   * The size of the top image, as a scale factor applied to the size of the specified image.
   *
   * Set the TopImageSize property transition options
   *
   * @param options transition options for Double
   */
  fun topImageSizeTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * The size of the top image, as a scale factor applied to the size of the specified image.
   *
   * DSL for [topImageSizeTransition].
   */
  fun topImageSizeTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer
}

/**
 * DSL function for creating a [LocationIndicatorLayer].
 */
fun locationIndicatorLayer(layerId: String, block: LocationIndicatorLayerDsl.() -> Unit): LocationIndicatorLayer = LocationIndicatorLayer(layerId).apply(block)

// End of generated file.