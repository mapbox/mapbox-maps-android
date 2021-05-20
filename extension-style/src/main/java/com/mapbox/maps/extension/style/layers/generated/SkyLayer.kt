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
 * A spherical dome around the map that is always rendered behind all other layers.
 *
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-sky)
 *
 * @param layerId the ID of the layer
 */
@UiThread
class SkyLayer(override val layerId: String) : SkyLayerDsl, Layer() {

  /**
   * Set the filter property
   *
   * @param filter the expression filter to set
   */
  override fun filter(filter: Expression) = apply {
    val propertyValue = PropertyValue("filter", filter)
    setProperty(propertyValue)
  }

  /**
   * A expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `feature-state` expression is not supported in filter expressions.
   */
  val filter: Expression?
    /**
     * Get the filter property
     *
     * @return filter
     */
    get() = getPropertyValue<Expression>("filter")

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
   * A color used to tweak the main atmospheric scattering coefficients. Using white applies the default coefficients giving the natural blue color to the atmosphere. This color affects how heavily the corresponding wavelength is represented during scattering. The alpha channel describes the density of the atmosphere, with 1 maximum density and 0 no density.
   */
  val skyAtmosphereColor: String?
    /**
     * Get the SkyAtmosphereColor property
     *
     * @return String
     */
    get() {
      skyAtmosphereColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * Set the SkyAtmosphereColor property
   *
   * @param skyAtmosphereColor value of skyAtmosphereColor
   */
  override fun skyAtmosphereColor(skyAtmosphereColor: String) = apply {
    val propertyValue = PropertyValue("sky-atmosphere-color", skyAtmosphereColor)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "sky-atmosphere-color".
   *
   * A color used to tweak the main atmospheric scattering coefficients. Using white applies the default coefficients giving the natural blue color to the atmosphere. This color affects how heavily the corresponding wavelength is represented during scattering. The alpha channel describes the density of the atmosphere, with 1 maximum density and 0 no density.
   */
  val skyAtmosphereColorAsExpression: Expression?
    /**
     * Get the SkyAtmosphereColor property as an Expression
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("sky-atmosphere-color")?.let {
        return it
      }
      return null
    }

  /**
   * Set the SkyAtmosphereColor property
   *
   * @param skyAtmosphereColor value of skyAtmosphereColor as Expression
   */
  override fun skyAtmosphereColor(skyAtmosphereColor: Expression) = apply {
    val propertyValue = PropertyValue("sky-atmosphere-color", skyAtmosphereColor)
    setProperty(propertyValue)
  }

  /**
   * A color used to tweak the main atmospheric scattering coefficients. Using white applies the default coefficients giving the natural blue color to the atmosphere. This color affects how heavily the corresponding wavelength is represented during scattering. The alpha channel describes the density of the atmosphere, with 1 maximum density and 0 no density.
   */
  val skyAtmosphereColorAsColorInt: Int?
    /**
     * A color used to tweak the main atmospheric scattering coefficients. Using white applies the default coefficients giving the natural blue color to the atmosphere. This color affects how heavily the corresponding wavelength is represented during scattering. The alpha channel describes the density of the atmosphere, with 1 maximum density and 0 no density.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      skyAtmosphereColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * Set the SkyAtmosphereColor property.
   *
   * @param skyAtmosphereColor value of skyAtmosphereColor
   */
  override fun skyAtmosphereColor(@ColorInt skyAtmosphereColor: Int) = apply {
    val propertyValue = PropertyValue("sky-atmosphere-color", colorIntToRgbaExpression(skyAtmosphereColor))
    setProperty(propertyValue)
  }

  /**
   * A color applied to the atmosphere sun halo. The alpha channel describes how strongly the sun halo is represented in an atmosphere sky layer.
   */
  val skyAtmosphereHaloColor: String?
    /**
     * Get the SkyAtmosphereHaloColor property
     *
     * @return String
     */
    get() {
      skyAtmosphereHaloColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * Set the SkyAtmosphereHaloColor property
   *
   * @param skyAtmosphereHaloColor value of skyAtmosphereHaloColor
   */
  override fun skyAtmosphereHaloColor(skyAtmosphereHaloColor: String) = apply {
    val propertyValue = PropertyValue("sky-atmosphere-halo-color", skyAtmosphereHaloColor)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "sky-atmosphere-halo-color".
   *
   * A color applied to the atmosphere sun halo. The alpha channel describes how strongly the sun halo is represented in an atmosphere sky layer.
   */
  val skyAtmosphereHaloColorAsExpression: Expression?
    /**
     * Get the SkyAtmosphereHaloColor property as an Expression
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("sky-atmosphere-halo-color")?.let {
        return it
      }
      return null
    }

  /**
   * Set the SkyAtmosphereHaloColor property
   *
   * @param skyAtmosphereHaloColor value of skyAtmosphereHaloColor as Expression
   */
  override fun skyAtmosphereHaloColor(skyAtmosphereHaloColor: Expression) = apply {
    val propertyValue = PropertyValue("sky-atmosphere-halo-color", skyAtmosphereHaloColor)
    setProperty(propertyValue)
  }

  /**
   * A color applied to the atmosphere sun halo. The alpha channel describes how strongly the sun halo is represented in an atmosphere sky layer.
   */
  val skyAtmosphereHaloColorAsColorInt: Int?
    /**
     * A color applied to the atmosphere sun halo. The alpha channel describes how strongly the sun halo is represented in an atmosphere sky layer.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      skyAtmosphereHaloColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * Set the SkyAtmosphereHaloColor property.
   *
   * @param skyAtmosphereHaloColor value of skyAtmosphereHaloColor
   */
  override fun skyAtmosphereHaloColor(@ColorInt skyAtmosphereHaloColor: Int) = apply {
    val propertyValue = PropertyValue("sky-atmosphere-halo-color", colorIntToRgbaExpression(skyAtmosphereHaloColor))
    setProperty(propertyValue)
  }

  /**
   * Position of the sun center [a azimuthal angle, p polar angle]. The azimuthal angle indicates the position of the sun relative to 0 degree north, where degrees proceed clockwise. The polar angle indicates the height of the sun, where 0 degree is directly above, at zenith, and 90 degree at the horizon. When this property is ommitted, the sun center is directly inherited from the light position.
   */
  val skyAtmosphereSun: List<Double>?
    /**
     * Get the SkyAtmosphereSun property
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("sky-atmosphere-sun")
    }

  /**
   * Set the SkyAtmosphereSun property
   *
   * @param skyAtmosphereSun value of skyAtmosphereSun
   */
  override fun skyAtmosphereSun(skyAtmosphereSun: List<Double>) = apply {
    val propertyValue = PropertyValue("sky-atmosphere-sun", skyAtmosphereSun)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "sky-atmosphere-sun".
   *
   * Position of the sun center [a azimuthal angle, p polar angle]. The azimuthal angle indicates the position of the sun relative to 0 degree north, where degrees proceed clockwise. The polar angle indicates the height of the sun, where 0 degree is directly above, at zenith, and 90 degree at the horizon. When this property is ommitted, the sun center is directly inherited from the light position.
   */
  val skyAtmosphereSunAsExpression: Expression?
    /**
     * Get the SkyAtmosphereSun property as an Expression
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("sky-atmosphere-sun")?.let {
        return it
      }
      skyAtmosphereSun?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the SkyAtmosphereSun property
   *
   * @param skyAtmosphereSun value of skyAtmosphereSun as Expression
   */
  override fun skyAtmosphereSun(skyAtmosphereSun: Expression) = apply {
    val propertyValue = PropertyValue("sky-atmosphere-sun", skyAtmosphereSun)
    setProperty(propertyValue)
  }

  /**
   * Intensity of the sun as a light source in the atmosphere (on a scale from 0 to a 100). Setting higher values will brighten up the sky.
   */
  val skyAtmosphereSunIntensity: Double?
    /**
     * Get the SkyAtmosphereSunIntensity property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("sky-atmosphere-sun-intensity")
    }

  /**
   * Set the SkyAtmosphereSunIntensity property
   *
   * @param skyAtmosphereSunIntensity value of skyAtmosphereSunIntensity
   */
  override fun skyAtmosphereSunIntensity(skyAtmosphereSunIntensity: Double) = apply {
    val propertyValue = PropertyValue("sky-atmosphere-sun-intensity", skyAtmosphereSunIntensity)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "sky-atmosphere-sun-intensity".
   *
   * Intensity of the sun as a light source in the atmosphere (on a scale from 0 to a 100). Setting higher values will brighten up the sky.
   */
  val skyAtmosphereSunIntensityAsExpression: Expression?
    /**
     * Get the SkyAtmosphereSunIntensity property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("sky-atmosphere-sun-intensity")?.let {
        return it
      }
      skyAtmosphereSunIntensity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the SkyAtmosphereSunIntensity property
   *
   * @param skyAtmosphereSunIntensity value of skyAtmosphereSunIntensity as Expression
   */
  override fun skyAtmosphereSunIntensity(skyAtmosphereSunIntensity: Expression) = apply {
    val propertyValue = PropertyValue("sky-atmosphere-sun-intensity", skyAtmosphereSunIntensity)
    setProperty(propertyValue)
  }

  /**
   * Defines a radial color gradient with which to color the sky. The color values can be interpolated with an expression using `sky-radial-progress`. The range [0, 1] for the interpolant covers a radial distance (in degrees) of [0, `sky-gradient-radius`] centered at the position specified by `sky-gradient-center`.
   */
  val skyGradient: Expression?
    /**
     * Get the SkyGradient property
     *
     * @return Expression
     */
    get() {
      return getPropertyValue("sky-gradient")
    }

  /**
   * Set the SkyGradient property
   *
   * @param skyGradient value of skyGradient
   */
  override fun skyGradient(skyGradient: Expression) = apply {
    val propertyValue = PropertyValue("sky-gradient", skyGradient)
    setProperty(propertyValue)
  }

  /**
   * Position of the gradient center [a azimuthal angle, p polar angle]. The azimuthal angle indicates the position of the gradient center relative to 0 degree north, where degrees proceed clockwise. The polar angle indicates the height of the gradient center, where 0 degree is directly above, at zenith, and 90 degree at the horizon.
   */
  val skyGradientCenter: List<Double>?
    /**
     * Get the SkyGradientCenter property
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("sky-gradient-center")
    }

  /**
   * Set the SkyGradientCenter property
   *
   * @param skyGradientCenter value of skyGradientCenter
   */
  override fun skyGradientCenter(skyGradientCenter: List<Double>) = apply {
    val propertyValue = PropertyValue("sky-gradient-center", skyGradientCenter)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "sky-gradient-center".
   *
   * Position of the gradient center [a azimuthal angle, p polar angle]. The azimuthal angle indicates the position of the gradient center relative to 0 degree north, where degrees proceed clockwise. The polar angle indicates the height of the gradient center, where 0 degree is directly above, at zenith, and 90 degree at the horizon.
   */
  val skyGradientCenterAsExpression: Expression?
    /**
     * Get the SkyGradientCenter property as an Expression
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("sky-gradient-center")?.let {
        return it
      }
      skyGradientCenter?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the SkyGradientCenter property
   *
   * @param skyGradientCenter value of skyGradientCenter as Expression
   */
  override fun skyGradientCenter(skyGradientCenter: Expression) = apply {
    val propertyValue = PropertyValue("sky-gradient-center", skyGradientCenter)
    setProperty(propertyValue)
  }

  /**
   * The angular distance (measured in degrees) from `sky-gradient-center` up to which the gradient extends. A value of 180 causes the gradient to wrap around to the opposite direction from `sky-gradient-center`.
   */
  val skyGradientRadius: Double?
    /**
     * Get the SkyGradientRadius property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("sky-gradient-radius")
    }

  /**
   * Set the SkyGradientRadius property
   *
   * @param skyGradientRadius value of skyGradientRadius
   */
  override fun skyGradientRadius(skyGradientRadius: Double) = apply {
    val propertyValue = PropertyValue("sky-gradient-radius", skyGradientRadius)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "sky-gradient-radius".
   *
   * The angular distance (measured in degrees) from `sky-gradient-center` up to which the gradient extends. A value of 180 causes the gradient to wrap around to the opposite direction from `sky-gradient-center`.
   */
  val skyGradientRadiusAsExpression: Expression?
    /**
     * Get the SkyGradientRadius property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("sky-gradient-radius")?.let {
        return it
      }
      skyGradientRadius?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the SkyGradientRadius property
   *
   * @param skyGradientRadius value of skyGradientRadius as Expression
   */
  override fun skyGradientRadius(skyGradientRadius: Expression) = apply {
    val propertyValue = PropertyValue("sky-gradient-radius", skyGradientRadius)
    setProperty(propertyValue)
  }

  /**
   * The opacity of the entire sky layer.
   */
  val skyOpacity: Double?
    /**
     * Get the SkyOpacity property
     *
     * @return Double
     */
    get() {
      return getPropertyValue("sky-opacity")
    }

  /**
   * Set the SkyOpacity property
   *
   * @param skyOpacity value of skyOpacity
   */
  override fun skyOpacity(skyOpacity: Double) = apply {
    val propertyValue = PropertyValue("sky-opacity", skyOpacity)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "sky-opacity".
   *
   * The opacity of the entire sky layer.
   */
  val skyOpacityAsExpression: Expression?
    /**
     * Get the SkyOpacity property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("sky-opacity")?.let {
        return it
      }
      skyOpacity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Set the SkyOpacity property
   *
   * @param skyOpacity value of skyOpacity as Expression
   */
  override fun skyOpacity(skyOpacity: Expression) = apply {
    val propertyValue = PropertyValue("sky-opacity", skyOpacity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for SkyOpacity.
   */
  val skyOpacityTransition: StyleTransition?
    /**
     * Get the SkyOpacity property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("sky-opacity-transition")
    }

  /**
   * Set the SkyOpacity property transition options
   *
   * @param options transition options for Double
   */
  override fun skyOpacityTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("sky-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [skyOpacityTransition].
   */
  override fun skyOpacityTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    skyOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The type of the sky
   */
  val skyType: SkyType?
    /**
     * Get the SkyType property
     *
     * @return SkyType
     */
    get() {
      getPropertyValue<String?>("sky-type")?.let {
        return SkyType.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Set the SkyType property
   *
   * @param skyType value of skyType
   */
  override fun skyType(skyType: SkyType) = apply {
    val propertyValue = PropertyValue("sky-type", skyType)
    setProperty(propertyValue)
  }

  /**
   * This is an Expression representation of "sky-type".
   *
   * The type of the sky
   */
  val skyTypeAsExpression: Expression?
    /**
     * Get the SkyType property as an Expression
     *
     * @return SkyType
     */
    get() {
      getPropertyValue<Expression>("sky-type")?.let {
        return it
      }
      skyType?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Set the SkyType property
   *
   * @param skyType value of skyType as Expression
   */
  override fun skyType(skyType: Expression) = apply {
    val propertyValue = PropertyValue("sky-type", skyType)
    setProperty(propertyValue)
  }

  /**
   * Get the type of this layer
   *
   * @return Type of the layer as [String]
   */
  override fun getType(): String {
    return "sky"
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
        StyleManager.getStyleLayerPropertyDefaultValue("sky", "visibility").silentUnwrap<String>()?.let {
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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("sky", "minzoom").silentUnwrap()

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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("sky", "maxzoom").silentUnwrap()

    /**
     * A color used to tweak the main atmospheric scattering coefficients. Using white applies the default coefficients giving the natural blue color to the atmosphere. This color affects how heavily the corresponding wavelength is represented during scattering. The alpha channel describes the density of the atmosphere, with 1 maximum density and 0 no density.
     */
    val defaultSkyAtmosphereColor: String?
      /**
       * Get the default value of SkyAtmosphereColor property
       *
       * @return String
       */
      get() {
        defaultSkyAtmosphereColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * This is an Expression representation of "sky-atmosphere-color".
     *
     * A color used to tweak the main atmospheric scattering coefficients. Using white applies the default coefficients giving the natural blue color to the atmosphere. This color affects how heavily the corresponding wavelength is represented during scattering. The alpha channel describes the density of the atmosphere, with 1 maximum density and 0 no density.
     */
    val defaultSkyAtmosphereColorAsExpression: Expression?
      /**
       * Get default value of the SkyAtmosphereColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * A color used to tweak the main atmospheric scattering coefficients. Using white applies the default coefficients giving the natural blue color to the atmosphere. This color affects how heavily the corresponding wavelength is represented during scattering. The alpha channel describes the density of the atmosphere, with 1 maximum density and 0 no density.
     */
    val defaultSkyAtmosphereColorAsColorInt: Int?
      /**
       * Get the default value of SkyAtmosphereColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultSkyAtmosphereColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * A color applied to the atmosphere sun halo. The alpha channel describes how strongly the sun halo is represented in an atmosphere sky layer.
     */
    val defaultSkyAtmosphereHaloColor: String?
      /**
       * Get the default value of SkyAtmosphereHaloColor property
       *
       * @return String
       */
      get() {
        defaultSkyAtmosphereHaloColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * This is an Expression representation of "sky-atmosphere-halo-color".
     *
     * A color applied to the atmosphere sun halo. The alpha channel describes how strongly the sun halo is represented in an atmosphere sky layer.
     */
    val defaultSkyAtmosphereHaloColorAsExpression: Expression?
      /**
       * Get default value of the SkyAtmosphereHaloColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-halo-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * A color applied to the atmosphere sun halo. The alpha channel describes how strongly the sun halo is represented in an atmosphere sky layer.
     */
    val defaultSkyAtmosphereHaloColorAsColorInt: Int?
      /**
       * Get the default value of SkyAtmosphereHaloColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultSkyAtmosphereHaloColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Position of the sun center [a azimuthal angle, p polar angle]. The azimuthal angle indicates the position of the sun relative to 0 degree north, where degrees proceed clockwise. The polar angle indicates the height of the sun, where 0 degree is directly above, at zenith, and 90 degree at the horizon. When this property is ommitted, the sun center is directly inherited from the light position.
     */
    val defaultSkyAtmosphereSun: List<Double>?
      /**
       * Get the default value of SkyAtmosphereSun property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-sun").silentUnwrap()
      }

    /**
     * This is an Expression representation of "sky-atmosphere-sun".
     *
     * Position of the sun center [a azimuthal angle, p polar angle]. The azimuthal angle indicates the position of the sun relative to 0 degree north, where degrees proceed clockwise. The polar angle indicates the height of the sun, where 0 degree is directly above, at zenith, and 90 degree at the horizon. When this property is ommitted, the sun center is directly inherited from the light position.
     */
    val defaultSkyAtmosphereSunAsExpression: Expression?
      /**
       * Get default value of the SkyAtmosphereSun property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-sun").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultSkyAtmosphereSun?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Intensity of the sun as a light source in the atmosphere (on a scale from 0 to a 100). Setting higher values will brighten up the sky.
     */
    val defaultSkyAtmosphereSunIntensity: Double?
      /**
       * Get the default value of SkyAtmosphereSunIntensity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-sun-intensity").silentUnwrap()
      }

    /**
     * This is an Expression representation of "sky-atmosphere-sun-intensity".
     *
     * Intensity of the sun as a light source in the atmosphere (on a scale from 0 to a 100). Setting higher values will brighten up the sky.
     */
    val defaultSkyAtmosphereSunIntensityAsExpression: Expression?
      /**
       * Get default value of the SkyAtmosphereSunIntensity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-atmosphere-sun-intensity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultSkyAtmosphereSunIntensity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Defines a radial color gradient with which to color the sky. The color values can be interpolated with an expression using `sky-radial-progress`. The range [0, 1] for the interpolant covers a radial distance (in degrees) of [0, `sky-gradient-radius`] centered at the position specified by `sky-gradient-center`.
     */
    val defaultSkyGradient: Expression?
      /**
       * Get the default value of SkyGradient property
       *
       * @return Expression
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-gradient").silentUnwrap()
      }

    /**
     * Position of the gradient center [a azimuthal angle, p polar angle]. The azimuthal angle indicates the position of the gradient center relative to 0 degree north, where degrees proceed clockwise. The polar angle indicates the height of the gradient center, where 0 degree is directly above, at zenith, and 90 degree at the horizon.
     */
    val defaultSkyGradientCenter: List<Double>?
      /**
       * Get the default value of SkyGradientCenter property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-gradient-center").silentUnwrap()
      }

    /**
     * This is an Expression representation of "sky-gradient-center".
     *
     * Position of the gradient center [a azimuthal angle, p polar angle]. The azimuthal angle indicates the position of the gradient center relative to 0 degree north, where degrees proceed clockwise. The polar angle indicates the height of the gradient center, where 0 degree is directly above, at zenith, and 90 degree at the horizon.
     */
    val defaultSkyGradientCenterAsExpression: Expression?
      /**
       * Get default value of the SkyGradientCenter property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-gradient-center").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultSkyGradientCenter?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * The angular distance (measured in degrees) from `sky-gradient-center` up to which the gradient extends. A value of 180 causes the gradient to wrap around to the opposite direction from `sky-gradient-center`.
     */
    val defaultSkyGradientRadius: Double?
      /**
       * Get the default value of SkyGradientRadius property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-gradient-radius").silentUnwrap()
      }

    /**
     * This is an Expression representation of "sky-gradient-radius".
     *
     * The angular distance (measured in degrees) from `sky-gradient-center` up to which the gradient extends. A value of 180 causes the gradient to wrap around to the opposite direction from `sky-gradient-center`.
     */
    val defaultSkyGradientRadiusAsExpression: Expression?
      /**
       * Get default value of the SkyGradientRadius property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-gradient-radius").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultSkyGradientRadius?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * The opacity of the entire sky layer.
     */
    val defaultSkyOpacity: Double?
      /**
       * Get the default value of SkyOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-opacity").silentUnwrap()
      }

    /**
     * This is an Expression representation of "sky-opacity".
     *
     * The opacity of the entire sky layer.
     */
    val defaultSkyOpacityAsExpression: Expression?
      /**
       * Get default value of the SkyOpacity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-opacity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultSkyOpacity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for SkyOpacity.
     */
    val defaultSkyOpacityTransition: StyleTransition?
      /**
       * Get the SkyOpacity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-opacity-transition").silentUnwrap()

    /**
     * The type of the sky
     */
    val defaultSkyType: SkyType?
      /**
       * Get the default value of SkyType property
       *
       * @return SkyType
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-type").silentUnwrap<String>()?.let {
          return SkyType.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * This is an Expression representation of "sky-type".
     *
     * The type of the sky
     */
    val defaultSkyTypeAsExpression: Expression?
      /**
       * Get default value of the SkyType property as an Expression
       *
       * @return SkyType
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("sky", "sky-type").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultSkyType?.let {
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
interface SkyLayerDsl {

  /**
   * Set the filter property
   *
   * @param filter the expression filter to set
   */
  fun filter(filter: Expression): SkyLayer

  /**
   * Set the Visibility property
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): SkyLayer

  /**
   * Set the minzoom property
   *
   * @param value value of minzoom
   */
  fun minZoom(minZoom: Double): SkyLayer

  /**
   * Set the maxzoom property
   *
   * @param value value of maxzoom
   */
  fun maxZoom(maxZoom: Double): SkyLayer

  // Property getters and setters

  /**
   * Set the SkyAtmosphereColor property
   *
   * @param skyAtmosphereColor value of skyAtmosphereColor
   */
  fun skyAtmosphereColor(skyAtmosphereColor: String = "white"): SkyLayer

  /**
   * Set the SkyAtmosphereColor property
   *
   * @param skyAtmosphereColor value of skyAtmosphereColor as Expression
   */
  fun skyAtmosphereColor(skyAtmosphereColor: Expression): SkyLayer

  /**
   * Set the SkyAtmosphereColor property.
   *
   * @param skyAtmosphereColor value of skyAtmosphereColor
   */
  fun skyAtmosphereColor(@ColorInt skyAtmosphereColor: Int): SkyLayer

  /**
   * Set the SkyAtmosphereHaloColor property
   *
   * @param skyAtmosphereHaloColor value of skyAtmosphereHaloColor
   */
  fun skyAtmosphereHaloColor(skyAtmosphereHaloColor: String = "white"): SkyLayer

  /**
   * Set the SkyAtmosphereHaloColor property
   *
   * @param skyAtmosphereHaloColor value of skyAtmosphereHaloColor as Expression
   */
  fun skyAtmosphereHaloColor(skyAtmosphereHaloColor: Expression): SkyLayer

  /**
   * Set the SkyAtmosphereHaloColor property.
   *
   * @param skyAtmosphereHaloColor value of skyAtmosphereHaloColor
   */
  fun skyAtmosphereHaloColor(@ColorInt skyAtmosphereHaloColor: Int): SkyLayer

  /**
   * Set the SkyAtmosphereSun property
   *
   * @param skyAtmosphereSun value of skyAtmosphereSun
   */
  fun skyAtmosphereSun(skyAtmosphereSun: List<Double>): SkyLayer

  /**
   * Set the SkyAtmosphereSun property
   *
   * @param skyAtmosphereSun value of skyAtmosphereSun as Expression
   */
  fun skyAtmosphereSun(skyAtmosphereSun: Expression): SkyLayer

  /**
   * Set the SkyAtmosphereSunIntensity property
   *
   * @param skyAtmosphereSunIntensity value of skyAtmosphereSunIntensity
   */
  fun skyAtmosphereSunIntensity(skyAtmosphereSunIntensity: Double = 10.0): SkyLayer

  /**
   * Set the SkyAtmosphereSunIntensity property
   *
   * @param skyAtmosphereSunIntensity value of skyAtmosphereSunIntensity as Expression
   */
  fun skyAtmosphereSunIntensity(skyAtmosphereSunIntensity: Expression): SkyLayer

  /**
   * Set the SkyGradient property
   *
   * @param skyGradient value of skyGradient
   */
  fun skyGradient(skyGradient: Expression): SkyLayer

  /**
   * Set the SkyGradientCenter property
   *
   * @param skyGradientCenter value of skyGradientCenter
   */
  fun skyGradientCenter(skyGradientCenter: List<Double> = listOf(0.0, 0.0)): SkyLayer

  /**
   * Set the SkyGradientCenter property
   *
   * @param skyGradientCenter value of skyGradientCenter as Expression
   */
  fun skyGradientCenter(skyGradientCenter: Expression): SkyLayer

  /**
   * Set the SkyGradientRadius property
   *
   * @param skyGradientRadius value of skyGradientRadius
   */
  fun skyGradientRadius(skyGradientRadius: Double = 90.0): SkyLayer

  /**
   * Set the SkyGradientRadius property
   *
   * @param skyGradientRadius value of skyGradientRadius as Expression
   */
  fun skyGradientRadius(skyGradientRadius: Expression): SkyLayer

  /**
   * Set the SkyOpacity property
   *
   * @param skyOpacity value of skyOpacity
   */
  fun skyOpacity(skyOpacity: Double = 1.0): SkyLayer

  /**
   * Set the SkyOpacity property
   *
   * @param skyOpacity value of skyOpacity as Expression
   */
  fun skyOpacity(skyOpacity: Expression): SkyLayer

  /**
   * Set the SkyOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun skyOpacityTransition(options: StyleTransition): SkyLayer

  /**
   * DSL for [skyOpacityTransition].
   */
  fun skyOpacityTransition(block: StyleTransition.Builder.() -> Unit): SkyLayer

  /**
   * Set the SkyType property
   *
   * @param skyType value of skyType
   */
  fun skyType(skyType: SkyType = SkyType.ATMOSPHERE): SkyLayer

  /**
   * Set the SkyType property
   *
   * @param skyType value of skyType as Expression
   */
  fun skyType(skyType: Expression): SkyLayer
}

/**
 * DSL function for [SkyLayer].
 */
fun skyLayer(layerId: String, block: SkyLayerDsl.() -> Unit): SkyLayer = SkyLayer(layerId).apply(block)

// End of generated file.