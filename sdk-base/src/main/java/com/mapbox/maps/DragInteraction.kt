package com.mapbox.maps

import androidx.annotation.RestrictTo
import com.mapbox.bindgen.Value
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.FeaturesetHolder
import com.mapbox.maps.interactions.InteractiveFeature

/**
 * For internal usage.
 */
@OptIn(MapboxExperimental::class)
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class DragInteraction<T : InteractiveFeature<*>> : MapInteraction {

  private constructor(
    featureset: FeaturesetDescriptor,
    filter: Value? = null,
    onDragBegin: (T, InteractionContext) -> Boolean,
    onDrag: (InteractionContext) -> Unit,
    onDragEnd: (InteractionContext) -> Unit,
    interactiveFeatureBuilder: (QueriedFeature) -> T
  ) {
    coreInteraction = Interaction(
      /* featuresetDescriptor */ featureset,
      /* filter */ filter,
      InteractionType.DRAG,
      object : InteractionHandler {
        override fun handleBegin(feature: QueriedFeature?, context: InteractionContext): Boolean =
          feature?.let {
            onDragBegin(interactiveFeatureBuilder(it), context)
          } ?: false

        override fun handleChange(context: InteractionContext) {
          onDrag(context)
        }

        override fun handleEnd(context: InteractionContext) {
          onDragEnd(context)
        }
      }
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
      }
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
      featuresetId: String,
      importId: String? = null,
      filter: Value? = null,
      onDragBegin: (InteractiveFeature<FeatureState>, InteractionContext) -> Boolean,
      onDrag: (InteractionContext) -> Unit,
      onDragEnd: (InteractionContext) -> Unit,
    ): MapInteraction {
      return DragInteraction(
        FeaturesetDescriptor(featuresetId, importId, null),
        filter,
        onDragBegin,
        onDrag,
        onDragEnd
      ) {
        InteractiveFeature(
          featuresetHolder = FeaturesetHolder.Featureset(featuresetId, importId),
          feature = it.feature,
          featureNamespace = it.featuresetFeatureId?.featureNamespace,
          state = FeatureState(it.state)
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
      layerId: String,
      filter: Value? = null,
      onDragBegin: (InteractiveFeature<FeatureState>, InteractionContext) -> Boolean,
      onDrag: (InteractionContext) -> Unit,
      onDragEnd: (InteractionContext) -> Unit,
    ): MapInteraction {
      return DragInteraction(
        FeaturesetDescriptor(null, null, layerId),
        filter,
        onDragBegin,
        onDrag,
        onDragEnd
      ) {
        InteractiveFeature(
          featuresetHolder = FeaturesetHolder.Layer(layerId),
          feature = it.feature,
          featureNamespace = it.featuresetFeatureId?.featureNamespace,
          state = FeatureState(it.state)
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