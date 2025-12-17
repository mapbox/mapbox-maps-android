package com.mapbox.maps.plugin.indoorselector

import com.mapbox.maps.IndoorFloor
import com.mapbox.maps.MapboxExperimental

/**
 * Interface for the indoor floor selector view.
 */
@MapboxExperimental
interface IndoorSelectorView {

  /**
   * Controls the visibility of the indoor selector.
   */
  var isIndoorSelectorVisible: Boolean

  /**
   * Set the gravity/position of the indoor selector on the map.
   *
   * @param gravity Gravity constant (e.g., Gravity.TOP | Gravity.END)
   */
  fun setIndoorGravity(gravity: Int)

  /**
   * Set the margins for the indoor selector.
   *
   * @param left Left margin in pixels
   * @param top Top margin in pixels
   * @param right Right margin in pixels
   * @param bottom Bottom margin in pixels
   */
  fun setIndoorSelectorMargins(left: Int, top: Int, right: Int, bottom: Int)

  /**
   * Update the floor list and selected floor.
   *
   * @param floors List of available floors
   * @param selectedFloorId ID of the currently selected floor
   */
  @OptIn(com.mapbox.annotation.MapboxExperimental::class)
  fun updateFloors(floors: List<IndoorFloor>, selectedFloorId: String?)
}