package com.mapbox.maps.plugin.viewannotation.core

import com.mapbox.maps.plugin.viewannotation.ViewAnnotationOptions

data class EnhancedViewAnnotationOptions(
  val options: ViewAnnotationOptions,
  var visibleCollisionBased: Boolean = true
)
