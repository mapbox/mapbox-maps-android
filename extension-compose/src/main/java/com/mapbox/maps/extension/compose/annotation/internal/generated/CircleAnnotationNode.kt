// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.annotation.internal.BaseAnnotationNode
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.OnCircleAnnotationClickListener

internal class CircleAnnotationNode(
  mapboxStyleManager: MapboxStyleManager,
  val annotationManager: CircleAnnotationManager,
  val annotation: CircleAnnotation,
  var onClicked: (CircleAnnotation) -> Boolean
) : BaseAnnotationNode(mapboxStyleManager) {
  private val onClickedListener: OnCircleAnnotationClickListener = OnCircleAnnotationClickListener {
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
    return "CircleAnnotationNode(#${hashCode()})"
  }
}