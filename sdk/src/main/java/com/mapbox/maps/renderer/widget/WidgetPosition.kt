package com.mapbox.maps.renderer.widget

import com.mapbox.maps.MapboxExperimental

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
