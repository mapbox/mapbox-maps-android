package com.mapbox.maps.plugin.gestures

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.InputDevice
import android.view.MotionEvent
import androidx.annotation.VisibleForTesting
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.mapbox.android.gestures.*
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.InvalidPluginConfigurationException
import com.mapbox.maps.plugin.PLUGIN_CAMERA_ANIMATIONS_CLASS_NAME
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.MapAnimationOwnerRegistry
import com.mapbox.maps.plugin.delegates.*
import com.mapbox.maps.plugin.gestures.generated.GesturesAttributeParser
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.gestures.generated.GesturesSettingsBase
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.math.*

/**
 * Manages gestures events on a MapView.
 */
class GesturesPluginImpl : GesturesPlugin, GesturesSettingsBase {

  private val context: Context

  private lateinit var gesturesManager: AndroidGesturesManager

  private lateinit var mapTransformDelegate: MapTransformDelegate
  private lateinit var mapCameraManagerDelegate: MapCameraManagerDelegate
  private lateinit var mapPluginProviderDelegate: MapPluginProviderDelegate
  private lateinit var cameraAnimationsPlugin: CameraAnimationsPlugin

  private val protectedCameraAnimatorOwnerList = CopyOnWriteArrayList<String>()

  // Listeners
  private val onMapClickListenerList = CopyOnWriteArrayList<OnMapClickListener>()
  private val onMapLongClickListenerList = CopyOnWriteArrayList<OnMapLongClickListener>()
  private val onFlingListenerList = CopyOnWriteArrayList<OnFlingListener>()
  private val onMoveListenerList = CopyOnWriteArrayList<OnMoveListener>()
  private val onRotateListenerList = CopyOnWriteArrayList<OnRotateListener>()
  private val onScaleListenerList = CopyOnWriteArrayList<OnScaleListener>()
  private val onShoveListenerList = CopyOnWriteArrayList<OnShoveListener>()

  // FocalPoint
  private var doubleTapFocalPoint = ScreenCoordinate(0.0, 0.0)
  private var centerScreen = doubleTapFocalPoint

  // Scale
  private var minimumGestureSpeed: Float = 0f
  private var minimumAngledGestureSpeed: Float = 0f
  private var minimumVelocity: Float = 0f
  private var scaleVelocityRatioThreshold: Double = 0.0
  private var quickZoom: Boolean = false
  private var spanSinceLast: Float = 0.0f
  private var screenHeight: Double = 0.0
  private var startZoom: Double = 0.0
  private var flingInProcess: Boolean = false
  private var scaleCachedAnchor: ScreenCoordinate? = null

  // Rotate
  private var minimumScaleSpanWhenRotating: Float = 0f
  private var angularVelocityMultiplier: Float = 0f
  private var minimumAngularVelocity: Float = 0f
  private var rotateVelocityRatioThreshold: Double = 0.0
  private var defaultSpanSinceStartThreshold: Float = 0f
  private var rotateCachedAnchor: ScreenCoordinate? = null

  // Animators
  private var scaleAnimators: Array<ValueAnimator>? = null
  private var rotateAnimators: Array<ValueAnimator>? = null
  private val scheduledAnimators = ArrayList<ValueAnimator>()
  private val gesturesInterpolator = LinearOutSlowInInterpolator()

