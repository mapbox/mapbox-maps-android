@file:JvmName("NinePatchUtils")
package com.mapbox.maps.extension.style.image

import android.graphics.Bitmap
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.maps.Image
import com.mapbox.maps.ImageContent
import com.mapbox.maps.ImageStretches
import com.mapbox.maps.extension.style.StyleInterface
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Adds an 9-patch image to be used in the style.
 * X-stretches, Y-stretches and padding will be calculated from [Bitmap.getNinePatchChunk].
 *
 * @param imageId ID of the image.
 * @param bitmap Bitmap data of the image.
 *
 * @return A string describing an error if the operation was not successful, empty otherwise.
 */
@JvmOverloads
fun StyleInterface.addImage9Patch(
  imageId: String,
  bitmap: Bitmap,
  scale: Float = pixelRatio,
  sdf: Boolean = false
): Expected<String, None> {
  val ninePatchImage = decodeNinePatchChunk(bitmap)
  return addStyleImage(
    imageId,
    scale,
    ninePatchImage.internalImage,
    sdf,
    ninePatchImage.stretchX,
    ninePatchImage.stretchY,
    ninePatchImage.imageContent
  )
}

/**
 * Utility function returning [NinePatchImage] from a given [Bitmap].
 *
 * [Bitmap] has to be in 9-patch format (.9.png) or [RuntimeException] will be thrown.
 */
fun Bitmap.parse9PatchBitmap() = decodeNinePatchChunk(this)

private fun decodeNinePatchChunk(bitmap: Bitmap): NinePatchImage {
  val ninePatchChunk = bitmap.ninePatchChunk
    ?: throw IllegalArgumentException("Given bitmap must be a 9-patch drawable (.9.png)!")
  val buffer = ByteBuffer.wrap(ninePatchChunk).order(ByteOrder.nativeOrder())
  // skip first byte
  buffer.get()
  // second byte - number of x coordinates
  val xCoordinatesNumber = buffer.get()
  // third byte - number of y coordinates
  val yCoordinatesNumber = buffer.get()
  // skip fourth byte
  buffer.get()

  // start reading by 4 byte chunks as int
  // skip 2 integers (8 bytes)
  buffer.int
  buffer.int

  // next integers contain padding values
  val leftPadding = buffer.int
  val rightPadding = buffer.int
  val topPadding = buffer.int
  val bottomPadding = buffer.int

  // skip next integer
  buffer.int

  // next integers contain X and Y stretches
  val xCoordinates = mutableListOf<Int>()
  for (i in 0 until xCoordinatesNumber) {
    xCoordinates.add(buffer.int)
  }
  val yCoordinates = mutableListOf<Int>()
  for (i in 0 until yCoordinatesNumber) {
    yCoordinates.add(buffer.int)
  }

  val byteBuffer = ByteBuffer.allocate(bitmap.byteCount)
  bitmap.copyPixelsToBuffer(byteBuffer)
  val internalImage = Image(bitmap.width, bitmap.height, byteBuffer.array())

  return NinePatchImage(
    internalImage = internalImage,
    stretchX = xCoordinates
      .map { it.toFloat() }
      .zipWithNext(::ImageStretches),
    stretchY = yCoordinates
      .map { it.toFloat() }
      .zipWithNext(::ImageStretches),
    imageContent = ImageContent(
      leftPadding.toFloat(),
      topPadding.toFloat(),
      (bitmap.width - rightPadding).toFloat(),
      (bitmap.height - bottomPadding).toFloat()
    )
  )
}