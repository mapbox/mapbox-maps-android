package com.mapbox.maps.interactions

import androidx.annotation.RestrictTo
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.maps.FeaturesetDescriptor
import com.mapbox.maps.FeaturesetFeatureId
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.interactions.standard.generated.StandardBuildings
import java.util.Objects

/**
 * Base class to differentiate featuresets.
 *
 * See generated predefined featuresets (e.g. [StandardBuildings]) to use with Mapbox Standard Style.
 * See [TypedFeaturesetDescriptor.Featureset] and [TypedFeaturesetDescriptor.Layer] to define a fully custom FeaturesetDescriptor.
 */
@MapboxExperimental
abstract class TypedFeaturesetDescriptor<FS : FeatureState, FF : FeaturesetFeature<FS>> protected constructor() {

  /**
   * For internal usage.
   */
  @RestrictTo(RestrictTo.Scope.LIBRARY)
  abstract fun toFeaturesetDescriptor(): FeaturesetDescriptor

  /**
   * For internal usage.
   */
  @RestrictTo(RestrictTo.Scope.LIBRARY)
  abstract fun getFeatureState(rawState: Value): FS

  /**
   * For internal usage.
   */
  @RestrictTo(RestrictTo.Scope.LIBRARY)
  abstract fun getFeaturesetFeature(
    feature: Feature,
    featureNamespace: String?,
    rawState: Value
  ): FF

  /**
   * Represents an actual featureset.
   *
   * @param featuresetId mandatory id.
   * @param importId optional style import id.
   */
  @MapboxExperimental
  class Featureset @JvmOverloads constructor(
    val featuresetId: String,
    val importId: String? = null
  ) : TypedFeaturesetDescriptor<FeatureState, FeaturesetFeature<FeatureState>>() {

    /**
     * For internal usage.
     */
    override fun toFeaturesetDescriptor() = FeaturesetDescriptor(featuresetId, importId, null)

    /**
     * For internal usage.
     */
    override fun getFeatureState(rawState: Value) = FeatureState(rawState)

    /**
     * For internal usage.
     */
    override fun getFeaturesetFeature(
      feature: Feature,
      featureNamespace: String?,
      rawState: Value
    ) = feature.id()?.let { featureId ->
      FeaturesetFeature(
        descriptor = this,
        id = FeaturesetFeatureId(featureId, featureNamespace),
        state = getFeatureState(rawState),
        originalFeature = feature
      )
    } ?: FeaturesetFeature(
      descriptor = this,
      id = null,
      state = getFeatureState(rawState),
      originalFeature = feature
    )

    /**
     * Overloaded equals.
     */
    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as Featureset

      if (featuresetId != other.featuresetId) return false
      if (importId != other.importId) return false

      return true
    }

    /**
     * Overloaded hashcode.
     */
    override fun hashCode(): Int {
      return Objects.hash(featuresetId, importId)
    }
  }

  /**
   * Represents an single style layer.
   *
   * @param layerId mandatory id.
   */
  @MapboxExperimental
  class Layer(
    val layerId: String,
  ) : TypedFeaturesetDescriptor<FeatureState, FeaturesetFeature<FeatureState>>() {

    /**
     * For internal usage.
     */
    override fun toFeaturesetDescriptor() = FeaturesetDescriptor(null, null, layerId)

    /**
     * For internal usage.
     */
    override fun getFeatureState(rawState: Value) = FeatureState(rawState)

    /**
     * For internal usage.
     */
    override fun getFeaturesetFeature(
      feature: Feature,
      featureNamespace: String?,
      rawState: Value
    ) = feature.id()?.let { featureId ->
      FeaturesetFeature(
        descriptor = this,
        id = FeaturesetFeatureId(featureId, featureNamespace),
        state = getFeatureState(rawState),
        originalFeature = feature
      )
    } ?: FeaturesetFeature(
      descriptor = this,
      id = null,
      state = getFeatureState(rawState),
      originalFeature = feature
    )

    /**
     * Overloaded equals.
     */
    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as Layer

      return layerId == other.layerId
    }

    /**
     * Overloaded hashcode.
     */
    override fun hashCode(): Int {
      return layerId.hashCode()
    }
  }

  internal companion object {
    internal const val DEFAULT_IMPORT_ID = "basemap"
  }
}