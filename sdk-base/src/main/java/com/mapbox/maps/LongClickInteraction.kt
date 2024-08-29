package com.mapbox.maps

import com.mapbox.bindgen.Value
import com.mapbox.maps.ClickInteraction.Companion.featureset
import com.mapbox.maps.ClickInteraction.Companion.invoke
import com.mapbox.maps.ClickInteraction.Companion.layer
import com.mapbox.maps.LongClickInteraction.Companion.featureset
import com.mapbox.maps.LongClickInteraction.Companion.invoke
import com.mapbox.maps.interactions.BaseInteractiveFeature
import com.mapbox.maps.interactions.FeaturesetHolder
import com.mapbox.maps.interactions.InteractiveFeature

/**
 * Defines the long click interaction.
 *
 * Refer to static factory methods to create the [LongClickInteraction]:
 *  1. [featureset] to register the long click interaction for given `featuresetId` and optional `importId` (when defining the interaction for an imported style).
 *  2. [layer] to register the long click list interaction for give `layerId`.
 *  3. [invoke] (meaning empty constructor in Kotlin and static method `map` in Java) to register the long click interaction for the map surface itself.
 *
 * Long click interaction callback requires returning `true` | `false` where `true` means that the interaction
 * has been consumed and other registered long click interactions will not have the callback invoked and `false` means
 * that the interactions has not been consumed and other registered long click interactions (if any) will have the callback invoked.
 *
 * Long click interactions callbacks are fired in determined order:
 *  1. For unique feature sets defined as [FeaturesetDescriptor] - the top most rendered feature will trigger the callback first.
 *  2. [LongClickInteraction.invoke] (map surface long click interaction outside of all the feature sets) will always get triggered last.
 *  3. When having several [LongClickInteraction]s with the same [FeaturesetDescriptor] / map surface (see point 2) - the **last** registered [LongClickInteraction] will be triggered **first**.
 */
@MapboxExperimental
class LongClickInteraction<T : BaseInteractiveFeature<*, *>> : MapInteraction {

  private constructor(
    featureset: FeaturesetDescriptor,
    filter: Value? = null,
    onLongClick: (T, InteractionContext) -> Boolean,
    interactiveFeatureBuilder: (QueriedFeature) -> T
  ) {
    coreInteraction = Interaction(
      /* featuresetDescriptor */ featureset,
      /* filter */ filter,
      InteractionType.LONG_CLICK,
      object : InteractionHandler {
        override fun handleBegin(feature: QueriedFeature?, context: InteractionContext): Boolean =
          feature?.let {
            onLongClick(interactiveFeatureBuilder(it), context)
          } ?: false

        override fun handleChange(context: InteractionContext) {
          // not needed
        }

        override fun handleEnd(context: InteractionContext) {
          // not needed
        }
      }
    )
  }

  private constructor(onLongClick: (InteractionContext) -> Boolean) {
    coreInteraction = Interaction(
      null,
      null,
      InteractionType.LONG_CLICK,
      object : InteractionHandler {
        override fun handleBegin(feature: QueriedFeature?, context: InteractionContext): Boolean {
          return onLongClick(context)
        }

        override fun handleChange(context: InteractionContext) {
          // not needed
        }

        override fun handleEnd(context: InteractionContext) {
          // not needed
        }
      }
    )
  }

  /**
   * Static variables and methods.
   */
  companion object {

    /**
     * Create the [LongClickInteraction] for given featureset defined with [featuresetId] and optional [importId].
     *
     * When several [LongClickInteraction]s are registered for the same [featuresetId] and [importId] - the callbacks will be triggered from last to first.
     *
     * @param featuresetId mandatory featureset id.
     * @param importId optional style import id.
     * @param filter optional filter. Defaults to NULL.
     * @param onLongClick callback triggered when featureset is clicked.
     */
    @JvmOverloads
    @JvmStatic
    @MapboxExperimental
    fun featureset(
      featuresetId: String,
      importId: String? = null,
      filter: Value? = null,
      onLongClick: (InteractiveFeature<FeaturesetHolder.Featureset>, InteractionContext) -> Boolean
    ): MapInteraction {
      return LongClickInteraction(
        FeaturesetDescriptor(featuresetId, importId, null),
        filter,
        onLongClick
      ) {
        InteractiveFeature(
          featuresetHolder = FeaturesetHolder.Featureset(featuresetId, importId),
          feature = it.feature,
          featureNamespace = it.featuresetFeatureId?.featureNamespace,
          state = it.state
        )
      }
    }

    /**
     * Create the [LongClickInteraction] for given [layerId].
     *
     * When several [LongClickInteraction]s are registered for the same [layerId] - the callbacks will be triggered from last to first.
     *
     * @param layerId mandatory layer id.
     * @param filter optional filter. Defaults to NULL.
     * @param onLongClick callback triggered when layer is clicked.
     */
    @JvmOverloads
    @JvmStatic
    @MapboxExperimental
    fun layer(
      layerId: String,
      filter: Value? = null,
      onLongClick: (InteractiveFeature<FeaturesetHolder.Layer>, InteractionContext) -> Boolean
    ): MapInteraction {
      return LongClickInteraction(
        FeaturesetDescriptor(null, null, layerId),
        filter,
        onLongClick
      ) {
        InteractiveFeature(
          featuresetHolder = FeaturesetHolder.Layer(layerId),
          feature = it.feature,
          featureNamespace = it.featuresetFeatureId?.featureNamespace,
          state = it.state
        )
      }
    }

    /**
     * Create the [LongClickInteraction] for the map surface itself.
     *
     * When several map [LongClickInteraction]s are registered - the callbacks will be triggered from last to first.
     *
     * @param onLongClick callback triggered when map surface is clicked.
     */
    @JvmStatic
    @JvmName("map")
    @MapboxExperimental
    operator fun invoke(onLongClick: (InteractionContext) -> Boolean): LongClickInteraction<Nothing> =
      LongClickInteraction(onLongClick)
  }
}