package com.mapbox.maps.extension.compose.style.interactions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.mapbox.maps.ClickInteraction
import com.mapbox.maps.InteractionContext
import com.mapbox.maps.LongClickInteraction
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.style.interactions.generated.FeaturesetFeatureScope
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.FeaturesetFeature

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
   * Add the [ClickInteraction] for given featureset defined with [id] and optional [importId].
   *
   * When several [ClickInteraction]s are registered for the same [id] and [importId] - the callbacks will be triggered from last to first.
   *
   * @param id mandatory featureset id.
   * @param importId optional style import id.
   * @param filter optional filter. Defaults to NULL.
   * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
   * @param onClick callback triggered when featureset is clicked.
   */
  @MapboxExperimental
  public fun onFeaturesetClicked(
    id: String,
    importId: String? = null,
    filter: Expression? = null,
    radius: Double? = null,
    onClick: FeaturesetFeatureScope.(FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean
  ): StyleInteractionsState = apply {
    clickInteractionFeatureset(
      featuresetId = id,
      importId = importId,
      filter = filter,
      radius = radius,
      onClick = onClick
    )
  }

  /**
   * Add the [ClickInteraction] for given [id].
   *
   * When several [ClickInteraction]s are registered for the same [id] - the callbacks will be triggered from last to first.
   *
   * @param id mandatory layer id.
   * @param filter optional filter. Defaults to NULL.
   * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
   * @param onClick callback triggered when layer is clicked.
   */
  @MapboxExperimental
  public fun onLayerClicked(
    id: String,
    filter: Expression? = null,
    radius: Double? = null,
    onClick: FeaturesetFeatureScope.(FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean
  ): StyleInteractionsState = apply {
    clickInteractionLayer(layerId = id, filter = filter, radius = radius, onClick = onClick)
  }

  /**
   * Add the [LongClickInteraction] for given featureset defined with [id] and optional [importId].
   *
   * When several [LongClickInteraction]s are registered for the same [id] and [importId] - the callbacks will be triggered from last to first.
   *
   * @param id mandatory featureset id.
   * @param importId optional style import id.
   * @param filter optional filter. Defaults to NULL.
   * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
   * @param onLongClick callback triggered when featureset is clicked.
   */
  @MapboxExperimental
  public fun onFeaturesetLongClicked(
    id: String,
    importId: String? = null,
    filter: Expression? = null,
    radius: Double? = null,
    onLongClick: FeaturesetFeatureScope.(FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean
  ): StyleInteractionsState = apply {
    longClickInteractionFeatureset(
      featuresetId = id,
      importId = importId,
      filter = filter,
      radius = radius,
      onLongClick = onLongClick
    )
  }

  /**
   * Add the [LongClickInteraction] for given [id].
   *
   * When several [LongClickInteraction]s are registered for the same [id] - the callbacks will be triggered from last to first.
   *
   * @param id mandatory layer id.
   * @param filter optional filter. Defaults to NULL.
   * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
   * @param onLongClick callback triggered when layer is clicked.
   */
  @MapboxExperimental
  public fun onLayerLongClicked(
    id: String,
    filter: Expression? = null,
    radius: Double? = null,
    onLongClick: FeaturesetFeatureScope.(FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean
  ): StyleInteractionsState = apply {
    longClickInteractionLayer(layerId = id, filter = filter, radius = radius, onLongClick = onLongClick)
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
    onClick: FeaturesetFeatureScope.(InteractionContext) -> Boolean
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
    onLongClick: FeaturesetFeatureScope.(InteractionContext) -> Boolean
  ): StyleInteractionsState = apply {
    longClickInteractionMap(onLongClick)
  }
}