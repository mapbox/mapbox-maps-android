package com.mapbox.maps.interactions

import androidx.annotation.RestrictTo
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.ClickInteraction
import com.mapbox.maps.FeaturesetFeatureId
import com.mapbox.maps.LongClickInteraction
import com.mapbox.maps.MapboxExperimental
import org.json.JSONObject
import java.util.Objects

/**
 * The map interaction result object returned in the callback of:
 *  1. User map interactions ([ClickInteraction], [LongClickInteraction]).
 *  2. Map query methods like `MapboxMap.queryRenderedFeatures`.
 */
@MapboxExperimental
open class FeaturesetFeature<FS : FeatureState> @RestrictTo(RestrictTo.Scope.LIBRARY) constructor(
  /**
   * Optional identifier holding feature id and feature namespace.
   * It could be NULL when underlying [Feature.id] is null.
   */
  val id: FeaturesetFeatureId?,
  /**
   * The [TypedFeaturesetDescriptor] this concrete feature comes from.
   * List of supported featuresets could be found in the nested classes (e.g. [TypedFeaturesetDescriptor.Featureset], [TypedFeaturesetDescriptor.Layer] etc).
   */
  val descriptor: TypedFeaturesetDescriptor<FS, *>,
  /**
   * Current feature state stored as a concrete instance of [FeatureState].
   * Important: this state is immutable and represents the feature state at the precise moment of the interaction callback.
   */
  val state: FS,
  /**
   * For internal usage.
   */
  internal val originalFeature: Feature,
) {
  /**
   * Mandatory feature [Geometry].
   *
   * Based on the feature type this should be casted to the concrete implementation
   * like [Point], [Polygon] etc.
   */
  open val geometry: Geometry = originalFeature.geometry()!!

  /**
   * The feature properties stored in [JSONObject].
   */
  val properties: JSONObject by lazy {
    originalFeature.properties()?.let { json ->
      JSONObject(json.toString())
    } ?: JSONObject()
  }

  /**
   * Override equals method.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as FeaturesetFeature<*>

    if (descriptor != other.descriptor) return false
    if (originalFeature != other.originalFeature) return false
    if (state != other.state) return false
    if (id != other.id) return false

    return true
  }

  /**
   * Override hashCode method.
   */
  override fun hashCode(): Int =
    Objects.hash(descriptor, originalFeature, state, id)
}