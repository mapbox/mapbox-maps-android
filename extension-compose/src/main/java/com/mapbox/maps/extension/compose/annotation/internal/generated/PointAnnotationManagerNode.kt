// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.annotation.internal.BaseAnnotationNode
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions

internal class PointAnnotationManagerNode(
  mapboxStyleManager: MapboxStyleManager,
  val annotationManager: PointAnnotationManager,
  var onClicked: (PointAnnotation) -> Boolean
) : BaseAnnotationNode(mapboxStyleManager) {
  private val onClickedListener: OnPointAnnotationClickListener = OnPointAnnotationClickListener {
    onClicked.invoke(it)
  }

  override fun onAttached(parent: MapNode) {
    super.onAttached(parent)
    annotationManager.addClickListener(onClickedListener)
  }

  private var currentAnnotations: MutableList<PointAnnotation> = mutableListOf()
  var annotationClusterItems: List<PointAnnotationOptions> = emptyList()
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
    annotationManager.onDestroy()
  }

  override fun getLayerIds(): List<String> {
    return annotationManager.associatedLayers
  }

  override fun toString(): String {
    return "PointAnnotationManagerNode(#${hashCode()})"
  }
}