  // needed most likely for devices with API <= 23 only
  // duration = 0 will still make animation end / cancel not immediately
  // this may cause cancelling some easeTo animation without single camera update
  private var immediateEaseInProcess = false
  private fun easeToImmediately(
    camera: CameraOptions,
    actionAfter: (() -> Unit)? = null
  ) {
    if (!immediateEaseInProcess) {
      immediateEaseInProcess = true
      cameraAnimationsPlugin.easeTo(
        camera,
        mapAnimationOptions {
          duration(0)
          owner(MapAnimationOwnerRegistry.GESTURES)
          animatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
              actionAfter?.invoke()
              immediateEaseInProcess = false
            }
          })
        }
      )
    }
  }

  /**
   * Cancels scheduled velocity animations if user doesn't lift fingers within [SCHEDULED_ANIMATION_TIMEOUT]
   */
  private val animationsTimeoutHandler = Handler()
  private var mainHandler: Handler? = null
  internal var doubleTapRegistered: Boolean = false

  override var internalSettings: GesturesSettings

  constructor(
    context: Context,
    pixelRatio: Float
  ) {
    this.context = context
    internalSettings = GesturesAttributeParser.parseGesturesSettings(context, null, pixelRatio)
    mainHandler = Handler(Looper.getMainLooper())
  }

  constructor(
    context: Context,
    attributeSet: AttributeSet,
    pixelRatio: Float
  ) {
    this.context = context
    internalSettings =
      GesturesAttributeParser.parseGesturesSettings(context, attributeSet, pixelRatio)
    mainHandler = Handler(Looper.getMainLooper())
  }

  @VisibleForTesting
  internal constructor(
    context: Context,
    attributeSet: AttributeSet,
    pixelRatio: Float,
    handler: Handler
  ) {
    this.context = context
    internalSettings =
      GesturesAttributeParser.parseGesturesSettings(context, attributeSet, pixelRatio)
    mainHandler = handler
  }

  override fun applySettings() {
    // no-ops
  }

  private fun initializeGestureListeners(context: Context, attachDefaultListeners: Boolean) {
    if (attachDefaultListeners) {
      val standardGestureListener = StandardGestureListener(
        context.resources.getDimension(
          com.mapbox.android.gestures.R.dimen.mapbox_defaultScaleSpanSinceStartThreshold
        )
      )
      val moveGestureListener = MoveGestureListener()
      minimumGestureSpeed = context.resources.getDimension(R.dimen.mapbox_minimum_scale_speed)
      minimumAngledGestureSpeed =
        context.resources.getDimension(R.dimen.mapbox_minimum_angled_scale_speed)
      minimumVelocity = context.resources.getDimension(R.dimen.mapbox_minimum_scale_velocity)
      scaleVelocityRatioThreshold =
        SCALE_VELOCITY_RATIO_THRESHOLD * context.resources.getDimension(R.dimen.mapbox_density_constant)

      val scaleGestureListener = ScaleGestureListener()
      minimumScaleSpanWhenRotating =
        context.resources.getDimension(R.dimen.mapbox_minimum_scale_span_when_rotating)
      angularVelocityMultiplier =
        context.resources.getDimension(R.dimen.mapbox_angular_velocity_multiplier)
      minimumAngularVelocity =
        context.resources.getDimension(R.dimen.mapbox_minimum_angular_velocity)
      rotateVelocityRatioThreshold =
        ROTATE_VELOCITY_RATIO_THRESHOLD * context.resources.getDimension(R.dimen.mapbox_density_constant)
      defaultSpanSinceStartThreshold = context.resources.getDimension(
        com.mapbox.android.gestures.R.dimen.mapbox_defaultScaleSpanSinceStartThreshold
      )
      val rotateGestureListener = RotateGestureListener()

      val shoveGestureListener = ShoveGestureListener()
      val tapGestureListener = TapGestureListener()

      gesturesManager.setStandardGestureListener(standardGestureListener)
      gesturesManager.setMoveGestureListener(moveGestureListener)
      gesturesManager.setStandardScaleGestureListener(scaleGestureListener)
      gesturesManager.setRotateGestureListener(rotateGestureListener)
      gesturesManager.setShoveGestureListener(shoveGestureListener)
      gesturesManager.setMultiFingerTapGestureListener(tapGestureListener)
    }
  }

  private fun initializeGesturesManager(
    gesturesManager: AndroidGesturesManager,
    setDefaultMutuallyExclusives: Boolean
  ) {
    if (setDefaultMutuallyExclusives) {
      val shoveScaleSet = HashSet<Int>()
      shoveScaleSet.add(AndroidGesturesManager.GESTURE_TYPE_SHOVE)
      shoveScaleSet.add(AndroidGesturesManager.GESTURE_TYPE_SCALE)

      val shoveRotateSet = HashSet<Int>()
      shoveRotateSet.add(AndroidGesturesManager.GESTURE_TYPE_SHOVE)
      shoveRotateSet.add(AndroidGesturesManager.GESTURE_TYPE_ROTATE)

      val scaleLongPressSet = HashSet<Int>()
      scaleLongPressSet.add(AndroidGesturesManager.GESTURE_TYPE_SCALE)
      scaleLongPressSet.add(AndroidGesturesManager.GESTURE_TYPE_LONG_PRESS)

      gesturesManager.setMutuallyExclusiveGestures(
        shoveScaleSet,
        shoveRotateSet,
        scaleLongPressSet
      )
    }
    gesturesManager.rotateGestureDetector.angleThreshold = 3.0f
    this.gesturesManager = gesturesManager
  }

  /**
   * Called when user touches the screen, all positions are absolute.
   *
   *
   * Forwards event to the related gesture detectors.
   *
   *
   * @param motionEvent the MotionEvent
   * @return True if touch event is handled
   */
  override fun onTouchEvent(motionEvent: MotionEvent?): Boolean {
    // Framework can return null motion events in edge cases mapbox-gl-native#9432
    if (motionEvent == null) {
      return false
    }

    // Check and ignore non touch or left clicks
    if (motionEvent.buttonState != 0 && motionEvent.buttonState != MotionEvent.BUTTON_PRIMARY) {
      return false
    }

    if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
      unregisterScheduledAnimators()
      mapTransformDelegate.setGestureInProgress(true)
      mapCameraManagerDelegate.dragStart(
        ScreenCoordinate(
          motionEvent.x.toDouble(),
          motionEvent.y.toDouble()
        )
      )
    }

    val result = gesturesManager.onTouchEvent(motionEvent)

    when (motionEvent.actionMasked) {
      MotionEvent.ACTION_POINTER_DOWN -> doubleTapFinished()

      MotionEvent.ACTION_UP -> {
        doubleTapFinished()
        // TODO will be fixed upstream, needed to not fire extra IDLE event in case of fast click
        mainHandler?.postDelayed(
          {
            mapTransformDelegate.setGestureInProgress(false)
          },
          50
        )
        // if fling happens after dragging then `dragEnd` will be called after fling animation is finished
        if (!flingInProcess) {
          mapCameraManagerDelegate.dragEnd()
        }

        if (scheduledAnimators.isNotEmpty()) {
          // Start all awaiting velocity animations
          animationsTimeoutHandler.removeCallbacksAndMessages(null)
          cameraAnimationsPlugin.registerAnimators(*scheduledAnimators.toTypedArray())
          for (animator in scheduledAnimators) {
            animator.start()
          }
          scheduledAnimators.clear()
        }
      }

      MotionEvent.ACTION_CANCEL -> {
        scheduledAnimators.clear()
        mapTransformDelegate.setGestureInProgress(false)
        mapCameraManagerDelegate.dragEnd()
        doubleTapFinished()
      }
    }

    return result
  }

  private fun unregisterScheduledAnimators() {
    animationsTimeoutHandler.removeCallbacksAndMessages(null)
    scheduledAnimators.clear()
    unregisterScheduledAnimators(scaleAnimators)
    unregisterScheduledAnimators(rotateAnimators)

    dispatchCameraIdle()
  }

  private fun unregisterScheduledAnimators(animators: Array<ValueAnimator>?) =
    animators?.let { cameraAnimationsPlugin.unregisterAnimators(*it) }

  /**
   * Schedules a velocity animator to be executed when user lifts fingers,
   * unless canceled by the [cancelAnimatorsRunnable].
   *
   * @param animators animator ot be scheduled
   */
  private fun scheduleAnimators(animators: Array<ValueAnimator>?) {
    animators?.forEach {
      scheduledAnimators.add(it)
    }
    animationsTimeoutHandler.removeCallbacksAndMessages(null)
    animationsTimeoutHandler.postDelayed(
      { unregisterScheduledAnimators() },
      SCHEDULED_ANIMATION_TIMEOUT
    )
  }

  /**
   * Called for events that don't fit the other handlers.
   *
   *
   * Examples of such events are mouse scroll events, mouse moves, joystick & trackpad.
   *
   *
   * @param event The MotionEvent occurred
   * @return True is the event is handled
   */
  override fun onGenericMotionEvent(event: MotionEvent): Boolean {
    // Mouse events
    // if (event.isFromSource(InputDevice.SOURCE_CLASS_POINTER)) { // this is not available before API 18
    if (event.source and InputDevice.SOURCE_CLASS_POINTER == InputDevice.SOURCE_CLASS_POINTER) {
      // Choose the action
      when (event.actionMasked) {
        // Mouse scrolls
        MotionEvent.ACTION_SCROLL -> {
          if (!internalSettings.zoomEnabled) {
            return false
          }

          // Cancel any animation
          cameraAnimationsPlugin.cancelAllAnimators(protectedCameraAnimatorOwnerList)

          // Get the vertical scroll amount, one click = 1
          val scrollDist = event.getAxisValue(MotionEvent.AXIS_VSCROLL)

          // Scale the map by the appropriate power of two factor
          val currentZoom = mapCameraManagerDelegate.getCameraOptions(null).zoom
          val cachedAnchor = cameraAnimationsPlugin.anchor
          currentZoom?.let {
            val anchor = ScreenCoordinate(event.x.toDouble(), event.y.toDouble())
            val zoom =
              cameraAnimationsPlugin.calculateScaleBy(scrollDist.toDouble(), currentZoom)
            easeToImmediately(
              CameraOptions.Builder().anchor(anchor).zoom(zoom).build(),
              actionAfter = { cameraAnimationsPlugin.anchor = cachedAnchor }
            )
          }

          return true
        }

        else ->
          // We are not interested in this event
          return false
      }
    }

    // We are not interested in this event
    return false
  }

  /**
   * Called when the size of the MapView has changed.
   */
  override fun onSizeChanged(width: Int, height: Int) {
    centerScreen = ScreenCoordinate((width / 2).toDouble(), (height / 2).toDouble())
  }

  /**
   * Standard gesture listener, receives callbacks for gestures detected by AndroidGesturesManager.
   */
  private inner class StandardGestureListener internal constructor(private val doubleTapMovementThreshold: Float) :
    StandardGestureDetector.SimpleStandardOnGestureListener() {

    /**
     * Called when an on down gesture was detected.
     */
    override fun onDown(motionEvent: MotionEvent): Boolean {
      return true
    }

    /**
     * Called when an on single tap up gesture was detected.
     */
    override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
      return handleSingleTapUpEvent()
    }

    /**
     * Called when an on single tap up confirmed gesture was detected.
     */
    override fun onSingleTapConfirmed(motionEvent: MotionEvent): Boolean {
      return handleClickEvent(ScreenCoordinate(motionEvent.x.toDouble(), motionEvent.y.toDouble()))
    }

    /**
     * Called when an on double tap gesture was detected.
     */
    override fun onDoubleTapEvent(motionEvent: MotionEvent): Boolean {
      return if (handleDoubleTapEvent(motionEvent, doubleTapMovementThreshold)) {
        true
      } else super.onDoubleTapEvent(motionEvent)
    }

    /**
     * Called when an on long press gesture was detected.
     */
    override fun onLongPress(motionEvent: MotionEvent) {
      handleLongPressEvent(ScreenCoordinate(motionEvent.x.toDouble(), motionEvent.y.toDouble()))
    }

    /**
     * Called when an on fling gesture was detected.
     */
    override fun onFling(
      e1: MotionEvent,
      e2: MotionEvent,
      velocityX: Float,
      velocityY: Float
    ): Boolean {
      return handleFlingEvent(e1, e2, velocityX, velocityY)
    }
  }

  private fun doubleTapFinished() {
    if (doubleTapRegistered) {
      // re-enable the move detector in case of double tap
      gesturesManager.moveGestureDetector.isEnabled = true
      doubleTapRegistered = false
    }
  }

  /**
   * Move gesture listener, receives callbacks for gestures detected by AndroidGesturesManager.
   */
  private inner class MoveGestureListener : MoveGestureDetector.SimpleOnMoveGestureListener() {
    /**
     * Called when an on move begin gesture was detected.
     */
    override fun onMoveBegin(detector: MoveGestureDetector): Boolean {
      return handleMoveStartEvent(detector)
    }

    /**
     * Called when an on move gesture was detected.
     */
    override fun onMove(
      detector: MoveGestureDetector,
      distanceX: Float,
      distanceY: Float
    ): Boolean {
      return handleMove(detector, distanceX, distanceY)
    }

    /**
     * Called when an on move end gesture was detected.
     */
    override fun onMoveEnd(detector: MoveGestureDetector, velocityX: Float, velocityY: Float) {
      handleMoveEnd(detector)
    }
  }

  /**
   * Scale gesture listener, receives callbacks for gestures detected by AndroidGesturesManager.
   */
  private inner class ScaleGestureListener :
    StandardScaleGestureDetector.SimpleStandardOnScaleGestureListener() {

    /**
     * Called when an on scale begin gesture was detected.
     */
    override fun onScaleBegin(detector: StandardScaleGestureDetector): Boolean {
      return handleScaleBegin(detector)
    }

    /**
     * Called when an on scale gesture was detected.
     */
    override fun onScale(detector: StandardScaleGestureDetector): Boolean {
      return handleScale(detector)
    }

    /**
     * Called when an on scale end gesture was detected.
     */
    override fun onScaleEnd(
      detector: StandardScaleGestureDetector,
      velocityX: Float,
      velocityY: Float
    ) {
      handleScaleEnd(detector, velocityX, velocityY)
    }
  }

  private fun getScaleFocalPoint(detector: StandardScaleGestureDetector): ScreenCoordinate {
    internalSettings.focalPoint?.let {
      // around user provided focal point
      return it
    }

    return if (quickZoom) {
      // around center
      centerScreen
    } else {
      // around gesture
      val pointF = detector.focalPoint
      ScreenCoordinate(pointF.x.toDouble(), pointF.y.toDouble())
    }
  }

  private fun calculateScale(velocityXY: Double, isScalingOut: Boolean): Double {
    var zoomAddition = velocityXY * MAX_ABSOLUTE_SCALE_VELOCITY_CHANGE * 1e-4
    zoomAddition = clamp(zoomAddition, 0.0, MAX_ABSOLUTE_SCALE_VELOCITY_CHANGE)
    if (isScalingOut) {
      zoomAddition = -zoomAddition
    }
    return zoomAddition
  }

  internal fun handleScaleEnd(
    detector: StandardScaleGestureDetector,
    velocityX: Float,
    velocityY: Float
  ) {
    if (quickZoom) {
      // re-enabled the move detector if the quickzoom happened
      gesturesManager.moveGestureDetector.isEnabled = true
    } else {
      // re-enable rotation in case it's been disabled
      gesturesManager.rotateGestureDetector.isEnabled = true
    }

    notifyOnScaleEndListeners(detector)

    val velocityXY = abs(velocityX) + abs(velocityY)
    if (!internalSettings.scaleVelocityAnimationEnabled || velocityXY < minimumVelocity || spanSinceLast / velocityXY < scaleVelocityRatioThreshold
    ) {
      // notifying listeners that camera is idle only if there is no follow-up animation
      dispatchCameraIdle()
      return
    }

    val zoomAddition = calculateScale(velocityXY.toDouble(), detector.isScalingOut)
    val currentZoom = mapCameraManagerDelegate.getCameraOptions(null).zoom
    val focalPoint = getScaleFocalPoint(detector)
    // (log(x + 1 / e^2) + 2) * 150, x=0 to 2.5 (MapboxConstants#MAX_ABSOLUTE_SCALE_VELOCITY_CHANGE)
    val animationTime = (
      (
        ln(
          abs(zoomAddition) + 1 / Math.E.pow(2.0)
        ) + 2
        ) * SCALE_VELOCITY_ANIMATION_DURATION_MULTIPLIER
      ).toLong()
    currentZoom?.let {
      val animators =
        createScaleAnimators(it, zoomAddition, focalPoint, animationTime)
      scaleAnimators = animators
      scheduleAnimators(animators)
    }
  }

  internal fun handleScale(detector: StandardScaleGestureDetector): Boolean {
    // in order not to mess up initial anchor values
    if (immediateEaseInProcess) {
      return true
    }
    // dispatching camera start event only when the movement actually occurred
    // cameraChangeDispatcher.onCameraMoveStarted(CameraChangeDispatcher.REASON_API_GESTURE);

    val focalPoint = getScaleFocalPoint(detector)
    scaleCachedAnchor = cameraAnimationsPlugin.anchor
    if (quickZoom) {
      internalSettings.zoomRate
      val pixelDeltaChange = abs(detector.currentEvent.y - doubleTapFocalPoint.y)
      val zoomedOut = detector.currentEvent.y < doubleTapFocalPoint.y

      // normalize the pixel delta change, ranging from 0 to screen height, to a constant zoom change range
      val normalizedDeltaChange = 0.0.normalize(
        pixelDeltaChange,
        0.0,
        screenHeight,
        QUICK_ZOOM_MAX_ZOOM_CHANGE
      )

      // calculate target zoom and adjust for a multiplier
      var targetZoom =
        if (zoomedOut) startZoom - normalizedDeltaChange else startZoom + normalizedDeltaChange
      targetZoom *= internalSettings.zoomRate.toDouble()
      easeToImmediately(
        CameraOptions.Builder()
          .zoom(targetZoom)
          .anchor(focalPoint)
          .build(),
        actionAfter = {
          cameraAnimationsPlugin.anchor = scaleCachedAnchor
          notifyOnScaleListeners(detector)
          spanSinceLast = abs(detector.currentSpan - detector.previousSpan)
        }
      )
    } else {
      val zoomBy =
        ln(detector.scaleFactor.toDouble()) / ln(PI / 2) * ZOOM_RATE.toDouble() * internalSettings.zoomRate.toDouble()
      mapCameraManagerDelegate.getCameraOptions(null).zoom?.let {
        easeToImmediately(
          CameraOptions.Builder()
            .zoom(it + zoomBy)
            .anchor(focalPoint)
            .build(),
          actionAfter = {
            cameraAnimationsPlugin.anchor = scaleCachedAnchor
            notifyOnScaleListeners(detector)
            spanSinceLast = abs(detector.currentSpan - detector.previousSpan)
          }
        )
      }
    }
    return true
  }

  internal fun handleScaleBegin(detector: StandardScaleGestureDetector): Boolean {
    quickZoom = detector.pointersCount == 1

    if (!internalSettings.zoomEnabled) {
      return false
    }

    if (quickZoom) {
      if (!internalSettings.quickZoomEnabled) {
        return false
      }
      // re-try disabling the move detector in case double tap has been interrupted before quickzoom started
      gesturesManager.moveGestureDetector.isEnabled = false
    } else {
      if (detector.previousSpan > 0) {
        val currSpan = detector.currentSpan
        val prevSpan = detector.previousSpan
        val currTime = detector.currentEvent.eventTime.toDouble()
        val prevTime = detector.previousEvent.eventTime.toDouble()
        if (currTime == prevTime) {
          return false
        }
        val speed = abs(currSpan - prevSpan) / (currTime - prevTime)
        if (speed < minimumGestureSpeed) {
          // do not scale if the minimal gesture speed is not met
          return false
        } else if (!gesturesManager.rotateGestureDetector.isInProgress) {
          val rotationDeltaSinceLast = gesturesManager.rotateGestureDetector.deltaSinceLast
          if (abs(rotationDeltaSinceLast) > 0.4 && speed < minimumAngledGestureSpeed) {
            // do not scale in case we're preferring to start rotation
            return false
          }

          if (internalSettings.disableRotateWhenScaling) {
            // disable rotate gesture when scale is detected first
            gesturesManager.rotateGestureDetector.isEnabled = false
          }
        }
      } else {
        return false
      }
    }

    screenHeight = Resources.getSystem().displayMetrics.heightPixels.toDouble()
    startZoom = mapCameraManagerDelegate.getCameraOptions(null).zoom!!

    cancelTransitionsIfRequired()

    notifyOnScaleBeginListeners(detector)

    spanSinceLast = abs(detector.currentSpan - detector.previousSpan)

    return true
  }

  /**
   * Rotate gesture listener, receives callbacks for gestures detected by AndroidGesturesManager.
   */
  private inner class RotateGestureListener :
    RotateGestureDetector.SimpleOnRotateGestureListener() {

    /**
     * Called when an on rotate begin gesture was detected.
     */
    override fun onRotateBegin(detector: RotateGestureDetector): Boolean {
      return handleRotateBegin(detector)
    }

    /**
     * Called when an on rotate gesture was detected.
     */
    override fun onRotate(
      detector: RotateGestureDetector,
      rotationDegreesSinceLast: Float,
      rotationDegreesSinceFirst: Float
    ): Boolean {
      return handleRotate(detector, rotationDegreesSinceLast)
    }

    /**
     * Called when an on rotate endw gesture was detected.
     */
    override fun onRotateEnd(
      detector: RotateGestureDetector,
      velocityX: Float,
      velocityY: Float,
      angularVelocity: Float
    ) {
      handleRotateEnd(detector, velocityX, velocityY, angularVelocity)
    }
  }

  private fun getRotateFocalPoint(detector: RotateGestureDetector): ScreenCoordinate {
    internalSettings.focalPoint?.let {
      // User provided focal point
      return it
    }

    // around gesture
    val pointF = detector.focalPoint
    return ScreenCoordinate(pointF.x.toDouble(), pointF.y.toDouble())
  }

  private fun createRotateAnimators(
    angularVelocity: Float,
    animationTime: Long,
    animationFocalPoint: ScreenCoordinate
  ): Array<ValueAnimator> {

    val rotateInterpolator = gesturesInterpolator

    // Calculate steps count of interpolation, rely on display update rate = 16ms
    val interpolatorSteps = animationTime.div(16) + 1
    var bearingDistance = angularVelocity
    for (step in 1..interpolatorSteps) {
      val fraction = step.toFloat() / interpolatorSteps
      val fractionInterpolated = rotateInterpolator.getInterpolation(fraction)
      val stepVelocity = angularVelocity * (1 - fractionInterpolated)
      bearingDistance += stepVelocity
    }

    val bearingCurrent = mapCameraManagerDelegate.getCameraOptions(null).bearing ?: return arrayOf()
    val bearingTarget = bearingCurrent + bearingDistance

    val bearingAnimator = cameraAnimationsPlugin.createBearingAnimator(
      options = cameraAnimatorOptions(bearingTarget) {
        owner(MapAnimationOwnerRegistry.GESTURES)
        startValue(bearingCurrent)
      },
    ) {
      interpolator = rotateInterpolator
      duration = animationTime
    }

    val screenCoordinate = ScreenCoordinate(animationFocalPoint.x, animationFocalPoint.y)
    val anchorAnimator = cameraAnimationsPlugin.createAnchorAnimator(
      options = cameraAnimatorOptions(screenCoordinate) {
        owner(MapAnimationOwnerRegistry.GESTURES)
        startValue(screenCoordinate)
      },
    ) {
      interpolator = rotateInterpolator
      duration = animationTime
    }

    anchorAnimator.addListener(
      object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
          cameraAnimationsPlugin.anchor = rotateCachedAnchor
          dispatchCameraIdle()
        }
      }
    )
    return arrayOf(bearingAnimator, anchorAnimator)
  }

  internal fun handleRotateEnd(
    detector: RotateGestureDetector,
    velocityX: Float,
    velocityY: Float,
    angularVelocityEvent: Float
  ) {
    var angularVelocity = angularVelocityEvent
    if (internalSettings.increaseScaleThresholdWhenRotating) {
      // resetting default scale threshold values
      gesturesManager.standardScaleGestureDetector.spanSinceStartThreshold =
        defaultSpanSinceStartThreshold
    }

    notifyOnRotateEndListeners(detector)

    angularVelocity *= angularVelocityMultiplier
    angularVelocity = clamp(
      angularVelocity,
      -MAXIMUM_ANGULAR_VELOCITY,
      MAXIMUM_ANGULAR_VELOCITY
    )

    val velocityXY = abs(velocityX) + abs(velocityY)
    val delta = abs(detector.deltaSinceLast)
    val ratio = (delta / velocityXY).toDouble()

    if (!internalSettings.rotateVelocityAnimationEnabled || abs(angularVelocity) < minimumAngularVelocity || gesturesManager.standardScaleGestureDetector.isInProgress() && ratio < rotateVelocityRatioThreshold) {
      // notifying listeners that camera is idle only if there is no follow-up animation
      dispatchCameraIdle()
      return
    }

    val animationTime = (
      (
        ln(
          abs(angularVelocity) + 1 / Math.E.pow(2.0)
        ) + 2
        ) * SCALE_VELOCITY_ANIMATION_DURATION_MULTIPLIER
      ).toLong()

    val focalPoint = getRotateFocalPoint(detector)
    rotateAnimators = createRotateAnimators(angularVelocity, animationTime, focalPoint)
    scheduleAnimators(rotateAnimators)
  }

  internal fun handleRotate(
    detector: RotateGestureDetector,
    rotationDegreesSinceLast: Float
  ): Boolean {
    // in order not to mess up initial anchor values
    if (immediateEaseInProcess) {
      return true
    }
    // dispatching camera start event only when the movement actually occurred
    // cameraChangeDispatcher.onCameraMoveStarted(CameraChangeDispatcher.REASON_API_GESTURE);

    // Calculate map bearing value
    val currentBearing = mapCameraManagerDelegate.getCameraOptions(null).bearing
    rotateCachedAnchor = cameraAnimationsPlugin.anchor
    currentBearing?.let {
      val bearing = it + rotationDegreesSinceLast

      // Rotate the map
      val focalPoint = getRotateFocalPoint(detector)
      easeToImmediately(
        CameraOptions.Builder()
          .anchor(focalPoint)
          .bearing(bearing)
          .build(),
        actionAfter = {
          cameraAnimationsPlugin.anchor = rotateCachedAnchor
          notifyOnRotateListeners(detector)
        }
      )
    }

    return true
  }

  internal fun handleRotateBegin(detector: RotateGestureDetector): Boolean {
    if (!internalSettings.rotateEnabled) {
      return false
    }

    val deltaSinceLast = abs(detector.deltaSinceLast)
    val currTime = detector.currentEvent.eventTime.toDouble()
    val prevTime = detector.previousEvent.eventTime.toDouble()
    if (currTime == prevTime) {
      return false
    }
    val speed = deltaSinceLast / (currTime - prevTime)
    val deltaSinceStart = abs(detector.deltaSinceStart)

    // adjust the responsiveness of a rotation gesture - the higher the speed, the bigger the threshold
    if (
      speed < 0.04 ||
      speed > 0.07 && deltaSinceStart < 5 ||
      speed > 0.15 && deltaSinceStart < 7 ||
      speed > 0.5 && deltaSinceStart < 15
    ) {
      return false
    }

    if (internalSettings.increaseScaleThresholdWhenRotating) {
      // when rotation starts, interrupting scale and increasing the threshold
      // to make rotation without scaling easier
      gesturesManager.standardScaleGestureDetector.spanSinceStartThreshold =
        minimumScaleSpanWhenRotating
      gesturesManager.standardScaleGestureDetector.interrupt()
    }

    cancelTransitionsIfRequired()

    notifyOnRotateBeginListeners(detector)

    return true
  }

  /**
   * Shove gesture listener, receives callbacks for gestures detected by AndroidGesturesManager.
   */
  private inner class ShoveGestureListener : ShoveGestureDetector.SimpleOnShoveGestureListener() {
    /**
     * Called when an on shove begin gesture was detected.
     */
    override fun onShoveBegin(detector: ShoveGestureDetector): Boolean {
      return handleShoveBegin(detector)
    }

    /**
     * Called when an on shove gesture was detected.
     */
    override fun onShove(
      detector: ShoveGestureDetector,
      deltaPixelsSinceLast: Float,
      deltaPixelsSinceStart: Float
    ): Boolean {
      return handleShove(detector, deltaPixelsSinceLast)
    }

    /**
     * Called when an on shove end gesture was detected.
     */
    override fun onShoveEnd(detector: ShoveGestureDetector, velocityX: Float, velocityY: Float) {
      handleShoveEnd(detector)
    }
  }

  internal fun handleShoveBegin(detector: ShoveGestureDetector): Boolean {
    if (!internalSettings.pitchEnabled) {
      return false
    }

    cancelTransitionsIfRequired()

    // disabling move gesture during shove
    gesturesManager.moveGestureDetector.isEnabled = false

    notifyOnShoveBeginListeners(detector)

    return true
  }

  internal fun handleShove(
    detector: ShoveGestureDetector,
    deltaPixelsSinceLast: Float
  ): Boolean {
    // dispatching camera start event only when the movement actually occurred
    // cameraChangeDispatcher.onCameraMoveStarted(CameraChangeDispatcher.REASON_API_GESTURE);

    // Get pitch value (scale and clamp)
    var pitch = mapCameraManagerDelegate.getCameraOptions(null).pitch
    pitch?.let {
      val optimizedPitch = it - (SHOVE_PIXEL_CHANGE_FACTOR * deltaPixelsSinceLast).toDouble()
      pitch = clamp(optimizedPitch, MINIMUM_PITCH, MAXIMUM_PITCH)
      easeToImmediately(
        CameraOptions.Builder().pitch(pitch).build(),
        actionAfter = { notifyOnShoveListeners(detector) }
      )
    }
    return true
  }

  internal fun handleShoveEnd(
    detector: ShoveGestureDetector
  ) {
    dispatchCameraIdle()

    // re-enabling move gesture
    gesturesManager.moveGestureDetector.isEnabled = true

    notifyOnShoveEndListeners(detector)
  }

  private inner class TapGestureListener :
    MultiFingerTapGestureDetector.OnMultiFingerTapGestureListener {
    override fun onMultiFingerTap(
      detector: MultiFingerTapGestureDetector,
      pointersCount: Int
    ): Boolean {
      if (!internalSettings.zoomEnabled || pointersCount != 2) {
        return false
      }

      cameraAnimationsPlugin.cancelAllAnimators(protectedCameraAnimatorOwnerList)
      // cameraChangeDispatcher.onCameraMoveStarted(REASON_API_GESTURE);

      val zoomFocalPoint: ScreenCoordinate
      // Single finger double tap
      internalSettings.focalPoint?.let {
        // zoom in on user focal point
        zoomFocalPoint = it
        zoomOutAnimated(zoomFocalPoint, false)
        return true
      }

      // Zoom in on gesture
      val pointF = detector.focalPoint
      zoomFocalPoint = ScreenCoordinate(pointF.x.toDouble(), pointF.y.toDouble())
      zoomOutAnimated(zoomFocalPoint, false)
      return true
    }
  }

  private fun createScaleAnimators(
    currentZoom: Double,
    zoomAddition: Double,
    animationFocalPoint: ScreenCoordinate,
    animationTime: Long
  ): Array<ValueAnimator> {

    val scaleInterpolator = gesturesInterpolator
    val zoomAnimator = cameraAnimationsPlugin.createZoomAnimator(
      options = cameraAnimatorOptions(currentZoom + zoomAddition) {
        owner(MapAnimationOwnerRegistry.GESTURES)
        startValue(currentZoom)
      }
    ) {
      interpolator = scaleInterpolator
      duration = animationTime
    }

    val anchorAnimator = cameraAnimationsPlugin.createAnchorAnimator(
      options = cameraAnimatorOptions(animationFocalPoint) {
        owner(MapAnimationOwnerRegistry.GESTURES)
        startValue(animationFocalPoint)
      },
    ) {
      interpolator = scaleInterpolator
      duration = animationTime
    }

    anchorAnimator.addListener(object : AnimatorListenerAdapter() {

      override fun onAnimationEnd(animation: Animator) {
        cameraAnimationsPlugin.anchor = scaleCachedAnchor
        dispatchCameraIdle()
      }
    })
    return arrayOf(zoomAnimator, anchorAnimator)
  }

  /**
   * Zoom in by 1.
   *
   * @param zoomFocalPoint focal point of zoom animation
   * @param runImmediately if true, animation will be started right away, otherwise it will wait until
   * [MotionEvent.ACTION_UP] is registered.
   */
  private fun zoomInAnimated(zoomFocalPoint: ScreenCoordinate, runImmediately: Boolean) {
    zoomAnimated(true, zoomFocalPoint, runImmediately)
  }

  /**
   * Zoom out by 1.
   *
   * @param zoomFocalPoint focal point of zoom animation
   * @param runImmediately if true, animation will be started right away, otherwise it will wait until
   * [MotionEvent.ACTION_UP] is registered.
   */
  internal fun zoomOutAnimated(zoomFocalPoint: ScreenCoordinate, runImmediately: Boolean) {
    zoomAnimated(false, zoomFocalPoint, runImmediately)
  }

  private fun zoomAnimated(
    zoomIn: Boolean,
    zoomFocalPoint: ScreenCoordinate,
    runImmediately: Boolean
  ) {
    // canceling here as well, because when using a button it will not be canceled automatically by onDown()
    unregisterScheduledAnimators(scaleAnimators)

    val currentZoom = mapCameraManagerDelegate.getCameraOptions(null).zoom
    currentZoom?.let {
      val animators = createScaleAnimators(
        currentZoom,
        (if (zoomIn) 1 else -1).toDouble(),
        zoomFocalPoint,
        ANIMATION_DURATION.toLong()
      )
      scaleAnimators = animators
      if (runImmediately) {
        animators.forEach {
          it.start()
        }
      } else {
        scheduleAnimators(animators)
      }
    }
  }

  private fun dispatchCameraIdle() {
    //    // we need to dispatch camera idle callback only if there is no other gestures in progress
    //    if (noGesturesInProgress()) {
    //      // invalidate the camera position, so that it's valid when fetched from the #onIdle event
    //      // and doesn't rely on the last frame being rendered
    //      mapTransformDelegate.invalidateCameraPosition();
    //      cameraChangeDispatcher.onCameraIdle();
    //    }
  }

  private fun cancelTransitionsIfRequired() {
    // we need to cancel core transitions only if there is no started gesture yet
    if (noGesturesInProgress()) {
      cameraAnimationsPlugin.cancelAllAnimators(protectedCameraAnimatorOwnerList)
    }
  }

  private fun noGesturesInProgress(): Boolean {
    return (
      (!internalSettings.scrollEnabled || !gesturesManager.moveGestureDetector.isInProgress) &&
        (!internalSettings.zoomEnabled || !gesturesManager.standardScaleGestureDetector.isInProgress)
      ) &&
      (!internalSettings.rotateEnabled || !gesturesManager.rotateGestureDetector.isInProgress) &&
      (!internalSettings.pitchEnabled || !gesturesManager.shoveGestureDetector.isInProgress)
  }

  internal fun handleLongPressEvent(screenCoordinate: ScreenCoordinate) {
    if (!onMapLongClickListenerList.isEmpty()) {
      val clickedPoint = mapCameraManagerDelegate.coordinateForPixel(screenCoordinate)
      for (listener in onMapLongClickListenerList) {
        if (listener.onMapLongClick(clickedPoint)) {
          return
        }
      }
    }
  }

  internal fun handleClickEvent(screenCoordinate: ScreenCoordinate): Boolean {
    if (!onMapClickListenerList.isEmpty()) {
      val clickedPoint = mapCameraManagerDelegate.coordinateForPixel(screenCoordinate)
      for (listener in onMapClickListenerList) {
        if (listener.onMapClick(clickedPoint)) {
          return true
        }
      }
    }
    return false
  }

  internal fun handleDoubleTapEvent(
    motionEvent: MotionEvent,
    doubleTapMovementThreshold: Float
  ): Boolean {
    val action = motionEvent.actionMasked
    if (action == MotionEvent.ACTION_DOWN) {
      doubleTapFocalPoint = ScreenCoordinate(motionEvent.x.toDouble(), motionEvent.y.toDouble())
      // disable the move detector in preparation for the quickzoom,
      // so that we don't move the map's center slightly before the quickzoom is started (see #14227)
      gesturesManager.moveGestureDetector.isEnabled = false
      doubleTapRegistered = true
    }

    if (motionEvent.actionMasked == MotionEvent.ACTION_UP) {
      val diffX = abs(motionEvent.x - doubleTapFocalPoint.x)
      val diffY = abs(motionEvent.y - doubleTapFocalPoint.y)
      if (diffX > doubleTapMovementThreshold || diffY > doubleTapMovementThreshold) {
        // Ignore double-tap event because we've started the quick-zoom. See #14013.
        return false
      }

      if (!internalSettings.zoomEnabled || !internalSettings.doubleTapToZoomEnabled) {
        return false
      }

      internalSettings.focalPoint?.let {
        // User provided focal point
        doubleTapFocalPoint = it
      }

      zoomInAnimated(doubleTapFocalPoint, false)
      return true
    }
    return false
  }

  internal fun handleFlingEvent(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
    if (!internalSettings.scrollEnabled) {
      // don't allow a fling if scroll is disabled
      return false
    }

    notifyOnFlingListeners()

    if (!internalSettings.flingVelocityAnimationEnabled) {
      return false
    }

    val screenDensity = internalSettings.pixelRatio

    // calculate velocity vector for xy dimensions, independent from screen size
    val velocityXY =
      hypot((velocityX / screenDensity).toDouble(), (velocityY / screenDensity).toDouble())
    if (velocityXY < VELOCITY_THRESHOLD_IGNORE_FLING) {
      // ignore short flings, these can occur when other gestures just have finished executing
      return false
    }

    flingInProcess = true

    cameraAnimationsPlugin.cancelAllAnimators(protectedCameraAnimatorOwnerList)
    // cameraChangeDispatcher.onCameraMoveStarted(REASON_API_GESTURE);

    // pitch results in a bigger translation, limiting input for #5281
    val pitch = mapCameraManagerDelegate.getCameraOptions(null).pitch
    pitch?.let {
      val pitchFactorAdditionalComponent = when {
        it == MINIMUM_PITCH -> {
          0.0
        }
        it > MINIMUM_PITCH && it < NORMAL_MAX_PITCH -> {
          it / 10.0
        }
        it in NORMAL_MAX_PITCH..MAXIMUM_PITCH -> {
          val a = ln(NORMAL_MAX_PITCH / 10.0)
          val b = ln(MAXIMUM_PITCH)
          // exp(a) = it / 10.0
          // exp(b) = it
          exp((b - a) * (it - NORMAL_MAX_PITCH) / (MAXIMUM_PITCH - NORMAL_MAX_PITCH) + a)
        }
        else -> 0.0
      }
      val pitchFactor = 1.5 + pitchFactorAdditionalComponent

      val offsetX = velocityX.toDouble() / pitchFactor / screenDensity.toDouble()
      val offsetY = velocityY.toDouble() / pitchFactor / screenDensity.toDouble()

      // calculate animation time based on displacement
      val animationTime =
        (velocityXY / 7.0 / pitchFactor + ANIMATION_DURATION_FLING_BASE).toLong()

      cameraAnimationsPlugin.easeTo(
        mapCameraManagerDelegate.getDragCameraOptions(
          centerScreen,
          ScreenCoordinate(centerScreen.x + offsetX, centerScreen.y + offsetY)
        ),
        mapAnimationOptions {
          owner(MapAnimationOwnerRegistry.GESTURES)
          duration(animationTime)
          interpolator(gesturesInterpolator)
          animatorListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator?) {
              super.onAnimationEnd(animation)
              flingInProcess = false
              mapCameraManagerDelegate.dragEnd()
            }
          })
        }
      )
    }
    return true
  }

  internal fun handleMoveStartEvent(detector: MoveGestureDetector): Boolean {
    if (!internalSettings.scrollEnabled) {
      return false
    }

    cancelTransitionsIfRequired()
    notifyOnMoveBeginListeners(detector)
    return true
  }

  internal fun handleMove(
    detector: MoveGestureDetector,
    distanceX: Float,
    distanceY: Float
  ): Boolean {
    // first move event is often delivered with no displacement
    if (distanceX != 0f || distanceY != 0f) {
      // dispatching camera start event only when the movement actually occurred
      // cameraChangeDispatcher.onCameraMoveStarted(CameraChangeDispatcher.REASON_API_GESTURE);
      if (notifyOnMoveListeners(detector)) {
        return true
      }
      val pitch = mapCameraManagerDelegate.getCameraOptions(null).pitch

      // Scroll the map
      val offset = if (pitch != null && pitch in NORMAL_MAX_PITCH..MAXIMUM_PITCH) {
        // reducing distance values for high pitch values
        // based equation system
        // f(NORMAL_MAX_PITCH) = 1.0 and f(MAXIMUM_PITCH) = 4.5
        // TODO use triangulation to calculate the y distance
        ScreenCoordinate((-distanceX).toDouble(), -distanceY / (0.14 * pitch - 7.4))
      } else {
        ScreenCoordinate((-distanceX).toDouble(), (-distanceY).toDouble())
      }
      easeToImmediately(
        mapCameraManagerDelegate.getDragCameraOptions(
          centerScreen,
          ScreenCoordinate(centerScreen.x + offset.x, centerScreen.y + offset.y)
        )
      )
    }
    return true
  }

  internal fun handleMoveEnd(detector: MoveGestureDetector) {
    dispatchCameraIdle()
    notifyOnMoveEndListeners(detector)
  }

  internal fun handleSingleTapUpEvent(): Boolean {
    cameraAnimationsPlugin.cancelAllAnimators(protectedCameraAnimatorOwnerList)
    return true
  }

  private fun notifyOnFlingListeners() {
    if (!onFlingListenerList.isEmpty()) {
      for (listener in onFlingListenerList) {
        listener.onFling()
      }
    }
  }

  private fun notifyOnMoveBeginListeners(detector: MoveGestureDetector) {
    if (!onMoveListenerList.isEmpty()) {
      for (listener in onMoveListenerList) {
        listener.onMoveBegin(detector)
      }
    }
  }

  private fun notifyOnMoveListeners(detector: MoveGestureDetector): Boolean {
    if (!onMoveListenerList.isEmpty()) {
      for (listener in onMoveListenerList) {
        if (listener.onMove(detector)) {
          return true
        }
      }
    }
    return false
  }

  private fun notifyOnMoveEndListeners(detector: MoveGestureDetector) {
    if (!onMoveListenerList.isEmpty()) {
      for (listener in onMoveListenerList) {
        listener.onMoveEnd(detector)
      }
    }
  }

  private fun notifyOnRotateBeginListeners(detector: RotateGestureDetector) {
    if (!onRotateListenerList.isEmpty()) {
      for (listener in onRotateListenerList) {
        listener.onRotateBegin(detector)
      }
    }
  }

  private fun notifyOnRotateListeners(detector: RotateGestureDetector) {
    if (!onRotateListenerList.isEmpty()) {
      for (listener in onRotateListenerList) {
        listener.onRotate(detector)
      }
    }
  }

  private fun notifyOnRotateEndListeners(detector: RotateGestureDetector) {
    if (!onRotateListenerList.isEmpty()) {
      for (listener in onRotateListenerList) {
        listener.onRotateEnd(detector)
      }
    }
  }

  private fun notifyOnScaleBeginListeners(detector: StandardScaleGestureDetector) {
    if (!onScaleListenerList.isEmpty()) {
      for (listener in onScaleListenerList) {
        listener.onScaleBegin(detector)
      }
    }
  }

  private fun notifyOnScaleListeners(detector: StandardScaleGestureDetector) {
    if (!onScaleListenerList.isEmpty()) {
      for (listener in onScaleListenerList) {
        listener.onScale(detector)
      }
    }
  }

  private fun notifyOnScaleEndListeners(detector: StandardScaleGestureDetector) {
    if (!onScaleListenerList.isEmpty()) {
      for (listener in onScaleListenerList) {
        listener.onScaleEnd(detector)
      }
    }
  }

  private fun notifyOnShoveBeginListeners(detector: ShoveGestureDetector) {
    if (!onShoveListenerList.isEmpty()) {
      for (listener in onShoveListenerList) {
        listener.onShoveBegin(detector)
      }
    }
  }

  private fun notifyOnShoveListeners(detector: ShoveGestureDetector) {
    if (!onShoveListenerList.isEmpty()) {
      for (listener in onShoveListenerList) {
        listener.onShove(detector)
      }
    }
  }

  private fun notifyOnShoveEndListeners(detector: ShoveGestureDetector) {
    if (!onShoveListenerList.isEmpty()) {
      for (listener in onShoveListenerList) {
        listener.onShoveEnd(detector)
      }
    }
  }

  /**
   * Add a callback that is invoked when the map is clicked.
   */
  override fun addOnMapClickListener(onMapClickListener: OnMapClickListener) {
    onMapClickListenerList.add(onMapClickListener)
  }

  /**
   * Remove a callback that is invoked when the map is clicked.
   */
  override fun removeOnMapClickListener(onMapClickListener: OnMapClickListener) {
    onMapClickListenerList.remove(onMapClickListener)
  }

  /**
   * Add a callback that is invoked when the map is long clicked.
   */
  override fun addOnMapLongClickListener(onMapLongClickListener: OnMapLongClickListener) {
    onMapLongClickListenerList.add(onMapLongClickListener)
  }

  /**
   * Remove a callback that is invoked when the map is long clicked.
   */
  override fun removeOnMapLongClickListener(onMapLongClickListener: OnMapLongClickListener) {
    onMapLongClickListenerList.remove(onMapLongClickListener)
  }

  /**
   * Add a callback that is invoked when the map is has received a fling gesture.
   */
  override fun addOnFlingListener(onFlingListener: OnFlingListener) {
    onFlingListenerList.add(onFlingListener)
  }

  /**
   * Remove a callback that is invoked when the map is has received a fling gesture.
   */
  override fun removeOnFlingListener(onFlingListener: OnFlingListener) {
    onFlingListenerList.remove(onFlingListener)
  }

  /**
   * Add a callback that is invoked when the map is moved.
   */
  override fun addOnMoveListener(onMoveListener: OnMoveListener) {
    onMoveListenerList.add(onMoveListener)
  }

  /**
   * Remove a callback that is invoked when the map is moved.
   */
  override fun removeOnMoveListener(listener: OnMoveListener) {
    onMoveListenerList.remove(listener)
  }

  /**
   * Add a callback that is invoked when the map is rotated.
   */
  override fun addOnRotateListener(onRotateListener: OnRotateListener) {
    onRotateListenerList.add(onRotateListener)
  }

  /**
   * Remove a callback that is invoked when the map is rotated.
   */
  override fun removeOnRotateListener(listener: OnRotateListener) {
    onRotateListenerList.remove(listener)
  }

  /**
   * Add a callback that is invoked when the map is scaled.
   */
  override fun addOnScaleListener(onScaleListener: OnScaleListener) {
    onScaleListenerList.add(onScaleListener)
  }

  /**
   * Remove a callback that is invoked when the map is scaled.
   */
  override fun removeOnScaleListener(listener: OnScaleListener) {
    onScaleListenerList.remove(listener)
  }

  /**
   * Add a callback that is invoked when the map is shoved.
   */
  override fun addOnShoveListener(onShoveListener: OnShoveListener) {
    onShoveListenerList.add(onShoveListener)
  }

  /**
   * Remove a callback that is invoked when the map is shoved.
   */
  override fun removeOnShoveListener(listener: OnShoveListener) {
    onShoveListenerList.remove(listener)
  }

  /**
   * Add animator owner (see [CameraAnimatorOptions.owner] or [MapAnimationOptions.owner]
   * which animation will not be canceled with when gesture animation is about to start.
   * When specified, you are responsible for listening to gesture interactions and canceling the specified owners' animations to avoid competing with gestures.
   */
  override fun addProtectedAnimationOwner(owner: String) {
    protectedCameraAnimatorOwnerList.add(owner)
  }

  /**
   * Remove animator owner (see [CameraAnimatorOptions.owner] or [MapAnimationOptions.owner])
   * which animation will not be canceled with when gesture animation is about to start.
   * When specified, you are responsible for listening to gesture interactions and canceling the specified owners' animations to avoid competing with gestures.
   */
  override fun removeProtectedAnimationOwner(owner: String) {
    protectedCameraAnimatorOwnerList.remove(owner)
  }

  /**
   * Get the current configured AndroidGesturesManager.
   */
  override fun getGesturesManager(): AndroidGesturesManager {
    return gesturesManager
  }

  /**
   * Set the AndroidGesturesManager instance.
   */
  override fun setGesturesManager(
    internalGesturesManager: AndroidGesturesManager,
    attachDefaultListeners: Boolean,
    setDefaultMutuallyExclusives: Boolean
  ) {
    initializeGesturesManager(internalGesturesManager, setDefaultMutuallyExclusives)
    initializeGestureListeners(context, attachDefaultListeners)
  }

  private fun clamp(value: Double, min: Double, max: Double): Double {
    return min.coerceAtLeast(max.coerceAtMost(value))
  }

  private fun clamp(value: Float, min: Float, max: Float): Float {
    return min.coerceAtLeast(max.coerceAtMost(value))
  }

  private fun Double.normalize(
    x: Double,
    dataLow: Double,
    dataHigh: Double,
    normalizedHigh: Double
  ): Double {
    return (x - dataLow) / (dataHigh - dataLow) * (normalizedHigh - this) + this
  }

  /**
   * Called when the map is destroyed. Should be used to cleanup plugin resources for that map.
   */
  override fun cleanup() {
    protectedCameraAnimatorOwnerList.clear()
  }

  /**
   * Bind the Gestures plugin with current context. This will enable the map
   * to use the Gesture plugin.
   *
   * @param context The Context
   * @param attrs The attribution set of the MapView.
   * @param pixelRatio The pixel density of the screen.
   */
  override fun bind(context: Context, attrs: AttributeSet?, pixelRatio: Float) {
    bind(context, AndroidGesturesManager(context), attrs, pixelRatio)
  }

  // For internal testing.
  internal fun bind(
    context: Context,
    gesturesManager: AndroidGesturesManager,
    attrs: AttributeSet?,
    pixelRatio: Float
  ) {
    this.gesturesManager = gesturesManager
    internalSettings = GesturesAttributeParser.parseGesturesSettings(context, attrs, pixelRatio)
  }

  /**
   * Provides all map delegate instances.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    this.mapTransformDelegate = delegateProvider.mapTransformDelegate
    this.mapCameraManagerDelegate = delegateProvider.mapCameraManagerDelegate
    this.mapPluginProviderDelegate = delegateProvider.mapPluginProviderDelegate
    @Suppress("UNCHECKED_CAST")
    this.cameraAnimationsPlugin = delegateProvider.mapPluginProviderDelegate.getPlugin(
      Class.forName(
        PLUGIN_CAMERA_ANIMATIONS_CLASS_NAME
      ) as Class<CameraAnimationsPlugin>
    )
      ?: throw InvalidPluginConfigurationException(
        "Can't look up an instance of plugin, " +
          "is it available on the clazz path and loaded through the map?"
      )
  }

  /**
   * Called when the plugin is first added to the map.
   */
  override fun initialize() {
    initializeGesturesManager(gesturesManager, true)
    initializeGestureListeners(context, true)
  }
}

