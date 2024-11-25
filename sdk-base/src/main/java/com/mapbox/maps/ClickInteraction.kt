package com.mapbox.maps

import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.maps.ClickInteraction.Companion.featureset
import com.mapbox.maps.ClickInteraction.Companion.invoke
import com.mapbox.maps.ClickInteraction.Companion.layer
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.FeaturesetFeature
import com.mapbox.maps.interactions.TypedFeaturesetDescriptor
import com.mapbox.maps.interactions.standard.generated.standardPoi

/**
 * Defines the click interaction.
 *
 * Refer to static factory methods to create the [ClickInteraction]:
 *  1. Mapbox Standard Style interactive features in defined as `ClickInteraction.standard<featureset_name>` (e.g. [ClickInteraction.Companion.standardPoi]).
 *  2. [featureset] to register the click interaction for given `featuresetId` and optional `importId` (when defining the interaction for an imported style).
 *  3. [layer] to register the click interaction for given `layerId`.
 *  4. [invoke] (meaning empty constructor in Kotlin and static method `map` in Java) to register the click interaction for the map surface itself.
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
class ClickInteraction<T : FeaturesetFeature<*>> : MapInteraction {

  internal constructor(
    featureset: FeaturesetDescriptor,
    filter: Value? = null,
    radius: Double? = null,
    onClick: (T, InteractionContext) -> Boolean,
    featuresetFeatureBuilder: (Feature, FeaturesetFeatureId?, Value) -> T
  ) {
    coreInteraction = Interaction(
      /* featuresetDescriptor */ featureset,
      /* filter */ filter,
      InteractionType.CLICK,
      object : InteractionHandler {
        override fun handleBegin(feature: QueriedFeature?, context: InteractionContext): Boolean {
          // it is fine to have the QueriedFeature without an ID but we require the geometry
          if (feature?.feature?.geometry() != null) {
            return onClick(
              featuresetFeatureBuilder(
                feature.feature,
                feature.featuresetFeatureId,
                feature.state
              ),
              context
            )
          }
          return false
        }

        override fun handleChange(context: InteractionContext) {
          // not needed
        }

        override fun handleEnd(context: InteractionContext) {
          // not needed
        }
      },
      radius
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
      },
      null
    )
  }

  /**
   * Static variables and methods.
   */
  companion object {
    /**
     * Create the [ClickInteraction] for given featureset defined with [id] and optional [importId].
     *
     * When several [ClickInteraction]s are registered for the same [id] and [importId] - the callbacks will be triggered from last to first.
     *
     * @param id mandatory featureset id.
     * @param importId optional style import id.
     * @param filter optional filter. Defaults to NULL.
     * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
     * @param onClick callback triggered when featureset is clicked.
     */
    @JvmOverloads
    @JvmStatic
    @MapboxExperimental
    fun featureset(
      id: String,
      importId: String? = null,
      filter: Value? = null,
      radius: Double? = null,
      onClick: (FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean
    ): MapInteraction {
      return ClickInteraction(
        FeaturesetDescriptor(id, importId, null),
        filter,
        radius,
        onClick
      ) { feature, featuresetFeatureId, state ->
        FeaturesetFeature(
          descriptor = TypedFeaturesetDescriptor.Featureset(id, importId),
          originalFeature = feature,
          id = featuresetFeatureId,
          state = FeatureState(state)
        )
      }
    }

    /**
     * Create the [ClickInteraction] for given [id].
     *
     * When several [ClickInteraction]s are registered for the same [id] - the callbacks will be triggered from last to first.
     *
     * @param id mandatory layer id.
     * @param filter optional filter. Defaults to NULL.
     * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
     * @param onClick callback triggered when layer is clicked.
     */
    @JvmOverloads
    @JvmStatic
    @MapboxExperimental
    fun layer(
      id: String,
      filter: Value? = null,
      radius: Double? = null,
      onClick: (FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean
    ): MapInteraction {
      return ClickInteraction(
        FeaturesetDescriptor(null, null, id),
        filter,
        radius,
        onClick
      ) { feature, featuresetFeatureId, state ->
        FeaturesetFeature(
          descriptor = TypedFeaturesetDescriptor.Layer(id),
          originalFeature = feature,
          id = featuresetFeatureId,
          state = FeatureState(state)
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