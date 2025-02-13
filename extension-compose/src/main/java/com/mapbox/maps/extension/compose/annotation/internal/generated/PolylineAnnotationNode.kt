// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.annotation.internal.BaseAnnotationNode
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManager
import kotlinx.coroutines.CoroutineScope

internal class PolylineAnnotationNode(
  mapboxStyleManager: MapboxStyleManager,
  val annotationManager: PolylineAnnotationManager,
  val annotation: PolylineAnnotation,
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
    return "PolylineAnnotationNode(#${hashCode()})"
  }
}