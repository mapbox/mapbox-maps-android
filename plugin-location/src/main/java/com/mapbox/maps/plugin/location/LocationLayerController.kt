package com.mapbox.maps.plugin.location

import android.graphics.Bitmap
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.QueryFeaturesCallback
import com.mapbox.maps.RenderedQueryOptions
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.location.LocationComponentConstants.*
import com.mapbox.maps.plugin.location.MapboxAnimator.AnimationsValueChangeListener
import com.mapbox.maps.plugin.location.listeneres.OnRenderModeChangedListener
import com.mapbox.maps.plugin.location.modes.RenderMode

internal class LocationLayerController(
  private val delegateProvider: MapDelegateProvider,
  style: StyleInterface,
  layerSourceProvider: LayerSourceProvider,
  locationModelLayerOptions: LocationModelLayerOptions?,
  private val bitmapProvider: LayerBitmapProvider,
  private var options: LocationComponentOptions,
  private val internalIndicatorPositionChangedListener: OnIndicatorPositionChangedListener,
  private val internalRenderModeChangedListener: OnRenderModeChangedListener
) {
  private var mRenderMode = RenderMode.NORMAL
  var isHidden = true
    private set
  private var isStale = options.enableStaleState()
  private var positionManager: LocationComponentPositionManager =
    LocationComponentPositionManager(style, delegateProvider.styleStateDelegate, options.layerAbove(), options.layerBelow())
  private var locationLayerRenderer =
    if (locationModelLayerOptions != null) {
      layerSourceProvider.getModelLocationLayerRenderer(locationModelLayerOptions)
    } else {
      layerSourceProvider.getIndicatorLocationLayerRenderer()
    }

  init {
    initializeComponents(style, options)
  }

  fun initializeComponents(style: StyleInterface, options: LocationComponentOptions) {
    locationLayerRenderer.addLayers(positionManager)
    locationLayerRenderer.initializeComponents(style, delegateProvider.styleStateDelegate)
    applyStyle(options)
    if (isHidden) {
      hide()
    } else {
      show()
    }
  }

  fun applyStyle(options: LocationComponentOptions) {
    if (positionManager.update(options.layerAbove(), options.layerBelow())) {
      locationLayerRenderer.removeLayers()
      locationLayerRenderer.addLayers(positionManager)
      if (isHidden) {
        hide()
      }
    }
    this.options = options
    styleBitmaps(options)
    locationLayerRenderer.styleAccuracy(options.accuracyAlpha(), options.accuracyColor())
    styleScaling(options)
    locationLayerRenderer.stylePulsingCircle(options)
    determineIconsSource()
    if (!isHidden) {
      show()
    }
  }

  fun setGpsBearing(gpsBearing: Float) {
    locationLayerRenderer.setGpsBearing(gpsBearing)
  }

  fun setZoomLevel(zoomLevel: Double) {
    locationLayerRenderer.setZoomLevel(zoomLevel)
  }

  fun setRenderMode(renderMode: RenderMode) {
    if (this.mRenderMode == renderMode) {
      return
    }
    this.mRenderMode = renderMode
    styleBitmaps(options)
    determineIconsSource()
    if (!isHidden) {
      show()
    }
    internalRenderModeChangedListener.onRenderModeChanged(renderMode)
  }

  fun getRenderMode(): RenderMode {
    return mRenderMode
  }

  //
  // Layer action
  //
  fun show() {
    isHidden = false
    locationLayerRenderer.show(mRenderMode, isStale)
  }

  fun hide() {
    isHidden = true
    locationLayerRenderer.hide()
  }

  val isConsumingCompass: Boolean
    get() = mRenderMode == RenderMode.COMPASS

  //
  // Styling
  //
  private fun styleBitmaps(options: LocationComponentOptions) { // shadow
    var shadowBitmap: Bitmap? = null
    if (options.elevation() > 0) { // Only add icon elevation if the values greater than 0.
      shadowBitmap = bitmapProvider.generateShadowBitmap(options)
    }
    // background
    val backgroundBitmap = bitmapProvider.generateBitmap(
      options.backgroundDrawable(), options.backgroundTintColor()
    )
    val backgroundStaleBitmap = bitmapProvider.generateBitmap(
      options.backgroundDrawableStale(), options.backgroundStaleTintColor()
    )
    // compass bearing
    val bearingBitmap =
      bitmapProvider.generateBitmap(options.bearingDrawable(), options.bearingTintColor())
    // foreground
    var foregroundBitmap = bitmapProvider.generateBitmap(
      options.foregroundDrawable(), options.foregroundTintColor()
    )
    var foregroundStaleBitmap = bitmapProvider.generateBitmap(
      options.foregroundDrawableStale(), options.foregroundStaleTintColor()
    )
    if (mRenderMode == RenderMode.GPS) {
      foregroundBitmap = bitmapProvider.generateBitmap(
        options.gpsDrawable(), options.foregroundTintColor()
      )
      foregroundStaleBitmap = bitmapProvider.generateBitmap(
        options.gpsDrawable(), options.foregroundStaleTintColor()
      )
    }
    locationLayerRenderer.addBitmaps(
      mRenderMode,
      shadowBitmap,
      backgroundBitmap,
      backgroundStaleBitmap,
      bearingBitmap,
      foregroundBitmap,
      foregroundStaleBitmap
    )
  }

  private fun styleScaling(options: LocationComponentOptions) {
    val scaleExpression = arrayListOf(
      Value("interpolate"),
      Value(arrayListOf(Value("linear"))),
      Value(arrayListOf(Value("zoom"))),
      Value(delegateProvider.mapTransformDelegate.getBounds().minZoom!!),
      Value(options.minZoomIconScale().toDouble()),
      Value(delegateProvider.mapTransformDelegate.getBounds().maxZoom!!),
      Value(options.maxZoomIconScale().toDouble())
    )
    locationLayerRenderer.styleScaling(scaleExpression)
  }

  private fun determineIconsSource() {
    locationLayerRenderer.updateIconIds(
      FOREGROUND_ICON,
      FOREGROUND_STALE_ICON,
      BACKGROUND_ICON,
      BACKGROUND_STALE_ICON,
      BEARING_ICON
    )
  }

  fun setLocationsStale(isStale: Boolean) {
    this.isStale = isStale
    locationLayerRenderer.setLocationStale(isStale, mRenderMode)
  }

  //
  // Map click event
  //
  fun onMapClick(point: Point, callback: QueryFeaturesCallback) {
    delegateProvider.mapFeatureQueryDelegate.queryRenderedFeatures(
      delegateProvider.mapProjectionDelegate.pixelForCoordinate(point),
      RenderedQueryOptions(
        listOf(
          FOREGROUND_LAYER
        ),
        Value("all")
      ),
      callback
    )
  }

  private val latLngValueListener: AnimationsValueChangeListener<Point> =
    AnimationsValueChangeListener { value ->
      locationLayerRenderer.setLatLng(value)
      internalIndicatorPositionChangedListener.onIndicatorPositionChanged(
        Point.fromLngLat(value.longitude(), value.latitude())
      )
    }

  private val gpsBearingValueListener: AnimationsValueChangeListener<Float> =
    AnimationsValueChangeListener { value -> locationLayerRenderer.setGpsBearing(value) }

  private val compassBearingValueListener: AnimationsValueChangeListener<Float> =
    AnimationsValueChangeListener { value -> locationLayerRenderer.setCompassBearing(value) }

  private val accuracyValueListener: AnimationsValueChangeListener<Float> =
    AnimationsValueChangeListener { value -> locationLayerRenderer.setAccuracyRadius(value) }

  /**
   * The listener that handles the updating of the pulsing circle's radius and opacity.
   */
  private val pulsingCircleRadiusListener: AnimationsValueChangeListener<Float> =
    AnimationsValueChangeListener { newPulseRadiusValue ->
      var newPulseOpacityValue: Float? = null
      if (options.pulseFadeEnabled()) {
        newPulseOpacityValue = 1.0f - newPulseRadiusValue / options.pulseMaxRadius()
      }
      locationLayerRenderer.updatePulsingUi(newPulseRadiusValue, newPulseOpacityValue)
    }

  val animationListeners: Set<AnimatorListenerHolder>
    get() {
      val holders: MutableSet<AnimatorListenerHolder> =
        HashSet()
      holders.add(AnimatorListenerHolder(MapboxAnimator.ANIMATOR_LAYER_LATLNG, latLngValueListener))
      if (mRenderMode == RenderMode.GPS) {
        holders.add(
          AnimatorListenerHolder(
            MapboxAnimator.ANIMATOR_LAYER_GPS_BEARING,
            gpsBearingValueListener
          )
        )
      } else if (mRenderMode == RenderMode.COMPASS) {
        holders.add(
          AnimatorListenerHolder(
            MapboxAnimator.ANIMATOR_LAYER_COMPASS_BEARING,
            compassBearingValueListener
          )
        )
      }
      if (mRenderMode == RenderMode.COMPASS || mRenderMode == RenderMode.NORMAL) {
        holders.add(
          AnimatorListenerHolder(
            MapboxAnimator.ANIMATOR_LAYER_ACCURACY,
            accuracyValueListener
          )
        )
      }
      if (options.pulseEnabled()) {
        holders.add(
          AnimatorListenerHolder(
            MapboxAnimator.ANIMATOR_PULSING_CIRCLE,
            pulsingCircleRadiusListener
          )
        )
      }
      return holders
    }

  fun adjustPulsingCircleLayerVisibility(visible: Boolean) {
    locationLayerRenderer.adjustPulsingCircleLayerVisibility(visible)
  }

  companion object {
    private const val TAG = "Mbgl-LocationLayerController"
  }
}