package com.mapbox.maps.plugin.lifecycle

import android.content.ComponentCallbacks2
import android.content.ComponentCallbacks2.TRIM_MEMORY_BACKGROUND
import android.content.ComponentCallbacks2.TRIM_MEMORY_COMPLETE
import android.content.ComponentCallbacks2.TRIM_MEMORY_MODERATE
import android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL
import android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW
import android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE
import android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN
import android.content.res.Configuration
import android.view.View
import androidx.annotation.RestrictTo
import androidx.lifecycle.*
import com.mapbox.maps.MapboxLifecycleObserver
import com.mapbox.maps.logI
import com.mapbox.maps.logW

/**
 * Concrete implementation of MapboxLifecyclePlugin.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class MapboxLifecyclePluginImpl : MapboxLifecyclePlugin {
  /**
   * Register a MapboxLifecycleObserver to observe life cycle events from LifecycleOwner
   *
   * @param mapView the instance of mapView, will get the LifecycleOwner from mapview's parent
   * @param observer the observer that listen to the life cycle events
   */
  override fun registerLifecycleObserver(mapView: View, observer: MapboxLifecycleObserver) {
    val viewLifecycleRegistry = ViewLifecycleOwner(
      view = mapView
    )
    logI(TAG, "registerLifecycleObserver is called")

    val componentCallback = object : ComponentCallbacks2 {
      override fun onConfigurationChanged(newConfig: Configuration) {
        // no need
      }

      override fun onLowMemory() {
        observer.onLowMemory()
      }

      override fun onTrimMemory(level: Int) {
        when (level) {
          TRIM_MEMORY_RUNNING_CRITICAL, TRIM_MEMORY_RUNNING_LOW -> {
            logW(TAG, "onTrimMemory with level $level is received, reduceMemoryUse will be called.")
            observer.onLowMemory()
          }
          TRIM_MEMORY_BACKGROUND, TRIM_MEMORY_COMPLETE, TRIM_MEMORY_MODERATE, TRIM_MEMORY_RUNNING_MODERATE, TRIM_MEMORY_UI_HIDDEN -> Unit
        }
      }
    }
    mapView.context.registerComponentCallbacks(componentCallback)
    viewLifecycleRegistry.lifecycle.addObserver(
      object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStart() {
          logI(TAG, "onStart is called, MapboxLifecycleObserver will be notified.")
          observer.onStart()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop() {
          logI(TAG, "onStop is called, MapboxLifecycleObserver will be notified.")
          observer.onStop()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
          logI(TAG, "onResume is called, MapboxLifecycleObserver will be notified.")
          observer.onResume()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
          logI(TAG, "onDestroy is called, MapboxLifecycleObserver will be notified.")
          observer.onDestroy()
          viewLifecycleRegistry.lifecycle.removeObserver(this)
          viewLifecycleRegistry.cleanUp()
          mapView.context.unregisterComponentCallbacks(componentCallback)
        }
      })
  }

  private companion object {
    private const val TAG = "MapboxLifecyclePlugin"
  }
}