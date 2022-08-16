package com.mapbox.maps.extension.androidauto

import androidx.car.app.Screen
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.mapbox.maps.MapboxExperimental

/**
 * When attaching the many possible experiences onto [MapboxCarMap], this installer allows you to
 * remove all the boilerplate. Use this installer for creating experiences that will be
 * attached as long as a screen is in use. To assign an experience to the entire app lifecycle
 * please use the [MapboxCarMapSessionInstaller].
 *
 * Use this to setup each [Screen]. Use the [Screen.mapboxMapInstaller] extension!
 *
 * @param screen Android Auto Screen.
 * @param mapboxCarMap instance that can be also be found from the [install] function.
 */
@MapboxExperimental
class MapboxCarMapScreenInstaller(
  private val screen: Screen,
  private val mapboxCarMap: MapboxCarMap
) {
  private val created = mutableSetOf<MapboxCarMapObserver>()
  private val started = mutableSetOf<MapboxCarMapObserver>()
  private val resumed = mutableSetOf<MapboxCarMapObserver>()
  private var gestureHandler: MapboxCarMapGestureHandler? = DefaultMapboxCarMapGestureHandler()

  /**
   * @param observers observers that will be attached while the [Screen] is in the
   * [Lifecycle.State.CREATED] state.
   */
  fun created(vararg observers: MapboxCarMapObserver) = apply {
    created.addAll(observers)
  }

  /**
   * @param observers observers that will be attached while the [Screen] is in the
   * [Lifecycle.State.STARTED] state.
   */
  fun started(vararg observers: MapboxCarMapObserver) = apply {
    started.addAll(observers)
  }

  /**
   * @param observers observers that will be attached while the [Screen] is in the
   * [Lifecycle.State.RESUMED] state.
   */
  fun resumed(vararg observers: MapboxCarMapObserver) = apply {
    resumed.addAll(observers)
  }

  /**
   * @param handler gesture handler that will be used while the [Screen] is in the
   * [Lifecycle.State.RESUMED] state.
   */
  fun gestures(handler: MapboxCarMapGestureHandler?) = apply {
    gestureHandler = handler
  }

  /**
   * Subscribes to the [Screen] lifecycle and coordinates assigns the observers to their specified
   * lifecycle.
   *
   * @return [mapboxCarMap]
   */
  fun install(): MapboxCarMap {
    screen.lifecycle.addObserver(object : DefaultLifecycleObserver {
      override fun onCreate(owner: LifecycleOwner) {
        created.forEach { mapboxCarMap.registerObserver(it) }
      }

      override fun onStart(owner: LifecycleOwner) {
        started.forEach { mapboxCarMap.registerObserver(it) }
      }

      override fun onResume(owner: LifecycleOwner) {
        resumed.forEach { mapboxCarMap.registerObserver(it) }
        gestureHandler?.let { mapboxCarMap.setGestureHandler(it) }
      }

      override fun onPause(owner: LifecycleOwner) {
        resumed.reversed().forEach { mapboxCarMap.unregisterObserver(it) }
        mapboxCarMap.setGestureHandler(DefaultMapboxCarMapGestureHandler())
      }

      override fun onStop(owner: LifecycleOwner) {
        started.reversed().forEach { mapboxCarMap.unregisterObserver(it) }
      }

      override fun onDestroy(owner: LifecycleOwner) {
        created.reversed().forEach { mapboxCarMap.unregisterObserver(it) }
      }
    })
    return mapboxCarMap
  }
}