package com.mapbox.maps.plugin.delegates

import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.maps.QueryFeatureExtensionCallback
import com.mapbox.maps.QueryFeatureStateCallback
import java.util.HashMap

/**
 * Delegate that to handle features in a source.
 */
interface MapFeatureDelegate {

  /**
   * Queries for feature extension values in a GeoJSON source.
   *
   * @param sourceIdentifier The identifier of the source to query.
   * @param feature to look for in the query.
   * @param extension, now only support keyword 'supercluster'.
   * @param extensionField, now only support following three extensions:
   *        'children': returns the children of a cluster (on the next zoom level).
   *        'leaves': returns all the leaves of a cluster (given its cluster_id)
   *        'expansion-zoom': returns the zoom on which the cluster expands into several children (useful for "click to zoom" feature).
   * @param args used for further query specification when using 'leaves' extensionField. Now only support following two args:
   *        'limit': the number of points to return from the query (must use type 'uint64_t', set to maximum for all points)
   *        'offset': the amount of points to skip (for pagination, must use type 'uint64_t')
   *
   * @param callback The result will be returned through the [QueryFeatureExtensionCallback].
   *         The result could be a feature extension value containing either a value (expansion-zoom) or a feature collection (children or leaves).
   *         Or a string describing an error if the operation was not successful.
   */
  fun queryFeatureExtensions(
    sourceIdentifier: String,
    feature: Feature,
    extension: String,
    extensionField: String,
    args: HashMap<String, Value>?,
    callback: QueryFeatureExtensionCallback
  )

  /**
   * Update the state map of a feature within a style source.
   *
   * Update entries in the state map of a given feature within a style source. Only entries listed in the
   * \p state map will be updated. An entry in the feature state map that is not listed in \p state will
   * retain its previous value.
   *
   * Note that updates to feature state are asynchronous, so changes made by this method migth not be
   * immediately visible using getStateFeature().
   *
   * @param sourceId Style source identifier.
   * @param sourceLayerId Style source layer identifier (for multi-layer sources such as vector sources).
   * @param featureId Identifier of the feature whose state should be updated.
   * @param state Map of entries to update with their respective new values.
   */
  fun setFeatureState(
    sourceId: String,
    sourceLayerId: String? = null,
    featureId: String,
    state: Value
  )

  /**
   * Get the state map of a feature within a style source.
   *
   * Note that updates to feature state are asynchronous, so changes made by other methods might not be
   * immediately visible.
   *
   * @param sourceId Style source identifier.
   * @param sourceLayerId Style source layer identifier (for multi-layer sources such as vector sources).
   * @param featureId Identifier of the feature whose state should be queried.
   * @return Feature's state map or an empty map if the feature could not be found.
   */
  fun getFeatureState(
    sourceId: String,
    sourceLayerId: String? = null,
    featureId: String,
    callback: QueryFeatureStateCallback
  )

  /**
   * Remove entries from a feature state map.
   *
   * Remove a specified entry or all entries from a feature's state map, depending on the value of stateKey.
   *
   * Note that updates to feature state are asynchronous, so changes made by this method migth not be
   * immediately visible using getStateFeature().
   *
   * @param sourceId Style source identifier.
   * @param sourceLayerId Style source layer identifier (for multi-layer sources such as vector sources).
   * @param featureId Identifier of the feature whose state should be removed.
   * @param stateKey Key of the entry to remove. If empty, the entire state is removed.
   */
  fun removeFeatureState(
    sourceId: String,
    sourceLayerId: String? = null,
    featureId: String,
    stateKey: String? = null
  )
}