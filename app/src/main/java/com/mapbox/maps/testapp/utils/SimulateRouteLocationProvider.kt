package com.mapbox.maps.testapp.utils

import android.os.Handler
import android.os.Looper
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.locationcomponent.LocationConsumer
import com.mapbox.maps.plugin.locationcomponent.LocationProvider
import com.mapbox.turf.TurfMeasurement
import java.util.concurrent.*

/**
 * A location provider implementation that takes in a line string as route and animate the location
 * updates along the route.
 */
class SimulateRouteLocationProvider(
  val route: LineString,
  private val handler: Handler = Handler(Looper.getMainLooper())
) : LocationProvider {
  private val locationConsumers = CopyOnWriteArraySet<LocationConsumer>()
  private var isFakeLocationEmitting = false
  override fun registerLocationConsumer(locationConsumer: LocationConsumer) {
    locationConsumers.add(locationConsumer)
    if (!isFakeLocationEmitting) {
      emitFakeLocations()
      isFakeLocationEmitting = true
    }
  }

  override fun unRegisterLocationConsumer(locationConsumer: LocationConsumer) {
    locationConsumers.remove(locationConsumer)
    if (locationConsumers.isEmpty()) {
      isFakeLocationEmitting = false
    }
  }

  private val pointList = route.coordinates().toMutableList()
  private val iterator = pointList.iterator()
  private var lastLocation: Point =
    if (iterator.hasNext()) iterator.next() else Point.fromLngLat(0.0, 0.0)
  private var lastBearing = 0.0

  private fun emitFakeLocations() {
    handler.postDelayed(
      {
        if (iterator.hasNext()) {
          val point = iterator.next()
          val bearing = TurfMeasurement.bearing(lastLocation, point)
          lastLocation = point
          lastBearing = bearing
          iterator.remove()

          locationConsumers.forEach { it.onLocationUpdated(point) }
          locationConsumers.forEach { it.onBearingUpdated(bearing) }
        }
        if (iterator.hasNext() && isFakeLocationEmitting) {
          emitFakeLocations()
        }
      },
      LOCATION_UPDATE_INTERVAL_MS
    )
  }

  private companion object {
    const val LOCATION_UPDATE_INTERVAL_MS = 1000L
  }
}