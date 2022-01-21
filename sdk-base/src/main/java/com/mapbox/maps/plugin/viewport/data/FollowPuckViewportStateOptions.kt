package com.mapbox.maps.plugin.viewport.data

import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.plugin.viewport.DEFAULT_FOLLOW_VIEWPORT_STATE_PITCH
import com.mapbox.maps.plugin.viewport.DEFAULT_FOLLOW_VIEWPORT_STATE_ZOOM
import com.mapbox.maps.plugin.viewport.DEFAULT_FRAME_ANIMATION_DURATION_MS
import java.util.Objects

/**
 * Options that impact the [FollowingViewportState].
 */
class FollowPuckViewportStateOptions private constructor(
  /**
   * The default zoom that will be generated for camera following frames.
   *
   * Defaults to [DEFAULT_FOLLOW_VIEWPORT_STATE_ZOOM].
   */
  val zoom: Double,
  /**
   * The default pitch that will be generated for following camera frames.
   *
   * Defaults to [DEFAULT_FOLLOW_VIEWPORT_STATE_PITCH] degrees.
   */
  val pitch: Double,
  /**
   * The edge padding of the map.
   */
  val padding: EdgeInsets,
  /**
   * The maximum duration between frames in milliseconds.
   *
   * Defaults to [DEFAULT_FRAME_ANIMATION_DURATION_MS] milliseconds
   */
  val animationDurationMs: Long,
  /**
   * The camera bearing configuration of the [FollowingViewportState].
   *
   * Defaults to [FollowPuckViewportStateBearing.SyncWithLocationPuck]
   */
  val bearing: FollowPuckViewportStateBearing
) {
  /**
   * Returns a builder that created the [FollowPuckViewportStateOptions]
   */
  fun toBuilder() = Builder().zoom(zoom).pitch(pitch).padding(padding)
    .animationDurationMs(animationDurationMs).bearing(bearing)

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is FollowPuckViewportStateOptions &&
    zoom.compareTo(other.zoom) == 0 &&
    pitch.compareTo(other.pitch) == 0 &&
    padding == other.padding &&
    animationDurationMs == other.animationDurationMs &&
    bearing == other.bearing

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() =
    Objects.hash(zoom, pitch, padding, animationDurationMs, bearing)

  /**
   * Returns a String for the object.
   */
  override fun toString() =
    "FollowingViewportStateOptions(zoom=$zoom, pitch=$pitch, padding=$padding, animationDurationMs=$animationDurationMs, bearing=$bearing)"

  /**
   * Builder for [FollowPuckViewportStateOptions]
   */
  class Builder {
    private var zoom: Double = DEFAULT_FOLLOW_VIEWPORT_STATE_ZOOM
    private var pitch: Double = DEFAULT_FOLLOW_VIEWPORT_STATE_PITCH
    private var padding: EdgeInsets = EdgeInsets(0.0, 0.0, 0.0, 0.0)
    private var animationDurationMs: Long = DEFAULT_FRAME_ANIMATION_DURATION_MS
    private var bearing: FollowPuckViewportStateBearing =
      FollowPuckViewportStateBearing.SyncWithLocationPuck

    /**
     * The default zoom that will be generated for camera following frames.
     *
     * Defaults to [DEFAULT_FOLLOW_VIEWPORT_STATE_ZOOM].
     */
    fun zoom(zoom: Double) = apply {
      this.zoom = zoom
    }

    /**
     * The default pitch that will be generated for following camera frames.
     *
     * Defaults to [DEFAULT_FOLLOW_VIEWPORT_STATE_PITCH] degrees.
     */
    fun pitch(pitch: Double) = apply {
      this.pitch = pitch
    }

    /**
     * The edge padding of the map.
     */
    fun padding(padding: EdgeInsets) = apply {
      this.padding = padding
    }

    /**
     * The maximum duration between frames in milliseconds.
     *
     * Defaults to [DEFAULT_FRAME_ANIMATION_DURATION_MS] milliseconds
     */
    fun animationDurationMs(duration: Long) = apply {
      this.animationDurationMs = duration
    }

    /**
     * The camera bearing configuration of the [FollowingViewportState].
     *
     * Defaults to [FollowPuckViewportStateBearing.SyncWithLocationPuck]
     */
    fun bearing(options: FollowPuckViewportStateBearing) = apply {
      this.bearing = options
    }

    /**
     * Builds [FollowPuckViewportStateOptions]
     */
    fun build() =
      FollowPuckViewportStateOptions(
        zoom,
        pitch,
        padding,
        animationDurationMs,
        bearing
      )
  }
}