package com.mapbox.maps.plugin.locationcomponent

import android.graphics.Bitmap
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_SOURCE

internal class ModelLayerRenderer(
  layerSourceProvider: LayerSourceProvider,
  private val locationModelLayerOptions: LocationPuck3D
) :
  LocationLayerRenderer {
  private var style: StyleManagerInterface? = null
  private var modelLayer = layerSourceProvider.getModelLayer(locationModelLayerOptions)
  private var source = layerSourceProvider.getModelSource(locationModelLayerOptions)

  override fun initializeComponents(style: StyleManagerInterface) {
    this.style = style
    source.bindTo(style)
  }

  override fun isLayerInitialised(): Boolean {
    return style?.styleSourceExists(MODEL_SOURCE) ?: false
  }

  override fun addLayers(positionManager: LocationComponentPositionManager) {
    positionManager.addLayerToMap(modelLayer)
  }

  override fun removeLayers() {
    style?.removeStyleLayer(modelLayer.layerId)
    style?.removeStyleSource(source.sourceId)
  }

  override fun hide() {
    setLayerVisibility(false)
  }

  override fun show(isStale: Boolean) {
    setLayerVisibility(true)
  }

  override fun styleAccuracy(accuracyAlpha: Float, accuracyColor: Int) {
  }

  override fun setLatLng(latLng: Point) {
    setLayerLocation(latLng)
  }

  override fun setBearing(bearing: Double) {
    setLayerBearing(bearing)
  }

  override fun setAccuracyRadius(accuracy: Float) {
  }

  override fun styleScaling(scaleExpression: List<Value>) {
    modelLayer.modelScaleExpression(scaleExpression)
  }

  override fun setLocationStale(isStale: Boolean) {
  }

  private fun setLayerVisibility(visible: Boolean) {
    modelLayer.visibility(visible)
  }

  private fun setLayerLocation(latLng: Point) {
    val modelValues = listOf(latLng.longitude(), latLng.latitude())
    source.setPosition(modelValues)
  }

  private fun setLayerBearing(bearing: Double) {
    val orientation = locationModelLayerOptions.modelRotation.map { it.toDouble() }.toMutableList()
    orientation[2] = orientation[2] + bearing
    modelLayer.modelRotation(orientation)
  }

  /**
   * Adjust the visibility of the pulsing LocationComponent circle.
   */
  override fun adjustPulsingCircleLayerVisibility(visible: Boolean) {
  }

  /**
   * Adjust the visual appearance of the pulsing LocationComponent circle.
   */
  override fun updatePulsingUi(pulsingColorInt: Int, radius: Float, opacity: Float?) {
  }

  override fun addBitmaps(
    topBitmap: Bitmap?,
    topStaleBitmap: Bitmap?,
    bearingBitmap: Bitmap?,
    bearingStaleBitmap: Bitmap?,
    shadowBitmap: Bitmap?,
    shadowStaleBitmap: Bitmap?
  ) {
  }

  override fun clearBitmaps() {
  }
}