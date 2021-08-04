package com.mapbox.maps.extension.style.image

import com.mapbox.maps.Image
import com.mapbox.maps.ImageContent
import com.mapbox.maps.ImageStretches

/**
 * Data class describing 9-patch bitmap.
 */
data class NinePatchImage(
  /**
   * Bitmap itself.
   */
  val internalImage: Image,
  /**
   * An array of two-element arrays, consisting of two numbers that represent
   * the from position and the to position of areas that can be stretched horizontally.
   */
  val stretchX: List<ImageStretches>,
  /**
   * An array of two-element arrays, consisting of two numbers that represent
   * the from position and the to position of areas that can be stretched vertically.
   */
  val stretchY: List<ImageStretches>,
  /**
   * An array of four numbers, with the first two specifying the left, top
   * corner, and the last two specifying the right, bottom corner. If present, and if the
   * icon uses icon-text-fit, the symbol's text will be fit inside the content box.
   */
  val imageContent: ImageContent
)