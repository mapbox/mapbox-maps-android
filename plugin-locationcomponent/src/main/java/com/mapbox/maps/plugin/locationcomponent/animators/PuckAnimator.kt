package com.mapbox.maps.plugin.locationcomponent.animators

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.Choreographer
import android.view.animation.LinearInterpolator
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants
import com.mapbox.maps.plugin.locationcomponent.LocationLayerRenderer
import com.mapbox.maps.threading.AnimationSynchronizer
import com.mapbox.maps.threading.AnimationThreadController

@SuppressLint("Recycle")
internal abstract class PuckAnimator<T>(
  evaluator: TypeEvaluator<T>
) : ValueAnimator(), Choreographer.FrameCallback {

  @VisibleForTesting(otherwise = PRIVATE)
  internal var updateListener: ((T) -> Unit)? = null
  @VisibleForTesting(otherwise = PRIVATE)
  internal var userConfiguredAnimator: ValueAnimator
  protected var locationRenderer: LocationLayerRenderer? = null
  internal open var enabled = false

  protected var choreographerFrameTimeNanos = 0L

  override fun doFrame(frameTimeNanos: Long) {
    // triggered still after Choreographer#doFrame used by animator but frameTimeNanos will be the same
    choreographerFrameTimeNanos = frameTimeNanos
    if (isRunning || userConfiguredAnimator.isRunning) {
      Choreographer.getInstance().postFrameCallback(this)
    }
  }

  open fun updateLayer(fraction: Float, value: T) {
    if (fraction >= 1.0f) {
      AnimationThreadController.postOnAnimatorThread {
        setExpectingPuckAnimator(false)
      }
    }
  }

  init {
    setObjectValues(emptyArray<Any>())
    setEvaluator(evaluator)
    addUpdateListener {
      AnimationThreadController.postOnMainThread {
        @Suppress("UNCHECKED_CAST")
        val updatedValue = it.animatedValue as T
        updateLayer(it.animatedFraction, updatedValue)
        updateListener?.invoke(updatedValue)
      }
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
      AnimationThreadController.postOnAnimatorThread {
        setExpectingPuckAnimator(true)
        start()
      }
    } else {
      options.invoke(userConfiguredAnimator)
      userConfiguredAnimator.setObjectValues(*targets)
      AnimationThreadController.postOnAnimatorThread {
        setExpectingPuckAnimator(true)
        userConfiguredAnimator.start()
      }
    }
  }

  private fun setExpectingPuckAnimator(expecting: Boolean) {
    AnimationSynchronizer.get(locationRenderer)?.let { synchronizer ->
      if (expecting) {
        Choreographer.getInstance().postFrameCallback(this)
      }
      if (this is PuckPositionAnimator) {
        synchronizer.expectingPuckPosition = expecting
      } else if (this is PuckBearingAnimator) {
        synchronizer.expectingPuckBearing = expecting
      }
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
    AnimationThreadController.postOnAnimatorThread {
      if (isRunning) {
        setExpectingPuckAnimator(false)
        cancel()
      }
      if (userConfiguredAnimator.isRunning) {
        setExpectingPuckAnimator(false)
        userConfiguredAnimator.cancel()
      }
    }
  }

  companion object {
    private val DEFAULT_INTERPOLATOR = LinearInterpolator()
  }
}