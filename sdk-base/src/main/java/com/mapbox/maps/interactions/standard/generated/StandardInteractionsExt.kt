// This file is generated.
@file:JvmName("StandardInteractions")

package com.mapbox.maps.interactions.standard.generated

import com.mapbox.bindgen.Value
import com.mapbox.maps.ClickInteraction
import com.mapbox.maps.FeaturesetDescriptor
import com.mapbox.maps.InteractionContext
import com.mapbox.maps.LongClickInteraction
import com.mapbox.maps.MapInteraction
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.interactions.TypedFeaturesetDescriptor

/**
 * Create the [ClickInteraction] for Mapbox Standard Style `poi` featureset and an optional [importId].
 *
 * When several [ClickInteraction]s are registered for the same [importId] - the callbacks will be triggered from last to first.
 *
 * @param importId optional style import id. Defaults to NULL.
 * @param filter optional filter. Defaults to NULL.
 * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
 * @param onClick callback triggered when the `poi` featureset is clicked.
 */
@JvmOverloads
@MapboxExperimental
fun ClickInteraction.Companion.standardPoi(
  importId: String? = null,
  filter: Value? = null,
  radius: Double? = null,
  onClick: (StandardPoiFeature, InteractionContext) -> Boolean
): MapInteraction {
  return ClickInteraction(
    FeaturesetDescriptor(
      /* featuresetId */ StandardPoi.FEATURESET_ID,
      /* importId */ importId ?: TypedFeaturesetDescriptor.DEFAULT_IMPORT_ID,
      /* layerId */null
    ),
    filter,
    radius,
    onClick
  ) { feature, featuresetFeatureId, state ->
    StandardPoiFeature(
      id = featuresetFeatureId,
      importId = importId,
      originalFeature = feature,
      state = StandardPoiState(state)
    )
  }
}

/**
 * Create the [LongClickInteraction] for Mapbox Standard Style `poi` featureset and an optional [importId].
 *
 * When several [LongClickInteraction]s are registered for the same [importId] - the callbacks will be triggered from last to first.
 *
 * @param importId optional style import id. Defaults to NULL.
 * @param filter optional filter. Defaults to NULL.
 * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
 * @param onLongClick callback triggered when the `poi` featureset is long clicked.
 */
@JvmOverloads
@MapboxExperimental
fun LongClickInteraction.Companion.standardPoi(
  importId: String? = null,
  filter: Value? = null,
  radius: Double? = null,
  onLongClick: (StandardPoiFeature, InteractionContext) -> Boolean
): MapInteraction {
  return LongClickInteraction(
    FeaturesetDescriptor(
      /* featuresetId */ StandardPoi.FEATURESET_ID,
      /* importId */ importId ?: TypedFeaturesetDescriptor.DEFAULT_IMPORT_ID,
      /* layerId */null
    ),
    filter,
    radius,
    onLongClick
  ) { feature, featuresetFeatureId, state ->
    StandardPoiFeature(
      id = featuresetFeatureId,
      importId = importId,
      originalFeature = feature,
      state = StandardPoiState(state)
    )
  }
}
/**
 * Create the [ClickInteraction] for Mapbox Standard Style `place-labels` featureset and an optional [importId].
 *
 * When several [ClickInteraction]s are registered for the same [importId] - the callbacks will be triggered from last to first.
 *
 * @param importId optional style import id. Defaults to NULL.
 * @param filter optional filter. Defaults to NULL.
 * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
 * @param onClick callback triggered when the `place-labels` featureset is clicked.
 */
@JvmOverloads
@MapboxExperimental
fun ClickInteraction.Companion.standardPlaceLabels(
  importId: String? = null,
  filter: Value? = null,
  radius: Double? = null,
  onClick: (StandardPlaceLabelsFeature, InteractionContext) -> Boolean
): MapInteraction {
  return ClickInteraction(
    FeaturesetDescriptor(
      /* featuresetId */ StandardPlaceLabels.FEATURESET_ID,
      /* importId */ importId ?: TypedFeaturesetDescriptor.DEFAULT_IMPORT_ID,
      /* layerId */null
    ),
    filter,
    radius,
    onClick
  ) { feature, featuresetFeatureId, state ->
    StandardPlaceLabelsFeature(
      id = featuresetFeatureId,
      importId = importId,
      originalFeature = feature,
      state = StandardPlaceLabelsState(state)
    )
  }
}

