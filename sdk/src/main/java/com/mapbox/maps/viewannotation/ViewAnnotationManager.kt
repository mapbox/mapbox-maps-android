package com.mapbox.maps.viewannotation

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ViewAnnotationOptions
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener

@MapboxExperimental
interface ViewAnnotationManager : OnCameraChangeListener {

  /**
   * Add annotation view inflated from [resId] synchronously.
   * Parent layout of [resId] must have fixed dimensions and should not use [ViewGroup.LayoutParams.WRAP_CONTENT].
   *
   * Annotation [options] must include Geometry where we want to bind our annotation view.
   *
   * Width and height could be specified explicitly but better idea will be not specifying them
   * as they will be calculated automatically based on view layout.
   *
   * @param resId layout resource id
   * @param options view annotation options
   *
   * @return [ViewAnnotation] containing id to manipulate with view annotation, inflated [View] with layout params
   *
   * @throws [RuntimeException] if options did not include geometry.
   */
  fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions
  ): ViewAnnotation

  /**
   * Add annotation view inflated from [resId] asynchronously.
   * Parent layout of [resId] must have fixed dimensions and should not use [ViewGroup.LayoutParams.WRAP_CONTENT].
   *
   * In order to use this function please add following [dependency](https://mvnrepository.com/artifact/androidx.asynclayoutinflater/asynclayoutinflater/1.0.0) to your project.
   *
   * Annotation [options] must include Geometry where we want to bind our annotation view.
   *
   * Width and height could be specified explicitly but better idea will be not specifying them
   * as they will be calculated automatically based on view layout.
   *
   * @param resId layout resource id
   * @param options view annotation options
   * @param asyncInflateCallback callback triggered when [ViewAnnotation] containing id to manipulate with view annotation,
   * inflated [View] with layout params is added.
   *
   * @throws [RuntimeException] if options did not include geometry or async inflater [dependency](https://mvnrepository.com/artifact/androidx.asynclayoutinflater/asynclayoutinflater/1.0.0) was not added.
   */
  fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions,
    asyncInflateCallback: ((ViewAnnotation) -> Unit)
  )

  /**
   * Add annotation [View].
   * View dimensions will be taken as width / height from view's layout params
   * unless they are not specified explicitly with [ViewAnnotationOptions.Builder.width] and [ViewAnnotationOptions.Builder.height].
   *
   * Annotation [options] must include Geometry where we want to bind our annotation view.
   *
   * Width and height could be specified explicitly but better idea will be not specifying them
   * as they will be calculated automatically based on view layout.
   *
   * @param view view that was already inflated
   * @param options view annotation options
   *
   * @return [ViewAnnotation] containing id to manipulate with view annotation, inflated [View] with layout params
   *
   * @throws [RuntimeException] if options did not include geometry.
   */
  fun addViewAnnotation(
    view: View,
    options: ViewAnnotationOptions
  ): ViewAnnotation

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
   * Find [ViewAnnotation] by feature id if it was specified as part of [ViewAnnotationOptions.associatedFeatureId].
   *
   * @return [ViewAnnotation] if view was found and NULL otherwise.
   */
  fun getViewAnnotationByFeatureId(featureId: String): ViewAnnotation?

  /**
   * Find [ViewAnnotationOptions] of view annotation by feature id if it was specified as part of [ViewAnnotationOptions.associatedFeatureId].
   *
   * @return [ViewAnnotationOptions] if view annotation was found and NULL otherwise.
   */
  fun getViewAnnotationOptionsByFeatureId(featureId: String): ViewAnnotationOptions?

  /**
   * Find [ViewAnnotation] by view annotation id.
   *
   * @return [ViewAnnotation] if view was found and NULL otherwise.
   */
  fun getViewAnnotationById(id: String): ViewAnnotation?

  /**
   * Get current [ViewAnnotationOptions] by view annotation id.
   *
   * @return [ViewAnnotationOptions] if view was found and NULL otherwise.
   */
  fun getViewAnnotationOptionsById(id: String): ViewAnnotationOptions?
}