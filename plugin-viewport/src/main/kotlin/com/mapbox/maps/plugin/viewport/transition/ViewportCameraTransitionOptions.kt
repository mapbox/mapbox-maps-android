package com.mapbox.maps.plugin.viewport.transition

/**
 * Options that impact the transition animation.
 *
 * The styling of the animation is determined by the [ViewportCameraStateTransition]
 * and [ViewportCameraTransition] implementations, but options here provide higher-level
 * constraints that those implementations need to obey.
 *
 * @param maxDurationMS maximum duration of the generated transitions set,
 * including delays between animators and their respective durations.
 */
class ViewportCameraTransitionOptions private constructor(
  val maxDurationMS: Long
) {
  /**
   * @return the builder that created the [ViewportCameraTransitionOptions]
   */
  fun toBuilder() = Builder()
    .maxDuration(maxDurationMS)

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as ViewportCameraTransitionOptions

    if (maxDurationMS != other.maxDurationMS) return false

    return true
  }

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode(): Int {
    return maxDurationMS.hashCode()
  }

  /**
   * Returns a string representation of the object.
   */
  override fun toString(): String {
    return "ViewportCameraTransitionOptions(maxDurationMS=$maxDurationMS)"
  }

  /**
   * Builder for [ViewportCameraTransitionOptions].
   */
  class Builder {
    private var maxDurationMS: Long = 1000L

    /**
     * Sets maximum duration of the generated transitions set in milliseconds,
     * including delays between animators and their respective durations.
     *
     * Defaults to 1000 milliseconds.
     */
    fun maxDuration(maxDurationMS: Long) = apply {
      this.maxDurationMS = maxDurationMS
    }

    /**
     * Builds [ViewportCameraTransitionOptions].
     */
    fun build() = ViewportCameraTransitionOptions(maxDurationMS)
  }
}
