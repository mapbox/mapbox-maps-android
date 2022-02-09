package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_LAYER
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_SOURCE

internal class ModelLayerRenderer(
  layerSourceProvider: LayerSourceProvider,
  private val locationModelLayerOptions: LocationPuck3D
) :
  LocationLayerRenderer {
  private var style: StyleInterface? = null
  private var modelLayer = layerSourceProvider.getModelLayer(locationModelLayerOptions)
  private var source = layerSourceProvider.getModelSource(locationModelLayerOptions)

  override fun initializeComponents(style: StyleInterface) {
    this.style = style
    source.bindTo(style)
  }

  override fun isRendererInitialised(): Boolean {
    return isLayerInitialised() && isSourceInitialised()
  }

  private fun isLayerInitialised(): Boolean {
    return style?.styleLayerExists(MODEL_LAYER) ?: false
  }

  private fun isSourceInitialised(): Boolean {
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

  override fun show() {
    setLayerVisibility(true)
  }

  override fun styleAccuracy(accuracyColor: Int, accuracyBorderColor: Int) {
  }

  override fun setLatLng(latLng: Point) {
    setLayerLocation(latLng)
  }

  override fun setBearing(bearing: Double) {
    setLayerBearing(bearing)
  }

  override fun setAccuracyRadius(accuracy: Float) {
  }

  override fun styleScaling(scaleExpression: Value) {
    modelLayer.modelScaleExpression(scaleExpression)
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

  override fun clearBitmaps() {
  }
}