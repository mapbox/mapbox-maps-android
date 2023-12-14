// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

import com.mapbox.maps.StyleManager
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
   * URL that points to an image. If the URL is not specified, the image is expected
   * to be loaded directly during runtime.
   */
  fun url(value: String): ImageSource = apply {
    setProperty(PropertyValue("url", TypeUtils.wrapToValue(value)))
  }

  /**
   * URL that points to an image. If the URL is not specified, the image is expected
   * to be loaded directly during runtime.
   */
  val url: String?
    /**
     * Get the Url property
     *
     * @return String
     */
    get() = getPropertyValue("url")

  /**
   * Corners of image specified in longitude, latitude pairs. Note: When using globe projection, the image will
   * be centered at the North or South Pole in the respective hemisphere if the average latitude
   * value exceeds 85 degrees or falls below -85 degrees.
   */
  fun coordinates(value: List<List<Double>>): ImageSource = apply {
    setProperty(PropertyValue("coordinates", TypeUtils.wrapToValue(value)))
  }

  /**
   * Corners of image specified in longitude, latitude pairs. Note: When using globe projection, the image will
   * be centered at the North or South Pole in the respective hemisphere if the average latitude
   * value exceeds 85 degrees or falls below -85 degrees.
   */
  val coordinates: List<List<Double>>?
    /**
     * Get the Coordinates property
     *
     * @return List<List<Double>>
     */
    get() = getPropertyValue("coordinates")

  /**
   * When loading a map, if PrefetchZoomDelta is set to any number greater than 0, the map
   * will first request a tile at zoom level lower than zoom - delta, but so that
   * the zoom level is multiple of delta, in an attempt to display a full map at
   * lower resolution as quick as possible. It will get clamped at the tile source minimum zoom.
   * The default delta is 4.
   */
  fun prefetchZoomDelta(value: Long = 4L): ImageSource = apply {
    setVolatileProperty(PropertyValue("prefetch-zoom-delta", TypeUtils.wrapToValue(value)))
  }

  /**
   * When loading a map, if PrefetchZoomDelta is set to any number greater than 0, the map
   * will first request a tile at zoom level lower than zoom - delta, but so that
   * the zoom level is multiple of delta, in an attempt to display a full map at
   * lower resolution as quick as possible. It will get clamped at the tile source minimum zoom.
   * The default delta is 4.
   */
  val prefetchZoomDelta: Long?
    /**
     * Get the PrefetchZoomDelta property
     *
     * Use static method [ImageSource.defaultPrefetchZoomDelta] to get the default property.
     *
     * @return Long
     */
    get() = getPropertyValue("prefetch-zoom-delta")

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
     * URL that points to an image. If the URL is not specified, the image is expected
     * to be loaded directly during runtime.
     */
    fun url(value: String): Builder = apply {
      val propertyValue = PropertyValue("url", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Corners of image specified in longitude, latitude pairs. Note: When using globe projection, the image will
     * be centered at the North or South Pole in the respective hemisphere if the average latitude
     * value exceeds 85 degrees or falls below -85 degrees.
     */
    fun coordinates(value: List<List<Double>>): Builder = apply {
      val propertyValue = PropertyValue("coordinates", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * When loading a map, if PrefetchZoomDelta is set to any number greater than 0, the map
     * will first request a tile at zoom level lower than zoom - delta, but so that
     * the zoom level is multiple of delta, in an attempt to display a full map at
     * lower resolution as quick as possible. It will get clamped at the tile source minimum zoom.
     * The default delta is 4.
     */
    fun prefetchZoomDelta(value: Long = 4L): Builder = apply {
      val propertyValue = PropertyValue("prefetch-zoom-delta", TypeUtils.wrapToValue(value))
      volatileProperties[propertyValue.propertyName] = propertyValue
    }
    /**
     * Build the ImageSource.
     *
     * @return the ImageSource
     */
    fun build(): ImageSource = ImageSource(this)
  }

  /**
   * Static variables and methods.
   */
  companion object {

    /**
     * When loading a map, if PrefetchZoomDelta is set to any number greater than 0, the map
     * will first request a tile at zoom level lower than zoom - delta, but so that
     * the zoom level is multiple of delta, in an attempt to display a full map at
     * lower resolution as quick as possible. It will get clamped at the tile source minimum zoom.
     * The default delta is 4.
     */
    val defaultPrefetchZoomDelta: Long?
      /**
       * Get the PrefetchZoomDelta property
       *
       * @return Long
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("image", "prefetch-zoom-delta").silentUnwrap()
  }
}

/**
 * DSL function for [ImageSource].
 */
fun imageSource(id: String, block: ImageSource.Builder.() -> Unit): ImageSource =
  ImageSource.Builder(id).apply(block).build()

// End of generated file.