package com.mapbox.maps.renderer.widget

import android.graphics.Bitmap
import com.mapbox.maps.MapboxExperimental

/**
 * Listener for BitmapWidget events.
 */
@MapboxExperimental
internal fun interface BitmapWidgetListener {
  /**
   * Invoked when the supplied Bitmap has been loaded.
   * The Bitmap object is no longer required by the BitmapWidget,
   * and can be recycled if needed during the callback.
   */
  fun onLoaded(bitmap: Bitmap)
}