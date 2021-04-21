package com.mapbox.maps.plugin

import com.mapbox.maps.extension.style.StyleInterface

/**
 * Interface for plugins need to be aware of the style change event.
 */
interface MapStyleObserverPlugin {
  /**
   * Called when a new Style is loaded.
   */
  fun onStyleChanged(styleDelegate: StyleInterface)
}