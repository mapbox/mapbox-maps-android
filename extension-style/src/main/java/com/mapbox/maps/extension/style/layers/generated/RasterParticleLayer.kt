// This file is generated.

package com.mapbox.maps.extension.style.layers.generated

import androidx.annotation.UiThread
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.Layer
import com.mapbox.maps.extension.style.layers.properties.*
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.types.*
import com.mapbox.maps.extension.style.utils.silentUnwrap
import java.util.*

/**
 * Particle animation driven by textures such as wind maps.
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/layers/#raster-particle)
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
 */
@UiThread
@MapboxExperimental
class RasterParticleLayer(override val layerId: String, val sourceId: String) : RasterParticleLayerDsl, Layer() {
  init {
    internalSourceId = sourceId
  }

  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  override fun sourceLayer(sourceLayer: String): RasterParticleLayer = apply {
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
  override fun slot(slot: String): RasterParticleLayer = apply {
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
  override fun filter(filter: Expression): RasterParticleLayer = apply {
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
     * Use static method [RasterParticleLayer.defaultVisibility] to get the default property value.
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
     * Use static method [RasterParticleLayer.defaultVisibility] to get the default property value.
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
   * Use static method [RasterParticleLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Visibility): RasterParticleLayer = apply {
    val propertyValue = PropertyValue("visibility", visibility)
    setProperty(propertyValue)
  }

  /**
   * Whether this layer is displayed.
   *
   * Use static method [[RasterParticleLayer.defaultVisibility] to get the default property value.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Expression): RasterParticleLayer = apply {
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
     * Use static method [RasterParticleLayer.defaultMinZoom] to get the default property value.
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
   * Use static method [RasterParticleLayer.defaultMinZoom] to get the default property value.
   *
   * @param minZoom value of minzoom
   */
  override fun minZoom(minZoom: Double): RasterParticleLayer = apply {
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
     * Use static method [RasterParticleLayer.defaultMaxZoom] to get the default property value.
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
   * Use static method [RasterParticleLayer.defaultMaxZoom] to get the default property value.
   *
   * @param maxZoom value of maxzoom
   */
  override fun maxZoom(maxZoom: Double): RasterParticleLayer = apply {
    val param = PropertyValue("maxzoom", maxZoom)
    setProperty(param)
  }

  // Property getters and setters

  /**
   * Displayed band of raster array source layer
   */
  @MapboxExperimental
  val rasterParticleArrayBand: String?
    /**
     * Displayed band of raster array source layer
     *
     * Use static method [RasterParticleLayer.defaultRasterParticleArrayBand] to get the default property.
     *
     * @return String
     */
    get() {
      return getPropertyValue("raster-particle-array-band")
    }

  /**
   * Displayed band of raster array source layer
   *
   * Use static method [RasterParticleLayer.defaultRasterParticleArrayBand] to set the default property.
   *
   * @param rasterParticleArrayBand value of rasterParticleArrayBand
   */
  @MapboxExperimental
  override fun rasterParticleArrayBand(rasterParticleArrayBand: String): RasterParticleLayer = apply {
    val propertyValue = PropertyValue("raster-particle-array-band", rasterParticleArrayBand)
    setProperty(propertyValue)
  }

  /**
   * Displayed band of raster array source layer
   *
   * This is an Expression representation of "raster-particle-array-band".
   *
   */
  @MapboxExperimental
  val rasterParticleArrayBandAsExpression: Expression?
    /**
     * Displayed band of raster array source layer
     *
     * Get the RasterParticleArrayBand property as an Expression
     *
     * Use static method [RasterParticleLayer.defaultRasterParticleArrayBandAsExpression] to get the default property.
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("raster-particle-array-band")?.let {
        return it
      }
      rasterParticleArrayBand?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Displayed band of raster array source layer
   *
   * Use static method [RasterParticleLayer.defaultRasterParticleArrayBandAsExpression] to set the default property.
   *
   * @param rasterParticleArrayBand value of rasterParticleArrayBand as Expression
   */
  @MapboxExperimental
  override fun rasterParticleArrayBand(rasterParticleArrayBand: Expression): RasterParticleLayer = apply {
    val propertyValue = PropertyValue("raster-particle-array-band", rasterParticleArrayBand)
    setProperty(propertyValue)
  }

  /**
   * Defines a color map by which to colorize a raster particle layer, parameterized by the `["raster-particle-speed"]` expression and evaluated at 256 uniformly spaced steps over the range specified by `raster-particle-max-speed`.
   */
  @MapboxExperimental
  val rasterParticleColor: Expression?
    /**
     * Defines a color map by which to colorize a raster particle layer, parameterized by the `["raster-particle-speed"]` expression and evaluated at 256 uniformly spaced steps over the range specified by `raster-particle-max-speed`.
     *
     * Use static method [RasterParticleLayer.defaultRasterParticleColor] to get the default property.
     *
     * @return Expression
     */
    get() {
      return getPropertyValue("raster-particle-color")
    }

  /**
   * Defines a color map by which to colorize a raster particle layer, parameterized by the `["raster-particle-speed"]` expression and evaluated at 256 uniformly spaced steps over the range specified by `raster-particle-max-speed`.
   *
   * Use static method [RasterParticleLayer.defaultRasterParticleColor] to set the default property.
   *
   * @param rasterParticleColor value of rasterParticleColor
   */
  @MapboxExperimental
  override fun rasterParticleColor(rasterParticleColor: Expression): RasterParticleLayer = apply {
    val propertyValue = PropertyValue("raster-particle-color", rasterParticleColor)
    setProperty(propertyValue)
  }

  /**
   * Сolor theme override for [rasterParticleColor].
   */
  @MapboxExperimental
  val rasterParticleColorUseTheme: String?
    /**
     * Get the RasterParticleColorUseTheme property
     *
     * Use static method [RasterParticleLayer.defaultRasterParticleColorUseTheme] to get the default property.
     *
     * @return current RasterParticleColorUseTheme property as Expression
     */
    get() {
      return getPropertyValue("raster-particle-color-use-theme")
    }

  /**
   * Set the RasterParticleColorUseTheme as String
   *
   * Use static method [RasterParticleLayer.defaultRasterParticleColorUseTheme] to get the default property.
   *
   * @param rasterParticleColorUseTheme theme value for color. Overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  override fun rasterParticleColorUseTheme(rasterParticleColorUseTheme: String): RasterParticleLayer = apply {
    val propertyValue = PropertyValue("raster-particle-color-use-theme", rasterParticleColorUseTheme)
    setProperty(propertyValue)
  }

  /**
   * Defines the amount of particles per tile. Default value: 512. Minimum value: 1.
   */
  @MapboxExperimental
  val rasterParticleCount: Long?
    /**
     * Defines the amount of particles per tile. Default value: 512. Minimum value: 1.
     *
     * Use static method [RasterParticleLayer.defaultRasterParticleCount] to get the default property.
     *
     * @return Long
     */
    get() {
      return getPropertyValue<Number?>("raster-particle-count")?.toLong()
    }

  /**
   * Defines the amount of particles per tile. Default value: 512. Minimum value: 1.
   *
   * Use static method [RasterParticleLayer.defaultRasterParticleCount] to set the default property.
   *
   * @param rasterParticleCount value of rasterParticleCount
   */
  @MapboxExperimental
  override fun rasterParticleCount(rasterParticleCount: Long): RasterParticleLayer = apply {
    val propertyValue = PropertyValue("raster-particle-count", rasterParticleCount)
    setProperty(propertyValue)
  }

  /**
   * Defines the amount of particles per tile. Default value: 512. Minimum value: 1.
   *
   * This is an Expression representation of "raster-particle-count".
   *
   */
  @MapboxExperimental
  val rasterParticleCountAsExpression: Expression?
    /**
     * Defines the amount of particles per tile. Default value: 512. Minimum value: 1.
     *
     * Get the RasterParticleCount property as an Expression
     *
     * Use static method [RasterParticleLayer.defaultRasterParticleCountAsExpression] to get the default property.
     *
     * @return Long
     */
    get() {
      getPropertyValue<Expression>("raster-particle-count")?.let {
        return it
      }
      rasterParticleCount?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Defines the amount of particles per tile. Default value: 512. Minimum value: 1.
   *
   * Use static method [RasterParticleLayer.defaultRasterParticleCountAsExpression] to set the default property.
   *
   * @param rasterParticleCount value of rasterParticleCount as Expression
   */
  @MapboxExperimental
  override fun rasterParticleCount(rasterParticleCount: Expression): RasterParticleLayer = apply {
    val propertyValue = PropertyValue("raster-particle-count", rasterParticleCount)
    setProperty(propertyValue)
  }

  /**
   * Defines defines the opacity coefficient applied to the faded particles in each frame. In practice, this property controls the length of the particle tail. Default value: 0.98. Value range: [0, 1]
   */
  @MapboxExperimental
  val rasterParticleFadeOpacityFactor: Double?
    /**
     * Defines defines the opacity coefficient applied to the faded particles in each frame. In practice, this property controls the length of the particle tail. Default value: 0.98. Value range: [0, 1]
     *
     * Use static method [RasterParticleLayer.defaultRasterParticleFadeOpacityFactor] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("raster-particle-fade-opacity-factor")
    }

  /**
   * Defines defines the opacity coefficient applied to the faded particles in each frame. In practice, this property controls the length of the particle tail. Default value: 0.98. Value range: [0, 1]
   *
   * Use static method [RasterParticleLayer.defaultRasterParticleFadeOpacityFactor] to set the default property.
   *
   * @param rasterParticleFadeOpacityFactor value of rasterParticleFadeOpacityFactor
   */
  @MapboxExperimental
  override fun rasterParticleFadeOpacityFactor(rasterParticleFadeOpacityFactor: Double): RasterParticleLayer = apply {
    val propertyValue = PropertyValue("raster-particle-fade-opacity-factor", rasterParticleFadeOpacityFactor)
    setProperty(propertyValue)
  }

  /**
   * Defines defines the opacity coefficient applied to the faded particles in each frame. In practice, this property controls the length of the particle tail. Default value: 0.98. Value range: [0, 1]
   *
   * This is an Expression representation of "raster-particle-fade-opacity-factor".
   *
   */
  @MapboxExperimental
  val rasterParticleFadeOpacityFactorAsExpression: Expression?
    /**
     * Defines defines the opacity coefficient applied to the faded particles in each frame. In practice, this property controls the length of the particle tail. Default value: 0.98. Value range: [0, 1]
     *
     * Get the RasterParticleFadeOpacityFactor property as an Expression
     *
     * Use static method [RasterParticleLayer.defaultRasterParticleFadeOpacityFactorAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("raster-particle-fade-opacity-factor")?.let {
        return it
      }
      rasterParticleFadeOpacityFactor?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Defines defines the opacity coefficient applied to the faded particles in each frame. In practice, this property controls the length of the particle tail. Default value: 0.98. Value range: [0, 1]
   *
   * Use static method [RasterParticleLayer.defaultRasterParticleFadeOpacityFactorAsExpression] to set the default property.
   *
   * @param rasterParticleFadeOpacityFactor value of rasterParticleFadeOpacityFactor as Expression
   */
  @MapboxExperimental
  override fun rasterParticleFadeOpacityFactor(rasterParticleFadeOpacityFactor: Expression): RasterParticleLayer = apply {
    val propertyValue = PropertyValue("raster-particle-fade-opacity-factor", rasterParticleFadeOpacityFactor)
    setProperty(propertyValue)
  }

  /**
   * Transition options for RasterParticleFadeOpacityFactor.
   */
  @MapboxExperimental
  val rasterParticleFadeOpacityFactorTransition: StyleTransition?
    /**
     * Get the RasterParticleFadeOpacityFactor property transition options
     *
     * Use static method [RasterParticleLayer.defaultRasterParticleFadeOpacityFactorTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("raster-particle-fade-opacity-factor-transition")
    }

  /**
   * Set the RasterParticleFadeOpacityFactor property transition options
   *
   * Use static method [RasterParticleLayer.defaultRasterParticleFadeOpacityFactorTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun rasterParticleFadeOpacityFactorTransition(options: StyleTransition): RasterParticleLayer = apply {
    val propertyValue = PropertyValue("raster-particle-fade-opacity-factor-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [rasterParticleFadeOpacityFactorTransition].
   */
  @MapboxExperimental
  override fun rasterParticleFadeOpacityFactorTransition(block: StyleTransition.Builder.() -> Unit): RasterParticleLayer = apply {
    rasterParticleFadeOpacityFactorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Defines the maximum speed for particles. Velocities with magnitudes equal to or exceeding this value are clamped to the max value. Default value: 1. Minimum value: 1.
   */
  @MapboxExperimental
  val rasterParticleMaxSpeed: Double?
    /**
     * Defines the maximum speed for particles. Velocities with magnitudes equal to or exceeding this value are clamped to the max value. Default value: 1. Minimum value: 1.
     *
     * Use static method [RasterParticleLayer.defaultRasterParticleMaxSpeed] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("raster-particle-max-speed")
    }

  /**
   * Defines the maximum speed for particles. Velocities with magnitudes equal to or exceeding this value are clamped to the max value. Default value: 1. Minimum value: 1.
   *
   * Use static method [RasterParticleLayer.defaultRasterParticleMaxSpeed] to set the default property.
   *
   * @param rasterParticleMaxSpeed value of rasterParticleMaxSpeed
   */
  @MapboxExperimental
  override fun rasterParticleMaxSpeed(rasterParticleMaxSpeed: Double): RasterParticleLayer = apply {
    val propertyValue = PropertyValue("raster-particle-max-speed", rasterParticleMaxSpeed)
    setProperty(propertyValue)
  }

  /**
   * Defines the maximum speed for particles. Velocities with magnitudes equal to or exceeding this value are clamped to the max value. Default value: 1. Minimum value: 1.
   *
   * This is an Expression representation of "raster-particle-max-speed".
   *
   */
  @MapboxExperimental
  val rasterParticleMaxSpeedAsExpression: Expression?
    /**
     * Defines the maximum speed for particles. Velocities with magnitudes equal to or exceeding this value are clamped to the max value. Default value: 1. Minimum value: 1.
     *
     * Get the RasterParticleMaxSpeed property as an Expression
     *
     * Use static method [RasterParticleLayer.defaultRasterParticleMaxSpeedAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("raster-particle-max-speed")?.let {
        return it
      }
      rasterParticleMaxSpeed?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Defines the maximum speed for particles. Velocities with magnitudes equal to or exceeding this value are clamped to the max value. Default value: 1. Minimum value: 1.
   *
   * Use static method [RasterParticleLayer.defaultRasterParticleMaxSpeedAsExpression] to set the default property.
   *
   * @param rasterParticleMaxSpeed value of rasterParticleMaxSpeed as Expression
   */
  @MapboxExperimental
  override fun rasterParticleMaxSpeed(rasterParticleMaxSpeed: Expression): RasterParticleLayer = apply {
    val propertyValue = PropertyValue("raster-particle-max-speed", rasterParticleMaxSpeed)
    setProperty(propertyValue)
  }

  /**
   * Defines a coefficient for a time period at which particles will restart at a random position, to avoid degeneration (empty areas without particles). Default value: 0.8. Value range: [0, 1]
   */
  @MapboxExperimental
  val rasterParticleResetRateFactor: Double?
    /**
     * Defines a coefficient for a time period at which particles will restart at a random position, to avoid degeneration (empty areas without particles). Default value: 0.8. Value range: [0, 1]
     *
     * Use static method [RasterParticleLayer.defaultRasterParticleResetRateFactor] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("raster-particle-reset-rate-factor")
    }

  /**
   * Defines a coefficient for a time period at which particles will restart at a random position, to avoid degeneration (empty areas without particles). Default value: 0.8. Value range: [0, 1]
   *
   * Use static method [RasterParticleLayer.defaultRasterParticleResetRateFactor] to set the default property.
   *
   * @param rasterParticleResetRateFactor value of rasterParticleResetRateFactor
   */
  @MapboxExperimental
  override fun rasterParticleResetRateFactor(rasterParticleResetRateFactor: Double): RasterParticleLayer = apply {
    val propertyValue = PropertyValue("raster-particle-reset-rate-factor", rasterParticleResetRateFactor)
    setProperty(propertyValue)
  }

  /**
   * Defines a coefficient for a time period at which particles will restart at a random position, to avoid degeneration (empty areas without particles). Default value: 0.8. Value range: [0, 1]
   *
   * This is an Expression representation of "raster-particle-reset-rate-factor".
   *
   */
  @MapboxExperimental
  val rasterParticleResetRateFactorAsExpression: Expression?
    /**
     * Defines a coefficient for a time period at which particles will restart at a random position, to avoid degeneration (empty areas without particles). Default value: 0.8. Value range: [0, 1]
     *
     * Get the RasterParticleResetRateFactor property as an Expression
     *
     * Use static method [RasterParticleLayer.defaultRasterParticleResetRateFactorAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("raster-particle-reset-rate-factor")?.let {
        return it
      }
      rasterParticleResetRateFactor?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Defines a coefficient for a time period at which particles will restart at a random position, to avoid degeneration (empty areas without particles). Default value: 0.8. Value range: [0, 1]
   *
   * Use static method [RasterParticleLayer.defaultRasterParticleResetRateFactorAsExpression] to set the default property.
   *
   * @param rasterParticleResetRateFactor value of rasterParticleResetRateFactor as Expression
   */
  @MapboxExperimental
  override fun rasterParticleResetRateFactor(rasterParticleResetRateFactor: Expression): RasterParticleLayer = apply {
    val propertyValue = PropertyValue("raster-particle-reset-rate-factor", rasterParticleResetRateFactor)
    setProperty(propertyValue)
  }

  /**
   * Defines a coefficient for the speed of particles’ motion. Default value: 0.2. Value range: [0, 1]
   */
  @MapboxExperimental
  val rasterParticleSpeedFactor: Double?
    /**
     * Defines a coefficient for the speed of particles’ motion. Default value: 0.2. Value range: [0, 1]
     *
     * Use static method [RasterParticleLayer.defaultRasterParticleSpeedFactor] to get the default property.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("raster-particle-speed-factor")
    }

  /**
   * Defines a coefficient for the speed of particles’ motion. Default value: 0.2. Value range: [0, 1]
   *
   * Use static method [RasterParticleLayer.defaultRasterParticleSpeedFactor] to set the default property.
   *
   * @param rasterParticleSpeedFactor value of rasterParticleSpeedFactor
   */
  @MapboxExperimental
  override fun rasterParticleSpeedFactor(rasterParticleSpeedFactor: Double): RasterParticleLayer = apply {
    val propertyValue = PropertyValue("raster-particle-speed-factor", rasterParticleSpeedFactor)
    setProperty(propertyValue)
  }

  /**
   * Defines a coefficient for the speed of particles’ motion. Default value: 0.2. Value range: [0, 1]
   *
   * This is an Expression representation of "raster-particle-speed-factor".
   *
   */
  @MapboxExperimental
  val rasterParticleSpeedFactorAsExpression: Expression?
    /**
     * Defines a coefficient for the speed of particles’ motion. Default value: 0.2. Value range: [0, 1]
     *
     * Get the RasterParticleSpeedFactor property as an Expression
     *
     * Use static method [RasterParticleLayer.defaultRasterParticleSpeedFactorAsExpression] to get the default property.
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("raster-particle-speed-factor")?.let {
        return it
      }
      rasterParticleSpeedFactor?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Defines a coefficient for the speed of particles’ motion. Default value: 0.2. Value range: [0, 1]
   *
   * Use static method [RasterParticleLayer.defaultRasterParticleSpeedFactorAsExpression] to set the default property.
   *
   * @param rasterParticleSpeedFactor value of rasterParticleSpeedFactor as Expression
   */
  @MapboxExperimental
  override fun rasterParticleSpeedFactor(rasterParticleSpeedFactor: Expression): RasterParticleLayer = apply {
    val propertyValue = PropertyValue("raster-particle-speed-factor", rasterParticleSpeedFactor)
    setProperty(propertyValue)
  }

  /**
   * Transition options for RasterParticleSpeedFactor.
   */
  @MapboxExperimental
  val rasterParticleSpeedFactorTransition: StyleTransition?
    /**
     * Get the RasterParticleSpeedFactor property transition options
     *
     * Use static method [RasterParticleLayer.defaultRasterParticleSpeedFactorTransition] to get the default property.
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("raster-particle-speed-factor-transition")
    }

  /**
   * Set the RasterParticleSpeedFactor property transition options
   *
   * Use static method [RasterParticleLayer.defaultRasterParticleSpeedFactorTransition] to set the default property.
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  override fun rasterParticleSpeedFactorTransition(options: StyleTransition): RasterParticleLayer = apply {
    val propertyValue = PropertyValue("raster-particle-speed-factor-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [rasterParticleSpeedFactorTransition].
   */
  @MapboxExperimental
  override fun rasterParticleSpeedFactorTransition(block: StyleTransition.Builder.() -> Unit): RasterParticleLayer = apply {
    rasterParticleSpeedFactorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Get the type of this layer
   *
   * @return Type of the layer as [String]
   */
  override fun getType(): String {
    return "raster-particle"
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
        StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "visibility").silentUnwrap<String>()?.let {
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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "minzoom").silentUnwrap()

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
      get() = StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "maxzoom").silentUnwrap()

    /**
     * Displayed band of raster array source layer
     */
    @MapboxExperimental
    val defaultRasterParticleArrayBand: String?
      /**
       * Displayed band of raster array source layer
       *
       * Get the default value of RasterParticleArrayBand property
       *
       * @return String
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-array-band").silentUnwrap()
      }

    /**
     * Displayed band of raster array source layer
     *
     * This is an Expression representation of "raster-particle-array-band".
     *
     */
    @MapboxExperimental
    val defaultRasterParticleArrayBandAsExpression: Expression?
      /**
       * Get default value of the RasterParticleArrayBand property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-array-band").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultRasterParticleArrayBand?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Defines a color map by which to colorize a raster particle layer, parameterized by the `["raster-particle-speed"]` expression and evaluated at 256 uniformly spaced steps over the range specified by `raster-particle-max-speed`.
     */
    @MapboxExperimental
    val defaultRasterParticleColor: Expression?
      /**
       * Defines a color map by which to colorize a raster particle layer, parameterized by the `["raster-particle-speed"]` expression and evaluated at 256 uniformly spaced steps over the range specified by `raster-particle-max-speed`.
       *
       * Get the default value of RasterParticleColor property
       *
       * @return Expression
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-color").silentUnwrap()
      }

    /**
     * Default color theme for [rasterParticleColor].
     */
    @MapboxExperimental
    val defaultRasterParticleColorUseTheme: String?
      /**
       * Get default value of the RasterParticleColor property as String
       *
       * @return Expression
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-color-use-theme").silentUnwrap()

    /**
     * Defines the amount of particles per tile. Default value: 512. Minimum value: 1.
     */
    @MapboxExperimental
    val defaultRasterParticleCount: Long?
      /**
       * Defines the amount of particles per tile. Default value: 512. Minimum value: 1.
       *
       * Get the default value of RasterParticleCount property
       *
       * @return Long
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-count").silentUnwrap<Number?>()?.toLong()
      }

    /**
     * Defines the amount of particles per tile. Default value: 512. Minimum value: 1.
     *
     * This is an Expression representation of "raster-particle-count".
     *
     */
    @MapboxExperimental
    val defaultRasterParticleCountAsExpression: Expression?
      /**
       * Get default value of the RasterParticleCount property as an Expression
       *
       * @return Long
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-count").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultRasterParticleCount?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Defines defines the opacity coefficient applied to the faded particles in each frame. In practice, this property controls the length of the particle tail. Default value: 0.98. Value range: [0, 1]
     */
    @MapboxExperimental
    val defaultRasterParticleFadeOpacityFactor: Double?
      /**
       * Defines defines the opacity coefficient applied to the faded particles in each frame. In practice, this property controls the length of the particle tail. Default value: 0.98. Value range: [0, 1]
       *
       * Get the default value of RasterParticleFadeOpacityFactor property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-fade-opacity-factor").silentUnwrap()
      }

    /**
     * Defines defines the opacity coefficient applied to the faded particles in each frame. In practice, this property controls the length of the particle tail. Default value: 0.98. Value range: [0, 1]
     *
     * This is an Expression representation of "raster-particle-fade-opacity-factor".
     *
     */
    @MapboxExperimental
    val defaultRasterParticleFadeOpacityFactorAsExpression: Expression?
      /**
       * Get default value of the RasterParticleFadeOpacityFactor property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-fade-opacity-factor").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultRasterParticleFadeOpacityFactor?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for RasterParticleFadeOpacityFactor.
     */
    @MapboxExperimental
    val defaultRasterParticleFadeOpacityFactorTransition: StyleTransition?
      /**
       * Get the RasterParticleFadeOpacityFactor property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-fade-opacity-factor-transition").silentUnwrap()

    /**
     * Defines the maximum speed for particles. Velocities with magnitudes equal to or exceeding this value are clamped to the max value. Default value: 1. Minimum value: 1.
     */
    @MapboxExperimental
    val defaultRasterParticleMaxSpeed: Double?
      /**
       * Defines the maximum speed for particles. Velocities with magnitudes equal to or exceeding this value are clamped to the max value. Default value: 1. Minimum value: 1.
       *
       * Get the default value of RasterParticleMaxSpeed property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-max-speed").silentUnwrap()
      }

    /**
     * Defines the maximum speed for particles. Velocities with magnitudes equal to or exceeding this value are clamped to the max value. Default value: 1. Minimum value: 1.
     *
     * This is an Expression representation of "raster-particle-max-speed".
     *
     */
    @MapboxExperimental
    val defaultRasterParticleMaxSpeedAsExpression: Expression?
      /**
       * Get default value of the RasterParticleMaxSpeed property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-max-speed").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultRasterParticleMaxSpeed?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Defines a coefficient for a time period at which particles will restart at a random position, to avoid degeneration (empty areas without particles). Default value: 0.8. Value range: [0, 1]
     */
    @MapboxExperimental
    val defaultRasterParticleResetRateFactor: Double?
      /**
       * Defines a coefficient for a time period at which particles will restart at a random position, to avoid degeneration (empty areas without particles). Default value: 0.8. Value range: [0, 1]
       *
       * Get the default value of RasterParticleResetRateFactor property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-reset-rate-factor").silentUnwrap()
      }

    /**
     * Defines a coefficient for a time period at which particles will restart at a random position, to avoid degeneration (empty areas without particles). Default value: 0.8. Value range: [0, 1]
     *
     * This is an Expression representation of "raster-particle-reset-rate-factor".
     *
     */
    @MapboxExperimental
    val defaultRasterParticleResetRateFactorAsExpression: Expression?
      /**
       * Get default value of the RasterParticleResetRateFactor property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-reset-rate-factor").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultRasterParticleResetRateFactor?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Defines a coefficient for the speed of particles’ motion. Default value: 0.2. Value range: [0, 1]
     */
    @MapboxExperimental
    val defaultRasterParticleSpeedFactor: Double?
      /**
       * Defines a coefficient for the speed of particles’ motion. Default value: 0.2. Value range: [0, 1]
       *
       * Get the default value of RasterParticleSpeedFactor property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-speed-factor").silentUnwrap()
      }

    /**
     * Defines a coefficient for the speed of particles’ motion. Default value: 0.2. Value range: [0, 1]
     *
     * This is an Expression representation of "raster-particle-speed-factor".
     *
     */
    @MapboxExperimental
    val defaultRasterParticleSpeedFactorAsExpression: Expression?
      /**
       * Get default value of the RasterParticleSpeedFactor property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-speed-factor").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultRasterParticleSpeedFactor?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for RasterParticleSpeedFactor.
     */
    @MapboxExperimental
    val defaultRasterParticleSpeedFactorTransition: StyleTransition?
      /**
       * Get the RasterParticleSpeedFactor property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("raster-particle", "raster-particle-speed-factor-transition").silentUnwrap()
  }
}

/**
 * This Interface contains all the functions that will be exposed to Koltin DSL.
 *
 * Separated the DSL receiver class to this interface to avoid IDE code suggestion for
 * property getters.
 */
@MapboxExperimental
@LayersDsl
interface RasterParticleLayerDsl {
  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  fun sourceLayer(sourceLayer: String): RasterParticleLayer

  /**
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   *
   * @param slot value of slot. Setting it to empty string removes the slot.
   */
  fun slot(slot: String): RasterParticleLayer

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
  fun filter(filter: Expression): RasterParticleLayer

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): RasterParticleLayer

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility as Expression
   */
  fun visibility(visibility: Expression): RasterParticleLayer

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param minZoom value of minzoom
   */
  fun minZoom(minZoom: Double): RasterParticleLayer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param maxZoom value of maxzoom
   */
  fun maxZoom(maxZoom: Double): RasterParticleLayer

  // Property getters and setters

  /**
   * Displayed band of raster array source layer
   *
   * @param rasterParticleArrayBand value of rasterParticleArrayBand
   */
  @MapboxExperimental
  fun rasterParticleArrayBand(rasterParticleArrayBand: String): RasterParticleLayer

  /**
   * Displayed band of raster array source layer
   *
   * @param rasterParticleArrayBand value of rasterParticleArrayBand as Expression
   */
  @MapboxExperimental
  fun rasterParticleArrayBand(rasterParticleArrayBand: Expression): RasterParticleLayer

  /**
   * Defines a color map by which to colorize a raster particle layer, parameterized by the `["raster-particle-speed"]` expression and evaluated at 256 uniformly spaced steps over the range specified by `raster-particle-max-speed`.
   *
   * @param rasterParticleColor value of rasterParticleColor
   */
  @MapboxExperimental
  fun rasterParticleColor(rasterParticleColor: Expression): RasterParticleLayer

  /**
   * Set the rasterParticleColorUseTheme as String for [rasterParticleColor].
   *
   * @param rasterParticleColorUseTheme overrides applying of color theme if "none" string value is set. To follow default theme "default" sting value should be set.
   */
  @MapboxExperimental
  fun rasterParticleColorUseTheme(rasterParticleColorUseTheme: String): RasterParticleLayer

  /**
   * Defines the amount of particles per tile. Default value: 512. Minimum value: 1.
   *
   * @param rasterParticleCount value of rasterParticleCount
   */
  @MapboxExperimental
  fun rasterParticleCount(rasterParticleCount: Long = 512L): RasterParticleLayer

  /**
   * Defines the amount of particles per tile. Default value: 512. Minimum value: 1.
   *
   * @param rasterParticleCount value of rasterParticleCount as Expression
   */
  @MapboxExperimental
  fun rasterParticleCount(rasterParticleCount: Expression): RasterParticleLayer

  /**
   * Defines defines the opacity coefficient applied to the faded particles in each frame. In practice, this property controls the length of the particle tail. Default value: 0.98. Value range: [0, 1]
   *
   * @param rasterParticleFadeOpacityFactor value of rasterParticleFadeOpacityFactor
   */
  @MapboxExperimental
  fun rasterParticleFadeOpacityFactor(rasterParticleFadeOpacityFactor: Double = 0.98): RasterParticleLayer

  /**
   * Defines defines the opacity coefficient applied to the faded particles in each frame. In practice, this property controls the length of the particle tail. Default value: 0.98. Value range: [0, 1]
   *
   * @param rasterParticleFadeOpacityFactor value of rasterParticleFadeOpacityFactor as Expression
   */
  @MapboxExperimental
  fun rasterParticleFadeOpacityFactor(rasterParticleFadeOpacityFactor: Expression): RasterParticleLayer

  /**
   * Defines defines the opacity coefficient applied to the faded particles in each frame. In practice, this property controls the length of the particle tail. Default value: 0.98. Value range: [0, 1]
   *
   * Set the RasterParticleFadeOpacityFactor property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun rasterParticleFadeOpacityFactorTransition(options: StyleTransition): RasterParticleLayer

  /**
   * Defines defines the opacity coefficient applied to the faded particles in each frame. In practice, this property controls the length of the particle tail. Default value: 0.98. Value range: [0, 1]
   *
   * DSL for [rasterParticleFadeOpacityFactorTransition].
   */
  @MapboxExperimental
  fun rasterParticleFadeOpacityFactorTransition(block: StyleTransition.Builder.() -> Unit): RasterParticleLayer

  /**
   * Defines the maximum speed for particles. Velocities with magnitudes equal to or exceeding this value are clamped to the max value. Default value: 1. Minimum value: 1.
   *
   * @param rasterParticleMaxSpeed value of rasterParticleMaxSpeed
   */
  @MapboxExperimental
  fun rasterParticleMaxSpeed(rasterParticleMaxSpeed: Double = 1.0): RasterParticleLayer

  /**
   * Defines the maximum speed for particles. Velocities with magnitudes equal to or exceeding this value are clamped to the max value. Default value: 1. Minimum value: 1.
   *
   * @param rasterParticleMaxSpeed value of rasterParticleMaxSpeed as Expression
   */
  @MapboxExperimental
  fun rasterParticleMaxSpeed(rasterParticleMaxSpeed: Expression): RasterParticleLayer

  /**
   * Defines a coefficient for a time period at which particles will restart at a random position, to avoid degeneration (empty areas without particles). Default value: 0.8. Value range: [0, 1]
   *
   * @param rasterParticleResetRateFactor value of rasterParticleResetRateFactor
   */
  @MapboxExperimental
  fun rasterParticleResetRateFactor(rasterParticleResetRateFactor: Double = 0.8): RasterParticleLayer

  /**
   * Defines a coefficient for a time period at which particles will restart at a random position, to avoid degeneration (empty areas without particles). Default value: 0.8. Value range: [0, 1]
   *
   * @param rasterParticleResetRateFactor value of rasterParticleResetRateFactor as Expression
   */
  @MapboxExperimental
  fun rasterParticleResetRateFactor(rasterParticleResetRateFactor: Expression): RasterParticleLayer

  /**
   * Defines a coefficient for the speed of particles’ motion. Default value: 0.2. Value range: [0, 1]
   *
   * @param rasterParticleSpeedFactor value of rasterParticleSpeedFactor
   */
  @MapboxExperimental
  fun rasterParticleSpeedFactor(rasterParticleSpeedFactor: Double = 0.2): RasterParticleLayer

  /**
   * Defines a coefficient for the speed of particles’ motion. Default value: 0.2. Value range: [0, 1]
   *
   * @param rasterParticleSpeedFactor value of rasterParticleSpeedFactor as Expression
   */
  @MapboxExperimental
  fun rasterParticleSpeedFactor(rasterParticleSpeedFactor: Expression): RasterParticleLayer

  /**
   * Defines a coefficient for the speed of particles’ motion. Default value: 0.2. Value range: [0, 1]
   *
   * Set the RasterParticleSpeedFactor property transition options
   *
   * @param options transition options for Double
   */
  @MapboxExperimental
  fun rasterParticleSpeedFactorTransition(options: StyleTransition): RasterParticleLayer

  /**
   * Defines a coefficient for the speed of particles’ motion. Default value: 0.2. Value range: [0, 1]
   *
   * DSL for [rasterParticleSpeedFactorTransition].
   */
  @MapboxExperimental
  fun rasterParticleSpeedFactorTransition(block: StyleTransition.Builder.() -> Unit): RasterParticleLayer
}

/**
 * DSL function for creating a [RasterParticleLayer].
 */
@MapboxExperimental
fun rasterParticleLayer(layerId: String, sourceId: String, block: RasterParticleLayerDsl.() -> Unit): RasterParticleLayer = RasterParticleLayer(layerId, sourceId).apply(block)

// End of generated file.