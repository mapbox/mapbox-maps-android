package com.mapbox.maps.plugin.viewport.data

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.plugin.viewport.DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_PITCH
import com.mapbox.maps.plugin.viewport.DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_ZOOM
import com.mapbox.maps.plugin.viewport.state.FollowPuckViewportState
import java.util.Objects

/**
 * Configuration options that impact the [FollowPuckViewportState].
 *
 * Each of the [CameraOptions] related properties is optional, so that the state can be configured
 * to only modify certain aspects of the camera if desired. This can be used, to achieve effects like
 * allowing zoom gestures to work simultaneously with [FollowPuckViewportState].
 *
 * @see [ViewportOptions.transitionsToIdleUponUserInteraction]
 */
class FollowPuckViewportStateOptions private constructor(
  /**
   * The value to use for setting [CameraOptions.padding]. If null, padding will not be modified by
   * the [FollowPuckViewportState].
   *
   * Defaults to 0 padding.
   */
  val padding: EdgeInsets?,
  /**
   * The value to use for setting [CameraOptions.zoom]. If null, zoom will not be modified by
   * the [FollowPuckViewportState].
   *
   * Defaults to [DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_ZOOM].
   */
  val zoom: Double?,
  /**
   * Indicates how to obtain the value to use for [CameraOptions.bearing] when setting the camera.
   * If set to null, bearing will not be modified by the [FollowPuckViewportState].
   *
   * Defaults to [FollowPuckViewportStateBearing.SyncWithLocationPuck]
   */
  val bearing: FollowPuckViewportStateBearing?,
  /**
   * The value to use for setting [CameraOptions.pitch]. If null, pitch will not be modified by
   * the [FollowPuckViewportState].
   *
   * Defaults to [DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_PITCH] degrees.
   */
  val pitch: Double?,
) {
  /**
   * Returns a builder that created the [FollowPuckViewportStateOptions]
   */
  fun toBuilder() = Builder().padding(padding).zoom(zoom).bearing(bearing).pitch(pitch)

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is FollowPuckViewportStateOptions &&
    padding == other.padding &&
    Objects.equals(zoom, other.zoom) &&
    bearing == other.bearing &&
    Objects.equals(pitch, other.pitch)

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = Objects.hash(padding, zoom, bearing, pitch)

  /**
   * Returns a String for the object.
   */
  override fun toString() =
    "FollowPuckViewportStateOptions(padding=$padding, zoom=$zoom, bearing=$bearing, pitch=$pitch)"

  /**
   * Builder for [FollowPuckViewportStateOptions]
   */
  class Builder {
    private var padding: EdgeInsets? = EdgeInsets(0.0, 0.0, 0.0, 0.0)
    private var zoom: Double? = DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_ZOOM
    private var bearing: FollowPuckViewportStateBearing? =
      FollowPuckViewportStateBearing.SyncWithLocationPuck
    private var pitch: Double? = DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_PITCH

    /**
     * The value to use for setting [CameraOptions.padding]. If null, padding will not be modified by
     * the [FollowPuckViewportState].
     *
     * Defaults to 0 padding.
     */
    fun padding(padding: EdgeInsets?) = apply {
      this.padding = padding
    }

    /**
     * The value to use for setting [CameraOptions.zoom]. If null, zoom will not be modified by
     * the [FollowPuckViewportState].
     *
     * Defaults to [DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_ZOOM].
     */
    fun zoom(zoom: Double?) = apply {
      this.zoom = zoom
    }

    /**
     * Indicates how to obtain the value to use for [CameraOptions.bearing] when setting the camera.
     * If set to null, bearing will not be modified by the [FollowPuckViewportState].
     *
     * Defaults to [FollowPuckViewportStateBearing.SyncWithLocationPuck]
     */
    fun bearing(options: FollowPuckViewportStateBearing?) = apply {
      this.bearing = options
    }

    /**
     * The value to use for setting [CameraOptions.pitch]. If null, pitch will not be modified by
     * the [FollowPuckViewportState].
     *
     * Defaults to [DEFAULT_FOLLOW_PUCK_VIEWPORT_STATE_PITCH] degrees.
     */
    fun pitch(pitch: Double?) = apply {
      this.pitch = pitch
    }

    /**
     * Builds [FollowPuckViewportStateOptions]
     */
    fun build() =
      FollowPuckViewportStateOptions(
        padding,
        zoom,
        bearing,
        pitch,
      )
  }
}