/**
 * Create the [LongClickInteraction] for Mapbox Standard Style `place-labels` featureset and an optional [importId].
 *
 * When several [LongClickInteraction]s are registered for the same [importId] - the callbacks will be triggered from last to first.
 *
 * @param importId optional style import id. Defaults to NULL.
 * @param filter optional filter. Defaults to NULL.
 * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
 * @param onLongClick callback triggered when the `place-labels` featureset is long clicked.
 */
@JvmOverloads
@MapboxExperimental
fun LongClickInteraction.Companion.standardPlaceLabels(
  importId: String? = null,
  filter: Value? = null,
  radius: Double? = null,
  onLongClick: (StandardPlaceLabelsFeature, InteractionContext) -> Boolean
): MapInteraction {
  return LongClickInteraction(
    FeaturesetDescriptor(
      /* featuresetId */ StandardPlaceLabels.FEATURESET_ID,
      /* importId */ importId ?: TypedFeaturesetDescriptor.DEFAULT_IMPORT_ID,
      /* layerId */null
    ),
    filter,
    radius,
    onLongClick
  ) { feature, featuresetFeatureId, state ->
    StandardPlaceLabelsFeature(
      id = featuresetFeatureId,
      importId = importId,
      originalFeature = feature,
      state = StandardPlaceLabelsState(state)
    )
  }
}
/**
 * Create the [ClickInteraction] for Mapbox Standard Style `buildings` featureset and an optional [importId].
 *
 * When several [ClickInteraction]s are registered for the same [importId] - the callbacks will be triggered from last to first.
 *
 * @param importId optional style import id. Defaults to NULL.
 * @param filter optional filter. Defaults to NULL.
 * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
 * @param onClick callback triggered when the `buildings` featureset is clicked.
 */
@JvmOverloads
@MapboxExperimental
fun ClickInteraction.Companion.standardBuildings(
  importId: String? = null,
  filter: Value? = null,
  radius: Double? = null,
  onClick: (StandardBuildingsFeature, InteractionContext) -> Boolean
): MapInteraction {
  return ClickInteraction(
    FeaturesetDescriptor(
      /* featuresetId */ StandardBuildings.FEATURESET_ID,
      /* importId */ importId ?: TypedFeaturesetDescriptor.DEFAULT_IMPORT_ID,
      /* layerId */null
    ),
    filter,
    radius,
    onClick
  ) { feature, featuresetFeatureId, state ->
    StandardBuildingsFeature(
      id = featuresetFeatureId,
      importId = importId,
      originalFeature = feature,
      state = StandardBuildingsState(state)
    )
  }
}

/**
 * Create the [LongClickInteraction] for Mapbox Standard Style `buildings` featureset and an optional [importId].
 *
 * When several [LongClickInteraction]s are registered for the same [importId] - the callbacks will be triggered from last to first.
 *
 * @param importId optional style import id. Defaults to NULL.
 * @param filter optional filter. Defaults to NULL.
 * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
 * @param onLongClick callback triggered when the `buildings` featureset is long clicked.
 */
@JvmOverloads
@MapboxExperimental
fun LongClickInteraction.Companion.standardBuildings(
  importId: String? = null,
  filter: Value? = null,
  radius: Double? = null,
  onLongClick: (StandardBuildingsFeature, InteractionContext) -> Boolean
): MapInteraction {
  return LongClickInteraction(
    FeaturesetDescriptor(
      /* featuresetId */ StandardBuildings.FEATURESET_ID,
      /* importId */ importId ?: TypedFeaturesetDescriptor.DEFAULT_IMPORT_ID,
      /* layerId */null
    ),
    filter,
    radius,
    onLongClick
  ) { feature, featuresetFeatureId, state ->
    StandardBuildingsFeature(
      id = featuresetFeatureId,
      importId = importId,
      originalFeature = feature,
      state = StandardBuildingsState(state)
    )
  }
}
// End of generated file.