// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import com.mapbox.geojson.Feature
import com.mapbox.maps.FeaturesetFeatureId
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.interactions.FeaturesetFeature

/**
 * Featureset describing the buildings.
 *
 * Typed class of [FeaturesetFeature] representing StandardBuildings coming from Mapbox Standard Style.
 * Allows easy access to [Feature] properties.
 */
@MapboxExperimental
class StandardBuildingsFeature(
  id: FeaturesetFeatureId?,
  importId: String?,
  originalFeature: Feature,
  state: StandardBuildingsState,
) : FeaturesetFeature<StandardBuildingsState>(
  id = id,
  descriptor = StandardBuildings(importId),
  originalFeature = originalFeature,
  state = state
) {

  /**
   * A high-level building group like building-2d, building-3d, etc.
   */
  val group get(): String? = originalFeature.getStringProperty("group")
}