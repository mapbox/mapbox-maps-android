package com.mapbox.maps.plugin.viewannotation

import android.view.View
import androidx.annotation.LayoutRes
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ViewAnnotationOptions
import com.mapbox.maps.plugin.ContextBinder
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener

@MapboxExperimental
interface ViewAnnotationPlugin: MapPlugin, ContextBinder, OnCameraChangeListener {

  /**
   * Add annotation view inflated from [id] synchronously.
   *
   * Annotation [options] must include Geometry where we want to bind our annotation view.
   *
   * Width and height could be specified explicitly but better idea will be not specifying them
   * as they will be calculated automatically based on view layout.
   *
   * @return Inflated [View] instance.
   * @throws [RuntimeException] if options did not include geometry.
   */
  fun addViewAnnotation(
    @LayoutRes id: Int,
    options: ViewAnnotationOptions
  ): View

  /**
   * Add annotation view inflated from [id] asynchronously.
   * Inflated [View] will be delivered in [result] callback.
   *
   * Annotation [options] must include Geometry where we want to bind our annotation view.
   *
   * Width and height could be specified explicitly but better idea will be not specifying them
   * as they will be calculated automatically based on view layout.
   *
   * @throws [RuntimeException] if options did not include geometry.
   */
  fun addViewAnnotation(
    @LayoutRes id: Int,
    options: ViewAnnotationOptions,
    result: (View) -> Unit
  )

  /**
   * Remove given annotation [view]. It will remove it from map completely.
   */
  fun removeViewAnnotation(view: View)

  /**
   * Update given [View] with [ViewAnnotationOptions].
   * Important thing to keep in mind that only properties present in [options] will be updated,
   * all other will remain the same as specified before.
   */
  fun updateViewAnnotation(
    view: View,
    options: ViewAnnotationOptions
  )

  /**
   * Find [View] by icon id if it was specified as part of [ViewAnnotationOptions.iconIdentifier].
   *
   * @return [View] if view was found and NULL otherwise.
   */
  fun findViewAnnotation(
    markerId: String
  ): View?
}