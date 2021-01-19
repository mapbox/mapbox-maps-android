package com.mapbox.maps.plugin.location

import android.content.Context
import android.location.Location
import android.view.MotionEvent
import androidx.annotation.Size
import androidx.annotation.VisibleForTesting
import com.mapbox.android.gestures.AndroidGesturesManager
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.android.gestures.RotateGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.plugin.InvalidPluginConfigurationException
import com.mapbox.maps.plugin.PLUGIN_CAMERA_ANIMATIONS_CLASS_NAME
import com.mapbox.maps.plugin.PLUGIN_GESTURE_CLASS_NAME
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.gestures.OnFlingListener
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.OnRotateListener
import com.mapbox.maps.plugin.location.listeneres.CancelableCallback
import com.mapbox.maps.plugin.location.listeneres.OnCameraTrackingChangedListener
import com.mapbox.maps.plugin.location.listeneres.OnLocationCameraTransitionListener
import com.mapbox.maps.plugin.location.modes.CameraMode

/**
 * [LocationCameraAnimatorCoordinator] wraps all Camera logic inside and acts as external Camera interface for the other Location plugin components.
 * All Camera manipulations go through this class and it is responsible for the gesture configurations to respect Camera tracking modes.
 * Encapsulates [LocationCameraAnimatorCoordinator] which creates, updates, and plays CameraAnimators with help of low-level CameraAPI
 */

internal class LocationCameraController {
  private val internalGesturesManager: AndroidGesturesManager
  private val delegateProvider: MapDelegateProvider
  private val internalCameraTrackingChangedListener: OnCameraTrackingChangedListener
  private lateinit var options: LocationComponentOptions

  private var mapTransformDelegate: MapTransformDelegate
  private val gesturePlugin: GesturesPlugin
  private val animationPlugin: CameraAnimationsPlugin
  private val initialGesturesManager: AndroidGesturesManager
  private val moveGestureDetector: MoveGestureDetector

  private val locationCameraAnimatorCoordinator: LocationCameraAnimatorCoordinator
  private var isEnabled = false

  var cameraMode: CameraMode = CameraMode.NONE
    private set

  var isTransitioning = false
    get() = locationCameraAnimatorCoordinator.isTransitioning
    private set

  constructor(
    context: Context?,
    delegateProvider: MapDelegateProvider,
    gesturePlugin: GesturesPlugin,
    animationPlugin: CameraAnimationsPlugin,
    internalCameraTrackingChangedListener: OnCameraTrackingChangedListener,
    options: LocationComponentOptions
  ) {
    this.internalGesturesManager = LocationGesturesManager(context)
    this.delegateProvider = delegateProvider
    this.internalCameraTrackingChangedListener = internalCameraTrackingChangedListener

    this.mapTransformDelegate = delegateProvider.mapTransformDelegate
    this.gesturePlugin = gesturePlugin
    this.animationPlugin = animationPlugin
    this.initialGesturesManager = gesturePlugin.getGesturesManager()
    this.moveGestureDetector = internalGesturesManager.moveGestureDetector

    gesturePlugin.addOnRotateListener(onRotateListener)
    gesturePlugin.addOnFlingListener(onFlingListener)
    gesturePlugin.addOnMoveListener(onMoveListener)
    initializeOptions(options)

    this.locationCameraAnimatorCoordinator = LocationCameraAnimatorCoordinator(
      delegateProvider.mapProjectionDelegate,
      delegateProvider.mapTransformDelegate,
      MapboxAnimatorSetProvider.instance,
      animationPlugin
    )
  }

  @VisibleForTesting
  constructor(
    internalGesturesManager: AndroidGesturesManager,
    delegateProvider: MapDelegateProvider,
    internalCameraTrackingChangedListener: OnCameraTrackingChangedListener
  ) {
    this.internalGesturesManager = internalGesturesManager
    this.delegateProvider = delegateProvider
    this.internalCameraTrackingChangedListener = internalCameraTrackingChangedListener

    this.mapTransformDelegate = delegateProvider.mapTransformDelegate
    this.gesturePlugin =
      delegateProvider.mapPluginProviderDelegate.getPlugin(PLUGIN_GESTURE_CLASS_NAME)
      ?: throw InvalidPluginConfigurationException(
        "Can't look up an instance of plugin, " +
          "is it available on the clazz path and loaded through the map?"
      )
    this.animationPlugin =
      delegateProvider.mapPluginProviderDelegate.getPlugin(PLUGIN_CAMERA_ANIMATIONS_CLASS_NAME)
      ?: throw InvalidPluginConfigurationException(
        "Can't look up an instance of plugin, " +
          "is it available on the clazz path and loaded through the map?"
      )
    this.initialGesturesManager = gesturePlugin.getGesturesManager()
    this.moveGestureDetector = internalGesturesManager.moveGestureDetector

    gesturePlugin.addOnRotateListener(onRotateListener)
    gesturePlugin.addOnFlingListener(onFlingListener)
    gesturePlugin.addOnMoveListener(onMoveListener)

    this.locationCameraAnimatorCoordinator = LocationCameraAnimatorCoordinator(
      delegateProvider.mapProjectionDelegate,
      delegateProvider.mapTransformDelegate,
      MapboxAnimatorSetProvider.instance,
      animationPlugin
    )
  }

