package com.mapbox.maps.renderer.widget

import android.graphics.Bitmap
import com.mapbox.maps.MapboxExperimental

/**
 * Widget displaying bitmap within specified position and margins.
 *
 * @param bitmap bitmap used to draw widget
 * @param position position of widget
 * @param marginX horizontal margin in pixels
 * @param marginY vertical margin in pixels
 * @param onBitmapLoaded callback when widget is first rendered, the Bitmap may be recycled if desired.
 */
@MapboxExperimental
open class BitmapWidget @JvmOverloads constructor(
  bitmap: Bitmap,
  position: WidgetPosition = WidgetPosition(
    vertical = WidgetPosition.Vertical.TOP,
    horizontal = WidgetPosition.Horizontal.LEFT,
  ),
  marginX: Float = 0f,
  marginY: Float = 0f,
  onBitmapLoaded: (Bitmap) -> Unit = {},
) : Widget() {
  override val renderer = BitmapWidgetRenderer(
    bitmap = bitmap,
    position = position,
    marginX = marginX,
    marginY = marginY,
    onBitmapLoaded = onBitmapLoaded,
  )

  /**
   * Update bitmap widget uses.
   *
   * @param bitmap the new Bitmap to render in the Widget.
   */
  fun updateBitmap(bitmap: Bitmap) {
    renderer.updateBitmap(bitmap)
  }

  override fun setTranslation(translationX: Float, translationY: Float) {
    renderer.setTranslation(translationX = translationX, translationY = translationY)
  }

  override fun setRotation(angleDegrees: Float) {
    renderer.setRotation(angleDegrees = angleDegrees)
  }
}