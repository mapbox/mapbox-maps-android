package com.mapbox.maps.plugin.locationcomponent

import android.animation.ValueAnimator
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.LocationPuck
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.locationcomponent.animators.PuckAnimatorManager
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import kotlin.math.pow

internal class LocationPuckManager(
  var settings: LocationComponentSettings,
  private val delegateProvider: MapDelegateProvider,
  style: StyleManagerInterface,
  private val layerSourceProvider: LayerSourceProvider,
  private val bitmapProvider: LayerBitmapProvider,
  private val presetProvider: PuckPresetProvider
) {

  var isHidden = true
    private set

  private var positionManager =
    LocationComponentPositionManager(style, settings.layerAbove, settings.layerBelow)

  private var lastStaleState = true

  private var lastLocation: Point = delegateProvider.mapCameraDelegate.getCameraOptions(null).center!!

  private var lastBearing: Double = delegateProvider.mapCameraDelegate.getBearing()

  private fun getLocationPuck(locationSettings: LocationComponentSettings): LocationPuck {
    return locationSettings.locationPuck ?: presetProvider.getPresetPuck(settings.presetPuckStyle)
  }

  private var locationLayerRenderer =
    when (val puck = getLocationPuck(settings)) {
      is LocationPuck2D -> {
        layerSourceProvider.getLocationIndicatorLayerRenderer(puck)
      }
      is LocationPuck3D -> {
        layerSourceProvider.getModelLayerRenderer(puck)
      }
    }

  private val animationManager = PuckAnimatorManager().apply {
    setLocationLayerRenderer(locationLayerRenderer)
  }

  fun initialize(style: StyleManagerInterface) {
    locationLayerRenderer.addLayers(positionManager)
    locationLayerRenderer.initializeComponents(style)
    val puck = getLocationPuck(settings)
    if (puck is LocationPuck2D) {
      prepareLocationIndicatorLayerBitmaps(puck)
      if (settings.pulsingEnabled) {
        animationManager.enablePulsingAnimation(settings.pulsingMaxRadius)
      }
    }
    styleScaling(settings)
    updateCurrentPosition(lastLocation)
    updateCurrentBearing(lastBearing)
    setLocationsStale(lastStaleState)
    if (settings.enabled) {
      show()
    } else {
      hide()
    }
  }

  fun isLayerInitialised(): Boolean {
    return locationLayerRenderer.isLayerInitialised()
  }

  fun updateSettings(settings: LocationComponentSettings) {
    this.settings = settings
    locationLayerRenderer.clearBitmaps()
    locationLayerRenderer.removeLayers()
    val locationPuck =
      settings.locationPuck ?: presetProvider.getPresetPuck(settings.presetPuckStyle)
    locationLayerRenderer = when (locationPuck) {
      is LocationPuck2D -> {
        layerSourceProvider.getLocationIndicatorLayerRenderer(locationPuck)
      }
      is LocationPuck3D -> {
        layerSourceProvider.getModelLayerRenderer(locationPuck)
      }
    }
    animationManager.setLocationLayerRenderer(locationLayerRenderer)
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

  fun updateCurrentPosition(vararg points: Point) {
    val targets = arrayOf(lastLocation, *points)
    lastLocation = arrayOf(*points).last()
    animationManager.animatePoints(*targets)
  }

  fun updateCurrentBearing(vararg bearings: Double) {
    val targets = doubleArrayOf(lastBearing, *bearings)
    lastBearing = doubleArrayOf(*bearings).last()
    animationManager.animateBearing(*targets)
  }

  fun updateLocationAnimator(block: ValueAnimator.() -> Unit) {
    animationManager.updatePointAnimator(block)
  }

  fun updateBearingAnimator(block: ValueAnimator.() -> Unit) {
    animationManager.updateBearingAnimator(block)
  }

  //
  // Layer action
  //
  fun show() {
    isHidden = false
    locationLayerRenderer.show(lastStaleState)
  }

  fun hide() {
    isHidden = true
    locationLayerRenderer.hide()
  }

  private fun prepareLocationIndicatorLayerBitmaps(puck: LocationPuck2D) {
    val topBitmap = puck.topImage?.let { bitmapProvider.generateBitmap(it, puck.topTintColor) }
    val topStaleBitmap =
      puck.topImage?.let { bitmapProvider.generateBitmap(it, puck.topStaleTintColor) }
    val bearingBitmap =
      puck.bearingImage?.let { bitmapProvider.generateBitmap(it, puck.bearingTintColor) }
    val bearingStaleBitmap =
      puck.bearingImage?.let { bitmapProvider.generateBitmap(it, puck.bearingStaleTintColor) }
    val shadowBitmap =
      puck.shadowImage?.let { bitmapProvider.generateBitmap(it, puck.shadowTintColor) }
    val shadowStaleBitmap =
      puck.shadowImage?.let { bitmapProvider.generateBitmap(it, puck.shadowStaleTintColor) }

    locationLayerRenderer.addBitmaps(
      topBitmap,
      topStaleBitmap,
      bearingBitmap,
      bearingStaleBitmap,
      shadowBitmap,
      shadowStaleBitmap
    )
  }

  private fun styleScaling(settings: LocationComponentSettings) {
    val puck = getLocationPuck(settings)
    val minZoom = delegateProvider.mapTransformDelegate.getBounds().minZoom ?: 0.0
    val maxZoom = delegateProvider.mapTransformDelegate.getBounds().maxZoom ?: 19.0
    val scaleExpression = when (puck) {
      is LocationPuck2D -> {
        arrayListOf(
          Value("interpolate"),
          Value(arrayListOf(Value("linear"))),
          Value(arrayListOf(Value("zoom"))),
          Value(minZoom),
          Value(settings.minZoomIconScale.toDouble()),
          Value(maxZoom),
          Value(settings.maxZoomIconScale.toDouble())
        )
      }
      is LocationPuck3D -> {
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
      }
    }
    locationLayerRenderer.styleScaling(scaleExpression)
  }

  fun setLocationsStale(isStale: Boolean) {
    this.lastStaleState = isStale
    locationLayerRenderer.setLocationStale(isStale)
  }
}