package com.mapbox.maps.plugin.delegates

import com.mapbox.bindgen.Value
import com.mapbox.common.Cancelable
import com.mapbox.maps.FeatureStateOperationCallback
import com.mapbox.maps.MapboxDelicateApi
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.QueryFeatureStateCallback
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.TypedFeaturesetDescriptor

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

  /**
   * Sets a feature state expression that applies to features within the specified featureset.
   *
   * All feature states with expressions that evaluate to true will be applied to the feature.
   * Feature states from later added feature state expressions have higher priority. Regular feature states have higher priority than feature state expressions.
   * The final feature state is determined by applying states in order from lower to higher priority. As a result, multiple expressions that set states with different keys can affect the same features simultaneously.
   * If an expression is added for a feature set, properties from that feature set are used, not the properties from original sources.
   *
   * Note that updates to feature state expressions are asynchronous, so changes made by this method might not be
   * immediately visible and will have some delay. The displayed data will not be affected immediately.
   *
   * @param featureStateExpressionId Unique identifier for the state expression.
   * @param featureset The featureset descriptor that specifies which featureset the expression applies to.
   * @param expression The expression to evaluate for the state. Should return boolean.
   * @param state The `state` object with properties to update with their respective new values.
   * @param callback The `feature state operation callback` called when the operation completes.
   *
   */
  @MapboxExperimental
  @MapboxDelicateApi
  fun <FS : FeatureState> setFeatureStateExpression(
    featureStateExpressionId: Int,
    featureset: TypedFeaturesetDescriptor<FS, *>,
    expression: Value,
    state: FS,
    callback: FeatureStateOperationCallback = FeatureStateOperationCallback { }
  )

  /**
   * Removes a specific feature state expression.
   *
   * Remove a specific expression from the feature state expressions based on the expression ID.
   *
   * Note that updates to feature state expressions are asynchronous, so changes made by this method might not be
   * immediately visible and will have some delay.
   *
   * @param featureStateExpressionId The unique identifier of the expression to remove.
   * @param callback The `feature state operation callback` called when the operation completes.
   */
  @MapboxExperimental
  fun removeFeatureStateExpression(
    featureStateExpressionId: Int,
    callback: FeatureStateOperationCallback = FeatureStateOperationCallback { }
  )

  /**
   * Reset all feature state expressions.
   *
   * Note that updates to feature state expressions are asynchronous, so changes made by this method might not be
   * immediately visible and will have some delay.
   *
   * @param callback The `feature state operation callback` called when the operation completes.
   */
  @MapboxExperimental
  fun resetFeatureStateExpressions(
    callback: FeatureStateOperationCallback = FeatureStateOperationCallback { }
  )
}