package com.mapbox.maps.extension.compose.style.interactions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.mapbox.maps.ClickInteraction
import com.mapbox.maps.InteractionContext
import com.mapbox.maps.LongClickInteraction
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.interactions.FeaturesetHolder
import com.mapbox.maps.interactions.InteractiveFeature

/**
 * Convenient method to create a [StyleInteractionsState] and remember it with the init block.
 *
 * @param init the initialization block applied to the [StyleInteractionsState] after it is created and remembered.
 *
 * @return [StyleInteractionsState]
 */
@MapboxExperimental
@Composable
public inline fun rememberStyleInteractionStates(crossinline init: StyleInteractionsState.() -> Unit = {}): StyleInteractionsState {
  return remember {
    StyleInteractionsState()
  }.apply(init)
}

/**
 * [StyleInteractionsState] manages the map interactions defined for the style.
 */
@Stable
@MapboxExperimental
public class StyleInteractionsState : BasicStyleInteractions() {

  @Composable
  internal fun BindTo(mapboxMap: MapboxMap) {
    BindToMap(map = mapboxMap)
  }

  /**
   * Add the [ClickInteraction] for given featureset defined with [featuresetId] and optional [importId].
   *
   * When several [ClickInteraction]s are registered for the same [featuresetId] and [importId] - the callbacks will be triggered from last to first.
   *
   * @param featuresetId mandatory featureset id.
   * @param importId optional style import id.
   * @param filter optional filter. Defaults to NULL.
   * @param onClick callback triggered when featureset is clicked.
   */
  @MapboxExperimental
  public fun onFeaturesetClicked(
    featuresetId: String,
    importId: String? = null,
    filter: Expression? = null,
    onClick: InteractiveFeatureScope.(InteractiveFeature<FeaturesetHolder.Featureset>, InteractionContext) -> Boolean
  ): StyleInteractionsState = apply {
    clickInteractionFeatureset(
      featuresetId = featuresetId,
      importId = importId,
      filter = filter,
      onClick = onClick
    )
  }

  /**
   * Add the [ClickInteraction] for given [layerId].
   *
   * When several [ClickInteraction]s are registered for the same [layerId] - the callbacks will be triggered from last to first.
   *
   * @param layerId mandatory layer id.
   * @param filter optional filter. Defaults to NULL.
   * @param onClick callback triggered when layer is clicked.
   */
  @MapboxExperimental
  public fun onLayerClicked(
    layerId: String,
    filter: Expression? = null,
    onClick: InteractiveFeatureScope.(InteractiveFeature<FeaturesetHolder.Layer>, InteractionContext) -> Boolean
  ): StyleInteractionsState = apply {
    clickInteractionLayer(layerId = layerId, filter = filter, onClick = onClick)
  }

  /**
   * Add the [LongClickInteraction] for given featureset defined with [featuresetId] and optional [importId].
   *
   * When several [LongClickInteraction]s are registered for the same [featuresetId] and [importId] - the callbacks will be triggered from last to first.
   *
   * @param featuresetId mandatory featureset id.
   * @param importId optional style import id.
   * @param filter optional filter. Defaults to NULL.
   * @param onLongClick callback triggered when featureset is clicked.
   */
  @MapboxExperimental
  public fun onFeaturesetLongClicked(
    featuresetId: String,
    importId: String? = null,
    filter: Expression? = null,
    onLongClick: InteractiveFeatureScope.(InteractiveFeature<FeaturesetHolder.Featureset>, InteractionContext) -> Boolean
  ): StyleInteractionsState = apply {
    longClickInteractionFeatureset(
      featuresetId = featuresetId,
      importId = importId,
      filter = filter,
      onLongClick = onLongClick
    )
  }

  /**
   * Add the [LongClickInteraction] for given [layerId].
   *
   * When several [LongClickInteraction]s are registered for the same [layerId] - the callbacks will be triggered from last to first.
   *
   * @param layerId mandatory layer id.
   * @param filter optional filter. Defaults to NULL.
   * @param onLongClick callback triggered when layer is clicked.
   */
  @MapboxExperimental
  public fun onLayerLongClicked(
    layerId: String,
    filter: Expression? = null,
    onLongClick: InteractiveFeatureScope.(InteractiveFeature<FeaturesetHolder.Layer>, InteractionContext) -> Boolean
  ): StyleInteractionsState = apply {
    longClickInteractionLayer(layerId = layerId, filter = filter, onLongClick = onLongClick)
  }

  /**
   * Add the [ClickInteraction] for the map surface itself.
   *
   * When several map [ClickInteraction]s are registered - the callbacks will be triggered from last to first.
   *
   * @param onClick callback triggered when map surface is clicked.
   */
  @MapboxExperimental
  public fun onMapClicked(
    onClick: InteractiveFeatureScope.(InteractionContext) -> Boolean
  ): StyleInteractionsState = apply {
    clickInteractionMap(onClick)
  }

  /**
   * Add the [LongClickInteraction] for the map surface itself.
   *
   * When several map [LongClickInteraction]s are registered - the callbacks will be triggered from last to first.
   *
   * @param onLongClick callback triggered when map surface is clicked.
   */
  @MapboxExperimental
  public fun onMapLongClicked(
    onLongClick: InteractiveFeatureScope.(InteractionContext) -> Boolean
  ): StyleInteractionsState = apply {
    longClickInteractionMap(onLongClick)
  }
}