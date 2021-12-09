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

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal fun styleScaling(settings: LocationComponentSettings) {
    val puck = settings.locationPuck
    val minZoom = delegateProvider.mapCameraManagerDelegate.getBounds().minZoom
    val maxZoom = delegateProvider.mapCameraManagerDelegate.getBounds().maxZoom
    when (puck) {
      is LocationPuck2D -> {
        val scaleExpression = puck.scaleExpression
        if (scaleExpression != null) {
          locationLayerRenderer.styleScaling(Value.fromJson(scaleExpression).take())
        }
      }
      is LocationPuck3D -> {
        val modelScaleExpression = puck.modelScaleExpression
        val scaleExpression = if (modelScaleExpression == null) {
          Value(
            arrayListOf(
              Value("interpolate"),
              Value(arrayListOf(Value("exponential"), Value(0.5))),
              Value(arrayListOf(Value("zoom"))),
              Value(minZoom),
              Value(
                arrayListOf(
                  Value("literal"),
                  Value(
                    arrayListOf(
                      Value(2.0.pow(maxZoom - minZoom) * puck.modelScale[0].toDouble()),
                      Value(2.0.pow(maxZoom - minZoom) * puck.modelScale[1].toDouble()),
                      Value(2.0.pow(maxZoom - minZoom) * puck.modelScale[2].toDouble())
                    )
                  )
                )
              ),
              Value(maxZoom),
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