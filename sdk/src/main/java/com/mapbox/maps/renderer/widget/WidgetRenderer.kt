package com.mapbox.maps.renderer.widget

import com.mapbox.maps.MapboxExperimental

internal interface WidgetRenderer {
  val needRender: Boolean

  fun onSurfaceChanged(width: Int, height: Int)
  fun prepare()
  fun render()
  fun release()

  fun setRotation(angleDegrees: Float)
  fun getRotation(): Float
  @MapboxExperimental
  fun setPosition(widgetPosition: WidgetPosition)
  @MapboxExperimental
  fun getPosition(): WidgetPosition
}