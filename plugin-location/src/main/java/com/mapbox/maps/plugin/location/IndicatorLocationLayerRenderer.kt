package com.mapbox.maps.plugin.location

import android.graphics.Bitmap
import androidx.annotation.ColorInt
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.plugin.delegates.MapStyleStateDelegate
import com.mapbox.maps.plugin.location.LocationComponentConstants.*
import com.mapbox.maps.plugin.location.modes.RenderMode
import com.mapbox.maps.plugin.location.utils.BitmapUtils
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

internal class IndicatorLocationLayerRenderer(layerSourceProvider: LayerSourceProvider) :
  LocationLayerRenderer {
  private var style: StyleInterface? = null
  private var styleStateDelegate: MapStyleStateDelegate? = null
  private var layer = layerSourceProvider.getIndicatorLocationLayer()
  private var lastLatLng: Point? = null
  private var lastBearing = 0.0
  private var lastAccuracy = 0f
  private lateinit var locationComponentOptions: LocationComponentOptions

  override fun initializeComponents(style: StyleInterface, styleStateDelegate: MapStyleStateDelegate) {
    this.style = style
    this.styleStateDelegate = styleStateDelegate
    lastLatLng?.let {
      setLatLng(it)
    }
    setLayerBearing(lastBearing)
    setAccuracyRadius(lastAccuracy)
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

  override fun show(renderMode: RenderMode, isStale: Boolean) {
    setImages(renderMode, isStale)
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

  override fun setZoomLevel(zoomLevel: Double) {
    // not supported
  }

  override fun setGpsBearing(gpsBearing: Float) {
    setLayerBearing(gpsBearing.toDouble())
  }

  override fun setCompassBearing(compassBearing: Float) {
    setLayerBearing(compassBearing.toDouble())
  }

  override fun setAccuracyRadius(accuracy: Float) {
    layer.accuracyRadius(accuracy.toDouble())
    lastAccuracy = accuracy
  }

  override fun styleScaling(scaleExpression: List<Value>) {
    layer.shadowImageSize(scaleExpression)
    layer.bearingImageSize(scaleExpression)
    layer.topImageSize(scaleExpression)
  }

  override fun setLocationStale(isStale: Boolean, renderMode: RenderMode) {
    setImages(renderMode, isStale)
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
    if (shadowBitmap != null) {
      style?.addImage(SHADOW_ICON, shadowBitmap)
    } else {
      style?.removeStyleImage(SHADOW_ICON)
    }
    style?.addImage(FOREGROUND_ICON, foregroundBitmap)
    style?.addImage(FOREGROUND_STALE_ICON, foregroundStaleBitmap)
    if (renderMode == RenderMode.COMPASS) {
      val leftOffset = (bearingBitmap.width - backgroundBitmap.width) / 2f
      val topOffset = (bearingBitmap.height - backgroundBitmap.height) / 2f
      style?.addImage(
        BEARING_ICON,
        BitmapUtils.mergeBitmap(bearingBitmap, backgroundBitmap, leftOffset, topOffset)
      )
      val staleLeftOffset =
        (bearingBitmap.width - backgroundStaleBitmap.width) / 2f
      val staleTopOffset =
        (bearingBitmap.height - backgroundStaleBitmap.height) / 2f
      style?.addImage(
        BEARING_STALE_ICON,
        BitmapUtils.mergeBitmap(
          bearingBitmap,
          backgroundStaleBitmap,
          staleLeftOffset,
          staleTopOffset
        )
      )
    } else {
      style?.addImage(BACKGROUND_ICON, backgroundBitmap)
      style?.addImage(BACKGROUND_STALE_ICON, backgroundStaleBitmap)
      style?.addImage(BEARING_ICON, bearingBitmap)
    }
  }

  private fun setLayerVisibility(visible: Boolean) {
    layer.visibility(visible)
  }

  private fun setLayerLocation(latLng: Point) {
    val values = listOf(latLng.latitude(), latLng.longitude(), 0.0)
    layer.location(values)
    lastLatLng = latLng
  }

  private fun setLayerBearing(bearing: Double) {
    layer.bearing(bearing)
    lastBearing = bearing
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
   * Adjust the the pulsing LocationComponent circle based on the set options.
   */
  override fun stylePulsingCircle(options: LocationComponentOptions) {
    this.locationComponentOptions = options
    layer.emphasisCircleColor(colorIntToRgbaExpression(options.pulseColor()))
    layer.emphasisCircleRadius(options.pulseMaxRadius().toDouble())
  }

  /**
   * Adjust the visual appearance of the pulsing LocationComponent circle.
   */
  override fun updatePulsingUi(
    radius: Float,
    opacity: Float?
  ) {
    val rgbaArray = colorToRgbaArray(locationComponentOptions.pulseColor())
    rgbaArray[3] = opacity ?: 1f
    layer.emphasisCircleRadius(radius.toDouble())
    layer.emphasisCircleColorTransition(100)
    layer.emphasisCircleColor(buildRGBAExpression(rgbaArray))
  }

  private fun setImages(renderMode: RenderMode, isStale: Boolean) {
    var topImage = ""
    var bearingImage = ""
    var shadowImage: String? = ""
    when (renderMode) {
      RenderMode.COMPASS -> {
        topImage = if (isStale) FOREGROUND_STALE_ICON else FOREGROUND_ICON
        bearingImage = if (isStale) BEARING_STALE_ICON else BEARING_ICON
        shadowImage = SHADOW_ICON
      }
      RenderMode.GPS -> {
        topImage = ""
        bearingImage = if (isStale) FOREGROUND_STALE_ICON else FOREGROUND_ICON
        shadowImage = if (isStale) BACKGROUND_STALE_ICON else BACKGROUND_ICON
        setAccuracyRadius(0f)
      }
      RenderMode.NORMAL -> {
        topImage = if (isStale) FOREGROUND_STALE_ICON else FOREGROUND_ICON
        bearingImage = if (isStale) BACKGROUND_STALE_ICON else BACKGROUND_ICON
        shadowImage = SHADOW_ICON
      }
    }
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