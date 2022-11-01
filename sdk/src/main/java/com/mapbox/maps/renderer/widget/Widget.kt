package com.mapbox.maps.renderer.widget

import com.mapbox.maps.MapboxExperimental

/**
 * Base class for widgets displayed on top of the map.
 */
@MapboxExperimental
abstract class Widget internal constructor() {
  internal abstract val renderer: WidgetRenderer

  /**
   * Update the widget to the new position.
   */
  abstract fun updatePosition(widgetPosition: WidgetPosition)

  /**
   * Get the current position of the widget.
   */
  abstract fun getPosition(): WidgetPosition

  /**
   * Set the translation of the widget in pixels, relative to it's current position.
   *
   * @param translateX the offset in pixels towards the right of the screen.
   * @param translateY the offset in pixels towards the bottom of the screen.
   */
  @Deprecated(
    message = "setTranslation is deprecated, please use updatePosition instead.",
    replaceWith = ReplaceWith("updatePosition")
  )
  fun setTranslation(translateX: Float, translateY: Float) {
    val currentPosition = getPosition()
    updatePosition(
      WidgetPosition {
        horizontal = currentPosition.horizontal
        vertical = currentPosition.vertical
        offsetX = when (currentPosition.horizontal) {
          WidgetPosition.Horizontal.LEFT, WidgetPosition.Horizontal.CENTER -> currentPosition.offsetX + translateX
          WidgetPosition.Horizontal.RIGHT -> currentPosition.offsetX - translateX
        }
        offsetY = when (currentPosition.vertical) {
          WidgetPosition.Vertical.TOP, WidgetPosition.Vertical.CENTER -> currentPosition.offsetY + translateY
          WidgetPosition.Vertical.BOTTOM -> currentPosition.offsetY - translateY
        }
      }
    )
  }

  /**
   * Get the absolute rotation of widget in degrees.
   */
  abstract fun setRotation(angleDegrees: Float)

  /**
   * Set absolute rotation of widget in degrees.
   */
  abstract fun getRotation(): Float
}