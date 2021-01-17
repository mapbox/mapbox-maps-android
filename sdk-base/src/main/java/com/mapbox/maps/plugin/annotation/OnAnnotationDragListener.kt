package com.mapbox.maps.plugin.annotation

/**
 * Generic interface definition of a callback to be invoked when an annotation is being dragged.
 *
 * @param <T> generic parameter extending from Annotation
 */
interface OnAnnotationDragListener<out T : Annotation<*>> {
  /**
   * Called when an annotation dragging has started.
   *
   * @param annotation the annotation
   */
  fun onAnnotationDragStarted(annotation: Annotation<*>)

  /**
   * Called when an annotation dragging is in progress.
   *
   * @param annotation the annotation
   */
  fun onAnnotationDrag(annotation: Annotation<*>)

  /**
   * Called when an annotation dragging has finished.
   *
   * @param annotation the annotation
   */
  fun onAnnotationDragFinished(annotation: Annotation<*>)
}