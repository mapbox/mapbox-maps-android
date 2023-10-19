@file:Suppress("RedundantVisibilityModifier")

package com.mapbox.maps.plugin.locationcomponent.generated

import android.graphics.Color
import android.os.Parcelable
import com.mapbox.maps.plugin.LocationPuck
import com.mapbox.maps.plugin.PuckBearing
import java.util.Objects
import kotlin.Any
import kotlin.Boolean
import kotlin.Float
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlin.jvm.JvmSynthetic
import kotlinx.parcelize.Parcelize

/**
 * Shows a location puck on the map.
 */
@Parcelize
public class LocationComponentSettings private constructor(
  /**
   * Whether the user location is visible on the map.
   */
  public val enabled: Boolean,
  /**
   * Whether the location puck is pulsing on the map. Works for 2D location puck only.
   */
  public val pulsingEnabled: Boolean,
  /**
   * The color of the pulsing circle. Works for 2D location puck only.
   */
  public val pulsingColor: Int,
  /**
   * The maximum radius of the pulsing circle. Works for 2D location puck only. Note: Setting
   * [pulsingMaxRadius] to LocationComponentConstants.PULSING_MAX_RADIUS_FOLLOW_ACCURACY will set the
   * pulsing circle's maximum radius to follow location accuracy circle. This property is specified in
   * pixels.
   */
  public val pulsingMaxRadius: Float,
  /**
   * Whether show accuracy ring with location puck. Works for 2D location puck only.
   */
  public val showAccuracyRing: Boolean,
  /**
   * The color of the accuracy ring. Works for 2D location puck only.
   */
  public val accuracyRingColor: Int,
  /**
   * The color of the accuracy ring border. Works for 2D location puck only.
   */
  public val accuracyRingBorderColor: Int,
  /**
   * Sets the id of the layer that's added above to when placing the component on the map.
   */
  public val layerAbove: String?,
  /**
   * Sets the id of the layer that's added below to when placing the component on the map.
   */
  public val layerBelow: String?,
  /**
   * Whether the puck rotates to track the bearing source.
   */
  public val puckBearingEnabled: Boolean,
  /**
   * The enum controls how the puck is oriented
   */
  public val puckBearing: PuckBearing,
  /**
   * Defines what the customised look of the location puck. Note that direct changes to the puck
   * variables won't have any effect, a new puck needs to be set every time.
   */
  public val locationPuck: LocationPuck
) : Parcelable {
  /**
   * Overloaded toString function.
   */
  public override fun toString() = """LocationComponentSettings(enabled=$enabled,
      pulsingEnabled=$pulsingEnabled, pulsingColor=$pulsingColor,
      pulsingMaxRadius=$pulsingMaxRadius, showAccuracyRing=$showAccuracyRing,
      accuracyRingColor=$accuracyRingColor, accuracyRingBorderColor=$accuracyRingBorderColor,
      layerAbove=$layerAbove, layerBelow=$layerBelow, puckBearingEnabled=$puckBearingEnabled,
      puckBearing=$puckBearing, locationPuck=$locationPuck)""".trimIndent()

  /**
   * Overloaded equals function.
   */
  public override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as LocationComponentSettings
    return enabled == other.enabled && pulsingEnabled == other.pulsingEnabled &&
        pulsingColor == other.pulsingColor &&
        pulsingMaxRadius.compareTo(other.pulsingMaxRadius) == 0 &&
        showAccuracyRing == other.showAccuracyRing &&
        accuracyRingColor == other.accuracyRingColor &&
        accuracyRingBorderColor == other.accuracyRingBorderColor &&
        layerAbove == other.layerAbove && layerBelow == other.layerBelow &&
        puckBearingEnabled == other.puckBearingEnabled && puckBearing == other.puckBearing &&
        locationPuck == other.locationPuck
  }

  /**
   * Overloaded hashCode function based on all class properties.
   */
  public override fun hashCode(): Int = Objects.hash(enabled, pulsingEnabled, pulsingColor,
      pulsingMaxRadius, showAccuracyRing, accuracyRingColor, accuracyRingBorderColor, layerAbove,
      layerBelow, puckBearingEnabled, puckBearing, locationPuck)

  /**
   * Convert to Builder allowing to change class properties.
   */
  public fun toBuilder(): Builder = Builder(locationPuck) .setEnabled(enabled)
      .setPulsingEnabled(pulsingEnabled) .setPulsingColor(pulsingColor)
      .setPulsingMaxRadius(pulsingMaxRadius) .setShowAccuracyRing(showAccuracyRing)
      .setAccuracyRingColor(accuracyRingColor) .setAccuracyRingBorderColor(accuracyRingBorderColor)
      .setLayerAbove(layerAbove) .setLayerBelow(layerBelow)
      .setPuckBearingEnabled(puckBearingEnabled) .setPuckBearing(puckBearing)
      .setLocationPuck(locationPuck)

  /**
   * Composes and builds a [LocationComponentSettings] object.
   *
   * This is a concrete implementation of the builder design pattern.
   */
  public class Builder(
    /**
     * Defines what the customised look of the location puck. Note that direct changes to the puck
     * variables won't have any effect, a new puck needs to be set every time.
     */
    @set:JvmSynthetic
    public var locationPuck: LocationPuck
  ) {
    /**
     * Whether the user location is visible on the map.
     */
    @set:JvmSynthetic
    public var enabled: Boolean = false

    /**
     * Whether the location puck is pulsing on the map. Works for 2D location puck only.
     */
    @set:JvmSynthetic
    public var pulsingEnabled: Boolean = false

    /**
     * The color of the pulsing circle. Works for 2D location puck only.
     */
    @set:JvmSynthetic
    public var pulsingColor: Int = Color.parseColor("#4A90E2")

    /**
     * The maximum radius of the pulsing circle. Works for 2D location puck only. Note: Setting
     * [pulsingMaxRadius] to LocationComponentConstants.PULSING_MAX_RADIUS_FOLLOW_ACCURACY will set the
     * pulsing circle's maximum radius to follow location accuracy circle. This property is specified
     * in pixels.
     */
    @set:JvmSynthetic
    public var pulsingMaxRadius: Float = 10f

    /**
     * Whether show accuracy ring with location puck. Works for 2D location puck only.
     */
    @set:JvmSynthetic
    public var showAccuracyRing: Boolean = false

    /**
     * The color of the accuracy ring. Works for 2D location puck only.
     */
    @set:JvmSynthetic
    public var accuracyRingColor: Int = Color.parseColor("#4d89cff0")

    /**
     * The color of the accuracy ring border. Works for 2D location puck only.
     */
    @set:JvmSynthetic
    public var accuracyRingBorderColor: Int = Color.parseColor("#4d89cff0")

    /**
     * Sets the id of the layer that's added above to when placing the component on the map.
     */
    @set:JvmSynthetic
    public var layerAbove: String? = null

    /**
     * Sets the id of the layer that's added below to when placing the component on the map.
     */
    @set:JvmSynthetic
    public var layerBelow: String? = null

    /**
     * Whether the puck rotates to track the bearing source.
     */
    @set:JvmSynthetic
    public var puckBearingEnabled: Boolean = false

    /**
     * The enum controls how the puck is oriented
     */
    @set:JvmSynthetic
    public var puckBearing: PuckBearing = PuckBearing.HEADING

    /**
     * Setter for enabled: whether the user location is visible on the map.
     *
     * @param enabled
     * @return Builder
     */
    public fun setEnabled(enabled: Boolean): Builder {
      this.enabled = enabled
      return this
    }

    /**
     * Setter for pulsingEnabled: whether the location puck is pulsing on the map. Works for 2D
     * location puck only.
     *
     * @param pulsingEnabled
     * @return Builder
     */
    public fun setPulsingEnabled(pulsingEnabled: Boolean): Builder {
      this.pulsingEnabled = pulsingEnabled
      return this
    }

    /**
     * Setter for pulsingColor: the color of the pulsing circle. Works for 2D location puck only.
     *
     * @param pulsingColor
     * @return Builder
     */
    public fun setPulsingColor(pulsingColor: Int): Builder {
      this.pulsingColor = pulsingColor
      return this
    }

    /**
     * Setter for pulsingMaxRadius: the maximum radius of the pulsing circle. Works for 2D location
     * puck only. Note: Setting [pulsingMaxRadius] to
     * LocationComponentConstants.PULSING_MAX_RADIUS_FOLLOW_ACCURACY will set the pulsing circle's
     * maximum radius to follow location accuracy circle. This property is specified in pixels.
     *
     * @param pulsingMaxRadius
     * @return Builder
     */
    public fun setPulsingMaxRadius(pulsingMaxRadius: Float): Builder {
      this.pulsingMaxRadius = pulsingMaxRadius
      return this
    }

    /**
     * Setter for showAccuracyRing: whether show accuracy ring with location puck. Works for 2D
     * location puck only.
     *
     * @param showAccuracyRing
     * @return Builder
     */
    public fun setShowAccuracyRing(showAccuracyRing: Boolean): Builder {
      this.showAccuracyRing = showAccuracyRing
      return this
    }

    /**
     * Setter for accuracyRingColor: the color of the accuracy ring. Works for 2D location puck
     * only.
     *
     * @param accuracyRingColor
     * @return Builder
     */
    public fun setAccuracyRingColor(accuracyRingColor: Int): Builder {
      this.accuracyRingColor = accuracyRingColor
      return this
    }

    /**
     * Setter for accuracyRingBorderColor: the color of the accuracy ring border. Works for 2D
     * location puck only.
     *
     * @param accuracyRingBorderColor
     * @return Builder
     */
    public fun setAccuracyRingBorderColor(accuracyRingBorderColor: Int): Builder {
      this.accuracyRingBorderColor = accuracyRingBorderColor
      return this
    }

    /**
     * Setter for layerAbove: sets the id of the layer that's added above to when placing the
     * component on the map.
     *
     * @param layerAbove
     * @return Builder
     */
    public fun setLayerAbove(layerAbove: String?): Builder {
      this.layerAbove = layerAbove
      return this
    }

    /**
     * Setter for layerBelow: sets the id of the layer that's added below to when placing the
     * component on the map.
     *
     * @param layerBelow
     * @return Builder
     */
    public fun setLayerBelow(layerBelow: String?): Builder {
      this.layerBelow = layerBelow
      return this
    }

    /**
     * Setter for puckBearingEnabled: whether the puck rotates to track the bearing source.
     *
     * @param puckBearingEnabled
     * @return Builder
     */
    public fun setPuckBearingEnabled(puckBearingEnabled: Boolean): Builder {
      this.puckBearingEnabled = puckBearingEnabled
      return this
    }

    /**
     * Setter for puckBearing: the enum controls how the puck is oriented.
     *
     * @param puckBearing
     * @return Builder
     */
    public fun setPuckBearing(puckBearing: PuckBearing): Builder {
      this.puckBearing = puckBearing
      return this
    }

    /**
     * Setter for locationPuck: defines what the customised look of the location puck. Note that
     * direct changes to the puck variables won't have any effect, a new puck needs to be set every
     * time.
     *
     * @param locationPuck
     * @return Builder
     */
    public fun setLocationPuck(locationPuck: LocationPuck): Builder {
      this.locationPuck = locationPuck
      return this
    }

    /**
     * Returns a [LocationComponentSettings] reference to the object being constructed by the
     * builder.
     *
     * @return LocationComponentSettings
     */
    public fun build(): LocationComponentSettings = LocationComponentSettings(enabled,
        pulsingEnabled, pulsingColor, pulsingMaxRadius, showAccuracyRing, accuracyRingColor,
        accuracyRingBorderColor, layerAbove, layerBelow, puckBearingEnabled, puckBearing,
        locationPuck)
  }
}

/**
 * Creates a [LocationComponentSettings] through a DSL-style builder.
 *
 * @param initializer the initialisation block
 * @return LocationComponentSettings
 */
@JvmSynthetic
public fun LocationComponentSettings(locationPuck: LocationPuck,
    initializer: LocationComponentSettings.Builder.() -> Unit): LocationComponentSettings =
    LocationComponentSettings.Builder(locationPuck).apply(initializer).build()
