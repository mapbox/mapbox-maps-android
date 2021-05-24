package com.mapbox.maps.plugin.animation

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.animation.animator.*
import com.mapbox.maps.plugin.delegates.*
import com.mapbox.maps.toCameraOptions
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.properties.Delegates

/**
 * [CameraAnimationsPluginImpl] is designed to rule all the animations happening to the map driven by changing [CameraOptions].
 * It is responsible for:
 *   - Storing all the [ValueAnimator]'s that could be run. Only specific camera animators could be used in this plugin.
 *   - Controlling animation execution - only one [ValueAnimator] of certain type could be run at a time.
 *     If some another animation with same [CameraAnimator.type] is about to start previous one will be cancelled.
 *   - Giving possibility to listen to [CameraOptions] values changes during animations via listeners.
 *   - If several animations of different [CameraAnimator.type] are running simultaneously map camera
 *     will be updated only on oldest animation update (the oldest is the one that was started first).
 *     That actually means that animation start order matters. High-level animations [flyTo] and [easeTo]
 *     will always trigger camera center animator first.
 *
 * [CameraAnimationsPluginImpl] is NOT thread-safe meaning all animations must be started from one thread.
 * However, it doesn't have to be the UI thread.
 */

internal class CameraAnimationsPluginImpl : CameraAnimationsPlugin {

  @VisibleForTesting(otherwise = PRIVATE)
  internal val animators = hashSetOf<CameraAnimator<*>>()
  private val runningAnimatorsQueue = LinkedHashSet<ValueAnimator>()

  /**
   * Using single [AnimatorSet] for high-level API functions like easeTo, flyTo etc
   * Only one high-level animator set could be active in a moment of time.
   * Consists of set owner + animator set itself.
   */
  private var highLevelAnimatorSet: HighLevelAnimatorSet? = null
  @VisibleForTesting(otherwise = PRIVATE)
  internal var highLevelListener: Animator.AnimatorListener? = null

  private val centerListeners = CopyOnWriteArraySet<CameraAnimatorChangeListener<Point>>()
  private val zoomListeners = CopyOnWriteArraySet<CameraAnimatorChangeListener<Double>>()
  private val paddingListeners = CopyOnWriteArraySet<CameraAnimatorChangeListener<EdgeInsets>>()
  private val anchorListeners =
    CopyOnWriteArraySet<CameraAnimatorNullableChangeListener<ScreenCoordinate?>>()
  private val bearingListeners = CopyOnWriteArraySet<CameraAnimatorChangeListener<Double>>()
  private val pitchListeners = CopyOnWriteArraySet<CameraAnimatorChangeListener<Double>>()

  private val lifecycleListeners = CopyOnWriteArraySet<CameraAnimationsLifecycleListener>()

  private var center by Delegates.observable<Point?>(null) { _, old, new ->
    new?.let {
      if (old != it) {
        centerListeners.forEach { listener -> listener.onChanged(it) }
      }
    }
  }

  private var zoom by Delegates.observable<Double?>(null) { _, old, new ->
    new?.let {
      if (old != it) {
        zoomListeners.forEach { listener -> listener.onChanged(it) }
      }
    }
  }

  private var padding by Delegates.observable<EdgeInsets?>(null) { _, old, new ->
    new?.let {
      if (old != it) {
        paddingListeners.forEach { listener -> listener.onChanged(it) }
      }
    }
  }

  override var anchor by Delegates.observable<ScreenCoordinate?>(null) { _, old, new ->
    if (old != new) {
      anchorListeners.forEach { listener -> listener.onChanged(new) }
    }
  }

  private var bearing by Delegates.observable<Double?>(null) { _, old, new ->
    new?.let {
      if (old != it) {
        bearingListeners.forEach { listener -> listener.onChanged(it) }
      }
    }
  }

  private var pitch by Delegates.observable<Double?>(null) { _, old, new ->
    new?.let {
      if (old != it) {
        pitchListeners.forEach { listener -> listener.onChanged(it) }
      }
    }
  }

