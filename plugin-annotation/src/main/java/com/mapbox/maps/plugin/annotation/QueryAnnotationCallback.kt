package com.mapbox.maps.plugin.annotation

/**
 * Callback for query annotation
 */
fun interface QueryAnnotationCallback<T> {
  /**
   * Get the queried annotation
   */
  fun onQueryAnnotation(annotations: T)
}