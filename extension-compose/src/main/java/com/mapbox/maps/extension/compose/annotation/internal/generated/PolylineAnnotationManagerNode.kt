// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.extension.compose.annotation.internal.BaseAnnotationNode
import com.mapbox.maps.extension.compose.internal.MapNode
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

  override fun onAttached(parent: MapNode) {
    annotationManager.addClickListener(onClickedListener)
  }

  private var currentAnnotations: MutableList<PolylineAnnotation> = mutableListOf()
  var annotationClusterItems: List<PolylineAnnotationOptions> = emptyList()
    set(value) {
      if (currentAnnotations.isNotEmpty()) {
        annotationManager.update(currentAnnotations)
      } else {
        currentAnnotations.addAll(annotationManager.create(value))
      }
      field = value
    }

  override fun cleanUp() {
    annotationManager.removeClickListener(onClickedListener)
    currentAnnotations.clear()
    annotationManager.deleteAll()
  }
}