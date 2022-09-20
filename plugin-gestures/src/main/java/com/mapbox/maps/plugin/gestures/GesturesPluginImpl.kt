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
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.core.animation.addListener
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.mapbox.android.gestures.*
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.logE
import com.mapbox.maps.plugin.InvalidPluginConfigurationException
import com.mapbox.maps.plugin.MapStyleObserverPlugin
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_CAMERA_PLUGIN_ID
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.MapAnimationOwnerRegistry
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import com.mapbox.maps.plugin.gestures.generated.GesturesAttributeParser
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.gestures.generated.GesturesSettingsBase
import com.mapbox.maps.threading.AnimationThreadController.postOnMainThread
import java.util.*
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.math.*

/**
 * Manages gestures events on a MapView.
 */
class GesturesPluginImpl : GesturesPlugin, GesturesSettingsBase, MapStyleObserverPlugin {

  private val context: Context
  private var pixelRatio: Float = 1f

  private lateinit var gesturesManager: AndroidGesturesManager
  private var style: StyleInterface? = null

  private lateinit var mapTransformDelegate: MapTransformDelegate
  private lateinit var mapCameraManagerDelegate: MapCameraManagerDelegate
  private lateinit var mapProjectionDelegate: MapProjectionDelegate
  private lateinit var mapPluginProviderDelegate: MapPluginProviderDelegate
  private lateinit var cameraAnimationsPlugin: CameraAnimationsPlugin

  private val protectedCameraAnimatorOwners = CopyOnWriteArraySet<String>()

  // Listeners
  private val onMapClickListeners = CopyOnWriteArraySet<OnMapClickListener>()
  private val onMapLongClickListeners = CopyOnWriteArraySet<OnMapLongClickListener>()
  private val onFlingListeners = CopyOnWriteArraySet<OnFlingListener>()
  private val onMoveListeners = CopyOnWriteArraySet<OnMoveListener>()
  private val onRotateListeners = CopyOnWriteArraySet<OnRotateListener>()
  private val onScaleListeners = CopyOnWriteArraySet<OnScaleListener>()
  private val onShoveListeners = CopyOnWriteArraySet<OnShoveListener>()

  // FocalPoint
  private var doubleTapFocalPoint = ScreenCoordinate(0.0, 0.0)
  @VisibleForTesting(otherwise = PRIVATE)
  internal var centerScreen = doubleTapFocalPoint

  // Scale
  private var minimumGestureSpeed: Float = 0f
  private var minimumAngledGestureSpeed: Float = 0f
  private var minimumVelocity: Float = 0f
  private var scaleVelocityRatioThreshold: Double = 0.0
  private var quickZoom: Boolean = false
  private var spanSinceLast: Float = 0.0f
  private var screenHeight: Double = 0.0
  private var startZoom: Double = 0.0
  private var scaleCachedAnchor: ScreenCoordinate? = null
  private var dragInProgress: Boolean = false

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
  private var gesturesInterpolator = LinearOutSlowInInterpolator()

