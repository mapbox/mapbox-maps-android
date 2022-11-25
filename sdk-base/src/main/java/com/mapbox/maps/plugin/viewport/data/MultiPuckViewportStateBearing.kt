package com.mapbox.maps.plugin.viewport.data

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin2
import com.mapbox.maps.plugin.viewport.state.MultiPuckViewportState
import java.util.Objects

/**
 * Describes different ways that [MultiPuckViewportState] can obtain values to use when setting
 * [CameraOptions.bearing].
 */
sealed class MultiPuckViewportStateBearing {
  /**
   * The [MultiPuckViewportState] sets the camera bearing to the constant value on every frame.
   *
   * @param bearing The bearing that the [MultiPuckViewportState] uses to generate camera updates.
   */
  class Constant(val bearing: Double) : MultiPuckViewportStateBearing() {
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
    override fun toString() = "MultiPuckViewportStateBearing#Constant(bearing=$bearing)"
  }

  /**
   * The [MultiPuckViewportState] sets the camera bearing to the same as the location puck's bearing.
   *
   * When set to this mode, the viewport's bearing is driven by the location, thus guarantees
   * the synchronization of the location puck and camera position.
   */
  class SyncWithLocationPuck(val locationComponent: LocationComponentPlugin2) : MultiPuckViewportStateBearing() {
    /**
     * Indicates whether some other object is "equal to" this one.
     */
    override fun equals(other: Any?) = other is SyncWithLocationPuck && locationComponent == other.locationComponent

    /**
     * Returns a hash code value for the object.
     */
    override fun hashCode() = Objects.hash(locationComponent)

    /**
     * Returns a String for the object.
     */
    override fun toString() = "MultiPuckViewportStateBearing#SyncWithLocationPuck"
  }
}