  private var lastCameraOptions: CameraOptions? = null
  private var cameraOptionsBuilder = CameraOptions.Builder()

  private lateinit var mapDelegateProvider: MapDelegateProvider
  private lateinit var mapCameraManagerDelegate: MapCameraManagerDelegate
  private lateinit var mapTransformDelegate: MapTransformDelegate
  private lateinit var mapProjectionDelegate: MapProjectionDelegate

  /**
   * Factory to provide animators for the default animations like easeTo, scaleBy, moveBy, rotateBy, pitchBy
   */
  lateinit var cameraAnimationsFactory: CameraAnimatorsFactory

  private enum class AnimationFinishStatus {
    CANCELED,
    ENDED
  }

  /**
   * Provides all map delegate instances.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    mapDelegateProvider = delegateProvider
    mapCameraManagerDelegate = mapDelegateProvider.mapCameraManagerDelegate
    mapTransformDelegate = mapDelegateProvider.mapTransformDelegate
    mapProjectionDelegate = mapDelegateProvider.mapProjectionDelegate
    cameraAnimationsFactory = CameraAnimatorsFactory(mapDelegateProvider)
  }

  /**
   * Called when the map is destroyed. Should be used to cleanup plugin resources for that map.
   * Cancel all running animations and cleanup all resources (registered animations, listeners).
   */
  override fun cleanup() {
    unregisterAnimators(*animators.toTypedArray())
    cancelAnimatorSet()
    centerListeners.clear()
    zoomListeners.clear()
    bearingListeners.clear()
    pitchListeners.clear()
    anchorListeners.clear()
    paddingListeners.clear()
    lifecycleListeners.clear()
    animators.clear()
  }

  private fun performMapJump(cameraOptions: CameraOptions) {
    if (lastCameraOptions == cameraOptions) {
      return
    }
    // move native map to new position
    mapCameraManagerDelegate.setCamera(cameraOptions)
    // notify listeners with actual values
    notifyListeners(cameraOptions)
    lastCameraOptions = cameraOptions
  }

  private fun updateAnimatorValues(cameraAnimator: CameraAnimator<*>): Boolean {
    @Suppress("IMPLICIT_CAST_TO_ANY")

    val startValue = cameraAnimator.startValue ?: when (cameraAnimator.type) {
      CameraAnimatorType.CENTER -> mapCameraManagerDelegate.cameraState.center
      CameraAnimatorType.ZOOM -> mapCameraManagerDelegate.cameraState.zoom
      CameraAnimatorType.ANCHOR -> anchor ?: ScreenCoordinate(0.0, 0.0)
      CameraAnimatorType.PADDING -> mapCameraManagerDelegate.cameraState.padding
      CameraAnimatorType.BEARING -> mapCameraManagerDelegate.cameraState.bearing
      CameraAnimatorType.PITCH -> mapCameraManagerDelegate.cameraState.pitch
    }.also {
      Logger.i(
        TAG,
        "Animation ${cameraAnimator.type.name}(${cameraAnimator.hashCode()}): automatically setting start value $it."
      )
    }

    val targets = cameraAnimator.targets
    cameraAnimator.setObjectValues(
      *Array(targets.size + 1) { index ->
        if (index == 0) {
          startValue
        } else {
          targets[index - 1]
        }
      }
    )
    return true
  }

  internal fun notifyListeners(cameraOptions: CameraOptions) {
    cameraOptions.also {
      bearing = it.bearing
      center = it.center
      padding = it.padding
      pitch = it.pitch
      zoom = it.zoom
    }
  }

  private fun updateCameraValue(cameraAnimator: CameraAnimator<*>) {
    when (cameraAnimator) {
      is CameraCenterAnimator -> cameraOptionsBuilder.center(cameraAnimator.animatedValue as? Point)
      is CameraZoomAnimator -> cameraOptionsBuilder.zoom(cameraAnimator.animatedValue as? Double)
      is CameraAnchorAnimator -> cameraOptionsBuilder.anchor(cameraAnimator.animatedValue as? ScreenCoordinate)
      is CameraPaddingAnimator -> cameraOptionsBuilder.padding(cameraAnimator.animatedValue as? EdgeInsets)
      is CameraBearingAnimator -> cameraOptionsBuilder.bearing(cameraAnimator.animatedValue as? Double)
      is CameraPitchAnimator -> cameraOptionsBuilder.pitch(cameraAnimator.animatedValue as? Double)
    }
  }

