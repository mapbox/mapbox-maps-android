package com.mapbox.maps.plugin.location

import android.graphics.Bitmap
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.delegates.MapStyleStateDelegate
import com.mapbox.maps.plugin.location.modes.RenderMode

internal interface LocationLayerRenderer {
  fun initializeComponents(style: StyleManagerInterface, styleStateDelegate: MapStyleStateDelegate)

  fun addLayers(positionManager: LocationComponentPositionManager)

  fun removeLayers()

  fun hide()

  fun show(renderMode: RenderMode, isStale: Boolean)

  fun styleAccuracy(accuracyAlpha: Float, accuracyColor: Int)

  fun setLatLng(latLng: Point)

  fun setZoomLevel(zoomLevel: Double)

  fun setGpsBearing(gpsBearing: Float)

  fun setCompassBearing(compassBearing: Float)

  fun setAccuracyRadius(accuracy: Float)

  fun styleScaling(scaleExpression: List<Value>)

  fun setLocationStale(isStale: Boolean, renderMode: RenderMode)

  fun adjustPulsingCircleLayerVisibility(visible: Boolean)

  fun stylePulsingCircle(options: LocationComponentOptions)

  fun updatePulsingUi(radius: Float, opacity: Float?)

  fun updateIconIds(
    foregroundIconString: String,
    foregroundStaleIconString: String,
    backgroundIconString: String,
    backgroundStaleIconString: String,
    bearingIconString: String
  )

  fun addBitmaps(
    renderMode: RenderMode,
    shadowBitmap: Bitmap?,
    backgroundBitmap: Bitmap,
    backgroundStaleBitmap: Bitmap,
    bearingBitmap: Bitmap,
    foregroundBitmap: Bitmap,
    foregroundStaleBitmap: Bitmap
  )
}