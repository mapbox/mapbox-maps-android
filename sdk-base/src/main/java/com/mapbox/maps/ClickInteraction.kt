package com.mapbox.maps

import com.mapbox.bindgen.Value
import com.mapbox.maps.ClickInteraction.Companion.featureset
import com.mapbox.maps.ClickInteraction.Companion.invoke
import com.mapbox.maps.interactions.BaseInteractiveFeature
import com.mapbox.maps.interactions.FeaturesetHolder
import com.mapbox.maps.interactions.InteractiveFeature

/**
 * Defines the click interaction.
 *
 * Refer to static factory methods to create the [ClickInteraction]:
 *  1. [featureset] to register the click interaction for given `featuresetId` and optional `importId` (when defining the interaction for an imported style).
 *  2. [layer] to register the click list interaction for give `layerId`.
 *  3. [invoke] (meaning empty constructor in Kotlin and static method `map` in Java) to register the click interaction for the map surface itself.
 *
 * Click interaction callback requires returning `true` | `false` where `true` means that the interaction
 * has been consumed and other registered click interactions will not have the callback invoked and `false` means
 * that the interactions has not been consumed and other registered click interactions (if any) will have the callback invoked.
 *
 * Click interactions callbacks are fired in determined order:
 *  1. For unique feature sets defined as [FeaturesetDescriptor] - the top most rendered feature will trigger the callback first.
 *  2. [ClickInteraction.invoke] (map surface click interaction outside of all the feature sets) will always get triggered last.
 *  3. When having several [ClickInteraction]s with the same [FeaturesetDescriptor] / map surface (see point 2) - the **last** registered [ClickInteraction] will be triggered **first**.
 */
@MapboxExperimental
class ClickInteraction<T : BaseInteractiveFeature<*, *>> : MapInteraction {

  private constructor(
    featureset: FeaturesetDescriptor,
    filter: Value? = null,
    onClick: (T, InteractionContext) -> Boolean,
    interactiveFeatureBuilder: (QueriedFeature) -> T
  ) {
    coreInteraction = Interaction(
      /* featuresetDescriptor */ featureset,
      /* filter */ filter,
      InteractionType.CLICK,
      object : InteractionHandler {
        override fun handleBegin(feature: QueriedFeature?, context: InteractionContext): Boolean =
          feature?.let {
            onClick(interactiveFeatureBuilder(it), context)
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

  private constructor(onClick: (InteractionContext) -> Boolean) {
    coreInteraction = Interaction(
      null,
      null,
      InteractionType.CLICK,
      object : InteractionHandler {
        override fun handleBegin(feature: QueriedFeature?, context: InteractionContext): Boolean {
          return onClick(context)
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
     * Create the [ClickInteraction] for given featureset defined with [featuresetId] and optional [importId].
     *
     * When several [ClickInteraction]s are registered for the same [featuresetId] and [importId] - the callbacks will be triggered from last to first.
     *
     * @param featuresetId mandatory featureset id.
     * @param importId optional style import id.
     * @param filter optional filter. Defaults to NULL.
     * @param onClick callback triggered when featureset is clicked.
     */
    @JvmOverloads
    @JvmStatic
    @MapboxExperimental
    fun featureset(
      featuresetId: String,
      importId: String? = null,
      filter: Value? = null,
      onClick: (InteractiveFeature<FeaturesetHolder.Featureset>, InteractionContext) -> Boolean
    ): MapInteraction {
      return ClickInteraction(
        FeaturesetDescriptor(featuresetId, importId, null),
        filter,
        onClick
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
     * Create the [ClickInteraction] for given [layerId].
     *
     * When several [ClickInteraction]s are registered for the same [layerId] - the callbacks will be triggered from last to first.
     *
     * @param layerId mandatory layer id.
     * @param filter optional filter. Defaults to NULL.
     * @param onClick callback triggered when layer is clicked.
     */
    @JvmOverloads
    @JvmStatic
    @MapboxExperimental
    fun layer(
      layerId: String,
      filter: Value? = null,
      onClick: (InteractiveFeature<FeaturesetHolder.Layer>, InteractionContext) -> Boolean
    ): MapInteraction {
      return ClickInteraction(
        FeaturesetDescriptor(null, null, layerId),
        filter,
        onClick
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
     * Create the [ClickInteraction] for the map surface itself.
     *
     * When several map [ClickInteraction]s are registered - the callbacks will be triggered from last to first.
     *
     * @param onClick callback triggered when map surface is clicked.
     */
    @JvmStatic
    @JvmName("map")
    @MapboxExperimental
    operator fun invoke(onClick: (InteractionContext) -> Boolean): ClickInteraction<Nothing> =
      ClickInteraction(onClick)
  }
}