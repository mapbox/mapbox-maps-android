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
 * Location Indicator layer.
 *
 * @see <a href="https://www.mapbox.com/mapbox-gl-style-spec/#layers-location-indicator">The online documentation</a>
 *
 * @param layerId the ID of the layer
 */
@UiThread
class LocationIndicatorLayer(override val layerId: String) : LocationIndicatorLayerDsl, Layer() {

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
   * Name of image in sprite to use as the middle of the location indicator.
   */
  val bearingImage: String?
    /**
     * Get the BearingImage property
     *
     * @return String
     */
    get() {
      return getPropertyValue<String>("bearing-image")
    }

  /**
   * Set the BearingImage property
   *
   * @param bearingImage value of bearingImage
   */
  override fun bearingImage(bearingImage: String) = apply {
    val propertyValue = PropertyValue("bearing-image", bearingImage)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "bearing-image".
   *
   * Name of image in sprite to use as the middle of the location indicator.
   */
  val bearingImageAsExpression: Expression?
    /**
     * Get the BearingImage property as an Expression
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
   * Set the BearingImage property
   *
   * @param bearingImage value of bearingImage as Expression
   */
  override fun bearingImage(bearingImage: Expression) = apply {
    val propertyValue = PropertyValue("bearing-image", bearingImage)
    setProperty(propertyValue)
  }

  /**
   * Name of image in sprite to use as the background of the location indicator.
   */
  val shadowImage: String?
    /**
     * Get the ShadowImage property
     *
     * @return String
     */
    get() {
      return getPropertyValue<String>("shadow-image")
    }

  /**
   * Set the ShadowImage property
   *
   * @param shadowImage value of shadowImage
   */
  override fun shadowImage(shadowImage: String) = apply {
    val propertyValue = PropertyValue("shadow-image", shadowImage)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "shadow-image".
   *
   * Name of image in sprite to use as the background of the location indicator.
   */
  val shadowImageAsExpression: Expression?
    /**
     * Get the ShadowImage property as an Expression
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
   * Set the ShadowImage property
   *
   * @param shadowImage value of shadowImage as Expression
   */
  override fun shadowImage(shadowImage: Expression) = apply {
    val propertyValue = PropertyValue("shadow-image", shadowImage)
    setProperty(propertyValue)
  }

  /**
   * Name of image in sprite to use as the top of the location indicator.
   */
  val topImage: String?
    /**
     * Get the TopImage property
     *
     * @return String
     */
    get() {
      return getPropertyValue<String>("top-image")
    }

  /**
   * Set the TopImage property
   *
   * @param topImage value of topImage
   */
  override fun topImage(topImage: String) = apply {
    val propertyValue = PropertyValue("top-image", topImage)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "top-image".
   *
   * Name of image in sprite to use as the top of the location indicator.
   */
  val topImageAsExpression: Expression?
    /**
     * Get the TopImage property as an Expression
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
   * Set the TopImage property
   *
   * @param topImage value of topImage as Expression
   */
  override fun topImage(topImage: Expression) = apply {
    val propertyValue = PropertyValue("top-image", topImage)
    setProperty(propertyValue)
  }

  /**
   * The accuracy, in meters, of the position source used to retrieve the position of the location indicator.
   */
  val accuracyRadius: Double?
    /**
     * Get the AccuracyRadius property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("accuracy-radius")
    }

  /**
   * Set the AccuracyRadius property
   *
   * @param accuracyRadius value of accuracyRadius
   */
  override fun accuracyRadius(accuracyRadius: Double) = apply {
    val propertyValue = PropertyValue("accuracy-radius", accuracyRadius)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "accuracy-radius".
   *
   * The accuracy, in meters, of the position source used to retrieve the position of the location indicator.
   */
  val accuracyRadiusAsExpression: Expression?
    /**
     * Get the AccuracyRadius property as an Expression
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
   * Set the AccuracyRadius property
   *
   * @param accuracyRadius value of accuracyRadius as Expression
   */
  override fun accuracyRadius(accuracyRadius: Expression) = apply {
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
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("accuracy-radius-transition")
    }

  /**
   * Set the AccuracyRadius property transition options
   *
   * @param options transition options for Double
   */
  override fun accuracyRadiusTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("accuracy-radius-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [accuracyRadiusTransition].
   */
  override fun accuracyRadiusTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    accuracyRadiusTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
   */
  val accuracyRadiusBorderColor: String?
    /**
     * Get the AccuracyRadiusBorderColor property
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
   * Set the AccuracyRadiusBorderColor property
   *
   * @param accuracyRadiusBorderColor value of accuracyRadiusBorderColor
   */
  override fun accuracyRadiusBorderColor(accuracyRadiusBorderColor: String) = apply {
    val propertyValue = PropertyValue("accuracy-radius-border-color", accuracyRadiusBorderColor)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "accuracy-radius-border-color".
   *
   * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
   */
  val accuracyRadiusBorderColorAsExpression: Expression?
    /**
     * Get the AccuracyRadiusBorderColor property as an Expression
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
   * Set the AccuracyRadiusBorderColor property
   *
   * @param accuracyRadiusBorderColor value of accuracyRadiusBorderColor as Expression
   */
  override fun accuracyRadiusBorderColor(accuracyRadiusBorderColor: Expression) = apply {
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
   * Set the AccuracyRadiusBorderColor property.
   *
   * @param accuracyRadiusBorderColor value of accuracyRadiusBorderColor
   */
  override fun accuracyRadiusBorderColor(@ColorInt accuracyRadiusBorderColor: Int) = apply {
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
     * @return transition options for String
     */
    get() {
      return getPropertyValue("accuracy-radius-border-color-transition")
    }

  /**
   * Set the AccuracyRadiusBorderColor property transition options
   *
   * @param options transition options for String
   */
  override fun accuracyRadiusBorderColorTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("accuracy-radius-border-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [accuracyRadiusBorderColorTransition].
   */
  override fun accuracyRadiusBorderColorTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    accuracyRadiusBorderColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
   */
  val accuracyRadiusColor: String?
    /**
     * Get the AccuracyRadiusColor property
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
   * Set the AccuracyRadiusColor property
   *
   * @param accuracyRadiusColor value of accuracyRadiusColor
   */
  override fun accuracyRadiusColor(accuracyRadiusColor: String) = apply {
    val propertyValue = PropertyValue("accuracy-radius-color", accuracyRadiusColor)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "accuracy-radius-color".
   *
   * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
   */
  val accuracyRadiusColorAsExpression: Expression?
    /**
     * Get the AccuracyRadiusColor property as an Expression
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
   * Set the AccuracyRadiusColor property
   *
   * @param accuracyRadiusColor value of accuracyRadiusColor as Expression
   */
  override fun accuracyRadiusColor(accuracyRadiusColor: Expression) = apply {
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
   * Set the AccuracyRadiusColor property.
   *
   * @param accuracyRadiusColor value of accuracyRadiusColor
   */
  override fun accuracyRadiusColor(@ColorInt accuracyRadiusColor: Int) = apply {
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
     * @return transition options for String
     */
    get() {
      return getPropertyValue("accuracy-radius-color-transition")
    }

  /**
   * Set the AccuracyRadiusColor property transition options
   *
   * @param options transition options for String
   */
  override fun accuracyRadiusColorTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("accuracy-radius-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [accuracyRadiusColorTransition].
   */
  override fun accuracyRadiusColorTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    accuracyRadiusColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The bearing of the location indicator.
   */
  val bearing: Double?
    /**
     * Get the Bearing property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("bearing")
    }

  /**
   * Set the Bearing property
   *
   * @param bearing value of bearing
   */
  override fun bearing(bearing: Double) = apply {
    val propertyValue = PropertyValue("bearing", bearing)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "bearing".
   *
   * The bearing of the location indicator.
   */
  val bearingAsExpression: Expression?
    /**
     * Get the Bearing property as an Expression
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
   * Set the Bearing property
   *
   * @param bearing value of bearing as Expression
   */
  override fun bearing(bearing: Expression) = apply {
    val propertyValue = PropertyValue("bearing", bearing)
    setProperty(propertyValue)
  }

  /**
   * The size of the bearing image, as a scale factor applied to the size of the specified image.
   */
  val bearingImageSize: Double?
    /**
     * Get the BearingImageSize property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("bearing-image-size")
    }

  /**
   * Set the BearingImageSize property
   *
   * @param bearingImageSize value of bearingImageSize
   */
  override fun bearingImageSize(bearingImageSize: Double) = apply {
    val propertyValue = PropertyValue("bearing-image-size", bearingImageSize)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "bearing-image-size".
   *
   * The size of the bearing image, as a scale factor applied to the size of the specified image.
   */
  val bearingImageSizeAsExpression: Expression?
    /**
     * Get the BearingImageSize property as an Expression
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
   * Set the BearingImageSize property
   *
   * @param bearingImageSize value of bearingImageSize as Expression
   */
  override fun bearingImageSize(bearingImageSize: Expression) = apply {
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
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("bearing-image-size-transition")
    }

  /**
   * Set the BearingImageSize property transition options
   *
   * @param options transition options for Double
   */
  override fun bearingImageSizeTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("bearing-image-size-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [bearingImageSizeTransition].
   */
  override fun bearingImageSizeTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    bearingImageSizeTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
   */
  val emphasisCircleColor: String?
    /**
     * Get the EmphasisCircleColor property
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
   * Set the EmphasisCircleColor property
   *
   * @param emphasisCircleColor value of emphasisCircleColor
   */
  override fun emphasisCircleColor(emphasisCircleColor: String) = apply {
    val propertyValue = PropertyValue("emphasis-circle-color", emphasisCircleColor)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "emphasis-circle-color".
   *
   * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
   */
  val emphasisCircleColorAsExpression: Expression?
    /**
     * Get the EmphasisCircleColor property as an Expression
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
   * Set the EmphasisCircleColor property
   *
   * @param emphasisCircleColor value of emphasisCircleColor as Expression
   */
  override fun emphasisCircleColor(emphasisCircleColor: Expression) = apply {
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
   * Set the EmphasisCircleColor property.
   *
   * @param emphasisCircleColor value of emphasisCircleColor
   */
  override fun emphasisCircleColor(@ColorInt emphasisCircleColor: Int) = apply {
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
     * @return transition options for String
     */
    get() {
      return getPropertyValue("emphasis-circle-color-transition")
    }

  /**
   * Set the EmphasisCircleColor property transition options
   *
   * @param options transition options for String
   */
  override fun emphasisCircleColorTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("emphasis-circle-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [emphasisCircleColorTransition].
   */
  override fun emphasisCircleColorTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    emphasisCircleColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
   */
  val emphasisCircleRadius: Double?
    /**
     * Get the EmphasisCircleRadius property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("emphasis-circle-radius")
    }

  /**
   * Set the EmphasisCircleRadius property
   *
   * @param emphasisCircleRadius value of emphasisCircleRadius
   */
  override fun emphasisCircleRadius(emphasisCircleRadius: Double) = apply {
    val propertyValue = PropertyValue("emphasis-circle-radius", emphasisCircleRadius)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "emphasis-circle-radius".
   *
   * The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
   */
  val emphasisCircleRadiusAsExpression: Expression?
    /**
     * Get the EmphasisCircleRadius property as an Expression
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
   * Set the EmphasisCircleRadius property
   *
   * @param emphasisCircleRadius value of emphasisCircleRadius as Expression
   */
  override fun emphasisCircleRadius(emphasisCircleRadius: Expression) = apply {
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
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("emphasis-circle-radius-transition")
    }

  /**
   * Set the EmphasisCircleRadius property transition options
   *
   * @param options transition options for Double
   */
  override fun emphasisCircleRadiusTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("emphasis-circle-radius-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [emphasisCircleRadiusTransition].
   */
  override fun emphasisCircleRadiusTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    emphasisCircleRadiusTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The displacement off the center of the top image and the shadow image when the pitch of the map is greater than 0. This helps producing a three-dimensional appearence.
   */
  val imagePitchDisplacement: Double?
    /**
     * Get the ImagePitchDisplacement property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("image-pitch-displacement")
    }

  /**
   * Set the ImagePitchDisplacement property
   *
   * @param imagePitchDisplacement value of imagePitchDisplacement
   */
  override fun imagePitchDisplacement(imagePitchDisplacement: Double) = apply {
    val propertyValue = PropertyValue("image-pitch-displacement", imagePitchDisplacement)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "image-pitch-displacement".
   *
   * The displacement off the center of the top image and the shadow image when the pitch of the map is greater than 0. This helps producing a three-dimensional appearence.
   */
  val imagePitchDisplacementAsExpression: Expression?
    /**
     * Get the ImagePitchDisplacement property as an Expression
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
   * Set the ImagePitchDisplacement property
   *
   * @param imagePitchDisplacement value of imagePitchDisplacement as Expression
   */
  override fun imagePitchDisplacement(imagePitchDisplacement: Expression) = apply {
    val propertyValue = PropertyValue("image-pitch-displacement", imagePitchDisplacement)
    setProperty(propertyValue)
  }

  /**
   * An array of [latitude, longitude, altitude] position of the location indicator.
   */
  val location: List<Double>?
    /**
     * Get the Location property
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("location")
    }

  /**
   * Set the Location property
   *
   * @param location value of location
   */
  override fun location(location: List<Double>) = apply {
    val propertyValue = PropertyValue("location", location)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "location".
   *
   * An array of [latitude, longitude, altitude] position of the location indicator.
   */
  val locationAsExpression: Expression?
    /**
     * Get the Location property as an Expression
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
   * Set the Location property
   *
   * @param location value of location as Expression
   */
  override fun location(location: Expression) = apply {
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
     * @return transition options for List<Double>
     */
    get() {
      return getPropertyValue("location-transition")
    }

  /**
   * Set the Location property transition options
   *
   * @param options transition options for List<Double>
   */
  override fun locationTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("location-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [locationTransition].
   */
  override fun locationTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    locationTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection.
   */
  val perspectiveCompensation: Double?
    /**
     * Get the PerspectiveCompensation property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("perspective-compensation")
    }

  /**
   * Set the PerspectiveCompensation property
   *
   * @param perspectiveCompensation value of perspectiveCompensation
   */
  override fun perspectiveCompensation(perspectiveCompensation: Double) = apply {
    val propertyValue = PropertyValue("perspective-compensation", perspectiveCompensation)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "perspective-compensation".
   *
   * The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection.
   */
  val perspectiveCompensationAsExpression: Expression?
    /**
     * Get the PerspectiveCompensation property as an Expression
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
   * Set the PerspectiveCompensation property
   *
   * @param perspectiveCompensation value of perspectiveCompensation as Expression
   */
  override fun perspectiveCompensation(perspectiveCompensation: Expression) = apply {
    val propertyValue = PropertyValue("perspective-compensation", perspectiveCompensation)
    setProperty(propertyValue)
  }

  /**
   * The size of the shadow image, as a scale factor applied to the size of the specified image.
   */
  val shadowImageSize: Double?
    /**
     * Get the ShadowImageSize property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("shadow-image-size")
    }

  /**
   * Set the ShadowImageSize property
   *
   * @param shadowImageSize value of shadowImageSize
   */
  override fun shadowImageSize(shadowImageSize: Double) = apply {
    val propertyValue = PropertyValue("shadow-image-size", shadowImageSize)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "shadow-image-size".
   *
   * The size of the shadow image, as a scale factor applied to the size of the specified image.
   */
  val shadowImageSizeAsExpression: Expression?
    /**
     * Get the ShadowImageSize property as an Expression
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
   * Set the ShadowImageSize property
   *
   * @param shadowImageSize value of shadowImageSize as Expression
   */
  override fun shadowImageSize(shadowImageSize: Expression) = apply {
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
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("shadow-image-size-transition")
    }

  /**
   * Set the ShadowImageSize property transition options
   *
   * @param options transition options for Double
   */
  override fun shadowImageSizeTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("shadow-image-size-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [shadowImageSizeTransition].
   */
  override fun shadowImageSizeTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    shadowImageSizeTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The size of the top image, as a scale factor applied to the size of the specified image.
   */
  val topImageSize: Double?
    /**
     * Get the TopImageSize property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("top-image-size")
    }

  /**
   * Set the TopImageSize property
   *
   * @param topImageSize value of topImageSize
   */
  override fun topImageSize(topImageSize: Double) = apply {
    val propertyValue = PropertyValue("top-image-size", topImageSize)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "top-image-size".
   *
   * The size of the top image, as a scale factor applied to the size of the specified image.
   */
  val topImageSizeAsExpression: Expression?
    /**
     * Get the TopImageSize property as an Expression
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
   * Set the TopImageSize property
   *
   * @param topImageSize value of topImageSize as Expression
   */
  override fun topImageSize(topImageSize: Expression) = apply {
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
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("top-image-size-transition")
    }

  /**
   * Set the TopImageSize property transition options
   *
   * @param options transition options for Double
   */
  override fun topImageSizeTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("top-image-size-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [topImageSizeTransition].
   */
  override fun topImageSizeTransition(block: StyleTransition.Builder.() -> Unit) = apply {
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
       * Get the default value of BearingImage property
       *
       * @return String
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing-image").silentUnwrap()
      }

    /**
     * This is an Expression representation of "bearing-image".
     *
     * Name of image in sprite to use as the middle of the location indicator.
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
       * Get the default value of ShadowImage property
       *
       * @return String
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "shadow-image").silentUnwrap()
      }

    /**
     * This is an Expression representation of "shadow-image".
     *
     * Name of image in sprite to use as the background of the location indicator.
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
       * Get the default value of TopImage property
       *
       * @return String
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "top-image").silentUnwrap()
      }

    /**
     * This is an Expression representation of "top-image".
     *
     * Name of image in sprite to use as the top of the location indicator.
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
       * Get the default value of AccuracyRadius property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "accuracy-radius").silentUnwrap()
      }

    /**
     * This is an Expression representation of "accuracy-radius".
     *
     * The accuracy, in meters, of the position source used to retrieve the position of the location indicator.
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
     * This is an Expression representation of "accuracy-radius-border-color".
     *
     * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
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
     * This is an Expression representation of "accuracy-radius-color".
     *
     * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
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
       * Get the default value of Bearing property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing").silentUnwrap()
      }

    /**
     * This is an Expression representation of "bearing".
     *
     * The bearing of the location indicator.
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
     * The size of the bearing image, as a scale factor applied to the size of the specified image.
     */
    val defaultBearingImageSize: Double?
      /**
       * Get the default value of BearingImageSize property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "bearing-image-size").silentUnwrap()
      }

    /**
     * This is an Expression representation of "bearing-image-size".
     *
     * The size of the bearing image, as a scale factor applied to the size of the specified image.
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
     * This is an Expression representation of "emphasis-circle-color".
     *
     * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
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
       * Get the default value of EmphasisCircleRadius property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "emphasis-circle-radius").silentUnwrap()
      }

    /**
     * This is an Expression representation of "emphasis-circle-radius".
     *
     * The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
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
       * Get the default value of ImagePitchDisplacement property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "image-pitch-displacement").silentUnwrap()
      }

    /**
     * This is an Expression representation of "image-pitch-displacement".
     *
     * The displacement off the center of the top image and the shadow image when the pitch of the map is greater than 0. This helps producing a three-dimensional appearence.
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
       * Get the default value of Location property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "location").silentUnwrap()
      }

    /**
     * This is an Expression representation of "location".
     *
     * An array of [latitude, longitude, altitude] position of the location indicator.
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
     * The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection.
     */
    val defaultPerspectiveCompensation: Double?
      /**
       * Get the default value of PerspectiveCompensation property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "perspective-compensation").silentUnwrap()
      }

    /**
     * This is an Expression representation of "perspective-compensation".
     *
     * The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection.
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
       * Get the default value of ShadowImageSize property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "shadow-image-size").silentUnwrap()
      }

    /**
     * This is an Expression representation of "shadow-image-size".
     *
     * The size of the shadow image, as a scale factor applied to the size of the specified image.
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
       * Get the default value of TopImageSize property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("location-indicator", "top-image-size").silentUnwrap()
      }

    /**
     * This is an Expression representation of "top-image-size".
     *
     * The size of the top image, as a scale factor applied to the size of the specified image.
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
   * Set the Visibility property
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): LocationIndicatorLayer

  /**
   * Set the minzoom property
   *
   * @param value value of minzoom
   */
  fun minZoom(minZoom: Double): LocationIndicatorLayer

  /**
   * Set the maxzoom property
   *
   * @param value value of maxzoom
   */
  fun maxZoom(maxZoom: Double): LocationIndicatorLayer

  // Property getters and setters

  /**
   * Set the BearingImage property
   *
   * @param bearingImage value of bearingImage
   */
  fun bearingImage(bearingImage: String): LocationIndicatorLayer

  /**
   * Set the BearingImage property
   *
   * @param bearingImage value of bearingImage as Expression
   */
  fun bearingImage(bearingImage: Expression): LocationIndicatorLayer

  /**
   * Set the ShadowImage property
   *
   * @param shadowImage value of shadowImage
   */
  fun shadowImage(shadowImage: String): LocationIndicatorLayer

  /**
   * Set the ShadowImage property
   *
   * @param shadowImage value of shadowImage as Expression
   */
  fun shadowImage(shadowImage: Expression): LocationIndicatorLayer

  /**
   * Set the TopImage property
   *
   * @param topImage value of topImage
   */
  fun topImage(topImage: String): LocationIndicatorLayer

  /**
   * Set the TopImage property
   *
   * @param topImage value of topImage as Expression
   */
  fun topImage(topImage: Expression): LocationIndicatorLayer

  /**
   * Set the AccuracyRadius property
   *
   * @param accuracyRadius value of accuracyRadius
   */
  fun accuracyRadius(accuracyRadius: Double = 0.0): LocationIndicatorLayer

  /**
   * Set the AccuracyRadius property
   *
   * @param accuracyRadius value of accuracyRadius as Expression
   */
  fun accuracyRadius(accuracyRadius: Expression): LocationIndicatorLayer

  /**
   * Set the AccuracyRadius property transition options
   *
   * @param options transition options for Double
   */
  fun accuracyRadiusTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * DSL for [accuracyRadiusTransition].
   */
  fun accuracyRadiusTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer

  /**
   * Set the AccuracyRadiusBorderColor property
   *
   * @param accuracyRadiusBorderColor value of accuracyRadiusBorderColor
   */
  fun accuracyRadiusBorderColor(accuracyRadiusBorderColor: String = "#ffffff"): LocationIndicatorLayer

  /**
   * Set the AccuracyRadiusBorderColor property
   *
   * @param accuracyRadiusBorderColor value of accuracyRadiusBorderColor as Expression
   */
  fun accuracyRadiusBorderColor(accuracyRadiusBorderColor: Expression): LocationIndicatorLayer

  /**
   * Set the AccuracyRadiusBorderColor property.
   *
   * @param accuracyRadiusBorderColor value of accuracyRadiusBorderColor
   */
  fun accuracyRadiusBorderColor(@ColorInt accuracyRadiusBorderColor: Int): LocationIndicatorLayer

  /**
   * Set the AccuracyRadiusBorderColor property transition options
   *
   * @param options transition options for String
   */
  fun accuracyRadiusBorderColorTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * DSL for [accuracyRadiusBorderColorTransition].
   */
  fun accuracyRadiusBorderColorTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer

  /**
   * Set the AccuracyRadiusColor property
   *
   * @param accuracyRadiusColor value of accuracyRadiusColor
   */
  fun accuracyRadiusColor(accuracyRadiusColor: String = "#ffffff"): LocationIndicatorLayer

  /**
   * Set the AccuracyRadiusColor property
   *
   * @param accuracyRadiusColor value of accuracyRadiusColor as Expression
   */
  fun accuracyRadiusColor(accuracyRadiusColor: Expression): LocationIndicatorLayer

  /**
   * Set the AccuracyRadiusColor property.
   *
   * @param accuracyRadiusColor value of accuracyRadiusColor
   */
  fun accuracyRadiusColor(@ColorInt accuracyRadiusColor: Int): LocationIndicatorLayer

  /**
   * Set the AccuracyRadiusColor property transition options
   *
   * @param options transition options for String
   */
  fun accuracyRadiusColorTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * DSL for [accuracyRadiusColorTransition].
   */
  fun accuracyRadiusColorTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer

  /**
   * Set the Bearing property
   *
   * @param bearing value of bearing
   */
  fun bearing(bearing: Double = 0.0): LocationIndicatorLayer

  /**
   * Set the Bearing property
   *
   * @param bearing value of bearing as Expression
   */
  fun bearing(bearing: Expression): LocationIndicatorLayer

  /**
   * Set the BearingImageSize property
   *
   * @param bearingImageSize value of bearingImageSize
   */
  fun bearingImageSize(bearingImageSize: Double = 1.0): LocationIndicatorLayer

  /**
   * Set the BearingImageSize property
   *
   * @param bearingImageSize value of bearingImageSize as Expression
   */
  fun bearingImageSize(bearingImageSize: Expression): LocationIndicatorLayer

  /**
   * Set the BearingImageSize property transition options
   *
   * @param options transition options for Double
   */
  fun bearingImageSizeTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * DSL for [bearingImageSizeTransition].
   */
  fun bearingImageSizeTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer

  /**
   * Set the EmphasisCircleColor property
   *
   * @param emphasisCircleColor value of emphasisCircleColor
   */
  fun emphasisCircleColor(emphasisCircleColor: String = "#ffffff"): LocationIndicatorLayer

  /**
   * Set the EmphasisCircleColor property
   *
   * @param emphasisCircleColor value of emphasisCircleColor as Expression
   */
  fun emphasisCircleColor(emphasisCircleColor: Expression): LocationIndicatorLayer

  /**
   * Set the EmphasisCircleColor property.
   *
   * @param emphasisCircleColor value of emphasisCircleColor
   */
  fun emphasisCircleColor(@ColorInt emphasisCircleColor: Int): LocationIndicatorLayer

  /**
   * Set the EmphasisCircleColor property transition options
   *
   * @param options transition options for String
   */
  fun emphasisCircleColorTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * DSL for [emphasisCircleColorTransition].
   */
  fun emphasisCircleColorTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer

  /**
   * Set the EmphasisCircleRadius property
   *
   * @param emphasisCircleRadius value of emphasisCircleRadius
   */
  fun emphasisCircleRadius(emphasisCircleRadius: Double = 0.0): LocationIndicatorLayer

  /**
   * Set the EmphasisCircleRadius property
   *
   * @param emphasisCircleRadius value of emphasisCircleRadius as Expression
   */
  fun emphasisCircleRadius(emphasisCircleRadius: Expression): LocationIndicatorLayer

  /**
   * Set the EmphasisCircleRadius property transition options
   *
   * @param options transition options for Double
   */
  fun emphasisCircleRadiusTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * DSL for [emphasisCircleRadiusTransition].
   */
  fun emphasisCircleRadiusTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer

  /**
   * Set the ImagePitchDisplacement property
   *
   * @param imagePitchDisplacement value of imagePitchDisplacement
   */
  fun imagePitchDisplacement(imagePitchDisplacement: Double = 0.0): LocationIndicatorLayer

  /**
   * Set the ImagePitchDisplacement property
   *
   * @param imagePitchDisplacement value of imagePitchDisplacement as Expression
   */
  fun imagePitchDisplacement(imagePitchDisplacement: Expression): LocationIndicatorLayer

  /**
   * Set the Location property
   *
   * @param location value of location
   */
  fun location(location: List<Double> = listOf(0.0, 0.0, 0.0)): LocationIndicatorLayer

  /**
   * Set the Location property
   *
   * @param location value of location as Expression
   */
  fun location(location: Expression): LocationIndicatorLayer

  /**
   * Set the Location property transition options
   *
   * @param options transition options for List<Double>
   */
  fun locationTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * DSL for [locationTransition].
   */
  fun locationTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer

  /**
   * Set the PerspectiveCompensation property
   *
   * @param perspectiveCompensation value of perspectiveCompensation
   */
  fun perspectiveCompensation(perspectiveCompensation: Double = 0.85): LocationIndicatorLayer

  /**
   * Set the PerspectiveCompensation property
   *
   * @param perspectiveCompensation value of perspectiveCompensation as Expression
   */
  fun perspectiveCompensation(perspectiveCompensation: Expression): LocationIndicatorLayer

  /**
   * Set the ShadowImageSize property
   *
   * @param shadowImageSize value of shadowImageSize
   */
  fun shadowImageSize(shadowImageSize: Double = 1.0): LocationIndicatorLayer

  /**
   * Set the ShadowImageSize property
   *
   * @param shadowImageSize value of shadowImageSize as Expression
   */
  fun shadowImageSize(shadowImageSize: Expression): LocationIndicatorLayer

  /**
   * Set the ShadowImageSize property transition options
   *
   * @param options transition options for Double
   */
  fun shadowImageSizeTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * DSL for [shadowImageSizeTransition].
   */
  fun shadowImageSizeTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer

  /**
   * Set the TopImageSize property
   *
   * @param topImageSize value of topImageSize
   */
  fun topImageSize(topImageSize: Double = 1.0): LocationIndicatorLayer

  /**
   * Set the TopImageSize property
   *
   * @param topImageSize value of topImageSize as Expression
   */
  fun topImageSize(topImageSize: Expression): LocationIndicatorLayer

  /**
   * Set the TopImageSize property transition options
   *
   * @param options transition options for Double
   */
  fun topImageSizeTransition(options: StyleTransition): LocationIndicatorLayer

  /**
   * DSL for [topImageSizeTransition].
   */
  fun topImageSizeTransition(block: StyleTransition.Builder.() -> Unit): LocationIndicatorLayer
}

/**
 * DSL function for [LocationIndicatorLayer].
 */
fun locationIndicatorLayer(layerId: String, block: LocationIndicatorLayerDsl.() -> Unit): LocationIndicatorLayer = LocationIndicatorLayer(layerId).apply(block)

// End of generated file.