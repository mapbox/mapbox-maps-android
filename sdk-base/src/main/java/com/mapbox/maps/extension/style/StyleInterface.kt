package com.mapbox.maps.extension.style

import android.graphics.Bitmap
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.maps.StyleManagerInterface

/**
 * Defines the style interface that can be used to interact with the map's style.
 */
interface StyleInterface : StyleManagerInterface {
  /**
   * The pixel ratio of the current display for the map to be rendered on, this value is initialised
   * and can be overwritten at the Map's initialisation period.
   */
  val pixelRatio: Float

  /**
   * Whether the Style instance is valid.
   *
   * Style becomes invalid after MapView.onDestroy() is invoked,
   * calling any method then could result in undefined behaviour and will print an error log.
   *
   * Keeping the reference to an invalid Style instance introduces significant native memory leak.
   *
   * @return True if Style is valid and false otherwise.
   */
  fun isValid(): Boolean

  /**
   * Adds an image to be used in the style. This API can also be used for updating
   * an image. If the image \a id was already added, it gets replaced by the new image.
   *
   * The image can be used in `icon-image`, `fill-pattern`, and `line-pattern`.
   *
   * [layout-symbol-icon-image](https://www.mapbox.com/mapbox-gl-js/style-spec/#layout-symbol-icon-image)
   *
   * [paint-line-line-pattern](https://www.mapbox.com/mapbox-gl-js/style-spec/#paint-line-line-pattern)
   *
   * [paint-fill-fill-pattern](https://www.mapbox.com/mapbox-gl-js/style-spec/#paint-fill-fill-pattern)
   *
   * @param imageId ID of the image.
   * @param bitmap Bitmap data of the image.
   *
   * @return A string describing an error if the operation was not successful, empty otherwise.
   */
  fun addImage(imageId: String, bitmap: Bitmap): Expected<String, None>
}