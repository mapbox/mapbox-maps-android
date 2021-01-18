package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.LocationPuck
import com.mapbox.maps.plugin.ThreeDLocationPuck
import com.mapbox.maps.plugin.TwoDLocationPuck
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import kotlin.math.pow

internal class LocationPuckManagerImpl(
  override var settings: LocationComponentSettings,
  private val delegateProvider: MapDelegateProvider,
  style: StyleManagerInterface,
  private val layerSourceProvider: LayerSourceProvider,
  private val bitmapProvider: LayerBitmapProvider,
  private val presetProvider: PuckPresetProvider
) : LocationPuckManager {

  var isHidden = true
    private set

  private var positionManager =
    LocationComponentPositionManager(style, settings.layerAbove, settings.layerBelow)

  private var lastStaleState = true

  private var lastLocation: Point? = null

  private var lastBearing: Float = 0.0f

  private fun getLocationPuck(locationSettings: LocationComponentSettings): LocationPuck {
    return locationSettings.locationPuck ?: presetProvider.getPresetPuck(settings.presetPuckStyle)
  }

  private var locationLayerRenderer =
    when (val puck = getLocationPuck(settings)) {
      is TwoDLocationPuck -> {
        layerSourceProvider.getLocationIndicatorLayerRenderer(puck)
      }
      is ThreeDLocationPuck -> {
        layerSourceProvider.getModelLayerRenderer(puck)
      }
    }

  override fun initialize(style: StyleManagerInterface) {
    locationLayerRenderer.addLayers(positionManager)
    locationLayerRenderer.initializeComponents(style)
    val puck = getLocationPuck(settings)
    if (puck is TwoDLocationPuck) {
      prepareLocationIndicatorLayerBitmaps(puck)
    }
    styleScaling(settings)
    lastLocation?.let {
      updateCurrentPosition(it)
    }
    updateCurrentBearing(lastBearing)
    setLocationsStale(lastStaleState)
    if (settings.enabled) {
      show()
    } else {
      hide()
    }
  }

  override fun isLayerInitialised(): Boolean {
    return locationLayerRenderer.isLayerInitialised()
  }

  override fun updateSettings(settings: LocationComponentSettings) {
    this.settings = settings
    locationLayerRenderer.clearBitmaps()
    locationLayerRenderer.removeLayers()
    val locationPuck =
      settings.locationPuck ?: presetProvider.getPresetPuck(settings.presetPuckStyle)
    locationLayerRenderer = when (locationPuck) {
      is TwoDLocationPuck -> {
        layerSourceProvider.getLocationIndicatorLayerRenderer(locationPuck)
      }
      is ThreeDLocationPuck -> {
        layerSourceProvider.getModelLayerRenderer(locationPuck)
      }
    }
    delegateProvider.getStyle {
      initialize(it)
    }
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

  override fun updateCurrentPosition(point: Point) {
    lastLocation = point
    locationLayerRenderer.setLatLng(point)
  }

  override fun updateCurrentBearing(bearing: Float) {
    lastBearing = bearing
    locationLayerRenderer.setBearing(bearing)
  }

  private fun prepareLocationIndicatorLayerBitmaps(puck: TwoDLocationPuck) {
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
      is TwoDLocationPuck -> {
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
      is ThreeDLocationPuck -> {
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