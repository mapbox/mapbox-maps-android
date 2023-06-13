package com.mapbox.maps.extension.androidauto

import android.graphics.Rect
import androidx.car.app.AppManager
import androidx.car.app.CarContext
import androidx.car.app.Session
import androidx.car.app.SurfaceCallback
import androidx.lifecycle.Lifecycle
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapboxExperimental

/**
 * This is the main entry point for controlling the Mapbox car map surface.
 *
 * You can add the [MapboxCarMap] to your entire [androidx.car.app.Session] using the [Lifecycle].
 * And then any [androidx.car.app.Screen] that is using a supported template will automatically
 * show the Mapbox map. You can also, specify a [MapboxCarMap] for each individual
 * [androidx.car.app.Screen] by constructing the [MapboxCarMap] with the screen Lifecycle.
 *
 * Supported templates include:
 *  - [androidx.car.app.navigation.model.NavigationTemplate]
 *  - [androidx.car.app.navigation.model.RoutePreviewNavigationTemplate]
 *  - [androidx.car.app.navigation.model.PlaceListNavigationTemplate]
 *
 * Customize your [MapboxCarMap] with your own implementations of [MapboxCarMapObserver].
 * Use the [registerObserver] and [unregisterObserver] functions to load and detach the observers.
 *
 * The internals of this class use [AppManager.setSurfaceCallback], which assumes there is a single
 * surface callback. Do not use setSurfaceCallback, and do not create multiple instances of
 * [MapboxCarMap].
 *
 * @since 1.0.0
 */
class MapboxCarMap {
  private val carMapSurfaceOwner = CarMapSurfaceOwner()

  /**
   * The initial options used to [setup] the map.
   */
  val mapInitOptions: MapInitOptions by lazy {
    check(carMapSurfaceOwner.isSetup()) {
      "You must call MapboxCarMap.setup before you can access the MapboxCarMap.mapInitOptions"
    }
    carMapSurfaceOwner.mapInitOptions
  }

  /**
   * Accessor for the carContext provided to the MapInitOptions. This makes it easier to create
   * screens with the MapboxCarMap in the constructor.
   *
   * For example:
   *  class YourMapScreen(val mapboxCarMap: MapboxCarMap) : Screen(mapboxCarMap.carContext) {
   *
   * The carContext can also be found in the [MapboxCarMapObserver] callbacks. Make sure to
   * call [MapboxCarMap.clearObservers] when your car session is destroyed.
   */
  val carContext: CarContext by lazy {
    check(carMapSurfaceOwner.isSetup()) {
      "You must call MapboxCarMap.setup before you can access the MapboxCarMap.carContext"
    }
    carMapSurfaceOwner.carContext
  }

  /**
   * Calls [AppManager.setSurfaceCallback] to give Mapbox access to render the map onto Android
   * Auto head units. You should only call this once per [CarContext] in a [Session].
   *
   * @param carContext Android Auto CarContext
   * @param mapInitOptions parameter to initialize the head unit map
   */
  @OptIn(MapboxExperimental::class)
  fun setup(
    carContext: CarContext,
    mapInitOptions: MapInitOptions,
  ): MapboxCarMap = apply {
    val surfaceCallback = prepareSurfaceCallback(carContext, mapInitOptions)
    carContext.getCarService(AppManager::class.java).setSurfaceCallback(surfaceCallback)
  }

  /**
   * Instead of using [setup], this function allows you to create your own [SurfaceCallback] and
   * forward the calls to the returned [SurfaceCallback]. This makes it possible for you to adopt
   * new api versions or intercept the calls and continue to use the [MapboxCarMap] as designed.
   *
   * This may be a temporary solution, while androidx.car.app:app:1.3.0 is rolling out
   * [SurfaceCallback.onClick]. If there is no use for this function in the future, it will be
   * removed.
   */
  @MapboxExperimental
  fun prepareSurfaceCallback(
    carContext: CarContext,
    mapInitOptions: MapInitOptions
  ): SurfaceCallback {
    check(mapInitOptions.context is CarContext) {
      "You must set up the MapboxCarMap MapInitOptions with a CarContext"
    }
    carMapSurfaceOwner.setup(carContext, mapInitOptions)
    return carMapSurfaceOwner
  }

  /**
   * Returns the current [MapboxCarMapSurface]. It is recommended to use [registerObserver] and
   * [MapboxCarMapObserver] to attach and detach your customizations.
   */
  val carMapSurface: MapboxCarMapSurface?
    get() {
      return carMapSurfaceOwner.mapboxCarMapSurface
    }

  /**
   * Accessor to the visible area calculated by the car library. It is recommended to
   * use the values returned by [MapboxCarMapObserver.onVisibleAreaChanged].
   */
  val visibleArea: Rect?
    get() {
      return carMapSurfaceOwner.visibleArea
    }

  /**
   * Accessor to the edgeInsets calculated by the car library. It is recommended to
   * use the values returned by [MapboxCarMapObserver.onVisibleAreaChanged].
   */
  val visibleEdgeInsets: EdgeInsets?
    get() {
      return carMapSurfaceOwner.visibleEdgeInsets
    }

  /**
   * Accessor to the stable area calculated by the car library. It is recommended to
   * use the values returned by [MapboxCarMapObserver.onStableAreaChanged].
   */
  val stableArea: Rect?
    get() {
      return carMapSurfaceOwner.stableArea
    }

  /**
   * Accessor to the stableEdgeInsets calculated by the car library. It is recommended to
   * use the values returned by [MapboxCarMapObserver.onStableAreaChanged].
   */
  val stableEdgeInsets: EdgeInsets?
    get() {
      return carMapSurfaceOwner.stableEdgeInsets
    }

  /**
   * @param mapboxCarMapObserver implements the desired mapbox car experiences
   */
  fun registerObserver(mapboxCarMapObserver: MapboxCarMapObserver): MapboxCarMap = apply {
    carMapSurfaceOwner.registerObserver(mapboxCarMapObserver)
  }

  /**
   * @param mapboxCarMapObserver the instance used in [registerObserver]
   */
  fun unregisterObserver(mapboxCarMapObserver: MapboxCarMapObserver): MapboxCarMap = apply {
    carMapSurfaceOwner.unregisterObserver(mapboxCarMapObserver)
  }

  /**
   * Optional function to clear all observers registered through [registerObserver]
   */
  fun clearObservers(): MapboxCarMap = apply {
    carMapSurfaceOwner.clearObservers()
  }

  /**
   * Override the car gestures with your own implementation. If you would like to build a custom
   * experience for handling gestures, you can implement the [MapboxCarMapGestureHandler]
   * interface, or override the [DefaultMapboxCarMapGestureHandler], or set to null to disable
   * gesture handling.
   */
  fun setGestureHandler(gestureHandler: MapboxCarMapGestureHandler?): MapboxCarMap = apply {
    carMapSurfaceOwner.gestureHandler = gestureHandler
  }
}