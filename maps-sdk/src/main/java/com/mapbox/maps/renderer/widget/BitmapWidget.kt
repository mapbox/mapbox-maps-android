package com.mapbox.maps.renderer.widget

import android.graphics.Bitmap
import com.mapbox.maps.MapboxExperimental

/**
 * Widget displaying bitmap within specified position and margins.
 *
 * @param bitmap bitmap used to draw widget
 * @param originalPosition position of widget
 */
@MapboxExperimental
open class BitmapWidget @JvmOverloads constructor(
  bitmap: Bitmap,
  private val originalPosition: WidgetPosition = WidgetPosition {
    verticalAlignment = WidgetPosition.Vertical.TOP
    horizontalAlignment = WidgetPosition.Horizontal.LEFT
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
  constructor(
    bitmap: Bitmap,
    @Suppress("DEPRECATION")
    position: WidgetPosition = WidgetPosition(
      vertical = WidgetPosition.Vertical.TOP,
      horizontal = WidgetPosition.Horizontal.LEFT,
    ),
    marginX: Float = 0f,
    marginY: Float = 0f,
  ) : this(
    bitmap = bitmap,
    originalPosition = WidgetPosition {
      horizontalAlignment = position.horizontalAlignment
      verticalAlignment = position.verticalAlignment
      offsetX = when (position.horizontalAlignment) {
        // as the WidgetPosition.offsetX now uses the absolute direction towards the right of the
        // screen, and for marginX we move the widget towards opposite of the horizontal alignment,
        // we need to flip the sign when horizontal alignment is set to RIGHT.
        WidgetPosition.Horizontal.LEFT, WidgetPosition.Horizontal.CENTER -> marginX
        WidgetPosition.Horizontal.RIGHT -> -marginX
      }
      offsetY = when (position.verticalAlignment) {
        // as the WidgetPosition.offsetY now uses the absolute direction towards the bottom of the
        // screen, and for marginY we move the widget towards opposite of the vertical alignment,
        // we need to flip the sign when vertical alignment is set to BOTTOM.
        WidgetPosition.Vertical.TOP, WidgetPosition.Vertical.CENTER -> marginY
        WidgetPosition.Vertical.BOTTOM -> -marginY
      }
    }
  )

  /**
   * The deprecated constructor for BitmapWidget.
   *
   * @param bitmap bitmap used to draw widget
   * @param position position of widget
   * @param marginX horizontal margin in pixels
   */
  @Suppress("DEPRECATION")
  @Deprecated(
    message = "Constructor with margins is deprecated, the offset parameters has been merged into " +
      "the WidgetPosition class, and the legacy constructor might be removed in future releases.",
    replaceWith = ReplaceWith("BitmapWidget(context, position)")
  )
  constructor(
    bitmap: Bitmap,
    @Suppress("DEPRECATION")
    position: WidgetPosition = WidgetPosition(
      vertical = WidgetPosition.Vertical.TOP,
      horizontal = WidgetPosition.Horizontal.LEFT,
    ),
    marginX: Float = 0f,
  ) : this(bitmap, position, marginX, 0f)

  override val renderer = BitmapWidgetRenderer(
    bitmap = bitmap,
    position = originalPosition
  )

  /**
   * Update bitmap widget uses.
   *
   * @param bitmap the new Bitmap to render in the Widget.
   */
  fun updateBitmap(bitmap: Bitmap) {
    renderer.updateBitmap(bitmap)
    triggerRepaint()
  }

  /**
   * Update the widget to the new position.
   */
  override fun setPosition(widgetPosition: WidgetPosition) {
    renderer.setPosition(widgetPosition)
    triggerRepaint()
  }

  /**
   * Get the current position of the widget.
   */
  override fun getPosition() = renderer.getPosition()

  /**
   * Set the absolute rotation of widget in degrees.
   */
  override fun setRotation(angleDegrees: Float) {
    renderer.setRotation(angleDegrees = angleDegrees)
    triggerRepaint()
  }

  /**
   * Get absolute rotation of widget in degrees.
   */
  override fun getRotation(): Float = renderer.getRotation()
}