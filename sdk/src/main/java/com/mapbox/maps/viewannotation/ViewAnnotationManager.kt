package com.mapbox.maps.viewannotation

import android.view.View
import androidx.annotation.LayoutRes
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ViewAnnotationOptions

/**
 * Manager API to control View Annotations.
 *
 * View annotations are Android [View]s that are drawn on top of the [MapView] and bound to some [Geometry] (only [Point] is supported for now).
 * In case some view annotations intersect on the screen Z-index is based on addition order.
 *
 * View annotations are invariant to map camera transformations however such properties as size, visibility etc
 * could be controlled by the user using update operation.
 *
 * View annotations are not explicitly bound to any sources however [ViewAnnotationOptions.associatedFeatureId] could be
 * used to bind given view annotation with some [Feature] by [Feature.id] meaning visibility of view annotation will be driven
 * by visibility of given feature.
 *
 * View annotation manager instance is destroyed automatically when [MapView.onDestroy] is called.
 */
@MapboxExperimental
interface ViewAnnotationManager {

  /**
   * Add view annotation inflated from [resId] synchronously.
   *
   * Annotation [options] must include Geometry where we want to bind our view annotation.
   *
   * Width and height could be specified explicitly but better idea will be not specifying them
   * as they will be calculated automatically based on view layout.
   *
   * @param resId layout resource id
   * @param options view annotation options
   *
   * @return inflated [View].
   *
   * @throws [RuntimeException] if options did not include geometry.
   */
  fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions
  ): View

  /**
   * Add view annotation inflated from [resId] asynchronously.
   *
   * In order to use this function please add following [dependency](https://mvnrepository.com/artifact/androidx.asynclayoutinflater/asynclayoutinflater/1.0.0) to your project.
   *
   * Annotation [options] must include Geometry where we want to bind our view annotation.
   *
   * Width and height could be specified explicitly but better idea will be not specifying them
   * as they will be calculated automatically based on view layout.
   *
   * @param resId layout resource id
   * @param options view annotation options
   * @param asyncInflater instance of [AsyncLayoutInflater] provided by the user
   * @param asyncInflateCallback callback triggered when [View] is inflated.
   *
   * @throws [RuntimeException] if options did not include geometry.
   */
  fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions,
    asyncInflater: AsyncLayoutInflater,
    asyncInflateCallback: (View) -> Unit
  )

  /**
   * Add annotation [View] which is already inflated.
   * View dimensions will be taken as width / height from view's layout params
   * unless they are not specified explicitly with [ViewAnnotationOptions.Builder.width] and [ViewAnnotationOptions.Builder.height].
   *
   * Annotation [options] must include Geometry where we want to bind our view annotation.
   *
   * Width and height could be specified explicitly but better idea will be not specifying them
   * as they will be calculated automatically based on view layout.
   *
   * @param view view that was already inflated
   * @param options view annotation options
   *
   * @throws [RuntimeException] if options did not include geometry.
   */
  fun addViewAnnotation(
    view: View,
    options: ViewAnnotationOptions
  )

  /**
   * Remove given annotation [view] from the map if it was present.
   *
   * @return true if view was removed and false if view was not found on the map.
   */
  fun removeViewAnnotation(view: View): Boolean

  /**
   * Remove all view annotations.
   */
  fun removeAllViewAnnotations()

  /**
   * Update given view annotation [view] with [ViewAnnotationOptions].
   * Important thing to keep in mind that only properties present in [options] will be updated,
   * all other will remain the same as specified before.
   *
   * @return true if view was updated and false if view was not found on the map.
   */
  fun updateViewAnnotation(
    view: View,
    options: ViewAnnotationOptions
  ): Boolean

  /**
   * Find [View] by feature id if it was specified as part of [ViewAnnotationOptions.associatedFeatureId].
   *
   * @return [View] if view was found and NULL otherwise.
   */
  fun getViewAnnotationByFeatureId(featureId: String): View?

  /**
   * Find [ViewAnnotationOptions] of view annotation by feature id if it was specified as part of [ViewAnnotationOptions.associatedFeatureId].
   *
   * @return [ViewAnnotationOptions] if view annotation was found and NULL otherwise.
   */
  fun getViewAnnotationOptionsByFeatureId(featureId: String): ViewAnnotationOptions?

  /**
   * Get current [ViewAnnotationOptions] for given [view].
   *
   * @return [ViewAnnotationOptions] if view was found and NULL otherwise.
   */
  fun getViewAnnotationOptionsByView(view: View): ViewAnnotationOptions?

  /**
   * Add an instance of [OnViewAnnotationUpdatedListener].
   */
  fun addOnViewAnnotationUpdatedListener(listener: OnViewAnnotationUpdatedListener)

  /**
   * Remove an instance of [OnViewAnnotationUpdatedListener].
   */
  fun removeOnViewAnnotationUpdatedListener(listener: OnViewAnnotationUpdatedListener)
}