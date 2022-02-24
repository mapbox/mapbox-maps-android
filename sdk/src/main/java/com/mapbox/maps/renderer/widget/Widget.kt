package com.mapbox.maps.renderer.widget

import com.mapbox.maps.MapboxExperimental

/**
 * Base class for widgets displayed on top of the map.
 */
@MapboxExperimental
abstract class Widget internal constructor() {
  internal abstract val renderer: WidgetRenderer

  /**
   * Set absolute translation of widget in pixels.
   */
  abstract fun setTranslation(translateX: Float, translateY: Float)

  /**
   * Set absolute rotation of widget in angles.
   */
  abstract fun setRotation(angleDegrees: Float)
}