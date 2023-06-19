// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.extension.compose.annotation.internal.BaseAnnotationNode
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager

internal class PointAnnotationNode(
  val annotationManager: PointAnnotationManager,
  val annotation: PointAnnotation,
  var onClicked: (PointAnnotation) -> Boolean
) : BaseAnnotationNode() {
  private val onClickedListener: OnPointAnnotationClickListener = OnPointAnnotationClickListener {
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