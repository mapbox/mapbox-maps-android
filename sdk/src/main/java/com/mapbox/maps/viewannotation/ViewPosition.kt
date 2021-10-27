package com.mapbox.maps.viewannotation

import com.mapbox.maps.ScreenCoordinate

internal data class ViewPosition(
  val leftTopCoordinate: ScreenCoordinate,
  val zIndex: Float,
)