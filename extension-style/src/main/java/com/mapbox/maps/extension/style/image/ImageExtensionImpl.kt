package com.mapbox.maps.extension.style.image

import android.graphics.Bitmap
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.utils.check

/**
 * Concrete implementation of ImagePlugin, the plugin is used to add an image to be used in the style.
 *
 * This API can also be used for updating an image. If the image id was already added, it gets replaced by the new image.
 *
 * The image can be used in `icon-image`, `fill-pattern`, and `line-pattern`.
 *
 * See [https://www.mapbox.com/mapbox-gl-js/style-spec/#layout-symbol-icon-image](https://www.mapbox.com/mapbox-gl-js/style-spec/#layout-symbol-icon-image)
 * See [https://www.mapbox.com/mapbox-gl-js/style-spec/#paint-line-line-pattern](https://www.mapbox.com/mapbox-gl-js/style-spec/#paint-line-line-pattern)
 * See [https://www.mapbox.com/mapbox-gl-js/style-spec/#paint-fill-fill-pattern](https://www.mapbox.com/mapbox-gl-js/style-spec/#paint-fill-fill-pattern)
 */
class ImageExtensionImpl(private val builder: Builder) : StyleContract.StyleImageExtension {
  /**
   * Add the image to the style.
   */
  override fun bindTo(delegate: MapboxStyleManager) {
    delegate.addStyleImage(
      builder.imageId,
      builder.scale ?: delegate.pixelRatio,
      builder.internalImage,
      builder.sdf,
      builder.stretchX,
      builder.stretchY,
      builder.content
    ).check()
  }

  /**
   * Builder for the [ImageExtensionImpl].
   */
  class Builder {
    /**
     * The Id of the image.
     */
    val imageId: String

    /**
     * Pixel data of the image.
     */
    internal lateinit var internalImage: Image

    /**
     * Constructor for the [ImageExtensionImpl.Builder].
     *
     * @param imageId the Id of the image extension.
     */
    @Deprecated(
      "Constructing `ImageExtensionImpl.Builder` without image or bitmap is deprecated, as the image or bitmap is a required field. " +
        "Please switch to `Builder(imageId: String, image: Image)` or `Builder(imageId: String, bitmap: Bitmap)` instead.",
      replaceWith = ReplaceWith("ImageExtensionImpl.Builder(imageId, image)")
    )
    constructor(imageId: String) {
      this.imageId = imageId
    }

    /**
     * Constructor for the [ImageExtensionImpl.Builder].
     *
     * @param imageId the id the the image extension
     * @param image the pixel data of the image.
     */
    constructor(imageId: String, image: Image) {
      this.imageId = imageId
      this.internalImage = image
    }

    /**
     * Constructor for the [ImageExtensionImpl.Builder].
     *
     * @param imageId the id the the image extension
     * @param bitmap the bitmap data of the image.
     */
    @OptIn(MapboxDelicateApi::class)
    constructor(imageId: String, bitmap: Bitmap) {
      this.imageId = imageId
      this.internalImage = bitmap.toMapboxImage()
    }

    /**
     * Scale factor for the image.
     */
    internal var scale: Float? = null

    /**
     * Option to treat whether image is SDF(signed distance field) or not.
     */
    internal var sdf: Boolean = false

    /**
     * An array of two-element arrays, consisting of two numbers that represent
     * the from position and the to position of areas that can be stretched horizontally.
     */
    internal var stretchX = listOf<ImageStretches>()

    /**
     * An array of two-element arrays, consisting of two numbers that represent
     * the from position and the to position of areas that can be stretched vertically.
     */
    internal var stretchY = listOf<ImageStretches>()

    /**
     * An array of four numbers, with the first two specifying the left, top
     * corner, and the last two specifying the right, bottom corner. If present, and if the
     * icon uses icon-text-fit, the symbol's text will be fit inside the content box.
     */
    internal var content: ImageContent? = null

    /**
     * Pixel data of the image.
     */
    @Suppress("DeprecatedCallableAddReplaceWith")
    @Deprecated("Configuring image through `image` function is deprecated, pass image to the `Builder(imageId: String, image: Image)` constructor instead.")
    fun image(image: Image): Builder = apply {
      this.internalImage = image
    }

    /**
     * Set bitmap data of the image.
     */
    @OptIn(MapboxDelicateApi::class)
    @Suppress("DeprecatedCallableAddReplaceWith")
    @Deprecated("Configuring image through `bitmap` function is deprecated, pass image to the `Builder(imageId: String, bitmap: Bitmap)` constructor instead.")
    fun bitmap(bitmap: Bitmap): Builder = apply {
      this.internalImage = bitmap.toMapboxImage()
    }

    /**
     * Scale factor for the image.
     */
    fun scale(scale: Float): Builder = apply {
      this.scale = scale
    }

    /**
     * Option to treat whether image is SDF(signed distance field) or not.
     */
    fun sdf(sdf: Boolean = false): Builder = apply {
      this.sdf = sdf
    }

    /**
     * An array of two-element arrays, consisting of two numbers that represent
     * the from position and the to position of areas that can be stretched horizontally.
     */
    fun stretchX(stretchX: List<ImageStretches> = listOf()): Builder = apply {
      this.stretchX = stretchX
    }

    /**
     * An array of two-element arrays, consisting of two numbers that represent
     * the from position and the to position of areas that can be stretched vertically.
     */
    fun stretchY(stretchY: List<ImageStretches> = listOf()): Builder = apply {
      this.stretchY = stretchY
    }

    /**
     * An array of four numbers, with the first two specifying the left, top
     * corner, and the last two specifying the right, bottom corner. If present, and if the
     * icon uses icon-text-fit, the symbol's text will be fit inside the content box.
     */
    fun content(content: ImageContent): Builder = apply {
      this.content = content
    }

    /**
     * Build the ImagePluginImpl.
     *
     * @return ImagePluginImpl
     */
    fun build(): ImageExtensionImpl {
      if (!this::internalImage.isInitialized) {
        throw IllegalStateException("An image plugin requires an image input.")
      }
      return ImageExtensionImpl(this)
    }
  }
}