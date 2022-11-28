package com.mapbox.maps.extension.androidauto

import androidx.car.app.Session
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.mapbox.maps.ContextMode
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.logW

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
  private val onCreated = mutableSetOf<MapboxCarMapObserver>()
  private val onStarted = mutableSetOf<MapboxCarMapObserver>()
  private val onResumed = mutableSetOf<MapboxCarMapObserver>()

  /**
   * @param observers observers that will be attached while the [Session] is in the
   * [Lifecycle.State.CREATED] state.
   */
  fun onCreated(vararg observers: MapboxCarMapObserver): MapboxCarMapSessionInstaller = apply {
    onCreated.addAll(observers)
  }

  /**
   * @param observers observers that will be attached while the [Session] is in the
   * [Lifecycle.State.STARTED] state.
   */
  fun onStarted(vararg observers: MapboxCarMapObserver): MapboxCarMapSessionInstaller = apply {
    onStarted.addAll(observers)
  }

  /**
   * @param observers observers that will be attached while the [Session] is in the
   * [Lifecycle.State.RESUMED] state.
   */
  fun onResumed(vararg observers: MapboxCarMapObserver): MapboxCarMapSessionInstaller = apply {
    onResumed.addAll(observers)
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
        enforceSharedContext(mapInitOptions)
        mapboxCarMap.setup(carContext, mapInitOptions)
        onCreated.forEach { mapboxCarMap.registerObserver(it) }
      }

      override fun onStart(owner: LifecycleOwner) {
        onStarted.forEach { mapboxCarMap.registerObserver(it) }
      }

      override fun onResume(owner: LifecycleOwner) {
        onResumed.forEach { mapboxCarMap.registerObserver(it) }
      }

      override fun onPause(owner: LifecycleOwner) {
        onResumed.reversed().forEach { mapboxCarMap.unregisterObserver(it) }
      }

      override fun onStop(owner: LifecycleOwner) {
        onStarted.reversed().forEach { mapboxCarMap.unregisterObserver(it) }
      }

      override fun onDestroy(owner: LifecycleOwner) {
        mapboxCarMap.clearObservers()
      }
    })
    return mapboxCarMap
  }

  private fun enforceSharedContext(mapInitOptions: MapInitOptions) {
    if (mapInitOptions.mapOptions.contextMode != ContextMode.SHARED) {
      if (mapInitOptions.mapOptions.contextMode == ContextMode.UNIQUE) {
        logW(
          TAG,
          "Explicitly switching context mode to MapOptions.contextMode = ContextMode.SHARED when creating the car map surface. " +
            "ContextMode.UNIQUE is not allowed as it leads to graphical artifacts and crashes."
        )
      }
      mapInitOptions.mapOptions = mapInitOptions.mapOptions.toBuilder()
        .contextMode(ContextMode.SHARED)
        .build()
    }
  }

  private companion object {
    private const val TAG = "CarMapSessionInstaller"
    private val defaultInitializer = MapboxCarMapInitializer { carContext ->
      MapInitOptions(context = carContext)
    }
  }
}