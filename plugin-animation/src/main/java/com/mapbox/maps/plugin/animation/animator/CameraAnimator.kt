package com.mapbox.maps.plugin.animation.animator

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import com.mapbox.common.Logger
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorType

/**
 * Base generic class for all camera animators.
 */
abstract class CameraAnimator<out T> (
  /**
   * [TypeEvaluator] for generic type
   */
  evaluator: TypeEvaluator<T>,
  /**
   * Camera animator options.
   */
  cameraAnimatorOptions: CameraAnimatorOptions<out T>
) : ValueAnimator() {

  /**
   * Animator owner or creator.
   */
  var owner = cameraAnimatorOptions.owner
    internal set
  /**
   * Start animation value, will use current map value option from [CameraOptions] if null.
   */
  val startValue = cameraAnimatorOptions.startValue
  /**
   * Sets the values to animate between for this animation (except start value).
   */
  val targets = cameraAnimatorOptions.targets

  private var registered = false
  private var internalUpdateListener: AnimatorUpdateListener? = null
  private var internalListener: AnimatorListener? = null

  private val userUpdateListeners = mutableListOf<AnimatorUpdateListener?>()
  private val userListeners = mutableListOf<AnimatorListener?>()

  internal var canceled = false
  internal var isInternal = false

  init {
    setObjectValues(targets[0], targets[0])
    setEvaluator(evaluator)
  }

  /**
   * The type of CameraAnimator
   */
  abstract val type: CameraAnimatorType

  /**
   * Set the animator object values
   *
   * @param values varags of object values
   */
  final override fun setObjectValues(vararg values: Any?) {
    super.setObjectValues(*values)
  }

  /**
   * Set the animator evaluator
   *
   * @param value the animator type evaluator
   */
  final override fun setEvaluator(value: TypeEvaluator<*>?) {
    super.setEvaluator(value)
  }

  /**
   * Start the animator
   */
  final override fun start() {
    if (registered) {
      canceled = false
      super.start()
    } else {
      Logger.w(
        TAG,
        "Animation $type was not registered and will not run. Register it with registerAnimation() method."
      )
    }
  }

  /**
   * Add an animator listener
   *
   * @param listener the listener to be invoked when animation state changes
   */
  final override fun addListener(listener: AnimatorListener?) {
    if (internalListener != null) {
      super.addListener(listener)
    }
    userListeners.add(listener)
  }

  /**
   * Add an animator update listener
   *
   * @param listener the listener to be invoked when animation update changes
   */
  final override fun addUpdateListener(listener: AnimatorUpdateListener?) {
    if (internalUpdateListener != null) {
      super.addUpdateListener(listener)
    }
    userUpdateListeners.add(listener)
  }

  /**
   * Remove an animator update listener
   *
   * @param listener the listener to be removed
   */
  final override fun removeUpdateListener(listener: AnimatorUpdateListener?) {
    if (listener != internalUpdateListener) {
      super.removeUpdateListener(listener)
    }
    if (userUpdateListeners.contains(listener)) {
      userUpdateListeners.remove(listener)
    }
  }

  /**
   * Remove all update listeners
   */
  final override fun removeAllUpdateListeners() {
    super.removeAllUpdateListeners()
    if (internalUpdateListener != null) {
      super.addUpdateListener(internalUpdateListener)
    }
    userUpdateListeners.clear()
  }

  /**
   * Remove an animator listener
   *
   * @param listener the listener to be removed
   */
  final override fun removeListener(listener: AnimatorListener?) {
    if (listener != internalListener) {
      super.removeListener(listener)
    }
    if (userListeners.contains(listener)) {
      userListeners.remove(listener)
    }
  }

  /**
   * Remove all animator listeners
   */
  final override fun removeAllListeners() {
    super.removeAllListeners()
    if (internalListener != null) {
      super.addListener(internalListener)
    }
    userListeners.clear()
  }

  /**
   * Cancels the animation. Unlike end(), cancel() causes the animation to stop in its tracks,
   * sending an Animator.AnimatorListener.onAnimationCancel(Animator) to its listeners,
   * followed by an Animator.AnimatorListener.onAnimationEnd(Animator) message.
   *
   * This method must be called on the thread that is running the animation.
   */
  final override fun cancel() {
    canceled = true
    super.cancel()
  }

  /**
   * true if CameraAnimator have any external listeners registered.
   */
  internal val hasUserListeners: Boolean
    get() = userUpdateListeners.isNotEmpty()

  internal fun addInternalUpdateListener(listener: AnimatorUpdateListener) {
    super.removeAllUpdateListeners()
    internalUpdateListener = listener
    super.addUpdateListener(internalUpdateListener)
    userUpdateListeners.forEach {
      super.addUpdateListener(it)
    }
  }

  internal fun removeInternalUpdateListener() {
    super.removeUpdateListener(internalUpdateListener)
    internalUpdateListener = null
  }

  internal fun addInternalListener(listener: AnimatorListener) {
    super.removeAllListeners()
    registered = true
    internalListener = listener
    super.addListener(internalListener)
    userListeners.forEach {
      super.addListener(it)
    }
  }

  internal fun removeInternalListener() {
    super.removeListener(internalListener)
    internalListener = null
    registered = false
  }

  internal fun getTargetValues(): Array<out T> {
    return targets
  }

  /**
   * Static variables and methods.
   */
  companion object {
    private const val TAG = "Mbgl-CameraAnimator"
  }
}