package com.mapbox.maps.renderer.widget

import android.graphics.Bitmap
import com.mapbox.maps.MapboxExperimental

/**
 * Widget displaying bitmap within specified position and margins.
 * This Widget recycles its bitmap once it has been loaded.
 *
 * @param bitmap bitmap used to draw widget
 * @param position position of widget
 * @param marginX horizontal margin in pixels
 * @param marginY vertical margin in pixels
 */
@MapboxExperimental
open class RecyclingBitmapWidget @JvmOverloads constructor(
  bitmap: Bitmap,
  position: WidgetPosition = WidgetPosition(
    vertical = WidgetPosition.Vertical.TOP,
    horizontal = WidgetPosition.Horizontal.LEFT,
  ),
  marginX: Float = 0f,
  marginY: Float = 0f
) : BitmapWidget(bitmap, position, marginX, marginY) {
  override fun onLoaded(bitmap: Bitmap) {
    super.onLoaded(bitmap)
    bitmap.recycle()
  }
}