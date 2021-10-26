package com.mapbox.maps.plugin

import android.content.Context
import android.util.AttributeSet

/**
 * Interface to bind a View and underlying context
 */
fun interface ContextBinder {
  /**
   * Bind the plugin with current map context.
   *
   * @param context The hosting context
   * @param attrs parent attributes
   * @param pixelRatio the pixel ratio of the device
   * @return View that will be added to the MapView
   */
  fun bind(context: Context, attrs: AttributeSet?, pixelRatio: Float)
}