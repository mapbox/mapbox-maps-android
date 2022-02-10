package com.mapbox.maps.plugin.viewport.data

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.viewport.state.FollowPuckViewportState
import java.util.Objects

/**
 * Describes different ways that [FollowPuckViewportState] can obtain values to use when setting
 * [CameraOptions.bearing].
 */
@MapboxExperimental
sealed class FollowPuckViewportStateBearing {
  /**
   * The [FollowPuckViewportState] sets the camera bearing to the constant value on every frame.
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
   * The [FollowPuckViewportState] sets the camera bearing to the same as the location puck's bearing.
   *
   * When set to this mode, the viewport's bearing is driven by the location, thus guarantees
   * the synchronization of the location puck and camera position.
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