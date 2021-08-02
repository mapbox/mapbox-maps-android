package com.mapbox.maps.extension.style.image

import com.mapbox.maps.Image
import com.mapbox.maps.ImageContent
import com.mapbox.maps.ImageStretches

data class NinePatchImage(
  val internalImage: Image,
  val stretchX: List<ImageStretches>,
  val stretchY: List<ImageStretches>,
  val imageContent: ImageContent
)