package com.mapbox.maps.plugin.delegates

import com.mapbox.maps.ViewAnnotationOptions
import com.mapbox.maps.ViewAnnotationsPosition

interface MapViewAnnotationDelegate {

  fun addViewAnnotation(
    viewId: String,
    options: ViewAnnotationOptions
  ): ViewAnnotationsPosition?

  fun updateViewAnnotation(
    viewId: String,
    options: ViewAnnotationOptions
  ): ViewAnnotationsPosition?

  fun removeViewAnnotation(viewId: String): ViewAnnotationsPosition?

  fun calculateViewAnnotationsPosition(): ViewAnnotationsPosition
}