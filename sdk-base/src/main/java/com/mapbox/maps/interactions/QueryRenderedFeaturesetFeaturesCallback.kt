package com.mapbox.maps.interactions

import com.mapbox.maps.MapboxExperimental

/**
 * Callback for `MapboxMap.queryRenderedFeatures`.
 */
@MapboxExperimental
fun interface QueryRenderedFeaturesetFeaturesCallback<FF : FeaturesetFeature<*>> {

  /**
   * Triggered when `MapboxMap.queryRenderedFeatures` is done and there is an actual [featuresetFeatures] list
   * to return for given [TypedFeaturesetDescriptor]. The features are returned in the order of how they are rendered
   * on the actual map.
   *
   * Note: this callback is not triggered if `MapboxMap.queryRenderedFeatures` was canceled
   * or an error occurred.
   */
  fun onQueryRenderedFeatures(featuresetFeatures: List<FF>)
}