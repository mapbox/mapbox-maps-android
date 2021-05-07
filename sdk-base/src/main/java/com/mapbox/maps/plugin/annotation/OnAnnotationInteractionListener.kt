package com.mapbox.maps.plugin.annotation

/**
 * Generic interface definition of a callback to be invoked when an annotation has been selected or deselected.
 *
 * @param <T> generic parameter extending from Annotation
 */
interface OnAnnotationInteractionListener<T : Annotation<*>> {
  /**
   * Called when an annotation has been selected
   *
   * @param annotation the annotation selected.
   */
  fun onSelectAnnotation(annotation: T)

  /**
   * Called when an annotation has been deselected
   *
   * @param annotation the annotation deselected.
   */
  fun onDeselectAnnotation(annotation: T)
}