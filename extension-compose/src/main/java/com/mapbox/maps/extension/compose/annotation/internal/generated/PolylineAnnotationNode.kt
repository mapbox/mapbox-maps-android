// This file is generated.

package com.mapbox.maps.extension.compose.annotation.internal.generated

import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.annotation.internal.BaseAnnotationNode
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.plugin.annotation.generated.OnPolylineAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManager

internal class PolylineAnnotationNode(
  mapboxStyleManager: MapboxStyleManager,
  val annotationManager: PolylineAnnotationManager,
  val annotation: PolylineAnnotation,
  var onClicked: (PolylineAnnotation) -> Boolean
) : BaseAnnotationNode(mapboxStyleManager) {
  private val onClickedListener: OnPolylineAnnotationClickListener = OnPolylineAnnotationClickListener {
    onClicked.invoke(it)
  }

  override fun onAttached(parent: MapNode) {
    super.onAttached(parent)
    annotationManager.addClickListener(onClickedListener)
  }

  override fun cleanUp() {
    annotationManager.removeClickListener(onClickedListener)
    annotationManager.delete(annotation)
  }

  override fun getLayerId(): String {
    return annotationManager.layer.layerId
  }

  override fun toString(): String {
    return "PolylineAnnotationNode(#${hashCode()})"
  }
}