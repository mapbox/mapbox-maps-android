package com.mapbox.maps.plugin.viewannotation

import android.view.View
import android.widget.FrameLayout
import com.mapbox.maps.ViewAnnotationOptions

internal data class ViewAnnotation(
  val view: View,
  var viewLayoutParams: FrameLayout.LayoutParams,
  val descriptor: ViewAnnotationDescriptor,
  val options: ViewAnnotationOptions
)
