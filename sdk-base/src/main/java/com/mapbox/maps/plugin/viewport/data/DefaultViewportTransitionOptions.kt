package com.mapbox.maps.plugin.viewport.data

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.viewport.DEFAULT_TRANSITION_MAX_DURATION_MS
import com.mapbox.maps.plugin.viewport.transition.DefaultViewportTransition

/**
 * Configuration options that impact the [DefaultViewportTransition].
 */
@MapboxExperimental
class DefaultViewportTransitionOptions private constructor(
  /**
   * The maximum duration of the transitions in milliseconds.
   *
   * Defaults to [DEFAULT_TRANSITION_MAX_DURATION_MS] milliseconds.
   */
  val maxDurationMs: Long
) {
  /**
   * Returns a builder that created the [DefaultViewportTransitionOptions]
   */
  fun toBuilder() = Builder().maxDurationMs(maxDurationMs)

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is DefaultViewportTransitionOptions &&
    maxDurationMs == other.maxDurationMs

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = maxDurationMs.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() =
    "DefaultViewportTransitionOptions(maxDurationMs=$maxDurationMs)"

  /**
   * Builder for [DefaultViewportTransitionOptions]
   */
  class Builder {
    private var maxDurationMs: Long = DEFAULT_TRANSITION_MAX_DURATION_MS

    /**
     * Sets maximum duration of the generated transitions set in milliseconds.
     *
     * Defaults to [DEFAULT_TRANSITION_MAX_DURATION_MS] milliseconds.
     */
    fun maxDurationMs(maxDurationMs: Long) = apply {
      this.maxDurationMs = maxDurationMs
    }

    /**
     * Builds [DefaultViewportTransitionOptions]
     */
    fun build() = DefaultViewportTransitionOptions(maxDurationMs)
  }
}