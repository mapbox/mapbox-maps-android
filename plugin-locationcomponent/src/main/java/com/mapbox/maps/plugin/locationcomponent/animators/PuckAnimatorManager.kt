package com.mapbox.maps.plugin.locationcomponent.animators

import android.animation.ValueAnimator
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.locationcomponent.*
import com.mapbox.maps.plugin.locationcomponent.LocationLayerRenderer
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings2
import com.mapbox.maps.util.MathUtils

internal class PuckAnimatorManager(
  indicatorPositionChangedListener: OnIndicatorPositionChangedListener,
  indicatorBearingChangedListener: OnIndicatorBearingChangedListener,
  indicatorAccuracyRadiusChangedListener: OnIndicatorAccuracyRadiusChangedListener
) {

  private var bearingAnimator = PuckBearingAnimator(indicatorBearingChangedListener)
  private var positionAnimator = PuckPositionAnimator(indicatorPositionChangedListener)
  private var accuracyRadiusAnimator =
    PuckAccuracyRadiusAnimator(indicatorAccuracyRadiusChangedListener)
  private var pulsingAnimator = PuckPulsingAnimator()

  @VisibleForTesting(otherwise = PRIVATE)
  constructor(
    indicatorPositionChangedListener: OnIndicatorPositionChangedListener,
    indicatorBearingChangedListener: OnIndicatorBearingChangedListener,
    indicatorAccuracyRadiusChangedListener: OnIndicatorAccuracyRadiusChangedListener,
    bearingAnimator: PuckBearingAnimator,
    positionAnimator: PuckPositionAnimator,
    pulsingAnimator: PuckPulsingAnimator,
    radiusAnimator: PuckAccuracyRadiusAnimator
  ) : this(
    indicatorPositionChangedListener,
    indicatorBearingChangedListener,
    indicatorAccuracyRadiusChangedListener
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

  fun applySettings2(settings2: LocationComponentSettings2) {
    accuracyRadiusAnimator.apply {
      enabled = settings2.showAccuracyRing
      accuracyCircleColor = settings2.accuracyRingColor
      accuracyCircleBorderColor = settings2.accuracyRingBorderColor
    }
    bearingAnimator.apply {
      enabled = settings2.puckBearingEnabled
    }
  }

  fun applyPulsingAnimationSettings(settings: LocationComponentSettings) {
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