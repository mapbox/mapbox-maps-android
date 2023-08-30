package com.mapbox.maps.plugin.animation

/**
 * Class responsible for storing options used by all CameraAnimator's from [CameraAnimationsPlugin].
 * [CameraAnimatorOptions.Builder] should be used to create options object via DSL function [cameraAnimatorOptions].
 */
class CameraAnimatorOptions<T> private constructor(
  /**
   * CameraAnimator targets (values between which interpolation will take place).
   */
  vararg val targets: T,
  /**
   * If specified explicitly - this value will be used as first target.
   * Otherwise current camera option on animation start will be taken.
   */
  val startValue: T?,
  /**
   * Optional field indicating who created animator.
   * Defaults to NULL if not specified explicitly.
   */
  val owner: String?
) {

  /**
   * Builder class used to construct immutable [CameraAnimatorOptions] object.
   */
  class Builder<T>(
    /**
     * CameraAnimator targets (values between which interpolation will take place).
     */
    vararg val targets: T
  ) {

    /**
     * Animator start value.
     * If specified explicitly - this value will be used as first target.
     * Otherwise current camera option on animation start will be taken.
     */
    private var startValue: T? = null

    /**
     * Optional field indicating who created animator.
     * Defaults to NULL if not specified explicitly.
     */
    private var owner: String? = null

    /**
     * Animator start value.
     * If specified explicitly - this value will be used as first target.
     * Otherwise current camera option on animation start will be taken.
     */
    fun startValue(startValue: T): Builder<T> = apply { this.startValue = startValue }

    /**
     * Optional field indicating who created animator.
     * Defaults to NULL if not specified explicitly.
     */
    fun owner(owner: String): Builder<T> = apply { this.owner = owner }

    /**
     * Build an actual [CameraAnimatorOptions] object.
     */
    fun build() = CameraAnimatorOptions(targets = targets, startValue = startValue, owner = owner)
  }

  /**
   * Hash code method.
   */
  override fun hashCode(): Int {
    var result = targets.contentHashCode()
    result = 31 * result + (owner?.hashCode() ?: 0)
    result = 31 * result + (startValue?.hashCode() ?: 0)
    return result
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
    val that = other as CameraAnimatorOptions<*>
    if (that.owner != owner) {
      return false
    }
    if (!that.targets.contentEquals(targets)) {
      return false
    }
    if (that.startValue != startValue) {
      return false
    }
    return true
  }

  /**
   * Static methods.
   */
  companion object {
    /**
     * Builder DSL function to create [CameraAnimatorOptions] object.
     */
    inline fun <T> cameraAnimatorOptions(vararg targets: T, block: (Builder<T>.() -> Unit) = {}) =
      Builder(targets = targets).apply(block).build()
  }
}