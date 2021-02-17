package com.mapbox.maps.plugin.animation

import android.animation.Animator
import android.animation.TimeInterpolator

/**
 * Map transition options which are used to configure animation if using high-level API like [CameraAnimationsPlugin.easeTo], [CameraAnimationsPlugin.flyTo] etc
 * [MapAnimationOptions.Builder] should be used to create options object via DSL function [mapAnimationOptions].
 */
class MapAnimationOptions private constructor(
  /**
   * Owner or creator this animation.
   */
  val owner: String?,
  /**
   * The duration of the animation in milliseconds.
   * If not set explicitly default duration will be taken (CameraAnimatorsFactory.DEFAULT_ANIMATION_DURATION_MS if using plugin-animation).
   */
  val duration: Long?,
  /**
   * The animation interpolator.
   * If not set explicitly default interpolator will be taken (CameraAnimatorsFactory.DEFAULT_INTERPOLATOR if using plugin-animation).
   */
  val interpolator: TimeInterpolator?,
  /**
   * Animator start / cancel / end listener.
   */
  val animatorListener: Animator.AnimatorListener?
) {

  /**
   * Builder class used to construct immutable [MapAnimationOptions] object.
   */
  class Builder {

    /**
     * Owner or creator this animation.
     */
    private var owner: String? = null

    /**
     * The duration of the animation in milliseconds.
     */
    private var duration: Long? = null

    /**
     * The animation interpolator.
     */
    private var interpolator: TimeInterpolator? = null

    /**
     * Animator start / cancel / end listener.
     */
    private var animatorListener: Animator.AnimatorListener? = null

    /**
     * Set the owner or creator this animation.
     */
    fun owner(owner: String) = apply { this.owner = owner }

    /**
     * Set the duration of the animation in milliseconds.
     */
    fun duration(duration: Long) = apply { this.duration = duration }

    /**
     * Set the animation interpolator.
     */
    fun interpolator(interpolator: TimeInterpolator) = apply { this.interpolator = interpolator }

    /**
     * Set the animator start / cancel / end listener.
     */
    fun animatorListener(animatorListener: Animator.AnimatorListener) = apply { this.animatorListener = animatorListener }

    /**
     * Build an actual [MapAnimationOptions] object.
     */
    fun build(): MapAnimationOptions = MapAnimationOptions(owner, duration, interpolator, animatorListener)
  }

  /**
   * Hash code method.
   */
  override fun hashCode(): Int {
    var result = duration ?: 0
    result = 31 * result + (owner?.hashCode() ?: 0)
    result = 31 * result + (interpolator?.hashCode() ?: 0)
    result = 31 * result + (animatorListener?.hashCode() ?: 0)
    return result.toInt()
  }

  /**
   * Equals method.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other == null || javaClass != other.javaClass) {
      return false
    }
    val that = other as MapAnimationOptions
    if (that.owner != owner) {
      return false
    }
    if (that.duration != duration) {
      return false
    }
    if (that.interpolator != interpolator) {
      return false
    }
    if (that.animatorListener != animatorListener) {
      return false
    }
    return true
  }

  /**
   * Static methods.
   */
  companion object {
    /**
     * Builder DSL function to create [MapAnimationOptions] object.
     */
    inline fun mapAnimationOptions(block: Builder.() -> Unit) = Builder().apply(block).build()
  }
}