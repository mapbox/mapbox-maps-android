package com.mapbox.maps.extension.style.sources

import com.mapbox.maps.Image
import com.mapbox.maps.extension.style.sources.generated.ImageSource
import com.mapbox.maps.extension.style.utils.check

/**
 * Updates the image of an image style source.
 *
 * See [https://docs.mapbox.com/mapbox-gl-js/style-spec/#sources-image](https://docs.mapbox.com/mapbox-gl-js/style-spec/#sources-image)
 *
 * @param image Pixel data of the image.
 */
fun ImageSource.updateImage(image: Image) {
  this.delegate?.updateStyleImageSourceImage(this.sourceId, image).check()
}