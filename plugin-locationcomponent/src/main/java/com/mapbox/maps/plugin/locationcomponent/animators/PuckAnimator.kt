package com.mapbox.maps.plugin.locationcomponent.animators

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.animation.LinearInterpolator
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants
import com.mapbox.maps.plugin.locationcomponent.LocationLayerRenderer

@SuppressLint("Recycle")
internal abstract class PuckAnimator<T>(
  evaluator: TypeEvaluator<T>
) : ValueAnimator() {

  @VisibleForTesting(otherwise = PRIVATE)
  internal var updateListener: ((T) -> Unit)? = null
  @VisibleForTesting(otherwise = PRIVATE)
  internal var userConfiguredAnimator: ValueAnimator
  protected var locationRenderer: LocationLayerRenderer? = null
  internal open var enabled = false

  abstract fun updateLayer(fraction: Float, value: T)

  init {
    setObjectValues(emptyArray<Any>())
    setEvaluator(evaluator)
    addUpdateListener {
      @Suppress("UNCHECKED_CAST")
      val updatedValue = it.animatedValue as T
      updateLayer(it.animatedFraction, updatedValue)
      updateListener?.invoke(updatedValue)
    }
    duration = LocationComponentConstants.DEFAULT_INTERVAL_MILLIS
    interpolator = DEFAULT_INTERPOLATOR
    userConfiguredAnimator = clone()
  }

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
   * Adds a listener to the set of listeners that are sent update events through the life of
   * an animation. This method is called on all listeners for every frame of the animation,
   * after the values for the animation have been calculated.
   *
   * @param listener the listener to be added to the current set of listeners for this animation.
   */
  final override fun addUpdateListener(listener: AnimatorUpdateListener?) {
    super.addUpdateListener(listener)
  }

  final override fun clone(): ValueAnimator {
    return super.clone()
  }

  fun setUpdateListener(updateListener: ((T) -> Unit)) {
    if (this.updateListener != updateListener) {
      this.updateListener = updateListener
    }
  }

  fun setLocationLayerRenderer(renderer: LocationLayerRenderer) {
    locationRenderer = renderer
  }

  fun animate(
    vararg targets: T,
    options: (ValueAnimator.() -> Unit)? = null
  ) {
    cancelRunning()
    if (options == null) {
      setObjectValues(*targets)
      start()
    } else {
      options.invoke(userConfiguredAnimator)
      userConfiguredAnimator.setObjectValues(*targets)
      userConfiguredAnimator.start()
    }
  }

  fun updateOptions(block: ValueAnimator.() -> Unit) {
    if (isRunning) {
      addListener(object : AnimatorListenerAdapter() {

        override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
          super.onAnimationEnd(animation, isReverse)
          block.invoke(this@PuckAnimator)
          removeListener(this)
        }
      })
    } else {
      block.invoke(this@PuckAnimator)
    }
  }

  fun cancelRunning() {
    if (isRunning) {
      cancel()
    }
    if (userConfiguredAnimator.isRunning) {
      userConfiguredAnimator.cancel()
    }
  }

  companion object {
    private val DEFAULT_INTERPOLATOR = LinearInterpolator()
  }
}