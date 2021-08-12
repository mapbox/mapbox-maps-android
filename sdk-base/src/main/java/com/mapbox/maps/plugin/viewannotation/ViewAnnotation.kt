package com.mapbox.maps.plugin.viewannotation

import android.view.View
import android.widget.FrameLayout
import com.mapbox.maps.ViewAnnotationOptions

data class ViewAnnotation(
  val id: String,
  val view: View,
  var viewLayoutParams: FrameLayout.LayoutParams
)
