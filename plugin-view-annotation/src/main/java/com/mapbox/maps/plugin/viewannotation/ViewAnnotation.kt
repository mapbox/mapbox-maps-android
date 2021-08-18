package com.mapbox.maps.plugin.viewannotation

import android.view.View
import android.widget.FrameLayout
import com.mapbox.geojson.Geometry

internal data class ViewAnnotation(
  val view: View,
  var viewLayoutParams: FrameLayout.LayoutParams,
  val descriptor: ViewAnnotationDescriptor,
  val initialWidth: Int, // TODO will be removed as will be stored in gl-native, needed for correct resize based on zoom
  val initialHeight: Int, // TODO will be removed as will be stored in gl-native, needed for correct resize based on zoom
  val geometry: Geometry, // TODO for prototype supporting only Point for now
  val options: ViewAnnotationOptions
)
