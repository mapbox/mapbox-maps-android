package com.mapbox.maps.plugin.annotation

/**
 * Generic fun interface definition of a callback to be invoked when an annotation has been long clicked.
 *
 * @param <T> generic parameter extending from Annotation
 */
fun interface OnAnnotationLongClickListener<T : Annotation<*>> {
  /**
   * Generic fun interface definition of a callback to be invoked when an annotation has been long clicked.
   *
   * @param <T> generic parameter extending from Annotation
   */
  fun onAnnotationLongClick(annotation: T): Boolean
}