  fun initializeOptions(options: LocationComponentOptions) {
    this.options = options
    if (options.trackingGesturesManagement()) {
      if (gesturePlugin.getGesturesManager() != internalGesturesManager) {
        gesturePlugin.setGesturesManager(
          internalGesturesManager,
          attachDefaultListeners = true,
          setDefaultMutuallyExclusives = true
        )
      }
      adjustGesturesThresholds()
    } else if (gesturePlugin.getGesturesManager() != initialGesturesManager) {
      gesturePlugin.setGesturesManager(
        initialGesturesManager,
        attachDefaultListeners = true,
        setDefaultMutuallyExclusives = true
      )
    }
  }

  fun setCameraMode(cameraMode: CameraMode) {
    setCameraMode(
      cameraMode,
      lastLocation = null,
      transitionDuration = LocationComponentConstants.TRANSITION_ANIMATION_DURATION_MS,
      zoom = null,
      bearing = null,
      pitch = null,
      internalTransitionListener = null
    )
  }

  fun setCameraMode(
    cameraMode: CameraMode,
    lastLocation: Location?,
    transitionDuration: Long,
    zoom: Double?,
    bearing: Double?,
    pitch: Double?,
    internalTransitionListener: OnLocationCameraTransitionListener?
  ) {
    if (this.cameraMode == cameraMode) {
      internalTransitionListener?.onLocationCameraTransitionFinished(cameraMode)
      return
    }
    val wasTracking: Boolean = isLocationTracking
    this.cameraMode = cameraMode
    mapTransformDelegate.setUserAnimationInProgress(isLocationTracking)

    if (cameraMode != CameraMode.NONE) {
      locationCameraAnimatorCoordinator.cancelAndUnregisterAllAnimators()
    }

    adjustGesturesThresholds()
    notifyCameraTrackingChangeListener(wasTracking)
    transitionToCurrentLocation(
      wasTracking,
      lastLocation,
      transitionDuration,
      zoom,
      bearing,
      pitch,
      internalTransitionListener
    )
  }

  /**
   * Initiates a camera animation to the current location if location tracking was engaged.
   * Notifies an internal listener when the transition's finished to invalidate animators and notify external listeners.
   */
  private fun transitionToCurrentLocation(
    wasTracking: Boolean,
    lastLocation: Location?,
    transitionDuration: Long,
    zoom: Double?,
    bearing: Double?,
    pitch: Double?,
    internalTransitionListener: OnLocationCameraTransitionListener?
  ) {
    if (!wasTracking && isLocationTracking && (lastLocation != null) && isEnabled) {
      locationCameraAnimatorCoordinator.isTransitioning = true
      val target: Point = Point.fromLngLat(lastLocation.longitude, lastLocation.latitude)
      val builder = CameraOptions.Builder().center(target)
      if (zoom != null) {
        builder.zoom(zoom)
      }
      if (pitch != null) {
        builder.pitch(pitch)
      }
      if (bearing != null) {
        builder.bearing(bearing)
      } else {
        if (isLocationBearingTracking) {
          builder.bearing(if (cameraMode == CameraMode.TRACKING_GPS_NORTH) 0.0 else lastLocation.bearing.toDouble())
        }
      }
      val update = builder.build()
      val callback: CancelableCallback = object : CancelableCallback {
        override fun onCancel() {
          locationCameraAnimatorCoordinator.isTransitioning = false
          internalTransitionListener?.onLocationCameraTransitionCanceled(cameraMode)
        }

        override fun onFinish() {
          locationCameraAnimatorCoordinator.isTransitioning = false
          internalTransitionListener?.onLocationCameraTransitionFinished(cameraMode)
        }
      }
      val currentPosition = mapTransformDelegate.getCameraOptions(null)
      locationCameraAnimatorCoordinator.cancelAndUnregisterAllAnimators()
      if (Utils.immediateAnimation(
          this.delegateProvider.mapProjectionDelegate,
          currentPosition.center!!,
          target
        )
      ) {
        locationCameraAnimatorCoordinator.jumpCamera(update)
        callback.onFinish()
      } else {
        locationCameraAnimatorCoordinator.animateCamera(update, transitionDuration, callback)
      }
    } else {
      internalTransitionListener?.onLocationCameraTransitionFinished(cameraMode)
    }
  }

