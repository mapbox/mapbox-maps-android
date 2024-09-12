package com.mapbox.maps.extension.compose.style.interactions

import com.mapbox.common.Cancelable
import com.mapbox.maps.FeatureStateOperationCallback
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.QueryFeatureStateCallback
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.InteractiveFeature

/**
 * The interactive feature scope provides extension functions to [InteractiveFeature]
 * so that user can update feature state directly on the [InteractiveFeature] within this scope.
 *
 * Note that this is a sealed interface, it's not expected to be implemented outside of this library.
 */
@MapboxExperimental
public sealed interface InteractiveFeatureScope {

  /**
   * Sets the state map for the [InteractiveFeature].
   *
   * @param state describes the new state of the map for given [InteractiveFeature].
   * @param callback The optional [QueryFeatureStateCallback] called when the query completes.
   *
   * @return A [Cancelable] object that could be used to cancel the pending query.
   */
  @MapboxExperimental
  public fun <FS : FeatureState> InteractiveFeature<FS>.setFeatureState(
    state: FS,
    callback: FeatureStateOperationCallback = FeatureStateOperationCallback {}
  ): Cancelable

  /**
   * Removes entries from a feature state based on the [InteractiveFeature].
   *
   * Removes a specified property or all property from a feature's state object, depending on the value of
   * [stateKey].
   *
   * Note that updates to feature state are asynchronous, so changes made by this method might not be
   * immediately visible using `getFeatureState`.
   *
   * @param stateKey The key of the property to remove. If `null`, all feature's state object properties are removed.
   * @param callback The [FeatureStateOperationCallback] called when the operation completes or ends.
   *
   * @return A [Cancelable] object that could be used to cancel the pending operation.
   */
  @MapboxExperimental
  public fun <FS : FeatureState> InteractiveFeature<FS>.removeFeatureState(
    stateKey: String? = null,
    callback: FeatureStateOperationCallback = FeatureStateOperationCallback { }
  ): Cancelable

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
  public fun <FS : FeatureState> InteractiveFeature<FS>.resetFeatureStates(
    callback: FeatureStateOperationCallback = FeatureStateOperationCallback { }
  ): Cancelable
}

@OptIn(MapboxExperimental::class)
internal class InteractiveFeatureScopeImpl internal constructor(private val mapboxMap: MapboxMap) :
  InteractiveFeatureScope {

  @MapboxExperimental
  override fun <FS : FeatureState> InteractiveFeature<FS>.setFeatureState(
    state: FS,
    callback: FeatureStateOperationCallback
  ): Cancelable {
    return mapboxMap.setFeatureState(
      interactiveFeature = this,
      state = state,
      callback = callback
    )
  }

  @MapboxExperimental
  override fun <FS : FeatureState> InteractiveFeature<FS>.removeFeatureState(
    stateKey: String?,
    callback: FeatureStateOperationCallback
  ): Cancelable {
    return mapboxMap.removeFeatureState(
      interactiveFeature = this,
      stateKey = stateKey,
      callback = callback
    )
  }

  @MapboxExperimental
  override fun <FS : FeatureState> InteractiveFeature<FS>.resetFeatureStates(callback: FeatureStateOperationCallback): Cancelable {
    return mapboxMap.resetFeatureStates(featuresetHolder = featuresetHolder, callback = callback)
  }
}