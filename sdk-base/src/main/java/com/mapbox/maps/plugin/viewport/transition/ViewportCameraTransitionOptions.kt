package com.mapbox.maps.plugin.viewport.transition

/**
 * Options that impact the transition animation.
 *
 * The styling of the animation is determined by the [ViewportCameraStateTransition]
 * and [ViewportCameraTransition] implementations, but options here provide higher-level
 * constraints that those implementations need to obey.
 *
 * @param maxDurationMs maximum duration of the generated transitions set,
 * including delays between animators and their respective durations.
 */
class ViewportCameraTransitionOptions private constructor(
  val maxDurationMs: Long
) {
  /**
   * @return the builder that created the [ViewportCameraTransitionOptions]
   */
  fun toBuilder() = Builder()
    .maxDuration(maxDurationMs)

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as ViewportCameraTransitionOptions

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
   * Builder for [ViewportCameraTransitionOptions].
   */
  class Builder {
    private var maxDurationMs: Long = 1000L

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
     * Builds [ViewportCameraTransitionOptions].
     */
    fun build() = ViewportCameraTransitionOptions(maxDurationMs)
  }

  internal companion object {
    fun build(setUp: Builder.() -> Unit): ViewportCameraTransitionOptions =
      Builder().apply(setUp).build()
  }
}