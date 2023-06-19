// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.extension.compose.annotation.internal.BaseAnnotationNode
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.OnCircleAnnotationClickListener

internal class CircleAnnotationManagerNode(
  val annotationManager: CircleAnnotationManager,
  var onClicked: (CircleAnnotation) -> Boolean
) : BaseAnnotationNode() {
  private val onClickedListener: OnCircleAnnotationClickListener = OnCircleAnnotationClickListener {
    onClicked.invoke(it)
  }

  override fun onAttached() {
    annotationManager.addClickListener(onClickedListener)
  }

  private var currentAnnotations: MutableList<CircleAnnotation> = mutableListOf()
  var annotationClusterItems: List<CircleAnnotationOptions> = emptyList()
    set(value) {
      if (currentAnnotations.isNotEmpty()) {
        annotationManager.delete(currentAnnotations)
        currentAnnotations.clear()
      }
      currentAnnotations.addAll(annotationManager.create(value))
      field = value
    }

  override fun cleanUp() {
    annotationManager.removeClickListener(onClickedListener)
    currentAnnotations.clear()
    annotationManager.deleteAll()
  }
}