package com.mapbox.maps.extension.androidauto

import androidx.car.app.CarContext
import androidx.car.app.Session
import androidx.lifecycle.Lifecycle
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapboxExperimental

/**
 * Lazy initialization for the [MapInitOptions]. The [CarContext] is not created until the [Session]
 * has been created. Use this initializer to specify custom initial map options.
 */
@MapboxExperimental
fun interface MapboxCarMapInitializer {
  /**
   * Triggered once the [Session] reaches the [Lifecycle.State.CREATED] state.
   *
   * @param carContext from [Session.getCarContext].
   * @return [MapInitOptions] for the initial [MapboxCarMap] configuration.
   */
  fun onCreate(carContext: CarContext): MapInitOptions
}