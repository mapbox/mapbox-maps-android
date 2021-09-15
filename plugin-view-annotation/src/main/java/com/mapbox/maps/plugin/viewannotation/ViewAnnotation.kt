package com.mapbox.maps.plugin.viewannotation

import android.view.View
import android.widget.FrameLayout

internal data class ViewAnnotation(
  val view: View,
  var viewLayoutParams: FrameLayout.LayoutParams,
  var options: ViewAnnotationOptions
)
