package com.mapbox.maps.extension.compose.style

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.mapbox.maps.Image
import com.mapbox.maps.ImageContent
import com.mapbox.maps.ImageStretches
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.toMapboxImage

/**
 * Style image to used with runtime styling.
 *
 * @param imageId the id of the image
 * @param image pixel data of the image
 * @param scale scale factor for the image
 * @param sdf option to treat whether image is SDF(signed distance field) or not
 * @param stretchX An array of two-element arrays, consisting of two numbers that represent the from position and the to position of areas that can be stretched horizontally.
 * @param stretchY An array of two-element arrays, consisting of two numbers that represent the from position and the to position of areas that can be stretched vertically.
 * @param content An array of four numbers, with the first two specifying the left, top corner, and the last two specifying the right, bottom corner. If present, and if the icon uses icon-text-fit, the symbol's text will be fit inside the content box.
 */
@MapboxExperimental
public data class StyleImage internal constructor(
  val imageId: String,
  val image: Image,
  val scale: Float? = null,
  val sdf: Boolean = false,
  val stretchX: List<ImageStretches> = listOf(),
  val stretchY: List<ImageStretches> = listOf(),
  val content: ImageContent? = null,
) {
  /**
   * Construct a [StyleImage] with [ImageBitmap].
   *
   * @param imageId the id of the image
   * @param imageBitmap the imageBitmap to be used in the style image
   * @param scale scale factor for the image
   * @param sdf option to treat whether image is SDF(signed distance field) or not
   * @param stretchX An array of two-element arrays, consisting of two numbers that represent the from position and the to position of areas that can be stretched horizontally.
   * @param stretchY An array of two-element arrays, consisting of two numbers that represent the from position and the to position of areas that can be stretched vertically.
   * @param content An array of four numbers, with the first two specifying the left, top corner, and the last two specifying the right, bottom corner. If present, and if the icon uses icon-text-fit, the symbol's text will be fit inside the content box.
   */
  public constructor(
    imageId: String,
    imageBitmap: ImageBitmap,
    scale: Float? = null,
    sdf: Boolean = false,
    stretchX: List<ImageStretches> = listOf(),
    stretchY: List<ImageStretches> = listOf(),
    content: ImageContent? = null
  ) : this(
    imageId = imageId,
    image = imageBitmap.asAndroidBitmap().toMapboxImage(),
    scale = scale,
    sdf = sdf,
    stretchX = stretchX,
    stretchY = stretchY,
    content = content
  )
}