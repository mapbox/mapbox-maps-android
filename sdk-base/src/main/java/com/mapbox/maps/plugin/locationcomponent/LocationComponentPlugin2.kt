package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettingsInterface2

/**
 * Define the interfaces for the Location plugin.
 */
interface LocationComponentPlugin2 : LocationComponentPlugin, LocationComponentSettingsInterface2 {

  /**
   * Adds a listener that gets invoked when indicator accuracy radius changes.
   *
   * @param listener Listener that gets invoked when indicator accuracy radius changes
   */
  fun addOnIndicatorAccuracyRadiusChangedListener(listener: OnIndicatorAccuracyRadiusChangedListener)

  /**
   * Removes a listener that gets invoked when indicator accuracy radius changes.
   *
   * @param listener Listener that gets invoked when indicator accuracy radius changes.
   */
  fun removeOnIndicatorAccuracyRadiusChangedListener(listener: OnIndicatorAccuracyRadiusChangedListener)
}