  private fun correctInitialCamera(
    currentCameraBuilder: CameraOptions.Builder,
    cameraAnimator: CameraAnimator<*>
  ): CameraOptions {
    return when (cameraAnimator) {
      is CameraCenterAnimator -> currentCameraBuilder.center(cameraAnimator.animatedValue as? Point).build()
      is CameraZoomAnimator -> currentCameraBuilder.zoom(cameraAnimator.animatedValue as? Double).build()
      is CameraAnchorAnimator -> currentCameraBuilder.anchor(cameraAnimator.animatedValue as? ScreenCoordinate).build()
      is CameraPaddingAnimator -> currentCameraBuilder.padding(cameraAnimator.animatedValue as? EdgeInsets).build()
      is CameraBearingAnimator -> currentCameraBuilder.bearing(cameraAnimator.animatedValue as? Double).build()
      is CameraPitchAnimator -> currentCameraBuilder.pitch(cameraAnimator.animatedValue as? Double).build()
      else -> throw RuntimeException("Unsupported animator type!")
    }
  }

  private fun registerInternalListener(animator: CameraAnimator<*>) {
    animator.addInternalListener(object : Animator.AnimatorListener {

      override fun onAnimationStart(animation: Animator?) {
        (animation as? CameraAnimator<*>)?.apply {

          if (runningAnimatorsQueue.isEmpty()) {
            highLevelListener?.onAnimationStart(animation)
          }

          lifecycleListeners.forEach {
            it.onAnimatorStarting(type, this, owner)
          }
          mapTransformDelegate.setUserAnimationInProgress(true)

          // check if such animation is not running already
          // if it is - then cancel it

          // Safely iterate over new set because of the possible changes of "this.animators" in Animator callbacks
          HashSet(animators).forEach {
            if (it.type == type && it.isRunning && it != this) {
              lifecycleListeners.forEach { listener ->
                listener.onAnimatorInterrupting(type, it, it.owner, this, this.owner)
              }
              it.cancel()
            }
          }

          // Prepare animator values
          // Some animators might not have initial values and should be skipped from internal update
          val isUpdated = updateAnimatorValues(this)
          if (isUpdated) {
            // finally register update listener in order to update map properly
            registerInternalUpdateListener(this)
            Logger.i(TAG, "Animation ${type.name}(${hashCode()}) started.")
          }
        } ?: throw RuntimeException(
          "Could not start animation in CameraManager! " +
            "Animation must be an instance of CameraAnimator and not null. "
        )
      }

      override fun onAnimationEnd(animation: Animator?) {
        finishAnimation(animation, AnimationFinishStatus.ENDED)
      }

      override fun onAnimationCancel(animation: Animator?) {
        finishAnimation(animation, AnimationFinishStatus.CANCELED)
      }

      override fun onAnimationRepeat(animation: Animator?) {}

      private fun finishAnimation(animation: Animator?, finishStatus: AnimationFinishStatus) {
        (animation as? CameraAnimator<*>)?.apply {
          runningAnimatorsQueue.remove(animation)
          val logText = when (finishStatus) {
            AnimationFinishStatus.CANCELED -> "was canceled."
            AnimationFinishStatus.ENDED -> "ended."
          }
          Logger.i(TAG, "Animation ${type.name}(${hashCode()}) $logText")
          if (isInternal) {
            Logger.i(TAG, "Internal Animator ${type.name} was unregistered")
            unregisterAnimators(this, cancelAnimators = false)
          }
          if (runningAnimatorsQueue.isEmpty()) {
            mapTransformDelegate.setUserAnimationInProgress(false)
          }
          lifecycleListeners.forEach {
            when (finishStatus) {
              AnimationFinishStatus.CANCELED -> it.onAnimatorCancelling(type, this, owner)
              AnimationFinishStatus.ENDED -> it.onAnimatorEnding(type, this, owner)
            }
          }
          if (runningAnimatorsQueue.isEmpty()) {
            when (finishStatus) {
              AnimationFinishStatus.CANCELED -> {
                highLevelListener?.onAnimationCancel(animation)
                highLevelListener?.onAnimationEnd(animation)
              }
              AnimationFinishStatus.ENDED -> highLevelListener?.onAnimationEnd(animation)
            }
          }
        } ?: throw RuntimeException(
          "Could not finish animation in CameraManager! " +
            "Animation must be an instance of CameraAnimator and not null. "
        )
      }
    })
  }