  private fun adjustGesturesThresholds() {
    if (options.trackingGesturesManagement()) {
      if (isLocationTracking) {
        moveGestureDetector.moveThreshold = options.trackingInitialMoveThreshold()
      } else {
        moveGestureDetector.moveThreshold = 0f
        moveGestureDetector.moveThresholdRect = null
      }
    }
  }

  fun setEnabled(enabled: Boolean) {
    isEnabled = enabled
  }

  private val isLocationTracking: Boolean
    get() {
      return (
        cameraMode == CameraMode.TRACKING
        ) || (
        cameraMode == CameraMode.TRACKING_COMPASS
        ) || (
        cameraMode == CameraMode.TRACKING_GPS
        ) || (cameraMode == CameraMode.TRACKING_GPS_NORTH)
    }

  private val isBearingTracking: Boolean
    get() {
      return (
        cameraMode == CameraMode.NONE_COMPASS
        ) || (
        cameraMode == CameraMode.TRACKING_COMPASS
        ) || (
        cameraMode == CameraMode.NONE_GPS
        ) || (
        cameraMode == CameraMode.TRACKING_GPS
        ) || (cameraMode == CameraMode.TRACKING_GPS_NORTH)
    }

  private val isLocationBearingTracking: Boolean
    get() {
      return (
        cameraMode == CameraMode.TRACKING_GPS
        ) || (
        cameraMode == CameraMode.TRACKING_GPS_NORTH
        ) || (cameraMode == CameraMode.NONE_GPS)
    }

  val isConsumingCompass: Boolean
    get() = (
      cameraMode == CameraMode.TRACKING_COMPASS ||
        cameraMode == CameraMode.NONE_COMPASS
      )

  private fun notifyCameraTrackingChangeListener(wasTracking: Boolean) {
    internalCameraTrackingChangedListener.onCameraTrackingChanged(cameraMode)
    if (wasTracking && !isLocationTracking) {
      gesturePlugin.updateSettings { focalPoint = null }
      internalCameraTrackingChangedListener.onCameraTrackingDismissed()
    }
  }

  @VisibleForTesting
  var onMoveListener: OnMoveListener = object : OnMoveListener {
    private var interrupt: Boolean = false
    override fun onMoveBegin(detector: MoveGestureDetector) {
      if (options.trackingGesturesManagement() && isLocationTracking) {
        if (detector.pointersCount > 1) {
          applyMultiFingerThresholdArea(detector)
          applyMultiFingerMoveThreshold(detector)
        } else {
          applySingleFingerMoveThreshold(detector)
        }
      } else {
        setCameraMode(CameraMode.NONE)
      }
    }

    private fun applyMultiFingerThresholdArea(detector: MoveGestureDetector) {
      val currentRect = detector.moveThresholdRect
      if (currentRect != null && currentRect != options.trackingMultiFingerProtectedMoveArea()) {
        detector.moveThresholdRect = options.trackingMultiFingerProtectedMoveArea()
        interrupt = true
      } else if (currentRect == null && options.trackingMultiFingerProtectedMoveArea() != null) {
        detector.moveThresholdRect = options.trackingMultiFingerProtectedMoveArea()
        interrupt = true
      }
    }

    private fun applyMultiFingerMoveThreshold(detector: MoveGestureDetector) {
      if (detector.moveThreshold != options.trackingMultiFingerMoveThreshold()) {
        detector.moveThreshold = options.trackingMultiFingerMoveThreshold()
        interrupt = true
      }
    }

    private fun applySingleFingerMoveThreshold(detector: MoveGestureDetector) {
      if (detector.moveThreshold != options.trackingInitialMoveThreshold()) {
        detector.moveThreshold = options.trackingInitialMoveThreshold()
        interrupt = true
      }
    }

    override fun onMove(detector: MoveGestureDetector): Boolean {
      if (interrupt) {
        detector.interrupt()
        return false
      }
      if (isLocationTracking || isBearingTracking) {
        setCameraMode(CameraMode.NONE)
        detector.interrupt()
      }
      return false
    }

    override fun onMoveEnd(detector: MoveGestureDetector) {
      if (options.trackingGesturesManagement() && !interrupt && isLocationTracking) {
        detector.moveThreshold = options.trackingInitialMoveThreshold()
        detector.moveThresholdRect = null
      }
      interrupt = false
    }
  }

