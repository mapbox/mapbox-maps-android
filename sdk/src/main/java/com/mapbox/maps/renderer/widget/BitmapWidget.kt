package com.mapbox.maps.renderer.widget

import android.graphics.Bitmap
import com.mapbox.maps.MapboxExperimental

/**
 * Widget displaying bitmap within specified position and margins.
 *
 * @param bitmap bitmap used to draw widget
 * @param position position of widget
 */
@MapboxExperimental
open class BitmapWidget @JvmOverloads constructor(
  bitmap: Bitmap,
  position: WidgetPosition = WidgetPosition {
    vertical = WidgetPosition.Vertical.TOP
    horizontal = WidgetPosition.Horizontal.LEFT
    offsetX = 0f
    offsetY = 0f
  }
) : Widget() {
  /**
   * The deprecated constructor for BitmapWidget.
   *
   * @param bitmap bitmap used to draw widget
   * @param position position of widget
   * @param marginX horizontal margin in pixels
   * @param marginY vertical margin in pixels
   */
  @Deprecated(
    message = "Constructor with margins is deprecated, the offset parameters has been merged into " +
      "the WidgetPosition class, and the legacy constructor might be removed in future releases.",
    replaceWith = ReplaceWith("BitmapWidget(context, position)")
  )
  constructor(bitmap: Bitmap, position: WidgetPosition, marginX: Float, marginY: Float) : this(
    bitmap = bitmap,
    position = WidgetPosition {
      horizontal = position.horizontal
      vertical = position.vertical
      offsetX = marginX
      offsetY = marginY
    }
  )

  override val renderer = BitmapWidgetRenderer(
    bitmap = bitmap,
    widgetPosition = position
  )

  /**
   * Update bitmap widget uses.
   *
   * @param bitmap the new Bitmap to render in the Widget.
   */
  fun updateBitmap(bitmap: Bitmap) {
    renderer.updateBitmap(bitmap)
  }

  /**
   * Update the widget to the new position.
   */
  override fun updatePosition(widgetPosition: WidgetPosition) {
    renderer.updatePosition(widgetPosition)
  }

  /**
   * Get the current position of the widget.
   */
  override fun getPosition() = renderer.getPosition()

  /**
   * Get the absolute rotation of widget in degrees.
   */
  override fun setRotation(angleDegrees: Float) {
    renderer.setRotation(angleDegrees = angleDegrees)
  }

  /**
   * Set absolute rotation of widget in degrees.
   */
  override fun getRotation(): Float = renderer.getRotation()
}