package com.mapbox.maps.renderer.widget

internal interface WidgetRenderer {
  val needRender: Boolean

  fun onSurfaceChanged(width: Int, height: Int)
  fun prepare()
  fun render()
  fun release()

  fun setRotation(angleDegrees: Float)
  fun setTranslation(translationX: Float, translationY: Float)
}