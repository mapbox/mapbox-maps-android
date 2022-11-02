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
  abstract fun setPosition(widgetPosition: WidgetPosition)

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
    message = "setTranslation is deprecated, please use setPosition instead.",
    replaceWith = ReplaceWith("setPosition")
  )
  abstract fun setTranslation(translateX: Float, translateY: Float)

  /**
   * Set the absolute rotation of widget in degrees.
   */
  abstract fun setRotation(angleDegrees: Float)

  /**
   * Get absolute rotation of widget in degrees.
   */
  abstract fun getRotation(): Float
}