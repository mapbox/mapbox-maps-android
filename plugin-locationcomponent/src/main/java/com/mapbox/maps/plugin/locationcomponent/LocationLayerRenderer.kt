package com.mapbox.maps.plugin.locationcomponent

import androidx.annotation.ColorInt
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.StyleManagerInterface

internal interface LocationLayerRenderer {
  fun initializeComponents(style: StyleManagerInterface)

  fun isRendererInitialised(): Boolean

  fun addLayers(positionManager: LocationComponentPositionManager)

  fun removeLayers()

  fun hide()

  fun show()

  fun styleAccuracy(accuracyAlpha: Float, accuracyColor: Int)

  fun setLatLng(latLng: Point)

  fun setBearing(bearing: Double)

  fun setAccuracyRadius(accuracy: Float)

  fun styleScaling(scaleExpression: Value)

  fun adjustPulsingCircleLayerVisibility(visible: Boolean)

  fun updatePulsingUi(
    @ColorInt
    pulsingColorInt: Int,
    radius: Float,
    opacity: Float?
  )

  fun clearBitmaps()
}