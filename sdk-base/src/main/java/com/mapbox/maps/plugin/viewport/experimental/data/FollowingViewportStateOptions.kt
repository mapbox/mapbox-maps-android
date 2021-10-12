package com.mapbox.maps.plugin.viewport.experimental.data

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
    defaultZoom == other.defaultZoom &&
    defaultPitch == other.defaultPitch &&
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

/**
 * Describes the camera bearing options for the [FollowingViewportState].
 */
sealed class FollowingViewportStateBearing {
  /**
   * The viewport's bearing is fixed to the given bearing.
   *
   * @param bearing The bearing that the [FollowingViewportState] uses to generate camera updates.
   */
  class Constant(val bearing: Double) : FollowingViewportStateBearing()

  /**
   * The viewport's bearing follows the user's heading, i.e. the direction the user is facing.
   */
  object Heading : FollowingViewportStateBearing()

  /**
   * The viewport's bearing follows the user's heading, i.e. the direction the user is actually
   * moving towards.
   */
  object Course : FollowingViewportStateBearing()

  /**
   * The viewport's bearing is set as the same as the location puck's bearing.
   *
   * When set to this mode, the viewport's bearing is driven by the location, thus guarantees
   * synchronization.
   */
  object SyncWithLocationPuck : FollowingViewportStateBearing()

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = when (this) {
    is Constant -> other is Constant && bearing == other.bearing
    is Heading -> other is Heading
    is Course -> other is Course
    is SyncWithLocationPuck -> other is SyncWithLocationPuck
  }

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = when (this) {
    is Constant -> Objects.hash(bearing)
    is Heading -> Objects.hash(Heading)
    is Course -> Objects.hash(Course)
    is SyncWithLocationPuck -> Objects.hash(SyncWithLocationPuck)
  }

  /**
   * Returns a String for the object.
   */
  override fun toString() = when (this) {
    is Constant -> "FollowingViewportStateBearing#Constant(bearing=$bearing)"
    is Heading -> "FollowingViewportStateBearing#Heading"
    is Course -> "FollowingViewportStateBearing#Course"
    is SyncWithLocationPuck -> "SyncWithLocationPuck#SyncWithLocationPuck"
  }
}