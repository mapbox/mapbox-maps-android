package com.mapbox.maps.extension.androidauto

import androidx.car.app.Session
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapboxExperimental

/**
 * When attaching the many possible experiences onto [MapboxCarMap], this installer allows you to
 * remove all the boilerplate. Use this installer for creating experiences that will be
 * attached as long as the head unit is using your application. To assign an experience to a screen
 * please use the [MapboxCarMapScreenInstaller].
 *
 * Use this to setup the [Session]. Use the [Session.mapboxMapInstaller] extension!
 *
 * @param session Android Auto Session.
 * @param mapboxCarMap instance that can be also be found from the [install] function.
 */

@MapboxExperimental
class MapboxCarMapSessionInstaller(
  private val session: Session,
  private val mapboxCarMap: MapboxCarMap = MapboxCarMap()
) {
  private val created = mutableSetOf<MapboxCarMapObserver>()
  private val started = mutableSetOf<MapboxCarMapObserver>()
  private val resumed = mutableSetOf<MapboxCarMapObserver>()

  /**
   * @param observers observers that will be attached while the [Session] is in the
   * [Lifecycle.State.CREATED] state.
   */
  fun created(vararg observers: MapboxCarMapObserver) = apply {
    created.addAll(observers)
  }

  /**
   * @param observers observers that will be attached while the [Session] is in the
   * [Lifecycle.State.STARTED] state.
   */
  fun started(vararg observers: MapboxCarMapObserver) = apply {
    started.addAll(observers)
  }

  /**
   * @param observers observers that will be attached while the [Session] is in the
   * [Lifecycle.State.RESUMED] state.
   */
  fun resumed(vararg observers: MapboxCarMapObserver) = apply {
    resumed.addAll(observers)
  }

  /**
   * Subscribes to the [Session] lifecycle and coordinates assigns the observers to their specified
   * lifecycle.
   *
   * @param initializer for specifying custom [MapInitOptions] configurations
   * @return [mapboxCarMap]
   */
  @JvmOverloads
  fun install(
    initializer: MapboxCarMapInitializer = defaultInitializer
  ): MapboxCarMap {
    session.lifecycle.addObserver(object : DefaultLifecycleObserver {
      override fun onCreate(owner: LifecycleOwner) {
        val carContext = session.carContext
        val mapInitOptions = initializer.onCreate(carContext)
        mapboxCarMap.setup(carContext, mapInitOptions)
        created.forEach { mapboxCarMap.registerObserver(it) }
      }

      override fun onStart(owner: LifecycleOwner) {
        started.forEach { mapboxCarMap.registerObserver(it) }
      }

      override fun onResume(owner: LifecycleOwner) {
        resumed.forEach { mapboxCarMap.registerObserver(it) }
      }

      override fun onPause(owner: LifecycleOwner) {
        resumed.reversed().forEach { mapboxCarMap.unregisterObserver(it) }
      }

      override fun onStop(owner: LifecycleOwner) {
        started.reversed().forEach { mapboxCarMap.unregisterObserver(it) }
      }

      override fun onDestroy(owner: LifecycleOwner) {
        mapboxCarMap.clearObservers()
      }
    })
    return mapboxCarMap
  }

  private companion object {
    private val defaultInitializer = MapboxCarMapInitializer { carContext ->
      carContext.defaultMapInitOptions()
    }
  }
}