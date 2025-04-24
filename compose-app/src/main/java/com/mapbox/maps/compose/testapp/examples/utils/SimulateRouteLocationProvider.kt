package com.mapbox.maps.compose.testapp.examples.utils

import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.locationcomponent.LocationConsumer
import com.mapbox.maps.plugin.locationcomponent.LocationProvider
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfMeasurement
import com.mapbox.turf.TurfMisc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.CopyOnWriteArraySet

/**
 * A location provider implementation that takes in a line string as route and animate the location
 * updates along the route.
 */
public class SimulateRouteLocationProvider(
  private val route: LineString,
) : LocationProvider {
  private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
  private var emitLocationsJob: Job? = null
  private val totalRouteLength by lazy { TurfMeasurement.length(route, TurfConstants.UNIT_CENTIMETERS) }
  private val routeStartPoint = route.coordinates().first()
  private val locationConsumers = CopyOnWriteArraySet<LocationConsumer>()
  private var isFakeLocationEmitting = false
  private val iterator = route.coordinates().toMutableList().iterator()

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
      emitLocationsJob?.cancel()
      isFakeLocationEmitting = false
    }
  }

  private fun emitFakeLocations() {
    val previousEmitLocationsJob = emitLocationsJob
    emitLocationsJob = scope.launch {
      // Make sure previous job is cancelled before starting a new one
      previousEmitLocationsJob?.cancelAndJoin()
      var lastLocation: Point = if (iterator.hasNext()) {
        iterator.next()
      } else {
        Point.fromLngLat(0.0, 0.0)
      }
      while (isActive && iterator.hasNext()) {
        val point = iterator.next().insertProgressInfo()
        val bearing = TurfMeasurement.bearing(lastLocation, point)
        lastLocation = point
        iterator.remove()

        withContext(Dispatchers.Main) {
          locationConsumers.forEach { it.onLocationUpdated(point) }
          locationConsumers.forEach { it.onBearingUpdated(bearing) }
        }
        delay(LOCATION_UPDATE_INTERVAL_MS)
      }
    }
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