  private fun registerInternalUpdateListener(animator: CameraAnimator<*>) {
    animator.addInternalUpdateListener {
      // set current animator value in any case
      updateCameraValue(animator)
      // main idea here is not to update map on each option change
      // we perform jump based on update tick of first (oldest) animation
      val firstAnimator = if (runningAnimatorsQueue.iterator().hasNext()) {
        runningAnimatorsQueue.iterator().next()
      } else {
        null
      }
      if (animator.type == CameraAnimatorType.ANCHOR) {
        anchor = it.animatedValue as ScreenCoordinate
      }
      val cameraOptions = when {
        // if no running animators in queue - get current map camera but apply first updated value
        firstAnimator == null -> {
          correctInitialCamera(
            mapCameraManagerDelegate.cameraState.toCameraOptions(anchor).toBuilder(),
            animator
          )
        }
        // if update is triggered for first (oldest) animator - build options and jump
        it == firstAnimator -> {
          cameraOptionsBuilder.anchor(anchor).build()
        }
        // do not perform jump if update is triggered for not first (oldest) animator
        else -> {
          null
        }
      }
      cameraOptions?.let { camera ->
        // move map camera
        performMapJump(camera)
        // reset values
        cameraOptionsBuilder = CameraOptions.Builder()
      }
      // add current animator to queue-set if was not present
      runningAnimatorsQueue.add(it)
    }
  }

  private fun cancelAnimatorSet() {
    highLevelAnimatorSet?.let {
      it.animatorSet.cancel()
      it.animatorSet.removeAllListeners()
    }
  }

  /**
   * Register given [ValueAnimator]'s.
   * Such [ValueAnimator]'s must be created with static methods like [CameraAnimationsPlugin.createCenterAnimator] or similar.
   * Only registered animations affect MapboxMap and would not even start otherwise.
   *
   * @param cameraAnimators Variable number of [ValueAnimator]'s
   */
  override fun registerAnimators(
    vararg cameraAnimators: ValueAnimator
  ) {
    for (animator in cameraAnimators) {
      if (animator is CameraAnimator<*>) {
        registerInternalListener(animator)
      } else {
        Logger.e(TAG, "All animators must be CameraAnimator's to be registered!")
        return
      }
    }
    animators.addAll(cameraAnimators.map { it as CameraAnimator<*> })
  }

  /**
   * Unregister given [ValueAnimator]'s.
   *
   * @param cameraAnimators variable number of [ValueAnimator]'s
   * @param cancelAnimators flag to manage animators cancelling
   */
  override fun unregisterAnimators(
    vararg cameraAnimators: ValueAnimator,
    cancelAnimators: Boolean
  ) {
    for (animator in cameraAnimators) {
      if (animator is CameraAnimator<*>) {
        if (cancelAnimators) {
          animator.cancel()
        }
        animator.removeInternalListener()
        animator.removeInternalUpdateListener()
      } else {
        Logger.e(TAG, "All animators must be CameraAnimator's to be unregistered!")
        return
      }
    }
    animators.removeAll(cameraAnimators.map { it as CameraAnimator<*> })
  }

  /**
   * Unregister all previously registered [ValueAnimator]'s.
   */
  fun unregisterAllAnimators() {
    unregisterAnimators(*animators.toTypedArray())
  }

