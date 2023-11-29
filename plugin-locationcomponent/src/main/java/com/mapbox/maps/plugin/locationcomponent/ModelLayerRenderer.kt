package com.mapbox.maps.plugin.locationcomponent

import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_LAYER
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_SOURCE

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class ModelLayerRenderer(
  layerSourceProvider: LayerSourceProvider,
  private val locationModelLayerOptions: LocationPuck3D
) : LocationLayerRenderer {

  private var style: MapboxStyleManager? = null

  @VisibleForTesting(otherwise = PRIVATE)
  internal var lastLocation: Point? = null
  private var lastBearing: Double = 0.0
  private var modelLayer = layerSourceProvider.getModelLayer(locationModelLayerOptions)
  private var source = layerSourceProvider.getModelSource(locationModelLayerOptions)

  override fun initializeComponents(style: MapboxStyleManager) {
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
    modelLayer.modelRotation(locationModelLayerOptions.modelRotation.map { it.toDouble() })
    modelLayer.modelOpacity(locationModelLayerOptions.modelOpacity.toDouble())
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
    lastLocation = latLng
    updateLocationOrBearing()
  }

  private fun setLayerBearing(bearing: Double) {
    lastBearing = bearing
    updateLocationOrBearing()
  }

  private fun updateLocationOrBearing() {
    lastLocation?.let { location ->
      val lngLat = listOf(location.longitude(), location.latitude())
      val orientation = listOf(0.0, 0.0, lastBearing)
      source.setPositionAndOrientation(lngLat, orientation)
    }
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

  override fun updateStyle(style: MapboxStyleManager) {
    this.style = style
    modelLayer.updateStyle(style)
    source.updateStyle(style)
  }
}