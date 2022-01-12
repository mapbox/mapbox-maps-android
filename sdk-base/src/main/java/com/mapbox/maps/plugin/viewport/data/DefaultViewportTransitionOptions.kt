package com.mapbox.maps.plugin.viewport.data

import com.mapbox.maps.plugin.viewport.DEFAULT_STATE_TRANSITION_MAX_DURATION_MS

/**
 * Options that impact the [DefaultViewportTransition].
 */
class DefaultViewportTransitionOptions private constructor(
  /**
   * The maximum duration of the transitions in milliseconds,
   * including delays between animators and their respective durations.
   *
   * Defaults to [DEFAULT_STATE_TRANSITION_MAX_DURATION_MS] milliseconds.
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
    private var maxDurationMs: Long = DEFAULT_STATE_TRANSITION_MAX_DURATION_MS

    /**
     * Sets maximum duration of the generated transitions set in milliseconds,
     * including delays between animators and their respective durations.
     *
     * Defaults to [DEFAULT_STATE_TRANSITION_MAX_DURATION_MS] milliseconds.
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