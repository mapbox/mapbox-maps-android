// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import com.mapbox.geojson.Feature
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import com.mapbox.maps.FeaturesetFeatureId
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.interactions.FeaturesetFeature

/**
 * Points for labeling places including countries, states, cities, towns, and neighborhoods.
 *
 * Typed class of [FeaturesetFeature] representing StandardPlaceLabels coming from Mapbox Standard Style.
 * Allows easy access to [Feature] properties.
 */
@MapboxExperimental
class StandardPlaceLabelsFeature(
  id: FeaturesetFeatureId?,
  importId: String?,
  originalFeature: Feature,
  state: StandardPlaceLabelsState,
) : FeaturesetFeature<StandardPlaceLabelsState>(
  id = id,
  descriptor = StandardPlaceLabels(importId),
  originalFeature = originalFeature,
  state = state
) {

  /**
   * Name of the place label.
   */
  val name get(): String? = originalFeature.getStringProperty("name")

  /**
   * Provides a broad distinction between place types.
   */
  val `class` get(): String? = originalFeature.getStringProperty("class")

  /**
   * Mandatory feature [Geometry] represented as a [Point].
   */
  override val geometry: Point get() = super.geometry as Point
}