/**
 * Extension val for MapView to get the Gestures plugin instance.
 */
val MapPluginProviderDelegate.gestures: GesturesPlugin
  get() = this.getPlugin(GesturesPluginImpl::class.java)!!

/**
 * Add a callback that is invoked when the map is clicked.
 */
fun MapPluginExtensionsDelegate.addOnMapClickListener(onMapClickListener: OnMapClickListener) {
  gesturesPlugin { addOnMapClickListener(onMapClickListener) }
}

/**
 * Remove a callback that is invoked when the map is clicked.
 */
fun MapPluginExtensionsDelegate.removeOnMapClickListener(onMapClickListener: OnMapClickListener) {
  gesturesPlugin { removeOnMapClickListener(onMapClickListener) }
}

/**
 * Add a callback that is invoked when the map is long clicked.
 */
fun MapPluginExtensionsDelegate.addOnMapLongClickListener(onMapLongClickListener: OnMapLongClickListener) {
  gesturesPlugin { addOnMapLongClickListener(onMapLongClickListener) }
}

/**
 * Remove a callback that is invoked when the map is long clicked.
 */
fun MapPluginExtensionsDelegate.removeOnMapLongClickListener(onMapLongClickListener: OnMapLongClickListener) {
  gesturesPlugin { removeOnMapLongClickListener(onMapLongClickListener) }
}

