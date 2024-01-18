package com.mapbox.maps.plugin.gestures

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import com.mapbox.maps.MapCenterAltitudeMode
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import com.mapbox.maps.threading.AnimationThreadController

/**
 * Internal utility to handle gesture interactions with render engine.
 */
@OptIn(MapboxExperimental::class)
internal class CoreGesturesHandler(
  private val mapTransformDelegate: MapTransformDelegate,
  private val mapCameraManagerDelegate: MapCameraManagerDelegate
) {
  private var gestureAnimationStarted = false
  private var gestureStarted = false
  private var cachedCenterAltitudeMode = mapCameraManagerDelegate.getCenterAltitudeMode()

  /**
   * We should set the center altitude mode to [MapCenterAltitudeMode.SEA] during gestures to avoid
   * bumpiness when the terrain is enabled. It's not necessary to update [MapCenterAltitudeMode] if
   * the user explicitly changed altitude to [MapCenterAltitudeMode.SEA] before the gesture starts.
   */
  private fun isSetCenterAltitudeModeNeeded(): Boolean {
    return cachedCenterAltitudeMode != MapCenterAltitudeMode.SEA
  }

  /**
   * Called when the gesture is started, i.e. the point when a gesture is recognised.
   */
  fun notifyCoreGestureStarted() {
    if (!gestureStarted) {
      AnimationThreadController.postOnMainThread {
        cachedCenterAltitudeMode = mapCameraManagerDelegate.getCenterAltitudeMode()
        gestureStarted = true
        mapTransformDelegate.setGestureInProgress(true)
        if (isSetCenterAltitudeModeNeeded()) {
          mapCameraManagerDelegate.setCenterAltitudeMode(MapCenterAltitudeMode.SEA)
        }
      }
    }
  }

  /**
   * Called on ACTION_UP or ACTION_CANCEL touch event. Note that the gesture might not end yet, a fling
   * or rotate camera animation might still be in progress.
   */
  fun notifyCoreTouchEnded() {
    // ACTION_UP or ACTION_CANCEL may be triggered but there was no actual gesture -
    // then we don't have to call native functions to avoid triggering extra MAP_IDLE event

    // fling animation starts before ACTION_UP or ACTION_CANCEL so it's important to retain SEA elevation
    // for the fling to avoid bumpiness
    if (gestureStarted && !gestureAnimationStarted) {
      AnimationThreadController.postOnMainThread {
        if (isSetCenterAltitudeModeNeeded()) {
          mapCameraManagerDelegate.setCenterAltitudeMode(cachedCenterAltitudeMode)
        }
        mapTransformDelegate.setGestureInProgress(false)
        gestureStarted = false
      }
    }
  }

  /**
   * Adds handling of gesture driven camera animator listener.
   * Can be used for fling or scale animation.
   */
  val coreGestureAnimatorHandler = object : AnimatorListenerAdapter() {
    override fun onAnimationStart(animation: Animator) {
      super.onAnimationStart(animation)
      AnimationThreadController.postOnMainThread {
        gestureAnimationStarted = true
        if (isSetCenterAltitudeModeNeeded()) {
          mapCameraManagerDelegate.setCenterAltitudeMode(MapCenterAltitudeMode.SEA)
        }
      }
    }

    override fun onAnimationEnd(animation: Animator) {
      super.onAnimationEnd(animation)
      AnimationThreadController.postOnMainThread {
        gestureAnimationStarted = false
        if (isSetCenterAltitudeModeNeeded()) {
          mapCameraManagerDelegate.setCenterAltitudeMode(cachedCenterAltitudeMode)
        }
        mapTransformDelegate.setGestureInProgress(false)
      }
    }
  }
}