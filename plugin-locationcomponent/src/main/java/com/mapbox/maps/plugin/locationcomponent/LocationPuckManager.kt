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
import com.mapbox.maps.plugin.MapProjection
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.locationcomponent.animators.PuckAnimatorManager
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings2
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.pow

internal class LocationPuckManager(
  var settings: LocationComponentSettings,
  var settings2: LocationComponentSettings2,
  private val delegateProvider: MapDelegateProvider,
  private val positionManager: LocationComponentPositionManager,
  private val layerSourceProvider: LayerSourceProvider,
  private val animationManager: PuckAnimatorManager
) {

  var isHidden = true
    private set

  @VisibleForTesting(otherwise = PRIVATE)
  internal var lastLocation: Point? = null

  @VisibleForTesting(otherwise = PRIVATE)
  internal var lastMercatorScale = 1.0
    set(value) {
      if (abs(value - field) > MERCATOR_SCALE_THRESHOLD) {
        field = value
        (settings.locationPuck as? LocationPuck3D)?.let { locationPuck3D ->
          locationLayerRenderer.styleScaling(get3DPuckScaleExpression(locationPuck3D, field))
        }
      }
    }

  private val onLocationUpdated: ((Point) -> Unit) = {
    lastLocation = it
    if (settings.locationPuck is LocationPuck3D) {
      val latitude =
        if (delegateProvider.mapProjectionDelegate.getMapProjection() == MapProjection.Globe) {
          delegateProvider.mapCameraManagerDelegate.cameraState.center.latitude()
        } else it.latitude()
      lastMercatorScale = mercatorScale(latitude)
    }
  }

  private var lastBearing: Double = delegateProvider.mapCameraManagerDelegate.cameraState.bearing
  private val onBearingUpdated: ((Double) -> Unit) = {
    lastBearing = it
  }

  private var lastAccuracyRadius: Double = 0.0
  private val onAccuracyRadiusUpdated: ((Double) -> Unit) = {
    lastAccuracyRadius = it
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

  fun updateStyle(style: StyleInterface) {
    locationLayerRenderer.updateStyle(style)
    positionManager.updateStyle(style)
  }

  fun initialize(style: StyleInterface) {
    if (!locationLayerRenderer.isRendererInitialised()) {
      animationManager.setUpdateListeners(
        onLocationUpdated,
        onBearingUpdated,
        onAccuracyRadiusUpdated
      )
      animationManager.setLocationLayerRenderer(locationLayerRenderer)
      animationManager.applyPulsingAnimationSettings(settings)
      animationManager.applySettings2(settings2)
      lastLocation?.let {
        updateCurrentPosition(it)
      }
      updateCurrentBearing(lastBearing)
      locationLayerRenderer.addLayers(positionManager)
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

  fun updateSettings2(settings2: LocationComponentSettings2) {
    this.settings2 = settings2
    animationManager.applySettings2(settings2)
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

  fun updateAccuracyRadius(vararg radius: Double, options: (ValueAnimator.() -> Unit)? = null) {
    val targets = doubleArrayOf(lastAccuracyRadius, *radius)
    animationManager.animateAccuracyRadius(
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

  fun updateAccuracyRadiusAnimator(block: ValueAnimator.() -> Unit) {
    animationManager.updateAccuracyRadiusAnimator(block)
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
        locationLayerRenderer.styleScaling(get3DPuckScaleExpression(puck, lastMercatorScale))
      }
    }
  }

  private fun get3DPuckScaleExpression(puck: LocationPuck3D, mercatorScale: Double): Value {
    val modelScaleConstant = 2.0.pow(MAX_ZOOM - MIN_ZOOM)
    val modelScaleExpression = puck.modelScaleExpression
    return if (modelScaleExpression == null) {
      Value(
        arrayListOf(
          Value("interpolate"),
          Value(arrayListOf(Value("exponential"), Value(PUCK_3D_EXPONENTIAL_EXPRESSION_BASE))),
          Value(arrayListOf(Value("zoom"))),
          Value(MIN_ZOOM),
          Value(
            arrayListOf(
              Value("literal"),
              Value(
                arrayListOf(
                  Value(modelScaleConstant * puck.modelScale[0].toDouble() * mercatorScale),
                  Value(modelScaleConstant * puck.modelScale[1].toDouble() * mercatorScale),
                  Value(modelScaleConstant * puck.modelScale[2].toDouble() * mercatorScale)
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
                  Value(puck.modelScale[0].toDouble() * mercatorScale),
                  Value(puck.modelScale[1].toDouble() * mercatorScale),
                  Value(puck.modelScale[2].toDouble() * mercatorScale)
                )
              )
            )
          )
        )
      )
    } else {
      Value.fromJson(modelScaleExpression).take()
    }
  }

  private fun mercatorScale(lat: Double): Double {
    // In Mercator projection the scale factor is changed along the meridians as a function of latitude
    // to keep the scale factor equal in all direction: k=sec(latitude), where sec(α) = 1 / cos(α).
    // Here we are inverting the logic, as the 3d puck is using real-world size, and we are revising
    // the appearance to look constant on a mercator projection map.
    return cos(
      // convert decimal latitude degrees to radians
      lat.coerceIn(-LATITUDE_MAX, LATITUDE_MAX) * Math.PI / 180.0
    )
  }

  private companion object {
    const val MIN_ZOOM = 0.50
    const val MAX_ZOOM = 22.0
    // To make the 3D puck's size constant across different zoom levels, the 3D puck's size (real world object size)
    // should be exponential to the zoom level.
    // The base of the exponential expression is decided by how the tile pyramid works: at zoom level n, we have 2^(n+1)
    // tiles to cover the earth.
    const val PUCK_3D_EXPONENTIAL_EXPRESSION_BASE = 0.5

    // We display most of the world at the lowest zoom level as a single square image, excluding the
    // polar regions by truncation at latitudes of φmax = ±85.05113°.
    // refs: https://en.wikipedia.org/wiki/Mercator_projection#:~:text=Web%20Mercator,-Main%20article%3A%20Web&text=The%20major%20online%20street%20mapping,%CF%86max%20%3D%20%C2%B185.05113%C2%B0
    const val LATITUDE_MAX = 85.051128779806604

    // Threshold to update the mercator scale factor when the latitude changes, so that we don't update the
    // scale expression too frequently and cause performance issues.
    const val MERCATOR_SCALE_THRESHOLD = 0.01
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