  /**
   * Cancel all animators except ones owned by [exceptOwnerList] list.
   */
  override fun cancelAllAnimators(exceptOwnerList: List<String>) {
    // Safely iterate over new set because of the possible changes of "this.animators" in Animator callbacks
    HashSet(animators).forEach {
      if (!exceptOwnerList.contains(it.owner)) {
        it.cancel()
      }
    }
    if (!exceptOwnerList.contains(highLevelAnimatorSet?.owner)) {
      cancelAnimatorSet()
    }
  }

  // property update listeners

  /**
   * Add [CameraAnimatorChangeListener] to receive map zoom updates.
   *
   * @param listener Instance of [CameraAnimatorChangeListener]
   */
  override fun addCameraZoomChangeListener(listener: CameraAnimatorChangeListener<Double>) {
    zoomListeners.add(listener)
  }

  /**
   * Remove [CameraAnimatorChangeListener]. No updates will arrive after that.
   *
   * @param listener Instance of [CameraAnimatorChangeListener]
   */
  override fun removeCameraZoomChangeListener(listener: CameraAnimatorChangeListener<Double>) {
    zoomListeners.remove(listener)
  }

  /**
   * Add [CameraAnimatorChangeListener] to receive map center updates.
   *
   * @param listener Instance of [CameraAnimatorChangeListener]
   */
  override fun addCameraCenterChangeListener(listener: CameraAnimatorChangeListener<Point>) {
    centerListeners.add(listener)
  }

  /**
   * Remove [CameraAnimatorChangeListener]. No updates will arrive after that.
   *
   * @param listener Instance of [CameraAnimatorChangeListener]
   */
  override fun removeCameraCenterChangeListener(listener: CameraAnimatorChangeListener<Point>) {
    centerListeners.remove(listener)
  }

  /**
   * Add [CameraAnimatorChangeListener] to receive map padding updates.
   *
   * @param listener Instance of [CameraAnimatorChangeListener]
   */
  override fun addCameraPaddingChangeListener(listener: CameraAnimatorChangeListener<EdgeInsets>) {
    paddingListeners.add(listener)
  }

  /**
   * Remove [CameraAnimatorChangeListener]. No updates will arrive after that.
   *
   * @param listener Instance of [CameraAnimatorChangeListener]
   */
  override fun removeCameraPaddingChangeListener(listener: CameraAnimatorChangeListener<EdgeInsets>) {
    paddingListeners.remove(listener)
  }

  /**
   * Add [CameraAnimatorNullableChangeListener] to receive map anchor updates.
   *
   * @param listener Instance of [CameraAnimatorNullableChangeListener]
   */
  override fun addCameraAnchorChangeListener(listener: CameraAnimatorNullableChangeListener<ScreenCoordinate?>) {
    anchorListeners.add(listener)
  }

  /**
   * Remove [CameraAnimatorNullableChangeListener]. No updates will arrive after that.
   *
   * @param listener Instance of [CameraAnimatorNullableChangeListener]
   */
  override fun removeCameraAnchorChangeListener(listener: CameraAnimatorNullableChangeListener<ScreenCoordinate?>) {
    anchorListeners.remove(listener)
  }

  /**
   * Add [CameraAnimatorChangeListener] to receive map bearing updates.
   *
   * @param listener Instance of [CameraAnimatorChangeListener]
   */
  override fun addCameraBearingChangeListener(listener: CameraAnimatorChangeListener<Double>) {
    bearingListeners.add(listener)
  }

  /**
   * Remove [CameraAnimatorChangeListener]. No updates will arrive after that.
   *
   * @param listener Instance of [CameraAnimatorChangeListener]
   */
  override fun removeCameraBearingChangeListener(listener: CameraAnimatorChangeListener<Double>) {
    bearingListeners.remove(listener)
  }

  /**
   * Add [CameraAnimatorChangeListener] to receive map pitch updates.
   *
   * @param listener Instance of [CameraAnimatorChangeListener]
   */
  override fun addCameraPitchChangeListener(listener: CameraAnimatorChangeListener<Double>) {
    pitchListeners.add(listener)
  }

