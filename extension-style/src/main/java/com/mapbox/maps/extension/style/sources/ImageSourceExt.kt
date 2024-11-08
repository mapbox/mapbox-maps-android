package com.mapbox.maps.extension.style.sources

import android.graphics.Bitmap
import com.mapbox.maps.Image
import com.mapbox.maps.MapboxDelicateApi
import com.mapbox.maps.extension.style.sources.generated.ImageSource
import com.mapbox.maps.extension.style.utils.check
import com.mapbox.maps.toMapboxImage

/**
 * Updates the image of an image style source with [Bitmap] provided.
 *
 * See [https://docs.mapbox.com/mapbox-gl-js/style-spec/#sources-image](https://docs.mapbox.com/mapbox-gl-js/style-spec/#sources-image)
 *
 * Important: This method will allocate native memory outside the JVM heap every time it is called.
 * Even if the same [bitmap] is passed.
 *
 * @param bitmap [Bitmap] to update given image style source.
 */
@OptIn(MapboxDelicateApi::class)
fun ImageSource.updateImage(bitmap: Bitmap) {
  this.delegate?.updateStyleImageSourceImage(this.sourceId, bitmap.toMapboxImage()).check()
}

/**
 * Updates the image of an image style source with [Image] provided.
 *
 * See [https://docs.mapbox.com/mapbox-gl-js/style-spec/#sources-image](https://docs.mapbox.com/mapbox-gl-js/style-spec/#sources-image)
 *
 * @param image [Image] to update given image style source.
 */
fun ImageSource.updateImage(image: Image) {
  this.delegate?.updateStyleImageSourceImage(this.sourceId, image).check()
}