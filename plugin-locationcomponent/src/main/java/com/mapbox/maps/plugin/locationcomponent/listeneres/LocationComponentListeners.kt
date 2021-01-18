package com.mapbox.maps.plugin.locationcomponent.listeneres

import com.mapbox.maps.plugin.locationcomponent.CompassEngine
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings

//
// Interfaces
//

/**
 * Interface definition for a callback to be invoked when a task is complete or cancelled.
 */
interface CancelableCallback {
  /**
   * Invoked when a task is cancelled.
   */
  fun onCancel()

  /**
   * Invoked when a task is complete.
   */
  fun onFinish()
}

/**
 * Callbacks related to the compass
 */
interface CompassListener {
  /**
   * Callback's invoked when a new compass update occurs. You can listen into the compass updates
   * using [CompassEngine.addCompassListener] and implementing these
   * callbacks. Note that this interface is also used internally to to update the UI chevron/arrow.
   *
   * @param userHeading the new compass heading
   */
  fun onCompassChanged(userHeading: Float)

  /**
   * This gets invoked when the compass accuracy status changes from one value to another. It
   * provides an integer value which is identical to the `SensorManager` class constants:
   *
   *  * [android.hardware.SensorManager.SENSOR_STATUS_NO_CONTACT]
   *  * [android.hardware.SensorManager.SENSOR_STATUS_UNRELIABLE]
   *  * [android.hardware.SensorManager.SENSOR_STATUS_ACCURACY_LOW]
   *  * [android.hardware.SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM]
   *  * [android.hardware.SensorManager.SENSOR_STATUS_ACCURACY_HIGH]
   *
   *
   * @param compassStatus the new accuracy of this sensor, one of
   * `SensorManager.SENSOR_STATUS_*`
   */
  fun onCompassAccuracyChange(compassStatus: Int)
}

/**
 * Listener that can be added as a callback when the last location update
 * is considered stale.
 *
 *
 * The time from the last location update that determines if a location update
 * is stale or not is provided by [LocationComponentSettings.staleStateTimeout].
 */
interface OnLocationStaleListener {
  /**
   * Called when the stale state changes.
   * @param isStale true if location is stale, false otherwise
   */
  fun onStaleStateChange(isStale: Boolean)
}

/**
 * The Location Component exposes an API for listening to when the user clicks on the location
 * layer icon visible on the map. When this event occurs, the [.onLocationComponentClick] method
 * gets invoked.
 */
interface OnLocationClickListener {
  /**
   * Called whenever user clicks on the location layer drawn on the map.
   */
  fun onLocationComponentClick()
}

// Internal interfaces

/**
 * Internal use.
 */
interface OnDeveloperAnimationListener {
  /**
   * Notifies listener when a developer invoked animation is about to start.
   */
  fun onDeveloperAnimationStarted()
}