package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.geojson.Point

/**
 * Defines the interface for LocationConsumer.
 */
interface LocationConsumer {

  /**
   * Called whenever the location is updated.
   */
  fun onLocationUpdated(location: Point)

  /**
   * Called whenever the bearing is updated.
   */
  fun onBearingUpdated(bearing: Float)
}