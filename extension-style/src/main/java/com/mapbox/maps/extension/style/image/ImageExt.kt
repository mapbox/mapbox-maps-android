@file:JvmName("ImageUtils")
package com.mapbox.maps.extension.style.image

import android.graphics.Bitmap
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.StyleInterface

/**
 * DSL function for [ImageExtensionImpl].
 */
fun image(imageId: String, block: ImageExtensionImpl.Builder.() -> Unit): ImageExtensionImpl =
  ImageExtensionImpl.Builder(imageId).apply(block).build()

/**
 * Extension function to add an image provided by the Style Extension to the Style.
 *
 * @param image The image to be added
 */
fun StyleInterface.addImage(image: StyleContract.StyleImageExtension) {
  image.bindTo(this)
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