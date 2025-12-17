package com.mapbox.maps.plugin.indoorselector

import androidx.annotation.RestrictTo
import com.mapbox.maps.IndoorFloor
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.LifecyclePlugin
import com.mapbox.maps.plugin.ViewPlugin
import com.mapbox.maps.plugin.indoorselector.generated.IndoorSelectorSettingsInterface

/**
 * Plugin interface for indoor floor selector.
 * Displays a vertical list of floors with navigation controls.
 *
 * The plugin automatically subscribes to IndoorManager events and updates the UI
 * when floor data is available. The UI is automatically shown/hidden based on
 * indoor data availability.
 *
 * Example usage:
 * ```
 * // Configure the plugin (optional - defaults are provided)
 * mapView.indoorSelector.updateSettings {
 *   enabled = true
 * }
 *
 * ```
 */
@MapboxExperimental
interface IndoorSelectorPlugin : ViewPlugin, LifecyclePlugin, IndoorSelectorSettingsInterface {

  /**
   * Update the list of available floors and the currently selected floor.
   *
   * @param floors List of available floors
   * @param selectedFloorId The ID of the currently selected floor (null if no floor is selected)
   */
  @OptIn(com.mapbox.annotation.MapboxExperimental::class)
  @RestrictTo(RestrictTo.Scope.LIBRARY)
  fun updateFloors(floors: List<IndoorFloor>, selectedFloorId: String?)

  /**
   * Add a floor selection listener to be notified when user selects a floor.
   *
   * @param listener The listener to add
   */
  fun addOnFloorSelectedListener(listener: OnFloorSelectedListener)

  /**
   * Remove a previously added floor selection listener.
   *
   * @param listener The listener to remove
   */
  fun removeOnFloorSelectedListener(listener: OnFloorSelectedListener)

  /**
   * Called when a floor is selected by the user.
   *
   * @param floorId The ID of the selected floor
   */
  @RestrictTo(RestrictTo.Scope.LIBRARY)
  fun onFloorSelected(floorId: String)
}