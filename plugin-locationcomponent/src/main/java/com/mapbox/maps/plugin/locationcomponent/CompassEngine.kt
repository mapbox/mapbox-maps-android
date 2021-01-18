package com.mapbox.maps.plugin.locationcomponent

import android.hardware.SensorManager
import com.mapbox.maps.plugin.locationcomponent.listeneres.CompassListener

/**
 * Interface defining the source of compass heading data that is
 * consumed by the [LocationComponentPlugin] when in compass related
 * [RenderMode].
 */
interface CompassEngine {
  /**
   * Adds a [CompassListener] that can be used to
   * receive heading and state changes.
   *
   * @param compassListener to be added
   */
  fun addCompassListener(compassListener: CompassListener)

  /**
   * Removes a [CompassListener] that can be used to
   * receive heading and state changes.
   *
   * @param compassListener to be removed
   */
  fun removeCompassListener(compassListener: CompassListener)

  /**
   * Returns the last heading value produced and pushed via
   * a compass listener.
   *
   * @return last heading value
   */
  val lastHeading: Float

  /**
   * Provides the last know accuracy status from the sensor manager.
   * <p>
   * An integer value which is identical to the [SensorManager] class constants:
   * <ul>
   * <li>{@link android.hardware.SensorManager#SENSOR_STATUS_NO_CONTACT}</li>
   * <li>{@link android.hardware.SensorManager#SENSOR_STATUS_UNRELIABLE}</li>
   * <li>{@link android.hardware.SensorManager#SENSOR_STATUS_ACCURACY_LOW}</li>
   * <li>{@link android.hardware.SensorManager#SENSOR_STATUS_ACCURACY_MEDIUM}</li>
   * <li>{@link android.hardware.SensorManager#SENSOR_STATUS_ACCURACY_HIGH}</li>
   * </ul>
   *
   * @return last accuracy status
   */
  val lastAccuracySensorStatus: Int
}