@file:JvmName("CameraAnimationsUtils")
package com.mapbox.maps.plugin.animation

import android.animation.Animator
import android.animation.Animator.DURATION_INFINITE
import android.animation.AnimatorSet
import android.os.Build
import androidx.annotation.RestrictTo
import com.mapbox.common.Cancelable
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraAnimationHint
import com.mapbox.maps.CameraAnimationHintStage
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_CAMERA_PLUGIN_ID
import com.mapbox.maps.plugin.animation.CameraAnimationsPluginImpl.Companion.TAG
import com.mapbox.maps.plugin.animation.animator.CameraAnchorAnimator
import com.mapbox.maps.plugin.animation.animator.CameraAnimator
import com.mapbox.maps.plugin.animation.animator.CameraBearingAnimator
import com.mapbox.maps.plugin.animation.animator.CameraCenterAnimator
import com.mapbox.maps.plugin.animation.animator.CameraPaddingAnimator
import com.mapbox.maps.plugin.animation.animator.CameraPitchAnimator
import com.mapbox.maps.plugin.animation.animator.CameraZoomAnimator
import com.mapbox.maps.plugin.delegates.MapPluginExtensionsDelegate
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the Camera Animations plugin instance.
 */
val MapPluginProviderDelegate.camera: CameraAnimationsPlugin
  @JvmName("getCamera")
  get() = this.getPlugin(MAPBOX_CAMERA_PLUGIN_ID)!!

private val emptyCancelable = Cancelable { }

/**
 * Extension easeTo() for [MapPluginExtensionsDelegate]
 * Ease the map camera to a given camera options and animation options
 *
 * Camera plugin with id = [Plugin.MAPBOX_CAMERA_PLUGIN_ID] must be added while constructing
 * `MapView` as part of `MapInitOptions.plugins`.
 *
 * @param cameraOptions The camera options to ease to
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or empty cancelable object if associated `MapView` was destroyed.
 */
@JvmOverloads
fun MapPluginExtensionsDelegate.easeTo(
  cameraOptions: CameraOptions,
  animationOptions: MapAnimationOptions? = null,
  animatorListener: Animator.AnimatorListener? = null
) = (cameraAnimationsPlugin { easeTo(cameraOptions, animationOptions, animatorListener) } as? Cancelable) ?: emptyCancelable

/**
 * Extension flyTo() function for [MapPluginExtensionsDelegate]
 * Fly the map camera to a given camera options.
 *
 * Camera plugin with id = [Plugin.MAPBOX_CAMERA_PLUGIN_ID] must be added while constructing
 * `MapView` as part of `MapInitOptions.plugins`.
 *
 * @param cameraOptions The camera options to fly to
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or empty cancelable object if associated `MapView` was destroyed.
 */
@JvmOverloads
fun MapPluginExtensionsDelegate.flyTo(
  cameraOptions: CameraOptions,
  animationOptions: MapAnimationOptions? = null,
  animatorListener: Animator.AnimatorListener? = null
) = (cameraAnimationsPlugin { flyTo(cameraOptions, animationOptions, animatorListener) } as? Cancelable) ?: emptyCancelable

/**
 * Extension pitchBy() function for [MapPluginExtensionsDelegate]
 * Pitch the map by with optional animation.
 *
 * Camera plugin with id = [Plugin.MAPBOX_CAMERA_PLUGIN_ID] must be added while constructing
 * `MapView` as part of `MapInitOptions.plugins`.
 *
 * @param pitch The amount to pitch by
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or empty cancelable object if associated `MapView` was destroyed.
 */
@JvmOverloads
fun MapPluginExtensionsDelegate.pitchBy(
  pitch: Double,
  animationOptions: MapAnimationOptions? = null,
  animatorListener: Animator.AnimatorListener? = null
) = (cameraAnimationsPlugin { pitchBy(pitch, animationOptions, animatorListener) } as? Cancelable) ?: emptyCancelable

/**
 * Extension scaleBy() function for [MapPluginExtensionsDelegate]
 * Scale the map by with optional animation.
 *
 * Camera plugin with id = [Plugin.MAPBOX_CAMERA_PLUGIN_ID] must be added while constructing
 * `MapView` as part of `MapInitOptions.plugins`.
 *
 * @param amount The amount to scale by
 * @param screenCoordinate The optional focal point to scale on
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or empty cancelable object if associated `MapView` was destroyed.
 */
