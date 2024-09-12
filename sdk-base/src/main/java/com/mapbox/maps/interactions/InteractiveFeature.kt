package com.mapbox.maps.interactions

import com.mapbox.geojson.Feature
import com.mapbox.maps.MapboxExperimental
import java.util.Objects

/**
 * The interactive feature object returned in the callback of interactions either
 * interactions (ClickInteraction, LongClickInteraction) or `MapboxMap.queryRenderedFeature`.
 */
@MapboxExperimental
class InteractiveFeature<FS : FeatureState>(
  /**
   * The [FeaturesetHolder] this concrete interactive feature comes from.
   * List of supported featuresets could be found in the nested classes (e.g. [FeaturesetHolder.Featureset], [FeaturesetHolder.Layer]).
   */
  val featuresetHolder: FeaturesetHolder<FS>,
  /**
   * The interactive feature data (id, geometry, properties as JSON) encapsulated in the [Feature] object.
   */
  val feature: Feature,
  /**
   * Optional feature namespace.
   *
   * Namespace represents the feature namespace defined by the Selector within a featureset to which this feature belongs.
   * If the underlying source is the same for multiple selectors within a featureset, the same [featureNamespace] should be used across those selectors.
   * Defining a [featureNamespace] value for the Selector is recommended, especially when multiple selectors exist in a featureset, as it can enhance the efficiency of feature operations.
   */
  val featureNamespace: String?,
  /**
   * Current interactive feature state stored as a concrete instance of [FeatureState].
   * Note that this state is immutable and represents the interactive feature state at the precise moment of the interaction callback.
   */
  val state: FS,
) {
  /**
   * Override equals method.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as InteractiveFeature<*>

    if (featuresetHolder != other.featuresetHolder) return false
    if (feature != other.feature) return false
    if (state != other.state) return false
    if (featureNamespace != other.featureNamespace) return false

    return true
  }

  /**
   * Override hashCode method.
   */
  override fun hashCode(): Int =
    Objects.hash(featuresetHolder, feature, state, featureNamespace)
}