package com.mapbox.maps.plugin.viewport.transition

import android.animation.Animator
import android.animation.AnimatorSet
import android.view.animation.Interpolator
import androidx.core.view.animation.PathInterpolatorCompat
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.viewport.ViewportCamera.Companion.VIEWPORT_CAMERA_OWNER
import kotlin.math.abs

/**
 * Helper class that provides default implementation of [ViewportCameraTransition] generators.
 */
class MapboxViewportCameraTransition(
  private val cameraManager: MapCameraManagerDelegate,
  private val cameraPlugin: CameraAnimationsPlugin
) : ViewportCameraTransition {
  override fun transitionFromLowZoomToHighZoom(
    cameraOptions: CameraOptions,
    transitionOptions: ViewportCameraTransitionOptions
  ): AnimatorSet {
    val animators = mutableListOf<Animator>()
    val currentMapCameraState = cameraManager.cameraState

    var centerDuration = 0L
    cameraOptions.center?.let { center ->
      val screenDistanceFromMapCenterToLocation = screenDistanceFromMapCenterToTarget(
        cameraManager = cameraManager,
        currentCenter = currentMapCameraState.center,
        targetCenter = center
      )

      val centerAnimationRate = 500L
      centerDuration =
        ((screenDistanceFromMapCenterToLocation / centerAnimationRate) * 1000L).toLong()
          .coerceAtMost(MAXIMUM_LOW_TO_HIGH_DURATION)
      animators += createCenterAnimator(
        center = center,
        duration = centerDuration
      )
    }

    var zoomDelay = 0L
    var zoomDuration = 0L
    cameraOptions.zoom?.let { zoom ->
      val currentMapCameraZoom = currentMapCameraState.zoom
      val zoomDelta = abs(zoom - currentMapCameraZoom)
      val zoomAnimationRate = 2.2
      zoomDelay = centerDuration / 2L
      zoomDuration = ((zoomDelta / zoomAnimationRate) * 1000L).toLong()
        .coerceAtMost(MAXIMUM_LOW_TO_HIGH_DURATION)

      animators += createZoomAnimator(
        zoom = zoom,
        startDelay = zoomDelay,
        duration = zoomDuration
      )
    }

    cameraOptions.bearing?.let { bearing ->
      val bearingDuration = 1800L
      val bearingDelay = (zoomDelay + zoomDuration - bearingDuration).coerceAtLeast(0L)
      animators += createBearingAnimator(
        rotation = normalizeBearing(currentMapCameraState.bearing, bearing),
        startDelay = bearingDelay,
        duration = bearingDuration
      )
    }

    val pitchAndPaddingDuration = 1200L
    val pitchAndPaddingDelay = (zoomDelay + zoomDuration - pitchAndPaddingDuration + 100L)
      .coerceAtLeast(0L)
    cameraOptions.pitch?.let { pitch ->
      animators += createPitchAnimator(
        pitch = pitch,
        startDelay = pitchAndPaddingDelay,
        duration = pitchAndPaddingDuration
      )
    }

    cameraOptions.padding?.let { padding ->
      animators += createPaddingAnimator(
        padding = padding,
        startDelay = pitchAndPaddingDelay,
        duration = pitchAndPaddingDuration
      )
    }

    return createAnimatorSet(animators).constraintDurationTo(transitionOptions.maxDurationMs)
  }

  override fun transitionFromHighZoomToLowZoom(
    cameraOptions: CameraOptions,
    transitionOptions: ViewportCameraTransitionOptions
  ): AnimatorSet {
    val animators = mutableListOf<Animator>()
    val currentMapCameraState = cameraManager.cameraState

    cameraOptions.center?.let { center ->
      animators += createCenterAnimator(
        center = center,
        startDelay = 800L,
        duration = 1000L
      )
    }

    cameraOptions.zoom?.let { zoom ->
      animators += createZoomAnimator(
        zoom = zoom,
        duration = 1800L
      )
    }

    cameraOptions.bearing?.let { bearing ->
      animators += createBearingAnimator(
        rotation = normalizeBearing(currentMapCameraState.bearing, bearing),
        startDelay = 600L,
        duration = 1200L
      )
    }

    cameraOptions.pitch?.let { pitch ->
      animators += createPitchAnimator(
        pitch = pitch,
        duration = 1000L
      )
    }

    cameraOptions.padding?.let { padding ->
      animators += createPaddingAnimator(
        padding = padding,
        duration = 1200L
      )
    }

    return createAnimatorSet(animators).constraintDurationTo(transitionOptions.maxDurationMs)
  }

  override fun transitionLinear(
    cameraOptions: CameraOptions,
    transitionOptions: ViewportCameraTransitionOptions
  ): AnimatorSet {
    val animators = mutableListOf<Animator>()
    val currentMapCameraState = cameraManager.cameraState

    cameraOptions.center?.let { center ->
      animators += createCenterAnimator(
        center = center,
        duration = LINEAR_ANIMATION_DURATION,
        interpolator = LINEAR_INTERPOLATOR
      )
    }

    cameraOptions.zoom?.let { zoom ->
      animators += createZoomAnimator(
        zoom = zoom,
        duration = LINEAR_ANIMATION_DURATION,
        interpolator = LINEAR_INTERPOLATOR
      )
    }

    cameraOptions.bearing?.let { bearing ->
      animators += createBearingAnimator(
        rotation = normalizeBearing(currentMapCameraState.bearing, bearing),
        duration = LINEAR_ANIMATION_DURATION,
        interpolator = LINEAR_INTERPOLATOR
      )
    }

    cameraOptions.pitch?.let { pitch ->
      animators += createPitchAnimator(
        pitch = pitch,
        duration = LINEAR_ANIMATION_DURATION,
        interpolator = LINEAR_INTERPOLATOR
      )
    }

    cameraOptions.padding?.let { padding ->
      animators += createPaddingAnimator(
        padding = padding,
        duration = LINEAR_ANIMATION_DURATION,
        interpolator = LINEAR_INTERPOLATOR
      )
    }

    return createAnimatorSet(animators).constraintDurationTo(transitionOptions.maxDurationMs)
  }

  private fun createCenterAnimator(
    center: Point,
    startDelay: Long = 0L,
    duration: Long,
    interpolator: Interpolator = SLOW_OUT_SLOW_IN_INTERPOLATOR
  ): Animator = cameraPlugin.createCenterAnimator(
    cameraAnimatorOptions(center) { owner(VIEWPORT_CAMERA_OWNER) }
  ) {
    this.startDelay = startDelay
    this.duration = duration
    this.interpolator = interpolator
  }

  private fun createZoomAnimator(
    zoom: Double,
    startDelay: Long = 0L,
    duration: Long,
    interpolator: Interpolator = SLOW_OUT_SLOW_IN_INTERPOLATOR
  ): Animator = cameraPlugin.createZoomAnimator(
    cameraAnimatorOptions(zoom) { owner(VIEWPORT_CAMERA_OWNER) }
  ) {
    this.startDelay = startDelay
    this.duration = duration
    this.interpolator = interpolator
  }

  private fun createBearingAnimator(
    rotation: Double,
    startDelay: Long = 0L,
    duration: Long,
    interpolator: Interpolator = SLOW_OUT_SLOW_IN_INTERPOLATOR
  ): Animator = cameraPlugin.createBearingAnimator(
    cameraAnimatorOptions(rotation) { owner(VIEWPORT_CAMERA_OWNER) }
  ) {
    this.startDelay = startDelay
    this.duration = duration
    this.interpolator = interpolator
  }

  private fun createPitchAnimator(
    pitch: Double,
    startDelay: Long = 0,
    duration: Long,
    interpolator: Interpolator = SLOW_OUT_SLOW_IN_INTERPOLATOR
  ): Animator = cameraPlugin.createPitchAnimator(
    cameraAnimatorOptions(pitch) { owner(VIEWPORT_CAMERA_OWNER) }
  ) {
    this.startDelay = startDelay
    this.duration = duration
    this.interpolator = interpolator
  }

  private fun createPaddingAnimator(
    padding: EdgeInsets,
    startDelay: Long = 0L,
    duration: Long,
    interpolator: Interpolator = SLOW_OUT_SLOW_IN_INTERPOLATOR
  ): Animator = cameraPlugin.createPaddingAnimator(
    cameraAnimatorOptions(padding) { owner(VIEWPORT_CAMERA_OWNER) }
  ) {
    this.startDelay = startDelay
    this.duration = duration
    this.interpolator = interpolator
  }

  companion object {
    private const val LINEAR_ANIMATION_DURATION: Long = 1000L
    private const val MAXIMUM_LOW_TO_HIGH_DURATION: Long = 3000L
    private val LINEAR_INTERPOLATOR: Interpolator = PathInterpolatorCompat.create(0f, 0f, 1f, 1f)
    private val SLOW_OUT_SLOW_IN_INTERPOLATOR: Interpolator =
      PathInterpolatorCompat.create(0.4f, 0f, 0.4f, 1f)
  }
}