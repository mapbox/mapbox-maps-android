package com.mapbox.maps.plugin.viewport.data

import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.plugin.viewport.DEFAULT_FRAME_ANIMATION_DURATION_MS
import java.util.Objects

/**
 * Options that impact the [FollowingViewportState].
 */
class FollowingViewportStateOptions private constructor(
  /**
   * The default zoom that will be generated for camera following frames.
   *
   * Defaults to `16.35`.
   */
  val zoom: Double,
  /**
   * The default pitch that will be generated for following camera frames.
   *
   * Defaults to `45.0` degrees.
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
  val frameAnimationDurationMs: Long,
  /**
   * The camera bearing configuration of the [FollowingViewportState].
   *
   * Defaults to [FollowingViewportStateBearing.SyncWithLocationPuck]
   */
  val bearing: FollowingViewportStateBearing
) {
  /**
   * Returns a builder that created the [FollowingViewportStateOptions]
   */
  fun toBuilder() = Builder().zoom(zoom).pitch(pitch).padding(padding)
    .frameAnimationDurationMs(frameAnimationDurationMs).bearing(bearing)

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is FollowingViewportStateOptions &&
    zoom.compareTo(other.zoom) == 0 &&
    pitch.compareTo(other.pitch) == 0 &&
    padding == other.padding &&
    frameAnimationDurationMs == other.frameAnimationDurationMs &&
    bearing == other.bearing

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() =
    Objects.hash(zoom, pitch, padding, frameAnimationDurationMs, bearing)

  /**
   * Returns a String for the object.
   */
  override fun toString() =
    "FollowingViewportStateOptions(zoom=$zoom, pitch=$pitch, padding=$padding, frameAnimationDurationMs=$frameAnimationDurationMs, bearing=$bearing)"

  /**
   * Builder for [FollowingViewportStateOptions]
   */
  class Builder {
    private var zoom: Double = 16.35
    private var pitch: Double = 45.0
    private var padding: EdgeInsets = EdgeInsets(0.0, 0.0, 0.0, 0.0)
    private var frameAnimationDurationMs: Long = DEFAULT_FRAME_ANIMATION_DURATION_MS
    private var bearing: FollowingViewportStateBearing =
      FollowingViewportStateBearing.SyncWithLocationPuck

    /**
     * The default zoom that will be generated for camera following frames.
     *
     * Defaults to `16.35`.
     */
    fun zoom(zoom: Double) = apply {
      this.zoom = zoom
    }

    /**
     * The default pitch that will be generated for following camera frames.
     *
     * Defaults to `45.0` degrees.
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
    fun frameAnimationDurationMs(duration: Long) = apply {
      this.frameAnimationDurationMs = duration
    }

    /**
     * The camera bearing configuration of the [FollowingViewportState].
     *
     * Defaults to [FollowingViewportStateBearing.SyncWithLocationPuck]
     */
    fun bearing(options: FollowingViewportStateBearing) = apply {
      this.bearing = options
    }

    /**
     * Builds [FollowingViewportStateOptions]
     */
    fun build() =
      FollowingViewportStateOptions(
        zoom,
        pitch,
        padding,
        frameAnimationDurationMs,
        bearing
      )
  }
}