package com.mapbox.maps.plugin.viewport.data

import com.mapbox.geojson.Geometry
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.viewport.DEFAULT_STATE_ANIMATION_DURATION_MS
import com.mapbox.maps.plugin.viewport.state.OverviewViewportState
import java.lang.IllegalArgumentException
import java.util.*

/**
 * Configuration options that impact the [OverviewViewportState].
 */
class OverviewViewportStateOptions private constructor(
  /**
   * The geometry that the [OverviewViewportState] should use when calculating its camera.
   */
  val geometry: Geometry,
  /**
   * The amount of padding in pixels to add to the map when calculating its camera.
   *
   * Defaults to EdgeInsets(0.0, 0.0, 0.0, 0.0).
   */
  val padding: EdgeInsets?,
  /**
   * The amount of padding in pixels to add to the given [geometry].
   *
   * Note: This padding is not applied to the map but to the provided [geometry]. If you want to apply padding to the map use param [padding].
   *
   * Defaults to EdgeInsets(0.0, 0.0, 0.0, 0.0).
   */
  val geometryPadding: EdgeInsets,
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
   * The maximum allowed zoom level when calculating the camera of [OverviewViewportState].
   *
   * Defaults to null. That is, there won't be any restriction on the maximum zoom level allowed.
   */
  val maxZoom: Double?,
  /**
   * The offset to the center of the given geometry relative to map center in pixels, when calculating the camera of [OverviewViewportState].
   *
   * Defaults to ScreenCoordinate(0.0, 0.0).
   */
  val offset: ScreenCoordinate,
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
  fun toBuilder(): Builder =
    Builder().geometry(geometry).padding(padding).geometryPadding(geometryPadding)
      .bearing(bearing).pitch(pitch).maxZoom(maxZoom).offset(offset)
      .animationDurationMs(animationDurationMs)

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is OverviewViewportStateOptions &&
    geometry == other.geometry &&
    padding == other.padding &&
    geometryPadding == other.geometryPadding &&
    Objects.equals(bearing, other.bearing) &&
    Objects.equals(pitch, other.pitch) &&
    Objects.equals(maxZoom, other.maxZoom) &&
    offset == other.offset &&
    animationDurationMs == other.animationDurationMs

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() =
    Objects.hash(geometry, padding, geometryPadding, bearing, pitch, maxZoom, offset, animationDurationMs)

  /**
   * Returns a String for the object.
   */
  override fun toString() =
    "OverviewViewportStateOptions(geometry=$geometry, padding=$padding, geometryPadding=$geometryPadding, bearing=$bearing, pitch=$pitch, maxZoom=$maxZoom, offset=$offset, animationDurationMs=$animationDurationMs)"

  /**
   * Builder for [OverviewViewportStateOptions]
   */
  class Builder {
    private var geometry: Geometry? = null
    private var padding: EdgeInsets? = EdgeInsets(0.0, 0.0, 0.0, 0.0)
    private var geometryPadding: EdgeInsets = EdgeInsets(0.0, 0.0, 0.0, 0.0)
    private var bearing: Double? = 0.0
    private var pitch: Double? = 0.0
    private var maxZoom: Double? = null
    private var offset: ScreenCoordinate = ScreenCoordinate(0.0, 0.0)
    private var animationDurationMs: Long = DEFAULT_STATE_ANIMATION_DURATION_MS

    /**
     * The geometry that the [OverviewViewportState] should use when calculating its camera.
     */
    fun geometry(geometry: Geometry): Builder = apply {
      this.geometry = geometry
    }

    /**
     * The amount of padding in pixels to add to the map when calculating its camera.
     *
     * Defaults to EdgeInsets(0.0, 0.0, 0.0, 0.0).
     */
    fun padding(padding: EdgeInsets?): Builder = apply {
      this.padding = padding
    }

    /**
     * The amount of padding in pixels to add to the given [geometry].
     *
     * Note: This padding is not applied to the map but to the provided [geometry]. If you want to apply padding to the map use param [padding].
     *
     * Defaults to EdgeInsets(0.0, 0.0, 0.0, 0.0).
     */
    fun geometryPadding(geometryPadding: EdgeInsets): Builder = apply {
      this.geometryPadding = geometryPadding
    }

    /**
     * The bearing that [OverviewViewportState] should use when calculating its camera.
     *
     * Defaults to 0.
     */
    fun bearing(bearing: Double?): Builder = apply {
      this.bearing = bearing
    }

    /**
     * The pitch that [OverviewViewportState] should use when calculating its camera.
     *
     * Defaults to 0.
     */
    fun pitch(pitch: Double?): Builder = apply {
      this.pitch = pitch
    }

    /**
     * The maximum allowed zoom level when calculating the camera of [OverviewViewportState].
     *
     * Defaults to null. That is, there won't be any restriction on the maximum zoom level allowed.
     */
    fun maxZoom(maxZoom: Double?): Builder = apply {
      this.maxZoom = maxZoom
    }

    /**
     * The offset to the center of the given geometry relative to map center in pixels, when calculating the camera of [OverviewViewportState].
     *
     * Defaults to ScreenCoordinate(0.0, 0.0).
     */
    fun offset(offset: ScreenCoordinate): Builder = apply {
      this.offset = offset
    }

    /**
     * The length of the animation performed by [OverviewViewportState] when it starts updating the
     * camera and anytime [OverviewViewportState.options] is set.
     *
     * @see [OverviewViewportState.options] for details.
     *
     * Defaults to [DEFAULT_STATE_ANIMATION_DURATION_MS] milliseconds
     */
    fun animationDurationMs(duration: Long): Builder = apply {
      this.animationDurationMs = duration
    }

    /**
     * Builds [OverviewViewportStateOptions]
     */
    fun build(): OverviewViewportStateOptions = geometry?.let {
      OverviewViewportStateOptions(
        geometry = it,
        padding = padding,
        geometryPadding = geometryPadding,
        bearing = bearing,
        pitch = pitch,
        maxZoom = maxZoom,
        offset = offset,
        animationDurationMs = animationDurationMs
      )
    } ?: throw IllegalArgumentException("Geometry is required for OverviewViewportStateOptions and shouldn't be null")
  }
}