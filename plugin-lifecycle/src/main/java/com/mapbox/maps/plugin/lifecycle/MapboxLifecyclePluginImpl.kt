package com.mapbox.maps.plugin.lifecycle

import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.mapbox.maps.MapboxLifecycleObserver
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Concrete implementation of MapboxLifecyclePlugin.
 */
class MapboxLifecyclePluginImpl : MapboxLifecyclePlugin {
  /**
   * Register a MapboxLifecycleObserver to observe life cycle events from LifecycleOwner
   *
   * @param mapView the instance of mapView, will get the LifecycleOwner from mapview's parent
   * @param observer the observer that listen to the life cycle events
   */
  override fun registerLifecycleObserver(mapView: FrameLayout, observer: MapboxLifecycleObserver) {
    ViewTreeLifecycleOwner.get(mapView)?.apply {
      lifecycle.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStart() {
          observer.onStart()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop() {
          observer.onStop()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
          observer.onDestroy()
        }
      })
    }
  }
}

/**
 * Extension function for MapView to get the map overlay plugin instance.
 *
 * @return Map overlay plugin instance
 */
fun MapPluginProviderDelegate.lifecycle(): MapboxLifecyclePlugin {
  return this.getPlugin(MapboxLifecyclePluginImpl::class.java)!!
}