  private val onRotateListener: OnRotateListener = object : OnRotateListener {
    override fun onRotateBegin(detector: RotateGestureDetector) {
      if (isBearingTracking) {
        setCameraMode(CameraMode.NONE)
      }
    }

    override fun onRotate(detector: RotateGestureDetector) { // no implementation
    }

    override fun onRotateEnd(detector: RotateGestureDetector) { // no implementation
    }
  }

  private val onFlingListener: OnFlingListener = OnFlingListener { setCameraMode(CameraMode.NONE) }

  fun onCameraMove() {
    if (isLocationTracking && options.trackingGesturesManagement()) {
      locationCameraAnimatorCoordinator.lastLocation?.let {
        val focalPoint = this.delegateProvider.mapProjectionDelegate.pixelForCoordinate(it)
        gesturePlugin.updateSettings { this.focalPoint = focalPoint }
      }
    }
  }

  private inner class LocationGesturesManager constructor(context: Context?) :
    AndroidGesturesManager(context) {
    override fun onTouchEvent(motionEvent: MotionEvent?): Boolean {
      if (motionEvent != null) {
        val action: Int = motionEvent.actionMasked
        if (action == MotionEvent.ACTION_UP) {
          adjustGesturesThresholds()
        }
      }
      return super.onTouchEvent(motionEvent)
    }
  }

  internal fun resetAllCameraAnimations() {
    val cameraOptions = delegateProvider.mapTransformDelegate.getCameraOptions(null)
    val isGpsNorth = cameraMode == CameraMode.TRACKING_GPS_NORTH
    locationCameraAnimatorCoordinator.resetAllCameraAnimations(cameraOptions, isGpsNorth)
  }

  internal fun cancelZoomAnimation() = locationCameraAnimatorCoordinator.cancelZoomAnimation()

  internal fun cancelPaddingAnimation() = locationCameraAnimatorCoordinator.cancelPaddingAnimation()

  internal fun cancelPitchAnimation() = locationCameraAnimatorCoordinator.cancelPitchAnimation()

  fun feedNewCameraZoomLevel(
    target: Double,
    animationDuration: Long,
    callback: CancelableCallback?
  ) = locationCameraAnimatorCoordinator.feedNewCameraZoomLevel(
    target,
    mapTransformDelegate.getCameraOptions(null),
    animationDuration,
    callback
  )

  fun feedNewCameraPadding(
    target: EdgeInsets,
    animationDuration: Long,
    callback: CancelableCallback?
  ) = locationCameraAnimatorCoordinator.feedNewCameraPadding(
    target,
    mapTransformDelegate.getCameraOptions(null),
    animationDuration,
    callback
  )

  fun feedNewCameraPitch(
    target: Double,
    animationDuration: Long,
    callback: CancelableCallback?
  ) = locationCameraAnimatorCoordinator.feedNewCameraPitch(
    target,
    mapTransformDelegate.getCameraOptions(null),
    animationDuration,
    callback
  )

  fun feedNewCameraCompassBearing(target: Float, animationDuration: Long) =
    locationCameraAnimatorCoordinator.feedNewCompassCameraBearing(
      target,
      mapTransformDelegate.getCameraOptions(null),
      animationDuration,
      cameraMode
    )

  fun feedNewCameraLocation(@Size(min = 1) newLocations: Array<Location>, animationDuration: Long) =
    locationCameraAnimatorCoordinator.feedNewCameraLocation(
      newLocations,
      mapTransformDelegate.getCameraOptions(null),
      animationDuration,
      cameraMode
    )

  companion object {
    internal fun isLocationTracking(cameraMode: CameraMode) =
      (cameraMode == CameraMode.TRACKING) ||
        (cameraMode == CameraMode.TRACKING_COMPASS) ||
        (cameraMode == CameraMode.TRACKING_GPS) ||
        (cameraMode == CameraMode.TRACKING_GPS_NORTH)

    internal fun isLocationBearingTracking(cameraMode: CameraMode) =
      cameraMode == CameraMode.TRACKING_GPS ||
        cameraMode == CameraMode.TRACKING_GPS_NORTH ||
        cameraMode == CameraMode.NONE_GPS

    internal fun isConsumingCompass(cameraMode: CameraMode) =
      cameraMode == CameraMode.TRACKING_COMPASS || cameraMode == CameraMode.NONE_COMPASS

    internal fun isGPSNorth(cameraMode: CameraMode) =
      cameraMode == CameraMode.TRACKING_GPS_NORTH
  }
}