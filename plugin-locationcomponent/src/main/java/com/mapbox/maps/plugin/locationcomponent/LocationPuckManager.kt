package com.mapbox.maps.plugin.locationcomponent

import android.animation.ValueAnimator
import android.content.Context
import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import androidx.core.animation.doOnEnd
import com.mapbox.bindgen.Value
import com.mapbox.common.location.LocationError
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.locationcomponent.animators.PuckAnimatorManager
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.plugin.locationcomponent.utils.take
import java.lang.ref.WeakReference
import kotlin.math.abs

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class LocationPuckManager(
  var settings: LocationComponentSettings,
  private val weakContext: WeakReference<Context>,
  private val delegateProvider: MapDelegateProvider,
  private val positionManager: LocationComponentPositionManager,
  private val animationManager: PuckAnimatorManager
) {

  @VisibleForTesting(otherwise = PRIVATE)
  internal var isHidden = true

  @VisibleForTesting(otherwise = PRIVATE)
  internal var lastLocation: Point? = null

  private val onLocationUpdated: ((Point) -> Unit) = {
    lastLocation = it
  }

  @VisibleForTesting(otherwise = PRIVATE)
  internal var lastBearing: Double = delegateProvider.mapCameraManagerDelegate.cameraState.bearing
  private val onBearingUpdated: ((Double) -> Unit) = {
    lastBearing = it
  }

  private var lastAccuracyRadius: Double = 0.0
  private val onAccuracyRadiusUpdated: ((Double) -> Unit) = {
    lastAccuracyRadius = it
  }

  @VisibleForTesting(otherwise = PRIVATE)
  internal var locationLayerRenderer = getLocationLayerRenderer(settings)

  private fun getLocationLayerRenderer(settings: LocationComponentSettings) = when (val puck = settings.locationPuck) {
    is LocationPuck2D -> {
      LayerSourceProvider.getLocationIndicatorLayerRenderer(puck, weakContext)
    }

    is LocationPuck3D -> {
      LayerSourceProvider.getModelLayerRenderer(puck)
    }
  }

  fun updateStyle(style: MapboxStyleManager) {
    locationLayerRenderer.updateStyle(style)
    positionManager.updateStyle(style)
  }

  fun initialize(style: MapboxStyleManager) {
    if (!locationLayerRenderer.isRendererInitialised()) {
      animationManager.setUpdateListeners(
        onLocationUpdated,
        onBearingUpdated,
        onAccuracyRadiusUpdated
      )
      animationManager.setLocationLayerRenderer(locationLayerRenderer)
      animationManager.applySettings(settings)
      lastLocation?.let {
        updateCurrentPosition(it)
      }
      updateCurrentBearing(lastBearing, forceUpdate = true)
      locationLayerRenderer.addLayers(positionManager)
      locationLayerRenderer.initializeComponents(style)
      styleScaling(settings)
      if (lastLocation != null && settings.enabled) {
        show(forceUpdate = true)
      } else {
        hide()
      }
    }
  }

  fun cleanUp() {
    hide()
    animationManager.onStop()
    locationLayerRenderer.clearBitmaps()
    locationLayerRenderer.removeLayers()
  }

  fun isLayerInitialised(): Boolean {
    return locationLayerRenderer.isRendererInitialised()
  }

  fun updateSettings(settings: LocationComponentSettings) {
    this.settings = settings
    positionManager.layerAbove = settings.layerAbove
    positionManager.layerBelow = settings.layerBelow
    locationLayerRenderer.clearBitmaps()
    locationLayerRenderer.removeLayers()
    locationLayerRenderer = getLocationLayerRenderer(settings)
    initialize(delegateProvider.mapStyleManagerDelegate)
  }

  //
  // Animations
  //

  fun onStart() {
    animationManager.onStart()
  }

  fun onStop() {
    animationManager.onStop()
  }

  fun updateCurrentPosition(vararg points: Point, options: (ValueAnimator.() -> Unit)? = null) {
    if (settings.enabled) {
      show()
    }
    val targets = lastLocation?.let {
      arrayOf(it, *points)
    } ?: arrayOf(*points, *points)
    animationManager.animatePosition(
      *targets,
      options = options
    )
  }

  fun updateCurrentBearing(
    vararg bearings: Double,
    options: (ValueAnimator.() -> Unit)? = null,
    forceUpdate: Boolean = false
  ) {
    if (settings.puckBearingEnabled) {
      animationManager.puckAnimationEnabled = true
      animateToBearing(bearings, options, forceUpdate)
    } else if (animationManager.puckAnimationEnabled) {
      animateToBearing(
        doubleArrayOf(0.0),
        options = {
          duration = 0
          doOnEnd {
            animationManager.puckAnimationEnabled = false
          }
        },
        forceUpdate
      )
    }
  }

  @VisibleForTesting(otherwise = PRIVATE)
  fun animateToBearing(
    bearings: DoubleArray,
    options: (ValueAnimator.() -> Unit)? = null,
    forceUpdate: Boolean
  ) {
    // Skip bearing updates if the change from the lastBearing is too small, thus avoid unnecessary calls to gl-native.
    if (!forceUpdate && abs(bearings.last() - lastBearing) < BEARING_UPDATE_THRESHOLD) {
      return
    }
    val targets = doubleArrayOf(lastBearing, *bearings)
    animationManager.animateBearing(
      *targets,
      options = options
    )
  }

  fun updateHorizontalAccuracyRadius(vararg radius: Double, options: (ValueAnimator.() -> Unit)? = null) {
    val targets = doubleArrayOf(lastAccuracyRadius, *radius)
    animationManager.animateAccuracyRadius(
      *targets,
      options = options
    )
    updateMaxPulsingRadiusToFollowAccuracyRing(radius.last())
  }

  /**
   * Update maximum radius for pulsing puck to follow location accuracy radius.
   * since pulsing radius is in pixels and location accuracy is in meters, we convert meters to pixel using
   * projection delegate.
   */
  @VisibleForTesting(otherwise = PRIVATE)
  internal fun updateMaxPulsingRadiusToFollowAccuracyRing(radius: Double) {
    if (settings.pulsingMaxRadius.toInt() == LocationComponentConstants.PULSING_MAX_RADIUS_FOLLOW_ACCURACY.toInt()) {
      val metersPerPixelAtLocation =
        delegateProvider.mapProjectionDelegate.getMetersPerPixelAtLatitude(
          delegateProvider.mapCameraManagerDelegate.cameraState.center.latitude(),
          delegateProvider.mapCameraManagerDelegate.cameraState.zoom
        )
      animationManager.updatePulsingRadius(radius / metersPerPixelAtLocation, settings)
    }
  }

  fun updateLocationAnimator(block: ValueAnimator.() -> Unit) {
    animationManager.updatePositionAnimator(block)
  }

  fun updateBearingAnimator(block: ValueAnimator.() -> Unit) {
    animationManager.updateBearingAnimator(block)
  }

  fun updateAccuracyRadiusAnimator(block: ValueAnimator.() -> Unit) {
    animationManager.updateAccuracyRadiusAnimator(block)
  }

  fun onLocationError(error: LocationError) {
    logW(TAG, "Location error: $error")
  }

  //
  // Layer action
  //
  fun show(forceUpdate: Boolean = false) {
    if (forceUpdate || isHidden) {
      isHidden = false
      locationLayerRenderer.show()
    }
  }

  fun hide() {
    isHidden = true
    locationLayerRenderer.hide()
  }

  /**
   * Function to set scaling for [LocationPuck].
   */
  @VisibleForTesting(otherwise = PRIVATE)
  internal fun styleScaling(settings: LocationComponentSettings) {
    when (val puck = settings.locationPuck) {
      is LocationPuck2D -> {
        puck.scaleExpression
      }
      is LocationPuck3D -> {
        puck.modelScaleExpression
      }
    }?.let {
      locationLayerRenderer.styleScaling(Value.fromJson(it).take())
    }
  }

  private companion object {
    // Skip bearing updates if the change from the lastBearing is too small, thus avoid unnecessary bearing animations.
    const val BEARING_UPDATE_THRESHOLD = 1.0
    const val TAG = "LocationPuckManager"
  }
}