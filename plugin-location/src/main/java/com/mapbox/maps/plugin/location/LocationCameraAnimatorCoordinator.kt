package com.mapbox.maps.plugin.location

import android.animation.Animator
import android.animation.ValueAnimator
import android.location.Location
import androidx.annotation.Size
import androidx.annotation.VisibleForTesting
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorType
import com.mapbox.maps.plugin.animation.CameraAnimatorType.*
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.MapAnimationOwnerRegistry
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import com.mapbox.maps.plugin.location.listeneres.CancelableCallback
import com.mapbox.maps.plugin.location.modes.CameraMode

/**
 *  Creates, updates, and plays [ValueAnimator]s with help of low-level Camera API.
 *  Manages all low-level logic around Camera manipulations for the Location plugin
 */
internal class LocationCameraAnimatorCoordinator(
  private val projection: MapProjectionDelegate,
  private val mapTransformDelegate: MapTransformDelegate,
  private val animatorSetProvider: MapboxAnimatorSetProvider,
  private val cameraAnimationsPlugin: CameraAnimationsPlugin
) {

  class ValueAnimatorHolder<T>(
    val animator: ValueAnimator,
    val targets: Array<T>
  )

  @VisibleForTesting
  internal val cameraAnimators = HashMap<CameraAnimatorType, ValueAnimatorHolder<*>>()

  var lastLocation: Point? = null
  var isTransitioning = false

  fun feedNewCameraLocation(
    @Size(min = 1) newLocations: Array<Location>,
    currentCameraPosition: CameraOptions,
    animationDuration: Long,
    cameraMode: CameraMode
  ) {

    val previousCameraLatLng = currentCameraPosition.center
    val previousCameraBearingRaw = currentCameraPosition.bearing
    if (previousCameraLatLng == null || previousCameraBearingRaw == null) return

    val previousCameraBearing = Utils.normalize(previousCameraBearingRaw.toFloat())
    var bearingCameraValues = getCameraBearingValues(previousCameraBearing.toDouble(), newLocations)

    if (LocationCameraController.isGPSNorth(cameraMode)) {
      bearingCameraValues = doubleArrayOf(0.0)
    }

    updateCameraBearingAnimators(previousCameraBearing.toDouble(), bearingCameraValues)
    updateCameraCenterAnimators(
      previousCameraLatLng,
      getCameraLatLngValues(newLocations).toTypedArray()
    )

    val newLocation = newLocations[newLocations.size - 1]
    val targetLatLng = Point.fromLngLat(newLocation.longitude, newLocation.latitude)
    val snap = Utils.immediateAnimation(projection, previousCameraLatLng, targetLatLng)

    val animatorTypes = arrayListOf<CameraAnimatorType>()

    if (LocationCameraController.isLocationTracking(cameraMode)) {
      animatorTypes.add(CENTER)
    }
    if (LocationCameraController.isLocationBearingTracking(cameraMode)) {
      animatorTypes.add(BEARING)
    }

    if (!isTransitioning) {
      playCameraAnimators(
        duration = if (snap) 0L else animationDuration,
        animatorTypes = animatorTypes.toTypedArray()
      )
    }
  }

  private fun getCameraLatLngValues(targetLocations: Array<Location>) =
    targetLocations.map { Point.fromLngLat(it.longitude, it.latitude) }

  private fun getCameraBearingValues(
    previousBearing: Double,
    targetLocations: Array<Location>
  ): DoubleArray {
    val bearings = DoubleArray(targetLocations.size) { 0.0 }
    // Because Location bearing values are normalized to [0, 360]
    // we need to do the same for the previous bearing value to determine the shortest path
    for (i in bearings.indices) {
      if (i == 0) {
        bearings[i] =
          Utils.shortestRotation(targetLocations[i].bearing, previousBearing.toFloat())
            .toDouble()
      } else {
        bearings[i] =
          Utils.shortestRotation(targetLocations[i].bearing, bearings[i - 1].toFloat())
            .toDouble()
      }
    }
    return bearings
  }

  private fun playCameraAnimators(duration: Long, vararg animatorTypes: CameraAnimatorType) {
    val animators = mutableListOf<Animator>()
    animatorTypes.forEach { animatorType ->
      cameraAnimators[animatorType]?.let { animators.add(it.animator) }
    }
    animatorSetProvider.startAnimation(
      animators,
      MapboxAnimatorSetProvider.animatorInstance,
      duration
    )
  }

  fun resetAllCameraAnimations(currentCameraPosition: CameraOptions, isGpsNorth: Boolean) {
    resetCameraCompassAnimation(currentCameraPosition)
    val snap = resetCameraLocationAnimations(currentCameraPosition, isGpsNorth)
    val duration = if (snap) 0 else LocationComponentConstants.TRANSITION_ANIMATION_DURATION_MS
    playCameraAnimators(duration, CENTER, BEARING)
  }

  private fun resetCameraLocationAnimations(
    currentCameraPosition: CameraOptions,
    isGpsNorth: Boolean
  ): Boolean {
    resetCameraGpsBearingAnimation(currentCameraPosition, isGpsNorth)
    return resetCameraLatLngAnimation(currentCameraPosition)
  }

  private fun resetCameraLatLngAnimation(currentCameraPosition: CameraOptions): Boolean {
    val animatorHolder = cameraAnimators[CENTER] ?: return true
    val animatorValues = animatorHolder.targets
    val currentTarget = animatorValues[animatorValues.size - 1] as Point
    val previousCameraTarget = currentCameraPosition.center

    previousCameraTarget?.let {
      updateCameraCenterAnimators(previousCameraTarget, arrayOf(currentTarget))
      return Utils.immediateAnimation(
        projection,
        previousCameraTarget, currentTarget
      )
    }
    return true
  }

  private fun resetCameraGpsBearingAnimation(
    currentCameraPosition: CameraOptions,
    isGpsNorth: Boolean
  ) {
    val animatorHolder = cameraAnimators[BEARING] ?: return
    val animatorValues = animatorHolder.targets
    var target = animatorValues[animatorValues.size - 1] as Double
    target = checkGpsNorth(isGpsNorth, target)
    val previous = currentCameraPosition.bearing ?: 0.0
    val normalizedPrevious =
      Utils.shortestRotation(target.toFloat(), previous.toFloat()).toDouble()
    updateCameraBearingAnimators(previous, doubleArrayOf(normalizedPrevious))
  }

  private fun resetCameraCompassAnimation(currentCameraPosition: CameraOptions) {
    val animatorHolder = cameraAnimators[BEARING] ?: return
    val animatorValues = animatorHolder.targets
    val target = animatorValues[animatorValues.size - 1] as Double
    val previous = currentCameraPosition.bearing ?: 0.0
    val normalizedPrevious =
      Utils.shortestRotation(target.toFloat(), previous.toFloat()).toDouble()
    updateCameraBearingAnimators(previous, doubleArrayOf(normalizedPrevious))
  }

  private fun updateCameraCenterAnimators(startLatLng: Point, targetLatLng: Array<Point>) {
    unregisterCameraAnimator(CENTER)
    val centerAnimator = cameraAnimationsPlugin.createCenterAnimator(
      options = cameraAnimatorOptions(*targetLatLng) {
        owner(MapAnimationOwnerRegistry.LOCATION)
        startValue(startLatLng)
      }
    )
    centerAnimator.addUpdateListener { lastLocation = it.animatedValue as Point }
    cameraAnimators[CENTER] = ValueAnimatorHolder(centerAnimator, targetLatLng)
    cameraAnimationsPlugin.registerAnimators(centerAnimator)
  }

  private fun updateCameraBearingAnimators(startBearing: Double, targetBearings: DoubleArray) {
    unregisterCameraAnimator(BEARING)
    val bearingAnimator = cameraAnimationsPlugin.createBearingAnimator(
      options = cameraAnimatorOptions(*targetBearings.toTypedArray()) {
        owner(MapAnimationOwnerRegistry.LOCATION)
        startValue(startBearing)
      }
    )
    cameraAnimators[BEARING] = ValueAnimatorHolder(bearingAnimator, targetBearings.toTypedArray())
    cameraAnimationsPlugin.registerAnimators(bearingAnimator)
  }

  private fun unregisterCameraAnimator(animatorType: CameraAnimatorType) {
    cameraAnimators[animatorType]?.let { animatorHolder ->
      cameraAnimationsPlugin.unregisterAnimators(animatorHolder.animator)
    }
  }

  fun cancelZoomAnimation() = unregisterCameraAnimator(ZOOM)

  fun cancelPaddingAnimation() = unregisterCameraAnimator(PADDING)

  fun cancelPitchAnimation() = unregisterCameraAnimator(PITCH)

  private fun updateCompassCameraBearing(
    targetCompassBearing: Float,
    previousCameraBearing: Float
  ) {
    val normalizedCameraBearing =
      Utils.shortestRotation(targetCompassBearing, previousCameraBearing)
    updateCameraBearingAnimators(
      previousCameraBearing.toDouble(),
      doubleArrayOf(normalizedCameraBearing.toDouble())
    )
  }

  private fun updateZoomCameraAnimator(
    targetZoomLevel: Double,
    previousZoomLevel: Double,
    cancelableCallback: CancelableCallback?
  ) {

    unregisterCameraAnimator(ZOOM)
    val zoomAnimator = cameraAnimationsPlugin.createZoomAnimator(
      options = cameraAnimatorOptions(targetZoomLevel) {
        owner(MapAnimationOwnerRegistry.LOCATION)
        startValue(previousZoomLevel)
      }
    )
    zoomAnimator.addListener(object : Animator.AnimatorListener {
      override fun onAnimationStart(animation: Animator?) {}
      override fun onAnimationEnd(animation: Animator?) {
        cancelableCallback?.onFinish()
      }

      override fun onAnimationCancel(animation: Animator?) {
        cancelableCallback?.onCancel()
      }

      override fun onAnimationRepeat(animation: Animator?) {}
    })
    cameraAnimators[ZOOM] = ValueAnimatorHolder(zoomAnimator, Array(1) { targetZoomLevel })
    cameraAnimationsPlugin.registerAnimators(zoomAnimator)
  }

  private fun updatePaddingCameraAnimator(
    target: EdgeInsets,
    current: EdgeInsets,
    cancelableCallback: CancelableCallback?
  ) {
    unregisterCameraAnimator(PADDING)
    val paddingAnimator = cameraAnimationsPlugin.createPaddingAnimator(
      options = cameraAnimatorOptions(target) {
        owner(MapAnimationOwnerRegistry.LOCATION)
        startValue(current)
      }
    )
    paddingAnimator.addListener(object : Animator.AnimatorListener {
      override fun onAnimationStart(animation: Animator?) {}
      override fun onAnimationEnd(animation: Animator?) {
        cancelableCallback?.onFinish()
      }

      override fun onAnimationCancel(animation: Animator?) {
        cancelableCallback?.onCancel()
      }

      override fun onAnimationRepeat(animation: Animator?) {}
    })
    cameraAnimators[PADDING] = ValueAnimatorHolder(paddingAnimator, Array(1) { target })
    cameraAnimationsPlugin.registerAnimators(paddingAnimator)
  }

  private fun updatePitchCameraAnimator(
    targetPitch: Double,
    previousPitch: Double,
    cancelableCallback: CancelableCallback?
  ) {
    unregisterCameraAnimator(PITCH)
    val pitchAnimator = cameraAnimationsPlugin.createPitchAnimator(
      options = cameraAnimatorOptions(targetPitch) {
        owner(MapAnimationOwnerRegistry.LOCATION)
        startValue(previousPitch)
      }
    )
    pitchAnimator.addListener(object : Animator.AnimatorListener {
      override fun onAnimationStart(animation: Animator?) {}
      override fun onAnimationEnd(animation: Animator?) {
        cancelableCallback?.onFinish()
      }

      override fun onAnimationCancel(animation: Animator?) {
        cancelableCallback?.onCancel()
      }

      override fun onAnimationRepeat(animation: Animator?) {}
    })
    cameraAnimators[PITCH] = ValueAnimatorHolder(pitchAnimator, Array(1) { targetPitch })
    cameraAnimationsPlugin.registerAnimators(pitchAnimator)
  }

  private fun checkGpsNorth(isGpsNorth: Boolean, targetCameraBearing: Double) =
    if (isGpsNorth) 0.0 else targetCameraBearing

  fun feedNewCameraZoomLevel(
    targetZoomLevel: Double,
    currentCameraPosition: CameraOptions,
    animationDuration: Long,
    callback: CancelableCallback?
  ) {
    updateZoomCameraAnimator(
      targetZoomLevel,
      currentCameraPosition.zoom ?: targetZoomLevel,
      callback
    )
    playCameraAnimators(animationDuration, ZOOM)
  }

  fun feedNewCameraPadding(
    target: EdgeInsets,
    currentCameraPosition: CameraOptions,
    animationDuration: Long,
    callback: CancelableCallback?
  ) {
    val current = currentCameraPosition.padding ?: EdgeInsets(0.0, 0.0, 0.0, 0.0)
    updatePaddingCameraAnimator(target, current, callback)
    playCameraAnimators(animationDuration, PADDING)
  }

  fun feedNewCameraPitch(
    targetPitch: Double,
    currentCameraPosition: CameraOptions,
    animationDuration: Long,
    callback: CancelableCallback?
  ) {
    val previousPitch = currentCameraPosition.pitch ?: targetPitch
    updatePitchCameraAnimator(targetPitch, previousPitch, callback)
    playCameraAnimators(animationDuration, PITCH)
  }

  fun feedNewCompassCameraBearing(
    targetCompassBearing: Float,
    currentCameraPosition: CameraOptions,
    animationDuration: Long,
    cameraMode: CameraMode
  ) {
    val previousCameraBearingRaw = currentCameraPosition.bearing ?: return
    val previousCameraBearing = previousCameraBearingRaw.toFloat()
    updateCompassCameraBearing(targetCompassBearing, previousCameraBearing)
    if (LocationCameraController.isConsumingCompass(cameraMode) && !isTransitioning) {
      playCameraAnimators(animationDuration, BEARING)
    }
  }

  internal fun cancelAndUnregisterAllAnimators() {
    // Cancel and unregister too
    cameraAnimationsPlugin.unregisterAnimators(
      cameraAnimators = cameraAnimators.values.map { it.animator }.toTypedArray()
    )
  }

  internal fun animateCamera(
    cameraOptions: CameraOptions,
    animationDuration: Long,
    callback: CancelableCallback
  ) {
    cancelAndUnregisterAllAnimators()
    cameraAnimationsPlugin.flyTo(
      cameraOptions,
      mapAnimationOptions {
        owner(MapAnimationOwnerRegistry.LOCATION)
        duration(animationDuration)
        animatorListener(object : Animator.AnimatorListener {
          override fun onAnimationEnd(animation: Animator?) {
            callback.onFinish()
          }

          override fun onAnimationCancel(animation: Animator?) {
            callback.onCancel()
          }

          override fun onAnimationRepeat(animation: Animator?) {}
          override fun onAnimationStart(animation: Animator?) {}
        })
      }
    )
  }

  internal fun jumpCamera(cameraOptions: CameraOptions) {
    cancelAndUnregisterAllAnimators()
    mapTransformDelegate.jumpTo(cameraOptions)
  }
}