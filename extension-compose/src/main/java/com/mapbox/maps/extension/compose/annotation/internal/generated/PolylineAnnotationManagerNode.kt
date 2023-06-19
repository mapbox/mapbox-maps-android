// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.extension.compose.annotation.internal.BaseAnnotationNode
import com.mapbox.maps.plugin.annotation.generated.OnPolylineAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions

internal class PolylineAnnotationManagerNode(
  val annotationManager: PolylineAnnotationManager,
  var onClicked: (PolylineAnnotation) -> Boolean
) : BaseAnnotationNode() {
  private val onClickedListener: OnPolylineAnnotationClickListener = OnPolylineAnnotationClickListener {
    onClicked.invoke(it)
  }

  override fun onAttached() {
    annotationManager.addClickListener(onClickedListener)
  }

  private var currentAnnotations: MutableList<PolylineAnnotation> = mutableListOf()
  var annotationClusterItems: List<PolylineAnnotationOptions> = emptyList()
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