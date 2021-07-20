package com.mapbox.maps.plugin.lifecycle

import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.mapbox.common.Logger
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
    val lifecycleOwner = ViewTreeLifecycleOwner.get(mapView)
    if (lifecycleOwner == null) {
      Logger.w(
        TAG,
        "Can't get lifecycleOwner for mapview, please make sure the host Activity is AppCompatActivity"
      )
    } else {
      mapView.context.registerComponentCallbacks(object : ComponentCallbacks {
        override fun onConfigurationChanged(newConfig: Configuration?) {
          // no need
        }

        override fun onLowMemory() {
          observer.onLowMemory()
        }
      })
      lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
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

  companion object {
    private const val TAG = "MapboxLifecyclePlugin"
  }
}

/**
 * Extension function for MapView to get the lifecycle plugin instance.
 *
 * @return Lifecycle plugin instance
 */
fun MapPluginProviderDelegate.lifecycle(): MapboxLifecyclePlugin {
  return this.getPlugin(MapboxLifecyclePluginImpl::class.java)!!
}