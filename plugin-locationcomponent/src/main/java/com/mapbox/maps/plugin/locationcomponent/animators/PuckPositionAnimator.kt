package com.mapbox.maps.plugin.locationcomponent.animators

import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.threading.AnimationSynchronizer

internal class PuckPositionAnimator(private val indicatorPositionChangedListener: OnIndicatorPositionChangedListener) : PuckAnimator<Point>(Evaluators.POINT) {

  override fun updateLayer(fraction: Float, value: Point) {
    locationRenderer?.let { layerRenderer ->
      AnimationSynchronizer.get(locationRenderer)?.let {
        it.sendPuckPositionUpdate(choreographerFrameTimeNanos, value)
        super.updateLayer(fraction, value)
      } ?: run {
        layerRenderer.setLatLng(value)
      }
    }
    indicatorPositionChangedListener.onIndicatorPositionChanged(value)
  }
}