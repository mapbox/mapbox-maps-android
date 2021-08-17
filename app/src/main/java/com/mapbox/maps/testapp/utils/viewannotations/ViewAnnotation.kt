package com.mapbox.maps.testapp.utils.viewannotations

import android.view.View
import android.widget.FrameLayout
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point

data class ViewAnnotation(
  val view: View,
  val viewLayoutParams: FrameLayout.LayoutParams,
  val descriptor: ViewAnnotationDescriptor,
  val width: Int,
  val height: Int,
  // bunch of potential properties about placing annotation etc
  // TODO for prototype supporting only Point for now
  val geometry: Geometry
)
