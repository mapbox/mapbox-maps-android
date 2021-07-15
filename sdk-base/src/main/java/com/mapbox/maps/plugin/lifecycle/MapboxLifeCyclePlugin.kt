package com.mapbox.maps.plugin.lifecycle

import android.widget.FrameLayout
import com.mapbox.maps.MapboxLifeCycleObserver
import com.mapbox.maps.plugin.MapPlugin

/**
 * Defines interface of a MapViewPlugin.
 */
fun interface MapboxLifeCyclePlugin : MapPlugin {

  /**
   * Register a MapboxLifeCycleObserver to observe life cycle events from LifecycleOwner
   *
   * @param mapView the instance of mapView, will get the LifecycleOwner from mapview's parent
   * @param observer the observer that listen to the life cycle events
   */
  fun registerLifeCycleObserver(mapView: FrameLayout, observer: MapboxLifeCycleObserver)
}