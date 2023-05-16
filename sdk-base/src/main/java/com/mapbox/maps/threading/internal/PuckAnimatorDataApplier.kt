package com.mapbox.maps.threading.internal

import androidx.annotation.RestrictTo
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental

/**
 * Abstraction to be used with [AnimationSynchronizer].
 * For internal usage only.
 */
@MapboxExperimental
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
interface PuckAnimatorDataApplier {

  /**
   * Set current latitude-longitude as [Point].
   */
  fun setLatLng(latLng: Point)

  /**
   * Set current bearing as [Double].
   */
  fun setBearing(bearing: Double)
}