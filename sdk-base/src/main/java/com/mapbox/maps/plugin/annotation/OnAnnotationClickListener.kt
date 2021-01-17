package com.mapbox.maps.plugin.annotation

/**
 * Generic interface definition of a callback to be invoked when an annotation has been clicked.
 *
 * @param <T> generic parameter extending from Annotation
 */
fun interface OnAnnotationClickListener<T : Annotation<*>> {
  /**
   * Called when an annotation has been clicked
   *
   * @param annotation the annotation clicked.
   * @return True if this click should be consumed and not passed further to other listeners
   * registered afterwards, false otherwise.
   */
  fun onAnnotationClick(annotation: T): Boolean
}