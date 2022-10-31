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
   * Get the absolute rotation of widget in degrees.
   */
  abstract fun setRotation(angleDegrees: Float)

  /**
   * Set absolute rotation of widget in degrees.
   */
  abstract fun getRotation() : Float
}