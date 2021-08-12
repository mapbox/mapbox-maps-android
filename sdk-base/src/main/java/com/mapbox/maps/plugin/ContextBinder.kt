package com.mapbox.maps.plugin

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Interface to bind any UI related instance to the MapView or context.
 */
interface ContextBinder {
  /**
   * Provide parameters needed to bind any UI related instance to the context.
   *
   * @param context The hosting context
   * @param attrs parent attributes
   * @param pixelRatio the pixel ratio of the device
   */
  fun bind(context: Context, attrs: AttributeSet?, pixelRatio: Float)

  /**
   * Optional overload allowing to bind directly to MapView passed as FrameLayout.
   *
   * @param view hosting MapView
   */
  fun bind(view: FrameLayout) {}
}