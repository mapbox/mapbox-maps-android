// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.annotation.internal.BaseAnnotationNode
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationOptions
import kotlinx.coroutines.CoroutineScope

internal class PolygonAnnotationManagerNode(
  mapboxStyleManager: MapboxStyleManager,
  val annotationManager: PolygonAnnotationManager,
  coroutineScope: CoroutineScope,
) : BaseAnnotationNode(mapboxStyleManager, coroutineScope) {

  internal var currentAnnotations: MutableList<PolygonAnnotation> = mutableListOf()
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
    currentAnnotations.clear()
    annotationManager.deleteAll()
    annotationManager.onDestroy()
  }

  override fun getLayerIds(): List<String> {
    return annotationManager.associatedLayers
  }

  override fun toString(): String {
    return "PolygonAnnotationManagerNode(#${hashCode()})"
  }
}