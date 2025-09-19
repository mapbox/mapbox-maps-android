// This file is generated.

package com.mapbox.maps.extension.compose.style.standard.generated

import androidx.compose.runtime.Composable
import com.mapbox.maps.ClickInteraction
import com.mapbox.maps.InteractionContext
import com.mapbox.maps.LongClickInteraction
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.style.interactions.BasicStyleInteractions
import com.mapbox.maps.extension.compose.style.interactions.generated.FeaturesetFeatureScope
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.FeaturesetFeature
import com.mapbox.maps.interactions.standard.generated.StandardBuildingsFeature
import com.mapbox.maps.interactions.standard.generated.StandardLandmarkIconsFeature
import com.mapbox.maps.interactions.standard.generated.StandardPlaceLabelsFeature
import com.mapbox.maps.interactions.standard.generated.StandardPoiFeature
import com.mapbox.maps.interactions.standard.generated.standardBuildings
import com.mapbox.maps.interactions.standard.generated.standardLandmarkIcons
import com.mapbox.maps.interactions.standard.generated.standardPlaceLabels
import com.mapbox.maps.interactions.standard.generated.standardPoi

/**
 * [StandardStyleInteractionsState] manages the map interactions defined for Mapbox Standard Style.
 */
public class StandardStyleInteractionsState : BasicStyleInteractions() {

  @Composable
  internal fun BindTo(mapboxMap: MapboxMap) {
    BindToMap(map = mapboxMap)
  }

  /**
   * Add the [ClickInteraction] for featureset 'poi' from Mapbox Standard Style or Mapbox Standard Satellite Style.
   *
   * When several [ClickInteraction]s are registered for the same [importId] - the callbacks will be triggered from last to first.
   *
   * @param importId optional Standard style import id.
   * @param filter optional filter. Defaults to NULL.
   * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
   * @param onClick callback triggered when the Standard featureset is clicked.
   */
  public fun onPoiClicked(
    importId: String? = null,
    filter: Expression? = null,
    radius: Double? = null,
    onClick: FeaturesetFeatureScope.(StandardPoiFeature, InteractionContext) -> Boolean
  ): StandardStyleInteractionsState = apply {
    entries.add { import ->
      ClickInteraction.standardPoi(
        importId = importId ?: import,
        filter = filter,
        radius = radius,
        onClick = { feature, context ->
          featuresetFeatureScope?.onClick(feature, context) ?: false
        }
      )
    }
  }

  /**
   * Add the [LongClickInteraction] for featureset 'poi' from Mapbox Standard Style or Mapbox Standard Satellite Style.
   *
   * When several [LongClickInteraction]s are registered for the same [importId] - the callbacks will be triggered from last to first.
   *
   * @param importId optional Standard style import id.
   * @param filter optional filter. Defaults to NULL.
   * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
   * @param onLongClick callback triggered when the Standard featureset is long clicked.
   */
  public fun onPoiLongClicked(
    importId: String? = null,
    filter: Expression? = null,
    radius: Double? = null,
    onLongClick: FeaturesetFeatureScope.(StandardPoiFeature, InteractionContext) -> Boolean
  ): StandardStyleInteractionsState = apply {
    entries.add { import ->
      LongClickInteraction.standardPoi(
        importId = importId ?: import,
        filter = filter,
        radius = radius,
        onLongClick = { feature, context ->
          featuresetFeatureScope?.onLongClick(feature, context) ?: false
        }
      )
    }
  }

  /**
   * Add the [ClickInteraction] for featureset 'place-labels' from Mapbox Standard Style or Mapbox Standard Satellite Style.
   *
   * When several [ClickInteraction]s are registered for the same [importId] - the callbacks will be triggered from last to first.
   *
   * @param importId optional Standard style import id.
   * @param filter optional filter. Defaults to NULL.
   * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
   * @param onClick callback triggered when the Standard featureset is clicked.
   */
  public fun onPlaceLabelsClicked(
    importId: String? = null,
    filter: Expression? = null,
    radius: Double? = null,
    onClick: FeaturesetFeatureScope.(StandardPlaceLabelsFeature, InteractionContext) -> Boolean
  ): StandardStyleInteractionsState = apply {
    entries.add { import ->
      ClickInteraction.standardPlaceLabels(
        importId = importId ?: import,
        filter = filter,
        radius = radius,
        onClick = { feature, context ->
          featuresetFeatureScope?.onClick(feature, context) ?: false
        }
      )
    }
  }

