package com.mapbox.maps.testapp.utils.viewannotations

import android.view.View
import android.widget.FrameLayout
import com.mapbox.geojson.Geometry

data class ViewAnnotation(
  val view: View,
  var viewLayoutParams: FrameLayout.LayoutParams,
  val descriptor: ViewAnnotationDescriptor,
  val initialWidth: Int, // TODO will be removed as will be stored in gl-native, needed for correct resize based on zoom
  val initialHeight: Int, // TODO will be removed as will be stored in gl-native, needed for correct resize based on zoom
  // TODO for prototype supporting only Point for now
  val geometry: Geometry,
  val options: ViewAnnotationOptions
)
