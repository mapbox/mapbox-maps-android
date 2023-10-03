package com.mapbox.maps.plugin.locationcomponent

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.RestrictTo
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.Style
import com.mapbox.maps.logE
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.BEARING_ICON
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.LOCATION_INDICATOR_LAYER
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.SHADOW_ICON
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.TOP_ICON
import com.mapbox.maps.plugin.locationcomponent.utils.BitmapUtils
import java.lang.ref.WeakReference
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class LocationIndicatorLayerRenderer(
  private val puckOptions: LocationPuck2D,
  private val weakContext: WeakReference<Context>,
  private val layer: LocationIndicatorLayerWrapper = LayerSourceProvider.getLocationIndicatorLayer()
) : LocationLayerRenderer {

  private var style: Style? = null

  override fun initializeComponents(style: Style) {
    this.style = style
    setupBitmaps(style)
  }

  override fun isRendererInitialised(): Boolean {
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

  override fun show() {
    setLayerVisibility(true)
  }

  override fun styleAccuracy(accuracyColor: Int, accuracyBorderColor: Int) {
    val colorArray: FloatArray = colorToRgbaArray(accuracyColor)
    val borderColorArray: FloatArray = colorToRgbaArray(accuracyBorderColor)
    val rgbaExpression = buildRGBAExpression(colorArray)
    val borderRgbaExpression = buildRGBAExpression(borderColorArray)
    layer.accuracyRadiusColor(rgbaExpression)
    layer.accuracyRadiusBorderColor(borderRgbaExpression)
  }

  override fun setLatLng(latLng: Point) {
    setLayerLocation(latLng)
  }

  override fun setBearing(bearing: Double) {
    setLayerBearing(bearing)
  }

  override fun setAccuracyRadius(accuracy: Float) {
    layer.accuracyRadius(accuracy.toDouble())
  }

  override fun styleScaling(scaleExpression: Value) {
    layer.shadowImageSize(scaleExpression)
    layer.bearingImageSize(scaleExpression)
    layer.topImageSize(scaleExpression)
  }

  private fun setupBitmaps(style: Style) {
    addImageToStyle(style, TOP_ICON, puckOptions.topImage)
    addImageToStyle(style, BEARING_ICON, puckOptions.bearingImage)
    addImageToStyle(style, SHADOW_ICON, puckOptions.shadowImage)
    layer.topImage(TOP_ICON)
    layer.bearingImage(BEARING_ICON)
    layer.shadowImage(SHADOW_ICON)
    layer.opacity(puckOptions.opacity.toDouble())
  }

  private fun addImageToStyle(style: Style, iconId: String, imageHolder: ImageHolder?) {
    // First try to use the bitmap directly
    imageHolder?.bitmap?.let { bitmap ->
      style.addImage(iconId, bitmap)
      return
    }
    // Otherwise, let's try to get the bitmap from the drawable resource ID
    weakContext.get()?.let { context ->
      imageHolder?.drawableId?.let { drawableRes ->
        BitmapUtils.getBitmapFromDrawableRes(context, drawableRes)?.let { bitmap ->
          style.addImage(iconId, bitmap)
        }
      } ?: logE(TAG, "No image holder data for $iconId!")
    } ?: logE(
      TAG,
      "Could not set 2D puck image as drawable for $iconId" +
        " because there is no Android Context!"
    )
  }

  override fun clearBitmaps() {
    style?.removeStyleImage(TOP_ICON)
    style?.removeStyleImage(BEARING_ICON)
    style?.removeStyleImage(SHADOW_ICON)
  }

  override fun updateStyle(style: Style) {
    this.style = style
    layer.updateStyle(style)
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
    @ColorInt
    pulsingColorInt: Int,
    radius: Float,
    opacity: Float?
  ) {
    val rgbaArray = colorToRgbaArray(pulsingColorInt)
    rgbaArray[3] = opacity ?: 1f
    layer.emphasisCircleRadius(radius.toDouble())
    layer.emphasisCircleColor(buildRGBAExpression(rgbaArray))
  }

  companion object {

    private const val TAG = "LocationPuck2D"

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