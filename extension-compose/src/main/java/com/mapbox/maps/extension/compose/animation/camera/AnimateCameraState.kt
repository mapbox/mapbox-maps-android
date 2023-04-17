package com.mapbox.maps.extension.compose.animation.camera

import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.dsl.cameraOptions

/**
 * Fire-and-forget animation function for [CameraState]. When the provided [targetState] is changed, the
 * animation will run automatically. If there is already an animation in-flight when [targetState]
 * changes, the on-going animation will adjust course to animate towards the new target value.
 *
 * [animateCameraState] returns a [State] object. The value of the state object will continuously
 * be updated by the animation until the animation finishes.
 *
 * Note, [animateCameraState] cannot be canceled/stopped without removing this composable function
 * from the tree. See [Animatable] for cancelable animations.
 *
 * @param targetState Target value of the animation
 * @param durationMillis duration of the internal tween animation spec, defaults to 1000 milliseconds
 * @param delayMillis the amount of time in milliseconds that animation waits before starting, defaults to 0 milliseconds
 * @param easing the easing curve that will be used to interpolate between start and end, defaults to [LinearOutSlowInEasing]
 * @param label An optional label to differentiate from other animations in Android Studio.
 * @param finishedListener An optional end listener to get notified when the animation is finished.
 * @return A [State] object of [CameraOptions], the value of which is updated by animation.
 */
@Composable
@MapboxExperimental
public fun animateCameraState(
  targetState: CameraState,
  durationMillis: Int = 1000,
  delayMillis: Int = 0,
  easing: Easing = LinearOutSlowInEasing,
  label: String? = "CameraStateAnimation",
  finishedListener: ((CameraState) -> Unit)? = null
): State<CameraOptions> {
  val transition: Transition<CameraState> =
    updateTransition(targetState = targetState, label = label)
  if (transition.currentState == targetState) {
    finishedListener?.invoke(targetState)
  }
  val cameraCenter by transition.animateCenter(
    transitionSpec = {
      tween(
        durationMillis = durationMillis,
        delayMillis = delayMillis,
        easing = easing,
      )
    },
  )
  val cameraPadding by transition.animatePadding(
    transitionSpec = {
      tween(
        durationMillis = durationMillis,
        delayMillis = delayMillis,
        easing = easing,
      )
    },
  )
  val cameraZoom by transition.animateZoom(
    transitionSpec = {
      tween(
        durationMillis = durationMillis,
        delayMillis = delayMillis,
        easing = easing,
      )
    }
  )
  val cameraBearing by transition.animateBearing(
    transitionSpec = {
      tween(
        durationMillis = durationMillis,
        delayMillis = delayMillis,
        easing = easing,
      )
    },
  )
  val cameraPitch by transition.animatePitch(
    transitionSpec = {
      tween(
        durationMillis = durationMillis,
        delayMillis = delayMillis,
        easing = easing,
      )
    },
  )
  val cameraOptionsState: MutableState<CameraOptions> = remember {
    mutableStateOf(CameraOptions.Builder().build())
  }
  var cameraOptionUpdate: CameraOptions by cameraOptionsState
  cameraOptionUpdate = cameraOptions {
    center(cameraCenter)
    padding(cameraPadding)
    zoom(cameraZoom)
    bearing(cameraBearing)
    pitch(cameraPitch)
  }
  return cameraOptionsState
}

/**
 * Utility function to make a copy of [CameraState] with mutated properties.
 *
 * @param center the center to overwrite
 * @param padding the padding to overwrite
 * @param zoom the zoom to overwrite
 * @param bearing the bearing to overwrite
 * @param pitch the pitch to overwrite
 */
public fun CameraState.copy(
  center: Point = this.center,
  padding: EdgeInsets = this.padding,
  zoom: Double = this.zoom,
  bearing: Double = this.bearing,
  pitch: Double = this.pitch,
): CameraState = CameraState(center, padding, zoom, bearing, pitch)