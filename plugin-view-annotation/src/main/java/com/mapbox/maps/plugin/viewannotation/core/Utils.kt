package com.mapbox.maps.plugin.viewannotation.core

import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.plugin.viewannotation.ViewAnnotationAnchor

fun ViewAnnotationAnchor.mapToIconAnchor() = when(this) {
  ViewAnnotationAnchor.CENTER -> IconAnchor.CENTER
  ViewAnnotationAnchor.LEFT -> IconAnchor.LEFT
  ViewAnnotationAnchor.RIGHT -> IconAnchor.RIGHT
  ViewAnnotationAnchor.TOP -> IconAnchor.TOP
  ViewAnnotationAnchor.BOTTOM -> IconAnchor.BOTTOM
  ViewAnnotationAnchor.TOP_LEFT -> IconAnchor.TOP_LEFT
  ViewAnnotationAnchor.TOP_RIGHT -> IconAnchor.TOP_RIGHT
  ViewAnnotationAnchor.BOTTOM_LEFT -> IconAnchor.BOTTOM_LEFT
  ViewAnnotationAnchor.BOTTOM_RIGHT -> IconAnchor.BOTTOM_RIGHT
}