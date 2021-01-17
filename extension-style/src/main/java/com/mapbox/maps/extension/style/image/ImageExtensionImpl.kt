package com.mapbox.maps.extension.style.image

import android.content.res.Resources
import android.graphics.Bitmap
import com.mapbox.maps.Image
import com.mapbox.maps.ImageContent
import com.mapbox.maps.ImageStretches
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.utils.check
import java.nio.ByteBuffer

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
  override fun bindTo(delegate: StyleManagerInterface) {
    delegate.addStyleImage(
      builder.imageId,
      builder.scale,
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
  class Builder(
    /**
     * ID of the image.
     */
    val imageId: String
  ) {
    /**
     * Pixel data of the image.
     */
    internal lateinit var internalImage: Image

    /**
     * Scale factor for the image.
     */
    internal var scale: Float = Resources.getSystem().displayMetrics.density

    /**
     * Option to treat whether image is SDF(signed distance field) or not.
     */
    internal var sdf: Boolean = false

    /**
     * An array of two-element arrays, consisting of two numbers that represent
     * the from position and the to position of areas that can be stretched horizontally.
     */
    internal val stretchX = mutableListOf<ImageStretches>()

    /**
     * An array of two-element arrays, consisting of two numbers that represent
     * the from position and the to position of areas that can be stretched vertically.
     */
    internal val stretchY = mutableListOf<ImageStretches>()

    /**
     * An array of four numbers, with the first two specifying the left, top
     * corner, and the last two specifying the right, bottom corner. If present, and if the
     * icon uses icon-text-fit, the symbol's text will be fit inside the content box.
     */
    internal var content: ImageContent? = null

    /**
     * Pixel data of the image.
     */
    fun image(image: Image) = apply {
      this.internalImage = image
    }

    /**
     * Set bitmap data of the image.
     */
    fun bitmap(bitmap: Bitmap) = apply {
      if (bitmap.config != Bitmap.Config.ARGB_8888) {
        throw RuntimeException("Only ARGB_8888 bitmap config is supported!")
      }
      val byteBuffer = ByteBuffer.allocate(bitmap.byteCount)
      bitmap.copyPixelsToBuffer(byteBuffer)
      this.internalImage = Image(bitmap.width, bitmap.height, byteBuffer.array())
    }

    /**
     * Scale factor for the image.
     */
    fun scale(scale: Float) = apply {
      this.scale = scale
    }

    /**
     * Option to treat whether image is SDF(signed distance field) or not.
     */
    fun sdf(sdf: Boolean = false) = apply {
      this.sdf = sdf
    }

    /**
     * An array of two-element arrays, consisting of two numbers that represent
     * the from position and the to position of areas that can be stretched horizontally.
     */
    fun stretchX(stretchX: List<ImageStretches> = listOf()) = apply {
      this.stretchX.addAll(stretchX)
    }

    /**
     * An array of two-element arrays, consisting of two numbers that represent
     * the from position and the to position of areas that can be stretched vertically.
     */
    fun stretchY(stretchY: List<ImageStretches> = listOf()) = apply {
      this.stretchY.addAll(stretchY)
    }

    /**
     * An array of four numbers, with the first two specifying the left, top
     * corner, and the last two specifying the right, bottom corner. If present, and if the
     * icon uses icon-text-fit, the symbol's text will be fit inside the content box.
     */
    fun content(content: ImageContent) = apply {
      this.content = content
    }

    /**
     * Build the ImagePluginImpl.
     *
     * @return ImagePluginImpl
     */
    fun build(): ImageExtensionImpl {
      if (!this::internalImage.isInitialized) {
        throw RuntimeException("An image plugin requires an image input.")
      }
      return ImageExtensionImpl(this)
    }
  }
}

/**
 * DSL function for [ImageExtensionImpl].
 */
fun image(imageId: String, block: ImageExtensionImpl.Builder.() -> Unit): ImageExtensionImpl =
  ImageExtensionImpl.Builder(imageId).apply(block).build()

/**
 * Extension function to add an image provided by the Style Plugin to the Style.
 *
 * @param image The image to be added
 */
fun StyleManagerInterface.addImage(image: StyleContract.StyleImageExtension) {
  image.bindTo(this)
}