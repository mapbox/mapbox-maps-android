package com.mapbox.maps.interactions

import com.mapbox.maps.MapboxExperimental

/**
 * Callback for `MapboxMap.getFeatureState`.
 */
@MapboxExperimental
fun interface FeatureStateCallback<FS : FeatureState> {

  /**
   * Triggered when `MapboxMap.getFeatureState` is done.
   *
   * Note: this callback is not triggered if `MapboxMap.getFeatureState` was canceled
   * or an error occurred.
   */
  fun onFeatureState(state: FS)
}