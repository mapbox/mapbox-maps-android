package com.mapbox.maps.plugin

import android.widget.FrameLayout

/**
 * Interface to bind a View and underlying context
 */
fun interface ViewBinder {
  /**
   * Bind the plugin with current frame layout that contains the map.
   *
   * @param view The FrameLayout that contains the map.
   */
  fun bind(view: FrameLayout)
}