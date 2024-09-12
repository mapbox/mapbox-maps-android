package com.mapbox.maps.extension.compose.style.imports

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.mapbox.maps.ClickInteraction
import com.mapbox.maps.InteractionContext
import com.mapbox.maps.LongClickInteraction
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.style.interactions.BasicStyleInteractions
import com.mapbox.maps.extension.compose.style.interactions.InteractiveFeatureScope
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.InteractiveFeature

/**
 * Convenient method to create a [StyleImportInteractionsState] and remember it with the init block.
 *
 * @param init the initialization block applied to the [StyleImportInteractionsState] after it is created and remembered.
 *
 * @return [StyleImportInteractionsState]
 */
@MapboxExperimental
@Composable
public inline fun rememberStyleImportInteractionsState(crossinline init: StyleImportInteractionsState.() -> Unit = {}): StyleImportInteractionsState {
  return remember {
    StyleImportInteractionsState()
  }.apply(init)
}

/**
 * [StyleImportInteractionsState] manages the map interactions defined for the style import.
 */
@Stable
@MapboxExperimental
public class StyleImportInteractionsState : BasicStyleInteractions() {

  @Composable
  internal fun BindTo(mapboxMap: MapboxMap, importId: String) {
    BindToMap(map = mapboxMap, importId)
  }

  /**
   * Add the [ClickInteraction] for given featureset defined with [featuresetId] and optional [importId].
   *
   * When several [ClickInteraction]s are registered for the same [featuresetId] and [importId] - the callbacks will be triggered from last to first.
   *
   * @param featuresetId mandatory featureset id.
   * @param importId optional style import id, if not set, the ID will be taken from [StyleImport.importId].
   * @param filter optional filter. Defaults to NULL.
   * @param onClick callback triggered when featureset is clicked.
   */
  @MapboxExperimental
  public fun onFeaturesetClicked(
    featuresetId: String,
    importId: String? = null,
    filter: Expression? = null,
    onClick: InteractiveFeatureScope.(InteractiveFeature<FeatureState>, InteractionContext) -> Boolean
  ): StyleImportInteractionsState = apply {
    clickInteractionFeatureset(
      featuresetId = featuresetId,
      importId = importId,
      filter = filter,
      onClick = onClick
    )
  }

  /**
   * Add the [LongClickInteraction] for given featureset defined with [featuresetId] and optional [importId].
   *
   * When several [LongClickInteraction]s are registered for the same [featuresetId] and [importId] - the callbacks will be triggered from last to first.
   *
   * @param featuresetId mandatory featureset id.
   * @param importId optional style import id,  if not set, the ID will be taken from [StyleImport.importId].
   * @param filter optional filter. Defaults to NULL.
   * @param onLongClick callback triggered when featureset is clicked.
   */
  @MapboxExperimental
  public fun onFeaturesetLongClicked(
    featuresetId: String,
    importId: String? = null,
    filter: Expression? = null,
    onLongClick: InteractiveFeatureScope.(InteractiveFeature<FeatureState>, InteractionContext) -> Boolean
  ): StyleImportInteractionsState = apply {
    longClickInteractionFeatureset(
      featuresetId = featuresetId,
      importId = importId,
      filter = filter,
      onLongClick = onLongClick
    )
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
  ): StyleImportInteractionsState = apply {
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
  ): StyleImportInteractionsState = apply {
    longClickInteractionMap(onLongClick)
  }
}