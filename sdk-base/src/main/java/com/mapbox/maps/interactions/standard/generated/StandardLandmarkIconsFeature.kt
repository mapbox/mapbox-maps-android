// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import com.mapbox.geojson.Feature
import com.mapbox.maps.FeaturesetFeatureId
import com.mapbox.maps.interactions.FeaturesetFeature

/**
 * Icons for landmark buildings
 *
 * Typed class of [FeaturesetFeature] representing StandardLandmarkIcons coming from Mapbox Standard Style.
 * Allows easy access to [Feature] properties.
 */
class StandardLandmarkIconsFeature(
  id: FeaturesetFeatureId?,
  importId: String?,
  originalFeature: Feature,
  state: StandardLandmarkIconsState,
) : FeaturesetFeature<StandardLandmarkIconsState>(
  id = id,
  descriptor = StandardLandmarkIcons(importId),
  originalFeature = originalFeature,
  state = state
) {

  /**
   * Unique landmark ID.
   */
  val landmarkId get(): String? = originalFeature.getStringProperty("id")

  /**
   * Name of the Landmark in local language.
   */
  val name get(): String? = originalFeature.getStringProperty("name")

  /**
   * Name of the Landmark in English.
   */
  val nameEn get(): String? = originalFeature.getStringProperty("name_en")

  /**
   * Short name of the Landmark in local language.
   */
  val shortName get(): String? = originalFeature.getStringProperty("short_name")

  /**
   * Short name of the Landmark in English.
   */
  val shortNameEn get(): String? = originalFeature.getStringProperty("short_name_en")

  /**
   * Landmark type or building use.
   */
  val type get(): String? = originalFeature.getStringProperty("type")
}