  /**
   * Add the [LongClickInteraction] for featureset 'place-labels' from Mapbox Standard Style or Mapbox Standard Satellite Style.
   *
   * When several [LongClickInteraction]s are registered for the same [importId] - the callbacks will be triggered from last to first.
   *
   * @param importId optional Standard style import id.
   * @param filter optional filter. Defaults to NULL.
   * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
   * @param onLongClick callback triggered when the Standard featureset is long clicked.
   */
  public fun onPlaceLabelsLongClicked(
    importId: String? = null,
    filter: Expression? = null,
    radius: Double? = null,
    onLongClick: FeaturesetFeatureScope.(StandardPlaceLabelsFeature, InteractionContext) -> Boolean
  ): StandardStyleInteractionsState = apply {
    entries.add { import ->
      LongClickInteraction.standardPlaceLabels(
        importId = importId ?: import,
        filter = filter,
        radius = radius,
        onLongClick = { feature, context ->
          featuresetFeatureScope?.onLongClick(feature, context) ?: false
        }
      )
    }
  }

  /**
   * Add the [ClickInteraction] for featureset 'buildings' from Mapbox Standard Style or Mapbox Standard Satellite Style.
   *
   * When several [ClickInteraction]s are registered for the same [importId] - the callbacks will be triggered from last to first.
   *
   * @param importId optional Standard style import id.
   * @param filter optional filter. Defaults to NULL.
   * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
   * @param onClick callback triggered when the Standard featureset is clicked.
   */
  public fun onBuildingsClicked(
    importId: String? = null,
    filter: Expression? = null,
    radius: Double? = null,
    onClick: FeaturesetFeatureScope.(StandardBuildingsFeature, InteractionContext) -> Boolean
  ): StandardStyleInteractionsState = apply {
    entries.add { import ->
      ClickInteraction.standardBuildings(
        importId = importId ?: import,
        filter = filter,
        radius = radius,
        onClick = { feature, context ->
          featuresetFeatureScope?.onClick(feature, context) ?: false
        }
      )
    }
  }

  /**
   * Add the [LongClickInteraction] for featureset 'buildings' from Mapbox Standard Style or Mapbox Standard Satellite Style.
   *
   * When several [LongClickInteraction]s are registered for the same [importId] - the callbacks will be triggered from last to first.
   *
   * @param importId optional Standard style import id.
   * @param filter optional filter. Defaults to NULL.
   * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
   * @param onLongClick callback triggered when the Standard featureset is long clicked.
   */
  public fun onBuildingsLongClicked(
    importId: String? = null,
    filter: Expression? = null,
    radius: Double? = null,
    onLongClick: FeaturesetFeatureScope.(StandardBuildingsFeature, InteractionContext) -> Boolean
  ): StandardStyleInteractionsState = apply {
    entries.add { import ->
      LongClickInteraction.standardBuildings(
        importId = importId ?: import,
        filter = filter,
        radius = radius,
        onLongClick = { feature, context ->
          featuresetFeatureScope?.onLongClick(feature, context) ?: false
        }
      )
    }
  }

  /**
   * Add the [ClickInteraction] for featureset 'landmark-icons' from Mapbox Standard Style or Mapbox Standard Satellite Style.
   *
   * When several [ClickInteraction]s are registered for the same [importId] - the callbacks will be triggered from last to first.
   *
   * @param importId optional Standard style import id.
   * @param filter optional filter. Defaults to NULL.
   * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
   * @param onClick callback triggered when the Standard featureset is clicked.
   */
  public fun onLandmarkIconsClicked(
    importId: String? = null,
    filter: Expression? = null,
    radius: Double? = null,
    onClick: FeaturesetFeatureScope.(StandardLandmarkIconsFeature, InteractionContext) -> Boolean
  ): StandardStyleInteractionsState = apply {
    entries.add { import ->
      ClickInteraction.standardLandmarkIcons(
        importId = importId ?: import,
        filter = filter,
        radius = radius,
        onClick = { feature, context ->
          featuresetFeatureScope?.onClick(feature, context) ?: false
        }
      )
    }
  }

  /**
   * Add the [LongClickInteraction] for featureset 'landmark-icons' from Mapbox Standard Style or Mapbox Standard Satellite Style.
   *
   * When several [LongClickInteraction]s are registered for the same [importId] - the callbacks will be triggered from last to first.
   *
   * @param importId optional Standard style import id.
   * @param filter optional filter. Defaults to NULL.
   * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
   * @param onLongClick callback triggered when the Standard featureset is long clicked.
   */
  public fun onLandmarkIconsLongClicked(
    importId: String? = null,
    filter: Expression? = null,
    radius: Double? = null,
    onLongClick: FeaturesetFeatureScope.(StandardLandmarkIconsFeature, InteractionContext) -> Boolean
  ): StandardStyleInteractionsState = apply {
    entries.add { import ->
      LongClickInteraction.standardLandmarkIcons(
        importId = importId ?: import,
        filter = filter,
        radius = radius,
        onLongClick = { feature, context ->
          featuresetFeatureScope?.onLongClick(feature, context) ?: false
        }
      )
    }
  }

