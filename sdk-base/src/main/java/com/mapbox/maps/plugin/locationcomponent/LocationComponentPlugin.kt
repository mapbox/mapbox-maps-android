package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.maps.plugin.*
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettingsInterface

/**
 * Define the interfaces for the Location plugin.
 */
interface LocationComponentPlugin :
  MapPlugin,
  MapStyleObserverPlugin,
  LifecyclePlugin,
  ContextBinder,
  LocationComponentSettingsInterface {
  /**
   * Set the LocationProvider, it will replace the default location provider provided by the LocationComponentPlugin.
   */
  fun setLocationProvider(locationProvider: LocationProvider)

  /**
   * Get the current LocationProvider under usage with the LocationComponentPlugin.
   *
   * @return The location provider currently under usage, and will return null if the location component plugin is not enabled and not initialised.
   */
  fun getLocationProvider(): LocationProvider?

  /**
   * Adds a listener that gets invoked when indicator position changes.
   *
   * @param listener Listener that gets invoked when indicator position changes
   */
  fun addOnIndicatorPositionChangedListener(listener: OnIndicatorPositionChangedListener)

  /**
   * Removes a listener that gets invoked when indicator position changes.
   *
   * @param listener Listener that gets invoked when indicator position changes.
   */
  fun removeOnIndicatorPositionChangedListener(listener: OnIndicatorPositionChangedListener)

  /**
   * Adds a listener that gets invoked when indicator bearing changes.
   *
   * @param listener Listener that gets invoked when indicator bearing changes
   */
  fun addOnIndicatorBearingChangedListener(listener: OnIndicatorBearingChangedListener)

  /**
   * Removes a listener that gets invoked when indicator bearing changes.
   *
   * @param listener Listener that gets invoked when indicator bearing changes.
   */
  fun removeOnIndicatorBearingChangedListener(listener: OnIndicatorBearingChangedListener)
}