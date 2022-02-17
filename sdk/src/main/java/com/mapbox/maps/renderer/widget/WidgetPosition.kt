package com.mapbox.maps.renderer.widget

import com.mapbox.maps.MapboxExperimental

/**
 * Specifies widget position relative to the screen.
 * @param horizontal position relative to the screen.
 * @param vertical position relative to screen.
 */
@MapboxExperimental
class WidgetPosition(
  val horizontal: Horizontal,
  val vertical: Vertical,
) {

  /**
   * Widget position Horizontal
   */
  enum class Horizontal {
    /**
     * Left Horizontal Position
     */
    LEFT,
    /**
     * Center Horizontal Position
     */
    CENTER,
    /**
     * Right Horizontal Position
     */
    RIGHT,
  }

  /**
   * Widget position Vertical
   */
  enum class Vertical {
    /**
     * Top Vertical Position
     */
    TOP,
    /**
     * Center Vertical Position
     */
    CENTER,
    /**
     * Bottom Vertical Position
     */
    BOTTOM,
  }
}