@JvmOverloads
fun MapPluginExtensionsDelegate.scaleBy(
  amount: Double,
  screenCoordinate: ScreenCoordinate?,
  animationOptions: MapAnimationOptions? = null,
  animatorListener: Animator.AnimatorListener? = null
) = (cameraAnimationsPlugin { scaleBy(amount, screenCoordinate, animationOptions, animatorListener) } as? Cancelable) ?: emptyCancelable

/**
 * Extension moveBy() function for [MapPluginExtensionsDelegate]
 * Move the map by a given screen coordinate with optional animation.
 *
 * Camera plugin with id = [Plugin.MAPBOX_CAMERA_PLUGIN_ID] must be added while constructing
 * `MapView` as part of `MapInitOptions.plugins`.
 *
 * @param screenCoordinate The screen coordinate distance to move by
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or empty cancelable object if associated `MapView` was destroyed.
 */
@JvmOverloads
fun MapPluginExtensionsDelegate.moveBy(
  screenCoordinate: ScreenCoordinate,
  animationOptions: MapAnimationOptions? = null,
  animatorListener: Animator.AnimatorListener? = null
) = (cameraAnimationsPlugin { moveBy(screenCoordinate, animationOptions, animatorListener) } as? Cancelable) ?: emptyCancelable

/**
 * Extension rotateBy() function for [MapPluginExtensionsDelegate]
 * Rotate the map by with optional animation.
 *
 * Camera plugin with id = [Plugin.MAPBOX_CAMERA_PLUGIN_ID] must be added while constructing
 * `MapView` as part of `MapInitOptions.plugins`.
 *
 * @param first The first pointer to rotate on
 * @param second The second pointer to rotate on
 * @param animationOptions Transition options (animation duration, listeners etc)
 *
 * @return [Cancelable] animator set object or empty cancelable object if associated `MapView` was destroyed.
 */
@JvmOverloads
fun MapPluginExtensionsDelegate.rotateBy(
  first: ScreenCoordinate,
  second: ScreenCoordinate,
  animationOptions: MapAnimationOptions? = null,
  animatorListener: Animator.AnimatorListener? = null
) = (cameraAnimationsPlugin { rotateBy(first, second, animationOptions, animatorListener) } as? Cancelable) ?: emptyCancelable

