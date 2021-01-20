package com.mapbox.maps.plugin.locationcomponent

import android.graphics.Bitmap
import androidx.annotation.ColorInt
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.StyleManagerInterface

internal interface LocationLayerRenderer {
  fun initializeComponents(style: StyleManagerInterface)

  fun isLayerInitialised(): Boolean

  fun addLayers(positionManager: LocationComponentPositionManager)

  fun removeLayers()

  fun hide()

  fun show(isStale: Boolean)

  fun styleAccuracy(accuracyAlpha: Float, accuracyColor: Int)

  fun setLatLng(latLng: Point)

  fun setBearing(bearing: Float)

  fun setAccuracyRadius(accuracy: Float)

  fun styleScaling(scaleExpression: List<Value>)

  fun setLocationStale(isStale: Boolean)

  fun adjustPulsingCircleLayerVisibility(visible: Boolean)

  fun updatePulsingUi(
    @ColorInt
    pulsingColorInt: Int,
    radius: Float,
    opacity: Float?
  )

  fun addBitmaps(
    topBitmap: Bitmap?,
    topStaleBitmap: Bitmap?,
    bearingBitmap: Bitmap?,
    bearingStaleBitmap: Bitmap?,
    shadowBitmap: Bitmap?,
    shadowStaleBitmap: Bitmap?
  )

  fun clearBitmaps()
}