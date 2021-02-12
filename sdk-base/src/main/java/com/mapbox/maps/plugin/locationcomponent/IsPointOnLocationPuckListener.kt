package com.mapbox.maps.plugin.locationcomponent

/**
 * Listener that gets invoked when the is point on location puck validation finished.
 */
fun interface IsPointOnLocationPuckListener {
  /**
   * This method is called when the is point on location puck validation finished.
   *
   * @param isOnLocationPuck true if the given point is on the location puck, false otherwise.
   */
  fun isPointOnLocationPuck(isOnLocationPuck: Boolean)
}