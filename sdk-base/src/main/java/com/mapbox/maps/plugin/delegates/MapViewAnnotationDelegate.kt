package com.mapbox.maps.plugin.delegates

import com.mapbox.maps.ViewAnnotationOptions
import com.mapbox.maps.ViewAnnotationsPositionCallback

interface MapViewAnnotationDelegate {

  fun addViewAnnotation(
    viewId: String,
    options: ViewAnnotationOptions
  )

  fun updateViewAnnotation(
    viewId: String,
    options: ViewAnnotationOptions
  )

  fun removeViewAnnotation(viewId: String)

  fun calculateViewAnnotationsPosition(callback: ViewAnnotationsPositionCallback)
}