package com.mapbox.maps.plugin.viewport.data

import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.plugin.viewport.DEFAULT_FRAME_TRANSITION_MAX_DURATION_MS
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
  val defaultZoom: Double,
  /**
   * The default pitch that will be generated for following camera frames.
   *
   * Defaults to `45.0` degrees.
   */
  val defaultPitch: Double,
  /**
   * The edge padding of the map.
   */
  val padding: EdgeInsets,
  /**
   * The maximum duration between frames in milliseconds.
   *
   * Defaults to [DEFAULT_FRAME_TRANSITION_MAX_DURATION_MS] milliseconds
   */
  val frameTransitionMaxDurationMs: Long,
  /**
   * The camera bearing configuration of the [FollowingViewportState].
   *
   * Defaults to [FollowingViewportStateBearing.SyncWithLocationPuck]
   */
  val bearingOptions: FollowingViewportStateBearing
) {
  /**
   * Returns a builder that created the [FollowingViewportStateOptions]
   */
  fun toBuilder() = Builder().defaultZoom(defaultZoom).defaultPitch(defaultPitch).padding(padding)
    .frameTransitionMaxDurationMs(frameTransitionMaxDurationMs).bearingOptions(bearingOptions)

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is FollowingViewportStateOptions &&
    defaultZoom.compareTo(other.defaultZoom) == 0 &&
    defaultPitch.compareTo(other.defaultPitch) == 0 &&
    padding == other.padding &&
    frameTransitionMaxDurationMs == other.frameTransitionMaxDurationMs &&
    bearingOptions == other.bearingOptions

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() =
    Objects.hash(defaultZoom, defaultPitch, padding, frameTransitionMaxDurationMs, bearingOptions)

  /**
   * Returns a String for the object.
   */
  override fun toString() =
    "FollowingViewportStateOptions(defaultZoom=$defaultZoom, defaultPitch=$defaultPitch, padding=$padding, frameTransitionMaxDurationMs=$frameTransitionMaxDurationMs, bearingOptions=$bearingOptions)"

  /**
   * Builder for [FollowingViewportStateOptions]
   */
  class Builder {
    private var defaultZoom: Double = 16.35
    private var defaultPitch: Double = 45.0
    private var padding: EdgeInsets = EdgeInsets(0.0, 0.0, 0.0, 0.0)
    private var frameTransitionMaxDuration: Long = DEFAULT_FRAME_TRANSITION_MAX_DURATION_MS
    private var bearingOptions: FollowingViewportStateBearing =
      FollowingViewportStateBearing.SyncWithLocationPuck

    /**
     * The default zoom that will be generated for camera following frames.
     *
     * Defaults to `16.35`.
     */
    fun defaultZoom(zoom: Double) = apply {
      this.defaultZoom = zoom
    }

    /**
     * The default pitch that will be generated for following camera frames.
     *
     * Defaults to `45.0` degrees.
     */
    fun defaultPitch(pitch: Double) = apply {
      this.defaultPitch = pitch
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
     * Defaults to [DEFAULT_FRAME_TRANSITION_MAX_DURATION_MS] milliseconds
     */
    fun frameTransitionMaxDurationMs(duration: Long) = apply {
      this.frameTransitionMaxDuration = duration
    }

    /**
     * The camera bearing configuration of the [FollowingViewportState].
     *
     * Defaults to [FollowingViewportStateBearing.SyncWithLocationPuck]
     */
    fun bearingOptions(options: FollowingViewportStateBearing) = apply {
      this.bearingOptions = options
    }

    /**
     * Builds [FollowingViewportStateOptions]
     */
    fun build() =
      FollowingViewportStateOptions(
        defaultZoom,
        defaultPitch,
        padding,
        frameTransitionMaxDuration,
        bearingOptions
      )
  }
}