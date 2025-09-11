package com.mapbox.maps.plugin.animation.animator

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraState
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.logD
import com.mapbox.maps.logE
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.animation.CameraAnimationsPluginImpl
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorType
import com.mapbox.maps.plugin.animation.CameraAnimatorType.ANCHOR
import com.mapbox.maps.plugin.animation.CameraAnimatorType.BEARING
import com.mapbox.maps.plugin.animation.CameraAnimatorType.CENTER
import com.mapbox.maps.plugin.animation.CameraAnimatorType.PADDING
import com.mapbox.maps.plugin.animation.CameraAnimatorType.PITCH
import com.mapbox.maps.plugin.animation.CameraAnimatorType.ZOOM
import com.mapbox.maps.threading.AnimationThreadController.postOnAnimatorThread
import java.util.concurrent.CopyOnWriteArraySet

/**
 * Base generic class for all camera animators.
 */
@OptIn(MapboxExperimental::class)
@SuppressLint("Recycle")
abstract class CameraAnimator<out T>(
  /**
   * [TypeEvaluator] for generic type
   */
  private val evaluator: TypeEvaluator<T>,
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

  private val userUpdateListeners = CopyOnWriteArraySet<AnimatorUpdateListener?>()
  private val userListeners = CopyOnWriteArraySet<AnimatorListener?>()

  internal var canceled = false
  internal var isInternal = false
  internal var skipped = false
  private val immediate get() = duration == 0L && startDelay == 0L

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
   * Update object values if animator is not [skipped], has [targets], start value and can not be skipped.
   *
   * @param getStartCameraState provider for [CameraState]
   */
  fun updateObjectValues(getStartCameraState: () -> CameraState) {
    if (skipped) {
      return
    }
    if (targets.isEmpty()) {
      logE(
        CameraAnimationsPluginImpl.Companion.TAG,
        "Skipped animation ${type.name} with no targets!"
      )
      skipped = true
      return
    }

    val cameraCurrentValue = defaultStartValue(getStartCameraState)
    val resolvedStartValue = startValue ?: cameraCurrentValue
    if (DEBUG_MODE) {
      logD(
        CameraAnimationsPluginImpl.Companion.TAG,
        "Animation ${type.name}(${hashCode()}): automatically setting start value $resolvedStartValue."
      )
    }
    val animationObjectValues = resolveAnimationObjectValues(resolvedStartValue)
    if ((evaluator is CameraTypeEvaluator) &&
      evaluator.canSkip(cameraCurrentValue, resolvedStartValue, animationObjectValues)
    ) {
      skipped = true
      if (DEBUG_MODE) {
        logD(
          CameraAnimationsPluginImpl.Companion.TAG,
          "Animation ${type.name}(${hashCode()}) was skipped."
        )
      }
      return
    }
    setObjectValues(*animationObjectValues)
  }

  /**
   * Provide a default start value for animation in case it is not provided by [cameraAnimatorOptions]
   *
   * @param cameraState current camera state
   */
  private inline fun defaultStartValue(cameraState: () -> CameraState): Any = run {
    when (type) {
      CENTER -> cameraState().center
      ZOOM -> cameraState().zoom
      ANCHOR -> ZERO_SCREEN_COORDINATE
      PADDING -> cameraState().padding
      BEARING -> cameraState().bearing
      PITCH -> cameraState().pitch
    }
  }

  /**
   * Resolve animation object values to provide to [setObjectValues] and animate over
   *
   * @param startValue start value resolved by [cameraAnimatorOptions] or [defaultStartValue]
   */
  open fun resolveAnimationObjectValues(startValue: Any): Array<*> =
    Array(targets.size + 1) { index ->
      if (index == 0) {
        startValue
      } else {
        targets[index - 1]
      }
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
    postOnAnimatorThread {
      if (registered) {
        canceled = false
        if (immediate) {
          handleImmediateAnimation()
          return@postOnAnimatorThread
        }
        super.start()
      } else {
        logW(
          TAG,
          "Animation $type was not registered and will not run. Register it with registerAnimation() method."
        )
      }
    }
  }

  /**
   * The most recent value calculated by this <code>ValueAnimator</code> when there is just one
   * property being animated. This value is only sensible while the animation is running. The main
   * purpose for this read-only property is to retrieve the value from the <code>ValueAnimator</code>
   * during a call to {@link AnimatorUpdateListener#onAnimationUpdate(ValueAnimator)}, which
   * is called during each animation frame, immediately after the value is calculated.
   *
   * @return animatedValue The value most recently calculated by this <code>ValueAnimator</code> for
   * the single property being animated. If there are several properties being animated
   * (specified by several PropertyValuesHolder objects in the constructor), this function
   * returns the animated value for the first of those objects.
   */
  override fun getAnimatedValue(): Any {
    // For immediate animations the returned ValueAnimator.getAnimatedValue() will be null, in this case, we should return the
    // last configured target value, so we immediately jump to the target value.
    if (immediate) {
      return targets.last() as Any
    }
    return super.getAnimatedValue()
  }

  /**
   * @throws UnsupportedOperationException if this animator does not support this method
   */
  internal fun getAnimatedValueAt(fraction: Float, startCameraState: CameraState? = null): T {
    if (targets.size != 1) {
      throw UnsupportedOperationException(
        "getAnimatedValueAt() is only supported for single target animations."
      )
    }
    if (startValue == null && startCameraState == null) {
      throw UnsupportedOperationException(
        "getAnimatedValueAt() is only supported for animators with a startValue " +
          "or a non-null current camera state must be provided."
      )
    }
    val resolvedStartValue =
      startValue ?: startCameraState?.let { defaultStartValue { startCameraState } } as? T
    if (resolvedStartValue == null) {
      throw UnsupportedOperationException("Could not resolve start value for animator")
    }
    val interpolation = interpolator.getInterpolation(fraction)
    return evaluator.evaluate(interpolation, resolvedStartValue, targets.last())
  }

  /**
   * Handle immediate animation(when duration and startDelay of the animation is 0)
   * we trigger animator listeners directly without triggering [ValueAnimator]'s start().
   *
   * This brings the following benefits:
   *  1. Saves resources by not starting the whole Android SDK animation framing.
   *  2. Has consistent behaviour on all supported APIs as on API <= 23 Android SDK Animator logic
   *  is different and has an explicit Handler.post which makes code unpredictable and brings in race conditions.
   */
  private fun handleImmediateAnimation() {
    // bypass the ValueAnimator and emit the user registered AnimatorListeners and AnimatorUpdateListeners immediately
    // in the same callchain (if not using AnimationThreadController with background thread)
    listeners.toList().let { listeners ->
      listeners.forEach {
        it.onAnimationStart(this)
      }
      internalUpdateListener?.onAnimationUpdate(this)
      userUpdateListeners.forEach {
        it?.onAnimationUpdate(this)
      }
      listeners.forEach {
        it.onAnimationEnd(this)
      }
    }
  }

  /**
   * Add an animator listener
   *
   * @param listener the listener to be invoked when animation state changes
   */
  final override fun addListener(listener: AnimatorListener?) {
    postOnAnimatorThread {
      if (internalListener != null) {
        super.addListener(listener)
      }
      userListeners.add(listener)
    }
  }

  /**
   * Add an animator update listener
   *
   * @param listener the listener to be invoked when animation update changes
   */
  final override fun addUpdateListener(listener: AnimatorUpdateListener?) {
    postOnAnimatorThread {
      if (internalUpdateListener != null) {
        super.addUpdateListener(listener)
      }
      userUpdateListeners.add(listener)
    }
  }

  /**
   * Remove an animator update listener
   *
   * @param listener the listener to be removed
   */
  final override fun removeUpdateListener(listener: AnimatorUpdateListener?) {
    postOnAnimatorThread {
      if (listener != internalUpdateListener) {
        super.removeUpdateListener(listener)
      }
      if (userUpdateListeners.contains(listener)) {
        userUpdateListeners.remove(listener)
      }
    }
  }

  /**
   * Remove all update listeners
   */
  final override fun removeAllUpdateListeners() {
    postOnAnimatorThread {
      super.removeAllUpdateListeners()
      if (internalUpdateListener != null) {
        super.addUpdateListener(internalUpdateListener)
      }
      userUpdateListeners.clear()
    }
  }

  /**
   * Remove an animator listener
   *
   * @param listener the listener to be removed
   */
  final override fun removeListener(listener: AnimatorListener?) {
    postOnAnimatorThread {
      if (listener != internalListener) {
        super.removeListener(listener)
      }
      if (userListeners.contains(listener)) {
        userListeners.remove(listener)
      }
    }
  }

  /**
   * Remove all animator listeners
   */
  final override fun removeAllListeners() {
    postOnAnimatorThread {
      super.removeAllListeners()
      if (internalListener != null) {
        super.addListener(internalListener)
      }
      userListeners.clear()
    }
  }

  /**
   * Cancels the animation. Unlike end(), cancel() causes the animation to stop in its tracks,
   * sending an Animator.AnimatorListener.onAnimationCancel(Animator) to its listeners,
   * followed by an Animator.AnimatorListener.onAnimationEnd(Animator) message.
   *
   * This method must be called on the thread that is running the animation.
   */
  final override fun cancel() {
    postOnAnimatorThread {
      canceled = true
      super.cancel()
    }
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
    private const val DEBUG_MODE = false
    private val ZERO_SCREEN_COORDINATE = ScreenCoordinate(0.0, 0.0)
  }
}