// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.sources.Source
import com.mapbox.maps.extension.style.types.SourceDsl
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.extension.style.utils.silentUnwrap
import java.util.*

/**
 * An image data source.
 *
 * @see [The online documentation](https://docs.mapbox.com/mapbox-gl-js/style-spec/sources/#image)
 *
 */
class ImageSource(builder: Builder) : Source(builder.sourceId) {

  init {
    sourceProperties.putAll(builder.properties)
    volatileSourceProperties.putAll(builder.volatileProperties)
  }

  /**
   * Get the type of the current source as a String.
   */
  override fun getType(): String {
    return "image"
  }

  /**
   * URL that points to an image.
   */
  fun url(value: String) = apply {
    setProperty(PropertyValue("url", TypeUtils.wrapToValue(value)))
  }

  /**
   * URL that points to an image.
   */
  fun url(value: Expression) = apply {
    setProperty(PropertyValue("url", value))
  }

  /**
   * URL that points to an image.
   */
  val url: String?
    /**
     * Get the Url property
     *
     * @return String
     */
    get() = getPropertyValue("url")

  /**
   * URL that points to an image.
   */
  val urlAsExpression: Expression?
    /**
     * Get the Url property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("url")?.let {
        return it
      }
      url?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Corners of image specified in longitude, latitude pairs.
   */
  fun coordinates(value: List<List<Double>>) = apply {
    setProperty(PropertyValue("coordinates", TypeUtils.wrapToValue(value)))
  }

  /**
   * Corners of image specified in longitude, latitude pairs.
   */
  fun coordinates(value: Expression) = apply {
    setProperty(PropertyValue("coordinates", value))
  }

  /**
   * Corners of image specified in longitude, latitude pairs.
   */
  val coordinates: List<List<Double>>?
    /**
     * Get the Coordinates property
     *
     * @return List<List<Double>>
     */
    get() = getPropertyValue("coordinates")

  /**
   * Corners of image specified in longitude, latitude pairs.
   */
  val coordinatesAsExpression: Expression?
    /**
     * Get the Coordinates property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("coordinates")?.let {
        return it
      }
      coordinates?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * When loading a map, if `PrefetchZoomDelta` is set to any number greater than 0, the map
   * will first request a tile for `zoom - delta` in a attempt to display a full
   * map at lower resolution as quick as possible. It will get clamped at the tile source
   * minimum zoom. The default `delta` is 4.
   */
  fun prefetchZoomDelta(value: Long = 4L) = apply {
    setVolatileProperty(PropertyValue("prefetch-zoom-delta", TypeUtils.wrapToValue(value)))
  }

  /**
   * When loading a map, if `PrefetchZoomDelta` is set to any number greater than 0, the map
   * will first request a tile for `zoom - delta` in a attempt to display a full
   * map at lower resolution as quick as possible. It will get clamped at the tile source
   * minimum zoom. The default `delta` is 4.
   */
  fun prefetchZoomDelta(value: Expression) = apply {
    setVolatileProperty(PropertyValue("prefetch-zoom-delta", value))
  }

  /**
   * When loading a map, if `PrefetchZoomDelta` is set to any number greater than 0, the map
   * will first request a tile for `zoom - delta` in a attempt to display a full
   * map at lower resolution as quick as possible. It will get clamped at the tile source
   * minimum zoom. The default `delta` is 4.
   */
  val prefetchZoomDelta: Long?
    /**
     * Get the PrefetchZoomDelta property
     *
     * @return Long
     */
    get() = getPropertyValue("prefetch-zoom-delta")

  /**
   * When loading a map, if `PrefetchZoomDelta` is set to any number greater than 0, the map
   * will first request a tile for `zoom - delta` in a attempt to display a full
   * map at lower resolution as quick as possible. It will get clamped at the tile source
   * minimum zoom. The default `delta` is 4.
   */
  val prefetchZoomDeltaAsExpression: Expression?
    /**
     * Get the PrefetchZoomDelta property as an Expression
     *
     * @return Expression
     */
    get() {
      getPropertyValue<Expression>("prefetch-zoom-delta")?.let {
        return it
      }
      prefetchZoomDelta?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Builder for ImageSource.
   *
   * @param sourceId the ID of the source
   */
  @SourceDsl
  class Builder(val sourceId: String) {
    internal val properties = HashMap<String, PropertyValue<*>>()
    // Properties that only settable after the source is added to the style.
    internal val volatileProperties = HashMap<String, PropertyValue<*>>()

    /**
     * URL that points to an image.
     */
    fun url(value: String) = apply {
      val propertyValue = PropertyValue("url", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * URL that points to an image.
     */
    fun url(value: Expression) = apply {
      val propertyValue = PropertyValue("url", value)
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Corners of image specified in longitude, latitude pairs.
     */
    fun coordinates(value: List<List<Double>>) = apply {
      val propertyValue = PropertyValue("coordinates", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Corners of image specified in longitude, latitude pairs.
     */
    fun coordinates(value: Expression) = apply {
      val propertyValue = PropertyValue("coordinates", value)
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * When loading a map, if `PrefetchZoomDelta` is set to any number greater than 0, the map
     * will first request a tile for `zoom - delta` in a attempt to display a full
     * map at lower resolution as quick as possible. It will get clamped at the tile source
     * minimum zoom. The default `delta` is 4.
     */
    fun prefetchZoomDelta(value: Long = 4L) = apply {
      val propertyValue = PropertyValue("prefetch-zoom-delta", TypeUtils.wrapToValue(value))
      volatileProperties[propertyValue.propertyName] = propertyValue
    }

    /**
     * When loading a map, if `PrefetchZoomDelta` is set to any number greater than 0, the map
     * will first request a tile for `zoom - delta` in a attempt to display a full
     * map at lower resolution as quick as possible. It will get clamped at the tile source
     * minimum zoom. The default `delta` is 4.
     */
    fun prefetchZoomDelta(value: Expression) = apply {
      val propertyValue = PropertyValue("prefetch-zoom-delta", value)
      volatileProperties[propertyValue.propertyName] = propertyValue
    }
    /**
     * Build the ImageSource.
     *
     * @return the ImageSource
     */
    fun build() = ImageSource(this)
  }

  /**
   * Static variables and methods.
   */
  companion object {

    /**
     * When loading a map, if `PrefetchZoomDelta` is set to any number greater than 0, the map
     * will first request a tile for `zoom - delta` in a attempt to display a full
     * map at lower resolution as quick as possible. It will get clamped at the tile source
     * minimum zoom. The default `delta` is 4.
     */
    val defaultPrefetchZoomDelta: Long?
      /**
       * Get the PrefetchZoomDelta property
       *
       * @return Long
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("image", "prefetch-zoom-delta").silentUnwrap()

    /**
     * When loading a map, if `PrefetchZoomDelta` is set to any number greater than 0, the map
     * will first request a tile for `zoom - delta` in a attempt to display a full
     * map at lower resolution as quick as possible. It will get clamped at the tile source
     * minimum zoom. The default `delta` is 4.
     */
    val defaultPrefetchZoomDeltaAsExpression: Expression?
      /**
       * Get the PrefetchZoomDelta property as an Expression
       *
       * @return Expression
       */
      get() {
        StyleManager.getStyleSourcePropertyDefaultValue("image", "prefetch-zoom-delta").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultPrefetchZoomDelta?.let {
          return Expression.literal(it)
        }
        return null
      }
  }
}

/**
 * DSL function for [ImageSource].
 */
fun imageSource(id: String, block: ImageSource.Builder.() -> Unit): ImageSource =
  ImageSource.Builder(id).apply(block).build()

// End of generated file.