  /**
   * Remove [CameraAnimatorChangeListener]. No updates will arrive after that.
   *
   * @param listener Instance of [CameraAnimatorChangeListener]
   */
  override fun removeCameraPitchChangeListener(listener: CameraAnimatorChangeListener<Double>) {
    pitchListeners.remove(listener)
  }

  /**
   * Add given [CameraAnimationsLifecycleListener] for capturing all events about animators lifecycle.
   *
   */
  override fun addCameraAnimationsLifecycleListener(listener: CameraAnimationsLifecycleListener) {
    lifecycleListeners.add(listener)
  }

  /**
   * Remove given [CameraAnimationsLifecycleListener] for capturing all events about animators lifecycle.
   *
   */
  override fun removeCameraAnimationsLifecycleListener(listener: CameraAnimationsLifecycleListener) {
    lifecycleListeners.remove(listener)
  }

  /**
   * Ease the map camera to a given camera options and animation options.
   *
   * @param cameraOptions The camera options to ease to
   * @param animationOptions Transition options (animation duration, listeners etc)
   *
   * @return [Cancelable] animator set object.
   */
  override fun easeTo(
    cameraOptions: CameraOptions,
    animationOptions: MapAnimationOptions?
  ) = startHighLevelAnimation(
    cameraAnimationsFactory.getEaseTo(cameraOptions),
    animationOptions
  )

  /**
   * Move the map by a given screen coordinate with optional animation.
   *
   * @param screenCoordinate The screen coordinate distance to move by
   * @param animationOptions Transition options (animation duration, listeners etc)
   *
   * @return [Cancelable] animator set object.
   */
  override fun moveBy(
    screenCoordinate: ScreenCoordinate,
    animationOptions: MapAnimationOptions?
  ) = startHighLevelAnimation(
    cameraAnimationsFactory.getMoveBy(screenCoordinate),
    animationOptions
  )

  /**
   * Scale the map by with optional animation.
   *
   * @param amount The amount to scale by
   * @param screenCoordinate The optional focal point to scale on
   * @param animationOptions Transition options (animation duration, listeners etc)
   *
   * @return [Cancelable] animator set object.
   */
  override fun scaleBy(
    amount: Double,
    screenCoordinate: ScreenCoordinate?,
    animationOptions: MapAnimationOptions?
  ) = startHighLevelAnimation(
    cameraAnimationsFactory.getScaleBy(amount, screenCoordinate),
    animationOptions
  )

  /**
   * Calculate target zoom by applying scale
   *
   * @param amount The amount to scale by
   * @param currentZoom The current zoom value
   *
   * @return [Cancelable] animator set object.
   */
  override fun calculateScaleBy(amount: Double, currentZoom: Double): Double =
    CameraTransform.calculateScaleBy(amount, currentZoom)

  /**
   * Rotate the map by with optional animation.
   *
   * @param first The first pointer to rotate on
   * @param second The second pointer to rotate on
   * @param animationOptions Transition options (animation duration, listeners etc)
   *
   * @return [Cancelable] animator set object.
   */
  override fun rotateBy(
    first: ScreenCoordinate,
    second: ScreenCoordinate,
    animationOptions: MapAnimationOptions?
  ) = startHighLevelAnimation(
    cameraAnimationsFactory.getRotateBy(first, second),
    animationOptions
  )

  /**
   * Pitch the map by with optional animation.
   *
   * @param pitch The amount to pitch by
   * @param animationOptions Transition options (animation duration, listeners etc)
   *
   * @return [Cancelable] animator set object.
   */
  override fun pitchBy(
    pitch: Double,
    animationOptions: MapAnimationOptions?
  ) = startHighLevelAnimation(
    cameraAnimationsFactory.getPitchBy(pitch),
    animationOptions
  )

