package com.mapbox.maps.plugin.locationcomponent.generated

import android.graphics.Color
import android.os.Parcelable
import com.mapbox.maps.plugin.LocationPuck
import com.mapbox.maps.plugin.PuckBearingSource
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
   * Enabled
   * Whether the user location is visible on the map.
   */
  public val enabled: Boolean,
  /**
   * PulsingEnabled
   * Whether the location puck is pulsing on the map. Works for 2D location puck only.
   */
  public val pulsingEnabled: Boolean,
  /**
   * PulsingColor
   * The color of the pulsing circle. Works for 2D location puck only.
   */
  public val pulsingColor: Int,
  /**
   * PulsingMaxRadius
   * The maximum radius of the pulsing circle. Works for 2D location puck only. Note: Setting
   * [pulsingMaxRadius] to LocationComponentConstants.PULSING_MAX_RADIUS_FOLLOW_ACCURACY will set the
   * pulsing circle's maximum radius to follow location accuracy circle. This property is specified in
   * pixels.
   */
  public val pulsingMaxRadius: Float,
  /**
   * ShowAccuracyRing
   * Whether show accuracy ring with location puck. Works for 2D location puck only.
   */
  public val showAccuracyRing: Boolean,
  /**
   * AccuracyRingColor
   * The color of the accuracy ring. Works for 2D location puck only.
   */
  public val accuracyRingColor: Int,
  /**
   * AccuracyRingBorderColor
   * The color of the accuracy ring border. Works for 2D location puck only.
   */
  public val accuracyRingBorderColor: Int,
  /**
   * LayerAbove
   * Sets the id of the layer that's added above to when placing the component on the map.
   */
  public val layerAbove: String?,
  /**
   * LayerBelow
   * Sets the id of the layer that's added below to when placing the component on the map.
   */
  public val layerBelow: String?,
  /**
   * PuckBearingEnabled
   * Whether the puck rotates to track the bearing source.
   */
  public val puckBearingEnabled: Boolean,
  /**
   * PuckBearingSource
   * The enum controls how the puck is oriented
   */
  public val puckBearingSource: PuckBearingSource,
  /**
   * LocationPuck
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
      puckBearingSource=$puckBearingSource, locationPuck=$locationPuck)""".trimIndent()

  /**
   * Overloaded equals function.
   */
  public override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as LocationComponentSettings
    return enabled == other.enabled && pulsingEnabled == other.pulsingEnabled &&
        pulsingColor == other.pulsingColor && pulsingMaxRadius == other.pulsingMaxRadius &&
        showAccuracyRing == other.showAccuracyRing &&
        accuracyRingColor == other.accuracyRingColor &&
        accuracyRingBorderColor == other.accuracyRingBorderColor &&
        layerAbove == other.layerAbove && layerBelow == other.layerBelow &&
        puckBearingEnabled == other.puckBearingEnabled &&
        puckBearingSource == other.puckBearingSource && locationPuck == other.locationPuck
  }

  /**
   * Overloaded hashCode function based on all class properties.
   */
  public override fun hashCode(): Int = Objects.hash(enabled, pulsingEnabled, pulsingColor,
      pulsingMaxRadius, showAccuracyRing, accuracyRingColor, accuracyRingBorderColor, layerAbove,
      layerBelow, puckBearingEnabled, puckBearingSource, locationPuck)

  /**
   * Convert to Builder allowing to change class properties.
   */
  public fun toBuilder(): Builder = Builder() .setEnabled(enabled)
      .setPulsingEnabled(pulsingEnabled) .setPulsingColor(pulsingColor)
      .setPulsingMaxRadius(pulsingMaxRadius) .setShowAccuracyRing(showAccuracyRing)
      .setAccuracyRingColor(accuracyRingColor) .setAccuracyRingBorderColor(accuracyRingBorderColor)
      .setLayerAbove(layerAbove) .setLayerBelow(layerBelow)
      .setPuckBearingEnabled(puckBearingEnabled) .setPuckBearingSource(puckBearingSource)
      .setLocationPuck(locationPuck)

  /**
   * Composes and builds a [LocationComponentSettings] object.
   *
   * This is a concrete implementation of the builder design pattern.
   */
  public class Builder {
    /**
     * Enabled
     * Whether the user location is visible on the map.
     */
    @set:JvmSynthetic
    public var enabled: Boolean? = false

    /**
     * PulsingEnabled
     * Whether the location puck is pulsing on the map. Works for 2D location puck only.
     */
    @set:JvmSynthetic
    public var pulsingEnabled: Boolean? = false

    /**
     * PulsingColor
     * The color of the pulsing circle. Works for 2D location puck only.
     */
    @set:JvmSynthetic
    public var pulsingColor: Int? = Color.parseColor("#4A90E2")

    /**
     * PulsingMaxRadius
     * The maximum radius of the pulsing circle. Works for 2D location puck only. Note: Setting
     * [pulsingMaxRadius] to LocationComponentConstants.PULSING_MAX_RADIUS_FOLLOW_ACCURACY will set the
     * pulsing circle's maximum radius to follow location accuracy circle. This property is specified
     * in pixels.
     */
    @set:JvmSynthetic
    public var pulsingMaxRadius: Float? = 10f

    /**
     * ShowAccuracyRing
     * Whether show accuracy ring with location puck. Works for 2D location puck only.
     */
    @set:JvmSynthetic
    public var showAccuracyRing: Boolean? = false

    /**
     * AccuracyRingColor
     * The color of the accuracy ring. Works for 2D location puck only.
     */
    @set:JvmSynthetic
    public var accuracyRingColor: Int? = Color.parseColor("#4d89cff0")

    /**
     * AccuracyRingBorderColor
     * The color of the accuracy ring border. Works for 2D location puck only.
     */
    @set:JvmSynthetic
    public var accuracyRingBorderColor: Int? = Color.parseColor("#4d89cff0")

    /**
     * LayerAbove
     * Sets the id of the layer that's added above to when placing the component on the map.
     */
    @set:JvmSynthetic
    public var layerAbove: String? = null

    /**
     * LayerBelow
     * Sets the id of the layer that's added below to when placing the component on the map.
     */
    @set:JvmSynthetic
    public var layerBelow: String? = null

    /**
     * PuckBearingEnabled
     * Whether the puck rotates to track the bearing source.
     */
    @set:JvmSynthetic
    public var puckBearingEnabled: Boolean? = true

    /**
     * PuckBearingSource
     * The enum controls how the puck is oriented
     */
    @set:JvmSynthetic
    public var puckBearingSource: PuckBearingSource? = PuckBearingSource.HEADING

    /**
     * LocationPuck
     * Defines what the customised look of the location puck. Note that direct changes to the puck
     * variables won't have any effect, a new puck needs to be set every time.
     */
    @set:JvmSynthetic
    public var locationPuck: LocationPuck? = null

    /**
     * Set enabled
     * Whether the user location is visible on the map.
     *
     * @param enabled enabled
     * @return Builder
     */
    public fun setEnabled(enabled: Boolean?): Builder {
      this.enabled = enabled
      return this
    }

    /**
     * Set pulsingEnabled
     * Whether the location puck is pulsing on the map. Works for 2D location puck only.
     *
     * @param pulsingEnabled pulsingEnabled
     * @return Builder
     */
    public fun setPulsingEnabled(pulsingEnabled: Boolean?): Builder {
      this.pulsingEnabled = pulsingEnabled
      return this
    }

    /**
     * Set pulsingColor
     * The color of the pulsing circle. Works for 2D location puck only.
     *
     * @param pulsingColor pulsingColor
     * @return Builder
     */
    public fun setPulsingColor(pulsingColor: Int?): Builder {
      this.pulsingColor = pulsingColor
      return this
    }

    /**
     * Set pulsingMaxRadius
     * The maximum radius of the pulsing circle. Works for 2D location puck only. Note: Setting
     * [pulsingMaxRadius] to LocationComponentConstants.PULSING_MAX_RADIUS_FOLLOW_ACCURACY will set the
     * pulsing circle's maximum radius to follow location accuracy circle. This property is specified
     * in pixels.
     *
     * @param pulsingMaxRadius pulsingMaxRadius
     * @return Builder
     */
    public fun setPulsingMaxRadius(pulsingMaxRadius: Float?): Builder {
      this.pulsingMaxRadius = pulsingMaxRadius
      return this
    }

    /**
     * Set showAccuracyRing
     * Whether show accuracy ring with location puck. Works for 2D location puck only.
     *
     * @param showAccuracyRing showAccuracyRing
     * @return Builder
     */
    public fun setShowAccuracyRing(showAccuracyRing: Boolean?): Builder {
      this.showAccuracyRing = showAccuracyRing
      return this
    }

    /**
     * Set accuracyRingColor
     * The color of the accuracy ring. Works for 2D location puck only.
     *
     * @param accuracyRingColor accuracyRingColor
     * @return Builder
     */
    public fun setAccuracyRingColor(accuracyRingColor: Int?): Builder {
      this.accuracyRingColor = accuracyRingColor
      return this
    }

    /**
     * Set accuracyRingBorderColor
     * The color of the accuracy ring border. Works for 2D location puck only.
     *
     * @param accuracyRingBorderColor accuracyRingBorderColor
     * @return Builder
     */
    public fun setAccuracyRingBorderColor(accuracyRingBorderColor: Int?): Builder {
      this.accuracyRingBorderColor = accuracyRingBorderColor
      return this
    }

    /**
     * Set layerAbove
     * Sets the id of the layer that's added above to when placing the component on the map.
     *
     * @param layerAbove layerAbove
     * @return Builder
     */
    public fun setLayerAbove(layerAbove: String?): Builder {
      this.layerAbove = layerAbove
      return this
    }

    /**
     * Set layerBelow
     * Sets the id of the layer that's added below to when placing the component on the map.
     *
     * @param layerBelow layerBelow
     * @return Builder
     */
    public fun setLayerBelow(layerBelow: String?): Builder {
      this.layerBelow = layerBelow
      return this
    }

    /**
     * Set puckBearingEnabled
     * Whether the puck rotates to track the bearing source.
     *
     * @param puckBearingEnabled puckBearingEnabled
     * @return Builder
     */
    public fun setPuckBearingEnabled(puckBearingEnabled: Boolean?): Builder {
      this.puckBearingEnabled = puckBearingEnabled
      return this
    }

    /**
     * Set puckBearingSource
     * The enum controls how the puck is oriented
     *
     * @param puckBearingSource puckBearingSource
     * @return Builder
     */
    public fun setPuckBearingSource(puckBearingSource: PuckBearingSource?): Builder {
      this.puckBearingSource = puckBearingSource
      return this
    }

    /**
     * Set locationPuck
     * Defines what the customised look of the location puck. Note that direct changes to the puck
     * variables won't have any effect, a new puck needs to be set every time.
     *
     * @param locationPuck locationPuck
     * @return Builder
     */
    public fun setLocationPuck(locationPuck: LocationPuck?): Builder {
      this.locationPuck = locationPuck
      return this
    }

    /**
     * Returns a [LocationComponentSettings] reference to the object being constructed by the
     * builder.
     *
     * Throws an [IllegalArgumentException] when a non-null property wasn't initialised.
     *
     * @return LocationComponentSettings
     */
    public fun build(): LocationComponentSettings {
      if (enabled==null) {
      	throw IllegalArgumentException("""Null enabled found when building
          LocationComponentSettings.""".trimIndent())
      }
      if (pulsingEnabled==null) {
      	throw IllegalArgumentException("""Null pulsingEnabled found when building
          LocationComponentSettings.""".trimIndent())
      }
      if (pulsingColor==null) {
      	throw IllegalArgumentException("""Null pulsingColor found when building
          LocationComponentSettings.""".trimIndent())
      }
      if (pulsingMaxRadius==null) {
      	throw IllegalArgumentException("""Null pulsingMaxRadius found when building
          LocationComponentSettings.""".trimIndent())
      }
      if (showAccuracyRing==null) {
      	throw IllegalArgumentException("""Null showAccuracyRing found when building
          LocationComponentSettings.""".trimIndent())
      }
      if (accuracyRingColor==null) {
      	throw IllegalArgumentException("""Null accuracyRingColor found when building
          LocationComponentSettings.""".trimIndent())
      }
      if (accuracyRingBorderColor==null) {
      	throw IllegalArgumentException("""Null accuracyRingBorderColor found when building
          LocationComponentSettings.""".trimIndent())
      }
      if (puckBearingEnabled==null) {
      	throw IllegalArgumentException("""Null puckBearingEnabled found when building
          LocationComponentSettings.""".trimIndent())
      }
      if (puckBearingSource==null) {
      	throw IllegalArgumentException("""Null puckBearingSource found when building
          LocationComponentSettings.""".trimIndent())
      }
      if (locationPuck==null) {
      	throw IllegalArgumentException("""Null locationPuck found when building
          LocationComponentSettings.""".trimIndent())
      }
      return LocationComponentSettings(enabled!!, pulsingEnabled!!, pulsingColor!!,
          pulsingMaxRadius!!, showAccuracyRing!!, accuracyRingColor!!, accuracyRingBorderColor!!,
          layerAbove, layerBelow, puckBearingEnabled!!, puckBearingSource!!, locationPuck!!)
    }
  }
}

/**
 * Creates a [LocationComponentSettings] through a DSL-style builder.
 *
 * @param initializer the initialisation block
 * @return LocationComponentSettings
 */
@JvmSynthetic
public fun LocationComponentSettings(initializer: LocationComponentSettings.Builder.() -> Unit):
    LocationComponentSettings = LocationComponentSettings.Builder().apply(initializer).build()
