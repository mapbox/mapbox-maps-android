package com.mapbox.maps.plugin.locationcomponent.animators

import com.mapbox.geojson.Point

internal class PuckPositionAnimator : PuckAnimator<Point>(Evaluators.POINT) {

  override fun updateLayer(fraction: Float, value: Point) {
    locationRenderer?.setLatLng(value)
  }
}