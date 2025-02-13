// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.annotation.internal.BaseAnnotationNode
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManager
import kotlinx.coroutines.CoroutineScope

internal class PolygonAnnotationNode(
  mapboxStyleManager: MapboxStyleManager,
  val annotationManager: PolygonAnnotationManager,
  val annotation: PolygonAnnotation,
  coroutineScope: CoroutineScope,
) : BaseAnnotationNode(mapboxStyleManager, coroutineScope) {

  override fun cleanUp() {
    annotationManager.delete(annotation)
    annotationManager.onDestroy()
  }

  override fun getLayerIds(): List<String> {
    return annotationManager.associatedLayers
  }

  override fun toString(): String {
    return "PolygonAnnotationNode(#${hashCode()})"
  }
}