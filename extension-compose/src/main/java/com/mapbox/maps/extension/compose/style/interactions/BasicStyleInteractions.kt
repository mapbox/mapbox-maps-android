package com.mapbox.maps.extension.compose.style.interactions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.mapbox.common.Cancelable
import com.mapbox.maps.ClickInteraction
import com.mapbox.maps.InteractionContext
import com.mapbox.maps.LongClickInteraction
import com.mapbox.maps.MapInteraction
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.interactions.FeaturesetHolder
import com.mapbox.maps.interactions.InteractiveFeature

/**
 * Base class for Interactions, it handles the interaction entries that would be added to the map upon bind.
 *
 * Note this is a generic implementation with no public API exposed.
 */
@MapboxExperimental
public abstract class BasicStyleInteractions {
  // map interaction builder entries that will be built and added to the map when the state is bind to the map.
  private val entries: MutableList<(String?) -> MapInteraction> = mutableListOf()

  // the interactive feature scope is created on bind to the map, and it provides extension functions to InteractiveFeature
  // so that user can update feature state directly on the InteractiveFeature in this scope.
  protected var interactiveFeatureScope: InteractiveFeatureScope? = null

  // Update interactions with the import id from the context
  @Composable
  protected fun UpdateInteractions(map: MapboxMap, importId: String?) {
    val cancelables = remember {
      mutableStateListOf<Cancelable>()
    }
    DisposableEffect(key1 = entries) {
      entries.forEach {
        cancelables.add(map.addInteraction(it.invoke(importId)))
      }
      onDispose {
        cancelables.forEach { it.cancel() }
        cancelables.clear()
      }
    }
  }

  // Bind the interactions to the map, initialising the interactive features scope, and add cached interactions to the map.
  @Composable
  protected fun BindToMap(map: MapboxMap, importId: String? = null) {
    UpdateInteractions(map = map, importId)
    DisposableEffect(Unit) {
      interactiveFeatureScope = InteractiveFeatureScopeImpl(map)
      onDispose {
        interactiveFeatureScope = null
      }
    }
  }

  /**
   * Create and add the [ClickInteraction] for given featureset defined with [featuresetId] and optional [importId].
   *
   * When several [ClickInteraction]s are registered for the same [featuresetId] and [importId] - the callbacks will be triggered from last to first.
   *
   * @param featuresetId mandatory featureset id.
   * @param importId optional style import id.
   * @param filter optional filter. Defaults to NULL.
   * @param onClick callback triggered when featureset is clicked.
   */
  protected fun clickInteractionFeatureset(
    featuresetId: String,
    importId: String? = null,
    filter: Expression? = null,
    onClick: InteractiveFeatureScope.(InteractiveFeature<FeaturesetHolder.Featureset>, InteractionContext) -> Boolean
  ) {
    entries.add { import ->
      ClickInteraction.featureset(
        featuresetId = featuresetId,
        importId = importId ?: import,
        filter = filter,
        onClick = { feature, context ->
          interactiveFeatureScope?.onClick(feature, context) ?: false
        }
      )
    }
  }

  /**
   * Create and add the [ClickInteraction] for given [layerId].
   *
   * When several [ClickInteraction]s are registered for the same [layerId] - the callbacks will be triggered from last to first.
   *
   * @param layerId mandatory layer id.
   * @param filter optional filter. Defaults to NULL.
   * @param onClick callback triggered when layer is clicked.
   */
  protected fun clickInteractionLayer(
    layerId: String,
    filter: Expression? = null,
    onClick: InteractiveFeatureScope.(InteractiveFeature<FeaturesetHolder.Layer>, InteractionContext) -> Boolean
  ) {
    entries.add {
      ClickInteraction.layer(
        layerId = layerId,
        filter = filter,
        onClick = { feature, context ->
          interactiveFeatureScope?.onClick(feature, context) ?: false
        }
      )
    }
  }

  /**
   * Create and add the [ClickInteraction] for the map surface itself.
   *
   * When several map [ClickInteraction]s are registered - the callbacks will be triggered from last to first.
   *
   * @param onClick callback triggered when map surface is clicked.
   */
  protected fun clickInteractionMap(onClick: InteractiveFeatureScope.(InteractionContext) -> Boolean) {
    entries.add {
      ClickInteraction { context ->
        interactiveFeatureScope?.onClick(context) ?: false
      }
    }
  }

  /**
   * Create and add the [LongClickInteraction] for the map surface itself.
   *
   * When several map [LongClickInteraction]s are registered - the callbacks will be triggered from last to first.
   *
   * @param onLongClick callback triggered when map surface is clicked.
   */
  protected fun longClickInteractionMap(onLongClick: InteractiveFeatureScope.(InteractionContext) -> Boolean) {
    entries.add {
      LongClickInteraction { context ->
        interactiveFeatureScope?.onLongClick(context) ?: false
      }
    }
  }

  /**
   * Create and add the [LongClickInteraction] for given featureset defined with [featuresetId] and optional [importId].
   *
   * When several [LongClickInteraction]s are registered for the same [featuresetId] and [importId] - the callbacks will be triggered from last to first.
   *
   * @param featuresetId mandatory featureset id.
   * @param importId optional style import id.
   * @param filter optional filter. Defaults to NULL.
   * @param onLongClick callback triggered when featureset is clicked.
   */
  protected fun longClickInteractionFeatureset(
    featuresetId: String,
    importId: String? = null,
    filter: Expression? = null,
    onLongClick: InteractiveFeatureScope.(InteractiveFeature<FeaturesetHolder.Featureset>, InteractionContext) -> Boolean
  ) {
    entries.add { import ->
      LongClickInteraction.featureset(
        featuresetId = featuresetId,
        importId = importId ?: import,
        filter = filter,
        onLongClick = { feature, context ->
          interactiveFeatureScope?.onLongClick(feature, context) ?: false
        }
      )
    }
  }

  /**
   * Create and add the [LongClickInteraction] for given [layerId].
   *
   * When several [LongClickInteraction]s are registered for the same [layerId] - the callbacks will be triggered from last to first.
   *
   * @param layerId mandatory layer id.
   * @param filter optional filter. Defaults to NULL.
   * @param onLongClick callback triggered when layer is clicked.
   */
  protected fun longClickInteractionLayer(
    layerId: String,
    filter: Expression? = null,
    onLongClick: InteractiveFeatureScope.(InteractiveFeature<FeaturesetHolder.Layer>, InteractionContext) -> Boolean
  ) {
    entries.add {
      LongClickInteraction.layer(
        layerId = layerId,
        filter = filter,
        onLongClick = { feature, context ->
          interactiveFeatureScope?.onLongClick(feature, context) ?: false
        }
      )
    }
  }
}