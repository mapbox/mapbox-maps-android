package com.mapbox.maps.viewannotation

import android.view.View
import android.widget.FrameLayout

internal data class ViewAnnotation(
  val id: String,
  val view: View,
  var viewLayoutParams: FrameLayout.LayoutParams,
)