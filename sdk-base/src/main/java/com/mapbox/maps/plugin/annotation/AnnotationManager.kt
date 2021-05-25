package com.mapbox.maps.plugin.annotation

import com.mapbox.geojson.Geometry
import com.mapbox.maps.plugin.delegates.MapDelegateProvider

/**
 * Interface for annotationManager
 */
interface AnnotationManager<
  G : Geometry,
  T : Annotation<G>,
  S : AnnotationOptions<G, T>,
  D : OnAnnotationDragListener<T>,
  U : OnAnnotationClickListener<T>,
  V : OnAnnotationLongClickListener<T>,
  I : OnAnnotationInteractionListener<T>> {

  /**
   * Create an annotation with the option
   */
  fun create(option: S): T

  /**
   * Create some annotations with the options
   */
  fun create(options: List<S>): List<T>

  /**
   * Update the annotation
   */
  fun update(annotation: T)

  /**
   * Update annotations in the list
   */
  fun update(annotations: List<T>)

  /**
   * Delete the annotation
   */
  fun delete(annotation: T)

  /**
   * Delete annotations in the list
   */
  fun delete(annotations: List<T>)

  /**
   * Delete all the added annotations
   */
  fun deleteAll()

  /**
   * Invoked when Mapview is destroyed
   */
  fun onDestroy()

  /**
   * Toggles the annotation's selection state.
   * If the annotation is deselected, it becomes selected.
   * If the annotation is selected, it becomes deselected.
   * @param annotation: The annotation to select.
   */
  fun selectAnnotation(annotation: T)

  /**
   * Invoked when MapView's width and height have changed.
   * @param width the width of mapView
   * @param height the height of mapView
   */
  fun onSizeChanged(width: Int, height: Int)

  /**
   * The delegateProvider
   */
  val delegateProvider: MapDelegateProvider

  /**
   * The added annotations
   */
  val annotations: List<T>

  /**
   * The added dragListensers
   */
  val dragListeners: MutableList<D>

  /**
   * The Added clickListeners
   */
  val clickListeners: MutableList<U>

  /**
   * The added longClickListeners
   */
  val longClickListeners: MutableList<V>

  /**
   * The added interactionListener
   */
  val interactionListener: MutableList<I>

  /**
   * Add a callback to be invoked when an annotation is dragged.
   *
   * @param d the callback to be invoked when an annotation is dragged
   */
  fun addDragListener(d: D) = dragListeners.add(d)

  /**
   * Remove a previously added callback that was to be invoked when an annotation has been dragged.
   *
   * @param d the callback to be removed
   */
  fun removeDragListener(d: D) = dragListeners.remove(d)

  /**
   * Add a callback to be invoked when a annotation has been clicked.
   *
   * @param u the callback to be invoked when a annotation is clicked
   */
  fun addClickListener(u: U) = clickListeners.add(u)

  /**
   * Remove a previously added callback that was to be invoked when annotation has been clicked.
   *
   * @param u the callback to be removed
   */
  fun removeClickListener(u: U) = clickListeners.remove(u)

  /**
   * Add a callback to be invoked when a annotation has been long clicked.
   *
   * @param v the callback to be invoked when a annotation is clicked
   */
  fun addLongClickListener(v: V) = longClickListeners.add(v)

  /**
   * Remove a previously added callback that was to be invoked when annotation has been long clicked.
   *
   * @param v the callback to be removed
   */
  fun removeLongClickListener(v: V) = longClickListeners.remove(v)

  /**
   * Add a callback to be invoked when a annotation has been selected or deselected.
   *
   * @param i the callback to be invoked when a annotation is selected or deselected
   */
  fun addInteractionListener(i: I) = interactionListener.add(i)

  /**
   * Remove a previously added callback that was to be invoked when annotation has been selected or deselected.
   *
   * @param i the callback to be removed
   */
  fun removeInteractionListener(i: I) = interactionListener.remove(i)

  /**
   * Enable a data-driven property. Please visit [The online documentation](https://docs.mapbox.com/android/maps/guides/data-driven-styling/) for more details about data-driven-styling
   */
  fun enableDataDrivenProperty(property: String)
}