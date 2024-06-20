// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.extension.compose.annotation.internal.BaseAnnotationNode
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.plugin.annotation.generated.OnPolygonAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationOptions

internal class PolygonAnnotationManagerNode(
  val annotationManager: PolygonAnnotationManager,
  var onClicked: (PolygonAnnotation) -> Boolean
) : BaseAnnotationNode() {
  private val onClickedListener: OnPolygonAnnotationClickListener = OnPolygonAnnotationClickListener {
    onClicked.invoke(it)
  }

  override fun onAttached(parent: MapNode) {
    annotationManager.addClickListener(onClickedListener)
  }

  private var currentAnnotations: MutableList<PolygonAnnotation> = mutableListOf()
  var annotationClusterItems: List<PolygonAnnotationOptions> = emptyList()
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