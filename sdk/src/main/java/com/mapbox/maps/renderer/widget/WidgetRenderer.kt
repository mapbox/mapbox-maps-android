package com.mapbox.maps.renderer.widget

internal interface WidgetRenderer {
  val needRender: Boolean

  fun onSurfaceChanged(width: Int, height: Int)
  fun prepare()
  fun render()
  fun release()

  fun setRotation(angleDegrees: Float)
  fun getRotation(): Float
  fun setPosition(widgetPosition: WidgetPosition)
  fun getPosition(): WidgetPosition
}