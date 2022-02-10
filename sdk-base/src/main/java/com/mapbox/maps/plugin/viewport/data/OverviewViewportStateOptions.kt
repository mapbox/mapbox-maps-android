package com.mapbox.maps.plugin.viewport.data

import com.mapbox.geojson.Geometry
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.viewport.DEFAULT_STATE_ANIMATION_DURATION_MS
import com.mapbox.maps.plugin.viewport.state.OverviewViewportState
import java.lang.IllegalArgumentException
import java.util.*

/**
 * Configuration options that impact the [OverviewViewportState].
 */
@MapboxExperimental
class OverviewViewportStateOptions private constructor(
  /**
   * The geometry that the [OverviewViewportState] should use when calculating its camera.
   */
  val geometry: Geometry,
  /**
   * The edge padding that [OverviewViewportState] should use when calculating its camera.
   *
   * Defaults to 0 padding.
   */
  val padding: EdgeInsets,
  /**
   * The bearing that [OverviewViewportState] should use when calculating its camera.
   *
   * Defaults to 0.
   */
  val bearing: Double?,
  /**
   * The pitch that [OverviewViewportState] should use when calculating its camera.
   *
   * Defaults to 0.
   */
  val pitch: Double?,
  /**
   * The length of the animation performed in milliseconds by [OverviewViewportState] when it starts
   * updating the camera and anytime [OverviewViewportState.options] is set.
   *
   * @see [OverviewViewportState.options] for details.
   *
   * Defaults to [DEFAULT_STATE_ANIMATION_DURATION_MS] milliseconds
   */
  val animationDurationMs: Long
) {
  /**
   * Returns a builder that created the [OverviewViewportStateOptions]
   */
  fun toBuilder() = Builder().geometry(geometry).padding(padding).bearing(bearing).pitch(pitch)
    .animationDurationMs(animationDurationMs)

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is OverviewViewportStateOptions &&
    geometry == other.geometry &&
    padding == other.padding &&
    Objects.equals(bearing, other.bearing) &&
    Objects.equals(pitch, other.pitch) &&
    animationDurationMs == other.animationDurationMs

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() =
    Objects.hash(geometry, padding, bearing, pitch, animationDurationMs)

  /**
   * Returns a String for the object.
   */
  override fun toString() =
    "OverviewViewportStateOptions(geometry=$geometry, padding=$padding, bearing=$bearing, pitch=$pitch, animationDurationMs=$animationDurationMs)"

  /**
   * Builder for [OverviewViewportStateOptions]
   */
  class Builder {
    private var geometry: Geometry? = null
    private var padding: EdgeInsets = EdgeInsets(0.0, 0.0, 0.0, 0.0)
    private var bearing: Double? = 0.0
    private var pitch: Double? = 0.0
    private var animationDurationMs: Long = DEFAULT_STATE_ANIMATION_DURATION_MS

    /**
     * The geometry that the [OverviewViewportState] should use when calculating its camera.
     */
    fun geometry(geometry: Geometry) = apply {
      this.geometry = geometry
    }

    /**
     * The edge padding that [OverviewViewportState] should use when calculating its camera.
     *
     * Defaults to 0 padding.
     */
    fun padding(padding: EdgeInsets) = apply {
      this.padding = padding
    }

    /**
     * The bearing that [OverviewViewportState] should use when calculating its camera.
     *
     * Defaults to 0.
     */
    fun bearing(bearing: Double?) = apply {
      this.bearing = bearing
    }

    /**
     * The pitch that [OverviewViewportState] should use when calculating its camera.
     *
     * Defaults to 0.
     */
    fun pitch(pitch: Double?) = apply {
      this.pitch = pitch
    }

    /**
     * The length of the animation performed by [OverviewViewportState] when it starts updating the
     * camera and anytime [OverviewViewportState.options] is set.
     *
     * @see [OverviewViewportState.options] for details.
     *
     * Defaults to [DEFAULT_STATE_ANIMATION_DURATION_MS] milliseconds
     */
    fun animationDurationMs(duration: Long) = apply {
      this.animationDurationMs = duration
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
        animationDurationMs
      )
    }
      ?: throw IllegalArgumentException("Geometry is required for OverviewViewportStateOptions and shouldn't be null")
  }
}