package com.mapbox.maps.plugin.viewport.data

/**
 * Describes an object that provides desired camera positions to [ViewportCamera].
 *
 * Implementation should always store the latest available [ViewportData] and return it via
 * [getViewportData]. Whenever data becomes available,
 * registered [ViewportDataSourceUpdateObserver] should be notified.
 */
interface ViewportDataSource {

  /**
   * Get the latest [ViewportData].
   */
  fun getViewportData(): ViewportData

  /**
   * Register an observer that gets called whenever the available [ViewportData] changes.
   * The observer also gets notified with latest data on registration.
   */
  fun registerUpdateObserver(viewportDataSourceUpdateObserver: ViewportDataSourceUpdateObserver)

  /**
   * Unregister [ViewportDataSourceUpdateObserver].
   */
  fun unregisterUpdateObserver(viewportDataSourceUpdateObserver: ViewportDataSourceUpdateObserver)
}