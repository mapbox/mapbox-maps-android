package com.mapbox.maps.interactions

import androidx.annotation.RestrictTo
import com.mapbox.maps.FeaturesetDescriptor
import com.mapbox.maps.MapboxExperimental
import java.util.Objects

/**
 * Holder class to differentiate featuresets.
 */
@MapboxExperimental
abstract class FeaturesetHolder private constructor() {

  /**
   * For internal usage.
   */
  @RestrictTo(RestrictTo.Scope.LIBRARY)
  abstract fun toFeaturesetDescriptor(): FeaturesetDescriptor

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
  ) : FeaturesetHolder() {

    /**
     * For internal usage.
     */
    override fun toFeaturesetDescriptor() = FeaturesetDescriptor(featuresetId, importId, null)

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
  ) : FeaturesetHolder() {

    /**
     * For internal usage.
     */
    override fun toFeaturesetDescriptor() = FeaturesetDescriptor(null, null, layerId)

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