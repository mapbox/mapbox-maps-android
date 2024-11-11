package com.mapbox.maps.coroutine

import com.mapbox.bindgen.Expected
import com.mapbox.maps.QueriedRenderedFeature
import com.mapbox.maps.QueriedSourceFeature
import com.mapbox.maps.RenderedQueryGeometry
import com.mapbox.maps.RenderedQueryOptions
import com.mapbox.maps.SourceQueryOptions
import com.mapbox.maps.plugin.delegates.MapFeatureQueryDelegate
import com.mapbox.maps.suspendMapboxCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Queries the map for rendered features.
 *
 * @param geometry The `screen pixel coordinates` (point, line string or box) to query for rendered features.
 * @param options The `render query options` for querying rendered features.
 *
 * @return a list of [QueriedRenderedFeature] or a string describing an error.
 */
@JvmSynthetic
suspend fun MapFeatureQueryDelegate.queryRenderedFeatures(
  geometry: RenderedQueryGeometry,
  options: RenderedQueryOptions,
): Expected<String, List<QueriedRenderedFeature>> =
  suspendMapboxCancellableCoroutine { continuation ->
    queryRenderedFeatures(geometry, options, continuation::resume)
  }

/**
 * Queries the map for source features.
 *
 * @param sourceId The style source identifier used to query for source features.
 * @param options The `source query options` for querying source features.
 *
 * @return a list of [QueriedSourceFeature] or a string describing an error.
 *
 * Note: In order to get expected results, the corresponding source needs to be in use and
 * the query shall be made after the corresponding source data is loaded.
 */
@JvmSynthetic
suspend fun MapFeatureQueryDelegate.querySourceFeatures(
  sourceId: String,
  options: SourceQueryOptions,
): Expected<String, List<QueriedSourceFeature>> =
  suspendMapboxCancellableCoroutine { continuation ->
    querySourceFeatures(sourceId, options, continuation::resume)
  }