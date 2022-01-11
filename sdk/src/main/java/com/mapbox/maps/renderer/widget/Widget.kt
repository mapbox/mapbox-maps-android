package com.mapbox.maps.renderer.widget

import android.graphics.Bitmap
import com.mapbox.maps.MapboxExperimental

@MapboxExperimental
abstract class Widget {
  internal abstract val renderer : WidgetRenderer

  abstract fun updateBitmap(bitmap: Bitmap)
  abstract fun translate(translateX: Float, translateY: Float)
  abstract fun rotate(angleDegrees: Float)
}