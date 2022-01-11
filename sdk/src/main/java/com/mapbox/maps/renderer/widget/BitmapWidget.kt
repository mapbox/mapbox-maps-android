package com.mapbox.maps.renderer.widget

import android.graphics.Bitmap
import com.mapbox.maps.MapboxExperimental

@MapboxExperimental
open class BitmapWidget(
  bitmap: Bitmap,
  position: WidgetPosition = WidgetPosition(
    vertical = WidgetPosition.Vertical.TOP,
    horizontal = WidgetPosition.Horizontal.LEFT,
  ),
  marginX: Float = 0f,
  marginY: Float = 0f,
) : Widget() {
  override val renderer = BitmapWidgetRendererImpl(
    bitmap = bitmap,
    position = position,
    marginX = marginX,
    marginY = marginY,
  )

  override fun updateBitmap(bitmap: Bitmap) {
    renderer.updateBitmap(bitmap)
  }

  override fun translate(translateX: Float, translateY: Float) {
    renderer.translate(translateX = translateX, translateY = translateY)
  }

  override fun rotate(angleDegrees: Float) {
    renderer.rotate(angleDegrees = angleDegrees)
  }
}
