// This file is generated.

package com.mapbox.maps.extension.compose.style.standard.generated

import androidx.compose.runtime.Composable
import com.mapbox.maps.ClickInteraction
import com.mapbox.maps.InteractionContext
import com.mapbox.maps.LongClickInteraction
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.style.interactions.BasicStyleInteractions
import com.mapbox.maps.extension.compose.style.interactions.generated.FeaturesetFeatureScope
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.interactions.standard.generated.StandardBuildingsFeature
import com.mapbox.maps.interactions.standard.generated.StandardPlaceLabelsFeature
import com.mapbox.maps.interactions.standard.generated.StandardPoiFeature
import com.mapbox.maps.interactions.standard.generated.standardBuildings
import com.mapbox.maps.interactions.standard.generated.standardPlaceLabels
import com.mapbox.maps.interactions.standard.generated.standardPoi

/**
 * [StandardStyleInteractionsState] manages the map interactions defined for Mapbox Standard Style.
 */
@MapboxExperimental
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
  @MapboxExperimental
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
  @MapboxExperimental
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
  @MapboxExperimental
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
  @MapboxExperimental
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
  @MapboxExperimental
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
  @MapboxExperimental
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
}