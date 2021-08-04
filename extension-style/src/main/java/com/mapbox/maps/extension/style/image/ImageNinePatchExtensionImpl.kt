package com.mapbox.maps.extension.style.image

import android.graphics.Bitmap
import com.mapbox.maps.Image
import com.mapbox.maps.ImageContent
import com.mapbox.maps.ImageStretches
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.utils.check

/**
 * Concrete implementation of ImagePlugin (see [ImageExtensionImpl])
 * but taking 9-patch [Bitmap] in order to calculate stretchX, stretchY and paddings automatically.
 */
class ImageNinePatchExtensionImpl(private val builder: Builder) : StyleContract.StyleImageExtension {
  /**
   * Add the image to the style.
   */
  override fun bindTo(delegate: StyleInterface) {
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
   * Builder for the [ImageNinePatchExtensionImpl].
   */
  class Builder(
    /**
     * ID of the image.
     */
    val imageId: String,
    /**
     * Actual 9-patch bitmap.
     */
    val bitmap: Bitmap
  ) {

    /**
     * Pixel data of the image.
     */
    internal var internalImage: Image

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

    init {
      if (bitmap.config != Bitmap.Config.ARGB_8888) {
        throw RuntimeException("Only ARGB_8888 bitmap config is supported!")
      }
      bitmap.parse9PatchBitmap().also {
        internalImage = it.internalImage
        content = it.imageContent
        stretchX = it.stretchX
        stretchY = it.stretchY
      }
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
     * Build the ImageNinePatchExtensionImpl.
     *
     * @return ImageNinePatchExtensionImpl
     */
    fun build(): ImageNinePatchExtensionImpl {
      return ImageNinePatchExtensionImpl(this)
    }
  }
}

/**
 * DSL function for [ImageNinePatchExtensionImpl].
 *
 * @param imageId id
 * @param bitmap [Bitmap] that must be be in 9-patch format or [RuntimeException] will be thrown.
 * @param block optional block for additional parameters
 */
fun image9Patch(imageId: String, bitmap: Bitmap, block: (ImageNinePatchExtensionImpl.Builder.() -> Unit)? = null): ImageNinePatchExtensionImpl =
  block?.let {
    ImageNinePatchExtensionImpl.Builder(imageId, bitmap).apply(it).build()
  } ?: ImageNinePatchExtensionImpl.Builder(imageId, bitmap).build()

/**
 * Extension function to add 9-patch image provided by the Style Extension to the Style.
 *
 * @param image The image to be added
 */
fun StyleInterface.addImage9Patch(image: StyleContract.StyleImageExtension) {
  image.bindTo(this)
}