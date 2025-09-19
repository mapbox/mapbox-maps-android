// This file is generated.

package com.mapbox.maps.interactions.standard.generated

import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.maps.FeaturesetDescriptor
import com.mapbox.maps.FeaturesetFeatureId
import com.mapbox.maps.interactions.TypedFeaturesetDescriptor

/**
 * Represents a `landmark-icons` featureset from Mapbox Standard Style.
 */
class StandardLandmarkIcons @JvmOverloads constructor(
  /**
   * Optional import id when using Mapbox Standard Style as a style import for another style.
   */
  val importId: String? = null,
) : TypedFeaturesetDescriptor<StandardLandmarkIconsState, StandardLandmarkIconsFeature>() {

  /**
   * For internal usage.
   */
  override fun toFeaturesetDescriptor() =
    FeaturesetDescriptor(FEATURESET_ID, importId ?: DEFAULT_IMPORT_ID, null)

  /**
   * For internal usage.
   */
  override fun getFeatureState(rawState: Value) = StandardLandmarkIconsState(rawState)

  /**
   * For internal usage.
   */
  override fun getFeaturesetFeature(
    feature: Feature,
    featureNamespace: String?,
    rawState: Value
  ) = feature.id()?.let { featureId ->
    StandardLandmarkIconsFeature(
      id = FeaturesetFeatureId(featureId, featureNamespace),
      importId = importId,
      originalFeature = feature,
      state = getFeatureState(rawState)
    )
  } ?: StandardLandmarkIconsFeature(
    id = null,
    importId = importId,
    originalFeature = feature,
    state = getFeatureState(rawState)
  )

  /**
   * Overloaded equals.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as StandardLandmarkIcons

    return importId == other.importId
  }

  /**
   * Overloaded hashcode.
   */
  override fun hashCode(): Int {
    return importId.hashCode()
  }

  internal companion object {
    internal const val FEATURESET_ID = "landmark-icons"
  }
}