  /**
   * Fly the map camera to a given camera options.
   *
   * This method implements an “optimal path” animation, as detailed in:
   * Van Wijk, Jarke J.; Nuij, Wim A. A. “Smooth and efficient zooming and
   * panning.” INFOVIS ’03. pp. 15–22.
   * [The online documentation](https://www.win.tue.nl/~vanwijk/zoompan.pdf#page=5).
   * Where applicable, local variable documentation begins with the associated
   * variable or function in van Wijk (2003).
   *
   * @param cameraOptions The camera options to fly to
   * @param animationOptions Transition options (animation duration, listeners etc)
   *
   * @return [Cancelable] animator set object.
   */
  override fun flyTo(
    cameraOptions: CameraOptions,
    animationOptions: MapAnimationOptions?
  ) = startHighLevelAnimation(
    cameraAnimationsFactory.getFlyTo(cameraOptions),
    animationOptions
  )

  override fun createZoomAnimator(
    options: CameraAnimatorOptions<Double>,
    block: (ValueAnimator.() -> Unit)?
  ) = CameraZoomAnimator(options, block)

  override fun createAnchorAnimator(
    options: CameraAnimatorOptions<ScreenCoordinate>,
    block: (ValueAnimator.() -> Unit)?
  ) = CameraAnchorAnimator(options, block)

  override fun createBearingAnimator(
    options: CameraAnimatorOptions<Double>,
    block: (ValueAnimator.() -> Unit)?
  ) = CameraBearingAnimator(options, block)

  override fun createPitchAnimator(
    options: CameraAnimatorOptions<Double>,
    block: (ValueAnimator.() -> Unit)?
  ) = CameraPitchAnimator(options, block)

  override fun createPaddingAnimator(
    options: CameraAnimatorOptions<EdgeInsets>,
    block: (ValueAnimator.() -> Unit)?
  ) = CameraPaddingAnimator(options, block)

  override fun createCenterAnimator(
    options: CameraAnimatorOptions<Point>,
    block: (ValueAnimator.() -> Unit)?
  ) = CameraCenterAnimator(options, block)

  /**
   * Play given [ValueAnimator]'s together
   *
   * @param animators Variable number of [ValueAnimator]'s
   */
  override fun playAnimatorsTogether(vararg animators: ValueAnimator) {
    val cameraAnimators = mutableListOf<CameraAnimator<*>>()
    for (cameraAnimator in animators) {
      if (cameraAnimator is CameraAnimator<*>) {
        cameraAnimator.isInternal = true
        cameraAnimator.owner = MapAnimationOwnerRegistry.INTERNAL
        cameraAnimators.add(cameraAnimator)
      } else {
        Logger.e(TAG, "All animators must be CameraAnimator's to be played together!")
      }
    }
    registerAnimators(*cameraAnimators.toTypedArray())
    AnimatorSet().apply {
      playTogether(*cameraAnimators.toTypedArray())
      start()
    }
  }

  /**
   * Play given [ValueAnimator]'s sequentially
   *
   * @param animators Variable number of [ValueAnimator]'s
   */
  override fun playAnimatorsSequentially(vararg animators: ValueAnimator) {
    val cameraAnimators = mutableListOf<CameraAnimator<*>>()
    for (cameraAnimator in animators) {
      if (cameraAnimator is CameraAnimator<*>) {
        cameraAnimator.isInternal = true
        cameraAnimator.owner = MapAnimationOwnerRegistry.INTERNAL
        cameraAnimators.add(cameraAnimator)
      } else {
        Logger.e(TAG, "All animators must be CameraAnimator's to be played sequentially!")
      }
    }
    registerAnimators(*cameraAnimators.toTypedArray())
    AnimatorSet().apply {
      playSequentially(*cameraAnimators.toTypedArray())
      start()
    }
  }

