@file:JvmName("ImageUtils")

package com.mapbox.maps.extension.style.image

import android.graphics.Bitmap
import com.mapbox.maps.Image
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.style.StyleContract

/**
 * DSL function for [ImageExtensionImpl].
 *
 * @param imageId the id the the image extension
 * @param block the configuration block
 */
@Deprecated(
  message = "Constructing image without image or bitmap is deprecated. " +
    "Please use `fun image(imageId: String, image: Image, block: ImageExtensionImpl.Builder.() -> Unit): ImageExtensionImpl` " +
    "or `fun image(imageId: String, bitmap: Bitmap, block: ImageExtensionImpl.Builder.() -> Unit): ImageExtensionImpl` instead.",
  replaceWith = ReplaceWith("image(imageId, image, block)")
)
fun image(imageId: String, block: ImageExtensionImpl.Builder.() -> Unit): ImageExtensionImpl =
  ImageExtensionImpl.Builder(imageId).apply(block).build()

/**
 * DSL function for [ImageExtensionImpl].
 *
 * @param imageId the id the the image extension
 * @param image the pixel data of the image.
 * @param block the configuration block
 */
fun image(
  imageId: String,
  image: Image,
  block: ImageExtensionImpl.Builder.() -> Unit = {}
): ImageExtensionImpl =
  ImageExtensionImpl.Builder(imageId, image).apply(block).build()

/**
 * DSL function for [ImageExtensionImpl].
 *
 * @param imageId the id for the image extension
 * @param bitmap the bitmap data of the image.
 * @param block the configuration block
 */
fun image(
  imageId: String,
  bitmap: Bitmap,
  block: ImageExtensionImpl.Builder.() -> Unit = {}
): ImageExtensionImpl =
  ImageExtensionImpl.Builder(imageId, bitmap).apply(block).build()

/**
 * Extension function to add an image provided by the Style Extension to the Style.
 *
 * @param image The image to be added
 */
fun MapboxStyleManager.addImage(image: StyleContract.StyleImageExtension) {
  image.bindTo(this)
}

/**
 * DSL function for [ImageNinePatchExtensionImpl].
 *
 * @param imageId id
 * @param bitmap [Bitmap] that must be be in 9-patch format or [RuntimeException] will be thrown.
 * @param block optional block for additional parameters
 */
@JvmOverloads
fun image9Patch(
  imageId: String,
  bitmap: Bitmap,
  block: (ImageNinePatchExtensionImpl.Builder.() -> Unit)? = null
): ImageNinePatchExtensionImpl =
  block?.let {
    ImageNinePatchExtensionImpl.Builder(imageId, bitmap).apply(it).build()
  } ?: ImageNinePatchExtensionImpl.Builder(imageId, bitmap).build()

/**
 * Extension function to add 9-patch image provided by the Style Extension to the Style.
 *
 * @param image The image to be added
 */
fun MapboxStyleManager.addImage9Patch(image: StyleContract.StyleImageExtension) {
  image.bindTo(this)
}