package com.mapbox.maps.plugin.viewport.data

import com.mapbox.geojson.Geometry
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.plugin.viewport.DEFAULT_FRAME_ANIMATION_DURATION_MS
import java.lang.IllegalArgumentException
import java.util.*

/**
 * Options that impact the [OverviewViewportState].
 */
class OverviewViewportStateOptions private constructor(
  /**
   * The geometry that the OverviewState should cover.
   */
  val geometry: Geometry,
  /**
   * The edge padding of the map.
   */
  val padding: EdgeInsets,
  /**
   * The bearing of the map.
   */
  val bearing: Double,
  /**
   * The pitch of the map.
   */
  val pitch: Double,
  /**
   * The duration between frames in milliseconds.
   *
   * Defaults to [DEFAULT_FRAME_ANIMATION_DURATION_MS] milliseconds
   */
  val frameAnimationDurationMs: Long
) {
  /**
   * Returns a builder that created the [OverviewViewportStateOptions]
   */
  fun toBuilder() = Builder().geometry(geometry).padding(padding).bearing(bearing).pitch(pitch)
    .frameAnimationDurationMs(frameAnimationDurationMs)

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is OverviewViewportStateOptions &&
    geometry == other.geometry &&
    padding == other.padding &&
    bearing.compareTo(other.bearing) == 0 &&
    pitch.compareTo(other.pitch) == 0 &&
    frameAnimationDurationMs == other.frameAnimationDurationMs

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() =
    Objects.hash(geometry, padding, bearing, pitch, frameAnimationDurationMs)

  /**
   * Returns a String for the object.
   */
  override fun toString() =
    "OverviewViewportStateOptions(geometry=$geometry, padding=$padding, bearing=$bearing, pitch=$pitch, frameAnimationDurationMs=$frameAnimationDurationMs)"

  /**
   * Builder for [OverviewViewportStateOptions]
   */
  class Builder {
    private var geometry: Geometry? = null
    private var padding: EdgeInsets = EdgeInsets(0.0, 0.0, 0.0, 0.0)
    private var bearing: Double = 0.0
    private var pitch: Double = 0.0
    private var frameAnimationDurationMs: Long = DEFAULT_FRAME_ANIMATION_DURATION_MS

    /**
     * The geometry that the OverviewState should cover.
     */
    fun geometry(geometry: Geometry) = apply {
      this.geometry = geometry
    }

    /**
     * The edge padding of the map.
     */
    fun padding(padding: EdgeInsets) = apply {
      this.padding = padding
    }

    /**
     * The bearing of the map.
     */
    fun bearing(bearing: Double) = apply {
      this.bearing = bearing
    }

    /**
     * The pitch of the map.
     */
    fun pitch(pitch: Double) = apply {
      this.pitch = pitch
    }

    /**
     * The duration between frames in milliseconds.
     *
     * Defaults to [DEFAULT_FRAME_ANIMATION_DURATION_MS] milliseconds
     */
    fun frameAnimationDurationMs(duration: Long) = apply {
      this.frameAnimationDurationMs = duration
    }

    /**
     * Builds [OverviewViewportStateOptions]
     */
    fun build() = geometry?.let {
      OverviewViewportStateOptions(
        it,
        padding,
        bearing,
        pitch,
        frameAnimationDurationMs
      )
    }
      ?: throw IllegalArgumentException("Geometry is required for OverviewViewportStateOptions and shouldn't be null")
  }
}