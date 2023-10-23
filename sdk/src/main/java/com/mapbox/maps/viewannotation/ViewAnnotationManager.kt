package com.mapbox.maps.viewannotation

import android.view.View
import androidx.annotation.LayoutRes
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import com.mapbox.geojson.Geometry
import com.mapbox.maps.AnnotatedLayerFeature
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.ViewAnnotationOptions

/**
 * Manager API to control View Annotations.
 *
 * View annotations are Android [View]s on top of the [MapView] and bound to some [Geometry] or [AnnotatedLayerFeature].
 *
 * In case some view annotations intersect on the screen Z-index is based on addition order.
 *
 * View annotations are invariant to map camera transformations however such properties as size, visibility etc
 * could be controlled by the user using update operation.
 *
 * View annotation manager instance is destroyed automatically when [MapView.onDestroy] is called.
 */
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
   * Find [View] by annotated layer feature if it was specified as part of [ViewAnnotationOptions.getAnnotatedFeature].
   *
   * @return [View] if view was found and NULL otherwise.
   */
  fun getViewAnnotation(annotatedLayerFeature: AnnotatedLayerFeature): View?

  /**
   * Find [ViewAnnotationOptions] by annotated layer feature if it was specified as part of [ViewAnnotationOptions.getAnnotatedFeature].
   *
   * @return [ViewAnnotationOptions] if view was found and NULL otherwise.
   */
  fun getViewAnnotationOptions(annotatedLayerFeature: AnnotatedLayerFeature): ViewAnnotationOptions?

  /**
   * Get current [ViewAnnotationOptions] for given [view].
   *
   * @return [ViewAnnotationOptions] if view was found and NULL otherwise.
   */
  fun getViewAnnotationOptions(view: View): ViewAnnotationOptions?

  /**
   * Add an instance of [OnViewAnnotationUpdatedListener].
   */
  fun addOnViewAnnotationUpdatedListener(listener: OnViewAnnotationUpdatedListener)

  /**
   * Remove an instance of [OnViewAnnotationUpdatedListener].
   */
  fun removeOnViewAnnotationUpdatedListener(listener: OnViewAnnotationUpdatedListener)

  /**
   * Set view annotation update mode defined in [ViewAnnotationUpdateMode].
   */
  fun setViewAnnotationUpdateMode(mode: ViewAnnotationUpdateMode)

  /**
   * Get view annotation update mode.
   */
  fun getViewAnnotationUpdateMode(): ViewAnnotationUpdateMode

  /**
   * Return Map of added [View] with their [ViewAnnotationOptions]. This method should be called every time
   * to get all added [View]s and associated [ViewAnnotationOptions].
   *
   * Note: Modifying [ViewAnnotationOptions] will not update actual annotation options. Use
   * [ViewAnnotationManager.updateViewAnnotation] instead. Modifying [View] will update the actual view property.
   *
   * @return Map of [View] and associated [ViewAnnotationOptions].
   */
  val annotations: Map<View, ViewAnnotationOptions>

  /**
   * Return camera options bound to given view annotation list, padding, bearing and pitch values.
   * Annotations with [ViewAnnotationOptions.visible] set to false will be excluded from the calculations of [CameraOptions].
   * Annotations with only [View.VISIBLE] will be included in the calculations for [CameraOptions]
   *
   * Note: This API isn't supported by Globe projection and will return NULL.
   * Calling this API immediately after adding the view is a no-op.
   * Please refer to [OnViewAnnotationUpdatedListener] documentation for understanding the exact moment of time when
   * view annotation is positioned.
   *
   * @param annotations view annotation list to be shown. Annotations should be added beforehand
   * with [ViewAnnotationManager.addViewAnnotation] API.
   * @param edgeInsets paddings to apply.
   * @param bearing camera bearing to apply.
   * @param pitch camera pitch to apply.
   *
   * @return [CameraOptions] object or NULL if [annotations] list is empty.
   *
   */
  fun cameraForAnnotations(
    annotations: List<View>,
    edgeInsets: EdgeInsets? = null,
    bearing: Double? = null,
    pitch: Double? = null
  ): CameraOptions?

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * Default view annotation update mode.
     */
    @JvmField
    val DEFAULT_UPDATE_MODE = ViewAnnotationUpdateMode.MAP_SYNCHRONIZED
  }
}