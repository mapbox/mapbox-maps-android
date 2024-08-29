package com.mapbox.maps.interactions

import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.maps.ClickInteraction
import com.mapbox.maps.LongClickInteraction
import com.mapbox.maps.MapboxExperimental
import java.util.Objects

/**
 * Should not be used directly. Derived classes should be used instead.
 */
@MapboxExperimental
abstract class BaseInteractiveFeature<FH : FeaturesetHolder, V : FeatureStateValue> internal constructor(
  /**
   * The [FeaturesetHolder] this concrete interactive feature comes from.
   * Could be [FeaturesetHolder.Featureset] or [FeaturesetHolder.Layer].
   */
  val featuresetHolder: FH,
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
   * Current interactive feature state stored as a raw [Value].
   * Note that this state is immutable and represents the interactive feature state at the precise moment of the interaction callback.
   */
  val state: Value,
) {

  /**
   * Override equals method.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as BaseInteractiveFeature<*, *>

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

/**
 * The interactive feature object returned in the callback of interactions
 * when using the `featureset` factory method (e.g. [ClickInteraction.featureset] or [LongClickInteraction.featureset]).
 */
@MapboxExperimental
class InteractiveFeature<FH : FeaturesetHolder>(
  featuresetHolder: FH,
  feature: Feature,
  featureNamespace: String?,
  state: Value,
) : BaseInteractiveFeature<FH, FeatureStateValue>(featuresetHolder, feature, featureNamespace, state)