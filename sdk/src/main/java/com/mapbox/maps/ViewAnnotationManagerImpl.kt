package com.mapbox.maps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.annotation.VisibleForTesting
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import com.mapbox.bindgen.Expected
import com.mapbox.maps.viewannotation.ViewAnnotation
import com.mapbox.maps.viewannotation.ViewAnnotation.Companion.USER_FIXED_DIMENSION
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.HashMap

internal class ViewAnnotationManagerImpl(
  private val mapView: MapView
) : ViewAnnotationManager, ViewAnnotationPositionsUpdateListener {

  private val mapboxMap: MapboxMap = mapView.getMapboxMap()
  private val viewPlugins = mapView.mapController.pluginRegistry.viewPlugins

  init {
    mapView.requestDisallowInterceptTouchEvent(false)
    mapboxMap.setViewAnnotationPositionsUpdateListener(this)
  }

  private val annotationMap = ConcurrentHashMap<String, ViewAnnotation>()
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val idLookupMap = ConcurrentHashMap<View, String>()
  private val hiddenViewMap = ConcurrentHashMap<View, Float>()

  // struct needed for drawing, declare it only once
  private val currentViewsDrawnMap = HashMap<String, ScreenCoordinate>()

  override fun addViewAnnotation(
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

  override fun addViewAnnotation(
    @LayoutRes resId: Int,
    options: ViewAnnotationOptions
  ): View {
    validateOptions(options)
    val view = LayoutInflater.from(mapView.context).inflate(resId, mapView, false)
    return prepareViewAnnotation(view, options)
  }

  override fun addViewAnnotation(view: View, options: ViewAnnotationOptions) {
    if (idLookupMap.containsKey(view)) {
      throw MapboxViewAnnotationException(
        "Trying to add view annotation that was already added before! " +
          "Please consider deleting annotation view ($view) beforehand."
      )
    }
    validateOptions(options)
    prepareViewAnnotation(view, options)
  }

  override fun removeViewAnnotation(view: View): Boolean {
    val id = idLookupMap.remove(view) ?: return false
    val annotation = annotationMap.remove(id) ?: return false
    annotation.view.removeOnAttachStateChangeListener(annotation.attachStateListener)
    mapView.removeView(view)
    getValue(mapboxMap.removeViewAnnotation(id))
    return true
  }

  override fun updateViewAnnotation(
    view: View,
    options: ViewAnnotationOptions,
  ): Boolean {
    val id = idLookupMap[view] ?: return false
    checkAssociatedFeatureIdUniqueness(options)
    annotationMap[id]?.let {
      it.handleVisibilityAutomatically = (options.visible == null)
      if (options.width != null) {
        it.measuredWidth = USER_FIXED_DIMENSION
      }
      if (options.height != null) {
        it.measuredHeight = USER_FIXED_DIMENSION
      }
      getValue(mapboxMap.updateViewAnnotation(id, options))
      return true
    } ?: return false
  }

  override fun getViewAnnotationByFeatureId(featureId: String): View? {
    val (view, _) = findByFeatureId(featureId)
    return view
  }

  override fun getViewAnnotationOptionsByFeatureId(featureId: String): ViewAnnotationOptions? {
    val (_, options) = findByFeatureId(featureId)
    return options
  }

  override fun getViewAnnotationOptionsByView(view: View): ViewAnnotationOptions? {
    val id = idLookupMap[view] ?: return null
    return getValue(mapboxMap.getViewAnnotationOptions(id))
  }

  override fun onViewAnnotationPositionsUpdate(positions: MutableList<ViewAnnotationPositionDescriptor>) {
    drawAnnotationViews(positions)
  }

  fun destroy() {
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
      measuredWidth = if (options.width != null) USER_FIXED_DIMENSION else inflatedViewLayout.width,
      measuredHeight = if (options.height != null) USER_FIXED_DIMENSION else inflatedViewLayout.height,
    )
    val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
      if (viewAnnotation.measuredWidth != USER_FIXED_DIMENSION &&
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
      if (viewAnnotation.measuredHeight != USER_FIXED_DIMENSION &&
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
          if (annotation.measuredWidth == USER_FIXED_DIMENSION) {
            width = descriptor.width
          }
          if (annotation.measuredHeight == USER_FIXED_DIMENSION) {
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