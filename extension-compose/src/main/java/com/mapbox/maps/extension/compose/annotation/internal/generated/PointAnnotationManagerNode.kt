// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.extension.compose.annotation.internal.BaseAnnotationNode
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions

internal class PointAnnotationManagerNode(
  val annotationManager: PointAnnotationManager,
  var onClicked: (PointAnnotation) -> Boolean
) : BaseAnnotationNode() {
  private val onClickedListener: OnPointAnnotationClickListener = OnPointAnnotationClickListener {
    onClicked.invoke(it)
  }

  override fun onAttached(parent: MapNode) {
    annotationManager.addClickListener(onClickedListener)
  }

  private var currentAnnotations: MutableList<PointAnnotation> = mutableListOf()
  var annotationClusterItems: List<PointAnnotationOptions> = emptyList()
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