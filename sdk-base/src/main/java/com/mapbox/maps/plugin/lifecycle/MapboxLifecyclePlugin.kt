package com.mapbox.maps.plugin.lifecycle

import android.view.View
import com.mapbox.maps.MapboxLifecycleObserver
import com.mapbox.maps.plugin.MapPlugin

/**
 * Defines interface of a MapViewPlugin.
 */
fun interface MapboxLifecyclePlugin : MapPlugin {

  /**
   * Register the MapboxLifecycleObserver to observe lifecycle events from LifecycleOwner
   *
   * @param mapView the instance of mapView, will get the LifecycleOwner from mapview's parent
   * @param observer observer listening to lifecycle events
   */
  fun registerLifecycleObserver(mapView: View, observer: MapboxLifecycleObserver)
}