/**
 * Add a callback that is invoked when the map is has received a fling gesture.
 */
fun MapPluginExtensionsDelegate.addOnFlingListener(onFlingListener: OnFlingListener) {
  gesturesPlugin { addOnFlingListener(onFlingListener) }
}

/**
 * Remove a callback that is invoked when the map is has received a fling gesture.
 */
fun MapPluginExtensionsDelegate.removeOnFlingListener(onFlingListener: OnFlingListener) {
  gesturesPlugin { removeOnFlingListener(onFlingListener) }
}

/**
 * Add a callback that is invoked when the map is moved.
 */
fun MapPluginExtensionsDelegate.addOnMoveListener(listener: OnMoveListener) {
  gesturesPlugin { addOnMoveListener(listener) }
}

/**
 * Remove a callback that is invoked when the map is moved.
 */
fun MapPluginExtensionsDelegate.removeOnMoveListener(listener: OnMoveListener) {
  gesturesPlugin { removeOnMoveListener(listener) }
}

/**
 * Add a callback that is invoked when the map is rotated.
 */
fun MapPluginExtensionsDelegate.addOnRotateListener(listener: OnRotateListener) {
  gesturesPlugin { addOnRotateListener(listener) }
}

/**
 * Remove a callback that is invoked when the map is rotated.
 */