  // Devices with API <= 23 animating with duration = 0 will send initial value from Looper with
  // some small delay as a result.
  // Hence multiple immediate animations sent close to each other may cancel previous ones.
  // This becomes visible with move gesture since animation steps are relative to each other.
  // So we defer move distances that are not applied to apply later (when immediate ease will end).
  private var immediateEaseInProcess = false
  private var deferredMoveDistanceX: Float = 0f
  private var deferredMoveDistanceY: Float = 0f
  private var deferredZoomBy: Double = 0.0
  private var deferredRotate: Double = 0.0
  private var deferredShove: Double = 0.0

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
            override fun onAnimationEnd(animation: Animator) {
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
  private val animationsTimeoutHandler: Handler
  private var mainHandler: Handler? = null
  internal var doubleTapRegistered: Boolean = false

  override var internalSettings: GesturesSettings

  constructor(
    context: Context,
    pixelRatio: Float
  ) {
    this.context = context
    this.pixelRatio = pixelRatio
    internalSettings = GesturesAttributeParser.parseGesturesSettings(context, null, pixelRatio)
    mainHandler = Handler(Looper.getMainLooper())
    animationsTimeoutHandler = Handler(Looper.getMainLooper())
  }

  constructor(
    context: Context,
    attributeSet: AttributeSet,
    pixelRatio: Float
  ) {
    this.context = context
    this.pixelRatio = pixelRatio
    internalSettings =
      GesturesAttributeParser.parseGesturesSettings(context, attributeSet, pixelRatio)
    mainHandler = Handler(Looper.getMainLooper())
    animationsTimeoutHandler = Handler(Looper.getMainLooper())
  }

  @VisibleForTesting
  internal constructor(
    context: Context,
    attributeSet: AttributeSet,
    style: StyleInterface
  ) {
    this.context = context
    this.pixelRatio = 1.0f
    internalSettings =
      GesturesAttributeParser.parseGesturesSettings(context, attributeSet, pixelRatio)
    mainHandler = Handler(Looper.getMainLooper())
    animationsTimeoutHandler = Handler(Looper.getMainLooper())
    this.style = style
  }

  @VisibleForTesting
  internal constructor(
    context: Context,
    attributeSet: AttributeSet,
    pixelRatio: Float,
    mainHandler: Handler,
    animationsTimeoutHandler: Handler
  ) {
    this.context = context
    this.pixelRatio = pixelRatio
    this.internalSettings =
      GesturesAttributeParser.parseGesturesSettings(context, attributeSet, pixelRatio)
    this.mainHandler = mainHandler
    this.animationsTimeoutHandler = animationsTimeoutHandler
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
    gesturesManager.rotateGestureDetector.angleThreshold = ROTATION_ANGLE_THRESHOLD
    gesturesManager.shoveGestureDetector.maxShoveAngle = MAX_SHOVE_ANGLE
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
  }

  private fun unregisterScheduledAnimators(animators: Array<ValueAnimator>?) =
    animators?.let { cameraAnimationsPlugin.unregisterAnimators(*it) }

  /**
   * Schedules a velocity animator to be executed when user lifts fingers.
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
          if (!internalSettings.pinchToZoomEnabled) {
            return false
          }

          // Cancel any animation
          cameraAnimationsPlugin.cancelAllAnimators(protectedCameraAnimatorOwners.toList())

          // Get the vertical scroll amount, one click = 1
          val scrollDist = event.getAxisValue(MotionEvent.AXIS_VSCROLL)

          // Scale the map by the appropriate power of two factor
          val currentZoom = mapCameraManagerDelegate.cameraState.zoom
          val cachedAnchor = cameraAnimationsPlugin.anchor
          val anchor = event.toScreenCoordinate()
          val zoom =
            cameraAnimationsPlugin.calculateScaleBy(scrollDist.toDouble(), currentZoom)
          easeToImmediately(
            CameraOptions.Builder().anchor(anchor).zoom(zoom).build(),
            actionAfter = { cameraAnimationsPlugin.anchor = cachedAnchor }
          )

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
    override fun onDown(motionEvent: MotionEvent?): Boolean {
      return true
    }

    /**
     * Called when an on single tap up gesture was detected.
     */
    override fun onSingleTapUp(motionEvent: MotionEvent?): Boolean {
      return handleSingleTapUpEvent()
    }

    /**
     * Called when an on single tap up confirmed gesture was detected.
     */
    override fun onSingleTapConfirmed(motionEvent: MotionEvent?): Boolean {
      if (motionEvent == null) {
        return false
      }
      return handleClickEvent(motionEvent.toScreenCoordinate())
    }

    /**
     * Called when an on double tap gesture was detected.
     */
    override fun onDoubleTapEvent(motionEvent: MotionEvent?): Boolean {
      if (motionEvent == null) {
        return false
      }
      return if (handleDoubleTapEvent(motionEvent, doubleTapMovementThreshold)) {
        true
      } else super.onDoubleTapEvent(motionEvent)
    }

    /**
     * Called when an on long press gesture was detected.
     */
    override fun onLongPress(motionEvent: MotionEvent?) {
      if (motionEvent == null) {
        return
      }

      handleLongPressEvent(motionEvent.toScreenCoordinate())
    }

    /**
     * Called when an on fling gesture was detected.
     */
    override fun onFling(
      e1: MotionEvent?,
      e2: MotionEvent?,
      velocityX: Float,
      velocityY: Float
    ): Boolean {
      if (e1 == null || e2 == null) {
        return false
      }

      return handleFlingEvent(e2, velocityX, velocityY)
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
      doubleTapFocalPoint
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
    if (!internalSettings.pinchToZoomDecelerationEnabled || velocityXY < minimumVelocity || spanSinceLast / velocityXY < scaleVelocityRatioThreshold
    ) {
      return
    }

    val zoomAddition = calculateScale(velocityXY.toDouble(), detector.isScalingOut)
    val currentZoom = mapCameraManagerDelegate.cameraState.zoom
    val focalPoint = getScaleFocalPoint(detector)
    // (log(x + 1 / e^2) + 2) * 150, x=0 to 2.5 (MapboxConstants#MAX_ABSOLUTE_SCALE_VELOCITY_CHANGE)
    val animationTime = (
      (
        ln(
          abs(zoomAddition) + 1 / Math.E.pow(2.0)
        ) + 2
        ) * SCALE_VELOCITY_ANIMATION_DURATION_MULTIPLIER
      ).toLong()
    val animators =
      createScaleAnimators(currentZoom, zoomAddition, focalPoint, animationTime)
    scaleAnimators = animators
    scheduleAnimators(animators)
  }

  internal fun handleScale(detector: StandardScaleGestureDetector): Boolean {
    // in order not to mess up initial anchor values
    if (!internalSettings.simultaneousRotateAndPinchToZoomEnabled && immediateEaseInProcess) {
      deferredZoomBy += calculateZoomBy(detector)
      return true
    }
    val focalPoint = getScaleFocalPoint(detector)
    scaleCachedAnchor = cameraAnimationsPlugin.anchor
    if (quickZoom) {
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
      targetZoom *= internalSettings.zoomAnimationAmount.toDouble()
      easeToImmediately(
        CameraOptions.Builder()
          .zoom(targetZoom)
          .anchor(focalPoint)
          .build(),
        actionAfter = {
          onScaleAnimationEnd(detector)
        }
      )
    } else {
      val zoomBy = calculateZoomBy(detector)
      if (internalSettings.simultaneousRotateAndPinchToZoomEnabled) {
        val zoom = cameraAnimationsPlugin.createZoomAnimator(
          cameraAnimatorOptions(mapCameraManagerDelegate.cameraState.zoom + zoomBy) {
            startValue(mapCameraManagerDelegate.cameraState.zoom)
            owner(MapAnimationOwnerRegistry.GESTURES)
          }
        ) {
          duration = 0
        }
        zoom.addListener(
          onEnd = {
            onScaleAnimationEnd(detector)
          }
        )
        val anchorAnimator = cameraAnimationsPlugin.createAnchorAnimator(
          options = cameraAnimatorOptions(focalPoint) {
            owner(MapAnimationOwnerRegistry.GESTURES)
          },
        ) {
          duration = 0
        }
        cameraAnimationsPlugin.playAnimatorsTogether(zoom, anchorAnimator)
      } else {
        easeToImmediately(
          CameraOptions.Builder()
            .zoom(mapCameraManagerDelegate.cameraState.zoom + zoomBy + deferredZoomBy)
            .anchor(focalPoint)
            .build(),
          actionAfter = {
            onScaleAnimationEnd(detector)
          }
        )
        deferredZoomBy = 0.0
      }
    }
    return true
  }

  private fun onScaleAnimationEnd(detector: StandardScaleGestureDetector) {
    cameraAnimationsPlugin.anchor = scaleCachedAnchor
    notifyOnScaleListeners(detector)
    spanSinceLast = abs(detector.currentSpan - detector.previousSpan)
  }

  internal fun handleScaleBegin(detector: StandardScaleGestureDetector): Boolean {
    quickZoom = detector.pointersCount == 1
    // Reset deferred animation values here in onBegin, ignores the last possible defererred value
    // before the onEnd. However it's not noticeable since onEnd animates using velocity.
    // Allows to keep things simple.
    deferredZoomBy = 0.0

    if (quickZoom) {
      if (!internalSettings.quickZoomEnabled) {
        return false
      }
      // re-try disabling the move detector in case double tap has been interrupted before quickzoom started
      gesturesManager.moveGestureDetector.isEnabled = false
    } else {
      if (!internalSettings.pinchToZoomEnabled) {
        return false
      }
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

          // disable rotate gesture when scale is detected first
          gesturesManager.rotateGestureDetector.isEnabled = false
        }
      } else {
        return false
      }
    }

