package com.mapbox.maps

import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.maps.ClickInteraction.Companion.featureset
import com.mapbox.maps.ClickInteraction.Companion.invoke
import com.mapbox.maps.ClickInteraction.Companion.layer
import com.mapbox.maps.LongClickInteraction.Companion.featureset
import com.mapbox.maps.LongClickInteraction.Companion.invoke
import com.mapbox.maps.LongClickInteraction.Companion.layer
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.FeaturesetFeature
import com.mapbox.maps.interactions.TypedFeaturesetDescriptor
import com.mapbox.maps.interactions.standard.generated.standardPoi

/**
 * Defines the long click interaction.
 *
 * Refer to static factory methods to create the [LongClickInteraction]:
 *  1. Mapbox Standard Style interactive features in defined as `LongClickInteraction.standard<featureset_name>` (e.g. [LongClickInteraction.Companion.standardPoi]).
 *  2. [featureset] to register the long click interaction for given `featuresetId` and optional `importId` (when defining the interaction for an imported style).
 *  3. [layer] to register the long click interaction for given `layerId`.
 *  4. [invoke] (meaning empty constructor in Kotlin and static method `map` in Java) to register the long click interaction for the map surface itself.
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
class LongClickInteraction<T : FeaturesetFeature<*>> : MapInteraction {

  internal constructor(
    featureset: FeaturesetDescriptor,
    filter: Value? = null,
    radius: Double? = null,
    onLongClick: (T, InteractionContext) -> Boolean,
    featuresetFeatureBuilder: (Feature, FeaturesetFeatureId?, Value) -> T
  ) {
    coreInteraction = Interaction(
      /* featuresetDescriptor */ featureset,
      /* filter */ filter,
      InteractionType.LONG_CLICK,
      object : InteractionHandler {
        override fun handleBegin(feature: QueriedFeature?, context: InteractionContext): Boolean {
          // it is fine to have the QueriedFeature without an ID but we require the geometry
          if (feature?.feature?.geometry() != null) {
            return onLongClick(
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
      },
      null
    )
  }

  /**
   * Static variables and methods.
   */
  companion object {

    /**
     * Create the [LongClickInteraction] for given featureset defined with [id] and optional [importId].
     *
     * When several [LongClickInteraction]s are registered for the same [id] and [importId] - the callbacks will be triggered from last to first.
     *
     * @param id mandatory featureset id.
     * @param importId optional style import id.
     * @param filter optional filter. Defaults to NULL.
     * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
     * @param onLongClick callback triggered when featureset is clicked.
     */
    @JvmOverloads
    @JvmStatic
    @MapboxExperimental
    fun featureset(
      id: String,
      importId: String? = null,
      filter: Value? = null,
      radius: Double? = null,
      onLongClick: (FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean
    ): MapInteraction {
      return LongClickInteraction(
        FeaturesetDescriptor(id, importId, null),
        filter,
        radius,
        onLongClick
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
     * Create the [LongClickInteraction] for given [id].
     *
     * When several [LongClickInteraction]s are registered for the same [id] - the callbacks will be triggered from last to first.
     *
     * @param id mandatory layer id.
     * @param filter optional filter. Defaults to NULL.
     * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
     * @param onLongClick callback triggered when layer is clicked.
     */
    @JvmOverloads
    @JvmStatic
    @MapboxExperimental
    fun layer(
      id: String,
      filter: Value? = null,
      radius: Double? = null,
      onLongClick: (FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean
    ): MapInteraction {
      return LongClickInteraction(
        FeaturesetDescriptor(null, null, id),
        filter,
        radius,
        onLongClick
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