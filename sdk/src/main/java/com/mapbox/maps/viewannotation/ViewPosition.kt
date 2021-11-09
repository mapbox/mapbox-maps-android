package com.mapbox.maps.viewannotation

import com.mapbox.maps.ScreenCoordinate

internal data class ViewPosition(
  val id: String,
  val leftTopCoordinate: ScreenCoordinate,
  val width: Int,
  val height: Int,
  val needAddToLayout: Boolean,
)