package com.mapbox.maps

import androidx.annotation.RestrictTo
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.FeaturesetFeature
import com.mapbox.maps.interactions.TypedFeaturesetDescriptor

/**
 * For internal usage.
 */
@OptIn(MapboxExperimental::class)
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class DragInteraction<T : FeaturesetFeature<*>> : MapInteraction {

  private constructor(
    featureset: FeaturesetDescriptor,
    filter: Value? = null,
    radius: Double? = null,
    onDragBegin: (T, InteractionContext) -> Boolean,
    onDrag: (InteractionContext) -> Unit,
    onDragEnd: (InteractionContext) -> Unit,
    featuresetFeatureBuilder: (Feature, FeaturesetFeatureId?, Value) -> T
  ) {
    coreInteraction = Interaction(
      /* featuresetDescriptor */ featureset,
      /* filter */ filter,
      InteractionType.DRAG,
      object : InteractionHandler {
        override fun handleBegin(feature: QueriedFeature?, context: InteractionContext): Boolean {
          // it is fine to have the QueriedFeature without an ID but we require the geometry
          if (feature?.feature?.geometry() != null) {
            return onDragBegin(
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
          onDrag(context)
        }

        override fun handleEnd(context: InteractionContext) {
          onDragEnd(context)
        }
      },
      radius
    )
  }

  private constructor(
    onDragBegin: (InteractionContext) -> Boolean,
    onDrag: (InteractionContext) -> Unit,
    onDragEnd: (InteractionContext) -> Unit,
  ) {
    coreInteraction = Interaction(
      null,
      null,
      InteractionType.DRAG,
      object : InteractionHandler {
        override fun handleBegin(feature: QueriedFeature?, context: InteractionContext): Boolean {
          return onDragBegin(context)
        }

        override fun handleChange(context: InteractionContext) {
          onDrag(context)
        }

        override fun handleEnd(context: InteractionContext) {
          onDragEnd(context)
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
     * For internal usage.
     */
    @JvmOverloads
    @JvmStatic
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    fun featureset(
      id: String,
      importId: String? = null,
      filter: Value? = null,
      radius: Double? = null,
      onDragBegin: (FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean,
      onDrag: (InteractionContext) -> Unit,
      onDragEnd: (InteractionContext) -> Unit,
    ): MapInteraction {
      return DragInteraction(
        featureset = FeaturesetDescriptor(id, importId, null),
        filter = filter,
        radius = radius,
        onDragBegin,
        onDrag,
        onDragEnd
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
     * For internal usage.
     */
    @JvmOverloads
    @JvmStatic
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    fun layer(
      id: String,
      filter: Value? = null,
      onDragBegin: (FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean,
      onDrag: (InteractionContext) -> Unit,
      onDragEnd: (InteractionContext) -> Unit,
    ): MapInteraction {
      return DragInteraction(
        featureset = FeaturesetDescriptor(null, null, id),
        filter = filter,
        radius = null,
        onDragBegin,
        onDrag,
        onDragEnd
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
     * For internal usage.
     */
    @JvmSynthetic
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    operator fun invoke(
      onDragBegin: (InteractionContext) -> Boolean,
      onDrag: (InteractionContext) -> Unit,
      onDragEnd: (InteractionContext) -> Unit,
    ) = DragInteraction<Nothing>(
      onDragBegin,
      onDrag,
      onDragEnd
    )
  }
}