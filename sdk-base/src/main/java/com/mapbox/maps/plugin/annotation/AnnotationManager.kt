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
  V : OnAnnotationLongClickListener<T>> {

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
  val annotations: MutableMap<Long, T>

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
   * Add a callback to be invoked when a symbol has been clicked.
   *
   * @param u the callback to be invoked when a symbol is clicked
   */
  fun addClickListener(u: U) = clickListeners.add(u)

  /**
   * Remove a previously added callback that was to be invoked when symbol has been clicked.
   *
   * @param u the callback to be removed
   */
  fun removeClickListener(u: U) = clickListeners.remove(u)

  /**
   * Add a callback to be invoked when a symbol has been long clicked.
   *
   * @param v the callback to be invoked when a symbol is clicked
   */
  fun addLongClickListener(v: V) = longClickListeners.add(v)

  /**
   * Remove a previously added callback that was to be invoked when symbol has been long clicked.
   *
   * @param v the callback to be removed
   */
  fun removeLongClickListener(v: V) = longClickListeners.remove(v)

  /**
   * Enable a data-driven property. Please visit https://docs.mapbox.com/android/maps/guides/data-driven-styling/ for more details about data-driven-styling
   */
  fun enableDataDrivenProperty(property: String)
}