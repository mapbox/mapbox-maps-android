package com.mapbox.maps.plugin.locationcomponent

/**
 * Defines the interface for Location Provider.
 */
interface LocationProvider {
  /**
   * Register the location consumer to the Location Provider.
   *
   * The Location Consumer will get location and bearing updates from the Location Provider.
   *
   * @param locationConsumer
   */
  fun registerLocationConsumer(locationConsumer: LocationConsumer)

  /**
   * Unregister the location consumer from the Location Provider.
   *
   * @param locationConsumer
   */
  fun unRegisterLocationConsumer(locationConsumer: LocationConsumer)
}