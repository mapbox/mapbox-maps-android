// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import com.mapbox.geojson.Feature
import com.mapbox.maps.FeaturesetFeatureId
import com.mapbox.maps.interactions.FeaturesetFeature

/**
 * Featureset describing the buildings.
 *
 * Typed class of [FeaturesetFeature] representing StandardBuildings coming from Mapbox Standard Style.
 * Allows easy access to [Feature] properties.
 */
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

  /**
   * The height in meters of a building or building part (rounded to the nearest integer).
   */
  val height get(): Double? = originalFeature.getNumberProperty("height")?.toDouble()

  /**
   * The height in meters from the ground to the bottom of a building part, for cases where the bottom of the part is not on the ground.
   */
  val minHeight get(): Double? = originalFeature.getNumberProperty("min_height")?.toDouble()
}