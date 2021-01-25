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
   */
  fun getLocationProvider(): LocationProvider?
}