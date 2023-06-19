// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.extension.compose.annotation.internal.BaseAnnotationNode
import com.mapbox.maps.plugin.annotation.generated.OnPolylineAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManager

internal class PolylineAnnotationNode(
  val annotationManager: PolylineAnnotationManager,
  val annotation: PolylineAnnotation,
  var onClicked: (PolylineAnnotation) -> Boolean
) : BaseAnnotationNode() {
  private val onClickedListener: OnPolylineAnnotationClickListener = OnPolylineAnnotationClickListener {
    onClicked.invoke(it)
  }

  override fun onAttached() {
    annotationManager.addClickListener(onClickedListener)
  }

  override fun cleanUp() {
    annotationManager.removeClickListener(onClickedListener)
    annotationManager.delete(annotation)
  }
}