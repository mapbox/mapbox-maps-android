package com.mapbox.maps.plugin.locationcomponent.animators

import android.animation.ValueAnimator
import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.locationcomponent.*
import com.mapbox.maps.plugin.locationcomponent.LocationLayerRenderer
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.util.MathUtils

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class PuckAnimatorManager(
  indicatorPositionChangedListener: OnIndicatorPositionChangedListener,
  indicatorBearingChangedListener: OnIndicatorBearingChangedListener,
  indicatorAccuracyRadiusChangedListener: OnIndicatorAccuracyRadiusChangedListener,
  pixelRatio: Float
) {
  private var bearingAnimator = PuckBearingAnimator(indicatorBearingChangedListener)
  private var positionAnimator = PuckPositionAnimator(indicatorPositionChangedListener)
  private var accuracyRadiusAnimator =
    PuckAccuracyRadiusAnimator(indicatorAccuracyRadiusChangedListener)
  private var pulsingAnimator = PuckPulsingAnimator(pixelRatio)

  internal var puckAnimationEnabled: Boolean
    get() = bearingAnimator.enabled
    set(value) {
      bearingAnimator.enabled = value
    }

  @VisibleForTesting(otherwise = PRIVATE)
  constructor(
    indicatorPositionChangedListener: OnIndicatorPositionChangedListener,
    indicatorBearingChangedListener: OnIndicatorBearingChangedListener,
    indicatorAccuracyRadiusChangedListener: OnIndicatorAccuracyRadiusChangedListener,
    bearingAnimator: PuckBearingAnimator,
    positionAnimator: PuckPositionAnimator,
    pulsingAnimator: PuckPulsingAnimator,
    radiusAnimator: PuckAccuracyRadiusAnimator,
    pixelRatio: Float
  ) : this(
    indicatorPositionChangedListener,
    indicatorBearingChangedListener,
    indicatorAccuracyRadiusChangedListener,
    pixelRatio
  ) {
    this.bearingAnimator = bearingAnimator
    this.positionAnimator = positionAnimator
    this.pulsingAnimator = pulsingAnimator
    this.accuracyRadiusAnimator = radiusAnimator
  }

  fun setLocationLayerRenderer(renderer: LocationLayerRenderer) {
    bearingAnimator.setLocationLayerRenderer(renderer)
    positionAnimator.setLocationLayerRenderer(renderer)
    pulsingAnimator.setLocationLayerRenderer(renderer)
    accuracyRadiusAnimator.setLocationLayerRenderer(renderer)
  }

  fun setUpdateListeners(
    onLocationUpdated: ((Point) -> Unit),
    onBearingUpdated: ((Double) -> Unit),
    onAccuracyRadiusUpdated: ((Double) -> Unit)
  ) {
    positionAnimator.setUpdateListener(onLocationUpdated)
    bearingAnimator.setUpdateListener(onBearingUpdated)
    accuracyRadiusAnimator.setUpdateListener(onAccuracyRadiusUpdated)
  }

  fun onStart() {
    if (pulsingAnimator.enabled) {
      pulsingAnimator.animateInfinite()
    }
  }

  fun onStop() {
    bearingAnimator.cancelRunning()
    positionAnimator.cancelRunning()
    pulsingAnimator.cancelRunning()
    accuracyRadiusAnimator.cancelRunning()
  }

  fun animateBearing(
    vararg targets: Double,
    options: (ValueAnimator.() -> Unit)?
  ) {
    bearingAnimator.animate(
      *MathUtils.prepareOptimalBearingPath(targets).toTypedArray(),
      options = options
    )
  }

  fun animatePosition(
    vararg targets: Point,
    options: (ValueAnimator.() -> Unit)?
  ) {
    positionAnimator.animate(*targets, options = options)
  }

  fun animateAccuracyRadius(
    vararg targets: Double,
    options: (ValueAnimator.() -> Unit)?
  ) {
    accuracyRadiusAnimator.animate(*targets.toTypedArray(), options = options)
  }

  fun updatePulsingRadius(target: Double, settings: LocationComponentSettings) {
    pulsingAnimator.apply {
      enabled = settings.pulsingEnabled
      if (settings.pulsingEnabled) {
        maxRadius = target
        animateInfinite()
      } else {
        cancelRunning()
      }
    }
  }

  fun applySettings(settings: LocationComponentSettings) {
    pulsingAnimator.apply {
      enabled = settings.pulsingEnabled
      maxRadius = settings.pulsingMaxRadius.toDouble()
      pulsingColor = settings.pulsingColor
      if (settings.pulsingEnabled) {
        animateInfinite()
      } else {
        cancelRunning()
      }
    }
    accuracyRadiusAnimator.apply {
      enabled = settings.showAccuracyRing
      accuracyCircleColor = settings.accuracyRingColor
      accuracyCircleBorderColor = settings.accuracyRingBorderColor
    }
  }

  fun updateBearingAnimator(block: ValueAnimator.() -> Unit) {
    bearingAnimator.updateOptions(block)
  }

  fun updatePositionAnimator(block: ValueAnimator.() -> Unit) {
    positionAnimator.updateOptions(block)
  }

  fun updateAccuracyRadiusAnimator(block: ValueAnimator.() -> Unit) {
    accuracyRadiusAnimator.updateOptions(block)
  }
}