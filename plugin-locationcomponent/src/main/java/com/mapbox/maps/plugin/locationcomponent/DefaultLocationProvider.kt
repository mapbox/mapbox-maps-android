package com.mapbox.maps.plugin.locationcomponent

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.bindgen.Expected
import com.mapbox.common.Cancelable
import com.mapbox.common.location.AccuracyLevel
import com.mapbox.common.location.IntervalSettings
import com.mapbox.common.location.Location
import com.mapbox.common.location.LocationError
import com.mapbox.common.location.LocationErrorCode
import com.mapbox.common.location.LocationObserver
import com.mapbox.common.location.LocationProviderRequest
import com.mapbox.common.location.LocationService
import com.mapbox.common.location.LocationServiceFactory
import com.mapbox.geojson.Point
import com.mapbox.maps.logE
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.LocationCompassEngine.CompassListener
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

/**
 * Default Location Provider implementation that produces location updates according to the device's
 * GPS or magnetic field sensor data.
 */
class DefaultLocationProvider @VisibleForTesting(otherwise = PRIVATE) internal constructor(
  context: Context,
  private val locationCompassEngine: LocationCompassEngine,
  locationService: LocationService,
  private val mainCoroutineDispatcher: CoroutineDispatcher,
) : LocationProvider {
  constructor(context: Context) : this(
    context,
    LocationCompassEngine(context.applicationContext),
    LocationServiceFactory.getOrCreate(),
    Dispatchers.Main.immediate,
  )

  private var locationProviderNotAvailable: LocationError? = null

  private val scope = CoroutineScope(SupervisorJob() + mainCoroutineDispatcher)

  /**
   * A [MutableStateFlow] that holds the current puck bearing source.
   */
  private val puckBearingFlow = MutableStateFlow(PuckBearing.COURSE)

  /**
   * A hot [Flow] that subscribes to the [locationCompassEngine] to receive device orientation.
   * It emits degrees.
   */
  private val deviceOrientationFlow: Flow<Double> = callbackFlow {
    val compassListener = CompassListener { userHeading -> trySendBlocking(userHeading.toDouble()) }
    locationCompassEngine.addCompassListener(compassListener)
    awaitClose {
      locationCompassEngine.removeCompassListener(compassListener)
    }
  }.shareIn(scope, SharingStarted.WhileSubscribed(replayExpirationMillis = 0L), replay = 1)

  /**
   * A hot [Flow] that emits [Location]s or [LocationError] wrapped in a [Expected].
   */
  private val locationUpdatesFlow: Flow<Location>

  /**
   * A map that keeps track of each [Job] associated to the [LocationConsumer].
   *
   * When [registerLocationConsumer] is called we start a new [Job] and add it to this map.
   * When [unRegisterLocationConsumer] is called we stop the associated [Job] and remove it from this map.
   */
  private val locationConsumersJobs = ConcurrentHashMap<LocationConsumer, Job>()

  init {
    val request = LocationProviderRequest.Builder()
      .accuracy(AccuracyLevel.HIGH)
      .interval(
        IntervalSettings.Builder()
          .minimumInterval(LocationComponentConstants.DEFAULT_FASTEST_INTERVAL_MILLIS)
          .interval(LocationComponentConstants.DEFAULT_INTERVAL_MILLIS).build()
      )
      .build()
    val result = locationService.getDeviceLocationProvider(request)
    // Depending on the result we either create a flow that will subscribe to the live tracking
    // client or create a flow that will emit a LocationError
    locationUpdatesFlow = if (result.isValue) {
      val applicationContext = context.applicationContext
      val locationProvider = result.value!!
      callbackFlow<Location> {

        // Wait that we have the right permissions
        var updateDelay = INIT_UPDATE_DELAY
        while (!PermissionsManager.areLocationPermissionsGranted(applicationContext)) {
          logW(TAG, MISSING_PERMISSION_MSG)
          delay(updateDelay)
          updateDelay = (updateDelay * 2).coerceAtMost(MAX_UPDATE_DELAY)
        }

        // If possible send the most recent location available
        val lastLocationCancelable = locationProvider.getLastLocation { result ->
          result?.let { trySend(it) }
        }

        // Then, register an observer that will emit the locations provided by the liveTrackingClient
        val observer = locationObserver(lastLocationCancelable)
        locationProvider.addLocationObserver(observer)
        // Finally wait for the flow to close to unregister the observer and stop live tracking
        awaitClose {
          locationProvider.removeLocationObserver(observer)
        }
      }
        // Convert this Flow into a hot flow so emissions are shared. That is, instead of each
        // collector (see registerLocationConsumer) create a new flow, the locations retrieved from
        // liveTrackingClient will be shared between all collectors.
        // This flow will replay the last emitted item to new collectors and remains active as long
        // as there are active collectors.
        .shareIn(scope, SharingStarted.WhileSubscribed(replayExpirationMillis = 0L), replay = 1)
    } else {
      // Live tracking client not available. Create a flow that emits LocationError
      val error = result.error!!
      logE(TAG, "LocationService error: $error")
      locationProviderNotAvailable =
        LocationError(LocationErrorCode.NOT_AVAILABLE, LIVE_TRACKING_CLIENT_NOT_AVAILABLE)
      emptyFlow()
    }
  }

  /**
   * @return a [LocationObserver] that emits the most recent [Location].
   */
  private fun ProducerScope<Location>.locationObserver(
    lastLocationCancelable: Cancelable?
  ) =
    object : LocationObserver {
      // Keep track of being already canceled to avoid calling native per each location update
      var lastLocationCanBeCanceled = lastLocationCancelable != null

      override fun onLocationUpdateReceived(locations: List<Location>) {
        if (lastLocationCanBeCanceled) {
          lastLocationCancelable?.cancel()
          lastLocationCanBeCanceled = false
        }
        trySendBlocking(locations.last())
      }
    }

  /**
   * Update the data source that drives the bearing updates of the [LocationProvider].
   *
   * @param source The [PuckBearing] used to drive the bearing updates.
   */
  fun updatePuckBearing(source: PuckBearing) {
    // emit the new source if it's different
    puckBearingFlow.value = source
  }

  /**
   * Register the location consumer to the Location Provider.
   *
   * The Location Consumer will get location and bearing updates from the Location Provider.
   *
   * @param locationConsumer
   */
  @SuppressLint("MissingPermission")
  override fun registerLocationConsumer(locationConsumer: LocationConsumer) {
    // If there's no location provider we can immediately inform the consumer
    if (locationProviderNotAvailable != null) {
      locationConsumer.onError(locationProviderNotAvailable!!)
    } else {
      // Otherwise, let's start a series of jobs responsible to collect various signals (altitude,
      // bearing, horizontal accuracy...) and proxy them to the `locationConsumer`.
      val previousJob =
        locationConsumersJobs.put(locationConsumer, collectLocationFlow(locationConsumer))
      previousJob?.cancel()
    }
  }

  /**
   * Creates a new [Job] responsible to notify [locationConsumer] about the different events: new
   * location, errors, bearing and accuracy.
   *
   * Internally, the created [Job] wraps four coroutines, one per each kind of event.
   */
  @OptIn(ExperimentalCoroutinesApi::class)
  private fun collectLocationFlow(locationConsumer: LocationConsumer): Job =
    CoroutineScope(Job() + mainCoroutineDispatcher).launch {
      val locationFlow = locationUpdatesFlow
      // Second one listens for new locations, converts it to Point and notifies the consumer
      launch {
        locationFlow.map {
          with(it) {
            altitude?.let { Point.fromLngLat(longitude, latitude, altitude!!) }
              ?: Point.fromLngLat(longitude, latitude)
          }
        }.collect {
          locationConsumer.onLocationUpdated(it)
        }
      }

      // Third one is responsible to provide bearing updates. It can get its values from either
      // the locationCompassEngineFlow or the locationFlow
      launch {
        // Listen for changes in the puck bearing source and switch the flow based on it
        puckBearingFlow.flatMapLatest { puckBearing ->
          when (puckBearing) {
            PuckBearing.HEADING -> deviceOrientationFlow
            PuckBearing.COURSE -> locationFlow.mapNotNull { it.bearing }
          }
        }.collect {
          locationConsumer.onBearingUpdated(it)
        }
      }

      // Finally, notify about accuracy changes if the consumer supports it
      launch {
        locationFlow.collect { location: Location ->
          location.horizontalAccuracy?.let { locationConsumer.onHorizontalAccuracyRadiusUpdated(it) }
        }
      }
    }

  /**
   * Unregister the location consumer from the Location Provider.
   *
   * @param locationConsumer
   */
  override fun unRegisterLocationConsumer(locationConsumer: LocationConsumer) {
    locationConsumersJobs.remove(locationConsumer)?.cancel()
  }

  /**
   * Register a listener to be invoked when compass needs to be calibrated.
   *
   * @param listener
   */
  fun addOnCompassCalibrationListener(listener: LocationCompassCalibrationListener) {
    locationCompassEngine.addCalibrationListener(listener)
  }

  /**
   * Unregister a listener to be invoked when compass needs to be calibrated.
   *
   * @param listener
   */
  fun removeCompassCalibrationListener(listener: LocationCompassCalibrationListener) {
    locationCompassEngine.removeCalibrationListener(listener)
  }

  @VisibleForTesting(otherwise = PRIVATE)
  internal companion object {
    private const val TAG = "MapboxLocationProvider"

    @VisibleForTesting(otherwise = PRIVATE)
    internal const val INIT_UPDATE_DELAY = 500L

    @VisibleForTesting(otherwise = PRIVATE)
    internal const val MAX_UPDATE_DELAY = 5000L

    @VisibleForTesting(otherwise = PRIVATE)
    internal const val LIVE_TRACKING_CLIENT_NOT_AVAILABLE = "LiveTrackingClient not available"
    private const val MISSING_PERMISSION_MSG =
      "Missing location permission, location component will not take effect before location permission is granted."
  }
}