package com.mapbox.maps.extension.compose.style

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.mapbox.maps.Image
import com.mapbox.maps.ImageContent
import com.mapbox.maps.ImageStretches
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.style.image.parse9PatchBitmap
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
public data class StyleImage(
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

  /**
   * Construct a [StyleImage] with [Painter].
   *
   * @param imageId the id of the image
   * @param painter the painter to be used in the style image
   * @param scale scale factor for the image
   * @param sdf option to treat whether image is SDF(signed distance field) or not
   * @param stretchX An array of two-element arrays, consisting of two numbers that represent the from position and the to position of areas that can be stretched horizontally.
   * @param stretchY An array of two-element arrays, consisting of two numbers that represent the from position and the to position of areas that can be stretched vertically.
   * @param content An array of four numbers, with the first two specifying the left, top corner, and the last two specifying the right, bottom corner. If present, and if the icon uses icon-text-fit, the symbol's text will be fit inside the content box.
   */
  public constructor(
    imageId: String,
    painter: Painter,
    scale: Float? = null,
    sdf: Boolean = false,
    stretchX: List<ImageStretches> = listOf(),
    stretchY: List<ImageStretches> = listOf(),
    content: ImageContent? = null
  ) : this(
    imageId = imageId,
    imageBitmap = painter.drawToBitmap(),
    scale = scale,
    sdf = sdf,
    stretchX = stretchX,
    stretchY = stretchY,
    content = content
  )

  /**
   * Construct a [StyleImage] with 9-patch [Bitmap] in order to calculate stretchX, stretchY and paddings automatically.
   *
   * @param imageId the id of the image
   * @param ninePatchBitmap the 9-patch [Bitmap] in order to calculate stretchX, stretchY and paddings automatically.
   * @param scale scale factor for the image
   * @param sdf option to treat whether image is SDF(signed distance field) or not
   */
  public constructor(
    imageId: String,
    ninePatchBitmap: Bitmap,
    scale: Float? = null,
    sdf: Boolean = false,
  ) : this(
    imageId = imageId,
    image = ninePatchBitmap.parse9PatchBitmap().internalImage,
    scale = scale,
    sdf = sdf,
    stretchX = ninePatchBitmap.parse9PatchBitmap().stretchX,
    stretchY = ninePatchBitmap.parse9PatchBitmap().stretchY,
    content = ninePatchBitmap.parse9PatchBitmap().imageContent
  )
}

private fun Painter.drawToBitmap(): ImageBitmap {
  val drawScope = CanvasDrawScope()
  val bitmap = ImageBitmap(intrinsicSize.width.toInt(), intrinsicSize.height.toInt())
  val canvas = Canvas(bitmap)
  drawScope.draw(
    density = Density(1f),
    layoutDirection = LayoutDirection.Ltr,
    canvas = canvas,
    size = intrinsicSize,
  ) {
    draw(intrinsicSize)
  }
  return bitmap
}