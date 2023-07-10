package com.mapbox.maps.plugin.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.mapbox.common.Cancelable
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.plugin.MapCameraPlugin
import com.mapbox.maps.plugin.animation.animator.*
import com.mapbox.maps.plugin.delegates.*
import com.mapbox.maps.threading.AnimationThreadController.postOnAnimatorThread
import com.mapbox.maps.threading.AnimationThreadController.postOnMainThread
import com.mapbox.maps.threading.AnimationThreadController.usingBackgroundThread
import com.mapbox.maps.util.MathUtils
import java.util.*
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

@OptIn(MapboxExperimental::class)
internal class CameraAnimationsPluginImpl : CameraAnimationsPlugin, MapCameraPlugin {

  @VisibleForTesting(otherwise = PRIVATE)
  internal val animators = hashSetOf<CameraAnimator<*>>()
  private val runningAnimatorsQueue = LinkedHashSet<ValueAnimator>()

  /**
   * Using single [AnimatorSet] for high-level API functions like easeTo, flyTo etc
   * Only one high-level animator set could be active in a moment of time.
   * Consists of set owner + animator set itself.
   */
  private var highLevelAnimatorSet: HighLevelAnimatorSet? = null

  private val centerListeners = CopyOnWriteArraySet<CameraAnimatorChangeListener<Point>>()
  private val zoomListeners = CopyOnWriteArraySet<CameraAnimatorChangeListener<Double>>()
  private val paddingListeners = CopyOnWriteArraySet<CameraAnimatorChangeListener<EdgeInsets>>()
  private val anchorListeners =
    CopyOnWriteArraySet<CameraAnimatorNullableChangeListener<ScreenCoordinate?>>()
  private val bearingListeners = CopyOnWriteArraySet<CameraAnimatorChangeListener<Double>>()
  private val pitchListeners = CopyOnWriteArraySet<CameraAnimatorChangeListener<Double>>()

  private val lifecycleListeners = CopyOnWriteArraySet<CameraAnimationsLifecycleListener>()

  /**
   * If debug mode is enabled extra logs will be written about animation lifecycle and
   * some other events that may be useful for debugging.
   */
  override var debugMode: Boolean = false

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