fun MapPluginExtensionsDelegate.removeOnRotateListener(listener: OnRotateListener) {
  gesturesPlugin { removeOnRotateListener(listener) }
}

/**
 * Add a callback that is invoked when the map is scaled.
 */
fun MapPluginExtensionsDelegate.addOnScaleListener(listener: OnScaleListener) {
  gesturesPlugin { addOnScaleListener(listener) }
}

/**
 * Remove a callback that is invoked when the map is scaled.
 */
fun MapPluginExtensionsDelegate.removeOnScaleListener(listener: OnScaleListener) {
  gesturesPlugin { removeOnScaleListener(listener) }
}

/**
 * Add a callback that is invoked when the map is shoved.
 */
fun MapPluginExtensionsDelegate.addOnShoveListener(listener: OnShoveListener) {
  gesturesPlugin { addOnShoveListener(listener) }
}

/**
 * Remove a callback that is invoked when the map is shoved.
 */
fun MapPluginExtensionsDelegate.removeOnShoveListener(listener: OnShoveListener) {
  gesturesPlugin { removeOnShoveListener(listener) }
}

/**
 * Get the current configured AndroidGesturesManager.
 */
fun MapPluginExtensionsDelegate.getGesturesManager(): AndroidGesturesManager? {
  return gesturesPlugin { getGesturesManager() } as AndroidGesturesManager?
}

/**
 * Set the AndroidGesturesManager instance.
 */
fun MapPluginExtensionsDelegate.setGesturesManager(
  androidGesturesManager: AndroidGesturesManager,
  attachDefaultListeners: Boolean,
  setDefaultMutuallyExclusives: Boolean
) {
  gesturesPlugin {
    setGesturesManager(
      androidGesturesManager,
      attachDefaultListeners,
      setDefaultMutuallyExclusives
    )
  }
}

/**
 * The gesture configuration object.
 */
fun MapPluginExtensionsDelegate.getGesturesSettings() = gesturesPlugin { getSettings() } as GesturesSettings?