package com.mapbox.maps.plugin.locationcomponent

import android.graphics.Bitmap
import androidx.annotation.ColorInt
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.extension.style.addStyleImage
import com.mapbox.maps.plugin.TwoDLocationPuck
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.BEARING_ICON
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.BEARING_STALE_ICON
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.LOCATION_INDICATOR_LAYER
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.SHADOW_ICON
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.SHADOW_STALE_ICON
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.TOP_ICON
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.TOP_STALE_ICON
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

internal class LocationIndicatorLayerRenderer(
  val puckOptions: TwoDLocationPuck,
  layerSourceProvider: LayerSourceProvider
) :
  LocationLayerRenderer {
  private var style: StyleManagerInterface? = null
  private var layer = layerSourceProvider.getLocationIndicatorLayer()

  override fun initializeComponents(style: StyleManagerInterface) {
    this.style = style
  }

  override fun isLayerInitialised(): Boolean {
    return style?.styleLayerExists(LOCATION_INDICATOR_LAYER) ?: false
  }

  override fun addLayers(positionManager: LocationComponentPositionManager) {
    positionManager.addLayerToMap(layer)
  }

  override fun removeLayers() {
    style?.removeStyleLayer(layer.layerId)
  }

  override fun hide() {
    setLayerVisibility(false)
  }

  override fun show(isStale: Boolean) {
    setImages(isStale)
    setLayerVisibility(true)
  }

  override fun styleAccuracy(accuracyAlpha: Float, accuracyColor: Int) {
    val colorArray: FloatArray = colorToRgbaArray(accuracyColor)
    colorArray[3] = accuracyAlpha
    val rgbaExpression = buildRGBAExpression(colorArray)
    layer.accuracyRadiusColor(rgbaExpression)
    layer.accuracyRadiusBorderColor(rgbaExpression)
  }

  override fun setLatLng(latLng: Point) {
    setLayerLocation(latLng)
  }

  override fun setBearing(bearing: Float) {
    setLayerBearing(bearing.toDouble())
  }

  override fun setAccuracyRadius(accuracy: Float) {
    layer.accuracyRadius(accuracy.toDouble())
  }

  override fun styleScaling(scaleExpression: List<Value>) {
    layer.shadowImageSize(scaleExpression)
    layer.bearingImageSize(scaleExpression)
    layer.topImageSize(scaleExpression)
  }

  override fun setLocationStale(isStale: Boolean) {
    setImages(isStale)
  }

  override fun addBitmaps(
    topBitmap: Bitmap?,
    topStaleBitmap: Bitmap?,
    bearingBitmap: Bitmap?,
    bearingStaleBitmap: Bitmap?,
    shadowBitmap: Bitmap?,
    shadowStaleBitmap: Bitmap?
  ) {
    topBitmap?.let { style?.addStyleImage(TOP_ICON, it) }
    topStaleBitmap?.let { style?.addStyleImage(TOP_STALE_ICON, it) }
    bearingBitmap?.let { style?.addStyleImage(BEARING_ICON, it) }
    bearingStaleBitmap?.let { style?.addStyleImage(BEARING_STALE_ICON, it) }
    shadowBitmap?.let { style?.addStyleImage(SHADOW_ICON, it) }
    shadowStaleBitmap?.let { style?.addStyleImage(SHADOW_STALE_ICON, it) }
  }

  override fun clearBitmaps() {
    style?.removeStyleImage(TOP_ICON)
    style?.removeStyleImage(TOP_STALE_ICON)
    style?.removeStyleImage(BEARING_ICON)
    style?.removeStyleImage(BEARING_STALE_ICON)
    style?.removeStyleImage(SHADOW_ICON)
    style?.removeStyleImage(SHADOW_STALE_ICON)
  }

  private fun setLayerVisibility(visible: Boolean) {
    layer.visibility(visible)
  }

  private fun setLayerLocation(latLng: Point) {
    val values = listOf(latLng.latitude(), latLng.longitude(), 0.0)
    layer.location(values)
  }

  private fun setLayerBearing(bearing: Double) {
    layer.bearing(bearing)
  }

  /**
   * Adjust the visibility of the pulsing LocationComponent circle.
   */
  override fun adjustPulsingCircleLayerVisibility(visible: Boolean) {
    if (!visible) {
      layer.emphasisCircleRadius(0.0)
    }
  }

  /**
   * Adjust the visual appearance of the pulsing LocationComponent circle.
   */
  override fun updatePulsingUi(
    radius: Float,
    opacity: Float?
  ) {
    // Check if pulsing effect is enabled and style the pulsing circle.
    if (puckOptions.pulsingEnabled) {
      val rgbaArray = colorToRgbaArray(puckOptions.pulsingColor)
      rgbaArray[3] = opacity ?: 1f
      layer.emphasisCircleRadius(radius.toDouble())
      layer.emphasisCircleColorTransition(100)
      layer.emphasisCircleColor(buildRGBAExpression(rgbaArray))
    }
  }

  private fun setImages(isStale: Boolean) {
    val topImage = if (isStale) TOP_STALE_ICON else TOP_ICON
    val bearingImage = if (isStale) BEARING_STALE_ICON else BEARING_ICON
    val shadowImage = if (isStale) SHADOW_STALE_ICON else SHADOW_ICON
    layer.topImage(topImage)
    layer.bearingImage(bearingImage)
    layer.shadowImage(shadowImage)
  }

  companion object {

    fun buildRGBAExpression(colorArray: FloatArray): List<Value> {
      return arrayListOf(
        Value("rgba"),
        Value(colorArray[0].toDouble()),
        Value(colorArray[1].toDouble()),
        Value(colorArray[2].toDouble()),
        Value(colorArray[3].toDouble())
      )
    }

    fun colorIntToRgbaExpression(@ColorInt color: Int): List<Value> {
      val numberFormat =
        NumberFormat.getNumberInstance(Locale.US)
      val decimalFormat = numberFormat as DecimalFormat
      decimalFormat.applyPattern("#.###")
      val alpha =
        decimalFormat.format((color shr 24 and 0xFF).toFloat() / 255.0f.toDouble())
      return arrayListOf(
        Value("rgba"),
        Value((color shr 16 and 0xFF).toLong()),
        Value((color shr 8 and 0xFF).toLong()),
        Value((color and 0xFF).toLong()),
        Value(alpha.toDouble())
      )
    }

    fun colorToRgbaArray(@ColorInt color: Int): FloatArray {
      return floatArrayOf(
        (color shr 16 and 0xFF.toFloat().toInt()).toFloat(), // r (0-255)
        (color shr 8 and 0xFF.toFloat().toInt()).toFloat(), // g (0-255)
        (color and 0xFF.toFloat().toInt()).toFloat(), // b (0-255)
        (color shr 24 and 0xFF) / 255.0f // a (0-1)
      )
    }
  }
}