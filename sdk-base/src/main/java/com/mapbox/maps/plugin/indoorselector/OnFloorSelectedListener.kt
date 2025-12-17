package com.mapbox.maps.plugin.indoorselector

import com.mapbox.maps.MapboxExperimental

/**
 * Listener for floor selection events.
 */
@MapboxExperimental
fun interface OnFloorSelectedListener {
  /**
   * Called when a floor is selected by the user.
   *
   * @param floorId The ID of the selected floor
   */
  fun onFloorSelected(floorId: String)
}