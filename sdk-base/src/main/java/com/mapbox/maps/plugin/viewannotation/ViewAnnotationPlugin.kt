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
   * Add annotation view inflated from [resId] synchronously.
   *
   * Annotation [options] must include Geometry where we want to bind our annotation view.
   *
   * Width and height could be specified explicitly but better idea will be not specifying them
   * as they will be calculated automatically based on view layout.
   *
   * If view gets resized based on resized view's content - this will be handled automatically.
   *
   * @return id for given view annotation. Actual [View] could be obtained by [getViewAnnotationById].
   * @throws [RuntimeException] if options did not include geometry.
   */
  fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions
  ): String

  /**
   * Add annotation view inflated from [resId] asynchronously.
   * Inflated [View] will be delivered in [result] callback.
   *
   * Annotation [options] must include Geometry where we want to bind our annotation view.
   *
   * Width and height could be specified explicitly but better idea will be not specifying them
   * as they will be calculated automatically based on view layout.
   *
   * If view gets resized based on resized view's content - this will be handled automatically.
   *
   * @throws [RuntimeException] if options did not include geometry.
   */
  fun addViewAnnotation(
    @LayoutRes resId: Int,
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
  fun getViewAnnotationByMarkerId(markerId: String): View?

  /**
   * Find [View] by view annotation id.
   *
   * @return [View] if view was found and NULL otherwise.
   */
  fun getViewAnnotationById(id: String): View?

  /**
   * Get current [ViewAnnotationOptions] by icon id if it was specified as part of [ViewAnnotationOptions.iconIdentifier].
   *
   * @return [ViewAnnotationOptions] if view was found and NULL otherwise.
   */
  fun getViewAnnotationOptionsByMarkerId(markerId: String): ViewAnnotationOptions?

  /**
   * Get current [ViewAnnotationOptions] by view annotation id.
   *
   * @return [ViewAnnotationOptions] if view was found and NULL otherwise.
   */
  fun getViewAnnotationOptionsById(id: String): ViewAnnotationOptions?
}