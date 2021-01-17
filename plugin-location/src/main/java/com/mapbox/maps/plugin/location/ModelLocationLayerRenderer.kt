package com.mapbox.maps.plugin.location

import android.graphics.Bitmap
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.delegates.MapStyleStateDelegate
import com.mapbox.maps.plugin.location.modes.RenderMode
import kotlin.math.pow

internal class ModelLocationLayerRenderer(
  layerSourceProvider: LayerSourceProvider,
  private val locationModelLayerOptions: LocationModelLayerOptions
) :
  LocationLayerRenderer {
  private lateinit var style: StyleManagerInterface
  private var modelLayer = layerSourceProvider.getModelLayer(locationModelLayerOptions)
  private var source = layerSourceProvider.getModelSource(locationModelLayerOptions)
  private var lastLatLng: Point? = null
  private var lastBearing = 0.0
  private var lastAccuracy = 0f
  private var renderMode = RenderMode.NORMAL

  override fun initializeComponents(style: StyleManagerInterface, styleStateDelegate: MapStyleStateDelegate) {
    this.style = style
    source.bindTo(style, styleStateDelegate)
    lastLatLng?.let {
      setLatLng(it)
    }
    setLayerBearing(lastBearing)
    setAccuracyRadius(lastAccuracy)
  }

  override fun addLayers(positionManager: LocationComponentPositionManager) {
    positionManager.addLayerToMap(modelLayer)
  }

  override fun removeLayers() {
    style.removeStyleLayer(modelLayer.layerId)
  }

  override fun hide() {
    setLayerVisibility(false)
  }

  override fun show(renderMode: RenderMode, isStale: Boolean) {
    setLayerVisibility(true)
    this.renderMode = renderMode
  }

  override fun styleAccuracy(accuracyAlpha: Float, accuracyColor: Int) {
  }

  override fun setLatLng(latLng: Point) {
    setLayerLocation(latLng)
  }

  override fun setZoomLevel(zoomLevel: Double) {
    val scale = 2.0.pow(MAX_ZOOM_LEVEL - zoomLevel)
    modelLayer.modelScale(locationModelLayerOptions.modelScale.map { it * scale })
  }

  override fun setGpsBearing(gpsBearing: Float) {
    setLayerBearing(gpsBearing.toDouble())
  }

  override fun setCompassBearing(compassBearing: Float) {
    setLayerBearing(compassBearing.toDouble())
  }

  override fun setAccuracyRadius(accuracy: Float) {
    lastAccuracy = accuracy
  }

  override fun styleScaling(scaleExpression: List<Value>) {
  }

  override fun setLocationStale(isStale: Boolean, renderMode: RenderMode) {
  }

  override fun updateIconIds(
    foregroundIconString: String,
    foregroundStaleIconString: String,
    backgroundIconString: String,
    backgroundStaleIconString: String,
    bearingIconString: String
  ) { // not supported
  }

  override fun addBitmaps(
    renderMode: RenderMode,
    shadowBitmap: Bitmap?,
    backgroundBitmap: Bitmap,
    backgroundStaleBitmap: Bitmap,
    bearingBitmap: Bitmap,
    foregroundBitmap: Bitmap,
    foregroundStaleBitmap: Bitmap
  ) {
    // Reset bearing when render mode change to normal
    if (renderMode == RenderMode.NORMAL) {
      setLayerBearing(0.0)
    }
  }

  private fun setLayerVisibility(visible: Boolean) {
    modelLayer.visibility(visible)
  }

  private fun setLayerLocation(latLng: Point) {
    val modelValues = listOf(latLng.longitude(), latLng.latitude())
    source.setPosition(modelValues)
    lastLatLng = latLng
  }

  private fun setLayerBearing(bearing: Double) {
    val orientation = locationModelLayerOptions.modelRotation.toMutableList()
    when (renderMode) {
      RenderMode.GPS,
      RenderMode.COMPASS -> {
        orientation[2] = orientation[2] + bearing
        modelLayer.modelRotation(orientation)
      }

      RenderMode.NORMAL -> {
        modelLayer.modelRotation(orientation)
      }
    }
    lastBearing = orientation[2]
  }

  /**
   * Adjust the visibility of the pulsing LocationComponent circle.
   */
  override fun adjustPulsingCircleLayerVisibility(visible: Boolean) {
  }

  /**
   * Adjust the the pulsing LocationComponent circle based on the set options.
   */
  override fun stylePulsingCircle(options: LocationComponentOptions) {
  }

  /**
   * Adjust the visual appearance of the pulsing LocationComponent circle.
   */
  override fun updatePulsingUi(
    radius: Float,
    opacity: Float?
  ) {
  }

  companion object {
    internal const val MAX_ZOOM_LEVEL = 19
  }
}