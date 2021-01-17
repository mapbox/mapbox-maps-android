package com.mapbox.maps.plugin

/**
 * Interface for plugins interacting with the lifecycle of the hosting context.
 */
interface LifecyclePlugin {

  /**
   * Called whenever activity's/fragment's lifecycle is entering a "started" state.
   */
  fun onStart() {}

  /**
   * Called whenever activity's/fragment's lifecycle is entering a "stopped" state.
   */
  fun onStop() {}
}