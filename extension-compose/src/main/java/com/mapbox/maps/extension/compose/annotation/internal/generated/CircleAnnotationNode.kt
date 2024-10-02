// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.annotation.internal.BaseAnnotationNode
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager

internal class CircleAnnotationNode(
  mapboxStyleManager: MapboxStyleManager,
  val annotationManager: CircleAnnotationManager,
  val annotation: CircleAnnotation,
) : BaseAnnotationNode(mapboxStyleManager) {

  override fun cleanUp() {
    annotationManager.delete(annotation)
    annotationManager.onDestroy()
  }

  override fun getLayerIds(): List<String> {
    return annotationManager.associatedLayers
  }

  override fun toString(): String {
    return "CircleAnnotationNode(#${hashCode()})"
  }
}