    screenHeight = Resources.getSystem().displayMetrics.heightPixels.toDouble()
    startZoom = mapCameraManagerDelegate.cameraState.zoom

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

    val bearingCurrent = mapCameraManagerDelegate.cameraState.bearing
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
    if (internalSettings.increasePinchToZoomThresholdWhenRotating) {
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

    if (!internalSettings.rotateDecelerationEnabled || abs(angularVelocity) < minimumAngularVelocity || gesturesManager.standardScaleGestureDetector.isInProgress() && ratio < rotateVelocityRatioThreshold) {
      // notifying listeners that camera is idle only if there is no follow-up animation
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
    if (!internalSettings.simultaneousRotateAndPinchToZoomEnabled && immediateEaseInProcess) {
      deferredRotate += rotationDegreesSinceLast
      return true
    }
    // Calculate map bearing value
    val currentBearing = mapCameraManagerDelegate.cameraState.bearing
    rotateCachedAnchor = cameraAnimationsPlugin.anchor
    val bearing = currentBearing + rotationDegreesSinceLast + deferredRotate
    deferredRotate = 0.0
    val focalPoint = getRotateFocalPoint(detector)
    // Rotate the map
    if (internalSettings.simultaneousRotateAndPinchToZoomEnabled) {
      val bearingAnimator =
        cameraAnimationsPlugin.createBearingAnimator(
          cameraAnimatorOptions(bearing) {
            owner(MapAnimationOwnerRegistry.GESTURES)
          }
        ) {
          duration = 0
        }
      bearingAnimator.addListener(
        onEnd = {
          onRotateAnimationEnd(detector)
        }
      )
      val anchorAnimator = cameraAnimationsPlugin.createAnchorAnimator(
        options = cameraAnimatorOptions(focalPoint) {
          owner(MapAnimationOwnerRegistry.GESTURES)
        },
      ) {
        duration = 0
      }
      cameraAnimationsPlugin.playAnimatorsTogether(bearingAnimator, anchorAnimator)
    } else {
      easeToImmediately(
        CameraOptions.Builder()
          .anchor(focalPoint)
          .bearing(bearing)
          .build(),
        actionAfter = {
          onRotateAnimationEnd(detector)
        }
      )
    }
    return true
  }

  private fun onRotateAnimationEnd(detector: RotateGestureDetector) {
    cameraAnimationsPlugin.anchor = rotateCachedAnchor
    notifyOnRotateListeners(detector)
  }

  internal fun handleRotateBegin(detector: RotateGestureDetector): Boolean {
    if (!internalSettings.rotateEnabled) {
      return false
    }

    // Reset deferred animation values here in onBegin, ignores the last possible defererred value
    // before the onEnd. However it's not noticeable since onEnd animates using velocity.
    // Allows to keep things simple.
    deferredRotate = 0.0

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

    if (internalSettings.increasePinchToZoomThresholdWhenRotating) {
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

    // Reset deferred animation values here in onBegin, ignores the last possible defererred value
    // before the onEnd. However it's not noticeable since onEnd animates using velocity.
    // Allows to keep things simple.
    deferredShove = 0.0

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
    if (immediateEaseInProcess) {
      deferredShove += deltaPixelsSinceLast
      return true
    }
    // Get pitch value (scale and clamp)
    var pitch = mapCameraManagerDelegate.cameraState.pitch
    val optimizedPitch =
      pitch - (SHOVE_PIXEL_CHANGE_FACTOR * (deltaPixelsSinceLast + deferredShove))
    deferredShove = 0.0
    pitch = clamp(optimizedPitch, MINIMUM_PITCH, MAXIMUM_PITCH)
    // changing pitch always should happen relative to the screen center otherwise camera may fly away
    easeToImmediately(
      CameraOptions.Builder().anchor(centerScreen).pitch(pitch).build(),
      actionAfter = { notifyOnShoveListeners(detector) }
    )
    return true
  }

  internal fun handleShoveEnd(
    detector: ShoveGestureDetector
  ) {
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
      if (!internalSettings.doubleTouchToZoomOutEnabled || pointersCount != 2) {
        return false
      }

      cameraAnimationsPlugin.cancelAllAnimators(protectedCameraAnimatorOwners.toList())

      val zoomFocalPoint: ScreenCoordinate
      // Single finger double tap
      internalSettings.focalPoint?.let {
        // zoom in on user focal point
        zoomFocalPoint = it
        animateZoomOut(zoomFocalPoint, false)
        return true
      }

      // Zoom in on gesture
      val pointF = detector.focalPoint
      zoomFocalPoint = ScreenCoordinate(pointF.x.toDouble(), pointF.y.toDouble())
      animateZoomOut(zoomFocalPoint, false)
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

    zoomAnimator.addListener(object : AnimatorListenerAdapter() {
      override fun onAnimationStart(animation: Animator) {
        super.onAnimationStart(animation)
        // notify scale gesture started when zoom animation starts.
        notifyOnScaleListeners(gesturesManager.standardScaleGestureDetector)
      }

      override fun onAnimationEnd(animation: Animator) {
        // notify scale gesture ended when zoom animation finished.
        notifyOnScaleEndListeners(gesturesManager.standardScaleGestureDetector)
      }
    })

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
  private fun animateZoomIn(zoomFocalPoint: ScreenCoordinate, runImmediately: Boolean) {
    handleZoomAnimation(true, zoomFocalPoint, runImmediately)
  }

  /**
   * Zoom out by 1.
   *
   * @param zoomFocalPoint focal point of zoom animation
   * @param runImmediately if true, animation will be started right away, otherwise it will wait until
   * [MotionEvent.ACTION_UP] is registered.
   */
  internal fun animateZoomOut(zoomFocalPoint: ScreenCoordinate, runImmediately: Boolean) {
    handleZoomAnimation(false, zoomFocalPoint, runImmediately)
  }

  /**
   * handle zoom animation and notify scale gesture events.
   * @param zoomIn true for zoom-in, false for zoom-out
   * @param zoomFocalPoint focal point of zoom animation
   * @param runImmediately if true, animation will be started right away, otherwise it will wait until
   * [MotionEvent.ACTION_UP] is registered.
   */
  internal fun handleZoomAnimation(
    zoomIn: Boolean,
    zoomFocalPoint: ScreenCoordinate,
    runImmediately: Boolean
  ) {
    // canceling here as well, because when using a button it will not be canceled automatically by onDown()
    unregisterScheduledAnimators(scaleAnimators)
    // notify scale gesture is starting.
    notifyOnScaleBeginListeners(gesturesManager.standardScaleGestureDetector)
    val currentZoom = mapCameraManagerDelegate.cameraState.zoom
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

  private fun cancelTransitionsIfRequired() {
    // we need to cancel core transitions only if there is no started gesture yet
    if (noGesturesInProgress()) {
      cameraAnimationsPlugin.cancelAllAnimators(protectedCameraAnimatorOwners.toList())
    }
  }

  private fun noGesturesInProgress(): Boolean {
    return (
      (!internalSettings.scrollEnabled || !gesturesManager.moveGestureDetector.isInProgress) &&
        ((!internalSettings.pinchToZoomEnabled && !internalSettings.doubleTouchToZoomOutEnabled && !internalSettings.doubleTapToZoomInEnabled) || !gesturesManager.standardScaleGestureDetector.isInProgress)
      ) &&
      (!internalSettings.rotateEnabled || !gesturesManager.rotateGestureDetector.isInProgress) &&
      (!internalSettings.pitchEnabled || !gesturesManager.shoveGestureDetector.isInProgress)
  }

  internal fun handleLongPressEvent(screenCoordinate: ScreenCoordinate) {
    if (!onMapLongClickListeners.isEmpty()) {
      val clickedPoint = mapCameraManagerDelegate.coordinateForPixel(screenCoordinate)
      for (listener in onMapLongClickListeners) {
        if (listener.onMapLongClick(clickedPoint)) {
          return
        }
      }
    }
  }

  internal fun handleClickEvent(screenCoordinate: ScreenCoordinate): Boolean {
    if (!onMapClickListeners.isEmpty()) {
      val clickedPoint = mapCameraManagerDelegate.coordinateForPixel(screenCoordinate)
      for (listener in onMapClickListeners) {
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
      doubleTapFocalPoint = motionEvent.toScreenCoordinate()
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

      if (!internalSettings.doubleTapToZoomInEnabled) {
        return false
      }

      internalSettings.focalPoint?.let {
        // User provided focal point
        doubleTapFocalPoint = it
      }

      animateZoomIn(doubleTapFocalPoint, false)
      return true
    }
    return false
  }

  internal fun handleFlingEvent(
    e2: MotionEvent,
    velocityX: Float,
    velocityY: Float
  ): Boolean {
    if (!internalSettings.scrollEnabled) {
      // don't allow a fling if scroll is disabled
      return false
    }

    if (isPointAboveHorizon(e2.toScreenCoordinate())) {
      return false
    }

    notifyOnFlingListeners()

    if (!internalSettings.scrollDecelerationEnabled) {
      return false
    }

    val screenDensity = pixelRatio

    // calculate velocity vector for xy dimensions, independent from screen size
    val velocityXY =
      hypot((velocityX / screenDensity).toDouble(), (velocityY / screenDensity).toDouble())
    if (velocityXY < VELOCITY_THRESHOLD_IGNORE_FLING) {
      // ignore short flings, these can occur when other gestures just have finished executing
      return false
    }

    val pitch = mapCameraManagerDelegate.cameraState.pitch

    // We limit the amount of fling displacement based on the camera pitch value.
    val pitchFactorAdditionalComponent = when {
      pitch < NORMAL_MAX_PITCH -> {
        pitch / 10.0
      }
      pitch in NORMAL_MAX_PITCH..MAXIMUM_PITCH -> {
        val a = ln(NORMAL_MAX_PITCH / 10.0)
        val b = ln(MAX_FLING_PITCH_FACTOR)
        // exp(a) = pitch / 10.0
        // exp(b) = pitch
        exp((b - a) * (pitch - NORMAL_MAX_PITCH) / (MAXIMUM_PITCH - NORMAL_MAX_PITCH) + a)
      }
      else -> 0.0
    }
    val pitchFactor =
      FLING_LIMITING_FACTOR + pitchFactorAdditionalComponent / screenDensity.toDouble()

    val offsetX =
      if (internalSettings.isScrollHorizontallyLimited()) 0.0 else velocityX.toDouble() / pitchFactor
    val offsetY =
      if (internalSettings.isScrollVerticallyLimited()) 0.0 else velocityY.toDouble() / pitchFactor

    cameraAnimationsPlugin.cancelAllAnimators(protectedCameraAnimatorOwners.toList())

    // calculate animation time based on displacement
    // velocityXY ranges from VELOCITY_THRESHOLD_IGNORE_FLING to ~5000
    // limit animation time to Android SDK default animation time
    val animationTime = (velocityXY / pitchFactor).toLong()

    // start the fling animation from a simulated touch point at the bottom of the display to reduce the fling speed at high pitch level.
    val simulateTouchPoint = ScreenCoordinate(centerScreen.x, centerScreen.y * 2.0)
    cameraAnimationsPlugin.easeTo(
      mapCameraManagerDelegate.getDragCameraOptions(
        simulateTouchPoint,
        ScreenCoordinate(simulateTouchPoint.x + offsetX, simulateTouchPoint.y + offsetY)
      ),
      mapAnimationOptions {
        owner(MapAnimationOwnerRegistry.GESTURES)
        duration(animationTime)
        interpolator(gesturesInterpolator)
        animatorListener(object : AnimatorListenerAdapter() {

          override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            postOnMainThread { mapCameraManagerDelegate.dragEnd() }
          }
        })
      }
    )
    return true
  }

  internal fun handleMoveStartEvent(detector: MoveGestureDetector): Boolean {
    if (!internalSettings.scrollEnabled) {
      return false
    }

    // Reset deferred animation values here in onBegin, ignores the last possible defererred value
    // before the onEnd. However it's not noticeable since onEnd animates using velocity.
    // Allows to keep things simple.
    deferredMoveDistanceX = 0f
    deferredMoveDistanceY = 0f

    cancelTransitionsIfRequired()
    notifyOnMoveBeginListeners(detector)
    return true
  }

  @VisibleForTesting(otherwise = PRIVATE)
  internal fun isPointAboveHorizon(
    pixel: ScreenCoordinate
  ): Boolean {
    // as we don't depend on style extension - working with raw values here
    style?.getStyleProjectionProperty("name").apply {
      if (this == null) {
        return false
      }
      val currentProjection = if (kind == StylePropertyValueKind.UNDEFINED) {
        "MERCATOR"
      } else {
        (value.contents as String).uppercase()
      }
      if (currentProjection != "MERCATOR") {
        return false
      }
    }
    // Prevent drag start in area around horizon to avoid sharp map movements
    val topMapMargin = 0.04 * mapTransformDelegate.getSize().height
    val reprojectErrorMargin = min(10.0, topMapMargin / 2)

    // sanitize input x to be non NaN
    var pointX = pixel.x
    if (pointX.isNaN()) {
      logE(TAG, "isPointAboveHorizon: screen coordinate x is NaN.")
      pointX = 0.0
    }

    // sanitize input y to be non NaN
    var pointY = pixel.y
    if (pointY.isNaN()) {
      logE(TAG, "isPointAboveHorizon: screen coordinate y is NaN.")
      pointY = 0.0
    }

    val point = ScreenCoordinate(pointX, pointY - topMapMargin)
    val coordinate = mapCameraManagerDelegate.coordinateForPixel(point)
    val roundtripPoint = mapCameraManagerDelegate.pixelForCoordinate(coordinate)
    return (roundtripPoint.y >= point.y + reprojectErrorMargin)
  }

  @VisibleForTesting(otherwise = PRIVATE)
  internal fun calculateZoomBy(standardScaleGestureDetector: StandardScaleGestureDetector) =
    ln(standardScaleGestureDetector.scaleFactor.toDouble()) /
      ln(PI / 2) *
      ZOOM_RATE.toDouble() *
      internalSettings.zoomAnimationAmount.toDouble()

  internal fun handleMove(
    detector: MoveGestureDetector,
    distanceX: Float,
    distanceY: Float
  ): Boolean {
    // first move event is often delivered with no displacement
    if (distanceX != 0f || distanceY != 0f) {
      if (notifyOnMoveListeners(detector)) {
        return true
      }

      // Skip 3 or more fingers pan
      if (detector.pointersCount > 2) {
        return false
      }

      // Skip 2-finger scroll if pinchScrollEnabled is disabled.
      if (!internalSettings.pinchScrollEnabled && detector.pointersCount > 1) {
        return false
      }

      val focalPoint = detector.focalPoint
      val fromX = focalPoint.x.toDouble()
      val fromY = focalPoint.y.toDouble()

      // Skip invalid focal points with non-finite values
      if (!focalPoint.x.isFinite() || !focalPoint.y.isFinite()) {
        logE(TAG, "Invalid focal point=$focalPoint to perform map panning!")
        return false
      }

      if (!dragInProgress) {
        if (isPointAboveHorizon(ScreenCoordinate(fromX, fromY))) {
          return false
        }
        dragInProgress = true
        mapCameraManagerDelegate.dragStart(ScreenCoordinate(fromX, fromY))
      }
      if (immediateEaseInProcess) {
        deferredMoveDistanceX += distanceX
        deferredMoveDistanceY += distanceY
        return true
      }
      val resolvedDistanceX =
        if (internalSettings.isScrollHorizontallyLimited()) 0.0 else (distanceX.toDouble() + deferredMoveDistanceX)
      val resolvedDistanceY =
        if (internalSettings.isScrollVerticallyLimited()) 0.0 else (distanceY.toDouble() + deferredMoveDistanceY)
      deferredMoveDistanceX = 0f
      deferredMoveDistanceY = 0f

      val toX = fromX - resolvedDistanceX
      val toY = fromY - resolvedDistanceY

      val cameraOptions = mapCameraManagerDelegate.getDragCameraOptions(
        ScreenCoordinate(fromX, fromY),
        ScreenCoordinate(toX, toY)
      )
      easeToImmediately(cameraOptions)
    }
    return true
  }

  internal fun handleMoveEnd(detector: MoveGestureDetector) {
    dragInProgress = false
    notifyOnMoveEndListeners(detector)
  }

  internal fun handleSingleTapUpEvent(): Boolean {
    cameraAnimationsPlugin.cancelAllAnimators(protectedCameraAnimatorOwners.toList())
    return true
  }

  private fun notifyOnFlingListeners() {
    for (listener in onFlingListeners) {
      listener.onFling()
    }
  }

  private fun notifyOnMoveBeginListeners(detector: MoveGestureDetector) {
    for (listener in onMoveListeners) {
      listener.onMoveBegin(detector)
    }
  }

  private fun notifyOnMoveListeners(detector: MoveGestureDetector): Boolean {
    for (listener in onMoveListeners) {
      if (listener.onMove(detector)) {
        return true
      }
    }
    return false
  }

  private fun notifyOnMoveEndListeners(detector: MoveGestureDetector) {
    for (listener in onMoveListeners) {
      listener.onMoveEnd(detector)
    }
  }

  private fun notifyOnRotateBeginListeners(detector: RotateGestureDetector) {
    for (listener in onRotateListeners) {
      listener.onRotateBegin(detector)
    }
  }

  private fun notifyOnRotateListeners(detector: RotateGestureDetector) {
    for (listener in onRotateListeners) {
      listener.onRotate(detector)
    }
  }

  private fun notifyOnRotateEndListeners(detector: RotateGestureDetector) {
    for (listener in onRotateListeners) {
      listener.onRotateEnd(detector)
    }
  }

  private fun notifyOnScaleBeginListeners(detector: StandardScaleGestureDetector) {
    for (listener in onScaleListeners) {
      listener.onScaleBegin(detector)
    }
  }

  private fun notifyOnScaleListeners(detector: StandardScaleGestureDetector) {
    for (listener in onScaleListeners) {
      listener.onScale(detector)
    }
  }

  private fun notifyOnScaleEndListeners(detector: StandardScaleGestureDetector) {
    for (listener in onScaleListeners) {
      listener.onScaleEnd(detector)
    }
  }

  private fun notifyOnShoveBeginListeners(detector: ShoveGestureDetector) {
    for (listener in onShoveListeners) {
      listener.onShoveBegin(detector)
    }
  }

  private fun notifyOnShoveListeners(detector: ShoveGestureDetector) {
    for (listener in onShoveListeners) {
      listener.onShove(detector)
    }
  }

  private fun notifyOnShoveEndListeners(detector: ShoveGestureDetector) {
    for (listener in onShoveListeners) {
      listener.onShoveEnd(detector)
    }
  }

  /**
   * Add a callback that is invoked when the map is clicked.
   */
  override fun addOnMapClickListener(onMapClickListener: OnMapClickListener) {
    onMapClickListeners.add(onMapClickListener)
  }

  /**
   * Remove a callback that is invoked when the map is clicked.
   */
  override fun removeOnMapClickListener(onMapClickListener: OnMapClickListener) {
    onMapClickListeners.remove(onMapClickListener)
  }

  /**
   * Add a callback that is invoked when the map is long clicked.
   */
  override fun addOnMapLongClickListener(onMapLongClickListener: OnMapLongClickListener) {
    onMapLongClickListeners.add(onMapLongClickListener)
  }

  /**
   * Remove a callback that is invoked when the map is long clicked.
   */
  override fun removeOnMapLongClickListener(onMapLongClickListener: OnMapLongClickListener) {
    onMapLongClickListeners.remove(onMapLongClickListener)
  }

  /**
   * Add a callback that is invoked when the map is has received a fling gesture.
   */
  override fun addOnFlingListener(onFlingListener: OnFlingListener) {
    onFlingListeners.add(onFlingListener)
  }

  /**
   * Remove a callback that is invoked when the map is has received a fling gesture.
   */
  override fun removeOnFlingListener(onFlingListener: OnFlingListener) {
    onFlingListeners.remove(onFlingListener)
  }

  /**
   * Add a callback that is invoked when the map is moved.
   */
  override fun addOnMoveListener(onMoveListener: OnMoveListener) {
    onMoveListeners.add(onMoveListener)
  }

  /**
   * Remove a callback that is invoked when the map is moved.
   */
  override fun removeOnMoveListener(listener: OnMoveListener) {
    onMoveListeners.remove(listener)
  }

  /**
   * Add a callback that is invoked when the map is rotated.
   */
  override fun addOnRotateListener(onRotateListener: OnRotateListener) {
    onRotateListeners.add(onRotateListener)
  }

  /**
   * Remove a callback that is invoked when the map is rotated.
   */
  override fun removeOnRotateListener(listener: OnRotateListener) {
    onRotateListeners.remove(listener)
  }

  /**
   * Add a callback that is invoked when the map is scaled.
   */
  override fun addOnScaleListener(onScaleListener: OnScaleListener) {
    onScaleListeners.add(onScaleListener)
  }

  /**
   * Remove a callback that is invoked when the map is scaled.
   */
  override fun removeOnScaleListener(listener: OnScaleListener) {
    onScaleListeners.remove(listener)
  }

  /**
   * Add a callback that is invoked when the map is shoved.
   */
  override fun addOnShoveListener(onShoveListener: OnShoveListener) {
    onShoveListeners.add(onShoveListener)
  }

  /**
   * Remove a callback that is invoked when the map is shoved.
   */
  override fun removeOnShoveListener(listener: OnShoveListener) {
    onShoveListeners.remove(listener)
  }

  /**
   * Add animator owner (see [CameraAnimatorOptions.owner] or [MapAnimationOptions.owner]
   * which animation will not be canceled with when gesture animation is about to start.
   * When specified, you are responsible for listening to gesture interactions and canceling the specified owners' animations to avoid competing with gestures.
   */
  override fun addProtectedAnimationOwner(owner: String) {
    protectedCameraAnimatorOwners.add(owner)
  }

  /**
   * Remove animator owner (see [CameraAnimatorOptions.owner] or [MapAnimationOptions.owner])
   * which animation will not be canceled with when gesture animation is about to start.
   * When specified, you are responsible for listening to gesture interactions and canceling the specified owners' animations to avoid competing with gestures.
   */
  override fun removeProtectedAnimationOwner(owner: String) {
    protectedCameraAnimatorOwners.remove(owner)
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
    style = null
    protectedCameraAnimatorOwners.clear()
    mainHandler?.removeCallbacksAndMessages(null)
    animationsTimeoutHandler.removeCallbacksAndMessages(null)
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
    this.pixelRatio = pixelRatio
    internalSettings = GesturesAttributeParser.parseGesturesSettings(context, attrs, pixelRatio)
  }

  /**
   * Provides all map delegate instances.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    delegateProvider.getStyle {
      this.style = it
    }
    this.mapTransformDelegate = delegateProvider.mapTransformDelegate
    this.mapCameraManagerDelegate = delegateProvider.mapCameraManagerDelegate
    this.mapProjectionDelegate = delegateProvider.mapProjectionDelegate
    this.mapPluginProviderDelegate = delegateProvider.mapPluginProviderDelegate
    @Suppress("UNCHECKED_CAST")
    this.cameraAnimationsPlugin = delegateProvider.mapPluginProviderDelegate.getPlugin(
      MAPBOX_CAMERA_PLUGIN_ID
    ) ?: throw InvalidPluginConfigurationException(
      "Can't look up an instance of plugin, " +
        "is it available on the clazz path and loaded through the map?"
    )
  }

  /**
   * Called when new style is loaded.
   */
  override fun onStyleChanged(styleDelegate: StyleInterface) {
    style = styleDelegate
  }

  /**
   * Called when the plugin is first added to the map.
   */
  override fun initialize() {
    initializeGesturesManager(gesturesManager, true)
    initializeGestureListeners(context, true)
  }

  private companion object {
    private const val TAG = "Gestures"
    const val ROTATION_ANGLE_THRESHOLD = 3.0f
    const val MAX_SHOVE_ANGLE = 45.0f
  }
}

/**
 * Convert a motion event to ScreenCoordinate.
 */
private fun MotionEvent.toScreenCoordinate() = ScreenCoordinate(x.toDouble(), y.toDouble())