package com.mapbox.maps.plugin.viewannotation.core

import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.viewannotation.ViewAnnotationOptions

data class BackupSymbol(
  val viewAnnotationOptions: ViewAnnotationOptions,
  // TODO revisit nullable PointAnnotation, should be non-nullable
  val pointAnnotation: PointAnnotation?
)