  /**
   * Map camera anchor value.
   * Default value is NULL meaning center of given map view.
   * Left-top corner is represented as [ScreenCoordinate] (0.0, 0.0).
   *
   * If [anchor] is set to some specific value (set directly or by some running anchor animation)
   * it will be used as anchor for all upcoming animations even if they do not animate anchor directly.
   *
   * **Note**: If anchor animator is started and no start value is specified explicitly
   * and [anchor] = NULL - then start value will be set to ScreenCoordinate(0.0, 0.0) automatically
   * and it will be start point for interpolation.
   */
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
   * Called whenever camera position changes.
   *
   * @param lat latitude
   * @param lon longitude
   * @param zoom zoom
   * @param pitch pitch in degrees
   * @param bearing bearing in degrees
   * @param padding padding ordered as [left, top, right, bottom]
   */
  override fun onCameraMove(
    lat: Double,
    lon: Double,
    zoom: Double,
    pitch: Double,
    bearing: Double,
    padding: Array<Double>
  ) {
    this.bearing = bearing
    this.center = Point.fromLngLat(lon, lat)
    // Insets array order : [left, top, right, bottom]
    this.padding = EdgeInsets(
      /* top = */
      padding[1],
      /* left = */
      padding[0],
      /* bottom = */
      padding[3],
      /* right = */
      padding[2],
    )
    this.pitch = pitch
    this.zoom = zoom
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

  /*
   * CameraOptions that are already applied should be skipped.
   */
  private fun skipMapJump(cameraOptions: CameraOptions): Boolean {
    if (cameraOptions.isEmpty) {
      return true
    }
    if (cameraOptions.anchor != null) {
      return false
    }
    cameraOptions.pitch?.let { userPitch ->
      // use-case when pitch >= 60 and terrain enabled might result in some optimizations
      // in gl-native and different result camera state - we check just for the pitch as
      // checking for terrain enabled requires having the style object and more complex code
      if (userPitch >= 60.0 || userPitch != pitch) return false
    }
    if (cameraOptions.zoom != null && cameraOptions.zoom != zoom) {
      return false
    }
    if (cameraOptions.bearing != null && cameraOptions.bearing != bearing) {
      return false
    }
    if (cameraOptions.center != null && cameraOptions.center != center) {
      return false
    }
    if (cameraOptions.padding != null && cameraOptions.padding != padding) {
      return false
    }
    return true
  }

  @VisibleForTesting(otherwise = PRIVATE)
  internal fun performMapJump(cameraOptions: CameraOptions) {
    if (skipMapJump(cameraOptions)) {
      if (debugMode) {
        logI(
          TAG,
          "Setting $cameraOptions to core was skipped due to optimization."
        )
      }
      return
    }
    // move native map to new position
    try {
      // setCamera triggers OnCameraMove that updates camera options and notifies listeners
      mapCameraManagerDelegate.setCamera(cameraOptions)
    } catch (e: Exception) {
      logE(
        TAG,
        "Exception while setting camera options : ${e.message} CameraOptions = $cameraOptions"
      )
    }
  }

  // Returns true if values were applied to animator, false if animation was skipped.
  private fun updateAnimatorValues(cameraAnimator: CameraAnimator<*>): Boolean {
    if (cameraAnimator.targets.isEmpty()) {
      logE(
        TAG,
        "Skipped animation ${cameraAnimator.type.name} with no targets!"
      )
      return false
    }
    val startValue = cameraAnimator.startValue ?: when (cameraAnimator.type) {
      CameraAnimatorType.CENTER -> center ?: mapCameraManagerDelegate.cameraState.center
      CameraAnimatorType.ZOOM -> zoom ?: mapCameraManagerDelegate.cameraState.zoom
      CameraAnimatorType.ANCHOR -> anchor ?: ScreenCoordinate(0.0, 0.0)
      CameraAnimatorType.PADDING -> padding ?: mapCameraManagerDelegate.cameraState.padding
      CameraAnimatorType.BEARING -> bearing ?: mapCameraManagerDelegate.cameraState.bearing
      CameraAnimatorType.PITCH -> pitch ?: mapCameraManagerDelegate.cameraState.pitch
    }.also {
      if (debugMode) {
        logI(
          TAG,
          "Animation ${cameraAnimator.type.name}(${cameraAnimator.hashCode()}): automatically setting start value $it."
        )
      }
    }
    val animationObjectValues = if (cameraAnimator is CameraBearingAnimator && cameraAnimator.useShortestPath) {
      MathUtils.prepareOptimalBearingPath(
        DoubleArray(cameraAnimator.targets.size + 1) { index ->
          if (index == 0) {
            startValue as Double
          } else {
            cameraAnimator.targets[index - 1]
          }
        }
      ).toTypedArray()
    } else {
      Array(cameraAnimator.targets.size + 1) { index ->
        if (index == 0) {
          startValue
        } else {
          cameraAnimator.targets[index - 1]
        }
      }
    }
    if (skipRedundantAnimator(animationObjectValues, cameraAnimator.type)) {
      if (debugMode) {
        logI(
          TAG,
          "Animation ${cameraAnimator.type.name}(${cameraAnimator.hashCode()}) was skipped."
        )
      }
      return false
    }
    cameraAnimator.setObjectValues(*animationObjectValues)
    return true
  }

  private fun skipRedundantAnimator(animationObjectValues: Array<out Any?>, type: CameraAnimatorType) = when (type) {
    CameraAnimatorType.ANCHOR -> false // anchor animations are never skipped
    CameraAnimatorType.CENTER -> animationObjectValues.all { Objects.equals(center, it) }
    CameraAnimatorType.ZOOM -> animationObjectValues.all { Objects.equals(zoom, it) }
    CameraAnimatorType.PADDING -> animationObjectValues.all { Objects.equals(padding, it) }
    CameraAnimatorType.BEARING -> animationObjectValues.all { Objects.equals(bearing, it) }
    CameraAnimatorType.PITCH -> animationObjectValues.all { Objects.equals(pitch, it) }
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

  private fun registerInternalListener(animator: CameraAnimator<*>) {
    postOnAnimatorThread {
      animator.addInternalListener(object : Animator.AnimatorListener {

        override fun onAnimationStart(animation: Animator) {
          // Hack needed to register update listener earlier for 0 duration animators
          // when using background thread. Due to series of rescheduling in this case and
          // taking into account that Android will trigger `onAnimationEnd` in the same callchain -
          // update logic will simply get skipped resulting in no map movement.
          if (usingBackgroundThread && animation.duration == 0L) {
            registerInternalUpdateListener(animation as CameraAnimator<*>)
          }
          postOnMainThread {
            onAnimationStartInternal(animation)
          }
        }

        private fun onAnimationStartInternal(animation: Animator) {
          (animation as? CameraAnimator<*>)?.let { startingAnimator ->
            // check for a specific use-case when canceling an animation with start delay that
            // has not yet started - in that case onAnimationStart logic must be skipped
            if (startingAnimator.canceled) {
              return
            }
            if (!updateAnimatorValues(startingAnimator)) {
              // animation was skipped - camera values are already applied
              startingAnimator.skipped = true
              return
            }
            lifecycleListeners.forEach {
              it.onAnimatorStarting(startingAnimator.type, startingAnimator, startingAnimator.owner)
            }
            mapTransformDelegate.setUserAnimationInProgress(true)
            // check if such animation is not running already
            // if it is - then cancel it
            // Safely iterate over new set because of the possible changes of "this.animators" in Animator callbacks
            HashSet(animators).forEach { existingAnimator ->
              if (existingAnimator.type == startingAnimator.type && existingAnimator.isRunning && existingAnimator != startingAnimator) {
                lifecycleListeners.forEach { listener ->
                  listener.onAnimatorInterrupting(
                    startingAnimator.type,
                    existingAnimator,
                    existingAnimator.owner,
                    startingAnimator,
                    startingAnimator.owner
                  )
                }
                postOnAnimatorThread {
                  existingAnimator.cancel()
                }
              }
            }
            // finally register update listener in order to update map properly -
            // if it's not specific use-case using 0-duration animations with background thread
            if (!usingBackgroundThread || startingAnimator.duration != 0L) {
              registerInternalUpdateListener(startingAnimator)
            }
            if (debugMode) {
              logI(
                TAG,
                "Animation ${startingAnimator.type.name}(${startingAnimator.hashCode()}) started."
              )
            }
          } ?: throw MapboxCameraAnimationException(
            "Could not start animation as it must be an instance of CameraAnimator and not null!"
          )
        }

        override fun onAnimationEnd(animation: Animator) {
          postOnMainThread { finishAnimation(animation, AnimationFinishStatus.ENDED) }
        }

        override fun onAnimationCancel(animation: Animator) {
          postOnMainThread { finishAnimation(animation, AnimationFinishStatus.CANCELED) }
        }

        override fun onAnimationRepeat(animation: Animator) {}

        private fun finishAnimation(animation: Animator, finishStatus: AnimationFinishStatus) {
          (animation as? CameraAnimator<*>)?.apply {
            if (skipped) {
              return
            }
            runningAnimatorsQueue.remove(animation)
            if (debugMode) {
              val logText = when (finishStatus) {
                AnimationFinishStatus.CANCELED -> "was canceled."
                AnimationFinishStatus.ENDED -> "ended."
              }
              logI(TAG, "Animation ${type.name}(${hashCode()}) $logText")
            }
            if (isInternal) {
              if (debugMode) {
                logI(TAG, "Internal Animator ${type.name}(${hashCode()}) was unregistered")
              }
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
              commitChanges()
            }
          } ?: throw MapboxCameraAnimationException(
            "Could not start animation as it must be an instance of CameraAnimator and not null!"
          )
        }
      })
    }
  }

  private fun registerInternalUpdateListener(animator: CameraAnimator<*>) {
    animator.addInternalUpdateListener {
      postOnMainThread { onAnimationUpdateInternal(animator, it) }
    }
  }

  private fun onAnimationUpdateInternal(animator: CameraAnimator<*>, valueAnimator: ValueAnimator) {
    // add current animator to queue-set if was not present
    runningAnimatorsQueue.add(animator)

    // set current animator value in any case
    updateCameraValue(animator)

    if (animator.type == CameraAnimatorType.ANCHOR) {
      anchor = valueAnimator.animatedValue as ScreenCoordinate
    }

    // commit applies changes immediately
    // this helps to avoid camera animations jitter noticeable on high zoom levels using location puck following mode.
    commitChanges()
  }

  private fun commitChanges() {
    performMapJump(cameraOptionsBuilder.anchor(anchor).build())
    // reset values
    cameraOptionsBuilder = CameraOptions.Builder()
  }

  private fun cancelAnimatorSet() {
    highLevelAnimatorSet?.let {
      postOnAnimatorThread {
        it.animatorSet.cancel()
        it.animatorSet.removeAllListeners()
      }
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
    postOnMainThread {
      for (animator in cameraAnimators) {
        if (animator is CameraAnimator<*>) {
          registerInternalListener(animator)
        } else {
          logE(TAG, "All animators must be CameraAnimator's to be registered!")
          return@postOnMainThread
        }
      }
      animators.addAll(cameraAnimators.map { it as CameraAnimator<*> })
    }
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
    postOnMainThread {
      for (animator in cameraAnimators) {
        if (animator is CameraAnimator<*>) {
          postOnAnimatorThread {
            if (cancelAnimators) {
              animator.cancel()
            }
            animator.removeInternalListener()
            animator.removeInternalUpdateListener()
          }
        } else {
          logE(TAG, "All animators must be CameraAnimator's to be unregistered!")
          return@postOnMainThread
        }
      }
      animators.removeAll(cameraAnimators.map { it as CameraAnimator<*> })
    }
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
        postOnAnimatorThread {
          it.cancel()
        }
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
      animationOptions: MapAnimationOptions?,
      animatorListener: Animator.AnimatorListener?
  ) = startHighLevelAnimation(
    cameraAnimationsFactory.getEaseTo(cameraOptions),
    animationOptions,
    animatorListener
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
    animationOptions: MapAnimationOptions?,
    animatorListener: Animator.AnimatorListener?
  ) = startHighLevelAnimation(
    cameraAnimationsFactory.getMoveBy(screenCoordinate),
    animationOptions,
    animatorListener
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
    animationOptions: MapAnimationOptions?,
    animatorListener: Animator.AnimatorListener?
  ) = startHighLevelAnimation(
    cameraAnimationsFactory.getScaleBy(amount, screenCoordinate),
    animationOptions,
    animatorListener
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
    animationOptions: MapAnimationOptions?,
    animatorListener: Animator.AnimatorListener?
  ) = startHighLevelAnimation(
    cameraAnimationsFactory.getRotateBy(first, second),
    animationOptions,
    animatorListener
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
    animationOptions: MapAnimationOptions?,
    animatorListener: Animator.AnimatorListener?
  ) = startHighLevelAnimation(
    cameraAnimationsFactory.getPitchBy(pitch),
    animationOptions,
    animatorListener
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
    animationOptions: MapAnimationOptions?,
    animatorListener: Animator.AnimatorListener?
  ) = startHighLevelAnimation(
    cameraAnimationsFactory.getFlyTo(cameraOptions),
    animationOptions,
    animatorListener
  )

  /**
   * Create CameraZoomAnimator
   *
   * @param options animator options object to set targets and other non mandatory options
   * @param block optional block to apply any [ValueAnimator] parameters
   */
  override fun createZoomAnimator(
    options: CameraAnimatorOptions<Double>,
    block: (ValueAnimator.() -> Unit)?
  ): ValueAnimator = CameraZoomAnimator(options, block)

  /**
   * Create CameraAnchorAnimator
   *
   * @param options animator options object to set targets and other non mandatory options
   * @param block optional block to apply any [ValueAnimator] parameters
   */
  override fun createAnchorAnimator(
    options: CameraAnimatorOptions<ScreenCoordinate>,
    block: (ValueAnimator.() -> Unit)?
  ): ValueAnimator = CameraAnchorAnimator(options, block)

  /**
   * Create CameraBearingAnimator. Current map camera option will be applied on animation start if not specified explicitly with [options.startValue].
   *
   * @param options animator options object to set targets and other non mandatory options
   * @param useShortestPath if set to True shortest bearing path will be applied while animating bearing values.
   * If set to False clock-wise rotation will be used if next target is greater or equal than current one
   * and counter clock-wise rotation will be used if next target less than current one.
   * @param block optional block to apply any [ValueAnimator] parameters
   */
  override fun createBearingAnimator(
    options: CameraAnimatorOptions<Double>,
    useShortestPath: Boolean,
    block: (ValueAnimator.() -> Unit)?
  ): ValueAnimator = CameraBearingAnimator(options, useShortestPath, block)

  /**
   * Create CameraPitchAnimator. Current map camera option will be applied on animation start if not specified explicitly with [options.startValue].
   *
   * @param options animator options object to set targets and other non mandatory options
   * @param block optional block to apply any [ValueAnimator] parameters
   */
  override fun createPitchAnimator(
    options: CameraAnimatorOptions<Double>,
    block: (ValueAnimator.() -> Unit)?
  ): ValueAnimator = CameraPitchAnimator(options, block)

  /**
   * Create CameraPaddingAnimator. Current map camera option will be applied on animation start if not specified explicitly with [options.startValue].
   *
   * @param options animator options object to set targets and other non mandatory options
   * @param block optional block to apply any [ValueAnimator] parameters
   */
  override fun createPaddingAnimator(
    options: CameraAnimatorOptions<EdgeInsets>,
    block: (ValueAnimator.() -> Unit)?
  ): ValueAnimator = CameraPaddingAnimator(options, block)

  /**
   * Create CameraCenterAnimator. Current map camera option will be applied on animation start if not specified explicitly with [options.startValue].
   *
   * @param options animator options object to set targets and other non mandatory options
   * @param block optional block to apply any [ValueAnimator] parameters
   */
  override fun createCenterAnimator(
    options: CameraAnimatorOptions<Point>,
    block: (ValueAnimator.() -> Unit)?
  ): ValueAnimator = CameraCenterAnimator(options, block)

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
        if (cameraAnimator.owner == null) {
          cameraAnimator.owner = MapAnimationOwnerRegistry.INTERNAL
        }
        cameraAnimators.add(cameraAnimator)
      } else {
        logE(TAG, "All animators must be CameraAnimator's to be played together!")
      }
    }
    registerAnimators(*cameraAnimators.toTypedArray())
    AnimatorSet().apply {
      playTogether(*cameraAnimators.toTypedArray())
      postOnAnimatorThread { start() }
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
        if (cameraAnimator.owner == null) {
          cameraAnimator.owner = MapAnimationOwnerRegistry.INTERNAL
        }
        cameraAnimators.add(cameraAnimator)
      } else {
        logE(TAG, "All animators must be CameraAnimator's to be played sequentially!")
      }
    }
    registerAnimators(*cameraAnimators.toTypedArray())
    AnimatorSet().apply {
      playSequentially(*cameraAnimators.toTypedArray())
      postOnAnimatorThread { start() }
    }
  }

  private fun startHighLevelAnimation(
    animators: Array<CameraAnimator<*>>,
    animationOptions: MapAnimationOptions?,
    animatorListener: Animator.AnimatorListener?
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
      animationOptions?.startDelay?.let {
        startDelay = it
      }
      animationOptions?.interpolator?.let {
        interpolator = it
      }
      animatorListener?.let {
        // listeners in Android SDK use non thread safe lists
        postOnAnimatorThread {
          addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
              postOnMainThread {
                commitChanges()
              }

              if (highLevelAnimatorSet?.animatorSet === animation) {
                highLevelAnimatorSet = null
              }
            }
          })
          addListener(it)
        }
      }
      playTogether(*animators)
    }
    return HighLevelAnimatorSet(animationOptions?.owner, animatorSet).also {
      highLevelAnimatorSet = it
      postOnAnimatorThread { it.animatorSet.start() }
    }
  }

  /**
   * Static variables and methods.
   */
  companion object {
    internal const val TAG = "Mbgl-CameraManager"
  }
}