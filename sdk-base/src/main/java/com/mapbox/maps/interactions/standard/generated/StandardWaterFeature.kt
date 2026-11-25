// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import com.mapbox.geojson.Feature
import com.mapbox.maps.FeaturesetFeatureId
import com.mapbox.maps.interactions.FeaturesetFeature

/**
 * Water polygons, such as oceans, lakes, and wide rivers.
 *
 * Typed class of [FeaturesetFeature] representing StandardWater coming from Mapbox Standard Style.
 * Allows easy access to [Feature] properties.
 */
class StandardWaterFeature(
  id: FeaturesetFeatureId?,
  importId: String?,
  originalFeature: Feature,
  state: StandardWaterState,
) : FeaturesetFeature<StandardWaterState>(
  id = id,
  descriptor = StandardWater(importId),
  originalFeature = originalFeature,
  state = state
)