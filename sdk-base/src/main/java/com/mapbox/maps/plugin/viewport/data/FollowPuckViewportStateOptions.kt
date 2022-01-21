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
   * The edge padding of the map.
   */
  val padding: EdgeInsets?,
  /**
   * The default zoom that will be generated for camera following frames.
   *
   * Defaults to [DEFAULT_FOLLOW_VIEWPORT_STATE_ZOOM].
   */
  val zoom: Double?,
  /**
   * The camera bearing configuration of the [FollowingViewportState].
   *
   * Defaults to [FollowPuckViewportStateBearing.SyncWithLocationPuck]
   */
  val bearing: FollowPuckViewportStateBearing?,
  /**
   * The default pitch that will be generated for following camera frames.
   *
   * Defaults to [DEFAULT_FOLLOW_VIEWPORT_STATE_PITCH] degrees.
   */
  val pitch: Double?,
  /**
   * The maximum duration between frames in milliseconds.
   *
   * Defaults to [DEFAULT_FRAME_ANIMATION_DURATION_MS] milliseconds
   */
  val animationDurationMs: Long
) {
  /**
   * Returns a builder that created the [FollowPuckViewportStateOptions]
   */
  fun toBuilder() = Builder().padding(padding).zoom(zoom).bearing(bearing).pitch(pitch)
    .animationDurationMs(animationDurationMs)

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is FollowPuckViewportStateOptions &&
    padding == other.padding &&
    Objects.equals(zoom, other.zoom) &&
    bearing == other.bearing &&
    Objects.equals(pitch, other.pitch) &&
    animationDurationMs == other.animationDurationMs

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() =
    Objects.hash(padding, zoom, bearing, pitch, animationDurationMs)

  /**
   * Returns a String for the object.
   */
  override fun toString() =
    "FollowingViewportStateOptions(padding=$padding, zoom=$zoom, bearing=$bearing, pitch=$pitch, animationDurationMs=$animationDurationMs)"

  /**
   * Builder for [FollowPuckViewportStateOptions]
   */
  class Builder {
    private var padding: EdgeInsets? = EdgeInsets(0.0, 0.0, 0.0, 0.0)
    private var zoom: Double? = DEFAULT_FOLLOW_VIEWPORT_STATE_ZOOM
    private var bearing: FollowPuckViewportStateBearing? =
      FollowPuckViewportStateBearing.SyncWithLocationPuck
    private var pitch: Double? = DEFAULT_FOLLOW_VIEWPORT_STATE_PITCH
    private var animationDurationMs: Long = DEFAULT_FRAME_ANIMATION_DURATION_MS

    /**
     * The edge padding of the map.
     */
    fun padding(padding: EdgeInsets?) = apply {
      this.padding = padding
    }

    /**
     * The default zoom that will be generated for camera following frames.
     *
     * Defaults to [DEFAULT_FOLLOW_VIEWPORT_STATE_ZOOM].
     */
    fun zoom(zoom: Double?) = apply {
      this.zoom = zoom
    }

    /**
     * The camera bearing configuration of the [FollowingViewportState].
     *
     * Defaults to [FollowPuckViewportStateBearing.SyncWithLocationPuck]
     */
    fun bearing(options: FollowPuckViewportStateBearing?) = apply {
      this.bearing = options
    }

    /**
     * The default pitch that will be generated for following camera frames.
     *
     * Defaults to [DEFAULT_FOLLOW_VIEWPORT_STATE_PITCH] degrees.
     */
    fun pitch(pitch: Double?) = apply {
      this.pitch = pitch
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
     * Builds [FollowPuckViewportStateOptions]
     */
    fun build() =
      FollowPuckViewportStateOptions(
        padding,
        zoom,
        bearing,
        pitch,
        animationDurationMs
      )
  }
}