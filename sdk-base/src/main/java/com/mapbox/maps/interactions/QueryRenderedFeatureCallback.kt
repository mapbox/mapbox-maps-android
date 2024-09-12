package com.mapbox.maps.interactions

import com.mapbox.maps.MapboxExperimental

/**
 * Callback for `MapboxMap.queryRenderedFeature`.
 */
@MapboxExperimental
fun interface QueryRenderedFeatureCallback<FS : FeatureState> {

  /**
   * Triggered when `MapboxMap.queryRenderedFeature` is done and there is an actual [interactiveFeature]
   * to return for given [FeaturesetHolder].
   *
   * Note: this callback is not triggered if `MapboxMap.queryRenderedFeature` was canceled
   * or an error occurred.
   */
  fun onQueryRenderedFeature(interactiveFeature: InteractiveFeature<FS>)
}