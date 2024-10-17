// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import com.mapbox.geojson.Feature
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import com.mapbox.maps.FeaturesetFeatureId
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.interactions.FeaturesetFeature

/**
 * A point of interest.
 *
 * Typed class of [FeaturesetFeature] representing StandardPoi coming from Mapbox Standard Style.
 * Allows easy access to [Feature] properties.
 */
@MapboxExperimental
class StandardPoiFeature(
  id: FeaturesetFeatureId?,
  importId: String?,
  originalFeature: Feature,
  state: StandardPoiState,
) : FeaturesetFeature<StandardPoiState>(
  id = id,
  descriptor = StandardPoi(importId),
  originalFeature = originalFeature,
  state = state
) {

  /**
   * Name of the point of interest.
   */
  val name get(): String? = originalFeature.getStringProperty("name")

  /**
   * A high-level point of interest category like airport, transit, etc.
   */
  val group get(): String? = originalFeature.getStringProperty("group")

  /**
   * A broad category of point of interest.
   */
  val `class` get(): String? = originalFeature.getStringProperty("class")

  /**
   * An icon identifier, designed to assign icons using the Maki icon project or other icons that follow the same naming scheme.
   */
  val maki get(): String? = originalFeature.getStringProperty("maki")

  /**
   * Mode of transport served by a stop/station. Expected to be null for non-transit points of interest.
   */
  val transitMode get(): String? = originalFeature.getStringProperty("transit_mode")

  /**
   * A type of transit stop. Expected to be null for non-transit points of interest.
   */
  val transitStopType get(): String? = originalFeature.getStringProperty("transit_stop_type")

  /**
   * A rail station network identifier that is part of specific local or regional transit systems. Expected to be null for non-transit points of interest.
   */
  val transitNetwork get(): String? = originalFeature.getStringProperty("transit_network")

  /**
   * A short identifier code of the airport. Expected to be null for non-airport points of interest
   */
  val airportRef get(): String? = originalFeature.getStringProperty("airport_ref")

  /**
   * Mandatory feature [Geometry] represented as a [Point].
   */
  override val geometry: Point get() = super.geometry as Point
}