/**
 * Static method to create instance of Mapbox camera animation plugin.
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
@JvmSynthetic
fun createCameraAnimationPlugin(): CameraAnimationsPlugin {
  return CameraAnimationsPluginImpl()
}

/**
 * Static method to get [CameraAnimatorsFactory].
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
@JvmSynthetic
fun CameraAnimationsPlugin.getCameraAnimatorsFactory(): CameraAnimatorsFactory {
  return (this as CameraAnimationsPluginImpl).cameraAnimationsFactory
}

/**
 * Calculates camera animation hints for provided [AnimatorSet] at specified fractions.
 * This API must be called right before the animation is started: moving camera in-between should be avoided.
 *
 * Requirements:
 * 1. [AnimatorSet] must have startDelay = 0 (TODO support non-zero startDelay).
 *  However, a child animator is allowed to have a non-zero startDelay.
 * 2. All child animations must be instances of [CameraAnimator].
 * 3. None of the child animations are allowed to have infinite duration.
 *  Note: [AnimatorSet] itself may have infinite duration.
 *
 * If [AnimatorSet] has zero duration, no camera animation hints are calculated: null is returned.
 *
 * @param fractions list of fractions at which to calculate intermediate camera states
 * @param startCameraState start value of the animations.
 *  Must be provided in case your [AnimatorSet]'s child animators do not have a startValue.
 *
 *  @return [CameraAnimationHint] object, null if calculation could not be performed
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
@JvmSynthetic
fun AnimatorSet.calculateCameraAnimationHint(
  fractions: List<Float>,
  startCameraState: CameraState? = null,
): CameraAnimationHint? {
  // Make sure all animators in animatorSet are camera animators
  val cameraAnimators = childAnimations.filterIsInstance<CameraAnimator<*>>()
  if (cameraAnimators.size != childAnimations.size) {
    logW(TAG, "Incompatible animators: all should be instances of CameraAnimator")
    return null
  }

  if (startDelay != 0L) {
    logW(TAG, "AnimatorSets with non-zero startDelay are not supported.")
    return null
  }

  if (childAnimations.isEmpty()) {
    logW(TAG, "AnimatorSet has no child animations.")
    return null
  }

  val totalDuration = if (duration >= 0) {
    duration
  } else {
    cameraAnimators.maxOf { it.safeTotalDuration() }
  }
  return cameraAnimators.calculateCameraAnimationHint(fractions, totalDuration, startCameraState)
}

/**
 * Calculates camera animation hints for provided list of [Animator] at specified fractions.
 * This API must be called right before the animation is started: moving camera in-between should be avoided.
 *
 * Requirements:
 * 1. None of the animations are allowed to have infinite duration.
 *
 * If all animators have zero duration, no camera animation hints are calculated: null is returned.
 *
 * @param fractions list of fractions at which to calculate intermediate camera states
 * @param startCameraState start value of the animations.
 *  Must be provided in case your [AnimatorSet]'s child animators do not have a startValue.
 *
 *  @return [CameraAnimationHint] object, null if calculation could not be performed
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
@JvmSynthetic
fun <T : CameraAnimator<*>> List<T>.calculateCameraAnimationHint(
  fractions: List<Float>,
  startCameraState: CameraState? = null,
): CameraAnimationHint? {
  if (isEmpty()) {
    return null
  }
  val totalDuration = maxOf { it.safeTotalDuration() }
  return calculateCameraAnimationHint(fractions, totalDuration, startCameraState)
}

private fun Animator.safeTotalDuration(): Long {
  return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    totalDuration
  } else {
    // For pre-N devices, totalDuration is not available, so we use the duration of the animator.
    duration + startDelay
  }
}

private fun <T : CameraAnimator<*>> List<T>.calculateCameraAnimationHint(
  fractions: List<Float>,
  totalDuration: Long,
  startCameraState: CameraState?,
): CameraAnimationHint? {
  if (isEmpty()) {
    return null
  }

  // No need to calculate camera animation hints if the animation is instant
  if (totalDuration == 0L) {
    return null
  }

  if (totalDuration == DURATION_INFINITE || this.any { it.duration == DURATION_INFINITE }) {
    logW(
      TAG,
      "Animators with infinite duration are not supported. " +
        "Please use finite duration for all animators.",
    )
    return null
  }

  val cameraOptionsBuilder = CameraOptions.Builder()

  val stages = fractions.map { totalFraction ->
    cameraOptionsBuilder.clear()
    this.map { cameraAnimator ->
      try {
        val fraction = getRelativeFraction(cameraAnimator, totalDuration, totalFraction)
        val value = cameraAnimator.getAnimatedValueAt(fraction, startCameraState)
        updateCameraValue(cameraAnimator, value, cameraOptionsBuilder)
      } catch (e: UnsupportedOperationException) {
        logW(
          TAG,
          "Unable to calculate animated value ahead of time for ${cameraAnimator.type.name}: ${e.message}"
        )
      }
    }
    val camera = cameraOptionsBuilder.build()
    // We use totalDuration to keep track of the progress because that's the total duration of the animation. That is,
    // the time between `setUserAnimationInProgress(true)` and `setUserAnimationInProgress(false)`
    val progress = (totalDuration * totalFraction).toLong()
    CameraAnimationHintStage.Builder()
      .camera(camera)
      .progress(progress)
      .build()
  }
  return CameraAnimationHint.Builder().stages(stages).build()
}

private fun getRelativeFraction(cameraAnimator: CameraAnimator<*>, totalDuration: Long, totalFraction: Float): Float {
  val childDuration = cameraAnimator.duration
  if (childDuration <= 0L) {
    return 1f
  }
  return ((totalFraction * totalDuration - cameraAnimator.startDelay) / childDuration).coerceIn(0f, 1f)
}

internal fun updateCameraValue(
  cameraAnimator: CameraAnimator<*>,
  animatedValue: Any?,
  cameraOptionsBuilder: CameraOptions.Builder
) {
  when (cameraAnimator) {
    is CameraCenterAnimator -> cameraOptionsBuilder.center(animatedValue as? Point)
    is CameraZoomAnimator -> cameraOptionsBuilder.zoom(animatedValue as? Double)
    is CameraAnchorAnimator -> cameraOptionsBuilder.anchor(animatedValue as? ScreenCoordinate)
    is CameraPaddingAnimator -> cameraOptionsBuilder.padding(animatedValue as? EdgeInsets)
    is CameraBearingAnimator -> cameraOptionsBuilder.bearing(animatedValue as? Double)
    is CameraPitchAnimator -> cameraOptionsBuilder.pitch(animatedValue as? Double)
  }
}

/**
 * Convenience method to clear camera options so it can be reused instead of creating
 * new [CameraOptions.Builder].
 */
internal fun CameraOptions.Builder.clear() {
  center(null)
  padding(null)
  anchor(null)
  zoom(null)
  bearing(null)
  pitch(null)
}