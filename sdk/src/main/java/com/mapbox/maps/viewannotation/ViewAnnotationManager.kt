package com.mapbox.maps.viewannotation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.annotation.VisibleForTesting
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import com.mapbox.bindgen.Expected
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import java.util.concurrent.ConcurrentHashMap

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
 */
@MapboxExperimental
class ViewAnnotationManager(
  private val mapView: MapView
) {
  private val mapboxMap: MapboxMap = mapView.getMapboxMap()
  private val viewPlugins = mapView.mapController.pluginRegistry.viewPlugins
  private val viewAnnotationPositionsUpdateListener = ViewAnnotationPositionsUpdateListener { drawAnnotationViews(it) }

  init {
    mapView.requestDisallowInterceptTouchEvent(false)
    mapboxMap.setViewAnnotationPositionsUpdateListener(viewAnnotationPositionsUpdateListener)
  }

  private val annotationMap = ConcurrentHashMap<String, ViewAnnotation>()
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val idLookupMap = ConcurrentHashMap<View, String>()
  private val hiddenViewMap = ConcurrentHashMap<View, Float>()

  // struct needed for drawing, declare it only once
  private val currentViewsDrawnMap = HashMap<String, ScreenCoordinate>()

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
  ) {
    validateOptions(options)
    asyncInflater.inflate(resId, mapView) { view, _, _ ->
      asyncInflateCallback.invoke(prepareViewAnnotation(view, options))
    }
  }

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
  ): View {
    validateOptions(options)
    val view = LayoutInflater.from(mapView.context).inflate(resId, mapView, false)
    return prepareViewAnnotation(view, options)
  }

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
  fun addViewAnnotation(view: View, options: ViewAnnotationOptions) {
    if (idLookupMap.containsKey(view)) {
      throw MapboxViewAnnotationException(
        "Trying to add view annotation that was already added before! " +
          "Please consider deleting annotation view ($view) beforehand."
      )
    }
    validateOptions(options)
    prepareViewAnnotation(view, options)
  }

  /**
   * Remove given annotation [view] from the map if it was present.
   *
   * @return true if view was removed and false if view was not found on the map.
   */
  fun removeViewAnnotation(view: View): Boolean {
    val id = idLookupMap.remove(view) ?: return false
    val annotation = annotationMap.remove(id) ?: return false
    annotation.view.removeOnAttachStateChangeListener(annotation.attachStateListener)
    mapView.removeView(view)
    getValue(mapboxMap.removeViewAnnotation(id))
    return true
  }

  /**
   * Update given view annotation [view] with [ViewAnnotationOptions].
   * Important thing to keep in mind that only properties present in [options] will be updated,
   * all other will remain the same as specified before.
   *
   * @return true if view was updated and false if view was not found on the map.
   */
  fun updateViewAnnotation(
    view: View,
    options: ViewAnnotationOptions,
  ): Boolean {
    val id = idLookupMap[view] ?: return false
    checkAssociatedFeatureIdUniqueness(options)
    annotationMap[id]?.let {
      it.handleVisibilityAutomatically = (options.visible == null)
      if (options.width != null) {
        it.measuredWidth = ViewAnnotation.USER_FIXED_DIMENSION
      }
      if (options.height != null) {
        it.measuredHeight = ViewAnnotation.USER_FIXED_DIMENSION
      }
      getValue(mapboxMap.updateViewAnnotation(id, options))
      return true
    } ?: return false
  }

  /**
   * Find [View] by feature id if it was specified as part of [ViewAnnotationOptions.associatedFeatureId].
   *
   * @return [View] if view was found and NULL otherwise.
   */
  fun getViewAnnotationByFeatureId(featureId: String): View? {
    val (view, _) = findByFeatureId(featureId)
    return view
  }

  /**
   * Find [ViewAnnotationOptions] of view annotation by feature id if it was specified as part of [ViewAnnotationOptions.associatedFeatureId].
   *
   * @return [ViewAnnotationOptions] if view annotation was found and NULL otherwise.
   */
  fun getViewAnnotationOptionsByFeatureId(featureId: String): ViewAnnotationOptions? {
    val (_, options) = findByFeatureId(featureId)
    return options
  }

  /**
   * Get current [ViewAnnotationOptions] for given [view].
   *
   * @return [ViewAnnotationOptions] if view was found and NULL otherwise.
   */
  fun getViewAnnotationOptionsByView(view: View): ViewAnnotationOptions? {
    val id = idLookupMap[view] ?: return null
    return getValue(mapboxMap.getViewAnnotationOptions(id))
  }

  internal fun destroy() {
    mapboxMap.setViewAnnotationPositionsUpdateListener(null)
    annotationMap.forEach { (id, annotation) ->
      getValue(mapboxMap.removeViewAnnotation(id))
      annotation.view.removeOnAttachStateChangeListener(annotation.attachStateListener)
      annotation.attachStateListener = null
      mapView.removeView(annotation.view)
    }
    currentViewsDrawnMap.clear()
    annotationMap.clear()
    idLookupMap.clear()
  }

  private fun validateOptions(options: ViewAnnotationOptions) {
    if (options.geometry == null) {
      throw IllegalArgumentException(EXCEPTION_TEXT_GEOMETRY_IS_NULL)
    }
  }

  private fun checkAssociatedFeatureIdUniqueness(options: ViewAnnotationOptions) {
    options.associatedFeatureId?.let { associatedFeatureId ->
      val (view, _) = findByFeatureId(associatedFeatureId)
      if (view != null) {
        throw MapboxViewAnnotationException(
          String.format(
            EXCEPTION_TEXT_ASSOCIATED_FEATURE_ID_ALREADY_EXISTS,
            associatedFeatureId
          )
        )
      }
    }
  }

  private fun prepareViewAnnotation(inflatedView: View, options: ViewAnnotationOptions): View {
    checkAssociatedFeatureIdUniqueness(options)
    val inflatedViewLayout = inflatedView.layoutParams as FrameLayout.LayoutParams
    val updatedOptions = options.toBuilder()
      .width(options.width ?: inflatedViewLayout.width)
      .height(options.height ?: inflatedViewLayout.height)
      .build()
    val viewAnnotation = ViewAnnotation(
      view = inflatedView,
      handleVisibilityAutomatically = (options.visible == null),
      visible = (inflatedView.visibility == View.VISIBLE),
      viewLayoutParams = inflatedViewLayout,
      measuredWidth = if (options.width != null) ViewAnnotation.USER_FIXED_DIMENSION else inflatedViewLayout.width,
      measuredHeight = if (options.height != null) ViewAnnotation.USER_FIXED_DIMENSION else inflatedViewLayout.height,
    )
    val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
      if (viewAnnotation.measuredWidth != ViewAnnotation.USER_FIXED_DIMENSION &&
        inflatedView.measuredWidth > 0 &&
        inflatedView.measuredWidth != viewAnnotation.measuredWidth
      ) {
        viewAnnotation.measuredWidth = inflatedView.measuredWidth
        getValue(
          mapboxMap.updateViewAnnotation(
            viewAnnotation.id,
            ViewAnnotationOptions.Builder()
              .width(inflatedView.measuredWidth)
              .build()
          )
        )
      }
      if (viewAnnotation.measuredHeight != ViewAnnotation.USER_FIXED_DIMENSION &&
        inflatedView.measuredHeight > 0 &&
        inflatedView.measuredHeight != viewAnnotation.measuredHeight
      ) {
        viewAnnotation.measuredHeight = inflatedView.measuredHeight
        getValue(
          mapboxMap.updateViewAnnotation(
            viewAnnotation.id,
            ViewAnnotationOptions.Builder()
              .height(inflatedView.measuredHeight)
              .build()
          )
        )
      }
      if (viewAnnotation.handleVisibilityAutomatically) {
        val isVisibleNow = (inflatedView.visibility == View.VISIBLE)
        if (isVisibleNow == viewAnnotation.visible) {
          return@OnGlobalLayoutListener
        }
        viewAnnotation.visible = isVisibleNow
        // hide view below map surface and pull it back when new position from core will arrive
        if (isVisibleNow) {
          hiddenViewMap[inflatedView] = inflatedView.translationZ
          inflatedView.translationZ = mapView.translationZ - 1f
        }
        if (getValue(mapboxMap.getViewAnnotationOptions(viewAnnotation.id))?.visible != isVisibleNow) {
          getValue(
            mapboxMap.updateViewAnnotation(
              viewAnnotation.id,
              ViewAnnotationOptions.Builder()
                .visible(isVisibleNow)
                .build()
            )
          )
        }
      }
    }
    viewAnnotation.attachStateListener = object : View.OnAttachStateChangeListener {
      override fun onViewAttachedToWindow(v: View?) {
        inflatedView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
      }

      override fun onViewDetachedFromWindow(v: View?) {
        inflatedView.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
      }
    }
    inflatedView.addOnAttachStateChangeListener(viewAnnotation.attachStateListener)
    annotationMap[viewAnnotation.id] = viewAnnotation
    idLookupMap[inflatedView] = viewAnnotation.id
    getValue(mapboxMap.addViewAnnotation(viewAnnotation.id, updatedOptions))
    return inflatedView
  }

  private fun findByFeatureId(featureId: String): Pair<View?, ViewAnnotationOptions?> {
    annotationMap.forEach { (id, annotation) ->
      getValue(mapboxMap.getViewAnnotationOptions(id))?.let { options ->
        if (options.associatedFeatureId == featureId) {
          return annotation.view to options
        }
      }
    }
    return null to null
  }

  private fun drawAnnotationViews(
    positionDescriptorCoreList: List<ViewAnnotationPositionDescriptor>
  ) {
    // firstly delete views that do not belong to the viewport
    currentViewsDrawnMap.keys.forEach { id ->
      if (positionDescriptorCoreList.indexOfFirst { it.identifier == id } == -1) {
        annotationMap[id]?.let { annotation ->
          // if view is invisible / gone we don't remove it so that visibility logic could
          // still be handled by OnGlobalLayoutListener
          if (annotation.view.visibility == View.VISIBLE) {
            mapView.removeView(annotation.view)
          }
        }
      }
    }
    // add and reposition new and existed views
    positionDescriptorCoreList.forEach { descriptor ->
      annotationMap[descriptor.identifier]?.let { annotation ->
        // update layout params explicitly if user has specified concrete width or height
        annotation.viewLayoutParams.apply {
          if (annotation.measuredWidth == ViewAnnotation.USER_FIXED_DIMENSION) {
            width = descriptor.width
          }
          if (annotation.measuredHeight == ViewAnnotation.USER_FIXED_DIMENSION) {
            height = descriptor.height
          }
        }
        annotation.view.apply {
          translationX = descriptor.leftTopCoordinate.x.toFloat()
          translationY = descriptor.leftTopCoordinate.y.toFloat()
        }
        if (!currentViewsDrawnMap.keys.contains(descriptor.identifier) && mapView.indexOfChild(annotation.view) == -1) {
          mapView.addView(annotation.view, annotation.viewLayoutParams)
        }
        hiddenViewMap[annotation.view]?.let { zIndex ->
          annotation.view.translationZ = zIndex
          hiddenViewMap.remove(annotation.view)
        }
        // as we preserve correct order we bring each view to the front and correct order will be preserved
        annotation.view.bringToFront()
      }
    }
    // bring to front map view plugins so that they are drawn on top of view annotations
    viewPlugins.forEach {
      it.value.bringToFront()
    }
    currentViewsDrawnMap.clear()
    positionDescriptorCoreList.forEach {
      currentViewsDrawnMap[it.identifier] = it.leftTopCoordinate
    }
  }

  private inline fun <reified V> getValue(expected: Expected<String, V>): V? {
    if (expected.isError) {
      throw MapboxViewAnnotationException(expected.error)
    }
    return expected.value
  }

  companion object {
    internal const val EXCEPTION_TEXT_GEOMETRY_IS_NULL = "Geometry can not be null!"
    internal const val EXCEPTION_TEXT_ASSOCIATED_FEATURE_ID_ALREADY_EXISTS =
      "View annotation with associatedFeatureId=%s already exists!"
  }
}