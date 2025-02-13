// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.annotation.internal.BaseAnnotationNode
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import kotlinx.coroutines.CoroutineScope

internal class CircleAnnotationManagerNode(
  mapboxStyleManager: MapboxStyleManager,
  val annotationManager: CircleAnnotationManager,
  coroutineScope: CoroutineScope,
) : BaseAnnotationNode(mapboxStyleManager, coroutineScope) {

  internal var currentAnnotations: MutableList<CircleAnnotation> = mutableListOf()
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
    currentAnnotations.clear()
    annotationManager.deleteAll()
    annotationManager.onDestroy()
  }

  override fun getLayerIds(): List<String> {
    return annotationManager.associatedLayers
  }

  override fun toString(): String {
    return "CircleAnnotationManagerNode(#${hashCode()})"
  }
}