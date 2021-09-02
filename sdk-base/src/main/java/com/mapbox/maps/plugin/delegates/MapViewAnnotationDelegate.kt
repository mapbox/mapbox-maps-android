package com.mapbox.maps.plugin.delegates

import com.mapbox.maps.ViewAnnotationOptions
import com.mapbox.maps.ViewAnnotationsPositionCallback

interface MapViewAnnotationDelegate {

  fun addViewAnnotation(
    viewId: String,
    options: ViewAnnotationOptions,
    callback: ViewAnnotationsPositionCallback
  )

  fun updateViewAnnotation(
    viewId: String,
    options: ViewAnnotationOptions,
    callback: ViewAnnotationsPositionCallback
  )

  fun removeViewAnnotation(viewId: String, callback: ViewAnnotationsPositionCallback)

  fun calculateViewAnnotationsPosition(callback: ViewAnnotationsPositionCallback)
}