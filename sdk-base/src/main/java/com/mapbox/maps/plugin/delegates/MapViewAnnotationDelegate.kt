package com.mapbox.maps.plugin.delegates

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.maps.ViewAnnotationOptions
import com.mapbox.maps.ViewAnnotationPositionsCallback

interface MapViewAnnotationDelegate {

  fun addViewAnnotation(
    viewId: String,
    options: ViewAnnotationOptions
  ): Expected<String, None>

  fun updateViewAnnotation(
    viewId: String,
    options: ViewAnnotationOptions
  ): Expected<String, None>

  fun removeViewAnnotation(viewId: String) : Expected<String, None>

  fun calculateViewAnnotationsPosition(callback: ViewAnnotationPositionsCallback)

  fun getViewAnnotationOptions(identifier: String): Expected<String, ViewAnnotationOptions>
}