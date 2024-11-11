package com.mapbox.maps.renderer.widget

import androidx.annotation.AnyThread
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.renderer.RenderThread

internal interface WidgetRenderer {

  @get:AnyThread
  val needRender: Boolean

  @RenderThread
  fun onSurfaceChanged(width: Int, height: Int)

  @RenderThread
  fun prepare()

  @RenderThread
  fun render()

  @RenderThread
  fun release()

  @AnyThread
  fun setRotation(angleDegrees: Float)

  @AnyThread
  fun getRotation(): Float

  @MapboxExperimental
  @AnyThread
  fun setPosition(widgetPosition: WidgetPosition)

  @MapboxExperimental
  @AnyThread
  fun getPosition(): WidgetPosition
}