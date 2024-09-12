package com.mapbox.maps.interactions

import androidx.annotation.RestrictTo
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.maps.FeaturesetDescriptor
import com.mapbox.maps.MapboxExperimental
import java.util.Objects

/**
 * Holder class to differentiate featuresets.
 */
@MapboxExperimental
abstract class FeaturesetHolder<FS : FeatureState> private constructor() {

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
  abstract fun getInteractiveFeature(
    feature: Feature,
    featureNamespace: String?,
    rawState: Value
  ): InteractiveFeature<FS>

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
  ) : FeaturesetHolder<FeatureState>() {

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
    override fun getInteractiveFeature(
      feature: Feature,
      featureNamespace: String?,
      rawState: Value
    ) = InteractiveFeature(
      featuresetHolder = this,
      feature = feature,
      featureNamespace = featureNamespace,
      state = getFeatureState(rawState)
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
  ) : FeaturesetHolder<FeatureState>() {

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
    override fun getInteractiveFeature(
      feature: Feature,
      featureNamespace: String?,
      rawState: Value
    ) = InteractiveFeature(
      featuresetHolder = this,
      feature = feature,
      featureNamespace = featureNamespace,
      state = getFeatureState(rawState)
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
}