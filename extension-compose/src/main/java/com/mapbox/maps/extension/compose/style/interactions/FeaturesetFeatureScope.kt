package com.mapbox.maps.extension.compose.style.interactions

import com.mapbox.common.Cancelable
import com.mapbox.maps.FeatureStateOperationCallback
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.QueryFeatureStateCallback
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.FeatureStateKey
import com.mapbox.maps.interactions.FeaturesetFeature

/**
 * The featureset feature scope provides extension functions to [FeaturesetFeature]
 * so that user can update feature state directly on the [FeaturesetFeature] within this scope.
 *
 * Note that this is a sealed interface, it's not expected to be implemented outside of this library.
 */
@MapboxExperimental
public sealed interface FeaturesetFeatureScope {

  /**
   * Sets the state map for the [FeaturesetFeature].
   *
   * @param state describes the new state of the map for given [FeaturesetFeature].
   * @param callback The optional [QueryFeatureStateCallback] called when the query completes.
   *
   * @return A [Cancelable] object that could be used to cancel the pending query.
   */
  @MapboxExperimental
  public fun <FS : FeatureState> FeaturesetFeature<FS>.setFeatureState(
    state: FS,
    callback: FeatureStateOperationCallback = FeatureStateOperationCallback {}
  ): Cancelable

  /**
   * Removes entries from a feature state based on the [FeaturesetFeature].
   *
   * Removes a specified property or all property from a feature's state object, depending on the value of
   * [stateKey].
   *
   * Note that updates to feature state are asynchronous, so changes made by this method might not be
   * immediately visible using `getFeatureState`.
   *
   * @param stateKey The typed key of the property to remove. If `null`, all feature's state object properties are removed.
   * @param callback The [FeatureStateOperationCallback] called when the operation completes or ends.
   *
   * @return A [Cancelable] object that could be used to cancel the pending operation.
   */
  @MapboxExperimental
  public fun <FS, FSK> FeaturesetFeature<FS>.removeFeatureState(
    stateKey: FSK? = null,
    callback: FeatureStateOperationCallback = FeatureStateOperationCallback { }
  ): Cancelable where FS : FeatureState, FSK : FeatureStateKey<FS>

  /**
   * Reset all the feature states within a style source.
   *
   * Remove all feature state entries from the specified style source or source layer.
   *
   * Note that updates to feature state are asynchronous, so changes made by this method might not be
   * immediately visible using `getFeatureState`.
   *
   * @param callback The [FeatureStateOperationCallback] called when the operation completes or ends.
   *
   * @return A [Cancelable] object that could be used to cancel the pending operation.
   */
  @MapboxExperimental
  public fun <FS : FeatureState> FeaturesetFeature<FS>.resetFeatureStates(
    callback: FeatureStateOperationCallback = FeatureStateOperationCallback { }
  ): Cancelable
}

@OptIn(MapboxExperimental::class)
internal class FeaturesetFeatureScopeImpl internal constructor(private val mapboxMap: MapboxMap) :
  FeaturesetFeatureScope {

  @MapboxExperimental
  override fun <FS : FeatureState> FeaturesetFeature<FS>.setFeatureState(
    state: FS,
    callback: FeatureStateOperationCallback
  ): Cancelable {
    return mapboxMap.setFeatureState(
      featuresetFeature = this,
      state = state,
      callback = callback
    )
  }

  @MapboxExperimental
  override fun <FS, FSK> FeaturesetFeature<FS>.removeFeatureState(
    stateKey: FSK?,
    callback: FeatureStateOperationCallback
  ): Cancelable where FS : FeatureState, FSK : FeatureStateKey<FS> {
    return mapboxMap.removeFeatureState(
      featuresetFeature = this,
      stateKey = stateKey,
      callback = callback
    )
  }

  @MapboxExperimental
  override fun <FS : FeatureState> FeaturesetFeature<FS>.resetFeatureStates(callback: FeatureStateOperationCallback): Cancelable {
    return mapboxMap.resetFeatureStates(descriptor = descriptor, callback = callback)
  }
}