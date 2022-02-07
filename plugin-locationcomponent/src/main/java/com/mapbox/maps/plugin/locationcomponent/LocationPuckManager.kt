package com.mapbox.maps.plugin.locationcomponent

import android.animation.ValueAnimator
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxLocationComponentException
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.locationcomponent.animators.PuckAnimatorManager
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import kotlin.math.pow

internal class LocationPuckManager(
  var settings: LocationComponentSettings,
  private val delegateProvider: MapDelegateProvider,
  private val positionManager: LocationComponentPositionManager,
  private val layerSourceProvider: LayerSourceProvider,
  private val animationManager: PuckAnimatorManager
) {

  var isHidden = true
    private set

  @VisibleForTesting(otherwise = PRIVATE)
  internal var lastLocation: Point? = null
  private val onLocationUpdated: ((Point) -> Unit) = {
    lastLocation = it
  }

  private var lastBearing: Double = delegateProvider.mapCameraManagerDelegate.cameraState.bearing
  private val onBearingUpdated: ((Double) -> Unit) = {
    lastBearing = it
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var locationLayerRenderer =
    when (val puck = settings.locationPuck) {
      is LocationPuck2D -> {
        layerSourceProvider.getLocationIndicatorLayerRenderer(puck)
      }
      is LocationPuck3D -> {
        layerSourceProvider.getModelLayerRenderer(puck)
      }
    }

  fun initialize(style: StyleInterface) {
    if (!locationLayerRenderer.isRendererInitialised()) {
      animationManager.setUpdateListeners(onLocationUpdated, onBearingUpdated)
      animationManager.setLocationLayerRenderer(locationLayerRenderer)
      animationManager.applyPulsingAnimationSettings(settings)
      locationLayerRenderer.addLayers(positionManager)
      lastLocation?.let {
        updateCurrentPosition(it)
      }
      updateCurrentBearing(lastBearing)
      locationLayerRenderer.initializeComponents(style)
      styleScaling(settings)
      if (lastLocation != null && settings.enabled) {
        show()
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
    locationLayerRenderer = when (val locationPuck = settings.locationPuck) {
      is LocationPuck2D -> {
        layerSourceProvider.getLocationIndicatorLayerRenderer(locationPuck)
      }
      is LocationPuck3D -> {
        layerSourceProvider.getModelLayerRenderer(locationPuck)
      }
    }
    delegateProvider.getStyle {
      initialize(it)
    }
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
    val targets = lastLocation?.let {
      show()
      arrayOf(it, *points)
    } ?: arrayOf(*points, *points)
    animationManager.animatePosition(
      *targets,
      options = options
    )
  }

  fun updateCurrentBearing(vararg bearings: Double, options: (ValueAnimator.() -> Unit)? = null) {
    val targets = doubleArrayOf(lastBearing, *bearings)
    animationManager.animateBearing(
      *targets,
      options = options
    )
  }

  fun updateLocationAnimator(block: ValueAnimator.() -> Unit) {
    animationManager.updatePositionAnimator(block)
  }

  fun updateBearingAnimator(block: ValueAnimator.() -> Unit) {
    animationManager.updateBearingAnimator(block)
  }

  //
  // Layer action
  //
  fun show() {
    isHidden = false
    locationLayerRenderer.show()
  }

  fun hide() {
    isHidden = true
    locationLayerRenderer.hide()
  }

  /**
   * Function to set scaling for [LocationPuck].
   * In order to keep 3D puck size constant across all zoom levels, we interpolate the model based on
   * current zoom level. MIN_ZOOM, MAX_ZOOM are used as two anchor points to calculate
   * the scale expression.
   */
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal fun styleScaling(settings: LocationComponentSettings) {
    when (val puck = settings.locationPuck) {
      is LocationPuck2D -> {
        val scaleExpression = puck.scaleExpression
        if (scaleExpression != null) {
          locationLayerRenderer.styleScaling(Value.fromJson(scaleExpression).take())
        }
      }
      is LocationPuck3D -> {
        val modelScaleConstant = 2.0.pow(MAX_ZOOM - MIN_ZOOM)
        val modelScaleExpression = puck.modelScaleExpression
        val scaleExpression = if (modelScaleExpression == null) {
          Value(
            arrayListOf(
              Value("interpolate"),
              Value(arrayListOf(Value("exponential"), Value(0.5))),
              Value(arrayListOf(Value("zoom"))),
              Value(MIN_ZOOM),
              Value(
                arrayListOf(
                  Value("literal"),
                  Value(
                    arrayListOf(
                      Value(modelScaleConstant * puck.modelScale[0].toDouble()),
                      Value(modelScaleConstant * puck.modelScale[1].toDouble()),
                      Value(modelScaleConstant * puck.modelScale[2].toDouble())
                    )
                  )
                )
              ),
              Value(MAX_ZOOM),
              Value(
                arrayListOf(
                  Value("literal"),
                  Value(
                    arrayListOf(
                      Value(puck.modelScale[0].toDouble()),
                      Value(puck.modelScale[1].toDouble()),
                      Value(puck.modelScale[2].toDouble())
                    )
                  )
                )
              )
            )
          )
        } else {
          Value.fromJson(modelScaleExpression).take()
        }
        locationLayerRenderer.styleScaling(scaleExpression)
      }
    }
  }

  private companion object {
    const val MIN_ZOOM = 0.50
    const val MAX_ZOOM = 22.0
  }
}

/**
 * Internal function to check if a method invoke on Value succeeded, throws exception if not.
 */
private inline fun <reified T> Expected<String, T>.take(): T {
  this.also {
    it.error?.let { err ->
      throw MapboxLocationComponentException(err)
    }
    it.value?.let { v ->
      return v
    }
  }
  throw MapboxLocationComponentException("Error in parsing expression.")
}