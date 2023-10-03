package com.mapbox.maps.plugin.locationcomponent.animators

import androidx.annotation.RestrictTo
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class PuckPositionAnimator(private val indicatorPositionChangedListener: OnIndicatorPositionChangedListener) : PuckAnimator<Point>(Evaluators.POINT) {

  override fun updateLayer(fraction: Float, value: Point) {
    locationRenderer?.setLatLng(value)
    indicatorPositionChangedListener.onIndicatorPositionChanged(value)
  }
}