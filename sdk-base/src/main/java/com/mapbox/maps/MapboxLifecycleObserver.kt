package com.mapbox.maps

/**
 * MapboxLifecycleObserver interface defines the lifecycle events that needed by MapView.
 */
interface MapboxLifecycleObserver {
  /**
   * Called to start rendering
   */
  fun onStart()

  /**
   * Called to stop rendering
   */
  fun onStop()

  /**
   * Called to dispose the renderer
   */
  fun onDestroy()

  /**
   * Called to reduce memory use
   */
  fun onLowMemory()

  /**
   * Called when the activity is resumed
   */
  fun onResume() {
    // Default empty implementation for backward compatibility
  }
}