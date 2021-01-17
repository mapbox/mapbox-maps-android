package com.mapbox.maps.plugin.attribution

import com.mapbox.maps.plugin.delegates.MapAttributionDelegate

/**
 * Interface for attribution dialog manager. This interface can be used to implement your
 * own AttributionDialogManager to replace the default attribution dialog.
 */
interface AttributionDialogManager {

  /**
   * Invoked when the map attribution should be shown to the end user
   *
   */
  fun showAttribution(mapAttributionDelegate: MapAttributionDelegate)

  /**
   * Invoked when the hosting Activity#onStop is called
   */
  fun onStop()
}