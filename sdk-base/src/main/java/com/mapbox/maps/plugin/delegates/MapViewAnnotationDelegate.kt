package com.mapbox.maps.plugin.delegates

import com.mapbox.maps.ViewAnnotationOptions
import com.mapbox.maps.ViewAnnotationsPosition

interface MapViewAnnotationDelegate {

  fun addViewAnnotation(
    viewId: Int,
    options: ViewAnnotationOptions
  ): ViewAnnotationsPosition?

  fun updateViewAnnotation(
    viewId: Int,
    options: ViewAnnotationOptions
  ): ViewAnnotationsPosition?

  fun removeViewAnnotation(viewId: Int): ViewAnnotationsPosition?

  fun calculateViewAnnotationsPosition(): ViewAnnotationsPosition
}