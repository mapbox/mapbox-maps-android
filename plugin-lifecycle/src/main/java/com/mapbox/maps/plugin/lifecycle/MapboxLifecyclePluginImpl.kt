package com.mapbox.maps.plugin.lifecycle

import android.content.ComponentCallbacks2
import android.content.ComponentCallbacks2.TRIM_MEMORY_BACKGROUND
import android.content.ComponentCallbacks2.TRIM_MEMORY_COMPLETE
import android.content.ComponentCallbacks2.TRIM_MEMORY_MODERATE
import android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL
import android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW
import android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE
import android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN
import android.content.Context
import android.content.res.Configuration
import android.view.View
import androidx.annotation.UiThread
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mapbox.maps.MapboxLifecycleObserver
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_LIFECYCLE_PLUGIN_ID
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Concrete implementation of MapboxLifecyclePlugin.
 */
class MapboxLifecyclePluginImpl : MapboxLifecyclePlugin {

  // All fields accessed on the main thread only.
  private var viewLifecycleOwner: ViewLifecycleOwner? = null
  private var lifecycleObserver: LifecycleObserver? = null
  private var componentCallback: ComponentCallbacks2? = null
  private var registeredContext: Context? = null

  /**
   * Register a MapboxLifecycleObserver to observe life cycle events from LifecycleOwner.
   *
   * Calling this again before [cleanup] replaces the previous registration instead of
   * stacking a second one.
   *
   * @param mapView the instance of mapView, will get the LifecycleOwner from mapview's parent
   * @param observer the observer that listen to the life cycle events
   */
  @UiThread
  override fun registerLifecycleObserver(mapView: View, observer: MapboxLifecycleObserver) {
    // Tear down any prior registration (e.g. attach/detach/reattach cycles).
    teardownRegistration()

    val owner = ViewLifecycleOwner(view = mapView)
    val context = mapView.context

    val callback = object : ComponentCallbacks2 {
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
    context.registerComponentCallbacks(callback)

    val lifecycleEventObserver = LifecycleEventObserver { _: LifecycleOwner, event ->
      when (event) {
        ON_START -> observer.onStart()
        ON_STOP -> observer.onStop()
        ON_DESTROY -> {
          logI(TAG, "onDestroy is called, MapboxLifecycleObserver will be notified.")
          observer.onDestroy()
          teardownRegistration()
        }
        else -> Unit
      }
    }

    // Store fields before addObserver: addObserver may synchronously replay ON_DESTROY
    // if the hosting lifecycle is already destroyed, which calls teardownRegistration().
    this.viewLifecycleOwner = owner
    this.lifecycleObserver = lifecycleEventObserver
    this.componentCallback = callback
    this.registeredContext = context

    owner.lifecycle.addObserver(lifecycleEventObserver)
  }

  /**
   * Called when the map is destroyed. Unregisters the ComponentCallbacks registered with the
   * Application so that the MapView graph can be garbage-collected even if the hosting Activity
   * has not yet been destroyed.
   */
  @UiThread
  override fun cleanup() {
    teardownRegistration()
  }

  @UiThread
  private fun teardownRegistration() {
    lifecycleObserver?.let { viewLifecycleOwner?.lifecycle?.removeObserver(it) }
    viewLifecycleOwner?.cleanUp()
    registeredContext?.unregisterComponentCallbacks(componentCallback)
    viewLifecycleOwner = null
    lifecycleObserver = null
    componentCallback = null
    registeredContext = null
  }

  private companion object {
    private const val TAG = "MapboxLifecyclePlugin"
  }
}

/**
 * Extension function for MapView to get the lifecycle plugin instance.
 *
 * @return Lifecycle plugin instance
 */
val MapPluginProviderDelegate.lifecycle: MapboxLifecyclePlugin
  get() = this.getPlugin(MAPBOX_LIFECYCLE_PLUGIN_ID)!!