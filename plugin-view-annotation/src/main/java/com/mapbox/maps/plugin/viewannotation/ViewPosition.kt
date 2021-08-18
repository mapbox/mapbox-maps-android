package com.mapbox.maps.plugin.viewannotation

import android.graphics.Matrix

data class ViewPosition(
  val bearing: Double,
  val rotationMatrix: Matrix? = null
)


