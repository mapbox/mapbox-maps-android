package com.mapbox.maps.extension.compose.style.layers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.mapbox.common.Cancelable
import com.mapbox.maps.ClickInteraction
import com.mapbox.maps.InteractionContext
import com.mapbox.maps.LongClickInteraction
import com.mapbox.maps.MapInteraction
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.style.interactions.BasicStyleInteractions
import com.mapbox.maps.extension.compose.style.interactions.generated.FeaturesetFeatureScope
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.FeaturesetFeature

/**
 * Convenient method to create a [LayerInteractionsState] and remember it with the init block.
 *
 * @param init the initialization block applied to the [LayerInteractionsState] after it is created and remembered.
 *
 * @return [LayerInteractionsState]
 */
@MapboxExperimental
@Composable
public inline fun rememberLayerInteractionsState(crossinline init: LayerInteractionsState.() -> Unit = {}): LayerInteractionsState {
  return remember {
    LayerInteractionsState()
  }.apply(init)
}

/**
 * [LayerInteractionsState] manages the map interactions defined for the layer.
 */
@Stable
@MapboxExperimental
public class LayerInteractionsState : BasicStyleInteractions() {

  private val styleLayerEntries: MutableList<(String) -> MapInteraction> = mutableListOf()

  @Composable
  private fun UpdateLayerInteractions(map: MapboxMap, layerId: String) {
    val cancelables = remember {
      mutableStateListOf<Cancelable>()
    }
    DisposableEffect(key1 = styleLayerEntries) {
      styleLayerEntries.forEach {
        cancelables.add(map.addInteraction(it.invoke(layerId)))
      }
      onDispose {
        cancelables.forEach { it.cancel() }
        cancelables.clear()
      }
    }
  }

  @Composable
  internal fun BindTo(mapboxMap: MapboxMap, layerId: String) {
    BindToMap(map = mapboxMap)
    UpdateLayerInteractions(map = mapboxMap, layerId = layerId)
  }

  /**
   * Add the [ClickInteraction] for this layer.
   *
   * When several [ClickInteraction]s are registered for the same layer - the callbacks will be triggered from last to first.
   *
   * @param filter optional filter. Defaults to NULL.
   * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
   * @param onClick callback triggered when layer is clicked.
   */
  @MapboxExperimental
  public fun onClicked(
    filter: Expression? = null,
    radius: Double? = null,
    onClick: FeaturesetFeatureScope.(FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean
  ): LayerInteractionsState = apply {
    styleLayerEntries.add { layerId ->
      ClickInteraction.layer(
        id = layerId,
        filter = filter,
        radius = radius,
        onClick = { feature, context ->
          featuresetFeatureScope?.onClick(feature, context) ?: false
        }
      )
    }
  }

  /**
   * Create the [LongClickInteraction] for this layer.
   *
   * When several [LongClickInteraction]s are registered for the same layer - the callbacks will be triggered from last to first.
   *
   * @param filter optional filter. Defaults to NULL.
   * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
   * @param onLongClick callback triggered when layer is clicked.
   */
  @MapboxExperimental
  public fun onLongClicked(
    filter: Expression? = null,
    radius: Double? = null,
    onLongClick: FeaturesetFeatureScope.(FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean
  ): LayerInteractionsState = apply {
    styleLayerEntries.add { layerId ->
      LongClickInteraction.layer(
        id = layerId,
        filter = filter,
        radius = radius,
        onLongClick = { feature, context ->
          featuresetFeatureScope?.onLongClick(feature, context) ?: false
        }
      )
    }
  }
}