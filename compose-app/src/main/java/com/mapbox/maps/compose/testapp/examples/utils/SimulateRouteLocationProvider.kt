package com.mapbox.maps.compose.testapp.examples.utils

import android.os.Handler
import android.os.Looper
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.locationcomponent.LocationConsumer
import com.mapbox.maps.plugin.locationcomponent.LocationProvider
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfMeasurement
import com.mapbox.turf.TurfMisc
import java.util.concurrent.CopyOnWriteArraySet

/**
 * A location provider implementation that takes in a line string as route and animate the location
 * updates along the route.
 */
public class SimulateRouteLocationProvider(
  private val route: LineString,
  private val handler: Handler = Handler(Looper.getMainLooper())
) : LocationProvider {
  private val totalRouteLength = TurfMeasurement.length(route, TurfConstants.UNIT_CENTIMETERS)
  private val routeStartPoint = route.coordinates().first()
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
          val point = iterator.next().insertProgressInfo()
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

  // use altitude of Point to pass through progress data, and use internal animator to interpolate
  // the progress, thus reduce the overhead to calculate the progress on each frame.
  private fun Point.insertProgressInfo() = Point.fromLngLat(
    longitude(),
    latitude(),
    TurfMeasurement.length(
      TurfMisc.lineSlice(routeStartPoint, this, route),
      TurfConstants.UNIT_CENTIMETERS
    ) / totalRouteLength
  )

  private companion object {
    const val LOCATION_UPDATE_INTERVAL_MS = 1000L
  }
}