  /**
  * Add the [ClickInteraction] for given featureset defined with [id] and optional [importId].
  *
  * When several [ClickInteraction]s are registered for the same [id] and [importId] - the callbacks will be triggered from last to first.
  *
  * @param id mandatory featureset id.
  * @param importId optional style import id.
  * @param filter optional filter. Defaults to NULL.
  * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
  * @param onClick callback triggered when featureset is clicked.
  */
  public fun onFeaturesetClicked(
  id: String,
  importId: String? = null,
  filter: Expression? = null,
  radius: Double? = null,
  onClick: FeaturesetFeatureScope.(FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean
  ): StandardStyleInteractionsState = apply {
    clickInteractionFeatureset(
      featuresetId = id,
      importId = importId,
      filter = filter,
      radius = radius,
      onClick = onClick
    )
  }

  /**
  * Add the [ClickInteraction] for given [id].
  *
  * When several [ClickInteraction]s are registered for the same [id] - the callbacks will be triggered from last to first.
  *
  * @param id mandatory layer id.
  * @param filter optional filter. Defaults to NULL.
  * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
  * @param onClick callback triggered when layer is clicked.
  */
  public fun onLayerClicked(
  id: String,
  filter: Expression? = null,
  radius: Double? = null,
  onClick: FeaturesetFeatureScope.(FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean
  ): StandardStyleInteractionsState = apply {
    clickInteractionLayer(layerId = id, filter = filter, radius = radius, onClick = onClick)
  }

  /**
  * Add the [LongClickInteraction] for given featureset defined with [id] and optional [importId].
  *
  * When several [LongClickInteraction]s are registered for the same [id] and [importId] - the callbacks will be triggered from last to first.
  *
  * @param id mandatory featureset id.
  * @param importId optional style import id.
  * @param filter optional filter. Defaults to NULL.
  * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
  * @param onLongClick callback triggered when featureset is clicked.
  */
  public fun onFeaturesetLongClicked(
  id: String,
  importId: String? = null,
  filter: Expression? = null,
  radius: Double? = null,
  onLongClick: FeaturesetFeatureScope.(FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean
  ): StandardStyleInteractionsState = apply {
    longClickInteractionFeatureset(
      featuresetId = id,
      importId = importId,
      filter = filter,
      radius = radius,
      onLongClick = onLongClick
    )
  }

  /**
  * Add the [LongClickInteraction] for given [id].
  *
  * When several [LongClickInteraction]s are registered for the same [id] - the callbacks will be triggered from last to first.
  *
  * @param id mandatory layer id.
  * @param filter optional filter. Defaults to NULL.
  * @param radius of an extra area around touch in screen pixels. Defaults to NULL meaning 0-radius pixels area.
  * @param onLongClick callback triggered when layer is clicked.
  */
  public fun onLayerLongClicked(
  id: String,
  filter: Expression? = null,
  radius: Double? = null,
  onLongClick: FeaturesetFeatureScope.(FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean
  ): StandardStyleInteractionsState = apply {
    longClickInteractionLayer(layerId = id, filter = filter, radius = radius, onLongClick = onLongClick)
  }

  /**
  * Add the [ClickInteraction] for the map surface itself.
  *
  * When several map [ClickInteraction]s are registered - the callbacks will be triggered from last to first.
  *
  * @param onClick callback triggered when map surface is clicked.
  */
  public fun onMapClicked(
  onClick: FeaturesetFeatureScope.(InteractionContext) -> Boolean
  ): StandardStyleInteractionsState = apply {
    clickInteractionMap(onClick)
  }

  /**
  * Add the [LongClickInteraction] for the map surface itself.
  *
  * When several map [LongClickInteraction]s are registered - the callbacks will be triggered from last to first.
  *
  * @param onLongClick callback triggered when map surface is clicked.
  */
  public fun onMapLongClicked(
  onLongClick: FeaturesetFeatureScope.(InteractionContext) -> Boolean
  ): StandardStyleInteractionsState = apply {
    longClickInteractionMap(onLongClick)
  }
}