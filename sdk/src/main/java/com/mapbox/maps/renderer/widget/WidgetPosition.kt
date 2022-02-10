package com.mapbox.maps.renderer.widget

import com.mapbox.maps.MapboxExperimental

/**
 * Specifies widget position relative to the screen.
 */
@MapboxExperimental
class WidgetPosition(
  val horizontal: Horizontal,
  val vertical: Vertical,
) {
  enum class Horizontal {
    LEFT,
    CENTER,
    RIGHT,
  }

  enum class Vertical {
    TOP,
    CENTER,
    BOTTOM,
  }
}