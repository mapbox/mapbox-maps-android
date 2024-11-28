package com.mapbox.maps.plugin.delegates

import com.mapbox.bindgen.Value
import com.mapbox.common.Cancelable
import com.mapbox.maps.FeatureStateOperationCallback
import com.mapbox.maps.QueryFeatureStateCallback

/**
 * Definition of the feature state delegate needed to update the feature state of the map.
 */
interface MapFeatureStateDelegate {
  /**
   * Updates the state object of a feature within a style source.
   *
   * Update entries in the `state` object of a given feature within a style source. Only properties of the
   * `state` object will be updated. A property in the feature `state` object that is not listed in `state` will
   * retain its previous value. The properties must be paint properties, layout properties are not supported.
   *
   * Note that updates to feature `state` are asynchronous, so changes made by this method might not be
   * immediately visible using `getStateFeature`. And the corresponding source needs to be in use to ensure the
   * feature data it contains can be successfully updated.
   *
   * @param sourceId The style source identifier.
   * @param sourceLayerId The style source layer identifier (for multi-layer sources such as vector sources).
   * @param featureId The feature identifier of the feature whose state should be updated.
   * @param state The `state` object with properties to update with their respective new values.
   * @param callback The `feature state operation callback` called when the operation completes or ends.
   * @return A `cancelable` object that could be used to cancel the pending operation.
   *
   */
  fun setFeatureState(
    sourceId: String,
    sourceLayerId: String? = null,
    featureId: String,
    state: Value,
    callback: FeatureStateOperationCallback,
  ): Cancelable

  /**
   * Get the state map of a feature within a style source.
   *
   * Note that updates to feature state are asynchronous, so changes made by other methods might not be
   * immediately visible.
   *
   * @param sourceId The style source identifier.
   * @param sourceLayerId The style source layer identifier (for multi-layer sources such as vector sources).
   * @param featureId The feature identifier of the feature whose state should be queried.
   * @param callback The `query feature state callback` called when the query completes.
   * @return A `cancelable` object that could be used to cancel the pending query.
   */
  fun getFeatureState(
    sourceId: String,
    sourceLayerId: String? = null,
    featureId: String,
    callback: QueryFeatureStateCallback,
  ): Cancelable

  /**
   * Removes entries from a feature state object.
   *
   * Remove a specified property or all property from a feature's state object, depending on the value of
   * `stateKey`.
   *
   * Note that updates to feature state are asynchronous, so changes made by this method might not be
   * immediately visible using `getStateFeature`.
   *
   * @param sourceId The style source identifier.
   * @param sourceLayerId The style source layer identifier (for multi-layer sources such as vector sources).
   * @param featureId The feature identifier of the feature whose state should be removed.
   * @param stateKey The key of the property to remove. If `null`, all feature's state object properties are removed.
   * @param callback The `feature state operation callback` called when the operation completes or ends.
   * @return A `cancelable` object that could be used to cancel the pending operation.
   */
  fun removeFeatureState(
    sourceId: String,
    sourceLayerId: String? = null,
    featureId: String,
    stateKey: String? = null,
    callback: FeatureStateOperationCallback,
  ): Cancelable

  /**
   * Reset all the feature states within a style source.
   *
   * Remove all feature state entries from the specified style source or source layer.
   *
   * Note that updates to feature state are asynchronous, so changes made by this method might not be
   * immediately visible using `getStateFeature`.
   *
   * @param sourceId The style source identifier.
   * @param sourceLayerId The style source layer identifier (for multi-layer sources such as vector sources).
   * @param callback The `feature state operation callback` called when the operation completes or ends.
   * @return A `cancelable` object that could be used to cancel the pending operation.
   */
  fun resetFeatureStates(
    sourceId: String,
    sourceLayerId: String? = null,
    callback: FeatureStateOperationCallback
  ): Cancelable
}