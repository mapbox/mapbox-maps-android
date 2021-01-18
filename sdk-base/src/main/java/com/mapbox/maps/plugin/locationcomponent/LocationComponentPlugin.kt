package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.maps.plugin.ContextBinder
import com.mapbox.maps.plugin.LifecyclePlugin
import com.mapbox.maps.plugin.MapCameraPlugin
import com.mapbox.maps.plugin.MapStyleObserverPlugin
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettingsInterface

/**
 * Define the interfaces for the Location plugin.
 */
interface LocationComponentPlugin :
  MapStyleObserverPlugin,
  LifecyclePlugin,
  MapCameraPlugin,
  ContextBinder,
  LocationConsumer,
  LocationComponentSettingsInterface {
  /**
   * Set the LocationProvider, it will replace the default location provider provided by the LocationComponentPlugin.
   */
  fun setLocationProvider(locationProvider: LocationProvider)
}