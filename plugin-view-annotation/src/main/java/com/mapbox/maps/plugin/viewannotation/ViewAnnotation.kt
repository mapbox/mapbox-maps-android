package com.mapbox.maps.plugin.viewannotation

import android.view.View
import android.widget.FrameLayout
import com.mapbox.geojson.Geometry

internal data class ViewAnnotation(
  val view: View,
  var viewLayoutParams: FrameLayout.LayoutParams,
  val descriptor: ViewAnnotationDescriptor,
  val geometry: Geometry, // TODO for prototype supporting only Point for now
  val options: ViewAnnotationOptions
)
