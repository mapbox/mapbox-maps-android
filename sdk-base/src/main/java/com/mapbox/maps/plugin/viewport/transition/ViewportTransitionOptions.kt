package com.mapbox.maps.plugin.viewport.transition

import com.mapbox.maps.plugin.viewport.DEFAULT_FRAME_TRANSITION_MAX_DURATION_MS

/**
 * Options that impact the transition animation.
 *
 * The styling of the animation is determined by the [ViewportStateTransition]
 * and [ViewportTransition] implementations, but options here provide higher-level
 * constraints that those implementations need to obey.
 *
 * @param maxDurationMs maximum duration of the generated transitions set,
 * including delays between animators and their respective durations.
 */
class ViewportTransitionOptions private constructor(
  val maxDurationMs: Long
) {
  /**
   * @return the builder that created the [ViewportTransitionOptions]
   */
  fun toBuilder() = Builder()
    .maxDuration(maxDurationMs)

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as ViewportTransitionOptions

    if (maxDurationMs != other.maxDurationMs) return false

    return true
  }

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode(): Int {
    return maxDurationMs.hashCode()
  }

  /**
   * Returns a string representation of the object.
   */
  override fun toString(): String {
    return "ViewportCameraTransitionOptions(maxDurationMs=$maxDurationMs)"
  }

  /**
   * Builder for [ViewportTransitionOptions].
   */
  class Builder {
    private var maxDurationMs: Long = DEFAULT_FRAME_TRANSITION_MAX_DURATION_MS

    /**
     * Sets maximum duration of the generated transitions set in milliseconds,
     * including delays between animators and their respective durations.
     *
     * Defaults to 1000 milliseconds.
     */
    fun maxDuration(maxDurationMs: Long) = apply {
      this.maxDurationMs = maxDurationMs
    }

    /**
     * Builds [ViewportTransitionOptions].
     */
    fun build() = ViewportTransitionOptions(maxDurationMs)
  }

  internal companion object {
    fun build(setUp: Builder.() -> Unit): ViewportTransitionOptions =
      Builder().apply(setUp).build()
  }
}