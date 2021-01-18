package com.mapbox.maps.plugin.locationcomponent

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.annotation.VisibleForTesting
import com.mapbox.android.core.location.LocationEngineCallback
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.android.core.location.LocationEngineRequest
import com.mapbox.android.core.location.LocationEngineResult
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Default Location Provider implementation, it can be overwritten by users.
 */
internal class LocationProviderImpl(context: Context) : LocationProvider {
  private val locationEngine = LocationEngineProvider.getBestLocationEngine(context)

  private val locationEngineRequest =
    LocationEngineRequest.Builder(LocationComponentConstants.DEFAULT_INTERVAL_MILLIS)
      .setFastestInterval(LocationComponentConstants.DEFAULT_FASTEST_INTERVAL_MILLIS)
      .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
      .build()

  private var currentLocationEngineListener: LocationEngineCallback<LocationEngineResult> =
    CurrentLocationEngineCallback(this)

  private val locationConsumers = CopyOnWriteArrayList<LocationConsumer>()

  @SuppressLint("MissingPermission")
  private fun requestLocationUpdates() {
    locationEngine.requestLocationUpdates(
      locationEngineRequest, currentLocationEngineListener, Looper.getMainLooper()
    )
  }

  @VisibleForTesting
  internal class CurrentLocationEngineCallback(locationProvider: LocationProviderImpl) :
    LocationEngineCallback<LocationEngineResult> {
    private val locationProviderWeakReference: WeakReference<LocationProviderImpl> =
      WeakReference(locationProvider)

    override fun onSuccess(result: LocationEngineResult) {
      result.lastLocation?.let {
        locationProviderWeakReference.get()?.notifyLocationUpdates(it)
      }
    }

    override fun onFailure(exception: Exception) {
      Logger.e(
        TAG,
        "Failed to obtain location update: $exception"
      )
    }
  }

  internal fun notifyLocationUpdates(location: Location) {
    locationConsumers.forEach { consumer ->
      consumer.onLocationUpdated(Point.fromLngLat(location.longitude, location.latitude))
      consumer.onBearingUpdated(location.bearing)
    }
  }

  /**
   * Register the location consumer to the Location Provider.
   *
   * The Location Consumer will get location and bearing updates from the Location Provider.
   *
   * @param locationConsumer
   */
  override fun registerLocationConsumer(locationConsumer: LocationConsumer) {
    if (locationConsumers.isEmpty()) {
      requestLocationUpdates()
    }
    locationConsumers.add(locationConsumer)
  }

  /**
   * Unregister the location consumer from the Location Provider.
   *
   * @param locationConsumer
   */
  override fun unRegisterLocationConsumer(locationConsumer: LocationConsumer) {
    locationConsumers.remove(locationConsumer)
    if (locationConsumers.isEmpty()) {
      locationEngine.removeLocationUpdates(currentLocationEngineListener)
    }
  }

  private companion object {
    private const val TAG = "MapboxLocationProvider"
  }
}