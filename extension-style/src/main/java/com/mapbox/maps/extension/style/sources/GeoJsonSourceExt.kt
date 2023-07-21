@file:JvmName("GeoJsonSourceUtils")

package com.mapbox.maps.extension.style.sources

import com.mapbox.geojson.Feature
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.logW

/**
 * Add additional features to a GeoJSON style source.
 * The data will be scheduled and applied on a worker thread.
 *
 * To check the result of the operation, use events API, which will propagate a `map-loaded` event upon success
 * or a `map-loading-error` event upon failure.
 *
 * Partially updating a GeoJSON source is not compatible with using shared cache and generated IDs.
 * It is important to ensure that every feature in the GeoJSON style source, as well as the newly added
 * feature, has a unique ID (or a unique promote ID if in use). Failure to provide unique IDs will result
 * in a `map-loading-error`.
 *
 * The method allows the user to provide a data ID, which will be returned as the dataId parameter in the
 * `source-data-loaded` event. However, it's important to note that multiple partial updates can be queued
 * for the same GeoJSON source when ongoing source parsing is taking place. In these cases, the partial
 * updates will be applied to the source in batches. Only the data ID provided in the most recent call within
 * each batch will be included in the `source-data-loaded` event. If no data ID is provided in the most recent
 * call, the data ID in the `source-data-loaded`event will be null.
 *
 * Note: This method is not thread-safe. The List<Feature> will be processed on a worker thread,
 * please make sure the underlying features are immutable.
 *
 * Note: calling [GeoJsonSource.geometry], [GeoJsonSource.feature], [GeoJsonSource.featureCollection], [GeoJsonSource.data], [GeoJsonSource.url]
 * will clear any pending GeoJSON source data operations if they were scheduled before.
 *
 * @param dataId An arbitrary string used to track the given GeoJSON data, empty string means null ID.
 * @param features An array of GeoJSON features to be added to the source.
 *
 */
@JvmOverloads
fun GeoJsonSource.addGeoJSONSourceFeatures(
  features: List<Feature>,
  dataId: String = "",
) {
  delegate?.let { style ->
    workerHandler.post {
      style.addGeoJSONSourceFeatures(
        this.sourceId,
        dataId,
        features
      )
    }
  } ?: logW(TAG, "GeoJsonSource.addGeoJSONSourceFeatures is ignored. Style is not loaded yet.")
}

/**
 * Update existing features in a GeoJSON style source.
 * The data will be scheduled and applied on a worker thread.
 *
 * To check the result of the operation, use events API, which will propagate a `map-loaded` event upon success
 * or a `map-loading-error` event upon failure.
 *
 * Partially updating a GeoJSON source is not compatible with using shared cache and generated IDs.
 * It is important to ensure that every feature in the GeoJSON style source, as well as the newly added
 * feature, has a unique ID (or a unique promote ID if in use). Failure to provide unique IDs will result
 * in a `map-loading-error`.
 *
 * The method allows the user to provide a data ID, which will be returned as the dataId parameter in the
 * `source-data-loaded` event. However, it's important to note that multiple partial updates can be queued
 * for the same GeoJSON source when ongoing source parsing is taking place. In these cases, the partial
 * updates will be applied to the source in batches. Only the data ID provided in the most recent call within
 * each batch will be included in the `source-data-loaded` event. If no data ID is provided in the most recent
 * call, the data ID in the `source-data-loaded`event will be null.
 *
 * Note: This method is not thread-safe. The List<Feature> will be processed on a worker thread,
 * please make sure the underlying features are immutable.
 *
 * Note: calling [GeoJsonSource.geometry], [GeoJsonSource.feature], [GeoJsonSource.featureCollection], [GeoJsonSource.data], [GeoJsonSource.url]
 * will clear any pending GeoJSON source data operations if they were scheduled before.
 *
 * @param dataId An arbitrary string used to track the given GeoJSON data, empty string means null ID.
 * @param features the GeoJSON features to be updated in the source.
 *
 */
@JvmOverloads
fun GeoJsonSource.updateGeoJSONSourceFeatures(
  features: List<Feature>,
  dataId: String = "",
) {
  delegate?.let { style ->
    workerHandler.post {
      style.updateGeoJSONSourceFeatures(
        this.sourceId,
        dataId,
        features
      )
    }
  } ?: logW(TAG, "GeoJsonSource.updateGeoJSONSourceFeatures is ignored. Style is not loaded yet.")
}

/**
 * Remove features from a GeoJSON style source.
 * The data will be scheduled and applied on a worker thread.
 *
 * To check the result of the operation, use events API, which will propagate a `map-loaded` event upon success
 * or a `map-loading-error` event upon failure.
 *
 * Partially updating a GeoJSON source is not compatible with using shared cache and generated IDs.
 * It is important to ensure that every feature in the GeoJSON style source, as well as the newly added
 * feature, has a unique ID (or a unique promote ID if in use). Failure to provide unique IDs will result
 * in a `map-loading-error`.
 *
 * The method allows the user to provide a data ID, which will be returned as the dataId parameter in the
 * `source-data-loaded` event. However, it's important to note that multiple partial updates can be queued
 * for the same GeoJSON source when ongoing source parsing is taking place. In these cases, the partial
 * updates will be applied to the source in batches. Only the data ID provided in the most recent call within
 * each batch will be included in the `source-data-loaded` event. If no data ID is provided in the most recent
 * call, the data ID in the `source-data-loaded`event will be null.
 *
 * Note: This method is not thread-safe. The List<String> will be processed on a worker thread,
 * please make sure the underlying features are immutable.
 *
 * Note: calling [GeoJsonSource.geometry], [GeoJsonSource.feature], [GeoJsonSource.featureCollection], [GeoJsonSource.data], [GeoJsonSource.url]
 * will clear any pending GeoJSON source data operations if they were scheduled before.
 *
 * @param dataId An arbitrary string used to track the given GeoJSON data, empty string means null ID.
 * @param featureIds the Ids of the features that need to be removed from the source.
 *
 */
@JvmOverloads
fun GeoJsonSource.removeGeoJSONSourceFeatures(
  featureIds: List<String>,
  dataId: String = "",
) {
  delegate?.let { style ->
    workerHandler.post {
      style.removeGeoJSONSourceFeatures(
        this.sourceId,
        dataId,
        featureIds
      )
    }
  } ?: logW(TAG, "GeoJsonSource.removeGeoJSONSourceFeatures is ignored. Style is not loaded yet.")
}

private const val TAG = "GeoJsonSourceUtils"