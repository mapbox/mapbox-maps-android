// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.extension.compose.annotation.internal.BaseAnnotationNode
import com.mapbox.maps.plugin.annotation.generated.OnPolygonAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManager

internal class PolygonAnnotationNode(
  val annotationManager: PolygonAnnotationManager,
  val annotation: PolygonAnnotation,
  var onClicked: (PolygonAnnotation) -> Boolean
) : BaseAnnotationNode() {
  private val onClickedListener: OnPolygonAnnotationClickListener = OnPolygonAnnotationClickListener {
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