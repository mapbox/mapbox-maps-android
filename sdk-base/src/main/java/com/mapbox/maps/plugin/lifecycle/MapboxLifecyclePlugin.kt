package com.mapbox.maps.plugin.lifecycle

import android.widget.FrameLayout
import com.mapbox.maps.MapboxLifecycleObserver
import com.mapbox.maps.plugin.MapPlugin

/**
 * Defines interface of a MapViewPlugin.
 */
fun interface MapboxLifecyclePlugin : MapPlugin {

  /**
   * Register a MapboxLifecycleObserver to observe life cycle events from LifecycleOwner
   *
   * @param mapView the instance of mapView, will get the LifecycleOwner from mapview's parent
   * @param observer the observer that listen to the life cycle events
   */
  fun registerLifecycleObserver(mapView: FrameLayout, observer: MapboxLifecycleObserver)
}