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
   * @return id for given view annotation. Actual [View] could be obtained by [findViewById].
   * @throws [RuntimeException] if options did not include geometry.
   */
  fun addViewAnnotation(
    @LayoutRes id: Int,
    options: ViewAnnotationOptions
  ): String

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
    result: (String) -> Unit
  )

  /**
   * Remove given annotation view by [id]. It will remove actual view from map completely as well.
   */
  fun removeViewAnnotation(id: String)

  /**
   * Update given view annotation [id] with [ViewAnnotationOptions].
   * Important thing to keep in mind that only properties present in [options] will be updated,
   * all other will remain the same as specified before.
   */
  fun updateViewAnnotation(
    id: String,
    options: ViewAnnotationOptions
  )

  /**
   * Find [View] by icon id if it was specified as part of [ViewAnnotationOptions.iconIdentifier].
   *
   * @return [View] if view was found and NULL otherwise.
   */
  fun findViewAnnotationByMarkerId(
    markerId: String
  ): View?

  /**
   * Find [View] by view annotation id.
   *
   * @return [View] if view was found and NULL otherwise.
   */
  fun findViewAnnotationById(
    id: String
  ): View?
}