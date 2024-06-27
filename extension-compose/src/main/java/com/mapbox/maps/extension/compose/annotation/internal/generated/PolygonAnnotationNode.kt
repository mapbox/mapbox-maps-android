// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.annotation.internal.BaseAnnotationNode
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.plugin.annotation.generated.OnPolygonAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManager

internal class PolygonAnnotationNode(
  mapboxStyleManager: MapboxStyleManager,
  val annotationManager: PolygonAnnotationManager,
  val annotation: PolygonAnnotation,
  var onClicked: (PolygonAnnotation) -> Boolean
) : BaseAnnotationNode(mapboxStyleManager) {
  private val onClickedListener: OnPolygonAnnotationClickListener = OnPolygonAnnotationClickListener {
    onClicked.invoke(it)
  }

  override fun onAttached(parent: MapNode) {
    super.onAttached(parent)
    annotationManager.addClickListener(onClickedListener)
  }

  override fun cleanUp() {
    annotationManager.removeClickListener(onClickedListener)
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