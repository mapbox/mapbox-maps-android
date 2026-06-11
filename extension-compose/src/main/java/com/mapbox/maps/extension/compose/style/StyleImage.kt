package com.mapbox.maps.extension.compose.style

import android.graphics.Bitmap
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
import com.mapbox.maps.extension.style.image.NinePatchImage
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

/**
 * Create and remember a [StyleImage] from a pre-parsed [NinePatchImage].
 *
 * Call `parse9PatchBitmap` to obtain the [NinePatchImage] from a
 * nine-patch bitmap (.9.png). Stretch regions and content padding are read directly from the
 * [NinePatchImage] fields.
 *
 * @param imageId the id of the image
 * @param image9Patch the pre-parsed nine-patch as [NinePatchImage]
 * @param scale scale factor for the image
 * @param sdf option to treat whether image is SDF(signed distance field) or not
 *
 * @return a [StyleImage]
 */
@Composable
public fun rememberStyleImage(
  imageId: String,
  image9Patch: NinePatchImage,
  scale: Float? = null,
  sdf: Boolean = false,
): StyleImage {
  return remember(imageId, image9Patch, scale, sdf) {
    StyleImage(
      imageId = imageId,
      image = image9Patch.internalImage,
      scale = scale,
      sdf = sdf,
      stretchX = image9Patch.stretchX,
      stretchY = image9Patch.stretchY,
      content = image9Patch.imageContent,
    )
  }
}

/**
 * Create and remember a [StyleImage] from a nine-patch [Bitmap].
 *
 * The [bitmap] must be a nine-patch drawable (.9.png). [parse9PatchBitmap] is called inside
 * the `remember` block; it runs once on first composition and is cached for subsequent
 * recompositions as long as the same [Bitmap] object is passed.
 *
 * To avoid re-parsing on every recomposition, wrap the bitmap in `remember` at the call site:
 * ```
 * val bitmap = remember { BitmapFactory.decodeResource(resources, R.drawable.my_nine_patch) }
 * val styleImage = remember9PatchStyleImage(imageId = "my-image", bitmap = bitmap)
 * ```
 *
 * @param imageId the id of the image
 * @param bitmap the nine-patch bitmap as [Bitmap]; must be a `.9.png` nine-patch drawable
 * @param scale scale factor for the image
 * @param sdf option to treat whether image is SDF(signed distance field) or not
 *
 * @return a [StyleImage]
 * @throws IllegalArgumentException if [bitmap] is not a valid nine-patch drawable
 */
@Composable
public fun remember9PatchStyleImage(
  imageId: String,
  bitmap: Bitmap,
  scale: Float? = null,
  sdf: Boolean = false,
): StyleImage {
  val ninePatch = remember(bitmap) { bitmap.parse9PatchBitmap() }
  return rememberStyleImage(imageId = imageId, image9Patch = ninePatch, scale = scale, sdf = sdf)
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