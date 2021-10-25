package com.mapbox.maps.plugin

import android.view.View

/**
 * Interface to bind a MapView */
fun interface ViewBinder {
  /**
   * Bind the ViewBinder with current MapView.
   *
   * @param mapView The hosting MapView
   */
  fun bind(mapView: View)
}