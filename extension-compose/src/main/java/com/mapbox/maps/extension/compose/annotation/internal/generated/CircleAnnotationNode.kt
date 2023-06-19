// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.extension.compose.annotation.internal.BaseAnnotationNode
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.OnCircleAnnotationClickListener

internal class CircleAnnotationNode(
  val annotationManager: CircleAnnotationManager,
  val annotation: CircleAnnotation,
  var onClicked: (CircleAnnotation) -> Boolean
) : BaseAnnotationNode() {
  private val onClickedListener: OnCircleAnnotationClickListener = OnCircleAnnotationClickListener {
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