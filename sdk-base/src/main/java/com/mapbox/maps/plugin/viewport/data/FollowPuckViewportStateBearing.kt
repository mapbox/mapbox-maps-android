package com.mapbox.maps.plugin.viewport.data

import com.mapbox.maps.MapboxExperimental
import java.util.Objects

/**
 * Describes the camera bearing options for the [FollowPuckViewportState].
 */
@MapboxExperimental
sealed class FollowPuckViewportStateBearing {
  /**
   * The viewport's bearing is fixed to the given bearing.
   *
   * @param bearing The bearing that the [FollowPuckViewportState] uses to generate camera updates.
   */
  class Constant(val bearing: Double) : FollowPuckViewportStateBearing() {
    /**
     * Indicates whether some other object is "equal to" this one.
     */
    override fun equals(other: Any?) = other is Constant && bearing == other.bearing

    /**
     * Returns a hash code value for the object.
     */
    override fun hashCode() = Objects.hash(bearing)

    /**
     * Returns a String for the object.
     */
    override fun toString() = "FollowPuckViewportStateBearing#Constant(bearing=$bearing)"
  }

  /**
   * The viewport's bearing is set as the same as the location puck's bearing.
   *
   * When set to this mode, the viewport's bearing is driven by the location, thus guarantees
   * synchronization.
   */
  object SyncWithLocationPuck : FollowPuckViewportStateBearing() {
    /**
     * Indicates whether some other object is "equal to" this one.
     */
    override fun equals(other: Any?) = other is SyncWithLocationPuck

    /**
     * Returns a hash code value for the object.
     */
    override fun hashCode() = Objects.hash(SyncWithLocationPuck)

    /**
     * Returns a String for the object.
     */
    override fun toString() = "FollowPuckViewportStateBearing#SyncWithLocationPuck"
  }
}