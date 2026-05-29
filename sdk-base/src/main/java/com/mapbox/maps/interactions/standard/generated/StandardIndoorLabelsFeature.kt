// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import com.mapbox.geojson.Feature
import com.mapbox.maps.FeaturesetFeatureId
import com.mapbox.maps.interactions.FeaturesetFeature

/**
 * Labels for indoor buildings.
 *
 * Typed class of [FeaturesetFeature] representing StandardIndoorLabels coming from Mapbox Standard Style.
 * Allows easy access to [Feature] properties.
 */
class StandardIndoorLabelsFeature(
  id: FeaturesetFeatureId?,
  importId: String?,
  originalFeature: Feature,
  state: StandardIndoorLabelsState,
) : FeaturesetFeature<StandardIndoorLabelsState>(
  id = id,
  descriptor = StandardIndoorLabels(importId),
  originalFeature = originalFeature,
  state = state
) {

  /**
   * Name of the point of interest.
   */
  val name get(): String? = originalFeature.getStringProperty("name")

  /**
   * A description of the room or area type.
   */
  val shapeType get(): String? = originalFeature.getStringProperty("shape_type")

  /**
   * A sub-category, like cafe, newsstand, etc.
   */
  val type get(): String? = originalFeature.getStringProperty("type")

  /**
   * A high-level category, like restaurant, retail, etc.
   */
  val `class` get(): String? = originalFeature.getStringProperty("class")
}