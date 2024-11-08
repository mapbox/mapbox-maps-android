package com.mapbox.maps.extension.compose.style

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.mapbox.maps.Image
import com.mapbox.maps.ImageContent
import com.mapbox.maps.ImageStretches
import com.mapbox.maps.MapboxDelicateApi
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
@Immutable
public data class StyleImage(
  val imageId: String,
  val image: Image,
  val scale: Float? = null,
  val sdf: Boolean = false,
  val stretchX: List<ImageStretches> = listOf(),
  val stretchY: List<ImageStretches> = listOf(),
  val content: ImageContent? = null,
)

/**
 * Create and remember a [StyleImage] with [ImageBitmap].
 *
 * @param imageId the id of the image
 * @param imageBitmap The image data of the bitmap as [ImageBitmap]
 * @param scale scale factor for the image
 * @param sdf option to treat whether image is SDF(signed distance field) or not
 * @param stretchX An array of two-element arrays, consisting of two numbers that represent the from position and the to position of areas that can be stretched horizontally.
 * @param stretchY An array of two-element arrays, consisting of two numbers that represent the from position and the to position of areas that can be stretched vertically.
 * @param content An array of four numbers, with the first two specifying the left, top corner, and the last two specifying the right, bottom corner. If present, and if the icon uses icon-text-fit, the symbol's text will be fit inside the content box.
 *
 * @return a [StyleImage]
 */
@OptIn(MapboxDelicateApi::class)
@Composable
public fun rememberStyleImage(
  imageId: String,
  imageBitmap: ImageBitmap,
  scale: Float? = null,
  sdf: Boolean = false,
  stretchX: List<ImageStretches> = listOf(),
  stretchY: List<ImageStretches> = listOf(),
  content: ImageContent? = null,
): StyleImage {
  return remember(imageId, imageBitmap, scale, sdf, stretchX, stretchY, content) {
    StyleImage(
      imageId = imageId,
      image = imageBitmap.asAndroidBitmap().toMapboxImage(),
      scale = scale,
      sdf = sdf,
      stretchX = stretchX,
      stretchY = stretchY,
      content = content
    )
  }
}

/**
 * Create and remember a [StyleImage] with [painter] and [key].
 *
 * @param key An optional key to be used as a key for the remembered value.
 * @param imageId the id of the image
 * @param painter the [Painter] to provide the image
 * @param scale scale factor for the image
 * @param sdf option to treat whether image is SDF(signed distance field) or not
 * @param stretchX An array of two-element arrays, consisting of two numbers that represent the from position and the to position of areas that can be stretched horizontally.
 * @param stretchY An array of two-element arrays, consisting of two numbers that represent the from position and the to position of areas that can be stretched vertically.
 * @param content An array of four numbers, with the first two specifying the left, top corner, and the last two specifying the right, bottom corner. If present, and if the icon uses icon-text-fit, the symbol's text will be fit inside the content box.
 *
 * @return a [StyleImage]
 */
@OptIn(MapboxDelicateApi::class)
@Composable
public fun rememberStyleImage(
  key: Any?,
  imageId: String,
  painter: Painter,
  scale: Float? = null,
  sdf: Boolean = false,
  stretchX: List<ImageStretches> = listOf(),
  stretchY: List<ImageStretches> = listOf(),
  content: ImageContent? = null,
): StyleImage {
  return remember(key, imageId, painter, scale, sdf, stretchX, stretchY, content) {
    StyleImage(
      imageId = imageId,
      image = painter.drawToBitmap().asAndroidBitmap().toMapboxImage(),
      scale = scale,
      sdf = sdf,
      stretchX = stretchX,
      stretchY = stretchY,
      content = content
    )
  }
}

/**
 * Create and remember a [StyleImage] with [resourceId].
 *
 * @param imageId the id of the image
 * @param resourceId the resource id to be loaded to the image
 * @param scale scale factor for the image
 * @param sdf option to treat whether image is SDF(signed distance field) or not
 * @param stretchX An array of two-element arrays, consisting of two numbers that represent the from position and the to position of areas that can be stretched horizontally.
 * @param stretchY An array of two-element arrays, consisting of two numbers that represent the from position and the to position of areas that can be stretched vertically.
 * @param content An array of four numbers, with the first two specifying the left, top corner, and the last two specifying the right, bottom corner. If present, and if the icon uses icon-text-fit, the symbol's text will be fit inside the content box.
 *
 * @return a [StyleImage]
 */
@Composable
public fun rememberStyleImage(
  imageId: String,
  @DrawableRes resourceId: Int,
  scale: Float? = null,
  sdf: Boolean = false,
  stretchX: List<ImageStretches> = listOf(),
  stretchY: List<ImageStretches> = listOf(),
  content: ImageContent? = null,
): StyleImage {
  return rememberStyleImage(
    key = resourceId,
    imageId = imageId,
    painter = painterResource(resourceId),
    scale = scale,
    sdf = sdf,
    stretchX = stretchX,
    stretchY = stretchY,
    content = content
  )
}

internal fun Painter.drawToBitmap(): ImageBitmap {
  val drawScope = CanvasDrawScope()
  val bitmap = ImageBitmap(intrinsicSize.width.toInt(), intrinsicSize.height.toInt())
  val canvas = Canvas(bitmap)
  drawScope.draw(
    density = Density(1f),
    layoutDirection = LayoutDirection.Ltr,
    canvas = canvas,
    size = intrinsicSize
  ) {
    draw(intrinsicSize)
  }
  return bitmap
}