  private fun startHighLevelAnimation(
    animators: Array<CameraAnimator<*>>,
    animationOptions: MapAnimationOptions?
  ): Cancelable {
    animators.forEach {
      it.isInternal = true
      it.owner = animationOptions?.owner
    }
    cancelAnimatorSet()
    registerAnimators(*animators)
    val animatorSet = AnimatorSet().apply {
      animationOptions?.duration?.let {
        duration = it
      }
      animationOptions?.interpolator?.let {
        interpolator = it
      }
      animationOptions?.animatorListener?.let {
        // duration == 0L does not behave the same as > 0 and actually
        // our custom `highLevelListener` designed for proper notifying of lifecycle
        // based on `runningAnimatorsQueue` is not working because animators end immediately.
        // We use standard listener in that corner case.
        highLevelListener = if (animationOptions.duration == 0L) {
          addListener(it)
          null
        } else {
          it
        }
      }
      playTogether(*animators)
    }
    return HighLevelAnimatorSet(animationOptions?.owner, animatorSet).also {
      highLevelAnimatorSet = it
      it.animatorSet.start()
    }
  }

  /**
   * Static variables and methods.
   */
  companion object {
    private const val TAG = "Mbgl-CameraManager"
  }
}

/**
 * Extension val for MapView to get the Camera Animations plugin instance.
 */
val MapPluginProviderDelegate.camera: CameraAnimationsPlugin
  get() = this.getPlugin(CameraAnimationsPluginImpl::class.java)!!

/**
 * Extension easeTo() for [MapPluginExtensionsDelegate]
 * Ease the map camera to a given camera options and animation options.
 *
 * @param cameraOptions The camera options to ease to
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or null if associated map object was garbage collected.
 */
fun MapPluginExtensionsDelegate.easeTo(
  cameraOptions: CameraOptions,
  animationOptions: MapAnimationOptions? = null
) = cameraAnimationsPlugin { easeTo(cameraOptions, animationOptions) } as Cancelable

/**
 * Extension flyTo() function for [MapPluginExtensionsDelegate]
 * Fly the map camera to a given camera options.
 *
 * @param cameraOptions The camera options to fly to
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or null if associated map object was garbage collected.
 */
fun MapPluginExtensionsDelegate.flyTo(
  cameraOptions: CameraOptions,
  animationOptions: MapAnimationOptions? = null
) = cameraAnimationsPlugin { flyTo(cameraOptions, animationOptions) } as Cancelable

/**
 * Extension pitchBy() function for [MapPluginExtensionsDelegate]
 * Pitch the map by with optional animation.
 *
 * @param pitch The amount to pitch by
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or null if associated map object was garbage collected.
 */
fun MapPluginExtensionsDelegate.pitchBy(
  pitch: Double,
  animationOptions: MapAnimationOptions? = null
) = cameraAnimationsPlugin { pitchBy(pitch, animationOptions) } as Cancelable

/**
 * Extension scaleBy() function for [MapPluginExtensionsDelegate]
 * Scale the map by with optional animation.
 *
 * @param amount The amount to scale by
 * @param screenCoordinate The optional focal point to scale on
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or null if associated map object was garbage collected.
 */
fun MapPluginExtensionsDelegate.scaleBy(
  amount: Double,
  screenCoordinate: ScreenCoordinate?,
  animationOptions: MapAnimationOptions? = null
) = cameraAnimationsPlugin { scaleBy(amount, screenCoordinate, animationOptions) } as Cancelable

/**
 * Extension moveBy() function for [MapPluginExtensionsDelegate]
 * Move the map by a given screen coordinate with optional animation.
 *
 * @param screenCoordinate The screen coordinate distance to move by
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or null if associated map object was garbage collected.
 */
fun MapPluginExtensionsDelegate.moveBy(
  screenCoordinate: ScreenCoordinate,
  animationOptions: MapAnimationOptions? = null
) = cameraAnimationsPlugin { moveBy(screenCoordinate, animationOptions) } as Cancelable

/**
 * Extension rotateBy() function for [MapPluginExtensionsDelegate]
 * Rotate the map by with optional animation.
 *
 * @param first The first pointer to rotate on
 * @param second The second pointer to rotate on
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or null if associated map object was garbage collected.
 */
fun MapPluginExtensionsDelegate.rotateBy(
  first: ScreenCoordinate,
  second: ScreenCoordinate,
  animationOptions: MapAnimationOptions? = null
) = cameraAnimationsPlugin { rotateBy(first, second, animationOptions) } as Cancelable